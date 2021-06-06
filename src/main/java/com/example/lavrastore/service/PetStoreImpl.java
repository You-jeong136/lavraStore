package com.example.lavrastore.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.lavrastore.dao.CartItemDao;
import com.example.lavrastore.dao.CategoryDao;
import com.example.lavrastore.dao.GroupItemDao;
import com.example.lavrastore.dao.ItemDao;
import com.example.lavrastore.dao.MemberDao;
import com.example.lavrastore.dao.ProductDao;
import com.example.lavrastore.domain.CartItem;
import com.example.lavrastore.domain.Category;
import com.example.lavrastore.domain.GroupItem;
import com.example.lavrastore.domain.Item;
import com.example.lavrastore.domain.Member;
import com.example.lavrastore.domain.Product;

@Service
@Transactional
public class PetStoreImpl implements PetStoreFacade {
	@Autowired
	private CategoryDao categoryDao;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ItemDao itemDao;
	@Autowired
	private CartItemDao cartItemDao;
	@Autowired
	private GroupItemDao groupItemDao;
	@Autowired
	private MemberDao memberDao;

	// -------------------------------------------------------------------------
	// Operation methods, implementing the PetStoreFacade interface
	// -------------------------------------------------------------------------


	/* Member */
	
	public Member getMember(String memberId) {
		return memberDao.getMember(memberId);
	}

	public Member getMember(String memberId, String password) {
		return memberDao.getMember(memberId, password);
	}

	public void insertMember(Member member) {
		memberDao.insertMember(member);
	}

	public void updateMember(Member member) {
		memberDao.updateMember(member);
	}

	public List<String> getUsernameList() {
		return memberDao.getUsernameList();
	}

	/* Category */

	public List<Category> getCategoryList() {
		return categoryDao.getCategoryList();
	}

	public Category getCategoryById(int categoryId) {
		return categoryDao.getCategoryById(categoryId);
	}

	public int insertCategory(Category category) {
		return categoryDao.insertCategory(category);
	}

	public int updateCategory(Category category) {
		return categoryDao.updateCategory(category);
	}

	public int deleteCategory(String categoryId) {
		return categoryDao.deleteCategory(categoryId);
	}
	

	

	/* Product */

	public List<Product> getProductListByCategory(int categoryId) {
		return productDao.getProductListByCategory(categoryId);
	}

	public Product getProductById(int productId) {
		return productDao.getProductById(productId);
	}
	
	@Override
	public Product getProductByName(String name, int categoryId) {
		return productDao.getProductByName(name, categoryId);
	}

	public Category getCategoryByProId(int productId) {
		return productDao.getCategoryByProId(productId);
	}
	
	public List<Product> searchProductList(String keywords){
		return productDao.searchProductList(keywords);
	}

	public int insertProduct(Product product) {
		return productDao.insertProduct(product);
	}

	public int updateProduct(Product product) {
		return productDao.updateProduct(product);
	}

	public int deleteProduct(Product product) {
		return productDao.deleteProduct(product);
	}

	/* Item */

	public List<Item> getItemListByProduct(int productId) {
		return itemDao.getItemListByProduct(productId);
	}

	public Item getItem(int itemId) {
		return itemDao.getItem(itemId);
	}

	@Override
	public List<Item> getItemForNotUser(int productId) {
		return itemDao.getItemForNotUser(productId);
	}

	@Override
	public List<Item> getItemForUser(String memberId, int productId) {
		return itemDao.getItemForUser(memberId, productId);
	}

	@Override
	public List<Item> getItemOrderByHighPriceForUser(String memberId, int productId) {
		return itemDao.getItemOrderByHighPriceForUser(memberId, productId);
	}

	@Override
	public List<Item> getItemOrderByHighPriceForNotUser(int productId) {
		return itemDao.getItemOrderByHighPriceForNotUser(productId);
	}

	@Override
	public List<Item> getItemOrderByLowPriceForUser(String memberId, int productId) {
		return itemDao.getItemOrderByLowPriceForUser(memberId, productId);
	}

	@Override
	public List<Item> getItemOrderByLowPriceForNotUser(int productId) {
		return itemDao.getItemOrderByLowPriceForNotUser(productId);
	}

	@Override
	public int insertItem(int itemId) {
		return itemDao.insertItem(itemId);
	}

	@Override
	public int updateItem(int itemId) {
		return itemDao.updateItem(itemId);
	}
	 
	@Override
	public int deleteItem(int itemId) {
		return itemDao.deleteItem(itemId);
	}

	/* CartItem */
	
	@Override
	public List<CartItem> getAllCartList() {
		return cartItemDao.getAllCartList();
	}

	@Override
	public List<CartItem> getCartListByGeneralCategory(int categoryId, String memberId) {
		return cartItemDao.getCartListByGeneralCategory(categoryId, memberId);
	}

	@Override
	public List<CartItem> getCartListByGroupCategory(int categoryId, String memberId) {
		return cartItemDao.getCartListByGroupCategory(categoryId, memberId);
	}

	@Override
	public List<CartItem> getCartListByPTPCategory(int categoryId, String memberId) {
		return cartItemDao.getCartListByPTPCategory(categoryId, memberId);
	}

	@Override
	public Item getItemByCartItemId(int cartItemId, String memberId) {
		return cartItemDao.getItemByCartItemId(cartItemId, memberId);
	}

	@Override
	public int insertCartItem(CartItem cartItem) {
		return cartItemDao.insertCartItem(cartItem);
	}

	@Override
	public int updateCartItem(CartItem cartItem) {
		return cartItemDao.updateCartItem(cartItem);
	}

	@Override
	public int deleteCartItem(int cartItemId) {
		return cartItemDao.deleteCartItem(cartItemId);
	}

	
	/*group Item */
	@Override
	public List<GroupItem> getAllGItemList() {
		return groupItemDao.getAllGItemList();
	}

	@Override
	public List<GroupItem> getGItemListByProduct(int productId) {
		return groupItemDao.getGItemListByProduct(productId);
	}

	@Override
	public List<GroupItem> getGItemListByMember(String memberId) {
		return groupItemDao.getGItemListByMember(memberId);
	}

	@Override
	public List<GroupItem> getGItemListByDeadline() {
		return groupItemDao.getGItemListByDeadline();
	}

	@Override
	public List<GroupItem> getGItemListByWindUp() {
		return groupItemDao.getGItemListByWindUp();
	}

	@Override
	public List<GroupItem> getGItmeListByPercent() {
		return groupItemDao.getGItmeListByPercent();
	}

	@Override
	public int insertGItem() throws DataAccessException {
		return groupItemDao.insertGItem();
	}

	@Override
	public int updateGItem(int itemId) throws DataAccessException {
		return groupItemDao.updateGItem(itemId);
	}

	@Override
	public int deleteGItem(int itemId) throws DataAccessException {
		return groupItemDao.deleteGItem(itemId);
	}

	@Override
	public GroupItem getGItem(int itemid) {
		return groupItemDao.getGItem(itemid);
	}

}