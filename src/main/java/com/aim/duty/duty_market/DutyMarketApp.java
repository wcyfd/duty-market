package com.aim.duty.duty_market;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.filter.codec.ProtocolCodecFilter;

import com.aim.duty.duty_market.cache.MarketCache;
import com.aim.duty.duty_market.module.market.service.MarketService;
import com.aim.duty.duty_market.module.market.service.MarketServiceImpl;
import com.aim.duty.duty_market.module.market.service.MarketServiceImplProxy;
import com.aim.duty.duty_market.net.ClientHandler;
import com.aim.game_base.net.IoHandlerAdapter;
import com.aim.game_base.net.MessageCodecFactory;
import com.aim.game_base.net.SpringContext;
import com.aim.game_base.net.WanServer;
import com.aim.game_base.net.WanServer.WanServerType;

/**
 * Hello world!
 *
 */
public class DutyMarketApp {	
	private static final String START_CONFIG_FILE = "ApplicationContext.xml";
	public static void main(String[] args) {		
		SpringContext.initSpringCtx(START_CONFIG_FILE);

		startWanServer(new ClientHandler(),  new InetSocketAddress(10001));
		
		MarketCache.init();
	}

	private static void startWanServer(IoHandlerAdapter handler, InetSocketAddress inetSocketAddress) {
		WanServer.startServer(new ProtocolCodecFilter(new MessageCodecFactory(Charset.forName("UTF-8"))), handler,
				inetSocketAddress, WanServerType.TCP);

	}
}
