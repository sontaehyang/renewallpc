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

<h3><span>그룹 배너 관리</span></h3>
<form id="groupBanner" method="post" enctype="multipart/form-data">
	<input type="hidden" name="categoryGroupId" value="${categoryGroupId}" />
	<input type="hidden" name="uploadFileMaxSize" value="${groupBanner.uploadFileMaxSize}" />
	<input type="hidden" name="uploadFileMinSize" value="${groupBanner.uploadFileMinSize}" />
	
	<div class="board_write">
		<table class="board_write_table" summary="그룹 배너 관리">
			<caption>그룹 배너 관리</caption>
			<colgroup>
				<col style="width: 150px;" />
				<col style="width: 150px;" />
				<col style="width: auto;" />
			</colgroup>
			<tbody>
				<c:set var="startIndex" value="0" />
				<c:forEach items="${list}" var="banner" varStatus="i">
					<tr>
						<td rowspan="3" class="label">배너 이미지 ${i.index + 1}</td>
						<td class="label">배너 타이틀</td> 
						<td>
							<input type="hidden" name="writeDatas[${i.index}].categoryGroupBannerId" class="categoryGroupBannerId" value="${banner.categoryGroupBannerId}" />
							<div>
								<input type="text" name="writeDatas[${i.index}].title" value="${banner.title}" class="full input_title" title="배너 타이틀" maxlength="255" />
							</div>
						</td> 
					</tr>
					<tr>
						<td class="label">연결 URL</td>
						<td>
							<div>
								<input type="text" name="writeDatas[${i.index}].linkUrl" value="${banner.linkUrl}" class="full input_linkUrl" title="연결 URL" maxlength="255" />
							</div>
						</td>
					</tr>
					<tr>
						<td class="label">이미지</td>
						<td>
							<div>
								<input type="hidden" name="writeDatas[${i.index}].fileName" value="${banner.fileName}" id="imgFileName${i.index}" />
								<input type="hidden" name="writeDatas[${i.index}].deleteFlag" value="${banner.fileName}" id="deleteFlag${i.index}" />
								<img src="${banner.imageSrc}" title="${banner.title}" width="${banner.thumbnailWidth}" height="${banner.thumbnailHeight}" id="imgFile${i.index}" /> <a href="javascript:deleteItem('${i.index}')" id="deleteBannerButton${i.index}">[삭제]</a> <br />
								파일명 : ${banner.fileName} <br />
								파일 수정 : <input type="file" name="writeDatas[${i.index}].uploadFile" class="full input_file" title="연결 URL" value="" /> (${banner.thumbnailWidth} X ${banner.thumbnailHeight})
							</div>
						</td>
					</tr>
					<c:set var="startIndex" value="${startIndex + 1}" />
				</c:forEach>
				<c:forEach begin="${startIndex}" end="${groupBanner.uploadFileMaxSize - 1}" step="1" varStatus="i">
					<tr>
						<td rowspan="3" class="label">배너 이미지 ${i.index + 1}</td>
						<td class="label">배너 타이틀</td> 
						<td>
							<div>
								<input type="text" name="writeDatas[${i.index}].title" class="full input_title" title="배너 타이틀" maxlength="255" />
							</div>
						</td> 
					</tr>
					<tr>
						<td class="label">연결 URL</td>
						<td>
							<div>
								<input type="text" name="writeDatas[${i.index}].linkUrl" class="full input_linkUrl" title="연결 URL" maxlength="255" />
							</div>
						</td>
					</tr>
					<tr>
						<td class="label">이미지</td>
						<td>
							<div>
								<input type="file" name="writeDatas[${i.index}].uploadFile" class="full input_file" title="연결 URL" value="" /> (${groupBanner.thumbnailWidth} X ${groupBanner.thumbnailHeight})
							</div>
						</td>
					</tr>
				</c:forEach>
					<tr>
						<td class="label">상품코드</td>
						<td class="label">
							<button type="button" id="button_add_relation_item" class="table_btn" onclick="Shop.findItem('group-banner')"><span>${op:message('M00582')} <!-- 상품 추가 --> </span></button>
						</td>
						<td>
							<div>
								<%-- input type="text" name="itemList" id="itemList" class="full input_itemList" title="상품코드" maxlength="250" value="${categoryGroup.itemList}" readonly="readonly" /> --%>
								<ul id="group-banner" class="sortable_item_relation">
									<c:forEach items="${list2}" var="item" varStatus="i">
										<li id="group-banner_item_${item.itemId}">
											<input type="hidden" name="itemList" value="${item.itemId}" />
											<p class="image"><img src="${shop:loadImage(item.itemUserCode, item.itemImage, 'XS')}" class="item_image size-100 none" alt="상품이미지" /></p>
											<p class="title">[${item.itemUserCode}]<br />${item.itemName}</p>
											
											<span class="ordering">${i.count}</span>
											<a href="javascript:Shop.deleteRelationItem('group-banner_item_${item.itemId}');" class="delete_item_image"><img src="/content/opmanager/images/icon/icon_x.gif" alt="" /></a>
										</li>
									</c:forEach>
								</ul>
							</div>
						</td>
					</tr>
				<%-- <tr>
					<td class="label">상품코드</td>
					<td class="label">
						<button type="button" id="button_add_relation_item" class="table_btn" onclick="Shop.findItem('group-banner')"><span>${op:message('M00582')} <!-- 상품 추가 --> </span></button>
					</td>
					<td>
						<div>
							<input type="text" name="itemList" class="full input_itemList" title="상품코드" maxlength="250" value="${categoryGroup.itemList }" />
						</div>
					</td>
				</tr> --%>
			</tbody>
		</table>						  
	</div> <!--// board_write E-->
	
	<div class="tex_c mt20">
		<button type="submit" class="btn btn-active">${op:message('M00088')} <!-- 등록 --> </button>
		<a href="javascript:Link.list('/opmanager/group-banner/list')" class="btn btn-default">${op:message('M00480')}</a>	<!-- 목록 -->
	</div>
</form>

<script type="text/javascript">
	$(function(){
		$("#groupBanner").validator(function() {
			var uploadFileMinSize = Number($('input[name="uploadFileMinSize"]').val());
			
			var selectFileCount = 0;
			$.each($(':file[class*="input_file"]'), function(){
				if ($(this).val() != '') {
					selectFileCount++;
				}
			});
			
			selectFileCount = Number($('.categoryGroupBannerId').size()) + selectFileCount;
			
			if (uploadFileMinSize > selectFileCount) {
				alert('최소 ' + uploadFileMinSize + '개의 이미지를 등록하셔야 합니다.');
				return false;
			}
			
			var errorMessage = "";
			var $focus;
			$.each($(':file[class*="input_file"]'), function(){
				if ($(this).val() != '' && errorMessage == '') {
					var index = $(':file[class*="input_file"]').index(this);
					if ($('input[class*="input_title"]').eq(index).val() == '') {
						errorMessage = '배너 타이틀을 입력해 주세요.';
						$focus = $('input[class*="input_title"]').eq(index);
					}
					
					if (errorMessage == '') {
						if ($('input[class*="input_linkUrl"]').eq(index).val() == '') {
							errorMessage = '연결 URL을 입력해 주세요.';
							$focus = $('input[class*="input_linkUrl"]').eq(index);
						}
					}
				}
			});

			if (errorMessage != '') {
				alert(errorMessage);
				
				if ($focus != undefined) {
					$focus.focus();
				}
				
				return false;
			}
			
			
		}); 
		
	});
	
	// 파일 삭제
	function deleteItem(id) {
		$('#imgFileName' + id).val("");
		$('#imgFile' + id).remove();
		$('#deleteBannerButton' + id).remove();
		$('#deleteFlag' + id).val("Y")
	}
	
	/* function findItemCallback(id, item){
		
		var itemId =  item.itemId;
		var strItemList = $('input[name ="itemList"]').val();
		var itemList = strItemList.split(',');
		
		if (isDupleItemId(itemId)) {
			return;
		}
		
		if (itemList.length < 4) {
			
			if (itemList.length == 3) {
				return;
			}
			
			if (strItemList != '') {
				strItemList += ", " + itemId;
			}else{
				strItemList += itemId;
			}
			
			$('input[name ="itemList"]').val(strItemList);
		}
	} */
	
	function isDupleItemId(itemId){
		
		var strItemList = $('input[name ="itemList"]').val();
		var itemList = strItemList.split(',');
		
		for (var i=0; i<itemList.length; i++) {
			if ( $.trim(itemList[i]) ==  itemId ) {
				return true;
			}
		}
		
		return false;
	}
	
	// 관련상품 삭제
	function deleteRelationItem(key) {
		$('#' + key).remove();
	};
	
</script>