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



<!-- 내용 : s -->
<div class="con">
	<div class="pop_title">
		<h3>입점문의</h3>
		<a href="javascript:history.back();" class="history_back">뒤로가기</a>
	</div>
	<!-- //pop_title -->
	<div class="pop_con">
	<form:form modelAttribute="storeInquiry" method="post" enctype="multipart/form-data">
		<form:hidden path="status" value="0"/>
		<form:hidden path="email" />
		<div class="bd_table typeA">
			<ul class="del_info">
				<li>
					<span class="del_tit t_gray">업체명</span>
					<div class="input_area">
						<form:input path="company" class="required transparent _emoji" title="업체명"/>
					</div>
				</li>
				<li>
					<span class="del_tit t_gray">담당자명</span>
					<div class="input_area">
						<form:input path="userName" class="required transparent _emoji" title="담당자명"/>
					</div>
				</li>
				<li>
					<span class="del_tit t_gray">담당자 연락처</span>
					<div class="input_area">
						<form:select path="phoneNumber1" class="required _number transparent w22p">
							<c:forEach var="code" items="${op:getCodeInfoList('PHONE')}" varStatus="i">
								<form:option value="${code.detail}">${code.label}</form:option>
							</c:forEach>
						</form:select>
						-
						<form:input path="phoneNumber2" maxlength="4" class="required _number transparent w32p" title="연락처"/>
						-
						<form:input path="phoneNumber3" maxlength="4" class="required _number transparent w32p" title="연락처"/>
					</div>
				</li>
				<li>
					<span class="del_tit t_gray">담당자 이메일</span>
					<div class="in_td">
						<div class="input_area02">
							<input id="email1" type="text" class="transparent" class="required" title="이메일주소">
						</div>
						<span class="mail">@</span>
						<div class="input_area02">
							<select id="email2"  name="email2" class="transparent required" title="이메일 도메인주소">
								<c:forEach var="code" items="${op:getCodeInfoList('EMAIL')}" varStatus="i">
									<option value="${code.detail}">${code.label}</option>
								</c:forEach>
								<option value="">직접입력</option>
							</select>
						</div>
						<input id="email3" type="text" disabled="disabled" placeholder="직접입력" style="display:none;" title="이메일 도메인주소"/>
					</div>
				</li>
				<li>
					<span class="del_tit t_gray">홈페이지 URL</span>
					<div class="input_area">
						<form:input path="homepage" class="required transparent _emoji" title="홈페이지 URL"/>
					</div>
				</li>
				<li>
					<span class="del_tit t_gray">판매하고자<br/>하는 제품</span>
					<div class="input_area">
						<form:textarea path="content" title="내용 _emoji" class="required"/>
					</div>
				</li>
				<li class="file">
					<span class="del_tit t_gray">첨부파일</span>
					<div class="input_area">
						<form:input path="file" type="file" />
					</div>
				</li>
			</ul>
		</div>
		<!-- //bd_table -->
		<%--		
		<div class="load_more02"><span>이메일 문의는 kevin@sigongmedia.co.kr로 보내주세요.</span></div>
		 --%>
		 
		<div class="btn_wrap">
			<button type="button" class="btn_st1 reset" onclick="history.back()">취소</button>
			<button type="submit" class="btn_st1 decision">저장</button>
		</div>
		<!-- //btn_wrap -->
	</form:form>
	</div>
	<!-- //pop_con -->

</div>
<!-- 내용 : e -->
		
<%-- <form:form modelAttribute="storeInquiry" method="post" enctype="multipart/form-data">
		<form:hidden path="status" value="0"/>
		<form:hidden path="email" />
		<div class="order">
			<h3 class="title">입점문의</h3>
			
			<div class="table_write">
				<table cellpadding="0" cellspacing="0">
					<colgroup>
						<col style="width:35%;">
						<col style="width:65%;">
					</colgroup>
					<tbody>
						<tr>
							<th>업체명</th>
							<td><form:input path="company" class="required _filter" title="업체명" /></td> 
						</tr>	
						<tr>
		 					<th>담당자명</th>
		 					<td>
		 						<form:input path="userName" class="required _filter" title="담당자명"/>
		 					</td>
		 				</tr>
		 				<tr>
		 					<th>담당자 연락처</th>
		 					<td>
		 						<form:select path="phoneNumber1">
		 							<form:option value="010" label="010" />
		 							<form:option value="011" label="011" />
		 							<form:option value="017" label="017" />
		 							<form:option value="019" label="019" />
		 						</form:select>
		 						-
		 						<form:input path="phoneNumber2" maxlength="4" class="required _number" title="휴대폰 번호 가운데 자리"/> - <form:input path="phoneNumber3" maxlength="4" class="required _number" title="휴대폰 번호 끝자리"/> 
		 					</td>
		 				</tr> 	
		 				<tr>
		 					<th>담당자 이메일</th>
		 					<td>
		 						<input id="email1" type="text"/> @ 
		 						<select id="email2" >
		 							<option value="">이메일 선택</option>
		 							<option value="hanmail.net">hanmail.net</option>
		 							<option value="naver.com">naver.com</option>
		 							<option value="direct">직접입력</option>
		 						</select>	
								<input id="email3" type="text" disabled="disabled" placeholder="직접입력"/>					 						
		 					</td>
		 				</tr>				
						<tr>
							<th>홈페이지 URL</th>
							<td><form:input path="homepage" class="required _filter" title="홈페이지 URL" /></td> 
						</tr> 
						<tr>
							<th valign="top">판매하고자 하는</br>제품</th>
							<td>
								<form:textarea path="content" cols="20" rows="10" class="required _filter" title="판매하고자 하는 제품" />
							</td> 
						</tr>
						<tr>
		 					<th class="bL0">첨부파일</th>
		 					<td class="bL0">
		 						<form:input path="file" type="file" />
		 					</td>
		 				</tr>  
					</tbody>
				</table>		
			</div><!--//table_write E-->
			<div class="btn_area wrap_btn">
				<div class="sale division-1" style="display:block"> 
					<div>
						<div>
							<button type="submit" class="btn btn_on btn-w100">${op:message('M00088')}</button> <!-- 등록 --><br />							
						</div>
					</div>
				</div>				 
			</div>
	</form:form> --%>
	
	

<page:javascript>
<script type="text/javascript">
$(function() {
	$('#storeInquiry').validator(function() {
		
		var regEmail = /([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
		var email = '';
		
		if ($('#email2').val() == '') {
			email = $('#email1').val()+'@'+$('#email3').val();
		} else {
			email = $('#email1').val()+'@'+$('#email2').val();
		}
		
		 if(!regEmail.test(email)) {
             alert('이메일 주소가 유효하지 않습니다');
             u_email.focus();
             return false;
         }
		
		$('input[name="email"]').val(email); 
		 
	});
	
	showAndHideEmailDomain();
	
});


function showAndHideEmailDomain() {
	$('#email2').on('change', function(){
		var email2 = $('#email2');
		var email3 = $('#email3');
		
		if ($(this).val() == '') {
			email3.prop('disabled','');
			email3.addClass('required');
			email2.removeClass('required');
			email3.show();
		} else {
			email3.val('');
			email2.addClass('required');
			email3.removeClass('required');
			email3.prop('disabled','disabled');
			email3.hide();
		}
	});
}
</script>
</page:javascript>