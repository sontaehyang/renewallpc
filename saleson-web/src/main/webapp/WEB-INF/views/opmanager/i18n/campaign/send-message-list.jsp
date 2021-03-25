<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="op" uri="/WEB-INF/tlds/functions" %>

<style>
    span.pagination {width: 100%; text-align: center;}
</style>

<div class="popup_wrap">
    <h1 class="popup_title">발송 메시지 목록</h1>
    <div class="popup_contents">
        <form:form modelAttribute="campaignDto" method="get" action="/opmanager/campaign/create/send-message-list">
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
                            <td class="label">발송 메시지 검색</td>
                            <td>
                                <div>
                                    <form:select path="where" class="choice3">
                                        <form:option value="title">제목</form:option>
                                        <form:option value="content">발송문구</form:option>
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
                <colgroup>
                    <col style="width: 150px;"/>
                    <col style=""/>
                    <col style="width: 80px;"/>
                    <col style="width: 30px;"/>
                </colgroup>
                <thead>
                <tr>
                    <th scope="label">제목</th>
                    <th scope="label">발송문구</th>
                    <th scope="label">URL</th>
                    <th class="label">선택</th>
                </tr>
                <tbody>
                <c:forEach var="list" items="${pageContent.content}" varStatus="i">

                    <tr data-id="${list.id}"
                        data-title="${list.title}"
                        data-content="${list.content}"
                        data-url-length="${fn:length(list.urlList)}">
                        <td>${list.title}</td>
                        <td>${list.content}</td>
                        <td id="campaign_url_list">
                            <c:forEach var="url" items="${list.urlList}" varStatus="j">
                                <input type="hidden" name="campaign_url" value="${url.contents}" />
                                ${url.contents}
                            </c:forEach>
                        </td>
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

            <span class="pagination">
                <page:pagination-jpa />
            </span>
        </div>

    </div>
</div>

<page:javascript>
    <script type="text/javascript">
        $(function() {
            $('.btn-select').on('click', function(e) {
                e.preventDefault();

                var $tr = $(this).closest('tr');
                var campaign = $tr.data();

                var urlList = new Array();
                $tr.find('input[name=campaign_url]').each(function(index, value) {
                    urlList.push($(value).val());
                });

                opener.handleFindCampaigncallback(campaign, urlList);
                self.close();
            });
        });

    </script>

</page:javascript>