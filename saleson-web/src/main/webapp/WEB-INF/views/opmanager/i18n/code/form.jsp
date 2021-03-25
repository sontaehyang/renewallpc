<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
	

<div class="popup_wrap">
    <h1 class="popup_title">${op:message('MENU_1401')}</h1> <!-- 공통코드 관리 -->
    <div class="popup_contents">
 
		<form:form modelAttribute="code" method="post" enctype="multipart/form-data">
			<form:hidden path="language" value="ko" />

			<div class="board_write">

				<table class="board_write_table">
					<caption>${op:message('MENU_1401')}</caption> <!-- 공통코드 관리 -->
					<colgroup>
						<col style="width:150px;">
						<col style="width:auto;">
					</colgroup>
					<tbody>
						<tr>
							<td class="label">${op:message('M01680')} <font color=red>*</font></td> <!-- 코드구분 -->
							<td>
								<div>
									<form:input type="text" path="codeType" title="${op:message('M01680')}" class="nine" readonly="true" maxlength="30"/>
								</div>
							</td>
						</tr>
						<tr>
							<td class="label">${op:message('M01374')} <font color=red>*</font></td> <!-- 코드 -->
							<td>
								<div>
									<form:input type="text" path="id" title="${op:message('M01374')}" class="nine" maxlength="25"/>
								</div>
							</td>
						</tr>
						<tr>
							<td class="label">${op:message('M01674')}</td> <!-- 코드라벨 -->
							<td>
								<div>
									<form:input type="text" path="label" title="${op:message('M01674')}" class="nine" maxlength="200"/>
								</div>
							</td>
						</tr>
						<tr>
							<td class="label">${op:message('M01677')}</td> <!-- 코드상세 -->
							<td>
								<div>
									<form:input type="text" path="detail" title="${op:message('M01677')}" class="nine" maxlength="200"/>
								</div>
							</td>
						</tr>
						<tr>
							<td class="label">${op:message('M01678')}</td> <!-- 정렬순서 -->
							<td>
								<div>
									<form:input type="text"  value="${op:negativeNumberToEmpty(code.ordering)}" path="ordering" name="ordering" id="ordering" title="${op:message('M01678')}" class="nine" maxlength="10"/>
								</div>
							</td>
						</tr>
						<tr>
							<td class="label">${op:message('M00669')}</td> <!-- 사용유무  -->
							<td>
								<div>
									<form:radiobutton path="useYn" value="Y" label="Y" />
									<form:radiobutton path="useYn" value="N" label="N" />
								</div>
							</td>
						</tr>

						<tr>
							<td class="label">${op:message('M01682')}</td> <!-- 상위ID  -->
							<td>
								<div>
									<form:input type="text" path="upId" title="${op:message('M01682')}" class="nine" maxlength="25"/>
								</div>
							</td>
						</tr>
						<tr>
							<td class="label">${op:message('M01675')}</td> <!-- 코드값  -->
							<td>
								<div>
									<form:input type="text" path="codeValue" title="${op:message('M01675')}" class="nine" maxlength="200"/>
								</div>
							</td>
						</tr>
						<tr>
							<td class="label">${op:message('M01684')}</td> <!-- 확장코드  -->
							<td>
								<div>
									<form:input type="text" path="extentionCode" title="${op:message('M01684')}" class="nine" maxlength="200"/>
								</div>
							</td>
						</tr>
						<tr>
							<td class="label">${op:message('M01685')}</td> <!-- 매핑코드  -->
							<td>
								<div>
									<form:input type="text" path="mappingCode" title="${op:message('M01685')}" class="nine" maxlength="200"/>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
       		 </div> <!--// board_write E-->

			<!-- 버튼시작 -->
			<div class="btn_center">
				<button type="submit" class="btn btn-active"><span>${op:message('M00101')}</span></button> <!-- 저장 -->
				<a href="JavaScript:self.close();" class="btn btn-dark-gray"><span>${op:message('M00569')}</span></a> <!-- 닫기 -->
			</div>
			<!-- 버튼 끝-->
		</form:form>
    </div>
</div>		
		
	
<script type="text/javascript">

$(function() { 
	
	// validator
	try{
		$('#code').validator(function() {
			var codeType = $("#codeType").val();
			var id = $("#id").val();

			if (codeType.length==0) {
				alert("코드구분은 필수 입력항목입니다.");
				$("#codeType").focus();
				return false;
			} 
			if (id.length==0) {
				alert("코드값은 필수 입력항목입니다.");
				$("#id").focus();
				return false;
			} 
		});
	} catch(e) {
		alert(e.message);
	}
	
});

</script>