<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>

<div class="popup_wrap">
	<h1 class="popup_title">${ title } ${op:message('M00605')} <!-- 발송 내역 --></h1>
	<div class="popup_contents">
		<h3><span>${ title } ${op:message('M00605')} <!-- 발송 내역 --></span></h3>			
		<div class="board_write">
			<table class="board_write_table">
				<caption>${ title } ${op:message('M00605')} <!-- 발송 내역 --></caption>
				<colgroup>
					<col style="width:10%;" />
					<col style="width:90%;" />
				</colgroup>
				<tbody>							 
					 <tr>
					 	<td class="label">${op:message('M00275')} <!-- 제목 --></td>
					 	<td>
					 		<div>
								${ sendMailLog.subject }
							</div>
					 	</td>	
					 </tr>
					 <tr>
					 	<td class="label">${op:message('M00006')} <!-- 내용 --></td>
					 	<td>
					 		<div style="overflow:auto">
								${ sendMailLog.content }
							</div>
					 	</td>	
					 </tr>
					 
					 <c:if test="${ !sendMailLog.sendLoginId eq '' }">
						 <tr>
						 	<td class="label">${op:message('M00606')} <!-- 발송한 관리자 ID --></td>
						 	<td>
						 		<div>
						 			${ sendMailLog.sendLoginId }
						 		</div>
						 	</td>
						 </tr>						 
					 </c:if>
				</tbody>					 
			</table>								 							
		</div> <!-- // board_write -->
		
		<div class="tex_c mt20">
			<a href="javascript:self.close();" class="btn btn-default">${op:message('M00569')} <!-- 닫기 --></a>	
		</div>
		 		
	</div><!--//popup_contents E-->
	<a href="javascript:self.close();" class="popup_close">${op:message('M00353')} <!-- 창 닫기 --></a>
</div>