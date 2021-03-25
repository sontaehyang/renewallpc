<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>			
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>	
	
 
	 
	<div id="lnb"> 
		<%--<h2>회사소개</h2> --%>
		<div class="category_team pages"> 
			<ul class="depth_02">
				<li class="has-sub">
					<a href="#" class="on">회사소개</a>	
					<ul class="depth_03">
						<li><a href="/pages/etc_company">회사개요</a></li> 
						<li><a href="/pages/etc_company2">전시회 출전 경력</a></li> 
					</ul><!--//3depth E--> 
				</li> 
				<li class="default"><a href="/pages/etc_clause">이용약관</a> </li>
				<li class="default"><a href="/pages/etc_protect">개인정보보호정책</a> </li> 
				<li class="default"><a href="/pages/etc_guide">쇼핑가이드</a> </li>
			</ul><!--//2depth E-->
		</div><!--//category_team E-->   
	</div> 	<!--//lnb E-->  
	
	  
 
				
