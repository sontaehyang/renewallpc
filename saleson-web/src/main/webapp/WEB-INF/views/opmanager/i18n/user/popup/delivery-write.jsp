<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="daum"	tagdir="/WEB-INF/tags/daum" %>

<c:set var="requireMark" value="<span>*</span>" />

<h2><span>${op:message('M01571')} <!-- 나의 배송지 관리 --></span></h2>

<form:form modelAttribute="userDelivery" method="post" action="${ action }">
	<form:hidden path="userDeliveryId" />
	<form:hidden path="userId" />
	<div class="board_write">
		<table class="board_write_table" summary="신규회원등록에 관련한 정보를 입력하는 칸입니다.">
 			<caption>${op:message('M01571')}</caption>
 			<colgroup>
 				<col style="width:180px;">
 				<col style="width:auto;">	 				
 			</colgroup>
 			<tbody>
 				<tr>
 					<td class="label">${op:message('M01584')} ${ requireMark }</td> <!-- 배송지 이름 --> 
 					<td> 
 						<div>
 							<form:input path="title" cssClass="half required" title="${op:message('M01584')}"/>
	 						<span> 
	 							<label class="checkbox-inline">
	 								<form:checkbox path="defaultFlag" value="Y" label="${op:message('M01585')}" /> <!-- 이 주소를 기본으로 설정함 --> 
	 							</label>
	 						</span>
 						</div>
 					</td>
 				</tr>
 				<%-- <tr>
 					<td class="label">주문자 회사명 ${ requireMark }</td>
 					<td>
 						<div>
 							<form:input path="companyName" title="주문자 회사명" cssClass="half required"/>
 						</div>
 					</td>
 				</tr> --%>
 				<tr>
 					<td class="label">${op:message('M01583')} ${ requireMark }</td><!-- 받으시는 분 --> 
 					<td>
 						<div>
 							<form:input path="userName" title="${op:message('M01583')}" cssClass="half required"/>
 						</div>
 					</td> 
 				</tr>
 				<%-- <tr>
 					<td class="label">${op:message('M01583')}（カナ） ${ requireMark }</td>
 					<td>
 						<div>
 							<form:input path="userNameKatakana" title="${op:message('M01583')}" cssClass="half required"/>
 							<span class="t_next">(全角25文字/半角50文字以内　例：モモ　タロウ)</span>
 						</div>
 					</td>
 				</tr> --%>
 				<tr>
 					<td class="label">전화번호 ${ requireMark }</td>
 					<td>
 						<div>
	 						<%-- <form:input path="phone1" title="${op:message('M00016')}" class="_number" maxlength="4" /> --%>
	 						<select title="전화번호 앞자리" id="phone1" name="phone1">
								<option value="" label="-선택-">-선택-</option>
								<c:forEach items="${op:getCodeInfoList('TEL')}" var="codes">
									<option value="${codes.detail}"  ${op:selected(userDelivery.phone1, codes.detail)}> ${codes.label }</option>
								</c:forEach>
							</select>
	 						-
							<form:input path="phone2" title="${op:message('M00016')}" class="_number" maxlength="4" />
							-
							<form:input path="phone3" title="${op:message('M00016')}" class="_number" maxlength="4" />
 						</div>
 					</td>
 				</tr>
 				<tr>
 					<td class="label">휴대폰번호</td>
 					<td>
 						<div>
	 						<%-- <form:input path="mobile1" title="${op:message('M00329')}" class="required _number" maxlength="4" /> --%>
	 						<select class="required" title="전화번호 앞자리" id="mobile1" name="mobile1">
								<option value="" label="-선택-">-선택-</option>
								<c:forEach items="${op:getCodeInfoList('PHONE')}" var="codes">
									<option value="${codes.detail}" ${op:selected(userDelivery.mobile1, codes.label)}> ${codes.label }</option>
								</c:forEach>
							</select>
	 						-
							<form:input path="mobile2" title="${op:message('M00329')}" class="required _number" maxlength="4" />
							-
							<form:input path="mobile3" title="${op:message('M00329')}" class="required _number" maxlength="4" />
						</div>
 					</td>
 				</tr>
 				<tr>
 					<td class="label last">주소 ${ requireMark }</td>
 					<td class="last">
 						<div>
 							<p class="mt5">
 								우편번호 <%-- <form:input path="zipcode1" title="우편번호" cssClass="required _number one" maxlength="3" /> - 
								<form:input path="zipcode2" title="우편번호" cssClass="required _number one" maxlength="4" /> --%>
								<form:hidden path="zipcode" title="우편번호" cssClass="required one" maxlength="7" />
								<form:input path="newZipcode" title="우편번호" class="required one _number" maxlength="5" readonly="true"/>
 								<button type="button" class="btn btn-dark-gray btn-sm" onclick="openDaumPostcode()"><span class="glyphicon glyphicon-search"></span> ${op:message('M00117')}</button>
 							</p>
 							<%-- <p class="mt5">
 								지역 <form:input path="dodobuhyun" title="지역" maxlength="8" class="required" />
 							</p> --%>
 							<p class="mt5">
 								주소 <form:input path="address" title="주소" cssClass="required half" maxlength="100" readonly="true"/>
 							</p>
 							<p class="mt5">
 								상세주소 <form:input path="addressDetail" title="상세주소" cssClass="required half" maxlength="100" />
 							</p>
 						</div>
 					</td>
 				</tr>
 			</tbody>
 		</table><!--//view E-->	 	
	</div><!--//popup_contents E-->
	<div class="popup_btns">
		<button type="submit" class="btn btn-active">${op:message('M01194')}</button> <!-- 확인 --> 
		<button type="button" class="btn btn-default" onclick="location.href='/opmanager/user/popup/delivery-list/${ userDelivery.userId }'">${op:message('M00037')}</button> <!-- 취소 --> 
	</div>
</form:form>

<daum:address />

<!-- <script src="http://dmaps.daum.net/map_js_init/postcode.js"></script> -->
<script type="text/javascript">
$(function(){
	Manager.activeUserDetails("delivery");
	$("#userDelivery").validator({
		'requiredClass' : 'required',
		'submitHandler' : function() {
			
		}
	}); 
})

function openDaumPostcode() {
	
		var tagNames = {
			'newZipcode'			: 'newZipcode',
			'zipcode' 				: 'zipcode',
			'zipcode1' 				: 'post1',
			'zipcode2' 				: 'post2',
		}
		
		openDaumAddress(tagNames);
	
}

/* function openDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {	        	
        	
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
            // 우편번호와 주소 정보를 해당 필드에 넣고, 커서를 상세주소 필드로 이동한다.
            document.getElementById('zipcode1').value = data.postcode1;
            document.getElementById('zipcode2').value = data.postcode2;
            document.getElementById('address').value = data.address;
            
            var indexNum = data.address.indexOf(" ");
            document.getElementById('dodobuhyun').value = data.address.substring(0, indexNum);

            //전체 주소에서 연결 번지 및 ()로 묶여 있는 부가정보를 제거하고자 할 경우,
            //아래와 같은 정규식을 사용해도 된다. 정규식은 개발자의 목적에 맞게 수정해서 사용 가능하다.
            //var addr = data.address.replace(/(\s|^)\(.+\)$|\S+~\S+/g, '');
            //document.getElementById('address').value = addr;
			document.getElementById('addressDetail').value = "";
            document.getElementById('addressDetail').focus();
        }
    }).open();
} */

function findZipcode(prefix) {
	
	var zipcode1Name = "zipcode1";
	var zipcode2Name = "zipcode2";
	var addressName = "address";
	var addressDetailName = "addressDetail";

	if (prefix == undefined) {
		prefix = "";
	} else {
		zipcode1Name = uppercase(zipcode1Name);
		zipcode2Name = uppercase(zipcode2Name);
		addressName = uppercase(addressName);
		addressDetailName = uppercase(addressDetailName);
	}
	
	var zipcode = $("#" + prefix + zipcode1Name).val() + $("#" + prefix + zipcode2Name).val();
	if (zipcode == '' || zipcode == undefined) return;
	
	var params = { 
		'post1' : $("#" + prefix + zipcode1Name).val(),
		'post2' : $("#" + prefix + zipcode2Name).val()
	};
		
	$.post('/zipcode/find-address', params, function(resp) {
		if (resp.zipcode) {
			$("#" + prefix + addressName).val(resp.zipcode.c5);
			$("#" + prefix + addressDetailName).val(resp.zipcode.c6);
		} else { 
			alert("${op:message('M00128')}");
			$("#" + prefix + addressName).val("");
			$("#" + prefix + addressDetailName).val("");
		}
	});
}
</script>