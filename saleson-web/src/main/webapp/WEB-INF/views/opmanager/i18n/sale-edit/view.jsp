<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<style>
span.require {color: #e84700; margin-left: 5px;}
</style>
<div class="popup_wrap">
	<h1 class="popup_title">가격변경내역</h1>
		<div class="popup_contents">
			<div class="board_write">						
				<table class="board_write_table" summary="상품기본정보">
					<caption>상품기본정보</caption>
					<colgroup>
						<col style="width: 180px;" />
						<col style="" />
						<col style="width: 180px;" />
						<col style="width: 250px;" />
					</colgroup>
					<tbody>
						<tr>
							<td class="label">가격변경내역</td>
							<td colspan="3">
								<div>
									${op:nl2br(itemSaleEdit.message)}
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>	
			
			<%-- <div id="buttons" class="tex_c mt20">
				<a href="javascript:Link.list('${requestContext.managerUri}/item/sale-edit/list?status=${itemSaleEdit.status}')" class="btn btn-default">${op:message('M00480')}</a>	<!-- 목록 -->
			</div>	 --%>	
		</div>		
	<a href="#" class="popup_close">창 닫기</a>	
</div>	

