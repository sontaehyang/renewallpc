<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>

<style>
.delivery{ 
	background:url('/content/images/product/product_bg01.gif') no-repeat 39px 29px;
}
</style>


		<div class="location">
			<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
		</div>
		
<!--팝업 리스트 시작-->
		<form:form modelAttribute="config" method="POST" enctype="multipart/form-data">
		<h3><span>${op:message('M00660')}</span></h3> <!-- 배송/반품/환불/교환안내 설정 --> 
		<div class="board_write">
			<form:textarea path="deliveryInfo" cols="30" rows="40" style="width: 1085px"  title="${op:message('M00661')}" />					 							
		</div>  <!--//board_write E-->
		<p class="red01 mt10">* ${op:message('M00662')}</p> <!-- 상품 구매시 배송/반품/환불 안내 화면에 입력한 내용이 기본으로 출력됩니다. -->
		<div class="btn_center">
			<button type="submit" class="btn btn-active"><span>${op:message('M00101')}</span></button> <!-- 저장 -->
		</div>
		</form:form>
						
<module:smarteditorInit />
<module:smarteditor id="deliveryInfo" />	
						
<script type="text/javascript">
$(function() { 
	
	// validator
	try{
		$('#config').validator(function() {
			
			Common.getEditorContent("deliveryInfo");
		});
	} catch(e) {
		alert(e.message);
	}
});
</script>	