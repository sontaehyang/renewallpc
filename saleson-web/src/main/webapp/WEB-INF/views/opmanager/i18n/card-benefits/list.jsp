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
					
		<h3>카드혜택 관리</h3>
		<form:form modelAttribute="CardBenefitsParam" method="get">
			<div class="board_write">
				<table class="board_write_table">
					<caption>${op:message('M00458')}</caption>
					<colgroup>
						<col style="width:150px;" />
						<col style="width:*;" />
					</colgroup>
					<tbody>
						
						<%-- <tr>	
							<div>
							 	<td class="label">게시 여부</td>
							 	<td>
							 		<div>
							 			<form:radiobutton path="answerFlag" value="3" checked="checked" label="${op:message('M00039')}" />
										<form:radiobutton path="answerFlag" value="1" label="${op:message('M00463')}" /> 
										<form:radiobutton path="answerFlag" value="0" label="${op:message('M00464')}" /> 
							 		</div>
							 	</td>
							 </div>		
						</tr> --%>
						 
						<tr>
							<div>
							 	<td class="label">게시 기간</td> <!-- 작성일 --> 
							 	<td>
							 		<div>
										<span class="datepicker"><form:input type="text" path="startDate" class="datepicker _number" title="${op:message('M00507')}" /></span> <!-- 시작일 --> 
										<span class="wave">~</span>
										<span class="datepicker"><form:input type="text" path="endDate" class="datepicker _number" title="${op:message('M00509')}" /></span> <!-- 종료일 --> 
										<span class="day_btns"> 
											<a href="#" class="btn_date all">${op:message('M00039')}</a> 
										</span>
									</div> 
							 	</td>
							 </div>		
						 </tr>
						 
						 <tr>
						 	<td class="label">키워드검색</td> <!-- 검색구분 --> 
						 	<td>
						 		<div>
									<form:select path="where" title="${op:message('M00468')}"> <!-- 키워드선택 --> 
										<form:option value="subject" label="제목" /> <!-- 제목 -->
									</form:select> 
									<form:input type="text" path="query" class="three" title="${op:message('M00021')} " />  <!-- 검색어 입력 -->
								</div>
						 	</td>	
						 </tr>
					</tbody>					 
				</table>								 							
			</div> <!-- // board_write -->
		
		<!-- 버튼시작 -->	
			<div class="btn_all">
				<div class="btn_left">
					<button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='/opmanager/card-benefits/list'"><span class="glyphicon glyphicon-repeat"></span> ${op:message('M00047')}</button> <!-- 초기화 -->
				</div>
				<div class="btn_right">
					<button type="submit" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> ${op:message('M00048')}</button> <!-- 검색 -->
				</div>
			</div>		 
		<!-- 버튼 끝-->
			<div class="count_title mt20">
				<h5>전체 : ${benefitsCount} 건 조회</h5>
				<span>출력수
					<form:select path="itemsPerPage" title="${op:message('M00239')}"> <!-- 화면출력 -->
						<form:option value="10" label="${op:message('M00240')}" />  <!-- 10개 출력 --> 
						<form:option value="20" label="${op:message('M00241')}" />  <!-- 20개 출력 -->
						<form:option value="50" label="${op:message('M00242')}" />  <!-- 50개 출력 --> 
						<form:option value="100" label="${op:message('M00243')}" /> <!-- 100개 출력 --> 
					</form:select>
				</span>
			</div>
		</form:form>
 
		<form action="/opmanager/benefits/list" method="post" id="listForm">
			<input type="hidden" value="${list.qnaId}">
				<div class="board_write">
					<table class="board_list_table" summary="${op:message('M00273')}"> <!-- 주문내역 리스트 -->
						<caption>${op:message('M00273')}</caption> 
						<colgroup>
							<col style="width:3%;" />
							<col style="width:7%;" />
							<col style="width:50%;" />					
							<col style="width:15%;" />
							<col style="width:15%;" />
							<col style="width:10%;"/>
						</colgroup>
						<thead>
							<tr>
								<th scope="col"><input id="allCheck" type="checkbox" title="${op:message('M00169')}" /></th> <!-- 체크박스 --> 
								<th scope="col">${op:message('M00200')}</th> <!-- 순번 --> 
								<th scope="col">제목</th> <!-- 제목 -->
								<th scope="col">시작일</th>  <!-- 시작일 -->
								<th scope="col">종료일</th> <!-- 종료일 -->
								<th scope="col">생성일</th> <!-- 생성일 -->
							</tr>  
						</thead>
						<tbody>
							<c:forEach items="${benefitsList}" var="list" varStatus="i">
								<tr>
									<td>
										<input type="checkbox" id="benefitsIds" name="benefitsIds" value="${list.benefitsId}" title=${op:message('M00169')} />
									</td>
									<td>${pagination.itemNumber - i.count}</td>
									<td style="text-align:center;">
										<a href="edit/${list.benefitsId }">${list.subject}</a> <!-- 제목 -->
									</td>
									<td>
									 	${fn:substring(list.startDate, 0, 4) }-${fn:substring(list.startDate, 4, 6) }-${fn:substring(list.startDate, 6, 8) } <!-- 시작일 -->
									</td>	
									<td>
										${fn:substring(list.endDate, 0, 4) }-${fn:substring(list.endDate, 4, 6) }-${fn:substring(list.endDate, 6, 8) } <!-- 종료일 -->
									</td>
									<td>
										${fn:substring(list.createdDate, 0, 4) }-${fn:substring(list.createdDate, 4, 6) }-${fn:substring(list.createdDate, 6, 8) } <!-- 생성일 --> 
									</td> 
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div><!--// board_write E-->
			
			<c:if test="${empty benefitsList}">
				<div class="no_content">
					${op:message('M00473')} <!-- 데이터가 없습니다. -->
				</div>
			</c:if>
			
			<div class="btn_all mt0">
				<div class="btn_left">
					<a href="javascript:deleteBenefits();" class="btn btn-default btn-sm"><span>${op:message('M00576')}</span></a>
				</div>

				<div class="btn_right">
					<a href="<c:url value="/opmanager/card-benefits/form?mode=0" />" class="btn btn-active btn-sm"><span class="glyphicon glyphicon-plus"></span> ${op:message('M00088')}</a>
				</div>
			</div>
				
			<page:pagination-manager />
		</form><br/>
		
		
<div style="display: none;">
	<span id="today">${today}</span>
	<span id="week">${week}</span>
	<span id="month1">${month1}</span>
	<span id="month3">${month3}</span>
</div>

<script type="text/javascript">

$(function() {
	
	searchDate();
	
});

/**
 * 조회 기간 설정
 * @return
 */
function searchDate()
{

	$(".btn_date").on('click',function(){

		var $id = $(this).attr('class').replace('btn_date ','');		// id[0] : type, id[1] : value

		if ($id == 'all') {
			
			$("input[type=text]",$(this).parent().parent()).val('');
			
		} else {

			var today = $("#today").text();

			var date1 = '';
			var date2 = '';

			if ($id == 'today') {
				date1 = today;
				date2 = today;
			} else {
				date1 = $("#"+$id).text();
				date2 = today;
			}

			$("input[type=text]",$(this).parent().parent()).eq(0).val(date1);
			$("input[type=text]",$(this).parent().parent()).eq(1).val(date2);
			
		}
		
	});
	
}

function deleteBenefits() {
	if (confirm("삭제시 복구 되지 않습니다. 삭제하시겠습니까?")) {
		
		var benefitsIds = [];
		$('input:checkbox[name="benefitsIds"]:checked').each(function(i) {
			benefitsIds[i] = $(this).val();
		});
		
		location.href = '/opmanager/card-benefits/delete?benefitsIds='+benefitsIds;
	}
}

$("#allCheck").on('click', function(){
	
	if($(this).prop("checked")) {
		$('input:checkbox[name="benefitsIds"]').prop("checked",true);
	} else {
		$('input:checkbox[name="benefitsIds"]').prop("checked",false);
	}
	
});

</script>

