<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<c:set var="isDisabled" value="${!empty itemCombinationOption.erpItemCode ? true : false}" />

<tr>
    <td>
        <input type="hidden" name="optionId" value="${itemCombinationOption.itemOptionId}" />
        <input type="hidden" name="erpItemCode" value="${itemCombinationOption.erpItemCode}" />
        <input type="hidden" name="optionType" value="C" />
        <input type="hidden" name="optionName1" value="${itemCombinationOption.optionName1}" />
        <input type="hidden" name="optionName3" value="" />
        <input type="hidden" name="optionDisplayType" value="${itemCombinationOption.optionDisplayType}" />
        <input type="hidden" name="optionOrdering" value="${itemCombinationOption.optionOrdering}" />

        <input type="text" name="optionName2" maxlength="200" class="required-item-option-combination" title="상품명" value="${itemCombinationOption.optionName2}" />
    </td>
    <td><input type="text" name="optionStockCode" maxlength="80" title="관리코드" value="${itemCombinationOption.optionStockCode}" readonly="readonly" /></td>
    <td><input type="text" name="optionPrice" value="${itemCombinationOption.optionPrice}" class="_number_comma" readonly="readonly" /></td>
    <td><input type="text" name="optionCostPrice" value="${itemCombinationOption.optionCostPrice}" class="_number_comma" ${isDisabled ? 'readonly="readonly"' : ''} /></td>
    <td><input type="text" name="optionQuantity" maxlength="5" value="${itemCombinationOption.optionQuantity}" title="옵션 판매수량" class="_number_comma" /></td>
    <td>
        <select name="optionStockFlag">
            <option value="N" ${op:selected('N', itemCombinationOption.optionStockFlag)} ${isDisabled && itemCombinationOption.optionStockFlag == 'Y' ? 'disabled="disabled"' : ''}>무제한</option>
            <option value="Y" ${op:selected('Y', itemCombinationOption.optionStockFlag)} ${isDisabled && itemCombinationOption.optionStockFlag == 'N' ? 'disabled="disabled"' : ''}>재고연동</option>
        </select>
    </td>
    <td><input type="text" name="optionStockQuantity" maxlength="5" value="${itemCombinationOption.optionStockQuantity < 0 ? '' : itemCombinationOption.optionStockQuantity}" readonly="realonly" title="옵션 재고수량" class="_number_comma" /></td>
    <td>
        <select name="optionSoldOutFlag">
            <option value="N" ${op:selected('N', itemCombinationOption.optionSoldOutFlag)} ${isDisabled && itemCombinationOption.optionSoldOutFlag == 'Y' ? 'disabled="disabled"' : ''}>판매가능</option>
            <option value="Y" ${op:selected('Y', itemCombinationOption.optionSoldOutFlag)} ${isDisabled && itemCombinationOption.optionSoldOutFlag == 'N' ? 'disabled="disabled"' : ''}>품절</option>
        </select>
    </td>
    <td>
        <select name="optionDisplayFlag">
            <option value="Y" ${op:selected('Y', itemCombinationOption.optionDisplayFlag)}>노출</option>
            <option value="N" ${op:selected('N', itemCombinationOption.optionDisplayFlag)}>숨김</option>
        </select>
    </td>
    <td><a href="#" class="btn btn-dark-gray btn-sm delete-item-combination-option">삭제</a></td>
</tr>