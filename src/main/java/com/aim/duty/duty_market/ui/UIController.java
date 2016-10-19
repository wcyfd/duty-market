package com.aim.duty.duty_market.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import com.aim.duty.duty_market_entity.common.MarketErrorCode;

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
		List list = new ArrayList();
		list.add("sale");
		list.add(commodityId);
		this.notifyObservers(list);
	}

	public void noticeBuy(int commodityId, int num, int success) {
		if (success != MarketErrorCode.SUCCESS) {
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
