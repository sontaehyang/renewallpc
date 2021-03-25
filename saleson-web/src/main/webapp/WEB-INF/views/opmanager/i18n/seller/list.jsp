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
				<h3><span>입점업체리스트</span></h3>	
				<form:form modelAttribute="sellerParam" method="get" enctype="multipart/form-data">
					<div class="board_write">
						<table class="board_write_table" summary="입점업체관리">
							<caption>입점업체관리</caption>
							<colgroup>
								<col style="width:150px;" />
								<col style="width:auto;" />
							</colgroup>
							<tbody>
								 <tr>
								 	<td class="label">${op:message('M01636')}</td> <!-- 입점업체상태 --> 
								 	<td>
								 		<div>
											<form:select path="statusCode" title="${op:message('M01636')}"> <!-- 영업상태선택 --> 
												<form:option value="" label="${op:message('M00039')}"/>  
												<form:option value="1" label="${op:message('M01637')}"/>  
												<form:option value="2" label="${op:message('M01638')}"/>
												<form:option value="3" label="${op:message('M01639')}"/> 
												<form:option value="4" label="${op:message('M01640')}"/>
											</form:select>
										</div>
								 	</td>
								 </tr>
								<tr>
									<td class="label">${op:message('M00011')}</td> <!-- 검색구분 --> 
								 	<td>
								 		<div>
											<form:select path="where" title="${op:message('M00468')}"> <!-- 키워드선택 --> 
												<form:option value="SELLER_NAME" label="${op:message('M01630')}" />  <!-- 판매자명 --> 
												<form:option value="USER_NAME" label="${op:message('M01632')}" />  <!-- 담당자명 --> 
												<form:option value="COMPANY_NAME" label="${op:message('M01635')}" />  <!-- 상호명 --> 
											</form:select> 
											<form:input type="text" path="query" class="three" title="${op:message('M00740')}" /> 
										</div>
								 	</td>	
								</tr>
							</tbody>					 
						</table>								 							
					</div> <!-- // board_write -->
					
					<!-- 버튼시작 -->	
					<div class="btn_all">
						<div class="btn_left">
							<button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='/opmanager/seller/list';"><span class="glyphicon glyphicon-repeat"></span> ${op:message('M00047')}</button> <!-- 초기화 -->
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
								<col style="" />
								<col style="width: 150px;" />
								<col style="width: 100px;" />
								<col style="width: 150px" />
								<col style="width: 150px" />
								<col style="width: 80px;" />
								<col style="width: 80px;" />
								<col style="width: 80px;" />
							</colgroup>
							<thead>
								<tr>
									<th>${op:message('M00200')}</th> <!-- 순번 -->
									<th style="min-width: 60px;">${op:message('M01630')}</th> <!-- 판매자명 -->
									<th>${op:message('M01631')}</th> <!-- 로그인ID -->
									<th>${op:message('M01632')} <!-- 담당자명 --></th>
									<th>${op:message('M01634')} <!-- 담당자 휴대폰번호 --></th>
									<th>${op:message('M01635')} <!-- 상호 --></th>
									<th>${op:message('M01653')} <!-- 수수료율 --></th>
									<th>${op:message('M01636')} <!-- 영업상태 --></th>
									<th>${op:message('M00796')} <!-- 로그인 --></th>
								</tr>
							</thead>
							<tbody class="sortable">
							
							<c:forEach items="${list}" var="seller" varStatus="i">
								
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
										<a href="/opmanager/seller/edit/${seller.sellerId}">${seller.sellerName}</a>
									</td>
									<td>
										${seller.loginId}
									</td>
									<td>
										${seller.userName}
									</td>
									<td>
										${seller.phoneNumber}
									</td>
									<td>
										${seller.companyName}
									</td>
									<td>
										${seller.commissionRate}%
									</td>
									<td>
										<c:choose>
											<c:when test="${seller.statusCode == 1}">
												${op:message('M01637')}
											</c:when>
											<c:when test="${seller.statusCode == 2}">
												${op:message('M01638')}
											</c:when>
											<c:when test="${seller.statusCode == 3}">
												${op:message('M01639')}
											</c:when>
											<c:when test="${seller.statusCode == 4}">
												${op:message('M01640')}
											</c:when>
										</c:choose>
										
									</td>
									<td>
										<c:if test="${seller.statusCode == 2}">
											<c:choose>
												<c:when test="${seller.sellerId != '90000000'}">
													<a href="/opmanager/seller/shadow-login?sellerId=${seller.sellerId}" class="btn btn-gradient btn-xs">${op:message('M00796')}</a>
												</c:when>
												<c:otherwise>
													-
												</c:otherwise>
											</c:choose>
										</c:if>
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
					<div class="btn_right mb0">
						<a href="/opmanager/seller/create" class="btn btn-active btn-sm"><span class="glyphicon glyphicon-plus"></span> ${op:message('M01641')}</a> <!-- 업체등록 -->
					</div>
					
					<page:pagination-manager />
					
				</div> <!-- // board_list -->
				
			</div>
			
<style>
td {background: #fff;}
.sortable-placeholder td{height: 100px; background: #d6eafd url("/content/styles/ui-lightness/images/ui-bg_diagonals-thick_20_666666_40x40.png") 50% 50% repeat;
opacity: .5;}



</style>			
