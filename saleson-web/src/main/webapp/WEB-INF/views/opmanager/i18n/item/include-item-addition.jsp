<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<tr>
	<td>
		<input type="hidden" name="additionItemId" value="${itemAddition.itemId}" />
		<input type="hidden" name="additionWeight" value=""	/>
		<input type="hidden" name="additionItemImage" value="${itemAddition.itemImage}" />

		<input type="text" name="additionItemName" value="${itemAddition.itemName}" class="required-item-addition" title="추가구성 상품명" />
	</td>
	<td><input type="text" name="additionStockCode" value="${itemAddition.stockCode}" class="" /></td>
	<td><input type="text" name="additionSalePrice" value="${itemAddition.salePrice}" class="required-item-addition _min_10 _number_comma" title="추가구성 상품가격" /></td>
	<td><input type="text" name="additionCostPrice" value="${itemAddition.costPrice}" class="_number_comma" /></td>
	<td>
		<select name="additionStockFlag">
			<option value="N" ${op:selected('N', itemAddition.stockFlag)}>무제한</option>
			<option value="Y" ${op:selected('Y', itemAddition.stockFlag)}>재고연동</option>
		</select>
	</td>
	<td><input type="text" name="additionStockQuantity" value="${itemAddition.stockQuantity}" readonly="readonly" class="_number_comma" title="추가 구성 상품 재고수량" /></td>
	<td>
		<select name="additionSoldOut">
			<option value="0" ${op:selected('0', itemAddition.soldOut)}>판매가능</option>
			<option value="1" ${op:selected('1', itemAddition.soldOut)}>품절</option>
		</select>
	</td>
	<td>
		<select name="additionTaxType">
			<option value="1" ${op:selected('1', itemAddition.taxType)}>과세상품</option>
			<option value="2" ${op:selected('2', itemAddition.taxType)}>면세상품</option>
		</select>
	</td>
	<td>
		<select name="additionDisplayFlag">
			<option value="Y" ${op:selected('Y', itemAddition.displayFlag)}>노출</option>
			<option value="N" ${op:selected('N', itemAddition.displayFlag)}>숨김</option>
		</select>
	</td>
	<td>
		<c:if test="${!empty itemAddition.itemImage}">
			<ul class="item_addition_image_main">
				<li>
					<img src="${shop:loadImage(itemAddition.itemUserCode, itemAddition.itemImage, 'XS')}" class="item_image" alt="${itemAddition.itemName}" />
					<a href="javascript:deleteItemImage('main', ${itemAddition.itemId});" class="delete_item_image"><img src="/content/opmanager/images/icon/icon_x.gif" alt="삭제" /></a>
				</li>
			</ul>
		</c:if>
		<p>
			<label for="additionImage${itemAddition.itemId}" class="btn btn-gradient btn-sm">업로드</label>
			<input type="file" id="additionImage${itemAddition.itemId}" name="additionImageFile" class="hide" />
		</p>
	</td>
	<td><a href="#" class="btn btn-dark-gray btn-sm delete-item-addition">삭제</a></td>
</tr>