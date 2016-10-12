package com.aim.duty.duty_market;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.filter.codec.ProtocolCodecFilter;

import com.aim.duty.duty_base.entity.base.Constant;
import com.aim.duty.duty_base.entity.bo.Brick;
import com.aim.duty.duty_base.entity.bo.Magic;
import com.aim.duty.duty_base.util.Util;
import com.aim.duty.duty_market.module.market.service.MarketService;
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

		MarketService marketService = SpringContext.getBean("marketService");
		marketService.serviceInit();

		Util.threadSleep(500);

		Brick brick = new Brick();

		Magic magic = new Magic();
		magic.setDuration(29);
		magic.setMagicId(2001);
		magic.setValue(5302);
		brick.addMagic(magic);

		Magic magic2 = new Magic();
		magic2.setDuration(33);
		magic2.setMagicId(4001);
		magic2.setValue(6543);
		brick.addMagic(magic2);
		
		brick.setMineId(444);
		brick.setNum(5);

		marketService.saleCommodity(12, Constant.BRICK, brick.serialize());

		WanServer.startServer(new ClientHandler(), new InetSocketAddress(10001), WanServerType.TCP);
	}
	
}
