<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<div class="popup_wrap">
	<h1 class="popup_title">상품쿠폰다운로드</h1> <!-- 발급가능 쿠폰 다운로드 -->
	<div class="popup_contents">
		
			<div class="board_wrap mt0">  
				<form id="couponForm">
					<table cellpadding="0" cellspacing="0" class="board-list">
			 			<caption>상품쿠폰다운로드</caption>
			 			<colgroup> 
			 				<col style="width:40px;">
			 				<col style="width:auto;">
			 				<col style="width:115px;">
			 				<col style="width:180px;">
			 				<col style="width:110px;">
			 			</colgroup>
			 			<thead>
			 				<tr>
			 					<th scope="col"><input type="checkbox" name="" id="allCheck" title="체크박스" /></th>
			 					<th scope="col">할인 쿠폰명</th>
			 					<th scope="col">할인금액</th>
			 					<th scope="col">유효기간</th>
			 					<th scope="col">상태</th>
			 				</tr>
			 			</thead>
			 			<tbody>
			 				<c:forEach items="${list}" var="coupon" varStatus="i">
			 					<tr>
				 					<td><input type="checkbox" name="couponId" title="체크박스" value="${coupon.couponId}" /></td>
				 					<td class="tleft"><div>${coupon.couponName}</div></td>
				 					<td>
				 						<div>
				 							<span class="coupons">${op:numberFormat(coupon.couponPay)}${coupon.couponPayType == '1' ? '원' : '%'}</span>
				 							<c:if test="${coupon.couponPayType == '2' && coupon.couponDiscountLimitPrice != -1}">, 최대 ${op:numberFormat(coupon.couponDiscountLimitPrice)}원 까지 할인</c:if>
				 						</div>
				 					</td>
				 					<td> 
				 						<c:choose>
											<c:when test="${coupon.couponApplyType == '0'}">제한없음</c:when> 
											<c:when test="${coupon.couponApplyType == '2' }">다운로드 시점부터 <strong>${coupon.couponApplyDay}일</strong> 후까지</c:when>
											<c:otherwise>${op:date(coupon.couponApplyStartDate)} ~ ${op:date(coupon.couponApplyEndDate)}</c:otherwise>
										</c:choose>
				 					</td>
				 					<td>
				 						<a href="javascript:couponDownload(${coupon.couponId })" class="btn btn-s btn-normal">다운받기</a>
				 					</td>
				 				</tr>
			 				</c:forEach>
			 				
			 			</tbody>
			 		</table>
			 		<c:if test="${empty list}">
				 		<div class="no_content">
				 			<p>${op:message('M01110')} <!-- 발급 가능한 쿠폰이 없습니다 --> </p>
				 		</div>
			 		</c:if>
			 		
			 		<div class="btn_wrap">
			 			<div class="btn_left">
				 			<button type="button" class="btn btn-default btn-m" id="allDownload">일괄발급</button>
				 		</div>
				 	</div>
				</form>
			
		</div>

		<page:pagination />
	</div><!--//popup_contents E-->
	
	<a href="javascript:self.close();" class="popup_close">창 닫기</a>
</div>
	
<page:javascript>	
<script type="text/javascript">
	$(function(){
		
		$("#allCheck").on("click",function(){
			if($(this).prop("checked")){
				$("input[name='couponId']").each(function(){
					$(this).prop("checked",true);
				});
			}else{
				$("input[name='couponId']").each(function(){
					$(this).prop("checked",false);
				});
			}
		});
		
		
		$("#allDownload").on("click",function(){
			
			if ($("input[name='couponId']:checked").size() == 0) {
				alert('발급을 원하시는 쿠폰을 선택해 주세요.');
				return;
			}
			
			Common.confirm(Message.get("M01502"), function(){ // 쿠폰을 다운 받으시겠습니까? 
				
				var errorCount = 0;
				$.each($("input[name='couponId']:checked"), function(){
					
					if($(this).prop("checked"))	{
						var couponId = $(this).val();
						$.post("/mypage/coupon-download/"+couponId, null, function(resp){
							if (!resp.isSuccess) {
								errorCount++;
							}
						}, 'json');
						
					}
				});
			
				if ($("input[name='couponId']:checked").size() != errorCount) {
					alert('쿠폰이 다운로드 되었습니다.');
					opener.location.reload();
				}
			
				location.reload();
			}); 
			
		});
	});
	 
	function couponDownload(couponId) {
		Common.confirm(Message.get("M01502"), function(){ // 쿠폰을 다운 받으시겠습니까?
			$.post("/mypage/coupon-download/"+couponId, null, function(resp) {
				if (resp.isSuccess) {
					alert('쿠폰이 다운로드 되었습니다.');
					location.reload();
					opener.location.reload(); 
				} else { 
					alert(resp.errorMessage);
					location.reload();
				}
			}, 'json');
			
		});
	}
</script>
</page:javascript>