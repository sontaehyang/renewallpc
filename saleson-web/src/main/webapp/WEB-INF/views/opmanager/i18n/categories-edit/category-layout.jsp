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

	<c:set var="categoryHeader"></c:set>
	<c:set var="categoryFooter"></c:set>
	
	<c:if test="${ categories.categoryHeader != '' && categories.categoryHeader != null }">
		<c:set var="categoryHeader">background: #ffdcdc;</c:set>
	</c:if>
	
	<c:if test="${ categories.categoryFooter != '' && categories.categoryFooter != null }">
		<c:set var="categoryFooter">background: #ffdcdc;</c:set>
	</c:if>
	
	<div class="m_screen_top">
		GNB
	</div> <!-- // m_screen_top  -->
	
	<div class="m_main_banner_t">
		<a href="/opmanager/categories-edit/popup/html-edit?editPosition=banner&code=${categories.categoryUrl}&editKind=4&editTitle=${op:message('M01543')}" class="edit_popup" rel="800*600" title="${op:message('M01543')}">
			<div class="m_banner_t ${categoryEditPosition.banner}">${op:message('M01543')} <!-- LNB 영역 왼쪽 프리 HTML 영역 --></div>
		</a>
	</div>	<!-- // m_main_banner_t  --> 
			
	<div class="m_main_right">
		
		<div class="m_top_banner">
			${op:message('M01303')} <!-- TOP 공통 배너 --> 	
		</div> <!-- // m_top_banner-->
	
		
		<div class="m_main_left_t">
			<div class="m_lnb_t m_lnb">LNB</div>
			<a href="/opmanager/categories-edit/popup/html-edit?editPosition=html1&code=${categories.categoryUrl}&editKind=4&editTitle=${op:message('M01542')}" class="edit_popup" rel="800*600" title="${op:message('M01304')}">
				<div class="m_html_t ${categoryEditPosition.html1}" style="height: 462px;">${op:message('M01542')} <!-- LNB 영역 아래쪽 프리 HTML 영역 --></div>
			</a>
		</div>	<!-- // m_main_left_t  --> 
								
		<div class="m_main_con_00">
			<a href="/opmanager/categories-edit/popup/header?categoryId=${categories.categoryId }" class="edit_popup" rel="800*600" >
				<div class="m_con01_00" style="${categoryHeader}" >${op:message('M01550')} <!-- 컨텐츠 중앙 프리 HTML 영역 --></div>
			</a>
			<div class="m_con03_00">${op:message('M01187')} <!-- 상품 리스트 --> </div>
			<a href="/opmanager/categories-edit/popup/footer?categoryId=${categories.categoryId }" class="edit_popup" rel="800*600" >
				<div class="m_con02_00" style="${categoryFooter}" >${op:message('M01552')} <!-- 상품 리스트 아래 프리 HTML 영역 --></div>
			</a>
			<div class="m_con03_00">${op:message('M01329')} <!-- 카테고리 목록 (크롤러) --> </div>
			<div class="m_con03_00">${op:message('M00854')} <!-- 최근체크한 상품 -->  </div>
			<div class="m_con03_00">${op:message('M01330')} <!-- 해당 카테고리 상품 랭킹 --> </div>
			<div class="m_con03_00">${op:message('M01310')} <!-- 최근 리뷰 --> </div>
		</div> <!-- // m_main_con_t  --> 
		
		<!-- <div class="m_main_quick2">
			퀵메뉴
		</div> --> <!-- // m_main_quick2 -->
	
	</div> <!-- // m_main_right -->
	
	
	<%-- <div class="m_screen_top">
		GNB
	</div> <!-- // m_screen_top  -->
	
	<div class="m_main_banner_t">
		<a href="/opmanager/categories-edit/popup/html-edit?editPosition=banner&code=${categories.categoryUrl}&editKind=4&editTitle=배너광고 HTML영역" class="edit_popup" rel="800*600" title="배너광고 HTML영역">
			<div class="m_banner_t ${categoryEditPosition.banner}">배너광고<br />
			HTML영역</div>
		</a>
	</div>	<!-- // m_main_banner_t  --> 
			
	<div class="m_main_left_t">
		<div class="m_lnb_t m_lnb">LNB</div>
		<a href="/opmanager/categories-edit/popup/html-edit?editPosition=html1&code=${categories.categoryUrl}&editKind=4&editTitle=HTML영역1" class="edit_popup" rel="800*600" title="HTML영역1">
			<div class="m_html_t ${categoryEditPosition.html1}">HTML영역1</div>
		</a>
	</div>	<!-- // m_main_left_t  --> 
	
	
	<div class="m_main_con_00">
		<a href="/opmanager/categories-edit/popup/header?categoryId=${categories.categoryId }" class="edit_popup" rel="800*600" >
			<div class="m_con02_00" style="${categoryHeader}" >상단이미지</div>
		</a>
		<a href="/opmanager/categories-edit/popup/footer?categoryId=${categories.categoryId }" class="edit_popup" rel="800*600" >
			<div class="m_con02_00 m_con" style="${categoryFooter}" >하단이미지</div>
		</a>
	</div> --%>
