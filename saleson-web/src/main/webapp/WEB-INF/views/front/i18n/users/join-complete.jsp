<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page" %>
	
	<div class="inner"> 
		<div class="location_area">
			<div class="breadcrumbs">
				<a href="/" class="home"><span class="hide">home</span></a>
			<span>회원가입</span> 
			</div>
		</div><!-- // location_area E -->
		
		<div class="content_title"> 
			<h2 class="title">회원가입</h2>  
		</div>
		<div class="join_step step04">
			<ul>
				<li><span>01</span> 본인인증</li>
				<li><span>02</span> 약관동의</li>
				<li><span>03</span> 정보입력</li>
				<li class="on"><span>04</span> 가입완료</li>
			</ul>
		</div> <!-- // join_step E -->
	 	<div class="join_confirm"> 
			<p class="txt01">리뉴올PC 회원가입이 완료되었습니다.</p>
			<p class="txt02">리뉴올PC에 가입해 주셔서 감사합니다. 고객님께서 만족스러운 쇼핑을 하실 수 있도록 노력하겠습니다. <br>
			지금부터 리뉴올PC에서 고객님만의 특별한 혜택을 누리세요. </p>
		</div>
		<div class="btn_wrap"> 
 			<a href="/" class="btn btn-default btn-lg">홈으로</a>
<%-- 			<a href="/users/login" class="btn btn-submit btn-lg">로그인</a>--%>
 		</div>  
	</div><!--// inner E-->
	

<%--<c:if test="${ redirect ne '' }">--%>
<%--	<page:javascript>--%>
<%--	<script type="text/javascript">--%>
<%--		$(function(){--%>
<%--			setTimeout(function(){--%>
<%--				location.href = '${ redirect }';--%>
<%--			}, 3000);--%>
<%--		})--%>
<%--	</script>--%>
<%--	</page:javascript>--%>
<%--</c:if>--%>

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
if(window.wcs) _nasa["cnv"] = wcs.cnv("1","10"); // 전환유형, 전환가치 설정해야함. 설치매뉴얼 참고
</script> 