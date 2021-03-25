<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

	<form id="recommendMail" method="post">
		<input type="hidden" name="itemUserCode" value="${itemUserCode}" />
		<div class="popup_wrap">
			<h1 class="popup_title">추천메일 보내기</h1>
			<div class="popup_contents">
				<div class="popup_review">
					 
					<div class="board_write_type01">
			 			<table cellpadding="0" cellspacing="0" summary="" class="esthe-table write">
				 			<caption>추천메일 보내기</caption>
				 			<colgroup>
				 				<col style="width:110px;">
				 				<col style="width:auto;">	 				
				 			</colgroup>
				 			<tbody> 
				 				<tr>
				 					<th>보내는 사람</th>
				 					<td>
				 						<div class="input_wrap col-w-1">
				 							<input type="text" name="senderEmail" value="${recommendMail.senderEmail}" class="required " title="보내는 사람 이메일 주소">
				 						</div>  
				 					</td>
				 				</tr> 
				 				<tr>
				 					<th>받는 사람</th>
				 					<td>
				 						<div class="input_wrap col-w-1">
				 							<input type="text" name="receiverEmail" value="${recommendMail.receiverEmail}" class="required " title="받는 사람 이메일 주소">
				 						</div>
				 					</td>
				 				</tr>
				 				<tr>
				 					<th>제목</th>
				 					<td>
				 						<div class="input_wrap col-w-1">
				 							<input type="text" name="title" value="${recommendMail.title}" class="required" title="제목">
				 						</div>  
				 					</td>
				 				</tr> 
				 				<tr>
				 					<th class="bL0" valign="top">내용</th>
				 					<td class="bL0">
				 						<textarea name="content" cols="10" rows="5"class="required">${recommendMail.content}</textarea>  
				 					</td>
				 				</tr> 
				 			</tbody>
				 		</table><!--//view E-->	 
				 	</div> 
					
				</div><!--//popup_review E-->
			</div><!--//popup_contents E-->
			
			<div class="popup_btns">
				<button type="submit" class="btn btn-success btns">확인</button> 
				<button type="button" class="btn btn-default btns" onclick="self.close()">취소</button> 
			</div>
			<a href="javascript:self.close()" class="popup_close">창 닫기</a>
		</div>
	</form>
		
<page:javascript>
<script type="text/javascript">
$(function(){
	$('#recommendMail').validator();
});

</script>
</page:javascript>