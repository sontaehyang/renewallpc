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
			<a href="/" class="home"><span class="hide">home</span></a>
			<span>베스트상품</span>
		</div>
	</div><!-- // location_area E --> 
	
	<div id="contents" class="pt10">
		<div>
			<img src="/content/images/common/event_visual_03.jpg" alt="배너이미지">
		</div>
		
		<div class="item-list thumb event new mt15">
			 <ul class="list-inner">
			 	<jsp:include page="../include/item-best-list.jsp"/>
			 </ul>
		</div>
	</div>
</div><!-- // inner E -->

<jsp:include page="../include/layer-cart.jsp" />
<jsp:include page="../include/layer-wishlist.jsp" />