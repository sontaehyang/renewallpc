<%------------------------------------------------------------------------------
 FILE NAME : INIsecurestart.jsp
 AUTHOR : ts@inicis.com
 DATE : 2007/08
 USE WITH : INIsecureresult.jsp, INIpay50.jar
 
 이니페이 플러그인을 이용하여 지불을 요청한다.
 
 Copyright 2007 Inicis, Co. All rights reserved.
------------------------------------------------------------------------------%>

<%@ page language = "java" contentType = "text/html;charset=euc-kr" %>

<%-- 
     ***************************************
     * 1. INIpay 라이브러리                * 
     *************************************** 
--%>
<%@ page import = "com.inicis.inipay.*" %>
<%

    request.setCharacterEncoding("euc-kr");
    /***************************************
     * 2. INIpay 인스턴스 생성             *
     ***************************************/
    INIpay50 inipay = new INIpay50();
    
    /***************************************
     * 3. 암호화 대상/값 설정              *
     ***************************************/
    inipay.SetField("inipayhome", "/home/hongjun82/resin/webapps/INIpay50"); // 이니페이 홈디렉터리(상점수정 필요)
    inipay.SetField("admin", "1111"); 							  // 키패스워드(상점아이디에 따라 변경)
    //***********************************************************************************************************
    //* admin 은 키패스워드 변수명입니다. 수정하시면 안됩니다. 1111의 부분만 수정해서 사용하시기 바랍니다.      *
    //* 키패스워드는 상점관리자 페이지(https://iniweb.inicis.com)의 비밀번호가 아닙니다. 주의해 주시기 바랍니다.*
    //* 키패스워드는 숫자 4자리로만 구성됩니다. 이 값은 키파일 발급시 결정됩니다.                               *
    //* 키패스워드 값을 확인하시려면 상점측에 발급된 키파일 안의 readme.txt 파일을 참조해 주십시오.             *
    //***********************************************************************************************************
    inipay.SetField("type", "chkfake");							  // 고정 (절대 수정 불가)
    
    inipay.SetField("enctype","asym"); 			                  // 고정 (절대 수정 불가) asym:비대칭, symm:대칭
    inipay.SetField("checkopt", "false"); 		                  // 고정 (절대 수정 불가) base64함:false, base64안함:true
    inipay.SetField("debug","true");                              // 로그모드("true"로 설정하면 상세로그가 생성됨.)
    inipay.SetField("crypto", "execure");						  // Extrus 암호화모듈 사용(고정)
    
    //필수항목 : mid, price, nointerest, quotabase
    //추가가능 : INIregno, oid
    //*주의* : 	추가가능한 항목중 암호화 대상항목에 추가한 필드는 반드시 hidden 필드에선 제거하고 
    //          SESSION이나 DB를 이용해 다음페이지(INIsecureresult.jsp)로 전달/셋팅되어야 합니다.
    inipay.SetField("mid", "INIpayTest");                           //상점아이디
    inipay.SetField("price", "1000");                               // 가격
    inipay.SetField("nointerest", "no");                            //무이자여부
    inipay.SetField("quotabase", "선택:일시불:2개월:3개월:6개월");  //할부기간
    String[] parameters = {"price","nointerest", "quotabase"};
    inipay.SetField("parameters",parameters);
    
    /********************************
     * 4. 암호화 대상/값을 암호화함 *
     ********************************/

    
    inipay.startAction();

    /*********************
     * 5. 암호화 결과    *
     *********************/
    String rn_resultMsg = "";
 		if( inipay.GetResult("ResultCode") != "00" ) 
		{
		    rn_resultMsg = inipay.GetResult("ResultMsg");
		}

    /*********************
     * 6. 세션정보 저장  *
     *********************/
    session.setAttribute("INI_MID"    , inipay.GetResult("mid"));
    session.setAttribute("INI_RN"     , inipay.GetResult("rn"));
    session.setAttribute("INI_ENCTYPE", inipay.GetResult("enctype"));
    session.setAttribute("INI_PRICE"  , inipay.GetResult("price") );
    session.setAttribute("admin"      , inipay.GetResult("admin"));

    /*******************************************
     * 7. 플러그인 전달 정보, hidden field 설정*
     *******************************************/
    String ini_encfield = inipay.GetResult("encfield");
    String ini_certid   = inipay.GetResult("certid");
   
    /*********************
     * 6. 인스턴스 해제  *
     *********************/
    inipay = null;
    
// 암호화정보 관련 에러발생 체크, 암호화 처리 정상 때만 결제 진행
if ( rn_resultMsg.trim().length() == 0 ){
%>

<html>    
<head>
<title>INIpay50 데모</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<meta http-equiv="Cache-Control" content="no-cache"/> 
<meta http-equiv="Expires" content="0"/> 
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="X-UA-Compatible" content="requiresActiveX=true" />

<link rel="stylesheet" href="css/group.css" type="text/css">
<style>
body, tr, td {font-size:10pt; font-family:굴림,verdana; color:#433F37; line-height:19px;}
table, img {border:none}

/* Padding ******/ 
.pl_01 {padding:1 10 0 10; line-height:19px;}
.pl_03 {font-size:20pt; font-family:굴림,verdana; color:#FFFFFF; line-height:29px;}

/* Link ******/ 
.a:link  {font-size:9pt; color:#333333; text-decoration:none}
.a:visited { font-size:9pt; color:#333333; text-decoration:none}
.a:hover  {font-size:9pt; color:#0174CD; text-decoration:underline}

.txt_03a:link  {font-size: 8pt;line-height:18px;color:#333333; text-decoration:none}
.txt_03a:visited {font-size: 8pt;line-height:18px;color:#333333; text-decoration:none}
.txt_03a:hover  {font-size: 8pt;line-height:18px;color:#EC5900; text-decoration:underline}
</style>

<!------------------------------------------------------------------------------- 
* 웹SITE 가 https를 이용하면 https://plugin.inicis.com/pay61_secunissl_cross.js를 사용 
* 웹SITE 가 Unicode(UTF-8)를 이용하면 http://plugin.inicis.com/pay61_secuni_cross.js를 사용 
* 웹SITE 가 https, unicode를 이용하면 https://plugin.inicis.com/pay61_secunissl_cross.js 사용 
--------------------------------------------------------------------------------> 
<!---------------------------------------------------------------------------------- 
※ 주의 ※ 
 상단 자바스크립트는 지불페이지를 실제 적용하실때 지불페이지 맨위에 위치시켜 
 적용하여야 만일에 발생할수 있는 플러그인 오류를 미연에 방지할 수 있습니다. 
 
  <script language=javascript src="http://plugin.inicis.com/pay61_secuni_cross.js"></script> 
  <script language=javascript> 
  StartSmartUpdate();   // 플러그인 설치(확인) 
  </script> 
----------------------------------------------------------------------------------->  
<!-- 플러그인 6.0 위변조 모듈 크로스 브라우징 pay61_secuni_cross.js-->
<script language=javascript src="http://plugin.inicis.com/pay61_secuni_cross.js"></script>
<script language=javascript>
StartSmartUpdate();
</script>

<script language=javascript>

var openwin;

function pay(frm)
{
	// MakePayMessage()를 호출함으로써 플러그인이 화면에 나타나며, Hidden Field
	// 에 값들이 채워지게 됩니다. 일반적인 경우, 플러그인은 결제처리를 직접하는 것이
	// 아니라, 중요한 정보를 암호화 하여 Hidden Field의 값들을 채우고 종료하며,
	// 다음 페이지인 INIsecureresult.php로 데이터가 포스트 되어 결제 처리됨을 유의하시기 바랍니다.

	if(document.ini.clickcontrol.value == "enable")
	{
		
		if(document.ini.goodname.value == "")  // 필수항목 체크 (상품명, 상품가격, 구매자명, 구매자 이메일주소, 구매자 전화번호)
		{
			alert("상품명이 빠졌습니다. 필수항목입니다.");
			return false;
		}
		else if(document.ini.buyername.value == "")
		{
			alert("구매자명이 빠졌습니다. 필수항목입니다.");
			return false;
		} 
		else if(document.ini.buyeremail.value == "")
		{
			alert("구매자 이메일주소가 빠졌습니다. 필수항목입니다.");
			return false;
		}
		else if(document.ini.buyertel.value == "")
		{
			alert("구매자 전화번호가 빠졌습니다. 필수항목입니다.");
			return false;
		}
		else if(( navigator.userAgent.indexOf("MSIE") >= 0 || navigator.appName == 'Microsoft Internet Explorer' ) &&  (document.INIpay == null || document.INIpay.object == null) )  // 플러그인 설치유무 체크
		{
			alert("\n이니페이 플러그인 128이 설치되지 않았습니다. \n\n안전한 결제를 위하여 이니페이 플러그인 128의 설치가 필요합니다. \n\n다시 설치하시려면 Ctrl + F5키를 누르시거나 메뉴의 [보기/새로고침]을 선택하여 주십시오.");
			return false;
		}
		else
		{
			/******
			 * 플러그인이 참조하는 각종 결제옵션을 이곳에서 수행할 수 있습니다.
			 * (자바스크립트를 이용한 동적 옵션처리)
			 */
			
						 
			if (MakePayMessage(frm))
			{
				disable_click();
				openwin = window.open("childwin.html","childwin","width=299,height=149");		
				return true;
			}
			else
			{
				if(IsPluginModule()) {
					alert("결제를 취소하셨습니다.");
				}
				return false;
			}
		}
	}
	else
	{
		return false;
	}
}


function enable_click()
{
	document.ini.clickcontrol.value = "enable"
}

function disable_click()
{
	document.ini.clickcontrol.value = "disable"
}

function focus_control()
{
	if(document.ini.clickcontrol.value == "disable")
		openwin.focus();
}
</script>


<script language="JavaScript" type="text/JavaScript">
<!--
function MM_reloadPage(init) {  //reloads the window if Nav4 resized
  if (init==true) with (navigator) {if ((appName=="Netscape")&&(parseInt(appVersion)==4)) {
    document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=MM_reloadPage; }}
  else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) location.reload();
}
MM_reloadPage(true);

function MM_jumpMenu(targ,selObj,restore){ //v3.0
  eval(targ+".location='"+selObj.options[selObj.selectedIndex].value+"'");
  if (restore) selObj.selectedIndex=0;
}
//-->
</script>
</head>

<!-----------------------------------------------------------------------------------------------------
※ 주의 ※
 아래의 body TAG의 내용중에 
 onload="javascript:enable_click()" onFocus="javascript:focus_control()" 이 부분은 수정없이 그대로 사용.
 아래의 form TAG내용도 수정없이 그대로 사용.
------------------------------------------------------------------------------------------------------->

<body bgcolor="#FFFFFF" text="#242424" leftmargin=0 topmargin=15 marginwidth=0 marginheight=0 bottommargin=0 rightmargin=0 onload="javascript:enable_click()" onFocus="javascript:focus_control()"><center>
<form name=ini method=post action="INIsecureresult.jsp" onSubmit="return pay(this)"> 
<table width="632" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td height="85" background="img/card.gif" style="padding:0 0 0 64">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="3%" valign="top"><img src="img/title_01.gif" width="8" height="27" vspace="5"></td>
          <td width="97%" height="40" class="pl_03"><font color="#FFFFFF"><b>결제요청 - 위변조 체크 정보 생성</b></font></td>
        </tr>
      </table></td>
  </tr>
  <tr> 
    <td align="center" bgcolor="6095BC"><table width="620" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td bgcolor="#FFFFFF" style="padding:8 0 0 56"> <table width="530" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td>이 페이지는 결제을 요청하는 페이지로써 샘플(예시) 페이지입니다. <br>
                  결제페이지 개발자는 소스 내용 중에 "<font color=red>※ 주의 ※</font>" 표시가 포함된 문장은 특히 주의하여 귀사의 요구에 맞게 적절히 수정 적용하시길 바랍니다.
                  <br>
                  <br>은행의 인터넷뱅킹 Site에서 사용 중인 플러그인(보안프로그램)과 동일한 방식으로 이니시스 결제 페이지에서도 플러그인 프로그램을 이용하여 보안 및 인증 처리를 하고 있습니다.<br>
		      인터넷 뱅킹 Site에서 인터넷뱅킹 업무를 수행하기 전에 플러그인 설치확인 페이지를 수행하는 것과 마찬가지로 이니페이 플러그인 설치확인 페이지(plugin_check.html)를 수행한 후 결제요청 페이지(INIsecurestart.jsp)의 기능을 구현하셔야 합니다.
		  <br>
                  
                  <br>
                 "결제" 버튼을 누르면 결제 정보를 안전하게 암호화하기 위한 플러그인 창이 출력됩니다.<br>
                  플러그인에서 제시하는 단계에 따라 정보를 입력한 후 <b>[결제 정보 확인]</b> 단계에서 "확인" 버튼을 누르면 
                  결제처리가 시작됩니다.<br>
                  통신환경에 따라 다소 시간이 걸릴수도 있으니 결제결과가 표시될때까지 "중지" 버튼을 누르거나 브라우저를 종료하시지 말고
                  잠시만 기다려 주십시오.<br></td>
              </tr>
            </table>
            <br>
            <table width="510" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="7"><img src="img/life.gif" width="7" height="30"></td>
                <td background="img/center.gif"><img src="img/icon03.gif" width="12" height="10"> 
                  <b>정보를 기입하신 후 결제버튼을 눌러주십시오.</b></td>
                <td width="8"><img src="img/right.gif" width="8" height="30"></td>
              </tr>
            </table>
            <br>
            <table width="510" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="510" colspan="2"  style="padding:0 0 0 23"> 
                  <table width="470" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                      <td width="109" height="25">결 제 방 법 </td>
                      <td width="343"> <select name=gopaymethod >
                          <option value="">[ 결제방법을 선택하세요. ] 
                          <option value="Card">신용카드 결제
                          <option value="VCard">인터넷안전 결제 
                          <option value="DirectBank">실시간 은행계좌이체 
                          <option value="HPP">핸드폰 결제
                          <option value="PhoneBill">받는전화결제 
                          <option value="Ars1588Bill">1588 전화 결제 
                          <option value="VBank">무통장 입금 
                          <option value="OCBPoint">OK 캐쉬백포인트 결제
                          <option value="Culture">문화상품권 결제
                          <option value="TeenCash">틴캐시 결제
                          <option value="dgcl">스마트문상 결제
                          <option value="bcsh">도서문화 상품권 결제
                          <option value="onlycard" > 신용카드 결제(전용메뉴) 
                          <option value="onlyisp"> 인터넷안전 결제 (전용메뉴) 
                          <option value="onlydbank"> 실시간 은행계좌이체 (전용메뉴) 
                          <option value="onlycid"> 신용카드/계좌이체/무통장입금(전용메뉴) 
                          <option value="onlyvbank"> 무통장입금(전용메뉴) 
                          <option value="onlyhpp"> 핸드폰 결제(전용메뉴) 
                          <option value="onlyphone"> 전화 결제(전용메뉴) 
                          <option value="onlyocb"> OK 캐쉬백 결제 - 복합결제 불가능(전용메뉴) 
                          <option value="onlyocbplus"> OK 캐쉬백 결제- 복합결제 가능(전용메뉴) 
                          <option value="onlyculture"> 문화상품권 결제(전용메뉴) 
                          <option value="onlyteencash"> 틴캐시 결제(전용메뉴)
                          <option value="onlydgcl"> 스마트문상(전용메뉴)
                          <option value="onlybcsh"> 도서문화 상품권 결제(전용메뉴)
                          </select></td>
                    </tr>
                    <tr> 
                      <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    </tr>
                    <tr> 
                      <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                      <td width="177" height="26">상 품 명</td>
                      <td width="280"><input type=text name=goodname size=20 value="축구공"></td>
                    </tr>
                    <tr> 
                      <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    </tr>
                    <tr> 
                      <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                      <td width="109" height="25">성 명</td>
                      <td width="343"><input type=text name=buyername size=20 value="홍길동"></td>
                    </tr>
                    <tr> 
                      <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    </tr>
                    <tr> 
                      <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                      <td width="109" height="25">전 자 우 편</td>
                      <td width="343"><input type=text name=buyeremail size=20 value="hkd@abcd.com"></td>
                    </tr>
                    <tr> 
                      <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    </tr>
                    
<!-----------------------------------------------------------------------------------------------------
  ※ 주의 ※
  보호자 이메일 주소입력 받는 필드는 소액결제(핸드폰 , 전화결제)
  중에  14세 미만의 고객 결제시에 부모 이메일로 결제 내용통보하라는 정통부 권고 사항입니다. 
  다른 결제 수단을 이용시에는 해당 필드(parentemail)삭제 하셔도 문제없습니다.
------------------------------------------------------------------------------------------------------->  
                    <tr> 
                      <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                      <td width="109" height="25">보호자 전자우편</td>
                      <td width="343"><input type=text name=parentemail size=20 value="parents@parents.com"></td>
                    </tr>
                    <tr> 
                      <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    </tr>
                    <tr> 
                      <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                      <td width="109" height="25">이 동 전 화</td>
                      <td width="343"><input type=text name=buyertel size=20 value="011-123-1234"></td>
                    </tr>
                    <tr> 
                    <tr> 
                      <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    </tr>
                    
                    <tr valign="bottom"> 
                      <td height="40" colspan="3" align="center"><input type=image src="img/button_03.gif" width="63" height="25"></td>
                    </tr>
                    <tr valign="bottom">
                      <td height="45" colspan="3">전자우편과 이동전화번호를 입력받는 것은 고객님의 결제성공 내역을 E-MAIL 또는 SMS 로
                   알려드리기 위함이오니 반드시 기입하시기 바랍니다.</td>
                    </tr>
                  </table></td>
              </tr>
            </table>
            <br>
          </td>
        </tr>
      </table></td>
  </tr>
  <tr> 
    <td><img src="img/bottom01.gif" width="632" height="13"></td>
  </tr>
</table>
</center>


<!-- 기타설정 -->
<!--
SKIN : 플러그인 스킨 칼라 변경 기능 - 5가지 칼라(ORIGINAL, GREEN, YELLOW, RED,PURPLE)
HPP : 컨텐츠 또는 실물 결제 여부에 따라 HPP(1)과 HPP(2)중 선택 적용(HPP(1):컨텐츠, HPP(2):실물).
Card(0): 신용카드 지불시에 이니시스 대표 가맹점인 경우에 필수적으로 세팅 필요 ( 자체 가맹점인 경우에는 카드사의 계약에 따라 설정) - 자세한 내용은 메뉴얼  참조.
OCB : OK CASH BAG 가맹점으로 신용카드 결제시에 OK CASH BAG 적립을 적용하시기 원하시면 "OCB" 세팅 필요 그 외에 경우에는 삭제해야 정상적인 결제 이루어짐.
no_receipt : 은행계좌이체시 현금영수증 발행여부 체크박스 비활성화 (현금영수증 발급 계약이 되어 있어야 사용가능)
-->
<input type=hidden name=acceptmethod value="SKIN(ORIGINAL):HPP(1)">
<input type=hidden name=currency value="WON">


<!--
상점 주문번호 : 무통장입금 예약(가상계좌 이체),전화결재 관련 필수필드로 반드시 상점의 주문번호를 페이지에 추가해야 합니다.
결제수단 중에 은행 계좌이체 이용 시에는 주문 번호가 결제결과를 조회하는 기준 필드가 됩니다.
상점 주문번호는 최대 40 BYTE 길이입니다.
## 주의:절대 한글값을 입력하시면 안됩니다. ##
-->
<input type=hidden name=oid size=40 value="orderid_of_merchant">



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
<input type=hidden name=ini_encfield value="<%=ini_encfield%>">
<input type=hidden name=ini_certid value="<%=ini_certid%>">
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
</form>
</body>
</html>
<%
} else {

 // 암호화 비정상처리시 페이지 출력
%>
<html>    
<head>
<title>INIpay50 데모</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="css/group.css" type="text/css">
<style>
body, tr, td {font-size:10pt; font-family:굴림,verdana; color:#433F37; line-height:19px;}
table, img {border:none}

/* Padding ******/ 
.pl_01 {padding:1 10 0 10; line-height:19px;}
.pl_03 {font-size:20pt; font-family:굴림,verdana; color:#FFFFFF; line-height:29px;}

/* Link ******/ 
.a:link  {font-size:9pt; color:#333333; text-decoration:none}
.a:visited { font-size:9pt; color:#333333; text-decoration:none}
.a:hover  {font-size:9pt; color:#0174CD; text-decoration:underline}

.txt_03a:link  {font-size: 8pt;line-height:18px;color:#333333; text-decoration:none}
.txt_03a:visited {font-size: 8pt;line-height:18px;color:#333333; text-decoration:none}
.txt_03a:hover  {font-size: 8pt;line-height:18px;color:#EC5900; text-decoration:underline}
</style>
</head>
<body>
<center>
<table align='center' width="70%">
    <tr>
        <td>암호화 필수항목 누락 및 비정규 형식에 따른 에러 발생입니다. 확인 바랍니다.<br/><%=rn_resultMsg%>
        </td>
    </tr>
</table>
</center>
</body>
</html>
<%
}
%>
