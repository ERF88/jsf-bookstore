package com.github.erf88.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import com.github.erf88.dao.DAO;
import com.github.erf88.model.Book;
import com.github.erf88.model.Sale;

@ManagedBean
@ViewScoped
public class SaleBean {

	public List<Sale> getSales(long seed) {
		List<Sale> sales = new ArrayList<Sale>();
		List<Book> books = new DAO<Book>(Book.class).findAll();
		Random random = new Random(seed);
		
		for (Book book : books) {
			Integer quantity = random.nextInt(500);
			sales.add(new Sale(book, quantity));
		}

		return sales;
	}

	public BarChartModel getSalesModel() {
		BarChartModel model = new BarChartModel();
		model.setAnimate(true);
		
		ChartSeries saleSeries2024 = new ChartSeries();
		saleSeries2024.setLabel("Vendas 2024");
		List<Sale> sales = getSales(1234);
		
		for (Sale sale : sales) {
			saleSeries2024.set(sale.getBook().getTitle(), sale.getQuantity());
		}
		model.addSeries(saleSeries2024);
		
		ChartSeries saleSeries2023 = new ChartSeries();
		saleSeries2023.setLabel("Vendas 2023");
		sales = getSales(4321);
		
		for (Sale sale : sales) {
			saleSeries2023.set(sale.getBook().getTitle(), sale.getQuantity());
		}
		model.addSeries(saleSeries2023);
		
		return model;
	}

}
