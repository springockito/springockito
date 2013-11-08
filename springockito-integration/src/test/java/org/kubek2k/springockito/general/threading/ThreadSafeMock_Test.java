package org.kubek2k.springockito.general.threading;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/general/threading/context.xml")
public class ThreadSafeMock_Test {

    @Resource
    private SomeBean threadUnsafeDefault;

    @Resource
    private SomeBean threadUnsafe;

    @Resource
    private SomeBean threadSafe;

    @Test
    public void shouldBeThreadSafe() throws InterruptedException {
        testThreadSafeSchemaMockAttribute(threadSafe, true);
    }

    @Test
    public void shouldBeThreadUnsafe() throws InterruptedException {
        testThreadSafeSchemaMockAttribute(threadUnsafe, false);
    }

    @Test
    public void shouldBeThreadUnsafeByDefault() throws InterruptedException {
        testThreadSafeSchemaMockAttribute(threadUnsafeDefault, false);
    }

    private void testThreadSafeSchemaMockAttribute(final SomeBean bean, boolean expectedThreadSafetyStatus) throws InterruptedException {

        final AtomicBoolean isInCorrectState = new AtomicBoolean(true);
        final AtomicBoolean threadSafety = new AtomicBoolean(true);

        //NOT: called by first thread
        //NOTE: when first thread calls this method it set some state to in incorrect state, then sleeps, then set state to the correct one.
        given(bean.someMethod("firstThread"))
                .will(new Answer<String>() {
                    @Override
                    public String answer(InvocationOnMock invocation) throws Throwable {
                        isInCorrectState.set(false);
                        Thread.sleep(2000);
                        isInCorrectState.set(true);
                        return "firstThread";
                    }
                });
        //NOT: called by second thread
        //NOTE: second thread check if it is in correct state, if not it means that in executed during first thread call (probably when first thread slept)
        given(bean.someMethod("secondThread"))
                .will(new Answer<String>() {
                    @Override
                    public String answer(InvocationOnMock invocation) throws Throwable {
                        if (!isInCorrectState.get()) {
                            threadSafety.set(false);
                        }
                        return "secondThread";
                    }
                });

        //NOTE: first thread starts immediately
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                bean.someMethod("firstThread");
            }
        });
        //NOTE: second thread waits for the first thread to give it chance to set incorrect state, then runs when (hopefully) first thread is sleeping
        //NOTE: if thread safe then should call mock method only after first thread end its invocation
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                bean.someMethod("secondThread");
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        assertThat(threadSafety.get())
            .isEqualTo(expectedThreadSafetyStatus);
    }
}
