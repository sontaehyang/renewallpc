<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>

<style type="text/css">
    .board_write_table .label p {
        margin-left: 20px;
    }
</style>

<h3><span>관리자 접속IP관리</span></h3>

<form:form modelAttribute="access" method="post">
    <form:hidden path="allowIpId"/>

<div class="board_write">
	<div class="text2">
		&nbsp;
	</div>
	<table class="board_write_table">
		<colgroup>
			<col style="width: 150px;">
		</colgroup>
		<tbody>
			 
		<tr>
            <td class="label">
                <span class="required_mark">*</span>
                <p>접근 타입</p></td>
            <td>
                <div>
                    <form:radiobutton path="accessType" value="1" cssClass="full required " title="접근 타입" label="관리자"/>
                    <form:radiobutton path="accessType" value="2" cssClass="full required " title="접근 타입" label="판매자"/>
                </div>
            </td>
		</tr>

		<tr>
            <td class="label"><span class="required_mark">*</span>
                <p>IP 주소</p></td>
			<td>
				<div>
                    <form:input path="remoteAddr" cssClass="full required" cssStyle="width: 300px" title="IP 주소"/>
                    <span class="required_mark" style="line-height: 26px;"> IP 입력시 111.111.111.111 이면 한 개의 IP 111.111.111.111 만 접근 // 111.111.111.* 이면 111.111.111.* 의 IP 대역 접근</span>
			 	</div>
			</td>
		</tr>

        </tbody>
	</table>	
	
	<p class="btns">
        <button type="submit" class="btn btn-active">등록</button>
        <a href="/opmanager/access/list" class="btn btn-default">목록</a> &nbsp;
	</p>
</div>
</form:form>

<script type="text/javascript">
$(function(){
	// validator
	$('#access').validator(function() {
		
	});
});
function fn_delete(){
	 if(confirm("접속IP를 삭제하시겠습니까?")){
		 $("#deleteForm").submit();
	 }
}
</script>