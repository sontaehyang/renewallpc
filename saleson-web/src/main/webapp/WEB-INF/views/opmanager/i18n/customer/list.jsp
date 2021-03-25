<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<div class="location">
	<a href="#"></a>&gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
</div>

<!-- 거래처관리 -->
<h3>거래처 관리</h3>
<form:form modelAttribute="customerParam" action="" method="post">
	<div class="board_write">
		<table class="board_write_table" summary="${ title }">
			<caption>${ title }</caption>
			<colgroup>
				<col style="width:150px;" />
				<col style="width:*;" />
			</colgroup>
			<tbody>
				 <tr>
				 	<td class="label">${op:message('M00011')} <!-- 검색구분 --> </td>
				 	<td>
				 		<div>
							<form:select path="where" title="${op:message('M00011')}">
								<form:option value="CUSTOMER_NAME" label="거래처명" />
								<form:option value="BUSINESS_NUMBER" label="사업자번호" />
								<form:option value="TEL_NUMBER" label="전화번호" />
							</form:select> 
							<form:input path="query" class="input_txt required _filter" title="${op:message('M00022')}" maxlength="20" /><!-- 검색어 -->
						</div>
				 	</td>
				 </tr>
				 <tr>  
				 	<td class="label">등록일자</td> 
				 	<td>
				 		<div>
				 			<span class="datepicker"><form:input path="searchStartDate" class="datepicker" maxlength="8" title="시작일자" /></span>
				 			<form:select path="searchStartDateTime">
								<form:option value="00" label="00시" />
								<c:forEach varStatus="i" begin="1" end="23">
									<c:if test="${i.count < 10 }">
										<form:option value="0${i.count}" label="0${i.count}시" />
									</c:if>
									<c:if test="${i.count >= 10 }">
										<form:option value="${i.count}" label="${i.count}시" />
									</c:if>
								</c:forEach>
							</form:select>
							<span class="wave">~</span>
							<span class="datepicker"><form:input path="searchEndDate" class="datepicker" maxlength="8" title="종료일자" /></span>
							<form:select path="searchEndDateTime">
								<form:option value="00" label="00시" />
								<c:forEach varStatus="i" begin="1" end="23">
									<c:if test="${i.count < 10 }">
										<form:option value="0${i.count}" label="0${i.count}시" />
									</c:if>
									<c:if test="${i.count >= 10 }">
										<form:option value="${i.count}" label="${i.count}시" />
									</c:if>
								</c:forEach>
							</form:select>
							
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
				 	<td class="label">새로 추가된 거래처</td>
				 	<td>
				 		<div>
				 			<form:checkbox path="newCustomerFlag" value="Y" label="전일 15:01 부터 현재"/>
						</div>
				 	</td>
				 </tr>
			</tbody>					 
		</table>				 							
	</div> <!-- // board_write -->
	
	<!-- 버튼시작 -->	
	<div class="btn_all">
		<div class="btn_left">
			<button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='/opmanager/customer/list'"><span>${op:message('M00047')}<!-- 초기화 --></span></button>
		</div> 
		<div class="btn_right">
			<button type="submit" class="btn btn-dark-gray btn-sm"><span>${op:message('M00048')}<!-- 검색 --></span></button>
		</div>
	</div>		 
	<!-- 버튼 끝-->
	
</form:form>

<div class="board_list">
	<form:form modelAttribute="customerParam" action="/opmanager/customer/list" method="post">
		<table class="board_list_table" summary="">
			<caption>${op:message('M00458')}</caption>
			<thead>
				<tr>
					<th scope="col">순번</th>
					<th scope="col">거래처코드</th>  
					<th scope="col">거래처명</th>	
					<th scope="col">거래처분류</th> 
					<th scope="col">거래처구분</th> 
					<th scope="col">사업자번호</th>  
					<th scope="col">전화번호</th>
				</tr>
			</thead>
			<c:forEach items="${customerlist}" var="list" varStatus="i">
				<tr>
					<td>${pagination.itemNumber - i.count}</td>
					<td>
						<div>
							<a href="/opmanager/customer/edit/${list.customerCode}">${list.customerCode}</a>
						</div>	
					</td>
					<td>
						<div>
							${list.customerName}
						</div>	
					</td>
					<td>
						<div>
							${list.customerGroup}
						</div>	
					</td>
					<td>
						<div>
							${list.customerType}
						</div>	
					</td>
					<td>
						<div>
							${list.businessNumber}
						</div>	
					</td>
					<td>
						<div>
							${list.telNumber}
						</div>	
					</td>
					
				</tr>	
			</c:forEach>	
		</table>
		
		<c:if test="${empty customerlist}">
		<div class="no_content">
			${op:message('M00473')} <!-- 데이터가 없습니다. --> 
		</div>
		</c:if>
		
		<div class="btn_all">
			<div class="btn_right">
				<a href="/opmanager/customer/download-excel2" class="btn_write gray_small"><img src="/content/opmanager/images/icon/icon_excel.png" alt=""><span>${op:message('M00254')}</span> </a> <!-- 엑셀 다운로드 -->
				<a href="/opmanager/customer/download-excel" class="btn_write gray_small"><img src="/content/opmanager/images/icon/icon_excel.png" alt=""><span>ERP 등록용 엑셀 다운로드</span> </a> <!-- 엑셀 다운로드 -->
				<!-- a href="javascript:uploadExcel()" class="btn_write gray_small"><img src="/content/opmanager/images/icon/icon_excel.png" alt=""><span>${op:message('M00793')}</span> </a --> <!-- 엑셀 업로드 -->
				<a href="/opmanager/customer/create" class="btn_write gray_small"><span>등록</span></a>
			</div>
		</div>
		<page:pagination-manager />
	</form:form>
</div>

		
<script type="text/javascript">
$(function() {
	
	Common.DateButtonEvent.set('.day_btns > a[class^=btn_date]', '', 'input[name="searchStartDate"]' , 'input[name="searchEndDate"]');
});
//엑셀 업로드
function uploadExcel() {
	Common.popup('/opmanager/customer/upload-excel', 'upload-excel', 600, 550, 0);
}
</script>
			