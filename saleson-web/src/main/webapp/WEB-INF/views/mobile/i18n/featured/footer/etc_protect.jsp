<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>

	<div class="title">
		<h2>개인정보보호정책</h2>
		<span class="his_back"><a href="javascript:void(0);" class="ir_pm" onclick="history.back();">뒤로가기</a></span>
	</div>
	<!-- //title -->

	<select class="combobox">
		<%--<option>2018.11.28일 시행일자</option>--%>
		<c:forEach var="periodList" items="${periodList}">
			<option <c:if test="${periodList.policyId eq policy.policyId }"> selected="selected"</c:if> value="${periodList.policyId}">
					${periodList.title}
			</option>
		</c:forEach>
	</select>

	<!-- 내용 : s -->
	<div class="con">	
		<div class="agreement_wrap">
			<div class="pt_noti">
				${policy.content}
				<%--<p class="tit">리뉴올PC는 귀하의 개인정보보호를 매우 중요시하며 , 『정보통신망 이용촉진등에관한법률』을 준수하고 있습니다.</p>
				<p class="tit">리뉴올PC는 개인정보보호방침을 통하여 귀하께서 제공하시는 개인 정보가 어떠한 용도와 방식으로 이용되고 있으며 개인정보보호를 위해 어떠한 조치가 취해지고 있는지 알려드립니다.</p>
				<p class="tit sub">이 개인정보보호방침의 순서는 다음과 같습니다.</p>
				<ul class="or">
					<li>1. 개인정보의 수집목적 및 이용목적 </li>
					<li>2. 목적외 사용 및 제 3자에 대한 제공 및 공유</li>
					<li>3. 개인정보 수집에 대한 동의</li>
					<li>4. 개인정보관리책임자 및 개인정보관리담당자</li>
					<li>5. 개인정보의 수집</li>
					<li>6. 쿠키에 의한 개인정보 수집</li>
					<li>7. 개인정보의 열람 정정</li>
					<li>8. 동의철회 (회원탈퇴)</li>
					<li>9. 개인정보의 보유기간 및 이용기간</li>
					<li>10. 만 14세 미만의 아동의 개인정보보호</li>
					<li>11. 개인정보침해 관련 상담 및 신고</li>
					<li>12 .정책 변경에 따른 공지의무</li>
				</ul>
				<p class="tit sub">1. 개인정보의 수집목적 및 이용목적</p>
				<p class="tit_sub">리뉴올PC는 이용자 확인, 이용대금 결제, 상품 배송 및 통계/분석을 통한 마케팅 자료로써 귀하의 취향에 맞는 최적의 서비스를 제공하기 위한 목적으로 귀하의 개인정보를 수집/이용하고 있습니다.<br/>수집하는 개인정보 항목에 따른 구체적인 수집목적 및 이용목적은 다음과 같습니다. </p>
				<ul class="ur">
					<li><span>-</span> 성명, 아이디, 비밀번호, 주민등록번호 : 회원제 서비스 이용에 따른 본인 확인 절차에 이용</li>
					<li><span>-</span> 이메일주소, 전화번호 : 고지사항 전달, 불만처리 등을 위한 원활한 의사소통 경로의 확보, 새로운 서비스 및 신상품이나 이벤트 정보 등의 안내</li>
					<li><span>-</span> 은행계좌정보, 신용카드정보 : 유료정보 이용 및 상품 구매에 대한 결제</li>
					<li><span>-</span> 주소, 전화번호 : 청구서, 경품 및 쇼핑물품 배송에 대한 정확한 배송지의 확보</li>
					<li><span>-</span> 기타 선택항목 : 개인맞춤 서비스를 제공하기 위한 자료</li>			
				</ul>--%>
			</div>
			<!-- pt_noti -->
		</div>
		<!-- agreement_wrap -->
	</div>
	<!-- 내용 : e -->

<page:javascript>
	<script type="text/javascript">
        $(function() {

            $('#policyList').on('change', function() {
                location.href = '/pages/etc_protect?id='+$(this).val();
            });
        });

        $('.combobox').on("change", function() {
            var policyId = $(this).val();

            $.post("/m/detail-policy-data", {"policyId" : policyId}, function(response) {
                var policyContent = response.data.content;

                $('.pt_noti').empty();
                $('.pt_noti').append(policyContent);

            });
        });


	</script>
</page:javascript>