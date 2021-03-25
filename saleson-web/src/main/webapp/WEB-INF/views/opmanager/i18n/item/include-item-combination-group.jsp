<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<div id="itemOption${index}" class="combination-option-wrapper" data-index="${index}">
    <div class="item-combination-option-title">
        <input type="text" name="op-option-name" class="required-item-option-combination" title="옵션명" maxlength="200" value="${group.title}" />
        <span class="check-box">
            <input type="radio" id="op-option-display-type1-${index}" name="op-option-display-type${index}" class="required-item-option-combination" title="구분" value="fixing" ${op:checked('fixing', group.displayType)} checked="checked" /> <label for="op-option-display-type1-${index}">기본</label>
            <input type="radio" id="op-option-display-type2-${index}" name="op-option-display-type${index}" class="required-item-option-combination" title="구분" value="select" ${op:checked('select', group.displayType)} /> <label for="op-option-display-type2-${index}">옵션</label>
        </span>
        <button type="button" class="btn btn-dark-gray btn-sm remove-combination-option-group">- 그룹삭제</button>
    </div>

    <div class="item-combination-option-contents">
        <table class="inner-table tbl-option active">
            <colgroup>
                <col style="" />
                <col style="width: 120px" />
                <col style="" />
                <col style="" />
                <col style="width: 70px" />
                <col style="" />
                <col style="" />
                <col style="" />
                <col style="" />
                <col style="" />
            </colgroup>
            <thead>
                <tr>
                    <th>상품명</th>
                    <th>관리코드</th>
                    <th>추가금액</th>
                    <th>원가</th>
                    <th>판매수량</th>
                    <th>재고연동</th>
                    <th>재고수량</th>
                    <th>판매상태</th>
                    <th>노출여부</th>
                    <th>삭제</th>
                </tr>
            </thead>
            <tbody class="item-combination-options sortable_item_combination_option">
                <c:set var="combinationOptionCount" value="0" />
                <c:forEach items="${item.itemOptions}" var="itemCombinationOption" varStatus="j">
                    <c:if test="${itemCombinationOption.optionType == 'C' && group.title == itemCombinationOption.optionName1}">
                        <c:set var="combinationOptionCount" value="${combinationOptionCount + 1}" />
                        <c:set var="itemCombinationOption" value="${itemCombinationOption}" scope="request" />

                        <jsp:include page="include-item-combination-option.jsp" />
                    </c:if>
                </c:forEach>

                <c:if test="${combinationOptionCount == 0}">
                    <jsp:include page="include-item-combination-option.jsp" />
                </c:if>
            </tbody>
        </table>
        <div class="item-options-info">
            <button type="button" class="btn btn-dark-gray btn-sm" onClick="Shop.findItem('itemOption${index}')"><span class="glyphicon glyphicon-search"></span> 조회</button>
            <button type="button" class="btn btn-gradient btn-sm add-item-combination-option"><span>+ 옵션추가</span></button>
        </div>
    </div>
</div>