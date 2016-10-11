package com.aim.duty.duty_market.module.market.action;

import org.apache.mina.core.session.IoSession;

import com.aim.duty.duty_base.entity.protobuf.protocal.market.Market.CS_SaleCommodity;
import com.aim.duty.duty_market.module.market.service.MarketService;
import com.aim.game_base.navigation.ActionSupport;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

public class MarketSaleCommodityAction implements ActionSupport {

	private MarketService marketService;

	public void setMarketService(MarketService marketService) {
		this.marketService = marketService;
	}

	@Override
	public void execute(ByteString data, IoSession session) {
		// TODO Auto-generated method stub
		try {
			CS_SaleCommodity po = CS_SaleCommodity.parseFrom(data);
			int price = po.getSinglePrice();
			byte propType = (byte) po.getPropType();
			ByteString prop = po.getAbstractProp();
			marketService.saleCommodity(price, propType,prop);
		} catch (InvalidProtocolBufferException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
