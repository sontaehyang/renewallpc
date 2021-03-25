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

<h3><span>${op:message('MENU_13202')}</span></h3>


<form:form modelAttribute="campaignRegularDto" cssClass="opmanager-search-form clear" method="get">
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
                    <td class="label">발송기간</td>
                    <td colspan="3">
                        <div>
                            <span class="datepicker"><form:input path="startSendDate" class="datepicker" maxlength="8" title="발송일자 시작일" /></span>
                            <span class="wave">~</span>
                            <span class="datepicker"><form:input path="endSendDate" class="datepicker" maxlength="8" title="발송일자 종료일" /></span>
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
                                <option value="0" ${op:selected(campaignRegularDto.startOrderAmount1, 0)}>-</option>
                                <c:forEach var="amount" items="${amounts}">
                                    <option value="${amount.value}" ${op:selected(campaignRegularDto.startOrderAmount1,amount.value)}>${amount.label} 이상</option>
                                </c:forEach>
                            </form:select>
                            <span class="wave">~</span>
                            <form:select path="endOrderAmount1" title="${op:message('M00211')}">
                                <option value="0" ${op:selected(campaignRegularDto.endOrderAmount1, 0)}>-</option>
                                <c:forEach var="amount" items="${amounts}">
                                    <option value="${amount.value}" ${op:selected(campaignRegularDto.endOrderAmount1,amount.value)}>${amount.label} 이하</option>
                                </c:forEach>
                            </form:select>
                        </div>
                    </td>

                    <td class="label">최근 3개월간 구매액</td>
                    <td>
                        <div>
                            <form:select path="startOrderAmount2" title="${op:message('M00211')} ">
                                <option value="0" ${op:selected(campaignRegularDto.startOrderAmount2, 0)}>-</option>
                                <c:forEach var="amount" items="${amounts}">
                                    <option value="${amount.value}" ${op:selected(campaignRegularDto.startOrderAmount2, amount.value)}>${amount.label} 이상</option>
                                </c:forEach>
                            </form:select>
                            <span class="wave">~</span>
                            <form:select path="endOrderAmount2" title="${op:message('M00211')} ">
                                <option value="0" ${op:selected(campaignRegularDto.endOrderAmount2, 0)}>-</option>
                                <c:forEach var="amount" items="${amounts}">
                                    <option value="${amount.value}" ${op:selected(campaignRegularDto.endOrderAmount2, amount.value)}>${amount.label} 이하</option>
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
                                <option value="0" ${op:selected(campaignRegularDto.startCartAmount, 0)}>-</option>
                                <c:forEach var="amount" items="${amounts}">
                                    <option value="${amount.value}" ${op:selected(campaignRegularDto.startCartAmount, amount.value)}>${amount.label} 이상</option>
                                </c:forEach>
                            </form:select>
                            <span class="wave">~</span>
                            <form:select path="endCartAmount" title="${op:message('M00211')} ">
                                <option value="0" ${op:selected(campaignRegularDto.endCartAmount, 0)}>-</option>
                                <c:forEach var="amount" items="${amounts}">
                                    <option value="${amount.value}" ${op:selected(campaignRegularDto.endCartAmount, amount.value)}>${amount.label} 이하</option>
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
                        onclick="location.href='/opmanager/campaign/regular/list';"><span
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
                <col style="width:80px;"/>
                <col style="width:auto;"/>
                <col style="width:100px"/>
                <col style="width:100px"/>
                <col style="width:180px;"/>
                <col style="width:100px"/>
                <col style="width:150px;"/>
            </colgroup>
            <thead>
            <tr>
                <th scope="col" rowspan="2">${op:message('M00200')}</th>
                <th scope="col" rowspan="2">정기발송 제목</th>
                <th scope="col" rowspan="2">발송주기</th>
                <th scope="col" rowspan="2">발송시간</th>
                <th scope="col" rowspan="2">발송기간</th>
                <th scope="col" rowspan="2">상태</th>
                <th scope="col" rowspan="2">관리</th>
            </tr>
            </thead>

            <tbody>
            <c:forEach items="${pageContent.content}" var="campaignRegular" varStatus="i">
                <tr>
                    <td>${op:numbering(pageContent, i.index)}</td>
                    <td class="tex_l">${campaignRegular.title}</td>
                    <td>
                        <c:choose>
                            <c:when test="${campaignRegular.sendCycle == 'month'}">
                                매월 ${campaignRegular.sendDate}일
                            </c:when>
                            <c:when test="${campaignRegular.sendCycle == 'week'}">
                                매주
                                <c:forEach items="${weekDays}" var="code">
                                    <c:if test="${code.value == campaignRegular.sendDay}">
                                        ${code.label}
                                    </c:if>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                매일
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>${campaignRegular.sendTime}시</td>
                    <td>${op:date(campaignRegular.startSendDate)} ~ ${op:date(campaignRegular.endSendDate)}</td>
                    <td>
                        <c:choose>
                            <c:when test="${campaignRegular.status == '0'}">
                                발송예정
                            </c:when>
                            <c:when test="${campaignRegular.status == '1'}">
                                <span style="color: #25A5DC">발송중</span>
                            </c:when>
                            <c:when test="${campaignRegular.status == '2'}">
                                발송취소
                            </c:when>
                            <c:when test="${campaignRegular.status == '3'}">
                                발송완료
                            </c:when>
                        </c:choose>
                    </td>
                    <td>
                        <div>
                            <c:choose>
                                <c:when test="${campaignRegular.status == '0' || campaignRegular.status == '1'}">
                                    <a href="javascript:;" onclick="campaignRegularEdit('${campaignRegular.id}');" class="btn btn-gradient btn-xs" title="${op:message('M00087')} ">${op:message('M00087')}</a>
                                    <a href="javascript:;" onclick="campaignRegularCancelDelete('cancel', '${campaignRegular.id}')" class="btn btn-gradient btn-xs" title="취소">취소</a>
                                </c:when>
                                <c:when test="${campaignRegular.status == '2'}">
                                    <a href="javascript:;" onclick="campaignRegularEdit('${campaignRegular.id}');" class="btn btn-gradient btn-xs" title="${op:message('M00087')} ">${op:message('M00087')}</a>
                                    <a href="javascript:;" onclick="campaignRegularCancelDelete('delete', '${campaignRegular.id}')" class="btn btn-gradient btn-xs btn-delete-user" title="${op:message('M00074')} ">${op:message('M00074')}</a>
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

<div class="btn_all">
    <div class="btn_right mb0">
        <a href="/opmanager/campaign/list" class="btn btn-active btn-sm"></span>정기발송 등록</a>
    </div>
</div>

<page:pagination-jpa />

<script type="text/javascript">

    $(function () {
        displayChange();
        displaySelected();

        $('#campaignRegularDto').validator(function () {
            if($('#startSendDate').val() > $('#endSendDate').val() && $('#endSendDate').val() != ''){
                alert("발송기간 범위를 다시 입력해주세요.");
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

    function checkAmount(value) {
        var start = parseInt($('#start' + value).val());
        var end = parseInt($('#end' + value).val());

        if (start > end && end > 0) {
            return false;
        }
        return true;
    }

    function displayChange() {
        $("#displayCount").on('change', function () {
            $("#size").val($(this).val());
            $('#campaignRegularDto').submit();
        });
    }

    function displaySelected() {
        $("#displayCount").val($("#size").val());
    }

    function campaignRegularEdit(campaignRegularId){
        location.href = "/opmanager/campaign/regular/list/create/" + campaignRegularId;
    }

    function campaignRegularCancelDelete(status, campaignRegularId) {
        var message = '취소';
        if (status == 'delete') {
            message = "삭제"
        }

        if (confirm('선택된 정기발송을 ' + message + '하시겠습니까?')) {
            var url = '/opmanager/campaign/regular/' + status + '/' +campaignRegularId;
            $.post(url, campaignRegularId, function(response) {
                if (response.isSuccess) {
                    location.reload();
                } else {
                    alert(response.errorMessage);
                }
            }, 'json');
        }
    }

</script>
