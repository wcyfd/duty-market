package com.aim.duty.duty_market.cache;

import java.util.LinkedHashMap;
import java.util.Map;

import com.aim.duty.duty_base.entity.base.Constant;
import com.aim.duty.duty_base.entity.bo.Commodity;

public class MarketCache {
	public static Map<Byte, Map<Integer, Commodity>> propTypeCommodityMap = new LinkedHashMap<>();
	public static Map<Integer, Commodity> commodityMap = new LinkedHashMap<>();

	public static void init() {
		propTypeCommodityMap.put(Constant.PROP, new LinkedHashMap<Integer, Commodity>());
		propTypeCommodityMap.put(Constant.WALL, new LinkedHashMap<Integer, Commodity>());
		propTypeCommodityMap.put(Constant.BRICK, new LinkedHashMap<Integer, Commodity>());
		propTypeCommodityMap.put(Constant.CEMENT, new LinkedHashMap<Integer, Commodity>());
		propTypeCommodityMap.put(Constant.EQUIP, new LinkedHashMap<Integer, Commodity>());
		propTypeCommodityMap.put(Constant.FORGE, new LinkedHashMap<Integer, Commodity>());
	}
}