package com.aim.duty.duty_market.module.market.service;

import com.aim.game_base.entity.net.base.Protocal.PT;
import com.google.protobuf.ByteString;

public interface MarketService {

	public void serviceInit();

	public void showCommodities();

	public void showCommoditiesByDuty(byte duty);

	public PT saleCommodity(int roleId, int price, byte propType, int num, String name, ByteString byteString);

	public PT buyCommodity(int roleId, int commodityId, int num);
}
