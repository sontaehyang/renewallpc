<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:if test="${items.size() > 0}">
    <li class="click op-chose-item" id="op-chose-user-${size}" data-index="${size}">
        선택 상품 추가 [총 ${items.size()}건]
        <input type="hidden" name="couponTargetItems[${size}].addType" value="selected">
        <input type="hidden" name="couponTargetItems[${size}].title" value="선택 상품 추가 [총 ${items.size()}건]">
        <c:forEach items="${items}" var="item" varStatus="i">
            <input type="hidden" name="couponTargetItems[${size}].itemIds" value="${item.itemId}">
        </c:forEach>
        &nbsp;<a href="javascript:;" class="fix_btn" style="right: 0;padding:2px 10px;" onclick="chosenItemDelete('op-chose-user-${size}')">삭제</a>
    </li>
</c:if>