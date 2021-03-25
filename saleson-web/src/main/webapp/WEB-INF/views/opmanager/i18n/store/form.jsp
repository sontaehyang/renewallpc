<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="daum"	tagdir="/WEB-INF/tags/daum" %>

<div class="location">
    <a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
</div>

<div class="board_write">
    <h3><span>판매처 관리</span></h3>
    <form:form modelAttribute="store" method="post" enctype="multipart/form-data">
        <table class="board_write_table">
            <caption>판매처 관리</caption>
            <colgroup>
                <col style="width:150px;" />
                <col style="width:auto;" />
            </colgroup>
            <tbody>
                <tr>
                    <td class="label">판매처명 <span class="require">*</span></td>
                    <td>
                        <div>
                            <form:input path="name" class="form-half required" title="판매처명" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="label">주소 <span class="require">*</span></td>
                    <td>
                        <div>
                            <form:hidden path="post" />
                            <form:hidden path="sido" />
                            <p class="mt5">
                                <form:input path="newPost" class="one required" title="신 우편번호" maxlength="5" readonly="true" />
                                <button type="button" onclick="openDaumPostcode()" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> 검색</button>
                            </p>
                            <p class="mt5">
                                <form:input path="address" class="half required" title="주소" readonly="true" />
                            </p>
                            <p class="mt5">
                                <form:input path="addressDetail" class="half required" title="상세주소" maxlength="200" />
                            </p>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="label">백화점 <span class="require">*</span></td>
                    <td>
                        <div>
                            <form:radiobutton path="storeType" label="전체" value="" checked="checked" />
                            <c:forEach items="${storeTypes}" var="type" varStatus="i">
                                <form:radiobutton path="storeType" label="${type.title}" value="${type.code}"/>
                            </c:forEach>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="label">전화번호 <span class="require">*</span></td>
                    <td>
                        <div>
                            <c:set var="tel_arr" value="${fn:split(store.telNumber, '-') }"/>
                            <select name="telNumber1" class="choice3">
                                <c:forEach items="${telCodes}" var="code">
                                    <option value="${code.value}" ${code.value == tel_arr[0] ? 'selected="selected"' : '' }>${code.label}</option>
                                </c:forEach>
                            </select>
                            <span class="wave">-</span>
                            <input type="text" name="telNumber2" value="${tel_arr[1]}" maxlength="4" class="form-sm _number required" title="전화번호 가운데자리" />
                            <span class="wave">-</span>
                            <input type="text" name="telNumber3" value="${tel_arr[2]}" maxlength="4" class="form-sm _number required" title="전화번호 마지막자리" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="label">영업 시간 <span class="require">*</span></td>
                    <td>
                        <div>
                            <form:hidden path="startTime" />
                            <form:hidden path="endTime" />

                            <c:set var="startHour" value="${fn:substring(store.startTime, 0, 2)}" />
                            <c:set var="startMinute" value="${fn:substring(store.startTime, 2, 4)}" />
                            <select id="startHour" name="startHour" title="시작시간">
                                <c:forEach begin="0" end="24" var="hour" varStatus="i">
                                    <option value="${hour < 10 ? '0' : ''}${hour}" ${startHour eq hour ? 'selected' : ''}>${hour < 10 ? '0' : ''}${hour}</option>
                                </c:forEach>
                            </select>
                            <span class="wave">:</span>
                            <select id="startMinute" name="startMinute" title="시작 분">
                                <c:forEach begin="0" end="59" var="minute" varStatus="i">
                                    <option value="${minute < 10 ? '0' : ''}${minute}" ${startMinute eq minute ? 'selected' : ''}>${minute < 10 ? '0' : ''}${minute}</option>
                                </c:forEach>
                            </select>

                            <span class="wave">~</span>

                            <c:set var="endHour" value="${fn:substring(store.endTime, 0, 2)}" />
                            <c:set var="endMinute" value="${fn:substring(store.endTime, 2, 4)}" />
                            <select id="endHour" name="endHour" title="종료시간">
                                <c:forEach begin="0" end="24" var="hour" varStatus="i">
                                    <option value="${hour < 10 ? '0' : ''}${hour}" ${endHour eq hour ? 'selected' : ''}>${hour < 10 ? '0' : ''}${hour}</option>
                                </c:forEach>
                            </select>
                            <span class="wave">:</span>
                            <select id="endMinute" name="endMinute" title="종료 분">
                                <c:forEach begin="0" end="59" var="minute" varStatus="i">
                                    <option value="${minute < 10 ? '0' : ''}${minute}" ${endMinute eq minute ? 'selected' : ''}>${minute < 10 ? '0' : ''}${minute}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>

        <!-- 버튼시작 -->
        <div class="tex_c mt20">
            <button type="submit" class="btn btn-active"><span>${op:message('M00101')} </span></button>
            <a href="/opmanager/store/list" class="btn btn-default">${op:message('M00037')}</a>
        </div>
        <!-- 버튼 끝-->

    </form:form>
</div>

<daum:address />
<script type="text/javascript">
    $(function(){
        $('#store').validator(function() {
            $('#startTime').val($('#startHour').val() + $('#startMinute').val());
            $('#endTime').val($('#endHour').val() + $('#endMinute').val());

            if (!confirm('판매처 정보를 저장하시겠습니까?')) {
                return false;
            }
        });
    });

    function openDaumPostcode() {
        var tagNames = {
            'newZipcode'			: 'newPost',
            'zipcode' 				: 'post'
        };

        openDaumAddress(tagNames);
    }
</script>