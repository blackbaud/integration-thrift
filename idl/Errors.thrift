/*
 * Contains exceptions thrown by server-to-server integration APIs.
 */

include "Types.thrift"

// define langauge-appropriate namespaces used by 
// code generated from this .thift file
namespace java com.blackbaud.integration.generated.errors
namespace csharp Blackbaud.Integration.Generated.Errors
namespace py blackbaud.integration.generated.errors
namespace js blackbaud.integration.generated.errors

enum AuthenticationFailureCode {
    /** Credential was null. */
	MISSING_CREDENTIAL = 0,
	/** Failed to authenticate the user in your credential. */
	INVALID_CREDENTIAL = 1,
	/** The credential accompanying the request was too stale. */
	EXPIRED_CREDENTIAL = 2,	
}

exception FailedAuthenticationException {
	1: required AuthenticationFailureCode failureCode,
	2: optional string message = "Authentication failed.",
}

exception NoSuchRecordException {
	1: optional string message = "The requested record could not be found.",
}

