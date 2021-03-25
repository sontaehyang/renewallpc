<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>	


	<div class="order">
		<c:set var="gnbType" value="activity" scope="request" />
		<jsp:include page="../include/mypage-header-gnb.jsp"></jsp:include>
		
		<ul class="wish_menu">
			<li><a href="/m/mypage/wishlist">관심상품</a></li>
			<li><a href="/m/mypage/review?recommendFlag=N" class="on">이용후기</a></li>
			<li><a href="">상품Q&amp;A</a></li> 
			<li><a href="">1:1문의</a></li> 
		</ul> 
		
		<h2 class="reveiw_title">이용후기 쓰기</h2>
		<div class="review_top"> 
			<div class="product_img">
				<img src="${itemReview.item.itemImage}" alt="제품이미지">
			</div>
			<div class="cont"> 
				<p class="name">${itemReview.item.itemName}</p>
				<div class="price_zone">
						<span class="item_price">${itemReview.item.itemPrice}원</span>
					<span class="sale_price">${itemReview.item.salePrice}원</span>
					</div> 
			</div> 
		</div><!--//review_top E-->
		
		<form:form modelAttribute="itemReview" method="post">
			<form:hidden path="recommendFlag"/>
			<div class="table_write">
				<table cellpadding="0" cellspacing="0">
					<colgroup>
						<col style="width:35%;">
						<col style="width:65%;">
					</colgroup>
					<tbody>
						<tr>
							<th>평가</th>
							<td> 
								<form:select path="score" class="half" title="평가">
									<form:option value="5" label="5점"/>
									<form:option value="4" label="4점"/>
									<form:option value="3" label="3점"/>
									<form:option value="2" label="2점"/>
									<form:option value="1" label="1점"/>
								</form:select>
							</td> 
						</tr>
						<tr>
							<th>제목</th>
							<td><form:input path="subject" title="제목" class="required" maxlength="100"/></td> 
						</tr> 
						<tr>
							<th valign="top">내용</th>
							<td>
								<form:textarea path="content" title="내용" class="required" cols="10" rows="5"/>
							</td> 
						</tr>  
					</tbody>
				</table>		
			</div>
			
			<div class="btn_area wrap_btn">
				<div style="display:block" class="sale division-1">
					<div>
						<div>
							<button type="submit" class="btn btn_on btn-w100" href="">저장</button>
						</div>
					</div> 
				</div>				 
			</div>	
		</form:form>
	
	</div>	<!--//order E-->
	 
<page:javascript>	
<script type="text/javascript">
$(function() {
	$('#itemReview').validator(function() {
		
	});
});
</script>
</page:javascript>