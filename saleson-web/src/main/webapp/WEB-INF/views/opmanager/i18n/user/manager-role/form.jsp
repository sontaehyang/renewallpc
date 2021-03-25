<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="daum"	tagdir="/WEB-INF/tags/daum" %>
<style>
#blog-landing {
margin-top: 10px;
position: relative;
max-width: 100%;
width: 100%;
}
#blog-landing > div {
	position: absolute;
}
.section {
	border: 1px solid #ccc;

}
.section li {
	padding-bottom: 15px;
}
.menu1 {
	font-weight: bold;
	font-size: 14px;
	padding: 10px;
	background: #f4f4f4;
	color: #000;
}
.menu2 {
	display: block;
	font-weight: bold;
	font-size: 13px;
	padding: 10px 10px 5px 10px;
	
	color: #000;
	border-top: 1px solid #eee;

}
.menu3 {
	display: block;
	font-size: 12px;
	padding: 2px 10px 2px 15px;
	color: #555;
}
</style>
			<h3><span>운영자 역할 관리</span></h3>
			
			<form:form modelAttribute="role" method="post" enctype="multipart/form-data">
				<input type="hidden" id="authority" />
				
				<div class="board_write">
					<table class="board_write_table" >
						<colgroup>
							<col style="width:15%;" />
							<col style="width:85%;" />
						</colgroup>
						<tbody>
					
			 				<tr>
			 					<td class="label">운영자 역할명 <span class="require">*</span></td>
			 					<td>
			 						<div class="input_wrap col-w-7">
			 							<form:input path="roleName" class="form-half required" title="운영자 역할명" />
			 						</div>
			 					</td>
			 				</tr>
			 				<tr>
			 					<td class="label">운영자 역할 설명</td>
			 					<td>
			 						<div class="input_wrap col-w-7">
			 							<form:textarea path="roleDesc" class="form-block" style="height: 60px" />
			 						</div>
			 					</td>
			 				</tr>
			 				
			 				<tr>
			 					<td class="label" valign="top" style="padding-top: 15px; font-size: 13px">메뉴권한</td>
			 					<td>
			 						<div style="padding: 15px 10 15px 15px">
				 						<p><label><input type="checkbox" class="check-all" /> 전체선택</label></p>
				 							
				 						<div id="blog-landing" class="input_wrap col-w-7">
				 							
				 							<c:forEach items="${menuList}" var="menu1">
				 								<div class="section">
				 									<div  class="menu1">
				 										<label><input type="checkbox" class="check-all" /> ${menu1.menuName}</label>
				 									</div>	
				 									<ul>
					 									<c:forEach items="${menu1.childMenu}" var="menu2">
					 										<li>
					 											<span class="menu2">
					 												<label><input type="checkbox" class="check-all" /> ${menu2.menuName}</label>
					 											</span>
					 											
						 										<c:forEach items="${menu2.childMenu}" var="menu3">
						 											<c:set var="hasMenuRight" value="N" />
						 											
						 											<c:forEach items="${menuRightList}" var="menuRight">
						 												<c:if test='${hasMenuRight == "N"}'>
						 													<c:if test="${menu3.menuId == menuRight.menuId}">
						 														
						 														<c:set var="hasMenuRight" value="Y" />
						 													
						 													</c:if>
						 												</c:if>
						 											</c:forEach>
						 											
						 											<span class="menu3">
						 												<label><input type="checkbox" name="menuIds" value="${menu3.menuId}" ${hasMenuRight == 'Y' ? 'checked="checked"' : ''} /> ${menu3.menuName}</label>
						 											</span>
						 										</c:forEach>
					 										</li>
					 									</c:forEach>
				 									</ul>
				 								</div>
				 							
				 							</c:forEach>
				 					
				 						</div>
				 					</div>
			 					</td>
			 				</tr>
			 				
			 				
			 				
		 				
						</tbody>
					</table>						 
				</div> <!--// board_write E-->
				<div class="btn_center_end">
					<button type="submit" class="btn btn-active">${op:message('M00101')} <!-- 저장 --></button>	
				</div>
			</form:form>
			
			
<script type="text/javascript" src="/content/modules/pinterest_grid.js"></script>

<script type="text/javascript">
var loginCheckId = '';
$(function(){
	$('#role').validator();
	
	$('#blog-landing').pinterest_grid({
		no_columns: 4,
		padding_x: 10,
		padding_y: 10,
		margin_bottom: 50,
		single_column_breakpoint: 700
	});
	
	$('.check-all').on('click', function(e) {
		var isChecked = $(this).prop('checked');
		var $target = $(this).parent().parent().parent().find('input[type=checkbox]');
		$target.prop('checked', isChecked);
	});
});
</script>			
			
