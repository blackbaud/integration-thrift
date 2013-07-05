package com.blackbaud.integration.demo;

import com.blackbaud.integration.errors.FailedAuthenticationException;
import com.blackbaud.integration.errors.NoSuchRecordException;
import com.blackbaud.integration.services.LuminatOnline;
import com.blackbaud.integration.types.BlackbaudRecordIds;
import com.blackbaud.integration.types.Credential;
import com.blackbaud.integration.types.EmailCampaign;
import com.blackbaud.integration.types.EmailMessage;
import com.blackbaud.integration.types.EmailMessageDelivery;
import com.blackbaud.integration.types.UnboundedDateRange;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import lombok.Delegate;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TTransportException;

@Slf4j
public class LuminateServer {
		
		public static void main(String args[]) throws TTransportException {
				
				final int port = 9090;
				final TNonblockingServerTransport socket = new TNonblockingServerSocket(port);
				
				final TThreadedSelectorServer.Args serverConfig = new TThreadedSelectorServer.Args(socket)
								.transportFactory(new TFramedTransport.Factory())
								.protocolFactory(new TBinaryProtocol.Factory())
								.processor(new LuminatOnline.Processor(new LuminateOnlineImpl()))
								.selectorThreads(Runtime.getRuntime().availableProcessors())
								.workerThreads(Runtime.getRuntime().availableProcessors() * 2);
				
				final TServer server = new TThreadedSelectorServer(serverConfig);
				
				log.info("Offering Luminate Online services on port {}.", port);
				server.serve();
		}
		
		private static final class LuminateOnlineImpl implements LuminatOnline.Iface {
				
				@Delegate
				private final LuminateConstituentService consSvc = new LuminateConstituentService();
				
				public String echo(Credential credential, String toBeEchoed) throws FailedAuthenticationException, TException {
						// TODO Check Authentication
						
						return toBeEchoed;
				}
		}
		
		private static final class LuminateConstituentService {
				
				public List<EmailMessageDelivery> getConstituentEmailCommunicationHistory(Credential credential, BlackbaudRecordIds constituentIds, UnboundedDateRange dateRange) throws FailedAuthenticationException, NoSuchRecordException, TException {
						// TODO Check Authentication
						
						final EmailCampaign fakeCampaign = new EmailCampaign(
										new BlackbaudRecordIds().setLuminateId(123456), "Fake Campaign Name", "Email Appeal");
						
						final EmailMessage fakeMessage = new EmailMessage(
										new BlackbaudRecordIds().setLuminateId(6791011), "Fake Message Name", 2 /* version */, null /* url */, fakeCampaign);
						
						final long nowMillis = new Date().getTime();
						final EmailMessageDelivery fakeDelivery = new EmailMessageDelivery(
										constituentIds, fakeMessage, nowMillis - 4000, nowMillis - 200);
						
						return Arrays.asList(fakeDelivery);
				}
		}
}
