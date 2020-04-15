package com.reactiveworks.stocktrade;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.reactiveworks.stocktrade.dao.exceptions.StockTradeDaoException;

public class Test {

	public static void main(String[] args) throws StockTradeDaoException {
		ApplicationContext context=new ClassPathXmlApplicationContext("StockTradeConfiguration.xml");
		//IStockTradeDao stockTradeDao= (IStockTradeDao)context.getBean("stockTradeDao");
		((ClassPathXmlApplicationContext)(context)).close();
	}

}
