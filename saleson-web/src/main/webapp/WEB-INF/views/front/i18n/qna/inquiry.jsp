<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<div class="inner">
	<div class="location_area">
		<div class="breadcrumbs">
			<a href="#" class="home"><span class="hide">home</span></a>
			<a href="#">고객센터</a>
			<span>1:1문의</span> 
		</div>
	</div><!-- // location_area E --> 
	
	<div id="contents">
 		<jsp:include page="/WEB-INF/views/layouts/front/inc_lnb_customer.jsp" />     
	 	<div class="contents_inner"> 
			<h2>1:1문의</h2>
		 	<div class="guide_box">
			 	<ul>
			 		<li>문의하신 내용의 답변은 고객님의 메일 또는 <strong>[마이페이지 > 1:1문의]</strong> 내역에서 확인하실 수 있습니다. </li>
					<li>질문해주신 사항에 대해서는 가능한 빨리 답변을 드리지만, 질문 내용에 따라서 다소 시간이 걸리는 경우도 있습니다.</li> 
				</ul>	
			</div>	 
		 	<h3 class="mt30">문의정보</h3>
		 	<div class="board_wrap">
		 		<form:form modelAttribute="qna" method="post">
		 			<form:hidden path="qnaId"/>
		 			<form:hidden path="userName"/>
		 			<form:hidden path="email"/>
		 			<table cellpadding="0" cellspacing="0" class="board-write">
			 			<caption>문의정보</caption>
			 			<colgroup>
			 				<col style="width:160px;">
			 				<col style="width:auto;">	 				
			 			</colgroup>
			 			<tbody>   
			 				<%--<tr>
			 					<th scope="row">이름 <span class="necessary"></span></th>
			 					<td> ${qna.userName} </td>
			 				</tr>
			 				<tr>
			 					<th scope="row">이메일주소</th>
			 					<td>
									<div class="input_wrap col-w-0">
										<form:input path="email" class="form-control required _emoji _filter" title="이메일주소" maxlength="40" />
									</div>
								</td>
			 				</tr>--%>
			 				<tr>
			 					<th scope="row">문의유형</th>
			 					<td> 
		 							<div class="input_wrap col-w-7">
										<form:select path="qnaGroup" class="form-control">
			 								<c:forEach items="${groups}" var="group">
			 									<form:option value="${group.id}" label="${group.label}" />
			 								</c:forEach>
			 							</form:select>
									</div> 
			 					</td>
			 				</tr>
			 				<tr>
			 					<th scope="row">제목 <span class="necessary"></span></th>
			 					<td>
			 						<div class="input_wrap col-w-0">
			 							<form:input path="subject" class="form-control required _emoji _filter" title="제목" maxlength="50" />
			 						</div>
			 					</td>
			 				</tr>
			 				<tr>
			 					<th scope="row" valign="top">내용 <span class="necessary"></span></th>
			 					<td>
			 						<div class="input_wrap col-w-0">
			 							<form:textarea path="question" class="_emoji _filter required" cols="10" rows="10" title="문의 내용" keyup="cal_pre()"/>
			 						</div>  
			 					</td> 
			 				</tr> 
			 			</tbody>
			 		</table><!--//view E-->	
			 		<div class="btn_wrap">
			 			<button type="submit" class="btn btn-success btn-lg">문의하기</button>
			 		</div> 	
			 	</form:form>
		 	</div>	 	  
		</div>
	</div>	<!--// contents E-->
</div><!-- // inner E -->

<page:javascript>
<!-- 2015.1.15 QNA 검증 -->
<script type="text/javascript">
$('#question').on('keydown', function() {
	cal_pre();
});

$(function() {
	$('#qna').validator(function() {
		if (!cal_pre()) {
			return false;
		}

		var message = '입력하신 내용을 문의하시겠습니까?';
		if (!confirm(message)) {
			Common.loading.hide();
			return false;
		}
	});	
});

// TEXTAREA 최대값 체크
function cal_pre() {
	var tmpStr;
	
	tmpStr = $('#question').val();
	if (!cal_byte(tmpStr, 500)) {
		return false;
	}

	return true;
}

function cal_byte(aquery, maxlength) {

	var tmpStr;
	var temp=0;
	var onechar;
	var tcount;
	tcount = 0;
	
	tmpStr = new String(aquery);
	temp = tmpStr.length;
	
	for (k=0;k<temp;k++)	{
		onechar = tmpStr.charAt(k);
		if (escape(onechar) =='%0D') {
			
		} else if (escape(onechar).length > 4) {
			tcount += 2;
		} else {
			tcount++;
		}
	}

	
	if(tcount>maxlength) {
		reserve = tcount-maxlength;
		alert("내용이 너무 많습니다.");
		cutText();
		return false;
	}

	return true;

}

function cutText() {
	nets_check($('#question').val(), 500);
}

function nets_check(aquery, maxlength) {

	var tmpStr;
	var temp=0;
	var onechar;
	var tcount;
	tcount = 0;
	
	tmpStr = new String(aquery);
	temp = tmpStr.length;
	
	for (k=0;k<temp;k++)	{
		
		onechar = tmpStr.charAt(k);
	
		if (escape(onechar).length > 4) {
			tcount += 2;
		} else {
			// 엔터값이 들어왔을때 값(rn)이 두번실행되는데 첫번째 값(n)이 들어왔을때 tcount를 증가시키지 않는다.
			if(escape(onechar)=='%0A') {
				
			} else {
				tcount++;
			}
		}
	
		if(tcount>maxlength) {
			tmpStr = tmpStr.substring(0,k);
			break;
		}

	}
	
	$('#question').val(tmpStr);
	cal_byte(tmpStr);

}

</script>	
</page:javascript>