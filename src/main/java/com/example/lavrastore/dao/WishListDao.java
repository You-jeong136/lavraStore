package com.example.lavrastore.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.lavrastore.domain.Item;
import com.example.lavrastore.domain.WishList;


public interface WishListDao {
	List<WishList> getAllWishList() throws DataAccessException;
	Item getItemByWishListId(int wishListId) throws DataAccessException;
	int getWishListByItemIdAndMemberId(int itemId, String memberId) throws DataAccessException;
	// Create
	int insertWishList(WishList wishList) throws DataAccessException; // 0이면 실패, 0 초과는 성공


	// Delete

	int deleteWishList(int wishListId) throws DataAccessException;
	//int deleteWishList2(int itemId) throws DataAccessException;
	int deleteWishListByItemIdAndMemberId(int itemId, String memberId);
}
