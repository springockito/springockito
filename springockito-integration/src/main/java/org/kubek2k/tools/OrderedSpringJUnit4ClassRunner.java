package org.kubek2k.tools;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

public class OrderedSpringJUnit4ClassRunner
	extends org.springframework.test.context.junit4.SpringJUnit4ClassRunner
{

	public OrderedSpringJUnit4ClassRunner( final Class< ? > clazz )
		throws InitializationError
	{
		super( clazz );
	}

	@Override
	protected List<FrameworkMethod> computeTestMethods()
	{
		final List<FrameworkMethod> frameworkMethods = super.computeTestMethods();
		Collections.sort( frameworkMethods, new Comparator<FrameworkMethod>()
		{

			public int compare( final FrameworkMethod o1, final FrameworkMethod o2 )
			{
				final Integer i1 = getOrder( o1 );
				final Integer i2 = getOrder( o2 );
				return i1.compareTo( i2 );
			}
		} );
		return frameworkMethods;
	}

	private int getOrder( final FrameworkMethod frameworkMethod )
	{
		final Method method = frameworkMethod.getMethod();
		if ( method.isAnnotationPresent( Ordered.class ) )
		{
			return method.getAnnotation( Ordered.class ).value();
		}
		else
		{
			return Ordered.DEFAULT;
		}
	}
}
