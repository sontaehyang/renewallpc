<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>


<h3><span>Call 회원 검색</span></h3>
<form:form modelAttribute="param" method="post">
	
	<div class="board_write">
	
		<table class="board_write_table">
			<colgroup>
				<col style="width:150px;" />
				<col style="width:*;" />
			</colgroup>
			<tbody>	
				 <tr>
				 	<td class="label">거래처 코드</td>
				 	<td>
				 		<div>
				 			<form:input path="customerCode" class="input_txt full" />
				 		</div>
				 	</td>
				 </tr>
				 <tr>
				 	<td class="label">이름</td>
				 	<td>
				 		<div>
				 			<form:input path="userName" class="input_txt full" />
				 		</div>
				 	</td>
				 </tr>
				 <tr>
				 	<td class="label">전화번호</td>
				 	<td>
				 		<div>
				 			<form:input path="telNumber" class="input_txt full" />
				 		</div>
				 	</td>
				 </tr>
				 <tr>
				 	<td class="label">이메일</td>
				 	<td>
				 		<div>
				 			<form:input path="email" class="input_txt full" />
				 		</div>
				 	</td>
				 </tr>
				 <tr>
				 	<td class="label">사업자번호</td>
				 	<td>
				 		<div>
				 			<form:input path="businessNumber" class="input_txt full" />
				 		</div>
				 	</td>
				 </tr>
			</tbody>					   
		</table>
									 							
	</div> <!-- // board_write -->  
	
	<!-- 버튼시작 -->	
	<div class="btn_all">
		<div class="btn_right">
			<button type="submit" class="btn btn-dark-gray btn-sm"><span>${op:message('M00048')}<!-- 검색 --></span></button>
		</div>
	</div>	
	
</form:form>

<div class="board_list">
	<table class="board_list_table board_list">
		<colgroup>
			<col style="width:10%;" />
			<col style="width:10%;" />
			<col style="width:10%;" />
			<col style="width:10%;" />
			<col style="width:10%;" />
			<col />
			<col style="width:10%;" />
		</colgroup>
		<thead>
		
			<tr>
				<th scope="col">이름</th>							
				<th scope="col">회원구분</th>
				<th scope="col">거래처구분</th>
				<th scope="col">전화번호</th>
				<th scope="col">휴대전화번호</th>
				<th scope="col">주소</th>
				<th scope="col">주문하기</th>
			</tr>
	
		</thead>
		<tbody>
		
			<c:forEach items="${ list }" var="user">
				<tr>
					<td>${ user.customerName }</td>
					<td>
						<c:choose>
							<c:when test="${user.userId > 0}">
								WEB 회원<br/>
								[${user.email}]
							</c:when>
							<c:otherwise>
								일반 거래처<br/>[${user.customerCode}]
							</c:otherwise>
						</c:choose>
					</td>
					<td>${user.customerType}</td>
					<td>${user.telNumber}</td>
					<td>${user.phoneNumber}</td>
					<td style="text-align:left;">
						[${user.zipcode}] ${user.address}<br/>
						${user.addressDetail}
					</td> 
					<td>
						<c:choose>
							<c:when test="${user.userId > 0}">
								<a href="javascript:newUserOrder('${user.userId}')">[주문하기]</a>
							</c:when>
							<c:otherwise>
								<a href="javascript:newGuestOrder('${user.customerCode}')">[주문하기]</a>
							</c:otherwise>
						</c:choose>
					</td>
				</tr> 
			</c:forEach> 
			
		</tbody>
	</table>
	
	<c:if test="${not empty list}">
	
		<div class="board_guide">
			<br/>
			<p class="tip">[안내]</p>
			<p class="tip">
				비회원 주문을 처리하시려면 <a href="javascript:newGuestOrder('')"><font color="#000">&lt;여기&gt;</font></a>를 클릭해주세요. <br />
				(비회원 주문 처리시 자동으로 거래처 정보가 생성됩니다)
			</p> 
		</div> 
	</c:if>
	  
	<c:if test="${empty list}">
		<div class="no_content">
			검색된 회원정보가 없습니다. <br/>비회원 주문을 처리하시려면 <a href="javascript:newGuestOrder('')"><font color="red">&lt;여기&gt;</font></a>를 클릭해주세요.
			<br/>(비회원 주문 처리시 자동으로 거래처 정보가 생성됩니다)
		</div>
	</c:if>			 
</div><!--// board_write E-->

<script type="text/javascript">
	function newGuestOrder(customerCode) {
		Common.popup('/opmanager/order/guest-new-order?customerCode=' + customerCode, 'new-order', 1024, 1024, 1);
	}
	
	function newUserOrder(userId) {
		Common.popup('/opmanager/order/new-order/' + userId, 'new-order', 1024, 1024, 1)
	}
	
</script>