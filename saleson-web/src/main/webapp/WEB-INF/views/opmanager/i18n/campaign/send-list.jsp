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

<h3><span>${op:message('MENU_13201')}</span></h3>
<p class="text-info text-sm">
    * 당일 발송 건수는 다음날 통계 배치가 실행된 후 업데이트가 됩니다.
</p>


<form:form modelAttribute="campaignDto" cssClass="opmanager-search-form clear" method="get">
    <fieldset>
        <legend class="hidden">${op:message('M00048')}</legend>
        <form:hidden path="size"/>

        <div class="board_write">
            <table class="board_write_table">
                <colgroup>
                    <col style="width: 150px;"/>
                    <col style="width: auto;"/>
                    <col style="width: 150px;"/>
                    <col style="width: auto;"/>
                </colgroup>

                <tbody>
                <tr>
                    <td class="label">캠페인 제목</td>
                    <td colspan="3">
                        <div>
                            <form:input path="title" class="optional seven" title="캠페인 제목"/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="label">예약 / 발송일자</td>
                    <td colspan="3">
                        <div>
                            <span class="datepicker"><form:input path="startSentDate" class="datepicker" maxlength="8" title="발송일자 시작일" /></span>
                            <span class="wave">~</span>
                            <span class="datepicker"><form:input path="endSentDate" class="datepicker" maxlength="8" title="발송일자 종료일" /></span>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="label">회원그룹</td>
                    <td>
                        <div>
                            <form:select path="groupCode" title="그룹명">
                                <form:option value="">전체</form:option>
                                <form:option value="0">그룹 미지정 회원</form:option>
                                <c:forEach items="${groups}" var="group">
                                    <form:option value="${group.groupCode}">${group.groupName}</form:option>
                                </c:forEach>
                            </form:select>
                        </div>
                    </td>
                    <td class="label">회원등급</td>
                    <td>
                        <div>
                            <form:select path="levelId" title="회원 Level">
                                <form:option value="-1">전체</form:option>
                                <form:option value="0">Level 미지정 회원</form:option>
                                <c:forEach items="${userLevelGroup}" var="userLevel">
                                            <form:option value="${userLevel.levelId}">${userLevel.levelName}</form:option>
                                        </c:forEach>
                            </form:select>
                        </div>
                    </td>
                </tr>

                <tr>
                    <td class="label">가입이후 구매액</td>
                    <td>
                        <div>
                            <form:select path="startOrderAmount1" title="${op:message('M00211')}">
                                <option value="0" ${op:selected(campaignDto.startOrderAmount1, 0)}>-</option>
                                <c:forEach var="amount" items="${amounts}">
                                    <option value="${amount.value}" ${op:selected(campaignDto.startOrderAmount1,amount.value)}>${amount.label} 이상</option>
                                </c:forEach>
                            </form:select>
                            <span class="wave">~</span>
                            <form:select path="endOrderAmount1" title="${op:message('M00211')}">
                                <option value="0" ${op:selected(campaignDto.endOrderAmount1, 0)}>-</option>
                                <c:forEach var="amount" items="${amounts}">
                                    <option value="${amount.value}" ${op:selected(campaignDto.endOrderAmount1,amount.value)}>${amount.label} 이하</option>
                                </c:forEach>
                            </form:select>
                        </div>
                    </td>

                    <td class="label">최근 3개월간 구매액</td>
                    <td>
                        <div>
                            <form:select path="startOrderAmount2" title="${op:message('M00211')} ">
                                <option value="0" ${op:selected(campaignDto.startOrderAmount2, 0)}>-</option>
                                <c:forEach var="amount" items="${amounts}">
                                    <option value="${amount.value}" ${op:selected(campaignDto.startOrderAmount2, amount.value)}>${amount.label} 이상</option>
                                </c:forEach>
                            </form:select>
                            <span class="wave">~</span>
                            <form:select path="endOrderAmount2" title="${op:message('M00211')} ">
                                <option value="0" ${op:selected(campaignDto.endOrderAmount2, 0)}>-</option>
                                <c:forEach var="amount" items="${amounts}">
                                    <option value="${amount.value}" ${op:selected(campaignDto.endOrderAmount2, amount.value)}>${amount.label} 이하</option>
                                </c:forEach>
                            </form:select>
                        </div>
                    </td>
                </tr>

                <tr>
                    <td class="label">최종 방문일(미접속)</td>
                    <td colspan="3">
                        <div>
                            <form:radiobutton path="lastLoginDateType" value="0" checked="checked" label="전체"/>
                            <form:radiobutton path="lastLoginDateType" value="1" label="1개월 이상"/>
                            <form:radiobutton path="lastLoginDateType" value="2" label="2개월 이상"/>
                            <form:radiobutton path="lastLoginDateType" value="3" label="3개월 이상"/>
                            <form:radiobutton path="lastLoginDateType" value="6" label="6개월 이상"/>
                            <form:radiobutton path="lastLoginDateType" value="24" label="1년 이상"/>
                        </div>
                    </td>
                </tr>

                <tr>
                    <td class="label">장바구니 상품개수</td>
                    <td>
                        <div>
                            <form:radiobutton path="cartCount" value="0" checked="checked" label="전체"/>
                            <form:radiobutton path="cartCount" value="1" label="1개 이상"/>
                            <form:radiobutton path="cartCount" value="3" label="3개 이상"/>
                            <form:radiobutton path="cartCount" value="5" label="5개 이상"/>
                            <form:radiobutton path="cartCount" value="100" label="100개 이상"/>
                        </div>
                    </td>
                    <td class="label">장바구니 상품 총액</td>
                    <td>
                        <div>
                            <form:select path="startCartAmount" title="${op:message('M00211')} ">
                                <option value="0" ${op:selected(campaignDto.startCartAmount, 0)}>-</option>
                                <c:forEach var="amount" items="${amounts}">
                                    <option value="${amount.value}" ${op:selected(campaignDto.startCartAmount, amount.value)}>${amount.label} 이상</option>
                                </c:forEach>
                            </form:select>
                            <span class="wave">~</span>
                            <form:select path="endCartAmount" title="${op:message('M00211')} ">
                                <option value="0" ${op:selected(campaignDto.endCartAmount, 0)}>-</option>
                                <c:forEach var="amount" items="${amounts}">
                                    <option value="${amount.value}" ${op:selected(campaignDto.endCartAmount, amount.value)}>${amount.label} 이하</option>
                                </c:forEach>
                            </form:select>
                        </div>
                    </td>
                </tr>

                </tbody>
            </table>
        </div> <!--// board_write E-->

        <div class="btn_all">
            <div class="btn_left"> <!-- 초기화 -->
                <button type="button" class="btn btn-dark-gray btn-sm"
                        onclick="location.href='/opmanager/campaign/send-list';"><span
                        class="glyphicon glyphicon-repeat"></span> ${op:message('M00047')}</button>
            </div>
            <div class="btn_right"> <!-- 검색 -->
                <button type="submit" class="btn btn-dark-gray btn-sm"><span
                        class="glyphicon glyphicon-search"></span> ${op:message('M00048')}</button>
            </div>
        </div>    <!--// btn_all E-->

    </fieldset>
</form:form>


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


<div class="board_write">
    <form id="listForm">
        <table class="board_list_table">
            <colgroup>
                <col style="width:50px;"/>
                <col style="width:70px;"/>
                <col style="width:auto;" />
                <col style="width:50px"/>
                <col style="width:170px;"/>
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
                <col style="width:160px;"/>
                <col style="width:100px;"/>
            </colgroup>
            <thead>
            <tr>
                <th scope="col" rowspan="2">${op:message('M00200')}</th>
                <th scope="col" rowspan="2">발송구분</th>
                <th scope="col" rowspan="2">캠페인 제목</th>
                <th scope="col" rowspan="2">상태</th>
                <th scope="col" rowspan="2">예약 / 발송일자</th>
                <th scope="col" colspan="2">SMS</th>
                <th scope="col" colspan="2">MMS</th>
                <th scope="col" colspan="2">KAKAO</th>
                <th scope="col" colspan="3">PUSH</th>
                <th scope="col" rowspan="2">링크<br />진입</th>
                <th scope="col" rowspan="2">이벤트<br />참여</th>
                <th scope="col" colspan="2">구매연결</th>
                <th scope="col" rowspan="2">결과 업데이트</th>
                <th scope="col" rowspan="2">관리</th>
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
            <c:forEach items="${pageContent.content}" var="campaign" varStatus="i">
                <tr>
                    <td>${op:numbering(pageContent, i.index)}</td>
                    <td>
                        <c:choose>
                            <c:when test="${campaign.regularFlag == true}">
                                정기
                            </c:when>
                            <c:otherwise>
                                일반
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td class="tex_l">
                        <c:choose>
                            <c:when test="${!empty campaign.autoMonth}">
                                <a href="javascript:Common.popup('/opmanager/campaign/send-list-auto/${campaign.id}/${campaign.autoMonth}', 'campaign', 1000, 800, 1);">${campaign.title}</a>
                            </c:when>
                            <c:otherwise>
                                <a href="javascript:Common.popup('/opmanager/campaign/send-list-detail/${campaign.id}', 'campaign', 1000, 800, 1);">
                                    <c:choose>
                                        <c:when test="${campaign.regularFlag == true}">
                                            <c:choose>
                                                <c:when test="${campaign.sendCycle == 'month'}">
                                                    [매월]
                                                </c:when>
                                                <c:when test="${campaign.sendCycle == 'week'}">
                                                    [매주]
                                                </c:when>
                                                <c:when test="${campaign.sendCycle == 'daily'}">
                                                    [매일]
                                                </c:when>
                                            </c:choose>

                                        </c:when>
                                    </c:choose>

                                    ${campaign.title}
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </td>

                    <td>
                        <c:choose>
                            <c:when test="${campaign.status == '0'}">
                                <strong style="color:red">예약</strong>
                            </c:when>
                            <c:when test="${campaign.status == '1'}">
                                발송
                            </c:when>
                            <c:when test="${campaign.status == '2'}">
                                취소
                            </c:when>
                        </c:choose>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${campaign.status == '1'}">
                                ${op:datetime(campaign.sentDate)}
                            </c:when>
                            <c:when test="${campaign.status == '2'}">
                                ${op:datetime(campaign.sendDate)}
                            </c:when>
                            <c:otherwise>
                                <strong style="color:red">${op:datetime(campaign.sendDate)}</strong>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>${op:numberFormat(campaign.smsSent)}</td>
                    <td>${op:numberFormat(campaign.smsSuccess)}</td>
                    <td>${op:numberFormat(campaign.mmsSent)}</td>
                    <td>${op:numberFormat(campaign.mmsSuccess)}</td>
                    <td>${op:numberFormat(campaign.kakaoSent)}</td>
                    <td>${op:numberFormat(campaign.kakaoSuccess)}</td>
                    <td>${op:numberFormat(campaign.pushSent)}</td>
                    <td>${op:numberFormat(campaign.pushSuccess)}</td>
                    <td>${op:numberFormat(campaign.pushReceive)}</td>
                    <td>${op:numberFormat(campaign.redirection)}</td>
                    <td>0</td>
                    <td>${op:numberFormat(campaign.orderCount)}</td>
                    <td>${op:numberFormat(campaign.orderAmount)}</td>
                    <td>${op:datetime(campaign.statisticsDate)}</td>
                    <td>
                        <div>
                            <c:choose>
                                <c:when test="${campaign.status == '0'}">
                                    <a href="javascript:;" onclick="campaignEdit('${campaign.id}');" class="btn btn-gradient btn-xs" title="${op:message('M00087')} ">${op:message('M00087')}</a>
                                    <a href="javascript:;" onclick="campaignCancelDelete('cancel', '${campaign.id}')" class="btn btn-gradient btn-xs" title="취소">취소</a>
                                </c:when>
                                <c:when test="${campaign.status == '2'}">
                                    <a href="javascript:;" onclick="campaignEdit('${campaign.id}');" class="btn btn-gradient btn-xs" title="${op:message('M00087')} ">${op:message('M00087')}</a>
                                    <a href="javascript:;" onclick="campaignCancelDelete('delete', '${campaign.id}')" class="btn btn-gradient btn-xs btn-delete-user" title="${op:message('M00074')} ">${op:message('M00074')}</a>
                                </c:when>
                            </c:choose>
                        </div>
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

</div>
<!--//board_write E-->

<page:pagination-jpa />

<script type="text/javascript">

    $(function () {
        displayChange();
        displaySelected();

        $('#campaignDto').validator(function () {
            if($('#startSentDate').val() > $('#endSentDate').val() && $('#endSentDate').val() != ''){
                alert("예약 / 발송일자 범위를 다시 입력해주세요.");
                return false;
            } else if (!checkAmount('OrderAmount1')) {
                alert('가입이후 구매액 범위를 다시 선택해주세요.');
                return false;
            } else if (!checkAmount('OrderAmount2')) {
                alert('최근 3개월간 구매액 범위를 다시 선택해주세요.');
                return false;
            } else if (!checkAmount('CartAmount')) {
                alert('장바구니 상품 총액 범위를 다시 선택해주세요.');
                return false;
            }
        });

        // 회원그룹 선택 -> 회원등급 Set
        $('#groupCode').on('change', function () {
            $.get('/opmanager/campaign/levelList', {'groupCode': $(this).val()}, function (response) {

                var levelList = response.data;

                $('#levelId option').remove();

                var appendOption = '';
                appendOption += '<option value="-1">전체</option>';
                appendOption += '<option value="0">Level 미지정 회원</option>';

                for (var i = 0; i < levelList.length; i++) {
                    appendOption += '<option value="' + levelList[i].levelId + '">' + levelList[i].levelName + '</option>';
                }

                $('#levelId').append(appendOption);

            });
        });
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

    function campaignEdit(campaignId){
        location.href = "/opmanager/campaign/list/create/" + campaignId;
    }

    function campaignCancelDelete(status, campaignId) {
        var message = '취소';
        if (status == 'delete') {
            message = "삭제"
        }

        if (confirm('선택된 캠페인을 ' + message + '하시겠습니까?')) {
            var url = '/opmanager/campaign/' + status + "/" +campaignId;
            $.post(url, campaignId, function(response) {
                if (response.isSuccess) {
                    location.reload();
                } else {
                    alert(response.errorMessage);
                }
            }, 'json');
        }
    }

    function checkAmount(value) {
        var start = parseInt($('#start' + value).val());
        var end = parseInt($('#end' + value).val());

        if (start > end && end > 0) {
            return false;
        }
        return true;
    }

</script>
