package com.blackbaud.integration.demo.luminate;

import com.blackbaud.integration.demo.luminate.LuminateModule;
import com.blackbaud.integration.generated.services.LuminatOnline;
import com.google.inject.Guice;
import com.google.inject.Injector;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TTransportException;

@Slf4j
public final class Server {
		
		public static void main(String args[]) throws TTransportException {
				
				final Injector inj = Guice.createInjector(new LuminateModule());
				
				final int port = 9090;
				final TNonblockingServerTransport socket = new TNonblockingServerSocket(port);
				
				final TThreadedSelectorServer.Args serverConfig = new TThreadedSelectorServer.Args(socket)
								.transportFactory(new TFramedTransport.Factory())
								.protocolFactory(new TBinaryProtocol.Factory())
								.processor(new LuminatOnline.Processor(inj.getInstance(LuminatOnline.Iface.class)))
								.selectorThreads(Runtime.getRuntime().availableProcessors())
								.workerThreads(Runtime.getRuntime().availableProcessors() * 2);
				
				final TServer server = new TThreadedSelectorServer(serverConfig);
				
				log.info("Offering Luminate Online services on port {}.", port);
				server.serve();
		}
}
