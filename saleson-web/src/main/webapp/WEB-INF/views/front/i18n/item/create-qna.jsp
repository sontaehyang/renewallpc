<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

	
		<div class="popup_wrap">
			<h1 class="popup_title">상품Q&amp;A</h1>
			<form:form modelAttribute="qna" method="post" enctype="multipart/form-data">
				<input type="hidden" name="itemId" value="${item.itemId}" />
				<%--<input type="hidden" name="qnaGroup" value="1" />--%>
				<input type="hidden" name="userName" value="${requestContext.user.userName}" />
				<input type="hidden" name="email" value="${requestContext.user.email}" />
				<input type="hidden" name="sellerId" value="${item.sellerId}" />
		
				<div class="popup_contents">
					<div class="popup_review">
						<div class="reivew_top">
							<div class="item_info">
								<p class="photo"><img src="${item.imageSrc}" alt="${item.itemName}" /></p>
								<div class="order_option noline">
									<p class="item_name line2 mt10">${item.itemName}</p>
								</div>
							</div>
						</div><!--// review_top E-->
						
						<div class="board_write_type01">
				 			<table cellpadding="0" cellspacing="0" class="board-write">
					 			<caption>상품Q&amp;A</caption>
					 			<colgroup>
					 				<col style="width:110px;">
					 				<col style="width:auto;">	 				
					 			</colgroup>
					 			<tbody> 
					 				<tr>
					 					<th scope="row">이름</th>
					 					<td>
					 						 <div>${requestContext.user.userName} (${requestContext.user.email})</div>
					 					</td>
					 				</tr> 
					 				<tr>
					 					<th scope="row">문의유형</th>
					 					<td> 
				 							<div>
				 								<c:forEach items="${groups}" var="group" varStatus="i">
			 										<input type="radio" id="qnaGroup${i.index}" name="qnaGroup" class="required" title="문의유형" value="${group.id}"> <label for="qnaGroup${i.index}">${group.label}</label>&nbsp;&nbsp;
			 									</c:forEach>
											</div> 
					 					</td>
					 				</tr>
					 				<tr>
					 					<th scope="row">제목</th>
					 					<td>
					 						<div class="input_wrap col-w-5">
					 							<form:input path="subject" maxlength="100" class="required _filter _emoji" title="제목" />
					 						</div>  
					 						<span><form:checkbox path="secretFlag" value="Y" label="비공개" /><input type="hidden" name="!secretFlag" value="N" /></span>
					 					</td>
					 				</tr> 
					 				<tr>
					 					<th scope="row" valign="top">내용</th>
					 					<td>
					 						<form:textarea path="question" rows="4" class="form-control full required _filter _emoji" title="내용" />
					 					</td>
					 				</tr> 
					 			</tbody>
					 		</table><!--//view E-->	 
						</div> <!-- //board_write_type01 E-->
						<div class="pop_desc">
							<ul>
								<li>상품 Q&amp;A에 등록한 게시글은 마이페이지 &gt; 상품 Q&amp;A에서 확인하실 수 있습니다.</li>
								<li>부적절한 게시물 등록시 ID이용 제한 및 게시물이 삭제될 수 있습니다.</li>
							</ul>
						</div><!--//qna_guide E-->
						
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
	var openerReload = '${openerReload}';
	if (openerReload == '-after-login') {
		opener.location.reload();
	}
	$('#qna').validator();
});

//내용 글자수체크 추가 [2017-04-27]minae.yun
$('#question').on('keydown', function() {
	cal_pre();
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