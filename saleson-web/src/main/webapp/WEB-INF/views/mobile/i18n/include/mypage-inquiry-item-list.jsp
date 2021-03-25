<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>


<%-- <c:forEach items="${qnaList}" var="list" varStatus="i">
	<div class="qna_cont">
		<div class="top">
			<span><img src="${list.itemImage}" height="53"></span>
			<p>${list.itemName}</p>
		</div>
		<div class="question">
			<a href="javascript:showAnswer('${i.count}')">
				<div>
					<span class="icon">${list.answerCount > 0 ? "답변완료" : "답변대기"}</span>
					<strong>[상품문의]</strong> 
					<span class="txt">${list.subject}</span>
				</div>
			</a>
			<span class="date">${op:date(list.createdDate)}</span>
		</div>
		<div class="answer quest-${i.count}" style="display: none;">
			<div class="q"> 
				<p>${list.question}</p>
			</div>	
				<p>
					<a href="javascript:inqueryItemListDelete('${list.qnaId}')">삭제</a>		 
				</p>
			<div class="btn_write">
				<button type="button" onclick="javascript:inqueryItemListDelete('${list.qnaId}')">삭제</button>
			</div>
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

					
					<c:forEach items="${qnaList}" var="list" varStatus="i">

						<li>
								<div class="active_title">
								
								
									<p class="txt">
										<c:choose>
											<c:when test="${list.answerCount > 0}">
												<span class="label reply_comp">답변완료</span>
											</c:when>
											<c:otherwise>
												<span class="label reply_stand">답변대기</span>
											</c:otherwise>
										</c:choose>
										
										<span class="date">${fn:substring(list.createdDate,0,4)}.${fn:substring(list.createdDate,4,6)}.${fn:substring(list.createdDate,6,8)}</span>
									</p>
								</div>
							
								<div class="active_product">
									<a href="javascript:show(${i.index});">
										<div class="active_img">
											<%-- <img src="${list.imageSrc}" alt="${list.itemName }"> --%>
											<img src="${shop:loadImageBySrc(list.imageSrc, 'XS')}" alt="${list.itemName }">
										</div>
										
										<div class="active_name">
											<p class="tit">${list.itemName}</p>
											<p class="q_tit">${list.subject}</p>
										</div>										
									</a>
								</div>
								<div class="q_con op-answer-con op-showview${i.index}" style="display: none;">
									<p class="question">${list.question}</p>
									
									<c:choose>
										<c:when test="${list.answerCount > 0}">
											<p class="answer">${list.qnaAnswer.answer} <br/>(답변일 : ${op:date(list.qnaAnswer.answerDate)}) </p>
											<span class="a_date"></span>
										</c:when>
										<c:otherwise>
											<div class="btn_wrap">
												<%--<a href="#" class="btn_st3 t_lgray s_small b_white">수정</a> --%>										
												<a href="javascript:inquiryItemListDelete('${list.qnaId }');" class="btn_st3 t_lgray s_small b_white">삭제</a>
											</div>
										</c:otherwise>
									</c:choose>
								
								</div>
						</li>
						
					</c:forEach>



<page:javascript>
<script type="text/javascript">
	function inquiryItemListDelete(qnaId) {
		if (confirm("해당 문의 내용을 삭제하시겠습니까?")) {
			location.href = "/m/mypage/inquiry-delete/inquiry-item/" + qnaId;
		}
	}
	

	
	
</script>
</page:javascript>