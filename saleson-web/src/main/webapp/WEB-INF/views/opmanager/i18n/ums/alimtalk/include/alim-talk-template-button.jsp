<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="op" uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop" uri="/WEB-INF/tlds/shop" %>

<div>
    <p class="text-info text-sm">
        * 타입이 '웹링크(WL)' 경우 링크 1 -> MOBILE 링크, 링크 2 -> PC 링크 입니다.<br/>
        * 타입이 '앱링크(AL)' 경우 링크 1 -> IOS, 링크 2 -> ANDROID 입니다.<br/>
        * 버튼은 최대 5개까지 입니다.
    </p>
    <div class="btn_all">
        <button type="button" class="btn btn-default btn-sm" id="addAlimTalkTemplateButton">버튼추가</button>
    </div>

    <table class="board_list_table">
        <colgroup>
            <col style="width: 60px;"/>
            <col style="width: 130px;"/>
            <col style="width: 120px;"/>
            <col style="width: auto;"/>
            <col style="width: auto;"/>
            <col style="width: 65px;"/>
        </colgroup>
        <thead>
			<tr>
				<th>No</th>
				<th>타입</th>
				<th>이름</th>
				<th>링크1</th>
				<th>링크2</th>
				<th></th>
			</tr>
        </thead>
        <tbody id="alimTalkTemplateButtonArea">
        <c:forEach var="button" items="${vendorButtons}" varStatus="i">
            <tr>
                <td>
                    <input type="hidden" name="alim_talk_template_button_ordering" title="알림톡 템플릿 버튼 노출순서" value="${button.ordering}"/>
                    <input type="hidden" name="alim_talk_template_button_id" title="알림톡 템플릿 버튼 ID" value="${button.id}"/>
                    <div>${button.ordering}</div>
                </td>
                <td>
                    <select name="alim_talk_template_button_type" class="required" title="알림톡 템플릿 버튼 타입">
                        <option value="">선택</option>
                        <c:forEach var="code" items="${templateButtonTypeList}">
                            <option <c:if test="${code.code eq button.type}">selected="selected"</c:if> value="${code.code}">
                                [${code.code}] ${code.title}
                            </option>
                        </c:forEach>
                    </select>
                </td>
                <td>
                    <input type="text" name="alim_talk_template_button_name" class="form-block required" title="알림톡 템플릿 버튼명" value="${button.name}" maxlength="20"/>
                </td>
                <td>
                    <c:set var="link1" value=""/>
                    <c:choose>
                        <c:when test='${button.type eq "WL"}'>
                            <c:set var="link1" value="${button.linkMobile}"/>
                        </c:when>
                        <c:when test='${button.type eq "AL"}'>
                            <c:set var="link1" value="${button.schemeIos}"/>
                        </c:when>
                    </c:choose>
                    <input type="text" name="alim_talk_template_button_link1" class="form-block" title="알림톡 템플릿 버튼 링크1" value="${link1}" maxlength="200"/>
                </td>
                <td>
                    <c:set var="link2" value=""/>
                    <c:choose>
                        <c:when test='${button.type eq "WL"}'>
                            <c:set var="link2" value="${button.linkPc}"/>
                        </c:when>
                        <c:when test='${button.type eq "AL"}'>
                            <c:set var="link2" value="${button.schemeAndroid}"/>
                        </c:when>
                    </c:choose>
                    <input type="text" name="alim_talk_template_button_link2" class="form-block" title="알림톡 템플릿 버튼 링크2" value="${link2}" maxlength="200"/>
                </td>
                <td>
                    <button type="button" class="btn btn-default btn-sm remove-alim-talk-template-button">제거</button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div id="alimTalkTemplateButtonFormArea">
    </div>
</div>