package com.aim.duty.duty_market.navigation;

import java.util.HashMap;
import java.util.Map;

import com.aim.duty.duty_market.module.market.action.MarketBuyCommodityAction;
import com.aim.duty.duty_market.module.market.action.MarketSaleCommodityAction;
import com.aim.duty.duty_market_entity.navigation.MarketProtocalId;
import com.aim.game_base.navigation.ActionSupport;

public class ActionNavigation {
	private static MarketSaleCommodityAction marketSaleCommodityAction;
	private static MarketBuyCommodityAction marketBuyCommodityAction;

	public static Map<Integer, ActionSupport> navigation = new HashMap<>();

	public static ActionSupport getAction(int protocal) {
		return navigation.get(protocal);
	}

	public static void init() {
		navigation.put(MarketProtocalId.SALE_COMMODITY, marketSaleCommodityAction);
		navigation.put(MarketProtocalId.BUY_COMMODITY, marketBuyCommodityAction);
	}

	public static void setMarketSaleCommodityAction(MarketSaleCommodityAction marketSaleCommodityAction) {
		ActionNavigation.marketSaleCommodityAction = marketSaleCommodityAction;
	}

	public static void setMarketBuyCommodityAction(MarketBuyCommodityAction marketBuyCommodityAction) {
		ActionNavigation.marketBuyCommodityAction = marketBuyCommodityAction;
	}
}
