package com.example.lavrastore.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.lavrastore.domain.Item;


public interface ItemDao {
  
  
  /* Read */
	
	List<Item> getItemListTopKByLikeCnt(int categoryId, int number) throws DataAccessException;
	List<Item> getItemListByProduct(int productId) throws DataAccessException;
	Item getItem(int itemId) throws DataAccessException;
	List<Item> getItemForNotUser(int productId) throws DataAccessException; // 인기순으로 되어 있음. 비로그인 용 
	List<Item> getItemForUser(String memberId, int productId) throws DataAccessException;
	List<Item> searchItemList(String keywords) throws DataAccessException;
	/*
	 * //인기순 
	 * List<Item> getItemOrderByHighLikeCntForUser(String memberId) throws DataAccessException; 
	 * List<Item> getItemOrderByHighLikeCntForNotUser() throws DataAccessException;
	 */
	
	//높은 가격순
	List<Item> getItemOrderByHighPriceForUser(String memberId, int productId) throws DataAccessException;
	List<Item> getItemOrderByHighPriceForNotUser(int productId) throws DataAccessException;
	
	// 낮은 가격순
	List<Item> getItemOrderByLowPriceForUser(String memberId, int productId) throws DataAccessException;
	List<Item> getItemOrderByLowPriceForNotUser(int productId) throws DataAccessException;
	
	
	/* Create */
	
	int insertItem(int itemId) throws DataAccessException;
	int insertPTPItem(Item item) throws DataAccessException;
	
	/* Update */
	
	int updateItem(Item item) throws DataAccessException;
	int updateItem2(Item item) throws DataAccessException;// ppt ItemDao의 update 부분의 기능들을 다 합침.
	
	/* Delete */
	
	int deleteItem(int itemId) throws DataAccessException;

}
