package org.kubek2k.springockito.general.threading;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( locations = "classpath:spring/general/threading/context.xml" )
public class ThreadSafeMock_Test
{

	@Test
	public void shouldBeThreadSafe()
		throws InterruptedException
	{
		testThreadSafeSchemaMockAttribute( this.threadSafe, true );
	}

	@Test
	public void shouldBeThreadUnsafe()
		throws InterruptedException
	{
		testThreadSafeSchemaMockAttribute( this.threadUnsafe, false );
	}

	@Test
	public void shouldBeThreadUnsafeByDefault()
		throws InterruptedException
	{
		testThreadSafeSchemaMockAttribute( this.threadUnsafeDefault, false );
	}

	private void testThreadSafeSchemaMockAttribute( final SomeBean bean, final boolean expectedThreadSafetyStatus )
		throws InterruptedException
	{

		final AtomicBoolean isInCorrectState = new AtomicBoolean( true );
		final AtomicBoolean threadSafety = new AtomicBoolean( true );

		// NOT: called by first thread
		// NOTE: when first thread calls this method it set some state to in incorrect state, then
		// sleeps, then set state to the correct one.
		given( bean.someMethod( "firstThread" ) ).will( new Answer<String>()
		{

			public String answer( final InvocationOnMock invocation )
				throws Throwable
			{
				isInCorrectState.set( false );
				Thread.sleep( 2000 );
				isInCorrectState.set( true );
				return "firstThread";
			}
		} );
		// NOT: called by second thread
		// NOTE: second thread check if it is in correct state, if not it means that in executed
		// during first thread call (probably when first thread slept)
		given( bean.someMethod( "secondThread" ) ).will( new Answer<String>()
		{

			public String answer( final InvocationOnMock invocation )
				throws Throwable
			{
				if ( !isInCorrectState.get() )
				{
					threadSafety.set( false );
				}
				return "secondThread";
			}
		} );

		// NOTE: first thread starts immediately
		final Thread t1 = new Thread( new Runnable()
		{

			public void run()
			{
				bean.someMethod( "firstThread" );
			}
		} );
		// NOTE: second thread waits for the first thread to give it chance to set incorrect state,
		// then runs when (hopefully) first thread is sleeping
		// NOTE: if thread safe then should call mock method only after first thread end its
		// invocation
		final Thread t2 = new Thread( new Runnable()
		{

			public void run()
			{
				try
				{
					Thread.sleep( 1000 );
				}
				catch(final InterruptedException e)
				{
					throw new RuntimeException( e );
				}
				bean.someMethod( "secondThread" );
			}
		} );

		t1.start();
		t2.start();

		t1.join();
		t2.join();

		assertThat( threadSafety.get() ).isEqualTo( expectedThreadSafetyStatus );
	}

	@Resource
	private SomeBean threadSafe;

	@Resource
	private SomeBean threadUnsafe;

	@Resource
	private SomeBean threadUnsafeDefault;
}
