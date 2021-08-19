<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>

<div class="inner">

	<div class="location_area">
		<div class="breadcrumbs">
			<a href="/" class="home"><span class="hide">home</span></a>
			<span>검색결과</span> 
		</div>
	</div><!-- // location_area E -->
	
	<div id="contents">
		<div class="search_result"> 
			<p class="tit"><span class="color_23ade3">‘${itemParam.query}’</span> 에 대한 검색결과</p>
			<p class="total">총 <strong>0</strong>개 상품</p> 
		</div>
		<div class="guide_box search">
		 	<ul>
		 		<li>검색어가 올바르게 입력되었는지 확인 해주세요.</li>
				<li>두 단어 이상의 검색어인 경우, 띄어쓰기를 확인해 주세요. (예: 일회용속옷 → 일회용 속옷)</li> 
				<li>상품명을 모르시면 상품과 관련된 단어를 입력해 보세요.</li>
				<li>상품이 품절되었을 경우 검색이 되지 않을 수 있습니다.</li>
			</ul>	
		</div>
	</div><!-- // contents E --> 
	
</div>

<!-- 카카오픽셀 설치 [검색 이벤트 전송] -->
<script type="text/javascript" charset="UTF-8" src="//t1.daumcdn.net/adfit/static/kp.js"></script>
<script type="text/javascript">
	kakaoPixel('1612698247174901358').pageView();
	kakaoPixel('1612698247174901358').search({
		keyword: '${itemParam.query}'
	});
</script>


<!-- *) 쇼핑몰에서 검색을 이용한 제품찾기 페이지 -->
<!-- AceCounter eCommerce (Product_Search) v8.0 Start -->
<script language='javascript'>
	var _skey='${itemParam.query}';
</script>
<!-- AceCounter eCommerce (Product_Search) v8.0 End -->
