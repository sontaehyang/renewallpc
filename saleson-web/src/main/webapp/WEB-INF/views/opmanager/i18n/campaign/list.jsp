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


<form:form modelAttribute="campaignBatchDto" cssClass="opmanager-search-form clear" method="get">
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
                    <td class="label">회원검색</td>  <!-- 검색구분 ${op:message('M00011')} -->
                    <td colspan="3">
                        <div>
                            <form:select path="where" title="${op:message('M00211')} ">
                                <form:option value="userName" label="${op:message('M00005')}"></form:option>
                                <form:option value="loginId">${op:message('M00081')}</form:option>
                            </form:select>
                            <form:input path="query" class="optional seven" title="${op:message('M00211')} "/>
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
                                <c:forEach items="${groups}" var="group1">
                                    <form:option value="${group1.groupCode}">${group1.groupName}</form:option>
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
                                <option value="0" ${op:selected(campaignBatchDto.startOrderAmount1, 0)}>-</option>
                                <c:forEach var="amount" items="${amounts}">
                                    <option value="${amount.value}" ${op:selected(campaignBatchDto.startOrderAmount1,amount.value)}>${amount.label} 이상</option>
                                </c:forEach>
                            </form:select>
                            <span class="wave">~</span>
                            <form:select path="endOrderAmount1" title="${op:message('M00211')}">
                                <option value="0" ${op:selected(campaignBatchDto.endOrderAmount1, 0)}>-</option>
                                <c:forEach var="amount" items="${amounts}">
                                    <option value="${amount.value}" ${op:selected(campaignBatchDto.endOrderAmount1,amount.value)}>${amount.label} 이하</option>
                                </c:forEach>
                            </form:select>
                        </div>
                    </td>

                    <td class="label">최근 3개월간 구매액</td>
                    <td>
                        <div>
                            <form:select path="startOrderAmount2" title="${op:message('M00211')} ">
                                <option value="0" ${op:selected(campaignBatchDto.startOrderAmount2, 0)}>-</option>
                                <c:forEach var="amount" items="${amounts}">
                                    <option value="${amount.value}" ${op:selected(campaignBatchDto.startOrderAmount2, amount.value)}>${amount.label} 이상</option>
                                </c:forEach>
                            </form:select>
                            <span class="wave">~</span>
                            <form:select path="endOrderAmount2" title="${op:message('M00211')} ">
                                <option value="0" ${op:selected(campaignBatchDto.endOrderAmount2, 0)}>-</option>
                                <c:forEach var="amount" items="${amounts}">
                                    <option value="${amount.value}" ${op:selected(campaignBatchDto.endOrderAmount2, amount.value)}>${amount.label} 이하</option>
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
                                <option value="0" ${op:selected(campaignBatchDto.startCartAmount, 0)}>-</option>
                                <c:forEach var="amount" items="${amounts}">
                                    <option value="${amount.value}" ${op:selected(campaignBatchDto.startCartAmount, amount.value)}>${amount.label} 이상</option>
                                </c:forEach>
                            </form:select>
                            <span class="wave">~</span>
                            <form:select path="endCartAmount" title="${op:message('M00211')} ">
                                <option value="0" ${op:selected(campaignBatchDto.endCartAmount, 0)}>-</option>
                                <c:forEach var="amount" items="${amounts}">
                                    <option value="${amount.value}" ${op:selected(campaignBatchDto.endCartAmount, amount.value)}>${amount.label} 이하</option>
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
                        onclick="location.href='/opmanager/campaign/list';"><span
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
                <col style="width:20px;"/> <!-- 순번 -->
                <col style="width:40px;"/> <!-- 이름 -->
                <col style="width:100px"/> <!-- 아이디 -->
                <col style="width:30px;"/> <!-- 그룹 -->
                <col style="width:30px;"/> <!-- 등급 -->
                <col style="width:30px;"/> <!-- 문자수신 -->
                <col style="width:30px"/>  <!-- PUSH수신 -->
                <col style="width:60px;"/> <!-- 가입이후 구매액 -->
                <col style="width:60px;"/> <!-- 최근 3개월간 구매액 -->
                <col style="width:60px;"/> <!-- 최종 방문일 -->
                <col style="width:40px"/>  <!-- 장바구니 상품개수 -->
                <col style="width:40px"/>  <!-- 장바구니 상품총액 -->
            </colgroup>
            <thead>
            <tr>
                <th scope="col">${op:message('M00200')}</th>    <!-- 순번 -->
                <th scope="col">${op:message('M00005')}</th>    <!-- 이름 -->
                <th scope="col">${op:message('M00081')}</th>    <!-- 아이디 -->
                <th scope="col">그룹</th>
                <th scope="col">등급</th>
                <th scope="col">문자수신</th>
                <th scope="col">PUSH수신</th>
                <th scope="col">가입이후 구매액</th>
                <th scope="col">최근 3개월간 구매액</th>
                <th scope="col">최종 방문일(미접속)</th>
                <th scope="col">장바구니 상품개수</th>
                <th scope="col">장바구니 상품 총액</th>
            </tr>
            </thead>

            <tbody>
            <c:forEach items="${pageContent.content}" var="user" varStatus="i">
                <tr>
                    <td>${op:numbering(pageContent, i.index)}</td>
                    <td><a href="javascript:Manager.userDetails(${user.userId})">${user.userName}</a></td>
                    <td>
                        <a href="javascript:Manager.userDetails(${user.userId})">${user.loginId}</a>
                    </td>
                    <td>

                        <c:set var="groupCode" value="-"/>
                        <c:forEach items="${groups}" var="group">
                            <c:if test="${group.groupCode eq user.groupCode}">
                                <c:set var="groupCode" value="${group.groupName}"/>
                            </c:if>
                        </c:forEach>

                        ${groupCode}
                    </td>
                    <td>
                        <c:set var="userlevel" value="-"/>


                        <c:forEach items="${groups}" var="group">

                            <c:if test="${group.groupCode eq user.groupCode}">
                                <c:forEach items="${group.userLevels}" var="userLevel">

                                    <c:if test="${userLevel.levelId eq user.levelId}">
                                        <c:set var="userlevel" value="${userLevel.levelName}"/>
                                    </c:if>

                                </c:forEach>
                            </c:if>

                        </c:forEach>

                        ${userlevel}
                    </td>
                    <td> <!-- 문자수신 -->
                        <c:choose>
                            <c:when test="${user.receiveSms == '0'}">${op:message('M00233')}</c:when>
                            <c:otherwise>${op:message('M00234')} </c:otherwise>
                        </c:choose>
                    </td>
                    <td> <!-- PUSH수신 -->
                        <c:choose>
                            <c:when test="${user.receivePush == '0'}">${op:message('M00233')}</c:when>
                            <c:otherwise>${op:message('M00234')} </c:otherwise>
                        </c:choose>
                    </td>

                    <td>${op:numberFormat(user.orderAmount1)}${op:message('M00049')}</td> <!-- 가입이후구매액 -->
                    <td>${op:numberFormat(user.orderAmount2)}${op:message('M00049')}</td> <!-- 최근3개월간구매액 -->

                    <td>
                            ${op:datetime(user.lastLoginDate)}
                    </td>

                    <td>${op:numberFormat(user.cartCount)}개</td>  <!-- 장바구니 상품개수 -->

                    <td>${op:numberFormat(user.cartAmount)}${op:message('M00049')}</td> <!-- 장바구니 상품 금액 -->

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
        <a href="javascript:createCampaign();" class="btn btn-active btn-sm"></span>대상에게 캠페인 발송</a>
        <a href="javascript:createCampaignRegular();" class="btn btn-active btn-sm"></span>대상에게 정기 발송</a>
    </div>
</div>
<!--// btn_all E-->

<page:pagination-jpa />


<div style="display: none;">
    <span id="today">${today}</span>
    <span id="week">${week}</span>
    <span id="month1">${month1}</span>
    <span id="month2">${month2}</span>
</div>

<form name="shadow-login-form" id="shadow-login-form" action="/op_security_login" method="post">
    <input type="hidden" name="target" value="/"/>
    <input type="hidden" name="op_login_type" value="ROLE_USER"/>
    <input type="hidden" id="op_username" name="op_username"/>
    <input type="hidden" id="op_password" name="op_password"/>
    <input type="hidden" id="op_signature" name="op_signature"/>
</form>

<script type="text/javascript">

    $(function () {
        displayChange();
        displaySelected();

        $('#campaignBatchDto').validator(function () {
            if (!checkAmount('OrderAmount1')) {
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
            $('#campaignBatchDto').submit();
        });
    }

    function displaySelected() {
        $("#displayCount").val($("#size").val());
    }

    function createCampaign() {

        if (${empty pageContent.content}) {
            alert("캠페인 발송 대상이 없습니다.");
            return false;
        }

        var url = '/opmanager/campaign/list/create';
        var param = $('#campaignBatchDto').serialize();

        location.href = url + '?' + param;

    }

    function createCampaignRegular() {

        if (${empty pageContent.content}) {
            alert("정기 발송 대상이 없습니다.");
            return false;
        }

        var url = '/opmanager/campaign/regular/list/create';
        var param = $('#campaignBatchDto').serialize();

        location.href = url + '?' + param;

    }


</script>
