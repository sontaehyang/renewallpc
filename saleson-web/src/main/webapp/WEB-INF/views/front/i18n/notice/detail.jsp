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

<div class="inner">
	<div class="location_area">
		<div class="breadcrumbs">
			<a href="#" class="home"><span class="hide">home</span></a>
			<a href="#">고객센터</a>
			<span>공지사항</span> 
		</div>
	</div><!-- // location_area E --> 

	<div id="contents">
 		<jsp:include page="/WEB-INF/views/layouts/front/inc_lnb_customer.jsp" />  
	 	<div class="contents_inner"> 
			<h2>공지사항</h2> 
			<div class="board_wrap"> 
		 		<div class="board_view">
	                <div class="stit">
						<dl class="tit">
							<dt class="hide">제목</dt>
	                    	<dd>${notice.subject}</dd> 
						</dl>
						<dl class="tright">
							<dt class="hide">등록일</dt>
							<dd class="dd01">${op:date(notice.createdDate)}</dd>
							<dt class="hide">조회수</dt>
							<dd>${op:numberFormat(notice.hits)}</dd>
						</dl>
	                </div> 
	                <dl class="cont">
	                	<dt class="hide">내용</dt>
	                	<dd> 
	                        ${notice.content}  
	                    </dd>
	                </dl>
	            </div>	 <!-- // board_view E -->		 	
			</div><!--// board_wrap E-->
			
		 	<dl class="prevnext">
	            <dt class="first">이전글</dt>
	            <c:if test="${beforeNotice.noticeId != 0}">
					<dd class="first"><a href="/notice/view/${beforeNotice.noticeId}">${beforeNotice.subject}</a> <span>${op:date(beforeNotice.createdDate)}</span></dd>
				</c:if>
				<c:if test="${beforeNotice.noticeId == 0}">
					<dd class="first">이전 글이 존재하지 않습니다.</dd>
				</c:if> 
	             <dt>다음글</dt>
	            <c:if test="${afterNotice.noticeId != 0}">
					<dd><a href="/notice/view/${afterNotice.noticeId}">${afterNotice.subject}</a> <span>${op:date(afterNotice.createdDate)}</span></dd>
				</c:if>
				<c:if test="${afterNotice.noticeId == 0}">
					<dd>다음 글이 존재하지 않습니다.</dd>
				</c:if>
	        </dl>
	        
	        <div class="btn_wrap"> 
		 		<a href="/notice/list" class="btn btn-success btn-lg" title="목록보기">목록보기</a>
		 	</div>  
		</div><!--// contents_inner E-->
	</div><!--// contents E--> 
</div><!-- // inner E -->	
