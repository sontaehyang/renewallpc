<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<%@ taglib prefix="daum"	tagdir="/WEB-INF/tags/daum" %>


<div id="wrap">
	
	<!-- container : s -->
	<div id="container">

<form:form modelAttribute="userDelivery" method="post" action="${action}">
<form:hidden path="userDeliveryId" />

	
		<!-- 내용 : s -->
		<div class="con">
			<div class="pop_title">
				<h3>배송지 수정</h3>
				<a href="javascript:history.back();" class="history_back">뒤로가기</a>
			</div>
			<!-- //pop_title -->
			<div class="pop_con">
				<div class="bd_table">
					<ul class="del_info">
					
						<li>
							<span class="del_tit t_gray">배송지 이름</span>
							<div class="input_area">
								<%-- <input type="text" class="transparent" value="${ item.title }"> --%>
								<form:input path="title" class="transparent"/>
							</div>
						</li>
						<li>
							<span class="del_tit t_gray star">받으시는 분</span>
							<div class="input_area">
								<%-- <input type="text" class="transparent" value="${item.userName }" readonly> --%>
								<form:input path="userName" class="transparent" />
							</div>
						</li>
						
						
						<li>
							<span class="del_tit t_gray star">휴대폰</span>
							<div class="num">
								<div class="in_td">
									<div class="input_area">
										<form:input path="mobile1" class="transparent"/>
									</div>
									<div class="in_td dash"></div>
									<div class="input_area">
										<form:input path="mobile2" class="transparent"/>
									</div>
									<div class="in_td dash"></div>
									<div class="input_area">
										<form:input path="mobile3" class="transparent"/>
									</div>
								</div>
							</div>
						</li>											
																
						
						<li>
							<span class="del_tit t_gray">전화번호</span>
							<div class="num">
								<div class="in_td">
									<div class="input_area">
										<form:input path="phone1" class="transparent"/>
									</div>
									<div class="in_td dash"></div>
									<div class="input_area">
										<form:input path="phone2" class="transparent"/>
									</div>
									<div class="in_td dash"></div>
									<div class="input_area">
										<form:input path="phone3" class="transparent"/>
									</div>
								</div>
							</div>
						</li>
											
						
						<li class="chunk">
							<label class="del_tit t_gray star">주소</label>
							<div class="user_info">
								<div class="in_td">
									<div class="input_area">								
										<form:input path="zipcode" class="transparent"/>
									</div>
								</div>
								<div class="in_td bar"></div>
								<div class="in_td address">
									<a href="javascript:openDaumPostcode();" class="btn_st3">우편번호</a>
								</div>
							</div>
							<div class="input_area">
								<form:input path="address" class="transparent" />
							</div>
							
						</li>
						<li>
							<span class="del_tit t_gray star">상세주소</span>
							<form:input path="addressDetail" class="transparent"/>
						</li>
					</ul>
				</div>
				<!-- //bd_table -->
				<div class="set">
					<span class="tit">기본배송지 설정</span>
					<label class="btn_st5">
						<input type="checkbox">
						<div class="slider round"></div>
					</label>
				</div>
				<!-- //set -->
				<div class="btn_wrap">
							
					 <button type="button" class="btn_st1 reset" onclick="location.href='/m/mypage/delivery'">취소</button>
					<button type="submit" class="btn_st1 decision">확인</button>
					
				</div>
				<!-- //btn_wrap -->
			</div>
			<!-- //pop_con -->

		</div>
		<!-- 내용 : e -->
		
		</form:form>
	</div>
	<!-- //#container -->
	<!-- container : e -->
	
</div>
<!-- //#wrap -->

<daum:address />
<page:javascript>
<script type="text/javascript">

$(function(){
	// 모바일 키페드 셋팅
	Common.setMobileKeypad(true);
	
	$("#userDelivery").validator({
		'requiredClass' : 'required',
		'submitHandler' : function() {
			
		}
	}); 
})

function openDaumPostcode() {
	var tagNames = {
		'newZipcode'			: 'newZipcode',
		'zipcode' 				: 'zipcode',
		'zipcode1' 				: 'zipcode1',
		'zipcode2' 				: 'zipcode2',
	}
	
	openDaumAddress(tagNames);
}







</script>
</page:javascript>