<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<c:forEach items="${ list }" var="item" varStatus="i" >
		<div id="op-delivery-info-${ item.userDeliveryId }">
			<input type="hidden" name="userDeliveryId" value="${ item.userDeliveryId }" />
			<input type="hidden" id="userName" value="${ item.userName }" />
			<input type="hidden" id="phone1" value="${ item.phone1 }" />
			<input type="hidden" id="phone2" value="${ item.phone2 }" />
			<input type="hidden" id="phone3" value="${ item.phone3 }" />
			<input type="hidden" id="mobile1" value="${ item.mobile1 }" />
			<input type="hidden" id="mobile2" value="${ item.mobile2 }" />
			<input type="hidden" id="mobile3" value="${ item.mobile3 }" />
			<input type="hidden" id="newZipcode" value="${ item.newZipcode }" />
			<input type="hidden" id="zipcode" value="${ item.zipcode }" />
			<input type="hidden" id="zipcode1" value="${ item.zipcode1 }" />
			<input type="hidden" id="zipcode2" value="${ item.zipcode2 }" />
		
			<input type="hidden" id="sido" value="${ item.sido }" />
			<input type="hidden" id="sigungu" value="${ item.sigungu }" />
			<input type="hidden" id="eupmyeondong" value="${ item.eupmyeondong }" />
			
			<input type="hidden" id="address" value="${ item.address }" />
			<input type="hidden" id="addressDetail" value="${ item.addressDetail }" />
		</div>
		<div class="list ${i.index == 0 ? 'nm' : ''}">
			<p class="lct">
				${ item.title }
				<c:if test="${ item.defaultFlag eq 'Y' }">
					<p class="color_e42222">(기본)</p>
				</c:if>	
			</p>
			<p class="name">${ item.userName }</p>
			<p class="number">
				<span>${ item.mobile }</span>
				<span>/</span>
				<span>${ item.phone }</span>
			</p>
			<p class="address">(${item.zipcode}) ${ item.address } &nbsp; ${ item.addressDetail }</p>
			<div class="btn_area cf">
				<a href="javascript:setDefaultAddr(${ item.userDeliveryId });" class="btn_st2 blue2" title="기본배송지 선택">기본배송지 선택</a>
				<a href="javascript:selectDelivery(${ item.userDeliveryId });" class="btn_st2 blue" title="배송지 선택">배송지 선택</a>
			</div>
		</div>
</c:forEach>