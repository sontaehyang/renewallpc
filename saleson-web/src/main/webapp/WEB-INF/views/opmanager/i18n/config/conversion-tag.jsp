<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>


		<div class="location">
			<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
		</div>

		<h3><span>${op:message('M00129')} </span></h3>
		<div class="conversion_box">
			<div>
				${op:message('M00130')}
			</div>
			<div class="right">
				<a href="#" onclick="Common.popup(url('/opmanager/config/popup/conversion-popup'), 'conversion', 450, 300);" class="btn_date">${op:message('M00132')}</a>
			</div>
		</div><!--//conversion_box E-->
		
		<!-- 버튼시작 -->		 
		<div class="btn_center">
			<a href="https://login41.marketingsolutions.overture.com/adui/signin/loadSignin.do?l=ja_JP" target="_blank" class="ctrl_btn">OVERTURE ${op:message('M00131')}</a>  
			&nbsp;
			<a href="https://adwords.google.co.jp/select/home" target="_blank" class="ctrl_btn">ADWORDS ${op:message('M00131')}</a>				 
		</div>			 
		<!-- 버튼 끝-->
		
		<h3 class="mt30"><span>${op:message('M00136')}</span> 
			<div class="right">
				(※ ${op:message('M00133')})
			</div>
		</h3>
		<form:form modelAttribute="config" method="post">
		
			<div class="board_result" style="margin:0 0 30px 0;">
				<div class="title">
					<div>${op:message('M00134')} </div>
				</div>
				<div class="cont">
					<form:textarea path="tagOverture" cols="30" rows="6" title="${op:message('M00134')}" cssClass="w90" />
				</div>
	
			</div>
			
			<div class="board_result" style="margin:30px 0 0 0;">
				<div class="title">
					<div>${op:message('M00135')} </div>
				</div>
				<div class="cont">
					<form:textarea path="tagAdwords" cols="30" rows="6" title="${op:message('M00135')}" cssClass="w90" />
				</div>
	
			</div>
				 
			
			<!-- 버튼시작 -->		 
			<div class="btn_center">
				<button type="submit" class="btn btn-active"><span>${op:message('M00101')}</span></button>				 
			</div>			 
			<!-- 버튼 끝-->
			
		</form:form>
		
<script type="text/javascript">
$(function() {
	// validator
	$('#config').validator(function() {});
});

</script>		