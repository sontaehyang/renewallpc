<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>

		
		<div class="location">
			<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
		</div>

		<h3><span>상품 랭킹 선정 기준</span></h3>
			
			<div class="board_write">
				<form:form modelAttribute="rankingConfig" method="post">
					<form:hidden path="rankConfigCode"/>
					<table class="board_write_table" summary="상품 랭킹 선정 기준">
						<caption>상품 랭킹 선정 기준</caption>
						<colgroup>
							<col style="width:210px;" />
							<col style="*" />
						</colgroup>
						<tbody>
						 	<tr>
							 	<td class="label">수정일자</td>
							 	<td>
							 		<div>
							 			${op:datetime(rankingConfig.createdDate)}
								 	</div>
							 	</td>
							 </tr>
							 <tr>
							 	<td class="label">판매금액 기준 산정 기준 일수</td>
							 	<td>
							 		<div>
							 			<form:input path="salePriceDays" class="form-amount text-right  _number required" maxlength="3"/> 일
								 	</div>
							 	</td>
							 </tr>
							  <tr>
							 	<td class="label">판매수량 기준 산정 기준 일수</td>
							 	<td>
							 		<div>
							 			<form:input path="saleCountDays" class="form-amount text-right  _number required" maxlength="3"/> 일
								 	</div>
							 	</td>	
							 </tr>
							  <tr>
							 	<td class="label">상품리뷰 기준 산정 기준 일수</td>
							 	<td>
							 		<div>
							 			<form:input path="itemReviewDays" class="form-amount text-right  _number required" maxlength="3"/> 일
								 	</div>
							 	</td>
							</tr>
							 <tr>	
							 	<td class="label">판매금액 기준 산정 가중치</td>
							 	<td>
							 		<div>
							 			<form:input path="salePriceWeight" class="form-amount text-right  _number required" maxlength="3"/>
								 	</div>
							 	</td>
							 </tr>
							 <tr>
							 	<td class="label">판매수량 기준 산정 가중치</td>
							 	<td>
							 		<div>
							 			<form:input path="saleCountWeight" class="form-amount text-right  _number required" maxlength="3"/>
								 	</div>
							 	</td>
							 </tr>
							 <tr>	
							 	<td class="label">상품리뷰 기준 산정 가중치</td>
							 	<td>
							 		<div>
							 			<form:input path="itemReviewWeight" class="form-amount text-right  _number required" maxlength="3"/>
								 	</div>
							 	</td>
							 </tr>
							  <tr>	
							 	<td class="label">상품조회수 기준 산정 가중치</td>
							 	<td>
							 		<div>
							 			<form:input path="itemHitWeight" class="form-amount text-right  _number required" maxlength="3"/>
								 	</div>
							 	</td>
							 </tr>
						</tbody>					 
					</table>			
					
					<!-- 버튼시작 -->		 
					<div class="tex_c mt20">
						<button type="submit" class="btn btn-active"><span>${op:message('M00101')} </span></button>				 
					</div>			 
					<!-- 버튼 끝-->
				</form:form>
									 							
			</div> <!-- // board_write -->
			
			<div class="board_guide">
				<br/>
				<p class="tip">[안내]</p>
				<p class="tip">
					- 상품랭킹은 'TOP 100', '카테고리 중그룹별 TOP 100', '1차 카테고리별 TOP 100'이 하루에 한번 위에 설정하신 정보를 토대로 산정됩니다.<br/>
					- 각각의 가중치를 0으로 설정하면 랭킹 산정 조건에서 재외 됩니다.<br/><br/>
					- '판매금액'의 경우는 <strong>'결제 일자'</strong>가 오늘부터 설정하신 '기준 일수'전까지의 판매 데이터를 기준으로 하여 랭킹을 산정 합니다.<br/>
					- '판매금액'은 실제 판매 금액을 기준으로 합니다. (쿠폰등 할인금액은 재외)<br/><br/>
					- '판매수량'의 경우는 <strong>'결제 일자'</strong>가 오늘부터 설정하신 '기준 일수'전까지의 판매 데이터를 기준으로 하여 랭킹을 산정 합니다.<br/><br/>
					- '상품리류'의 경우는 <strong>'리뷰 작성일'</strong>이 오늘부터 설정하신 '기준 일수'전까지의 판매 데이터를 기준으로 하여 랭킹을 산정 합니다.<br/><br/>
					- '상품조회수'의 경우는 <strong>'상품이 등록된 시점'</strong>부터 오늘까지 <strong>상품 상세 페이지 접근수</strong>를 기준으로 하여 랭킹을 산정 합니다.<br/><br/>
					- 가중치는 각각의 조건별로 1위 ~ 100위를 구한후 (1위=100점/2위=99점...) 각각의 점수에 가중치를 곱한후 최종 점수가 가장 높은 100개의 상품을 산출 합니다. <br/><br/>
					ex) 상품 A (판매 금액 1위 / 리뷰 99위), 상품 B (판매금액 60위 / 리뷰 50위) <br/>
					&nbsp;&nbsp; - 상품 A (100(순위 점수) X 10(금액 가중치)) + (1(순위점수) X 15(가중치)) = 1,015점(상품 A 최종점수)<br/>
					&nbsp;&nbsp; - 상품 B (40(순위 점수) X 10(금액 가중치)) + (50(순위점수) X 15(가중치)) = 1,150점(상품 B 최종점수)<br/>
				</p> 
			</div>
			
<script type="text/javascript">
$(function() {
	// validator
	$('#config').validator(function() {});
});

</script>				
			