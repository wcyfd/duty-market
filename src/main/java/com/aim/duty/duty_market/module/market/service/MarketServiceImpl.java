package com.aim.duty.duty_market.module.market.service;

import java.util.ArrayList;
import java.util.List;

import com.aim.duty.duty_base.cache.ConstantCache;
import com.aim.duty.duty_base.entity.base.AbstractProp;
import com.aim.duty.duty_base.entity.bo.Commodity;
import com.aim.duty.duty_base.entity.protobuf.protocal.market.Market.SC_SaleCommodity;
import com.aim.duty.duty_market.cache.MarketCache;
import com.aim.game_base.entity.net.base.Protocal.Response;
import com.google.protobuf.ByteString;

public class MarketServiceImpl implements MarketService {

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
	public Response.Builder saleCommodity(int price, byte propType, ByteString byteString) {
		Response.Builder response = Response.newBuilder();
		SC_SaleCommodity.Builder scSaleCommodity = SC_SaleCommodity.newBuilder();	
		AbstractProp saleProp = null;
			try {
				saleProp = (AbstractProp) ConstantCache.salePropClassMap.get(propType).newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(saleProp == null){
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
	public void buyCommodity(int commodityId, int num) {
		Commodity commodity = MarketCache.commodityMap.get(commodityId);
		AbstractProp prop = commodity.getSaleProp();
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
