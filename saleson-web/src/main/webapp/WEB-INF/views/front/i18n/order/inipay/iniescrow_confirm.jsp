<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>

<html>
<head>
<title>이니에스크로 구매확인</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<meta http-equiv="Cache-Control" content="no-cache"/> 
<meta http-equiv="Expires" content="0"/> 
<meta http-equiv="Pragma" content="no-cache"/>
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>

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

<script language=javascript src="http://plugin.inicis.com/pay60_escrow.js"></script>
<script language=javascript src="https://code.jquery.com/jquery-2.2.4.min.js"></script>
<script language="Javascript">
// 플러그인 설치(확인)
StartSmartUpdate();

function f_check(){
	if(document.all.tid.value == ""){
		alert("거래번호가 빠졌습니다.")
		return;
	}
	if(document.all.mid.value == ""){
		alert("상점아이디(mid)가 빠졌습니다.")
		return;
	}
}


var openwin;

function pay(frm)
{	
  	// 필드 체크
  	f_check();
  
	// MakePayMessage()를 호출함으로써 플러그인이 화면에 나타나며, Hidden Field
	// 에 값들이 채워지게 됩니다. 플러그인은 통신을 하는 것이 아니라, Hidden
	// Field의 값들을 채우고 종료한다는 사실에 유의하십시오.
	
	if(document.ini.clickcontrol.value == "enable")
	{
		if(document.INIpay==null||document.INIpay.object==null)
		{
			if (navigator.appName == "Microsoft Internet Explorer")
				alert("플러그인을 설치 후 다시 시도 하십시오.");
			else
				alert("이니시스 에스크로 Plugin에서 해당 브라우저를 지원하지 않습니다.\n\nInternet Explorer에서 다시 시도 하십시오.");
			return false;
		}
		else
		{			
			if (MakePayMessage(frm))
			{							
				return true;
			}
			else
			{
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

</head>

<!-----------------------------------------------------------------------------------------------------
※ 주의 ※
 아래의 body TAG의 내용중에 
 onload="javascript:enable_click();" onFocus="javascript:focus_control()" 이 부분은 수정없이 그대로 사용.
 아래의 form TAG내용도 수정없이 그대로 사용.
------------------------------------------------------------------------------------------------------->
<body bgcolor="#FFFFFF" text="#242424" leftmargin=0 topmargin=15 marginwidth=0 marginheight=0 bottommargin=0 rightmargin=0 onload="javascript:enable_click();" onFocus="javascript:focus_control()"><center>
<!-- 구매확인을 위한 폼 : 이름 변경 불가 -->
<!-- pay()가 "true"를 반환하면 post된다 -->
<form id="ini" name=ini method=post action="${op:property('saleson.url.shoppingmall')}/mypage/INIescrow_result" onSubmit="return pay(this)">
<table width="632" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td height="85" background="/content/img/card.gif" style="padding:0 0 0 64">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="3%" valign="top"><img src="/content/img/title_01.gif" width="8" height="27" vspace="5"></td>
          <td width="97%" height="40" class="pl_03"><font color="#FFFFFF"><b>INICIS ESCROW 구매확인</b></font></td>
        </tr>
      </table></td>
  </tr>
  <tr> 
    <td align="center" bgcolor="6095BC"><table width="620" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td bgcolor="#FFFFFF" style="padding:8 0 0 56"> 
            <table width="510" border="0" cellspacing="0" cellpadding="0">  
              <tr> 
                <td width="7"><img src="/content/img/life.gif" width="7" height="30"></td>
                <td background="/content/img/center.gif" align="center"> 
                  <b>해당 상품을 구매확정 하시겠습니까?</b></td>
                <td width="8"><img src="/content/img/right.gif" width="8" height="30"></td>
              </tr>              
              <tr><td><br></td></tr>
              <tr>              
                <td colspan="2" align="center" style="font-size: 12px;">
	                <font color=gray>
	                	주문하신 상품의 최종구입을 확정 지으시려면 아래버튼을 눌러 주세요. 
	                </font>
                </td>
              </tr>
            </table>            
            <table width="510" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="510" colspan="2"  style="padding:0 0 0 23"> 
                  <table width="470" border="0" cellspacing="0" cellpadding="0">
                    <tr valign="bottom"> 
                      <td height="40" colspan="3" align="center">
                        <input type="submit" value="구매 확정">
                      </td>                      
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
    <td><img src="/content/img/bottom01.gif" width="632" height="13"></td>
  </tr>
</table>
</center>
<!-- 
상점아이디.
테스트를 마친 후, 발급받은 아이디로 바꾸어 주십시오.
-->
<input type=hidden name=tid value="${orderParam.pgKey}">
<input type=hidden name=mid value="${op:property('pg.inipay.escrow.mid') }"/>					
<input type=hidden name=paymethod value="">
<input type=hidden name=encrypted value="">
<input type=hidden name=sessionkey value="">
<input type=hidden name=version value=5000>
<input type=hidden name=clickcontrol value="">
<input type=hidden name=acceptmethod value=" ">
<input type=hidden name=orderCode value="${orderParam.orderCode}">
<input type=hidden name=orderSequence value="${orderParam.orderSequence}">
<input type=hidden name=itemSequence value="${orderParam.itemSequence}">

<div>
  <input type="hidden" name="_csrf" value="${_csrf.token}">
</div>

</form>
</body>
</html>                                                                                                                                                                                                                                                                                                                                                                                                                                                              
