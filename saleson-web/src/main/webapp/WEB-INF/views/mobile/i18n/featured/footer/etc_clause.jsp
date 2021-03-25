<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>

	<div class="title">
		<h2>이용약관</h2>
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
			${policy.content}
			<%--<dl class="ag_con">
				<dt>제 1 조 (목적)</dt>
				<dd>이 약관은 주식회사 리뉴올PC이 운영하는 리뉴올PC몰(이하 “몰”이라  한다 )에서 제공하는 인터넷 관련 서비스(이하 “서비스”라 한다)를 이용함에 있 어 사이버 몰과 이용자의 권리 의무 및 책임사항을 규정함을 목적으로 합 니다.</dd>
			</dl>
			<dl class="ag_con num">
				<dt>제 2 조 (목적)</dt>
				<dd><span>1</span>“리뉴올PC몰”이란 주식회사 리뉴올PC이 재화 또는 용역 (이하 “재화등”이라 함)을 이용자에게 제공하기 위하여 컴퓨터등 정보통신설비를 이용하여 재화등을 거래할 수 있도록 설정한 가상의 영업장을 말하며, 아울러 사이버몰을 운영하는 사업자의 의미로도 사용합니다.</dd>
				<dd><span>2</span> “이용자”란 “리뉴올PC몰”에 접속하여 이 약관에 따라 “리뉴올PC몰”이 제공하는 서비스를 받는 회원 및 비회원을 말합니다.</dd>
				<dd><span>3</span>‘회원’이라 함은 “리뉴올PC몰”에 개인정보를 제공하여 회원등록을 한자로서, "세일즈몰"의 정보를 지속적으로 제공받으며, "리뉴올PC몰"이 제공하는 서비스를 계속적으로 시용할 수 잇는 자를 말합니다.</dd>
				<dd><span>4</span>'비회원'이라 함은 회원에 가입하지 않고 "세일즈몰"이 제공하는 서비스를 이용하는 자를 말합니다.</dd>
			</dl>
			<dl class="ag_con num">
				<dt>제 3 조 (약관의 명시와 개)</dt>
				<dd><span>1</span>“리뉴올PC몰”은 이 약관의 내용과 상호 및 대표자 성명, 영업소 소재지 주소(소비자의 불만을 처리할 수 있는 곳의 주소를 포함), 전화번호 모 사전송번호 전자우편주소, 사업자등록번호, 통신판매업신고 번호, 개인정보관리책임자등을 이용자가 쉽게 알 수 있도록 주식회사 세일즈 온몰의 초기 서비스화면 (전면)에 게시합니다. 다만, 약관의 내용은 이 용자가 연결화면을 통하여 볼 수 있도록 할 수 있습니다. </dd>
			</dl>--%>
		</div>
	</div>
	<!-- 내용 : e -->

<page:javascript>
	<script type="text/javascript">
        $(function() {
            /*$('#policyList').on('change', function() {
                location.href = '/pages/etc_clause?id='+$(this).val();
            });*/
        });

        $('.combobox').on("change", function() {
            var policyId = $(this).val();

            $.post("/m/detail-policy-data", {"policyId" : policyId}, function(response) {
                var policyContent = response.data.content;

                $('.agreement_wrap').empty();
                $('.agreement_wrap').append(policyContent);

            });
        });

	</script>
</page:javascript>