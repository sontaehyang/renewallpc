<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>


			<div class="location">
				<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
			</div>
		
			<div class="item_list">			
				<h3><span>SEO 설정</span></h3>	
				
				<form:form modelAttribute="searchParam" method="get">
					<div class="board_write">		
						<table class="board_write_table" summary="상품리스트">
							<caption>상품리스트</caption>
							<colgroup>
								<col style="width: 150px" />
								<col style="" /> 
							</colgroup>
							<tbody>
								
								<tr>
									<td class="label">${op:message('M00011')}</td>    <!-- 검색구분 -->
									<td colspan="3">
										<div>
											<form:select path="where" title="상세검색 선택">
												<form:option value="URL">URL</form:option>
											</form:select>
											<form:input path="query" class="w360" title="${op:message('M00022')}" /> <!-- 검색어 -->
										</div>
									</td>
								</tr>
							</tbody>
						</table>
						
					</div> <!-- // board_write -->
					
					<div class="btn_all">
						<div class="btn_left">
							<button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='/opmanager/seo/list';"><span class="glyphicon glyphicon-repeat"></span> ${op:message('M00047')}</button>
						</div>
						<div class="btn_right">
							<button type="submit" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> ${op:message('M00048')}</button> <!-- 검색 -->
						</div>
					</div>
				</form:form>
					
				<div class="count_title mt20">
					<h5>
						${op:message('M00039')} : ${op:numberFormat(pagination.totalItems)} ${op:message('M00743')}
					</h5>	 <!-- 전체 -->   <!-- 건 조회 --> 
				</div>
				
				<div class="board_list">
					<form id="listForm">
						<table class="board_list_table" summary="전체SEO리스트">
							<caption>SEO리스트</caption>
							<colgroup>
								<col style="width: 30px" />
								<col style="width: 60px" />
								<col style="width: 200px" />
								<col style="" />
								<col style="" />
								<col style="width: 120px" />
								<col style="width: 120px;" />
							</colgroup>
							<thead>
								<tr>
									<th><input type="checkbox" id="check_all" title="체크박스" /></th>
									<th>${op:message('M00200')}</th> <!-- 순번 -->
									<th>URL</th>
									<th>Title</th>
									<th>Keywords</th>
									<th>노출</th>
									<th>${op:message('M00590')}</th>	<!-- 관리 -->	
								</tr>
							</thead>
							<tbody class="sortable">
							
							<c:forEach items="${list}" var="seo" varStatus="i">
		
								<c:set var="noindexYn" value="N" />
								<c:if test="${seo.indexFlag == 'Y'}">
									<c:set var="noindexYn"><span style="color: #25A5DC">Y</span></c:set>
								</c:if>
	
								
								<tr>
									<td><input type="checkbox" name="id" value="${seo.seoId}" title="체크박스" /></td>
									<td>${pagination.itemNumber - i.count}</td>
									<td class="left"><a href="javascript:Link.view('/opmanager/seo/edit/${seo.seoId}')">${seo.seoUrl}</a></td>
									<td class="left">${seo.title}</td>
									<td class="left">${seo.keywords}</td>
								
									<td>${noindexYn}</td>
									<td>
										<a href="/opmanager/seo/edit/${seo.seoId}?url=${requestContext.currentUrl}" class="btn btn-gradient btn-xs">${op:message('M00087')}</a> <!-- 수정 -->
										<a href="#" class="delete_item btn btn-gradient btn-xs">${op:message('M00074')}</a> <!-- 삭제 -->
									</td>
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
							<a id="delete_list_data" href="#" class="btn btn-default btn-sm">${op:message('M00576')}</a> <!-- 선택삭제 -->
						</div>
						<div class="btn_right mb0">
							<a href="/opmanager/seo/create" class="btn btn-active btn-sm"><span class="glyphicon glyphicon-plus"></span> ${op:message('M00088')}</a> <!-- 등록 -->
						</div>
					</div>
					<page:pagination-manager />
					
				</div> <!-- // board_list -->
				
			</div>
			
<style>
td {background: #fff;}
.sortable-placeholder td{height: 100px; background: #d6eafd url("/content/styles/ui-lightness/images/ui-bg_diagonals-thick_20_666666_40x40.png") 50% 50% repeat;
opacity: .5;}



</style>			
<script type="text/javascript">
$(function() {
	
	// 상품삭제 
	$('.delete_item').on('click', function(e) {
		e.preventDefault();
		$('#check_all').prop("checked", false);
		$(this).closest("table").find('input[name=id]:enabled').prop("checked", false);
		$(this).closest("tr").find('input[name=id]').prop("checked", true);
		
		Common.updateListData("/opmanager/seo/list/delete", Message.get("M00306"));	// 선택된 데이터를 삭제하시겠습니까?
	});
	
	// 목록데이터 - 삭제처리
	$('#delete_list_data').on('click', function() {
		Common.updateListData("/opmanager/seo/list/delete", Message.get("M00306"));	// 선택된 데이터를 삭제하시겠습니까?
	});
	
	

});

</script>

