<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.onlinepowers.framework.context.*"%>

<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="board" 	tagdir="/WEB-INF/tags/board"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>

<board:header />
	<form:form modelAttribute="board" method="post">
		<form:hidden path="boardId"/>
		<input type="hidden" name="boardCode" id="boardCode" value="${boardContext.boardCfg.boardCode}" />
		<input type="hidden" name="secret" id="secret" value="1" />
		<input type="hidden" name="token" id="token" value="${requestContext.token}" /> 
		<!--//agree_box S-->
		<div class="agree_box">
			${cmsContent}
		</div><!--//agree_box E-->
		<div class="agree_check">
			<input type="checkbox" id="agree01" title="정보 수집에 동의에 체크해주세요." /> <label for="agree01">정보 수집에 동의 합니다. </label>
		</div>
		<!-- 게시판 write 시작 -->
		<table cellpadding="0" cellspacing="0" summary="질의응답 제목, 작성자, 패스워드, 이메일, 전화번호, 질의내용을 입력하는 페이지입니다." class="board_list_table02">
			<caption>질의응답 작성페이지</caption>
			<colgroup>
				<col style="width:13%" />
				<col style="width:auto" />
			</colgroup>
			<tr>
				<th scope="row"><label for="write_txt01">제목</label></th>
				<td>
					<form:input path="subject" cssClass="full required w570" maxlength="100" title="제목" />
				</td>
			</tr>
			<tr>
				<th scope="row"><label for="writer">작성자</label></th>
				<td>
					<form:input path="userName" cssClass="required w100" title="작성자" />
				</td>
			</tr>
			<c:if test="${modeTitle == '등록'}">
				<tr>
					<th scope="row"><label for="write_word">패스워드</label></th>
					<td>
						<form:password path="password" cssClass="required w100" maxlength="4" title="패스워드" />
						<span class="txt_ol">※패스워드는 4자리까지 입력해 주시기 바랍니다. 비밀번호를 반드시 기억해 주시기 바랍니다.</span>
					</td>
				</tr>
			</c:if>
			<tr>
				<th scope="row">이메일</th>
				<td>
					<c:set var="email_arr" value="${fn:split(board.email, '@') }"/>
					<input type="text" name="email_1" id="email_1" class="required" title="이메일주소" value="${email_arr[0]}" class="w173" /> @ 
					<input type="text" name="email_2" id="email_2" class="required" title="이메일주소 도메인" value="${email_arr[1]}" class="w163" /> 
					<select name="email_choice" class="email_choice">
						<option value="">직접입력</option>
						<option value="naver.com" <c:if test="${email_arr[1] == 'naver.com'}">selected='selected'</c:if>>naver.com</option>
						<option value="daum.net" <c:if test="${email_arr[1] == 'daum.net'}">selected='selected'</c:if>>daum.net</option>
						<option value="nate.com" <c:if test="${email_arr[1] == 'nate.com'}">selected='selected'</c:if>>nate.com</option>
					</select>
				</td>
			</tr>
			<tr>
				<th scope="row">전화번호</th>
				<td>
					<c:set var="tel_arr" value="${fn:split(board.etc3, '-') }"/>
					<input type="text" name="tel_1" value="${tel_arr[0]}" title="전화번호 첫번째자리" class="required _number w78"/> - 
					<input type="text" name="tel_2" value="${tel_arr[1]}" title="전화번호 두번째자리" class="required _number w78"/> - 
					<input type="text" name="tel_3" value="${tel_arr[2]}" title="전화번호 마지막자리" class="required _number w78"/> </td>
			</tr>
			<tr>
				<th scope="row" valign="top">질의내용</th>
				<td class="answer01">
					<div>
					어린이집안전공제회에서 2014년 상반기 「찾아가는 안전 인형극」 교육 희망 어린이집을 모집합니다. 어린이집 내 생활에서 가장 많이 발생하는 안전사고 유형과 예방법을 인형을 통해 흥미롭고 이해하기 쉽도록 교육하는 안전 인형극에 많은 관심 부탁드립니다. <br />
					<form:textarea path="content" cols="150" rows="50" cssClass="q_txtBox" title="내용" />
					</div>
					
				</td>
			</tr>
			<c:if test="${modeTitle == '등록'}">
				<tr>
					<th scope="row" valign="top">자동입력방지</th>
					<td class="auto_secret">
						<div class="auto_number"><img src="/captcha/" style="width:175px; height:38px;"/></div>
						<div class="auto_write">
							<input type="text" name="captchaText" id="captchaText" title="자동입력방지문자" class="required w154" />
							<p>아래에 나오는 숫자와 문자를 순서대로 입력해 주세요.</p>
						</div>
					</td>
				</tr>
			</c:if>
	
		</table>
		<!-- 게시판 write 끝 --> 
		
		<div class="btn_right">
			<c:if test="${modeTitle == '등록'}">
				<input type="image" src="/content/images/btn/btn_write.gif" title="글쓰기" alt="글쓰기" />
			</c:if>
			<c:if test="${modeTitle == '수정'}">
				<input type="image" src="/content/images/btn/btn_refresh.gif" title="수정" alt="수정" />
				<a href="javascript:;" onClick="board_del();"><img src="/content/images/btn/btn_del.gif" title="삭제" alt="삭제" /></a>
			</c:if>
			<a href="javascript:cancel('${requestContext.prevPageUrl}');"><img src="/content/images/btn/btn_list.gif" alt="목록" /></a>		
	    </div>
		
	</form:form>
	
	<form id="deleteForm" name="deleteForm" method="post" action="/csia-board/${boardContext.boardCfg.boardCode}/qnaDelete">
		<input type="hidden" name="boardCode" id="boardCode" value="${boardContext.boardCfg.boardCode}" />
		<input type="hidden" name="boardId" id="boardId" value="${boardContext.boardId}" />
		<input type="hidden" name="password" id="password" value="${board.password}" />
	</form>
				
<board:footer />




<script type="text/javascript">
$(function() {
	// validator
	$('#board').validator(function() {
		
		if($("input[type=checkbox]:checked").size() == 0){
			alert("정보수집에 동의해 주세요.");
			$("#agree01").focus();
			return false;
		}
		
		return Common.confirm("내용을 ${modeTitle} 처리 하시겠습니까?");
	});
	
	$(".email_choice").change(function(){
		$("#email_2").val($('.email_choice option:selected').val());
	});
		
});

function board_del(){
	Common.confirm("글을 삭제하시겠습니까?", function() {
		$("#deleteForm").submit();
	});
}

function cancel(prevUrl) {
	Common.confirm("${modeTitle}을 취소하시겠습니까?", function() {
		if (prevUrl == '') location.href = '${boardContext.boardBaseUri}';
		location.href = prevUrl;
	});
}
</script>
	

