<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>


<!-- 본문 -->
<div class="popup_wrap">

    <h1 class="popup_title">캠페인 대상 쿠폰 리스트</h1> <!-- 쿠폰 발급하기 -->

    <!-- 본문 -->
    <div class="popup_contents">
        <form:form modelAttribute="couponParam" method="get">

            <div class="board_write">
                <form:hidden path="levelId"/>
                <form:hidden path="sendDate"/>
                <form:hidden path="sendTime"/>
                <table class="board_write_table" summary="${op:message('M01137')}">
                    <caption>${op:message('M01137')}</caption>
                    <colgroup>
                        <col style="width: 150px;" />
                        <col style="width: auto;" />
                        <col style="width: 150px;" />
                        <col style="width: auto;" />
                    </colgroup>
                    <tbody>
                        <tr>
                            <td class="label">쿠폰명</td>
                            <td>
                                <div>
                                    <form:input path="query" class="full" title="${op:message('M01140')}" /> <!-- 상세검색 입력 -->
                                </div>
                            </td>
                            <td class="label">쇼핑채널</td>
                            <td>
                                <div>
                                    <p>
                                        <form:checkbox path="couponTypes" label="PC" value="WEB" />
                                        <form:checkbox path="couponTypes" label="모바일" value="MOBILE" />
                                        <form:checkbox path="couponTypes" label="앱" value="APP" />
                                    </p>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>

            </div> <!-- // board_write -->

            <div class="btn_all">
                <div class="btn_left">
                    <button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='/opmanager/coupon/campaign-list';"><span class="glyphicon glyphicon-repeat"></span> ${op:message('M00047')}</button> <!-- 초기화 -->
                </div>
                <div class="btn_right">
                    <button type="submit" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> ${op:message('M00048')}</button> <!-- 검색 -->
                </div>
            </div>


            <div class="count_title mt20">
                <h5>
                    ${op:message('M00039')} : ${op:numberFormat(pagination.totalItems)} ${op:message('M00743')}
                </h5>
            </div>
        </form:form>

        <div class="board_list mt20">
            <form id="listForm">
                <table class="board_list_table" summary="${op:message('M01146')}">
                    <caption>${op:message('M01146')}</caption> <!-- 전체상품리스트 -->
                    <colgroup>
                        <col style="width: 40px;" />
                        <col />
                        <col />
                        <col />
                        <col />
                        <col />
                        <col />
                        <col />
                        <col style="width: 50px;"/>
                    </colgroup>
                    <thead>
                    <tr>
                        <th>번호</th>
                        <th>쿠폰종류</th>
                        <th>${op:message('M00879')}</th> <!-- 쿠폰명 -->
                        <th>할인 정보</th>
                        <th>다운로드 가능기간</th>
                        <th>적용대상</th>
                        <th>사용가능 시작일</th>
                        <th>사용가능 종료일</th>
                        <th class="label">선택</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${couponList}" var="list" varStatus="i">
                        <tr id="coupon-${list.couponId}">
                            <td>${pagination.itemNumber - i.count}</td>
                            <td>${list.couponTargetTimeTypeLabel}<br><c:if test="${list.couponOfflineFlag == 'Y'}">(오프라인쿠폰)</c:if></td>
                            <td class="coupon-name">${list.couponName}</td>
                            <td class="coupon-comment" style="display: none;">${list.couponComment}</td>
                            <td>
                                    ${op:numberFormat(list.couponPay)}
                                <c:choose>
                                    <c:when test="${list.couponPayType == '1'}">원 </c:when>
                                    <c:otherwise>
                                        % <c:if test="${list.couponDiscountLimitPrice != -1}">, 최대 ${op:numberFormat(list.couponDiscountLimitPrice)}원 까지 </c:if>
                                    </c:otherwise>
                                </c:choose>
                                    ${op:message('M00452')}
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${list.couponIssueType == '0'}">제한없음</c:when>
                                    <c:otherwise>${op:date(list.couponIssueStartDate)} ~ ${op:date(list.couponIssueEndDate)}</c:otherwise>
                                </c:choose>
                            </td>
                            <td>${list.couponTargetUserTypeLabel}</td>
                            <td>
                                    ${op:date(list.couponApplyStartDate)}
                            </td>
                            <td>
                                    ${op:date(list.couponApplyEndDate)}
                            </td>
                            <td>
                                <a href="javascript:sendCoupon('${list.couponId}')" class="btn btn-gradient btn-sm btn-select">선택</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </form>
            <c:if test="${empty couponList}">
                <div class="no_content">
                    <p>${op:message('M00591')}</p> <!-- 등록된 데이터가 없습니다. -->
                </div>
            </c:if>

            <page:pagination-manager />
            <div class="btn_all">
            </div>
        </div> <!-- // board_list -->

        <a href="javascript:self.close()" class="popup_close">창 닫기</a>

    </div> <!-- // item_list01 -->
</div>

<page:javascript>
    <script type="text/javascript">

        function sendCoupon(id) {
            var $selector = $('#coupon-'+id);

            opener.$('input[name=couponId]').val(id);
            opener.$('#couponInfo').text($selector.find('.coupon-name').text());
            opener.$('#couponComment').text($selector.find('.coupon-comment').text());

            window.self.close();
        }

        <c:if test="${ not empty openerReload }">
        $(function(){
            opener.location.reload();
        });
        </c:if>

    </script>
</page:javascript>