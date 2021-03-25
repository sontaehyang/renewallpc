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
		
				
			<div class="item_list">			
				<h3><span>현금영수증 신청내역</span></h3>
				
				<form:form modelAttribute="cashbillParam" method="get">

					<div class="board_write">		
						<table class="board_write_table" summary="현금영수증">
							<colgroup>
								<col style="width: 150px" />
								<col style="width: auto;" /> 
								<col style="width: 150px" />
								<col style="width: auto;" />
							</colgroup>
							<tbody>
								<tr>
									<td class="label">${op:message('M00011')}</td>    <!-- 검색구분 -->
									<td colspan="3">
										<div>
											<form:select path="where" title="상세검색 선택">
												<form:option value="name">이름</form:option>
												<form:option value="orderCode">주문번호</form:option>
												<form:option value="cashbillCode">발급코드</form:option>
											</form:select>
											<form:input path="query" class="w38" title="상세검색 입력" />
										</div>
									</td>
								</tr>

                                <tr>
                                    <td class="label">상태</td>
                                    <td colspan="3">
                                        <div>
                                            <span><label><input type="radio" name="cashbillStatus" value="" checked /> 전체 </label></span>
                                            <form:radiobuttons path="cashbillStatus" items="${cashbillStatus}" itemLabel="title" itemValue="code" />
                                        </div>
                                    </td>
                                </tr>
							</tbody>
						</table>
						
					</div> <!-- // board_write -->
					
					<div class="btn_all">
						<div class="btn_left">
							<button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='/opmanager/receipt/cashbill/list';">${op:message('M00047')}</button> <!-- 초기화 -->
						</div>
						<div class="btn_right">
							<button type="submit" class="btn btn-dark-gray btn-sm">${op:message('M00048')}</button> <!-- 검색 -->
						</div>
					</div>

                    <div class="count_title mt20">
                        <h5>전체 : ${op:numberFormat(pageContent.totalElements)}건
                        </h5>
                        <span>

							<span>${op:message('M00052')} <!-- 출력수 --></span>
							<form:select path="size" title="출력수 선택">
                                <form:option value="10" label="10" />
                                <form:option value="20" label="20" />
                                <form:option value="30" label="30" />
                                <form:option value="50" label="50" />
                                <form:option value="100" label="100" />
                            </form:select>

							<c:if test="${!empty itemParam.categoryId}">
                                <button type="button" id="change_ordering2" class="btn ctrl_btn " style="position:fixed;right:0; bottom:260px; z-index: 1000;">${op:message('M00791')}</button> <!-- 정렬순서변경 -->
                            </c:if>
						</span>
                    </div>
                </form:form>

				
				<div class="board_list">
					<form id="listForm">
						<table class="board_list_table" summary="리스트">
							<colgroup>
								<col style="width: 30px" />
								<col style="width: 60px;" />

							</colgroup>
							<thead>
								<tr>
									<th><input type="checkbox" id="check_all" title="체크박스" /></th>
									<th>순번</th>
									<th>주문번호</th>
                                    <th>발급구분</th>
                                    <th>과세구분</th>
                                    <th>증빙구분</th>
                                    <th>발급코드</th>
                                    <th>이름</th>
                                    <th>상품</th>
									<th>금액</th>
                                    <th>신청일</th>
                                    <th>상태</th>
									<th>처리</th>
								</tr>
							</thead>
							<tbody>
                                <c:forEach items="${pageContent.content}" var="cashbillIssue" varStatus="i">
                                    <tr>
                                        <td><input type="checkbox" name="id" value="${cashbillIssue.id}" /></td>
                                        <td>${op:numbering(pageContent, i.index)}</td>

                                        <td>
                                            <c:if test="${empty cashbillIssue.cashbill.orderCode}">
                                                -
                                            </c:if>
                                            <c:if test="${!empty cashbillIssue.cashbill.orderCode}">
                                                <a href="javascript:Manager.orderDetails('all', '0', '${cashbillIssue.cashbill.orderCode}', '1')">${cashbillIssue.cashbill.orderCode}</a>
                                            </c:if>
                                        </td>
                                        <td>${cashbillIssue.cashbillIssueType.title}</td>
                                        <td>${cashbillIssue.taxType.title}</td>
                                        <td>${cashbillIssue.cashbill.cashbillType.title}</td>
                                        <td><a href="#">${cashbillIssue.cashbill.cashbillCode}</a></td>
                                        <td>${cashbillIssue.cashbill.customerName}</td>
                                        <td>${cashbillIssue.itemName}</td>
                                        <td>${op:numberFormat(cashbillIssue.amount)}</td>


                                        <td>${op:datetime(cashbillIssue.createdDate)}</td>
                                        <td>${cashbillIssue.cashbillStatus.title}</td>
                                        <td>
                                            <c:if test="${cashbillIssue.cashbillStatus.code == 'PENDING'}">
                                                <button type="button" class="btn btn-xs btn-gradient op-btn-issue">발급</button>
                                            </c:if>
                                            <c:if test="${cashbillIssue.cashbillStatus.code == 'ISSUED'}">
                                                <button type="button" class="btn btn-xs btn-gradient op-btn-cancel">취소</button>
                                            </c:if>
                                            <c:if test="${cashbillIssue.cashbillStatus.code == 'CANCELED'}">
                                                -
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
							</tbody>
						</table>
					</form>
					
					<c:if test="${empty pageContent.content}">
					<div class="no_content">
						${op:message('M00473')} <!-- 데이터가 없습니다. -->
					</div>
					</c:if>

					<div class="btn_all">
						<div class="btn_left mb0">
							<button type="button" id="update_list_data_issue" class="btn btn-default btn-sm">선택발급</button>
						</div>
						<div class="btn_right mb0">
							<a href="/opmanager/receipt/cashbill/create" class="btn btn-active btn-sm">현금영수증 수동 발급</a>
						</div>
					</div>
					
					<page:pagination-jpa />
					
				</div>
			</div>
			
<script type="text/javascript">
$(function() {

    // 발행
    $('.op-btn-issue').on('click', function(e) {
        e.preventDefault();
        $('#check_all').prop("checked", false);
        $(this).closest("table").find('input[name=id]:enabled').prop("checked", false);
        $(this).closest("tr").find('input[name=id]').prop("checked", true);

        Common.updateListData("/opmanager/receipt/cashbill/list/issue", "현금영수증을 발급하시겠습니까?");
    });

    // 목록데이터 - 발행처리
    $('#update_list_data_issue').on('click', function() {
        Common.updateListData("/opmanager/receipt/cashbill/list/issue", "선택된 정보로 현금영수증을 발급하시겠습니까?");
    });

    // 취소
    $('.op-btn-cancel').on('click', function(e) {
        e.preventDefault();
        $('#check_all').prop("checked", false);
        $(this).closest("table").find('input[name=id]:enabled').prop("checked", false);
        $(this).closest("tr").find('input[name=id]').prop("checked", true);

        Common.updateListData("/opmanager/receipt/cashbill/list/cancel", "현금영수증을 취소하시겠습니까?");
    });



});
</script>
