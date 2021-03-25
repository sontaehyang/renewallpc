<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

		<div class="location">
			<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
		</div>
		
		
		<form:form modelAttribute="itemParam" method="get" enctype="multipart/form-data">
			<!--이용후기(리뷰)관리 시작-->
			<h3><span>${op:message('M00756')}</span></h3>	 <!-- 이용후기(리뷰)관리 -->
			<div class="board_write">
				<table class="board_write_table" summary="${op:message('M00459')}"> <!-- 이용후기(리뷰) --> 
					<caption>${op:message('M00459')}</caption>
					<colgroup>
						<col style="width:150px;" />
						<col style="width:auto;" />
					</colgroup>
					<tbody>
						 <tr>
						 	<td class="label">${op:message('M00011')}</td> <!-- 검색구분 --> 
						 	<td>
						 		<div>
									<form:select path="where" title="${op:message('M00011')}">
										<form:option value="ITEM_NAME">${op:message('M00018')}</form:option> <!-- 상품명 --> 
										<form:option value="ITEM_USER_CODE">${op:message('M00019')}</form:option> <!-- 상품번호 --> 
										<form:option value="USER_NAME">${op:message('M00472')}</form:option> <!-- 작성자 --> 
									</form:select>
									<form:input type="text" path="query" class="three" title="${op:message('M00021')}" /> <!-- 검색어 입력 --> 
								</div>
						 	</td>	
						 </tr>
						<tr>
						 	<td class="label">${op:message('M01630')}</td> <!-- 판매자명 --> 
						 	<td>
						 		<div>
									<select name="sellerId" title="${op:message('M01630')}"> <!-- 판매자선택 --> 
										<option value="0">${op:message('M00039')}</option> <!-- 전체 --> 
										<c:forEach items="${sellerList}" var="list" varStatus="i">
										<c:choose>
											<c:when test="${itemParam.sellerId == list.sellerId}">
												<c:set var='selected' value='selected'/>
											</c:when>
											<c:otherwise>
												<c:set var='selected' value=''/>
											</c:otherwise>
										</c:choose>
										<option value="${list.sellerId}" ${selected}>${list.sellerName}</option>
										</c:forEach>
									</select>
									<a href="javascript:Common.popup('/opmanager/seller/find?statusCode=2', 'find_seller', 800, 500, 1)" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> 검색</a>
								</div>
						 	</td>	
						 </tr>
						 <tr>
						 	<td class="label">공개유무</td>
						 	<td>
						 		<div>
						 			<form:radiobutton path="reviewDisplayFlag" value="" label="전체" />
									<form:radiobutton path="reviewDisplayFlag" value="Y" label="공개" />   <!-- 공개 -->
									<form:radiobutton path="reviewDisplayFlag" value="N" label="비공개" /> <!-- 비공개 -->
								</div>
						 	</td>	
						 </tr>
						 <tr>
						 	<td class="label">메인공개 유무</td>
						 	<td>
						 		<div>
						 			<form:radiobutton path="recommendFlag" value="" label="전체" />
						 			<form:radiobutton path="recommendFlag" value="Y" label="공개" />
									<form:radiobutton path="recommendFlag" value="N" label="비공개" />
								</div>
						 	</td>	
						 </tr>
						 <tr>
						 	<td class="label">${op:message('M00276')}</td> <!-- 작성일 --> 
						 	<td>
						 		<div>
									<span class="datepicker"><form:input type="text" path="searchStartDate" class="datepicker _number" title="${op:message('M00507')}" /></span> <!-- 시작일 --> 
									<span class="wave">~</span>
									<span class="datepicker"><form:input type="text" path="searchEndDate" class="datepicker _number" title="${op:message('M00509')}" /></span> <!-- 종료일 --> 
									<span class="day_btns"> 
										<a href="javascript:;" class="table_btn today">${op:message('M00026')}</a>   <!-- 오늘 --> 
										<a href="javascript:;" class="table_btn week-1">${op:message('M00027')}</a> <!-- 1주일 -->  
										<a href="javascript:;" class="table_btn month-1">${op:message('M00029')}</a>  <!-- 한달 --> 
										<a href="javascript:;" class="table_btn month-3">${op:message('M00030')}</a>  <!-- 3개월 --> 
										<a href="javascript:;" class="table_btn clear">${op:message('M00039')}</a> <!-- 전체 -->
									</span>
								</div> 
						 	</td>	
						 </tr>
						 <tr>
						 	<td class="label">${op:message('M00757')}</td> <!-- 평가 --> 
						 	<td>
						 		<div>
									<form:select path="reviewScore" title="${op:message('M00758')}"> <!-- 평가선택 --> 
										<form:option value="0">${op:message('M00039')}</form:option> <!-- 전체 --> 
										<form:option value="1">★</form:option>
										<form:option value="2">★★</form:option>
										<form:option value="3">★★★</form:option>
										<form:option value="4">★★★★</form:option>
										<form:option value="5">★★★★★</form:option>
									</form:select>
								</div>
						 	</td>	
						 </tr>
					</tbody>					 
				</table>								 							
			</div> <!-- // board_write -->
			
			<!-- 버튼시작 -->	
			<div class="btn_all">
				<div class="btn_left">
				
					<button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='/opmanager/item/review/list';"><span class="glyphicon glyphicon-repeat"></span> ${op:message('M00047')}</button> <!-- 초기화 -->
				</div>
				<div class="btn_right">
					<button type="submit" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> ${op:message('M00048')}</button> <!-- 검색 -->
				</div>
			</div>		 
			<!-- 버튼 끝-->
			
			<div class="count_title mt20">
				<h5>
					${op:message('M00039')} : ${reviewCount} ${op:message('M00743')}
				</h5>	 <!-- 전체 -->   <!-- 건 조회 --> 
				<span>
					${op:message('M00052')} : 
					<form:select path="itemsPerPage" title="${op:message('M00239')}"> <!-- 화면출력 -->
						<form:option value="10" label="${op:message('M00240')}" />  <!-- 10개 출력 --> 
						<form:option value="20" label="${op:message('M00241')}" />  <!-- 20개 출력 -->
						<form:option value="50" label="${op:message('M00242')}" />  <!-- 50개 출력 --> 
						<form:option value="100" label="${op:message('M00243')}" /> <!-- 100개 출력 --> 
					</form:select>
				</span>
			</div>
		</form:form>
		
		<form id="listForm">
			<div class="board_write">
				<table class="board_list_table" summary="${op:message('M00273')}"> <!-- 주문내역 리스트 --> 
					<caption>${op:message('M00273')}</caption>
					<colgroup>
						<col style="width:50px;" />
						<col style="width:70px;" />
						<col style="width:70px;" />
						<col style="width:auto;" />
						<col style="width:80px;" />						
						<col style="width:80px;" />
						<col style="width:400px;" />
						<col style="width:80px;" />
						<col style="width:80px;" />
						<col style="width:110px;" />
						<col style="width:130px;" />
					</colgroup>
					<thead>
						<tr>
							<th scope="col">${op:message('M00200')}</th> <!-- 순번 -->
							<th scope="col">${op:message('M00191')}</th> <!-- 공개유무 -->
							<th scope="col">포토리뷰</th> <!-- 상품이미지 -->
							<th scope="col">내용</th> <!-- 제목 -->
							<th scope="col">${op:message('M00472')}</th> <!-- 작성자 --> 
							<th scope="col">${op:message('M00757')}</th> <!-- 평가 -->
							<th scope="col">상품</th>
							<th scope="col">공개유무</th>
							<th scope="col">메인공개 유무</th>
							<th scope="col">${op:message('M00276')}</th> <!-- 작성일 --> 
							<th scope="col">${op:message('M00087')}/${op:message('M00074')}</th>	 <!-- 수정 --> <!-- 삭제 -->  				
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${reviewList}" var="list" varStatus="i">
							<tr>
								<td>${pagination.itemNumber - i.count}</td>
								<td>${list.displayFlag == "Y" ? op:message('M00096') : op:message('M00097')}</td>
								<td>
									<div class="item_img2">
										<img src="${list.thumbnailSrc}" width="50px" heigt="50px" alt="포토리뷰">
									</div>
								</td> 
								<td class="tex_l">
									<div>	
										<a href="javascript:reviewDetail(${list.itemReviewId});">${op:strcut(list.content,50)}</a>
									</div>
								</td>
								<td>
									<c:choose>
										<c:when test="${list.userId > 0}"> <!-- 이름/이메일 -->
											<a href="javascript:Common.popup('/opmanager/user/popup/details/${list.userId}', '/user/popup/details', 1100, 800 ,1, 0, 0)">${list.userName}</a>
										</c:when>
										<c:otherwise> -	</c:otherwise>
									</c:choose>
								</td>
								<td>
									${list.starScore}
								</td>
								<td>[${list.item.itemUserCode}]<br/>${list.item.itemName}</td>
								<td>${list.displayFlag == 'Y' ? '공개' : '비공개'}</td>
								<td>${list.recommendFlag == 'Y' ? '공개' : '비공개'}</td>
								<td>${op:date(list.createdDate)}</td>
								<td>
									<a href="/opmanager/item/review/edit/${list.itemReviewId}" class="btn btn-gradient btn-xs">${op:message('M00087')}</a>
									<a href="javascript:deleteCheck('${list.itemReviewId}')" class="btn btn-gradient btn-xs">${op:message('M00074')}</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>				 
			</div><!--// board_write E-->
			
			<c:if test="${empty reviewList}">
				<div class="no_content">
					${op:message('M00473')} <!-- 데이터가 없습니다. -->
				</div>
			</c:if>	

			<page:pagination-manager /><br/>
		</form>
		
<div style="display: none;">
	<span id="today">${today}</span>
	<span id="week">${week}</span>
	<span id="month1">${month1}</span>
	<span id="month3">${month3}</span>
</div>
		
<script type="text/javascript">
$(function() {
	
	Common.DateButtonEvent.set('.day_btns > a[class^=table_btn]', '', 'input[name="searchStartDate"]' , 'input[name="searchEndDate"]');
	
	//목록데이터 - 삭제처리
	$('#recommend_popup').on('click', function() {
		Common.popup(url('/opmanager/item/review/recommend'), 'recommend', 450, 460);
		return false;
	});
	
	//목록데이터 - 삭제처리
	$('#recommend_popup').on('click', function() {
		Common.updateListData("/opmanager/item/review/recommend", "선택된 리뷰를 추천하시겠습니까?");	// 선택된 데이터를 삭제하시겠습니까?
	});
	
	//페이지당 보여줄 데이터 수량지정
	$('#itemsPerPage').on("change", function(){
		$('#itemParam').submit();	
	});
});

function deleteCheck(reviewId) {	

	if(confirm(Message.get("M00196"))) {	// 삭제하시겠습니까? 
		location.replace("/opmanager/item/review/delete/" + reviewId);  
	} else {
		return;
	}
	
}
function reviewDetail(reviewId) {
	location.href='/opmanager/item/review/edit/'+reviewId;
}
</script>