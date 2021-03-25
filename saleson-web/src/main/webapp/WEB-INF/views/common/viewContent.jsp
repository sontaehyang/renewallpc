<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>글내용</title>
<style>
body,p{margin:0;padding:0}
body{font:10pt 굴림; color:#444;}
img {border:0}

div.view_content {padding: 15px;}
</style>
<script>
// a링크를 모두 검색해서 링크를 새창으로 뜨도록 설정
function init(){
  var source = document.getElementsByTagName("a");
  for(var i=0; i<source.length; i++){
    source[0].setAttribute("target", "_blank");
  }
}
window.onload = init;
</script>
</head>
<body>
<div class="view_content">
${viewContent}
</div>
</body>
</html>