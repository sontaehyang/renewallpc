<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>

<!-- 본문 -->
<div class="popup_wrap">
    <h1 class="popup_title">문의하기</h1>
    <form:form modelAttribute="alimTalkTemplateComment">
        <div class="popup_contents pb0">
            <table cellpadding="0" cellspacing="0" summary="" class="board_write_table">
                <colgroup>
                    <col style="width:120px;" />
                    <col style="width:*;" />
                </colgroup>
                <tbody>
                    <tr>
                        <th class="label">문의 내용</th>
                        <td>
                            <div>
                                <form:textarea path="content" cssClass="required _filter content" title="내용" style="height:250px;" />
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
            <p class="popup_btns">
                <button type="submit" class="btn btn-active w250 mb10"><span>등록</span></button><br/>
            </p>
        </div>
    </form:form>

    <a href="#" class="popup_close">창 닫기</a>
</div>

<script type="text/javascript">
    $(function() {
        $("#alimTalkTemplateComment").validator(function(){});
    });
</script>