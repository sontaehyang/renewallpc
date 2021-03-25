<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>

<style>
    .tip {
        color: #dc4618;
        font-size: 20px;
    }
</style>

<div class="location">
    <a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
</div>

<h3><span>${ title } UMS 설정</span></h3>
<span class="mail_code">
    <a href="/opmanager/ums/change-code/${ums.templateCode}" class="btn_right btn gray02" onclick="Common.popup(this.href, 'orderView', 500, 500, 1); return false;" title="새창">대체코드</a>
</span>
<form:form modelAttribute="ums" method="post">
    <h4><b>자동 메시징 관리</b></h4>
    <table class="board_write_table">
        <colgroup>
            <col style="width: 200px;" />
        </colgroup>
        <tr>
            <td class="label">발송 코드</td>
            <td>
                <div>
                    <form:input path="templateCode" label="발송 코드" class="required _filter seven" maxlength="50" readonly="${empty ums.templateCode ? 'false' : 'true'}" />
                </div>
            </td>
        </tr>
        <tr>
            <td class="label">발송 항목</td>
            <td>
                <div>
                    <form:input path="templateName" label="발송 항목" class="required _filter seven" maxlength="50" readonly="${empty ums.templateCode ? 'false' : 'true'}" />
                </div>
            </td>
        </tr>
        <%--<tr>
            <td class="label">발송 시기</td>
            <td>
                <div>
                    구체적인 기획 더 필요....
                </div>
            </td>
        </tr>--%>

        <tr>
            <td class="label">사용 여부</td>
            <td>
                <div>
                    <form:radiobutton path="usedFlag" value="true" label="사용"/>
                    <form:radiobutton path="usedFlag" value="false" label="미사용"/>
                </div>
            </td>
        </tr>

        <tr>
            <td class="label">야간 발송 (21 ~ 06)</td>
            <td>
                <div>
                    <form:radiobutton path="nightSendFlag" value="true" label="허용"/>
                    <form:radiobutton path="nightSendFlag" value="false" label="불가"/>
                </div>
            </td>
        </tr>
    </table>
    <br/>

    <c:set var="messageIndex" value="0" />
    <c:set var="alimTalkIndex" value="1" />
    <c:set var="pushIndex" value="2" />

    <h4><b>메시지 관리</b></h4>
    <input type="hidden" name="detailList[${messageIndex}].umsType" value="MESSAGE" />
    <input type="hidden" name="detailList[${messageIndex}].templateCode" id="messageTemplateCode" value="${ums.templateCode}"/>
    <form:hidden path="detailList[${messageIndex}].id" />
    <%--<form:hidden path="detailList[${messageIndex}].umsId" />--%>
    <table class="board_write_table">
        <colgroup>
            <col style="width: 200px;" />
        </colgroup>
        <tr>
            <td class="label">사용 여부</td>
            <td>
                <div>
                    <form:radiobutton path="detailList[${messageIndex}].usedFlag" value="true" label="사용"/>
                    <form:radiobutton path="detailList[${messageIndex}].usedFlag" value="false" label="미사용"/>
                </div>
            </td>
        </tr>
        <tr>
            <td class="label">메시지 타입</td>
            <td>
                <div>
                    <form:radiobutton path="detailList[${messageIndex}].sendType" value="sms" label="SMS" checked="checked"/>
                    <form:radiobutton path="detailList[${messageIndex}].sendType" value="lms" label="LMS"/>
                    <form:radiobutton path="detailList[${messageIndex}].sendType" value="mms" label="MMS"/>
                </div>
            </td>
        </tr>

        <tr>
            <td class="label">발송 문구</td>
            <td>
                <div>
                    <form:textarea path="detailList[${messageIndex}].message"/>
                </div>
            </td>
        </tr>
    </table>
    <br/>

    <h4><b>카카오 알림톡 관리</b></h4>
    <input type="hidden" name="detailList[${alimTalkIndex}].umsType" value="ALIM_TALK" />
    <input type="hidden" name="detailList[${alimTalkIndex}].templateCode" id="alimTalkTemplateCode" value="${ums.templateCode}"/>
    <form:hidden path="detailList[${alimTalkIndex}].id" />
    <%--<form:hidden path="detailList[${alimTalkIndex}].umsId" />--%>
    <table class="board_write_table">
        <colgroup>
            <col style="width: 200px;" />
        </colgroup>
        <tr>
            <td class="label">사용 여부</td>
            <td>
                <div>
                    <form:radiobutton path="detailList[${alimTalkIndex}].usedFlag" value="true" label="사용"/>
                    <form:radiobutton path="detailList[${alimTalkIndex}].usedFlag" value="false" label="미사용"/>
                </div>
            </td>
        </tr>
        <tr>
            <td class="label">실패시 대체발송</td>
            <td>
                <div>
                    <form:radiobutton path="detailList[${alimTalkIndex}].failProcessFlag" value="true" label="사용"/>
                    <form:radiobutton path="detailList[${alimTalkIndex}].failProcessFlag" value="false" label="미사용"/>
                    <p class="text-info text-sm">
                        * 카카오 알림톡 발송 실패시 LMS로 대체발송됩니다.
                    </p>
                </div>
            </td>
        </tr>
        <tr>
            <td class="label">
                템플릿 정보
            </td>
            <td>
                <div>
                    <c:set var="alimTalkButtonList" value="${umsConfig.detailList[alimTalkIndex].alimTalkButtonList}" scope="request"/>
                    <c:set var="detailListIndex" value="${alimTalkIndex}" scope="request"/>
                    <c:set var="templateButtonTypeList" value="${templateButtonTypeList}" scope="request"/>
                    <jsp:include page="include/alim-talk-template.jsp"/>
                </div>
            </td>
        </tr>
    </table>
    <br/>

    <h4><b>푸시 메시지 관리</b></h4>

    <input type="hidden" name="detailList[${pushIndex}].umsType" value="PUSH" />
    <input type="hidden" name="detailList[${pushIndex}].templateCode" id="pushTemplateCode" value="${ums.templateCode}"/>
    <form:hidden path="detailList[${pushIndex}].id" />
    <%--<form:hidden path="detailList[${pushIndex}].umsId" />--%>
    <table class="board_write_table">
        <colgroup>
            <col style="width: 200px;" />
        </colgroup>
        <tr>
            <td class="label">사용 여부</td>
            <td>
                <div>
                    <form:radiobutton path="detailList[${pushIndex}].usedFlag" value="true" label="사용"/>
                    <form:radiobutton path="detailList[${pushIndex}].usedFlag" value="false" label="미사용"/>
                </div>
            </td>
        </tr>

        <tr>
            <td class="label">실패시 대체발송</td>
            <td>
                <div>
                    <form:radiobutton path="detailList[${pushIndex}].failProcessFlag" value="true" label="사용"/>
                    <form:radiobutton path="detailList[${pushIndex}].failProcessFlag" value="false" label="미사용"/>
                    <p class="text-info text-sm">
                        * 푸시 발송 실패시 LMS로 대체발송됩니다.
                    </p>
                </div>
            </td>
        </tr>

        <tr>
            <td class="label">발송 문구</td>
            <td>
                <div>
                    <form:textarea path="detailList[${pushIndex}].message"/>
                </div>
            </td>
        </tr>
    </table>
    <br/>

    <p class="btn_center">
        <button type="submit" class="btn btn-active">${op:message('M00101')} <!-- 저장 --></button>
        <a type="button" class="btn btn-default" href="/opmanager/ums/list">취소</a>
    </p>

</form:form>

<page:javascript>
<script type="text/javascript">

    $(document).ready(function() {

        Common.checkedMaxStringLength('textarea', null, 1000);

        $("#ums").validator(function() {
            // OP_UMS_CONFIG에서 입력한 templateCode를 참조하는 테이블(OP_UMS_CONFIG_DETAIL)의 templateCode에 입력
            var templateCode = $("#templateCode").val();
            $("#messageTemplateCode").val(templateCode);
            $("#pushTemplateCode").val(templateCode);
        });

        $('#showAlimTalkTemplate').on('click', function() {
            Common.popup('/opmanager/ums/alimtalk/' + $("#templateCode").val() + '/list', 'alimtalkTemplate', 1850, 850, 1);
        });

    });
</script>
</page:javascript>