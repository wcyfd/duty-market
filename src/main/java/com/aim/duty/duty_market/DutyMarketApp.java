package com.aim.duty.duty_market;

import java.net.InetSocketAddress;

import com.aim.duty.duty_base.cache.ConstantCache;
import com.aim.duty.duty_market.module.market.service.MarketService;
import com.aim.duty.duty_market.navigation.ActionNavigation;
import com.aim.duty.duty_market.net.ServerHandler;
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

//		ConstantCache.init();
		ActionNavigation.init();

		MarketService marketService = SpringContext.getBean("marketService");
		marketService.serviceInit();

		WanServer.startServer(new ServerHandler(), new InetSocketAddress(10001), WanServerType.TCP);
	}

}
