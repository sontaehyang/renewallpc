<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>



<h1 class="popup_title">${statisticsParam.dodobuhyun} ${op:message('M01354')}${op:message('M01296')} </h1>  <!-- 지역별 매출 상세 --> <!-- 팝업 -->
<div class="popup_contents">
	 <h3><span>${statisticsParam.dodobuhyun} ${op:message('M01354')}</span></h3> <!-- 지역별 매출 상세 -->
	<form:form modelAttribute="statisticsParam" method="get">
		<form:hidden path="sidoMappingGroupKey" />
		<form:hidden path="dodobuhyun" />
		<div class="board_write">						
			<table class="board_write_table" summary="${statisticsParam.dodobuhyun} ${op:message('M01354')}">
				<caption>${statisticsParam.dodobuhyun} ${op:message('M01354')}</caption>
				<colgroup>
					<col style="width: 120px;">
					<col style="width: auto;"> 
				</colgroup>
				<tbody>
					<tr>
						<td class="label">${op:message('M01347')}</td> <!-- 기간 -->
						<td>
					 		<div>
								<span class="datepicker"><form:input path="startDate" class="term datepicker" title="${op:message('M00507')}" id="dp28" /></span> <!-- 시작일 -->
								<span class="wave">~</span>
								<span class="datepicker"><form:input path="endDate" class="term datepicker" title="${op:message('M00509')}" id="dp29" /></span> <!-- 종료일 -->
									<span class="day_btns"> 
										<a href="javascript:;" class="btn_date today">${op:message('M00026')}</a><!-- 오늘 --> 
										<a href="javascript:;" class="btn_date week-1">${op:message('M00027')}</a><!-- 1주일 --> 
										<a href="javascript:;" class="btn_date day-15">${op:message('M00028')}</a><!-- 15일 --> 
										<a href="javascript:;" class="btn_date month-1">${op:message('M00029')}</a><!-- 한달 -->
										<a href="javascript:;" class="btn_date month-3">${op:message('M00030')}</a><!-- 3개월 --> 
										<a href="javascript:;" class="btn_date year-1">${op:message('M00031')}</a><!-- 1년 -->
									</span>
							</div> 
				 		</td>
					</tr>
					<tr>
						<td class="label">판매자</td>
						<td>
							<div>
								<form:select path="sellerId">
									<form:option value="0">${op:message('M00039')}</form:option>
									<c:forEach items="${sellerList}" var="list" varStatus="i">
										<form:option value="${list.sellerId}">[${list.loginId}] ${list.sellerName}</form:option>
									</c:forEach>
								</form:select>&nbsp;
								<a href="javascript:Common.popup('/opmanager/seller/find', 'find_seller', 800, 500, 1)" class="btn btn-dark-gray btn-sm"> <span class="glyphicon glyphicon-search"></span> 검색</a>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
			
		</div>
		
		<div class="btn_all">
			<!-- <div class="btn_left">
				<button type="button" class="btn btn-dark-gray btn-sm"><span>초기화</span></button>
			</div> -->
			<div class="btn_right">
				<button type="submit" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> ${op:message('M00048')}</button> <!-- 검색 -->
				<a href="/opmanager/shop-statistics/sales/area/detail/excel-download?sidoMappingGroupKey=${statisticsParam.sidoMappingGroupKey}&dodobuhyun=${statisticsParam.dodobuhyun}&startDate=${statisticsParam.startDate}&endDate=${statisticsParam.endDate}"  class="btn btn-success btn-sm"><span class="glyphicon glyphicon-save"></span>${op:message('M00254')}</a> <!-- 엑셀 다운로드 -->
			</div>
		</div>
	</form:form>
	
	<div class="board_guide">
		<br/>
		<p class="tip">[안내]</p>
		<p class="tip">
			비회원(거래처)는 전화번호, 웹회원은 아이디(이메일)로 구분되어 있습니다.
		</p>  
	</div>
	
	<div class="board_list" style="overflow: hidden; float: left; width: 100%;">
		
		
		<c:set var="payCount">0</c:set>
		<c:set var="cancelCount">0</c:set>
		
		<c:forEach items="${areaList }" var="list" varStatus="i">
			<c:if test="${list.orderType eq 'PAY' }">
				<c:set var="payCount">${payCount+1 }</c:set> 
			</c:if>
			<c:if test="${list.orderType eq 'CANCEL' }">
				<c:set var="cancelCount">${cancelCount+1 }</c:set> 
			</c:if>
		
		</c:forEach>
		
		
		<table class="board_list_table" style="width: 50%; float: left;">
			<thead>
				<tr>
					<th colspan="3" >${op:message('M01355')}</th> <!-- 결제 -->
				</tr>
				<tr>
					<th>${op:message('M01356')} <br/>(${op:message('M00081')})</th> <!-- 회원이름 --> <!-- 아이디 -->
					<th class="border_left number">${op:message('M01357')}</th> <!-- 건수 -->
					<th class="border_left number">${op:message('M01358')}</th> <!-- 매출액 -->
				</tr>
			</thead>
			<tbody>
				<c:set var="z">1</c:set>
				<c:forEach items="${areaList}" var="list" varStatus="i">
					<c:if test="${list.orderType eq 'PAY' }">
						<tr>
							<td>
								<c:choose>
									<c:when test="${not empty list.loginId}">
										<a href="/opmanager/user/popup/details/${list.userId}">
											${list.userName}(웹회원)<br/>(${list.loginId})
										</a>
									</c:when>
									<c:otherwise>
										${list.userName}(거래처)
										<c:if test="${!empty list.telNumber}">
											<br/>(${list.telNumber})
										</c:if>
									</c:otherwise>
								</c:choose>
							</td>
							<td class="border_left number">${op:numberFormat(list.quantity)}</td>
							<td class="border_left number">${op:numberFormat(list.itemPrice)}</td>
						</tr> 
					</c:if>
				</c:forEach>
				<c:if test="${payCount < cancelCount }">
				<c:forEach begin="1" end="${cancelCount-payCount}">
					<tr>
						<td class="border_left">&nbsp; <br />&nbsp;</td>
						<td class="border_left">&nbsp;</td>
						<td class="border_left">&nbsp;</td>
					</tr>
				</c:forEach>
				</c:if>
				
			</tbody>
		</table>
		
		<table class="board_list_table" style="width: 50%; float: left;">
			<thead>
				<tr>
					<th colspan="3" class="border_left">취소</th>
				</tr>
				<tr>
					<th class="border_left">${op:message('M01356')} <br/>(${op:message('M00081')})</th> <!-- 회원이름 --> <!-- 아이디 -->
					<th class="border_left number">${op:message('M01357')}</th> <!-- 건수 -->
					<th class="border_left number">${op:message('M01361')}</th> <!-- 취소액 -->
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${areaList}" var="list" varStatus="j">
					<c:if test="${list.orderType eq 'CANCEL' }">
						<tr>
							<td class="border_left">
								<c:choose>
									<c:when test="${not empty list.loginId}">
										<a href="/opmanager/user/popup/details/${list.userId}">
											${list.userName}(웹회원)<br/>(${list.loginId})
										</a>
									</c:when>
									<c:otherwise>
										${list.userName}(거래처)
										<c:if test="${!empty list.telNumber}">
											<br/>(${list.telNumber})
										</c:if>
									</c:otherwise>
								</c:choose>
							</td>
							<td class="border_left number">${op:numberFormat(list.quantity)}</td>
							<td class="border_left number">${op:numberFormat(list.itemPrice)}</td>
						</tr>
						
					</c:if>
				</c:forEach>
				<c:if test="${payCount > cancelCount }">
				<c:forEach begin="1" end="${payCount - cancelCount}">
					<tr>
						<td class="border_left">&nbsp; <br />&nbsp;</td>
						<td class="border_left">&nbsp;</td>
						<td class="border_left">&nbsp;</td>
					</tr>
				</c:forEach>
				</c:if>
			</tbody>
		</table>
		
		<c:if test="${empty areaList }">
			<div class="no_content" style="margin-top:100px;">
				<p>
					${op:message('M00591')} <!-- 등록된 데이터가 없습니다. -->
				</p>
			</div>
		</c:if>
	</div>
</div><!--//popup_contents E-->
	<a href="#" class="popup_close">${op:message('M00353')}</a><!-- 창 닫기 -->
</div>


<script type="text/javascript">
	$(function(){
		Common.DateButtonEvent.set('.day_btns > a[class^=btn_date]', '', 'input[name="startDate"]' , 'input[name="endDate"]');
	});
	
	function sellerSeller(sellerId) {
		$('#sellerId').val(sellerId)
	}
</script>