<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<style>
    span.require {color: #e84700; margin-left: 5px;}
    .board_write_table td {overflow:hidden;padding:10px 15px;}
    div.item_list {width: 1100px;}
    div.config_table {width:500px;}
</style>

<div class="location">
    <a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
</div>



<form:form modelAttribute="attendance" method="post" enctype="multipart/form-data">
    <div class="item_list">
        <h3>출석체크 등록</h3>
        <div class="board_write">
            <table class="board_write_table">
                <caption>${op:message('M00746')}</caption>
                <colgroup>
                    <col style="width:170px;" />
                    <col style="width:auto;" />
                    <col style="width:170px;" />
                    <col style="width:auto;" />
                </colgroup>
                <tbody>
                    <tr>
                        <td class="label">출석체크 이벤트 기간</td>
                        <td colspan="3">
                            <div>
                                <select title="${op:message('M01392')}" name="year" ${mode == 'edit' ? 'disabled' : ''} > <!-- 시작 년도 -->
                                    <option value="${lastYear}">${lastYear}</option>
                                    <option value="${lastYear+1}">${lastYear+1}</option>
                                </select> ${op:message('M01076')}<!-- 년 -->

                                <select title="${op:message('M01393')}" name="month" ${mode == 'edit' ? 'disabled' : ''} > <!-- 시작 월 -->
                                    <c:forEach begin="1" end="12" varStatus="i">
                                        <c:if test="${i.index < 10 }">
                                            <c:set var="month">0${i.index}</c:set>
                                            <option value="0${i.index}" ${op:selected(month,attendance.month) }>0${i.index }</option>
                                        </c:if>
                                        <c:if test="${i.index >= 10 }">
                                            <option value="${i.index}" ${op:selected(i.index,attendance.month) }>${i.index }</option>
                                        </c:if>
                                    </c:forEach>
                                </select> ${op:message('M01077')}<!-- 월 -->
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>

    <div class="item_list mt30 config_table">

        <h3><span>출석체크 설정</span></h3> <!-- 상품옵션등록 -->

        <div class="board_write">
            <div class="btn_center">
                <button type="button" class="btn btn-gradient btn-sm add-attendance-config"><span>+ 설정추가</span></button>
            </div>
            <div class="item-option-table">
                <div>
                    <table class="inner-table tbl-option active">
                        <colgroup>
                            <col style="width: 70px" />
                            <col style="width: 70px" />
                            <col style="width: 150px" />
                            <col style="width: 70px" />
                        </colgroup>
                        <thead>
                        <tr>
                            <th>출석일수</th>
                            <th>연속여부</th>
                            <th>이벤트코드</th>
                            <th>삭제</th>
                        </tr>
                        </thead>
                        <tbody id="attendance-config">

                        <c:set var="configCount" value="0" />
                        <c:forEach items="${attendance.attendanceConfigs}" var="config" varStatus="i">
                            <c:set var="configCount">${optionCount + 1}</c:set>
                            <tr>
                                <td><input type="text" class="_number required" name="days" value="${config.days}" /></td>
                                <td>
                                    <select name="continueYn">
                                        <option value="Y" ${op:selected('Y', config.continueYn)}>사용</option>
                                        <option value="N" ${op:selected('N', config.continueYn)}>사용안함</option>
                                    </select>
                                </td>
                                <td>
                                    <input type="hidden" name="attendanceConfigId" value="${config.attendanceConfigId}" />
                                    <input type="text" class="required" name="eventCode" value="${config.eventCode}" />
                                </td>
                                <td><a href="#" class="btn btn-dark-gray btn-sm delete-item-option">삭제</a></td>
                            </tr>
                        </c:forEach>

                        <c:if test="${configCount == 0}">
                            <tr>
                                <td><input type="text" maxlength="2" name="days" value="${config.days}" class="_number" /></td>
                                <td>
                                    <select name="continueYn">
                                        <option value="Y" ${op:selected('Y', config.continueYn)}>사용</option>
                                        <option value="N" ${op:selected('N', config.continueYn)}>사용안함</option>
                                    </select>
                                </td>
                                <td><input type="text" name="eventCode" value="${config.eventCode}" /></td>
                                <td><a href="#" class="btn btn-dark-gray btn-sm delete-item-option">삭제</a></td>
                            </tr>
                        </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <div class="item_list mt30">
        <h3><span>상단 컨텐츠</span></h3> <!-- 상단 컨텐츠 -->

        <form:textarea path="contentTop" cols="30" rows="10" style="width: 1085px; height: 400px" class="" title="상단 컨텐츠" />
    </div>

    <div class="item_list mt30">
        <h3><span>하단 컨텐츠</span></h3> <!-- 하단 컨텐츠 -->

        <form:textarea path="contentBottom" cols="30" rows="10" style="width: 1085px; height: 400px" class="" title="하단 컨텐츠" />
    </div>

    <div id="buttons" class="tex_c mt20">
        <button type="submit" class="btn btn-active">${mode== 'create' ? op:message('M00088') : op:message('M00087')}</button>
        <a href="javascript:Link.list('${requestContext.managerUri}/attendance/list')" class="btn btn-default">${op:message('M00480')}</a>	<!-- 목록 -->
    </div>
</form:form>

<module:smarteditorInit />
<module:smarteditor id="contentTop" />
<module:smarteditor id="contentBottom" />

<script type="text/javascript">

$(function() {

    Common.addNumberComma();
    // 필수 입력항목 마커.
    //Common.displayRequireMark();

    $("#attendance").validator(function() {
        if ("${currentMonth}" > $('select[name="month"]').find('option:selected').val()) {
            if ("${lastYear}" > $('select[name="year"]').find('option:selected').val()) {
                alert('현재 진행중이거나 지나간 달은 등록할 수 없습니다.');
                return false;
            }
        }

        if ($('input[name="days"]').val() < 1) {
            alert('출석일수는 0보다 커야합니다.');
            $('input[name="days"]').focus();
            return false;
        }

        if ($('input[name="eventCode').val() == null || $('input[name="eventCode').val() == '') {
            alert('이벤트코드를 입력해 주세요.');
            $('input[name="eventCode"]').focus();
            return false;
        }

        var prevDays = '';

        var isDuplicate = false;

        $('input[name="days"]').each(function() {
            var days = $(this).val();
            var continueYn = $(this).closest('tr').find('select[name="continueYn"]').val();

            if (prevDays == days) {
                $('select[name="continueYn"]').each(function() {
                    if ($(this).val() == continueYn) {
                        isDuplicate = true;

                        return false;
                    }
                })
            }
            if (isDuplicate) {
                return false;
            }
            prevDays = days;
        });

        if (isDuplicate) {
            alert('출석일수와 연속여부가 중복되는 설정이 존재합니다.');
            return false;
        }

        if (!confirm('출석체크 정보를 저장하시겠습니까?')) {
            return false;
        }

        Common.getEditorContent("contentTop");
        Common.getEditorContent("contentBottom");
    });



    // 상품옵션 - 옵션추가
    $('.add-attendance-config').on('click', function() {
        var $templateOption = $('#attendance-config tr').eq(0).find('input[name=optionStockQuantity]');
        var isReadonly = $templateOption.prop('readonly');
        $templateOption.prop('readonly', true);

        var optionHtml = $('#attendance-config tr').eq(0).parentHtml();
        $('#attendance-config').append(optionHtml);

        $templateOption.prop('readonly', isReadonly);

        var $newItem = $('#attendance-config tr:last-child');
        $newItem.find('input').val('');
        $newItem.find('input').addClass("required");
        $newItem.find('select').each(function() {
            $(this).find('option:eq(0)').prop('selected', true);
        });
    });

    // 상품옵션 - 옵션삭제
    $('#attendance-config').on('click', '.delete-item-option', function(e) {
        e.preventDefault();

        if ($('#attendance-config tr').size() > 1) {
            $(this).closest('tr').remove();
        } else {
            $(this).closest('tr').find('input').val('');
            $(this).closest('tr').find('select').each(function() {
                $(this).find('option:eq(0)').prop('selected', true);
            });
        }
    });
});
</script>
