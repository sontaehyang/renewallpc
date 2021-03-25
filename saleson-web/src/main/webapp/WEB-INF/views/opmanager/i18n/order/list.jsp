<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>

<style type="text/css">
	.sort_area button, .sort_area a {margin-top : 5px;}
</style>

<div class="location">
	<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
</div>
<!-- 본문 -->


<!--전체주문 내역 시작-->
<h3><span>${ title }<!-- 전체주문 내역 --></span></h3>
<form:form modelAttribute="orderSearchParam" action="" method="post">

	<div class="board_write">

		<table class="board_write_table" summary="${ title }">
			<caption>${ title }</caption>
			<colgroup>
				<col style="width:150px;" />
				<col style="width:*;" />
			</colgroup>
			<tbody>
				 <tr>
				 	<td class="label">${op:message('M00011')} <!-- 검색구분 --> </td>
				 	<td>
				 		<div>
							<form:select path="where" title="${op:message('M00011')}">
								<form:option value="USER_NAME" label="${op:message('M00014')}" /><!-- 주문자명 -->
								<form:option value="RECEIVE_NAME" label="${op:message('M00017')}" /><!-- 수령인명 -->
								<form:option value="PHONE" label="${op:message('M00016')}" /><!-- 전화번호 -->
								<form:option value="ORDER_ID" label="${op:message('M00013')}" /><!-- 주문번호 -->
								<form:option value="USER_ID" label="${op:message('M00015')}" /><!-- 주문자ID -->
								<form:option value="ITEM_NAME" label="${op:message('M00018')}" /><!-- 상품명 -->
								<form:option value="ITEM_CODE" label="${op:message('M00019')}" /><!-- 상품번호 -->
								<form:option value="EMAIL" label="${op:message('M00020')}" /><!-- 메일주소 -->
								<form:option value="COMPANY_NAME" label="구매자 회사명" />
								<form:option value="RECEIVE_COMPANY_NAME" label="수취인 회사명" />
							</form:select>
							<form:input path="query" class="input_txt required _filter" title="${op:message('M00022')}" maxlength="20" /><!-- 검색어 -->
						</div>
				 	</td>
				 </tr>
				 <tr>
				 	<td class="label">${op:message('M00023')}</td><!-- 주문일자 -->
				 	<td>
				 		<div>
				 			<form:select path="searchDateType">

				 				<c:choose>
				 					<c:when test="${searchOrderStatus == '3' || searchOrderStatus == '4'}">
				 						<form:option value="UPDATE_DATE" label="결재 확인일" />
				 					</c:when>
				 					<c:when test="${searchOrderStatus == '13'}">
				 						<form:option value="UPDATE_DATE" label="취소 일자" />
				 					</c:when>
				 					<c:when test="${searchOrderStatus == '11'}">
				 						<form:option value="UPDATE_DATE" label="반품 일자" />
				 					</c:when>
				 					<c:when test="${searchOrderStatus == '8'}">
				 						<form:option value="UPDATE_DATE" label="교환 일자" />
				 					</c:when>
				 					<c:otherwise>

				 					</c:otherwise>
				 				</c:choose>

				 				<form:option value="O.CREATED_DATE" label="${op:message('M00023')}" /> <!-- 주문 일자 -->

				 			</form:select>
							<span class="datepicker"><form:input path="searchStartDate" class="datepicker" maxlength="8" title="${op:message('M00024')}" /><!-- 주문일자 시작일 --></span>
							<form:select path="searchStartDateTime">
								<form:option value="00" label="00시" />
								<c:forEach varStatus="i" begin="1" end="23">
									<c:if test="${i.count < 10 }">
										<form:option value="0${i.count}" label="0${i.count}시" />
									</c:if>
									<c:if test="${i.count >= 10 }">
										<form:option value="${i.count}" label="${i.count}시" />
									</c:if>
								</c:forEach>
							</form:select>
							<span class="wave">~</span>
							<span class="datepicker"><form:input path="searchEndDate" class="datepicker" maxlength="8" title="${op:message('M00025')}" /><!-- 주문일자 종료일 --></span>
							<form:select path="searchEndDateTime">
								<form:option value="00" label="00시" />
								<c:forEach varStatus="i" begin="1" end="23">
									<c:if test="${i.count < 10 }">
										<form:option value="0${i.count}" label="0${i.count}시" />
									</c:if>
									<c:if test="${i.count >= 10 }">
										<form:option value="${i.count}" label="${i.count}시" />
									</c:if>
								</c:forEach>
							</form:select>
							<span class="day_btns">
								<a href="javascript:;" class="btn_date today">${op:message('M00026')}</a><!-- 오늘 -->
								<a href="javascript:;" class="btn_date week-1">${op:message('M00027')}</a><!-- 1주일 -->
								<a href="javascript:;" class="btn_date day-15">${op:message('M00028')}</a><!-- 15일 -->
								<a href="javascript:;" class="btn_date month-1">${op:message('M00029')}</a><!-- 한달 -->
								<a href="javascript:;" class="btn_date month-3">${op:message('M00030')}</a><!-- 3개월 -->
								<a href="javascript:;" class="btn_date year-1">${op:message('M00031')}</a><!-- 1년 -->
								<a href="javascript:;" class="btn_date day-1" onclick='setYesterDayTimeToCurrentTime("15")'>전일 15시부터 현재까지</a><!-- 1년 -->
							</span>
						</div>
				 	</td>
				 </tr>
				 <c:if test="${ not empty searchOrderStatusList }">
					 <tr>
					 	<td class="label">${op:message('M00032')}<!-- 진행상태 --></td>
					 	<td>
					 		<div>
					 			<form:checkboxes items="${ searchOrderStatusList }" path="searchOrderStatus" itemLabel="label" itemValue="value" />
							</div>
					 	</td>
					 </tr>
				 </c:if>

				 <c:if test="${searchOrderStatus == '13'}">
					 <tr>
					 	<td class="label">취소구분</td>
					 	<td>
					 		<div>
								<form:radiobutton path="cancelType" id="cancelType1" label="${op:message('M00039')}"/>
								<form:radiobutton path="cancelType" id="cancelType2" value="before" label="매입전취소" />
								<form:radiobutton path="cancelType" id="cancelType3" value="after" label="매입후취소" />
							</div>
					 	</td>
					 </tr>
				 </c:if>
			</tbody>
		</table>

	</div> <!-- // board_write -->

	<!-- 버튼시작 -->
	<div class="btn_all">
		<div class="btn_left">
			<button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='/opmanager/order/list<c:if test="${ !empty searchOrderStatus }">/${searchOrderStatus}</c:if>'"><span class="glyphicon glyphicon-repeat"></span> ${op:message('M00047')}<!-- 초기화 --></button>
		</div>
		<div class="btn_right">
			<button type="submit" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> ${op:message('M00048')}<!-- 검색 --></button>
		</div>
	</div>
	<!-- 버튼 끝-->

	<div class="count_title mt20">
		<h5>
			${op:message('M00039')} : ${op:numberFormat(totalCount)} ${op:message('M00743')}
		</h5>
		<span>
			<form:select path="orderBy" title="등록일선택">

				<c:if test="${not empty searchOrderStatus}">
					<c:if test="${searchOrderStatus == '3' || searchOrderStatus == '4'}">
						<form:option value="UPDATE_DATE" label="결재 확인일" />
					</c:if>
					<c:if test="${searchOrderStatus == '13'}">
						<form:option value="UPDATE_DATE" label="취소 일자" />
					</c:if>
					<c:if test="${searchOrderStatus == '11'}">
						<form:option value="UPDATE_DATE" label="반품 일자" />
					</c:if>
					<c:if test="${searchOrderStatus == '8'}">
						<form:option value="UPDATE_DATE" label="교환 일자" />
					</c:if>
				</c:if>

				<form:option value="CREATED_DATE" label="주문일자" /> <!-- 등록일 -->
				<form:option value="ORDER_ID" label="${op:message('M00202')}" /> <!-- 등록일 -->
				<form:option value="MAIL_SEND_FLAG" label="${op:message('M00057')} + ${op:message('M00202')}(${op:message('M00689')})" /> <!-- 메일 -->

			</form:select>
			<form:select path="sort" title="검색방법 선택">
				<form:option value="DESC" label="${op:message('M00689')}" />    <!-- 내림차순 -->
				<form:option value="ASC" label="${op:message('M00690')}" />    <!-- 오름차순 -->
			</form:select>

			${op:message('M00052')} : <!-- 출력수 -->
			<form:select path="itemsPerPage" title="${op:message('M00054')}${op:message('M00052')}"
				onchange="$('form#orderSearchParam').submit();"> <!-- 화면 출력수 -->
				<form:option value="50" label="50${op:message('M00053')}" /> <!-- 개 출력 -->
				<form:option value="100" label="100${op:message('M00053')}" /> <!-- 개 출력 -->
				<form:option value="200" label="200${op:message('M00053')}" /> <!-- 개 출력 -->
				<form:option value="500" label="500${op:message('M00053')}" /> <!-- 개 출력 -->
			</form:select>
		</span>
	</div>
</form:form>

<c:if test="${searchOrderStatus eq '3' }">
	<div class="board_guide" style="border:1px solid #d5d5d5; padding: 15px; margin-top:15px;">

		<p class="tip">
			<span class="datepicker"><input type="text" id="erpDownDate" class="datepicker" /></span>
			<a href="javascript:;" class="btn_write gray_small" onclick="downloadErpExcel()"><img src="/content/opmanager/images/icon/icon_excel.png" alt=""><span>ERP 매출 일괄등록 엑셀 다운로드</span> </a> <!-- 엑셀 다운로드 -->
			<a href="javascript:;" class="btn_write gray_small" onclick="downloadErpExcel2()"><img src="/content/opmanager/images/icon/icon_excel.png" alt=""><span>원가 일괄등록 엑셀 다운로드</span> </a>
		</p>
		<p class="tip">
			 <br/>
			ERP 매출 일괄등록 엑셀 다운로드는 일자별 통계기준으로 [매출(결제 확인일) / 취소(취소일)] 내역을 다운로드 할수 있습니다.<br/>
			만약 예외 상황이 있는경우는 엑셀을 가공처리 하신후 ERP에 일괄 적용하시면됩니다.<br />
		</p>
	</div>

	<div class="board_guide" style="border:1px solid #d5d5d5; padding: 15px; margin-top:15px;">

		<p class="tip">
			<a href="javascript:uploadExcel('11st')" class="btn_write gray_small"><img src="/content/opmanager/images/icon/icon_excel.png" alt=""><span>11st 주문서 업로드</span> </a>
			<a href="javascript:uploadExcel('gmarket')" class="btn_write gray_small"><img src="/content/opmanager/images/icon/icon_excel.png" alt=""><span>지마켓&옥션 주문서 업로드</span> </a>
			<a href="javascript:uploadExcel('storefarm')" class="btn_write gray_small"><img src="/content/opmanager/images/icon/icon_excel.png" alt=""><span>스토어팜 주문서 업로드</span> </a>
			<a href="javascript:uploadExcel('beautycall')" class="btn_write gray_small"><img src="/content/opmanager/images/icon/icon_excel.png" alt=""><span>뷰티콜 주문서 업로드</span> </a>
			<a href="javascript:uploadExcel('allthat')" class="btn_write gray_small"><img src="/content/opmanager/images/icon/icon_excel.png" alt=""><span>올댓 쇼핑 주문서 업로드</span> </a>
			<a href="javascript:uploadExcel('naver')" class="btn_write gray_small"><img src="/content/opmanager/images/icon/icon_excel.png" alt=""><span>네이버 페이 주문서 업로드</span> </a>
		</p>
		<p class="tip">
			<br/>네이버 페이의 경우 옵션가격이 상품가격(업로드용 엑셀 U행)에 포함 되어있습니다. 따라서 옵션가격은 0원으로 업로드 됩니다.

			<br/>
			<br/>
<a href="javascript:openMarketTip()" style="color:#0081FF"><strong>주의사항 보기!!</strong></a>
<div id="openmarket_tip" style="display:none">
<pre>
1. 11st
  - 추가 구성품의 경우 G행에 정보가 (추가상품)으로 시작해야 합니다.
  - 상품 코드 : AL, BQ
  - 옵션 코드 : BR

2. 지마켓/옥션
  - 복합 옵션 상품
    1) 옥션
      문자(:) 을 기준으로 왼쪽에 있는 정보에 문자(_)가 있는경우 복합상품입니다. 문자(:)을 기준으로 오른쪽 정보로 상품정보 조회후 주문정보가 생성됩니다.
      ex) 침대 다리_침대 상판:11412. 스테인레스 45cm_11408. 마사지침대(홀) 흰색 폭75cm/10000원/1개

    2) 지마켓
      문자(,) 을 기준으로 복합 상품을 체크 합니다.
      ex) 침대 다리:11412. 스테인레스 45cm, 침대상판:11408. 마사지침대(홀) 흰색 폭75cm/10000원/1개
  - 추가 구성
    L행에 정보에 문자(,)가 있는경우 복수의 추가구성 상품을 등록할수 있습니다.
  - 상품 코드 : AD, AT
  - 옵션 코드 : AU

3. 네이버
  - 복합 옵션 상품
    R행에 문자(_)를 기준으로 복합상품을 체크 합니다.
    ex) 침대 다리:11412. 스테인레스 45cm_침대상판:11408. 마사지침대(홀) 흰색 폭75cm
  - 상품 코드 : O, BI
  - 옵션 코드 : BJ

4. 스토어팜
  - 복합 옵션 상품
    R행에 문자(/)를 기준으로 복합상품을 체크 합니다.
    ex) 침대 다리:11412. 스테인레스 45cm/침대상판:11408. 마사지침대(홀) 흰색 폭75cm
  - 상품 코드 : AK, BL
  - 옵션 코드 : BM
<font color='red'>
1. 상품명(옵션명)에 위에 명시된 복합옵션 구분 문자가 들어 있는경우 업로드하실 엑셀에서 해당 문자를 제거 후 업로드 하시기 바랍니다.
ex) 스토어팜 엑셀 R행에 "침대시트: 12705. 일회용/침대시트 (방수)_브라운" 처럼 복합 옵션 구분자가 상품명에 들어가 있는경우

2. 각 오픈마켓 상품코드, 옵션코드 행에 코드가 입력된경우 해당 코드를 사용해서 업로드 됩니다.
ex) 스토어팜 엑셀 R행에 "침대시트: 일회용/침대시트 (방수)"가 입력되어 있고 BM행에 "12705"가 입력되어 있으면 정상업로드 가능

3. 오픈마켓 상품이 복합 옵션으로 구성되어있는경우 ex) 침대 다리:11412. 스테인레스 45cm_침대상판:11408. 마사지침대(홀) 흰색 폭75cm
웹상품중에 11412, 11408으로 구성되어진 상품이 반드시 1개가 검색되어야 정상 업로드 가능합니다.
만약 상품이 여러개 검색 되었을경우 상품코드(스토어팜의 경우 : AK, BL행)에 상품번호를 입력해서 업로드하시기 바랍니다.
</font>
</pre>
</div>
		</p>
	</div>

 	<div class="board_guide" style="border:1px solid #d5d5d5; padding: 15px; margin-top:15px;">

		<p class="tip">
			<c:forEach items="${brandList}" var="brand">
				<a href="javascript:downloadBrandExcel('${brand}')" class="btn_write gray_small"><img src="/content/opmanager/images/icon/icon_excel.png" alt=""><span>${brand} 주문서 다운로드</span> </a>
			</c:forEach>
			<c:if test="${ empty brandList }">
				다운로드할 업체배송 주문이 없습니다.
			</c:if>
		</p>
		<p class="tip">
			<br/>
			선택 다운로드 : 선택하신 주문에 해당 브랜드 상품이 있는경우에 다운로드 됩니다.
			<br/>
			미 선택 다운로드 : 하단에 조회된 주문(${op:numberFormat(totalCount)}건)중에 해당 브랜드 상품 전체를 다운로드 합니다.
		</p>
	</div>

</c:if>

<c:if test="${searchOrderStatus eq '4' }">
	<div class="board_guide" style="border:1px solid #d5d5d5; padding: 15px; margin-top:15px;">

		<p class="tip">
			<a href="javascript:;" class="btn_write gray_small" onclick="downloadShippingExeclByOpenMarket('11st')"><img src="/content/opmanager/images/icon/icon_excel.png" alt=""><span>11st 엑셀 다운로드</span> </a> <!-- 엑셀 다운로드 -->
			<a href="javascript:;" class="btn_write gray_small" onclick="downloadShippingExeclByOpenMarket('gmarket')"><img src="/content/opmanager/images/icon/icon_excel.png" alt=""><span>지마켓&옥션 엑셀 다운로드</span> </a> <!-- 엑셀 다운로드 -->
			<a href="javascript:;" class="btn_write gray_small" onclick="downloadShippingExeclByOpenMarket('storefarm')"><img src="/content/opmanager/images/icon/icon_excel.png" alt=""><span>스토어팜 엑셀 다운로드</span> </a> <!-- 엑셀 다운로드 -->
			<a href="javascript:;" class="btn_write gray_small"onclick="downloadShippingExeclByOpenMarket('beautycall')"><img src="/content/opmanager/images/icon/icon_excel.png" alt=""><span>뷰티콜 엑셀 다운로드</span> </a> <!-- 엑셀 다운로드 -->
			<a href="javascript:;" class="btn_write gray_small" onclick="downloadShippingExeclByOpenMarket('allthat')"><img src="/content/opmanager/images/icon/icon_excel.png" alt=""><span>올댓 쇼핑 엑셀 다운로드</span> </a> <!-- 엑셀 다운로드 -->
			<a href="javascript:;" class="btn_write gray_small" onclick="downloadShippingExeclByOpenMarket('naver')"><img src="/content/opmanager/images/icon/icon_excel.png" alt=""><span>네이버 페이 엑셀 다운로드</span> </a> <!-- 엑셀 다운로드 -->
		</p>
		<p class="tip">
			<br/>선택한 항목을 다운로드 합니다.
			<br/>교환/부분 반품 처리되어 신규로 생성된 오픈마켓 주문의 경우 오픈마켓 송장 업로드용 엑셀을 받으실수 없습니다.
			<br/>오픈마켓 주문을 "주문서 수정" 하신경우 다운로드 기능을 사용하실수 없습니다.
		</p>
	</div>
</c:if>

<c:if test="${searchOrderStatus == '9' || searchOrderStatus == '10'}">
	<div class="board_guide" style="border:1px solid #d5d5d5; padding: 15px; margin-top:15px;">
		<p class="tip">
			환불의 경우 환불 요청이 들어온 주문(기존주문)은 "전체 취소"됩니다.<br/>
			현재 구성하신 주문이 "신규 주문"으로 생성 되오니 환불 요청이 들어온 상품 이외에 환불 요청이 들어오지 않은 기존 상품들도 유지하신후 저장하셔야 합니다.<br/>
			생성된 "신규 주문"은 교환상품 발송 대기 상태로 "배송완료 목록"에서 확인하실수 있습니다.<br/><br/>
			취소/생성 되는 주문은 처리 시점을 기준으로 통계에 잡히게 됩니다.

			<br/><br/>
			[오픈마켓 주문] <br/>
			오픈 마켓 주문의 경우 주문 상품의 수정기능을 이용하실수 없으며, 전체 환불 완료 상태로 변경만 가능합니다.
		</p>
	</div>
</c:if>

<c:if test="${searchOrderStatus == '6' || searchOrderStatus == '7'}">
	<div class="board_guide" style="border:1px solid #d5d5d5; padding: 15px; margin-top:15px;">
		<p class="tip">
			교환의 경우 교환 요청이 들어온 주문(기존주문)은 "전체 취소"됩니다.<br/>
			현재 구성하신 주문이 "신규 주문"으로 생성 되오니 교환 요청이 들어온 상품 이외에 교환 요청이 들어오지 않은 기존 상품들도 유지하신후 저장하셔야 합니다.<br/>
			생성된 "신규 주문"은 교환상품 발송 대기 상태로 "배송준비중 목록"에서 확인하실수 있습니다.<br/><br/>
			취소/생성 되는 주문은 처리 시점을 기준으로 통계에 잡히게 됩니다.

			<br/><br/>
			[오픈마켓 주문] <br/>
			오픈 마켓 주문의 경우 주문 상품의 수정기능을 이용하실수 없으며, 전체 교환 완료 상태로 변경만 가능합니다.
		</p>
	</div>
</c:if>

<div class="board_guide" style="border:1px solid #d5d5d5; padding: 15px; margin-top:15px;">
	<p class="tip">
		<a href="javascript:;" class="btn_write gray_small" onclick="downloadOrderExcel('${searchOrderStatus}', ${totalCount})"><img src="/content/opmanager/images/icon/icon_excel.png" alt=""><span>주문내역 다운로드</span> </a>
	</p>

	<p class="tip">
		<br/>
		<c:choose>
			<c:when test="${ not empty searchOrderStatus }">
				<c:if test="${totalCount >= 1000}">
					주문 내역이 1,000건이 넘는경우 선택 다운로드 혹은 일자별 검색후 다운로드 하시기 바랍니다.
					<br/>
				</c:if>
				다운로드 하실 주문을 선택후 다운로드 하시면 선택다운로드 됩니다.
			</c:when>
			<c:otherwise>
				주문전체 목록에서 주문목록 다운로드는 선택 다운로드만 가능합니다.
				<br/>한번에 선택 다운로드 가능한 주문은 최대 500건 입니다.(우측 출력수 조정 최대치)
			</c:otherwise>
		</c:choose>
	</p>
</div>


<form id="listForm" name="listForm">
	<input type="hidden" name="mode" value="" />
	<div class="sort_area mt15">
		<div>
			<span>${op:message('M00804')} <!-- 선택한 주문서를 일괄 --></span>

			<c:if test="${not empty searchOrderStatus}">
				<select title="주문상태" id="orderStatus1">
					<c:choose>
						<c:when test="${searchOrderStatus == '1'}">
							<option value="50">결제완료</option>
							<option value="1">배송준비중</option>
							<!-- option value="99">주문취소</option -->
						</c:when>
						<c:when test="${searchOrderStatus == '2' }">
							<option value="1">배송준비중</option>
							<!-- option value="99">주문취소</option -->
						</c:when>
						<c:when test="${searchOrderStatus == '3'}">
							<option value="2">배송중</option>
							<!-- option value="99">주문취소</option -->
						</c:when>
						<c:when test="${searchOrderStatus == '4'}">
							<option value="3">배송완료</option>
							<option value="31">교환처리중</option>
							<option value="41">반품처리중</option>
						</c:when>
						<c:when test="${searchOrderStatus == '5'}">
							<option value="31">교환처리중</option>
							<option value="41">반품처리중</option>
						</c:when>
						<c:when test="${searchOrderStatus == '9'}">
							<option value="41">반품처리중</option>
						</c:when>
						<c:when test="${searchOrderStatus == '6'}">
							<option value="31">교환처리중</option>
							<!-- option value="36">교환완료</option -->
						</c:when>
						<c:when test="${searchOrderStatus == '7'}">
							<!-- option value="36">교환완료</option -->
						</c:when>
					</c:choose>
				</select>
				<button type="button" id="change_order_status1" class="btn btn-dark-gray btn-sm">${op:message('M00433')}</button> <!-- 변경 -->
			</c:if>

			<button type="button" class="btn ctrl_btn order_print">${op:message('M00343')}</button> <!-- 주문서 인쇄 -->
			<button type="button" class="btn ctrl_btn order_mail">${op:message('M00803')}</button> <!-- 주문 메일 발송 -->

			<c:if test="${searchOrderStatus eq '3' }">


				<a href="#" class="btn_write gray_small" onclick="downloadShippingTargetExecl()"><img src="/content/opmanager/images/icon/icon_excel.png" alt=""><span>송장 미등록 주문 엑셀다운로드</span> </a> <!-- 엑셀 다운로드 -->
				<a href="javascript:uploadExcel('shipping')" class="btn_write gray_small"><img src="/content/opmanager/images/icon/icon_excel.png" alt=""><span>송장번호 엑셀업로드</span> </a>

				<a href="#" class="btn_write gray_small" onclick="downloadForDeliveryCompanyExecl()"><img src="/content/opmanager/images/icon/icon_excel.png" alt=""><span>배송업체 전달용 엑셀 다운로드</span> </a> <!-- 엑셀 다운로드 -->
				<a href="javascript:uploadExcel('cj-shipping')" class="btn_write gray_small"><img src="/content/opmanager/images/icon/icon_excel.png" alt=""><span>CJ 대한통운 송장번호 업로드</span> </a>

			</c:if>

		</div>
	</div>


	<div class="board_list">

		<c:choose>
			<c:when test="${searchOrderStatus == '1'}">
				<jsp:include page="./include/bank-view-list.jsp" />
			</c:when>
			<c:when test="${searchOrderStatus == '2'}">
				<jsp:include page="./include/pay-view-list.jsp" />
			</c:when>
			<c:otherwise>
				<jsp:include page="./include/item-view-list.jsp" />
			</c:otherwise>
		</c:choose>

		<c:if test="${empty orderList}">
		<div class="no_content">
			${op:message('M00473')} <!-- 데이터가 없습니다. -->
		</div>
		</c:if>
	</div><!--// board_write E-->

	<div class="sort_area mt15">
		<div>
			<span>${op:message('M00804')} <!-- 선택한 주문서를 일괄 --></span>

			<c:if test="${not empty searchOrderStatus}">
				<select title="주문상태" id="orderStatus2">
					<c:choose>
						<c:when test="${searchOrderStatus == '1'}">
							<option value="50">결제완료</option>
							<option value="1">배송준비중</option>
							<option value="99">주문취소</option>
						</c:when>
						<c:when test="${searchOrderStatus == '2' }">
							<option value="1">배송준비중</option>
							<option value="99">주문취소</option>
						</c:when>
						<c:when test="${searchOrderStatus == '3'}">
							<option value="2">배송중</option>
							<option value="99">주문취소</option>
						</c:when>
						<c:when test="${searchOrderStatus == '4'}">
							<option value="3">배송완료</option>
							<option value="31">교환처리중</option>
							<option value="41">반품처리중</option>
						</c:when>
						<c:when test="${searchOrderStatus == '5'}">
							<option value="31">교환처리중</option>
							<option value="41">반품처리중</option>
						</c:when>
						<c:when test="${searchOrderStatus == '9'}">
							<option value="41">반품처리중</option>

						</c:when>
						<c:when test="${searchOrderStatus == '6'}">
							<option value="31">교환처리중</option>
							<!-- option value="36">교환완료</option -->
						</c:when>
						<c:when test="${searchOrderStatus == '7'}">
							<!-- option value="36">교환완료</option -->
						</c:when>
					</c:choose>
				</select>
				<button type="button" id="change_order_status2" class="btn btn-dark-gray btn-sm">${op:message('M00433')}</button> <!-- 변경 -->
			</c:if>

			<button type="button" class="btn ctrl_btn order_print">${op:message('M00343')}</button> <!-- 주문서 인쇄 -->
			<button type="button" class="btn ctrl_btn order_mail">${op:message('M00803')}</button> <!-- 주문 메일 발송 -->

			<c:if test="${searchOrderStatus eq '3' }">
				<a href="javascript:downloadShippingTargetExecl()" class="btn_write gray_small"><img src="/content/opmanager/images/icon/icon_excel.png" alt=""><span>송장 미등록 주문 엑셀다운로드</span> </a> <!-- 엑셀 다운로드 -->
				<a href="javascript:uploadExcel('shipping')" class="btn_write gray_small"><img src="/content/opmanager/images/icon/icon_excel.png" alt=""><span>송장번호 엑셀업로드</span> </a>
				<a href="#" class="btn_write gray_small" onclick="downloadForDeliveryCompanyExecl()"><img src="/content/opmanager/images/icon/icon_excel.png" alt=""><span>배송업체 전달용 엑셀 다운로드</span> </a> <!-- 엑셀 다운로드 -->
			</c:if>
		</div>
	</div>
</form>


<!--// 전체주문 내역 끝-->
<c:if test="${searchOrderStatus eq '4' }">
	<div class="board_guide" style="border:1px solid #d5d5d5; padding: 15px; margin-top:15px;">

		<p class="tip">
			<a href="javascript:;" class="btn_write gray_small" onclick="downloadShippingExeclByOpenMarket('11st')"><img src="/content/opmanager/images/icon/icon_excel.png" alt=""><span>11st 엑셀 다운로드</span> </a> <!-- 엑셀 다운로드 -->
			<a href="javascript:;" class="btn_write gray_small" onclick="downloadShippingExeclByOpenMarket('gmarket')"><img src="/content/opmanager/images/icon/icon_excel.png" alt=""><span>지마켓&옥션 엑셀 다운로드</span> </a> <!-- 엑셀 다운로드 -->
			<a href="javascript:;" class="btn_write gray_small" onclick="downloadShippingExeclByOpenMarket('storefarm')"><img src="/content/opmanager/images/icon/icon_excel.png" alt=""><span>스토어팜 엑셀 다운로드</span> </a> <!-- 엑셀 다운로드 -->
			<a href="javascript:;" class="btn_write gray_small"onclick="downloadShippingExeclByOpenMarket('beautycall')"><img src="/content/opmanager/images/icon/icon_excel.png" alt=""><span>뷰티콜 엑셀 다운로드</span> </a> <!-- 엑셀 다운로드 -->
			<a href="javascript:;" class="btn_write gray_small" onclick="downloadShippingExeclByOpenMarket('allthat')"><img src="/content/opmanager/images/icon/icon_excel.png" alt=""><span>올댓 쇼핑 엑셀 다운로드</span> </a> <!-- 엑셀 다운로드 -->
			<a href="javascript:;" class="btn_write gray_small" onclick="downloadShippingExeclByOpenMarket('naver')"><img src="/content/opmanager/images/icon/icon_excel.png" alt=""><span>네이버 페이 엑셀 다운로드</span> </a> <!-- 엑셀 다운로드 -->
		</p>
		<p class="tip">
			<br/>선택한 항목을 다운로드 합니다.
			<br/>교환/부분 반품 처리되어 신규로 생성된 오픈마켓 주문의 경우 오픈마켓 송장 업로드용 엑셀을 받으실수 없습니다.
		</p>
	</div>
</c:if>

<c:if test="${searchOrderStatus == '9' || searchOrderStatus == '10'}">
	<div class="board_guide" style="border:1px solid #d5d5d5; padding: 15px; margin-top:15px;">
		<p class="tip">
			환불의 경우 환불 요청이 들어온 주문(기존주문)은 "전체 취소"됩니다.<br/>
			현재 구성하신 주문이 "신규 주문"으로 생성 되오니 환불 요청이 들어온 상품 이외에 환불 요청이 들어오지 않은 기존 상품들도 유지하신후 저장하셔야 합니다.<br/>
			생성된 "신규 주문"은 교환상품 발송 대기 상태로 "배송완료 목록"에서 확인하실수 있습니다.<br/><br/>
			취소/생성 되는 주문은 처리 시점을 기준으로 통계에 잡히게 됩니다.

			<br/><br/>
			[오픈마켓 주문] <br/>
			오픈 마켓 주문의 경우 주문 상품의 수정기능을 이용하실수 없으며, 전체 환불 완료 상태로 변경만 가능합니다.
		</p>
	</div>
</c:if>

<c:if test="${searchOrderStatus == '6' || searchOrderStatus == '7'}">
	<div class="board_guide" style="border:1px solid #d5d5d5; padding: 15px; margin-top:15px;">
		<p class="tip">
			교환의 경우 교환 요청이 들어온 주문(기존주문)은 "전체 취소"됩니다.<br/>
			현재 구성하신 주문이 "신규 주문"으로 생성 되오니 교환 요청이 들어온 상품 이외에 교환 요청이 들어오지 않은 기존 상품들도 유지하신후 저장하셔야 합니다.<br/>
			생성된 "신규 주문"은 교환상품 발송 대기 상태로 "배송준비중 목록"에서 확인하실수 있습니다.<br/><br/>
			취소/생성 되는 주문은 처리 시점을 기준으로 통계에 잡히게 됩니다.

			<br/><br/>
			[오픈마켓 주문] <br/>
			오픈 마켓 주문의 경우 주문 상품의 수정기능을 이용하실수 없으며, 전체 교환 완료 상태로 변경만 가능합니다.
		</p>
	</div>
</c:if>

<page:pagination-manager />

<script type="text/javascript">

function openMarketTip() {
	if ($('#openmarket_tip').css('display') == 'none') {
		$('#openmarket_tip').show();
	} else {
		$('#openmarket_tip').hide();
	}

	setHeight();
}

$(function() {
	showOrderStatusSelector();
	Common.DateButtonEvent.set('.day_btns > a[class^=btn_date]', '', 'input[name="searchStartDate"]' , 'input[name="searchEndDate"]');


	$('#orderStatus1, #orderStatus2').on('change', function(){
		$('#orderStatus1').val($(this).val());
		$('#orderStatus2').val($(this).val());
	});

	$('.order_print').on('click', function(){
		$id = $('input[name="id"]:checked');
		if ($id.size() == 0) {
			alert(Message.get("M00308"));	// 처리할 항목을 선택해 주세요.
			return;
		}

		var popup = Common.popup("/opmanager/order/order-print?" + $id.serialize(), 'print-view-list', 672, 900, 1);
		return;
	});

	$('.order_mail').on('click', function(){
		$id = $('input[name="id"]:checked');
		if ($id.size() == 0) {
			alert(Message.get("M00308"));	// 처리할 항목을 선택해 주세요.
			return;
		}

		var popup = Common.popup("/opmanager/order/order-mail?" + $id.serialize(), 'mail-send-list', 1000, 600, 1);
		return;
	});

	$('#change_order_status1, #change_order_status2').on('click', function(){
		$id = $('input[name="id"]:checked');
		if ($id.size() == 0) {
			alert(Message.get("M00308"));	// 처리할 항목을 선택해 주세요.
			return;
		}

		$baseOrderStatus = $('select.baseGroupOrderStatus');
		if ($baseOrderStatus.size() > 0) {
			$.each($baseOrderStatus, function(){
				var orderId = $(this).attr('id').split("-")[1];
				var groupIndex = $(this).attr('id').split("-")[2];
				var groupOrgOrderStatusClass = "groupOrgOrderStatus-" + orderId + "-" + groupIndex;
				var orderStatus = $(this).val();

				$.each($('.' + groupOrgOrderStatusClass), function() {
					var orderItemId = $(this).attr('id').split("-")[2];
					var orderStatusInputId = "orderStatus-" + orderId + "-" + orderItemId;
					var orgOrderStatus = $(this).val();
					var changeOrderStatus = orderStatus;

					if (orderStatus == "0") {
						if (orgOrderStatus == '42' || orgOrderStatus == '32') {
							changeOrderStatus = orgOrderStatus;
						}
					} else if (orderStatus == '50' || orderStatus == '1') {
						if (orgOrderStatus == '42') {
							changeOrderStatus = "46";
						} else if (orgOrderStatus == '32') {
							changeOrderStatus = "33";
						}
					} else if (orderStatus == '2') {
						if (orgOrderStatus == '33') {
							changeOrderStatus = "34";
						}
					}

					$('#' + orderStatusInputId).val(changeOrderStatus);
				});
			});
		} else {
			// 전체 주문서 수정 Select가 없는경우는 개별로 Select가 있는경우
			$orderStatus = $('#orderStatus1');
			if ($orderStatus.size() > 0) {

				var orderStatus = $orderStatus.val();
				if (orderStatus == '50' || orderStatus == '1') {
					$.each($('input.changeOrderStatus'), function(){
						var orderId = $(this).attr('id').split("-")[1];
						var orderItemId = $(this).attr('id').split("-")[2];

						var orgOrderStatus = $('#orgOrderStatus-' + orderId + "-" + orderItemId).val();
						if (orgOrderStatus == '42') {
							$(this).val("46");
						} else if (orgOrderStatus == '32') {
							$(this).val("33");
						} else {
							$(this).val(orderStatus);
						}
					});
				} else if (orderStatus == '2') {
					$.each($('input.changeOrderStatus'), function(){
						var orderId = $(this).attr('id').split("-")[1];
						var orderItemId = $(this).attr('id').split("-")[2];

						var orgOrderStatus = $('#orgOrderStatus-' + orderId + "-" + orderItemId).val();
						if (orgOrderStatus == '33') {
							$(this).val("34");
						} else {
							$(this).val(orderStatus);
						}
					});
				} else {
					$('input.changeOrderStatus').val($orderStatus.val());
				}
			}
		}

		$baseDeliveryCompanyId = $('select.baseGroupDeliveryCompanyId');
		if ($baseDeliveryCompanyId.size() > 0) {
			$.each($baseDeliveryCompanyId, function(){
				$('.' + $(this).attr('id')).val($(this).val());
			});

			$baseDeliveryNumber = $('input.baseGroupDeliveryNumber');
			$.each($baseDeliveryNumber, function(){
				$('.' + $(this).attr('id')).val($(this).val());
			});
		}

		if ($('#orderStatus1').val() == '99') {
			if (!confirm("선택하신 주문을 취소 하시겠습니까?")) {
				return;
			}

			$('input[name="mode"]', $("#listForm")).val('cancel');
		} else {

			var orderStatus = $('#orderStatus1').val();
			var orderStatusLabel = $('#orderStatus1 option:selected').text();

			if (orderStatus=="1"){
				if (!confirm("배송준비중목록으로 넘어가면 당일매출로 확정됩니다. \n변경하시겠습니까?")) {
					return;
				}
			} else {
				if (!confirm("선택하신 주문을 '"+orderStatusLabel+"' 으로 변경하시겠습니까?")) {
					return;
				}
			}

			$('input[name="mode"]', $("#listForm")).val('');
		}

		postChangeOrderStatusList($("#listForm").serialize());
	});

	setOrderLnbCount();
});

// 주문 상태 변경 전송
function postChangeOrderStatusList(param) {
	$.post('/opmanager/order/change-order-status', param, function(response){
		Common.responseHandler(response, function(response) {

			location.reload();

		}, function(response){

			alert(response.errorMessage);

		});
	});
}

// 단일 주문 상태 변경
function changeOrderStatus(orderId, status, confirmMessage) {
	if (confirmMessage != undefined) {
		if (!confirm(confirmMessage)) {
			return;
		}
	}

	$('input.changeOrderStatus-' + orderId).val(status);
	$('input[name="id"][value="'+orderId+'"]').prop('checked', true);
	postChangeOrderStatusList($("#listForm").serialize());
}

function setOrderLnbCount() {

	$.post('/opmanager/order/orderLnbCount', null, function(resp){

		if (resp.info == undefined) {
			return;
		}

		$.each(resp.info, function(key, state){
			$object = $('ul.lnbs a[href="/opmanager/order/list/' + state.key + '"]');
			if ($object.size() > 0) {
				var html = $object.html();
				$object.html(html + ' (' + state.count + ')');
			}
		});
	}, 'json');

}

function uploadExcel(mode) {
	Common.popup('/opmanager/order/'+ mode +'/upload-excel', mode + '-upload-excel', 600, 550, 1);
}

function setYesterDayTimeToCurrentTime(yesterDayTime){

	var now = new Date();
	var hour = now.getHours();

	if (hour < 10) {
		hour = '0'+hour;
	}

	$('select#conditionType').val('PAY_DATE');
	$('select[name = searchStartDateTime]').val(yesterDayTime);
	$('select[name = searchEndDateTime]').val(hour);
}

function downloadErpExcel(){

	/*
	$id = $('input[name="id"]:checked');
	if ($id.size() == 0) {
		alert(Message.get("M00308"));	// 처리할 항목을 선택해 주세요.
		return;
	}
	*/

	location.href = "/opmanager/order/erp-excel-download?searchStartDate=" + $('#erpDownDate').val();
}

function downloadErpExcel2(){
	/*
	$id = $('input[name="id"]:checked');
	if ($id.size() == 0) {
		alert(Message.get("M00308"));	// 처리할 항목을 선택해 주세요.
		return;
	}
	*/
	location.href = "/opmanager/order/erp-excel-download2?searchStartDate=" + $('#erpDownDate').val();
}

function downloadShippingTargetExecl(){
	$id = $('input[name="id"]:checked');
	if ($id.size() == 0) {
		alert(Message.get("M00308"));	// 처리할 항목을 선택해 주세요.
		return;
	}

	location.href = "/opmanager/order/shipping-target/download-excel?"+$id.serialize();
}

function orderTracking(url) {
	Common.popup(url, 'order-tracking', 700, 700, 1);
}

function downloadBrandExcel(brandName) {
	$id = $('input[name="id"]:checked');
	var param = "?brand="+brandName;
	if ($id.size() > 0) {
		param += "&" + $id.serialize();
	}

	location.href = '/opmanager/order/brand/download-excel' + param;
}

function downloadOrderExcel(searchOrderStatus, totalCount) {
	$id = $('input[name="id"]:checked');
	var param = "";
	if ($id.size() > 0) {
		param = "?" + $id.serialize();
	}

	if (searchOrderStatus == '') {

		if ($id.size() == 0) {
			alert('다운받으실 주문을 선택해 주세요.');
			return;
		}
		searchOrderStatus = 'all';
	} else {
		if (totalCount >= 1000) {
			if ($id.size() == 0) {
				alert('주문 내역이 1,000건이 넘는경우 선택 다운로드 혹은 일자별 검색후 다운로드 하시기 바랍니다.');
				return;
			}
		}
	}

	location.href = '/opmanager/order/excel-download/'+ searchOrderStatus + param;
}

function downloadShippingExeclByOpenMarket(market){

	$id = $('input[name="id"]:checked');
	if ($id.size() == 0) {
		alert(Message.get("M00308"));	// 처리할 항목을 선택해 주세요.
		return;
	}

	location.href = "/opmanager/order/shipping/"+market+"/download-excel?"+$id.serialize();

}

function downloadForDeliveryCompanyExecl(){
	$id = $('input[name="id"]:checked');
	if ($id.size() == 0) {
		alert(Message.get("M00308"));	// 처리할 항목을 선택해 주세요.
		return;
	}

	location.href = "3/download-excel?"+$id.serialize();
}

function showOrderStatusSelector(){
	var showSelectorOrderStatus = ['1','2','3','4','5','9','6'];
	var searchOrderStatus = '${searchOrderStatus}';

	var orderSelector1 = $('#orderStatus1');
	var orderSelector2 = $('#orderStatus2');
	var changeOrderStatusBtn1 = $('#change_order_status1');
	var changeOrderStatusBtn2 = $('#change_order_status2');


	if (searchOrderStatus == '' || searchOrderStatus == null) {
		return;
	}

	var showFlag = true;

	for (var i=0; i<showSelectorOrderStatus.length; i++) {
		if (searchOrderStatus != showSelectorOrderStatus[i]) {
			showFlag = false;
		} else {
			showFlag = true;
			break;
		}
	}

	if (showFlag) {
		orderSelector1.show();
		orderSelector2.show();
		changeOrderStatusBtn1.show();
		changeOrderStatusBtn2.show();
	} else {
		orderSelector1.hide();
		orderSelector2.hide();
		changeOrderStatusBtn1.hide();
		changeOrderStatusBtn2.hide();
	}
}

function cjDeliveryNumber(data) {
	try {
		if (data == undefined) {
			alert('처리가능한 데이터가 없습니다.');
		} else {

			$.each(data, function(key, value){
				var orderId = key.split("-")[1];
				var deliveryNumberKey = "groupDeliveryNumber-"+orderId;
				var deliveryCompanyKey = "groupDeliveryCompanyId-"+orderId;

				$('#' + deliveryNumberKey).val(value);
				$('.' + deliveryNumberKey).val(value);

				$('#' + deliveryCompanyKey).val('1');
				$('.' + deliveryCompanyKey).val('1');

				$('input[type="checkbox"][value="'+orderId+'"]').prop('checked', true);
			});

		}
	} catch(e) {
		alert(e.message)
	}
}

</script>