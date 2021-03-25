<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

	<!-- 본문 -->
<div class="popup_wrap">
	
	<h1 class="popup_title">발급대상 미리보기</h1>
	
	<div class="popup_contents">
	
		<div class="count_title mt20">
			<h5>검색된 회원수 : <span id="userCount"style="display:inline-block;color:black;">${totalCount}</span></h5>
		</div>

		<form:form modelAttribute="coupon" method="get">
			<form:hidden path="couponTargetUsers" />
			<div class="board_write mt20">
				<table class="board_list_table mt0" summary="${op:message('M00210')}">
					<caption>${op:message('M00210')}</caption>
					<colgroup>
						<col style="width: 15%;">
						<col style="width: 13%;">
						<col style="width: 12%;">
						<col style="width: 12%;">
						<col style="width: 15%;">
					</colgroup>
					<thead>
						<tr>
							<th>${op:message('M00081')}</th> <!-- 아이디 -->
							<th>${op:message('M01192')}</th> <!-- 고객명 -->
							<th>${op:message('M00212')}</th> <!-- 회원등급 -->
							<th>${op:message('M01193')}</th> <!-- 회원그룹 -->
							<th>${op:message('M00125')}</th> <!-- 이메일 -->
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${list}" var="user" varStatus="i">
							<tr>
								<td class="loginId">${user.loginId}</td>
								<td>${user.userName}</td>
								<td>${user.userDetail.levelName}</td> <!-- 회원등급 -->
								<td>${user.userDetail.groupName}</td>
								<td>${user.email}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>

			<c:if test="${empty list}">
				<div class="no_content">
						${op:message('M00251')}
				</div>
			</c:if>
		</form:form>

		<page:pagination-manager />
	</div> <!-- // popup_contents -->
	
	<a href="#" class="popup_close">${op:message('M00353')}</a> <!-- 창 닫기 --> 
</div>

<form:form modelAttribute="coupon" method="post">
    <ul id="op-chosen-user-list" style="display:none">
        <c:if test="${not empty coupon.couponTargetUsers}">
            <c:forEach items="${coupon.couponTargetUsers}" var="target" varStatus="i">
                <li class="click op-chose-user" id="op-chose-user-${i.index}" data-index="${i.index}">
                        ${target.title}
                    <c:choose>
                        <c:when test="${target.addType == 'selected'}">
                            <input type="hidden" name="couponTargetUsers[${i.index}].addType" value="selected" />
                            <input type="hidden" name="couponTargetUsers[${i.index}].title" value="${target.title}" />
                            <c:forEach items="${target.userIds}" var="id">
                                <input type="hidden" name="couponTargetUsers[${i.index}].userIds" value="${id}" />
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <input type="hidden" name="couponTargetUsers[${i.index}].addType" value="search" />
                            <input type="hidden" name="couponTargetUsers[${i.index}].title" value="${target.title}" />

                            <input type="hidden" name="couponTargetUsers[${i.index}].loginId" value="${target.loginId}" />
                            <input type="hidden" name="couponTargetUsers[${i.index}].userName" value="${target.userName}" />
                            <input type="hidden" name="couponTargetUsers[${i.index}].email" value="${target.email}" />
                            <input type="hidden" name="couponTargetUsers[${i.index}].searchReceiveEmail" value="${target.searchReceiveEmail}" />
                            <input type="hidden" name="couponTargetUsers[${i.index}].searchReceiveSms" value="${target.searchReceiveSms}" />
                        </c:otherwise>
                    </c:choose>
                </li>
            </c:forEach>
        </c:if>
    </ul>
</form:form>

<script type="text/javascript">
    $(function() {
        opener.Common.loading.hide();
    });

    $(window).unload(function() {
        opener.Common.loading.hide();
    });

    function paging(page) {
        $('#coupon').attr('action', '?page=' + page).submit();
    }
</script>