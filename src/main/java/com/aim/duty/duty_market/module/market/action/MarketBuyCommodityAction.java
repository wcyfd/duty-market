package com.aim.duty.duty_market.module.market.action;

import org.apache.mina.core.session.IoSession;

import com.aim.duty.duty_base.entity.protobuf.protocal.market.Market.CS_BuyCommodity;
import com.aim.duty.duty_market.module.market.service.MarketService;
import com.aim.game_base.entity.net.base.Protocal.SC.Builder;
import com.aim.game_base.navigation.ActionSupport;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

public class MarketBuyCommodityAction implements ActionSupport {

	private MarketService marketService;

	public void setMarketService(MarketService marketService) {
		this.marketService = marketService;
	}

	@Override
	public void execute(ByteString data, IoSession session) {
		try {
			CS_BuyCommodity cs = CS_BuyCommodity.parseFrom(data);
			int commodityId = cs.getCommodityId();
			int num = cs.getNum();
			Builder builder = marketService.buyCommodity(commodityId, num);
			if (builder != null) {
				session.write(builder);
			}
		} catch (InvalidProtocolBufferException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
