<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>

<body>
<style>
    .err_wrap {position:fixed;width:1250px;left:50%;top:45%;transform:translate(-50%, -50%);}
    .err_wrap .err_info {font-size:16px;font-weight:500;text-align:center;line-height:24px;}
    .err_wrap .err_info > dt {margin-bottom:30px;font-size:22px;font-weight:700;color:#000;}
    .btn img{vertical-align:middle; margin-top:-1px; margin-right:3px;}
</style>
<div class="err_wrap">
    <dl class="err_info">
        <img src="/content/images/common/ready.jpg" alt="서비스 준비 중입니다.">
    </dl><!--// err_info -->
</div>
</body>