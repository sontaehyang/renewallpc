<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>

<div class="location">
	<a href="#">통계</a> &gt;  <a href="#">판매율통계</a> &gt; <a href="#" class="on">상품별 집계</a> 
</div>
<div class="statistics_web">
	<h3><span>상품별 집계</span></h3>
	<form:form modelAttribute="statisticsParam" method="get" >
		<div class="board_write">						
			<table class="board_write_table" summary="${op:message('M01346')}">
				<caption>${op:message('M01346')}</caption><!-- 상품별 집계 -->
				<colgroup>
					<col style="width: 20%;">
					<col style="width: auto;"> 
				</colgroup>
				<tbody>
					<tr>
						<td class="label">${op:message('M01347')}</td><!-- 기간 -->
						<td>
					 		<div> 
								<span class="datepicker"><form:input path="startDate" class="term datepicker required" title="${op:message('M00507')}" id="dp28" /></span> <!-- 시작일 -->
								<span class="wave">~</span>
								<span class="datepicker"><form:input path="endDate" class="term datepicker required" title="${op:message('M00509')}" id="dp29" /></span>  <!-- 종료일 -->
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
						<td class="label">${op:message('M01353')}</td> <!-- 카테고리선택 -->
						<td >
							<div>
								<form:select path="code" >
									<option value="">= ${op:message('M00585')} =</option> <!-- 팀/그룹 -->
									<c:forEach items="${categoryTeamGroupList}" var="categoriesTeam">
										<c:if test="${categoriesTeam.categoryTeamFlag == 'Y'}">
											<form:option value="${categoriesTeam.categoryTeamId}" label="${categoriesTeam.name}" />
										</c:if>
									</c:forEach>
								</form:select>
								
								<form:select path="categoryGroupId" class="category">
								</form:select>
								
								<form:select path="categoryClass1" class="category">
								</form:select>
								
								<form:select path="categoryClass2" class="category">
								</form:select>
								
								<form:select path="categoryClass3" class="category">
								</form:select>
								
								<form:select path="categoryClass4" class="category">
								</form:select>
							</div>
						</td>
					</tr>
					<tr>
						<td class="label">${op:message('M01348')} </td><!-- 출력 유형 -->
						<td>
							<div>
								<form:radiobutton path="type" label="${op:message('M01378')}" value="1" class="required" title="${op:message('M01348')}" /> <!-- 기간 총 --> <!-- 출력 유형 -->
								<form:radiobutton path="type" label="${op:message('M01379')}" value="2" class="required" title="${op:message('M01348')}" /> <!-- 월별 --> <!-- 출력 유형 -->
								<form:radiobutton path="type" label="${op:message('M01380')}" value="3" class="required" title="${op:message('M01348')}" /> :  <!-- 매주 집계 -->  <!-- 출력 유형 -->
								<form:select path="weekType" class="required" title="${op:message('M01348')} ${op:message('M01349')}">
									<c:forEach items="${weekList}" var="list">
										<option value="${list.value}" ${op:selected(list.value,statisticsParam.weekType) }>${list.week}</option>
									</c:forEach>
								</form:select> ${op:message('M01350')} <!-- 요일 시작 --> &nbsp;
								<form:radiobutton path="type" label="${op:message('M01381')}" value="4" class="required" title="${op:message('M01348')}" /> <!-- 매일 -->
							</div>
						</td>
					</tr>
					<tr>
						<td class="label">${op:message('M01185')}</td><!-- 상품 검색 -->
						<td>
							<div>
								<form:select path="where" title="상품명 선택">
									<form:option value="ITEM_NAME">${op:message('M00018')}</form:option> <!-- 상품명 -->
									<form:option value="ITEM_USER_CODE">${op:message('M00783')} </form:option><!-- 상품코드 -->
								</form:select>
								<form:input path="query" class="three" title="상세검색 입력" />
							</div>
						</td>
					</tr>
				</tbody>
			</table>
			
		</div> <!-- // board_write -->
		
		
		
		<div class="btn_all">
			<!-- <div class="btn_left">
				<button type="button" class="btn btn-dark-gray btn-sm"><span>초기화</span></button>
			</div> -->
			<div class="btn_right">
				<button type="submit" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> ${op:message('M00048')}</button><!-- 검색 -->
			</div>
		</div>
	
	</form:form>
	
	<div class="sort_area mt30">
	</div>
	
	<div class="board_list" style="overflow-y: auto;">
		
		<!-- 판매율별 -->
		<table class="board_list_table">
			<caption>상품별 판매율</caption>
			
			<thead>
				<tr>
					<th class="border_left">${op:message('M00018')}</th><!-- 상품명 -->
					<th class="border_left">${op:message('M00585')}</th><!-- 팀 -->
					<th class="border_left">${op:message('M00586')}</th> <!-- 그룹 -->
					<th class="border_left">${op:message('M01351')}</th> <!-- 그룹 이름 -->
					<th class="border_left">${op:message('M00270')}</th> <!-- 카테고리 -->
					<th class="border_left">${op:message('M01352')}</th> <!-- 카테고리 이름 -->
					<c:forEach items="${hedarList }" var="list">
						${list}
					</c:forEach>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${itemDateList}" var="list">
					<tr>
						<td style="text-align: left;">
							<img src="${list.itemImage}" class="item_image" alt="상품이미지"  />
							${list.itemName } (${list.itemUserCode })
						</td>
						<td class="border_left">
							${list.code}
						</td>
						<td class="border_left">
							${list.groupCode}
						</td>
						<td class="border_left">
							${list.groupName}
						</td>
						<td class="border_left">
							${list.categoryCode}
						</td>
						<td class="border_left">
							${list.categoryName}
						</td>
						<c:forEach items="${list.dateList2}" var="list2">
							<td class="border_left" style="text-align: right;">${op:numberFormat(list2.webPriceTotal)}円 <br />${op:numberFormat(list2.webPayCount)}個</td>
						</c:forEach>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<!-- // 판매율별 -->
		<c:if test="${empty itemDateList}">
			<div class="no_content">
				<p>
					${op:message('M00591')} <!-- 등록된 데이터가 없습니다. -->
				</p>
			</div>
		</c:if>
		
		<div class="sort_area">
			<div class="right">
				<a href="/opmanager/shop-statistics/sales/item/day-excel-download?${queryString}" class="btn btn-success btn-sm"><span class="glyphicon glyphicon-save"></span> ${op:message('M00254')}</a> <!-- 엑셀 다운로드 -->
			</div>
		</div>
		
		<page:pagination-manager />
		
		<!-- <div class="board_guide">
			<p class="tip">Tip</p>
			<p class="tip">- 상품명을 클릭하시면 상세내역을 조회하실 수 있습니다.</p>
				<p class="tip">- 조회기간은 3개월까지만 가능합니다.</p>
		</div> -->
		
	</div>
	
</div>

<script type="text/javascript">
	$(function(){
		
		$("#statisticsParam").validator();
		
		ShopEventHandler.categorySelectboxChagneEvent2();  
		Shop.activeCategoryClass2('${statisticsParam.code}','${statisticsParam.categoryGroupId}', '${statisticsParam.categoryClass1}', '${statisticsParam.categoryClass2}', '${statisticsParam.categoryClass3}', '${statisticsParam.categoryClass4}');
		Common.DateButtonEvent.set('.day_btns > a[class^=btn_date]', '', 'input[name="startDate"]' , 'input[name="endDate"]');
		
	});
</script>