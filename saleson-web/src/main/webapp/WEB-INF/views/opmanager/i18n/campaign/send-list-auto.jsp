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
                    전체 : ${op:numberFormat(pagination.totalItems)}건 조회
                </h5>
            </div>

            <table class="board_list_table">
                <colgroup>
                    <col style="width:120px" />
                    <col style="width:100px;"/>
                    <col style="width:200px;"/>
                    <col style="width:20px;"/>
                    <col style="width:20px;"/>
                    <col style="width:20px;"/>
                    <col style="width:20px;"/>
                    <col style="width:20px;"/>
                    <col style="width:20px;"/>
                    <col style="width:20px"/>
                    <col style="width:20px;"/>
                    <col style="width:20px;"/>
                </colgroup>
                <thead>
                    <tr>
                        <th scope="col" rowspan="2">발송시간</th>
                        <th scope="col" rowspan="2">전화번호</th>
                        <th scope="col" rowspan="2">메시지</th>
                        <th scope="col" colspan="2">SMS</th>
                        <th scope="col" colspan="2">MMS</th>
                        <th scope="col" colspan="2">KAKAO</th>
                        <th scope="col" colspan="3">PUSH</th>
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
                    </tr>
                </thead>
                <tbody>

                    <c:forEach items="${pageContent}" var="list" varStatus="i">
                        <tr>
                            <td><div>${list.sendDate}</div></td>
                            <td><div>${list.phoneNumber}</div></td>
                            <td><div>${list.content}</div></td>
                            <td>${op:numberFormat(list.smsSent)}</td>
                            <td>${op:numberFormat(list.smsSuccess)}</td>
                            <td>${op:numberFormat(list.mmsSent)}</td>
                            <td>${op:numberFormat(list.mmsSuccess)}</td>
                            <td>${op:numberFormat(list.kakaoSent)}</td>
                            <td>${op:numberFormat(list.kakaoSuccess)}</td>
                            <td>${op:numberFormat(list.pushSent)}</td>
                            <td>${op:numberFormat(list.pushSuccess)}</td>
                            <td>${op:numberFormat(list.pushReceive)}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div><!--//popup_contents E-->
    </div>

<page:pagination-manager />

<div class="btn_all">
    <div class="btn_center mb0">
        <a href="javascript:self.close();" class="btn btn-active">확인</a>
    </div>
</div>
