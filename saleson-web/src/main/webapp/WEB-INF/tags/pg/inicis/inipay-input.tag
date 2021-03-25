<%@ tag pageEncoding="utf-8" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>

<c:choose>
	<c:when test="${op:property('pg.inipay.web.type') == 'webStandard'}">
<input type="hidden" name="version" value="1.0" />
<input type="hidden" name="mid" />
<input type="hidden" name="goodname" />
<input type="hidden" name="oid" />
<input type="hidden" name="price" />
<input type="hidden" name="taxfree" />
<input type="hidden" name="currency" value="WON" />
<input type="hidden" name="buyername" />
<input type="hidden" name="buyertel" />
<input type="hidden" name="buyeremail" />
<input type="hidden" name="timestamp" />
<input type="hidden" name="signature" />
<input type="hidden" name="returnUrl" />
<input type="hidden" name="mKey" />

<!--
ex) Card (계약 결제 수단이 존재하지 않을 경우 에러로 리턴)
사용 가능한 입력 값
Card,DirectBank,HPP,Vbank,kpay,Swallet,Paypin,EasyPay,PhoneBill,GiftCard,EWallet
onlypoint,onlyocb,onyocbplus,onlygspt,onlygsptplus,onlyupnt,onlyupntplus
-->
<input type="hidden" name="gopaymethod" />

<!-- 
제공기간
ex)20151001-20151231, [Y2:년단위결제, M2:월단위결제, yyyyMMdd-yyyyMMdd : 시작일-종료일]
-->
<input type="hidden" name="offerPeriod" />
<input type="hidden" name="acceptmethod" value="no_receipt:below1000"/>

<!-- 
초기 표시 언어
[ko|en] (default:ko)
-->
<input type="hidden" name="languageView" value="ko" />

<!-- 
리턴 인코딩
[UTF-8|EUC-KR] (default:UTF-8)
-->
<input type="hidden" name="charset" value="UTF-8" />

<!-- 
결제창 표시방법
[overlay, popup] (default:overlay)
-->
<input type="hidden" name="payViewType" value="overlay" />

<!-- 
payViewType='overlay','popup'시 취소버튼 클릭시 창닥기 처리 URL(가맹점에 맞게 설정)
close.jsp 샘플사용(생략가능, 미설정시 사용자에 의해 취소 버튼 클릭시 인증결과 페이지로 취소 결과를 보냅니다.)
-->
<input type="hidden" name="closeUrl" />

<!--  
payViewType='popup'시 팝업을 띄울수 있도록 처리해주는 URL(가맹점에 맞게 설정)
popup.jsp 샘플사용(생략가능,payViewType='popup'으로 사용시에는 반드시 설정)
-->
<input type="hidden" name="popupUrl" value="" />

<!-- 
할부 개월 설정
ex) 2:3:4
-->
<input type="hidden" name="quotabase" />

<!--
중복 카드 코드
ex) 01:03:04:11
-->
<input type="hidden" name="ini_onlycardcode" />

<!-- 
카드 코드
ex) 2:3:4
-->
<input type="hidden" name="ini_cardcode" />

<!-- 
할부 선택
ex) 2:3:4
-->
<input type="hidden" name="ansim_quota" />

<!-- 
가상계좌이용시 주민번호 설정 기능
13자리(주민번호),10자리(사업자번호),미입력시(화면에서입력가능)
-->
<input type="hidden" name="vbankRegNo" />

<!-- 
가맹점 관리데이터(2000byte)
인증결과 리턴시 함께 전달됨
-->
<input type="hidden"  name="merchantData" />
	</c:when>
	<c:otherwise>
<!-- 기타설정 -->
<!--
SKIN : 플러그인 스킨 칼라 변경 기능 - 5가지 칼라(ORIGINAL, GREEN, YELLOW, RED,PURPLE)
HPP : 컨텐츠 또는 실물 결제 여부에 따라 HPP(1)과 HPP(2)중 선택 적용(HPP(1):컨텐츠, HPP(2):실물).
Card(0): 신용카드 지불시에 이니시스 대표 가맹점인 경우에 필수적으로 세팅 필요 ( 자체 가맹점인 경우에는 카드사의 계약에 따라 설정) - 자세한 내용은 메뉴얼  참조.
OCB : OK CASH BAG 가맹점으로 신용카드 결제시에 OK CASH BAG 적립을 적용하시기 원하시면 "OCB" 세팅 필요 그 외에 경우에는 삭제해야 정상적인 결제 이루어짐.
no_receipt : 은행계좌이체시 현금영수증 발행여부 체크박스 비활성화 (현금영수증 발급 계약이 되어 있어야 사용가능)
-->
<input type=hidden name=acceptmethod value="SKIN(ORIGINAL):no_receipt">
<input type=hidden name=currency value="WON">


<!--
상점 주문번호 : 무통장입금 예약(가상계좌 이체),전화결재 관련 필수필드로 반드시 상점의 주문번호를 페이지에 추가해야 합니다.
결제수단 중에 은행 계좌이체 이용 시에는 주문 번호가 결제결과를 조회하는 기준 필드가 됩니다.
상점 주문번호는 최대 40 BYTE 길이입니다.
## 주의:절대 한글값을 입력하시면 안됩니다. ##
-->
<input type=hidden name=oid size=40 value="">



<!--
플러그인 좌측 상단 상점 로고 이미지 사용
이미지의 크기 : 90 X 34 pixels
플러그인 좌측 상단에 상점 로고 이미지를 사용하실 수 있으며,
주석을 풀고 이미지가 있는 URL을 입력하시면 플러그인 상단 부분에 상점 이미지를 삽입할수 있습니다.
-->
<!--input type=hidden name=ini_logoimage_url  value="http://[사용할 이미지주소]"-->

<!--
좌측 결제메뉴 위치에 이미지 추가
이미지의 크기 : 단일 결제 수단 - 91 X 148 pixels, 신용카드/ISP/계좌이체/가상계좌 - 91 X 96 pixels
좌측 결제메뉴 위치에 미미지를 추가하시 위해서는 담당 영업대표에게 사용여부 계약을 하신 후
주석을 풀고 이미지가 있는 URL을 입력하시면 플러그인 좌측 결제메뉴 부분에 이미지를 삽입할수 있습니다.
-->
<!--input type=hidden name=ini_menuarea_url value="http://[사용할 이미지주소]"-->

<!--
플러그인에 의해서 값이 채워지거나, 플러그인이 참조하는 필드들
삭제/수정 불가
uid 필드에 절대로 임의의 값을 넣지 않도록 하시기 바랍니다.
-->

<input type="hidden" name="buyername" />
<input type="hidden" name="buyeremail" />
<input type="hidden" name="buyertel" />

<input type="hidden" name="gopaymethod" value="" />
<input type="hidden" name="goodname" />
<input type=hidden name=ini_encfield value="" />
<input type=hidden name=ini_certid value="">
<input type=hidden name=quotainterest value="">
<input type=hidden name=paymethod value="">
<input type=hidden name=cardcode value="">
<input type=hidden name=cardquota value="">
<input type=hidden name=rbankcode value="">
<input type=hidden name=reqsign value="DONE">
<input type=hidden name=encrypted value="">
<input type=hidden name=sessionkey value="">
<input type=hidden name=uid value=""> 
<input type=hidden name=sid value="">
<input type=hidden name=version value=5000>
<input type=hidden name=clickcontrol value="">

	</c:otherwise>
</c:choose>
