<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<%@ taglib prefix="daum"	tagdir="/WEB-INF/tags/daum" %>

      		
      		<p class="form-group">
	      		* 주소를 수정 하시면 해당 주소로 등록되어 있는 상품에 즉시 적용됩니다. <br />
	      		* 주소를 변경하시려면 주소를 추가하여 사용하시기 바랍니다.<br />
	      		* 주소를 추가하시려면 주소록에 내용을 기입하시고 추가버튼을 클릭하시면 됩니다.<br />
	      		* 출고지 별로 조건부 무료정책을 적용하시려면 해당 출고지 주소에 배송정책을 등록하셔야 됩니다.<br />
	      		* 출고지 배송정책을 변경하면 해당 출고지 배송비로 설정되어있는 상품에 즉시 적용됩니다.
	      	</p>
	      	
	      	<div id="sm-display-area1">
	      		<table class="inner-table th-center">
	      			<colgroup>
	      				<col style="" />
	      				<col style="" />
	      				<col style="" />
	      			</colgroup>
					<tr>
						<th>선택</th>    		
						<th>주소명</th>    		
						<th>이름</th>    		
						<th>주소</th>    		
						<th>연락처</th>    		
						<th>배송비</th>    		
						<th>도서산간<br />추가배송비</th>    		
						<th>통합배송비</th>    		
					</tr>
					<c:forEach items="${list}" var="shipment">
	      				<tr class="listCount">
	      					<td class="text-center">
	      						<input type="radio" name="shipmentId" value="${shipment.shipmentId}" />
	      						<span class="defaultAddressFlag hide">${shipment.defaultAddressFlag}</span>
	      						<span class="sellerId hide">${shipment.sellerId}</span>		
	      					</td>
	      					<td class="text-center">
	      						<span class="addressName">${shipment.addressName}</span>
	      						${shipment.defaultAddressFlag == 'Y' ? ' <span class="text-info">(기본)</span>' : ''}	
	      					</td>
	      					<td class="text-center"><span class="name">${shipment.name}</span></td>
	      					<td>
	      						<%-- <span class="zipcode" style="display:none">${shipment.zipcode}</span>
	      						<span class="zipcode1" style="display:none">${shipment.zipcode}</span>
	      						<span class="zipcode2" style="display:none">${shipment.zipcode}</span> --%>
	      						<span class="zipcode">(${shipment.zipcode})</span>
	      						<span class="address">${shipment.address}</span>
	      						<span class="addressDetail">${shipment.addressDetail}</span>
	      					</td>
	      					<td class="text-center">
	      						<span class="telephoneNumber">${shipment.telephoneNumber}</span>
	      					</td>
	      					<td class="text-center">
	      						<span class="shipping hide">${shipment.shipping}</span>
	      						<span class="shippingFreeAmount hide">${shipment.shippingFreeAmount}</span>
	      						
	      						${op:numberFormat(shipment.shipping)}원<br />
	      						(${op:numberFormat(shipment.shippingFreeAmount)} 이상 무료)	
	      					</td>
	      					<td class="text-center">
	      						<span class="shippingExtraCharge1 hide">${shipment.shippingExtraCharge1}</span>
	      						<span class="shippingExtraCharge2 hide">${shipment.shippingExtraCharge2}</span>
	      						
	      						<span class="display-inline-block">제주</span> : ${op:numberFormat(shipment.shippingExtraCharge1)}원<br />
	      						<span class="display-inline-block">도서</span> : ${op:numberFormat(shipment.shippingExtraCharge2)}원
	      					</td>
	      					<td class="text-center">
	      						<span class="shipmentGroupCode hide">${shipment.shipmentGroupCode}</span>
	      						<c:choose>
		      						<c:when test='${empty shipment.shipmentGroupCode}'>
		      							미설정
		      						</c:when>
		      						<c:otherwise>
		      							<c:forEach begin="1" end="10" var="i">
		      								<c:set var="sgc" value="${shipmentGroupCodeValue}_${i}" />
		      								<c:if test="${shipment.shipmentGroupCode == sgc}">
		      									통합배송정책${i}
		      								</c:if>
		      							</c:forEach>
		      						</c:otherwise>
		      					</c:choose>
	      					</td>
	      					
		      		</c:forEach>
	      		</table>
	      		<c:if test="${empty list}">
	      			<div class="no_content" style="padding: 30px;">
	      				주소를 등록해 주세요.
	      			</div>
	      		</c:if>
	      		
	      		<div class="buttons">
	      			<div class="float text-left">
	      			<c:if test="${isPopup}">
	      				<button type="button" class="btn btn-warning btn-sm select-shipment">적용</button>
	      			</c:if>
	      			</div>
	      			
	      			<c:if test="${hasModifyPermission}">
		      			<div class="float text-right">
		      				<button type="button" class="btn btn-active btn-sm btn-create" id="btnCreate">출고지${op:message('M00088')}</button> <!-- 등록 -->
							<button type="button" class="btn btn-default btn-sm btn-edit hide" id="btnUpdate">${op:message('M00087')}</button> <!-- 수정 -->
							<a href="javascript:shipmentDelete();" class="btn btn-default btn-sm btn-delete hide">${op:message('M00074')}</a> <!-- 삭제 -->
		      			</div>
	      			</c:if>
	      		</div>
	      	</div>
      		
      	<c:if test="${hasModifyPermission}">
      		<div id="sm-display-area2" style="display: none">
	      		<form:form modelAttribute="shipment" method="post" enctype="multipart/form-data">
	      			<input type="hidden" name="shipmentId" id="shipmentId" value="0" />
	      			<input type="hidden" name="telephoneNumber" id="telephoneNumber" />
	      			<input type="hidden" name="sellerId" value="${shipment.sellerId}"/>
	      			<input type="hidden" id="existingAddressFlag" value="0"/>
	      			
		      		<table class="inner-table">
		      			<col style="width: 150px;" />
		      			<col style="" />
		      			
		      			<tr>
		      				<th>주소명</th>
		      				<td>
		      					<form:input path="addressName" class="required" title="주소명" />
		      					<label><input type="checkbox" name="defaultAddressFlag" value="Y" /> 기본주소로 설정함 (기본주소는 상품등록시 바로 확인됩니다.)</label>	
		      					<input type="hidden" name="!defaultAddressFlag" value="N" />
		      				</td>
		      			</tr>
		      			<tr>
		      				<th>이름</th>
		      				<td>
		      					<form:input path="name" class="required" title="이름" />
		      				</td>
		      			</tr>
		      			<tr>
		      				<th>연락처</th>
		      				<td>
		      					<input type="text" id="telephoneNumber1" name="telephoneNumber1" maxlength="4" class="form-sm text-center required _number" title="연락처 국번" /> -
		      					<input type="text" id="telephoneNumber2" name="telephoneNumber2" maxlength="4" class="form-sm text-center required _number" title="연락처 가운데 번호" /> -
		      					<input type="text" id="telephoneNumber3" name="telephoneNumber3" maxlength="4" class="form-sm text-center required _number" title="연락처 마지막 번호" />
		      				</td>
		      			</tr>
		      			<tr>
		      				<th>주소</th>
		      				<td class="lh-2">
		      					<p>
		      						<input type="text" id="zipcode" name="zipcode" maxlength="7" class="form-sm text-center required" readonly="readonly" style="width: 70px;" />
			      					<a href="#" class="btn btn-dark-gray btn-sm" id="find-address">주소찾기</a>
		      					</p>
		      					<div class="line-group">
		      						<form:input path="address" class="form-block required" title="주소" readonly="true"/>
		      						<!-- <input type="text" name="address" class="form-block required" title="주소" readonly="readonly" /> -->
		      					</div>
		      					<div class="line-group">
		      						<form:input path="addressDetail" class="form-block required" title="상세주소"/>
		      						<!-- <input type="text" name="addressDetail" class="form-block required" title="상세주소" /> -->
		      					</div>
		      				</td>
		      			</tr>
		      			<tr>
		      				<th>배송비</th>
		      				<td>
		      					<form:input path="shipping" maxlength="8" class="amount required _number" title="배송비" /> 원 | 
		      					<form:input path="shippingFreeAmount" maxlength="8" class="amount required _number" title="배송비 무료 금액" /> 원 이상 구매 시 무료배송. 
		      				</td>
		      			</tr>
		      			<tr>
		      				<th>도서산간 추가 배송비</th>
		      				<td>
		      					<span class="display-inline-block" style="width:50px;">제주도</span> : 
		      					<form:input path="shippingExtraCharge1" maxlength="8" class="amount required _number" title="제주도 추가 배송비" /> 원 <br />
		      					
		      					<div class="line-group">
		      						<span class="display-inline-block" style="width:50px;">도서산간</span> : 
		      						<form:input path="shippingExtraCharge2" maxlength="8" class="amount required _number" title="도서산간 추가 배송비" /> 원
		      					</div>
		      				</td>
		      			</tr>
		      			<tr>
		      				<th>통합배송비 부과 설정</th>
		      				<td>
		      					<p class="text-info text-sm">
		      						* 주문 시 같은 통합배송비 부과 설정 값을 가지는 상품금액의 합이 상품 각각의 배송비 부과 기준 금액이 됩니다.
		      					</p>
		      					
		      					<form:select path="shipmentGroupCode">
		      						<form:option value="">미설정</form:option>
		      						
		      						<c:forEach begin="1" end="10" var="i">
		      							<form:option value="${shipmentGroupCodeValue}_${i}"> 통합배송정책${i}</form:option>
		      						</c:forEach>
		      					
		      					</form:select>
		      					
		      				</td>
		      			</tr>
		      		</table>
		      		
					<p class="popup_btns">
						<button type="submit" class="btn btn-active btn-create btn-create1">${op:message('M00088')}</button> <!-- 등록 -->
						<button type="submit" class="btn btn-active btn-edit btn-edit1 hide">${op:message('M00087')}</button> <!-- 수정 -->
						<a href="javascript:shipmentDelete();" class="btn btn-default btn-delete hide" id="btnDelete">${op:message('M00074')}</a> <!-- 삭제 -->
						
						<c:if test="${isPopup}">
							<button type="button" class="btn btn-default btn-cancel">${op:message('M00037')}</button> <!-- 취소 -->
						</c:if>
					</p>
				</form:form>
			</div>
		</c:if>

<!-- 다음 주소검색 -->
<daum:address />

<script>
var message = '저장하시겠습니까?';
$(function() {
	shipmentEvent();
	
	initValidator();
	
	// 우편번호 검색
	findAddress();
	
	$('input[name=shipmentId]:radio[value='+opener.shipmentId.value+']').click();
	
	$('#btnCreate').click(function() {
		$('#btnDelete').hide();
	});
	
	$('#btnUpdate').click(function() {
		
	});
});


function initValidator() {
	$('#shipment').validator(function() {
		var telephoneNumber = $('input[name=telephoneNumber1]').val() + '-' + $('input[name=telephoneNumber2]').val() + '-' + $('input[name=telephoneNumber3]').val();

		$('#telephoneNumber').val(telephoneNumber);
		
		if ($('#existingAddressFlag').val() == 'Y' && $('input:checkbox[name=defaultAddressFlag]').is(':checked') == false) {
			alert("기본 배송정보는 최소 한 개이상 등록해야합니다.")
			return false;
		}
		if (!confirm(message)) {
			return false;
		}
	});
}

function shipmentEvent() {
	$('input[type=radio]').prop('checked', false);
	var shipment = {};
	
	// 출고지 주소 checkbox 클릭 시 
	$('input[name=shipmentId]').on('click', function() {
		$('.btn-edit').removeClass('hide');
		$('.btn-delete').removeClass('hide');
		
		var $shipment = $(this).closest('tr');
		var data = ['shipmentId', 'addressName', 'name', 'telephoneNumber', 'zipcode', 'address', 'addressDetail', 'defaultAddressFlag', 'shipping', 'shippingFreeAmount', 'shippingExtraCharge1', 'shippingExtraCharge2', 'sellerId', 'shipmentGroupCode'];
		
		shipment = {};

		
		for (var i = 0; i < data.length; i++) {
			var value = $shipment.find('span.' + data[i]).text();
			if(data[i] == 'zipcode') {
				var temp = '';
				temp = value.replace('(','');
				value = temp.replace(')','');
			}
			shipment[data[i]] = value;
			
		}
		
		shipment.shipmentId = $(this).val();
		/* shipment.zipcode1 = shipment.zipcode.split('-')[0];
		shipment.zipcode2 = shipment.zipcode.split('-')[1]; */
		
		shipment.telephoneNumber1 = shipment.telephoneNumber.split('-')[0];
		shipment.telephoneNumber2 = shipment.telephoneNumber.split('-')[1];
		shipment.telephoneNumber3 = shipment.telephoneNumber.split('-')[2];
		
		// 기본주소.
	    if (shipment.defaultAddressFlag == 'Y') {
	    	$('#shipment input[name=defaultAddressFlag]').prop('checked', true);
	    } else {
	    	$('#shipment input[name=defaultAddressFlag]').prop('checked', false);
	    }
		
		for(var key in shipment) {
	        if (shipment.hasOwnProperty(key)) {
	        	if (key == 'defaultAddressFlag') {
	        		$('#shipment #existingAddressFlag').val(shipment[key]);
	        	} else {
	        		$('#shipment #' + key +'').val(shipment[key]);	        		
	        	}
	        }
	    }
	});
	
	// 선택 버튼 클릭 이벤트.
	$('.select-shipment').on('click', function() {
		var $shipmentId =  $('input[name=shipmentId]');
		
		if (!$shipmentId.is(':checked')) {
			alert('출고지 주소를 선택해 주세요.');
			$shipmentId.eq(0).focus();
			return false;
		}
		
		opener.handleShipmentPopupCallback(shipment);
		self.close();
	});
	
	// 등록 버튼.
	$('.btn-create').on('click', function() {
	
		if ($('#sm-display-area2').css('display') == 'none') {
			$('#sm-display-area1').hide();
			$('#sm-display-area2').show();
			$('.btn-edit1').hide();
			$('#shipment input[type=text]').val('');
			$('#shipment input[type=checkbox]').prop('checked',false);
			$('#shipmentGroupCode').val('');
			return;
		}
		message = "저장하시겠습니까?";
		$('#shipment input[name=shipmentId]').val('0');
		$('#shipment').attr('action', '${requestContext.managerUri}/shipment/create');
		
		if($("#shippingExtraCharge1").val() == ''){
			$("#shippingExtraCharge1").val(0);
		}
		if($("#shippingExtraCharge2").val() == ''){
			$("#shippingExtraCharge2").val(0);
		}
	});
	
	// 수정 버튼.
	$('.btn-edit').on('click', function() {
		if ($('#sm-display-area2').css('display') == 'none') {
			$('#sm-display-area1').hide();
			$('#sm-display-area2').show();
			$('.btn-create1').hide();
			return;
		}
		message = "출고지 정보를 변경하면 해당 출고지(배송비)로 설정되어있는 상품에 즉시 적용됩니다.";
		$('#shipment input[name=shipmentId]').val(shipment.shipmentId);
		$('#shipment').attr('action', '${requestContext.managerUri}/shipment/edit');
	});
	
	
	// 취소버튼 
	$('.btn-cancel').on('click', function() {
		$('#sm-display-area2').hide();
		$('#sm-display-area1').show();
		$('.btn-create1').show();
		$('.btn-edit1').show();
	});
	
}

function shipmentDelete() {
	if ($('#existingAddressFlag').val() == 'Y') {
		alert('기본정보로 설정한 배송정보는 지울수 없습니다.');
		return;
	}
	if ($('.listCount').length == 1) {
		alert('배송정보는 최소 한 개 이상 등록해야합니다.');
		return;
	}

	if (confirm("배송정보를 삭제하시겠습니까?")) {
		$('#shipment').attr('action', '${requestContext.managerUri}/shipment/delete');
		$('#shipment').submit();
	}
}

function closePopup() {
	self.close();
}

//우편번호 검색
function findAddress() {
	$('#find-address').on('click', function() {
		var tagNames = {
				'newZipcode'			: 'zipcode',
				'zipcode' 				: '',
			}
		openDaumAddress(tagNames);
	});
}

/* 
function findAddress() {
	new daum.Postcode({
	    oncomplete: function(data) {	        	
	    	try {
	    		
	    		var zipcode = data.postcode;
	    		if (zipcode == '') {
	    			zipcode = data.zonecode;
	    		}
	    		
	    		var jibunAddress = data.jibunAddress;
		        if (jibunAddress == '') {
		        	jibunAddress = data.autoJibunAddress;
		        }
		        
		        var roadAddress = data.roadAddress;
		        if (roadAddress == '') {
		        	roadAddress = data.autoRoadAddress;
		        }
	    		
	    		address = jibunAddress;
	    		if (data.userSelectedType == 'R') {
	    			zipcode = data.zonecode;
	    			address = roadAddress;
	    			
	    		}
	    		
	    		$('input[name=zipcode]').val(zipcode);
	    		$('input[name=address]').val(address);
	    		$('input[name=addressDetail]').val('').focus();
		        
	    	} catch (e) {
				alert(e.message);
			}
	    	
	    }
	}).open();
}
 */

</script>