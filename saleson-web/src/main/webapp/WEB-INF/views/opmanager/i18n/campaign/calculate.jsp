<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="op" uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<style type="text/css">
    .sort_area button, .sort_area a {
        margin-top: 5px;
    }
</style>

<div class="location">
    <a href="#"></a> &gt; <a href="#"></a> &gt; <a href="#" class="on"></a>
</div>

<h3><span>${op:message('M00210')}</span></h3>

<p class="text-info text-sm">* 전월 정산내역은 매월 3일 이후에 조회가 가능합니다.</p>

<form:form modelAttribute="calculateParam" cssClass="opmanager-search-form clear" method="get">
    <fieldset>
        <div class="board_write">
            <table class="board_write_table" summary="정산년월조회">
                <colgroup>
                    <col style="width:170px;" />
                    <col style="width:auto;" />
                </colgroup>
                <tbody>
                <tr>
                    <td class="label">정산년월 조회</td>
                    <td>
                        <div>
                            <p class="mt5">
                                <select name="searchYear" id="searchYear">
                                    <c:forEach begin="${beginYear}" end="${endYear}" step="1" var="i">
                                        <option value="${endYear - i + beginYear}"
                                                label="${endYear - i + beginYear}" ${op:selected(endYear - i + beginYear , calculateParam.searchYear)}>
                                        ${endYear - i + beginYear}</option>
                                    </c:forEach>
                                </select>년
                                &nbsp;
                                <select name="searchMonth" id="searchMonth">
                                    <c:forEach begin="1" end="12" varStatus="i">
                                        <c:if test="${i.index < 10 }">
                                            <c:set var="month">0${i.index}</c:set>
                                            <option value="0${i.index}" ${op:selected(month,calculateParam.searchMonth) }>0${i.index}</option>
                                        </c:if>
                                        <c:if test="${i.index >= 10 }">
                                            <option value="${i.index}" ${op:selected(i.index,calculateParam.searchMonth) }>${i.index}</option>
                                        </c:if>
                                    </c:forEach>
                                </select>월
                            </p>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>

        <div class="btn_all">
            <div class="btn_left"> <!-- 초기화 -->
                <button type="button" class="btn btn-dark-gray btn-sm"
                        onclick="location.href='/opmanager/campaign/calculate';"><span
                        class="glyphicon glyphicon-repeat"></span> ${op:message('M00047')}</button>
            </div>
            <div class="btn_right"> <!-- 검색 -->
                <button type="submit" class="btn btn-dark-gray btn-sm"><span
                        class="glyphicon glyphicon-search"></span> ${op:message('M00048')}</button>
            </div>
        </div>

    </fieldset>
</form:form>

<div class="board_write">
    <table class="board_list_table">
        <colgroup>
            <col style="width:auto;"/>
            <col style="width:auto;"/>
            <col style="width:auto;"/>
            <col style="width:auto;"/>
            <col style="width:auto;"/>
            <col style="width:auto;"/>
            <col style="width:auto;"/>
            <col style="width:auto;"/>
            <col style="width:auto;"/>
            <col style="width:auto;"/>
            <col style="width:auto;"/>
            <col style="width:auto;"/>
        </colgroup>
        <thead>
        <tr>
            <th scope="col">정산년월</th>
            <th scope="col">기본료</th>
            <th scope="col">SMS 총 성공건수</th>
            <th scope="col">SMS 금액</th>
            <th scope="col">LMS 총 성공 건수</th>
            <th scope="col">LMS 금액</th>
            <th scope="col">MMS 총 성공 건수</th>
            <th scope="col">MMS 금액</th>
            <th scope="col">알림톡 총 성공 건수</th>
            <th scope="col">알림톡 금액</th>
            <th scope="col">PUSH 총 성공 건수</th>
            <th scope="col">PUSH 금액</th>
        </tr>
        </thead>

        <tbody id="data-list">
            <%-- Template 호출 --%>
        </tbody>

    </table>

    <div class="no_content">
        ${op:message('M00473')} <!-- 데이터가 없습니다. -->
    </div>
</div>

<script type="text/javascript">

    $(function () {
        getCalculateList();
    });

    function getCalculateList() {

        var url = '/opmanager/campaign/calculate/api',
            param = {
                'searchYear': $('#searchYear').val(),
                'searchMonth': $('#searchMonth').val()
            };

        $.get(url, param, function (response) {
            Common.responseHandler(response, function () {

                try {
                    var data = response.data;
                    if (typeof data != 'undefined' && data != null) {
                        makeList(data.list);
                    }

                } catch (e) {}

                console.log(data);
            });
        }, 'json');
    }

    function makeList(list) {
        var html = '';
        if (typeof list != 'undefined' && list != null && list.length > 0) {

            var template = $('#calculateRowTemplate').html();

            for (var i = 0; i < list.length; i++) {
                var rawHtml = template,
                    row = list[i];

                var calDt = row.calDt.substring(0, 4) + '년 ' + row.calDt.substring(4, 6) + '월';

                rawHtml = rawHtml.replaceAll('{{calDt}}', calDt)
                    .replaceAll('{{basicRate}}', Common.numberFormat(row.basicRate) + '원')
                    .replaceAll('{{smsCnt}}', Common.numberFormat(row.smsCnt) + '건')
                    .replaceAll('{{smsAmt}}', Common.numberFormat(row.smsAmt) + '원')
                    .replaceAll('{{lmsCnt}}', Common.numberFormat(row.lmsCnt) + '건')
                    .replaceAll('{{lmsAmt}}', Common.numberFormat(row.lmsAmt) + '원')
                    .replaceAll('{{mmsCnt}}', Common.numberFormat(row.mmsCnt) + '건')
                    .replaceAll('{{mmsAmt}}', Common.numberFormat(row.mmsAmt) + '원')
                    .replaceAll('{{kkoCnt}}', Common.numberFormat(row.kkoCnt) + '건')
                    .replaceAll('{{kkoAmt}}', Common.numberFormat(row.kkoAmt) + '원')
                    .replaceAll('{{pushCnt}}', Common.numberFormat(row.pushCnt) + '건')
                    .replaceAll('{{pushAmt}}', Common.numberFormat(row.pushAmt) + '원');

                html += rawHtml + '\n';
            }

            if (html != '') {
                $('.no_content').hide();
                $('#data-list').html(html);
            } else {
                $('.no_content').show();
            }
        }
    }

</script>

<%-- Template 호출 --%>
<script type="text/html" id="calculateRowTemplate">
    <tr>
        <td>{{calDt}}</td>
        <td>{{basicRate}}</td>
        <td>{{smsCnt}}</td>
        <td>{{smsAmt}}</td>
        <td>{{lmsCnt}}</td>
        <td>{{lmsAmt}}</td>
        <td>{{mmsCnt}}</td>
        <td>{{mmsAmt}}</td>
        <td>{{kkoCnt}}</td>
        <td>{{kkoAmt}}</td>
        <td>{{pushCnt}}</td>
        <td>{{pushAmt}}</td>
    </tr>
</script>