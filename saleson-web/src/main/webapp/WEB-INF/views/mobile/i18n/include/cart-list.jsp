<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>

<c:forEach items="${buy.receivers}" var="receiver" varStatus="receiverIndex">
	<c:forEach items="${receiver.itemGroups}" var="shipping" varStatus="deliveryIndex">
		<c:set var="viewCount" value="${ viewCount + 1 }" />
		<c:set var="singleShipping" value="${shipping.singleShipping}"/>
		<c:set var="shipping" value="${shipping}" scope="request" /> 
		<c:set var="rowspan" value="0" scope="request" />
		<c:if test="${singleShipping == false}">
			<c:set var="rowspan" value="${ fn:length(shipping.buyItems) }" scope="request" />
		</c:if>
		<div class="cart_list">
			<ul class="list">
				<li>
					<span class="check">
						<input type="checkbox" id="" name="" value="" />
						<label for="">선택</label>
					</span>
					<div class="inner">
						<div class="con_top cf">
							<div class="cart_img"></div>
							<div class="cart_name">
								<p class="tit">이름</p>
								<p class="detail">설명</p>
							</div>
						</div>
						<div class="con_bot">
							<div class="cacul">
								<a href="#" class="minus"></a>
								<input type="text" class="num" value="1"/>
								<a href ="#" class="plus">증가</a>
								<button type="button" class="btn_st3">변경</button>
							</div><!--//cacul E -->
							<div class="price">
								<p class="per_price"><span></span>원</p>
								<p class="sale_price"><span></span>원</p>
							</div>
						</div>
					</div><!--//inner E -->
				</li>
			</ul>
			<div class="shipping_wrap">
				<sapn class="tit">배송비</sapn>
				<a href="#" class="btn_st4 t_blue">배송정책(무료배송/판매자조건부무료 등)</a>
				<p class="shipping_price"><span></span>원</p>
			</div><!--//shipping_wrap E -->
		</div><!--//cart_list E -->
		</c:forEach>
</c:forEach>