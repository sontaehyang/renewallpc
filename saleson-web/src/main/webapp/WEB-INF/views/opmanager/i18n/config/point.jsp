<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>


		<div class="location">
			<a href="#">${op:message('M00282')}</a><!-- 환경설정 --> &gt;  <a href="#">${op:message('M00423')}</a> <!-- 운영설정 -->  &gt; <a href="#" class="on">${op:message('M00424')}</a> <!-- 포인트설정 -->
		</div>


		<h3><span>기본 ${op:message('M00246')}</span></h3>
		<form:form modelAttribute="config" action="/opmanager/config/point" method="post" enctype="multipart/form-data">
			<div class="board_write">
				<table class="board_write_table" summary="${op:message('M00527')}">
					<caption>${op:message('M00527')}</caption>
					<colgroup>
						<col style="width:150px;">
						<col style="">
						<col style="width:150px;">
						<col style="">
					</colgroup>
					<tbody>
						 <tr>
						 	<td class="label">${op:message('M00426')}</td><!-- 기본포인트 -->
						 	<td colspan="3">
						 		<div>
						 			<p class="text-info text-sm">
						 				* % 선택시 소수점 반영이 됩니다.<br />
						 				* ${op:message('M00500')}	<%-- 기간별 포인트가 설정되어 있는 경우 기간별 포인트가 우선 적용 됩니다. --%>
						 			</p>

								    <input type="text" id="pointDefault" name="pointDefault" title="${op:message('M00426')}" class="form-sm text-center _number required" maxlength="${pointDefaultType == 1 ? 3 : 5}"
								           value="${config.pointDefaultText}"/>
								    <%--
						 			<form:input type="text" path="pointDefault" title="${op:message('M00426')}" class="form-sm text-center _number required" maxlength="${pointDefaultType == 1 ? 3 : 5}" />
						 			--%>
						 			&nbsp;
									( <form:radiobutton path="pointDefaultType" name="pointDefaultType" id="check_use" class="lengthChange" value="1" checked="checked" label="%" />
									  <form:radiobutton path="pointDefaultType" name="pointDefaultType" id="won" class="lengthChange" value="2" label="P" />
									)
									${op:message('M00499')} <!-- 를 상품 전체에 적용 -->

								</div>
						 	</td>
						 </tr>
						 <%-- <tr>
						 	<td class="label">${op:message('M00797')} <!-- 특정일 --> ${op:message('M00798')} <!-- 적용시간 --></td><!-- 기본포인트 -->
						 	<td colspan="3">
						 		<div>
						 			${op:message('M00797')} <!-- 특정일 -->
						 			<form:select path="repeatDayStartTime" title="${op:message('M00508')}"> <!-- 시간 선택 -->
										<c:forEach items="${hours}" var="code">
											<form:option value="${code.value}" label="${code.label}" />
										</c:forEach>
									</form:select>시 ~ ${op:message('M00799')} <!-- 다음날 --> <!-- 시 -->

									<form:select path="repeatDayEndTime" title="${op:message('M00508')}">
										<c:forEach items="${hours}" var="code">
											<form:option value="${code.value}" label="${code.label}" />
										</c:forEach>
									</form:select>시 59분
								</div>
						 	</td>
						 </tr> --%>
						 <tr>
						 	<td class="label">${op:message('M00513')}</td> <!-- 회원가입시 포인트 -->
						 	<td colspan="3">
						 		<div>
									<form:input type="text" path="pointJoin" class="form-amount text-right  _number required" title="${op:message('M00513')}" maxlength="5" /> P
								</div>
						 	</td>
						 </tr>

						 <tr>
							 <td class="label">${op:message('M00514')}</td> <!-- 리뷰 채택시 포인트 -->
							 <td>
								 <div>
									 일반 리뷰 <form:input type="text" path="pointReview" class="form-amount text-right _number required" title="${op:message('M00514')}" maxlength="5" /> P <br/>
									 포토 리뷰 <form:input type="text" path="photoPointReview" class="form-amount text-right _number required" title="${op:message('M00514')}" maxlength="5" /> P
								 </div>
							 </td>
							 <td class="label">리뷰 노출 설정</td>
							 <td>
								 <div>
									 <form:radiobutton path="reviewDisplayType" value="1" checked="checked" label="즉시 노출" />
									 <form:radiobutton path="reviewDisplayType" value="2" label="관리자 승인 후 노출" />
								 </div>
							 </td>
						 </tr>

						 <%-- CJH 2016.10.20 미구현 기능..
						 <tr>
						 	<td class="label">첫 구매시 ${op:message('M00246')}</td>
						 	<td>
						 		<div>
									<form:input type="text" path="pointFirstBuy" class="form-amount text-right _number required" title="첫 구매시 ${op:message('M00246')}" maxlength="5" /> P
								</div>
						 	</td>
						 	<td class="label">방문시 ${op:message('M00246')}(일별)</td>
						 	<td>
						 		<div>
									<form:input type="text" path="pointVisitDay" class="form-amount text-right _number required" title="방문시 ${op:message('M00246')}(일별)" maxlength="5" /> P
								</div>
						 	</td>
						 </tr>

						 <tr>
						 	<td class="label">${op:message('M00518')}</td> <!-- 포인트 지급시기 -->
						 	<td colspan="3">
						 		<div>
						 			<p class="text-info text-sm">
						  				* ${op:message('M00246')} 지급 시기를 설정합니다.<br />
						  				* 기간 입력 값이 없는 경우 설정된 지급 시기 시점에 즉시 지급됩니다.
						  			</p>
						 			<span>
										<form:radiobutton path="pointSaveTime" name="pointSaveTime" id="complete01" value="1" checked="checked" label="${op:message('M00519')}" /> <!-- 결재완료시 -->
								  		<form:radiobutton path="pointSaveTime" name="pointSaveTime" id="complete02" value="2" label="${op:message('M00520')}" /> <!-- 배송완료시 -->
							  		</span>

							  		<div class="form-group">

							  			<input type="hidden" name="pointSaveTime" value="2" />
										<input type="text" name="pointSaveAfterDay" id="pointSaveAfterDay" class="form-sm optional _number" maxlength="3" value="${op:negativeNumberToEmpty(config.pointSaveAfterDay)}" />
										일 후 ${op:message('M00246')}를 지급합니다.
									</div>
								</div>
						 	</td>
						 </tr>
						 --%>

						 <tr>
						 	<td class="label">${op:message('M00522')}</td> <!-- 포인트 지급제한 -->
						 	<td colspan="3">
						 		<div>
						 			<p class="text-info text-sm">
						 				* 상황별 ${op:message('M00246')} 지급 제한 설정이 가능합니다.<br />
						 				* 일반적으로 ${op:message('M00246')}를 사용하여 결제시 ${op:message('M00246')}는 지급이 안되며, 이용 약관에 표기하는 것이 좋습니다.
						 			</p>
						 			 <ul>
						 			 	<li><form:radiobutton path="pointSaveType" value="1" label="${op:message('M00800')}" /></li>  <!-- 조건없음 -->
						 			 	<li><form:radiobutton path="pointSaveType" value="2" label="${op:message('M00525')}" /></li> <!-- 결제시 포인트 사용하면 포인트을 지급하지 않음 -->
						 			 </ul>
								</div>
						 	</td>
						 </tr>
						 <tr>
						 	<td class="label">${op:message('M00527')}</td>
						 	<td colspan="3">
						 		<div>
						 			<p class="text-info text-sm">
						 				* 주문시 ${op:message('M00246')} 사용 금액을 제한할 수 있습니다.<br />
						 				* ${op:message('M00530')} <!-- 입력값이 없을경우 무제한으로 설정됩니다. -->
						 			</p>
						 			${op:message('M00528')} <!-- 최소 --> <input type="text" name="pointUseMin" id="pointUseMin" value="${op:negativeNumberToEmpty(config.pointUseMin)}" class="form-amount text-right optional _number" maxlength="9" /> P <br />
						 			${op:message('M00529')} <!-- 최대 -->  <input type="text" name="pointUseMax" id="pointUseMax" value="${op:negativeNumberToEmpty(config.pointUseMax)}" class="form-amount text-right optional _number" maxlength="9" /> P
								</div>
						 	</td>
						 </tr>
						 <tr>
							 <td class="label">${op:message('M00246')} 사용 비율 설정</td>
							 <td colspan="3">
								 <div>
									 <input type="text" name="pointUseRatio" id="pointUseRatio" class="form-sm optional _number" maxlength="3"
											value="${op:negativeNumberToEmpty(config.pointUseRatio)}" /> %
								 </div>
							 </td>
						 </tr>
						 <tr>
						 	<td class="label">${op:message('M00246')} 만료일 설정</td>
						 	<td colspan="3">
						 		<div>
						 			<p class="text-info text-sm">
											${op:message('M00246')} 만료일은 개월단위로 설정하시기 바랍니다.<br />
						 				* ${op:message('M00530')} <!-- 입력값이 없을경우 무제한으로 설정됩니다. -->
						 			</p>
						 			적립후 <input type="text" name="pointExpirationMonth" id="pointExpirationMonth" class="form-sm optional _number" maxlength="5" value="${op:negativeNumberToEmpty(config.pointExpirationMonth)}" /> 개월후에 만료
								</div>
						 	</td>
						 </tr>
						 <tr>
						 	<td class="label">배송쿠폰 만료일 설정</td>
						 	<td colspan="3">
						 		<div>
						 			<p class="text-info text-sm">
						 				배송쿠폰 만료일은 개월단위로 설정하시기 바랍니다.<br />
						 				* ${op:message('M00530')} <!-- 입력값이 없을경우 무제한으로 설정됩니다. -->
						 			</p>
						 			적립후 <input type="text" name="shippingCouponExpirationMonth" id="shippingCouponExpirationMonth" class="form-sm optional _number" maxlength="5" value="${op:negativeNumberToEmpty(config.shippingCouponExpirationMonth)}" /> 개월후에 만료
								</div>
						 	</td>
						 </tr>
					</tbody>
				</table>
			</div> <!--//board_write E-->

			<h3 class="title">
				<span>${op:message('M01075')}</span> <!-- 기간별 포인트 설정 -->
				<button type="button" class="btn btn-gradient btn-sm add_item_point" onclick="addItemPoint()"><span>${op:message('M00501')}</span></button> <!-- 기간별 포인트 추가 -->
			</h3>

			<p class="text-info">
				<%-- * ${op:message('M00502')} --%> <!-- 매월 포인트가 설정되어 있는 경우 매월 해당일 적립 금액이 최우선 적용 됩니다. -->
				* 기간별 ${op:message('M00246')}를 설정하시면 설정한 기간 동안에는 기본 ${op:message('M00246')}를 지급하지 않고 설정된 ${op:message('M00246')}를 지급합니다.<br/>
				* 단, 상품등록시 '할인/${op:message('M00246')} 설정 항목'에 '${op:message('M00246')}지급'설정이 사용함으로 설정된 경우에는 기간별 ${op:message('M00246')}가 적용되지 않습니다.
			</p>
			<div class="board_write">
				<table id="item_point_config" class="board_list_table" summary="${op:message('M00503')}"> <!-- 포인트 추가 -->
					<caption>${op:message('M00504')}</caption> <!-- 포인트 추가 리스트 -->
					<colgroup>
						<col style="width: 250px;">
						<col style="width: auto">
						<col style="width: 100px;">
						<col style="width: 70px;">
					</colgroup>
					<thead>
						<tr>
							<th>${op:message('M00246')}</th> <!-- 포인트 -->
							<th>${op:message('M00505')}</th> <!-- 포인트 적용 기간 -->
							<th>${op:message('M00506')}</th> <!-- 특정일 적용 -->
							<th>${op:message('M00074')}</th> <!-- 삭제 -->
						</tr>
					</thead>
					<tbody id="item_point_area">
						<c:forEach items="${pointConfig}" var="pointConfig" varStatus="i">
							<tr>
								<td>
									<input type="text" name="point" id="point_${i.count}" value="${pointConfig.pointText}" maxlength="${pointConfig.pointType == 1 ? 3 : 5}" class="three required _number" title="${op:message('M00246')}" />
									<input type="hidden" name="pointType" value="${pointConfig.pointType}" />

									<input type="radio" name="R${pointConfig.pointConfigId}" id="R${pointConfig.pointConfigId}1" ${op:checked('1', pointConfig.pointType)} class="lengthChange" value="1"> <label for="R${pointConfig.pointConfigId}1">%</label>
									<input type="radio" name="R${pointConfig.pointConfigId}" id="R${pointConfig.pointConfigId}2" ${op:checked('2', pointConfig.pointType)} class="lengthChange" value="2"> <label for="R${pointConfig.pointConfigId}2">P</label>
								</td>
								<td>
									<span class="datepicker"><input type="text" name="startDate" class="term required _date" maxlength="8" value="${pointConfig.startDate}" title="${op:message('M00507')}" /></span> <!-- 시작일 -->
									<select name="startTime" title="${op:message('M00508')}"> <!-- 시간 선택 -->
										<c:forEach items="${hours}" var="code">
											<option value="${code.value}" ${op:selected(code.value, pointConfig.startTime)}>${code.label}</option>
										</c:forEach>
									</select>시 ~  <!-- 시 -->
									<span class="datepicker"><input type="text" name="endDate" class="term required _date" maxlength="8" value="${pointConfig.endDate}" title="${op:message('M00509')}" /></span> <!-- 종료일 -->
									<select name="endTime" title="${op:message('M00508')}">
										<c:forEach items="${hours}" var="code">
											<option value="${code.value}" ${op:selected(code.value, pointConfig.endTime)}>${code.label}</option>
										</c:forEach>
									</select>시 59분
								</td>
								<td>
									<select name="pointRepeatDay">
										<option value="">${op:message('M00039')}</option> <!-- 전체 -->
										<c:forEach begin="1" end="31" step="1" var="i">
											<option value="${ i }"
												<c:if test="${pointConfig.repeatDay eq i}">selected="selected"</c:if>>${ i }</option>
										</c:forEach>
									</select> ${op:message('M00511')} <!-- 일 -->
								</td>

								<td><a href="#" class="btn btn-gradient btn-xs delete_item_point">${op:message('M00074')}</a></td>
							</tr>
						</c:forEach>
						<c:if test="${empty pointConfig}">
							<tr class="noneDate">
								<td colspan="4">기간별 ${op:message('M00246')} 설정이 없습니다.</td>
							</tr>
						</c:if>
					</tbody>
				</table>
			</div> <!--//board_write E-->

			<!-- <h3 class="title"><span>${op:message('M00515')}</span></h3> <!-- 추천인 ID를 입력한 회원이 특정금액 이상 상품 구매시 포인트 설정 -->
			<!--<div class="board_write">
				<table class="board_write_table" summary="${op:message('M00512')}">
					<caption>${op:message('M00512')}</caption>
					<colgroup>
						<col style="width:150px;">
						<col style="width:auto;">
						<col style="width:150px;">
						<col style="width:auto;">
					</colgroup>
					<tbody>
						 <tr>
						 	<td class="label">${op:message('M00227')}</td>
						 	<td>
						 		<div>
									<form:input type="text" path="pointRecommendPrice" class="optional _number" maxlength="10" value="0" />${op:message('M00516')}
								</div>
						 	</td>
						 	<td class="label">${op:message('M00246')}</td>
						 	<td>
						 		<div>
									<form:input type="text" path="pointRecommend" class="optional _number" maxlength="10" value="0" /> P
								</div>
						 	</td>
						 </tr>
					</tbody>
				</table>
			</div><!--//board_write E-->

			<%-- <h3 class="title"><span>${op:message('M00531')}</span></h3>
			<div class="board_write">
				<table class="board_write_table" summary="${op:message('M00531')}"> <!-- 소비세 설정 -->
					<caption>${op:message('M00531')}</caption>
					<colgroup>
						<col style="width:150px;">
						<col style="width:*">
					</colgroup>
					<tbody>
						 <tr>
						 	<td class="label">${op:message('M00531')}</td>
						 	<td>
						 		<div>
						 			<form:input type="text" path="tax" class="required optional _number" maxlength="3" /> %
						 			<input type="hidden" name="taxType" value="1" />
						 			<!-- form:radiobutton path="taxType" name="taxType" id="rounding1" value="1" label="${op:message('M00532')}" checked="checked" / --> <!-- 반올림 -->
								  	<!-- form:radiobutton path="taxType" name="taxType" id="rounding2" value="2" label="${op:message('M00533')}" / --> <!-- 소수점표시 -->
						 			( ${op:message('M00656')} ) <!-- 소수점 이하는 반올림 됩니다. -->
								</div>
						 	</td>
						 <tr>
						 	<td class="label">${op:message('M00657')}</td> <!-- 세금표시방법 -->
						 	<td>
						 		<div>
						 			<form:radiobutton path="taxDisplayType" value="2" label=" ${op:message('M00421')} " /> <!-- 세금별도 -->
								  	<form:radiobutton path="taxDisplayType" value="1" label=" ${op:message('M00608')}" /> <!-- 세금포함 --> <br/>
								</div>
						 	</td>
						 </tr>
					</tbody>
				</table>
			</div> <!--//board_write E--> --%>



			<div class="btn_center">
				<button type="submit" class="btn btn-active"><span>${op:message('M00101')}</span></button>  <!-- 저장 -->
			</div>

		</form:form>

		<div class="board_guide">
				<br/>
				<p class="tip">[안내]</p>
				<p class="tip">
					${op:message('M00246')} 적용 우선 순서는 아래의 내용으로 적용됩니다.<br/>
					상품정보 > 상품 등록 : 할인/${op:message('M00246')} 설정 - ${op:message('M00246')} 지급<br/>
					환경설정 > ${op:message('M00246')} 설정 : 기간별 ${op:message('M00246')} 설정<br/>
					<!-- 환경설정 > ${op:message('M00246')} 설정 : 기간별 ${op:message('M00246')} 설정 - 특정일 적용<br/>
					환경설정 > ${op:message('M00246')} 설정 : 기간별 ${op:message('M00246')} 설정 - ${op:message('M00246')} 적용기간<br/> -->
					환경설정 > ${op:message('M00246')} 설정 : ${op:message('M00246')} 설정 - 기본${op:message('M00246')}<br/>
					* 기폰 ${op:message('M00246')} 설정시 ${op:message('M00246')} 적용기간이 이 중복 되지않게 등록해주세요.
				</p>
			</div>

<table id="item_point_template" style="display:none">
	<tbody>
		<tr>
			<td>
				{INPUT_RADIO}
			</td>
			<td>
				<span class="datepicker"><input type="text" name="startDate" class="term required _date" maxlength="8" title="${op:message('M00507')}" /></span>
				<select name="startTime" title="${op:message('M00508')}">
					<c:forEach items="${hours}" var="code">
						<option value="${code.value}">${code.label}</option>
					</c:forEach>
				</select>시 ~
				<span class="datepicker"><input type="text" name="endDate" class="term required _date" maxlength="8" title="${op:message('M00509')}" /></span>
				<select name="endTime" title="${op:message('M00508')}">
					<c:forEach items="${hours}" var="code">
						<option value="${code.value}">${code.label}</option>
					</c:forEach>
				</select>시 59분
			</td>
			<td>
				<select name="pointRepeatDay">
					<option value="">${op:message('M00039')}</option>
					<c:forEach begin="1" end="31" step="1" var="i">
						<option value="${ i }">${ i }</option>
					</c:forEach>
				</select> ${op:message('M00511')} <!-- 일 -->
			</td>
			<td><a href="#" class="btn btn-gradient btn-xs delete_item_point">${op:message('M00074')}</a></td>
		</tr>
	</tbody>
</table>


<script type="text/javascript">

//포인트 설정 삭제
$('#item_point_area').on('click', '.delete_item_point', function(e){
	// 해당 적용날짜 및 시간을 삭제하시겠습니까?
	if(confirm(Message.get("M00536"))) {
		e.preventDefault();
		$(this).closest("tr").remove();
	} else {
		return;
	}
});


function addItemPoint() {

	$(".noneDate").hide();

	var optionTemplate = $('#item_point_template tbody').html();
	var randomKey = Math.floor(Math.random() * 10000) + 1;

	var radioButtons = '';
	radioButtons += '<input type="text" name="point" id="point_' + randomKey + '" maxlength="3" class="three required _number_float" title="${op:message('M00246')}" />';
	radioButtons +=	'<input type="hidden" name="pointType" />';
	radioButtons += '<input type="radio" name="R' + randomKey + '" id="R' + randomKey + '1" class="lengthChange" checked="checked" value="1"> <label for="R' + randomKey + '1">%</label>';
	radioButtons += '<input type="radio" name="R' + randomKey + '" id="R' + randomKey + '2" class="lengthChange" value="2"> <label for="R' + randomKey + '2">P</label>';

	$('#item_point_area').append(optionTemplate.replace('{INPUT_RADIO}', radioButtons));
	$('#item_point_area').find('.ui-datepicker-trigger').remove();
	$('#item_point_area').find("input.term").removeClass('hasDatepicker').datepicker();

	setHeight();
}


$(function() {

	EventHandler.checkOnlyNumberFloat();

	initNumberClass();

	setHeight();

	$('input[name="pointDefaultType"]').on('click', function(){
		$('input[name="pointDefault"]').val('0');
		setNumberClass($('input[name="pointDefault"]'), $(this).val());
	});

	// item_point_config
	$('#item_point_area').on('click', ':radio' ,function() {
		var objValue = $(this).siblings('input[name=point]');
		objValue.val('0');
		setNumberClass(objValue, $(this).val());
	});

	// datepicer
	var $item = $('#item_point_area');
	$item.find('.ui-datepicker-trigger').remove();
	$item.find("input.term").each(function() {
		$(this).removeClass('hasDatepicker').datepicker();
	});

	// validator
	try{
		$('#config').validator(function() {
			var valiCheck = 0;
			// 포인트 처리
			$('#item_point_area tr').each(function(){
				var pointType = $(this).find('input[type=radio]').eq(0).prop('checked') == true ? "1" : "2";
				$(this).find('input[name=pointType]').val(pointType);


				if ($(this).find('input[name=startDate]').val() > $(this).find('input[name=endDate]').val()) {
					alert(Message.get("M01289"));	// 포인트 적용기간 시작일과 종료일이 알맞지 않습니다.
					$(this).find('input[name=startDate]').focus();
					valiCheck++;
					return false;
				}
			});

			if (valiCheck > 0) {
				return false;
			}

			if ($('#pointUseMax').val() != "" && $('#pointUseMin').val() > $('#pointUseMax').val()) {
				alert(Message.get("M01288"));	// 포인트 최소사용 금액과 최대사용 금액이 알맞지 않습니다.
				$('#pointUseMin').focus();
				return false;
			}

		});
	} catch(e) {
		alert(e.message);
	}

	$('.lengthChange').on('change', '.lengthChange', function() {
		var length = $(this).val() == 1 ? 3 : 5;
		var target = $(this).parent().find('input[type=text]');

		target.val("");
		document.getElementById(target.attr('id')).maxLength = length;
	});
});

function deleteCheck() {
	// 배너를 삭제하시겠습니까?
	if(confirm(Message.get("M00537"))) {
		location.replace("/opmanager/config/top-banner/delete");
	} else {
		return;
	}
}

function setNumberClass(obj, pointType) {

	if (obj.length > 0) {
		obj.removeClass('_number');
		obj.removeClass('_number_float');

		if ('1' == pointType) {
			obj.addClass('_number_float');
		} else {
			obj.addClass('_number');
		}
	}
}

function initNumberClass() {
	setNumberClass($('input[name="pointDefault"]'), $('input[name="pointDefaultType"]').val());

	$('#item_point_area').find('input[name=point]').each(function(idx){

		var objRadio = $(this).siblings(':radio:checked');
		setNumberClass($(this), objRadio.val());

	});
}

</script>