<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

	
		<div class="popup_wrap">
			<h1 class="popup_title">입점문의</h1>
			<form:form modelAttribute="storeInquiry" method="post" enctype="multipart/form-data">
				<form:hidden path="status" value="0"/>
				<form:hidden path="email" />
				<div class="popup_contents">
					<div class="popup_review">
						<div class="board_view">
				 			<table cellpadding="0" cellspacing="0"  class="order_view">
					 			<caption>입점문의</caption>
					 			<colgroup>
					 				<col style="width:140px;">
					 				<col style="width:auto;">	 				
					 			</colgroup>
					 			<tbody> 
					 				<tr>
					 					<th scope="row">업체명</th>
					 					<td>
					 						<div class="input_wrap col-w-0"> 
					 							 <form:input path="company" class="required _emoji" title="업체명"/>
					 						</div>
					 					</td>
					 				</tr> 
					 				<tr>
					 					<th scope="row">담당자명</th>
					 					<td>
					 						<div class="input_wrap col-w-0"> 
					 							<form:input path="userName" class="required _emoji" title="담당자명"/>
					 						</div>
					 					</td>
					 				</tr> 
					 				<tr>
					 					<th scope="row">담당자 연락처</th>
					 					<td>
					 						<div>
												<div class="input_wrap col-w-9"> 
													<form:select path="phoneNumber1">
							 							<form:option value="010" label="010" />
							 							<form:option value="011" label="011" />
							 							<form:option value="017" label="017" />
							 							<form:option value="019" label="019" />
							 						</form:select>
										    	</div>
										    	<span class="connection"> - </span>
										    	<div class="input_wrap col-w-9">			 	 			 	 							 
													<form:input path="phoneNumber2" maxlength="4" class="required _number" title="휴대폰 번호 가운데 자리"/>
												</div>
												<span class="connection"> - </span>	
												<div class="input_wrap col-w-9">			 	 			 	 							 
													<form:input path="phoneNumber3" maxlength="4" class="required _number" title="휴대폰 번호 끝자리"/>
												</div>						 
											</div>
											<%--
					 						<form:select path="phoneNumber1">
					 							<form:option value="010" label="010" />
					 							<form:option value="011" label="011" />
					 							<form:option value="017" label="017" />
					 							<form:option value="019" label="019" />
					 						</form:select>
					 						-
					 						<form:input path="phoneNumber2" maxlength="4" class="required _number" title="휴대폰 번호 가운데 자리"/> - 
					 						<form:input path="phoneNumber3" maxlength="4" class="required _number" title="휴대폰 번호 끝자리"/> 
					 						 --%>
					 					</td>
					 				</tr>
					 				<tr>
					 					<th scope="row">담당자 이메일</th>
					 					<td>
					 						<div class="input_wrap col-w-8">	
					 							<input id="email1" type="text" class="required " title="이메일주소"/>
					 						</div>		
					 						<span class="connection">@</span>
					 						<div class="input_wrap col-w-7">
						 						<select id="email2"  name="email2" class="transparent required" title="이메일 도메인주소">
													<c:forEach var="code" items="${op:getCodeInfoList('EMAIL')}" varStatus="i">
														<option value="${code.detail}">${code.label}</option>
													</c:forEach>
													<option value="">직접입력</option>
												</select>
					 						</div>		
					 						<div class="input_wrap col-w-7">
												<input id="email3" type="text" disabled="disabled" placeholder="직접입력" title="이메일 도메인주소"/>	
											</div>						 						
					 					</td>
					 				</tr>
					 				<tr>
					 					<th scope="row">홈페이지 URL</th>
					 					<td>
					 						<form:input path="homepage" class="required _emoji" title="홈페이지 URL"/>
					 					</td>
					 				</tr>
					 				<tr>
					 					<th scope="row" valign="top">판매하고자 하는</br>제품</th>
					 					<td>
					 						<form:textarea path="content" rows="4" class="form-control full required _filter _emoji" style="width: 100%; height: 50px; padding:0" title="내용" />
					 					</td>
					 				</tr> 
					 				<tr>
					 					<th scope="row">첨부파일</th>
					 					<td>
					 						<form:input path="file" type="file" />
					 					</td>
					 				</tr>
					 			</tbody>
					 		</table><!--//view E-->	 
						</div> <!-- //board_write_type01 E-->
						<%--
						<div class="qna_guide">
							<ul>
								<li>※ 이메일 문의는 kevin@sigongmedia.co.kr로 보내주세요.</li>
							</ul>
						</div><!--//qna_guide E-->
						--%>
					</div>
				</div><!--//popup_contents E-->
	
				<div class="btn_wrap">
					<button type="submit" class="btn btn-success btn-lg">저장하기</button> 
					<button type="button" class="btn btn-default btn-lg" onclick="javascript:self.close()">취소하기</button> 
				</div>
				<a href="javascript:self.close()" class="popup_close">창 닫기</a>
			</form:form>
		</div>
				
	


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
	
	$('#email2').on('change', function(){
		var email2 = $('#email2');
		var email3 = $('#email3');
		
		if ($(this).val() == '') {
			email3.prop('disabled','');
			email3.addClass('required');
			email2.removeClass('required');
		} else {
			email3.val('');
			email2.addClass('required');
			email3.removeClass('required');
			email3.prop('disabled','disabled');
		}
	});
});
</script>
</page:javascript>