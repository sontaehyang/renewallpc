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

<c:if test="${featured.replyUsedFlag eq 'Y'}">
    <div class="op-event-title" id="reply">
        <p>댓글</p>
    </div>
    <div class="featured-reply-form">
        <p>
            <input type="text" id="replyContent" class="reply-input" />
            <button type="button" id="replySubmit" class="btn_st3 _filter">댓글 등록</button>
        </p>
    </div>
    <table class="featured-reply-list">
        <colgroup>
            <col style="width: 10%;"> <!-- 번호 -->
            <col style="width: auto;"> <!-- 댓글 내용 -->
            <col style="width: 20%;"> <!-- 작성자 -->
            <col style="width: 20%;"> <!-- 작성일 -->
        </colgroup>
        <thead>
        <tr>
            <th scope="col">번호</th>
            <th scope="col">댓글 내용</th>
            <th scope="col">작성자</th>
            <th scope="col">작성일</th>
        </tr>
        </thead>
        <tbody id="tbody">
        <c:forEach var="reply" items="${replyList}" varStatus="i">
            <tr>
                <td>${pagination.itemNumber - i.count}</td>
                <td>${reply.replyContent}</td>
                <td>${reply.userName}</td>
                <td>${reply.created}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <c:if test="${empty replyList}">
        <div class="no_content">
            등록된 댓글이 없습니다.
        </div>
    </c:if>
    <page:pagination-manager />
</c:if>
