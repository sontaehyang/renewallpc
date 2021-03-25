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

	<div class="popup_wrap">
		
		<h1 class="popup_title">{categoriesEditParam.editTitle } ${op:message('M01326')} <!-- 추천 상품 등록 --></h1>
		<form id="categoryEditForm" method="post" action="/opmanager/categories-edit/related">
			<input name="categoryTeamId" type="hidden" value="${categoryTeam.categoryTeamId}" />
			<div class="popup_contents">				
				<h2>¶ ${categoriesEditParam.editTitle } ${op:message('M01326')}</h2>				
				<div class="board_write">
					<table class="board_write_table" summary="${op:message('M00579')} <!-- 팀 기본정보 --> ">
						<caption>${op:message('M00579')} <!-- 팀 기본정보 --> </caption>
						<colgroup>
							<col style="width: 150px;" />
							<col style="width: auto;" />
						</colgroup>
						<tbody>
							<tr>
								<td class="label">${op:message('M00581')} <!-- 추천상품 --> </td>
								<td>
									<div>
										<p class="mb10">
											<button type="button" id="button_add_relation_item" class="table_btn" onclick="Shop.findItem('recommend')"><span>${op:message('M00582')} <!-- 상품 추가 --> </span></button>
											<button type="button" class="table_btn" onclick="Shop.deleteRelationItemAll('related')"><span>${op:message('M00411')} <!-- 전체삭제 --> </span></button>
										</p>
										
										<ul id="related" class="sortable_item_relation">
											<li style="display: none;"></li>
											
											<c:forEach items="${itemList}" var="item" varStatus="i">
												<c:if test="${!empty item.itemId}">
													<li id="related_item_${item.itemId}">
														<input type="hidden" name="relatedItemIds" value="${item.itemId}" />
														<p class="image"><img src="${item.imageSrc}" class="item_image size-100 none" alt="상품이미지" /></p>
														<p class="title">[${item.itemUserCode}]<br />${item.itemName}</p>
														
														<span class="ordering">${i.count}</span>
														<a href="javascript:Shop.deleteRelationItem('related_item_${item.itemId}');" class="delete_item_image"><img src="/content/opmanager/images/icon/icon_x.gif" alt="" /></a>
													</li>
												</c:if>
											</c:forEach>
		
										</ul>
									</div>
								</td>
							</tr>	 
						</tbody>
					</table>						 
				</div> <!--// board_write E-->
				
				<p class="popup_btns">
					<button type="submit" class="btn btn-active">${op:message('M00088')} <!-- 등록 --> </button>
					<a href="javascript:self.close();" class="btn btn-default">${op:message('M00037')} <!-- 취소 --> </a>
				</p>
				
			</div> <!-- // popup_contents -->
		</form>
		<a href="#" class="popup_close">창 닫기</a>
	</div>
	
<script type="text/javascript">
	$(function(){
		
		// 관련상품 드레그
	    $(".sortable_item_relation").sortable({
	        placeholder: "sortable_item_relation_placeholder"
	    });
	});
</script>	
