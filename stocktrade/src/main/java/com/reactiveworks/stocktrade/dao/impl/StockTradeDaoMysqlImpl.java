package com.reactiveworks.stocktrade.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.reactiveworks.stocktrade.dao.IStockTradeDao;
import com.reactiveworks.stocktrade.dao.exceptions.StockTradeDaoException;
import com.reactiveworks.stocktrade.model.StockTrade;

/**
 * MySql implementation of IStockTradeDao.
 */
public class StockTradeDaoMysqlImpl extends JdbcDaoSupport implements IStockTradeDao {

	private static final String INSERT_QUERY = "INSERT INTO stocktrade(security,date,open,high,low,close,volume,adj_close) VALUES(?,?,?,?,?,?,?,?)";
	private static final String DELETE_QUERY = "DELETE FROM stocktrade WHERE stockTrade_id=?;";
	private static final String UPDATE_QUERY = "UPDATE stocktrade SET volume=? WHERE stockTrade_id=?;";
	private static final String SELECT_QUERY = "SELECT security,date,open,high,low,close,volume,adj_close FROM stocktrade;";
	private static final Logger LOGGER_OBJ = Logger.getLogger(StockTradeDaoMysqlImpl.class);

	/**
	 * creates the list of stockTrade objects.
	 * 
	 * @return the list of stockTrade objects.
	 * @throws StockTradeDaoException when dao operation fails.
	 * @throws DataBaseAccessException when unable to access the database.
	 */
	@Override
	public List<StockTrade> getStockTradeRecords() throws StockTradeDaoException {
		LOGGER_OBJ.debug("execution of getStockTradeRecords() method started.");
		List<StockTrade> stockTrdList = null;
		try {
			stockTrdList = getJdbcTemplate().query(SELECT_QUERY, new StockTradeMapper());

		} catch (DataAccessException dataAccessExp) {
			LOGGER_OBJ.debug("unable to access the database");
			throw new StockTradeDaoException("unable to get the stocktrade record from the database ", dataAccessExp);
		}
		LOGGER_OBJ.debug("getStockTradeRecords() method execution completed.");
		return stockTrdList;
	}

	/**
	 * Creates the stockTrade record in the database.
	 * 
	 * @param stockTradeObj the stockTrade object to be inserted into the database.
	 * @throws StockTradeDaoException when unable to access the database.
	 */
	@Override
	public void createStockTradeRecord(StockTrade stockTradeObj) throws StockTradeDaoException {
		LOGGER_OBJ.debug("execution of createStockTradeRecord() method started.");
		java.sql.Date date = new java.sql.Date(stockTradeObj.getDate().getTime());

		try {
			getJdbcTemplate().update(INSERT_QUERY,
					new Object[] { stockTradeObj.getSecurity(), date, stockTradeObj.getOpen(), stockTradeObj.getHigh(),
							stockTradeObj.getLow(), stockTradeObj.getClose(), stockTradeObj.getVolume(),
							stockTradeObj.getAdjClose() });
		} catch (DataAccessException dataAccessExp) {
			LOGGER_OBJ.debug("unable to access the database");
			throw new StockTradeDaoException("unable to create the stocktrade record ", dataAccessExp);

		}

		LOGGER_OBJ.debug("createStockTradeRecord() method execution completed.");
	}

	/**
	 * inserts multiple stockTrade records into the database.
	 * 
	 * @param stockTradesList the list of stockTrade records to be inserted into the
	 *                        database.
	 * @throws StockTradeDaoException when dao operation fails.
	 */
	@Override
	public void createStockTradeRecords(List<StockTrade> stockTradesList) throws StockTradeDaoException {
		LOGGER_OBJ.debug("execution of createStockTradeRecords() method started.");
		try {
			getJdbcTemplate().batchUpdate(INSERT_QUERY, new BatchPreparedStatementSetter() {

				@Override
				public void setValues(PreparedStatement preparedStatement, int index) {

					try {
						preparedStatement.setString(1, stockTradesList.get(index).getSecurity());
						java.sql.Date date = new java.sql.Date(stockTradesList.get(index).getDate().getTime());
						preparedStatement.setDate(2, date);
						preparedStatement.setDouble(3, stockTradesList.get(index).getOpen());
						preparedStatement.setDouble(4, stockTradesList.get(index).getHigh());
						preparedStatement.setDouble(5, stockTradesList.get(index).getLow());
						preparedStatement.setDouble(6, stockTradesList.get(index).getClose());
						preparedStatement.setDouble(7, stockTradesList.get(index).getVolume());
						preparedStatement.setDouble(8, stockTradesList.get(index).getAdjClose());
					} catch (SQLException sqlExp) {
						LOGGER_OBJ.error("unable to insert stockTrade object with id "+ (index+1) +" into the database");
					}

				}

				@Override
				public int getBatchSize() {

					return stockTradesList.size();
				}
			});
		} catch (DataAccessException dataAccessExp) {
			LOGGER_OBJ.debug("unable to access the database");
			throw new StockTradeDaoException("unable to create the stocktrade records in the database ", dataAccessExp);

		}
		LOGGER_OBJ.debug("createStockTradeRecords() method execution completed.");
	}

	/**
	 * deletes the stockTrade record from the database.
	 * 
	 * @param stockTradeId the id of the stockTrade record to be deleted from the
	 *                     database.
	 * @throws StockTradeDaoException when unable to access the database.
	 */
	@Override
	public void deleteStockTradeRecord(int stockTradeId) throws StockTradeDaoException {
		LOGGER_OBJ.debug("execution of deleteStockTradeRecord() method started.");
		try {
			getJdbcTemplate().update(DELETE_QUERY, new Object[] { stockTradeId });
		} catch (DataAccessException dataAccessExp) {
			LOGGER_OBJ.debug("unable to access the database");
			throw new StockTradeDaoException("unable to delete the stocktrade record with id= " + stockTradeId,
					dataAccessExp);
		}

		LOGGER_OBJ.debug("deleteStockTradeRecord() method execution completed.");
	}

	/**
	 * Updates the stockTrade record in the database.
	 * 
	 * @param stockTrdId the id of the stockTrade record to be updated .
	 * @param volume the new updated value of the stockTrade volume.
	 * @throws StockTradeDaoException when dao operation fails.
	 */
	@Override
	public void updateStockTradeRecord(int stockTrdId, double volume) throws StockTradeDaoException {
		LOGGER_OBJ.debug("execution of updateStockTradeRecord() method started.");
		try {
			getJdbcTemplate().update(UPDATE_QUERY, new Object[] { volume, stockTrdId });
		} catch (DataAccessException dataAccessExp) {
			LOGGER_OBJ.debug("unable to access the database");
			throw new StockTradeDaoException("unable to update the stocktrade record with id= " + stockTrdId,
					dataAccessExp);
		}

		LOGGER_OBJ.debug("updateStockTradeRecord() method execution completed.");

	}

	/**
	 * it maps database record fields to the StockTrade object fields.
	 */
	private static final class StockTradeMapper implements RowMapper<StockTrade> {

		@Override
		public StockTrade mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			StockTrade stockTradeObj = new StockTrade();
			stockTradeObj.setSecurity(resultSet.getString(1));
			stockTradeObj.setDate(resultSet.getDate(2));
			stockTradeObj.setOpen(resultSet.getDouble(3));
			stockTradeObj.setHigh(resultSet.getDouble(4));
			stockTradeObj.setLow(resultSet.getDouble(5));
			stockTradeObj.setClose(resultSet.getDouble(6));
			stockTradeObj.setVolume(resultSet.getDouble(7));
			stockTradeObj.setAdjClose(resultSet.getDouble(8));
			return stockTradeObj;
		}

	}

}