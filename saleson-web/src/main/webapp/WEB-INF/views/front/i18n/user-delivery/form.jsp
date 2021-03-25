<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>
<%@ taglib prefix="daum"	tagdir="/WEB-INF/tags/daum" %>

<!-- 본문 -->
<!-- 팝업사이즈 660*553-->
<div class="popup_wrap">
	<h1 class="popup_title">배송지 추가/수정</h1>
	<div class="popup_contents"> 
		<form:form modelAttribute="userDelivery" method="post" action="${ action }">
			<form:hidden path="userDeliveryId" />
			<div class="board_wrap">	   
		 		<table cellpadding="0" cellspacing="0"  class="board-write">
		 			<caption>배송지 추가/수정</caption>
		 			<colgroup>		 			  
		 				<col style="width:150px;"> 
		 				<col style="width:auto;"> 
		 			</colgroup> 
		 			<tbody>
		 				<tr>
		 					<th scope="row">배송지 이름 <span class="necessary"></span></th> 
		 					<td>
		 						<div class="input_wrap col-w-7">
		 							<form:input path="title" cssClass="required" title="${op:message('M01584')}" maxlength="50"/>
		 						</div> 
		 						<span class="address_basic"><form:checkbox path="defaultFlag" value="Y" label=" 이 주소를 기본으로 설정함" /></span>
		 					</td>  
		 				</tr> 
		 				<tr>
		 					<th scope="row">받으시는 분 <span class="necessary"></span></th> 
		 					<td>
		 						<div class="input_wrap col-w-7">
		 							<form:input path="userName" title="${op:message('M01583')}" cssClass="required" maxlength="50"/>
		 						</div>  
		 					</td>  
		 				</tr>
		 				<tr>
		 					<th scope="row">휴대전화번호 <span class="necessary"></span></th> 
		 					<td>
		 						<div class="hp_area">
		 							<div class="input_wrap col-w-10">
		 								<form:select path="mobile1" title="휴대폰번호" cssClass="form-control required">
											<form:option value="" label="선택"></form:option>
											<form:options items="${op:getCodeInfoList('PHONE')}" itemLabel="label" itemValue="key.id" />
										</form:select>
		 							</div>
		 							<span class="connection"> - </span>
		 							 <div class="input_wrap col-w-10">			 	 			 	 							 
		 								<form:input path="mobile2" title="${op:message('M00329')}" class="required _number full" maxlength="4" />
		 							</div>
		 							<span class="connection"> - </span>
		 							<div class="input_wrap col-w-10">			 	 			 	 							 
		 								<form:input path="mobile3" title="${op:message('M00329')}" class="required _number full" maxlength="4" /> 
		 							</div> 	
		 						</div>
		 					</td>  
		 				</tr> 
		 				<tr>
		 					<th scope="row">전화번호</th> 
		 					<td>
		 						<div class="hp_area">
		 							<div class="input_wrap col-w-10">
		 								<form:select path="phone1" cssClass="form-control" title="전화번호">
											<form:option value="" label="선택"></form:option>
											<form:options items="${op:getCodeInfoList('TEL')}" itemLabel="label" itemValue="key.id" />
										</form:select> 
		 							</div>
		 							<span class="connection"> - </span>
		 							 <div class="input_wrap col-w-10">			 	 			 	 							 
		 								<form:input path="phone2" title="${op:message('M00016')}" class="_number" maxlength="4" />
		 							</div>
		 							<span class="connection"> - </span>
		 							<div class="input_wrap col-w-10">			 	 			 	 							 
		 								<form:input path="phone3" title="${op:message('M00016')}" class="_number" maxlength="4" />
		 							</div> 		
		 						</div> 
		 					</td>  
		 				</tr>
		 				<tr>
		 					<th scope="row" valign="top">배송지 주소 <span class="necessary"></span></th>
		 					<td>
		 						<div>
		 							<%-- <form:hidden path="newZipcode" /> --%>
		 							<form:hidden path="sido" />
									<form:hidden path="sigungu" />
									<form:hidden path="eupmyeondong" />
										    
		 							<div class="input_wrap col-w-10">
		 								<form:hidden path="zipcode" /> 
		 								<form:input path="newZipcode" title="우편번호" cssClass="required" maxlength="5" readonly="true" /> 
		 							</div>	 
		 							<div class="input_wrap"><button type="button" class="btn btn-ms btn-default" onclick="openDaumPostcode()">검색</button></div>  
		 							<div class="input_wrap mt8 col-w-0">			 	 			 	 							 
		 								<form:input path="address" title="${op:message('M00118')}" cssClass="required full" readonly="true" maxlength="100" htmlEscape="false"/>
		 							</div>			 	 				  			 
		 							<div class="input_wrap mt8 col-w-0">
		 								<form:input path="addressDetail" title="${op:message('M00118')}" cssClass="full" maxlength="255" htmlEscape="false" />
		 							</div>		 							 
		 						</div>
		 					</td>
		 				</tr>   
		 			</tbody>
		 		</table>   
		 	</div> <!-- // board_wrap E -->
		 	<div class="btn_wrap pt30">
				<button type="submit" class="btn btn-success btn-lg" title="확인하기">확인하기</button> 
				
				<c:set var="url">/delivery/list</c:set>
				<c:if test="${ target eq 'order' }">
					<c:set var="url">/order/delivery?target=order&receiverIndex=${receiverIndex}</c:set>
				</c:if>
				
				<button type="button" class="btn btn-default btn-lg" onclick="location.href='${ url }'" title="취소하기">취소하기</button>
			</div>
		</form:form>
	</div><!--//popup_contents E--> 
	<a href="javascript:self.close()" class="popup_close">창 닫기</a>
</div>

<page:javascript>

<!-- 다음 주소검색 -->
<daum:address />
<script type="text/javascript">

$(function(){
	$("#userDelivery").validator({
		'requiredClass' : 'required',
		'submitHandler' : function() {
			
		}
	}); 
})

function openDaumPostcode() {
	var tagNames = {
		'zipcode' 				: 'zipcode',
		'newZipcode'			: 'newZipcode'
	}
	
	openDaumAddress(tagNames);
}
</script>
</page:javascript>