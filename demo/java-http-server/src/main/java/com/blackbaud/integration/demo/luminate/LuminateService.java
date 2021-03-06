package com.blackbaud.integration.demo.luminate;

import com.blackbaud.integration.generated.errors.FailedAuthenticationException;
import com.blackbaud.integration.generated.services.LuminatOnline;
import com.blackbaud.integration.generated.types.Credential;
import com.blackbaud.integration.security.server.Secured;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.Delegate;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;

@Slf4j
@Singleton
class LuminateService implements LuminatOnline.Iface {
		
		@Delegate
		private final ConstituentSubService consSvc;
		
		@Inject
		public LuminateService(ConstituentSubService consSvc) {
				this.consSvc = consSvc;
		}

		@Secured
		public String echo(Credential credential, String toBeEchoed) throws FailedAuthenticationException, TException {

				log.info("Received request to echo '{}'", toBeEchoed);

				return toBeEchoed;
		}
}
