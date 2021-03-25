<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="daum"	tagdir="/WEB-INF/tags/daum" %>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules" %>
<style>
.phone_number {width: 45px;}

</style>
			<h3>판매자정보수정</h3>

			<form:form modelAttribute="seller" method="post">
				<input type="hidden" name="sellerId" value="${seller.sellerId}" />
				<input type="hidden" name="commissionRate" value="${seller.commissionRate}" />
				<div class="board_write">
					<table class="board_write_table">
						<colgroup>
							<col style="width:170px;" />
							<col style="" />
							<col style="width:170px;" />
							<col style="" />
						</colgroup>
						<tbody>
							<tr>
			 					<td class="label">상호</td>
			 					<td>
			 						<div class="input_wrap col-w-7">
			 							${seller.companyName}
			 						</div>
			 					</td>
			 					<td class="label">대표자명</td>
			 					<td>
			 						<div class="input_wrap col-w-7">
			 							${seller.representativeName}
			 						</div>
			 					</td>

		 					</tr>
		 					<tr>
			 					<td class="label">업태</td>
			 					<td>
			 						<div class="input_wrap col-w-7">
			 							${seller.businessType}
			 						</div>
			 					</td>
			 					<td class="label">종목</td>
			 					<td>
			 						<div class="input_wrap col-w-7">
			 							${seller.businessItems}
			 						</div>
			 					</td>
		 					</tr>
		 					<tr>
			 					<td class="label">사업자등록번호</td>
			 					<td colspan="3">
			 						<div class="input_wrap col-w-7">
			 							${seller.businessNumber}
			 						</div>
			 					</td>
		 					</tr>
		 					<tr>
			 					<td class="label">사업장소재지</td>
			 					<td colspan="3">
			 						<div class="input_wrap col-w-7">
			 							${seller.businessLocation}
			 						</div>
			 					</td>
		 					</tr>


		 					<tr>
			 					<td class="label">아이디</td>
			 					<td colspan="3">
			 						<div class="input_wrap">
			 							${seller.loginId}
			 						</div>
			 					</td>
		 					</tr>

						</tbody>
					</table>


					<h3 class="mt20">담당자정보</h3>
					<table class="board_write_table">
						<colgroup>
							<col style="width:170px;" />
							<col style="" />
							<col style="width:170px;" />
							<col style="" />
						</colgroup>
						<tbody>
							<tr>
			 					<td class="label">담당자명<span class="require">*</span></td>
			 					<td>
			 						<div class="input_wrap col-w-7">
			 							<form:input path="userName" class="required" title="담당자명" />
			 						</div>
			 					</td>
			 					<td class="label">전화번호<span class="require">*</span></td>
			 					<td>
			 						<div class="input_wrap col-w-7">
		 								<c:set var="tel_arr" value="${fn:split(seller.telephoneNumber, '-') }"/>
										<select name="telephoneNumber1" class="choice3" style="width:100px;">
											<c:forEach items="${telCodes}" var="code">
												<option value="${code.value}" ${code.value == tel_arr[0] ? 'selected="selected"' : '' }>${code.label}</option>
											</c:forEach>
										</select> -
										<input type="text" name="telephoneNumber2" value="${tel_arr[1]}" maxlength="4" class="required form-sm _number" title="전화번호 가운데자리"/> -
										<input type="text" name="telephoneNumber3" value="${tel_arr[2]}" maxlength="4" class="required form-sm _number" title="전화번호 마지막자리"/>
			 						</div>
			 					</td>
		 					</tr>
		 					<tr>
			 					<td class="label">담당자 휴대폰번호<span class="require">*</span></td>
			 					<td>
			 						<div class="input_wrap">
			 							<c:set var="phone_arr" value="${fn:split(seller.phoneNumber, '-') }"/>
										<select name="phoneNumber1" class="choice3" style="width:100px;">
											<c:forEach items="${phoneCodes}" var="code">
												<option value="${code.value}" ${code.value == phone_arr[0] ? 'selected="selected"' : '' }>${code.label}</option>
											</c:forEach>
										</select> -
										<input type="text" name="phoneNumber2" value="${phone_arr[1]}" maxlength="4" class="required form-sm _number" title="휴대폰번호 가운데자리"/> -
										<input type="text" name="phoneNumber3" value="${phone_arr[2]}" maxlength="4" class="required form-sm _number" title="휴대폰번호 마지막자리"/>

			 						</div>
			 					</td>
			 					<td class="label">팩스번호<span class="require">*</span></td>
			 					<td>
			 						<div class="input_wrap col-w-7">
			 							<c:set var="fax_arr" value="${fn:split(seller.faxNumber, '-') }"/>
										<select name="faxNumber1" class="choice3" style="width:100px;">
											<c:forEach items="${telCodes}" var="code">
												<option value="${code.value}" ${code.value == fax_arr[0] ? 'selected="selected"' : '' }>${code.label}</option>
											</c:forEach>
										</select> -
										<input type="text" name="faxNumber2" value="${fax_arr[1]}" maxlength="4" class="required form-sm _number" title="팩스 가운데자리"/> -
										<input type="text" name="faxNumber3" value="${fax_arr[2]}" maxlength="4" class="required form-sm _number" title="팩스 마지막자리"/>
			 						</div>
			 					</td>
		 					</tr>
		 					<tr>
			 					<td class="label">담당자 이메일<span class="require">*</span></td>
			 					<td colspan="3">
			 						<div class="input_wrap col-w-7">
			 							<form:input path="email" class="quater required _email" title="담당자 이메일"/>
			 						</div>
			 					</td>
		 					</tr>
		 					<tr>
								<td class="label">${op:message('M00115')}<span class="require">*</span></td> <!-- 우편번호 -->
		 						<td colspan="3">
									<div class="input_wrap col-w-7">
											<input type="hidden" name="newPost" value="">
											<input type="text" name="post" id="post" value="${seller.post}" class="required" title="${op:message('M00115')}  ${op:message('M00107')}" maxlength="5" class="one" required="required" readonly="readonly">
											<a href="javascript:;" onclick="openDaumPostcode()" class="btn btn-dark-gray btn-sm"> <span class="glyphicon glyphicon-search"></span> ${op:message('M00117')}</a>
									</div>
								</td>
		 					</tr>
		 					<tr>
			 					<td class="label">${op:message('M01646')}<span class="require">*</span></td> <!-- 주소 -->
								<td colspan="3">
									<div class="input_wrap col-w-7">
										<form:input type="text" path="address" title="${op:message('M01646')}" class="full required" style="width:70%" readonly="true" /><br/>
										<form:input type="text" path="addressDetail" title="${op:message('M01647')}" class="full required" style="width:70%" maxlength="100"/>
									</div>
								</td>
		 					</tr>
		 				</tbody>
		 			</table>

				</div> <!--// board_write E-->

				<div class="item_list mt30">
					<h3><span>주문 담당자</span>
						<span class="text-info">
							* <strong>주문안내 SMS발송</strong> 시간을 설정한 경우 설정된 시간에 주문 담당자에게 주문안내 SMS발송합니다.
						</span>
					</h3>
					<div class="board_write">
						<table class="board_write_table">
							<caption>${op:message('M00746')}</caption>
							<colgroup>
								<col style="width:170px;" />
								<col style="width:auto;" />
								<col style="width:170px;" />
								<col style="width:auto;" />
							</colgroup>
							<tbody>
								</tr>
								<tr>
									<td class="label">${op:message('M01632')}</td> <!-- 담당자명 -->
									<td colspan="3">
										<div>
											<form:input type="text" path="secondUserName" title="${op:message('M01632')}" class="full" />
										</div>
									</td>
								</tr>
								<tr>
									<td class="label">${op:message('M01633')}</td> <!-- 담당자 전화번호 -->
									<td>
										<div>
											<c:set var="tel_arr" value="${fn:split(seller.secondTelephoneNumber, '-') }"/>
											<select name="secondTelephoneNumber1" class="choice3">
												<c:forEach items="${telCodes}" var="code">
													<option value="${code.value}" ${code.value == tel_arr[0] ? 'selected="selected"' : '' }>${code.label}</option>
												</c:forEach>
											</select> -
											<input type="text" name="secondTelephoneNumber2" value="${tel_arr[1]}" maxlength="4" class="form-sm _number" title="전화번호 가운데자리"/> -
											<input type="text" name="secondTelephoneNumber3" value="${tel_arr[2]}" maxlength="4" class="form-sm _number" title="전화번호 마지막자리"/>
										</div>
									</td>
									<td class="label">${op:message('M01634')}</td> <!-- 담당자 휴대폰번호 -->
									<td>
										<div>
											<c:set var="phone_arr" value="${fn:split(seller.secondPhoneNumber, '-') }"/>
											<select name="secondPhoneNumber1" class="choice3">
												<c:forEach items="${phoneCodes}" var="code">
													<option value="${code.value}" ${code.value == phone_arr[0] ? 'selected="selected"' : '' }>${code.label}</option>
												</c:forEach>
											</select> -
											<input type="text" name="secondPhoneNumber2" value="${phone_arr[1]}" maxlength="4" class="form-sm _number" title="휴대폰번호 가운데자리"/> -
											<input type="text" name="secondPhoneNumber3" value="${phone_arr[2]}" maxlength="4" class="form-sm _number" title="휴대폰번호 마지막자리"/>
										</div>
									</td>
								</tr>
								<tr>
									<td class="label">${op:message('M01644')}</td> <!-- 담당자이메일 -->
							        <td>
										<div>
											<form:input path="secondEmail" class="form-half optional _email" title="담당자 이메일"/>
										</div>
							        </td>
							        <td class="label">주문안내 SMS 발송</td>
							        <td>
										<div>
											<select name="smsSendTime">
												<option value="">미설정</option>

												<c:set var="timeSeperate" value="오전" />

												<c:forEach begin="1" end="23" var="i">
													<c:set var="time" value="${i}" />
													<c:set var="timeValue" value="${i}" />
													<c:if test="${i >= 12}">
														<c:set var="timeSeperate" value="오후" />
													</c:if>
													<c:if test="${i > 12}">
														<c:set var="time" value="${i - 12}" />
													</c:if>

													<c:if test="${i < 10}">
														<c:set var="i">0${i}</c:set>
													</c:if>

													<option value="${timeValue}" ${seller.smsSendTime == timeValue ? 'selected="selected"' : '' }>${timeSeperate}&nbsp;${time}시</option>

												</c:forEach>
											</select>


										</div>
							        </td>
								</tr>

							</tbody>
						</table>
					</div> <!-- // board_write -->
				</div>


				<div class="item_list mt30">
					<h3><span>정산정보</span></h3> <!-- 정산정보 -->
					<div class="board_write">
						<table class="board_write_table">
							<caption>정산정보</caption>
							<colgroup>
								<col style="width:170px;" />
								<col style="width:auto;" />
								<col style="width:170px;" />
								<col style="width:auto;" />
							</colgroup>
							<tbody>
								<tr>
									<td class="label">${op:message('M01653')}</td> <!-- 수수료율 -->
									<td colspan="3">
										<div>
											${seller.commissionRate} %
										</div>
									</td>
						        </tr>
						        <tr>
									<td class="label">${op:message('M01654')}</td> <!-- 정산주기 -->
									<td>
										<div>
											<c:choose>
												<c:when test="${seller.remittanceType == 1}">
													일정산
												</c:when>
												<c:when test="${seller.remittanceType == 2}">
													주정산
												</c:when>
												<c:when test="${seller.remittanceType == 3}">
													15일정산
												</c:when>
												<c:when test="${seller.remittanceType == 4}">
													월정산
												</c:when>
											</c:choose>
										</div>
									</td>

									<c:if test="${seller.remittanceType != 4}">
										<c:set var="tdHide" value="hide"/>
									</c:if>

									<td class="label remittanceDayTd ${tdHide}">정산일</td> <!-- 정산일 -->
									<td class="remittanceDayTd ${tdHide}">
										<div>
											${seller.remittanceDay}일
										</div>
									</td>
						        </tr>
						        <tr>
									<td class="label">${op:message('M01655')}<span class="require">*</span></td> <!-- 정산- 입급은행명 -->
									<td>
										<div>
											<form:input type="text" path="bankName" title="${op:message('M01655')}" class="full required" maxlength="20"/>
										</div>
									</td>
									<td class="label">${op:message('M01663')}<span class="require">*</span></td> <!-- 정산- 입급계좌번호 -->
									<td>
										<div>
											<form:input type="text" path="bankInName" title="${op:message('M01663')}" class="full required" maxlength="10"/>
										</div>
									</td>
						        </tr>
						        <tr>
									<td class="label">${op:message('M01656')}<span class="require">*</span></td> <!-- 정산- 입급계좌번호 -->
									<td colspan="3">
										<div>
											<p class="text-info text-sm">* '-' 을 제외한 계좌번호를 입력해주세요.</p>
											<form:input type="text" path="bankAccountNumber" title="${op:message('M01656')}" class="full _number required" maxlength="20"/>
										</div>
									</td>
						        </tr>
							</tbody>
						</table>
						<div class="check-all-item-notice-label" style="display: block;">
							* 정산주기 상세설명<br>
							1. 일정산 : 구매확정일의 다음날 정산됩니다.<br>
							2. 주정산 : 구매확정일 기준 차주 월요일에 정산됩니다. (일 ~ 토요일까지의 구매확정주문들이 차주 월요일에 정산)<br>
							3. 15일주기 : 1 ~ 14일 구매확정주문 = 15일에 정산, 15 ~ 말일 구매확정주문 = 다음달 1일에 정산됩니다.<br>
							4. 월정산 : 월정산의 정산일날 구매확정된 주문은 다음달에 정산됩니다. (ex. 설정한 정산일이 매달1일, 주문의 구매확정이 1월 1일인경우 2월1일에 정산.)<br>
							* <strong>정산일이 휴일인 경우 다음날 정산됩니다.</strong>
						</div>
					</div>
				</div>


				<div class="board_write mt30">
					<h3><span>조건부배송비</span></h3> <!-- 조건부배송비 -->
					<div class="board_write">
						<table class="board_write_table">
							<caption>조건부배송비</caption>
							<colgroup>
								<col style="width:170px;" />
								<col style="width:auto;" />
								<col style="width:170px;" />
								<col style="width:auto;" />
							</colgroup>
							<tbody>
								<tr>
									<td class="label">설정 여부</td> <!-- 판매자 조건부 배송비 설정여부 -->
									<td colspan="3">
										<div>
											<form:radiobutton path="shippingFlag" title="${op:message('M01658')}" class="required" value="N" label="미설정"/>
											<form:radiobutton path="shippingFlag" title="${op:message('M01658')}" class="required" value="Y" label="설정"/>

										</div>
									</td>
						        </tr>

						        <c:set var="shipping_hide" value="hide"/>
						        <c:if test="${seller.shippingFlag=='Y' || seller.shippingFlag==''}">
									<c:set var="shipping_hide" value=""/>
									<c:set var="shipping_required" value="required"/>
						        </c:if>

						        <tr class="shipping_tr ${shipping_hide}">
									<td class="label">배송비<span class="require">*</span></td> <!-- 판매자 조건부 배송비 -->
									<td>
										<div>
											<form:input type="text" path="shipping" title="${op:message('M01658')}" class="full ${shipping_required} amount _number_comma shipping _min_0" /> 원
										</div>
									</td>
									<td class="label">무료배송 조건별 금액<span class="require">*</span></td> <!-- 판매자 조건부 배송비 무료배송 금액 -->
									<td>
										<div>
											<form:input type="text" path="shippingFreeAmount" title="${op:message('M01659')}" class="full ${shipping_required} amount _number_comma shipping _min_0" /> 원 이상 무료
										</div>
									</td>
						        </tr>
						        <tr class="shipping_tr ${shipping_hide}">
									<td class="label">${op:message('M01660')}<span class="require">*</span></td> <!-- 추가배송비 - 제주도 -->
									<td>
										<div>
											<form:input type="text" path="shippingExtraCharge1" title="${op:message('M01658')}" class="full ${shipping_required} amount _number_comma shipping _min_0" /> 원
										</div>
									</td>
									<td class="label">${op:message('M01661')}<span class="require">*</span></td> <!-- 추가배송비 - 도서산간 -->
									<td>
										<div>
											<form:input type="text" path="shippingExtraCharge2" title="${op:message('M01659')}" class="full ${shipping_required} amount _number_comma shipping _min_0" /> 원
										</div>
									</td>
						        </tr>
							</tbody>
						</table>
					</div>
				</div>

				<div class="board_write mt30">
					<h3><span>미니몰 상단</span></h3>
					<div>
						<form:textarea path="headerContent" cols="30" rows="20" style="width: 1085px" class="" title="미니몰 상단 내용" />
					</div>
				</div>
				<div class="buttons">
					<button type="submit" class="btn btn-active">${op:message('M00087')} <!-- 저장 --></button>
				</div>
			</form:form>


<daum:address />
<module:smarteditorInit />
<module:smarteditor id="headerContent" />

<page:javascript>
<script type="text/javascript">
var loginCheckId = '';
$(function(){
	Common.addNumberComma();
	$('#seller').validator(function() {
		Common.removeNumberComma();
		Common.getEditorContent("headerContent");
	});

	$('input[name=shippingFlag]').on('change', function(){
		if($(this).val()=='Y'){
			$('.shipping_tr').removeClass('hide');
			$('.shipping').addClass('required');
		}else{
			$('.shipping_tr').addClass('hide');
			$('.shipping').removeClass('required');
		}


	});


	// 비밀번호 변경팝업
	$('.btn-password').on('click', function(e) {
		e.preventDefault();
		Common.popup('/seller/edit/password-change-popup', 'password_change_popup', 370, 330, 1);
	});

});


function openDaumPostcode() {

	var tagNames = {
		'newZipcode'			: 'post',
		/* 'zipcode' 				: 'post', */
		'zipcode1' 				: 'post1',
		'zipcode2' 				: 'post2',
	}

	openDaumAddress(tagNames);

}
</script>
</page:javascript>
