package com.example.lavrastore.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.example.lavrastore.domain.CartItem;
import com.example.lavrastore.domain.Item;
import com.example.lavrastore.domain.LineItem;
import com.example.lavrastore.domain.Member;
import com.example.lavrastore.domain.Order;
import com.example.lavrastore.service.OrderValidator;
import com.example.lavrastore.service.PetStoreFacade;

@Controller
@SessionAttributes("itemOrder")
public class ItemOrderController {
	
	private PetStoreFacade lavraStore;
	UserSession userSession;

	@Autowired
	private OrderValidator orderValidator;
	
	@Autowired
	public void setPetStore(PetStoreFacade petStore) {
		this.lavraStore = petStore;
	}

	@ModelAttribute
	protected void checkLogin(HttpServletRequest request) {
		userSession = (UserSession) request.getSession().getAttribute("userSession");
	}
	
	@ModelAttribute("creditCardTypes")
	public List<String> referenceData() {
		ArrayList<String> creditCardTypes = new ArrayList<String>();
		creditCardTypes.add("Visa");
		creditCardTypes.add("MasterCard");
		creditCardTypes.add("American Express");
		return creditCardTypes;			
	}
	
	@ModelAttribute("bankTypes") 
	public List<String> bankData() {
		ArrayList<String> bankTypes = new ArrayList<String>();
		bankTypes.add("???????????????");
		bankTypes.add("????????????");
		bankTypes.add("????????????");
		bankTypes.add("SC??????(??? ??????)");
		bankTypes.add("????????????");
		bankTypes.add("????????????");
		return bankTypes;			
	}
	
	@ModelAttribute("itemOrder")
	public Order createNewOrder(HttpSession session) {
		Order itemOrder = (Order)session.getAttribute("itemOrder");
		if (itemOrder == null) itemOrder = new Order();
		return itemOrder;
	}

	@PostMapping("/item/buy")
	@Transactional
	public String buyItem(
			@RequestParam(value="no", defaultValue = "-1") int no,
			@RequestParam(value="q", defaultValue= "-1") int q,
			@ModelAttribute("itemOrder") Order itemOrder,
			Model model) {
		if(no == -1 || q == -1) return "redirect:error";
		
		Member member;
		if (userSession == null) {
			return "LoginForm";
		}

		member = userSession.getMember();
		Item item = lavraStore.getItem(no);
		
		itemOrder.initItemOrder(item, member, q);
		
		model.addAttribute("item", item);
		//model.addAttribute("member", member);
		model.addAttribute("isInCart", 0); //????????? ?????? ????????????????????? ???????????? ????????? 
		model.addAttribute("quantity", q);
		
		return "ItemOrder";
	}
	
	@PostMapping("/cart/buy") 
	@Transactional
	public String buyCartItem(
			HttpServletRequest request, @ModelAttribute("itemOrder") Order itemOrder, Model model) {
		
		Member member;
		if (userSession == null) {
			return "LoginForm";
		}
		
		member = userSession.getMember();
		String[] checkCartItem = request.getParameterValues("checkCartItem");
		 
		 if(checkCartItem == null) {
			 return "redirect:error";
		 }
		 
		 ArrayList<CartItem> list = new ArrayList<CartItem>();
		 for(int i = 0; i < checkCartItem.length; i++) {
			 int cartItemId = Integer.parseInt(checkCartItem[i]);
			 list.add(lavraStore.getCartItemById(cartItemId));
		 }
		 
		 for(CartItem cartItem : list) {
			 cartItem.setItem(lavraStore.getItemByCartItemId(cartItem.getCartItemId(), member.getMemberId()));
		 }
		 
		 itemOrder.initCartItemOrder(list, member); //cartItem?????? ?????? quantity??? item??????. 
		 
		 model.addAttribute("cartItemList", list);
		// model.addAttribute("member", member);
		 model.addAttribute("isInCart", 1);
		 
		 return "ItemOrder";
	}
	
	@PostMapping("/item/orderSubmit/{isInCart}")
	@Transactional
	public ModelAndView validateAndConfirmOrder(
			HttpServletRequest request,
			@PathVariable int isInCart,
			@ModelAttribute("itemOrder") Order itemOrder,
			BindingResult result,
			SessionStatus status) {
		System.out.println("orderSubmit?????? ????????? ??? ?????? :" + itemOrder.getCardNum() + ", " + itemOrder.getPayType() +
				"," + itemOrder.getLineItems().get(0).getItemId() + "," + itemOrder.getShipAddr1());
		
		orderValidator.validateShippingAddress(itemOrder, result);
		if(itemOrder.getPayType() == 0) { //?????? ????????? ?????? ????????? ?????? ?????? ???????????? ??????????????? ??????. ????????? ???????????? ?????? ?????? ????????? ????????? ????????? ??????.
			orderValidator.validateNoBank(itemOrder, result);
		} else {
			orderValidator.validateCreditCard(itemOrder, result);
		}
		
		
		if (result.hasErrors()) {
			ModelAndView mav = new ModelAndView("ItemOrder");
			if(isInCart == 0) {
				mav.addObject("item", itemOrder.getLineItems().get(0).getItem());
				//mav.addObject("member", member);
				mav.addObject("isInCart", 0); //????????? ?????? ????????????????????? ???????????? ????????? 
				mav.addObject("quantity", itemOrder.getLineItems().get(0).getQuantity());
				return mav; 
			} else {
				 String[] payment_product = request.getParameterValues("payment_product");
				 for(String s : payment_product) {
					 System.out.println(s);
				 }
				 ArrayList<CartItem> list = new ArrayList<CartItem>();
				 for(int i = 0; i < payment_product.length; i++) {
					 int cartItemId = Integer.parseInt(payment_product[i]);
					 System.out.println(lavraStore.getCartItemById(cartItemId).getCartItemId());
					 list.add(lavraStore.getCartItemById(cartItemId));
				 }
				 
				 for(CartItem cartItem : list) {
					 cartItem.setItem(lavraStore.getItemByCartItemId(cartItem.getCartItemId(), itemOrder.getMemberId()));
				 }
				
				mav = new ModelAndView("ItemOrder");
				mav.addObject("cartItemList", list);
				mav.addObject("isInCart", 1); //????????? ?????? ????????????????????? ???????????? ????????? 
				return mav; 
			}
		}
		
		// ????????? ????????? 
		lavraStore.insertOrder(itemOrder); //lineItem ????????????.
		
		ModelAndView mav = new ModelAndView("ItemOrderConfirm");
		mav.addObject("itemOrder", itemOrder);
		mav.addObject("success", true);
		mav.addObject("lineItems", itemOrder.getLineItems()); //item ?????? ??? ??????.
		
		status.setComplete();  // remove sessionCart and orderForm from session
		
		return mav;
	}
	
	@GetMapping("/item/viewOrder")
	@Transactional
	public ModelAndView itemViewOrder(
			@RequestParam(value = "no", defaultValue = "-1") int no) {
		Order order = lavraStore.getOrderById(no);
		List<LineItem> lineItems = lavraStore.getLineItemByOrderId(no);
		

		for(LineItem lineItem : lineItems) {
			lineItem.setItem(lavraStore.getItem(lineItem.getItemId()));
		}
		
		order.setLineItems(lineItems);
		
		ModelAndView mav = new ModelAndView("ItemOrderConfirm");
		
		mav.addObject("itemOrder", order);
		mav.addObject("success", true);
		mav.addObject("lineItems", lineItems);
		
		return mav;
	}

}
