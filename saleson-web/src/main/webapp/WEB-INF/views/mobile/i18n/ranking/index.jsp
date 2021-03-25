<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<div class="event">

	<h3 class="title">히트 상품 랭킹</h3>	
	<ul class="rank_menu">
		<li><a href="/m/ranking/cate00team_esthetic/#" class="${code == 'esthetic' ? 'on' : 'off'}">에스테틱</a></li>
		<li><a href="/m/ranking/cate00team_nail/#" class="${code == 'nail' ? 'on' : 'off'}">네일</a></li>
		<li><a href="/m/ranking/cate00team_matsuge_extension/#" class="${code == 'matsuge_extension' ? 'on' : 'off'}">속눈썹</a></li>
		<li><a href="/m/ranking/cate00team_hair/#" class="${code == 'hair' ? 'on' : 'off'}">헤어</a></li>
	</ul>
	
	<div id="list" class="ranking_list">
		<ul id="list-data" class="list_item">	
			<jsp:include page="../include/item-list.jsp" />		
		</ul>
	</div>
		
</div><!--//rangking E-->
