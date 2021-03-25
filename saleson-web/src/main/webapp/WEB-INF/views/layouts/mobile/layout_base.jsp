<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!doctype html>
<html>
<head>
	<tiles:insertAttribute name="head"></tiles:insertAttribute>
	<meta content="width=device-width,initial-scale=1.0,user-scalable=yes" name="viewport">
	
</head>

<body>
<div id="wrap" class="popup_wrap">
	<section id="mobile_main">
		<header id="header">
			<!-- h1><a href="/m" id="popup_logo"><img src="/content/mobile/images/common/mobile_logo.gif" alt="" /></a></h1> -->
		</header>
		
		<section id="container">
			<tiles:insertAttribute name="content"></tiles:insertAttribute>
		</section>
	</section>
</div>	


<tiles:insertAttribute name="common"></tiles:insertAttribute>

<script type="text/javascript">
$(function() {
	$('#popup_logo').on('click', function(e) {
		var existOpener = $(window.opener).size() > 0 ? true : false;
		
		if (existOpener) {
			e.preventDefault();
			opener.location.href = '/m/';
			self.close();
		}
	});
});
</script>
<jsp:include page="../common/adwords-overture-script.jsp" />
<tiles:insertAttribute name="script"></tiles:insertAttribute>
</body>
</html>

