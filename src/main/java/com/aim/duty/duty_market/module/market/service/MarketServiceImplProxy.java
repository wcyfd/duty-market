package com.aim.duty.duty_market.module.market.service;

import java.awt.EventQueue;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.aim.duty.duty_base.entity.base.AbstractProp;
import com.aim.duty.duty_base.entity.base.GameObject;
import com.aim.duty.duty_base.entity.bo.Brick;
import com.aim.duty.duty_base.entity.bo.Commodity;
import com.aim.duty.duty_market.cache.MarketCache;
import com.aim.duty.duty_market.ui.MainFrame;
import com.aim.game_base.entity.net.base.Protocal.Response;
import com.google.protobuf.ByteString;

public class MarketServiceImplProxy implements MarketService {

	private MarketService marketService;

	public void setMarketService(MarketService marketService) {
		this.marketService = marketService;
	}

	private MainFrame mainFrame;

	public void setMainFrame(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	@Override
	public void serviceInit() {
		// TODO Auto-generated method stub
		marketService.serviceInit();
		MainFrame.start(mainFrame);

		// EventQueue.invokeLater(new Runnable() {
		// @Override
		// public void run() {
		//
		// JTable table = mainFrame.getTable();
		// DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		// tableModel.getDataVector().clear();
		//
		// for (Commodity commodity : MarketCache.commodityMap.values())
		// addCommodity(table, commodity);
		//
		// }
		// });

	}

	private void addCommodity(JTable table, byte propType, AbstractProp prop, int num, int price) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();

		model.addRow(new Object[] { prop.getId(), propType, prop, num, price });
	}

	@Override
	public void showCommodities() {
		// TODO Auto-generated method stub
		marketService.showCommodities();
	}

	@Override
	public void showCommoditiesByDuty(byte duty) {
		// TODO Auto-generated method stub
		marketService.showCommoditiesByDuty(duty);
	}

	@Override
	public Response.Builder saleCommodity(int price, byte propType, ByteString prop) {
		// TODO Auto-generated method stub
		Response.Builder builder = marketService.saleCommodity(price, propType, prop);

		try {
			AbstractProp b = (AbstractProp) MarketCache.salePropClassMap.get(propType).newInstance();
			b.deserialize(prop);
			addCommodity(mainFrame.getTable(), b.getPropType(), b, b.getNum(), price);
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return builder;

	}

	@Override
	public void buyCommodity(int commodityId, int num) {
		// TODO Auto-generated method stub

	}

}
