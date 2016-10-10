package com.aim.duty.duty_market.module.market.service;

import com.aim.duty.duty_base.entity.base.AbstractProp;

public class MarketServiceImplProxy implements MarketService {
	private MarketService marketService;

	public void setMarketService(MarketService marketService) {
		this.marketService = marketService;
	}

	@Override
	public void showCommodities() {
		// TODO Auto-generated method stub
		marketService.showCommodities();
	}

	@Override
	public void showCommoditiesByDuty(byte duty) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saleCommodity(int price, AbstractProp prop) {
		// TODO Auto-generated method stub

	}

	@Override
	public void buyCommodity(int commodityId, int num) {
		// TODO Auto-generated method stub

	}

}
