package com.aim.duty.duty_market.module.market.service;

import com.aim.duty.duty_base.entity.base.AbstractProp;
import com.aim.duty.duty_base.entity.base.Constant;
import com.aim.duty.duty_base.entity.bo.Commodity;
import com.aim.duty.duty_market.cache.MarketCache;
import com.aim.duty.duty_market.cache.MarketConstantCache;
import com.aim.game_base.entity.net.base.Protocal.Response;
import com.google.protobuf.ByteString;

public class MarketServiceImpl implements MarketService {

	@Override
	public void serviceInit() {
		// TODO Auto-generated method stub
		MarketCache.init();
	}

	@Override
	public void showCommodities() {
		// TODO Auto-generated method stub

	}

	@Override
	public void showCommoditiesByDuty(byte duty) {
		// TODO Auto-generated method stub

	}

	@Override
	public Response.Builder saleCommodity(int price,byte propType, ByteString byteString) {
		AbstractProp prop = null;
		
		Commodity commodity = new Commodity();
		commodity.setSinglePrice(price);
		commodity.setProp(prop);

		MarketCache.propTypeCommodityMap.get(propType).put(commodity.getId(), commodity);
		MarketCache.commodityMap.put(commodity.getId(), commodity);
		return null;
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
