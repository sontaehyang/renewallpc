<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>

<style>
	.label {
		text-align: center;
	}

	td {
		padding-left: 10px;
	}

	.td-head {
		border-top: #0d0d0d solid 2px !important;
	}
</style>

<h3><span>ISMS 시간설정 관리</span></h3>

	<div class="board_write">
		<table class="board_write_table" summary="${op:message('M00527')}">
			<caption>${op:message('M00527')}</caption>
			<colgroup>
				<col style="width:100px;">
				<col style="width:250px;">
				<col style="width:auto;">
			</colgroup>
			<tbody>
				<c:set var="commonCount" value="0"/>
				<c:set var="managerCount" value="0"/>
				<c:set var="userCount" value="0"/>

				<c:forEach items="${getIsmsList}" var="rs" varStatus="i">
					<c:choose>
						<c:when test='${rs.ismsType == "0"}'><c:set var="commonCount" value="${commonCount+1}"/></c:when>
						<c:when test='${rs.ismsType == "1"}'><c:set var="managerCount" value="${managerCount+1}"/></c:when>
						<c:when test='${rs.ismsType == "2"}'><c:set var="userCount" value="${userCount+1}"/></c:when>
					</c:choose>
				</c:forEach>

				<c:set var="isCommonHead" value="Y"/>
				<c:set var="isManagerHead" value="Y"/>
				<c:set var="isUserHead" value="Y"/>

				<c:forEach items="${getIsmsList}" var="rs" varStatus="i">

					<c:set var="headClass" value=''/>

					<tr>
						<c:set var="ismsTypeName" value=""/>
						<c:set var="ismsTypeCount" value=""/>
						<c:choose>
							<c:when test='${rs.ismsType == "0" and isCommonHead == "Y"}'>
								<c:set var="ismsTypeName" value="공통"/>
								<c:set var="ismsTypeCount" value="${commonCount}"/>
								<c:set var="isCommonHead" value="N"/>

							</c:when>
							<c:when test='${rs.ismsType == "1" and isManagerHead == "Y"}'>
								<c:set var="ismsTypeName" value="관리자"/>
								<c:set var="ismsTypeCount" value="${managerCount}"/>
								<c:set var="isManagerHead" value="N"/>

							</c:when>
							<c:when test='${rs.ismsType == "2" and isUserHead == "Y"}'>
								<c:set var="ismsTypeName" value="회원"/>
								<c:set var="ismsTypeCount" value="${userCount}"/>
								<c:set var="isUserHead" value="N"/>

							</c:when>
						</c:choose>

						<c:if test="${not empty ismsTypeName and ismsTypeCount > 0}">
							<td class="label td-head" rowspan="${ismsTypeCount}">${ismsTypeName}</td>
							<c:set var="headClass" value='class="td-head"'/>
						</c:if>

						<td ${headClass}>${rs.description}</td>
						<td ${headClass}>
							<div>
								<input type="text" id="${rs.key}" class="form-sm text-center form-amount required" value="${rs.value }" />
							</div>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="btn_center">
		<button type="button" id="btnSubmit" class="btn btn-active"><span>${op:message('M00101')}</span></button>  <!-- 저장 -->
	</div>

<script type="text/javascript">
	$("#btnSubmit").click(function(){

     	var param = {data : JSON.stringify(createParamData())};

		$.post("/opmanager/isms/isms-config", param, function(response){
			alert("반영되었습니다.");
        }, 'json').error(function(e){
            alert(e.message);
        });
	});

    function createParamData(){
        var ary = new Array();
        var body = "";
        var key = "";
        var value = "";
        for(var i = 0; i < $("table tbody input").size(); i++){
            key = $("table tbody input")[i].id;
            value = $("#"+key).val();
            body = {"key":key,"value":value}
            ary.push(body);
        }
        return ary;
    }

</script>