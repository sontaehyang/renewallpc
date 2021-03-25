<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="google-site-verification" content="SmohXHHHgY5oD0bmt3-vcberAyDga1Levn2hDHDBfbs" />
<title>메세지 변환</title>

<script type="text/javascript" src="/content/modules/jquery/jquery-1.11.0.min.js"></script>
	<script type="text/javascript" src="/content/modules/spin.min.js"></script>

<script type="text/javascript" src="/content/modules/op.common.js"></script>


<style>
* {margin:0; padding: 0; border: 0;}
body {font-family: segoe UI; font-size: 12px;}
h1 {background: #f1f1f1; height: 42px; padding: 18px 0 0 20px; border: 0px dashed #ccc; color: #e04e38; font-size: 20px; font-weight: normal;}
h2 {color: #e04e38; height: 60px; border-bottom: 1px solid #e1e1e1;}
fieldset {}

#header {background: #f1f1f1;}
#left {float: left; width: 50%; text-align: center; height: 240px;}
	.inner {padding: 20px;}
	textarea {width: 100%; border: 1px solid #488dff; height: 200px; font-size: 15px; padding: 9px; line-height: 1.6; text-align: left;}
#result {float: left; width: 50%; ; height: 240px;}
#resultController {float: left; width: 50%; ; height: 240px;}
#resultScript {float: left; width: 50%; ; height: 240px;}
	.result {background: #f5f5f5; height: 100%; height: 200px; font-size: 14px; overflow-y: auto; padding: 10px; line-height: 1.6;}
#button {height: 30px;}	

button {position: absolute; left: 50%; margin-left: 20px; margin-top: 10px; background: #4588f9; color: #fff; padding: 7px 20px; font-size: 12px;}
</style>
</head>
<body>
<div id="header">
	<h1>변환 <!-- 메세지 코드 변환 --></h1>
</div>


<div id="content">
	<form:form modelAttribute="message" action="/demo/message" method="post">

	<fieldset>
		<div id="button">
			<button type="submit">변환하기</button>
		</div>
		<div id="left">
			<div class="inner">
				<form:textarea path="content" />
			</div>
		
		</div>
		

		<div id="result">
			<div class="inner">
				<div class="result">${op:nl2br(result)}</div>
			
			</div>
		
		</div>
		
		<div id="resultController">
			<div class="inner" style="padding-right: 0;">
				<div class="result">${op:nl2br(resultController)}</div>
			
			</div>
		
		</div>
		
		<div id="resultScript">
			<div class="inner">
				<div class="result">${op:nl2br(resultScript)}</div>
			
			</div>
		
		</div>

		
	
	</fieldset>
	</form:form>
</div>

<script>
/*
$(function() {
	$.post("/demo/json", '', function(response) {
		alert(response[0].title);
	}, 'json');
});

*/

</script>