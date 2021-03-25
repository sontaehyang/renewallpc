<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
	<div class="title">
		<h2>회원정보</h2>
		<span class="his_back"><a href="/m/mypage" class="ir_pm">뒤로가기</a></span>
		<ul class="tab_list01 four_tab letter">
			<li><a href="/m/users/editMode">회원정보수정</a></li>
			<li class="on"><a href="#">배송주소록 관리</a></li>
			<li><a href="/m/sns-user/setup-sns">SNS연동설정</a></li>
			<li><a href="/m/users/secede">회원탈퇴</a></li>
		</ul>
	</div>
	<!-- //title -->
	
	<!-- 내용 : s -->
	<div class="con">
		<div class="mypage_wrap">
			<div class="address_manage">
				<div class="cart_top">
					<p class="txt">배송지를 등록 및 관리 하실 수 있습니다.</p>
					<a href="#" type="button" class="btn_st3" onclick="writeDelivery()">배송지 추가</a>
				</div>
				<!-- //cart_top -->
				
				<div class="address_list">
					<div class="list_info">
					
						<c:forEach var="item" items="${ list }">
						
						<c:if test="${item.defaultFlag == 'Y'}">
						<div class="list nm">
							<p class="lct">${ item.title } (기본배송지)</p>
							<p class="name">${ item.userName }</p>
							<p class="number">
								<span>${ item.mobile }</span>
								<span>/</span>
								<span>${ item.phone }</span>
							</p>
							<p class="address">${ item.address }&nbsp;${ item.addressDetail }</p>
							<div class="btn_area cf">
								<button type="button" class="btn_st3 b_white" onclick="editDelivery(${item.userDeliveryId})">수정</button>
								<button type="button" class="btn_st3 b_white" onclick="deleteAction(${item.userDeliveryId})">삭제</button>
							</div>
						</div>
						</c:if>
						
						<c:if test="${item.defaultFlag == 'N' }">
						
						<div class="list">
							<p class="lct">${ item.title }</p>
							<p class="name">${ item.userName }</p>
							<p class="number">
								<span>${ item.mobile }</span>
								<span>/</span>
								<span>${ item.phone }</span>
							</p>
							<p class="address">${ item.address } ${ item.addressDetail }</p>
							<div class="btn_area cf">
								<button type="button" class="btn_st3 bd_blue decision" onclick="setDefaultAddr(${item.userDeliveryId})">기본배송지 선택</a>
								<button type="button" class="btn_st3 b_white" onclick="editDelivery(${item.userDeliveryId})">수정</a>
								<button type="button" class="btn_st3 b_white" onclick="deleteAction(${item.userDeliveryId})">삭제</a>
							</div>
						</div>
						
						
						</c:if>
						
			
						</c:forEach>
						
						<c:if test="${empty list }">
							<ul>
								<li>
									<div class="common_none">
										<p>배송주소록 정보가 없습니다.</p>
									</div>
								</li>
							</ul>				
						</c:if>
						
					</div>
					<!-- //list_info -->
				</div>
				<!-- //address_list -->

			</div>
			<!-- //address_manage -->
			
		</div>
		<!-- //mypage_wrap -->
	</div>
	<!-- 내용 : e -->
		

<page:javascript>
<script type="text/javascript">
function deleteAction(id) {
	if (!confirm("해당 배송지를 삭제 하시겠습니까?")) {
		return;
	}
	
	location.href = '/m/delivery/list-action/del?target=/m/mypage/delivery&userDeliveryId='+id;
}

function editDelivery(id) {
	location.href='/m/delivery/edit/' + id;
}

function setDefaultAddr(id) {
	if (!confirm("해당 배송지를 기본 배송지로 설정 하시겠습니까?")) { 
		return;
	} 
	
	location.href = '/m/delivery/list-action/mod?target=/m/mypage/delivery&userDeliveryId='+id;
}

function writeDelivery() {
	location.href='/m/delivery/write?target=mypage';
}
</script>
</page:javascript>