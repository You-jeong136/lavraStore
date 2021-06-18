package com.example.lavrastore.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.lavrastore.domain.PTPItem;

public interface PTPItemDao {

	PTPItem getPItem(int itemid) throws DataAccessException;
	
	List<PTPItem> getPItemListByProduct(int productId) throws DataAccessException;
	
	// 가격순
	List<PTPItem> getPItemListByHighPrice();
	List<PTPItem> getPItemListByLowPrice();
	
	/* CREATE */
	int insertPItem() throws DataAccessException;
	
	/* UPDATE */
	int updatePItem(int itemId) throws DataAccessException;
	
	/* DELETE */
	int deletePItem(int itemId) throws DataAccessException;
}