<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>

<div class="location">
    <a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
</div>


<form:form modelAttribute="policyParam" method="get">
    <h3><span>${op:message('M00756')}</span></h3>
    <div class="board_write">
        <table class="board_write_table" summary="${op:message('M00459')}">
            <caption>${op:message('M00459')}</caption>
            <colgroup>
                <col style="width:150px;" />
                <col style="width:*;" />
            </colgroup>
            <tbody>
            <tr>
                <td class="label">${op:message('M00011')}</td> <!-- 검색구분 -->
                <td>
                    <div>

                        <form:select path="where" title="${op:message('M00011')}">
                            <form:option value="TITLE">제목</form:option> <!-- 제목 -->
                        </form:select>
                        <form:input type="text" path="query" class="three" title="${op:message('M00021')}" /> <!-- 검색어 입력 -->
                    </div>
                </td>
            </tr>
            <tr>
                <td class="label">정책 구분</td>
                <td>
                    <div>
                        <form:radiobutton path="policyType" value="" label="전체" checked="checked" />
                        <form:radiobutton path="policyType" value="0" label="약관" />   <!-- 공개 -->
                        <form:radiobutton path="policyType" value="1" label="개인정보처리방침" /> <!-- 비공개 -->
                        <form:radiobutton path="policyType" value="3" label="마케팅이용약관" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="label">전시 여부</td>
                <td>
                    <div>
                        <form:radiobutton path="exhibitionStatus" value="" label="전체" checked="checked" />
                        <form:radiobutton path="exhibitionStatus" value="Y" label="공개" />   <!-- 공개 -->
                        <form:radiobutton path="exhibitionStatus" value="N" label="비공개" /> <!-- 비공개 -->
                    </div>
                </td>
            </tr>
            </tbody>
        </table>
    </div> <!-- // board_write -->

    <!-- 버튼시작 -->
    <div class="btn_all">
        <div class="btn_left">

            <button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='/opmanager/config/policy/list';"><span>${op:message('M00047')}</span></button> <!-- 초기화 -->
        </div>
        <div class="btn_right">
            <button type="submit" class="btn btn-dark-gray btn-sm"><span>${op:message('M00048')}</span></button> <!-- 검색 -->
        </div>
    </div>
    <!-- 버튼 끝-->

    <div class="count_title mt20">
        <h5>
                ${op:message('M00039')} : ${totalCount} ${op:message('M00743')}
        </h5>	 <!-- 전체 -->   <!-- 건 조회 -->
        <span>
            ${op:message('M00052')} :
            <form:select path="itemsPerPage" title="${op:message('M00239')}" onchange="$('form#policyParam').submit();"> <!-- 화면출력 -->
                <form:option value="10" label="${op:message('M00240')}" />  <!-- 10개 출력 -->
                <form:option value="20" label="${op:message('M00241')}" />  <!-- 20개 출력 -->
                <form:option value="50" label="${op:message('M00242')}" />  <!-- 50개 출력 -->
                <form:option value="100" label="${op:message('M00243')}" /> <!-- 100개 출력 -->
            </form:select>
        </span>
    </div>
</form:form>

<form id="listForm">
    <div class="board_write">
        <table class="board_list_table" summary="${op:message('M00273')}"> <!-- 주문내역 리스트 -->
            <caption>${op:message('M00273')}</caption>
            <colgroup>
                <%--<col style="width:30px;" />
                <col style="width:50px;" />--%>
                <col style="width:200px;" />
                <col style="width:auto;" />
                <col style="width:100px;" />
                <col style="width:100px;" />
                <col style="width:100px;" />
            </colgroup>
            <thead>
            <tr>
                <%--<th scope="col"><input type="checkbox" id="check_all" title="${op:message('M00169')}" /></th>
                <th scope="col">${op:message('M00200')}</th>--%> <!-- 순번 -->
                <th scope="col">정책구분</th> <!-- 정책구분 -->
                <th scope="col">제목</th> <!-- 제목 -->
                <th scope="col">수정일<br/>수정자</th> <!-- 수정일 / 수정자 -->
                <th scope="col">전시 여부</th> <!-- 전시 여부 -->
                <th scope="col">${op:message('M00087')}</th>	 <!-- 수정 -->
            </tr>
            </thead>
            <tbody>
            <c:forEach var="policys" items="${policyList}" varStatus="i">
                <tr>
                        <%--<td><input type="checkbox" name="id" id="check" value="${list.itemReviewId}" title="체크박스" /></td>
                        <td>${pagination.itemNumber - i.count}</td>--%>

                    <td>${policys.policyTypeLabel}</td>

                    <td>${policys.title}</td>

                    <td>
                        <c:choose>
                            <c:when test="${empty policys.updatedDate or empty policys.updatedLoginId}">
                                -
                            </c:when>
                            <c:otherwise>
                                ${op:date(policys.updatedDate)}<br/>(${policys.updatedLoginId})
                            </c:otherwise>

                        </c:choose>

                    </td>

                    <td>${policys.exhibitionStatus == "Y" ? "공개" : "비공개"}</td>

                    <td>
                        <a href="/opmanager/config/policy/detail/${policys.policyId}" class="btn btn-gradient btn-xs">${op:message('M00087')}</a>
                    </td>
                </tr>
            </c:forEach>

            </tbody>
        </table>
    </div><!--// board_write E-->

    <c:if test="${empty policyList}">
        <div class="no_content">
                ${op:message('M00473')} <!-- 데이터가 없습니다. -->
        </div>
    </c:if>

    <div class="btn_all">
        <div class="btn_right">
            <a href="/opmanager/config/policy/create" class="btn btn-active btn-sm">${op:message('M00088')} </a>
        </div>
    </div>


    <page:pagination-manager /><br/>
</form>

<script type="text/javascript">
    $(function() {
        Common.DateButtonEvent.set('.day_btns > a[class^=table_btn]', '', 'input[name="exhibitionStartDate"]' , 'input[name="exhibitionEndDate"]');
    });

</script>