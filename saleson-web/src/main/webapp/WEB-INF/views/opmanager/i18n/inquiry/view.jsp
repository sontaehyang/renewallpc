<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>


<form:form modelAttribute="inquiry" action="/opmanager/inquiry/answer" method="post">
    <form:hidden path="inquiryId" value="${inquiry.inquiryId }"/>
    <form:hidden path="telNumber" value="${inquiry.telNumber }"/>
    <form:hidden path="inquiryType" value="${inquiry.inquiryType }"/>
    <form:hidden path="smsTitle" value="드루벨트 문의사항에 대한 답변입니다."/>
    <table cellpadding="0" cellspacing="0" summary="" class="board_list_table inquiry_view" style="width:80%;">
        <tbody>
        <tr>
            <th scope="row">문의유형</th>
            <td class="tleft">
                <div class="inquiry_type">
                    <c:choose>
                        <c:when test="${inquiry.inquiryType == 'etc'}">
                            기타
                        </c:when>
                        <c:when test="${inquiry.inquiryType == 'item'}">
                            상품 문의
                        </c:when>
                        <c:when test="${inquiry.inquiryType == 'order'}">
                            주문 및 배송 문의
                        </c:when>
                        <c:when test="${inquiry.inquiryType == 'custom'}">
                            팀 단체 및 커스텀 문의
                        </c:when>
                        <c:otherwise> - </c:otherwise>
                    </c:choose>
                </div>
            </td>
        </tr>
        <tr>
            <th scope="row">회원 구분</th>
            <td class="tleft">
                <c:choose>
                    <c:when test="${inquiry.userEmail != '' && inquiry.userEmail != null }">
                        회원(${inquiry.userEmail })
                    </c:when>
                    <c:otherwise>
                        비회원
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
        <tr>
            <th scope="row">연락처</th>
            <td class="tleft">
                <div>
                        ${inquiry.telNumber}
                </div>
            </td>
        </tr>
        </tr>
        <tr>
            <th scope="row">작성일</th>
            <td class="tleft">
                    ${op:date(inquiry.createdDate) }
            </td>
        </tr>
        <tr>
            <th scope="row">상품명</th>
            <td class="tleft">
                <c:choose>
                    <c:when test="${inquiry.itemCode != null && inquiry.itemCode != '' }">
                        <a href="javascript:window.open('/products/view/${inquiry.itemCode} ', 'newWindow');">${inquiry.itemName } (${inquiry.itemCode })</a>
                    </c:when>
                    <c:otherwise>
                        -
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
        <tr>
            <th scope="row">내용</th>
            <td class="tleft">
                    ${inquiry.inquiryContent }
            </td>
        </tr>
        <tr>
            <th scope="row">첨부파일</th>
            <td class="tleft">
                <a type="application/octet-stream" href="#" download="${inquiry.inquiryImgName}" onclick="downloadFile('${inquiry.inquiryImgName}');">
                    <p>${inquiry.inquiryImgName}</p>
                </a>
            </td>
        </tr>

        <c:if test="${not empty answerList}">
            <tr>
                <th scope="row">답변 내역</th>
                <td class="tleft">
                    <table class="inner-table">
                        <thead>
                        <tr>
                            <th style="width:80%">내용</th>
                            <th style="width:20%">답변일</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${answerList}" var="answer">
                            <tr>
                                <td class="tleft">${op:nl2br(answer.content)}</td>
                                <td>${op:datetime(answer.createdDate)}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </td>
            </tr>
        </c:if>

        <tr>
            <th scope="row">답변 내용</th>
            <td class="tleft">
                <form:textarea path="inquiryAnswer" cols="30" rows="6" class="required inquiry-text" title="답변 내용" />
            </td>
        </tr>
        </tbody>
    </table> <!-- // 기본정보 끝 -->

    <div class="btn_all">

        <div class="btn_center">
            <button type="submit" class="btn btn-active btn-sm">답변 하기</button>
            <a href="javascript:location.href='/opmanager/inquiry/list'" class="btn btn-default btn-sm">
                <span>뒤로 가기</span>
            </a>
        </div>
    </div>
</form:form>
<script type="text/javascript">

    $('#inquiry').validator(function(){

    });

    function downloadFile(inquiryImgName) {
        event.preventDefault();
        location.href="/opmanager/inquiry/download-file?inquiryImgName=" + inquiryImgName;
        Common.loading.hide();
    }
</script>
