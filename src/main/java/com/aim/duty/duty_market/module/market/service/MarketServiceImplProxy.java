package com.aim.duty.duty_market.module.market.service;

import java.awt.EventQueue;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.aim.duty.duty_base.entity.base.AbstractProp;
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

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {

				JTable table = mainFrame.getTable();
				DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
				tableModel.getDataVector().clear();

				for (Commodity commodity : MarketCache.commodityMap.values())
					addCommodity(table, commodity);

			}
		});

	}

	private void addCommodity(JTable table, Commodity commodity) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		int count = model.getRowCount();
		System.out.println("count:" + count);
		AbstractProp prop = commodity.getSaleProp();

		model.addRow(new Object[] { prop.getId(), commodity, commodity.getSaleNum(), commodity.getSinglePrice() });
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
	public Response.Builder saleCommodity(int price,byte propType, ByteString prop) {
		// TODO Auto-generated method stub
		return marketService.saleCommodity(price,propType, prop);
	
	}

	@Override
	public void buyCommodity(int commodityId, int num) {
		// TODO Auto-generated method stub

	}

}
