package com.reactiveworks.stocktrade.dao;

import com.reactiveworks.stocktrade.dao.impl.StockTradeDaoCsvImpl;
import com.reactiveworks.stocktrade.dao.impl.StockTradeDaoMysqlImpl;

/**
 * Factory for StockTradeDao.
 */
public class StockTradeFactory {

	private StockTradeDaoCsvImpl stockTrdDaoCsvImpl;
	private StockTradeDaoMysqlImpl stockTrdDaoMysqlImpl;
	private String implType;
	private static final String CSV = "csv";
	private static final String MYSQL = "mysql";

	public String getImplType() {
		return implType;
	}

	public void setImplType(String implType) {
		this.implType = implType;
	}

	public StockTradeDaoCsvImpl getStockTrdDaoCsvImpl() {
		return stockTrdDaoCsvImpl;
	}

	public void setStockTrdDaoCsvImpl(StockTradeDaoCsvImpl stockTrdDaoCsvImpl) {
		this.stockTrdDaoCsvImpl = stockTrdDaoCsvImpl;
	}

	public StockTradeDaoMysqlImpl getStockTrdDaoMysqlImpl() {
		return stockTrdDaoMysqlImpl;
	}

	public void setStockTrdDaoMysqlImpl(StockTradeDaoMysqlImpl stockTrdDaoMysqlImpl) {
		this.stockTrdDaoMysqlImpl = stockTrdDaoMysqlImpl;
	}

	/**
	 * Returns the StockTradeDao implementation object .
	 * 
	 * @return IStockTradeDao the StockTradeDao implementation object.
	 */
	public IStockTradeDao getInstance() {

		if (implType.equalsIgnoreCase(CSV)) {
			return stockTrdDaoCsvImpl;
		} else if (implType.equalsIgnoreCase(MYSQL)) {
			return stockTrdDaoMysqlImpl;
		} else {
			return stockTrdDaoCsvImpl;
		}

	}

}