<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>


		<div class="location">
			<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
		</div>

		<h3><span>브랜드관리</span></h3>
		<form:form modelAttribute="brand" method="post" enctype="multipart/form-data">
			<div class="board_write">
					<table class="board_write_table">
						<caption>브랜드관리</caption>
						<colgroup>
							<col style="width:150px;" />
							<col style="width:auto;" />
							
						</colgroup>
						<tbody>
							<tr>
								<td class="label">브랜드명 <span class="require">*</span></td>
								<td>
									<div>
										<form:input path="brandName" class="form-half required" title="브랜드명" />
 									</div>
								</td>				 
							</tr>
							<tr>
								<td class="label">브랜드 이미지</td>
								<td>
									<div>
										<input type="file" name="file" />
										
										<c:if test="${!empty brand.brandImage}">
											<div style="margin-top: 10px">
												<img src="${brand.brandImageSrc}" alt="" style="border: 1px solid #ccc; padding: 5px;" />
												<p>
													<label><input type="checkbox" name="brandImageDeleteFlag" value="Y" /> 이미지 삭제</label>
													<input type="hidden" name="!brandImageDeleteFlag" value="N" />
												</p>
											</div>
										</c:if>
 									</div>
								</td>				 
							</tr>
							
							<tr>
								<td class="label">공개여부</td>
								<td>
									<div>
										<form:radiobutton path="displayFlag" label="공개" value="Y" checked="checked" />
										<form:radiobutton path="displayFlag" label="비공개" value="N" />
								    </div>
								</td>
							</tr>
						</tbody>					 
					</table>	
					
					<h3 class="mt30">브랜드 상단 내용</h3>
					<form:textarea path="brandContent" cols="30" rows="80" style="width: 1080px;height: 250px;"  title="${op:message('M00661')}" />
					
						
					<!-- 버튼시작 -->		 
					<div class="tex_c mt20">
						<button type="submit" class="btn btn-active"><span>${op:message('M00101')} </span></button>		
						<a href="/opmanager/brand/list" class="btn btn-default">${op:message('M00037')} </a>		 
					</div>			 
					<!-- 버튼 끝-->	
			</div> <!-- // board_write -->
		</form:form>					 
		
		<div class="board_guide">
			<br/>
			<p class="tip">[안내]</p>
			<p class="tip">
				* 브랜드명, 업체명, 연락처, 주소는 필수 입력사항입니다.
			</p> 
		</div>							


		
<module:smarteditorInit />
<module:smarteditor id="brandContent" />
<script type="text/javascript">
$(function(){

	$('#brand').validator(function() {
		Common.getEditorContent("brandContent");
	});

});

function openDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {	        	
        	
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
            // 우편번호와 주소 정보를 해당 필드에 넣고, 커서를 상세주소 필드로 이동한다.
            document.getElementById('post1').value = data.postcode1;
            document.getElementById('post2').value = data.postcode2;
            document.getElementById('address').value = data.address;

            //전체 주소에서 연결 번지 및 ()로 묶여 있는 부가정보를 제거하고자 할 경우,
            //아래와 같은 정규식을 사용해도 된다. 정규식은 개발자의 목적에 맞게 수정해서 사용 가능하다.
            //var addr = data.address.replace(/(\s|^)\(.+\)$|\S+~\S+/g, '');
            //document.getElementById('address').value = addr;

            document.getElementById('detailAddress').focus();
        }
    }).open();
}
</script>
		