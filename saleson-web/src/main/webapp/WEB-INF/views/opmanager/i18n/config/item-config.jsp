<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>

<div class="location">
    <a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
</div>

<form:form modelAttribute="config" method="post">
    <h3><span>상품 상/하단 내용 관리</span></h3>
    <div class="board_write">
        <table class="board_write_table" summary="상품 상/하단 내용 관리">
            <colgroup>
                <col style="width: 180px;" />
                <col style="" />
                <col style="width: 160px;" />
                <col style="" />
            </colgroup>
            <tbody>
            <tr>
                <td class="label">상품 상단 내용</td>
                <td colspan="3">
                    <div>
                        <form:textarea path="itemHeaderContent" cols="30" rows="6" style="width: 1085px" title="상품 상단 내용" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="label">상품 하단 내용</td>
                <td colspan="3">
                    <div>
                        <form:textarea path="itemFooterContent" cols="30" rows="6" style="width: 1085px" title="상품 하단 내용" />
                    </div>
                </td>
            </tr>
            </tbody>
        </table>
    </div> <!-- // board_write -->

    <h3 class="mt50"><span>상품 상/하단 내용 관리 (MOBILE)</span></h3>
    <div class="board_write">
        <table class="board_write_table" summary="상품 상/하단 내용 관리 (MOBILE)">
            <colgroup>
                <col style="width: 180px;" />
                <col style="" />
                <col style="width: 160px;" />
                <col style="" />
            </colgroup>
            <tbody>
            <tr>
                <td class="label">상품 상단 내용 (MOBILE)</td>
                <td colspan="3">
                    <div>
                        <form:textarea path="itemHeaderContentMobile" cols="30" rows="6" style="width: 1085px" title="상품 상단 내용" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="label">상품 하단 내용 (MOBILE)</td>
                <td colspan="3">
                    <div>
                        <form:textarea path="itemFooterContentMobile" cols="30" rows="6" style="width: 1085px" title="상품 하단 내용" />
                    </div>
                </td>
            </tr>
            </tbody>
        </table>
    </div> <!-- // board_write -->

    <!-- 버튼시작 -->
    <div class="tex_c mt20">
        <button type="submit" class="btn btn-active"><span>${op:message('M00101')}</span></button>
    </div>
    <!-- 버튼 끝-->
</form:form>

<module:smarteditorInit />
<module:smarteditor id="itemHeaderContent" />
<module:smarteditor id="itemFooterContent" />
<module:smarteditor id="itemHeaderContentMobile" />
<module:smarteditor id="itemFooterContentMobile" />

<script type="text/javascript">
    $(function() {
        // validator
        $('#config').validator(function() {
            // 에디터 내용 설정
            Common.getEditorContent("itemHeaderContent");
            Common.getEditorContent("itemFooterContent");
            Common.getEditorContent("itemHeaderContentMobile");
            Common.getEditorContent("itemFooterContentMobile");
        });
    });
</script>