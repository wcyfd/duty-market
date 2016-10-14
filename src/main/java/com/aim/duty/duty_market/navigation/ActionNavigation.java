package com.aim.duty.duty_market.navigation;

import java.util.HashMap;
import java.util.Map;

import com.aim.duty.duty_market.module.market.action.MarketSaleCommodityAction;
import com.aim.game_base.navigation.ActionSupport;

public class ActionNavigation {
	private static MarketSaleCommodityAction marketSaleCommodityAction;

	public static Map<Integer, ActionSupport> navigation = new HashMap<>();

	public static ActionSupport getAction(int protocal) {
		return navigation.get(protocal);
	}

	public static void init() {
		navigation.put(ProtocalId.BUY_COMMODITY, marketSaleCommodityAction);
	}

	public static void setMarketSaleCommodityAction(MarketSaleCommodityAction marketSaleCommodityAction) {
		ActionNavigation.marketSaleCommodityAction = marketSaleCommodityAction;
	}
}
