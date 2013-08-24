package com.blackbaud.integration.demo;

import com.blackbaud.integration.demo.luminate.LuminateEndpoint;
import com.blackbaud.integration.demo.luminate.LuminateModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceFilter;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebListener;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access=AccessLevel.PRIVATE)
public final class Bootstrap {
		
		/**
		 * Maps url patterns to backing servlet classes
		 */
		public static final class RoutingModule extends ServletModule {

				@Override
				protected void configureServlets() {
						serve("/luminate").with(LuminateEndpoint.class);
				}
		}

		/**
		 * Configures a guice {@link Injector} and stores in the app's {@link ServletContext}.
		 */
		@WebListener
		public static final class InitGuiceInjector extends GuiceServletContextListener {

				@Override
				protected Injector getInjector() {
						return Guice.createInjector(new LuminateModule(), new RoutingModule());
				}
		}

		/**
		 * Treat all in-bound servlet requests as candidates for guice interception and injection.
		 */
		@WebFilter(urlPatterns = "/*")
		public static final class InterceptGuicyServlets extends GuiceFilter {
		}
}
