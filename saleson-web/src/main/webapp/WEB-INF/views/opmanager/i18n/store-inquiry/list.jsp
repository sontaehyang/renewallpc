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
				<h3><span>입점문의리스트</span></h3>	
				<form:form modelAttribute="storeInquiryParam" method="get" enctype="multipart/form-data">
					<div class="board_write">
						<table class="board_write_table" summary="입점문의관리">
							<caption>입점문의관리</caption>
							<colgroup>
								<col style="width:150px;" />
								<col style="width:auto;" />
							</colgroup>
							<tbody>
								 <tr>
								 	<td class="label">진행상태</td> 
								 	<td>
								 		<div>
											<form:select path="status" title="진행상태"> <!-- 영업상태선택 --> 
												<form:option value="" label="${op:message('M00039')}"/>  
												<form:option value="0" label="접수완료"/>  
												<form:option value="1" label="처리중"/>
												<form:option value="2" label="처리완료"/> 
											</form:select>
										</div>
								 	</td>
								 </tr>
								<tr>
									<td class="label">${op:message('M00011')}</td> <!-- 검색구분 --> 
								 	<td>
								 		<div>
											<form:select path="where" title="${op:message('M00468')}"> <!-- 키워드선택 --> 
												<form:option value="USER_NAME" label="${op:message('M01632')}" />  <!-- 담당자명 --> 
												<form:option value="COMPANY" label="업체명" />  
											</form:select> 
											<form:input type="text" path="query" class="three" title="검색어" /> 
										</div>
								 	</td>	
								</tr>
							</tbody>					 
						</table>								 							
					</div> <!-- // board_write -->
					
					<!-- 버튼시작 -->	
					<div class="btn_all">
						<div class="btn_left">
							<button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='/opmanager/store-inquiry/list';"><span class="glyphicon glyphicon-repeat"></span> ${op:message('M00047')}</button> <!-- 초기화 -->
						</div>
						<div class="btn_right">
							<button type="submit" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> ${op:message('M00048')}</button> <!-- 검색 -->
						</div>
					</div>		 
					<!-- 버튼 끝-->
					
					<div class="count_title mt20">
						<h5>
							${op:message('M00039')} : ${op:numberFormat(totalCount)} ${op:message('M00743')}
						</h5>
					</div>
				</form:form>
				<div class="board_list">
					<form id="listForm">
						<table class="board_list_table" summary="전체업체리스트">
							<caption>전체업체리스트</caption>
							<colgroup>
								<col style="width: 50px" />
								<col style="width: 150px" />
								<col style="" />
								<col style="width: 150px;" />
								<col style="width: 150px;" />
								<col style="width: 150px" />
								<col style="width: 150px" />
							</colgroup>
							<thead>
								<tr>
									<th>${op:message('M00200')}</th> <!-- 순번 -->
									<th>상태</th>
									<th style="min-width: 60px;">업체명</th>
									<th>${op:message('M01632')} <!-- 담당자명 --></th>
									<th>이메일</th>
									<th>담당자 연락처</th>
									<th>등록일</th>
								</tr>
							</thead>
							<tbody class="sortable">
							
							<c:forEach items="${list}" var="storeInquiry" varStatus="i">
								
								<tr>
									<td>
										<c:choose>
											<c:when test="${itemParam.orderBy == 'ORDERING' && itemParam.sort == 'ASC'}">
												${pagination.number + i.count}
											</c:when>
											<c:otherwise>
												${pagination.itemNumber - i.count}
											</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
											<c:when test="${storeInquiry.status == '0'}">
												접수완료
											</c:when>
											<c:when test="${storeInquiry.status == '1'}">
												처리중
											</c:when>
											<c:when test="${storeInquiry.status == '2'}">
												처리완료
											</c:when>
										</c:choose>
									</td>
									<td>
										<a href="/opmanager/store-inquiry/detail/${storeInquiry.storeInquiryId}">${storeInquiry.company}</a>
										<c:if test="${storeInquiry.fileName != null}">[<a href="/upload/store-inquiry/${storeInquiry.storeInquiryId}.${fn:split(storeInquiry.fileName,'.')[1]}" download="${storeInquiry.fileName}">${storeInquiry.fileName}</a>]</c:if>
									</td>
									<td>
										${storeInquiry.userName}
									</td>
									<td>
										${storeInquiry.email}
									</td>
									<td>
										${storeInquiry.phoneNumber}
									</td>
									<td>
										${op:date(storeInquiry.creationDate)}
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
					<br /> 
					
					<page:pagination-manager />
					
				</div> <!-- // board_list -->
				
			</div>
			
<style>
td {background: #fff;}
.sortable-placeholder td{height: 100px; background: #d6eafd url("/content/styles/ui-lightness/images/ui-bg_diagonals-thick_20_666666_40x40.png") 50% 50% repeat;
opacity: .5;}



</style>			
