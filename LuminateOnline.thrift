/*
 * Defines the API for server-to-server interactions
 * between discrete Blackbaud services.
 */

include "Types.thrift"
include "Errors.thrift"

// define langauge-appropriate namespaces used by 
// code generated from this .thift file
namespace java com.blackbaud.integration.luminate
namespace csharp Blackbaud.Integration.Luminate
namespace py blackbaud.integration.luminate
namespace js blackbaud.integration.luminate

/**
 * Defines operations that fetch/manipulate constituents and their related records.
 */
service ConstituentService {
	
	/** Echoes input text to confirm/deny communication with the service. */
	string echo(
		1: Types.Credential credential, 
		2: string toBeEchoed,
	) throws (1: Errors.FailedAuthenticationException authEx);

	/** Fetches history of email communications sent to a single constituent. */
	list<Types.EmailMessageDelivery> getEmailCommunicationHistory(
		1: Types.Credential credential, 
		2: Types.BlackbaudRecordIds constituentIds,
		3: Types.UnboundedDateRange dateRange,
	) throws (
		1: Errors.FailedAuthenticationException authEx,
		2: Errors.NoSuchRecordException noSuchRecEx, 
	);

}
