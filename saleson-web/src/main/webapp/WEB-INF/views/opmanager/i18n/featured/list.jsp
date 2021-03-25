<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>


	<div class="location">
		<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
	</div>	

	<div class="item_list">
			
		<h3><span>특집페이지 설정</span></h3>
		<form:form modelAttribute="featuredParam" method="get">
			<div class="board_write">								
				<table class="board_write_table" summary="관련상품등록">
					<caption>관련상품등록 </caption>
					<colgroup>
						<col style="width: 150px;">
						<col style="width: 430px;">
						<col style="width: 150px;">
						<col style="">
					</colgroup>
					<tbody>
						<tr>
							<td class="label">${op:message('M00011')}</td>    <!-- 검색구분 -->
							<td colspan="3">
								<div>
									<form:select path="where" title="상세검색 선택">
										<form:option value="FEATURED_NAME">제목</form:option> <!-- 특집페이지명 -->
										<form:option value="FEATURED_URL">URL</form:option>
										<!-- 
										<form:option value="FEATURED_SIMPLE_CONTENT">${op:message('M01084')}</form:option> <!-- 특집페이지 설명 
										-->
									</form:select>
									<form:input path="query" class="w360" title="${op:message('M00022')}" /> <!-- 검색어 -->
								</div>
							</td>
						</tr>
						
						<tr>
							<%-- <td class="label">사용유무</td>
							<td>
								<div>
									<p>
										<form:radiobutton path="featuredFlag" value="" checked="checked" label="${op:message('M00039')}" /> <!-- 전체 -->
										<form:radiobutton path="featuredFlag" value="Y" label="사용" />
										<form:radiobutton path="featuredFlag" value="N" label="비사용" />
									</p>
								</div>
							</td> --%>
							
							<td class="label">진행상태</td>
							<td>
								<div>
									<p>
										<form:radiobutton path="progression" value="" label="전체" checked="checked"/>
										<form:radiobutton path="progression" value="1" label="미진행"  />
										<form:radiobutton path="progression" value="2" label="진행중"  />
										<form:radiobutton path="progression" value="3" label="진행완료" />
									</p>
								</div>
							</td>
				
							<td class="label">${op:message('M00191')}</td> <!-- 공개유무 -->
							<td>
								<div>
									<p>
										<form:radiobutton path="displayListFlag" value="" checked="checked" label="${op:message('M00039')}" /> <!-- 전체 -->
										<form:radiobutton path="displayListFlag" value="Y" label="${op:message('M00096')}" /> <!-- 공개 -->
										<form:radiobutton path="displayListFlag" value="N" label="${op:message('M00097')}" /> <!-- 비공개 -->
									</p>
								</div>
							</td>
						</tr>

						
						<%-- <tr>
							<td class="label">특집 간략설명</td>
							<td>
								<div>
									<form:input path="featuredSimpleContent" class="full" title="특집 간략설명" />
								</div>
							</td>
						</tr> --%>
						<tr>
							<td class="label">구분</td> <!-- 공개유무 -->
							<td>
								<div>
									<p>
										<form:radiobutton path="featuredClass" value="0" checked="checked" label="${op:message('M00039')}" /> <!-- 전체 -->
										<form:radiobutton path="featuredClass" value="1" label="기획전" /> 
										<form:radiobutton path="featuredClass" value="2" label="이벤트" /> 
									</p>
								</div>
							</td>
							<td class="label">${op:message('M00052')} <!-- 출력수 --></td>
							<td>
								<div>
									<form:input path="itemsPerPage" class="_number" title="${op:message('M00052')}" /> 
								</div>
							</td>
						</tr>
					</tbody>
				</table>
				
			</div>
			
			<div class="btn_all">
				<div class="btn_left">
					<button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='/opmanager/${featuredTypeUri}/list';"><span class="glyphicon glyphicon-repeat"></span> ${op:message('M00047')}</button> <!-- 초기화 -->
				</div>
				<div class="btn_right">
					<button type="submit" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> ${op:message('M00048')}</button> <!-- 검색 -->
				</div>
			</div>
		</form:form>
		
		<div class="count_title mt20">
			<h5>
				${op:message('M00039')} : ${featuredCount} ${op:message('M00743')}
			</h5>	 <!-- 전체 -->   <!-- 건 조회 --> 
		</div>
		
		<form id="checkedDeleteForm" method="post" action="/opmanager/${featuredTypeUri}/checked-delete">
			<input type="hidden" name="featuredCode" value="${featuredParam.featuredCodeChecked}" />
			<div class="board_write">
				<table class="board_list_table" summary="처리내역 리스트">
					<caption>처리내역 리스트</caption>
					<colgroup>
						<col style="width:50px;">
						<col style="width:70px">
						<col style="width:auto;">
						<col style="width:70px;">
						<col style="width:400px">
						<col style="width:70px;">
						<col style="width:70px;">
						<col style="width:150px">
						<col style="width:100px">			
						<col style="width:70px"> 
					</colgroup>
					<thead>
						<tr>
							<th scope="col"><input type="checkbox" class="delete_all" title="체크박스"></th>
							<th scope="col">구분</th> 
							<th scope="col">제목</th> <!-- 특집페이지명 -->
							<th scope="col">이벤트 코드</th>
							<th scope="col">URL</th>
							<!--  
							<th scope="col">${op:message('M01084')}</th> 특집페이지 설명
							 -->
							
							<th scope="col">진행상태</th>
							<th scope="col">공개유무</th>
							<th scope="col">진행기간</th>
							<th scope="col">${op:message('M00202')}</th>  <!-- 등록일 -->
							<th scope="col">${op:message('M00087')}</th>  <!-- 수정 -->
						</tr>
					</thead>
					<tbody class="sortable">
						<c:forEach items="${featuredList}" var="list" varStatus="i">
							<tr>
								 <td><input type="checkbox" name="featuredIds" title="체크박스" value="${list.featuredId}"></td>
								 <td>
								 	<c:choose>
								 		<c:when test="${list.featuredClass == '1' }">기획전</c:when>
								 		<c:otherwise>이벤트</c:otherwise>
								 	</c:choose>
								 	
								 </td>
								 <td class="tex_l"><a href="javascript:Link.view('/opmanager/${featuredTypeUri}/edit/${list.featuredId}')">${list.featuredName}</a></td>
								<td>
									<c:if test="${not empty list.eventCode}">
										${list.eventCode}
									</c:if>
								</td>
								 <td class="tex_l">
									 <c:choose>
										 <c:when test='${op:property("saleson.view.type") eq "api"}'>
											 <div style="width: 45%; float: left; margin-left: 5%;">
												 <a href="${op:property("saleson.url.frontend")}/featured/detail.html?pages=${list.featuredUrl}" target="_blank"><img src="/content/opmanager/images/icon/icon_preview_pc.gif" alt="">
													 /featured/detail.html?pages=${list.featuredUrl}
												 </a>
											 </div>
										 </c:when>
										 <c:otherwise>
											<div style="width: 45%; float: left; margin-left: 5%;">
												 <a href="/pages/${list.featuredUrl}" target="_blank"><img src="/content/opmanager/images/icon/icon_preview_pc.gif" alt="">
													 /pages/${list.featuredUrl}
												 </a>
											 </div>
											 <div style="width: 45%; float: left; margin-left: 5%;">
												 <a href="/m/pages/${list.featuredUrl}" target="_blank"><img src="/content/opmanager/images/icon/icon_preview_mobile.gif" alt="">
													 /m/pages/${list.featuredUrl}
												 </a>
											 </div>
										 </c:otherwise>
									 </c:choose>

								 	<%-- <a href="${fn:contains(featuredTypeUri,'-mobile') ? '/sp' : ''}/pages/${list.featuredUrl}" target="_blank"><img src="/content/images/opmanager/common/icon_preview_pc.gif" alt=""></a>
								 	${list.featuredType == 2 ? '/sp' : ''}/pages/${list.featuredUrl} --%>
								 </td>
								 <td>
								 	<c:choose>
										<c:when test="${list.progression eq '1' }">미진행</c:when>
										<c:when test="${list.progression eq '2' }">진행중</c:when>
										<c:when test="${list.progression eq '3' }">진행완료</c:when>
									</c:choose>
								 </td>
								 <td>${fn:replace(fn:replace(list.displayListFlag,'Y','공개'),'N','비공개')}</td>
								 <!-- 
								 <td class="tleft">${list.featuredSimpleContent}</td>
								  -->
								 <td>
								 	<c:choose>
										<c:when test="${list.startDate ne '99999999' and list.endDate eq '99999999'}">${op:date(list.startDate)} ~ </c:when>
										<c:when test="${list.startDate eq '99999999' and list.endDate eq '99999999'}">상시 게시</c:when>
										<c:when test="${list.startDate != '' and list.startDate ne '' }">${op:date(list.startDate)} ~ ${op:date(list.endDate)}</c:when>
										<c:otherwise>-</c:otherwise>
									</c:choose>
								 </td>
								 <td>${op:date(list.createdDate) }</td>
								 <td><a href="javascript:Link.view('/opmanager/${featuredTypeUri}/edit/${list.featuredId}')" class="btn btn-gradient btn-xs">${op:message('M00087')}</a></td> <!-- 수정 -->
							</tr>
						</c:forEach>
					</tbody>
				</table>				 
			</div>
			
			<c:if test="${empty featuredList}">
				<div class="no_content">
					${op:message('M00473')} <!-- 데이터가 없습니다. -->
				</div>
			</c:if>
				
			
			<div class="btn_all">
				<div class="btn_left mb0">
					<button type="button" class="btn btn-default btn-sm checked_delete">${op:message('M01034')}</button> <!-- 일괄삭제 -->
					
					<button type="button" id="change_ordering" class="btn btn-dark-gray btn-sm" onclick="changeListOrdering()">${op:message('M00791')}</button> <!-- 정렬순서변경 -->
					<div class="board_guide ml10" style="float: right;">
						<p class="tip"></p>
					</div>
					<!-- <button type="button" id="change_ordering2" class="btn ctrl_btn "  onclick="changeListOrdering()">${op:message('M00791')}</button> --> <!-- 정렬순서변경 -->
				</div>
				<div class="btn_right mb0">
					<a href="/opmanager/${featuredTypeUri}/create" class="btn btn-active btn-sm"><span class="glyphicon glyphicon-plus"></span> ${op:message('M00088')}</a> <!-- 등록 -->
				</div>
			</div>
			
			<page:pagination-manager />
		</form>	
					
	</div> <!-- // item_list02 -->


<style>
td {background: #fff;}
.sortable-placeholder td {
	height: 70px; 
	background: #d6eafd url("/content/styles/ui-lightness/images/ui-bg_diagonals-thick_20_666666_40x40.png") 50% 50% repeat;
	opacity: 0.5;
}
#change_ordering, #change_ordering2 {
	display: none;
}
#change_ordering2 {
	position:fixed; right:0; bottom: 166px; z-index: 1000;
}
</style>


<script type="text/javascript">
$(function(){
	if (isPosibleToChangeOrdering()) {
		// 관련상품 drag sortable
	    $( ".sortable" ).sortable({
	        placeholder: "sortable-placeholder"
	    });
	    $( ".sortable" ).disableSelection(); 
	    
	    $('#change_ordering, #change_ordering2').css({'background-color': '#25a5dc', 'border': '1px solid #25a5dc'}).show();
	    $('.tip').append("Tip. 목록을 드래그 하여 정렬순서를 변경하세요.");
	} else {
		$('#change_ordering, #change_ordering2').css({'background-color': '#c34e00', 'border': '1px solid #c34e00'}).show();
	}
	
	
	$(".delete_all").on("click",function(){
		var flag = $(this).prop("checked");
		$("input[name='featuredIds']").each(function(){
			$(this).prop("checked",flag);
		});
	});		
	
	$(".checked_delete").on("click",function(){
		Common.confirm(Message.get("M00196"),function(){	// 삭제하시겠습니까?
			if($("input[name='featuredIds']:checked").size() > 0){
				$("#checkedDeleteForm").submit();
			} else {
                alert('삭제할 항목을 선택해 주세요.');
            }
        });
	});
	
	$("#reset").on("click",function(){
		$("input").each(function(){
			$(this).val('');
		});
	});
});


//순서변경 가능여부 체크
function isPosibleToChangeOrdering() {
	// 순서 변경이 가능한 조건인지 체크.
	var $query = $('#query');
	var $featuredFlag = $('#featuredFlag2');
	var $displayListFlag = $('#displayListFlag2');

	if ($query.val() != ''
			|| $featuredFlag.prop('checked') == false 
			|| $displayListFlag.prop('checked') == false ) {
		
		return false;
	}
	return true;
}

// 순서변경 
function changeListOrdering() {
	if (!isPosibleToChangeOrdering()) {
		if (confirm(Message.get("M00298") + '\n' + Message.get("M00299"))) { // '현재 검색 조건으로는 순서 정렬이 불가능합니다. 순서 변경이 가능한 조건으로 다시 검색하시겠습니까?'
			$('#query').val('');
			$('#featuredFlag2').prop('checked', true);
			$('#displayListFlag2').prop('checked', true);
			
			$('#featuredParam').submit();
		} 
		return;
	}
	
	var $form = $('#checkedDeleteForm');

	if (confirm(Message.get("M01483"))) { // 순서를 변경하시겠습니까? 
		$('.delete_all').click();
		var param = $form.serialize();
		var startOrdering = ((Number('${pagination.currentPage}') - 1) * Number($('#itemsPerPage').val())) + 1;
		
		param = param + '&startOrdering=' + startOrdering;

		
		$.post('/opmanager/featured/list/change-ordering', param, function(response) {
			Common.responseHandler(response, function(){
				$('.delete_all').click();
				alert(Message.get("M01221"));	// 처리되었습니다.
				location.reload();
			});
		});
	} 
}

</script>