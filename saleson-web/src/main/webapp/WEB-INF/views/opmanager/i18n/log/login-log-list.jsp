<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>

<div class="location">
	<a href="#"></a>&gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
</div>

<h3><span>로그인 통계</span></h3>

<form:form modelAttribute="loginLogParam" method="get">
	<div class="board_write">
		<table class="board_write_table">
			<caption>로그인 통계</caption>
			<colgroup>
				<col style="width:150px;" />
					<col style="width:auto;" />
			</colgroup>
			<tbody>
				<tr>
				 	<td class="label">로그인 일자</td> 
				 	<td>
				 		<div>
							<span class="datepicker"><form:input path="searchStartDate" maxlength="8" class="datepicker" title="${op:message('M00024')}" /><!-- 주문일자 시작일 --></span>
							<span class="wave">~</span>
							<span class="datepicker"><form:input path="searchEndDate" maxlength="8" class="datepicker" title="${op:message('M00025')}" /><!-- 주문일자 종료일 --></span>
							<span class="day_btns">
								<a href="javascript:;" class="btn_date clear">전체</a> 
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
				 	<td class="label">로그인 타입</td> 
				 	<td>
				 		<div>
				 			<form:select path="loginType">
								<form:option value="" label="전체" />
								<form:option value="manager" label="관리자" />
								<form:option value="seller" label="판매자" />
							</form:select>
						</div>
					</td>							
				 </tr>
				 <tr>
				 	<td class="label">로그인 성공여부</td> 
				 	<td>
				 		<div>
				 			<form:select path="successFlag">
								<form:option value="" label="전체" />
								<form:option value="Y" label="로그인 성공" />
								<form:option value="N" label="로그인 실패" />
							</form:select>
						</div>
					</td>
				 </tr>
				 <tr>
				 	<td class="label">로그인 ID</td> 
				 	<td>
				 		<div>
				 			<form:input path="loginId" maxlength="60" class="w38" title="로그인 ID"/>
						</div>
					</td>
				 </tr>						 
			</tbody>					 
		</table>								 							
	</div> <!-- // board_write -->
	<div class="btn_all">
		<div class="btn_left">
			<button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='/opmanager/log/login-log'"><span class="glyphicon glyphicon-repeat"></span> ${op:message('M00047')}</button> <!-- 초기화 -->
		</div>
		<div class="btn_right">
			<button type="submit" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> ${op:message('M00048')}</button> <!-- 검색 -->
		</div>
	</div>
	
	<div class="count_title mt20">
		<h5>
			${op:message('M00039')} : ${op:numberFormat(pagination.totalItems)} ${op:message('M00743')}
		</h5>	 <!-- 전체 -->   <!-- 건 조회 --> 
		<span>
			<span>${op:message('M00052')} <!-- 출력수 --></span>
			<form:select path="itemsPerPage" title="출력수 선택">
				<form:option value="10" label="10" />
				<form:option value="20" label="20" />
				<form:option value="30" label="30" />
				<form:option value="50" label="50" />
				<form:option value="100" label="100" />
				<form:option value="500" label="500" />
				<form:option value="1000" label="1000" />
			</form:select>
		</span>
	</div>		
</form:form>

<div class="board_write">
	<table class="board_list_table" summary="로그인 로그 리스트">
		<caption>로그인 로그 리스트</caption>
		<colgroup>
			<col style="width:150px;" />
			<col style="width:100px;" />				
			<col style="width:300px;" />
			<col style="width:150px;" />
			<col style="width:150px;" />
			<col style="width:auto;" />
		</colgroup>
		<thead>
			<tr>
				<th scope="col">로그인 일자</th>
				<th scope="col">로그인 타입</th>				
				<th scope="col">로그인 ID</th> 
				<th scope="col">로그인 성공유무</th>
				<th scope="col">로그인 IP</th>
				<th scope="col">비고</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list}" var="list" varStatus="i" >
				<tr>
					<td>
						<div>
							${op:datetime(list.loginDate) }
						</div>
					</td>
					<td>
						<div>
							${list.loginType == "manager" ? "관리자" : list.loginType == "seller" ? "판매자" : list.loginType }
						</div>
					</td>
					<td>
						<div>
							${list.loginId }
						</div>
					</td>
					<td>
						<div>
							${list.successFlag == "Y" ? "성공" : "실패" }
						</div>
					</td>
					<td>
						<div>
							${list.remoteAddr }
						</div>
					</td>
					<td>
						<div>
							${list.memo }
						</div>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>				 
</div><!--// board_write E-->

<c:if test="${empty list}">
	<div class="no_content">
		${op:message('M00473')} <!-- 데이터가 없습니다. -->
	</div>
</c:if>

<page:pagination-manager />


<script type="text/javascript">
	$(function(){
		Common.DateButtonEvent.set('.day_btns > a[class^=btn_date]', '', 'input[name="searchStartDate"]' , 'input[name="searchEndDate"]');
	});
</script>