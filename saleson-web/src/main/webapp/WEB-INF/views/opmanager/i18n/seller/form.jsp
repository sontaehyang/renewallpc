<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>
<%@ taglib prefix="daum"	tagdir="/WEB-INF/tags/daum" %>
<style>
span.require {color: #e84700; margin-left: 5px;}

</style>
		
			<div class="location">
				<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
			</div>
		
			
			
			<form:form modelAttribute="seller" method="post" enctype="multipart/form-data">			
				<input type="hidden" name="idCheck" id="idCheck" value="0"/>
				<input type="hidden" name="mode" id="mode" value="${mode}"/>
				<div class="item_list">
					<!--입점업체 등록/수정 시작-->
					<h3><span>${op:message('M00746')}</span></h3> <!-- 입점업체 등록/수정 --> 
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
								<tr>
									<td class="label">${op:message('M01630')}<span class="require">*</span></td> <!-- 판매자명 --> 
									<td colspan="3"> 
										<div>
											<form:input type="text" path="sellerName" title="${op:message('M01630')}" class="form-half required" maxlength="20"/>	 
									    </div>
									</td>							
								</tr>
								<tr>
									<td class="label">${op:message('M01631')}<span class="require">*</span></td> <!-- 로그인아이디 --> 		
									<td>
										<div>
											<c:choose>
												<c:when test="${mode=='create'}">
													<form:input type="text" path="loginId" title="${op:message('M01631')}" class=" required" />
								    				&nbsp;<span id="dupl_message"></span>
												</c:when>
												<c:otherwise>
													${seller.loginId}	 
													<input type="hidden" name="loginId" id="loginId" value="${seller.loginId}"/>
												</c:otherwise>
											</c:choose>
											
									    </div>
									</td>		
								</tr>
								<c:if test="${mode=='create'}">
									<tr>
										<td class="label">
											${op:message('M00150')}
											<c:set var="pwd_required" value=""/>
											<c:if test="${mode=='create'}">
												<span class="require">*</span>
												<c:set var="pwd_required" value="required"/>
											</c:if>
										</td> <!-- 패스워드 -->
										<td>
											<div>
												<input type="password" name="password" id="password" title="${op:message('M00150')}" class=" _password ${pwd_required}" />
										    </div>
										</td>
										<td class="label">${op:message('M00151')}</td> <!-- 패스워드 확인-->
										<td>
											<div>
												<input type="password" name="password2" id="password_confirm" title="${op:message('M00151')}" class=" ${pwd_required}" />
										    </div>
										</td>
									</tr>
								</c:if>
							</tbody>
						</table>
					</div>
				</div>
				
				<div class="item_list mt30">
				<!--사업자정보 시작-->
					<h3><span>사업자 정보</span></h3> <!-- 입점업체 등록/수정 --> 
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
								<tr>
									<td class="label">${op:message('M01635')}<span class="require">*</span></td> <!-- 상호 --> 
									<td colspan="3">
										<div>
											<form:input type="text" path="companyName" title="${op:message('M01635')}" class="form-half required" />
										</div>
									</td>
									
						        </tr>
						        <tr>
						        	<td class="label">${op:message('M01648')}<span class="require">*</span></td> <!-- 대표자명 --> 
									<td>
										<div>
											<form:input type="text" path="representativeName" title="${op:message('M01648')}" class=" required" />
										</div>
									</td>
									<td class="label">${op:message('M01651')}<span class="require">*</span></td> <!-- 업태 --> 
									<td>
										<div>
											<form:input type="text" path="businessType" title="${op:message('M01651')}" class=" required" />
										</div>
									</td>
									
						        </tr>
						        <tr>
									<td class="label">${op:message('M01649')}<span class="require">*</span></td> <!-- 사업자번호 --> 
									<td>
										<div>
											<c:set var="businessNumber_arr" value="${fn:split(seller.businessNumber, '-') }"/>
											<input type="text" name="businessNumber1" id="businessNumber1" value="${businessNumber_arr[0]}" title="${op:message('M01649')}" class=" _number form-sm required" maxlength="3"/>
											-
											<input type="text" name="businessNumber2" id="businessNumber2" value="${businessNumber_arr[1]}" title="${op:message('M01649')}" class=" _number form-sm required" maxlength="2"/>
											-
											<input type="text" name="businessNumber3" id="businessNumber3" value="${businessNumber_arr[2]}" title="${op:message('M01649')}" class=" _number form-sm required" maxlength="5"/>
											
										</div>
									</td>
									<td class="label">${op:message('M01652')}<span class="require">*</span></td> <!-- 종목 --> 
									<td>
										<div>
											<form:input type="text" path="businessItems" title="${op:message('M01652')}" class=" required" />
										</div>
									</td>
						        </tr>
						        <tr>
									<td class="label">${op:message('M01650')}<span class="require">*</span></td> <!-- 사업장 소재지 --> 
									<td colspan="3">
										<div>
											<form:input type="text" path="businessLocation" title="${op:message('M01650')}" class="full required"  style="width:50%"/>
											<!-- <button type="button" class="op-copy-address btn btn-dark-gray btn-sm">주소복사</button>  -->
										</div>
									</td>
						        </tr>
							</tbody>
						</table>
					</div>
				</div>
				
				
				<div class="item_list mt30">
					<h3><span>담당자정보</span></h3>
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
									<td class="label">${op:message('M01632')}<span class="require">*</span></td> <!-- 담당자명 --> 
									<td colspan="3">
										<div>
											<form:input type="text" path="userName" title="${op:message('M01632')}" class=" required" />
										</div>
									</td>						 
								</tr>
								<tr>
									<td class="label">${op:message('M01633')}<span class="require">*</span></td> <!-- 담당자 전화번호 --> 
									<td>
										<div>
											<c:set var="tel_arr" value="${fn:split(seller.telephoneNumber, '-') }"/>
											<select name="telephoneNumber1" class="choice3">
												<c:forEach items="${telCodes}" var="code">
													<option value="${code.value}" ${code.value == tel_arr[0] ? 'selected="selected"' : '' }>${code.label}</option>
												</c:forEach>
											</select> - 
											<input type="text" name="telephoneNumber2" value="${tel_arr[1]}" maxlength="4" class="required form-sm _number" title="전화번호 가운데자리"/> - 
											<input type="text" name="telephoneNumber3" value="${tel_arr[2]}" maxlength="4" class="required form-sm _number" title="전화번호 마지막자리"/>
										</div>
									</td>	
									<td class="label">${op:message('M01634')}<span class="require">*</span></td> <!-- 담당자 휴대폰번호 --> 
									<td>
										<div>
											<c:set var="phone_arr" value="${fn:split(seller.phoneNumber, '-') }"/>
											<select name="phoneNumber1" class="choice3">
												<c:forEach items="${phoneCodes}" var="code">
													<option value="${code.value}" ${code.value == phone_arr[0] ? 'selected="selected"' : '' }>${code.label}</option>
												</c:forEach>
											</select> - 
											<input type="text" name="phoneNumber2" value="${phone_arr[1]}" maxlength="4" class="required form-sm _number" title="휴대폰번호 가운데자리"/> - 
											<input type="text" name="phoneNumber3" value="${phone_arr[2]}" maxlength="4" class="required form-sm _number" title="휴대폰번호 마지막자리"/>
										</div>
									</td>						
								</tr>
								<tr>
									<td class="label">${op:message('M01643')}<span class="require">*</span></td> <!-- 팩스번호 --> 
									<td>
										<div>
											<c:set var="fax_arr" value="${fn:split(seller.faxNumber, '-') }"/>
											<select name="faxNumber1" class="choice3">
												<c:forEach items="${telCodes}" var="code">
													<option value="${code.value}" ${fax_arr[0] == code.value ? 'selected="true"' : '' }>${code.label}</option>
												</c:forEach>
											</select> - 
											<input type="text" name="faxNumber2" value="${fax_arr[1]}" maxlength="4" class="required form-sm _number" title="팩스 가운데자리"/> - 
											<input type="text" name="faxNumber3" value="${fax_arr[2]}" maxlength="4" class="required form-sm _number" title="팩스 마지막자리"/>
										</div>
									</td>			
							        <td class="label">${op:message('M01644')}<span class="require">*</span></td> <!-- 담당자이메일 --> 
							        <td>
										<div>
											<form:input path="email" class="form-half required _email" title="담당자 이메일"/>
										</div>	
							        </td>		 
								</tr>
	
										
								<tr>
									<td class="label">${op:message('M00115')}<span class="require">*</span></td> <!-- 우편번호 --> 
									<td colspan="3">
										<div>
											<input type="hidden" name="newPost" value=""> 
											<input type="text" name="post" id="post" value="${seller.post}" class="required" title="${op:message('M00115')}  ${op:message('M00107')}" maxlength="5" class="one" readonly="readonly">
											<button type="button" onclick="openDaumPostcode()" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> 검색</button>
										</div>
									</td>
						        </tr>
						        <tr>
									<td class="label">${op:message('M01646')}<span class="require">*</span></td> <!-- 주소 --> 
									<td colspan="3">
										<div>
											<form:input type="text" path="address" title="${op:message('M01646')}" class=" required" style="width:70%" readonly="true" /><br/>
											<form:input type="text" path="addressDetail" title="${op:message('M01647')}" class=" required" style="width:70%; margin-top: 3px;" maxlength="100"/>
										</div>
									</td>
						        </tr>
							</tbody>					 
						</table>								 							
					</div> <!-- // board_write -->
				</div>
				
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
											<form:input type="text" path="secondUserName" title="${op:message('M01632')}" class="" />
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
									<td class="label">${op:message('M01653')}<span class="require">*</span></td> <!-- 수수료율 --> 
									<td colspan="3">
										<div>
											<form:input type="text" path="commissionRate" title="${op:message('M01653')}" class=" required amount _number_float _percent _min_0" maxlength="4"/>%
										</div>
									</td>
						        </tr>
						        <tr>
									<td class="label">${op:message('M01654')}<span class="require">*</span></td> <!-- 정산주기 --> 
									<td>
										<div>
											
											<form:select path="remittanceType" class="choice3" style="width:100px;">
												<form:option value="">선택하세요</form:option>
												<c:forEach items="${remittanceTypeCodes}" var="code">
													<form:option value="${code.value}">${code.label}</form:option>
												</c:forEach>
											</form:select>
										</div>
									</td>
									
									<c:if test="${seller.remittanceType != 4}">
										<c:set var="tdHide" value="hide"/>
									</c:if>
									
									<td class="label remittanceDayTd ${tdHide}">정산일</td> <!-- 정산일 --> 
									<td class="remittanceDayTd ${tdHide}">
										<div>
											<form:select path="remittanceDay">
												<form:option value="">선택하세요</form:option>
												<form:option value="1">1일</form:option>
												<form:option value="5">5일</form:option>
												<form:option value="10">10일</form:option>
												<form:option value="15">15일</form:option>
												<form:option value="20">20일</form:option>
												<form:option value="25">25일</form:option>
											</form:select>
										</div>
									</td>
						        </tr>
						        <tr>
									<td class="label">${op:message('M01655')}<span class="require">*</span></td> <!-- 정산- 입급은행명 --> 
									<td>
										<div>
											<form:input type="text" path="bankName" title="${op:message('M01655')}" class=" required" maxlength="20"/>
										</div>
									</td>
									<td class="label">${op:message('M01663')}<span class="require">*</span></td> <!-- 정산- 입급계좌번호 --> 
									<td>
										<div>
											<form:input type="text" path="bankInName" title="${op:message('M01663')}" class=" required" maxlength="10"/>
										</div>
									</td>
						        </tr>
						        <tr>
									<td class="label">${op:message('M01656')}<span class="require">*</span></td> <!-- 정산- 입급계좌번호 --> 
									<td colspan="3">
										<div>
											<p class="text-info text-sm">* '-' 을 제외한 계좌번호를 입력해주세요.</p>
											<form:input type="text" path="bankAccountNumber" title="${op:message('M01656')}" class=" _number required" maxlength="20"/>
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
				
				<div class="item_list mt30">
					<h3><span>판매자 배송비 설정</span></h3> <!-- 조건부배송비 --> 
					<div class="board_write">
						<table class="board_write_table">
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
											<form:input type="text" path="shipping" title="배송비" class=" ${shipping_required} amount _number_comma shipping _min_0" /> 원
										</div>
									</td>
									<td class="label">무료배송 조건별 금액<span class="require">*</span></td> <!-- 판매자 조건부 배송비 무료배송 금액 --> 
									<td>
										<div>
											<form:input type="text" path="shippingFreeAmount" title="조건별 무료배송 금액" class=" ${shipping_required} amount _number_comma shipping _min_0" /> 원 이상 무료
										</div>
									</td>
						        </tr>
						        <tr class="shipping_tr ${shipping_hide}">
									<td class="label">${op:message('M01660')}<span class="require">*</span></td> <!-- 추가배송비 - 제주도 --> 
									<td>
										<div>
											<form:input type="text" path="shippingExtraCharge1" title="${op:message('M01658')}" class=" ${shipping_required} amount _number_comma shipping _min_0" /> 원
										</div>
									</td>
									<td class="label">${op:message('M01661')}<span class="require">*</span></td> <!-- 추가배송비 - 도서산간 --> 
									<td>
										<div>
											<form:input type="text" path="shippingExtraCharge2" title="${op:message('M01659')}" class=" ${shipping_required} amount _number_comma shipping _min_0" /> 원
										</div>
									</td>
						        </tr>
							</tbody>
						</table>
					</div>
					
					<div class="text-info" style="margin-top: 10px">
						* 판매자 배송비를 설정한 경우 설정 값이 상품 등록 시 배송시 설정 항목 중 <strong>판매자조건부</strong>에 설정됩니다. <br />
						* <strong>판매자조건부</strong>로 설정된 상품을 주문하는 경우 해당 설정 금액 기준으로 묶음 배송비가 부과됩니다. 
					
					</div>
				</div>
				
				<div class="item_list mt30">
					<h3><span>관리항목</span></h3> 
					<div class="board_write">
						<table class="board_write_table">
							<colgroup>
								<col style="width:170px;" />
								<col style="width:auto;" />
								<col style="width:170px;" />
								<col style="width:auto;" />
							</colgroup>
							<tbody>
						        <tr>
									<td class="label">${op:message('M01636')}</td> <!-- 영업상태 --> 
									<td>
										<div>
											<form:radiobutton path="statusCode" title="${op:message('M01658')}" class="required" value="1" label="영업진행중" checked="checked"/>
											<form:radiobutton path="statusCode" title="${op:message('M01658')}" class="required" value="2" label="정상"/>
											<form:radiobutton path="statusCode" title="${op:message('M01658')}" class="required" value="3" label="영업중지" />
											<form:radiobutton path="statusCode" title="${op:message('M01658')}" class="required" value="4" label="계약해지 / 폐점" />
										</div>
									</td>
									<td class="label">상품 승인 설정</td> 
									<td>
										<div>
											<form:radiobutton path="itemApprovalType" class="required" value="1" label="운영자 승인" checked="checked"/>
											<form:radiobutton path="itemApprovalType" class="required" value="2" label="판매자 등록/수정 시 자동 승인"/>
										</div>
									</td>
						        </tr>
						        <tr>
									<td class="label">담당MD<span class="require">*</span></td> <!-- 담당MD --> 
									<td colspan="3">
										<div>
											<input type="hidden" name="currentMdId" value="${seller.mdId}" />
											<input type="hidden" id="mdId" name="mdId" value="${seller.mdId}" />
											<form:input path="mdName" title="담당MD" readonly="true" />

											<button type="button" onclick="findMd('mdId')" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> MD검색</button>
											<button type="button" onclick="clearMd('mdId')" class="btn btn-gradient btn-sm"><span class="glyphicon glyphicon-remove"></span> 초기화</button>
										</div>
									</td>	
								</tr>
							</tbody>					 
						</table>								 							
					</div> <!-- // board_write -->
				</div>
				<div class="btn_center">
					<c:if test="${mode == 'create'}">
						<button type="submit" class="btn btn-active"><span>${op:message('M00101')}</span></button> <!-- 저장 -->		
					</c:if>	
					<c:if test="${mode == 'edit'}">
						
						<button type="submit" class="btn btn-active"><span>${op:message('M00087')}</span></button> <!-- 수정 --> 		
						<c:if test="${seller.statusCode == 1}">
						<a href="javascript:;" onClick="fn_delete()" class="btn btn-active">${op:message('M00074')}</a>&nbsp;	 
						</c:if>					
					</c:if>	
					
					<a href="/opmanager/seller/list" class="btn btn-default"><span>${op:message('M00037')}</span></a> <!-- 취소 --> 
				</div>
			</form:form>
			
<form id="deleteForm" method="post" action="/opmanager/seller/delete">
	<input type="hidden" name="sellerId" value="${seller.sellerId}" />
</form>

<!-- 다음 주소검색 -->
<daum:address />
<script type="text/javascript">

$(function() {
	
	Common.addNumberComma();
	
	// 주소복사
	$('.op-copy-address').on('click', function() {
		$('#businessLocation').val($('#address').val() + ' ' + $('#addressDetail').val());
	});
	
	
	// validator
	try{
		$('#seller').validator(function() {
			
			var loginId = $("#loginId").val();
			 var params = {
				'loginId' : loginId
			};
			 
			if($('#mode').val() == 'create'){
				var idDupple = false;
				$.post("/opmanager/seller/id-duplicate-Check",params,function(response){
					Common.loading.hide();
					if (response.isSuccess) {
						$("#idCheck").val('1');
					} else {
						alert("${op:message('M01588')}");
						$("#loginId").focus();
						idDupple = true;
					}

				});
				
				if(idDupple){
					return false;
				}
			}
			if($("#password_confirm").val() != ''){
				if ($("#password").val() != $("#password_confirm").val()) {
					alert("${op:message('M00158')}");
					$("#password_confirm").fucous();
					return false;
				}
			}
			
			if($('#remittanceType').val() == 4) {
				if($('#remittanceDay').val() == ''){
					alert("정산일 선택해주세요. ");
					$('#remittanceDay').fucous();
					return false;
				}
			}
			
			Common.removeNumberComma(); 
		});
	} catch(e) {
		alert(e.message);
	}
	
	$('.emailSel').on("change", function(){
		$('input[name=email2]').val($(this).val());
	});
	
	$('#loginId').on('change', function(){
		$('#dupl_message').text(IdAvailabilityCheck());
		
	});
	
	$('#remittanceType').on('change', function(){
		if($(this).val()==4){
			$('.remittanceDayTd').removeClass('hide');
			$('#remittanceDay').attr('name', 'remittanceDay');
		}else{
			$('.remittanceDayTd').addClass('hide');
			$('#remittanceDay').attr('name', '');
		}
		
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
	$('#password').on('change', function(){
		if($('#mode').val() == 'edit'){
			if($('#password').val() != ''){
				$('#password').addClass('required');
				$('#password_confirm').addClass('required');
			}else{
				$('#password').removeClass('required');
				$('#password_confirm').removeClass('required');
			}
		}
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

function IdAvailabilityCheck() {
	
	var loginId = $("#loginId").val();
	var params = {
		'loginId' 	: loginId
	};
	var returnMessage = "";
	$.post("/opmanager/seller/id-duplicate-Check", params, function(response){
		Common.loading.hide();
		//Common.responseHandler(response, function() {
			if (response.isSuccess) {
				$("#idCheck").val('1');
				returnMessage = "${op:message('M00161')}";
				loginCheckId = $("#loginId").val();
			} else {
				returnMessage = "${op:message('M01588')}";
				$("#loginId").focus();
			}
		//});
	});
	
	return returnMessage;
}

function fn_delete(){
	 if(confirm("판매자계정을 삭제하시겠습니까?")){
		 $("#deleteForm").submit();
	 }
}

function findMd(targetId) {
	Common.popup('/opmanager/seller/find-md?targetId=' + targetId, 'find_md', 720, 800, 1);
}

function clearMd(targetId) {
	var $target = $('#' + targetId);
	$target.val('');
	$target.closest('td').find('#mdName').val('');
}

// MD 검색 콜백 
function handleFindMdCallback(response) {
	var $target = $('#' + response.targetId);
	$target.val(response.userId);
	$target.closest('td').find('#mdName').val(response.userName);

}
</script>
