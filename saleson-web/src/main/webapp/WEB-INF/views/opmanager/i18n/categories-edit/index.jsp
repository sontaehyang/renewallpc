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
	<h3><span>화면관리</span></h3>


<%--	
		<div class="cnt_area mt30">		
			
			<c:if test="${categoriesEditParam.editKind == '1' || categoriesEditParam.editKind == '' || categoriesEditParam.editKind == null}">
				<!-- 메인화면 시작-->
				<div id="activity3" class="tab_content on" style="width: 800px;">  
					<div class="screnn_header">
						<a href="javascript:Common.popup('/opmanager/categories-edit/top-banner/top', 'promotion-banner', '800', '700', '1');">
							<div class="banner promotion">TOP 공통 배너 영역</div>
						</a>
						<div class="top">
							<div>로고</div>
							<div>통합검색</div>
							<a href="javascript:Common.popup('/opmanager/categories-edit/top-banner/right', 'promotion-banner', '800', '700', '1');">
								<div class="promotion">우측 공통 배너 영역</div>
							</a>
						</div>
					</div><!--// header E-->
				 	
				 	<div class="category">
				 		<div class="left">카테고리</div>
				 		<div class="right">
				 			<a href="/opmanager/categories-edit/popup/promotion-banner?editPosition=promotion" class="edit_popup" rel="800*700" title="${op:message('M01540')}">
				 				<div class="promotion">프로모션 배너영역</div>
				 			</a>
				 			<div>MD Choice! 추천상품 </div>
				 		</div>
				 	</div>
					
					<a href="javascript:Common.popup('/opmanager/categories-edit/featured-banner/list', 'promotion-banner', '1000', '700', '1');">
						<div class="banner promotion">기획전 배너 영역</div>
					</a>
					<div class="banner02">신상품</div>
					<div class="banner02">히트 상품 랭킹</div>
					<div class="banner02">마감임박! 스팟세일</div>
					<div class="banner02">BEST 추천상품</div>
					<a href="/opmanager/categories-edit/popup/html-edit?editPosition=recommend" class="edit_popup" rel="800*600" title="${op:message('M01311')} ">
						<div class="banner02 promotion">회사소개 프리 HTML 영역</div>
					</a>
					<div class="banner02">공지사항 / 온라인입금계좌 / 고객센터 / 카톡상담</div>
				</div>
				<!-- // 메인화면 끝-->
			</c:if>
			
		</div><!--//cnt_area E-->
 --%>	

<style>
.bg_site {
	position: relative;
	width: 900px;
	height: 2122px;
	border: 1px solid #ccc;
	background: url(/content/opmanager/images/common/bg_site_80.jpg) no-repeat 0 0;
}
.bg_site a {
	position: absolute;
	left: 85px;
	width: 730px;
	border: 5px solid #999;
	text-align: center;
	text-decoration: none;
	padding-top: 10px;
	background: #ffdcdc;
	opacity: 0.8;
	color: #000;
	font-size: 13px;
	font-weight: bold;
}

.bg_site a:hover {
	border: 5px solid #ff6600;
	background: #fff;
	opacity: 0.8;
}
.bg_site a.ds-top {
	height: 69px;
}
.bg_site a.ds-top-right {
	width: 200px;
	height: 64px;
	left: 615px;
	top: 89px;
}

.bg_site a.ds-left {
	width: 100px;
	height: 200px;
	left: 5px;
	top: 250px;
}

.bg_site a.ds-promotion {
	left: 208px;
	top: 154px;
	width: 487px;
	height: 300px;
}

.bg_site a.ds-featured {
	top: 611px;
	height: 257px;
}

.bg_site a.ds-new {
	top: 878px;
	height: 210px;
}

.bg_site a.ds-ranking {
	top: 1099px;
	height: 250px;
}

.bg_site a.ds-best {
	top: 1384px;
	height: 479px;
}

</style>
		<div class="bg_site">
			<a href="javascript:Common.popup('/opmanager/categories-edit/top-banner/top', 'promotion-banner', '800', '700', '1');" class="ds-top">TOP 공통 배너 영역</a>
			<a href="javascript:Common.popup('/opmanager/categories-edit/top-banner/right', 'promotion-banner', '800', '700', '1');" class="ds-top-right">우측 공통 배너 영역</a>
			<a href="javascript:Common.popup('/opmanager/categories-edit/popup/left-banner', 'promotion-banner', '800', '700', '1');" class="ds-left">왼쪽 배너 영역</a>
			<a href="/opmanager/categories-edit/popup/promotion-banner?editPosition=promotion" class="ds-promotion edit_popup" rel="800*700">프로모션 배너 영역</a>
			<a href="javascript:Common.popup('/opmanager/categories-edit/featured-banner/list', 'promotion-banner', '1000', '700', '1');" class="ds-featured">기획전 배너 영역</a>
			<a href="/opmanager/main-display/new" class="ds-new">신상품 영역</a>
			<a href="/opmanager/ranking/list" class="ds-ranking">히트 상품 랭킹 영역</a>
			<a href="/opmanager/main-display/best" class="ds-best">BEST 추천 상품 영역</a>	
		</div>
	
<script type="text/javascript">

	var code = '${categoriesEditParam.code}' != '' ? '${categoriesEditParam.code}' : 'main' ;
	var editKind = '${categoriesEditParam.editKind}' != '' ? '${categoriesEditParam.editKind}' : '1';
	
	
	
	$(function(){
		 
		$("select[name='code']").on("change",function(){
			location.href="/opmanager/categories-edit/index?type=main&code="+$(this).val()+"&editKind="+$(this).find("option:selected").attr("rel");
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
