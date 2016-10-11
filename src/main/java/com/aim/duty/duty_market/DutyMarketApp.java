package com.aim.duty.duty_market;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.filter.codec.ProtocolCodecFilter;

import com.aim.duty.duty_base.entity.base.Constant;
import com.aim.duty.duty_base.entity.bo.Brick;
import com.aim.duty.duty_market.cache.MarketCache;
import com.aim.duty.duty_market.module.market.service.MarketService;
import com.aim.duty.duty_market.module.market.service.MarketServiceImpl;
import com.aim.duty.duty_market.module.market.service.MarketServiceImplProxy;
import com.aim.duty.duty_market.net.ClientHandler;
import com.aim.duty.duty_market.ui.MainFrame;
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

		
		
		MarketService marketService = SpringContext.getBean("marketService");
		marketService.serviceInit();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Brick b = new Brick();
		b.setMineId(0);
		b.setId(1);
		b.setNum(20);
		
		marketService.saleCommodity(12,Constant.BRICK,null);

		startWanServer(new ClientHandler(),  new InetSocketAddress(10001));
	}

	private static void startWanServer(IoHandlerAdapter handler, InetSocketAddress inetSocketAddress) {
		WanServer.startServer(new ProtocolCodecFilter(new MessageCodecFactory(Charset.forName("UTF-8"))), handler,
				inetSocketAddress, WanServerType.TCP);

	}
}
