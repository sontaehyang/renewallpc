<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<div class="title">
	<h2>나의 등급안내</h2>
	<span class="his_back"><a href="/m/mypage" class="ir_pm">뒤로가기</a></span>
</div>
<div class="con">
	<div class="mypage_wrap">
		<div class="grade_info">
			<div class="grade">
				<span class="ico">
					<img src="/content/mobile/images/common/${userLevel.fileName}" alt="${userLevel.levelName}">
				</span>
				<p class="txt">${user.userName}님의 등급은 <span>${userLevel.levelName}</span>입니다.</p>
				<p class="desc t_medium t_lgray">(최근 ${userLevel.referencePeriod}개월간 ${op:numberFormat(userLevel.priceStart)}만원 이상 구매)</p>
			</div>
			<div class="info">
				<ul>
					<li>
						<p class="tit">적립혜택</p>
						<p class="num"><span>${op:numberFormat(userLevel.pointRate)}</span> %</p>
					</li>
					<li>
						<p class="tit">할인쿠폰혜택</p>
						<p class="num">매월 <span>?</span> 장</p>
					</li>
					<li>
						<p class="tit">배송비쿠폰지급</p>
						<!-- <p class="tit">배송비 혜택</p> -->
						<p class="num">매월 <span>${op:numberFormat(userLevel.shippingCouponCount)}</span> 장</p>

					</li>
				</ul>
			</div>
		</div><!-- //grade_info E -->
		
		<div class="grade_detail">
			<h4>에스크로 결제금액</h4>
			<ul class="detail_list">
				<c:forEach items="${userLevels}" var="grade" varStatus="gradeIndex">
					<li>
						<span class="ico"><img src="/content/mobile/images/common/${grade.fileName}" alt="${grade.levelName}"></span>
						<p class="grade_name">${grade.levelName}</p>
						<p class="grade_txt t_medium t_gray">구매금액 ${op:numberFormat(grade.priceEnd)}원 미만(등업일로부터 ${grade.referencePeriod}개월 기준)
						<ul>
							<li>${op:message('M00246')} ${op:numberFormat(grade.pointRate)} %</li>
							<li>할인쿠폰 ??</li>
							<li>무료배송쿠폰 달 ${op:numberFormat(grade.shippingCouponCount)} 장 지급</li>
						</ul>
					</li>
				</c:forEach>
			</ul>
		</div><!-- //grade_detail E -->
		
		<div class="grade_desc">
			<span class="t_medium t_gray">※ 회원 등급 선정 기준 및 혜택 이용안내</span>
			<ul>
				<li>회원등급은 등급산정일로부터 6개월간의 구매확정 된 구매실적을 기준으로 산정 됩니다.</li>
				<li>구매실적은 쿠폰, ${op:message('M00246')} 등을 할인적용한 후의 최종 결제 금액을 기준으로 산정 됩니다.</li>
				<li>적립은 '상품 적립', '등급 별 추가 적립'순으로 적립됩니다.</li>
				<li>쿠폰은 등급 변경 후 매월 1일 00:00 시에 적립됩니다.</li>
				<li>회원 등급, 쿠폰, ${op:message('M00246')} 이용에 대한 궁금하신 사항은 고객센터의 FAQ를 참고 해 주시기 바랍니다.</li>
			</ul>
		</div><!-- //grade_desc E -->
	</div><!-- //mypage_wrap E -->
</div><!--//con E -->