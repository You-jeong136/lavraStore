 <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%
	request.setCharacterEncoding("UTF-8");
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Lavra</title>
	<link type="text/css" rel="stylesheet" href="<c:url value='/style/view.css' />"> <%-- #paging 때문에 --%>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
	<script src="http://code.jquery.com/jquery-1.4.4.min.js"></script>
	
	<style>
.funcs {
			margin-top: 5%;
			margin-bottom: 10%;
		}
		.funcs input 
		{
			margin-right: 10px;
			padding: 5px;
			background-color: white;
			border: 1px solid #646EFF;
			color: #646EFF;
			border-top-left-radius: 5px; 
			border-bottom-left-radius: 5px;
			border-top-right-radius: 5px; 
        	border-bottom-right-radius: 5px;
      }
		.funcs input:hover
		{ 	
			color:white; 
			background-color: #646EFF; 
		}
		
		.cart_info {
			float:right; 
			position:absolute; 
			left: 70%;
			right: 5%;
		}
		.select{ 
			width: 5%;
		}
		.title{ 
			width: 15%;
		}
		.price{ 
			width: 15%;
		}
		.quan{ 
			width: 15%;
		}
		.total{ 
			width: 20%;
		}
		th, td {
			text-align: center;
		}
	</style>
	<script>
		$(document).ready(function() {
			$('#allCheck').click(function(){
				$('input[name=checkCartItem]:checkbox').attr('checked', true);
				$('input[name=soldoutCartItem]:checkbox').attr('checked', true);// input type 중 name이 checkCartItem이고, type이 checkbox인 경우
				//$('#quantity'+1029).val(String(1121));
			});
			$('#allReset').click(function(){
				$('input[name=checkCartItem]:checkbox').attr('checked', false); 
				$('input[name=soldoutCartItem]:checkbox').attr('checked', false);
			});
					
		});
		
	</script>
	<script>
	
		function moveTarget(targetUri) {
			form.action = targetUri;
			form.submit();
		}
		
		function checkDelConfirm(targetUri) {
			//삭제 검증일때는 soldoutFlag가 -1임. 상관없으니까.
			//alert(targetUri);
			var isChk = false;
			
			var products = document.getElementsByName("checkCartItem");
			//console.log('console' + products[0]);
			for(var i = 0; i < products.length; i++) {
				if(products[i].checked == true){
					isChk = true;
					break;
				}
			}
			
			if(!isChk) {
				var soldout = document.getElementsByName("soldoutCartItem");
				
				
				for(var i = 0; i < soldout.length; i++) {
					if(soldout[i].checked == true){
						isChk = true;
						break;
					}
				}
			}
			
			
			if(!isChk) {
				alert('상품을 1개 이상 선택해주세요.');
				return false;
			}
			
			moveTarget(targetUri);
		}
		
		function checkOrderConfirm(targetUri) {
			//삭제 검증일때는 soldoutFlag가 -1임. 상관없으니까.
			//alert(targetUri);
			var isChk = false;
		
	/* 		var soldout = document.getElementsByName("soldoutCartItem");
			
			
			for(var i = 0; i < soldout.length; i++) {
				if(soldout[i].checked == true){
					isChk = true;
					break;
				}
			}
			
			if(!isChk) {
				alert('품절된 상품이 포함되어 있습니다.');
				return false;
			}
			
			isChk = false; */ //컨트롤러에서 soldoutCartItem을 처리하지 않아 order로 넘어가지 않음. 
			
			var products = document.getElementsByName("checkCartItem");
			//console.log('console' + products[0]);
			for(var i = 0; i < products.length; i++) {
				if(products[i].checked == true){
					isChk = true;
					break;
				}
			}
			
			if(!isChk) {
				alert('상품을 1개 이상 선택해주세요.');
				return false;
			}
			
			moveTarget(targetUri);
		}
		
	</script>
	<script>	

		function updateQuantity(id, itemQuantity, page){
			var quantity = document.getElementById("quantity"+id).value;
		
			//document.getElementById("quantity"+id).value = quantity;
			//$('#quantity'+id).val(quantity);
			//alert(quantity + ' ' + "quantity"+id);
			//console.log($.isNumeric(quantity));
			if(quantity <= 0) {
				alert('입력하신 값이 올바르지 않습니다.');
				return false;
			}
			if(quantity > itemQuantity) {
				alert('현재 재고가 충분하지 않습니다.');
				return false;
			} 
			
			var msg = {
				"cartItemId" : id,
				"quantity": quantity
			}
			var jsonStr = JSON.stringify(msg);

			$.ajax({
				type: "POST",
				url: "/cart/uq",
				contentType : "application/json",
				data: jsonStr,
				processData: false,
				success: function(response) {
					//alert('success = ' + response.cartItemId);
					//alert(JSON.stringify(response));
					//document.getElementById(response.cartItemId).value = response.quantity;
					//$('#quantity'+response.cartItemId).val(String(response.quantity));
					//$('input[name=quantity]').val(response.quantity); //.attr('value', response)
					moveTarget('/cart/view/1?page='+page);
					
				},
				error: function(){
					alert("입력하신 형식이 옳지 않습니다.", arguments);
				}
			});
		}
		
	</script>
</head>
<body>
	<%@ include file="header.jsp" %>
	<div class="container"  style="margin-left: auto; margin-right: auto; width: 90%; margin-top:5%;">
		<ul class="nav nav-tabs">
		  <li  class="active"><a href="/cart/view/1">악세사리</a></li>
		  <%--<li><a href="/cart/view/2">개인거래</a></li>
		  <li><a href="/cart/view/3">공동구매</a></li>--%>
		</ul>
	</div> 
<%-- <c:if test="${cartItemList == '' || cartItemList eq null}"> 
		<div style="width: 50%; margin-left: auto; margin-right: auto;">
		아직 상품 준비가 되지 않았습니다.</div>
	</c:if> --%>
	<form method="POST" name="form"> <%-- action이 없으면 얘를 부른 컨트롤러로 넘어간다. 체크한 것만 넘어가면 돼서 Command 객체 필요 없음. form:form을 사용안 한 건 여기서 하나만 선택한 결과를 알고 싶은 게 아니고, 어떤 것들이 선택 되었는지가 중요하기 때문. 즉 path를 설정할  게 없음--%>
		<div class="container" style="margin-top: 5%;">
		<table class="table-hover w-auto"> <!-- class="table table-hover w-auto" -->
	    <thead>
	      <tr>
	        <th class="select">선택</th>
	        <th class="title">상품명</th>
	        <th class="title"></th>
	        <th class="price">가격</th>
	        <th class="quan">수량</th>
	        <th class="total">합계</th>
	      </tr>
	    </thead>
	    <tbody>
	    	<c:set var="allTotalCost" value="0" />
	    	<c:forEach var="cartItem" items="${cartItemList}">
	    		<c:set var="categoryId" value="${cartItem.categoryId}" />
	    		<tr>
	    			<c:set var="item" value="${cartItem.item}" />
	    			<c:set var="itemTotalCost" value="${item.price * cartItem.quantity}" />
	    			
	    			<td class="select">
	    			
	    			<c:choose>
						<c:when test="${item.isSoldout == 1}">
							<input type="checkbox" name="soldoutCartItem" value="${cartItem.cartItemId}" id="${cartItem.cartItemId}" class="checkWish allCheckbox"/> 
						</c:when>
						<c:when test="${item.isSoldout == 0}">
							<input type="checkbox" name="checkCartItem" value="${cartItem.cartItemId}" id="${cartItem.cartItemId}" class="checkWish allCheckbox"/>
						</c:when>
					</c:choose>
	    			
	    			
	    			</td>
	    			
	    			<td class="title">
	    			<a href="<c:url value='/accessory/detail'>
		            				<c:param name='no' value='${item.itemId}' /></c:url>">
	    			<img style="height: 50px;" src="<c:url value='${item.image}' />" /></a></td>
	    			<td class="title"><a href="<c:url value='/accessory/detail'>
		            				<c:param name='no' value='${item.itemId}' /></c:url>">
		            	${item.title}</a></td>
	    			<td  class="price">
	    			<c:choose>
						<c:when test="${item.isSoldout == 0}">
							<fmt:formatNumber value="${item.price}" pattern="###,###,###"/>원
						</c:when>
						<c:when test="${item.isSoldout == 1}">
							<span><font color="red">[품절]</font></span>
						</c:when>
					</c:choose>
	    			</td>
	    			<td  class="quan"><input type="text" id="quantity${cartItem.cartItemId}" name="quantity" value="${cartItem.quantity}" size="2" style="text-align:center;"/>&nbsp;<button id="btnQuan" onClick="updateQuantity(${cartItem.cartItemId}, ${item.quantity}, ${curPage})">수정</button></td> 
	    			<td  class="total"><fmt:formatNumber value="${itemTotalCost}" pattern="###,###,###"/>원</td>
	    			<c:set var="allTotalCost" value="${allTotalCost + itemTotalCost}" />
	    		</tr>
	    	</c:forEach>
	    </tbody>
	    </table>
	    <div id="paging">
			<c:forEach var="val" begin="1" end="${totalPageSize}"
				varStatus="status">
				<a href='<c:url value="/cart/view/${categoryId}?page=${val}"/>'>
					<font color="black"><B>${val}</B></font>
				</a>
				<c:if test="${!status.last}">&nbsp;|&nbsp;</c:if>
			</c:forEach>
		</div>
		<div class="well-lg" style=" border: 1px solid black; width: 90%; height: auto; position:relative; vertical-align: middle; min-height: 200px; overflow: auto; margin: auto;">
			<div class="cart_info well-sm" style="top: 20%;">상품합계 금액: <fmt:formatNumber value="${allTotalCost}" pattern="###,###,###"/>원</div>
			<div class="cart_info well-sm" style="top: 40%;">
				<c:if test="${allTotalCost < 200000}">
					<c:set var="deliver" value="3000" />
					배송비: 3,000원
				</c:if>
				<c:if test="${allTotalCost >= 200000}">
					<c:set var="deliver" value="0" />
					배송비: 무료 
				</c:if>
			</div>
			<c:set var="allTotalCost" value="${allTotalCost + deliver}" />
			<div class="well-sm" style="left: 35%; top: 65%; position:absolute; ">
				<B>전체 상품 주문 금액: <fmt:formatNumber value="${allTotalCost}" pattern="###,###,###"/>원</B></div>
			<div class="well-sm" style="left:70%; top: 75%; position:absolute; ">
				<font color="red">**20만원 이상 주문 시 <B>무료배송</B></font></div>
		</div>
		<div class="w3-center funcs">
         	<input type="button" value="전체 선택" id="allCheck" > 
         	<input type="button" value="전체 해제" id="allReset">
			<input type="button" value="선택 상품 모두 삭제" onClick="checkDelConfirm('<c:url value='/cart/handling/del' />')">
			<input type="button" value="선택 상품 모두 주문" onClick="checkOrderConfirm('<c:url value='/cart/buy' />')">
		 </div>
    </div>
	</form>
</body>