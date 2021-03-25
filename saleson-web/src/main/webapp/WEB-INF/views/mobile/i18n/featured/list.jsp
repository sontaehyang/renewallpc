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

<!-- 내용 : s -->
<div class="con exhibitions_wrap">
	<div class="event_wrap" id="op-list-data">
		<jsp:include page="../include/pages-item-list.jsp"></jsp:include>
	</div>
	<!-- //event_wrap -->
	
	<div class="load_more" id="op-list-more">
		<button type="button" class="btn_st2" onclick="javascript:paginationMore('${featuredParam.featuredClass == 1 ? 'featured' : 'event'}')"><span>더보기</span></button>
	</div>

	<input type="hidden" name="page" value="1">

</div>
<!-- 내용 : e -->

<page:javascript>
<script type="text/javascript">
$(function() {
	var pageType = '${featuredParam.featuredClass == 1 ? 'featured' : 'event'}';
	
	// 상단 탭메뉴 선택
	Mobile.mainTabSelected(pageType);
	
	// 이전페이지로 돌아왔을 때 More Data 유지.
	Mobile.pagination.init(pageType);
	
	// 더보기 버튼
	showHideMoreButton();
});

var currentPage = 1;
function paginationMore(key) {

	$('input[name="page"]').val(Number($('input[name="page"]').val()) + 1);
	currentPage = $('input[name="page"]').val();
	$.post('/m/' + key + '/list', $("#featuredParam").serialize() + '&page=' + currentPage, function(html) {
		$("#op-list-data").append(html);
		// 더보기 버튼 
		showHideMoreButton();
		Mobile.pagination.set(key);
	});
}

function showHideMoreButton() {
	var totalItems = Number('${pagination.totalItems}');

	if ($("div#op-list-data").find(' > a').size() == totalItems || totalItems == 0) {
		$('#op-list-more').hide();
	}
}

</script>
</page:javascript>