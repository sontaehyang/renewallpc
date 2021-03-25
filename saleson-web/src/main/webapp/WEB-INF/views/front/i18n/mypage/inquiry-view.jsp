<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

	<div class="sub_location">
			<a href="#">エステ用品通販HOME</a> &gt; <a href="#">마이페이지</a> &gt; <a href="#">7에스테혜택</a> &gt; <a href="#" class="on">나의 문의 현황</a>
		</div> <!-- // sub_location E-->			
					
		<h4>お問い合わせ</h4>		
	 	
	 	<div class="mypage_short">
	 		
		 	<p class="unit_tit">お問い合わせ内容</p>
		 	
		 	<table cellpadding="0" cellspacing="0" summary="" class="esthe-table view">
	 			<caption>table list</caption>
	 			<colgroup>
	 				<col style="width:180px;">
	 				<col style="width:auto;">	 				
	 			</colgroup>
	 			<tbody>
	 				<tr>
	 					<td class="label">お問い合わせ区分</td>
	 					<%-- <td>${qna.label}</td> --%>
	 				</tr>
	 				<tr>
	 					<td class="label">登録日</td>
	 					<td>${op:datetime(qna.createdDate)}</td>
	 				</tr>
	 				<%-- <tr>
	 					<td class="label">ご注文番号</td>
	 					<td>${qna.orderId}</td>
	 				</tr> --%>
	 				<%-- <tr>
	 					<td class="label">商品名</td>
	 					<td>${qna.itemName}</td>
	 				</tr> --%>
	 				<tr>
	 					<td class="label last">お問い合わせ内容 </td>
	 					<td class="last">
	 						<div>
	 							${op:nl2br(qna.question)}
							</div>
	 					</td>
	 				</tr>
	 			</tbody>
	 		</table>
		 		
	 	</div> <!-- // mypage_short E -->
	 	
	 	
	 	<!-- 문의내용 답변이 있을경우 -->
	 	<c:if test="${!empty qna.qnaAnswer}">
		 	<div class="mypage_short">
		 		
			 	<p class="unit_tit">お問い合わせ　回答</p>
			 	
			 	<table cellpadding="0" cellspacing="0" summary="" class="esthe-table view">
		 			<caption>table list</caption>
		 			<colgroup>
		 				<col style="width:180px;">
		 				<col style="width:auto;">
		 				<col style="width:180px;">
		 				<col style="width:auto;">	 				
		 			</colgroup>
		 			<tbody>
		 				<tr>
		 					<td class="label">回答人</td>
		 					<%-- <td>${qna.userName } (${qna.loginId})</td> --%>
		 					<td class="label">回答日</td>
		 					<td>${op:datetime(qna.qnaAnswer.createdDate)}</td>
		 				</tr>
		 				<tr>
		 					<td class="label last">回答内容</td>
		 					<td colspan="3"  class="last">
		 						<div>
		 							${op:nl2br(qna.qnaAnswer.answer) }
		 						</div>
		 					</td>
		 				</tr>
		 			</tbody>
		 		</table>
			 		
		 	</div> <!-- // mypage_short E -->
		 </c:if>
	 	