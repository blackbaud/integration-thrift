package com.blackbaud.integration.demo.luminate;

import com.blackbaud.integration.generated.services.LuminatOnline;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServlet;

@Singleton
//@WebServlet(urlPatterns="/luminate", loadOnStartup=1)
public final class LuminateEndpoint extends TServlet {
		
		@Inject
		public LuminateEndpoint(LuminatOnline.Iface luminateSvc) {
				super(new LuminatOnline.Processor(luminateSvc), new TBinaryProtocol.Factory());
		}
}
