<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>

<div class="inner">
	<div class="location_area">
		<div class="breadcrumbs">
			<a href="#" class="home"><span class="hide">home</span></a>
			<a href="#">회사소개</a>
			<span>이용약관</span>
		</div>
	</div><!-- // location_area E -->

 	<div id="contents">
	 	<jsp:include page="/WEB-INF/views/layouts/front/inc_lnb_intro.jsp" />
	 	<div class="contents_inner">
			<h2 class="mt30">이용약관</h2>
			<select class="combobox">
				<%--<option>2018.11.28일 시행일자</option>--%>
				<c:forEach var="periodList" items="${periodList}">
					<option <c:if test="${periodList.policyId eq policy.policyId }"> selected="selected"</c:if> value="${periodList.policyId}">
							${periodList.title}
					</option>
				</c:forEach>
			</select>
			<div class="individual">
				${policy.content}
			</div><!-- // individual E -->
		</div><!-- // contents_inner E -->
	</div>
</div><!-- // company E -->

<page:javascript>
	<script type="text/javascript">
        $(function() {
            /*$('#policyList').on('change', function() {
                location.href = '/pages/etc_clause?id='+$(this).val();
            });*/
        });

        $('.combobox').on("change", function() {
            var policyId = $(this).val();

            $.post("/detail-policy-data", {"policyId" : policyId}, function(response) {
                var policyContent = response.data.content;

                $('.individual').empty();
                $('.individual').append(policyContent);

            });
        });

	</script>
</page:javascript>