<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>

<style>
    .mr3 {margin-right: 3px;}
    .mr7 {margin-right: 7px;}
    .fl {float: left;}
</style>

<!-- MiniColors -->
<script src="/content/modules/jquery/minicolors/jquery.minicolors.js"></script>
<link rel="stylesheet" href="/content/modules/jquery/minicolors/jquery.minicolors.css" />

<div class="location">
    <a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
</div>

<c:set var="isPopup" value="${target == 'popup'}" />

<div class="${isPopup ? 'popup_wrap' : 'board_write'}">
    <c:choose>
        <c:when test="${isPopup}">
            <h1 class="popup_title">카테고리 필터 관리</h1>
        </c:when>
        <c:otherwise>
            <h3><span>카테고리 필터 관리</span></h3>
        </c:otherwise>
    </c:choose>
    <div class="${isPopup ? 'popup_contents' : ''}">
        <form:form modelAttribute="filterGroup" method="post" enctype="multipart/form-data">
            <table class="board_write_table">
                <caption>카테고리 필터 관리</caption>
                <colgroup>
                    <col style="width:150px;" />
                    <col style="width:auto;" />
                </colgroup>
                <tbody>
                <tr>
                    <td class="label">필터 그룹명 <span class="require">*</span></td>
                    <td>
                        <div>
                            <form:input path="label" class="form-half required" title="필터 그룹명" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="label">필터 설명 <span class="require">*</span></td>
                    <td>
                        <div>
                            <form:input path="description" class="form-half required" title="필터 설명" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="label">필터 타입 <span class="require">*</span></td>
                    <td>
                        <div>
                            <c:forEach items="${filterTypes}" var="type" varStatus="i">
                                <form:radiobutton path="filterType" label="${type.title}" value="${type.code}" checked="${type.code == 'NORMAL' ? 'checked' : ''}" />
                            </c:forEach>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="label">필터 옵션 <span class="require">*</span></td>
                    <td>
                        <div class="text-info text-sm">
                            * [+ 항목추가] 버튼으로 항목을 추가할 수 있습니다.<br/>
                            * 항목을 삭제한 후 저장 버튼을 클릭하여 반영합니다.
                        </div>
                        <div>
                            <button type="button" class="btn btn-gradient btn-sm" onclick="addInput();">+ 항목추가</button>
                        </div>

                        <div id="code-input-area">
                            <c:choose>
                                <c:when test="${filterGroup.codeList.size() > 0}">
                                    <c:forEach items="${filterGroup.codeList}" var="code" varStatus="i">
                                        <p class="code-zone mt5">
                                            <form:hidden path="codeList[${i.index}].id" />
                                            <span class="code-label" id="label_${i.index}"><form:input path="codeList[${i.index}].label" class="required mr7 fl" title="필터 옵션"/></span>
                                            <span class="code-color"><form:input path="codeList[${i.index}].labelCode" class="colorpicker required" title="색상" maxlength="7" /></span>
                                            <span class="code-image">
                                                    <input type="file" name="codeList[${i.index}].imageFile" title="이미지" class="fl" />
                                                    <c:if test="${fn:indexOf(filterGroup.codeList[i.index].imageSrc, 'no-image') == -1}">
                                                        <img src="${filterGroup.codeList[i.index].imageSrc}" id="codeList[${i.index}].labelImage" name="codeList[${i.index}].labelImage" title="이미지" style="width: 100px; height: 100px;" />
                                                    </c:if>
                                                </span>
                                            <button type="button" class="btn btn-dark-gray btn-sm ml5 op-delete-filter">삭제</button>
                                        </p>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <p class="code-zone mt5">
                                        <span class="code-label" id="label_0"><form:input path="codeList[0].label" class="required mr7 fl" title="필터 옵션" /></span>
                                        <span class="code-color"><form:input path="codeList[0].labelCode" class="colorpicker mr_2" title="색상" maxlength="7" /></span>
                                        <span class="code-image"><input type="file" name="codeList[0].imageFile" title="이미지" class="fl" /></span>
                                        <button type="button" class="btn btn-dark-gray btn-sm ml5 op-delete-filter">삭제</button>
                                    </p>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>

            <!-- 버튼시작 -->
            <div class="tex_c mt20">
                <button type="submit" class="btn btn-active"><span>${op:message('M00101')} </span></button>
                <a href="/opmanager/categories-filter/list?target=${target}" class="btn btn-default">${op:message('M00037')} </a>
            </div>
            <!-- 버튼 끝-->
        </form:form>

        <div class="board_guide">
            <br/>
            <p class="tip">[안내]</p>
            <p class="tip">
                필터 타입 변경, 필터 옵션 수정/삭제시 연동된 상품 필터가 해제됩니다.<br/>
                * 필터 타입(전체) - 필터 이름 변경시 연동 해제<br/>
                * 필터 타입(색상) - 필터 색상 변경시 연동 해제<br/>
            </p>
        </div>
    </div>
</div>

<script type="text/javascript">
    let color = '${color}';
    let image = '${image}';
    let target = '${target}';
    let count = '${filterGroup.codeList.size() > 0 ? filterGroup.codeList.size() : 1}';
    let maxCount = 10;

    $(function(){
        $('.colorpicker').minicolors();

        filterTypeCheck();

        $('input[name="filterType"]').on('click', function () {
            filterTypeCheck();
        });

        $('#filterGroup').validator(function() {
            /*if (count > maxCount) {
                alert('필터 옵션은 최대 ' + maxCount + '개까지 추가 가능합니다.');
                return false;
            }*/

            if (!confirm('필터 정보를 저장하시겠습니까?')) {
                return false;
            }
        });
    });

    function addInput() {
        var index = 0;
        $('.code-label').each(function(i) {
            index = $(this).attr('id').replace('label_', '');
        });

        index++;

        let html =
            '<p class="code-zone mt5">' +
            '<span class="code-label" id="label_' + index + '"><input type="text" name="codeList[' + index + '].label" class="required mr7 fl" title="필터 옵션"></span> ' +
            '<span class="code-color"><input type="text" name="codeList[' + index  + '].labelCode" class="colorpicker required mr3" title="색상" maxlength="7"></span>' +
            '<span class="code-image"><input type="file"name="codeList[' + index + '].imageFile" title="이미지" class="required fl"/></span>' +
            '<button type="button" class="btn btn-dark-gray btn-sm ml5 op-delete-filter">삭제</button>' +
            '</p>';

        //if (count < maxCount) {
        $('#code-input-area').append(html);
        $('.colorpicker').minicolors();

        filterTypeCheck();
        count++;
        //}
    }

    $('#code-input-area').on('click', '.op-delete-filter', function() {
        if (count > 1) {
            $(this).parent().remove();
            count--;

        } else {
            alert('필터 옵션을 최소 1개이상 등록해주세요.');
            return false;
        }
    });

    function filterTypeCheck() {
        if ($('input[name="filterType"]:checked').val() == color) {
            $('.code-image').hide();
            $('.code-image input:file').removeClass('required');

            $('.code-color').show();
            $('.code-color .colorpicker').addClass('required');

        } else if ($('input[name="filterType"]:checked').val() == image) {
            $('.code-color').hide();
            $('.code-color .colorpicker').removeClass('required');

            $('.code-image').show();

            $('input[type=file]').each(function() {
                if ($(this).siblings('img').length == 0) {
                    $(this).addClass('required');
                }
            });
        } else {
            $('.code-color').hide();
            $('.code-color .colorpicker').removeClass('required');

            $('.code-image').hide();
            $('.code-image input:file').removeClass('required');
        }
    }
</script>