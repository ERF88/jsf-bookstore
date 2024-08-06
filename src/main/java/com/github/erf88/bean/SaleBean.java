package com.github.erf88.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import com.github.erf88.dao.SaleDao;
import com.github.erf88.model.Sale;

@Named
@ViewScoped
public class SaleBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private SaleDao dao;
	
	public List<Sale> getSales() {
		return dao.findAll();
	}

	public BarChartModel getSalesModel() {
		BarChartModel model = new BarChartModel();
		model.setAnimate(true);
		
		ChartSeries chartSeries = new ChartSeries();
		chartSeries.setLabel("Vendas 2024");
		List<Sale> sales = getSales();
		
		for (Sale sale : sales) {
			chartSeries.set(sale.getBook().getTitle(), sale.getQuantity());
		}
		model.addSeries(chartSeries);
		
		return model;
	}

}
