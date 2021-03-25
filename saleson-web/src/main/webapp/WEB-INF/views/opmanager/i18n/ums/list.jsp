<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>

<div class="location">
    <a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
</div>

<h3><span>${op:message('M00210')}</span></h3>
<form:form modelAttribute="umsDto" cssClass="opmanager-search-form clear" method="get">
    <fieldset>
        <legend class="hidden">${op:message('M00048')}</legend>
            <%--<form:hidden path="sort" />
			<form:hidden path="orderBy" />
			<form:hidden path="itemsPerPage"/>--%>
        <form:hidden path="page" />

        <div class="board_write">
            <table class="board_write_table">
                <colgroup>
                    <col style="width: 150px;" />
                    <col style="width: auto;" />
                    <col style="width: 150px;" />
                    <col style="width: auto;" />
                </colgroup>
                <tbody>
                <tr>
                    <td class="label">검색 구분</td>
                    <td colspan="3">
                        <div>
                            <div class="col-xs-2 pr-0">
                                <form:select path="where" class="form-block" title="검색구분">
                                    <form:option value="templateName">발송 항목</form:option>
                                </form:select>
                            </div>
                            <div class="col-xs-5">
                                <form:input type="text" path="query" class="form-block" title="검색어" />
                            </div>
                        </div>
                    </td>
                    <%--<td class="label">발송 타입 종류</td>
                    <td >
                        <div>
                            <form:checkbox path="umsTypes" label="S" value="MESSAGE" title="S" />
                            <form:checkbox path="umsTypes" label="A" value="ALIM_TALK" title="A" />
                            <form:checkbox path="umsTypes" label="P" value="PUSH" title="P" />
                        </div>
                    </td>--%>
                </tr>

                </tbody>
            </table>
        </div> <!--// board_write E-->

        <div class="btn_all">
            <div class="btn_left">
                <button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='/opmanager/ums/list';"><span class="glyphicon glyphicon-repeat"></span> ${op:message('M00047')}</button> <!-- 초기화 -->
            </div>
            <div class="btn_right">
                <button type="submit" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> ${op:message('M00048')}</button> <!-- 검색 -->
            </div>
        </div>	<!--// btn_all E-->
    </fieldset>

    <div class="count_title mt20">
        <h5>
            전체 : ${op:numberFormat(pageContent.totalElements)}건 조회
        </h5>
        <span>
        ${op:message('M00052')} :
        <form:select path="size" title="${op:message('M00239')}"> <!-- 화면출력 -->
            <form:option value="10" label="${op:message('M00240')}" />  <!-- 10개 출력 -->
            <form:option value="20" label="${op:message('M00241')}" />  <!-- 20개 출력 -->
            <form:option value="50" label="${op:message('M00242')}" />  <!-- 50개 출력 -->
        </form:select>
    </span>
    </div>
</form:form>

<div class="board_write">
    <form id="listForm">
        <table class="board_list_table">
            <colgroup>
                <col style="width:30px;" />
                <col style="width:auto;" />
                <col style="width:auto;" />
                <col style="width:100px;" />
                <col style="width:100px;" />
                <col style="width:100px;" />
                <col style="width:150px;" />
            </colgroup>
            <thead>
            <tr>
                <th scope="col">No</th>
                <th scope="col">발송 코드</th>
                <th scope="col">발송 항목</th>
                <th scope="col">발송 타입</th>
                <th scope="col">사용 여부</th>
                <th scope="col">야간 발송 여부</th>
                <th scope="col">알림톡 템플릿 상태</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="list" items="${pageContent.content}" varStatus="i">
                <tr>
                    <td>${op:numbering(pageContent, i.index)}</td>
                    <td>${list.templateCode}</td>
                    <td><a href="/opmanager/ums/edit/${list.id}">${list.templateName}</a></td>
                    <td>
                        <c:set var="alimTalkCss" value="label-default"/>
                        <c:set var="messageCss" value="label-default"/>
                        <c:set var="pushCss" value="label-default"/>

                        <c:forEach var="detailList" items="${list.detailList}" varStatus="i">
                            <c:choose>
                                <c:when test="${detailList.umsType eq 'MESSAGE' and detailList.usedFlag == true}">
                                    <c:set var="messageCss" value="label-success"/>
                                </c:when>
                                <c:when test="${detailList.umsType eq 'ALIM_TALK' and detailList.usedFlag == true}">
                                    <c:set var="alimTalkCss" value="label-success"/>
                                </c:when>
                                <c:when test="${detailList.umsType eq 'PUSH' and detailList.usedFlag == true}">
                                    <c:set var="pushCss" value="label-success"/>
                                </c:when>
                            </c:choose>
                        </c:forEach>

                        <span class="label ${alimTalkCss}">A</span>
                        <span class="label ${messageCss}">M</span>
                        <span class="label ${pushCss}">P</span>
                    </td>
                    <td>
                        <c:set var="usedFlagCss" value="label-default"/>
                        <c:set var="usedFlagLabel" value="미사용"/>

                        <c:if test='${list.usedFlag eq "true"}'>
                            <c:set var="usedFlagCss" value="label-success"/>
                            <c:set var="usedFlagLabel" value="사용"/>
                        </c:if>

                        <span class="label ${usedFlagCss}">${usedFlagLabel}</span>
                    </td>
                    <td>
                        <c:set var="nightSendFlagCss" value="label-success"/>
                        <c:set var="nightSendFlagLabel" value="불가"/>

                        <c:if test='${list.nightSendFlag eq "true"}'>
                            <c:set var="nightSendFlagCss" value="label-danger"/>
                            <c:set var="nightSendFlagLabel" value="허용"/>
                        </c:if>
                        <span class="label ${nightSendFlagCss}">${nightSendFlagLabel}</span>
                    </td>
                    <td>
                        <c:set var="alimTalkCss" value="label-default"/>
                        <c:set var="alimTalkLabel" value="미등록"/>

                        <c:set var="alimTalkIndex" value="1" />
                        <c:if test="${!empty list.detailList[alimTalkIndex].applyCode}">
                            <c:set var="alimTalkCss" value="label-success"/>
                            <c:set var="alimTalkLabel" value="등록"/>
                        </c:if>

                        <span class="label ${alimTalkCss}">
                            ${alimTalkLabel}
                        </span>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <%--<page:pagination-jpa />--%>
    </form>

    <div class="btn_all">
        <div class="btn_right">
            <%--
            <a href="/opmanager/ums/create" class="btn btn-active btn-sm"><span class="glyphicon glyphicon-plus"></span> 등록</a>
            --%>
        </div>
    </div>

    <c:if test="${empty pageContent}">
        <div class="no_content">
            UMS 설정 정보가 없습니다.
        </div>
    </c:if>

</div><!--//board_write E-->

<page:pagination-jpa />
<%--<page:pagination-manager />--%>

<script type="text/javascript">
    $(function() {
        // 데이터 출력 수 설정.
        $('#size').on("change", function(){
            $('#page').val("1");
            $('#umsParam').submit();
        });
    });

</script>
