package com.aim.duty.duty_market.module.market.service;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.aim.duty.duty_base.cache.ConstantCache;
import com.aim.duty.duty_base.entity.base.AbstractProp;
import com.aim.duty.duty_base.entity.protobuf.protocal.market.Market.SC_SaleCommodity;
import com.aim.duty.duty_market.ui.MainFrame;
import com.aim.duty.duty_market.ui.UIController;
import com.aim.game_base.entity.net.base.Protocal.Response;
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
	public Response.Builder saleCommodity(int price, byte propType, ByteString prop) {
		// TODO Auto-generated method stub
		Response.Builder builder = marketService.saleCommodity(price, propType, prop);
		try {
			SC_SaleCommodity data = SC_SaleCommodity.parseFrom(builder.getData());
			uiController.noticeAdd(data.getCommodityId());
		} catch (InvalidProtocolBufferException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return builder;

	}

	@Override
	public void buyCommodity(int commodityId, int num) {
		// TODO Auto-generated method stub

	}

}
