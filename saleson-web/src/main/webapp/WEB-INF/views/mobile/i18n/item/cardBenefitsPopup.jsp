<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>

	<form:form method="post" enctype="multipart/form-data">
		<!-- 내용 : s -->
		<div class="con">
			<div class="pop_title">
				<h3>카드 혜택 안내</h3>
				<a href="javascript:history.back();" class="history_back">뒤로가기</a>
			</div>
			<!-- //pop_title -->
			<div class="pop_con">
				<div class="card_wrap">
					<h4>6월 신용카드 무이자 행사 안내</h4>
					<div class="info">
						<p class="period">◈ 기간 : ${op:date(cardBenefits.startDate)} ~ ${op:date(cardBenefits.endDate)}</p>
						<!--p class="target">◈ 대상 : 5만원 이상 신용카드 결제 고객</p> -->
					</div>
					<ul class="desc">
						${cardBenefits.content }
					</ul>
				</div>
				<!-- //card_wrap -->
			</div>
			<!-- //pop_con -->

		</div>
		<!-- 내용 : e -->
	</form:form>


