<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>
		
		
			<div class="location">
				<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
			</div>
		
			<!--팝업 등록/수정 시작-->
			<h3><span>${op:message('M00746')}</span></h3> <!-- 팝업 등록/수정 --> 
			
			<form:form modelAttribute="popup" method="post" enctype="multipart/form-data">			
				<div class="board_write">
					<table class="board_write_table">
						<caption>${op:message('M00746')}</caption>
						<colgroup>
							<col style="width:150px;" />
							<col style="width:auto;" />
						</colgroup>
						<tbody>
							<tr>
								<td class="label">${op:message('M00739')}</td> <!-- 팝업제목 --> 
								<td>
									<div>
										<form:input type="text" path="subject" title="${op:message('M00739')}" class="form-block required" />		 
								    </div>
								</td>							
							</tr>
							<tr>
								<td class="label">${op:message('M00496')}</td> <!-- 사용기간 --> 
								<td>
									<div>
										<span class="datepicker"><form:input path="startDate" maxlength="8" class="datepicker required" title="${op:message('M00741')}" /></span> <!-- 팝업시작일자 -->
										 
										<form:select path="startTime" title="${op:message('M00508')}"> <!-- 시간 선택 -->
											<c:forEach items="${hours}" var="code">
												<form:option value="${code.value}" label="${code.label}"/> 
											</c:forEach>
										</form:select>시
										<span class="wave">~</span>
										<span class="datepicker"><form:input path="endDate" maxlength="8" class="datepicker required" title="${op:message('M00742')}" /></span> <!-- 팝업종료일자 --> 
										<form:select path="endTime" title="${op:message('M00508')}">
											<c:forEach items="${hours}" var="code">
												<form:option value="${code.value}" label="${code.label}"/> 
											</c:forEach>
										</form:select>시 59분
									</div>
								</td>							
							</tr>		
							<tr>
								<td class="label">${op:message('M00730')}</td> <!-- 팝업상태 -->
								<td>
									<div>
										<form:radiobutton path="popupClose" label="${op:message('M00083')}" value="1" checked="checked"/> <!-- 사용 --> 
										<form:radiobutton path="popupClose" label="${op:message('M00732')}" value="2"/> <!-- 일시정지 --> 
										<form:radiobutton path="popupClose" label="${op:message('M00733')}" value="3"/> <!-- 종료 --> 
									</div>
								</td>						 
							</tr>
							<tr>
								<td class="label">${op:message('M00744')}</td> <!-- 팝업타입 --> 
								<td>
									<div>
										<form:radiobutton path="popupType" label="${op:message('M00747')}" value="1" checked="checked"/> (PC 적용) <!-- 윈도우 -->
										<form:radiobutton path="popupType" label="${op:message('M00748')}" value="2"/>(PC, Mobile 적용) <!-- 레이어 -->
									</div>
								</td>						 
							</tr>
							<tr>
								<td class="label">${op:message('M00749')}</td> <!-- 팝업창 위치 --> 
								<td>
									<div>
										<span>Top <form:input type="text" path="topPosition" title="Top" class="one" /> px ,	
										Left <form:input type="text" path="leftPosition" title="Left" class="one" /> px
								    </div>
								</td>							
							</tr>
							<tr>
								<td class="label">${op:message('M00750')}</td> <!-- 팝업창 크기 --> 
								<td>
									<div>
										Width <form:input type="text" path="width" title="Width" class="one" /> px ,	
										Height <form:input type="text" path="height" title="Height" class="one" /> px
								    </div>
								</td>							
							</tr>
							<tr>
								<td class="label">${op:message('M00734')}</td> <!-- 팝업형태 --> 
								<td>
									<div>
										<form:radiobutton path="popupStyle" onclick="javascript:showDiv(1)" label="${op:message('M00736')}" value="1" checked="checked"/> <!-- 텍스트입력 --> 
										<!-- form:radiobutton path="popupStyle" onclick="javascript:showDiv(2)" label="${op:message('M00736')}-${op:message('M00737')}" value="2"/--> <!-- 테두리없음 --> 
										<form:radiobutton path="popupStyle" onclick="javascript:showDiv(3)" label="${op:message('M00738')}" value="3"/> <!-- 이미지등록 --> 
									</div>
								</td>						 
							</tr>
							<tr class="content">
						        <td class="label">${op:message('M00751')}</td> <!-- 팝업내용 -->
						        <td>
						        	<div>
						        		<form:textarea path="content" cols="" rows="10" style="width: 700px;" title="${op:message('M00661')}" />
						        	</div>
						        </td>
						    </tr>
							<tr class="image">
								<td class="label">${op:message('M00752')}</td> <!-- 이미지 --> 
					            <td>
					            	<div>
					            		<input type="file" name="imageFile" style="width:300px;" title="${op:message('M00417')}"/> <!-- 이미지파일 --> 
						            	<p class="item_image_main">
						            		<c:if test="${popup.popupImage != '' && popup.popupImage != null}">
												<img src="${popup.popupImageSrc}" class="item_image size-100" alt="${op:message('M00753')}" /> <!-- 팝업이미지 --> 
												<a href="javascript:deleteItemImage('${popup.popupId}');" class="delete_item_image"><img src="/content/opmanager/images/icon/icon_x.gif" alt="" /></a>
											</c:if>
										</p>
					                </div>
					            </td>
					        </tr>
					        <tr class="image">
					            <td class="label">${op:message('M00754')}</td> <!-- 이미지링크 --> 
					            <td>
					            	<div>
					            		<input type="text" id="imageLink" name="imageLink" class="full" maxlength="100" value="${popup.imageLink}" />
					            	</div>
					            </td>
					        </tr>
					        <!-- <tr class="image">
					        	<td class="label">배경색</td>
					        	<td>
					        		<div>
								        <input type="text" id="backgroundColor" name="backgroundColor" class="one" value="${popup.backgroundColor}" />
								        <c:if test="${popup.backgroundColor != ''}">
								        	<span style="height:20px;width:1000px;background-color:${popup.backgroundColor}">&nbsp;&nbsp;&nbsp;&nbsp;</span>
								        </c:if>
								    </div>
						        </td>
					        </tr> -->	
						</tbody>					 
					</table>								 							
				</div> <!-- // board_write -->
				
				<div class="btn_center">
					<button type="submit" class="btn btn-active"><span>${op:message('M00101')}</span></button> <!-- 저장 --> 			 
					<a href="/opmanager/popup/list" class="btn btn-default"><span>${op:message('M00037')}</span></a> <!-- 취소 --> 
				</div>
			</form:form>
		
<module:smarteditorInit />
<module:smarteditor id="content" />	

<script type="text/javascript">

$(function() {
	
	if ($('input[name=popupStyle]:checked').val() == 3) {
		$('.image').show();
		$('.content').hide();
	} else {
		$('.image').hide();
		$('.content').show();
	}
	
	// validator
	try{
		$('#popup').validator(function() {
			
			if ( checkedDate( $('#startDate').val(), $('#startTime').val(), $('#endDate').val(), $('#endTime').val() ) ) {
				alert('사용기간의 종료기간이 시작기간보다 빠를 수 없습니다.');
				return false;
			}
			
			Common.getEditorContent("content");
		});
	} catch(e) {
		alert(e.message);
	}
});
	
function showDiv(num){
    if(num == "3") {
    	$('.image').show();
    	$('.content').hide();
    } else {
    	$('.image').hide();
    	$('.content').show();

    	// 네이버 스마트에디터 초기 설정 display:none일 경우 height 0px로 설정되는 버그때문에 추가
		if ($('#editor_frame').height() === 0) {
			$('#editor_frame').height(254);
		}
    }
    setHeight();
}

//상품이미지 삭제 
function deleteItemImage(id) {
	if (!confirm(Message.get("M00755"))) {	// 이미지가 실제로 삭제됩니다.(복구불가)\n삭제하시겠습니까? 
		return;
	} 

	var param = {'popupId': id};
	$.post('/opmanager/popup/delete-item-image', param, function(response){
		Common.responseHandler(response);
		$('.item_image_main').remove();
	});	
}

function  checkedDate(startDate,startTime, endDate, endTime){
	
	return (startDate+startTime+'00') > (endDate+endTime+'59' );
	
}

</script>
