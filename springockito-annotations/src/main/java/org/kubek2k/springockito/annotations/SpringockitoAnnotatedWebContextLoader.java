package org.kubek2k.springockito.annotations;

import org.kubek2k.springockito.annotations.internal.Loader;
import org.springframework.test.context.ContextConfigurationAttributes;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebMergedContextConfiguration;
import org.springframework.web.context.support.GenericWebApplicationContext;

public class SpringockitoAnnotatedWebContextLoader
	extends AnnotationConfigWebContextLoader
{

	@Override
	protected void customizeContext(
		final GenericWebApplicationContext context,
		final WebMergedContextConfiguration webMergedConfig )
	{
		super.customizeContext( context, webMergedConfig );
		this.loader.registerMocksAndSpies( context );
	}

	@Override
	protected Class< ? >[] detectDefaultConfigurationClasses( final Class< ? > declaringClass )
	{
		final Class< ? >[] clazz = super.detectDefaultConfigurationClasses( declaringClass );
		this.loader.defineMocksAndSpies( declaringClass );
		return clazz;
	}

	@Override
	public void processContextConfiguration( final ContextConfigurationAttributes configAttributes )
	{
		super.processContextConfiguration( configAttributes );
		this.loader.defineMocksAndSpies( configAttributes.getDeclaringClass() );
	}

	private Loader loader = new Loader();
}
