package com.aim.duty.duty_market.module.market.action;

import org.apache.mina.core.session.IoSession;

import com.aim.duty.duty_market.module.market.service.MarketService;
import com.aim.duty.duty_market_entity.protobuf.protocal.market.MarketProtocal.CS_SaleCommodity;
import com.aim.game_base.entity.net.base.Protocal.SC.Builder;
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
			int num = po.getNum();
			byte propType = (byte) po.getPropType();
			String name = po.getName();
			ByteString prop = po.getAbstractProp();
			Builder builder = marketService.saleCommodity(price, propType, num, name, prop);
			if (builder != null)
				session.write(builder);
		} catch (InvalidProtocolBufferException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
