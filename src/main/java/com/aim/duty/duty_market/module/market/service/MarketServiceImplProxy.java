package com.aim.duty.duty_market.module.market.service;

import org.apache.mina.core.session.IoSession;

import com.aim.duty.duty_market.ui.UIController;
import com.aim.duty.duty_market_entity.protobuf.protocal.market.MarketProtocal.SC_BuyCommodity;
import com.aim.duty.duty_market_entity.protobuf.protocal.market.MarketProtocal.SC_SaleCommodity;
import com.aim.game_base.entity.net.base.Protocal.SC;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

public class MarketServiceImplProxy implements MarketService {

	private MarketService marketService;

	public void setMarketService(MarketService marketService) {
		this.marketService = marketService;
	}

	private UIController uiController;

	public void setUiController(UIController uiController) {
		this.uiController = uiController;
	}

	@Override
	public void serviceInit() {
		// TODO Auto-generated method stub
		marketService.serviceInit();
		uiController.start();
	}

	@Override
	public void showCommodities() {
		// TODO Auto-generated method stub
		marketService.showCommodities();
	}

	@Override
	public void showCommoditiesByDuty(byte duty) {
		// TODO Auto-generated method stub
		marketService.showCommoditiesByDuty(duty);
	}

	@Override
	public SC saleCommodity(int roleId, int price, byte propType, int num, String name,
			ByteString prop) {
		// TODO Auto-generated method stub
		SC sc = marketService.saleCommodity( roleId, price, propType, num, name, prop);
		try {
			SC_SaleCommodity data = SC_SaleCommodity.parseFrom(sc.getData());
			uiController.noticeAdd(data.getCommodityId());
		} catch (InvalidProtocolBufferException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return sc;

	}

	@Override
	public SC buyCommodity(int roleId, int commodityId, int num) {
		// TODO Auto-generated method stub
		SC sc = marketService.buyCommodity( roleId, commodityId, num);

		try {
			SC_BuyCommodity scBuyCommodity = SC_BuyCommodity.parseFrom(sc.getData());
			uiController.noticeBuy(commodityId, num, scBuyCommodity.getSuccess());
		} catch (InvalidProtocolBufferException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sc;
	}

}
