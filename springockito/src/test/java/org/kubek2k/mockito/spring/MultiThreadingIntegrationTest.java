package org.kubek2k.mockito.spring;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.concurrent.atomic.AtomicBoolean;

import org.kubek2k.mockito.spring.testbeans.BeanAutowiringMock;
import org.kubek2k.mockito.spring.testbeans.InterfaceToBeMocked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@ContextConfiguration(locations = { "classpath:spring/mockitoComponentScanContext.xml" })
public class MultiThreadingIntegrationTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private BeanAutowiringMock beanAutowiringMock;

    @Autowired
    private InterfaceToBeMocked interfaceToBeMocked;

    /**
     * We want to have a situation when two separately run tests have separate
     * mocks injected. I have to use a new thread to spawn tests - it's
     * something like a junittestrunner simulator - it's caused by the fact that
     * we'll use {@link InheritableThreadLocal} to keep an instance of current
     * mocks - please
     * {@link MultiThreadingIntegrationTest#shouldInheritMocksWhenCodeThatIsBeingSpawnedByTestSpawnsANewThreads()}
     */
    @Test
    public void shouldAllowConcurrentAccessToMockitoMocks() throws InterruptedException {
        final AtomicBoolean test1AssertionOutcome = new AtomicBoolean(false);
        final AtomicBoolean test2AssertionOutcome = new AtomicBoolean(false);

        Thread testRunner = new Thread() {
            @Override
            public void run() {
                Thread test1Runner = new Thread() {
                    @Override
                    public void run() {
                        when(interfaceToBeMocked.getValue()).thenReturn(1);
                        assertAndSetOutcomeVariable(beanAutowiringMock.getValue(), 1, test1AssertionOutcome);
                    }

                };

                Thread test2Runner = new Thread() {
                    @Override
                    public void run() {
                        when(interfaceToBeMocked.getValue()).thenReturn(2);
                        assertAndSetOutcomeVariable(beanAutowiringMock.getValue(), 2, test2AssertionOutcome);
                    }
                };

                test1Runner.start();
                test2Runner.start();

                try {
                    test1Runner.join();
                    test2Runner.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    test1AssertionOutcome.set(false);
                    test2AssertionOutcome.set(false);
                }
            }
        };
        testRunner.start();
        testRunner.join();

        assertTrue(test1AssertionOutcome.get());
        assertTrue(test2AssertionOutcome.get());
    }
    


    /**
     * Check that all threads spawned by application code gets an access to the 
     * same mock
     */
    @Test
    public void shouldInheritMocksWhenCodeThatIsBeingSpawnedByTestSpawnsANewThreads() throws InterruptedException {

        final AtomicBoolean inheritedValueAssertionOutcome = new AtomicBoolean(false);
        Thread testRunner = new Thread() {
            @Override
            public void run() {
                try {
                    when(interfaceToBeMocked.getValue()).thenReturn(11);

                    // we simulate that tested code actually spawned a new
                    // thread
                    Thread inheritingThread = new Thread() {
                        @Override
                        public void run() {
                            // the simulating code is executing mocks' recorded
                            // codetry {
                            assertAndSetOutcomeVariable(beanAutowiringMock.getValue(), 11, inheritedValueAssertionOutcome);
                        }
                    };

                    inheritingThread.start();
                    inheritingThread.join();
                    
                } catch (InterruptedException e) {
                    inheritedValueAssertionOutcome.set(false);
                }
            }

        };

        testRunner.start();
        testRunner.join();

        assertTrue(inheritedValueAssertionOutcome.get());
    }

    private void assertAndSetOutcomeVariable(Integer value, Integer expectedValue, final AtomicBoolean outcomeVariable) {
        assertEquals(value, Integer.valueOf(expectedValue));
        outcomeVariable.set(value.equals(expectedValue));
    }
}
