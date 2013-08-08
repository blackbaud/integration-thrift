package com.blackbaud.integration.security.server;

/**
 * Integration API requests are authenticated using a combination of username and symmetric shared key.
 * This interface describes components that can find the correct shared key value for a given API username.
 */
public interface SharedKeyProvider {

		public String lookupKey(String userName) throws NoSuchUserException;

		/**
		 * Indicates that a particular username is unknown to the API.
		 */
		public static final class NoSuchUserException extends Exception {

				public NoSuchUserException(String userName) {
						super(String.format("No user with name '%s' could be found.", userName));
				}
		}
}
