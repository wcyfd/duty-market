package com.aim.duty.duty_market.module.market.service;

import java.util.ArrayList;
import java.util.List;

import com.aim.duty.duty_base.cache.ConstantCache;
import com.aim.duty.duty_base.common.ErrorCode;
import com.aim.duty.duty_base.entity.base.AbstractProp;
import com.aim.duty.duty_base.entity.bo.Commodity;
import com.aim.duty.duty_base.entity.protobuf.protocal.market.Market.SC_BuyCommodity;
import com.aim.duty.duty_base.entity.protobuf.protocal.market.Market.SC_SaleCommodity;
import com.aim.duty.duty_base.service.prop.PropService;
import com.aim.duty.duty_market.cache.MarketCache;
import com.aim.game_base.entity.net.base.Protocal.SC;
import com.google.protobuf.ByteString;

public class MarketServiceImpl implements MarketService {

	private PropService propService;

	public void setPropService(PropService propService) {
		this.propService = propService;
	}

	@Override
	public void serviceInit() {
		// TODO Auto-generated method stub
		MarketCache.init();

		List<Commodity> allCommodity = new ArrayList<>();
		this.commodityDeserialize(allCommodity);
	}

	private void commodityDeserialize(List<Commodity> allCommodity) {
		for (Commodity commodity : allCommodity) {
			ByteString salePropData = commodity.getSalePropData();
			byte propType = commodity.getSalePropType();
			try {
				AbstractProp saleProp = (AbstractProp) ConstantCache.salePropClassMap.get(propType).newInstance();
				saleProp.deserialize(salePropData);
				commodity.setSaleProp(saleProp);
			} catch (InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
	public SC.Builder saleCommodity(int price, byte propType, ByteString byteString) {
		SC.Builder response = SC.newBuilder();
		SC_SaleCommodity.Builder scSaleCommodity = SC_SaleCommodity.newBuilder();
		AbstractProp saleProp = null;
		try {
			saleProp = (AbstractProp) ConstantCache.salePropClassMap.get(propType).newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (saleProp == null) {
			response.setData(scSaleCommodity.setSuccess(0).build().toByteString());
			return response;
		}
		saleProp.deserialize(byteString);

		Commodity commodity = new Commodity();
		int commodityId = MarketCache.getCommodityId();
		commodity.setId(commodityId);
		commodity.setSinglePrice(price);
		commodity.setSaleProp(saleProp);
		commodity.setSalePropType(propType);
		commodity.setSaleNum(saleProp.getNum());

		MarketCache.propTypeCommodityMap.get(propType).put(commodity.getId(), commodity);
		MarketCache.commodityMap.put(commodity.getId(), commodity);

		return response.setData(scSaleCommodity.setSuccess(1).setCommodityId(commodityId).build().toByteString());

	}

	@Override
	public SC.Builder buyCommodity(int commodityId, int num) {
		SC.Builder builder = SC.newBuilder();
		SC_BuyCommodity.Builder sc = SC_BuyCommodity.newBuilder();

		Commodity commodity = MarketCache.commodityMap.get(commodityId);
		if (commodity == null) {
			sc.setSuccess(ErrorCode.MARKET_NO_COMMODITY);
			return builder.setData(sc.build().toByteString());
		}
		AbstractProp prop = commodity.getSaleProp();
		AbstractProp targetProp = propService.extract(prop, num);

		if (targetProp.getNum() != num) {
			sc.setSuccess(ErrorCode.MARKET_COMMODITY_NOT_ENOUGH);
			return builder.setData(sc.build().toByteString());
		}

		sc.setAbstractProp(targetProp.serialize());

		if (prop.getNum() <= 0) {
			MarketCache.propTypeCommodityMap.get(prop.getPropType()).remove(commodity.getId());
			MarketCache.commodityMap.remove(commodity.getId());
		}
		return builder.setData(sc.setSuccess(ErrorCode.SUCCESS).build().toByteString());
	}

}
