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

	<c:set var="categoryMobileHtml"></c:set>
	
	<c:if test="${ categories.categoryMobileHtml != '' && categories.categoryMobileHtml != null }">
		<c:set var="categoryMobileHtml">background: #ffdcdc;</c:set>
	</c:if>
	
	
	<div class="m_main_right">
					
		<div class="m_main_con_t" style="float: none; margin: auto;">
			<div class="m_con03_t">${op:message('M01218')} <!-- 상품목록 --></div>
			<div class="m_con03_t">${op:message('M01555')} <!-- 카테고리에서 검색 --></div>
			<a href="/opmanager/mobile-category-edit/popup/header?categoryId=${categories.categoryId }" class="edit_popup" rel="800*600" >
				<div class="m_con01_t m_con" style="${categoryMobileHtml}"  >${op:message('M01554')} <!-- 프리 HTML 영역 --></div>
			</a> 
			<div class="m_con03_t">${op:message('M00854')} <!-- 최근체크한상품 --></div>
		</div> <!-- // m_main_con_t  --> 
		
	</div> <!-- // m_main_right -->
