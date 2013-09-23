package com.blackbaud.integration.demo.luminate;

import com.blackbaud.integration.generated.services.LuminatOnline;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.server.TServlet;

@Singleton
public class LuminateJsonEndpoint extends TServlet {
		
		@Inject
		public LuminateJsonEndpoint(LuminatOnline.Iface luminateSvc) {
				super(new LuminatOnline.Processor(luminateSvc), new TJSONProtocol.Factory());
		}
}
