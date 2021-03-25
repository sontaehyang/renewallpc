<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<style>
#blog-landing {
margin-top: 10px;
position: relative;
max-width: 100%;
width: 100%;
}
#blog-landing > div {
	position: absolute;
}
.section {
	border: 1px solid #ccc;

}

.menu1 {
	font-weight: bold;
	font-size: 14px;
	padding: 10px;
	background: #f4f4f4;
	color: #000;
}
.menu2 {
	display: block;
	font-weight: bold;
	font-size: 13px;
	padding: 2px 10px 2px 15px;
	
	color: #000;
	border-top: 1px solid #eee;

}
</style>

<div class="location">
	<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
</div>

<!-- 본문 -->
<div class="item_list">		
	<h3>쿠폰등록</h3> <!-- 쿠폰신규등록 -->
	<form:form modelAttribute="coupon" method="post" enctype="multipart/form-data">
		<form:hidden path="dataStatusCode" />
		
		<form:hidden path="couponId" />
		<div class="board_write">				
			<table class="board_write_table" summary="${op:message('M01155')}">
				<caption>${op:message('M01155')}</caption>
				<colgroup>
					<col style="width: 170px;" />
					<col style="width: auto;" /> 
				</colgroup>
				<tbody>
					<tr>
						<td class="label">적용 채널</td> 
						<td>
							<div>
								<form:checkbox path="selectCouponTypes" label="PC" class="required" value="WEB" title="적용 채널" />
								<form:checkbox path="selectCouponTypes" label="모바일" class="required" value="MOBILE" title="적용 채널" />
								<form:checkbox path="selectCouponTypes" label="앱" class="required" value="APP" title="적용 채널" />
							</div>
						</td>
					</tr>
					<tr>
						<td class="label">고객 쿠폰명</td> <!-- 쿠폰명 --> 
						<td>
							<div>
								<form:input path="couponName" class="required half" title="${op:message('M00879')}" maxlength="100" />
							</div>
						</td>
					</tr>
					<tr> 
						<td class="label">${op:message('M01156')}</td> <!-- 쿠폰설명 --> 
						<td>
							<div>
								<form:input path="couponComment" class="nine" title="${op:message('M01156')}" maxlength="500" />
							</div>
						</td>
					</tr>

					<tr>
						<td class="label">오프라인 쿠폰 여부</td>
						<td>
							<div>
								<p class="text-info text-sm">* 오프라인 쿠폰 발행 시 '총 발급 수량'에 입력한 수 만큼 발행됩니다.</p>
								<p class="text-info text-sm">* 오프라인 쿠폰 발행 시 '쿠폰종류'는 '일반'으로 '복수 다운로드 가능여부'는 '불가능'으로 고정됩니다.</p>
								<form:radiobutton path="couponOfflineFlag" label="미발행" value="N" class="required" title="오프라인 쿠폰 여부" />
								<form:radiobutton path="couponOfflineFlag" label="발행" value="Y" class="required" title="오프라인 쿠폰 여부" />
							</div>
						</td>
					</tr>
					<tr>
						<td class="label">쿠폰 코드 직접 입력</td>
						<td>
							<div>
								<p class="text-info text-sm">* 직접 입력 쿠폰 발행 시 '총 발급 수량'에 입력한 수 만큼 발행됩니다.</p>
								<p class="text-info text-sm">* 직접 입력 쿠폰 발행 시 '쿠폰종류'는 '일반'으로 '복수 다운로드 가능여부'는 '불가능'으로 고정됩니다.</p>
								<form:radiobutton path="directInputFlag" label="미발행" value="N" class="required" title="직접 입력 쿠폰 여부" />
								<form:radiobutton path="directInputFlag" label="발행" value="Y" class="required" title="직접 입력 쿠폰 여부" />
							</div>
							<div>
								<p class="text-info text-sm">* 코드는 대문자(A~Z) 및 숫자(0~9) 여야 합니다.</p>
								<form:input path="directInputValue" class="nine" title="직접 입력 쿠폰 번호" maxlength="100" disabled="${directInputFlag != 'Y'}"/>
							</div>
						</td>
					</tr>
					<tr>
						<td class="label">쿠폰종류</td>
						<td>
							<div>
								<p class="text-info text-sm">* 상품 구매 및 첫구매 쿠폰은 구매확정시 발행됩니다.</p>
								<form:radiobutton path="couponTargetTimeType" label="일반" value="1" class="required" title="쿠폰종류" /><!-- 다운클릭 -->
								<form:radiobutton path="couponTargetTimeType" label="생일" value="3" class="required" title="쿠폰종류" /><!-- 다운클릭 -->
								<form:radiobutton path="couponTargetTimeType" label="회원가입" value="2" class="required" title="쿠폰종류" /><!-- 자동다운 -->
								<form:radiobutton path="couponTargetTimeType" label="상품 구매 후 발행" value="4" class="required" title="쿠폰종류" /><!-- 자동다운 -->
								<form:radiobutton path="couponTargetTimeType" label="첫구매" value="5" class="required" title="쿠폰종류" /><!-- 자동다운 -->
							</div>
						</td>
					</tr>

					<tr class="op-coupon-target-time-3-view op-coupon-target-time-view" ${coupon.couponTargetTimeType != '3' ? 'style="display:none;"' : ''}>
						<td class="label">생일 전 후 <span class="require">*</span></td>
						<td>
							<div>
								<p class="text-info text-sm">* 생일 전 후 입력일 동안 회원의 쿠폰다운로드 가능 목록에 보여집니다.</p>
								<input type="text" name="couponBirthday" title="생일 전 후 다운로드 기간" value="${op:negativeNumberToEmpty(coupon.couponBirthday)}" class="_number" maxlength="2" /> 일
							</div>
						</td>
					</tr>

					<tr>
						<td class="label">다운로드 가능기간</td>
						<td>
							<div>
								<p>
									<form:radiobutton path="couponIssueType" value="0" label="${op:message('M00497')}" title="다운로드 가능 기간설정" />
								</p>
								<p class="issue">
									<form:radiobutton path="couponIssueType" value="1" title="다운로드 가능 기간설정" label="시작, 종료일 설정" /> &nbsp;
									<span class="datepicker">
										<form:input path="couponIssueStartDate" class="two datepicker optional _number" maxlength="8" title="다운로드 가능기간 시작일" /> <!-- 달력 선택 -->
									</span>
									~
									<span class="datepicker">
										<form:input path="couponIssueEndDate" class="two datepicker optional _number" maxlength="8" title="다운로드 가능기간 종료일" /> <!-- 달력 선택 -->
									</span>
								</p>
							</div>
						</td>
					</tr>
					<tr>
						<td class="label">사용 가능 기간</td> <!-- 쿠폰 적용 기간 -->
						<td>
							<div>
								<p class="mb5">
									<form:radiobutton path="couponApplyType" value="0" label="${op:message('M00497')}" title="사용 가능 기간설정" />
								</p>
								<p class="mb5">
									<form:radiobutton path="couponApplyType" value="1" label="시작, 종료일 선택" title="사용 가능 기간설정" /> &nbsp;
									<span class="datepicker">
										<form:input path="couponApplyStartDate" class="two datepicker optional _number" maxlength="8" title="사용 가능 기간 시작일" /><!-- 달력 선택 -->
									</span>
									~
									<span class="datepicker">
										<form:input path="couponApplyEndDate" class="two datepicker optional _number" maxlength="8" title="사용 가능 기간 종료일" /><!-- 달력 선택 -->
									</span>
								</p>

								<p class="mb5">
									<form:radiobutton path="couponApplyType" value="2" label="고객 다운로드 시점으로 부터 " title="사용 가능 기간설정" /> &nbsp;
									<form:input path="couponApplyDay" class="one _number text-right" title="유효 기간 설정" maxlength="4" />일 후 까지 사용가능
								</p>
							</div>
						</td>
					</tr>

					<tr>
						<td class="label">총 발급 수량</td>
						<td>
							<div>
								<p class="text-info text-sm">* 공란으로 두면 다운로드 가능기간에는 무제한 다운로드가 가능합니다.</p>
								<input type="text" name="couponDownloadLimit" title="총 발급 수량" value="${op:negativeNumberToEmpty(coupon.couponDownloadLimit)}" class="_number" maxlength="9" />장
							</div>
						</td>
					</tr>

					<tr>
						<td class="label">회원별<br/>다운로드 가능 수량</td>
						<td>
							<div>
								<p class="text-info text-sm">* 공란으로 두면 다운로드 가능기간에는 무제한 다운로드가 가능합니다.</p>
								<input type="text" name="couponDownloadUserLimit" value="${op:negativeNumberToEmpty(coupon.couponDownloadUserLimit)}" class="_number" maxlength="9" />장
							</div>
						</td>
					</tr>

					<tr>
						<td class="label">복수 다운로드 가능여부</td>
						<td>
							<div>
								<p class="text-info text-sm">* 복수 다운로드 가능여부를 "가능" 상태로 설정할 경우 <strong>쿠폰을 사용하지 않아도</strong> 회원별로 사용하지 않은 쿠폰을 여러장 다운로드 받을수 있습니다.</p>
								<p class="text-info text-sm">* 복수 다운로드 가능여부를 "불가능" 상태로 설정할 경우 <strong>쿠폰을 사용 한후</strong>에 다운로드가 가능합니다.</p>
								<form:radiobutton path="couponMulitpleDownloadFlag" label="가능" value="Y" class="required" title="복수 다운로드 가능여부" />
								<form:radiobutton path="couponMulitpleDownloadFlag" label="불가능" value="N" class="required" title="복수 다운로드 가능여부" />
							</div>
						</td>
					</tr>

					<tr>
						<td class="label">발급대상[회원]</td>
						<td>
							<div>
								<form:radiobutton path="couponTargetUserType" label="전체 회원" value="1" class="required" title="발급대상[회원]" />
								<form:radiobutton path="couponTargetUserType" label="회원 선택" value="2" class="required" title="발급대상[회원]" disabled="${coupon.couponTargetTimeType == '3' ? 'true' : 'false'}" />
								<form:radiobutton path="couponTargetUserType" label="회원 등급별" value="3" class="required" title="발급대상[회원]" />
							</div>
						</td>
					</tr>
					
					<tr class="op-coupon-target-user-2-view op-coupon-target-user-view" ${coupon.couponTargetUserType != '2' ? 'style="display:none;"' : ''}>
						<td class="label">회원 선택</td>
						<td>
							<div>
								<p>
									선택한 조건 (회원을 중복 선택해도 회원 1명당 쿠폰 1장씩 발행됩니다.)
									<button type="button" class="table_btn" onclick="findUser()"><span>${op:message('M01162')}</span></button> <!-- 회원추가 -->
									<button type="button" class="table_btn" onclick="targetUserPreview()"><span>발급대상 미리보기</span></button>
								</p>

								<div class="category_list03">
									<div class="bundle_two">
										<ul id="op-chosen-user-list">
											<c:if test="${not empty coupon.couponTargetUsers}">
												<c:forEach items="${coupon.couponTargetUsers}" var="target" varStatus="i">
													<li class="click op-chose-user" id="op-chose-user-${i.index}" data-index="${i.index}">
														${target.title}
														<c:choose>
															<c:when test="${target.addType == 'selected'}">
																<input type="hidden" name="couponTargetUsers[${i.index}].addType" value="selected" />
																<input type="hidden" name="couponTargetUsers[${i.index}].title" value="${target.title}" />
																<c:forEach items="${target.userIds}" var="id">
																	<input type="hidden" name="couponTargetUsers[${i.index}].userIds" value="${id}" />
																</c:forEach>
															</c:when>
															<c:otherwise>
																<input type="hidden" name="couponTargetUsers[${i.index}].addType" value="search" />
																<input type="hidden" name="couponTargetUsers[${i.index}].title" value="${target.title}" />
																
																<input type="hidden" name="couponTargetUsers[${i.index}].loginId" value="${target.loginId}" />
																<input type="hidden" name="couponTargetUsers[${i.index}].userName" value="${target.userName}" />
																<input type="hidden" name="couponTargetUsers[${i.index}].email" value="${target.email}" />
																<input type="hidden" name="couponTargetUsers[${i.index}].searchReceiveEmail" value="${target.searchReceiveEmail}" />
																<input type="hidden" name="couponTargetUsers[${i.index}].searchReceiveSms" value="${target.searchReceiveSms}" />
															</c:otherwise>
														</c:choose>
														&nbsp;<a href="javascript:;" class="fix_btn" style="right: 0;padding:2px 10px;" onclick="chosenUserDelete('op-chose-user-${i.index}')">${op:message('M00074')}</a>
													</li>
												</c:forEach>
											</c:if>
										</ul>							
									</div> <!-- // bundle_two -->							
								</div> <!--// category_list03 -->
							</div>
						</td>
					</tr>
					
					<tr class="op-coupon-target-user-3-view op-coupon-target-user-view" ${coupon.couponTargetUserType != '3' ? 'style="display:none;"' : ''}>
						<td class="label">회원 등급</td>
						<td>
							<div style="padding: 15px 10 15px 15px">
								<p class="text-info text-sm">* 다운로드 시점에 회원 등급을 기준으로 합니다.</p>
								<p><label><input type="checkbox" class="check-all" /> 전체선택</label></p>								
								<div id="blog-landing" class="input_wrap col-w-7">

									<c:forEach items="${userLevelGroup}" var="levelGroup">
										<c:set var="groupLabel">${levelGroup.key}</c:set>
										<c:forEach items="${groupList}" var="group">
											<c:if test="${groupLabel == group.groupCode}">
												<c:set var="groupLabel">${group.groupName}</c:set>
											</c:if>
										</c:forEach>
										 
										<div class="section">
		 									<div class="menu1">
		 										<label><input type="checkbox" class="check-all" /> ${groupLabel}</label>
		 									</div>	
		 									<ul>
			 									<c:forEach items="${levelGroup.value}" var="userLevel">
			 										<li>
			 											<span class="menu2">
			 												<label><input type="checkbox" name="selectCouponTargetUserLevels" title="회원 등급"
																		  value="${userLevel.levelId}"
																		${op:checkedArray(coupon.selectCouponTargetUserLevelArray, userLevel.levelId)} /> ${userLevel.levelName}</label>
			 											</span>
			 										</li>
			 									</c:forEach>
		 									</ul>
		 								</div>
									</c:forEach>
									
								</div>
								
							</div>
							
						</td>
					</tr>
					
					<tr>
						<td class="label">쿠폰 사용가능 <br/>상품 판매가 (개당)</td>
						<td>
							<div>
								<p>
									<p class="text-info text-sm">* 공란으로 두면 상품금액에 상관없이 사용이 가능합니다.</p>
									<input type="text" name="couponPayRestriction" value="${op:negativeNumberToEmpty(coupon.couponPayRestriction)}" class="_number" maxlength="9" />
									원 이상 상품에만 사용가능
								</p>
							</div>
						</td>
					</tr>
					<tr>
						<td class="label">할인 금액 설정 (개당)</td> 
						<td>
							<div>
								<p>
									<p class="text-info text-sm">* %할인의 기준금액은 상품에 설정된 <strong>실판매가(할인 적용가) + 옵션 판매가</strong> 기준 입니다.</p>
									<form:input path="couponPay" class="count_down2 required _number" maxlength="9" title="할인 금액 설정 (개당)" /> <!-- 금액 --> 
									<form:select path="couponPayType">
										<form:option value="1">${op:message('M00049')}</form:option> <!-- 원 -->
										<form:option value="2">%</form:option>
									</form:select>
								</p>
							</div>
						</td>
					</tr>
					<tr>
						<td class="label">최대 할인금액(%)</td>
						<td>
							<div>
								<p>
									<p class="text-info text-sm">
										* "해택"을 %로 지정후 발행된 경우 해당 입력값이 사용됩니다.<br />
										ex) 판매가 50,000원인 상품에 10% 할인 쿠폰을 사용할경우 상한액을 2,500원으로 입력하시면 5,000원이 할인되는 것이 아니라 2,500원만 할인됩니다. 
										<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;단, 하단 <strong>"중복 할인여부"</strong> 설정을 <strong>"구매 수량만큼 할인"</strong> 할경우 <strong>최대 할인금액[%] X 구매 수량</strong> 만큼 할인됩니다.
									</p> 
									<input type="text" name="couponDiscountLimitPrice" value="${op:negativeNumberToEmpty(coupon.couponDiscountLimitPrice)}" class="_number" maxlength="9" /> 원
								</p>
							</div>
						</td>
					</tr>
					<tr>
						<td class="label">중복 할인여부</td>
						<td> 
							<div>
								<p class="text-info text-sm">* "구매 수량만큼 할인"의 경우 구매고객이 1,000원 할인쿠폰을 사용하는 상품의 구매수량이 2개인경우 2,000원이 할인 됩니다.</p>
								<form:radiobutton path="couponConcurrently" value="1" label="1개의 수량만 할인" title="중복 할인여부" class="required" />
								<form:radiobutton path="couponConcurrently" value="2" label="구매 수량만큼 할인" title="중복 할인여부" class="required" />
							</div>
						</td>
					</tr>
					
					<tr>
						<td class="label">발급대상[상품]</td>
						<td>
							<div>
								<form:radiobutton path="couponTargetItemType" label="전체 상품" value="1" title="발급대상[상품]" class="required" /> <!-- 전체상품에 발급합니다. -->
								<form:radiobutton path="couponTargetItemType" label="상품 선택" value="2" title="발급대상[상품]" class="required" /> <!-- 특정 상품에 발급합니다. -->
							</div>
						</td>
					</tr>
					
					<tr class="op-coupon-target-item-2-view op-coupon-target-item-view" ${coupon.couponTargetItemType != '2' ? 'style="display:none;"' : ''}>
						<td class="label">상품 선택</td>
						<td>
							<div>
								<p>
									선택한 조건
									<button type="button" class="table_btn" onclick="findItem()"><span>상품추가</span></button>
									<button type="button" class="table_btn" onclick="targetItemPreview()"><span>발급대상 미리보기</span></button>
									<button type="button" class="btn btn-success btn-sm" onclick="uploadExcel();"><span class="glyphicon glyphicon-open"></span> 엑셀 업로드</button>
								</p>

								<div class="category_list03">
									<div class="bundle_two">
										<ul id="op-chosen-item-list">
											<c:if test="${not empty coupon.couponTargetItems}">
												<c:forEach items="${coupon.couponTargetItems}" var="target" varStatus="i">
													<li class="click op-chose-item" id="op-chose-item-${i.index}" data-index="${i.index}">
														${target.title}
														
														<c:choose>
															<c:when test="${target.addType == 'selected'}">
																<input type="hidden" name="couponTargetItems[${i.index}].addType" value="selected" />
																<input type="hidden" name="couponTargetItems[${i.index}].title" value="${target.title}" />
																<c:forEach items="${target.itemIds}" var="id">
																	<input type="hidden" name="couponTargetItems[${i.index}].itemIds" value="${id}" />
																</c:forEach>
															</c:when>
															<c:otherwise>
																<input type="hidden" name="couponTargetItems[${i.index}].addType" value="search" />
																<input type="hidden" name="couponTargetItems[${i.index}].title" value="${target.title}" />
																
																<input type="hidden" name="couponTargetItems[${i.index}].categoryGroupId" value="${target.categoryGroupId}" />
																<input type="hidden" name="couponTargetItems[${i.index}].categoryClass1" value="${target.categoryClass1}" />
																<input type="hidden" name="couponTargetItems[${i.index}].categoryClass2" value="${target.categoryClass2}" />
																<input type="hidden" name="couponTargetItems[${i.index}].categoryClass3" value="${target.categoryClass3}" />
																<input type="hidden" name="couponTargetItems[${i.index}].categoryClass4" value="${target.categoryClass4}" />
																<input type="hidden" name="couponTargetItems[${i.index}].where" value="${target.where}" />
																<input type="hidden" name="couponTargetItems[${i.index}].query" value="${target.query}" />
															</c:otherwise>
														</c:choose>
														
														&nbsp;<a href="javascript:;" class="fix_btn" style="right: 0;padding:2px 10px;" onclick="chosenItemDelete('op-chose-item-${i.index}')">${op:message('M00074')}</a>
													</li>
												</c:forEach>
											</c:if>
										</ul>							
									</div> <!-- // bundle_two -->							
								</div> <!--// category_list03 -->
							</div>
						</td>
					</tr>
					
					<tr> 
						<td class="label">쿠폰 다운로드 가능여부</td> <!-- 사용상태 --> 
						<td>
							<div>
								<p class="text-info text-sm">* "불가능" 으로 설정시 다운로드가 불가능 합니다. 단, 이미 다운로드된 쿠폰은 사용 가능합니다.</p>
								<form:radiobutton path="couponFlag" value="Y" label="가능" title="쿠폰 다운로드 가능여부" class="required" />
								<form:radiobutton path="couponFlag" value="N" label="불가능" title="쿠폰 다운로드 가능여부" class="required" />
							</div>
						</td>
					</tr>
					
				</tbody>
			</table>
		</div> <!-- // board_write -->
	
		<div class="btn_center">
			<button type="submit" class="btn btn-active">임시저장</button>
			<a href="${requestContext.prevPageUrl}" class="btn btn-default">${op:message('M00037')}</a> <!-- 취소 -->
		</div>
	
	</form:form>

	<form id="targetUserPreviewForm" action="/opmanager/coupon/target-user-preview" target="targetUserPreview" method="get"></form>
	<form id="targetItemPreviewForm" action="/opmanager/coupon/target-item-preview" target="targetItemPreview" method="get"></form>

</div> <!-- // item_list01 -->

<script type="text/javascript" src="/content/modules/pinterest_grid.js"></script>
<script type="text/javascript">
$("document").ready(function() {
		
	//쿠폰종류 회원가입or상품구매후발행or첫구매인 경우 -> 쿠폰 다운로드 가능여부N 셋팅 [2017-09-15]minae.yun
	//(신규회원 쿠폰은 다운버튼 클릭이 아니라 가입시 자동 발행, 첫구매 쿠폰은 첫구매시 자동 발행, 상품구매후발행은 구매확정시 발행)
	$('input:radio[name=couponTargetTimeType]').change(function(){
	var targetTime = $('input:radio[name=couponTargetTimeType]:checked').val();

	$("input:radio[id=couponTargetUserType2]").attr("disabled", false);

		if (targetTime == "2" || targetTime == "4" || targetTime == "5") {
			$("input:radio[id=couponFlag2]").prop("checked", true);
			$("input:radio[name=couponFlag]").attr("disabled", true);
			///다운로드 가능기간 숨김
			$(".issue").hide();
			$("input:radio[id=couponIssueType1]").prop("checked", true);
		} else {
			$("input:radio[name=couponFlag]").attr("disabled", false);
			$("input:radio[id=couponFlag1]").prop("checked", true);
			$(".issue").show();

			// 생일 쿠폰일 경우 회원 선택 disabled
			if (targetTime == "3") {
				$("input:radio[id=couponTargetUserType2]").attr("disabled", true);
				$('.op-coupon-target-user-view').hide();

				if ($('input[name="couponTargetUserType"]:checked').val() == "2") {
					$('#couponTargetUserType1').prop('checked', true);
				}
			}
		}
		
		if (targetTime != "3") {
			$('input[name="couponBirthday"]').removeClass('required');
		} else {
			$('input[name="couponBirthday"]').addClass('required');
		}
	});
	
	var targetTime = $('input:radio[name=couponTargetTimeType]:checked').val();
	if (targetTime == "2" || targetTime == "4" || targetTime == "5") {
		$("input:radio[id=couponFlag2]").prop("checked", true);
		$("input:radio[name=couponFlag]").attr("disabled", true);
		///다운로드 가능기간 숨김
		$(".issue").hide();
		$("input:radio[id=couponIssueType1]").prop("checked", true);
	}
	
	//오프라인 쿠폰 발행이면 쿠폰종류 일반만 선택, 복수 다운로드 불가능 선택, 발급수량 입력 필수[2017-09-26]minae.yun
	$('input:radio[name=couponOfflineFlag]').on('change',function(){
		var offlineFlag = $('input:radio[name=couponOfflineFlag]:checked').val();
		var flag = offlineFlag == "Y";

		if (flag) {
			$("input:radio[id=directInputFlag1]").prop("checked", true);
			$('input[name=directInputValue]').val('');
			$('input[name=directInputValue]').prop('disabled', true);
		}

		displayEvent(flag);
	});

	$('input:radio[name=directInputFlag]').on('change',function(){
		var directInputFlag = $('input:radio[name=directInputFlag]:checked').val();
		var flag = "Y" == directInputFlag;

		if (flag) {
			$("input:radio[id=couponOfflineFlag1]").prop("checked", true);
			$('input[name=directInputValue]').prop('disabled', false);
		} else {
			$('input[name=directInputValue]').val('');
			$('input[name=directInputValue]').prop('disabled', true);
		}

		displayEvent(flag);
	});

	var offlineFlag = $('input:radio[name=couponOfflineFlag]:checked').val();
	var directInputFlag = $('input:radio[name=directInputFlag]:checked').val();
	if (offlineFlag == "Y" || directInputFlag == "Y") {

		$("[name=couponTargetTimeType]:not(:checked)").attr('disabled', 'disabled');
		$("[name=couponMulitpleDownloadFlag]:not(:checked)").attr('disabled', 'disabled');
		$('input[name="couponDownloadLimit"]').addClass('required');
		$("input:radio[name=couponFlag]").attr("disabled", false);

		if (offlineFlag == "Y") {

			$("input:radio[id=directInputFlag1]").prop("checked", true);

		} else if (directInputFlag == "Y") {

			$("input:radio[id=couponOfflineFlag1]").prop("checked", true);
			$('input[name=directInputValue]').prop('disabled', false);

		}
	}

	function displayEvent(flag) {
		if (flag) {
			$("input:radio[id=couponTargetTimeType1]").prop("checked", true);
			$("[name=couponTargetTimeType]:not(:checked)").attr('disabled', 'disabled');
			$("input:radio[id=couponMulitpleDownloadFlag2]").prop("checked", true);
			$("[name=couponMulitpleDownloadFlag]:not(:checked)").attr('disabled', 'disabled');
			$('input[name="couponDownloadLimit"]').addClass('required');

			$("input:radio[name=couponFlag]").attr("disabled", false);
			$("input:radio[id=couponFlag1]").prop("checked", true);
			$(".issue").show();

			// 생일체크 후 오프라인 쿠폰 발행체크시 필수에서 빠지도록 수정
			$('input[name="couponBirthday"]').removeClass('required');
			$('input[name="couponBirthday"]').val('');

		} else {
			$("input:radio[name=couponTargetTimeType]").attr("disabled", false);
			$("input:radio[name=couponMulitpleDownloadFlag]").attr("disabled", false);
			$('input[name="couponDownloadLimit"]').removeClass('required');
		}

		$('.op-coupon-target-time-view').hide();
		$('.op-coupon-target-time-'+ $('input[name="couponTargetTimeType"]:checked').val() +'-view').show();

	}

	$('#directInputValue').on('input keyup paste', function(){

		var value = $(this).val();
		var test = /^[\-|0-9a-zA-Z]+$/.test(value);

		if (!test) {
			value = value.substr(0, value.length-1);

		}
		$(this).val(value.toUpperCase())
	});
	
});

$(function() {
	// 필수 입력항목 마커.
	Common.displayRequireMark();
	
	initCouponApplyType();
	initCouponTargetUserType();
	initCouponIssueType();
	
	$('#blog-landing').pinterest_grid({
		no_columns: 4,
		padding_x: 10,
		padding_y: 10,
		margin_bottom: 50,
		single_column_breakpoint: 700
	});
	
	$('.check-all').on('click', function(e) {
		var isChecked = $(this).prop('checked');
		var $target = $(this).parent().parent().parent().find('input[type=checkbox]');
		$target.prop('checked', isChecked);
	});

	if ($('input[name="couponTargetTimeType"]:checked').val() != "3") {
		$('input[name="couponBirthday"]').removeClass('required');
	} else {
		$('input[name="couponBirthday"]').addClass('required');
	}
	
	$('input[name="couponTargetTimeType"]').on('click', function(){
		$('.op-coupon-target-time-view').hide();
		$('.op-coupon-target-time-'+ $(this).val() +'-view').show();
		
		//$('input[name="couponBirthday"]').addClass('required');
		//$('input[name="selectCouponTargetUserLevels"]').removeClass('required');
	});
	
	$('input[name="couponTargetUserType"]').on('click', function(){
		$('.op-coupon-target-user-view').hide();
		$('.op-coupon-target-user-'+ $(this).val() +'-view').show();
		
		$('input[name="selectCouponTargetUserLevels"]').removeClass('required');
		
		initCouponTargetUserType();
	});
	
	$('input[name="couponTargetItemType"]').on('click', function(){
		$('.op-coupon-target-item-view').hide();
		$('.op-coupon-target-item-'+ $(this).val() +'-view').show();
	});
	
	$('input[name="couponIssueType"]').on('click', function(){
		$('input[name="couponIssueStartDate"], input[name="couponIssueEndDate"]').removeClass('required');
		
		initCouponIssueType();
	});
	 
	$('input[name="couponApplyType"]').on('click', function(){
		$('input[name="couponApplyStartDate"], input[name="couponApplyEndDate"], input[name="couponApplyDay"]').removeClass('required');
		
		initCouponApplyType();
	});
	
	$("#coupon").validator(function() {

		if($("input[name='couponIssueType']:checked").val() == '1'){
			if ($("#couponIssueStartDate").val() > $("#couponIssueEndDate").val()) {
				$("#couponIssueEndDate").focus();
				alert("${op:message('M01181')}"); //시작일을 종료일 이후로 입력하실수 없습니다. 
				return false;
			}
		}
		
		if( $("input[name='couponApplyType']:checked").val() == '1'){
			if ($("#couponApplyStartDate").val() > $("#couponApplyEndDate").val()) {
				$("#couponApplyEndDate").focus();
				alert("${op:message('M01181')}"); // 시작일을 종료일 이후로 입력하실수 없습니다.
				return false;
			}
		}
		
		if( $("input[name='couponApplyType']:checked").val() == '2'){
			if (Number($('input[name="couponApplyDay"]').val()) <= 0) {
				$('input[name="couponApplyDay"]').focus();
				alert('사용 가능기간을 0보다 큰값으로 설정해 주세요.');
				return false; 
			}
		}
		
		if ($('input[name="couponTargetUserType"]:checked').val() == "2") {
			if ($('ul#op-chosen-user-list > li').size() == 0) {
				alert('쿠폰을 발행하실 회원을 선택해 주세요.');
				return false;
			}
		}
		
		if ($('input[name="couponTargetItemType"]:checked').val() == "2") {
			if ($('ul#op-chosen-item-list > li').size() == 0) {
				alert('쿠폰을 발행하실 상품을 선택해 주세요.');
				return false;
			}
		}
		
		if ($('input[name="couponOfflineFlag"]:checked').val() == "Y") {
			if ($('#couponDownloadLimit') == null || $('#couponDownloadLimit') < 0) {
				alert('오프라인 쿠폰의 발급 수량을 입력해주세요.');
				return false;
			}
		}

		if ($('input[name="directInputFlag"]:checked').val() == "Y") {

			var directInputValue = $('#directInputValue').val();
			var directInputValueTest = /^[\-|0-9A-Z]+$/.test(directInputValue);
			if (directInputValue == '') {
				alert('직접 입력 쿠폰 정보를 입력해 주세요.');
				$('#directInputValue').focus();
				return false;
			}

			if(!directInputValueTest) {
				alert('쿠폰코드는 알파벳 대문자와 숫자만 가능합니다.');
				$('#directInputValue').focus();
				return false;
			}

			if (duplicateDirectInputValue()) {
				alert('직접입력 쿠폰 정보가 중복 입니다.');
				$('#directInputValue').focus();
				return false;
			}

			if ($('#couponDownloadLimit') == null || $('#couponDownloadLimit') < 0) {
				alert('직접입력 쿠폰의 발급 수량을 입력해주세요.');
				return false;
			}
		}
	});
});

function initCouponIssueType() {
	if ($('input[name="couponIssueType"]:checked').val() == "1") {
		$('input[name="couponIssueStartDate"], input[name="couponIssueEndDate"]').addClass('required');	
	}
}

function initCouponTargetUserType() {
	if ($('input[name="couponTargetUserType"]:checked').val() == "3") {
		$('input[name="selectCouponTargetUserLevels"]').addClass('required');
	}
}

function initCouponApplyType() {
	if ($('input[name="couponApplyType"]:checked').val() == "1") {
		$('input[name="couponApplyStartDate"], input[name="couponApplyEndDate"]').addClass('required');	
	} else if ($('input[name="couponApplyType"]:checked').val() == "2") {
		$('input[name="couponApplyDay"]').addClass('required');
	}
}

function findItem() {
	Common.popup("/opmanager/coupon/find-item", "find-item", 750, 600, 1);
}

function chosenItemDelete(id) {
	$('ul#op-chosen-item-list > #' + id).remove();
	$.each($('ul#op-chosen-item-list > li'), function(i) {
		$.each($(this).find('input'), function(j) {
			$(this).attr('name', $(this).attr('name').replace(/\[.*\]/gi, '['+i+']'));
		});
	});
}

function targetItemPreview() {
	
	if ($('ul#op-chosen-item-list > li').size() == 0) {
		alert('쿠폰을 발행하실 상품을 선택해 주세요.');
		return;
	}

    $('#targetItemPreviewForm .target-item-preview-input').remove();

	$.each($('ul#op-chosen-item-list > li').find('input'), function() {
        $('#targetItemPreviewForm').append($('<input type="hidden" />').attr({
            'class'		: 'target-item-preview-input',
			'name'		: $(this).attr('name'),
			'value'		: $(this).val()
		}));
	});

    Common.popup('about:blank', 'targetItemPreview', 900, 700, 1);
    $('#targetItemPreviewForm').submit();
}

function findUser() {
	Common.popup("/opmanager/coupon/find-user", "find-user", 750, 600, 1);
}

function chosenUserDelete(id) {
	$('ul#op-chosen-user-list > #' + id).remove();
	$.each($('ul#op-chosen-user-list > li'), function(i) {
		$.each($(this).find('input'), function(j) {
			$(this).attr('name', $(this).attr('name').replace(/\[.*\]/gi, '['+i+']'));
		});
	});
}

function targetUserPreview() {
	
	if ($('ul#op-chosen-user-list > li').size() == 0) {
		alert('쿠폰을 발행하실 회원을 선택해 주세요.');
		return;
	}

    $('#targetUserPreviewForm .target-user-preview-input').remove();

	$.each($('ul#op-chosen-user-list > li').find('input'), function() {
        $('#targetUserPreviewForm').append($('<input type="hidden" />').attr({
			'class'		: 'target-user-preview-input',
			'name'		: $(this).attr('name'),
			'value'		: $(this).val()
		}));
	});

    Common.popup('about:blank', 'targetUserPreview', 900, 700, 1);
    $('#targetUserPreviewForm').submit();
}

// 엑셀 업로드
function uploadExcel() {
    Common.popup('/opmanager/coupon/item-upload-excel', 'upload-excel', 600, 550, 0);
}

function duplicateDirectInputValue() {

	var param = {
		couponId : $('#couponId').val(),
		value : $('#directInputValue').val()
	}

	var returnValue = false;
	$.get("/opmanager/coupon/duplicate-direct-input-value",param, function(response) {
		if (response.isSuccess) {
			returnValue = response.data;
		} else {
			alert(response.errorMessage);
			returnValue = true;
		}
	});
	return returnValue;
}
</script>