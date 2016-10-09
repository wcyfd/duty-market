package com.aim.duty.duty_market.service;

import com.aim.duty.duty_base.entity.base.AbstractProp;
import com.aim.duty.duty_base.entity.bo.Commodity;
import com.aim.duty.duty_market.cache.MarketCache;

public class MarketServiceImpl implements MarketService {

	@Override
	public void showCommodities() {
		// TODO Auto-generated method stub

	}

	@Override
	public void showCommoditiesByDuty(byte duty) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saleCommodity(int price, AbstractProp prop) {
		byte propType = prop.getPropType();
		Commodity commodity = new Commodity();
		commodity.setSinglePrice(price);
		commodity.setProp(prop);

		MarketCache.propTypeCommodityMap.get(propType).put(commodity.getId(), commodity);
		MarketCache.commodityMap.put(commodity.getId(), commodity);

	}

	@Override
	public void buyCommodity(int commodityId, int num) {
		Commodity commodity = MarketCache.commodityMap.get(commodityId);
		AbstractProp prop = commodity.getProp();
		if (prop.getNum() < num) {
			return;
		}

		prop.setNum(prop.getNum() - num);
		if (prop.getNum() <= 0) {
			MarketCache.propTypeCommodityMap.get(prop.getPropType()).remove(commodity.getId());
			MarketCache.commodityMap.remove(commodity.getId());
		}
	}

}
