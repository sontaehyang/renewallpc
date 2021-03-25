<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>

<div class="popup_wrap">
    <h1 class="popup_title">푸시정보 검색</h1>
    <div class="popup_contents">
        <form:form modelAttribute="applicationInfoDto" method="get" action="/opmanager/campaign/create/application-info">
            <div class="item_list">
                <div class="board_write">
                    <table class="board_write_table">
                        <caption>${op:message('M00746')}</caption>
                        <colgroup>
                            <col style="width:150px;" />
                            <col style="" />
                        </colgroup>
                        <tbody>
                        <tr>
                            <td class="label">회원 검색</td>
                            <td>
                                <div>
                                    <form:select path="where" class="choice3">
                                        <form:option value="userName">회원이름</form:option>
                                        <form:option value="loginId">아이디</form:option>
                                    </form:select>

                                    <form:input type="text" path="query" title="검색어" class="form-half required"/>
                                </div>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="btn_all">
                <div class="btn_right">
                    <button type="submit" class="btn btn-dark-gray btn-sm btn-search"><span class="glyphicon glyphicon-search"></span> ${op:message('M00048')}</button> <!-- 검색 -->
                </div>
            </div> <!-- // btn_all -->

        </form:form>

        <div class="board_write" style="clear:both">
            <table class="board_list_table">
                <thead>
                <colgroup>
                    <col style="width: 80px;"/>
                    <col style="width: 100px;"/>
                    <col style="width: 120px;"/>
                    <col style="width: 50px;"/>
                    <col style="width: 40px;"/>
                    <col style="width: 30px;"/>
                </colgroup>
                <tr>
                    <th scope="label">이름</th>
                    <th scope="label">아이디</th>
                    <th scope="label">전화번호</th>
                    <%--<th scope="label">푸시 토큰</th>--%>
                    <th scope="label">디바이스<br />고유번호</th>
                    <th scope="label">디바이스<br />타입</th>
                    <th class="label">선택</th>
                </tr>
                <tbody>
                <c:forEach var="list" items="${pageContent}" varStatus="i">
                    <tr
                            data-user-id="${list.USER_ID}"
                            data-login-id="${list.LOGIN_ID}"
                            data-user-name="${list.USER_NAME}"
                            data-phone-number="${list.PHONE_NUMBER}"
                            data-application-no="${list.APPLICATION_NO}"
                            data-uuid="${list.UUID}"
                            data-push-token="${list.PUSH_TOKEN}"
                            data-device-type="${list.DEVICE_TYPE}">
                        <td>${list.USER_NAME}</td>
                        <td>${list.LOGIN_ID}</td>
                        <td>${list.PHONE_NUMBER}</td>
                        <%--<td>${list.PUSH_TOKEN}</td>--%>
                        <td>${list.UUID}</td>
                        <td>${list.DEVICE_TYPE}</td>
                        <td>
                            <div>
                                <button type="button" class="btn btn-gradient btn-sm btn-select">
                                    선택
                                </button>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <c:if test="${query == null}">
                <div class="no_content">
                    회원을 검색해주세요.
                </div>
            </c:if>
            <c:if test="${empty pageContent && query != null}">
                <div class="no_content">
                    데이터가 없습니다.
                </div>
            </c:if>
        </div>
    </div>

</div>

<page:javascript>
    <script type="text/javascript">
        $(function() {
            $('.btn-select').on('click', function(e) {
                e.preventDefault();

                var user = $(this).closest('tr').data();

                opener.handleFindPushCallback(user);
                self.close();
            });

            $('.btn-search').on('click', function() {
                var $query = $('#query');

                if ($.trim($query.val()) == '') {
                    alert('회원이름 또는 회원 아이디를 입력해주세요.');
                    $query.focus();
                    return false;
                }
            });

        });

    </script>

</page:javascript>