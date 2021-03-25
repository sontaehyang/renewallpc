<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<!-- [레이어] 환불 신청 정보 -->

<div id="layer_return" class="order_return_layer">
	<div id="layer2">
		<h1 class="popup_title">반품신청</h1>
		<div class="pop-conts">
			<div class="return_write">
	 			<table cellpadding="0" cellspacing="0" class="esthe-table write">
		 			<caption>table list</caption>
		 			<colgroup>
		 				<col style="width:110px;">
		 				<col style="width:auto;">	 				
		 			</colgroup>
		 			<tbody> 
		 				<tr>
		 					<th>은행명</th>
		 					<td>
		 						<div class="input_wrap col-w-1">
		 							<input type="text" title="은행명" name="returnBankName" />
		 						</div>  
		 					</td>
		 				</tr>  
		 				<tr>
		 					<th>계좌번호</th>
		 					<td>
		 						<div class="input_wrap col-w-1">
		 							<input type="text" title="계좌번" name="returnVirtualNo" />
		 						</div>  
		 					</td>
		 				</tr> 
		 				<tr>
		 					<th class="bL0">예금주</th>
		 					<td class="bL0">
		 						<div class="input_wrap col-w-1">
		 							<input type="text" title="예금주" name="returnBankInName" />
		 						</div>
		 					</td>
		 				</tr> 
		 			</tbody>
		 		</table><!--//view E-->	 
			
			</div><!--//popup_review E-->
		</div><!--//popup_contents E-->
		<div class="btn_area wrap_btn">
			<div class="sale division-2" style="display:block"> 
				<div>
					<div>
						<a href="javascript:orderReturnBankInfoProcess();" class="btn btn_on btn-w100">신청</a>
					</div>
				</div>
				<div>
					<div>
						<a href="javascript:Shop.closeOrderReturnLayer();" class="btn btn_out btn-w100">취소</a>
					</div>
				</div> 
			</div>				 
		</div>	
		<div class="btn-r"> <a href="javascript:Shop.closeOrderReturnLayer()" class="cbtn"><span class="hide">팝업닫기</span></a> </div>
	</div> 
</div>