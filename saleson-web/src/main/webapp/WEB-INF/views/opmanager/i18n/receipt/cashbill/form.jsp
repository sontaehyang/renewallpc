<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>
<%@ taglib prefix="daum"	tagdir="/WEB-INF/tags/daum" %>
<style>
    span.require {color: #e84700; margin-left: 5px;}

</style>

<div class="location">
    <a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
</div>



<form:form modelAttribute="cashbillIssue" method="post" enctype="multipart/form-data">
    <div class="item_list">

        <h3>현금영수증 수동 발급</h3>
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
                        <td class="label">발행구분</td>
                        <td colspan="3">
                            <div>
                                <c:forEach items="${cashbillTypes}" var="cashbillType" varStatus="i">
                                    <c:if test="${cashbillType.code != 'NONE'}">
                                        <form:radiobutton path="cashbill.cashbillType" value="${cashbillType.code}" id="cashbill_${i.count}" label=" ${cashbillType.title}" checked="${cashbillType.code == 'BUSINESS' ? 'checked' : ''}"/>
                                    </c:if>
                                </c:forEach>
                                <div class="hp_area cashbillType mt10" id="cashbillType1">
                                    <p><strong>사업자번호</strong></p>
                                    <div class="input_wrap col-w-9 mt5">
                                        <form:input path="cashbill.businessNumber1" class="required _number" maxlength="3" title="사업자등록번호"/>
                                        <span class="connection"> - </span>
                                        <form:input path="cashbill.businessNumber2" class="required _number" maxlength="2" title="사업자등록번호"/>
                                        <span class="connection"> - </span>
                                        <form:input path="cashbill.businessNumber3" class="required _number" maxlength="5" title="사업자등록번호"/>
                                    </div>
                                </div>
                                <div class="hp_area cashbillType mt10" id="cashbillType2" style="display:none;">
                                    <p><strong>휴대전화번호</strong></p>
                                    <div class="input_wrap col-w-9 mt5">
                                        <form:select path="cashbill.cashbillPhone1" id="cashbillPhone1" title="휴대전화번호">
                                            <form:option value="" label="선택"></form:option>
                                            <form:options items="${op:getCodeInfoList('PHONE')}" itemLabel="label" itemValue="key.id" />
                                        </form:select>
                                        <span class="connection"> - </span><form:input path="cashbill.cashbillPhone2" class="_number" maxlength="4" title="휴대전화번호"/>
                                        <span class="connection"> - </span>
                                        <form:input path="cashbill.cashbillPhone3" class="_number" maxlength="4" title="휴대전화번호"/>
                                    </div>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="label">발급구분</td>
                        <td colspan="3">
                            <div>
                                <c:forEach items="${cashbillIssueTypes}" var="cashbillIssueType" varStatus="i">
                                    <c:if test="${cashbillIssueType.code != 'NONE'}">
                                        <form:radiobutton path="cashbillIssueType" id="cashbillIssueType_${i.count}" label=" ${cashbillIssueType.title}"  value="${cashbillIssueType.code}" checked="${cashbillIssueType.code == 'NORMAL' ? 'checked' : ''}"/>
                                    </c:if>
                                </c:forEach>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="label">신청자명</td>
                        <td colspan="3">
                            <div>
                                <form:input path="cashbill.customerName" class="required" title="신청자명"/>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="label">주문번호</td>
                        <td colspan="3">
                            <div>
                                <form:input path="cashbill.orderCode" class="" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="label">과세구분</td>
                        <td colspan="3">
                            <div>
                                <c:forEach items="${taxTypes}" var="taxType" varStatus="i">
                                    <form:radiobutton path="taxType" id="taxType${i.count}" label=" ${taxType.title}"  value="${taxType.code}" checked="${taxType.code == 'CHARGE' ? 'checked' : ''}"/>
                                </c:forEach>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="label">금액</td>
                        <td colspan="3">
                            <div>
                                <form:input path="amount" class="required _number" title="금액"/>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="label">영수증 상품명</td>
                        <td colspan="3">
                            <div>
                                <form:input path="itemName" />
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>

    <div class="btn_center">
        <button type="submit" class="btn btn-active"><span>저장</span></button>
        <a href="/opmanager/receipt/cashbill/list" class="btn btn-default"><span>취소</span></a>
    </div>
</form:form>


<!-- 다음 주소검색 -->
<daum:address />
<script type="text/javascript">

    $(function() {

        Common.addNumberComma();
        // 필수 입력항목 마커.
        Common.displayRequireMark();
        // 발행구분 체크
        cashbillTypeCheck();

        // 주소복사
        $('.op-copy-address').on('click', function() {
            $('#businessLocation').val($('#address').val() + ' ' + $('#addressDetail').val());
        });


        // validator
        try{
            $('#cashbillIssue').validator(function() {

                var issueType = $('input[name="cashbill.cashbillType"]:checked').val();

                var phoneNumber1 = $("#cashbillPhone1 option:selected").val();

                if (issueType == 'PERSONAL' && phoneNumber1 == '') {
                    alert('휴대전화번호 앞자리를 선택해 주세요.');
                    return false;
                }

                var amount = $('#amount').val()
                if (amount < 1) {
                    alert('금액은 0보다 커야합니다.');
                    return false;
                }

                Common.removeNumberComma();
            });
        } catch(e) {
            alert(e.message);
        }
    });

    function openDaumPostcode() {

        var tagNames = {
            'newZipcode'			: 'post',
            /* 'zipcode' 				: 'post', */
            'zipcode1' 				: 'post1',
            'zipcode2' 				: 'post2',
        }

        openDaumAddress(tagNames);

    }

    function cashbillTypeCheck() {
        $('input[name="cashbill.cashbillType"]').on('click', function(e){
            if ($(this).val() == 'BUSINESS') {
                $('div#cashbillType2').hide();
                $('div#cashbillType1').show();

                $('input[name="cashbill.cashbillPhone1"]').removeClass('required');
                $('input[name="cashbill.cashbillPhone2"]').removeClass('required');
                $('input[name="cashbill.cashbillPhone3"]').removeClass('required');

                $('input[name="cashbill.businessNumber1"]').addClass('required');
                $('input[name="cashbill.businessNumber2"]').addClass('required');
                $('input[name="cashbill.businessNumber3"]').addClass('required');

            } else if ($(this).val() == 'PERSONAL') {
                $('div#cashbillType1').hide();
                $('div#cashbillType2').show();

                $('input[name="cashbill.cashbillPhone1"]').addClass('required');
                $('input[name="cashbill.cashbillPhone2"]').addClass('required');
                $('input[name="cashbill.cashbillPhone3"]').addClass('required');

                $('input[name="cashbill.businessNumber1"]').removeClass('required');
                $('input[name="cashbill.businessNumber2"]').removeClass('required');
                $('input[name="cashbill.businessNumber3"]').removeClass('required');
            }
        });
    }
</script>
