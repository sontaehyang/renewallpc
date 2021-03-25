<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop" 		uri="/WEB-INF/tlds/shop" %>

<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate value="${now}" pattern="yyyyMMdd" var="today" />

<c:set var="prefixMonth" value="최근 "/>

<!-- 내용 -->
	<div  class="mainArea">
		<!-- 본문 -->
		<div class="orderList">
			<ul class="list">
				<li class="typeA">
					<a href="<c:url value="/opmanager/order/new-order" />">
						<p class="num_new" id="new-order-count">0</p>
						<p class="tit">신규주문</p>
					</a>
					<p class="info">신규 주문수 &#40; ${shop:monthString(prefixMonth, '1')} &#41;</p>
				</li>
				<li class="typeA">
					<a href="<c:url value="/opmanager/order/shipping-ready" />">
						<p class="num_new" id="shipping-ready-count">0</p>
						<p class="tit">배송준비중</p>
					</a>
					<p class="info">배송준비중인 주문수 &#40; ${shop:monthString(prefixMonth, '1')} &#41;</p>
				</li>
				<li class="typeB">
					<a href="<c:url value="/opmanager/order/shipping" />">
						<p class="num_new" id="shipping-count">0</p>
						<p class="tit">배송중</p>
					</a>
					<p class="info">배송중인 주문수 &#40; ${shop:monthString(prefixMonth, '1')} &#41;</p>
				</li>
				<li class="typeB">
					<a href="<c:url value="/opmanager/order/confirm" />">
						<p class="num_new" id="confirm-count">0</p>
						<p class="tit">구매확정</p>
					</a>
					<p class="info">구매확정된 주문수 &#40; ${shop:monthString(prefixMonth, '1')} &#41;</p>
				</li>
			</ul><!--// list E-->
			<ul class="list">
				<li class="typeA">
					<a href="<c:url value="/opmanager/order/waiting-deposit?searchStartDate=${today}&searchEndDate=${today}" />">
						<p class="num_new" id="waiting-deposit-count">0</p>
						<p class="tit">입금대기</p>
					</a>
					<p class="info">금일 주문수 기준</p>
				</li>
				<li class="typeA">
					<a href="/opmanager/order/cancel/list?claimStatus=01">
						<p class="num_re" id="cancel-request-count">0</p>
						<p class="tit">취소요청</p>
					</a>
					<p class="info">현재까지 미처리된 요청 수</p>
				</li>
				<li class="typeB">
					<a href="/opmanager/order/exchange/list?claimStatus=01">
						<p class="num_re" id="exchange-request-count">0</p>
						<p class="tit">교환요청</p>
					</a>
					<p class="info">현재까지 미처리된 요청 수</p>
				</li>
				<li class="typeB">
					<a href="/opmanager/order/return/list?claimStatus=01">
						<p class="num_re" id="return-request-count">0</p>
						<p class="tit">반품요청</p>
					</a>
					<p class="info">현재까지 미처리된 요청 수</p>
				</li>
			</ul><!--// list E-->
		</div><!--// orderList E-->
		<ul class="visitor clear_fix">
			<li class="clear_fix">
				<span class="tit">오늘 방문자</span>
				<div class="count">
					<span class="num" id="today-visit-count">0</span>
				</div>
			</li>
			<li class="clear_fix">
				<span class="tit">신규가입자</span>
				<div class="count">
					<span class="total">TODAY<span class="num" id="today-subscribers-count">0</span></span>
					<span class="total">WEEKLY<span class="num" id="weekly-subscribers-count">0</span></span>
				</div>
			</li>
		</ul><!--// visitor E-->
		<div class="orderList">
			<ul class="list">
				<li class="delay">
					<div class="detail det01">
						<a href="<c:url value="/opmanager/order/new-order"/>">
							<p class="num" id="shipping-delay-count">0</p>
							<p class="tit">배송출고 지연</p>
						</a>
					</div>
					<p class="info">신규주문, 배송준비중 상태의 주문이 ${delayDays.shippingDelay }일 이상 출고되지 않음</p>
				</li>
				<li class="delay">
					<div class="detail det02">
						<a href="<c:url value="/opmanager/order/exchange/list"/>">
							<p class="num none" id="exchange-delay-count">0</p>
							<p class="tit">교환 지연</p>
						</a>
					</div>
					<p class="info">교환 승인된 상품의 처리가 ${delayDays.exchangeDelay }일 이상 회수되지 않음</p>
				</li>
				<li class="delay">
					<div class="detail det03">
						<a href="<c:url value="/opmanager/order/return/list"/>">
							<p class="num" id="return-delay-count">0</p>
							<p class="tit">반품 지연</p>
						</a>
					</div>
					<p class="info">반품 승인된 상품의 처리가 ${delayDays.returnDelay }일 이상 회수되지 않음</p>
				</li>
			</ul>
		</div><!--// orderList E-->
		
		<div class="main_notice clear_fix">
			<div class="notice">
				<span class="tit">공지사항</span>
				<c:forEach items="${noticeList }" var="notice">
					<a href="<c:url value="/opmanager/notice/edit/${notice.noticeId }"/>" class="cont">${notice.subject }</a>
					<span class="date">${op:date(notice.createdDate) }</span>
				</c:forEach>
			</div>
			<a href="<c:url value="/opmanager/notice/list"/>" class="more" title="공지사항 더보기"><img src="/content/opmanager/images/btn_more.gif" alt="+"></a>
		</div><!--// main_notice E-->
		
		<div class="summary_list clear_fix">
			<div class="board">
				<p class="list_tit">상품문의&#40;답변대기&#41;<a href="<c:url value="/opmanager/qna-item/list?answerCount=2"/>" class="number">${op:numberFormat(qnaCount) }건</a></p>
				<ul class="list">
					<c:if test="${!empty qnaList}">
						<c:forEach items="${qnaList}" var="qna">
							<li><a href="<c:url value="/opmanager/qna-item/view/${qna.qnaId}" />" class="cont">${qna.subject}</a><span class="date">${op:date(qna.createdDate)}</span></li>
						</c:forEach>
					</c:if>
					<c:if test="${empty qnaList}">
						<li>답변대기중인 상품문의가 없습니다.</li>
					</c:if>
				</ul>
			</div><!--// board E-->
			
			<div class="board">
				<p class="list_tit">1:1문의&#40;답변대기&#41;<a href="<c:url value="/opmanager/qna/list?answerCount=2"/>" class="number">${op:numberFormat(oneByOneCount) }건</a></p>
				<ul class="list">
					<c:if test="${!empty oneByOneList}">
						<c:forEach items="${oneByOneList}" var="oneByone">
							<li><a href="<c:url value="/opmanager/qna/view/${oneByone.qnaId}" />" class="cont">${oneByone.subject}</a><span class="date"> ${op:date(oneByone.createdDate)}</span></li>
						</c:forEach>
					</c:if>
					<c:if test="${empty oneByOneList}">
						<li>답변대기중인 1:1문의가 없습니다.</li>
					</c:if>
				</ul>
			</div><!--// board E-->
			<div class="board">
				<p class="list_tit">상품 평&#40;비공개&#41;<a href="<c:url value="/opmanager/item/review/list?reviewDisplayFlag=N"/>" class="number">${op:numberFormat(reviewCount) }건</a></p>
				<ul class="list">
					<c:if test="${!empty reviewList}">
						<c:forEach items="${reviewList}" var="review">
							<li><a href="<c:url value="/opmanager/item/review/edit/${review.itemReviewId}" />" class="cont">${review.content}</a><span class="date">${op:date(review.createdDate)}</span></li>
						</c:forEach>
					</c:if>
					<c:if test="${empty reviewList}">
						<li>등록된 상품문의가 없습니다.</li>
					</c:if>
				</ul>
			</div><!--// board E-->
		</div><!--// summary_list E-->
		
		<div class="currentState">
			<div class="state_box  ask">
				<p class="tit">입점 문의 현황</p>
				<ul class="det">
					<li><span class="det_tit">접수완료</span><a href="<c:url value="/opmanager/store-inquiry/list?status=0" />" class="det_num" id="receipt-finish-count">0</a></li>
					<li><span class="det_tit">처리중</span><a href="<c:url value="/opmanager/store-inquiry/list?status=1" />" class="det_num" id="process-count">0</a></li>
					<li><span class="det_tit">처리완료</span><a href="<c:url value="/opmanager/store-inquiry/list?status=2" />" class="det_num" id="process-finish-count">0</a></li>
				</ul>
			</div>
			<div class="state_box calc">
				<p class="tit">입점업체 정산현황</p>
				<ul class="det">
					<li><span class="det_tit">정산예정</span><a href="<c:url value="/opmanager/remittance/expected/list" />" class="det_num" id="remittance-expected-count">0</a></li>
					<li><span class="det_tit">정산확정</span><a href="<c:url value="/opmanager/remittance/confirm/list" />" class="det_num" id="remittance-confirm-count">0</a></li>
				</ul>
			</div>
		</div><!--// currentState E-->
	</div><!--// mainArea E-->
<script>
$(function() {
	setOrderCount();
	setStoreCount();
	setUserCount();
	setRemittanceCount();
	setShippingDelayCount();
});

//입점문의 Count 
function setStoreCount() {
	
	var complainCount = 0;
	$.post('/common/opmanager/store-count', null, function(resp){
		
		if (resp.data == undefined) {
			return;
		}
		
		$.each(resp.data, function(i, state) {
			
			$object = $('a#' + state.key + "-count");
			if ($object.size() > 0) {
				$object.html(Common.numberFormat(state.count));
			}
			
		});
		
	}, 'json'); 
	
}

//주문내역 Count 
function setOrderCount() {
	var complainCount = 0;
	$.post('/common/opmanager/order-count', {'month' : 1 }, function(resp){
		if (resp.data == undefined) {
			return;
		}
		$.each(resp.data, function(i, state) {
			$object = $('p#' + state.key + "-count");
			if ($object.size() > 0) {
				$object.html(Common.numberFormat(state.count));
			}
			
		});
		
	}, 'json'); 
}
<%--이상우 [2017-05-11 추가]--%>
//방문자,가입자 Count 
function setUserCount() {
	
	 $.post('/common/opmanager/user-count', null, function(resp){
		
		if (resp.data == undefined) {
			return;
		}
		
		$.each(resp.data, function(i, state) {
			$object = $('span#' + state.id + "-count"); 
			
	 		if ($object.size() > 0) {
				$object.html(Common.numberFormat(state.count));
			} 
			
		});
		
	}, 'json'); 
}
<%--이상우 [2017-05-11 추가]--%>
//정산예정, 확정 Count 
function setRemittanceCount() {
	
	 $.post('/common/opmanager/remittance-count', null, function(resp){
		
		if (resp.data == undefined) {
			return;
		}
		
		$.each(resp.data, function(i, state) {
			$object = $('a#' + state.id + "-count"); 
			
	 		if ($object.size() > 0) {
				$object.html(Common.numberFormat(state.count));
			} 
			
		});
		
	}, 'json'); 
}

<%--이상우 [2017-05-11 추가]--%>
//배송,교환,반품 지연 Count 
function setShippingDelayCount() {
	
	 $.post('/common/opmanager/shipping-delay-count', null, function(resp){
		
		if (resp.data == undefined) {
			return;
		}
		
		$.each(resp.data, function(i, state) {
			$object = $('p#' + state.id + "-count"); 
			$delayObject = $('span#' + state.id + "-day"); 
			
	 		if ($object.size() > 0) {
				$object.html(Common.numberFormat(state.count));
			} 
			
		});
		
	}, 'json'); 
}

</script>
