package com.blackbaud.integration.demo.luminate;

import com.blackbaud.integration.generated.services.LuminatOnline;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServlet;

@Singleton
public final class LuminateBinaryEndpoint extends TServlet {
		
		@Inject
		public LuminateBinaryEndpoint(LuminatOnline.Iface luminateSvc) {
				super(new LuminatOnline.Processor(luminateSvc), new TBinaryProtocol.Factory());
		}
}
