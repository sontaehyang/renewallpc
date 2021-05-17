<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<h2><span>${op:message('M00824')}</span></h2> <!-- 기본정보 -->



<table cellpadding="0" cellspacing="0" summary="" class="board_list_table mb30">
	<caption>${op:message('M00246')} 지급/차감</caption>
	<colgroup>
		<col style="width:120px;" /> 
		<col style="width:*" />
		<col style="width:120px;" /> 
		<col style="width:*" />
	</colgroup>
	<tbody>
		<tr>
			<th scope="row">${op:message('M00005')}</th> <!-- 이름 -->
			<td class="tleft">
				<div>
					${user.userName}
				</div>
			</td>
			<th scope="row">${op:message('M00081')}(${op:message('M00125')})</th> <!-- 아이디 --> <!-- 이메일 -->
			<td class="tleft">
				<div>${user.loginId} / ${user.userDetail.userlevel.levelName}</div>
			</td>
		</tr>
		<tr>
			<th scope="row">핸드폰번호</th>
			<td class="tleft">
				<div>
					${user.userDetail.phoneNumber}
				</div>
			</td>
			<th scope="row">${op:message('M00016')}</th> <!-- 전화번호 -->
			<td class="tleft">
				<div>
					${user.userDetail.telNumber}
			 	</div>
			</td>
		</tr>
		<tr>
			<th scope="row">생년월일</th>
			<td class="tleft">
				<div>
					${user.userDetail.birthday}
			 	</div>
			</td>
		</tr>
		<tr>
			<th scope="row">${op:message('M00118')}</th> <!-- 주소 -->
			<td class="tleft" colspan="3">
				<div>
					 (${empty user.userDetail.newPost ? user.userDetail.post : user.userDetail.newPost})
					 &nbsp;${user.userDetail.address}&nbsp;${user.userDetail.addressDetail}
			 	</div>
			</td>
		</tr>
		<tr>
			<th scope="row">${op:message('M00836')}</th> <!-- 회원가입일 -->
			<td class="tleft">
				<div>${op:datetime(user.createdDate)}</div>
			</td>
			<th scope="row">${op:message('M00837')}</th> <!-- 마지막로그인 -->
			<td class="tleft">
				<div>${op:datetime(user.loginDate)} (login : ${user.loginCount}${op:message('M00851')}${op:message('M00852')})</div> <!-- 회방문 -->
			</td>
		</tr>
	</tbody>
</table> <!-- // 기본정보 끝 -->

<h2><span>최근 주문내역</span></h2>
<table class="board_list_table" summary="주문내역 리스트">
	<caption>주문내역 리스트</caption>
	<colgroup>
		<col style="width:5%;" />
		<col style="width:10%;" />
		<col style="width:7%;" />
		<col style="width:7%;" />
		<col style="width:7%;" />
		<col />
		<col style="width:10%;" />
		<col style="width:10%;" />
		<col style="width:10%;" />
		<col style="width:10%;" />
	</colgroup>
	<thead>
		<tr>
			<th scope="col">주문번호</th>
			<th scope="col">주문일자</th>
			<th scope="col">수취인</th>
			<th scope="col">판매자</th>
			<th scope="col" colspan="2">상품정보</th>
			<th scope="col">수량</th>
			<th scope="col">판매금액</th>
			<th scope="col">주문상태</th>
			<th scope="col">배송정보</th>
		</tr>

	</thead>
	<tbody>
		<c:forEach items="${ orderList }" var="order" varStatus="index">
			<c:forEach items="${order.orderShippingInfos}" var="info">
				<c:forEach items="${info.orderItems}" var="orderItem" varStatus="itemIndex">
					<c:set var="totalSaleAmount" value="${orderItem.saleAmount}" />
					<c:forEach items="${orderItem.additionItemList}" var="addition">
						<c:set var="totalSaleAmount">${totalSaleAmount + addition.saleAmount}</c:set>
					</c:forEach>

					<tr ${orderItem.additionItemFlag == 'Y' ? 'style="display:none"' : ''}>
						<td>
							<a href="javascript:goUrl('/opmanager/order/view/order-detail/${order.orderSequence}/${orderItem.orderCode}')">${orderItem.orderCode}</a><br />
						</td>
						<td>
							${op:datetime(order.createdDate)}
						</td> 
						<td>${info.receiveName}</td>
						<td>
							<c:choose>
								<c:when test="${shop:sellerId() == orderItem.sellerId}">자사</c:when>
								<c:otherwise>
									<span class="glyphicon glyphicon-user"></span>${orderItem.sellerName}
								</c:otherwise>
							</c:choose> 
						</td>
						<td>
							<img src="${shop:loadImageBySrc(orderItem.imageSrc, 'XS')}" alt="${orderItem.itemName}" width="100%"/>
						</td>
						<td class="text-left">
							${orderItem.itemName} [${orderItem.itemUserCode}]
							${ shop:viewOptionText(orderItem.options) }
							${ shop:viewAdditionOrderItemList(orderItem.additionItemList)}
						</td>
						<td class="text-right"><strong>${orderItem.quantity}개</strong></td>
						<td class="text-right">${op:numberFormat(totalSaleAmount)}원</td>
						<td>${orderItem.orderStatusLabel}</td>
						<td>
							<c:if test="${orderItem.shippingDate != '00000000000000'}">
								<c:choose>
									<c:when test="${empty orderItem.deliveryNumber}">
										${orderItem.deliveryMethodType.title}
									</c:when>
									<c:otherwise>
										송장 번호 : ${orderItem.deliveryNumber}(${orderItem.deliveryCompanyName})
									</c:otherwise>
								</c:choose>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</c:forEach>
		</c:forEach>
	</tbody>
</table>
<c:if test="${empty orderList}">
<div class="no_content">
	${op:message('M00473')} <!-- 데이터가 없습니다. --> 
</div>
</c:if>					

<script type="text/javascript">
$(function() {
	Manager.activeUserDetails("details");
});

function goUrl(url) {
	opener.location.href = url;
}

</script>
