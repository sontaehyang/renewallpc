<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
	
	<div class="location">
		<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
	</div>

	<h3><span>${ title } ${op:message('M00392')} <!-- 이메일 설정 등록/수정 --></span></h3>
			
	<ul class="mail_list">
		<c:forEach items="${mailTemplateCodeList}" var="list">
			<li ><a href="<c:url value="/opmanager/mail-config/edit/${list.key}" />" <c:if test="${ mailConfig.templateId eq list.key}">style="color:red;"</c:if>>${list.value}</a></li>
		</c:forEach>
	</ul> <!-- // mail_list -->
	<br/>
	<span class="mail_code" >
		<a href="/opmanager/mail-config/change-code/${ mailConfig.templateId }" class="btn_right btn gray02" onclick="Common.popup(this.href, 'orderView', 500, 500, 1); return false;" title="새창">${op:message('M00393')} <!-- 메일 대체코드 --></a>
	</span>
	<br/><br/>
	<form:form modelAttribute="mailConfig" method="post" enctype="multipart/form-data">
		<form:hidden path="templateId" />
		<form:hidden path="mailConfigId"/>
		<form:hidden path="adminSendFlag" label="${op:message('M00397')}" value="N" class="required _filter" />
		<input type="hidden" name="title" value="${ title }" />
		<div class="email_tb">
			
			<table class="board_write_table">
				<colgroup>
					<col style="width: 150px;" />
				</colgroup>
				<tr>
					<td class="label">발송여부</td>
					<td style="padding-left:18px;">
						<form:radiobutton path="buyerSendFlag" label="${op:message('M00397')}" value="Y" class="required _filter"/>
						<form:radiobutton path="buyerSendFlag" label="${op:message('M00398')}" value="N" class="required _filter"/>
					</td>
				</tr>
			</table>
			
			<h3 class="title"><span>Web-Mail</span></h3>
			<div class="board_write">
					
				<table class="board_write_table">
					<colgroup>
						<col style="width: 120px;" />
						<col />
					</colgroup>
					<tr>
						<td class="label">${op:message('M00370')} <!-- 메일 제목 --></td>
						<td>
							<div><form:input path="buyerSubject" class="full w65 mr10 required _filter" title="${op:message('M00370')}"/></div>
						</td>
					</tr>
					<tr>
						<td class="label">${op:message('M00400')} <!-- 메일 내용 --></td>
						<td>
							<div>
								<form:textarea path="buyerContent" cols="30" rows="40" class="" title="${op:message('M00400')}" />
							</div>
						</td>
					</tr>
				</table>
			</div> <!-- // board_write -->
				
		</div> <!-- //  email_tb -->
		
		<p class="btn_center">
			<button type="submit" class="btn btn-active">${op:message('M00101')} <!-- 저장 --></button>
		</p>
	</form:form>
	<div class="board_guide">
		<p class="tip">Tip</p>
		<p class="tip">- 『 {}${op:message('M00402')} <!-- 중괄호 로 감싸져 있는 문자 』는 메일 발송시 상응하는 값으로 자동으로 변환되어 전송됩니다. --></p>
			<p class="tip">- 『 {}${op:message('M00403')} <!-- 중괄호 로 감싸져 있는 문자 』는 수정하지 마시고 사용하시기 바랍니다. --></p>
	</div>
	
<module:smarteditorInit />
<module:smarteditor id="buyerContent" />
<%--<module:smarteditor id="adminContent" />--%>

	 
	<script type="text/javascript">
		$(function(){
			
			$("#mailConfig").validator({
				'requiredClass' : 'required',
				'submitHandler' : function() {
					Common.getEditorContent("buyerContent");
					//Common.getEditorContent("adminContent");
				}
			});
			
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

