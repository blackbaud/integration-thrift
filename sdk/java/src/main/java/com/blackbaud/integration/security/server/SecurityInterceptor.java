package com.blackbaud.integration.security.server;

import com.blackbaud.integration.generated.types.Credential;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * A method interceptor that expects and verifies the {@link Credential} argument.
 */
@Slf4j
public final class SecurityInterceptor implements MethodInterceptor {

		@Inject
		private Authenticator authenticator;

		public Object invoke(MethodInvocation invocation) throws Throwable {

				final Class<?>[] paramTypes = invocation.getMethod().getParameterTypes();
				final Object[] params = invocation.getArguments();

				if (paramTypes.length < 1 || false == Credential.class.isAssignableFrom(paramTypes[0]))
				{
						final String errMsg = String.format(
										"Cannot secure the %s method.  Expected %s as first argument.",
										invocation.getMethod(), Credential.class.getSimpleName());
						
						log.error(errMsg);
						throw new RuntimeException(errMsg);
				}
				
				authenticator.authenticate((Credential) params[0]);
				return invocation.proceed();

		}
}
