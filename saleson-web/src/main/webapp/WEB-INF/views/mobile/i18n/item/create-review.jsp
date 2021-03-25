<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>


	<form:form modelAttribute="itemReview" method="post" enctype="multipart/form-data">
		<form:hidden path="itemId" />
		
		<div class="con">
			<div class="pop_title">
				<h3>이용후기 작성</h3>
				<a href="javascript:history.back();" class="history_back">뒤로가기</a>
			</div>
			<!-- //pop_title -->
			<div class="pop_con">
				<div class="write_top">
					<div class="inner">
						<div class="write_img">
							<img src="${item.imageSrc}" alt="${item.itemName}">
						</div>
						<div class="write_name">
							<p>${item.itemName}</p>
						</div>
					</div>
				</div>
				<!-- //write_top -->
				
				<div class="bd_table">
					<ul class="del_info">
						<li>
							<span class="del_tit t_gray">평가</span>
							<input type="hidden" class="rating_point" name="score" title="별점정보">
							<div class="review_rating_info">
								<a href="#" class="star_box">
									<span class="star"><img src="/content/mobile/images/common/star_rating_off.gif" alt="1"></span>
									<span class="star"><img src="/content/mobile/images/common/star_rating_off.gif" alt="2"></span>
									<span class="star"><img src="/content/mobile/images/common/star_rating_off.gif" alt="3"></span>
									<span class="star"><img src="/content/mobile/images/common/star_rating_off.gif" alt="4"></span>
									<span class="star"><img src="/content/mobile/images/common/star_rating_off.gif" alt="5"></span>
								</a>
							</div> 
						</li>
						<li>
							<span class="del_tit t_gray">제목</span>
							<div class="input_area">
								<form:input path="subject" maxlength="100" class="transparent required _filter _emoji" title="${op:message('M00275')}" placeholder="제목을 입력해주세요"/>
							</div>
						</li>
						<li>
							<span class="del_tit t_gray">내용</span>
							<div class="text_area">
								<form:textarea path="content" cols="30" rows="6" class="required _filter _emoji" title="${op:message('M01415')}" placeholder="내용을 입력해주세요" />
							</div>
						</li>
						<li>
		 					<span class="del_tit t_gray">첨부파일</span>
		 					<div>
		 						<input type="file" id="imageFile" name="imageFiles[]" title="리뷰이미지" multiple="multiple" />
		 					</div>
		 				</li> 
					</ul>
				</div>
				<!-- //bd_table -->
					
			
				<div class="btn_wrap">
					<button onclick="javascript:history.back();" class="btn_st1 reset">취소</button>
					<button type="submit" class="btn_st1 decision">등록</button>
				</div>
				<!-- //btn_wrap -->
			</div>
			<!-- //pop_con -->
		</div>
	</form:form>
	

<page:javascript>
<script type="text/javascript">
$(function() {
	// 파일 체크
	checkFile();

	$('#itemReview').validator(function() {
		if($('input[name="score"]').val() == '') {
			alert("별점을 선택해주세요.");
			return false;
		}
	});
});

//파일 확장자 체크
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