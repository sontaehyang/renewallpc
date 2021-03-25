<%@ page
	language = "java"
	contentType = "text/html; charset=euc-kr"
	import = "com.inicis.inipay.*"
%>

<% 
  request.setCharacterEncoding("euc-kr");
	/**************************************
	 * 1. INIpay Ŭ������ �ν��Ͻ� ���� *
	 **************************************/
	INIpay50 inipay = new INIpay50();
	
	
	 
	/*********************
	 * 2. �߱� ���� ���� *
	 *********************/
	inipay.SetField("inipayhome", "/usr/local/INIpay50");  // �̴����� Ȩ���͸�(�������� �ʿ�)
	inipay.SetField("mid", request.getParameter("mid"));          // �������̵�
	inipay.SetField("admin", "1111");                             // Ű�н�����(�������̵� ���� ����)
  //***********************************************************************************************************
  //* admin �� Ű�н����� �������Դϴ�. �����Ͻø� �ȵ˴ϴ�. 1111�� �κи� �����ؼ� ����Ͻñ� �ٶ��ϴ�.      *
  //* Ű�н������ ���������� ������(https://iniweb.inicis.com)�� ��й�ȣ�� �ƴմϴ�. ������ �ֽñ� �ٶ��ϴ�.*
  //* Ű�н������ ���� 4�ڸ��θ� �����˴ϴ�. �� ���� Ű���� �߱޽� �����˴ϴ�.                               *
  //* Ű�н����� ���� Ȯ���Ͻ÷��� �������� �߱޵� Ű���� ���� readme.txt ������ ������ �ֽʽÿ�.             *
  //***********************************************************************************************************
	inipay.SetField("type","receipt");                            // ����
	inipay.SetField("paymethod","CASH");                          // ����(��û�з�)
	inipay.SetField("debug", "true");                             // �α׸��("true"�� �����ϸ� �󼼷αװ� ������.)
  inipay.SetField("crypto", "execure");						              // Extrus ��ȣȭ��� ���(����)

	inipay.SetField("currency", request.getParameter("currency"));     // ȭ����� (����)
	inipay.SetField("goodname", request.getParameter("goodname"));     // ��ǰ��
	inipay.SetField("price", request.getParameter("cr_price"));        // �� ���ݰ��� �ݾ�
	inipay.SetField("sup_price", request.getParameter("sup_price"));   // ���ް���
	inipay.SetField("tax", request.getParameter("tax"));               // �ΰ���
	inipay.SetField("srvc_price", request.getParameter("srvc_price")); // �����
	inipay.SetField("reg_num", request.getParameter("reg_num"));       // ���ݰ����� �ֹε�Ϲ�ȣ	
	inipay.SetField("useopt", request.getParameter("useopt"));         // ���ݿ����� ����뵵 ("0" - �Һ��� �ҵ������, "1" - ����� ����������)

	inipay.SetField("buyername", request.getParameter("buyername"));   // ������ ����
	inipay.SetField("buyeremail", request.getParameter("buyeremail")); // ������ �̸��� �ּ�
	inipay.SetField("buyertel", request.getParameter("buyertel"));     // ������ ��ȭ��ȣ
	
	/****************
	 * 3. ���� ��û *
	 ****************/
	inipay.startAction();

	
	
	/*****************************************************************
	 * 4. �߱� ���                           	                 *
	 ****************************************************************/
	 
	 String tid          = inipay.GetResult("tid");               // �ŷ���ȣ
	 String resultcode   = inipay.GetResult("ResultCode");        // ���ݿ����� ���� ����ڵ� (4�ڸ� - "0000"�̸� �߱޼���)
	 String resultMsg    = inipay.GetResult("ResultMsg");         // ������� (�������� ���� ����)
	 String payMethod    = inipay.GetResult("paymethod");         // ���ҹ�� (�Ŵ��� ����)
	 String applnum      = inipay.GetResult("ApplNum");           // ���ݿ����� ���� ���� ��ȣ
	 String appldate     = inipay.GetResult("ApplDate");          // �̴Ͻý� ���γ�¥ (YYYYMMDD)
	 String appltime     = inipay.GetResult("ApplTime");          // �̴Ͻý� ���νð� (HHMMSS)
	 String cshr_price   = inipay.GetResult("CSHR_ApplPrice");    // �����ݰ��� �ݾ� ( or "TotPrice")
	 String rsup_price   = inipay.GetResult("CSHR_SupplyPrice");  // ���ް�
	 String rtax         = inipay.GetResult("CSHR_Tax");          // �ΰ���
	 String rsrvc_price  = inipay.GetResult("CSHR_ServicePrice"); // �����
	 String rshr_type    = inipay.GetResult("CSHR_Type");         // ���ݿ����� ��뱸��
	 
	 
%>

<html>
<head>
<title>INIpay50 ���ݿ����� ���� ����</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="css/group.css" type="text/css">
<style>
body, tr, td {font-size:9pt; font-family:����,verdana; color:#433F37; line-height:19px;}
table, img {border:none}

/* Padding ******/ 
.pl_01 {padding:1 10 0 10; line-height:19px;}
.pl_03 {font-size:20pt; font-family:����,verdana; color:#FFFFFF; line-height:29px;}

/* Link ******/ 
.a:link  {font-size:9pt; color:#333333; text-decoration:none}
.a:visited { font-size:9pt; color:#333333; text-decoration:none}
.a:hover  {font-size:9pt; color:#0174CD; text-decoration:underline}

.txt_03a:link  {font-size: 8pt;line-height:18px;color:#333333; text-decoration:none}
.txt_03a:visited {font-size: 8pt;line-height:18px;color:#333333; text-decoration:none}
.txt_03a:hover  {font-size: 8pt;line-height:18px;color:#EC5900; text-decoration:underline}
</style>
<script>
	var openwin=window.open("childwin.html","childwin","width=299,height=149");
	openwin.close();
	
function showreceipt() // ���� ������ ���
{
	var showreceiptUrl = "https://iniweb.inicis.com/DefaultWebApp/mall/cr/cm/Cash_mCmReceipt.jsp?noTid=<%=tid%>" + "&clpaymethod=22";
	window.open(showreceiptUrl,"showreceipt","width=380,height=540, scrollbars=no,resizable=no");
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
//-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#242424" leftmargin=0 topmargin=15 marginwidth=0 marginheight=0 bottommargin=0 rightmargin=0><center> 
<table width="632" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td height="83" background="<% 
    					// ���Ҽ��ܿ� ���� ��� �̹����� ���� �ȴ�
    					
    				if(resultcode.equals("0000")){
					out.println("img/cash_top.gif");
				}
				else{
					out.println("img/spool_top.gif");
				}
				
				%>"style="padding:0 0 0 64">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="3%" valign="top"><img src="img/title_01.gif" width="8" height="27" vspace="5"></td>
          <td width="97%" height="40" class="pl_03"><font color="#FFFFFF"><b>���ݰ��� ������ �߱ް��</b></font></td>
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
                  <b>���Բ��� ��û�Ͻ� ���ݿ����� �߱� �����Դϴ�. </b></td>
                <td width="8"><img src="img/right.gif" width="8" height="30"></td>
              </tr>
            </table>
            <br>
            <table width="510" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="407"  style="padding:0 0 0 9"><img src="img/icon.gif" width="10" height="11"> 
                  <strong><font color="433F37">�߱޳���</font></strong></td>
                <td width="103">&nbsp;</td>
              </tr>
              <tr> 
                <td colspan="2"  style="padding:0 0 0 23">
		  <table width="470" border="0" cellspacing="0" cellpadding="0">
                    
                    <tr> 
                      <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                      <td width="109" height="26">�� �� �� ��</td>
                      <td width="343"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><%=resultcode%></td>
                            <td width='142' align='right'><a href='javascript:showreceipt();'><img src='img/button_02.gif' width='94' height='24' border='0'></a></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    </tr>
                    <tr> 
                      <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                      <td width="109" height="25">�� �� �� ��</td>
                      <td width="343"><%=resultMsg%></td>
                    </tr>
                    <tr> 
                      <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    </tr>
                    <tr> 
                      <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                      <td width="109" height="25">�� �� �� ȣ</td>
                      <td width="343"><%=tid%></td>
                    </tr>
                    <tr> 
                      <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    </tr>
                    <tr> 
                      <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                      <td width="109" height="25">�� �� �� ȣ</td>
                      <td width="343"><%=applnum%></td>
                    </tr>
                    <tr> 
                      <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    </tr>
                    <tr> 
                      <td width='18' align='center'><img src='img/icon02.gif' width='7' height='7'></td>
                      <td width='109' height='25'>�� �� �� ¥</td>
                      <td width='343'><%=appldate%></td>
                    </tr>
                	    
                    <tr> 
                      <td height='1' colspan='3' align='center'  background='img/line.gif'></td>
                    </tr>
                    <tr> 
                      <td width='18' align='center'><img src='img/icon02.gif' width='7' height='7'></td>
                      <td width='109' height='25'>�� �� �� ��</td>
                      <td width='343'><%=appltime%></td>
                    </tr>
                    <tr> 
                      <td height='1' colspan='3' align='center'  background='img/line.gif'></td>
                    </tr>
                    <tr> 
                      <td width='18' align='center'><img src='img/icon02.gif' width='7' height='7'></td>
                      <td width='109' height='25'>�� ���ݰ����ݾ�</td>
                      <td width='343'><%=cshr_price%> ��</td>
                    </tr>
                    <tr> 
                      <td height='1' colspan='3' align='center'  background='img/line.gif'></td>
                    </tr>
                    <tr> 
                      <td width='18' align='center'><img src='img/icon02.gif' width='7' height='7'></td>
                      <td width='109' height='25'>�� �� �� ��</td>
                      <td width='343'><%=rsup_price%> ��</td>
                    </tr>
                    <tr> 
                      <td height='1' colspan='3' align='center'  background='img/line.gif'></td>
                    </tr>
                    <tr> 
                      <td width='18' align='center'><img src='img/icon02.gif' width='7' height='7'></td>
                      <td width='109' height='25'>�� �� ��</td>
                      <td width='343'><%=rtax%> ��</td>
                    </tr>
                    <tr> 
                      <td height='1' colspan='3' align='center'  background='img/line.gif'></td>
                    </tr>
                    <tr> 
                      <td width='18' align='center'><img src='img/icon02.gif' width='7' height='7'></td>
                      <td width='109' height='25'>�� �� ��</td>
                      <td width='343'><%=rsrvc_price%> ��</td>
                    </tr>
                    <tr> 
                      <td height='1' colspan='3' align='center'  background='img/line.gif'></td>
                    </tr>
                    <tr> 
                      <td width='18' align='center'><img src='img/icon02.gif' width='7' height='7'></td>
                      <td width='109' height='25'>�� �� �� ��</td>
                      <td width='343'><% 
                      			if(rshr_type.trim().equals("0"))
                      			{
                      				out.println("�Һ��� �ҵ������");
                      			}else{
                      				out.println("����� ����������");
                      			}
                      		      %></td>
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
