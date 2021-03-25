<%-----------------------------------------------------
 * INIcancel.jsp
 *
 * Date : 2007/09
 * Author : ts@inicis.com
 * Project : INIpay V5.0 for JAVA(JSP)
 * 
 * http://www.inicis.com
 * Copyright (C) 2007 Inicis, Co. All rights reserved.
-------------------------------------------------------%>
<%@ page language = "java" contentType = "text/html; charset=euc-kr" %>
<%-- 
     ***************************************
     * 1. INIpay 라이브러리                * 
     *************************************** 
--%>
<%@ page import = "com.inicis.inipay.INIpay" %>
<%
    request.setCharacterEncoding("euc-kr");
	/***************************************
	 * 2. INIpay 클래스의 인스턴스 생성 *
	 ***************************************/
	INIpay inipay = new INIpay();
	
	/*********************
	 * 3. 취소 정보 설정 *
	 *********************/
  inipay.SetField("inipayhome", "/usr/local/INIpay50");  // 이니페이 홈디렉터리(상점수정 필요)
  inipay.SetField("type", "cancel");                            // 고정 (절대 수정 불가)
  inipay.SetField("debug", "true");                             // 로그모드("true"로 설정하면 상세로그가 생성됨.)
  inipay.SetField("mid", request.getParameter("mid") );         // 상점아이디
  inipay.SetField("admin", "1111");                             //상점 키패스워드 (비대칭키)
  inipay.SetField("cancelreason", request.getParameter("cancelreason") );   // 현금영수증 취소코드
  //***********************************************************************************************************
  //* admin 은 키패스워드 변수명입니다. 수정하시면 안됩니다. 1111의 부분만 수정해서 사용하시기 바랍니다.      *
  //* 키패스워드는 상점관리자 페이지(https://iniweb.inicis.com)의 비밀번호가 아닙니다. 주의해 주시기 바랍니다.*
  //* 키패스워드는 숫자 4자리로만 구성됩니다. 이 값은 키파일 발급시 결정됩니다.                               *
  //* 키패스워드 값을 확인하시려면 상점측에 발급된 키파일 안의 readme.txt 파일을 참조해 주십시오.             *
  //***********************************************************************************************************
  inipay.SetField("tid", request.getParameter("tid") );         // 취소할 거래의 거래아이디
  inipay.SetField("cancelmsg", request.getParameter("msg") );   // 취소사유
  inipay.SetField("crypto", "execure");						    // Extrus 암호화모듈 사용(고정)

	/****************
	 * 4. 취소 요청 *
	 ****************/
	inipay.startAction();
	
	
	/****************************************************************
	 * 5. 취소 결과
	 *
	 * 결과코드 : inipay.GetResult("ResultCode") ("00"이면 취소 성공)
	 * 결과내용 : inipay.GetResult("ResultMsg") (취소결과에 대한 설명)
	 * 취소날짜 : inipay.GetResult("CancelDate") (YYYYMMDD)
	 * 취소시각 : inipay.GetResult("CancelTime") (HHMMSS)
	 * 현금영수증 취소 승인번호 : inipay.GetResult("CSHR_CancelNum")
	 * (현금영수증 발급 취소시에만 리턴됨)
	 ****************************************************************/
%>

<html>
<head>
<title>INIpayTX50 취소요청 페이지 샘플</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="css/group.css" type="text/css">
<style>
body, tr, td {font-size:9pt; font-family:굴림,verdana; color:#433F37; line-height:19px;}
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

<script language="JavaScript" type="text/JavaScript">
<!--
function MM_reloadPage(init) {  //reloads the window if Nav4 resized
  if (init==true) with (navigator) {if ((appName=="Netscape")&&(parseInt(appVersion)==4)) {
    document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=MM_reloadPage; }}
  else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) location.reload();
}
MM_reloadPage(true);
//-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#242424" leftmargin=0 topmargin=15 marginwidth=0 marginheight=0 bottommargin=0 rightmargin=0><center> 
<table width="632" border="0" cellspacing="0" cellpadding="0">
  <tr> 
<%
    // 지불 수단에 따라 상단 이미지가 변경된다.
   	String background_img = "img/spool_top.gif";    //default image
   	if ("01".equals(inipay.GetResult("ResultCode") ) )
   	{
   	    background_img = "img/cancle_top.gif";
   	}
%>
    <td height="83" background="<%=background_img%>"style="padding:0 0 0 64">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="3%" valign="top"><img src="img/title_01.gif" width="8" height="27" vspace="5"></td>
          <td width="97%" height="40" class="pl_03"><font color="#FFFFFF"><b>취소결과</b></font></td>
        </tr>
      </table></td>
  </tr>
  <tr> 
    <td align="center" bgcolor="6095BC"><table width="620" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td bgcolor="#FFFFFF" style="padding:0 0 0 56">
		  <table width="510" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="7"><img src="img/life.gif" width="7" height="30"></td>
                <td background="img/center.gif"><img src="img/icon03.gif" width="12" height="10"> 
                  <b>고객님께서 이니페이를 통해 취소하신 내용입니다. </b></td>
                <td width="8"><img src="img/right.gif" width="8" height="30"></td>
              </tr>
            </table>
            <br>
            <table width="510" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="407"  style="padding:0 0 0 9"><img src="img/icon.gif" width="10" height="11"> 
                  <strong><font color="433F37">취소내역</font></strong></td>
                <td width="103">&nbsp;</td>
              </tr>
              <tr> 
                <td colspan="2"  style="padding:0 0 0 23">
		  <table width="470" border="0" cellspacing="0" cellpadding="0">
                    
                    <tr> 
                      <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                      <td width="109" height="26">결 과 코 드</td>
                      <td width="343"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><%=inipay.GetResult("ResultCode")%></td>
                            <td width='142' align='right'>&nbsp;</td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    </tr>
                    <tr> 
                      <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                      <td width="109" height="25">결 과 내 용</td>
                      <td width="343"><%=inipay.GetResult("ResultMsg")%></td>
                    </tr>
                    <tr> 
                      <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    </tr>
                    <tr> 
                      <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                      <td width="109" height="25">거 래 번 호</td>
                      <td width="343"><%=request.getParameter("tid")%></td>
                    </tr>
                    <tr> 
                      <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    </tr>
                    <tr> 
                      <td width='18' align='center'><img src='img/icon02.gif' width='7' height='7'></td>
                      <td width='109' height='25'>취 소 날 짜</td>
                      <td width='343'><%=inipay.GetResult("CancelDate")%></td>
                    </tr>                	    
                    <tr> 
                      <td height='1' colspan='3' align='center'  background='img/line.gif'></td>
                    </tr>
                    <tr> 
                      <td width='18' align='center'><img src='img/icon02.gif' width='7' height='7'></td>
                      <td width='109' height='25'>취 소 시 각</td>
                      <td width='343'><%=inipay.GetResult("CancelTime")%></td>
                    </tr>
                    <tr> 
                      <td height='1' colspan='3' align='center'  background='img/line.gif'></td>
                    </tr>                    
                    <tr> 
                      <td width='18' align='center'><img src='img/icon02.gif' width='7' height='7'></td>
                      <td width='109' height='25'>현금영수증<br>취소승인번호</td>
                      <td width='343'><%=inipay.GetResult("CSHR_CancelNum")%></td>
                    </tr>
                    <tr> 
                      <td height='1' colspan='3' align='center'  background='img/line.gif'></td>
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
</center></body>
</html>
