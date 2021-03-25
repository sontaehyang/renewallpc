<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
	
	
	<div class="location">
		<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
	</div>

	<h3><span>${ title } SMS 설정 등록/수정</span></h3>
			
	<ul class="mail_list">
		<c:forEach items="${smsTemplateCodeList}" var="list">
			<li ><a href="<c:url value="/opmanager/sms-config/edit/${list.key}" />" <c:if test="${ smsConfig.templateId eq list.key}">style="color:red;"</c:if>>${list.value}</a></li>
		</c:forEach>
	</ul> <!-- // mail_list -->
	<br/>
	<span class="mail_code" >
		<a href="/opmanager/sms-config/change-code/${ smsConfig.templateId }" class="btn_right btn gray02" onclick="Common.popup(this.href, 'orderView', 500, 500, 1); return false;" title="새창">대체코드</a>
	</span>
	<br/><br/>
	<form:form modelAttribute="smsConfig" method="post">
		<form:hidden path="templateId" />
		<form:hidden path="smsConfigId"/>
		<input type="hidden" name="title" value="${ title }" /> 
		<input type="hidden" name="adminSendFlag" value="N" />
		<div class="email_tb">
			
			<table class="board_write_table">
				<colgroup>
					<col style="width: 200px;" />
				</colgroup>
				<tr>
					<td class="label">${op:message('M00395')} <!-- 사용자 발송 --></td>
					<td>
						<div>
							<form:radiobutton path="buyerSendFlag" label="${op:message('M00397')}" value="Y" class="required _filter"/>
							<form:radiobutton path="buyerSendFlag" label="${op:message('M00398')}" value="N" class="required _filter"/>
						</div>
					</td>
				</tr>
				<tr>
					<td class="label">발송타입</td>
					<td >
						<div>
							<p class="text-info text-sm">
								* SMS 발송은 80byte로 제한되며 초과된 문자는 삭제되어 전송됩니다.
							</p>
							
							<form:radiobutton path="smsType" label="SMS" value="sms" class="required _filter"/>
							<form:radiobutton path="smsType" label="LMS" value="mms" class="required _filter" />
						</div>
						
					</td>
				</tr> 
				<!-- tr>
					<td class="label">${op:message('M00396')} <!-- 관리자 발송</td>
					<td style="padding-left:18px;">
						<form:radiobutton path="adminSendFlag" label="${op:message('M00397')}" value="Y" class="required _filter"/>
						<form:radiobutton path="adminSendFlag" label="${op:message('M00398')}" value="N" class="required _filter"/>
					</td>
				</tr -->
			</table>
			
			<div class="board_write">
				<h3 class="title"><span>SMS</span></h3>	
				<table class="board_write_table">
					<colgroup>
						<col style="width: 200px;" />
						<col />
					</colgroup>
					<tr>
						<td class="label">사용자 SMS 제목</td>
						<td>
							<div>
								<form:input path="buyerTitle" class="full w65 mr10 _filter" maxlength="30" title="사용자 SMS 제목"/>
							</div>
						</td>
					</tr>
					<tr>
						<td class="label">사용자 SMS 내용</td>
						<td>
							<div><form:textarea path="buyerContent" cols="30" rows="6" class="required _filter" title="${op:message('M00400')}"/></div>
						</td>
					</tr>
					<!-- tr>
						<td class="label">관리자 SMS 내용</td>
						<td>
							<div><form:textarea path="adminContent" cols="30" rows="6" class="_filter" title="${op:message('M00400')}" /></div>
						</td>
					</tr -->
				</table>
			</div> <!-- // board_write -->
				
		</div> <!-- //  email_tb -->
		
		<p class="btn_center">
			<button type="submit" class="btn btn-active">${op:message('M00101')} <!-- 저장 --></button>
		</p>
	</form:form>
	
	
	<script type="text/javascript">
		$(function(){
			$("#smsConfig").validator(function() {});
			
			$('textarea, input[type=text]').on('focus', function(){
				setNyField(this);
			});
		});
		
		var myField = null;
		function setNyField(obj) {
			myField = obj;
		} 
		
		function insertAtCursor(myValue) {
			
			// 고객 요청사항으로 해당 기능 동작 안하게 막음. [cjh] 2014.09.25
			return;
			
			if (myField == undefined) {
				setNyField(document.getElementById("buyerContent"));
			}
			
			if (document.selection) {  //IE support
			   myField.focus();
			   sel = document.selection.createRange();
			   sel.text = myValue;
			} else if ((myField.selectionStart)||(myField.selectionStart == 0)) { // Chrome, Firefox, Netscape support
			   var startPos = myField.selectionStart;
			   var endPos = myField.selectionEnd;
			   myField.value = myField.value.substring(0, startPos)
			                       + myValue
			                       + myField.value.substring(endPos, myField.value.length);
			} else {
			   myField.value += myValue;
			}
		}
		
	</script>

