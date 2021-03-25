<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
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
			<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
		</div>
		
			<h3><span>${op:message('M00643')}</span></h3> <!-- 배송비 설정 --> 
			
			<form id="listForm">
				<div class="board_write">
					<table class="board_list_table">
						<colgroup>
							<col style="width:50px;" />						
							<col style="" />						
							<col style="width:100px;" />
							<col style="width:180px;" />	
							<col style="width:180px;" />
							<col style="width:150px;" />
						</colgroup>
						<thead>
							<tr>
								<!-- <th scope="col"><input type="checkbox" id="check_all" title="체크박스" /></th> -->
								<th scope="col" rowspan="2">${op:message('M00200')}</th> <!-- 순번 --> 
								<th scope="col" rowspan="2" style="border-left: 1px solid #d5d5d5">${op:message('M00635')}</th> <!-- 배송비 정책 제목 --> 
								<th scope="col" rowspan="2" style="border-left: 1px solid #d5d5d5">${op:message('M00636')}</th> <!-- 배송 구분 --> 
								<th scope="col" colspan="2" style="border-left:1px solid #d5d5d5; border-right:1px solid #d5d5d5">${op:message('M00067')}</th> <!-- 배송비 --> 
								<th scope="col" rowspan="2">${op:message('M00168')}</th> <!-- 수정/삭제 --> 
							</tr>
							<tr>
								<!-- <th scope="col"><input type="checkbox" id="check_all" title="체크박스" /></th> -->
								<th style="border-left:1px solid #d5d5d5">${op:message('M00067')}</th> <!-- 배송비 --> 
								<th style="border-left:1px solid #d5d5d5">제주도</th> <!-- 훗카이도 --> 
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${deliveryList}" var="list" varStatus="i">
							
								
								<c:forEach items="${list.deliveryChargeList}" var="chargeList" varStatus="j">
									<c:set var="rowspan" value="" />
									<c:if test="${fn:length(list.deliveryChargeList) > 1}">
										<c:set var="rowspan">rowspan="${fn:length(list.deliveryChargeList)}"</c:set>
									</c:if>
									
									<c:choose>
										<c:when test="${j.count == 1}">
											<tr>
												<!-- <td><input type="checkbox" name="id" id="check" value="${list.deliveryId}" title="체크박스" /></td> -->
												<td ${rowspan}>${i.count}</td>
												<td ${rowspan} class="text-left" style="border-left: 1px solid #ececec">${list.title}</td>
												<td ${rowspan} style="border-left: 1px solid #ececec">${list.deliveryType == "1" ? op:message('M00637') : op:message('M00638')}</td> <!-- 묶음배송 -->  <!-- 개별배송 -->
												 
												<td class="text-right" style="border-left: 1px solid #ececec">
													${op:numberFormat(chargeList.deliveryCharge)}${op:message('M00049')} /<br/> 
												
													<c:if test="${chargeList.deliveryChargeType == 3}">
														${op:numberFormat(chargeList.deliveryFreeAmount)}${op:message('M01210')} <!-- 원 이상 구매시 무료 -->
													</c:if>
													<c:if test="${chargeList.deliveryChargeType == 2}">
														${op:message('M00639')} <!-- 유료 -->
													</c:if>
													<c:if test="${chargeList.deliveryChargeType == 1}">
														${op:message('M00448')} <!-- 무료 -->
													</c:if>
												</td>
												<td class="text-right" style="border-left: 1px solid #ececec">
													${op:numberFormat(list.deliveryExtraCharge1)}${op:message('M00049')} /<br/> 
													${op:numberFormat(list.deliveryExtraChargeFree1)}${op:message('M01210')} <!-- 원 이상 구매시 무료 -->	
												</td>
													
												<td style="border-left: 1px solid #ececec" ${rowspan}>
													<div>
														<a href="/opmanager/delivery/edit/${list.deliveryId}" class="table_btn">${op:message('M00087')}</a> <!-- 수정 --> 
														<a href="javascript:deleteCheck('${list.deliveryId}');" class="table_btn">${op:message('M00074')}</a> <!-- 삭제 --> 
													</div>
												</td>
											</tr>
										</c:when>
										<c:otherwise>
											<tr>
												<td class="text-right" style="border-left: 1px solid #ececec">
													${op:numberFormat(chargeList.deliveryCharge)}${op:message('M00049')} 
												</td>
												<td class="text-right" style="border-left: 1px solid #ececec">
													${op:numberFormat(list.deliveryExtraCharge1)}${op:message('M00049')} 	
												</td>
												<td class="text-right" style="border-left: 1px solid #ececec">
													${op:numberFormat(list.deliveryExtraCharge2)}${op:message('M00049')} 
												</td>
												<td class="text-center" style="border-left: 1px solid #ececec;border-right: 1px solid #ececec">
													<c:if test="${chargeList.deliveryChargeType == 3}">
														${op:numberFormat(chargeList.deliveryFreeAmount)}${op:message('M01210')} <!-- 원 이상 구매시 무료 -->
													</c:if>
													<c:if test="${chargeList.deliveryChargeType == 2}">
														${op:message('M00639')} <!-- 유료 -->
													</c:if>
													<c:if test="${chargeList.deliveryChargeType == 1}">
														${op:message('M00448')} <!-- 무료 -->
													</c:if>
												</td>
											</tr>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</c:forEach>	
						</tbody>
					</table>				 
				</div><!--//board_write E-->	
				<c:if test="${empty deliveryList}">
					<div class="no_content">
						${op:message('M00473')} <!-- 데이터가 없습니다. -->
					</div>
				</c:if>	
				
				<div class="btn_all">
					<!-- <div class="btn_left">
						<button type="button" id="delete_list_data" class="btn btn-dark-gray btn-sm"><span>선택삭제</span></button>
					</div> -->
					<div class="btn_right">
						<a href="/opmanager/delivery/create" class="ctrl_btn"><span>${op:message('M00088')}</span></a> <!-- 등록 --> 
					</div>
				</div> 
			</form>
					 
				 
<script type="text/javascript">
//목록데이터 - 삭제처리
$('#delete_list_data').on('click', function() {
	Common.updateListData("/opmanager/delivery/list/delete", Message.get("M00306"));	// 선택된 데이터를 삭제하시겠습니까?
});

function deleteCheck(deliveryId) {
	if (confirm(Message.get("M00642"))) {	// 해당 배송비를 삭제하시겠습니까?
		location.replace("/opmanager/delivery/delete/" + deliveryId);  
	} else {
		return;
	}
}
</script>
