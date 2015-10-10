package com.bhargav.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class AppInit implements WebApplicationInitializer{

	public void onStartup(ServletContext servletContext) throws ServletException {
		
		System.out.println("Starting app . . .");
		
		WebApplicationContext context = getContext();
		servletContext.addListener(new ContextLoaderListener(context));
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet("DispatcherServlet", new DispatcherServlet(context));
		dispatcher.setAsyncSupported(true);
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/api/*");
	}
	
	private WebApplicationContext getContext()
	{
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.setConfigLocations("com.bhargav.config");
		return context;
	}

}
