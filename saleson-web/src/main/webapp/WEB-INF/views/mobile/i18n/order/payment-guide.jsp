<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>

		<div class="con">
			<div class="pop_title">
				<h3>
					<c:if test="${paymentType == 'realtimebank'}">실시간 계좌이체 이용안내</c:if>
					<c:if test="${paymentType == 'bank'}">무통장 입금 이용안내</c:if>
					<c:if test="${paymentType == 'tax'}">현금영수증 안내</c:if>
				</h3>
				<a href="javascript:history.back();" class="history_back">뒤로가기</a>
			</div>
			<!-- //pop_title -->
			<div class="pop_con">
				<div class="payment_pop">
					<c:if test="${paymentType == 'realtimebank'}">
						<ul>
							<li>
								<p class="txt">실시간 계좌이체는 리뉴올PC에 결제액이 바로 입금되는 방식으로 별도의 서비스 가입절차가 필요없이 실시간으로 고객님의 통장에서 결제처리가 되는 방식으로 수수료는 리뉴올PC에서 부담합니다.	</p>
							</li>
							<li>
								<p class="txt">대금청구서는 가맹점란에 "㈜LG데이콤 가맹점"으로 표기됩니다.</p>
							</li>
							<li>
								<p class="txt">실시간 계좌이체는 10분이내 입금내역이 확인됩니다.</p>
							</li>
						</ul>
					</c:if>
					<c:if test="${paymentType == 'bank'}">
						<ul>
							<li>
								<p class="txt">고객님의 편리를 위하여 <span>무통장 입금하시는 고객님께 우리은행 가상전용 계좌(1인1구좌)를 부여</span>해 드리고 있습니다. 
								부여된 전용계좌로 상품금액을 입금해주시면 주문이 완료됩니다. 
								무통장 입금 시 입금자명은 주문 시 입력한 입금자명과 일치해야 정상 확인이 가능합니다.
								</p>
							</li>
							<li>
								<p class="txt"><span>입금확인</span>은 5분 단위로 자동 전산 확인이 됩니다. 
								단, 10분 이후에도 입금확인이 완료되지 않으면 고객센터로 연락하시기 바랍니다. 수신자부담 무료전화 080-700-7200
								</p>
							</li>
							<li>
								<p class="txt">주문에 대한 고유의 무통장 입금 계좌번호는 다음단계인 [주문완료] 페이지에서 확인할 수 있으며 고객님의 핸드폰 SMS로 전송됩니다.</p>
								<p class="desc">※ 무통장입금 오류 발생의 대부분은 전용계좌번호 및 입금금액(주문금액)이 다른 경우에 발생할 때가 많습니다. 입금 오류일 경우<span>[나의 쇼핑정보] 주문내역</span>에서 입금계좌번호, 입금액을 확인 후 재시도를 부탁드립니다.</p>
							</li>
							<li>
								<p class="txt">주문을 위한 무통장 입금 계좌번호는 주문 별로 각각 다른 계좌번호가 자동생성 되고 해당 주문에만 유효합니다.</p>
							</li>
						</ul>
					</c:if>
					<c:if test="${paymentType == 'tax'}">
						<h4>현금영수증 종류</h4>
						<dl>
							<dt>소득공제용(개인용)</dt>
							<dd>개인이 1년 동안 현금을 사용한 금액에 대해 소득공제를 받을 수 있는 영수증</dd>
							<dt>지출증빙용(사업자)</dt>
							<dd>세금계산서처럼 기업이 지정된 기간 내에 현금을 사용한 부분에 대해서 세액공제를 받을 수 있는 영수증</dd>
						</dl>
						<ul>
							<li>
								<p class="txt">정보통신망 이용촉진 및 정보보호 등에 관한 법률 개정으로 인해 2012년 8월 18일부터 주민등록번호 사용이 제한됩니다.</p>
							</li>
							<li>
								<p class="txt">현금영수증을 신청하지 않으신 경우 현금영수증이 자동발급 되며 자진발급 된 현금영수증은 국세청 현금영수증 사이트 (www.taxsave.go.kr)에서 사용자등록 후 고객님(본인)의 것으로 전환등록 하실 수 있습니다.</p>
							</li>
							<li>
								<p class="txt">세금계산서와 현금영수증은 모두 지출증빙이 가능하고 현금영수증 자진 발급 제 시행에 따라 세금계산서는 발급 되지 않습니다. 지출증빙용 현금영수증은 세금계산서와 동일하게 부가세 환급을 받으실 수 있습니다.</p>
							</li>
						</ul>
					</c:if>
				</div>

			</div>
			<!-- pop_con -->
		</div>
		<!-- 내용 : e -->