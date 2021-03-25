<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

	
		<!-- 본문 -->
		<!-- 팝업사이즈 600*534-->
		<div class="popup_wrap">
		<form:form modelAttribute="itemReview" method="post" enctype="multipart/form-data">
			<form:hidden path="itemId" />
			<h1 class="popup_title">이용후기</h1>
			<div class="popup_contents">
				<div class="popup_review">
					<div class="reivew_top">
						<div class="item_info">
							<p class="photo"><img src="${ shop:loadImageBySrc(item.imageSrc, 'XS') }" alt="${item.itemName}"></p>
							<div class="order_option noline">
								<p class="item_name line2 mt10">${item.itemName}</p>
							</div>
						</div>
					</div><!--// review_top E-->
					
					<div class="board_wrap mt20">
			 			<table cellpadding="0" cellspacing="0" class="board-write">
				 			<caption>이용후기</caption>
				 			<colgroup>
				 				<col style="width:110px;">
				 				<col style="width:auto;">	 				
				 			</colgroup>
				 			<tbody> 
				 				<tr>
				 					<th scope="row">이름</th>
				 					<td>
				 						 <div>${requestContext.user.userName} (${requestContext.user.email})</div>
				 					</td>
				 				</tr> 
				 				<tr>
				 					<th scope="row">평가</th>
				 					<td>
				 						<div class="input_wrap col-w-6">
				 							<select name="score" class="form-control required" title="평가">
										       <option value="">선택하세요</option> 
												<option value="5">★★★★★</option> 
												<option value="4">★★★★☆</option> 
												<option value="3">★★★☆☆</option> 
												<option value="2">★★☆☆☆</option> 
												<option value="1">★☆☆☆☆</option> 
										  	</select>   
				 						</div>
				 					</td>
				 				</tr>
				 				<tr>
				 					<th scope="row">제목</th>
				 					<td>
				 						<div class="input_wrap col-w-0">
				 							<form:input path="subject" maxlength="100" class="required _filter _emoji" title="${op:message('M00275')}" />
				 						</div>  
				 					</td>
				 				</tr> 
				 				<tr>
				 					<th  scope="row" valign="top">내용</th>
				 					<td>
				 						<div class="input_wrap col-w-0">
				 							<form:textarea path="content" rows="4" class="form-control full required _filter _emoji" title="${op:message('M01415')}" />
				 						</div>  
				 					</td>
				 				</tr>
				 				<tr>
				 					<th>첨부파일</th>
				 					<td>
				 						<input type="file" id="imageFile" name="imageFiles[]" title="리뷰이미지" multiple="multiple" />
				 					</td>
				 				</tr> 
				 			</tbody>
				 		</table><!--//view E-->	 
				 	</div> 
					
				</div><!--//popup_review E-->
			</div><!--//popup_contents E-->
			
			<div class="btn_wrap">
				<button type="submit" class="btn btn-success btn-lg">저장하기</button> 
				<button type="button" class="btn btn-default btn-lg" onclick="javascript:self.close()">취소하기</button>
			</div>
			</form:form>
			<a href="javascript:self.close()" class="popup_close">창 닫기</a>
		</div>
					
		
			
		
	


<page:javascript>
<script type="text/javascript">
$(function() {
	var openerReload = '${openerReload}';
	if (openerReload == '-after-login') {
		opener.location.reload();
	}

	// 파일 체크
	checkFile();

	$('#itemReview').validator(function() {});
});


// 파일 체크
function checkFile() {
	var size = 5;
	var limit = 4;
	var extensions = ['jpg', 'jpeg', 'png', 'gif', 'bmp'];

	$('#imageFile').on('change', function(e) {
		var files = e.target.files;

		// 파일 첨부 개수 체크
		if (limit < e.target.files.length) {
			alert('사진은 ' + limit + '장까지 첨부 가능합니다.');
			$(this).val("");
			return;
		}

		for (var i=0; i<files.length; i++) {
			var file = files[i];
			var fileExt = files[i].name;

			// 사이즈 체크
			if (fileExt != "") {
				if (file.size > (size * 1024 * 1024)) {
					alert("파일크기는 " + size + "MB 이내로 등록 가능합니다.");
					$(this).val("");
					return;
				}
			}

			// 확장자 체크
			fileExt = fileExt.slice(fileExt.indexOf(".") + 1).toLowerCase();
			if (!extensions.includes(fileExt)) {
				alert('이미지 파일(jpg, jpeg, png, gif, bmp) 만 등록 가능합니다.');
				$(this).val("");
				return;
			}
		}
	});
}
</script>
</page:javascript>