<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>

	<div class="title">
		<h2>검색결과</h2>
		<span class="his_back"><a href="javascript:void(0);" class="ir_pm" onclick="history.back();">뒤로가기</a></span>
	</div>
	<div class="product_wrap">
		<div class="search_result_txt none">
			<p><span>‘${itemParam.query}’</span>에 대한 검색결과가 없습니다.</p>
			<div class="result_none">
				<ul>
					<li>검색어가 올바르게 입력되었는지 확인 해주세요.</li>
					<li>두 단어 이상의 검색어인 경우, 띄어쓰기를 확인해 주세요.</li>
					<li>검색어가 올바르게 입력되었는지 확인 해주세요.</li>
					<li>상품명을 모르시면 상품과 관련된 단어를 입력해 보세요.</li>
				</ul>
			</div>
			<!-- //search_result_none -->
		</div>
		<!-- //search_result_txt -->
	</div>
	<!-- //product_wrap -->

<!-- 카카오픽셀 설치 [검색 이벤트 전송] -->
<script type="text/javascript" charset="UTF-8" src="//t1.daumcdn.net/adfit/static/kp.js"></script>
<script type="text/javascript">
	kakaoPixel('1612698247174901358').pageView();
	kakaoPixel('1612698247174901358').search({
		keyword: '${itemParam.query}'
	});
</script>

<!-- *) 쇼핑몰에서 검색을 이용한 제품찾기 페이지 -->
<script language='javascript'>
	var m_skey='${itemParam.query}';
</script>
