<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<div class="inner">
	<div class="location_area">
		<div class="breadcrumbs">
			<a href="#" class="home"><span class="hide">home</span></a>
			<span>로그인</span> 
		</div>
	</div><!-- // location_area E -->
	
	<div class="content_title"> 
		<h2 class="title">장기간 미사용 계정 휴면 전환 안내</h2> 
	</div>
	<div class="order_confirm notice"> 
		<p>회원님의 계정은 개인정보보호를 위해 1년 이상 리뉴올PC 서비스를 이용하지 않은 계정에 한해<br/>정보통신망 이용 촉진 및 정보보호 등에 관한 법률에 따라 휴면 계정으로 전환되었습니다.</p>
		<strong>리뉴올PC를 다시 이용하시려면,  하단의 “계속 이용하기” 버튼을 클릭해 주시기 바랍니다.</strong>
	</div><!-- // order_confirm notice E -->
	<div class="btn_wrap pt30">  
		<button type="button" class="btn btn-default btn-lg c_666" title="휴면계정유지" onclick="location.href='/op_security_logout'">휴면계정유지</button>  
		<button type="button" class="btn btn-success btn-lg" title="계속 이용하기" onclick="wakeUpSleepUser()">계속 이용하기</button> 
	</div><!-- // btn_wrap E -->
	<div class="board_wrap mt70">  
		<table cellpadding="0" cellspacing="0" class="order_view nt">
			<caption>배송지 1</caption>
			<colgroup>
				<col style="width:128px;">
				<col style="width:auto;">	 				
			</colgroup>
			<tbody>
				<tr>
					<th scope="row">휴면계정시<br/>제한사항</th>
					<td>
						<p>- 회원의 개인정보는 별도 분리하여 보관하게 됩니다.</p>
						<p>- 메일 및 SMS 수신이 중지됩니다.</p>
					</td> 
				</tr> 
				<tr>
					<th scope="row">일반회원 전환시<br/>변경사항</th>
					<td>
						<p>- 회원님의 개인정보가 복원됩니다.</p>
						<p>- 메일 및 SMS를 다시 수신하실 수 있습니다.</p>
					</td> 
				</tr> 
				<tr>
					<th scope="row">리뉴올PC<br/>이용약관</th>
					<td>회원이 12개월(365일)이상 로그인을 하지 않는 경우 해당 회원의 아이디는 휴면아이디가 되어 회원 로그인을 비롯한 모든 서비스에 대한 이용이 정지되고, 회사는 휴면 아이디의 개인 정보를 다른 아이디로 별도로 관리한다.</td>
				</tr> 
			</tbody>
		</table><!-- // order_view E -->
	</div><!-- // board_wrap E -->


</div><!-- // inner E-- -->

<page:javascript>
<script type="text/javascript">

function wakeUpSleepUser() {
	$.get('/users/wakeup-user', function(response) {
		if (response.isSuccess) {
			alert("고객님의 계정이 휴면해제 되었습니다.\n원활한 서비스 이용을 위하여 재 로그인 해주십시오.");
			location.href="/users/login";
		} else {
			alert(response.errorMessage);	
		}
	});
}

</script>
</page:javascript>