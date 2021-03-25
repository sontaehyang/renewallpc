<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>


<div class="location">
    <a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
</div>


<div class="board_write">
    <form:form modelAttribute="configPg" method="post">
        <h3><span>PG 설정</span></h3>
        <table class="board_write_table pg-info" summary="PG 정보">
            <caption>PG 설정</caption>
            <colgroup>
                <col style="width:170px;" />
                <col style="width: auto;" />
            </colgroup>
            <tbody>
                <tr class="all">
                    <td class="label">결제 서비스 타입 <span class="require">*</span></td>
                    <td>
                        <div>
                            <form:select path="pgType" title="결제타입">
                                <form:option value="">선택</form:option>
                                <c:forEach var="list" items="${pgTypes}">
                                    <form:option value="${list.code}">${list.title }</form:option>
                                </c:forEach>
                            </form:select>
                        </div>
                    </td>
                </tr>
                <tr class="all">
                    <td class="label">현금 영수증 서비스 타입 <span class="require">*</span></td>
                    <td>
                        <div>
                            <form:select path="cashbillServiceType" title="결제타입">
                                <form:option value="">선택</form:option>
                                <c:forEach var="list" items="${cashbillServiceTypes}">
                                    <form:option value="${list.code}">${list.title }</form:option>
                                </c:forEach>
                            </form:select>
                        </div>
                    </td>
                </tr>
                <tr class="all">
                    <td class="label">MID <span class="require">*</span></td>
                    <td>
                        <div>
                            <form:input path="mid" class="form-block required" title="MID" />
                        </div>
                    </td>
                </tr>
                <tr class="inipay lgdacom nicepay kcp">
                    <td class="label">KEY</td>
                    <td>
                        <div>
                            <form:input path="key" class="form-block" title="KEY" />
                        </div>
                    </td>
                </tr>
                <tr class="inipay">
                    <td class="label">모바일 MID</td>
                    <td>
                        <div>
                            <form:input path="mobileMid" class="form-block" title="모바일 MID" />
                        </div>
                    </td>
                </tr>
                <tr class="inipay">
                    <td class="label">모바일 KEY</td>
                    <td>
                        <div>
                            <form:input path="mobileKey" class="form-block" title="모바일 KEY" />
                        </div>
                    </td>
                </tr>
                <tr class="inipay">
                    <td class="label">SIGN</td>
                    <td>
                        <div>
                            <form:input path="sign" class="form-block" title="SIGN" />
                        </div>
                    </td>
                </tr>
                <tr class="nicepay">
                    <td class="label">취소 비밀번호</td>
                    <td>
                        <div>
                            <form:input path="cancelPassword" class="form-block" title="취소 비밀번호" />
                        </div>
                    </td>
                </tr>
                <tr class="all">
                    <td class="label">할부 정보</td>
                    <td>
                        <div>
                            <form:input path="instalment" class="form-block" title="할부 정보" />
                        </div>
                    </td>
                </tr>
                <tr class="all">
                    <td class="label">통화 정보</td>
                    <td>
                        <div>
                            <form:input path="currency" class="form-block" title="통화 정보" />
                        </div>
                    </td>
                </tr>
                <tr class="all">
                    <td class="label">가상계좌 환불 서비스<br/>가입여부</td>
                    <td>
                        <div>
                            <form:radiobutton path="useVbackRefundService" value="false" cssClass="required" title="가상계좌 환불 서비스 가입여부" label="미가입" checked="checked"/>
                            <form:radiobutton path="useVbackRefundService" value="true" cssClass="required" title="가상계좌 환불 서비스 가입여부" label="가입" />
                        </div>
                    </td>
                </tr>
                <tr class="all">
                    <td class="label">현금영수증 자진발급 기능<br/>사용여부</td>
                    <td>
                        <div>
                            <form:radiobutton path="useAutoCashReceipt" value="false" cssClass="required" title="현금영수증 자진발급 기능 사용여부" label="미사용" checked="checked"/>
                            <form:radiobutton path="useAutoCashReceipt" value="true" cssClass="required" title="현금영수증 자진발급 기능 사용여부" label="사용" />
                        </div>
                    </td>
                </tr>
                <tr class="all">
                    <td class="label">실시간 계좌이체 부분 취소<br/>사용여부</td>
                    <td>
                        <div>
                            <form:radiobutton path="realtimePartcancelFlag" value="false" cssClass="required" title="실시간 계좌이체 부분 취소 사용여부" label="미사용" checked="checked"/>
                            <form:radiobutton path="realtimePartcancelFlag" value="true" cssClass="required" title="실시간 계좌이체 부분 취소 사용여부" label="사용" />
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>

        <div class="item_list mt30">
            <br/>
            <h3><span>네이버페이(주문형) 설정</span></h3>
            <table class="board_write_table" summary="네이버페이(주문형) 사용 여부">
                <caption>PG 설정</caption>
                <colgroup>
                    <col style="width:150px;" />
                    <col style="width: auto;" />
                </colgroup>
                <tbody>

                <tr>
                    <td class="label">사용여부</td>
                    <td>
                        <div>
                            <form:radiobutton path="useNpayOrder" value="false" cssClass="required" title="네이버페이(주문형) 사용여부" label="미사용" checked="checked"/>
                            <form:radiobutton path="useNpayOrder" value="true" cssClass="required" title="네이버페이(주문형) 사용여부 " label="사용" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="label">MID</td>
                    <td>
                        <div>
                            <form:input path="npayMid" class="form-block npay-order" title="네이버페이 MID" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="label">KEY</td>
                    <td>
                        <div>
                            <form:input path="npayKey" class="form-block npay-order" title="네이버페이 KEY" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="label">LOG KEY</td>
                    <td>
                        <div>
                            <form:input path="npayLogKey" class="form-block npay-order" title="네이버페이 로그" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="label">BUTTON KEY</td>
                    <td>
                        <div>
                            <form:input path="npayButtonKey" class="form-block npay-order" title="네이버페이 버튼" />
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>

        <div class="item_list mt30">
            <br/>
            <h3><span>네이버페이(결제형) 설정</span></h3>
            <table class="board_write_table" summary="네이버페이(결제형) 사용 여부">
                <caption>PG 설정</caption>
                <colgroup>
                    <col style="width:150px;" />
                    <col style="width: auto;" />
                </colgroup>
                <tbody>
                    <tr>
                        <td class="label">사용여부</td>
                        <td>
                            <div>
                                <form:radiobutton path="useNpayPayment" value="false" cssClass="required" title="네이버페이(결제형) 사용여부" label="미사용" checked="checked"/>
                                <form:radiobutton path="useNpayPayment" value="true" cssClass="required" title="네이버페이(결제형) 사용여부 " label="사용" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="label">CLIENT ID</td>
                        <td>
                            <div>
                                <form:input path="npayClientId" class="form-block npay-payment" title="네이버페이(결제형) CLIENT ID" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="label">CLIENT SECRET</td>
                        <td>
                            <div>
                                <form:input path="npayClientSecret" class="form-block npay-payment" title="네이버페이(결제형) CLIENT SECRET" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="label">PARTNER ID</td>
                        <td>
                            <div>
                                <form:input path="npayPartnerId" class="form-block npay-payment" title="네이버페이(결제형) PARTNER ID" />
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>

        <div class="item_list mt30">
            <br/>
            <h3><span>에스크로 설정</span></h3>
            <table class="board_write_table" summary="에스크로 사용 여부">
                <caption>PG 설정</caption>
                <colgroup>
                    <col style="width:150px;" />
                    <col style="width: auto;" />
                </colgroup>
                <tbody>
                    <tr>
                        <td class="label">사용여부</td>
                        <td>
                            <div>
                                <form:radiobutton path="useEscroow" value="false" cssClass="required" title="에스크로 사용여부" label="미사용" checked="checked"/>
                                <form:radiobutton path="useEscroow" value="true" cssClass="required" title="에스크로 사용여부 " label="사용" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="label">MID</td>
                        <td>
                            <div>
                                <form:input path="escroowMid" class="form-block escroow" title="에스크로 MID" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="label">KEY</td>
                        <td>
                            <div>
                                <form:input path="escroowKey" class="form-block escroow" title="에스크로 KEY" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="label">모바일 MID</td>
                        <td>
                            <div>
                                <form:input path="mobileEscroowMid" class="form-block escroow" title="에스크로 모바일 MID" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="label">모바일 KEY</td>
                        <td>
                            <div>
                                <form:input path="mobileEscroowKey" class="form-block escroow" title="에스크로 모바일 KEY" />
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>

        <div class="tex_c mt20">
            <button type="submit" class="btn btn-active">저장</button>
        </div>

    </form:form>

</div><!--//board_write E-->

<page:javascript>
<script type="text/javascript">

    $(function() {

        initDisplay($('#pgType').val());
        displayEvent();

        var confirmMessage = 'PG 설정을 저장 하시겠습니까?';

        $('#configPg').validator(function() {

            if (!confirm(confirmMessage)) {
                return false;
            }

        });
    });

    function displayEvent() {
        $('#pgType').on('change', function(){
            initDisplay($(this).val());
        });
    }

    function initDisplay(pgType) {

        pgType = pgType.toLowerCase();

        $('.pg-info tr').hide();
        $('.pg-info').find('.all').show();

        if (typeof pgType != 'undefined' && pgType != '') {
            $('.pg-info').find('.' + pgType).show();
        }
    }

</script>
</page:javascript>