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
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<div class="location">
	<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
</div>

<h3><span></span></h3>

<form:form modelAttribute="styleBook" method="post" enctype="multipart/form-data">
	<form:hidden path="id" />

	<div class="board_write">
		<table class="board_write_table" summary="스토리북 관리">
			<caption>스토리북 관리</caption>
			<colgroup>
				<col style="width: 150px;" />
				<col style="width: auto;" />
			</colgroup>
			<tbody>
				<tr>
					<td class="label">제목</td>
					<td>
						<div>
							<form:input path="title" maxlength="100" class="form-block required" title="제목" />
						</div>
					</td>
				</tr>
				<tr>
					<td class="label">내용</td>
					<td>
						<div>
							<form:textarea path="content" cols="30" rows="3" class="required" title="내용" />
						</div>
					</td>
				</tr>
				<tr>
					<td class="label"><p>${op:message('M00983')}</p><p></p></td>
					<td>
						<div>
							<p>
								<input type="file" name="imageFile" />
							</p>
							<c:if test="${not empty styleBook.image}">
								<p class="item_image_main">
									<img src="${styleBook.imageSrc}" style="width: 500px;">
								</p>
							</c:if>
						</div>
					</td>
				</tr>
				<tr>
					<td class="label">관련상품</td>
					<td>
						<div>
							<p class="mb10">
								<button type="button" id="button_add_relation_item" class="table_btn" onclick="findItem()"><span>${op:message('M00582')} <!-- 상품 추가 --> </span></button>
								<button type="button" class="table_btn" onclick="Shop.deleteRelationItemAll('display')"><span>${op:message('M00411')} <!-- 전체삭제 --> </span></button>
							</p>

							<ul id="display" class="sortable_item_relation">
								<li style="display: none;"></li>

								<c:forEach items="${styleBook.items}" var="styleBookItem" varStatus="i">

									<c:set var="item" value="${styleBookItem.item}"/>
									<c:if test="${not empty item.itemId}">
										<li id="display_${item.itemId}">
											<input type="hidden" name="displayItemIds" value="${item.itemId}" />
											<p class="image"><img src="${shop:loadImage(item.itemCode, item.itemImage, 'XS')}" class="item_image size-100 none" alt="상품이미지" /></p>
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
												[${item.itemUserCode}] ${item.itemName}
											</p>

											<span class="ordering">${i.count}</span>
											<a href="javascript:Shop.deleteRelationItem('display_${item.itemId}');" class="delete_item_image">
												<img src="/content/opmanager/images/icon/icon_x.gif" alt="" />
											</a>
										</li>
									</c:if>
								</c:forEach>

							</ul>
						</div>
					</td>
				</tr>
			</tbody>
		</table>

		<div class="btn_center" style="padding-right: 190px;">
			<button type="submit" class="btn btn-active">${mode != "edit" ? op:message('M00088') : op:message('M00087')}</button>	 <!-- 등록 / 수정 -->
			<a href="${!empty requestContext.prevPageUrl ? requestContext.prevPageUrl : 'opmanager/style-book/list'}" class="btn btn-default">${op:message('M00037')}</a>	 <!-- 취소 -->
		</div>

	</div>
</form:form>


<page:javascript>
<script type="text/javascript">

	var DATA_FORM_OBJECT = $('#styleBook');

	$(function(){

		var confirmMessage = '스타일북 정보를 '+ '${mode != "edit" ? op:message('M00088') : op:message('M00087')}' + '하시겠습니까?';

		DATA_FORM_OBJECT.validator(function() {

			if (!confirm(confirmMessage)) {
				return false;
			}

		});

		Common.checkedMaxStringLength('textarea[name=content]',null, 500);

		// 관련상품 드레그
		$(".sortable_item_relation").sortable({
			placeholder: "sortable_item_relation_placeholder"
		});
	});

	function findItem(){
		//setHeight();
		Shop.findItem('display');
	}
</script>
</page:javascript>