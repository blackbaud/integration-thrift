/*
 * Models data srucutures and domain objects used by the integration API.
 */

// define langauge-appropriate namespaces used by 
// code generated from this .thift file
namespace java com.blackbaud.integration.types
namespace csharp Blackbaud.Integration.Types
namespace py blackbaud.integration.types
namespace js blackbaud.integration.types

// =============================== typedefs ====================================

/** Data type for unique identifiers in the Luminate Online system. */
typedef i64 LuminateId

/** Data type for unique identifiers used by the Infinity platform. */
typedef string InfinityId

/** Precise moment in time based on milliseconds since the epoch. */
typedef i64 Timestamp

/** Data type for URL parameters */
typedef string Url

// ============================= domain types ==================================

/**
 * A signed, time-sensitive user request credential 
 * used to authenticate Blackbaud integration API calls.
 */
struct Credential {
	1: required string apiUserName,
	2: required Timestamp millisSinceEpoch,
  /** 
	 * Base 64 encoded HMAC calculated by applying SHA256 algorithm 
	 * to the combination of apiUserName and millisSinceEpoch 
	 */
	3: required string hmacSha256,
}

/**
 * A single conceptual record may be represented in multiple systems 
 * using different identifiers.  When performing an integration operation
 * on a record, it is desirable to pass all known system identifiers.
 */
struct BlackbaudRecordIds {
	1: optional LuminateId luminateId,
	2: optional InfinityId infinityId,
}

/**
 * Range of calendar dates in which both the start and end values are optional.
 * Useful for filtering the scope of data returned by an integration service.
 */
struct UnboundedDateRange {
	1: optional Timestamp startDate,
	2: optional Timestamp endDate,
}

/**
 * Models the details of an email campaign that aggregates 
 * multiple email messages.
 */
struct EmailCampaign {
	1: required BlackbaudRecordIds emailCampaignIds,
	2: required string campaignName,
	// must be a string, infinity models this as a free-form type
	3: required string campaignType,
}

/** Models a unique email payload to be delivered to constituents. */
struct EmailMessage {
	1: required BlackbaudRecordIds emailMessageIds,
	2: required string msgName,
	/** 
	 * Used in A/B testing, version number differentiates <br/>
	 * discrete messages with the same ID. 
	 */
	3: required i32 msgVersionNumber,
	4: required Url msgUrl,
	5: required EmailCampaign parentCampaign,
}

/**
 * Tracks the historical delivery of single email message
 * to a particular constituent.
 */ 
struct EmailMessageDelivery {
	1: required BlackbaudRecordIds constituentIds,
	2: required EmailMessage emailMessage,
	3: required Timestamp dateSent,
	4: required Timestamp dateOpened,
}

