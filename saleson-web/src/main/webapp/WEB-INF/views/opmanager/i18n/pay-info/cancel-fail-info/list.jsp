<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>


<div class="location">
    <a href="#"></a>&gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
</div>
<!-- 2022.06.23 -->
<h3>결제취소 실패 현황</h3>
<form:form modelAttribute="orderCancelFailParam" method="get">
    <div class="board_write">
        <table class="board_write_table">
            <caption>결제취소 실패 현황</caption>
            <colgroup>
                <col style="width:150px;" />
                <col style="width:*;" />
            </colgroup>
            <tbody>
            <tr>
                <td class="label">키워드검색</td> <!-- 검색구분 -->
                <td>
                    <div>
                        <form:select path="where" title="${op:message('M00468')}"> <!-- 키워드선택 -->
                            <form:option value="orderCode" label="주문번호" />
                            <form:option value="pgKey" label="PG 결제번호" />
                        </form:select>
                        <form:input type="text" path="query" class="three" title="${op:message('M00021')} " />  <!-- 검색어 입력 -->
                    </div>
                </td>
            </tr>
            <c:if test="${configPg.useNpayPayment == true}">
                <tr>
                    <td class="label">PG서비스 타입</td>
                    <td>
                        <div>
                            <form:radiobutton path="pgServiceType" value="" checked="checked" label="전체" />
                            <form:radiobutton path="pgServiceType" value="${configPg.pgType}" label="${configPg.pgType}" />
                            <form:radiobutton path="pgServiceType" value="naverpay" label="naverpay" />
                        </div>
                    </td>
                </tr>
            </c:if>

            <tr>
                <td class="label">결제방법</td>
                <td>
                    <div>
                        <form:radiobutton path="approvalType" value="" checked="checked" label="전체" />
                        <form:radiobutton path="approvalType" value="bank" label="온라인입금" />
                        <form:radiobutton path="approvalType" value="card" label="신용카드" />
                        <form:radiobutton path="approvalType" value="vbank" label="가상계좌" />
                        <form:radiobutton path="approvalType" value="point" label="포인트" />
                    </div>
                </td>
            </tr>
            <tr>
                <div>
                    <td class="label">주문 일자검색</td> <!-- 작성일 -->
                    <td>
                        <div>
                            <span class="datepicker"><form:input type="text" path="searchStartDate" class="datepicker _number" title="${op:message('M00507')}" value="${today}" /></span> <!-- 시작일 -->
                            <span class="wave">~</span>
                            <span class="datepicker"><form:input type="text" path="searchEndDate" class="datepicker _number" title="${op:message('M00509')}" value="${today}" /></span> <!-- 종료일 -->
                            <span class="day_btns">
                                <a href="#" class="btn_date today">${op:message('M00026')}</a>
                                <a href="#" class="btn_date week">${op:message('M00222')}</a>
                                <a href="#" class="btn_date month1">${op:message('M00029')}</a>
                                <a href="#" class="btn_date month3">세달</a>
                                <a href="#" class="btn_date all">${op:message('M00039')}</a>
                            </span>
                        </div>
                    </td>
                </div>
            </tr>
            </tbody>
        </table>
    </div> <!-- // board_write -->

    <!-- 버튼시작 -->
    <div class="btn_all">
        <div class="btn_left">
            <button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='/opmanager/pay-info/cancel-fail-info/list'"><span>${op:message('M00047')}</span></button> <!-- 초기화 -->
        </div>
        <div class="btn_right">
            <button type="submit" class="btn btn-dark-gray btn-sm"><span>${op:message('M00048')}</span></button><!-- 검색 -->
        </div>
    </div>
    <!-- 버튼 끝-->

    <div class="count_title mt20">
        <h5>전체 : ${op:numberFormat(pageContent.totalElements)} 건 조회</h5>
        <span>출력수
					<form:select path="itemsPerPage" title="${op:message('M00054')}${op:message('M00052')}"
                                 onchange="$('form#orderCancelFailParam').submit();"> <!-- 화면 출력수 -->
                        <form:option value="10" label="${op:message('M00240')}" />  <!-- 10개 출력 -->
                        <form:option value="20" label="${op:message('M00241')}" />  <!-- 20개 출력 -->
                        <form:option value="50" label="${op:message('M00242')}" />  <!-- 50개 출력 -->
                        <form:option value="100" label="${op:message('M00243')}" /> <!-- 100개 출력 -->
                    </form:select>
				</span>
    </div>
</form:form>

<form action="/opmanager/pay-info/cancel-fail-info/list" method="get" id="listForm">
    <%--    <input type="hidden" value="${list.qnaId}">--%>
    <div class="board_write">
        <table class="board_list_table" summary="결제취소 실패 현황">
            <caption>결제취소 실패 현황</caption>
            <colgroup>
                <col style="width:3%;" />
                <col style="width:8%;" />
                <col style="width:15%;" />
                <col style="width:15%;" />
                <col style="width:10%;" />
                <col style="width:25%;" />
                <col style="width:10%;" />
                <col style="width:10%;" />
            </colgroup>
            <thead>
            <tr>
                <th scope="col">순번</th> <!-- 체크박스 -->
                <th scope="col">주문번호</th>
                <th scope="col">PG 결제번호</th>
                <th scope="col">결제수단</th>
                <th scope="col">결제일</th>
                <th scope="col">취소금액</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${pageContent.content}" var="content" varStatus="i">
                <tr>
                    <td>${op:numbering(pageContent, i.index)}</td>
                    <td>${content.orderCode}</td>
                    <td>${content.pgKey}</td>
                    <td>${content.approvalType}</td>
                    <td>${content.payDate}</td>
                    <td>${op:numberFormat(content.cancelAmount) }원</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div><!--// board_write E-->

    <c:if test="${empty pageContent.content}">
        <div class="no_content">
                ${op:message('M00473')} <!-- 데이터가 없습니다. -->
        </div>
    </c:if>

</form><br/>
<div style="text-align:center;">
    <page:pagination-jpa />
</div>

<span>
		</span>

<div style="display: none;">
    <span id="today">${today}</span>
    <span id="week">${week}</span>
    <span id="month1">${month1}</span>
    <span id="month3">${month3}</span>
</div>

<script type="text/javascript">

    $(".btn_date").on('click',function(){

        var $id = $(this).attr('class').replace('btn_date ','');		// id[0] : type, id[1] : value

        if ($id == 'all') {

            $("input[type=text]",$(this).parent().parent()).val('');

        } else {

            var today = $("#today").text();

            var date1 = '';
            var date2 = '';

            if ($id == 'today') {
                date1 = today;
                date2 = today;
            } else {
                date1 = $("#"+$id).text();
                date2 = today;
            }

            $("input[type=text]",$(this).parent().parent()).eq(0).val(date1);
            $("input[type=text]",$(this).parent().parent()).eq(1).val(date2);

        }
    });
</script>