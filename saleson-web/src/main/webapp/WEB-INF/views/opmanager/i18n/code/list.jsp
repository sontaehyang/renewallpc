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

	<!-- 상단타이틀 -------------------------------------------------------------------- -->
		<h3><span>${op:message('MENU_1401')}</span></h3> <!-- 공통코드관리 -->
		<div class="board_write">
			<table class="board_write_table" summary="${op:message('MENU_1401')}"> <!-- 공통코드관리 -->
				<caption>${op:message('MENU_1401')}</caption>
				<colgroup>
					<col style="width:100%;" />
				</colgroup>
				<tbody>
					 <tr>
					 	<td></td>
					 </tr>
				</tbody>
			</table>
		</div>
	<!-- 상단타이틀 -------------------------------------------------------------------- -->
<style>
	#dvCodeType li:hover,#dvCodeType li:focus {color:#000000;background-color:lightsteelblue;}
</style>
<div class="container-fluid">

  <div class="row">
  	<!-- (왼쪽) CODE-TYPE 코드구분  ----------------------------------------------------- -->
    <div class="col-sm-3" style="height:100%;border 1px;" id="dvCodeType" name="dvCodeType">
		<form id="listForm" style="height:100%;">
			<div style="height:600px;margin:0px 0px 5px 0px;">
				<div style="text-align:right;background-color:#f8f8f8;height:48px;padding:10px;">
				  <input id="SimpleSearch" type="text" placeholder="CodeType Filter.." style="width:100%;">
				  <i><font >total: ${codeTypeCount}</font></i>
				</div>

				<ul class="list-group" id="simpleSearchList" style="height:600px;margin:5px 0px;overflow:auto">
					<c:set var="listStyle" value="" />
					<c:forEach items="${codeTypeList}" var="list">
						<c:choose>
							<c:when test="${codeParam.codeType == list.codeType}">
								<c:set var="listStyle">style="background-color:#C4DEFF;"</c:set>
							</c:when>
							<c:otherwise>
								<c:set var="listStyle"></c:set>
							</c:otherwise>
						</c:choose>
					  <li class="list-group-item" onclick="fnCodeSearch('${list.codeType}');" ${ listStyle } >${list.codeType} <span class="badge">${list.codeTypeCnt}</span></li>
					</c:forEach>
				</ul>
			</div>
		</form>
  	<!-- (왼쪽) CODE-TYPE 코드구분  ----------------------------------------------------- -->
    </div>

  	<!-- 코드 데이터  -->
    <div class="col-sm-9">
  	<!-- (오른쪽) CODE-TYPE 코드구분  ----------------------------------------------------- -->

	<!-- 조회 부 -------------------------------------------------------------------- -->
	<form:form modelAttribute="codeParam" method="get" enctype="multipart/form-data">
		<table class="board_write_table" style="border-top:0px;" summary="${op:message('M01676')}"> <!-- 공통코드 -->
			<colgroup>
				<col style="width:150px;" />
				<col style="width:*;" />
			</colgroup>
			<tbody>
				 <tr>
				 	<td class="label">${op:message('M00011')}</td> <!-- 검색구분 -->
				 	<td>
				 		<div>
							<form:select path="where" title="${op:message('M00468')}"> <!-- 키워드 선택 -->
								<form:option value="ID">${op:message('M01675')}</form:option> <!-- 코드값 -->
								<form:option value="LABEL">${op:message('M01674')}</form:option> <!-- 코드라벨 -->
							</form:select>
							<form:input type="text" path="query" class="three" title="${op:message('M00021')}" placeholder="Search.."/> <!-- 검색어 입력 -->
							<form:input type="hidden" path="codeType" name="codeType" id="codeType" />
						</div>
				 	</td>
				 </tr>
			</tbody>
		</table>

		<!-- 버튼시작 -->
		<div class="btn_all">
			<div class="btn_left">
				<button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='/opmanager/code/list';"><span class="glyphicon glyphicon-repeat"></span> ${op:message('M00047')}</button> <!-- 초기화 -->
			</div>
			<div class="btn_right">
				<button type="submit" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> ${op:message('M00048')}</button> <!-- 검색 -->
			</div>
		</div>
		<!-- 버튼 끝-->

		<div class="count_title mt20">
			<h5>
				${op:message('M00039')} : ${pagination.totalItems} ${op:message('M00743')}
			</h5>	 <!-- 전체 -->   <!-- 건 조회 -->
		</div>

	</form:form>
	<!-- 조회 부 -------------------------------------------------------------------- -->


		<form id="listForm2">
			<div class="board_write">
				<table class="board_list_table" summary="${op:message('M01676')}"> <!-- 공통코드 -->
					<caption>${op:message('M01676')}</caption>
					<colgroup>
						<col style="" />
						<col style="" />
						<col style="" />
						<col style="" />
						<col style="" />
						<col style="" />
						<col style="" />
					</colgroup>
					<thead>
						<tr>
							<th scope="col">${op:message('M01680')}</th> <!-- 코드구분 -->
							<th scope="col">${op:message('M01374')}</th> <!-- 코드 -->
							<th scope="col">${op:message('M01674')}</th> <!-- 코드라벨 -->
							<th scope="col">${op:message('M01677')}</th> <!-- 코드상세 -->
							<th scope="col">${op:message('M01678')}</th> <!-- 정렬순서 -->
							<th scope="col">${op:message('M00082')}</th> <!-- 사용여부 -->

							<th scope="col">${op:message('M01682')}</th> <!-- 상위id -->
							<th scope="col">${op:message('M01675')}</th> <!-- 코드값 -->
							<th scope="col">${op:message('M01684')}</th> <!-- 확장코드 -->
							<th scope="col">${op:message('M01685')}</th> <!-- 매핑코드 -->

							<th scope="col">-</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${codeList}" var="list" varStatus="i">
							<tr >
								<td>${list.codeType}</td>
								<td style="text-align:left;">${list.id}</td>
								<td style="text-align:left;">${list.label}</td>
								<td>${list.detail}</td>
								<td>${list.ordering}</td>
								<td>${list.useYn}</td>
								<td style="text-align:left;">${list.upId}</td>
								<td style="text-align:left;">${list.codeValue}</td>
								<td style="text-align:left;">${list.extentionCode}</td>
								<td style="text-align:left;">${list.mappingCode}</td>
								<td>
									<a href='javascript:fnUpdateCode("${list.codeType}","${list.id }")' class="btn btn-gradient btn-xs">${op:message('M00087')}</a> <!-- 수정 -->
									<a href='javascript:fnDeleteCode("${list.codeType}","${list.id }")' class="delete_item btn btn-gradient btn-xs">${op:message('M00074')}</a> <!-- 삭제 -->
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div><!--// board_write E-->

			<c:if test="${empty codeList}">
				<div class="no_content">
					${op:message('M00473')} <!-- 데이터가 없습니다. -->
				</div>
			</c:if>

			<div class="btn_all">
				<div class="btn_left mb0">
				</div>
				<div class="btn_right mb0">
					<a href="javascript:fnCodeAdd();" class="btn btn-active btn-sm"><span class="glyphicon glyphicon-plus"></span> ${op:message('M00088')}</a> <!-- 신규등록 -->
				</div>
			</div>

			<page:pagination-manager />

		</form>
    </div>
  </div>
</div>

<script type="text/javascript">

	$(document).ready(function(){
	  $("#SimpleSearch").on("keyup", function() {
	    var value = $(this).val().toLowerCase();
	    $("#simpleSearchList li").filter(function() {
	      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
	    });
	  });
	});

	//Code 추가
	function fnCodeAdd(){
		Common.popup('/opmanager/code/create?whereCodeType=' + $("#codeType").val(), 'createCode', 800, 500, 1);
	}
	function fnUpdateCode(codeType, id) {
		Common.popup('/opmanager/code/edit?whereCodeType=' + encodeURIComponent(codeType) + "&whereId=" + encodeURIComponent(id), 'editCode', 800, 500, 1);
	}

	//CodeType 선택시 조회
	function fnCodeSearch(pCodeType){
		$('#codeType').val(pCodeType);
		$('#codeParam').submit();
	}

	function fnSearch() {
		fnCodeSearch("${codeParam.codeType}");
	}
	function fnDeleteCode(codeType, id) {
		Common.confirm("${op:message('M00196')}", function() {
			$.post(url("/opmanager/code/delete"), {"codeType" : codeType, "id" : id}, function(response) {
				Common.responseHandler(response, function() {
					alert("${op:message('M00205')}");
					fnSearch();
				});
			});
		});
	}
</script>