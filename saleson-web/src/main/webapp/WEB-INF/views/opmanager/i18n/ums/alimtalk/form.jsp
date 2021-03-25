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


<div class="popup_wrap">
    <h1 class="popup_title">알림톡 설정</h1>
    <div class="popup_contents pb0">
        <form:form modelAttribute="vendor" method="post">
            <form:hidden path="id" value="${saleson.id}" />
            <form:hidden path="templateCode" value="${templateCode}" />

            <c:set var="templateButtonTypeList" value="${templateButtonTypeList}" scope="request"/>
            <table class="board_write_table">
                <colgroup>
                    <col style="width: 100px;" />
                    <col style="width: auto;" />
                    <col style="width: 100px;" />
                    <col style="width: auto;" />
                </colgroup>
                <tbody>
                    <tr>
                        <td class="label">승인 코드</td>
                        <td colspan="3">
                            <div>
                                <c:choose>
                                    <c:when test="${empty vendor.applyCode}">
                                        <form:input path="applyCode" label="승인 코드" title="승인 코드" class="required _filter seven" maxlength="7" />
                                    </c:when>
                                    <c:otherwise>
                                        <form:hidden path="applyCode" />
                                        ${vendor.applyCode}
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="label">Vendor<br/>템플릿 이름</td>
                        <td>
                            <div>
                                <form:input path="title" label="템플릿 이름" title="템플릿 이름" class="required _filter full" maxlength="20" value="${vendor.title}" />
                            </div>
                        </td>
                        <td class="label">리뉴올PC<br/>템플릿 이름</td>
                        <td>
                            <div>
                                ${saleson.title}
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="label">Vendor<br/>템플릿 내용</td>
                        <td>
                            <div>
                                <form:textarea path="content" title="템플릿 내용" style="height:150px;" />
                            </div>
                        </td>
                        <td class="label">리뉴올PC<br/>템플릿 내용</td>
                        <td>
                            <div>
                                ${op:nl2br(saleson.content)}
                            </div>
                        </td>
                    </tr>
                    <c:if test="${!empty vendor.applyCode}">
                        <tr>
                            <td class="label">Vendor<br/>검수 상태</td>
                            <td>
                                <div>
                                    Vendor [${vendor.inspStatusTitle}(${vendor.inspStatus})]
                                    <form:hidden path="inspStatus" title="검수 상태" />

                                    <!-- 검수상태가 등록(REG)인 경우에만 노출  -->
                                    <c:if test="${vendor.inspStatus == 'REG'}">
                                        <button type="button" class="btn btn-default btn-sm" id="verifyAlimTalkTemplate">검수 요청</button>
                                    </c:if>
                                </div>
                            </td>
                            <td class="label">리뉴올PC<br/>검수 상태</td>
                            <td>
                                <div>
                                    리뉴올PC [${saleson.inspStatusTitle}(${saleson.inspStatus})]
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="label">Vendor<br/>템플릿 상태</td>
                            <td>
                                <div>
                                    Vendor [${vendor.statusTitle}(${vendor.status})]
                                    <form:hidden path="status" title="템플릿 상태" />
                                </div>
                            </td>
                            <td class="label">리뉴올PC<br/>템플릿 상태</td>
                            <td>
                                <div>
                                    리뉴올PC [${saleson.statusTitle}(${saleson.status})]
                                </div>
                            </td>
                        </tr>
                    </c:if>
                    <tr>
                        <td class="label">Vendor<br/>버튼 정보</td>
                        <td>
                            <div>
                                <c:set var="vendorButtons" value="${vendor.buttons}" scope="request"/>
                                <jsp:include page="include/alim-talk-template-button.jsp"/>
                            </div>
                        </td>
                        <td class="label">리뉴올PC<br/>버튼 정보</td>
                        <td style="vertical-align: top;">
                            <div>
                                <c:set var="salesonButtons" value="${saleson.buttons}" scope="request"/>
                                <jsp:include page="include/alim-talk-template-button-view.jsp"/>
                            </div>
                        </td>
                    </tr>
                    <c:if test="${!empty vendor.applyCode}">
                        <tr>
                            <td class="label">문의 내용</td>
                            <td colspan="3">
                                <c:set var="templateComments" value="${vendor.comments}" scope="request"/>
                                <jsp:include page="include/alim-talk-template-comment.jsp"/>
                            </td>
                        </tr>
                    </c:if>
                </tbody>
            </table>

            <div class="check-all-item-notice-label" style="display: block;">
                * 리뉴올PC의 정보와 불일치 할 경우 <strong>'동기화'</strong> 를 실행 해 주세요.<br/>
                * 저장과 동시에 Vender <strong>'템플릿 등록 요청'</strong> 또는 <strong>'템플릿 수정 요청'</strong>이 진행이 됩니다.
            </div>

            <br/>

            <p class="btn_center">
                <c:if test="${!empty vendor.applyCode}">
                    <button type="button" class="btn btn-default" id="syncAlimTalkTemplate">동기화</button>
                </c:if>
                <!-- 최초 등록 또는 검수상태가 등록(REG), 반려(REJ)인 경우에만 노출  -->
                <c:if test="${empty vendor.applyCode || vendor.inspStatus == 'REG' || vendor.inspStatus == 'REJ'}">
                    <button type="submit" class="btn btn-active">${op:message('M00101')} <!-- 저장 --></button>
                </c:if>
                <a type="button" class="btn btn-default" href="/opmanager/ums/alimtalk/${templateCode}/list">목록</a>
            </p>
        </form:form>
    </div>
</div>

<page:javascript>
    <script type="text/javascript">

        var $ALIM_TALK_TEMPLATE_BUTTON_AREA = $('#alimTalkTemplateButtonArea');

        $(document).ready(function() {
            var applyCode = $("#applyCode").val();
            var templateCode = $("#templateCode").val();

            // 버튼 폼 readOnly 체크
            $.each($ALIM_TALK_TEMPLATE_BUTTON_AREA.find('select[name="alim_talk_template_button_type"]'), function() {
                var type = $(this).val();
                var isReadOnly = !(type == 'AL' || type == 'WL');
                var $parent = $(this).closest('tr');

                $parent.find('input[name="alim_talk_template_button_link1"]').prop('readonly', isReadOnly);
                $parent.find('input[name="alim_talk_template_button_link2"]').prop('readonly', isReadOnly);
                if (isReadOnly) {
                    $parent.find('input[name="alim_talk_template_button_link1"]').val('');
                    $parent.find('input[name="alim_talk_template_button_link2"]').val('');
                }
            });

            Common.checkedMaxStringLength('textarea', null, 1000);

            $("#vendor").validator(function() {
                makeAlimTalkTemplateButtonForm();
            });

            $('#syncAlimTalkTemplate').on('click', function() {
                if (confirm('Vendor -> 리뉴올PC 동기화를 진행하시겠습니까?')) {
                    $.post("/opmanager/ums/alimtalk/synchronization", $("#vendor").serialize(), function (response) {
                        if (response.isSuccess) {
                            alert('Vendor -> 리뉴올PC 성공적으로 동기화 완료되었습니다.');
                            location.reload();
                        } else {
                            alert(response.errorMessage);
                        }
                    });
                }
            });

            $('#verifyAlimTalkTemplate').on('click', function() {
                if (confirm('Vendor에 검수 요청하시겠습니까?')) {
                    if (applyCode == null || applyCode == '') {
                        return false;
                    }

                    $.post("/opmanager/ums/alimtalk/verify", $("#vendor").serialize(), function(response) {
                        if (response.isSuccess) {
                            alert('Vendor에 성공적으로 검수 요청하었습니다.');
                            location.reload();
                        } else {
                            alert(response.errorMessage);
                        }
                    });
                }
            });

            $('#commentAlimTalkTemplate').on('click', function() {
                Common.popup('/opmanager/ums/alimtalk/popup/' + applyCode + '/comment/create', 'alimtalkComment', 550, 650, 1);
            });

            $('#addAlimTalkTemplateButton').on('click', function() {
                addAlimTalkTemplateButton();
            });

            $ALIM_TALK_TEMPLATE_BUTTON_AREA.on('click', '.remove-alim-talk-template-button',function() {
                $(this).closest('tr').remove();
            });

            $ALIM_TALK_TEMPLATE_BUTTON_AREA.on('change', 'select[name="alim_talk_template_button_type"]',function() {
                var type = $(this).val();
                var isReadOnly = !(type == 'AL' || type == 'WL');
                var $parent = $(this).closest('tr');

                $parent.find('input[name="alim_talk_template_button_link1"]').prop('readonly', isReadOnly);
                $parent.find('input[name="alim_talk_template_button_link2"]').prop('readonly', isReadOnly);
                if (isReadOnly) {
                    $parent.find('input[name="alim_talk_template_button_link1"]').val('');
                    $parent.find('input[name="alim_talk_template_button_link2"]').val('');
                }
            });

        });

        function addAlimTalkTemplateButton() {

            var template = $('#addAlimTalkTemplateButtonTemplate').html(),
                maxButton = 5,
                length = $ALIM_TALK_TEMPLATE_BUTTON_AREA.find('tr').length;

            if (maxButton == length) {
                alert('알림톡 버튼은 최대'+maxButton+'개 입니다.');
                return false;
            }

            $ALIM_TALK_TEMPLATE_BUTTON_AREA.append(template);
        }

        function makeAlimTalkTemplateButtonForm() {
            var template = $('#alimTalkTemplateButtonTemplate').html(),
                $form = $('#alimTalkTemplateButtonFormArea');

            var html = '';

            $ALIM_TALK_TEMPLATE_BUTTON_AREA.find('tr').each(function(i){
                var $button = $ALIM_TALK_TEMPLATE_BUTTON_AREA.find('tr').eq(i);
                var id = $button.find('input[name="alim_talk_template_button_id"]').val();
                var type = $button.find('select[name="alim_talk_template_button_type"]').val();
                var name = $button.find('input[name="alim_talk_template_button_name"]').val();
                var link1 = $button.find('input[name="alim_talk_template_button_link1"]').val();
                var link2 = $button.find('input[name="alim_talk_template_button_link2"]').val();

                var rawhtml = template;

                rawhtml = rawhtml.replaceAll('{{buttonIndex}}', i)
                    .replaceAll('{{id}}', id)
                    .replaceAll('{{ordering}}', i)
                    .replaceAll('{{type}}', type)
                    .replaceAll('{{name}}', name)
                    .replaceAll('{{link1}}', link1)
                    .replaceAll('{{link2}}', link2);

                html += rawhtml+'\n';
            });

            $form.html(html);
        }
    </script>
    <script type="text/html" id="alimTalkTemplateButtonTemplate">
        <input type="hidden" name="buttons[{{buttonIndex}}].id" value="{{id}}"/>
        <input type="hidden" name="buttons[{{buttonIndex}}].ordering" value="{{ordering}}"/>
        <input type="hidden" name="buttons[{{buttonIndex}}].type" value="{{type}}"/>
        <input type="hidden" name="buttons[{{buttonIndex}}].name" value="{{name}}"/>
        <input type="hidden" name="buttons[{{buttonIndex}}].link1" value="{{link1}}"/>
        <input type="hidden" name="buttons[{{buttonIndex}}].link2" value="{{link2}}"/>

    </script>
    <script type="text/html" id="addAlimTalkTemplateButtonTemplate">
        <tr>
            <td>
                <input type="hidden" name="alim_talk_template_button_ordering"
                       title="알림톡 템플릿 버튼 노출순서" value="0"/>
                <input type="hidden" name="alim_talk_template_button_id"
                       title="알림톡 템플릿 버튼 ID" value=""/>
                <div>
                    -
                </div>
            </td>
            <td>
                <select name="alim_talk_template_button_type"
                        class="required" title="알림톡 템플릿 버튼 타입">
                    <option value="">선택</option>
                    <c:forEach var="code" items="${templateButtonTypeList}">
                        <option value="${code.code}">[${code.code}] ${code.title}</option>
                    </c:forEach>
                </select>
            </td>
            <td>
                <input type="text" name="alim_talk_template_button_name"  class="form-block required" title="알림톡 템플릿 버튼 이름"/>
            </td>
            <td>
                <input type="text" name="alim_talk_template_button_link1" class="form-block" title="알림톡 템플릿 버튼 링크1"/>
            </td>
            <td>
                <input type="text" name="alim_talk_template_button_link2" class="form-block" title="알림톡 템플릿 버튼 링크2"/>
            </td>
            <td>
                <button type="button" class="btn btn-default btn-sm remove-alim-talk-template-button">
                    제거
                </button>
            </td>
        </tr>
    </script>
</page:javascript>