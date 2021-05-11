<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page" %>

	<!-- container : s -->
	<div id="container">
		<div class="title">
			<h2>회원가입</h2>
			<span class="his_back"><a href="javascript:void(0);" class="ir_pm" onclick="history.back();">뒤로가기</a></span>
		</div><!-- //title -->
		
		<!-- 내용 : s -->
		<div class="con">
			<ol class="join_pr cf"> 
				<li><span>STEP.1</span><p>본인인증</p><img src="/content/mobile/images/common/bg_pr.gif" alt=""></li>
				<li><span>STEP.2</span><p>약관동의</p><img src="/content/mobile/images/common/bg_pr.gif" alt=""></li>
				<li><span>STEP.3</span><p>정보입력</p><img src="/content/mobile/images/common/bg_pr.gif" alt=""></li>
				<li class="on"><span>STEP.4</span><p>가입완료</p><img src="/content/mobile/images/common/bg_pr.gif" alt=""></li>
			</ol>
			<!-- join_pr -->
			
			<div class="join_area">
				<div class="comp_txt">
					<p class="ob_txt3">리뉴올PC<br/>회원가입이 완료되었습니다.</p>
					<p class="ob_txt2 deep">고객님께서 만족스러운 쇼핑을 하실 수 있도록 노력하겠습니다.<br/>지금부터 리뉴올PC에서 고객님만의 특별한 혜택을 누리세요. </p>
				</div>
				
				<div class="pr_btn typeA cf">
					<button class="btn_st1 reset" type="button" onclick="location.href='/m'" title="홈으로">홈으로</button>
					<button class="btn_st1 b_pink" type="button" onclick="location.href='/m/users/login'" title="다음단계">로그인</button>
				</div>
			</div><!-- join_area -->
		</div><!-- 내용 : e -->
	</div><!-- //#container -->

<script type="text/javascript">
	//<![CDATA[
	var DaumConversionDctSv="type=P,orderID=,amount=";
	var DaumConversionAccountID="AhEsM4T6uIfTz.omUTDgpw00";
	if(typeof DaumConversionScriptLoaded=="undefined"&&location.protocol!="file:"){
		var DaumConversionScriptLoaded=true;
		document.write(unescape("%3Cscript%20type%3D%22text/javas"+"cript%22%20src%3D%22"+(location.protocol=="https:"?"https":"http")+"%3A//t1.daumcdn.net/cssjs/common/cts/vr200/dcts.js%22%3E%3C/script%3E"));
	}
	//]]>
</script>



<!-- 전환페이지 설정 -->
<script type="text/javascript" src="//wcs.naver.net/wcslog.js"></script> 
<script type="text/javascript"> 
var _nasa={};
if(window.wcs) _nasa["cnv"] = wcs.cnv("2","10"); // 전환유형, 전환가치 설정해야함. 설치매뉴얼 참고
</script> 


<!-- 
구글 전환추적 로그분석 transaction_id 주문번호 변수 삽입
Event snippet for 구매_NEW conversion page In your html page, add the snippet and call gtag_report_conversion when someone clicks on the chosen link or button.
-->
<script>
	function gtag_report_conversion(url) {
		var callback = function () { if (typeof(url) != 'undefined') { window.location = url; } };
		gtag('event', 'conversion', { 'send_to': 'AW-754482062/8nYfCLnyx40CEI734ecC', 'value': 10.0, 'currency': 'KRW', 'transaction_id': '', 'event_callback': callback });
		return false;
	} 
</script>

