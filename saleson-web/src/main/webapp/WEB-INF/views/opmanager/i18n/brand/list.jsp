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
		
			<h3><span>브랜드관리</span></h3> 
			<form:form modelAttribute="brandParam" method="get" enctype="multipart/form-data">
				<div class="board_write">
					<table class="board_write_table" summary="브랜드관리">
						<caption>브랜드관리</caption>
						<colgroup>
							<col style="width:150px;" />
							<col style="width:auto;" />
							<col style="width:150px;" />
							<col style="width:auto;" />
						</colgroup>
						<tbody>
							 <tr>
								<td class="label">${op:message('M00011')}</td>    <!-- 검색구분 -->
								<td colspan="3">
									<div>
										<form:select path="where" title="상세검색 선택">
											<form:option value="BRAND_NAME">브랜드명</form:option>
										</form:select>
										<form:input path="query" class="w360" title="상세검색 입력" />
									</div>
								</td>
							</tr>
							 <tr>
							 	<td class="label">공개여부</td>
							 	<td colspan="3">
							 		<div>
										<form:radiobutton path="displayFlag" label="공개" value="Y" checked="checked"/>
										<form:radiobutton path="displayFlag" label="비공개" value="N"/>
									</div> 
							 	</td>
							 </tr>
						</tbody>					 
					</table>								 							
				</div> <!-- // board_write -->
				
				<!-- 버튼시작 -->	
				<div class="btn_all">
					<div class="btn_left">
						<button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='/opmanager/brand/list';"><span class="glyphicon glyphicon-repeat"></span> ${op:message('M00047')}</button> <!-- 초기화 -->
					</div>
					<div class="btn_right">
						<button type="submit" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span>${op:message('M00048')}</button> <!-- 검색 -->
					</div>
				</div>		 

			</form:form>

			<div class="count_title mt20">
				<h5>
					${op:message('M00039')} : ${brandCount} ${op:message('M00743')}
				</h5>	 <!-- 전체 -->   <!-- 건 조회 --> 
			</div>
			
			<form id="listForm">
				<div class="board_write">
					<table class="board_list_table" summary="브랜드관리 리스트">
						<caption>브랜드관리 리스트</caption>
						<colgroup>
							<col style="width:8%;" />
							<col style="width:7%;" />				
							<col style="width:auto;" />
							<col style="width:14%;" />	
							<col style="width:11%;" />
							<col style="width:10%;" />
							<col style="width:16%;" />
						</colgroup>
						<thead>
							<tr>
								<th scope="col"><input type="checkbox" id="check_all" title="${op:message('M00169')}" /></th>
								<th scope="col">브랜드명</th>
								
								<th scope="col">공개여부</th> 
								<th scope="col">${op:message('M00087')}/${op:message('M00074')}</th> <!-- 수정 -->	<!-- 삭제 -->	
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${brandList}" var="list" varStatus="i" >
								<tr>
									<td><input type="checkbox" name="id" id="check" value="${list.brandId}" title=${op:message('M00169')} /></td>
									<td class="tleft">
										<div>	
											<a href="/opmanager/brand/edit/${list.brandId}">${list.brandName}</a>							
										</div>
									</td>
									
									<td>${list.displayFlag}</td>
									<td class="red"><a href="/opmanager/brand/edit/${list.brandId}" class="btn btn-gradient btn-xs">${op:message('M00087')}</a> <a href="javascript:deleteCheck('${list.brandId}');" class="btn btn-gradient btn-xs">${op:message('M00074')}</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>				 
				</div><!--// board_write E-->
				
				<c:if test="${empty brandList}">
					<div class="no_content">
						${op:message('M00473')} <!-- 데이터가 없습니다. -->
					</div>
				</c:if>
				
				<div class="btn_all">
					<div class="btn_left mb0">
						<a id="delete_list_data" href="#" class="btn btn-default btn-sm">${op:message('M01034')}</a> <!-- 일괄삭제 -->
					</div>
					<div class="btn_right mb0">
						<a href="/opmanager/brand/create" class="btn btn-active btn-sm"><span class="glyphicon glyphicon-plus"></span> ${op:message('M00088')}</a> <!-- 등록 -->
					</div>
				</div>
				
				<page:pagination-manager /><br/>
				<!--// 전체주문 내역 끝-->
			</form>

<div class="board_guide">
	<br/>
	<p class="tip">[안내]</p>
	<p class="tip">
		등록되어있는 브랜드를 삭제할 경우 해당 브랜드 상품으로 등록된 상품정보가 자사 상품(브랜드 미지정) 상태로 변경됩니다.
		<br/> 브랜드 정보를 삭제할 경우 반드시 상품의 상태(노출 상태 등)를 변경 하신 후 삭제하시기 바랍니다.
	</p>
</div>			

<script type="text/javascript">

$(function() {
	
	//목록데이터 - 삭제처리
	$('#delete_list_data').on('click', function() {
		Common.updateListData("/opmanager/brand/delete-list", "선택된 브랜드를 삭제 하시겠습니까?\n상품에 선택되어 있는 브랜드 정보도 초기화 됩니다.");	// 선택된 데이터를 삭제하시겠습니까?
	});	
});

function deleteCheck(brandId) {
	if (confirm("해당 브랜드 정보를 삭제 하시겠습니까?\n상품에 선택되어 있는 브랜드 정보도 초기화 됩니다.")) {		// 해당 팝업을 삭제하시겠습니까? 
		location.replace("/opmanager/brand/delete/" + brandId);  
	} else {
		return;
	}
}

</script>