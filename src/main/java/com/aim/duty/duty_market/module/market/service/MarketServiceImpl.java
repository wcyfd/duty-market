package com.aim.duty.duty_market.module.market.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.aim.duty.duty_base.common.ErrorCode;
import com.aim.duty.duty_market.cache.MarketCache;
import com.aim.duty.duty_market_entity.Commodity;
import com.aim.duty.duty_market_entity.protobuf.protocal.market.MarketProtocal.SC_BuyCommodity;
import com.aim.duty.duty_market_entity.protobuf.protocal.market.MarketProtocal.SC_SaleCommodity;
import com.aim.game_base.entity.net.base.Protocal.SC;
import com.google.protobuf.ByteString;

public class MarketServiceImpl implements MarketService {

	@Override
	public void serviceInit() {
		// TODO Auto-generated method stub
		List<Commodity> allCommodity = new ArrayList<>();
		this.loadCommodities(allCommodity);
	}

	private void loadCommodities(List<Commodity> allCommodity) {
		for (Commodity c : allCommodity) {
			this.addCommodity(c);
		}
	}

	private void addCommodity(Commodity c) {
		MarketCache.commodityMap.put(c.getId(), c);
		Map<Integer, Commodity> map = MarketCache.propTypeCommodityMap.get(c.getSalePropType());
		if (map == null) {
			map = new LinkedHashMap<>();
			MarketCache.propTypeCommodityMap.put(c.getSalePropType(), map);
		}
		map.put(c.getId(), c);
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
	public SC.Builder saleCommodity(int price, byte propType, int num, String name, ByteString byteString) {
		SC.Builder response = SC.newBuilder();
		SC_SaleCommodity.Builder scSaleCommodity = SC_SaleCommodity.newBuilder();

		Commodity commodity = new Commodity();
		int commodityId = MarketCache.getCommodityId();
		commodity.setId(commodityId);
		commodity.setSinglePrice(price);
		commodity.setSalePropType(propType);
		commodity.setSaleNum(num);
		commodity.setSaleName(name);
		commodity.setSalePropData(byteString);

		this.addCommodity(commodity);

		return response.setData(
				scSaleCommodity.setSuccess(ErrorCode.SUCCESS).setCommodityId(commodityId).build().toByteString());

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

		int sourceNum = commodity.getSaleNum();
		int remainCount = sourceNum - num;

		commodity.setSaleNum(remainCount >= 0 ? remainCount : 0);

		if (commodity.getSaleNum() == sourceNum) {
			sc.setSuccess(ErrorCode.MARKET_COMMODITY_NOT_ENOUGH);
			return builder.setData(sc.build().toByteString());
		}

		sc.setAbstractProp(commodity.getSalePropData());

		if (commodity.getSaleNum() <= 0) {
			MarketCache.propTypeCommodityMap.get(commodity.getSalePropType()).remove(commodity.getId());
			MarketCache.commodityMap.remove(commodity.getId());
		}
		return builder.setData(sc.setSuccess(ErrorCode.SUCCESS).build().toByteString());
	}

}
