package com.aim.duty.duty_market.ui;

import java.awt.EventQueue;

import javax.swing.table.DefaultTableModel;

import com.aim.duty.duty_base.cache.ConstantCache;
import com.aim.duty.duty_base.entity.base.AbstractProp;
import com.aim.duty.duty_base.entity.bo.Commodity;
import com.aim.duty.duty_market.cache.MarketCache;
import com.google.protobuf.ByteString;

public class UIController {
	private MainFrame mainFrame;

	public void setMainFrame(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	public void start() {
		MainFrame.start(mainFrame);
	}

	public void noticeAdd(int commodityId) {

		final Commodity commodity = MarketCache.commodityMap.get(commodityId);
		final byte propType = commodity.getSalePropType();
		final int price = commodity.getSinglePrice();
		ByteString propData = commodity.getSalePropData();

		try {
			final AbstractProp prop = (AbstractProp) ConstantCache.salePropClassMap.get(propType).newInstance();
			prop.deserialize(propData);
			EventQueue.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					DefaultTableModel model = (DefaultTableModel) mainFrame.getTable().getModel();

					model.addRow(new Object[] { commodity.getId(), propType, prop, prop.getNum(), price });

				}

			});
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
