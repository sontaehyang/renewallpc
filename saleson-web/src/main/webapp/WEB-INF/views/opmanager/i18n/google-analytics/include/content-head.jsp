<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>

<style>

    .line {
        border: 1px solid #ececec;
        margin-top: 5px;
        margin-bottom: 10px;
    }

	.list-footer span {
		margin-left: 3px;
		margin-right: 3px;
	}
</style>

<div class="location">
    <a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
</div>

<h3><span></span></h3>

<div class="board_write">
    <table class="board_write_table pg-info" summary="GoogleAnalytics 조회" id="paramTable">
        <caption>GoogleAnalytics 조회</caption>
        <colgroup>
            <col style="width:170px;" />
            <col style="width: auto;" />
        </colgroup>
        <tbody>
            <tr>
                <td class="label">profile</td>
                <td>
                    <div>
                        <input id="profile" type="hidden" value="ga:${config.profile}"/>
                        ga:${config.profile}
                    </div>
                </td>
            </tr>
            <tr>
                <td class="label">조회 기간</td>
                <td>
                    <div>
                        <span class="datepicker">
                            <input type="text" name="beginDate" value="${beginDate}" class="datepicker" maxlength="8">
                        </span>
                            <span class="wave">~</span>
                            <span class="datepicker">
                            <input type="text" name="endDate" value="${endDate}" class="datepicker" maxlength="8">
                        </span>
                            <span class="day_btns">
                            <a href="javascript:;" class="btn_date today">${op:message('M00026')}</a><!-- 오늘 -->
                            <a href="javascript:;" class="btn_date week-1">${op:message('M00027')}</a><!-- 1주일 -->
                            <a href="javascript:;" class="btn_date day-15">${op:message('M00028')}</a><!-- 15일 -->
                            <a href="javascript:;" class="btn_date month-1">${op:message('M00029')}</a><!-- 한달 -->
                        </span>
                    </div>
                </td>
            </tr>
        </tbody>
    </table>

    <div class="btn_all">
        <div class="btn_left">
        </div>
        <div class="btn_right">
            <button type="button" class="btn btn-dark-gray btn-sm" onclick="view()">
                <span class="glyphicon glyphicon-search"></span> ${op:message('M00048')}<!-- 검색 -->
            </button>
        </div>
    </div>
</div><!--//board_write E-->