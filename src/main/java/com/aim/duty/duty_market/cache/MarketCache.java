package com.aim.duty.duty_market.cache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.aim.duty.duty_base.entity.bo.Commodity;
import com.aim.duty.duty_base.service.prop.PropConstant;

public class MarketCache {
	private static int id = 0;
	private static Lock lock = new ReentrantLock();

	public static int getCommodityId() {
		lock.lock();
		id++;
		lock.unlock();
		return id;
	}

	public static Map<Byte, Map<Integer, Commodity>> propTypeCommodityMap = new LinkedHashMap<>();
	public static Map<Integer, Commodity> commodityMap = new LinkedHashMap<>();

	public static void init() {
		propTypeCommodityMap.put(PropConstant.PROP, new LinkedHashMap<Integer, Commodity>());
		propTypeCommodityMap.put(PropConstant.WALL, new LinkedHashMap<Integer, Commodity>());
		propTypeCommodityMap.put(PropConstant.BRICK, new LinkedHashMap<Integer, Commodity>());
		propTypeCommodityMap.put(PropConstant.CEMENT, new LinkedHashMap<Integer, Commodity>());
		propTypeCommodityMap.put(PropConstant.EQUIP, new LinkedHashMap<Integer, Commodity>());
		propTypeCommodityMap.put(PropConstant.FORGE, new LinkedHashMap<Integer, Commodity>());
	}
}
