<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

	<div class="location">
		<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
	</div>
	
	<!-- 본문 -->
	<div class="item_list">		
		<h3>쿠폰등록</h3> <!-- 쿠폰신규등록 -->
		<form:form modelAttribute="coupon" method="post">
			<form:hidden path="couponId" />
			<div class="board_write">				
				<table class="board_write_table" summary="${op:message('M01155')}">
					<caption>${op:message('M01155')}</caption>
					<colgroup>
						<col style="width: 160px;" />
						<col style="width: auto;" /> 
					</colgroup>
					<tbody>
						<tr>
							<td class="label">쇼핑채널 *</td> 
							<td>
								<div>
									<form:radiobutton path="couponType" label="공통" value="1" checked="checked" />
									<form:radiobutton path="couponType" label="PC" value="2" />
									<form:radiobutton path="couponType" label="모바일" value="3" />
								</div>
							</td>
						</tr>
						<tr>
							<td class="label">${op:message('M00878')}</td> <!-- 쿠폰종류 --> 
							<td>
								<div>
									<form:radiobutton path="couponKind" label="일반 상품쿠폰" value="1" checked="checked" onClick="javascript:hideConcurrency(1)"/> <!-- 상품쿠폰 -->
								</div>
							</td>
						</tr>
						<tr>
							<td class="label">고객 쿠폰명 *</td> <!-- 쿠폰명 --> 
							<td>
								<div>
									<form:input path="couponName" class="required half" title="${op:message('M00879')}" />
								</div>
							</td>
						</tr>
						<tr>
							<td class="label">${op:message('M01156')}</td> <!-- 쿠폰설명 --> 
							<td>
								<div>
									<form:input path="couponComment" class="nine" title="${op:message('M01156')}" />
								</div>
							</td>
						</tr>
						
						<tr>
							<td class="label">${op:message('M01148')}</td> <!-- 쿠폰 발급기간 --> 
							<td>
								<div>
									<p>
										<form:radiobutton path="couponIssueType" value="0" label="${op:message('M00497')}" title="${op:message('M00497')}" />
									</p>
									<p>
										<form:radiobutton path="couponIssueType" value="1" title="시작, 종료일 설정" label="시작, 종료일 설정" /> &nbsp;
										<span class="datepicker">
											<form:input path="couponIssueStartDate" class="two datepicker optional _number" maxlength="8" title="${op:message('M01158')}" /> <!-- 달력 선택 -->
										</span>
										~
										<span class="datepicker">
											<form:input path="couponIssueEndDate" class="two datepicker optional _number" maxlength="8" title="${op:message('M01158')}" /> <!-- 달력 선택 -->
										</span>
									</p>

								</div>
							</td>
						</tr>
						<tr>
							<td class="label">${op:message('M01149')}</td> <!-- 쿠폰 적용 기간 -->
							<td>
								<div>
									<p class="mb5">
										<form:radiobutton path="couponApplyType" value="0" label="${op:message('M00497')}" />
									</p>
									<p class="mb5">
										<form:radiobutton path="couponApplyType" value="1" label="시작, 종료일 선택" /> &nbsp;
										<span class="datepicker">
											<form:input path="couponApplyStartDate" class="two datepicker optional _number" maxlength="8" title="${op:message('M01158')}" /><!-- 달력 선택 -->
										</span>
										~
										<span class="datepicker">
											<form:input path="couponApplyEndDate" class="two datepicker optional _number" maxlength="8" title="${op:message('M01158')}" /><!-- 달력 선택 -->
										</span>
									</p>
									
									<p class="mb5">
										<form:radiobutton path="couponApplyType" value="2" label="유효 기간 설정" /> &nbsp;
										<form:input path="couponApplyDay" class="one _number text-right" title="유효 기간 설정" />일
									</p>
								</div>
							</td>
						</tr>
						
						<tr>
							<td class="label">${op:message('M01160')}</td> <!-- 발급대상 -->
							<td>
								<div>
									<form:radiobutton path="couponTarget" label="${op:message('M01100')}" value="1" checked="checked" /> <!-- 전체회원 --> 
									<form:radiobutton path="couponTarget" label="${op:message('M01161')}" value="2" /> <!-- 특정회원 -->
									<input type="hidden" name="couponTargetDetail" id="couponTargetDetail" />
								</div>
								
								<div class="expose couponTarget" ${coupon.couponTarget == '1' || coupon.couponTarget == null ? 'style="display: none;"' : ''}>
									<p>
										선택한 조건 (회원을 중복 선택해도 회원 1명당 쿠폰 1장씩 발행됩니다.)
										<button type="button" class="table_btn" id="userAdd"><span>${op:message('M01162')}</span></button> <!-- 회원추가 -->
									</p>

									<div class="category_list03">
										<div class="bundle_two">
											<ul id="userList">
												<c:if test="${coupon.couponTarget == '2'}">
													${coupon.replaceCouponTargetDetail}
												</c:if>
												<input type="hidden" id="userLiIndex" value="0"/>
											</ul>							
										</div> <!-- // bundle_two -->							
									</div> <!--// category_list03 -->
								</div>
							</td>
						</tr>
						
						<tr>
							<td class="label">* ${op:message('M01163')}</td> <!-- 발급방법 -->
							<td>
								<div>
									<table class="board_write_table" summary="${op:message('M01163')}">
										<caption>${op:message('M01163')}</caption>
										<colgroup>
											<col style="width: 180px;" />
											<col />
										</colgroup>
										<tbody>
											<tr>
												<td>
													<div>
														<form:radiobutton path="couponMeans" label="즉시 발급" value="1" checked="checked"  />
													</div>
												</td>
												<td>
													<div class="board_guide ml10">
														<p class="tip">1. 회원이 쿠폰을 다운로드 하지 않아도 사용 가능 상태의 쿠폰이 생성됩니다.</p>
														<p class="tip">2. <strong>"즉시 발급"</strong>으로 발행된 쿠폰은 회수가 <strong>불가능</strong> 합니다.</p>
														<p class="tip">3. <strong>"발급대상 > 전체" + "즉시 발급"</strong>의 경우 발급 이후 <strong>신규 가입한 회원</strong>에게도 발급 됩니다.</p>
														<p class="tip">4. <strong>"발급대상 > 전체" + "즉시 발급"</strong>의 경우 회원이 사이트에 <strong>로그인 하는 시점</strong>에 발급됩니다.</p>
													</div>
												</td>
											</tr>
											<tr>
												<td>
													<div>
														<form:radiobutton path="couponMeans" label="${op:message('M01153')}" value="2" />
													</div>
												</td>
												<td> 
													<div class="board_guide ml10"> 
														<p class="tip">1. 이 쿠폰의 총 발행 쿠폰 수량을<input type="text" name="couponDownloadCount" style="color:#000" value="${op:negativeNumberToEmpty(coupon.couponDownloadCount)}" class="_number" maxlength="6" />회로 제한합니다 <strong>(공란으로 두면 무제한)</strong></p>
														<p class="tip">2. <strong>"회원 직접 다운로드"</strong>로 발행된 쿠폰은 <strong>다운로드 중지가 가능</strong> 합니다. 단, 이미 고객이 다운로드 한 쿠폰은 회수가 <strong>불가능</strong> 합니다.</p>
														<p class="tip">3. <strong>"발급대상 > 전체" + "회원 직접 다운로드"</strong>의 경우 발급 이후 <strong>신규 가입한 회원</strong>에게도 발급 됩니다.</p>
													</div>
												</td>
											</tr>
											<tr>
												<td>
													<div>
														<form:radiobutton path="couponMeans" label="쿠폰번호" value="3" onClick="this.form.couponDownloadMax.disabled=true"/>
													</div>
												</td>
												<td>
													<div class="board_guide ml10">
														<p class="tip">1. 오프라인 이벤트용 쿠폰입니다.</p>
													</div>
												</td>
											</tr>
										</tbody>
									</table>
								</div>
							</td>
						</tr>
						
						<tr>
							<td class="label">* ${op:message('M01147')} (개당)</td> <!-- 혜택 --> 
							<td>
								<div>
									<p> 판매가 기준
										<form:input path="couponPay" class="count_down2 required _number" maxlength="6" title="쿠폰 할인 혜택" /> <!-- 금액 --> 
										<form:select path="couponPayType">
											<form:option value="1">${op:message('M00049')}</form:option> <!-- 원 -->
											<form:option value="2">%</form:option>
										</form:select>
										${op:message('M01172')} <!-- 할인합니다. --> 
									</p>
							</td>
						</tr>
						<tr>
							<td class="label">중복 할인여부</td>
							<td> 
								<div>
									<form:radiobutton path="couponConcurrently" value="1" label="1개의 수량만 할인" />
									<form:radiobutton path="couponConcurrently" value="2" label="구매 수량만큼 할인" />
									<p class="tip">"구매 수량만큼 할인"의 경우 구매고객이 1,000원 할인쿠폰을 사용하는 상품의 구매수량이 2개인경우 2,000원이 할인 됩니다.</p>
								</div>
							</td>
						</tr>
						<tr>
							<td class="label">%할인금액 상한선</td>
							<td>
								<div>
									<p>
										<input type="text" name="couponLimitAmount" value="${op:negativeNumberToEmpty(coupon.couponLimitAmount)}" class="_number" maxlength="6" /> 원
										<p class="tip">
											- "해택"을 %로 지정후 발행된 경우 해당 입력값이 사용됩니다.<br />
											- ex) 판매가 50,000원인 상품에 10% 할인 쿠폰을 사용할경우 상한액을 2,500원으로 입력하시면 5,000원이 할인되는 것이 아니라 2,500원만 할인됩니다. <strong>(1개 상품 판매가 기준)</strong>
										</p>
									</p>
								</div>
							</td>
						</tr>
						<tr>
							<td class="label">적용가능 상품 판매가(개당)</td>
							<td>
								<div>
									<p>
										<input type="text" name="couponPayRestriction" value="${op:negativeNumberToEmpty(coupon.couponPayRestriction)}" class="_number" maxlength="6" />
										원 이상 상품에만 사용가능(공란으로 두면 상품금액에 상관없이 사용이 가능합니다)
									</p>
								</div>
							</td>
						</tr>
						<tr>
							<td class="label">쿠폰 사용여부 *</td> <!-- 사용상태 --> 
							<td>
								<div>
									<form:radiobutton path="couponUse" value="1" label="사용" checked="checked" /> <!-- 사용중 -->
									<form:radiobutton path="couponUse" value="2" label="미사용" /> <!-- 사용중지 -->
								</div>
							</td>
						</tr>
						<tr>
							<td class="label">상품 선택</td>
							<td>
								<div>
									<p class="mb5">
										<form:radiobutton path="couponTerms" label="전체상품" checked="checked" value="1" /> <!-- 전체상품에 발급합니다. -->
										<form:radiobutton path="couponTerms" label="특정상품" value="2" /> <!-- 특정 상품에 발급합니다. -->
										
										<!-- 특정 상품 클릭시 노출 -->
										<c:if test="${coupon.couponTerms != '2'}">
											<div class="expose couponTermsItem" style="display: none;">
										</c:if>
										<c:if test="${coupon.couponTerms == '2'}">
											<div class="expose couponTermsItem">
										</c:if>
										<c:if test="${mode != 'publish' }">
											<p><button type="button" class="table_btn" id="itemAdd"><span>${op:message('M00582')}</span></button> <!-- 상품추가 --></p>
										</c:if> 
											
											<div class="category_list02" style="width: 540px;">							
												<div class="bundle_two">
													<input type="hidden" name="couponTermsDetail" id="couponTermsDetail" />							
													<ul id="itemList">
														<c:if test="${coupon.couponTerms == '2'}">
															${coupon.replaceCouponTermsDetail}
														</c:if>
														<%-- <c:forEach items="${itemList}" var="list">
															<li class="click">${list.itemName}<input name="itemId" type="hidden" value="${list.itemId}" /><a href="javascript:;" class="fix_btn" style="right: -5px;">${op:message('M00074')}</a></li> <!-- 삭제 --> 
														</c:forEach> --%>
														<input type="hidden" id="itemLiIndex" value="0"/>
													</ul>							
												</div> <!-- // bundle_two -->							
											</div> <!--// category_list02 -->
											
										</div>
										<!-- // 특정 상품 클릭시 노출 -->
										
									</p>
									<span style="color:red;">※ 제외할 상품이 있는 경우 특정상품을 선택해 주세요</span>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
				
			</div> <!-- // board_write -->
			
		
			<div class="btn_center">
			<c:choose>
				<c:when test="${mode eq 'publish'}">
					<button type="submit" class="btn btn-active">즉시발행</button>
				</c:when>
				<c:otherwise>
					<button type="submit" class="btn btn-active">임시저장</button>
				</c:otherwise>
			</c:choose>
				<a href="${requestContext.prevPageUrl}" class="btn btn-default">${op:message('M00037')}</a> <!-- 취소 -->
			</div>
		
		</form:form>
		
	</div> <!-- // item_list01 -->

<script type="text/javascript">
var mode = '${mode}';

function payCheck(value){
	if(value == 1){
		$('#couponPay1').removeAttr('disabled');
		$('#couponPay2').attr('disabled','true');
		$('#couponLimitAmount').attr('disabled','true');
	}else if(value == 2){
		$('#couponPay1').attr('disabled','true');
		$('#couponPay2').removeAttr('disabled');
		$('#couponLimitAmount').removeAttr('disabled');
	}
}
function hideConcurrency(value){
	if(value == 1){
		$('#concurrencyHiding').removeAttr('style');
	}else if(value == 3){
		$('#concurrencyHiding').css('display','none');	
	}
}

function popUpChosenList(value) {
	var chosenList = new Array();

	$("input[name='searchAdd["+value+"].userIds']").each(function(){
		chosenList.push($(this).val());
	});
	
	var url = '/opmanager/coupon/event/chosen-user/'

	if(chosenList.length >0){
		url += chosenList;
	}else{
		url += 0;
	}
	url += '?searchKey=';
	
	var loginId = $("input[name='searchAdd["+value+"].loginId']").val();
	if($("input[name='searchAdd["+value+"].loginId']").length > 0 && (loginId != null && loginId != '')){
		url += loginId+',';
	}else{
		url+=0+',';		
	}
	
	var userName = $("input[name='searchAdd["+value+"].userName']").val();
	if($("input[name='searchAdd["+value+"].userName']").length > 0 && (userName != '' && userName != null)){
		url += userName +"," ;
	}else{
		url += 0+',';
	}
	
	var email = $("input[name='searchAdd["+value+"].email']").val();
	if($("input[name='searchAdd["+value+"].email']").length > 0 && (email != null && email != '')){
		url += email+",";
	}else{
		url += 0+",";
	}
	
	var receiveEmail = $("input[name='searchAdd["+value+"].searchReceiveEmail']").val();
	if($("input[name='searchAdd["+value+"].searchReceiveEmail']").length > 0 && (receiveEmail != null && receiveEmail != '')){
		url +=receiveEmail +",";
	}else{
		url += 9+",";	//수신시 0이므로 9를 사용
	}
	
	var receiveSms =  $("input[name='searchAdd["+value+"].searchReceiveSms']").val()
	if($("input[name='searchAdd["+value+"].searchReceiveSms']").length > 0 && (receiveSms != null && receiveSms != '')){
		url += receiveSms;
	}else{
		url += 9;		//수신시 0이므로 9를 사용
	}	
 	
	Common.popup(url, 'chosenUsers', 750,600, 1);
}
function popUpChosenItemList(value){
	
	var chosenList = new Array();
	$("input[name='itemSearchAdd["+value+"].itemIds']").each(function(){
		chosenList.push($(this).val());
	});
	
	var excludeList;
	if($("input[name='itemSearchAdd["+value+"].excludeItems']").length > 0){
		excludeList = $("input[name='itemSearchAdd["+value+"].excludeItems']").val().split(',');
	}
	
	if($("input[name='itemSearchAdd["+value+"].excludeItems']").length > 0){
		for(var i=0; i<chosenList.length; i++){
			for(var j=0; j<excludeList.length; j++){
				if(chosenList[i] == $.trim(excludeList[j])){
					chosenList.splice(i,1);
					i--;
					break;
				}
			}
		}
	}
	var url= '/opmanager/coupon/event/chosen-item/';
 	if(chosenList.length > 0){
		url += chosenList;
	}else{
		url += 0;
	}
	url += '?excludeList=';
	if($("input[name='itemSearchAdd["+value+"].excludeItems']").length > 0){
			url += excludeList;
	}else{
		url += 0;
	}

	//window.open(url,"",'scrollbars=yes,toolbar=no,resizable=no,width=750,height=600,left=0,top=0'); 
	Common.popup(url, 'chosenItems', 750,600, 1);
}
function popUpAllChosenItemList(value){
	
	var url= '/opmanager/coupon/event/all-chosen-item/';
	var excludeList=$("input[name='itemSearchAdd["+value+"].excludeItems']").val();
	if(excludeList != null && excludeList != ''){
		url += excludeList;
	}else{
		url += 0;
	}
	
	url += '?searchKey='; 
	
	var categoryGroupId = $("input[name='itemSearchAdd["+value+"].categoryGroupId']").val();
	if(categoryGroupId != '' && categoryGroupId != null){
		url += categoryGroupId +',';
	}else{
		url+='0,';
	}

	var categoryClass1 = $("input[name='itemSearchAdd["+value+"].categoryClass1']").val();
	if(categoryClass1 != '' && categoryClass1 != null){
		url += categoryClass1+',';
	}else{
		url+='0,';
	}
	
	var categoryClass2 = $("input[name='itemSearchAdd["+value+"].categoryClass2']").val();
	if(categoryClass2 != '' && categoryClass2 != null){
		url += categoryClass2+',';
	}else{
		url+='0,';
	}
	var categoryClass3 = $("input[name='itemSearchAdd["+value+"].categoryClass3']").val();
	if(categoryClass3 != '' && categoryClass3 != null){
		url += categoryClass3+',';
	}else{
		url+='0,';
	}
	var categoryClass4 = $("input[name='itemSearchAdd["+value+"].categoryClass4']").val();
	if(categoryClass4 != '' && categoryClass4 != null){
		url += categoryClass4+',';
	}else{
		url+='0,';
	}
	
	var where = $("input[name='itemSearchAdd["+value+"].where']").val();
	if(where != null && where != ''){
		url += where+',';
	}else{
		url+='0,';
	}
	var query = $("input[name='itemSearchAdd["+value+"].query']").val();
	if(query != null && query != ''){
		url += query;
	}
	
	//window.open(url,"",'scrollbars=yes,toolbar=no,resizable=no,width=750,height=600,left=0,top=0'); 
	Common.popup(url, 'chosenItems', 750,600, 1);
}

	$(function(){
		/* // 원, % 선택 시 헤택 자리수 조정. 
		$('#couponPayType').on('change', function() {
			if ($(this).val() == '1') {
				$('#couponPay').attr('maxlength', '6');
			} else {
				$('#couponPay').attr('maxlength', '2');
			}
			$('#couponPay').val('');
		});
			 */
		/* if(mode == 'publish'){
			$("input[type='radio'], input[name$='Date'], input[name='couponPayRestriction'], input[name='couponPay'], select[name='couponPayType'], select[name='categoryGroupId'], select[name^='categoryClass']").attr('disabled',true);
			$("input[name='couponUse']").attr("disabled",false);
		} */
		

		$("#coupon").validator(function() {
			/** 쿠폰 발행 확인창 (순서 0순위!) */
			if( $("input[name='couponIssueType']:checked").val() == '1'){
				
				if($("#couponIssueStartDate").val() == ''){
					$("#couponIssueStartDate").focus();
					alert("${op:message('M01180')}"); //시작 날짜를 입력해주세요.
					return false;
				}
				
				if($("#couponIssueEndDate").val() == ''){
					$("#couponIssueEndDate").focus();
					alert("${op:message('M01180')}"); //시작 날짜를 입력해주세요.
					return false;
				}
				
				if ($("#couponIssueStartDate").val() > $("#couponIssueEndDate").val()) {
					$("#couponIssueEndDate").focus();
					alert("${op:message('M01181')}"); //시작일을 종료일 이후로 입력하실수 없습니다. 
					return false;
				}
			}
			
			if( $("input[name='couponApplyType']:checked").val() == '1'){
				
				if($("#couponApplyStartDate").val() == ''){
					$("#couponApplyStartDate").focus();
					alert("${op:message('M01180')}"); // 시작 날짜를 입력해주세요.
					return false;
				}
				
				if($("#couponApplyEndDate").val() == ''){
					$("#couponApplyEndDate").focus();
					alert("${op:message('M01180')}"); // 시작 날짜를 입력해주세요. 
					return false;
				}
				
				if ($("#couponApplyStartDate").val() > $("#couponApplyEndDate").val()) {
					$("#couponApplyEndDate").focus();
					alert("${op:message('M01181')}"); // 시작일을 종료일 이후로 입력하실수 없습니다.
					return false;
				}
			} else if ($("input[name='couponApplyType']:checked").val() == '2') {
				if($("#couponApplyDay").val() == ''){
					$("#couponApplyDay").focus();
					alert("유효기간을 설정해 주세요."); // 시작 날짜를 입력해주세요.
					return false;
				}
			}

			// 발급대상이 특정회원인 경우 
			if ($('#couponTarget2').prop('checked')) {
				if ($('#userList > li').size() == 0) {
					alert('회원을 추가해 주세요.');
					$('#userAdd').focus();
					return false;
				}
			}

			// 특정 상품에 발급하는 경우 
			if ($('#couponTerms2').prop('checked')) {
				if ($('#itemList > li').size() == 0) {
					alert('상품을 추가해 주세요.');
					$('#itemAdd').focus();
					return false;
				}
			}
		
			if (mode == "publish") {
				if ($('#couponMeans1').prop('checked') == true) {
					if (!confirm("쿠폰 자동발급의 경우 한번 발행된 쿠폰은 회수하실수 없습니다.\n발행하시겠습니까?")) {
						return false;
					}
				} else {
					if (!confirm("확인 버튼을 누르시면 변경사항을 저장 후, 쿠폰을 최종발행합니다.\n발행하시겠습니까?")) {
						return false;
					}
				}
			}
			
			if($("input[name='couponTarget']:checked").val() == '2') {
				var target = $.trim($("#userList").html());
				target = target.replace(/&/g, "&amp;")
				    .replace(/</g, "&lt;")
				    .replace(/>/g, "&gt;")
				    .replace(/"/g, "&quot;")
				    .replace(/javascript:/g, "*js*");
				$("#couponTargetDetail").val(target);
			}
			
			if($("input[name='couponTerms']:checked").val() == '2') {
				var target = $.trim($("#itemList").html());
				target = target.replace(/&/g, "&amp;")
				    .replace(/</g, "&lt;")
				    .replace(/>/g, "&gt;")
				    .replace(/"/g, "&quot;")
				    .replace(/javascript:/g, "*js*");
				$("#couponTermsDetail").val(target);
			}

			// 총 발행수량 공백 예외 처리
			if($(":radio[id=couponDownloadMax]:checked").val() == 2) {
				if($("input[name=couponDownloadCount]").val() == 0 || $("input[name=couponDownloadCount]").val() == "") {
					alert("최대 발행수량을 입력해 주세요.")
					return false;
				}
			}
			
			// 금액할인공백 예외 처리
			if($(":radio[id=couponPayType1]:checked").val() == 1) {
				if($("input[name=couponPay]").val() == 0 || $("input[name=couponPay]").val() == "") {
					alert("금액할인을 입력해 주세요.")
					return false;
				}
			}

			// %할인 공백 예외 처리
			if($(":radio[id=couponPayType2]:checked").val() == 2) {
				if($("input[id=couponPay2]").val() == 0 || $("input[id=couponPay2]").val() == "") {
					alert("%할인을 입력해 주세요.")
					return false;
				}
				if($("input[name=couponLimitAmount]").val() == -1) {
					alert("최대 할인 금액을 입력해 주세요.")
					return false;
				}
			}
			
			// 최소 상품금액 공백 예외 처리
			if($("input[name=couponPayRestriction]").val() == 0 || $("input[name=couponPayRestriction]").val() == "") {
				alert("최소 상품 금액을 입력해 주세요.")
				return false;
			}
			
			// 임시 저장시 확인 처리
			if (mode != "publish") {
				var message = "임시 저장 하시겠습니까?";
				if (!Common.confirm(message)) {
					return false;
				}
			}
		});
		
		// %할인 최대 금액이 0일 때
		if($("input[name=couponLimitAmount]").val() == 0) {
			$("input[name=couponLimitAmount]").val("");
		}
		
		if(mode != 'publish'){
			
			$("body").on("click",".fix_btn",function(){
				$(this).parent().remove();
				$('.userCount').html($('#userList').find('li').length);
			});
		
			$("#categoryAdd").on("click",function(){
				if ($('#categoryGroupId').val() == '0') {
					alert('카테고리를 선택해 주십시오.');
					$("#categoryGroupId").focus();
					return;
				}
					
				var categoryText = "";
				var categoryId = "";
				var categoryInput = "";
				var flag = true;

				var isSelectedCategory = false;
				for(var i = 1; i <= 4; i++ ){
					if ($("select[name='categoryClass"+i+"']").val() != ''){
						
						categoryText = $("select[name='categoryClass"+i+"'] option:selected").text();
						categoryInput = "<input name='categoryId' type='hidden' value='"+$("select[name='categoryClass"+i+"'] option:selected").attr("rel")+"' />";
						categoryId = $("select[name='categoryClass"+i+"'] option:selected").attr("rel");

						if (!isSelectedCategory) {
							isSelectedCategory = true;
						}
					}
				}

				
				if (!isSelectedCategory) {
					alert('1차 카테고리를 선택해 주십시오.');
					$("select[name='categoryClass1']").focus();
					return;
				}
				
				$("input[name='categoryId']").each(function(){
					if( $(this).val() == categoryId ){
						flag = false;
					} 
				});
				
				if(flag){
					$("#categoryList").append("<li class='click'>" + categoryText + categoryInput + "<a href='javascript:;' class='fix_btn' style='right: 0'>삭제</a></li>");
				}
				
			});
			
			var userArray = {
				"popupUrl" : "/opmanager/coupon/user-list",	
				"popupName" : "user-list",
				"width" : 750 ,
				"height" : 600
			};
		
		
			$("#userAdd").on("click",userArray,couponPopup);
		
			var itemArray = {
				"popupUrl" : "/opmanager/coupon/item-list",	
				"popupName" : "item-list",
				"width" : 1180 ,
				"height" : 800
			};
		
			$("#itemAdd").on("click",itemArray,couponPopup);
		}
		
		var issueTypeArray = {
				"input" : "issueType" ,
				"classResult"  :  "issueType",
				"startDate" : "couponIssueStartDate",
				"endDate" : "couponIssueEndDate"
			};
		
		$("input[name='issueType']").on("click",issueTypeArray, showAndHide);
		
		var applyTypeArray = {
				"input" : "applyType" ,
				"classResult"  :  "applyType",
				"startDate" : "couponApplyStartDate",
				"endDate" : "couponApplyEndDate"
			};
		$("input[name='applyType']").on("click",applyTypeArray, showAndHide);
		
		var couponTargetArray = {
			"input" : "couponTarget",
			"classResult"  :  "couponTarget",
			"message" : "${op:message('M01160')}", //  발급 대상
			"showType" : "1"
		};
		
		$("input[name='couponTarget']").on("click",couponTargetArray, showAndHide2);
		/*
		var couponMeansArray = {
			"input" : "couponMeans",
			"classResult"  :  "couponMeans",
			"message" : "발급 방법",
			"showType" : "2"
		};
			
		$("input[name='couponMeans']").on("click",couponMeansArray, showAndHide2);
		*/
		
		var couponTermsArray = {
				"input" : "couponTerms",
				"classResult1"  :  "couponTermsItem",
				"classResult2"  :  "couponTermsCategory",
				"message" : "${op:message('M01182')}" // 발급 조건
			};
			
		$("input[name='couponTerms']").on("click",couponTermsArray, showAndHide3);
		
		
		$( window ).scroll(function() {
			setHeight();
		});
		
		//ShopEventHandler.categorySelectboxChagneEvent();  
		//Shop.activeCategoryClass('${itemParam.categoryGroupId}', '${itemParam.categoryClass1}', '${itemParam.categoryClass2}', '${itemParam.categoryClass3}', '${itemParam.categoryClass4}');
		
	});
	
	$(window).load(function() {

		// 쿠폰번호일 때 발행수량 비활성화
		if($(":radio[id=couponMeans3]:checked").val() == 3) {
			$("#couponDownloadMax").attr("disabled", true);
		}
		
		// 할인 방식에 따라 활성화
		if($(":radio[id=couponPayType2]:checked").val() == 2) {
			$('#couponPay1').attr('disabled','true');
			$('#couponPay2').removeAttr('disabled');
			$('#couponLimitAmount').removeAttr('disabled');
		}
		
	}); 
		
	function showAndHide(event){
		if( $("input[name='"+event.data.input+"']:checked").val() != "1" ){
			$("."+event.data.classResult).show();
		} else {
			$("."+event.data.classResult).hide();
			$("#"+startDate).val('');
			$("#"+endDate).val('');
		}
	}
	
	function showAndHide2(event){
		if( $("input[name='"+event.data.input+"']:checked").val() != "1" ){
			$("."+event.data.classResult).show();
		} else {
			Common.confirm(event.data.message+"${op:message('M01183')}", // 을 전체로 변경시에는 데이터가 삭제됩니다 그래도 실행 하시겠습니까?
			function(){
				$('.userCount').html('0');
				$("."+event.data.classResult).hide();
				
				if(event.data.showType == '1'){
					$("."+event.data.classResult+" > div > div > ul > li").remove();
				} else {
					$("#couponDownloadCount").val('');
					$("input[name='couponConcurrently']").attr('checked',false);
					$("input[name='couponAgain']").attr('checked',false); 
				}
			},
			function(){
				$("input[name='"+event.data.input+"']").each(function(){
					if( $(this).val() == '1'){
						$(this).prop("checked", false);
					}else {
						$(this).prop("checked", true);	
					}
				});
			});
		}
			
	}
	
	var couponTermsValue = ""; 
 	
	function showAndHide3(event){
		
		if( $("input[name='"+event.data.input+"']:checked").val() != '1' ){
			
			couponTermsValue = $("input[name='"+event.data.input+"']:checked").val();
			
			if($("input[name='"+event.data.input+"']:checked").val() == '2'){
				$("."+event.data.classResult1).show();
				$("."+event.data.classResult2).hide();
				$("."+event.data.classResult2+" > div > div > ul > li").remove();
			} else if($("input[name='"+event.data.input+"']:checked").val() == '3') {
				$("."+event.data.classResult2).show();
				$("."+event.data.classResult1).hide();
				$("."+event.data.classResult1+" > div > div > ul > li").remove();
			}
			
		} else  {
			
			Common.confirm(event.data.message+"${op:message('M01183')}", // 을 전체로 변경시에는 데이터가 삭제됩니다 그래도 실행 하시겠습니까?
			function(){
				
				if(couponTermsValue == "2"){
					$("."+event.data.classResult1).hide();
					$("."+event.data.classResult1+" > div > div > ul > li").remove();
				} else if(couponTermsValue == "3"){
					$("."+event.data.classResult2).hide();
					$("."+event.data.classResult2+" > div > div > ul > li").remove();
				} 
				
			},
			function(){
				$("input[name='"+event.data.input+"']").each(function(){
					if( $(this).val() == couponTermsValue){
						$(this).prop("checked", true);	
					}else {
						$(this).prop("checked", false);
					}
				});
			});
		} 
	}
	
	function couponPopup(event){
		Common.popup(event.data.popupUrl, event.data.popupName, event.data.width, event.data.height, 1);
	}
	
</script>