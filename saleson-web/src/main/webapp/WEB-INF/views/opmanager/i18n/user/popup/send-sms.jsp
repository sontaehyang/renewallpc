<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>

	<!-- 본문 -->
<div class="popup_wrap">
	<h1 class="popup_title">SMS 발송</h1>
	<form:form modelAttribute="userSms">
		<div class="popup_contents pb0">
					
			<c:choose>
				<c:when test="${empty smsConfig}">
					<div class="no_content">
						※환경설정 > SMS설정 > 회원SMS발송를 확인해주세요
					</div>	
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${smsConfig.buyerSendFlag == 'Y'}">
							<table cellpadding="0" cellspacing="0" summary="" class="board_write_table">
								<colgroup>
									<col style="width:120px;" />
									<col style="width:*" />
								</colgroup>
								<tbody>
									<tr>
										<th class="label">발송대상</th>
										<td>
											<div>
												${user.userName} [${user.loginId}]
											</div>
										</td>
									</tr>
									<tr>
										<th class="label">핸드폰번호</th>
										<td>
											<div>
												${user.userDetail.phoneNumber}
											</div>
										</td>
									</tr>
									<tr>
										<th class="label">발송타입</th>
										<td>
											<div>
												<form:radiobutton path="smsType" label="SMS" value="sms" cssClass="required" title="발송타입" />
												<form:radiobutton path="smsType" label="LMS" value="mms" cssClass="required" title="발송타입" />
											</div>
										</td>
									</tr>
									<tr>
										<th class="label">제목</th>
										<td>
											<div>
												<form:input path="title" title="제목" style="width:100%" />
												<p>LMS로 발송하실 경우 필수 입니다.</p>
											</div>
										</td>
									</tr>
									<tr>
										<th class="label">내용</th>
										<td>
											<div>
												<form:textarea path="content" cssClass="required _filter content" title="내용" style="height:150px;"/>
											</div>
										</td>
									</tr>
								</tbody>
							</table>
							
							<p class="popup_btns">
								<button type="submit" class="btn btn-active w250 mb10"><span>발송</span></button><br/>
							</p>
												
						</c:when>
						<c:otherwise>
							
							<div class="no_content">
								※환경설정 > SMS설정 > 회원SMS발송의 "사용자 발송상태"를 확인해주세요.
							</div>										
					
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
					
		</div>
		
		
	</form:form>
		
	<a href="#" class="popup_close">창 닫기</a>
</div>

<script type="text/javascript">
	$(function() {
		
		$('textarea.content').on('keyup', function(){
			showTextByte($(this));
		});
		
		$.each($('textarea.content'), function(){
			showTextByte($(this));
		})
		
		$("#userSms").validator(function(){
			
			var smsType = $('input[name="smsType"]:checked').val();
			if ('mms' == smsType) {
				if ($('input[name="title"]').val() == '') {
					alert('제목을 입력해 주세요.')
					$('input[name="title"]').focus();
					return false;
				}
			}
			
			if(!confirm('발송 하시겠습니까?')) {
				return false;
			}
		});
	});
	
	function showTextByte($_this) {
		var text = $_this.val();
		var stringByteLength = (function(s,b,i,c){
		    for(b=i=0;c=s.charCodeAt(i++);b+=c>>11?3:c>>7?2:1);
		    return b
		})(text);
		
		if (stringByteLength == 0) {
			$_this.parent().find('span.add-text-view').remove();
			return;
		}
		
		var pattern = /{[a-zA-Z0-9_-]+}/gi;
		var matchTexts = text.match(pattern);
		var matchTextDefaultLength = 8;
		
		if (matchTexts) {
			text = text.replace(pattern, '');
			var replaceStringByteLength = (function(s,b,i,c){
			    for(b=i=0;c=s.charCodeAt(i++);b+=c>>11?3:c>>7?2:1);
			    return b
			})(text);
			
			stringByteLength = replaceStringByteLength + (matchTextDefaultLength * matchTexts.length);
		}
		
		
		$_this.parent().find('span.add-text-view').remove();
		$_this.parent().append('<span class="add-text-view">총 : '+ stringByteLength +' Byte [대체코드는 1개당 '+ matchTextDefaultLength +' Byte로 체크됩니다.]</span>');
	}
</script>