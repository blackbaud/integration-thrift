package com.blackbaud.integration.security.server;

import static org.junit.Assert.*;

import com.blackbaud.integration.generated.errors.FailedAuthenticationException;
import com.blackbaud.integration.security.CredentialBuilder;
import com.blackbaud.integration.security.server.SharedKeyProvider.NoSuchUserException;
import java.util.Date;
import org.easymock.EasyMock;
import org.junit.Test;

/**
 * A unit test of the {@link Authenticator} class.
 */
public class AuthenticatorTest {

		@Test
		public void testIsCredentialStale() {

				final long credentialTimeToLive = 30 * 1000;
				final long twiceTimeToLive = credentialTimeToLive * 2;

				final String sharedKey = "shared_secret_key";
				final Authenticator auth = new Authenticator(mockKeyProvider(sharedKey), credentialTimeToLive);
				final CredentialBuilder credBuilder = new CredentialBuilder("guy.noir", sharedKey);

				assertFalse(auth.isCredentialStale(credBuilder.sign()));
				assertFalse(auth.isCredentialStale(credBuilder.sign(new Date().getTime())));

				assertTrue(auth.isCredentialStale(credBuilder.sign(new Date().getTime() - twiceTimeToLive)));
				assertTrue(auth.isCredentialStale(credBuilder.sign(new Date().getTime() + twiceTimeToLive)));
		}

		@Test
		public void testIsCredentialAuthentic() throws Exception {

				final String sharedKey = "shared_secret_key";
				final Authenticator auth = new Authenticator(mockKeyProvider(sharedKey), 10);
				final CredentialBuilder credBuilder = new CredentialBuilder("guy.noir", sharedKey);

				assertTrue(auth.isCredentialAuthentic(credBuilder.sign()));

				assertFalse(auth.isCredentialAuthentic(credBuilder.sign().setApiUserName("gal.noir")));
				assertFalse(auth.isCredentialAuthentic(credBuilder.sign().setMillisSinceEpoch(123)));
				assertFalse(auth.isCredentialAuthentic(credBuilder.sign().setHmacSha256("hmac_hac")));
		}

		@Test(expected = FailedAuthenticationException.class)
		public void testAuthenticate_NullCredential() throws FailedAuthenticationException {

				final long credentialTimeToLive = 30 * 1000;
				final long twiceTimeToLive = credentialTimeToLive * 2;

				final String sharedKey = "shared_secret_key";
				final Authenticator auth = new Authenticator(mockKeyProvider(sharedKey), credentialTimeToLive);
				auth.authenticate(null);
		}

		/**
		 * @param sharedKey
		 * @return a mock {@link SharedKeyProvider} that returns the same shared key for all user names
		 */
		private SharedKeyProvider mockKeyProvider(String sharedKey) {

				final SharedKeyProvider keyProvider = EasyMock.createNiceMock(SharedKeyProvider.class);

				try
				{
						EasyMock.expect(keyProvider.lookupKey(EasyMock.anyObject(String.class))).andReturn(sharedKey).anyTimes();
				}
				catch (NoSuchUserException ex)
				{
						fail(ex.getMessage());
				}

				EasyMock.replay(keyProvider);

				return keyProvider;

		}
}
