package com.aim.duty.duty_market.module.market.service;

import com.aim.duty.duty_base.entity.base.AbstractProp;
import com.aim.duty.duty_base.entity.bo.Commodity;
import com.aim.game_base.entity.net.base.Protocal.Response;
import com.aim.game_base.entity.net.base.Protocal.Response.Builder;
import com.google.protobuf.ByteString;

public interface MarketService {

	public void serviceInit();
	
	public void showCommodities();

	public void showCommoditiesByDuty(byte duty);

	public Builder saleCommodity(int price,byte propType, ByteString byteString);

	public void buyCommodity(int commodityId,int num);
}
