<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<h2><span>SNS연결상태</span></h2> <!-- 기본정보 -->

	<c:set var="naver" value="" />
	<c:set var="facebook" />
	<c:set var="kakao" value="" />

    <c:forEach var="userSnsList" items="${userSnsList}" varStatus="i">
        <c:choose>
            <c:when test='${userSnsList.snsType eq "naver"}'>
                <c:set var="naver" value="${userSnsList}"/>
            </c:when>
            <c:when test='${userSnsList.snsType eq "facebook"}'>
                <c:set var="facebook" value="${userSnsList}"/>
            </c:when>
            <c:when test='${userSnsList.snsType eq "kakao"}'>
                <c:set var="kakao" value="${userSnsList}"/>
            </c:when>
        </c:choose>
    </c:forEach>

	<c:set var="certifiedDate" value="인증 (${op:datetime(certified)})"/>
	<table cellpadding="0" cellspacing="0" summary="" class="board_list_table sns-list">
		<tbody>
			<tr>
				<th>SNS종류</th>
				<th>연결일시</th>
				<th>연결여부</th>
			</tr>
			<tr class='${!empty naver ? "linked" : ""}' data-type='naver' data-email='${!empty naver ? naver.email : ""}' data-order='' data-id='${!empty naver ? naver.snsUserId : ""}'>
				<td>Naver</td>
				<td>${!empty naver ? op:datetime(naver.createdDate) : ""}</td>
				<td><a href="javacript:;">${!empty naver ? "연결해제" : "미연결"}</a></td>
			</tr>
			<tr class='${!empty facebook ? "linked" : ""}' data-type='facebook' data-email='${!empty facebook ? facebook.email : ""}' data-order='' data-id='${!empty facebook ? facebook.snsUserId : ""}'>
				<td>Facebook</td>
				<td>${!empty facebook ? op:datetime(facebook.createdDate) : ""}</td>
				<td><a href="javacript:;">${!empty facebook ? "연결해제" : "미연결"}</a></td>
			</tr>
			<tr class='${!empty kakao ? "linked" : ""}' data-type='kakao' data-email='${!empty kakao ? kakao.email : ""}' data-order='' data-id='${!empty kakao ? kakao.snsUserId : ""}'>
				<td>Kakao</td>
				<td>${!empty kakao ? op:datetime(kakao.createdDate) : ""}</td>
				<td><a href="javacript:;" data-type='kakao'>${!empty kakao ? "연결해제" : "미연결"}</a></td>
			</tr>
			<tr>
				<th colspan="2">계정인증상태</th>
				<th>${empty certified ? "미인증" : certifiedDate}</th>
			</tr>
		</tbody>
	</table>

<script type="text/javascript">
	$(function(){
		Manager.activeUserDetails("sns-user");
		
		$(".sns-list tr a").on("click",function(event){
			event.stopPropagation();
			event.preventDefault();
			var $li = $(this).parent().parent();
			var type = $li.data("type");
			var status = $li.hasClass("linked") ? "on" : "off";
			var order = $li.data("order");
			var certified = "${certified}";
			
			// sns연동일 경우 disconnect 진행, 미연동일 경우 연동 진행
			if (status == "on") {
				if (!confirm("선택하신 SNS의 연결해제를 진행하시겠습니까?")) {
					return false;
				}
				var id = $li.data("id");
				var param = {"snsUserId" : id};
				$.ajaxSetup({
					"async" : false
				});
				$.post("/sns-user/disconnect-sns", param, function(response){
					if (response.status == "00") {
						alert(response.message);
						location.reload();
					}
					else {
						console.log("error occurred - " + response.message);
					}
				});
				return false;
			}
			else {
				// 미연결일 경우 실행될 스크립트
			}
		});
	});
</script>

