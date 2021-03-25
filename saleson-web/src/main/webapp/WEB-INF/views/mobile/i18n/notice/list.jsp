<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

		<div id="container">
			<div class="title">
				<h2>고객센터</h2>
				<span class="his_back"><a href="javascript:void(0);" class="ir_pm" onclick="history.back();">뒤로가기</a></span>
				<ul class="tab_list01">
					<li class="on"><a href="/m/notice/list">공지사항</a></li>
					<li><a href="/m/faq">FAQ</a></li>
					<li><a href="/m/qna">1:1문의</a></li>
				</ul>
			</div><!-- // title E -->
			
			<form:form modelAttribute="noticeParam" method="post">
				<form:hidden path="page" />
			</form:form>
			<div class="con">
				<div class="customer_wrap">
					<div class="notice_list">
						<div class="list">
							<ul id="list-data">
								<jsp:include page="../include/notice-list.jsp" />
							</ul>
						</div><!-- // list E -->
			 
						<div class="load_more" id="list-more">
							<button type="button" class="btn_st2">
								<a href="javascript:paginationMore('notice')"><span>더보기</span></a>
							</button>
						</div><!-- //load_more E -->
			 		</div><!-- //notice_list E -->
				</div><!-- //customer_wrap E -->
			</div><!-- //con E -->
		</div><!--// #customer E-->

<page:javascript>	
<script type="text/javascript">

$(function() {
	
	// 더보기 버튼
	showHideMoreButton();
});



function paginationMore(key) {
	
	$('input[name="page"]', $("#noticeParam")).val(Number($('input[name="page"]', $("#noticeParam")).val()) + 1);
 	$.post('/m/notice/list/notice-list', $("#noticeParam").serialize(), function(html) {
 		$("#list-data").append(html);
 		// 더보기 버튼 
 		showHideMoreButton();
 		
 		history.pushState(null, null, "/m/notice/list?" + $("#noticeParam").serialize());
 	});
	
}

function showHideMoreButton() {
	var totalItems = Number('${pagination.totalItems}');
	if ($("#list-data").find(' > li').size() == totalItems || totalItems == 0) {
		$('#list-more').hide();
	}
}
</script>
</page:javascript>