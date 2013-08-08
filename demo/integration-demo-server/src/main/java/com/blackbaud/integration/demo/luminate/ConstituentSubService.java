package com.blackbaud.integration.demo.luminate;

import com.blackbaud.integration.generated.errors.FailedAuthenticationException;
import com.blackbaud.integration.generated.errors.NoSuchRecordException;
import com.blackbaud.integration.generated.types.BlackbaudRecordIds;
import com.blackbaud.integration.generated.types.Credential;
import com.blackbaud.integration.generated.types.EmailCampaign;
import com.blackbaud.integration.generated.types.EmailMessage;
import com.blackbaud.integration.generated.types.EmailMessageDelivery;
import com.blackbaud.integration.generated.types.UnboundedDateRange;
import com.blackbaud.integration.security.server.Secured;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.inject.Singleton;
import org.apache.thrift.TException;

@Singleton
@Secured
class ConstituentSubService {

		public List<EmailMessageDelivery> getConstituentEmailCommunicationHistory(Credential credential, BlackbaudRecordIds constituentIds, UnboundedDateRange dateRange) throws FailedAuthenticationException, NoSuchRecordException, TException {

				final EmailCampaign fakeCampaign1 = new EmailCampaign(
								new BlackbaudRecordIds().setLuminateId(123456), "Fake Campaign", "Email Appeal");
				
				final EmailCampaign fakeCampaign2 = new EmailCampaign(
								new BlackbaudRecordIds().setLuminateId(345678), "Another Fake Campaign", "Email Appeal");

				final EmailMessage fakeMessage1 = new EmailMessage(
								new BlackbaudRecordIds().setLuminateId(6791011),
								"Fake Message", 2 /* email version */,
								"http://foo.bar.com/messsage/6791011/2", fakeCampaign1);
				
				final EmailMessage fakeMessage2 = new EmailMessage(
								new BlackbaudRecordIds().setLuminateId(383388),
								"Another Fake Message", 4 /* email version */,
								"http://foo.bar.com/messsage/383388/4", fakeCampaign1);
				
				final EmailMessage fakeMessage3 = new EmailMessage(
								new BlackbaudRecordIds().setLuminateId(83827),
								"Yet Another Fake Message", 1 /* email version */,
								"http://foo.bar.com/messsage/83827/1", fakeCampaign2);


				final long nowMillis = new Date().getTime();
				return Arrays.asList(
								new EmailMessageDelivery(constituentIds, fakeMessage1, nowMillis - 4000, nowMillis - 200),
								new EmailMessageDelivery(constituentIds, fakeMessage2, nowMillis - 8000, nowMillis - 210),
								new EmailMessageDelivery(constituentIds, fakeMessage3, nowMillis - 1000, nowMillis - 220)
								);
		}
}
