<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>

<div class="board_write sortable_display_item">
	<h2>${template.title} / 롤링여부 (${template.rollingYN})</h2>
	<table class="board_write_table" summary="">
		<caption></caption>
		<colgroup>
			<col style="width: 20%;" />
			<col style="width: auto;" />
		</colgroup>
		<tbody>
			<tr class="prodTr" id="prodTr1">
				<td class="label">선택 상품</td>
				<td>
					<div>
						<p class="mb10">
							<button type="button" id="button_add_relation_item" class="table_btn" onclick="findItem('prodDisplay${templateIndex}')"><span>${op:message('M00582')} <!-- 상품 추가 --> </span></button>
							<button type="button" class="table_btn" onclick="if(confirm('모든 상품을 삭제하시겠습니까?')){Shop.deleteRelationItemAll('prodDisplay${templateIndex }');}"><span>${op:message('M00411')} <!-- 전체삭제 --> </span></button>
						</p>

						<ul id="prodDisplay${templateIndex}" class="sortable_item_relation" item-limit="${template.count}">
							<!-- <li style="display: none;"></li> -->
							<c:forEach items="${displayItemList}" var="item" varStatus="i">
								<c:if test="${templateIndex == item.displaySubCode}">
									<c:if test="${!empty item.itemId}">
										<li id="prodDisplay_item_${item.itemId}">
											<input type="hidden" name="prodDisplay${templateIndex}ItemIds" value="${item.itemId}" />

											<p class="image"><img src="${item.imageSrc}" class="item_image size-100 none" alt="상품이미지" /></p>
											<p class="title">
												<c:choose>
													<c:when test="${item.dataStatusCode == '1'}">
														<c:if test="${item.itemSoldOutFlag == 'Y'}"><strong style="color:red">[품절]</strong></c:if>
														<c:if test="${item.displayFlag == 'N'}"><strong style="color:red">[비공개]</strong></c:if>
													</c:when>
													<c:otherwise>
														<a href="javascript:Manager.itemLog('${item.itemId}')"><strong style="color:red">[비공개]</strong></a>
													</c:otherwise>
												</c:choose>
												[${item.itemUserCode}]<br />${item.itemName}
											</p>

											<a href="javascript:javascript:deleteItem();" class="delete_item"><img src="/content/opmanager/images/icon/icon_x.gif" alt="" /></a>
										</li>
									</c:if>
								</c:if>
							</c:forEach>
						</ul>
					</div>
				</td>
			</tr>
		</tbody>
	</table>
</div>