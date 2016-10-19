package com.aim.duty.duty_market.navigation;

import java.util.HashMap;
import java.util.Map;

import com.aim.duty.duty_market_entity.navigation.MarketProtocalId;
import com.aim.duty.duty_market_entity.protobuf.protocal.market.MarketProtocal.SC_BuyCommodity;
import com.aim.duty.duty_market_entity.protobuf.protocal.market.MarketProtocal.SC_SaleCommodity;

public class ProtocalNavigation {
	public static Map<Integer, Class<?>> map = new HashMap<>();

	public static void init() {
		map.put(MarketProtocalId.BUY_COMMODITY, SC_BuyCommodity.class);
		map.put(MarketProtocalId.SALE_COMMODITY, SC_SaleCommodity.class);		
	}
	
	public static Class<?> getClassByProtocalId(int protocalId){
		return map.get(protocalId);
	}
}
