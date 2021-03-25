<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>


		<div class="location">
			<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
		</div>
		
		
		
		
		<div>
		<form name="loginWeb" action="/opmanager/user-login-banner/loginWeb" method="post" enctype="multipart/form-data">
			<input type="hidden" name="categoryEditId" value="${loginBannerWeb.categoryEditId}"/>
			<h3>PC 로그인 배너</h3>
			<table class="board_write_table">
				<colgroup>
					<col style="width: 180px;" />
					<col style="width: auto;" />
				</colgroup>
				<tbody>
					<tr>
						<td class="label">${op:message('M00413')}</td><!-- URL -->
						<td>
							<div>
								<input type="text" name="editUrl" class="nine" value="${loginBannerWeb.editUrl}" title="${op:message('M00413')}" />
							</div>
						</td>
					</tr>
					
					<tr>
						<td class="label">${op:message('M00412')} <br>(이미지 사이즈)</td> <!-- 이미지 업로드 --> 
						<td>
							<div>
								<input name="editImages" type="file" title="${op:message('M00417')}"/> <!-- 이미지 파일 --> 
							</div>
						</td>
					</tr>
					
					<c:if test="${!empty loginBannerWeb.editImage }">
						<tr>
							<td class="label">${op:message('M00416')}</td> <!-- 현재 이미지 -->
							<td>
								<div>
									<img src="/upload/loginbanner/${loginBannerWeb.categoryEditId }/${loginBannerWeb.code}/${loginBannerWeb.editImage}" alt="${op:message('M00659')}" width="300px" height="100px"/> <br/> <!-- 상품이미지 --> 
								</div>
								<div>
									${loginBannerWeb.editImage}&nbsp;&nbsp;<a href="/opmanager/user-login-banner/imgDelete/${loginBannerWeb.categoryEditId }/${loginBannerWeb.code}" class="table_btn">${op:message('M01511')}</a> <!-- 파일 삭제 -->
								</div>
							</td>
						</tr>
					</c:if>
					
					<tr>
						<td colspan="2">
							<div class="btn_center">
								<button type="submit" class="btn btn-active"> 저장 </button> 
							</div>
						</td>
					</tr>
					
				</tbody>
			</table>
			</form>
		</div>
		
		<br/><br/><br/>
		
		
		<div>
			<form name="loginMobile" action="/opmanager/user-login-banner/loginMobile" method="post" enctype="multipart/form-data">
			<input type="hidden" name="categoryEditId" value="${loginBannerMobile.categoryEditId}"/>
			<h3>MOBILE 로그인 배너</h3>
			<table class="board_write_table">
				<colgroup>
					<col style="width: 180px;" />
					<col style="width: auto;" />
				</colgroup>
				<tbody>
					<tr>
						<td class="label">${op:message('M00413')}</td><!-- URL -->
						<td>
							<div>
								<input type="text" name="editUrl" class="nine" value="${loginBannerMobile.editUrl }" title="${op:message('M00413')}" />
							</div>
						</td>
					</tr>
					
					<tr>
						<td class="label">${op:message('M00412')} <br>(이미지 사이즈)</td> <!-- 이미지 업로드 --> 
						<td>
							<div>
								<input type="file" name="editImages" title="${op:message('M00417')}"/> <!-- 이미지 파일 --> 
							</div>
						</td>
					</tr>
						 
					<c:if test="${!empty loginBannerMobile.editImage }">
						<tr>
							<td class="label">${op:message('M00416')}</td> <!-- 현재 이미지 -->
							<td>
								<div>
									<img src="/upload/loginbanner/${loginBannerMobile.categoryEditId }/${loginBannerMobile.code}/${loginBannerMobile.editImage}" alt="${op:message('M00659')}" width="300px" height="100px"/> <br/> <!-- 상품이미지 --> 
								</div>
								<div>
									${loginBannerMobile.editImage}&nbsp;&nbsp;<a href="/opmanager/user-login-banner/imgDelete/${loginBannerMobile.categoryEditId }/${loginBannerMobile.code}" class="table_btn">${op:message('M01511')}</a> <!-- 파일 삭제 -->
								</div>
							</td>
						</tr>
					</c:if>
					
					<tr>
						<td colspan="2">
							<div class="btn_center">
								<button type="submit" class="btn btn-active"> 저장 </button> 
							</div>
						</td>
					</tr>
				</tbody>
			</table>
			</form>
		</div>		 
		