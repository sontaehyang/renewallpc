<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="op" uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<h1 class="popup_title">${op:message('MENU_13201')} 상세</h1>


<!-- 본문 -->
<div class="popup_wrap">

    <div class="popup_contents">
        <p class="text-info text-sm">
            * 예약발송은 메시지가 발송된 후에 대상 조회가 가능합니다.
        </p>

        <table class="board_write_table">
            <colgroup>
                <col style="width: 200px;"/>
                <col style="width: auto;"/>
                <col style="width: 200px;"/>
                <col style="width: auto;"/>
            </colgroup>

            <tbody>
                <tr>
                    <td class="label">캠페인 제목</td>
                    <td colspan="3"><div>${campaign.title}</div></td>
                </tr>
                <tr>
                    <td class="label">캠페인 내용</td>
                    <td colspan="3"><div>${op:nl2br(campaign.content)}</div></td>
                </tr>
                <tr>
                    <td class="label">발송수단</td>
                    <td>
                        <div>
                            <c:choose>
                                <c:when test="${campaign.sendType == '0'}">
                                    문자
                                </c:when>
                                <c:when test="${campaign.sendType == '1'}">
                                    PUSH
                                </c:when>
                                <c:when test="${campaign.sendType == '2'}">
                                    PUSH 우선
                                </c:when>
                            </c:choose>
                        </div>
                    </td>
                    <td class="label">쿠폰</td>
                    <td>
                        <div>
                            ${couponName}
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="label">URL</td>
                    <td colspan="3">
                        <c:forEach var="url" items="${campaign.urlList}" varStatus="j">
                            <div>${url.contents}</div>
                        </c:forEach>
                    </td>
                </tr>
                <tr>
                    <td class="label" rowspan="5">GA 캠페인</td>
                    <td class="label">utm_source</td>
                    <td colspan="2"><div>${campaign.utmSource}</div></td>
                </tr>
                <tr>
                    <td class="label">utm_medium</td>
                    <td colspan="2"><div>${campaign.utmMedium}</div></td>
                </tr>
                <tr>
                    <td class="label">utm_campaign</td>
                    <td colspan="2"><div>${campaign.utmCampaign}</div></td>
                </tr>
                <tr>
                    <td class="label">utm_item</td>
                    <td colspan="2"><div>${campaign.utmItem}</div></td>
                </tr>
                <tr>
                    <td class="label">utm_content</td>
                    <td colspan="2"><div>${campaign.utmContent}</div></td>
                </tr>
                <tr>
                    <td class="label">검색어 조건(이름,아이디)</td>
                    <td colspan="3"><div>${searchWhere}</div></td>
                </tr>
                <tr>
                    <td class="label">검색어</td>
                    <td><div>${campaign.query}</div></td>
                </tr>
                <tr>
                    <td class="label">회원그룹</td>
                    <td>
                        <div>
                            ${groupName}
                        </div>
                    </td>
                    <td class="label">회원등급</td>
                    <td>
                        <div>
                            ${levelName}
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="label">가입이후 구매액</td>
                    <td>
                        <div>
                            <c:if test="${campaign.startOrderAmount1 > 0}">
                                ${op:numberFormat(campaign.startOrderAmount1)}원
                            </c:if>
                            <span class="wave">~</span>
                            <c:if test="${campaign.endOrderAmount1 > 0}">
                                ${op:numberFormat(campaign.endOrderAmount1)}원
                            </c:if>
                        </div>
                    </td>
                    <td class="label">최근 3개월간 구매액</td>
                    <td>
                        <div>
                            <c:if test="${campaign.startOrderAmount2 > 0}">
                                ${op:numberFormat(campaign.startOrderAmount2)}원
                            </c:if>
                            <span class="wave">~</span>
                            <c:if test="${campaign.endOrderAmount2 > 0}">
                                ${op:numberFormat(campaign.endOrderAmount2)}원
                            </c:if>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="label">최종 방문일(미접속)</td>
                    <td colspan="3">
                        <div>
                            <c:if test="${!empty campaign.lastLoginDate}">
                                ${op:datetime(campaign.lastLoginDate)}일 이후
                            </c:if>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="label">장바구니 상품개수</td>
                    <td>
                        <div>
                            <c:if test="${campaign.cartCount > 0}">
                                ${op:numberFormat(campaign.cartCount)}개
                            </c:if>
                        </div>
                    </td>
                    <td class="label">장바구니 상품 총액</td>
                    <td>
                        <div>
                            <c:if test="${campaign.startCartAmount > 0}">
                                ${op:numberFormat(campaign.startCartAmount)}원
                            </c:if>
                            <span class="wave">~</span>
                            <c:if test="${campaign.endCartAmount > 0}">
                                ${op:numberFormat(campaign.endCartAmount)}원
                            </c:if>
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>

</div><!--// board_write E-->

<c:if test="${campaign.status != '0'}">
    <form:form modelAttribute="campaignDto" cssClass="opmanager-search-form clear" method="get">
        <form:hidden path="size"/>


            <div class="popup_contents">

                <h4><span>발송 대상</span></h4>

                <div class="count_title mt20">
                    <h5>
                        전체 : ${op:numberFormat(pageContent.totalElements)}건 조회
                    </h5>
                    <span>
                        <select name="displayCount" id="displayCount" title="${op:message('M00239')} "> <!-- ~개 출력 -->
                            <option value="10">${op:message('M00240')}</option>
                            <option value="20">${op:message('M00241')}</option>
                            <option value="50">${op:message('M00242')}</option>
                            <option value="100">${op:message('M00243')} </option>
                        </select>
                    </span>
                </div>

                <table class="board_list_table">
                    <colgroup>
                        <col style="width:80px"/>
                        <col style="width:100px" />
                        <col style="width:20px;"/>
                        <col style="width:20px;"/>
                        <col style="width:20px;"/>
                        <col style="width:20px;"/>
                        <col style="width:20px;"/>
                        <col style="width:20px;"/>
                        <col style="width:20px;"/>
                        <col style="width:20px"/>
                        <col style="width:20px;"/>
                        <col style="width:20px;"/>
                        <col style="width:40px;"/>
                        <col style="width:20px;"/>
                        <col style="width:20px;"/>
                    </colgroup>
                    <thead>
                    <tr>
                        <th scope="col" rowspan="2">이름</th>
                        <th scope="col" rowspan="2">전화번호</th>
                        <th scope="col" colspan="2">SMS</th>
                        <th scope="col" colspan="2">MMS</th>
                        <th scope="col" colspan="2">KAKAO</th>
                        <th scope="col" colspan="3">PUSH</th>
                        <th scope="col" rowspan="2">링크<br />진입</th>
                        <th scope="col" rowspan="2">이벤트<br />참여</th>
                        <th scope="col" colspan="2">구매연결</th>
                    </tr>
                    <tr>
                        <th scope="col">발송</th>
                        <th scope="col">성공</th>
                        <th scope="col">발송</th>
                        <th scope="col">성공</th>
                        <th scope="col">발송</th>
                        <th scope="col">성공</th>
                        <th scope="col">발송</th>
                        <th scope="col">성공</th>
                        <th scope="col">수신</th>
                        <th scope="col">건수</th>
                        <th scope="col">금액</th>
                    </tr>
                    </thead>
                    <tbody>

                        <c:forEach items="${pageContent.content}" var="user" varStatus="i">
                            <tr>
                                <td><div>${user.userName}</div></td>
                                <td><div>${user.phoneNumber}</div></td>
                                <td>${op:numberFormat(user.smsSent == null ? 0 : user.smsSent)}</td>
                                <td>${op:numberFormat(user.smsSuccess == null ? 0 : user.smsSuccess)}</td>
                                <td>${op:numberFormat(user.mmsSent == null ? 0 : user.mmsSent)}</td>
                                <td>${op:numberFormat(user.mmsSuccess == null ? 0 : user.mmsSuccess)}</td>
                                <td>${op:numberFormat(user.kakaoSent == null ? 0 : user.kakaoSent)}</td>
                                <td>${op:numberFormat(user.kakaoSuccess == null ? 0 : user.kakaoSuccess)}</td>
                                <td>${op:numberFormat(user.pushSent == null ? 0 : user.pushSent)}</td>
                                <td>${op:numberFormat(user.pushSuccess == null ? 0 : user.pushSuccess)}</td>
                                <td>${op:numberFormat(user.pushReceive == null ? 0 : user.pushReceive)}</td>
                                <td>${op:numberFormat(user.redirection == null ? 0 : user.redirection)}</td>
                                <td>0</td>
                                <td>${op:numberFormat(user.orderCount == null ? 0 : user.orderCount)}</td>
                                <td>${op:numberFormat(user.orderAmount == null ? 0 : user.orderAmount)}</td>
                            </tr>
                        </c:forEach>

                    </tbody>
                </table>
            </div><!--//popup_contents E-->

    </form:form>
</c:if>

<page:pagination-jpa />

<div class="btn_all">
    <div class="btn_center mb0">
        <a href="javascript:self.close();" class="btn btn-active">확인</a>
    </div>
</div>

<script type="text/javascript">

    $(function () {
        displayChange();
        displaySelected();
    });

    function displayChange() {
        $("#displayCount").on('change', function () {
            $("#size").val($(this).val());
            $('#campaignDto').submit();
        });
    }

    function displaySelected() {
        $("#displayCount").val($("#size").val());
    }
</script>
