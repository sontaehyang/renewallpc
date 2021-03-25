<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>


<!doctype html>
<html lang="ko">
<head>
<meta charset="utf-8" />
<title>Document</title>
<meta name="robots" content="noindex,noarchive"/>

<link rel="stylesheet" type="text/css" href="/content/styles/base.css" />
<!-- 
<script type="text/javascript" src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
<script type="text/javascript">
$(function() {
	//myIP();
});



function myIP() {
    if (window.XMLHttpRequest) xmlhttp = new XMLHttpRequest();
    else xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
  
    xmlhttp.open("GET"," http://api.hostip.info/get_html.php ",false);
    xmlhttp.send();
  
    //alert(xmlhttp.responseText);
    hostipInfo = xmlhttp.responseText.split("\n");
  
    var ip = "";
    for (i=0; hostipInfo.length >= i; i++) {
    	//alert(hostipInfo[i]);
        ipAddress = hostipInfo[i].split(":");
        if ( ipAddress[0] == "IP" ) {
        	ip = ipAddress[1].trim();
        	break;
        }
    }
  
    //alert(ip);
    $('#ip').html(ip);
}
</script> -->
<style>
body {font: 24px bold verdana;}
div {background: #ffffcc; color: #000; width: 280px; margin: 200px auto 50px auto; padding: 30px 20px; border: 2px dashed #ccc; text-align: center;}

.auth_code {
	border: 3px solid #666;
	font-size: 25px;
	width: 210px;
	padding: 10px
}
.auth_button {
	background: #666;
	color: #fff;
	font-size: 20px;
	border: 0;
	padding: 11px;
}
form {
	width: 300px;
	margin: 0 auto;
}
p.error {
	color: #ff6600;
	font-size: 18px;
	padding: 5px 10px;
}
</style>
</head>
<body>
<div>
IP : <span id="ip">${ip}</span>
</div>



<form method="post" style="text-align: right">
	<a href="/demo/allow-ip" style="text-decoration: underline; color: b">* IP 등록하기 ></a>
</form>
</body>
</html>