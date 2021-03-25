<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>


		<div class="location">
			<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
		</div>

			<!--팝업 리스트 시작-->
			<h3><span>팝업관리</span></h3>
			<form:form modelAttribute="popupSearchParam" method="get" enctype="multipart/form-data">
				<div class="board_write">
					<table class="board_write_table" summary="팝업관리">
						<caption>팝업관리</caption>
						<colgroup>
							<col style="width:150px;" />
							<col style="width:auto;" />
							<col style="width:150px;" />
							<col style="width:auto;" />
						</colgroup>
						<tbody>
							 <tr>
							 	<td class="label">${op:message('M00730')}</td> <!-- 팝업상태 -->
							 	<td>
							 		<div>
										<form:select path="popupClose" title="${op:message('M00731')}"> <!-- 팝업상태선택 -->
											<form:option value="0" label="${op:message('M00039')}"/> <!-- 전체 -->
											<form:option value="1" label="${op:message('M00083')}"/> <!-- 사용 -->
											<form:option value="2" label="${op:message('M00732')}"/> <!-- 일시정지 -->
											<form:option value="3" label="${op:message('M00733')}"/> <!-- 종료 -->
										</form:select>
									</div>
							 	</td>
							 	<td class="label">${op:message('M00734')}</td> <!-- 팝업형태 -->
							 	<td>
							 		<div>
										<form:select path="popupStyle" title="${op:message('M00735')}"> <!-- 팝업형태선택 -->
											<form:option value="0" label="${op:message('M00039')}"/> <!-- 전체 -->
											<form:option value="1" label="${op:message('M00736')}"/> <!-- 텍스트입력 -->
											<form:option value="2" label="${op:message('M00737')}"/> <!-- 테두리없음 -->
											<form:option value="3" label="${op:message('M00738')}"/> <!-- 이미지등록 -->
										</form:select>
									</div>
							 	</td>
							 </tr>
							<tr>
								<td class="label">${op:message('M00011')}</td> <!-- 검색구분 -->
							 	<td colspan="3">
							 		<div>
										<form:select path="where" title="${op:message('M00468')}"> <!-- 키워드선택 -->
											<form:option value="SUBJECT" label="${op:message('M00739')}" />  <!-- 팝업제목 -->
										</form:select>
										<form:input type="text" path="query" class="three" title="${op:message('M00740')}" /> <!-- 팝업제목 입력 -->
									</div>
							 	</td>
							</tr>
							 <tr>
							 	<td class="label">${op:message('M00496')}</td> <!-- 사용기간 -->
							 	<td colspan="3">
							 		<div>
										<span class="datepicker"><form:input path="startDate" maxlength="8" class="datepicker" title="${op:message('M00741')}" /></span> <!-- 팝업시작일자 -->
										<span class="wave">~</span>
										<span class="datepicker"><form:input path="endDate" maxlength="8" class="datepicker" title="${op:message('M00742')}" /></span> <!-- 팝업종료일자 -->
									</div>
							 	</td>
							 </tr>
						</tbody>
					</table>
				</div> <!-- // board_write -->

				<!-- 버튼시작 -->
				<div class="btn_all">
					<div class="btn_left">
						<button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='/opmanager/popup/list';"><span class="glyphicon glyphicon-repeat"></span> ${op:message('M00047')}</button> <!-- 초기화 -->
					</div>
					<div class="btn_right">
						<button type="submit" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> ${op:message('M00048')}</button> <!-- 검색 -->
					</div>
				</div>
				<!-- 버튼 끝-->
			</form:form>

			<div class="count_title mt20">
				<h5>
					${op:message('M00039')} : ${popupCount} ${op:message('M00743')}
				</h5>	 <!-- 전체 -->   <!-- 건 조회 -->
			</div>

			<form id="listForm">
				<div class="board_write">
					<table class="board_list_table" summary="${op:message('M00273')}"> <!-- 주문내역 리스트 -->
						<caption>${op:message('M00273')}</caption>
						<colgroup>
							<col style="width:8%;" />
							<col style="width:7%;" />
							<col style="width:auto;" />
							<col style="width:14%;" />
							<col style="width:11%;" />
							<col style="width:10%;" />
							<col style="width:10%;" />
							<col style="width:16%;" />
						</colgroup>
						<thead>
							<tr>
								<th scope="col"><input type="checkbox" id="check_all" title="${op:message('M00169')}" /></th>
								<th scope="col">NO</th>
								<th scope="col">${op:message('M00739')}</th>	 <!-- 팝업제목 -->
								<th scope="col">${op:message('M00496')}</th> <!-- 사용기간 -->
								<th scope="col">${op:message('M00730')}</th> <!-- 팝업상태 -->
								<th scope="col">${op:message('M00744')}</th> <!-- 팝업타입 -->
								<th scope="col">${op:message('M00734')}</th> <!-- 팝업형태 -->
								<th scope="col">${op:message('M00087')}/${op:message('M00074')}</th> <!-- 수정 -->	<!-- 삭제 -->
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${popupList}" var="list" varStatus="i" >
								<tr>
									<td><input type="checkbox" name="id" id="check" value="${list.popupId}" title=${op:message('M00169')} /></td>
									<td>${pagination.itemNumber - i.count}</td>
									<td class="tleft">
										<div>
											<a href="/opmanager/popup/edit/${list.popupId}">${list.subject}</a>
										</div>
									</td>
									<td>${op:date(list.startDate)} ~ ${op:date(list.endDate)}</td>
									<td>${list.popupClose}</td>
									<td>${list.popupType}</td>
									<td>${list.popupStyle}</td>
									<td class="red"><a href="/opmanager/popup/edit/${list.popupId}" class="btn btn-gradient btn-xs">${op:message('M00087')}</a> <a href="javascript:deleteCheck('${list.popupId}');" class="btn btn-gradient btn-xs">${op:message('M00074')}</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div><!--// board_write E-->

				<c:if test="${empty popupList}">
					<div class="no_content">
						${op:message('M00473')} <!-- 데이터가 없습니다. -->
					</div>
				</c:if>

				<div class="btn_all">
					<div class="btn_left mb0">
						<a id="delete_list_data" href="#" class="btn btn-default btn-sm">${op:message('M00576')}</a> <!-- 선택삭제 -->
					</div>
					<div class="btn_right mb0">
						<a href="/opmanager/popup/write" class="btn btn-active btn-sm"><span class="glyphicon glyphicon-plus"></span> ${op:message('M00088')}</a> <!-- 등록 -->
					</div>
				</div>

				<page:pagination-manager /><br/>
				<!--// 전체주문 내역 끝-->
			</form>


<script type="text/javascript">

$(function() {

	//목록데이터 - 삭제처리
	$('#delete_list_data').on('click', function() {
		Common.updateListData("/opmanager/popup/delete-list", Message.get("M00306"));	// 선택된 데이터를 삭제하시겠습니까?
	});
});

function deleteCheck(popupId) {
	if (confirm(Message.get("M00745"))) {		// 해당 팝업을 삭제하시겠습니까?
		location.replace("/opmanager/popup/delete/" + popupId);
	} else {
		return;
	}
}

</script>