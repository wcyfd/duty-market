package com.aim.duty.duty_market.service;

import com.aim.duty.duty_base.entity.base.AbstractProp;

public interface MarketService {

	public void showCommodities();

	public void showCommoditiesByDuty(byte duty);

	public void saleCommodity(int price, AbstractProp prop);

	public void buyCommodity(int commodityId,int num);
}
