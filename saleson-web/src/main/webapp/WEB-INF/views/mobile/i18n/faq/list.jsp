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
			<li><a href="/m/notice/list">공지사항</a></li>
			<li class="on"><a href="/m/faq" class="on">FAQ</a></li>
			<li><a href="/m/qna">1:1문의</a></li>
		</ul>
	</div>
	<div class="con">
		<div class="customer_wrap">
			<div class="faq_list">
				<div class="select">
					<select name="faq" id="faqType">
                        <c:forEach var="faqType" items="${faqTypes}" varStatus="i">
							<option value="${faqType.code}" ${faqType.code == faqDto.faqType ? 'selected="selected"':"" }>${faqType.title}</option>
						</c:forEach>
					</select>
				</div><!--// select E -->
				<div class="list">
					<ul>
						<jsp:include page="../include/faq-list.jsp" />
					</ul>
				</div><!--// list E -->
			</div><!--// faq_list E -->
		</div><!--// customer_wrap E -->
	</div><!--// con E -->
</div><!--// container E -->

<page:javascript>
<script type="text/javascript">
$(function() {
	$('#faqType').on('change', function(){
		var value = $(this).find('option:selected').val();
		location.href="/m/faq?faqType="+value;
	});

	// 조회수
    $('.op-faq-click').on('click', function(e) {
        e.preventDefault();
        var id = $(this).data('id');

        $.post('/m/faq/' + id + '/hit', {}, function(response) {
            //
        });
    });
});


</script>
</page:javascript>