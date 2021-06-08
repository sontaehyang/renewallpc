<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="op" uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<h1 class="popup_title">${op:message('MENU_13201')} 상세</h1>

<div class="popup_wrap">
    <div class="popup_contents">

        <h4><span>발송 대상</span></h4>

        <div class="count_title mt20">
            <h5>
                전체 : ${op:numberFormat(pageContent.totalElements)}건 조회
            </h5>
        </div>

        <table class="board_list_table">
            <colgroup>
                <col style="width:120px" />
                <col style="width:100px;"/>
                <col style="width:auto;"/>
                <col style="width:100px;"/>
                <col style="width:50px;"/>
                <col style="width:50px;"/>
            </colgroup>
            <thead>
            <tr>
                <th scope="col">발송시간</th>
                <th scope="col">전화번호</th>
                <th scope="col">메시지</th>
                <th scope="col">발송타입</th>
                <th scope="col">성공</th>
                <th scope="col">수신</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${pageContent.content}" var="list" varStatus="i">
                <tr>
                    <td><div>${list.sendDateText}</div></td>
                    <td><div>${list.phone}</div></td>
                    <td><div>${list.content}</div></td>
                    <td><div>${list.typeLabel}</div></td>
                    <td><div>${op:numberFormat(list.success)}</div></td>
                    <td>
                        <div>
                            <c:choose>
                                <c:when test="${list.pushFlag}">
                                    ${op:numberFormat(list.pushReceive)}
                                </c:when>
                                <c:otherwise>
                                    -
                                </c:otherwise>
                            </c:choose>

                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <c:if test="${empty pageContent.content}">
            <div class="no_content">
                    ${op:message('M00473')} <!-- 데이터가 없습니다. -->
            </div>
        </c:if>

        <page:pagination-jpa />

        <div class="btn_all">
            <div class="btn_center mb0">
                <a href="javascript:self.close();" class="btn btn-active">확인</a>
            </div>
        </div>

    </div><!--//popup_contents E-->
</div>




