<%@ tag pageEncoding="utf-8" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<!--  결제수단설정	sndPaymethod 
	- 신용카드 		1000000000
	- 가상계좌 		0100000000 
	- 에스크로 		0100000000 
	- 계좌이체 		0010000000
	- 월드패스카드 	0001000000
	- 문화상품권		0000100000
	- 휴대폰			0000010000
	- 문화상품권		0000001000 
-->
<input type="hidden" name="sndPaymethod" value=""/>		
<input type="hidden" name="sndStoreid" value=""/>			<!-- 상점아이디 초기 테스트 아이디 : 2999199999-->
<input type="hidden" name="sndOrdernumber" value=""/>		<!-- 주문번호 : euc-kr 기준 50Byte(한글 25자), 다음과 같은 특수문자는 사용하실 수 없습니다. ' " ` - 따옴표,쌍따옴표,백쿼테이션 -->
<input type="hidden" name="sndGoodname" value=""/>			<!-- 상품명 : euc-kr 기준 50Byte(한글 25자), 다음과 같은 특수문자는 사용하실 수 없습니다. ' " ` - 따옴표,쌍따옴표,백쿼테이션 -->
<input type="hidden" name="sndAmount" value=""/>			<!-- 금액 -->
<input type="hidden" name="sndOrdername" value=""/>			<!-- 주문자명 : euc-kr 기준 50Byte(한글 25자), 다음과 같은 특수문자는 사용하실 수 없습니다. ' " ` - 따옴표,쌍따옴표,백쿼테이션 -->
<input type="hidden" name="sndEmail" value=""/>				<!-- 전자우편 : euc-kr 기준 50Byte -->
<input type="hidden" name="sndMobile" value=""/>			<!-- 이동전화 : 전화번호는 euc-kr 기준 12Byte입니다. value 값에 숫자만 넣게 해주시길 바랍니다. '-' 가 들어가면 안됩니다 -->
<input type=hidden  name="sndServicePeriod" value="">		<!-- 제공기간(컨텐츠상품일때) : 현재 휴대폰 결제만 적용되었습니다. - 기본 설정으로 당일날짜가 표기 -->

<!----------------------------------------------- <Part 2. 추가설정항목(메뉴얼참조)>  ----------------------------------------------->

	<!-- 0. 공통 환경설정 -->
	<input type=hidden	name=sndReply value="kspay/return">
	<input type=hidden  name=sndGoodType value="1"> 	<!-- 상품유형: 실물(1),디지털(2) -->
	
	<!-- 1. 신용카드 관련설정 -->
	
	<!-- 신용카드 결제방법  -->
	<!-- 일반적인 업체의 경우 ISP,안심결제만 사용하면 되며 다른 결제방법 추가시에는 사전에 협의이후 적용바랍니다 -->
	<input type=hidden  name=sndShowcard value="I,M"> <!-- I(ISP), M(안심결제), N(일반승인:구인증방식), A(해외카드), W(해외안심)-->
	
	<!-- 신용카드(해외카드) 통화코드: 해외카드결제시 달러결제를 사용할경우 변경 -->
	<input type=hidden	name=sndCurrencytype value="WON"> <!-- 원화(WON), 달러(USD) -->
	
	<!-- 할부개월수 선택범위 -->
	<!--상점에서 적용할 할부개월수를 세팅합니다. 여기서 세팅하신 값은 결제창에서 고객이 스크롤하여 선택하게 됩니다 -->
	<!--아래의 예의경우 고객은 0~12개월의 할부거래를 선택할수있게 됩니다. -->
	<input type=hidden	name=sndInstallmenttype value="ALL(0:2:3:4:5:6:7:8:9:10:11:12)">
	
	<!-- 가맹점부담 무이자할부설정 -->
	<!-- 카드사 무이자행사만 이용하실경우  또는 무이자 할부를 적용하지 않는 업체는  "NONE"로 세팅  -->
	<!-- 예 : 전체카드사 및 전체 할부에대해서 무이자 적용할 때는 value="ALL" / 무이자 미적용할 때는 value="NONE" -->
	<!-- 예 : 전체카드사 3,4,5,6개월 무이자 적용할 때는 value="ALL(3:4:5:6)" -->
	<!-- 예 : 삼성카드(카드사코드:04) 2,3개월 무이자 적용할 때는 value="04(3:4:5:6)"-->
	<!-- <input type=hidden	name=sndInteresttype value="10(02:03),05(06)"> -->
	<input type=hidden	name=sndInteresttype value="NONE">

	<!-- 2. 온라인입금(가상계좌) 관련설정 -->
	<input type=hidden	name=sndEscrow value="0"> 			<!-- 에스크로사용여부 (0:사용안함, 1:사용) -->
	
	<!-- 3. 월드패스카드 관련설정 -->
	<input type=hidden	name=sndWptype value="1">  			<!--선/후불카드구분 (1:선불카드, 2:후불카드, 3:모든카드) -->
	<input type=hidden	name=sndAdulttype value="1">  		<!--성인확인여부 (0:성인확인불필요, 1:성인확인필요) -->
	
	<!-- 4. 계좌이체 현금영수증발급여부 설정 -->
    <input type=hidden  name=sndCashReceipt value="1">          <!--계좌이체시 현금영수증 발급여부 (0: 발급안함, 1:발급) -->

	<!-- 5. 상품권, 게임문화상품권 관련 설정 -->
	<input type=hidden  name=sndMembId value=""> <!-- 가맹점사용자ID (문화,게임문화 상품권결제시 필수) -->
<!---------------------------------------------------------------------------------------------------------------------------->

<!----------------------------------------------- <Part 3. 승인응답 결과데이터>  ----------------------------------------------->
<!-- 결과데이터: 승인이후 자동으로 채워집니다. (*변수명을 변경하지 마세요) -->

	<input type=hidden name=reWHCid 	value="">
	<input type=hidden name=reWHCtype 	value="">
	<input type=hidden name=reWHHash 	value="">
<!---------------------------------------------------------------------------------------------------------------------------->