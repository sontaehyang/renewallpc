<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>


<div class="location">
    <a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
</div>

<h3><span>약관/개인정보처리방침</span></h3>
<div class="board_write mt20">
    <form:form modelAttribute="policy" method="post" enctype="multipart/form-data">
        <table class="board_write_table" cellpadding="0" cellspacing="0" summary="${op:message('M00206')}">
            <caption>${op:message('M00206')}</caption>
            <colgroup>
                <col style="width: 200px;">
                <col style="width: auto;">
            </colgroup>
            <tbody>
            <tr>
                <td class="label">정책 타입</td>
                <td>
                    <div>
                        <form:radiobutton path="policyType" value="0" label="약관" checked="checked" />
                        <form:radiobutton path="policyType" value="1" label="개인정보처리방침" />
                        <form:radiobutton path="policyType" value="3" label="마케팅이용약관" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="label">제목</td>
                <td>
                    <div>
                        <form:input path="title" title="제목"  class="half required" maxlength="70"/>
                    </div>
                </td>
            </tr>
            <tr>
                <td class="label">전시 여부</td>
                <td>
                    <div>
                        <form:radiobutton path="exhibitionStatus" value="Y" label="공개" checked="checked" />
                        <form:radiobutton path="exhibitionStatus" value="N" label="비공개" />
                    </div>
                </td>
            </tr>

            </tbody>
        </table>
        <br/>
        <tr style="width: auto;">
            <td>
                <div>
                    <form:textarea path="content" cols="100" rows="20" style="width:100%;height:300px;" title="내용" />
                </div>
            </td>
        </tr>

        <p class="btn_center">
            <button type="submit" class="btn btn-active">${op:message('M00101')}</button>
            <a href="javascript:Link.list('${requestContext.managerUri}/config/policy/list')" class="btn btn-default">${op:message('M00480')}</a>	<!-- 목록 -->
        </p>
    </form:form>
</div><!--//board_write E-->

<module:smarteditorInit />
<module:smarteditor id="content" />

<script type="text/javascript">
    $(function() {

        /*Common.DateButtonEvent.set('.day_btns > a[class^=table_btn]', '', 'input[name="searchStartDate"]' , 'input[name="searchEndDate"]');*/

        // validator
        $('#policy').validator(
            function() {

                return Common.getEditorContent("content", true);

            });



    });

</script>
