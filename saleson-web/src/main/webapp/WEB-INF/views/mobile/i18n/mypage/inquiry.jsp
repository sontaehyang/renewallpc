<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
	
	<!-- container : s -->
	<div id="container">
		<div class="title">
			<h2>활동정보</h2>
			<span class="his_back"><a href="javascript:void(0);" class="ir_pm" onclick="history.back();">뒤로가기</a></span>
			<ul class="tab_list01 four_tab">
				<li><a href="/m/mypage/wishlist">관심상품</a></li>
				<li><a href="/m/mypage/review-nonregistered">이용후기</a></li>
				<li><a href="/m/mypage/inquiry-item">상품Q&amp;A</a></li>
				<li class="on"><a href="#">1:1문의</a></li>
			</ul>
		</div>
		<!-- //title -->
		
		<!-- 내용 : s -->
		<div class="con">
			<div class="mypage_wrap">
				<div class="date_search op-date-search">
					<div class="date_search_info">
						<p class="txt">찾아보고 싶은 날짜를 설정해 주세요.</p>
						 <button class="date_search_btn op-date-search-btn" >기간검색</button> 
					</div>
					
					<div class="date_search_con op-date-search-con">
					<form:form modelAttribute="qnaParam" method="get">
					<form:hidden path="page" />
						<ul>
								
							<li>
								
								<span class="del_tit t_black">답변기간</span>
									<div class="date_area op-day_btns">
									
										<select  id="op-pointdate">
											 <option value="clear" selected>전체</option>
											<option value="today">오늘</option>
											<option value="month-1">최근 1달</option>
											<option value="year-1">최근 1년</option>
										</select>
									
									 	<a href="javascript:;" hidden="true" class="btn btn_date clear" >전체</a>
									 	<a href="javascript:;" hidden="true" class="btn btn_date today" >오늘</a>
										<a href="javascript:;" hidden="true" class="btn btn_date month-1">최근 1달</a>   
										<a href="javascript:;" hidden="true" class="btn btn_date year-1">최근 1년</a>   
									
										<form:input type="hidden" path="searchStartDate" maxlength="8"/>
										<form:input type="hidden" path="searchEndDate" maxlength="8" /> 
									
									</div>							
							</li>
							<li>
								
								<p class="desc">최근 1년까지의 답변내역이 제공되며, 한번 조회기간은 6개월 이내로 선택하셔야 됩니다.</p>
								
							</li>
							
						</ul>
						<button type="submit" class="btn_st1 decision">검색</button>
						
						</form:form>
						
					</div>
				</div>
				<!-- //date_search -->
				
				<div class="review_top">
					<a href="/m/qna" class="review_write_btn"><span>문의하기</span></a>
				</div>
				<!-- //review_top -->
				
				<div class="active_con">
					<ul id="op-list-data">
					
						<jsp:include page="../include/mypage-inquiry-list.jsp"></jsp:include>
						
					</ul>
					
					<c:if test="${empty qnaList }">
						<ul>
							<li>
								<div class="common_none">
									<p>1:1 문의가 없습니다.</p>
								</div>
							</li>
						</ul>				
					</c:if>
					
					
						<div class="load_more" id="op-list-more"><button type="button" class="btn_st2" onclick="javascript:paginationMore('inquiry')"><span>더보기</span></button></div>
					
					
				</div>
				<!-- //active_con -->

				
			</div>
			<!-- //mypage_wrap -->
		
		</div>
		<!-- 내용 : e -->
		
	</div>
	<!-- //#container -->
	<!-- container : e -->

<page:javascript>	
<script type="text/javascript">
$(function() {

 	Common.DateButtonEvent.set('.op-day_btns a[class^=btn]', '', 'input[name="searchStartDate"]' , 'input[name="searchEndDate"]');
	dateEvent();
	 
	
	// 더보기 버튼
	showHideMoreButton();
	
	/*  $('#searchStartDate').on("change", function(){
		$('#qnaParam').submit();	
	}); */
	 
	//기간검색
		$('.op-date-search .op-date-search-btn').click(function(){
			e.preventDefault();
			$(this).toggleClass('on');
			if($(this).hasClass('on')){
				$(this).text('닫기');
			} else {
				$(this).text('기간검색');
			}
			$('.op-date-search .op-date-search-con').toggle();
		});
	 
});
 
 
function paginationMore(key) {
	 $('input[name="page"]', $("#qnaParam")).val(Number($('input[name="page"]', $("#qnaParam")).val()) + 1);
 	$.post('/m/mypage/inquiry/list', $("#qnaParam").serialize(), function(html) {
 		$("#op-list-data").append(html);
 		// 더보기 버튼 
 		showHideMoreButton();
 		
 		history.pushState(null, null, "/m/mypage/inquiry?" + $("#qnaParam").serialize());
 	});
}

function showHideMoreButton() {
 	if ($("#op-list-data").find(' > li').size() == Number('${pagination.totalItems}') || Number('${pagination.totalItems}') == 0) {
 		$('#op-list-more').hide();
 	}
}

 //기존 답변 토글  스크립트
/*  function showAnswer(index) {
	 var answer = $('.quest-' + index);
	 
	 if (answer.hasClass("view-on") == false) {
		 answer.show();
		 answer.addClass("view-on");
	 } else {
		 answer.hide();
		 answer.removeClass("view-on");
	 }
 } */

//새로만든 답변 토글 스크립트
function show(index){
	 
	 var answer = $('.op-showview'+index);
	
	 answer.toggle();	 
	 
}
 
 
//이벤트 change (select 작동되면 a태그 작동 )
function dateEvent() {
 	$("#op-pointdate").on("change", function(){
 		var mode = $(this).find("option:selected").val();
 		$("." + mode).trigger("click");
 	});
}
</script>
</page:javascript>