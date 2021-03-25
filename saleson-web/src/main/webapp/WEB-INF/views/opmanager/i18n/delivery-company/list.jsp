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
			<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
		</div>


			<form:form modelAttribute="deliveryCompanyParam" method="get" enctype="multipart/form-data">
				<h3><span>${op:message('M01211')}</span></h3>
				<div class="board_write">
					<table class="board_write_table" summary="${op:message('M01211')}">
						<caption>${op:message('M01211')}</caption> <!-- 배송업체 -->
						<colgroup>
							<col style="width:150px;" />
							<col style="width:auto;" />
						</colgroup>
						<tbody>
							 <tr>
							 	<td class="label">${op:message('M00011')}</td> <!-- 검색구분 -->
							 	<td>
							 		<div>
							 			<form:select path="where">
											<form:option value="DELIVERY_COMPANY_NAME" label="${op:message('M00665')}" /> <!-- 배송업체명 -->
											<form:option value="TEL_NUMBER" label="${op:message('M00666')}" />	 <!-- 대표연락처 -->
										</form:select>
										<form:input type="text" path="query" name="deliveryCompanyName" class="w360" title="${op:message('M00665')}" />
							 		</div>
							 	</td>
							 </tr>
						</tbody>
					</table>
				</div> <!-- // board_write -->

				<!-- 버튼시작 -->
				<div class="btn_all">
					<div class="btn_left">
						<button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='/opmanager/delivery-company/list';"><span class="glyphicon glyphicon-repeat"></span> ${op:message('M00047')}</button>
					</div>
					<div class="btn_right">
						<button type="submit" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> ${op:message('M00048')}</button> <!-- 검색 -->
					</div>
				</div>
				<!-- 버튼 끝-->
			</form:form>

			<div class="count_title mt20">
				<h5>
					${op:message('M00039')} : ${deliveryCompanyCount} ${op:message('M00743')}
				</h5>	 <!-- 전체 -->   <!-- 건 조회 -->
			</div>

			<form id="listForm">
			<div class="board_write">
				<table class="board_list_table" summary="${op:message('M00273')}"> <!-- 주문내역 리스트 -->
					<caption>${op:message('M00273')}</caption>
					<colgroup>
						<col style="width:100px;" />
						<col style="width:400px;" />
						<col style="width:200px;" />
				 		<col style="width:auto;" />
						<col style="width:150px;" />
					</colgroup>
					<thead>
						<tr>
							<th scope="col"><input type="checkbox" name="tempId2" id="check_all" title="${op:message('M00169')}" /></th> <!-- 체크박스 -->
							<th scope="col">${op:message('M00665')}</th> <!-- 배송업체명 -->
							<th scope="col">${op:message('M00666')}</th> <!-- 대표연락처 -->
							<th scope="col">${op:message('M00668')}</th> <!-- 배송업체 확인 URL -->
							<th scope="col">${op:message('M00669')}</th> <!-- 사용유무 -->
						</tr>

					</thead>
					<tbody>
					<c:forEach items="${deliveryCompanyList}" var="companyList">
						<tr>
							<td><input type="checkbox" name="id" id="check" value="${companyList.deliveryCompanyId}" title="${op:message('M00169')}" /></td>
							<td>
								<div>
									<a href="/opmanager/delivery-company/edit/${companyList.deliveryCompanyId}">${companyList.deliveryCompanyName}</a>
								</div>
							</td>
							<td>${companyList.telNumber}</td>
							<td>${companyList.deliveryCompanyUrl}</td>
							<td>${companyList.useFlag == "Y" ? op:message('M00083') : op:message('M00089')}</td> <!-- 사용 --> <!-- 사용안함 -->
						</tr>
					</c:forEach>
					</tbody>
				</table>

				<c:if test="${empty deliveryCompanyList}">
					<div class="no_content">
						${op:message('M00473')} <!-- 데이터가 없습니다. -->
					</div>
				</c:if>

			</div><!--// board_write E-->

			<div class="btn_all pt20">
				<div class="btn_left">
					<a id="delete_list_data" href="#" class="btn btn-default btn-sm"><span>${op:message('M00576')}</span></a> <!-- 선택삭제 -->
				</div>
				<div class="btn_right">
					<a href="/opmanager/delivery-company/create" class="btn btn-active btn-sm"><span class="glyphicon glyphicon-plus"></span> ${op:message('M00088')}</a> <!-- 등록 -->
				</div>
			</div>
			</form>

			<page:pagination-manager /><br/>



<script type="text/javascript">
$(function() {

	//목록데이터 - 삭제처리
	$('#delete_list_data').on('click', function() {
		Common.updateListData("/opmanager/delivery-company/delete", Message.get("M00306"));	// 선택된 데이터를 삭제하시겠습니까?
	});

});
</script>