<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>


<div class="location">
	<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
</div>
<!-- 본문 -->


<!--전체주문 내역 시작-->
<h3><span>${ title }<!-- 전체주문 내역 --></span></h3>

<form:form name="mallConfigParam" modelAttribute="mallConfigParam" action="/opmanager/mall/collect/action" method="post">
	<div class="board_write">
	
		<table class="board_write_table">
			<colgroup>
				<col style="width:150px;" />
				<col style="width:*;" />
			</colgroup>
			<tbody>
				 <tr>  
				 	<td class="label">${op:message('M00023')}</td><!-- 주문일자 --> 
				 	<td>
				 		<div>
							<span class="datepicker"><form:input path="searchStartDate" class="datepicker" maxlength="8" title="${op:message('M00024')}" /><!-- 주문일자 시작일 --></span>
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
							<span class="datepicker"><form:input path="searchEndDate" class="datepicker" maxlength="8" title="${op:message('M00025')}" /><!-- 주문일자 종료일 --></span>
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
								<a href="javascript:;" class="btn_date day-1" onclick='setYesterDayTimeToCurrentTime("15")'>전일 15시부터 현재까지</a><!-- 1년 -->
							</span>
						</div> 
				 	</td>	
				 </tr>
				 <tr>
				 	<td class="label">수집 대상</td>
				 	<td>
				 		<div>
					 		<form:checkbox path="collectTargetDefault" value="1" label=" 일반주문" />
					 		<form:checkbox path="collectTargetClaim" value="1" label=" 클레임" />
				 		</div>
				 	</td> 
				 </tr>
			</tbody>					  
		</table>
									 							
	</div> <!-- // board_write -->
	<div class="btn_all">
		<div class="btn_left">

		</div> 
		<div class="btn_right">
			<button type="submit" class="btn btn-dark-gray btn-sm"><span>수집 시작</span></button>
		</div>
	</div>  


	<div class="board_list">
		<div class="btn_all">
			<div class="btn_left mb0">
				
			</div>
			<div class="btn_right mb0">
				 
			</div>
		</div>
		<form id="listForm">
			<table class="board_list_table" summary="주문내역 리스트">
				<caption>주문내역 리스트</caption>
				<colgroup>
					<col style="width:2%;" />
					<col style="width:5%;" />
					<col style="width:8%;" />
					<col style="width:8%;" />
					<col style="width:12%;" />
					<col />
					<col style="width:8%;" />
					<col style="width:12%;" />
					<col />
					<col style="width:8%;" />
				</colgroup>
				<thead>
					<tr>
						<th scope="col" rowspan="2"><input type="checkbox" id="check_all" /></th>
						<th scope="col" rowspan="2">Mall 타입</th>
						<th scope="col" rowspan="2">Mall Login Id</th>
						<th scope="col" colspan="3">일반주문</th>
						<th scope="col" colspan="3">클레임</th>
						<th scope="col" rowspan="2">거래 상태</th>
					</tr>
					<tr>
						<th scope="col">수집 결과</th>
						<th scope="col">최근 수집일자</th>
						<th scope="col">최근 수집 검색 범위</th>
						<th scope="col">수집 결과</th>
						<th scope="col">최근 수집일자</th>
						<th scope="col">최근 수집 검색 범위</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${ list }" var="config" varStatus="index">
						<tr>
							<td><input type="checkbox" name="id" value="${config.mallConfigId}" /></td>
							<td>${config.mallType}</td>
							<td>${config.mallLoginId}</td>
							<td>${config.collectStatusCodeLabel}</td>
							<td>${op:datetime(config.lastDate)}</td>
							<td>${op:datetime(config.lastSearchStartDate)}~${op:datetime(config.lastSearchEndDate)}</td>
							<td>${config.claimCollectStatusCodeLabel}</td>
							<td>${op:datetime(config.lastClaimDate)}</td>
							<td>${op:datetime(config.lastClaimSearchStartDate)}~${op:datetime(config.lastClaimSearchEndDate)}</td>
							<td>${config.dataStatusCodeLabel}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</form>
		
		<c:if test="${empty list}">
		<div class="no_content">
			${op:message('M00473')} <!-- 데이터가 없습니다. --> 
		</div>
		</c:if>	
		
		<div class="btn_all">
			<div class="btn_left mb0">
	
			</div>
			<div class="btn_right mb0">
				 
			</div>
		</div> 
	
	</div> 

</form:form>
<script type="text/javascript"> 
	$(function(){
		Common.DateButtonEvent.set('.day_btns > a[class^=btn_date]', '', 'input[name="searchStartDate"]' , 'input[name="searchEndDate"]');
		
		$("#mallConfigParam").validator({
			'requiredClass' : 'required',
			'submitHandler' : function() {
				var $form = $('#mallConfigParam');
				if ($form.find('input[name="collectTargetDefault"]').prop('checked') == false && $form.find('input[name="collectTargetClaim"]').prop('checked') == false) {
					alert('수집할 주문타입을 1개 이상 선택해 주세요.');
					return false;
				}
				
				if ($form.find('input[name=id]:checked').size() == 0) {
					alert(Message.get("M00308"));	// 처리할 항목을 선택해 주세요.
					return false;
				}
				
				
			}
		});
	});
</script>