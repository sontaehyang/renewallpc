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


	<div class="location">
		<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
	</div>
	
	<!-- 본문 -->
	<h3><span>상품진열관리</span></h3>
	
	<div class="cnt_area mt30" style="width: 800px;">		
		<%-- <div id="tab_nav" class="tab_nav col6">
			<ul>
				<li class="on"><a href="#activity3">화면관리</a></li>
				<li><a href="#activity4">모바일 화면관리</a></li>
				
				<li class="on"><a href="/opmanager/mobile-category-edit/index?type=main">${op:message('M01078')}</a></li> <!-- 메인 화면 -->
				<li><a href="/opmanager/mobile-category-edit/index?type=categories">${op:message('M01079')}</a></li> <!-- 카테고리 화면 -->
				<li><a href="/opmanager/mobile-category-edit/index?type=etc">${op:message('M00172')}</a></li> <!-- 기타 -->
			</ul>
		</div> --%>
		
		
		<!-- 메인화면 시작-->
		<div id="activity3" class="tab_content on"> 
		
			<div class="screnn_header">
				<div class="banner">카테고리/검색</div> 
			</div><!--// header E-->
			<a href="/opmanager/mobile-category-edit/popup/promotion-banner?editPosition=promotion" class="edit_popup" rel="800*700" title="${op:message('M01305')}">
		 		<div class="promotion">프로모션 이미지 영역</div>
		 	</a>  
			<div class="banner">신제품 </div>
			<a href="/opmanager/mobile-category-edit/popup/html-edit?editPosition=html1" class="edit_popup" rel="800*600" title="${op:message('M01554')}">
				<div class="banner02 promotion">기획전 이미지 영역</div>
			</a>
			<div class="banner02">히트 상품 랭킹</div>
			<div class="banner02">마감임박! 스팟상품</div>
			<div class="banner02">BEST 추천상품</div>
			<div class="banner02">공지사항</div>
			<div class="banner02">고객센터 / 카톡상담</div>
		</div>
		<!-- // 메인화면 끝-->
	</div><!--//cnt_area E-->
		
		
<script type="text/javascript">

	var code = '${categoriesEditParam.code}' != '' ? '${categoriesEditParam.code}' : 'main' ;
	var editKind = '${categoriesEditParam.editKind}' != '' ? '${categoriesEditParam.editKind}' : '1';
	
	
	
	$(function(){
		 
		$("select[name='code']").on("change",function(){
			location.href="/opmanager/mobile-category-edit/index?type=main&code="+$(this).val()+"&editKind="+$(this).find("option:selected").attr("rel");
		});
		
		$(".edit_popup").on("click",function(){
			var popupSize = $(this).attr("rel").split("*");
			Common.popup($(this).attr("href")+"&editTitle="+$(this).attr("title")+"&code="+code+"&editKind="+editKind,"promotion-banner",popupSize[0],popupSize[1], 1);
			return false;
		});
	});
	
	function categoryLayout(){
		location.reload();
	}
</script>
