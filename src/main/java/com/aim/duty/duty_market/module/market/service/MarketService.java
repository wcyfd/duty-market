package com.aim.duty.duty_market.module.market.service;

import com.aim.game_base.entity.net.base.Protocal.SC;
import com.aim.game_base.entity.net.base.Protocal.SC.Builder;
import com.google.protobuf.ByteString;

public interface MarketService {

	public void serviceInit();
	
	public void showCommodities();

	public void showCommoditiesByDuty(byte duty);

	public SC saleCommodity(int price,byte propType, int num,String name,ByteString byteString);

	public SC buyCommodity(int commodityId,int num);
}
