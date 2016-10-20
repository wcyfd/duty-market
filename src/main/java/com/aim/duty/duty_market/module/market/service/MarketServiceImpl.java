package com.aim.duty.duty_market.module.market.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.aim.duty.duty_market.cache.MarketCache;
import com.aim.duty.duty_market_entity.bo.Commodity;
import com.aim.duty.duty_market_entity.common.MarketErrorCode;
import com.aim.duty.duty_market_entity.navigation.MarketProtocalId;
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
	public SC saleCommodity(int roleId, int price, byte propType, int num, String name, ByteString byteString) {
		SC.Builder sc = SC.newBuilder();
		SC_SaleCommodity.Builder scSaleCommodity = SC_SaleCommodity.newBuilder();

		Commodity commodity = new Commodity();
		int commodityId = MarketCache.getCommodityId();
		commodity.setId(commodityId);
		commodity.setSinglePrice(price);
		commodity.setSalePropType(propType);
		commodity.setSaleNum(num);
		commodity.setSaleName(name);
		commodity.setSalePropData(byteString);
		commodity.setRoleId(roleId);

		this.addCommodity(commodity);

		return sc
				.setProtocal(MarketProtocalId.SALE_COMMODITY)
				.setData(
						scSaleCommodity.setSuccess(MarketErrorCode.SUCCESS).setCommodityId(commodityId).setRoleId(roleId)
								.build().toByteString()).build();

	}

	@Override
	public SC buyCommodity(int roleId, int commodityId, int num) {
		SC_BuyCommodity.Builder scBuyCommodityBuilder = SC_BuyCommodity.newBuilder();

		Commodity commodity = MarketCache.commodityMap.get(commodityId);
		if (commodity == null) {
			return SC.newBuilder().setProtocal(MarketProtocalId.BUY_COMMODITY)
					.setData(scBuyCommodityBuilder.setSuccess(MarketErrorCode.MARKET_NO_COMMODITY).build().toByteString())
					.build();
		}

		int sourceNum = commodity.getSaleNum();
		int remainCount = sourceNum - num;

		if (remainCount < 0) {
			return SC
					.newBuilder()
					.setProtocal(MarketProtocalId.BUY_COMMODITY)
					.setData(
							scBuyCommodityBuilder.setSuccess(MarketErrorCode.MARKET_COMMODITY_NOT_ENOUGH).setRoleId(roleId).build()
									.toByteString()).build();
		}

		commodity.setSaleNum(remainCount >= 0 ? remainCount : 0);

		if (commodity.getSaleNum() <= 0) {
			MarketCache.propTypeCommodityMap.get(commodity.getSalePropType()).remove(commodity.getId());
			MarketCache.commodityMap.remove(commodity.getId());
		}
		return SC
				.newBuilder()
				.setProtocal(MarketProtocalId.BUY_COMMODITY)
				.setData(
						scBuyCommodityBuilder.setSuccess(MarketErrorCode.SUCCESS)
				.setAbstractProp(commodity.getSalePropData()).setRoleId(roleId).setPropType(commodity.getSalePropType()).setNum(num).setSinglePrice(commodity.getSinglePrice()).build().toByteString()).build();
	}

}
