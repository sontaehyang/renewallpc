<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<%-- 

<c:forEach items="${qnaList}" var="list" varStatus="i">
	<div class="qna_cont">
		<div class="question">
			<a href="javascript:showAnswer('${i.count}')">
				<div>
					<span class="icon">${list.answerCount > 0 ? "답변완료" : "답변대기"}</span>
					<strong>[${list.qnaGroup}]</strong> 
					<span class="txt">${list.subject}</span>
				</div>
			</a>
			<span class="date">${op:date(list.createdDate)}</span>
		</div>
		<div class="answer quest-${i.count}" style="display: none;">
			<div class="q"> 
				<p>${list.question}</p>
			</div>
			<c:if test="${list.answerCount > 0}">
				<div class="a">
					<p>${list.qnaAnswer.answer}</p>
					<span class="date">(답변일 : ${op:date(list.qnaAnswer.answerDate)})</span>
				</div>
			</c:if>
		</div>
	</div><!--//qna_cont E-->
</c:forEach> --%>




<c:forEach items="${qnaList}" var="qna" varStatus="i">

	<li>
		<div class="main_txt">
			<a href="javascript:show(${i.index});" class="main_txt"> 
			<c:if test="${qna.answerCount == 1}">
					<span class="status_on">답변완료</span>
			</c:if> 
			<c:if test="${qna.answerCount == 0}">
					<span class="status_off">답변대기</span>
			</c:if> <span class="date">${fn:substring(qna.createdDate,0,4)}.${fn:substring(qna.createdDate,4,6)}.${fn:substring(qna.createdDate,6,8)}</span>
				<p class="content">${qna.subject}</p>
			</a>
		</div>
		<div class="sub_txt op-showview${i.index}" style="display: none;">
			<p class="question">${qna.question}</p>

			<c:if test="${qna.qnaAnswer.answer != null && qna.qnaAnswer.answer != '' }">
				<p class="answer">${qna.qnaAnswer.answer}
					<span class="a_date">(답변일 : <span>${fn:substring(qna.qnaAnswer.answerDate,0,4)}.${fn:substring(qna.qnaAnswer.answerDate,4,6)}.${fn:substring(qna.qnaAnswer.answerDate,6,8)}</span>)
					</span>
				</p>
			</c:if>

		</div>
	</li>

</c:forEach>

<page:javascript>
<script type="text/javascript">
	function inquiryItemListDelete(qnaId) {
		if (confirm("해당 문의 내용을 삭제하시겠습니까?")) {
			location.href = "/m/mypage/inquiry-delete/inquiry/" + qnaId;
		}
	}
	

	
	
</script>
</page:javascript>


