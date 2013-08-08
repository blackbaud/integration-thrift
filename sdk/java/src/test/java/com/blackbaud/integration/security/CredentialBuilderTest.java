package com.blackbaud.integration.security;

import com.blackbaud.integration.security.CredentialBuilder;
import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;
import org.junit.Test;

/**
 * A unit test of the {@link CredentialBuilder} class.
 */
public class CredentialBuilderTest {

		@Test
		public void testSign_PreservesUsername() {

				final String username = "foo";
				assertEquals(
								username,
								new CredentialBuilder(username, "bar").sign(12345).getApiUserName());
		}

		@Test
		public void testSign_PreservesMillisSinceEpoch() {

				final long millis = 12345;
				assertEquals(
								millis,
								new CredentialBuilder("foo", "bar").sign(millis).getMillisSinceEpoch());
		}

		@Test
		public void testSign_ConsistentHmac() {

				assertEquals(
								new CredentialBuilder("foo", "bar").sign(12345).getHmacSha256(),
								new CredentialBuilder("foo", "bar").sign(12345).getHmacSha256());
		}
		
		@Test
		public void testSign_CorrectHmac() {

				final String actual = new CredentialBuilder("foo", "bar").sign(12345).getHmacSha256();

				assertEquals(
								"sIDBcqfOiui1W7Z6L+k25pA0+9gCKJWZ+E0agcdZJI4=",
								actual);
		}

		@Test
		public void testSign_HmacSensitiveToUserName() {

				assertNotEquals(
								new CredentialBuilder("foo", "bar").sign(12345).getHmacSha256(),
								new CredentialBuilder("f00", "bar").sign(12345).getHmacSha256());
		}

		@Test
		public void testSign_HmacSensitiveToKey() {

				assertNotEquals(
								new CredentialBuilder("foo", "bar").sign(12345).getHmacSha256(),
								new CredentialBuilder("foo", "b@r").sign(12345).getHmacSha256());
		}

		public void testSign_HmacSensitiveToTimeOfSignature() {

				assertEquals(
								new CredentialBuilder("foo", "bar").sign(12345).getHmacSha256(),
								new CredentialBuilder("foo", "bar").sign(12346).getHmacSha256());
		}
		
		public void testSign_HmacSensitiveToTimeOfSignature_2() throws InterruptedException {

				final CredentialBuilder credBuilder = new CredentialBuilder("foo", "bar");
				
				final String hmac1 = credBuilder.sign().getHmacSha256();
				TimeUnit.MILLISECONDS.sleep(1);
				final String hmac2 = credBuilder.sign().getHmacSha256();
				
				assertNotEquals(hmac1, hmac2);
		}
}
