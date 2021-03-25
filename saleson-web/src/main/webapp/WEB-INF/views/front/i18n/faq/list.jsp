<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<style type="text/css">
	.panel img {max-width: 100%;}
</style>

<div class="inner">
	<div class="location_area">
		<div class="breadcrumbs">
			<a href="/" class="home"><span class="hide">home</span></a>
			<a href="/notice/list">고객센터</a>
			<span>FAQ</span> 
		</div>
	</div>

	<div id="contents">
 		<jsp:include page="/WEB-INF/views/layouts/front/inc_lnb_customer.jsp" />
	 	<div class="contents_inner">    
			<h2>FAQ</h2> 
			<div class="board_info"> 
				<div>
					<form:form modelAttribute="faqDto" action="/faq" method="get">
						<form:hidden path="faqType"/>
						<input id="where" name="where" value="ALL" type="hidden">
						<form:input path="query" title="검색어입력" />
						<button type="submit" class="btn btn-m btn-submit">검색</button>
					</form:form> 
				</div> 
			</div> 
		 	<div class="faq_tab">
		 		<ul>
		 			<c:forEach var="faqType" items="${faqTypes}" varStatus="i">
						<li ${faqDto.faqType == faqType.code ? 'class="on"' : ''}><a href="/faq?faqType=${faqType.code}" >${faqType.title}</a></li>
					</c:forEach>
		 		</ul>
		 	</div>
		 	
		 	<div class="faq_list">
				<ul id="faq">
                    <c:forEach items="${pageContent.content}" var="list" varStatus="i">
						<li>
							<div class="list op-faq-click"  data-id="${list.id}">
								<span class="q"></span><span class="txt">${list.title}</span>
							</div> 
							<div class="panel">
								${list.content}
							</div>
						</li>
					</c:forEach>
				</ul>
			</div> 
		  
			<page:pagination-jpa />
		</div>
	</div>
</div>


<page:javascript>
<script type="text/javascript">
    $(function() {

        // 조회수
        $('.op-faq-click').on('click', function(e) {
            e.preventDefault();
            var id = $(this).data('id');

            $.post('/faq/' + id + '/hit', {}, function(response) {
                //
            });
        });
    });
</script>
</page:javascript>