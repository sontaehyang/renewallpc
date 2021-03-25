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

<style>
	.delete_item {
		position: absolute;
		top: 5px;
		right: 5px;
	}

	.categories tbody tr td{
		background-color: #FFF;
	}

	/* 순서 변경할 영역 표시 */
	.categories_placeholder {
		border: 1px dotted black;
		margin: 0 1em 1em 0;
		height: 360px;
		margin-left: auto;
		margin-right: auto;
		background-color: #D6EAFD;
	}
</style>

	<!-- 본문 -->
	<div class="popup_wrap categories">

		<h1 class="popup_title"> ${displayGroupCode.displayGroupCodeName}</h1>

		<div class="popup_contents">
			<form  id="displayForm" method="post" enctype="multipart/form-data">
				<div class="display-item-area">
				</div>

				<input name="displayGroupCode" type="hidden" value="${displayGroupCode.displayGroupCode}" />
				<input name="displayTemplateCode" type="hidden" value="${displayGroupCode.displayTemplateCode}" />
				<input name="viewTarget" type="hidden" value="${viewTarget}" />

				<c:forEach var="template" items="${displayTemplate.displaySettingValueList}" varStatus="i">
					<c:set var="template" value="${template}" scope="request" />
					<c:set var="displayImageList" value="${displayImageList}" scope="request" />
					<c:set var="displayItemList" value="${displayItemList}" scope="request" />
					<c:set var="displayEditorList" value="${displayEditorList}" scope="request" />
					<c:set var="templateIndex" scope="request">${i.index}</c:set>

					<c:choose>
						<c:when test="${template.type == 1}"> <%-- 이미지 --%>
							<jsp:include page="./include/image-list.jsp" />
						</c:when>
						<c:when test="${template.type == 2}">  <%-- 에디터 --%>
							<jsp:include page="./include/editor.jsp" />
						</c:when>
						<c:when test="${template.type == 3}">  <%-- 상품 --%>
							<c:set var="itemLimitCount" value="${template.count}" />
							<jsp:include page="./include/item-list.jsp"/>
						</c:when>
					</c:choose>
				</c:forEach>

				<p class="popup_btns">
					<button type="submit" class="btn btn-active">${op:message('M00088')} <!-- 등록 --> </button>
					<a href="javascript:self.close();" class="btn btn-default">${op:message('M00037')} <!-- 취소 --> </a>
				</p>
			</form>

		</div> <!-- // popup_contents -->

		<a href="#" class="popup_close">창 닫기</a>
	</div>

<module:smarteditorInit />
<c:forEach var="template" items="${displayTemplate.displaySettingValueList}" varStatus="index">
	<c:set var="templateIndex" value="${index.index}"/>
	<c:if test="${template.type == 2}">
		<c:set var="id" value="displayEditorContents${templateIndex}" />
		<module:smarteditor id="${id}" />
	</c:if>
</c:forEach>
<!-- MiniColors -->
<script src="/content/modules/jquery/minicolors/jquery.minicolors.js"></script>
<link rel="stylesheet" href="/content/modules/jquery/minicolors/jquery.minicolors.css" />
<script type="text/javascript">
	$(function(){

		$(function() {
			$('.colorpicker').minicolors();
		});

		$(".delete_item").click(function(){
			if (confirm("삭제하시겠습니까?")) {
				$(this).parent().remove();
			}
		});

		$("#displayForm").validator(function() {

			<c:forEach var="template" items="${displayTemplate.displaySettingValueList}" varStatus="index">
				<c:set var="templateIndex" value="${index.index}"/>
				<c:if test="${template.type == 2}">
					<c:set var="id" value="displayEditorContents${templateIndex}" />
					Common.getEditorContent("${id}");
				</c:if>
			</c:forEach>

			makeDisplayItemList();

			if (isItemLimitCount()) {
				return false;
			}

		});

		$(".file_delete").on("click",function(){
			if (confirm("이미지를 삭제하시겠습니까?")) {

				var id = $(this).attr("id");
				var arrObj = id.split('-');

				var param = {
					"displayGroupCode" : "${displayGroupCode.displayGroupCode}",
					"displaySubCode" : arrObj[1],
					"viewTarget" : arrObj[2],
					"ordering" : arrObj[3]
				};

				$.post("/opmanager/display/template/${displayGroupCode.displayGroupCode}/file-delete",param, function(resp){
					Common.responseHandler(resp, function(){
						location.reload();
					});
				});
			}
		});

		// 드래그 순서 이동
		$(".sortable_display_image").sortable({
			placeholder: "sortable_display_image_placeholder"
		});

		$(".sortable_display_editor").sortable({
			placeholder: "sortable_display_editor_placeholder"
		});

		$(".sortable_display_item").sortable({
			placeholder: "sortable_display_item_placeholder"
		});
	});

	function findItem(dest) {

		if (!isItemLimitCount(dest)) {
			Shop.findItem(dest, "");
		}
	}

	function isItemLimitCount(dest) {

		<c:forEach var="template" items="${displayTemplate.displaySettingValueList}" varStatus="index">
			<c:set var="templateIndex" value="${index.index}" />

			var inputName = "prodDisplay${templateIndex}ItemIds";
			var ulName = "prodDisplay${templateIndex}";

			// 등록 or 추가일 경우 검사
			if (dest == null || ulName == dest) {
				var itemLimitCount = ${template.count};
				var itemCount = $("input[name='" + inputName + "']").length;

				// limit 조건 설정 (등록일 경우 n개 초과, 추가일 경우 n개 이상)
				var isLimit = dest == null ? (itemCount > itemLimitCount) : (itemCount >= itemLimitCount);

				if (isLimit) {
					alert("상품 추가는 " + itemLimitCount + "개 까지 가능합니다.");
					return true;
				}
			}

		</c:forEach>

		return false;
	}

	function makeDisplayItemList() {

		$('form#displayForm div.display-item-area').empty();

		var html = "";

		<c:forEach var="template" items="${displayTemplate.displaySettingValueList }" varStatus="index">
			<c:set var="templateIndex" value="${index.index}" />
			var inputName = "prodDisplay${templateIndex }ItemIds";
			var loopNumber = ${template.count };

			for (var i=0; i<loopNumber; i++) {

				var value = $("input[name='"+inputName+"']").eq(i).val();

				if (typeof value == "undefined" || value == '') {
					value = "";
				}

				html += "<input type='hidden' name='displayItemIds[${templateIndex}]["+i+"]' value='"+value+"'/>\n";
			}

		 </c:forEach>

		 $('form#displayForm div.display-item-area').append(html);

	}



</script>