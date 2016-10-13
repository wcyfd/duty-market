package com.aim.duty.duty_market.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import com.aim.duty.duty_base.common.ErrorCode;
import com.aim.duty.duty_base.entity.bo.Commodity;
import com.aim.duty.duty_market.cache.MarketCache;
import com.aim.duty.duty_market.module.market.service.MarketService;
import com.aim.game_base.entity.net.base.Protocal.SC.Builder;
import com.google.protobuf.ByteString;

public class UIController extends Observable {
	private MainFrame mainFrame;

	public void setMainFrame(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	public void start() {
		MainFrame.start(mainFrame);
		this.addObserver(mainFrame);
	}

	public void noticeAdd(int commodityId) {
		setChanged();
		Commodity commodity = MarketCache.commodityMap.get(commodityId);
		List list = new ArrayList();
		list.add("sale");
		list.add(commodity);
		this.notifyObservers(list);
	}

	public void noticeBuy(int commodityId, int num, int success) {
		if (success != ErrorCode.SUCCESS) {
			return;
		}

		this.setChanged();
		List list = new ArrayList();
		list.add("buy");
		list.add(commodityId);
		list.add(num);
		this.notifyObservers(list);
	}

}
