<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>


<h3><span>${op:message('M00261')}</span></h3>

<div class="board_write">
	<table class="board_list_table">
		<colgroup>
			<col style="width:60px" />

		</colgroup>
		<thead>
		<tr>
			<th scope="col">레벨</th>
			<th scope="col">${op:message('M00262')}</th>
			<th scope="col">${op:message('M00263')}</th>
			<th scope="col">${op:message('M00264')}</th>
			<th scope="col">등급 심사 주기</th>
			<th scope="col">매출 기준기간</th>
			<th scope="col">매출 제외 기준 일자</th>
			<th scope="col">구매시 추가 적립 포인트</th>
			<th scope="col">구매시 추가 할인율</th>
			<th scope="col">배송비 쿠폰 발행수(월별)</th>
			<th scope="col">회원 수</th>
			<th scope="col">${op:message('M00168')}</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${list}" var="userLevel" varStatus="i">
			<tr>
				<td>${userLevel.depth}</td>
				<td>
					<div>
						<c:choose>
							<c:when test="${!empty userLevel.fileName}">
								<img src="${requestContext.uploadBaseFolder}/user_level/${userLevel.levelId}/${userLevel.fileName}" alt="${userLevel.fileName}" style="width: 60px;" />
							</c:when>
							<c:otherwise>
								<img src="/content/opmanager/images/icon/icon_family.png" alt="${op:message('M00213')}" style="width: 60px;" />
							</c:otherwise>
						</c:choose>
					</div>
				</td>
				<td>${userLevel.levelName}</td>
				<td>${op:numberFormat(userLevel.priceStart)}원 이상 ~ ${op:numberFormat(userLevel.priceEnd)}원 미만</td>
				<td>${userLevel.retentionPeriod}개월</td>
				<td>${userLevel.referencePeriod}개월</td>
				<td>${userLevel.exceptReferencePeriod}일</td>
				<td>${userLevel.pointRate}%</td>
				<td>${userLevel.discountRate}%</td>
				<td>${userLevel.shippingCouponCount}장</td>
				<td>${op:numberFormat(userLevel.userCount)}명</td>
				<td>
					<div>
						<a href="javascript:userLevelEdit('${userLevel.levelId}');" class="btn btn-gradient btn-xs">${op:message('M00087')}</a>
						<a href="javascript:userLevelDelete('${userLevel.levelId}');" class="btn btn-gradient btn-xs">${op:message('M00074')}</a>
					</div>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<c:if test="${ empty list }">
		<div class="no_content">
			<p>${op:message('M00170')} </p>
		</div>
	</c:if>
</div><!--//board_write E-->

<div class="btn_all">
	<div class="btn_left">

	</div>
	<div class="btn_right">
		<a href="/opmanager/user-level/create/${userLevelSearchParam.groupCode}" class="btn btn-active btn-sm"><span class="glyphicon glyphicon-plus"></span> ${op:message('M00266')}</a>
	</div>
</div>


<div class="text-info">
	* 회원 등급은 매일 새벽에 재산정 됩니다. (대상 : 회원 등급 유지 기간이 끝난 회원)<br />
	* 회원 구매 금액이 여러 등급에 해당하는 경우 레벨이 높은 등급으로 선정됩니다.
</div>


<script type="text/javascript">
	function userLevelEdit(levelId){
		location.href = '/opmanager/user-level/edit/'+levelId;
	}

	function userLevelDelete(levelId){
		if (confirm('삭제하시겠습니까?')) {
			location.href = '/opmanager/user-level/delete/'+levelId;
		}
	}
</script>			
			