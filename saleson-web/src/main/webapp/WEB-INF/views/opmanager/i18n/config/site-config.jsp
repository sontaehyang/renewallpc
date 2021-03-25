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

		<h3><span>${op:message('M00102')}</span></h3>
			
			<div class="board_write">
				<form:form modelAttribute="config" method="post">
				<table class="board_write_table" summary="사이트 기본설정">
					<caption>사이트 기본설정/caption>
					<colgroup>
						<col style="width:210px;" />
						<col style="*" />
					</colgroup>
					<tbody>
					 	<tr>
						 	<td class="label">${op:message('M00091')} (${op:message('M00092')} )</td>
						 	<td>
						 		<div>
						 			<form:input path="shopName" cssClass="full required" title="${op:message('M00091')} (${op:message('M00092')} )" maxlength="50" />
							 	</div>
						 	</td>	
						 </tr>
						 <tr>
						 	<td class="label">${op:message('M00090')} </td>
						 	<td>
						 		<div>
						 			<form:input path="seoTitle" cssClass="full required" title="${op:message('M00090')} " maxlength="255" />
								</div>
						 	</td>	
						 </tr>
						
						 <tr>
						 	<td class="label">${op:message('M00093')} </td>
						 	<td>
						 		<div>
						 			<form:input path="seoKeywords" cssClass="full optional" title="${op:message('M00093')} " maxlength="255" />
							 	</div>
						 	</td>	
						 </tr>	
						 <tr>
						 	<td class="label">${op:message('M00094')} </td>
						 	<td>
						 		<div>
						 			<form:textarea path="seoDescription" rows="3"  cssClass="full optional" title="${op:message('M00094')} " maxlength="255" />
							 	</div>
						 	</td>	
						 </tr>
						 <tr>
						 	<td class="label">${op:message('M00999')}</td> <!-- H1태그 -->
						 	<td>
						 		<div>
						 			<form:input path="seoHeaderContents1" cssClass="full optional" title="H1" maxlength="255" />
							 	</div>
						 	</td>	
						 </tr>
						 <tr>
						 	<td class="label">${op:message('M01000')}</td> <!-- 테마워드용 타이틀 -->
						 	<td>
						 		<div>
						 			<form:input path="seoThemawordTitle" cssClass="full optional" title="테마워드용 타이틀" maxlength="255" />
							 	</div>
						 	</td>	
						 </tr>	
						 <tr>
						 	<td class="label">${op:message('M01001')}</td> <!-- 테마워드용 기술서 -->
						 	<td>
						 		<div>
						 			<form:textarea path="seoThemawordDescription" rows="3"  cssClass="full optional" title="테마 워드용 기술서" maxlength="255" />
							 	</div>
						 	</td>	
						 </tr>
						  <tr>
						 	<td class="label">${op:message('M01074')}</td> <!-- 로그인 영역 TEXT -->
						 	<td>
						 		<div>
						 			<form:input path="loginText"  cssClass="full optional" title="테마 워드용 기술서" maxlength="255" />
							 	</div>
						 	</td>	
						 </tr>
						 <tr>
						 	<td class="label">${op:message('M00095')} </td>
						 	<td>
						 		<div>
						 			<form:radiobutton path="sourceFlag" value="Y" label="${op:message('M00096')} " title="${op:message('M00095')} " cssClass="optional"/>
						 			<form:radiobutton path="sourceFlag" value="N" label="${op:message('M00097')} "/>
									<span>(${op:message('M00098')} )</span>
								</div>
						 	</td>
						 </tr>
						 
					</tbody>					 
				</table>			
				
				<!-- 버튼시작 -->		 
				<div class="tex_c mt20">
					<button type="submit" class="btn btn-active"><span>${op:message('M00101')} </span></button>				 
				</div>			 
				<!-- 버튼 끝-->
				</form:form>
									 							
			</div> <!-- // board_write -->
			
<script type="text/javascript">
$(function() {
	// validator
	$('#config').validator(function() {});
});

</script>				
			