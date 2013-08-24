package com.blackbaud.integration.demo.luminate;

import com.blackbaud.integration.generated.services.LuminatOnline;
import com.blackbaud.integration.security.server.Secured;
import com.blackbaud.integration.security.server.SecurityInterceptor;
import com.blackbaud.integration.security.server.SharedKeyProvider;
import com.blackbaud.integration.security.server.SharedKeyProvider.NoSuchUserException;
import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;
import com.google.inject.name.Names;

public final class LuminateModule extends AbstractModule {

		@Override
		protected void configure() {

				bind(LuminatOnline.Iface.class).to(LuminateService.class);

				bind(SharedKeyProvider.class).toInstance(new SharedKeyProvider() {
						public String lookupKey(String userName) throws NoSuchUserException {
								return "1234";
						}
				});

				bind(Long.class).annotatedWith(Names.named("maxCredentialAgeMillis")).toInstance(100 * 1000l);

				final SecurityInterceptor secInt = new SecurityInterceptor();
				requestInjection(secInt);

				bindInterceptor(Matchers.any(), Matchers.annotatedWith(Secured.class), secInt);
		}
}
