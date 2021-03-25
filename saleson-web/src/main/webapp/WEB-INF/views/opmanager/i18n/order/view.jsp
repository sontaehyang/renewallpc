<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>


		<!-- 본문 -->
		<div class="popup_wrap" style="min-width:1200px;">
			<h1 class="popup_title">${op:message('M00342')} <!-- 주문정보 상세화면 --></h1>
			<div class="popup_contents">			
				<form:form modelAttribute="order" action="/opmanager/order/edit/${ order.orderId }" method="post">
					<form:hidden path="customerCode" />				
					<div class="board_result" style="margin:10px 0 50px 0;">
						<div class="title">
							<div>${op:message('M00823')}</div> <!-- 처리내역 -->
							<span><a href="javascript:historyToggle()" class="btn_date" id="historyButton">CLOSE</a></span>
						</div>
						<div id="history">
							<c:if test="${ not empty orderStatusLogMap }">
								<div class="cont">
									
									
									<c:forEach items="${ orderStatusLogMap }" var="map">
										<c:set var="logs" value="${map.value}" />
										<c:set var="title" value="${op:message('M01255')}" />
										 
										<c:if test="${ not empty logs[0].itemName }">
											<c:set var="title" value="${ logs[0].itemName } 상품 변경 내역" />
										</c:if>
										<p><span class="blet"><img src="/content/opmanager/images/common/popup_bl.png" alt="" /></span> ${title} </p><!-- 주문 변경 상태 -->
										<ul>
											<c:forEach items="${logs}" var="item">
												<li>
													• ${ item.orderStatusLabel } (${ op:datetime(item.createdDate) }<c:if test="${ not empty item.userName }"> [ ${item.userName} ]</c:if>)
												</li>
											</c:forEach>
										</ul>
									</c:forEach>
									
								</div>
							</c:if>
							<c:if test="${ not empty sendMailLogList }">
								<div class="cont">
									<p><span class="blet"><img src="/content/opmanager/images/common/popup_bl.png" alt="" /></span> ${op:message('M00301')} <!-- 메일발송내역 --></p>
									<ul>
										<c:forEach items="${sendMailLogList}" var="item">
											<li>• ${ item.orderStatusLabel } (${ op:datetime(item.createdDate) } [ ${ item.sendLoginId } ]) <a href="/opmanager/order/send-mail-view?id=${ item.sendMailLogId }" class="table_btn"  onclick="Common.popup(this.href, 'mail-view', 600, 562, 1); return false;">메일보기</a></li>
										</c:forEach>
									</ul>
								</div>
							</c:if>	
							<c:if test="${ not empty orderPrintLogList }">
								<div class="cont">
									<p><span class="blet"><img src="/content/opmanager/images/common/popup_bl.png" alt="" /></span> ${op:message('M00366')} <!-- 출력기록 --></p>
									<ul>
										<c:forEach items="${orderPrintLogList}" var="item">
											<li>• ${ item.title } (${ op:datetime(item.createdDate) }<c:if test="${ not empty item.userName }"> [ ${item.userName} ]</c:if>)</li>
										</c:forEach>
									</ul>
								</div>
							</c:if>
						</div>
					</div>
					
					<!--주문관리 시작-->
					<h3><span>${op:message('M00284')} <!-- 주문관리 --></span>
						<div class="right">
							<button type="button" class="btn gray02" onclick="Common.popup('/opmanager/order/order-print?id=${order.orderId}', 'print-view', 672, 900, 1); return false;">${op:message('M00343')} <!-- 주문서 인쇄 --></button>
						</div>
					</h3>
					
					<div class="board_write">
						<table class="board_write_table" summary="${op:message('M00284')}">
							<caption>${op:message('M00284')} <!-- 주문관리 --></caption>
							<colgroup>
								<col style="width:150px;" />
								<col style="width:auto;" />
								<col style="width:150px;" />
								<col style="width:auto;" />
							</colgroup>
							<tbody>
								<tr>
									<td class="label">${op:message('M00013')} <!-- 주문번호 --></td>
									<td>
										<div>
											<strong>${ order.orderCode }</strong>
									 	</div>
									</td>
									<td class="label">${op:message('M00344')} <!-- 주문날짜 --></td>
									<td>
										<div>
											<span>${ op:datetime(order.createdDate) }</span> &nbsp;&nbsp;
											<a href="/opmanager/order/order-mail?id=${ order.orderId }" class="table_btn" onclick="Common.popup(this.href, 'order-mail', 1000, 600, 1); return false;">${op:message('M00345')} <!-- 수동 메일발송 --></a>
									 	</div>
									</td>
								</tr>						 
							</tbody>
						</table>						 
					</div>
					<!--//board_write E-->

					<!--주문상품 시작--> 
					<h3 class="mt30"><span>${op:message('M00808')} <!-- 주문상품 --></span>
					
						<div class="left">
							
							<c:if test="${not empty changeOrderButtons}">
								<c:forEach items="${changeOrderButtons}" var="button">
									<button type="button" class="btn btn-dark-gray btn-sm" onclick="editItem('${order.orderId}', '${button.key}')">${button.value}</button>
								</c:forEach>
							</c:if>
							
							<c:if test="${order.orderChange == true}">
								<button type="button" class="btn btn-dark-gray btn-sm" onclick="editItem('${order.orderId}', 'change')">주문서 수정</button>
							</c:if>
						</div>
					
						
						
						<div class="right">
							${op:message('M00427')} <!-- 단위 --> : ${op:message('M00814')} <!-- 엔 -->
						</div>
					</h3>
					
					<c:if test="${order.orderChange == true}">
						<c:if test="${!(order.osType == 'Web' || order.osType == 'Mobile' || order.osType == 'Admin')}">
							<div class="board_guide">
								<p class="tip">
									주의! 오픈마켓 주문을 "주문서 수정" 하시면 배송중 목록에서 엑셀 다운로드 기능을 사용하실수 없습니다.
								</p> 
							</div>
						</c:if>
					</c:if>
					<div class="board_write">
						<table class="board_list_table" summary="주문상품">
							<caption>주문상품</caption>
							<colgroup>
								<col style="width:46%;" />
								<col style="width:8%;" />
								<col style="width:8%;" />
								<col style="width:8%;" />
								<col style="width:8%;" />  
								<col style="width:*" />
								<col style="width:10%;" />			
							</colgroup>
							<thead>
								<tr>
									<th scope="col">${op:message('M00018')} <!-- 상품명 --></th>
									<th scope="col">${op:message('M00355')} <!-- 옵션가격 --></th>
									<th scope="col">${op:message('M00356')} <!-- 단가 --></th>
									<th scope="col">${op:message('M00357')} <!-- 수량 --></th>
									<th scope="col">${op:message('M00628')} <!-- 할인금액 --></th>
									<th scope="col">${op:message('M00358')} <!-- 합계 --></th>
									<th scope="col">상태</th>
								</tr>
							</thead>
							<tbody>
								<c:set var="itemIndex" value="0" />
								<c:set var="groupIndex" value="0" />
								<c:forEach items="${ order.orderItemGroup }" var="group" varStatus="orderItemGroupIndex">
									<c:forEach items="${ group.value }" var="item" varStatus="i">
										<tr>
											 <td>
											 	<div>
											 		<div class="item_img"><img src="${ item.item.imageSrc }" style="width:50px;" /></div>
											 		<div class="item_name" style="padding: 0 10px;">
											 			${ item.itemUserCode }<br />
											 			${ item.itemName }  
											 			<c:if test="${ not empty item.requiredOptionsList }">
											 				<br/><br/>
															<ul class="item-list">
								 								<c:forEach items="${ item.requiredOptionsList }" var="option">
								 									<c:if test="${option.optionHideFlag == 'Y'}">
																		<li>
																			<span>${ option.optionName1 }</span>
																			${option.optionName2} 
																			<c:if test="${!empty option.extraPrice && option.extraPrice != 0}">
																			(${op:numberFormat(option.extraPrice)}원)
																			</c:if>
																			
																			<c:if test="${!empty option.optionCode}">
																			(상품번호 : ${option.optionCode})
																			</c:if>
																		</li>
																	</c:if>
																</c:forEach>
															</ul>
														</c:if>
														
														<c:if test="${ not empty item.openMarektOption }">
															<br /><br /> 
															<span>${item.openMarektOption}</span>
														</c:if>
											 		</div> 
											 	</div>
											 </td>
											 <td>
											 	<div>
											 		${ op:numberFormat(item.totalRequiredOptionsPrice) }
											 	</div>
											 </td>
											 <td>
											 	<div>
											 		${ op:numberFormat(item.itemPrice) }
											 	</div>
											 </td>
											 <td>
											 	<div>
											 		${ op:numberFormat(item.quantity) }
											 	</div>
											 </td>
											 <td>
											 	<div>
											 		${ op:numberFormat(item.itemCouponDiscountAmount) }
											 	</div>
											</td>
											<td>
												${ op:numberFormat(((item.totalRequiredOptionsPrice + item.itemPrice) * item.quantity) - item.itemCouponDiscountAmount) }
												<c:if test="${item.orderStatus == '42'}">
													<strong style="color:red;">(반품)</strong>
												</c:if>
												<c:if test="${item.orderStatus == '32'}">
													<strong style="color:red;">(교환)</strong>
												</c:if>
												
												<%-- 
												<c:if test="${item.orderStatus == '40' || item.orderStatus == '30'}">
													<br/><button type="button" onclick="refuseYourApplication('${item.orderItemId}')">거절</button>
												</c:if>
												--%>
											</td>
											
											
											
											<c:if test="${ i.index == 0 }">
												<td rowspan="${ fn:length(group.value) }">
													<c:set var="returnItemCount" value="0" />
													<c:set var="bankItemCount" value="0" />
													<c:set var="exchangeItemCount" value="0" />
													<c:forEach items="${ group.value }" var="groupItem" varStatus="groupItemIndex">
														<c:if test="${groupItem.orderStatus == '42'}">
															<c:set var="returnItemCount">${returnItemCount + 1}</c:set>
														</c:if>
														<c:if test="${groupItem.orderStatus == '0'}">
															<c:set var="bankItemCount">${bankItemCount + 1}</c:set>
														</c:if>
														<c:if test="${groupItem.orderStatus == '32'}">
															<c:set var="exchangeItemCount">${exchangeItemCount + 1}</c:set>
														</c:if>
													</c:forEach>
													<p>
														<c:choose>
															<c:when test="${not empty searchOrderStatus}">
															</c:when>
															<c:otherwise>
																<select id="groupOrderStatus-${order.orderId}-${groupIndex}" class="baseGroupOrderStatus">
																	<c:choose>
																		<c:when test="${bankItemCount > 0 || (bankItemCount > 0 && (returnItemCount > 0 || exchangeItemCount > 0))}">
																			<option value="0">입금대기</option>
																			<option value="50" ${op:selected(item.orderStatus, '50')}>결제완료</option>
																			<option value="1" ${op:selected(item.orderStatus, '1')}>배송준비중</option>
																		</c:when>
																		<c:when test="${bankItemCount == 0 && returnItemCount > 0}">
																			<option value="42">반품입금대기</option>
																			<option value="46" ${op:selected(item.orderStatus, '46')}>반품완료</option>
																		</c:when>
																		<c:when test="${bankItemCount == 0 && exchangeItemCount > 0}">
																			<option value="32">교환입금대기</option>
																			<option value="33" ${op:selected(item.orderStatus, '33')}>교환상품재발송</option>
																		</c:when>
																		<c:when test="${item.orderStatus == '50'}">
																			<option value="50" ${op:selected(item.orderStatus, '50')}>결제완료</option>
																			<option value="1" ${op:selected(item.orderStatus, '1')}>배송준비중</option>
																			<option value="10" ${op:selected(item.orderStatus, '10')}>주문취소요청</option>
																		</c:when>
																		<c:when test="${item.orderStatus == '1'}">
																			<option value="1" ${op:selected(item.orderStatus, '1')}>배송준비중</option>
																			<c:if test="${exchangeItemCount == 0}">
																				<option value="10" ${op:selected(item.orderStatus, '10')}>주문취소요청</option>
																			</c:if>
																		</c:when>
																		<c:when test="${item.orderStatus == '2'}">
																			<option value="2" ${op:selected(item.orderStatus, '2')}>배송중</option>
																			<option value="3" ${op:selected(item.orderStatus, '3')}>배송완료</option>
																		</c:when>
																		<c:when test="${item.orderStatus == '3'}">
																			<option value="3" ${op:selected(item.orderStatus, '3')}>배송완료</option>
																		</c:when>
																		<c:when test="${item.orderStatus == '34'}">
																			<option value="34" ${op:selected(item.orderStatus, '34')}>교환상품발송</option>
																			<option value="3" ${op:selected(item.orderStatus, '3')}>배송완료</option>
																		</c:when>
																		<c:when test="${item.orderStatus == '30'}">
																			<option value="30" ${op:selected(item.orderStatus, '30')}>교환요청</option>
																			<option value="31" ${op:selected(item.orderStatus, '31')}>교환처리중</option>
																			<!-- option value="36" ${op:selected(item.orderStatus, '36')}>교환완료</option -->
																		</c:when>   
																		<c:when test="${item.orderStatus == '31'}">
																			<option value="31" ${op:selected(item.orderStatus, '31')}>교환처리중</option>
																			<!-- option value="36" ${op:selected(item.orderStatus, '36')}>교환완료</option -->
																		</c:when> 
																		<c:when test="${item.orderStatus == '40'}">
																			<option value="40" ${op:selected(item.orderStatus, '40')}>반품접수</option>
																			<option value="41" ${op:selected(item.orderStatus, '41')}>반품처리중</option>
																		</c:when>
																		<c:otherwise>
																			<option value="${item.orderStatus}">${item.orderStatusLabel}</option>
																		</c:otherwise>
																	</c:choose>
																</select>
																
															</c:otherwise>
														</c:choose>
													</p>
													<input type="hidden" name="orderItemStatusParams[${itemIndex}].orgOrderStatus" value="${item.orderStatus}" class="groupOrgOrderStatus-${order.orderId}-${groupIndex}" id="orgOrderStatus-${order.orderId}-${ item.orderItemId }" />
													<input type="hidden" name="orderItemStatusParams[${itemIndex}].orderItemId" value="${item.orderItemId}" />
													<input type="hidden" name="orderItemStatusParams[${itemIndex}].orderStatus" value="${item.orderStatus}" class="changeOrderStatus changeOrderStatus-${order.orderId} groupOrderStatus-${order.orderId}" id="orderStatus-${order.orderId}-${ item.orderItemId }" />
													
													<c:choose>
														<c:when test="${item.orderStatus == '50' && order.approvalType == 'bank'}">
															<br/> <button type="button" class="btn btn-dark-gray btn-sm" onclick="changeOrderStatus('${order.orderId}', 0, '해당 주문의 상태를 입금 대기 상태로 변경 하시겠습니까?')">입금 확인</button>
														</c:when>
														<c:when test="${item.orderStatus == '1' || item.orderStatus == '33'}">
															<br/>
															<select name="orderItemStatusParams[${itemIndex}].deliveryCompanyId" id="groupDeliveryCompanyId-${order.orderId}" class="full baseGroupDeliveryCompanyId">
																<option value="0">-선택-</option>
																<c:forEach items="${activeDeliveryCompanyList}" var="deliveryCompany">
																	<option value="${deliveryCompany.deliveryCompanyId}" ${op:selected(item.deliveryCompanyId, deliveryCompany.deliveryCompanyId)}>${deliveryCompany.deliveryCompanyName}</option>
																</c:forEach>
															</select> <br />
															<input type="text" name="orderItemStatusParams[${itemIndex}].deliveryNumber" id="groupDeliveryNumber-${order.orderId}" class="full baseGroupDeliveryNumber" value="${item.deliveryNumber}" />
														</c:when>
														<c:when test="${item.orderStatus == '2' || item.orderStatus == '34'}">
															<p class="mt10">송장번호 : ${ item.deliveryNumber }</p>
															<input type="hidden" name="orderItemStatusParams[${itemIndex}].deliveryCompanyId" value="${item.deliveryCompanyId}" />
															<input type="hidden" name="orderItemStatusParams[${itemIndex}].deliveryNumber" value="${item.deliveryNumber}" />
														</c:when>
														<c:when test="${item.orderStatus == '3'}">
															<c:if test="${ item.deliveryNumber != ''}">
																<p class="mt10">송장번호 : ${ item.deliveryNumber }</p>
															</c:if>
														</c:when>
													</c:choose>
													
													<c:forEach items="${ group.value }" var="groupItem" varStatus="groupItemIndex">
														<c:if test="${groupItemIndex.index > 0}">
															<c:set var="itemIndex">${itemIndex + 1}</c:set>
															<input type="hidden" name="orderItemStatusParams[${itemIndex}].orgOrderStatus" value="${groupItem.orderStatus}" class="groupOrgOrderStatus-${order.orderId}-${groupIndex}" id="orgOrderStatus-${order.orderId}-${ groupItem.orderItemId }" />
															<input type="hidden" name="orderItemStatusParams[${itemIndex}].orderItemId" value="${groupItem.orderItemId}" />
															<input type="hidden" name="orderItemStatusParams[${itemIndex}].orderStatus" class="changeOrderStatus changeOrderStatus-${order.orderId} groupOrderStatus-${order.orderId}" id="orderStatus-${order.orderId}-${ groupItem.orderItemId }" value="${ groupItem.orderStatus }" />
															<c:if test="${groupItem.orderStatus == '1' || groupItem.orderStatus == '2' || groupItem.orderStatus == '33' || groupItem.orderStatus == '34'}">
																<input type="hidden" name="orderItemStatusParams[${itemIndex}].deliveryCompanyId" class="groupDeliveryCompanyId-${order.orderId}" value="${groupItem.deliveryCompanyId}" />
																<input type="hidden" name="orderItemStatusParams[${itemIndex}].deliveryNumber" class="groupDeliveryNumber-${order.orderId}" value="${groupItem.deliveryNumber}" />
															</c:if>
															
															<c:if test="${groupItemIndex.last == true}">
																<c:set var="itemIndex">${itemIndex + 1}</c:set>
															</c:if>
														</c:if>
													</c:forEach>
													 
													<c:if test="${item.orderStatus == '0' || item.orderStatus == '42' || item.orderStatus == '32'}">
														<c:if test="${returnItemCount > 0}">
															<p class="mt10" style="color:red;">반품 상품 : ${op:numberFormat(returnItemCount)}건</p>	
														</c:if>
														
														<c:if test="${exchangeItemCount > 0}">
															<p class="mt10" style="color:red;">교환 상품 : ${op:numberFormat(exchangeItemCount)}건</p>
														</c:if>
														<c:if test="${order.orderPayment.orderPayAmount - order.orderPayment.payAmount > 0}">
															<!-- p class="mt10" style="color:red;">미납금 : ${op:numberFormat(order.orderPayment.orderPayAmount - order.orderPayment.payAmount)}원</p -->
														</c:if>
													</c:if>
												</td>
												
												<c:set var="groupIndex">${groupIndex + 1}</c:set>
											</c:if>
											
											<c:if test="${fn:length(group.value) == 1}">
												<c:set var="itemIndex">${itemIndex + 1}</c:set>
											</c:if>
										</tr>
									</c:forEach>
								</c:forEach> 
							</tbody>
						</table>				 
					</div>
					<!--// 주문상품 끝-->
					
					
					
					<c:if test="${ order.sumEarnPoints > 0 }">
						<div class="board_write">
							<table class="board_list_table01">
								<thead>
									<tr>
										<th scope="col" style="text-align:right;">${op:message('M01591')}</th> <!-- 적립 포인트 -->
									</tr>
								</thead>
								<tbody>
									<tr>
										 <td style="text-align:right;">
										 	<div>
										 		${ op:numberFormat(order.sumEarnPoints) } P
										 	</div>
										 </td>
									</tr>							 							 
								</tbody>
							</table>
						</div>
					</c:if>
					
					<div class="btn_center">
						<button type="submit" class="btn btn-active">${op:message('M00354')}</button> <!-- 변경 정보 저장 -->
						<a href="javascript:;" onclick="Common.popup('/opmanager/order/order-print?id=${order.orderId}', 'print-view', 672, 900, 1);" class="btn btn-default">${op:message('M00343')} <!-- 주문서 인쇄 --></a>
					</div>
					
					<div class="total_wrap">
				 		<table cellpadding="0" cellspacing="0" summary="상품금액, 배송비, 할인금액, 최종 결제 금액" class="total_price">
							<caption>총 구매 금액</caption>
							<colgroup>
								<c:set var="width" value="12%" />
								<c:if test="${ order.taxType eq '2' }">
									<c:set var="width" value="10%" />
								</c:if>
								<col style="width:${ width };">
								<col style="width:2%;">
								<col style="width:${ width };">
								<c:if test="${ order.taxType eq '2' }">
									<col style="width:2%;">
									<col style="width:${ width };">
								</c:if>
								<col style="width:2%;">
								<col style="width:${ width };">
								<col style="width:2%;">
								<col style="width:${ width };">
								<col style="width:2%;">
								<col style="width:${ width };">
								<col style="width:2%;">
								<col style="width:${ width };">
								<col style="width:2%;">
								<col style="width:auto;">
							</colgroup> 
							 
								  		
							<tbody>
								<tr>
									<th scope="col">
										${op:message('M00817')} <!-- 상품합계 --> 
										(<c:choose>
					 						<c:when test="${ order.taxType eq '2' }">${op:message('M00421')}<!-- 세금별도 --></c:when>
					 						<c:otherwise>${op:message('M00608')}<!-- 세금포함 --></c:otherwise>
					 					</c:choose>)
					 				</th>
									<th rowspan="2" class="label"><img src="/content/images/btn/btn_minus.png" alt="minus">  </th> 
									<th scope="col">${op:message('M00818')} <!-- 상품쿠폰 --></th>
									<c:if test="${ order.taxType eq '2' }">
										<th rowspan="2" class="label"><img src="/content/images/btn/btn_plus.png" alt="minus">  </th> 
										<th scope="col">消費税 <!-- 소비세 --></th>
									</c:if>
									<th rowspan="2" class="label"><img src="/content/images/btn/btn_plus.png" alt="minus">  </th>
									<th scope="col">${op:message('M00359')} <!-- 배송료 --> (${op:message('M00608')}) <!-- 세금포함 --></th>
									<th rowspan="2" class="label"><img src="/content/images/btn/btn_minus.png" alt="minus">  </th>
									<th scope="col">${op:message('M00820')} <!-- 장바구니 쿠폰 --></th>
									<th rowspan="2" class="label"><img src="/content/images/btn/btn_minus.png" alt="minus">  </th>
									<th scope="col">${op:message('M00246')} <!-- 포인트 --></th>
									<th rowspan="2" class="label"><img src="/content/images/btn/btn_minus.png" alt="minus">  </th>
									<th scope="col">${op:message('M00811')} <!-- 추가할인 --></th>
									<th rowspan="2" class="label"><img src="/content/images/btn/btn_total.png" alt="minus">  </th>
									<th scope="col">${op:message('M00629')} <!-- 최종 결제 금액 -->(${op:message('M00608')}) <!-- 세금포함 --></th>
								</tr>
								<tr>
									<td>${ op:numberFormat(order.orderPayment.sumItemPrice) }<span>원</span></td>		 
									<td>${ op:numberFormat(order.orderPayment.sumItemCouponDiscountAmount) } <span>원</span></td>
									<c:if test="${ order.taxType eq '2' }"> 
										<td>${ op:numberFormat(order.sumExcisePrice) } <span>원</span></td>
									</c:if> 
									<td>${ op:numberFormat(order.orderPayment.sumDeliveryPrice + order.orderPayment.addDeliveryPrice) } <span>원</span></td> 
									<td>${ op:numberFormat(order.orderPayment.sumCartCouponDiscountAmount) } <span>원</span></td> 
									<td>${ op:numberFormat(order.orderPayment.sumUsePoint) } <span>원</span></td>  
									<td>${ op:numberFormat(order.orderPayment.addDiscountAmount) } <span>원</span></td>
									<td class="total_num">
										${ op:numberFormat(order.orderPayment.orderPayAmount) }<span>원</span>
										<c:if test="${order.orderPayment.returnAmount > 0}">
											<!-- p>(환불 : -${op:numberFormat(order.orderPayment.returnAmount)}<span>원</span>) </p -->
										</c:if>  
										
								</tr> 
							</tbody> 
						</table>  
					</div>
					
					<div> 
						
						<h3 class="mt30"><span>${op:message('M00315')} <!-- 주문자정보 --></span></h3>
						<!--주문자정보-->
						<table class="board_write_table" summary="${op:message('M00315')}">
							<caption>${op:message('M00315')}</caption>
							<colgroup>
								<col style="width:120px;">  
								<col style="width:90px;" />
								<col style="" />
							</colgroup>
							<tbody id="buyerInputArea">
								<%-- <c:if test="${order.guestFlag == 'N'}">
									<tr>
										<td class="label">${op:message('M00105')} <!-- 회사명 --></td>
										<td colspan="2">
											<div>
												<form:input path="companyName" title="${op:message('M00105')}" cssClass="seven" maxlength="50" />
										 	</div>
										</td>
									</tr>
								</c:if> --%>
								<tr>
									<td class="label"><span>*</span> ${op:message('M00311')} ${op:message('M00005')} <!-- 주문자 이름(한자) --> </td>
									<td colspan="2">
										<div>
											<form:input path="userName" title="${op:message('M00311')} ${op:message('M00005')}" cssClass="seven required" maxlength="50" />
									 	</div>
									</td>
								</tr>
								<tr>
									<td class="label">${op:message('M00016')} <!-- 전화번호 --></td>
									<td colspan="2">
										<div>
											<form:input path="fullPhone" title="${op:message('M00016')}" cssClass="two" maxlength="14" /> 
										</div>
									</td>
								</tr>
								<tr>
									<td class="label"><span>*</span> ${op:message('M00155')} <!-- 휴대폰번호 --></td>
									<td colspan="2">
										<div>
											<form:input path="fullMobile" title="${op:message('M00016')}" cssClass="two required" maxlength="14" />
									 	</div>
									</td>
								</tr>	
								<tr>
									<td class="label"> ${op:message('M00314')} <!-- E-mail --></td>
									<td colspan="2">
										<div>
											<form:input path="email" title="${op:message('M00314')}" cssClass="seven" maxlength="50" />
									 	</div>
									</td>
								</tr>
								<tr>
									<td class="label" rowspan="2"><span>*</span> ${op:message('M00118')} <!-- 주소 --></td>
									<td class="label"><span>*</span> ${op:message('M00115')} <!-- 우편번호 --></td>
									<td>
										<div>
											<form:input path="zipcode1" title="${op:message('M00115')}" cssClass="one required _number" maxlength="3" /> -
											<form:input path="zipcode2" title="${op:message('M00115')}" cssClass="one required _number" maxlength="3" />
											<a href="javascript:;" class="table_btn" onclick="openDaumPostcode()">${op:message('M00117')}</a>
										</div>
									</td>
								</tr>
								<tr>
									<td class="label"><span>*</span> ${op:message('M00118')}<!-- 주소 --></td>
									<td>
										<div>
											<form:hidden path="sido" />
											<form:hidden path="sigungu" />
											<form:hidden path="eupmyeondong" />
											<form:input path="address" title="${op:message('M00118')}" cssClass="full required" maxlenght="100" />
											<form:input path="addressDetail" title="${op:message('M00118')}" cssClass="full required" cssStyle="margin-top:10px;" maxlength="50" />
										</div>
									</td>
								</tr>				 
							</tbody>
						</table>
						
					</div>	<!--// 주문자정보 끝-->
					<!-- 수령자정보-->
					<div>
						<h3 class="mt30"><span>${op:message('M00316')} <!-- 수령자정보 --></span> <button type="button" class="btn btn-dark-gray btn-sm" onclick="infoCopy()"><span>주문자 정보와 동일</span></button></h3>				
						<table class="board_write_table" summary="${op:message('M00316')}">
							<caption>${op:message('M00316')}</caption>
							<colgroup>
								<col style="width:120px;">  
								<col style="width:90px;" />
								<col style="" />
							</colgroup>
							<tbody>
								<c:if test="${order.guestFlag == 'N'}">
									<tr>
										<td class="label">${op:message('M00105')} <!-- 회사명 --></td>
										<td colspan="2">
											<div>
												<form:input path="receiveCompanyName" title="${op:message('M00105')}" cssClass="seven" maxlength="50" />
										 	</div>
										</td>
									</tr>
								</c:if>	
								<tr>
									<td class="label"><span>*</span> ${op:message('M00317')} ${op:message('M00005')} <!-- 수령자 이름(한자) --></td>
									<td colspan="2">
										<div>
											<form:input path="receiveName" title="${op:message('M00317')} ${op:message('M00005')}" cssClass="seven required" maxlength="50" />
									 	</div>
									</td>
								</tr>
								<tr>
									<td class="label">${op:message('M00016')} <!-- 전화번호 --></td>
									<td colspan="2">
										<div>
											<form:input path="fullReceivePhone" title="${op:message('M00016')}" cssClass="two" maxlength="14" />
									 	</div>
									</td>
								</tr>
								<tr>
									<td class="label"><span>*</span> ${op:message('M00155')} <!-- 휴대폰번호 --></td>
									<td colspan="2">
										<div>
											<form:input path="fullReceiveMobile" title="${op:message('M00155')}" cssClass="two required" maxlength="14" />
									 	</div>
									</td>
								</tr>	
								<tr>
									<td class="label" rowspan="2"><span>*</span> ${op:message('M00118')} <!-- 주소 --></td>
									<td class="label"><span>*</span> ${op:message('M00115')} <!-- 우편번호 --></td>
									<td>
										<div>
											<form:input path="receiveZipcode1" title="${op:message('M00115')}" cssClass="one required _number" maxlength="3" /> -
											<form:input path="receiveZipcode2" title="${op:message('M00115')}" cssClass="one required _number" maxlength="3" />
											<a href="javascript:;" class="table_btn" onclick="openDaumPostcode('receive')">${op:message('M00117')}</a>
										</div>
									</td>
								</tr>
								<tr>
									<td class="label"><span>*</span> ${op:message('M00118')} <!-- 주소 --></td>
									<td>
										<div>
											<form:hidden path="receiveSido" />
											<form:hidden path="receiveSigungu" />
											<form:hidden path="receiveEupmyeondong" />
											<form:input path="receiveAddress" title="${op:message('M00118')}" cssClass="full required" maxlength="100" />
											<form:input path="receiveAddressDetail" title="${op:message('M00118')}" cssClass="full required" cssStyle="margin-top:10px;" maxlength="50" />
										</div>
									</td>
								</tr>				 
							</tbody>
						</table>
					</div>
					<!--//주문자정보 , 수령자정보  끝 -->
					
					<!--결제정보 시작-->
					<h3 class="mt30" style="clear:both;"><span>${op:message('M00318')} <!-- 결제정보 --></span></h3>
					<div class="board_write">
						<table class="board_write_table" summary="${op:message('M00318')}">
							<caption>${op:message('M00318')}</caption>
							<colgroup>
								<col style="width:150px;" /> 
								<col style="width:auto;" />
							</colgroup>
							<tbody>
								<tr>
									<td class="label">${op:message('M00060')} <!-- 결제방법 --></td>
									<td>
										<div>
											${ order.orderPayment.approvalTypeLabel } 
									 	</div>
									</td>
								</tr>
								
								<c:if test="${ order.orderPayment.approvalType eq 'bank' }">
					 				<tr>
					 					<td class="label">${op:message('M00541')} <!-- 입금은행 --> </td>
					 					<td><div>${ order.orderPayment.bankVirtualNo }</div></td>
					 				</tr>
					 				<tr>
					 					<td class="label">${op:message('M00542')} <!-- 입금명의 --></td>
					 					<td><div>${ order.orderPayment.bankInName }</div></td>
					 				</tr>
					 				<tr>
					 					<td class="label last">${op:message('M00543')} <!-- 입금예정일 --></td>
					 					<td class="last"><div>${ op:date(op:replaceAll(order.orderPayment.bankDate, '-', '')) }</div></td>
					 				</tr>
								</c:if>
								
								<c:if test="${ order.orderPayment.approvalType eq 'conv' }">
									<tr>
					 					<td class="label">コンビニ名 <!-- 편의점 이름 --> </td>
					 					<td><div>${ order.orderPayment.cvsName }</div></td>
					 				</tr>
					 				
					 				<tr>
					 					<td class="label last">支払番号 <!-- 지불번호 --> </td>
					 					<td class="last"><div>${ order.orderPayment.receiptNo }</div></td>
					 				</tr>
					 				<tr>
					 					<td class="label last">支払期限 <!-- 지불기한 --> </td>
					 					<td class="last"><div>${ op:date(order.orderPayment.payLimit) }</div></td>
					 				</tr>
								</c:if>
								
							</tbody>
						</table>
						 			 			 
					</div>
					<!--// 결제정보 끝 -->
					
					<c:if test="${not empty cashReceiptList}">
						<h3 class="mt30" style="clear:both;"><span>현금영수증 신청 정보</span></h3>
						<div class="board_write">
							<table class="board_write_table" summary="현금영수증 신청 정보">
								<caption>현금영수증 신청 정보</caption>
								<colgroup>
									<col style="width:150px;" /> 
									<col style="width:*;" />
									<col style="width:150px;" /> 
									<col style="width:*;" />
								</colgroup>
								<tbody>
									<c:forEach items="${cashReceiptList}" var="item">
										<tr>
											<td class="label">신청자명</td>
											<td>
												<div>
													${item.cashReceiptName}
											 	</div>
											</td>
											<td class="label">신청 금액</td>
											<td>
												<div>
													${op:numberFormat(item.cashReceiptAmount)}원
											 	</div>
											</td>
										</tr>
										<tr>
											<td class="label">신청 구분</td>
											<td>
												<div>
													${item.cashReceiptType}
											 	</div>
											</td>
											<td class="label">신청 정보</td>
											<td>
												<div>
													${item.cashReceiptCode}
											 	</div>
											</td>
										</tr>
									</c:forEach>
										 
								</tbody>
							</table>
							 			 			 
						</div>
					</c:if>
					
					<c:if test="${order.taxInvoiceType == '1' }">
					
						<h3 class="mt30" style="clear:both;"><span>세금계산서 신청 정보</span></h3>
						<div class="board_write">
							<table class="board_write_table" summary="세금계산서 신청 정보">
								<caption>세금계산서 신청 정보</caption>
								<colgroup>
									<col style="width:150px;" /> 
									<col style="width:*;" />
									<col style="width:150px;" /> 
									<col style="width:*;" />
								</colgroup>
								<tbody>
										<tr>
											<td class="label">사업자번호</td>
											<td>
												<div>
													${order.taxInvoiceBusinessNumber}
											 	</div>
											</td>
											<td colspan="2"/>
										</tr>
										<tr>
											<td class="label">상호</td>
											<td>
												<div>
													${order.taxInvoiceBusinessCompanyName}
											 	</div>
											</td>
											<td colspan="2"/>
										</tr>
										<tr>
											<td class="label">대표자명</td>
											<td>
												<div>
													${order.taxInvoiceBusinessBossName}
											 	</div>
											</td>
											<td colspan="2"/>
										</tr>
										<tr>
											<td class="label">업태</td>
											<td>
												<div>
													${order.taxInvoiceBusinessStatus}
											 	</div>
											</td>
											<td class="label">종목</td>
											<td>
												<div>
													${order.taxInvoiceBusinessStatusType}
											 	</div>
											</td>
										</tr>
										<tr>
											<td class="label">사업장 소재지</td>
											<td>
												<div>
													${order.taxInvoiceBusinessLocation}
											 	</div>
											</td>
											<td colspan="2"/>
										</tr>
										<tr>
											<td class="label">이메일</td>
											<td>
												<div>
													${order.taxInvoiceBusinessEmail}
											 	</div>
											</td>
											<td colspan="2"/>
										</tr>
								</tbody>
							</table>
							 			 			 
						</div>
					
					</c:if>
					
					<!--주문정보 시작-->
					<c:if test="${ !empty order.addInfoToHashMap }">
						<h3 class="mt30" style="clear:both;"><span>${op:message('M00059')} <!-- 주문정보 --></span></h3>
						<div class="board_write">
							<table class="board_write_table" summary="${op:message('M00059')}">
								<caption>${op:message('M00059')}</caption>
								<colgroup>
									<col style="width:150px;" /> 
									<col style="width:*;" />
								</colgroup>
								<tbody>
									<c:set var="comment" />
									<c:forEach items="${ order.addInfoToHashMap }" var="item">
										<c:if test="${ item.key == '배송요청사항' }">
											<c:set var="comment">${item.value}</c:set>
										</c:if>
									</c:forEach>
									
									<c:forEach items="${ order.addInfoToHashMap }" var="item">
										<tr>
											<td class="label">${ item.key }</td>
											<td>
												<div>
													- ${ op:nl2br(item.value) }
													<form:hidden path="content" value="${comment}" />
											 	</div>
											</td>
										</tr>
									</c:forEach>
										 
								</tbody>
							</table>
							 			 			 
						</div>
					</c:if>
					<!--// 주문정보 끝 -->
					
					<c:if test="${order.orderPayment.returnApprovalType == 'bank'}">
						<c:if test="${fn:trim(order.orderPayment.returnBankInName) != ''}">	
							<h3 class="mt30" style="clear:both;"><span>환불 정보</span></h3>
							<div class="board_write">
								<table class="board_write_table" summary="환불 정보">
									<caption>환불 정보</caption>
									<colgroup>
										<col style="width:150px;" /> 
										<col style="width:*;" />
									</colgroup>
									<tbody>
										<tr>
											<td class="label">환불 계좌 정보</td>
											<td>
												<div>
													${order.orderPayment.returnBankInName} <br/>
													<c:choose>
														<c:when test="${fn:trim(order.orderPayment.returnVirtualNo) == ''}">
															계좌 정보 없음
														</c:when>
														<c:otherwise>
															계좌 정보 : ${order.orderPayment.returnVirtualNo} (${order.orderPayment.returnBankName})
														</c:otherwise>
													</c:choose>
											 	</div>
											</td>
										</tr>
											 
									</tbody>
								</table>
								 			 			 
							</div>
						</c:if>
					</c:if>
					
					<!--관리자 참고 시작-->
					<h3 class="mt30" style="clear:both;"><span>${op:message('M00322')} <!-- 관리자 참고 --></span></h3>
					<div class="board_write">
						<table class="board_write_table" summary="${op:message('M00322')}">
							<caption>${op:message('M00322')}</caption>
							<colgroup>
								<col style="width:160px;">
								<col style="width:*;">
							</colgroup>
							<tbody>
								<tr>
									<td class="label">* ${op:message('M00323')} <!-- 관리자 메모 --></td>
									<td>
										<div>
										 	<textarea cols="30" rows="6" name="adminMessage" title="${op:message('M00323')}">${ order.adminMessage }</textarea>
									 	</div>
									</td>
								</tr>
								
								
								<tr>
									<td class="label red">* ${op:message('M00323')}<br/>(물류센터 공유) <!-- 관리자 메모 (구매자 공유) --></td>
									<td>
										<div>
										 	<textarea cols="30" rows="6" name="adminMessageEtc" title="${op:message('M00323')} (${op:message('M00324')})">${ order.adminMessageEtc }</textarea>
									 	</div>
									</td>
								</tr>
											 
							</tbody>
						</table>	
						<div class="btn_center">
							<button type="submit" class="btn btn-active">${op:message('M00354')}</button> <!-- 변경 정보 저장 -->
							<a href="javascript:;" onclick="Common.popup('/opmanager/order/order-print?id=${order.orderId}', 'print-view', 672, 900, 1);" class="btn btn-default">${op:message('M00343')} <!-- 주문서 인쇄 --></a>
						</div>			 			 			 
					</div>
					
					
					<!--//board_result E-->
					<!--//주문관리 끝-->
					
					<!--// 관리자 참고 끝 -->
				</form:form>
			</div><!--//popup_contents E-->
			<a href="#" class="popup_close">${op:message('M00353')} <!-- 창 닫기 --></a>
		</div>

<script src="https://spi.maps.daum.net/imap/map_js_init/postcode.js"></script>
<script type="text/javascript">

	var validatorOptions = {
		'requiredClass' : 'required',
		'submitHandler' : function() {
			
		}
			
	};

	// 사용자 요청 거절
	function refuseYourApplication(orderItemId) {
		
		if (confirm('해당 주문 상품의 사용자 요청을 거절 하시겠습니까?\n거절후에는 사용자가 해당 요청을 신청하기 전상태로 복구됩니다.')) {
			var param = {
				'orderId'		: '${order.orderId}',
				'orderItemId'	: orderItemId
			};
			
			$.post('/opmanager/order/refuse-your-application', param, function(response){
				if (response.isSuccess) {
					location.reload();
				} else {
					alert(response.errorMessage);
				}
			}, 'json');
		}
		
	}
	
	function uppercase(text) {
		if (text == '' || text == undefined) return text;
		return text.substring(0, 1).toUpperCase() + text.substring(1);
	}
	
	//다음 우편번호 검색
	function openDaumPostcode(mode) {
		
		var zipcode1 		= "zipcode1";
		var zipcode2 		= "zipcode2";
		var address 		= "address";
		var addressDetail 	= "addressDetail";
		var sido			= "sido";
		var sigungu			= "sigungu";
		var eupmyeondong	= "eupmyeondong";
		
		
		if (mode == "receive") {
			zipcode1 		= "receiveZipcode1";
			zipcode2 		= "receiveZipcode2";
			address 		= "receiveAddress";
			addressDetail 	= "receiveAddressDetail";
			sido			= "receiveSido";
			sigungu			= "receiveSigungu";
			eupmyeondong	= "receiveEupmyeondong";
		}
		
	    new daum.Postcode({
	        oncomplete: function(data) {	        	
	        	
	            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
	            // 우편번호와 주소 정보를 해당 필드에 넣고, 커서를 상세주소 필드로 이동한다.
	            document.getElementById(zipcode1).value = data.postcode1;
	            document.getElementById(zipcode2).value = data.postcode2;
	            
	            document.getElementById(address).value = data.address;
	            document.getElementById(sido).value = Zipcode.getSido(data.address);
	            document.getElementById(sigungu).value = Zipcode.getSigungu(data.address);
	            document.getElementById(eupmyeondong).value = Zipcode.getEupmyeondong(data.address);
	            
	            //var indexNum = data.address.indexOf(" ");
	            //document.getElementById('dodobuhyun').value = data.address.substring(0, indexNum);

	            //전체 주소에서 연결 번지 및 ()로 묶여 있는 부가정보를 제거하고자 할 경우,
	            //아래와 같은 정규식을 사용해도 된다. 정규식은 개발자의 목적에 맞게 수정해서 사용 가능하다.
	            //var addr = data.address.replace(/(\s|^)\(.+\)$|\S+~\S+/g, '');
	            //document.getElementById('address').value = addr;
				document.getElementById(addressDetail).value = "";
	            document.getElementById(addressDetail).focus();

	        }
	    }).open();
	}
	
	function historyToggle() {
		$area = $('div#history');
		if ($area.css('display') == undefined) {
			$area.hide();
			$('a#historyButton').html('OPEN');
		} else if ($area.css('display') == 'block') {
			$area.hide();
			$('a#historyButton').html('OPEN');
		} else { 
			$area.show();
			$('a#historyButton').html('CLOSE');
		}
	} 
	
	$(function(){

		// 폼체크
		$("#order").validator(function(){
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
			
			if (!confirm("주문정보를 변경 하시겠습니까?")) {
				return false;
			}
			
		});
		
		$('#update_delivery_prearranged_date').on('click', function() {
			var orderId = '${order.orderId}';
			var deliveryPrearrangedDate = $('input[name="deliveryPrearrangedDate"]').val();

			if (deliveryPrearrangedDate == '') {
				alert("${op:message('M00362')}"); // 배송 지정일이 입력되지 않습니다.
				return;
			}
			
			if (!confirm("${op:message('M00363')}")) return; // 배송 지정일을 수정 하시겠습니까?
					
			$.post("/opmanager/order/change-delivery-prearranged-date/" + orderId, {'deliveryPrearrangedDate': deliveryPrearrangedDate}, function(response) {
				Common.responseHandler(response, function(response) {
					
					alert("${op:message('M00364')}"); //저장했습니다. 
					opener.location.reload();
					location.reload();
					
				}, function(response){
					
					alert("Error.");
					
				});
				
			}, 'json');
		});
		
		$('#update_order_status').on('click', function(){
			var param = {
				'orderId' 			: '${order.orderId}',
				'orderStatus'		: $('select[name="orderStatus"]').val(),
				'orgOrderStatus'	: $('input[name="orgOrderStatus"]').val()
			};
			
			if (param.orderStatus == param.orgOrderStatus) {
				return;
			}
			
			if (!confirm("${op:message('M00365')}")) return; // 수정 하시겠습니까? 
					
			$.post("/opmanager/order/change-order-status/" + param.orderId, param, function(response) {
				Common.responseHandler(response, function(response) {
					
					//alert("${op:message('M00364')}"); //저장했습니다. 
					opener.location.reload();
					location.reload();
					
				}, function(response){
					
					alert("Error.");
					
				});
				
			}, 'json');
		});
		
	});
	
	function editItem(orderId, mode) {
		if (mode == 'total-return') {
			Common.popup("/opmanager/order/total-return/" + orderId, 'total-return', 800, 590, 1);
		} else if (mode == 'total-cancel') {
			Common.popup("/opmanager/order/total-cancel/" + orderId, 'total-cancel', 800, 590, 1);
		} else {
			Common.popup("/opmanager/order/edit-item/" + mode + "/" + orderId, 'edit-item-' + mode, 1200, 800, 1);
		}
	}
	
	function uppercase(text) {
		if (text == '' || text == undefined) return text;
		return text.substring(0, 1).toUpperCase() + text.substring(1);
	}

	function infoCopy() { 
		$.each($('input, select', $('#buyerInputArea')), function() {
			var name = uppercase($(this).attr('id'));
			name = 'receive' + name;
			
			if (name == 'receiveUserName') {
				name = 'receiveName';
			} else if (name == 'receiveFullPhone') {
				name = 'fullReceivePhone';
			} else if (name == 'receiveFullMobile') {
				name = 'fullReceiveMobile';
			}
			
			if ($('#' + name).size() == 1) {
				$('#' + name).val($(this).val());
			}
		});
	}
	
	/*
	function findItem(targetId) {
		var query = $('input[name="query"]').val();
		var addQuery = "";
		if (query != '') {
			addQuery = '&where=' + $('select[name="where"]').val() + "&query=" + query;
		}
		Common.popup("/opmanager/item/find-item?targetId=" + targetId + addQuery, 'find-item', 800, 600);
	}
	*/
	// 상품검색 callback
	
	/*
	function findItemCallback(id, item) {
		
		var key = id + '_item_' + item.itemId;
		var itemCount = $('#' + key).size();
		
		if (itemCount == 0) {
			
			
			var html = '';
			html += '<tr id="' + key + '">';
			
			html += '	<td style="text-align:left; line-hegith:15px;"><div>';
			html += 		item.itemName;
			
			if (item.itemOptionGroups != undefined) {
				if (item.itemOptionGroups.length > 0) {
					if (item.itemOptionGroups[0].optionTitle != '') {
						html += "<br/>- 옵션";
						for (var i = 0; i < item.itemOptionGroups.length; i++) {
							html += '' + item.itemOptionGroups[i].optionTitle + ': <select>';
							
							for (var j = 0 ; j < item.itemOptionGroups[i].itemOptions.length; j++) {
								html += '<option>' + item.itemOptionGroups[i].itemOptions[j].optionName2 + '</option>';
							}
							
							html += '</select><br/>';
						}
					}
				}
			}
			html += '	</div></td>';
			
			html += '	<td><div>' + '<input type="text" class="full" title="상품번호" value="'+ item.itemId +'"/>' + '</div></td>';
			html += '	<td><div>' + '<input type="text" class="full _number" title="옵션가격" value="0"/>' + '</div></td>';
			html += '	<td><div>' + '<input type="text" class="full _number" title="단가" value="'+ item.itemSalePrice +'"/>' + '</div></td>';
			html += '	<td><div>' + '<input type="text" class="full _number" title="수량" value="1"/>' + '</div></td>';
			html += '	<td><div>' + '<input type="text" class="half" title="배송료" value="0"/>' + '</div></td>';
			html += '	<td><div>' + '<a href="javascript:Shop.deleteRelationItem(\'' + key + '\')" class="btn_date">삭제</a>' + '</div></td>';
			html += '</tr>';

			$('#' + id).append(html);
		}
	}
	*/
</script>