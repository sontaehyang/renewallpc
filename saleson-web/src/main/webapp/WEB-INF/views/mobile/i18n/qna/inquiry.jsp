<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
	<div class="title">
		<h2>고객센터</h2>
		<span class="his_back"><a href="javascript:void(0);" class="ir_pm" onclick="history.back();">뒤로가기</a></span>
		<ul class="tab_list01">
			<li><a href="/m/notice/list">공지사항</a></li>
			<li><a href="/m/faq">FAQ</a></li>
			<li class="on"><a href="/m/qna">1:1문의</a></li>
		</ul>
	</div>
	<!-- //title -->
	
	<!-- 내용 : s -->
	<div class="con">
		<div class="customer_wrap">
		
		<form:form modelAttribute="qna" method="post">
			<form:hidden path="qnaId"/>
			<form:hidden path="userName"/>
			<form:hidden path="email"/>
			
			<div class="bd_table">
				<ul class="del_info">
					<%--<li>
						<span class="del_tit t_gray">이름</span>
						<div class="input_area">
							&lt;%&ndash; <input type="text" class="transparent" value="${qna.userName}" readonly> &ndash;%&gt;
							<form:input path="userName" class="transparent"/>
						</div>
					</li>
					<li>
						<span class="del_tit t_gray">이메일</span>
						<div class="input_area">
							&lt;%&ndash; <input type="text" class="transparent" value="${qna.email}" readonly> &ndash;%&gt;
							<form:input path="email" class="transparent"/>
						</div>
					</li>--%>
					<li>
						<span class="del_tit t_gray">문의구분</span>
						<div class="select_area">
							
							<form:select path="qnaGroup" class="transparent">
								
								<c:forEach items="${groups}" var="group">
								<form:option value="${group.id}" label="${group.label}" />
								</c:forEach>
								
							</form:select>								
							
						</div>
					</li>
					<li class="chunk">
						<span class="del_tit t_gray">문의내용</span>
						<div class="input_area bd_bot">
							<form:input path="subject" class="transparent required _emoji _filter" title="제목"  maxlength="50" placeholder="제목을 입력해주세요."/>
						</div>
						<div class="text_area">
							<form:textarea path="question" cols="30" rows="6" class="required _emoji _filter" title="내용" placeholder="내용을 입력해주세요" onkeyup="cal_pre()"/>
						</div>
					</li>
				</ul>
			</div>
			<!-- //bd_table -->
			
			<div class="btn_wrap">
				<button type="button" class="btn_st1 reset" onclick="history.back();">취소</button>
				<button type="submit" class="btn_st1 decision">등록</button> 
			</div>
			<!-- //btn_wrap -->
			
			</form:form>
			
		</div>
		<!-- //customer_wrap -->
	
	</div>
	<!-- 내용 : e -->
	
<page:javascript>	
<!-- 2015.1.15 QNA 검증 -->
<script type="text/javascript">
$(function() {
	$('#qna').validator(function() {
		var message = '입력하신 내용을 문의하시겠습니까?';
		if (!confirm(message)) {
			Common.loading.hide();
			return false;
		} 
	});	
});

//TEXTAREA 최대값 체크
function cal_pre() {
	var tmpStr;
	
	tmpStr = $('#question').val();
	cal_byte(tmpStr, 500);
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
		return;
	}

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