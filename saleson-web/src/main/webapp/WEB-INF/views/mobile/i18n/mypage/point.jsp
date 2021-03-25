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
			<h2>${op:message('M00246')} 조회</h2>
			<span class="his_back"><a href="javascript:void(0);" class="ir_pm" onclick="history.back();">뒤로가기</a></span>
		</div>
		<!-- //title -->
		
		<!-- 내용 : s -->
		<div class="con">
			<div class="mypage_wrap">
				
				<c:if test="${!empty list}">
				
				<div class="active_info">
				
					<ul>					
						<li>						
							<p class="num">							
								<c:set var="sum" value="0" />							
									<c:forEach var="pointsum" items="${list}">								
										<c:set var="sum">${ sum + pointsum.point} </c:set>								
									</c:forEach>							
								<span>${sum} P </span>							 
							</p>
								<p class="tit">사용가능 ${op:message('M00246')}</p>
						</li>																
						<li>
							<p class="num"><span>1,200</span>P</p>
							<p class="tit">소멸예정 ${op:message('M00246')}</p>
						</li>
					</ul>
				</div>
				<!-- //active_info -->
				
				
				<div class="date_search op-date_search">
						
						
					<div class="date_search_info">
						<p class="txt">찾아보고 싶은 날짜를 설정해 주세요.</p>
												 
						 <!-- <button class="date_search_btn">기간검색</button>   -->
						  <button class="date_search_btn op-date_search_btn" >기간검색</button>  
					</div> 
					
					<div class="date_search_con op-date_search_con">
					
						<form:form modelAttribute="pointParam" method="post">
						
						<ul>
						
								<li>
								
									<span class="del_tit t_black">적립기간</span>
									<div class="date_area op-day_btns">
									 
									 	<select id="op-pointdate">
									 	    <option value="clear" selected>전체</option>
											<option value="today">오늘</option>
											<option value="month-1">최근 1달</option>
											<option value="year-1">최근 1년 </option>																
										</select>
										
										<a href="javascript:;" hidden="true" class="btn_date clear" >전체</a>
									 	<a href="javascript:;" hidden="true" class="btn_date today" >오늘</a>
										<a href="javascript:;" hidden="true" class="btn_date month-1">최근 1달</a>   
										<a href="javascript:;" hidden="true" class="btn_date year-1">최근 1년</a>   
								 		
										<form:input type="hidden" path="searchStartDate" maxlength="8"/>
										<form:input type="hidden" path="searchEndDate" maxlength="8" /> 
										
									</div>
									<div class="date_area last">										
										<select name="plusminus">
											<option value="">전체</option>
											<option value="1">적립</option>
											<option value="2">사용</option>	
										</select>
									</div>
								</li>
								
								 <li>
								 
									<p class="desc">최근 1년까지의 적립내역이 제공되며, 한번 조회기간은 6개월 이내로 선택하셔야 됩니다.</p>
																	
								 </li> 
							
						</ul>	
							
						 <button type="submit" class="btn_st1 decision">검색</button> 
						 
						 </form:form>
						 
						 
					</div>
				</div>
				<!-- //date_search -->
				
				<div class="active_con">
					<ul id="op-list-data">
					
						<jsp:include page="../include/mypage-point-list.jsp"></jsp:include>
						
					</ul>
										
				<c:if test="${totalCount > 10 }">						
					<div class="load_more" id="op-list-more">
					<button type="button" class="btn_st2" onclick="javascript:paginationMore('point')"><span>더보기</span></button>
					</div>
				</c:if>	
				</div>
				<!-- //active_con -->
				
				</c:if>
				
				<c:if test="${empty list}">
						<div class="point_none">
							<p>사용 가능 쿠폰이 없습니다.</p>
						</div>
				</c:if>				
				
			</div>
			<!-- //mypage_wrap -->
		
		</div>
		<!-- 내용 : e -->
		
	</div>
	<!-- //#container -->
	<!-- container : e -->


<page:javascript>
<script type="text/javascript">

$(function(){
	// 이전페이지로 돌아왔을 때 More Data 유지.
	Mobile.pagination.init('point');
	
	showHideMoreButton();
	
	//기간검색
	$('.op-date_search .op-date_search_btn').click(function(){
		e.preventDefault();
		$(this).toggleClass('on');
		if($(this).hasClass('on')){
			$(this).text('닫기');
		} else {
			$(this).text('기간검색');
		}
		$('.op-date_search .op-date_search_con').toggle();
	});
});



var currentPage = 1;
function paginationMore(key) {
	currentPage++;
	$.post('/m/mypage/point/' + currentPage, 'page=' + currentPage, function(html) {
		$("#op-list-data").append(html);
		
		showHideMoreButton();
		
		Mobile.pagination.set(key);
	});
}

function showHideMoreButton() {
	if ($("#op-list-data").find(' > li').size() == Number('${pagination.totalItems}')) {
		$('#op-list-more').hide();
	}
}



$(function (){
	Common.DateButtonEvent.set('.op-day_btns a[class^=btn]', '', 'input[name="searchStartDate"]' , 'input[name="searchEndDate"]');
	dateEvent();
});



//이벤트 change (select 작동되면 a태그 작동 )
function dateEvent() {
	$("#op-pointdate").on("change", function(){
		var mode = $(this).find("option:selected").val();
		$("." + mode).trigger("click");
	});
}

</script>
</page:javascript>