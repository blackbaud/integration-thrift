/*
 * Defines the API for server-to-server interactions
 * between discrete Blackbaud services.
 */

include "Types.thrift"
include "Errors.thrift"

// define langauge-appropriate namespaces used by 
// code generated from this .thift file
namespace java com.blackbaud.integration.generated.services
namespace csharp Blackbaud.Integration.Generated.Services
namespace py blackbaud.integration.generated.services
namespace js blackbaud.integration.generated.services

/*
 * Describes services offered by Luminate Online.
 */
service LuminatOnline {
	
	/** Echoes input text to confirm/deny communication with the service. */
	string echo(
		1: Types.Credential credential, 
		2: string toBeEchoed,
	) throws (1: Errors.FailedAuthenticationException authEx);

	/** Fetches history of email communications sent to a single constituent. */
	list<Types.EmailMessageDelivery> getConstituentEmailCommunicationHistory(
		1: Types.Credential credential, 
		2: Types.BlackbaudRecordIds constituentIds,
		3: Types.UnboundedDateRange dateRange,
	) throws (
		1: Errors.FailedAuthenticationException authEx,
		2: Errors.NoSuchRecordException noSuchRecEx, 
	);

}
