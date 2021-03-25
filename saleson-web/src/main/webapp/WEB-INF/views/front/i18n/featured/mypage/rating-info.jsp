<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<!-- 팝업사이즈 600*673-->
<div class="popup_wrap">
	<h1 class="popup_title">등급 및 혜택 안내</h1>
	<div class="popup_contents">
		<div class="pop_table mt0">
	 		<table class="benefit_info">
	 			<caption>반송지 정보</caption>
	 			<colgroup>
	 				<col style="width:16%;">
	 				<col style="width:auto;">
	 				<col style="width:15%;">
	 				<col style="width:21.5%;">
	 				<col style="width:14%;">
	 			</colgroup>
	 			<thead>
	 				<tr>
	 					<th scope="col">등급</th>
	 					<th scope="col">등급조건</th>
	 					<th scope="col">적립혜택</th>
	 					<th scope="col">구매시 할인</th>
	 					<th scope="col">배송쿠폰</th>
	 				</tr>
	 			</thead>
	 			<tbody>
	 				<c:forEach items="${userLevels}" var="grade" varStatus="gradeIndex">
	 				<tr>
	 					<td class="level">
							<%--  혜택보기 팝업창 회원등급 이미지  --%>
							<span class="ico"><img src="/content/mobile/images/common/${grade.fileName}" alt="${grade.levelName}"  style="width:40px; height: 40px;"></span>
							<p class="grade_name">${grade.levelName}</p>
	 					</td>
	 					<td>
	 						<p class="">구매금액  ${op:numberFormat(grade.priceEnd)}만원 미만</p>
	 						<p class="std">(등업일로부터 ${grade.referencePeriod}개월 기준)</p>
	 					</td>
	 					<td class="act">${op:numberFormat(grade.pointRate)} %</td>
	 					<td class="act">${op:numberFormat(grade.discountRate)} %</td>
	 					<td class="act">${op:numberFormat(grade.shippingCouponCount)} 장</td>
	 				</tr>
	 				</c:forEach>
	 			</tbody>
	 		</table>
		</div>
		<div class="card_desc note">
			<dl>
				<dt>회원 등급 선정 기준 및 혜택 이용안내</dt>
				<dd>회원등급은 등급산정일로부터 6개월간의 구매확정 된 구매실적을 기준으로 산정됩니다.</dd>
				<dd>구매실적은 쿠폰, ${op:message('M00246')} 등을 할인적용한 후의 최종 결제 금액을 기준으로 산정됩니다.</dd>
				<dd>적립은 '상품 적립', '등급 별 추가 적립'순으로 적립됩니다.</dd>
				<dd>쿠폰은 등급 변경 후 매월 1일 00:00 시에 적립됩니다.</dd>
				<dd>회원 등급, 쿠폰, ${op:message('M00246')} 이용에 대한 궁금하신 사항은 고객센터의 FAQ를 참고해 주시기 바랍니다.</dd>
			</dl>
		</div><!--//card_desc E-->

		
	</div><!--//popup_contents E-->

	<a href="javascript:self.close();" class="popup_close">창 닫기</a>
</div><!-- //popup_wrap E -->

