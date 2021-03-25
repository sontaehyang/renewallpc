<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="op" uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop" uri="/WEB-INF/tlds/shop" %>

<div>
    <div>
        <button type="button" class="btn btn-default btn-sm" id="commentAlimTalkTemplate">문의등록</button>
    </div>
    <c:if test="${!empty templateComments}">
        <table class="board_list_table">
            <colgroup>
                <col style="width: 80px;"/>
                <col style="width: auto;"/>
                <col style="width: 80px;"/>
                <col style="width: 150px;"/>
            </colgroup>
            <thead>
                <tr>
                    <th>작성자</th>
                    <th>댓글내용</th>
                    <th>댓글상태</th>
                    <th>등록일</th>
                </tr>
            </thead>
            <tbody id="alimTalkTemplateButtonArea">
                <c:forEach var="comment" items="${templateComments}" varStatus="i">
                    <tr>
                        <td>${comment.userName}</td>
                        <td>${op:nl2br(comment.content)}</td>
                        <td>${comment.statusTitle}(${comment.status})</td>
                        <td>${comment.createdAt}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>