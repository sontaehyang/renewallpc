<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<div class="location">
    <a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
</div>

<div class="item_list">
    <h3 class="mt20"><span>SNS 연동 관리</span></h3>

    <form:form modelAttribute="displaySns" method="post" enctype="multipart/form-data">
        <form:hidden path="snsId" />
        <div class="board_write">
            <table class="board_write_table" summary="SNS 연동 관리">
                <caption>SNS 연동 관리</caption>
                <colgroup>
                    <col style="width: 220px;" />
                    <col style="width: auto;" />
                </colgroup>
                <tbody>
                    <td class="label">TOKEN <span class="require">*</span></td>
                        <td>
                            <div>
                                <form:input path="snsToken" class="half required" title="TOKEN" maxlength="200" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="label">SNS 종류 <span class="require">*</span></td>
                        <td>
                            <div>
                                <form:radiobutton path="snsType" value="youtube" checked="checked" label="youtube" class="required requirement" title="youtube" />
                                <form:radiobutton path="snsType" value="facebook" label="facebook" class="required requirement" title="facebook" />
                                <form:radiobutton path="snsType" value="instagram" label="instagram" class="required requirement" title="instagram" />
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>

        <div class="btn_center" style="padding-right: 190px;">
            <button type="submit" class="btn btn-active">${op:message('M00088')}</button>	 <!-- 등록 -->
            <a href="/opmanager/display/sns/list" class="btn btn-default">${op:message('M00037')}</a>	 <!-- 취소 -->
        </div>
    </form:form>
</div> <!-- // item_list02 -->

<script type="text/javascript">
    $("#displaySns").validator(function() {});
</script>