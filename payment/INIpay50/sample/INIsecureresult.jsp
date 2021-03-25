<%------------------------------------------------------------------------------
 FILE NAME : INIsecurestart.jsp
 AUTHOR : ts@inicis.com
 DATE : 2007/08
 USE WITH : config.jsp, INIpay50.jar
 
 �̴����� �÷������� �̿��Ͽ� ������ ��û�Ѵ�.
 
 Copyright 2007 Inicis, Co. All rights reserved.
------------------------------------------------------------------------------%>

<%@ page language = "java" contentType = "text/html;charset=euc-kr" %>
<%@ page import = "java.util.Hashtable" %>
<%-- 
     ***************************************
     * 1. INIpay ���̺귯��                * 
     *************************************** 
--%>
<%@ page import = "com.inicis.inipay.*" %>
<%
  request.setCharacterEncoding("euc-kr");
  /***************************************
   * 2. INIpay �ν��Ͻ� ����             *
   ***************************************/
  INIpay50 inipay = new INIpay50();
 

  /*********************
   * 3. ���� ���� ���� *
   *********************/

  inipay.SetField("inipayhome", "/usr/local/INIpay50");  // �̴����� Ȩ���͸�(�������� �ʿ�)
  inipay.SetField("type", "securepay");  // ���� (���� ���� �Ұ�)
  inipay.SetField("admin", session.getAttribute("admin"));  // Ű�н�����(�������̵� ���� ����)
  //***********************************************************************************************************
  //* admin �� Ű�н����� �������Դϴ�. �����Ͻø� �ȵ˴ϴ�. 1111�� �κи� �����ؼ� ����Ͻñ� �ٶ��ϴ�.      *
  //* Ű�н������ ���������� ������(https://iniweb.inicis.com)�� ��й�ȣ�� �ƴմϴ�. ������ �ֽñ� �ٶ��ϴ�.*
  //* Ű�н������ ���� 4�ڸ��θ� �����˴ϴ�. �� ���� Ű���� �߱޽� �����˴ϴ�.                               *
  //* Ű�н����� ���� Ȯ���Ͻ÷��� �������� �߱޵� Ű���� ���� readme.txt ������ ������ �ֽʽÿ�.             *
  //***********************************************************************************************************
  inipay.SetField("debug", "true");  // �α׸��("true"�� �����ϸ� �󼼷αװ� ������.)
  inipay.SetField("crypto", "execure");	// Extrus ��ȣȭ��� ���(����)
	
  inipay.SetField("uid", request.getParameter("uid") );  // INIpay User ID (���� ���� �Ұ�)
  inipay.SetField("oid", request.getParameter("oid") );  // ��ǰ�� 
  inipay.SetField("goodname", request.getParameter("goodname") );  // ��ǰ�� 
  inipay.SetField("currency", request.getParameter("currency") );  // ȭ�����

  inipay.SetField("mid", session.getAttribute("INI_MID") );  // �������̵�
  inipay.SetField("enctype", session.getAttribute("INI_ENCTYPE") );  //�������� �������� ��ȣȭ ����
  inipay.SetField("rn", session.getAttribute("INI_RN") );  //�������� �������� RN��
  inipay.SetField("price", session.getAttribute("INI_PRICE") );  //����


  /**---------------------------------------------------------------------------------------
   * price ���� �߿䵥���ʹ�
   * ���������� ���������θ� �ݵ�� Ȯ���ϼž� �մϴ�.
   *
   * ���� ��û���������� ��û�� �ݾװ�
   * ���� ������ �̷���� �ݾ��� �ݵ�� ���Ͽ� ó���Ͻʽÿ�.
   *
   * ��ġ �޴��� 2���� ���� ó�������� �ۼ��κ��� ���Ȱ�� �κ��� Ȯ���Ͻñ� �ٶ��ϴ�.
   * ������������: �̴Ͻý�Ȩ������->��������������ڷ��->��Ÿ�ڷ�� ��
   *              '���� ó�� ������ �� ���� �ݾ� ���� ������ ���� üũ' ������ �����Ͻñ� �ٶ��ϴ�.
   * ����)
   * �� ��ǰ ���� ������ OriginalPrice �ϰ�  �� ���� ������ �����ϴ� �Լ��� Return_OrgPrice()�� �����ϸ�
   * ���� ���� �����Ͽ� �����ݰ� ������������ Post�Ǿ� �Ѿ�� ������ �� �Ѵ�.
   *
		String originalPrice = merchant.getOriginalPrice();
		String postPrice = inipay.GetResult("price"); 
		if ( originalPrice != postPrice )
		{
			//���� ������ �ߴ��ϰ�  �ݾ� ���� ���ɼ��� ���� �޽��� ��� ó��
			//ó�� ���� 
		}
  ---------------------------------------------------------------------------------------**/

  inipay.SetField("paymethod", request.getParameter("paymethod") );			          // ���ҹ�� (���� ���� �Ұ�)
  inipay.SetField("encrypted", request.getParameter("encrypted") );			          // ��ȣ��
  inipay.SetField("sessionkey",request.getParameter("sessionkey") );			        // ��ȣ��
  inipay.SetField("buyername", request.getParameter("buyername") );			          // ������ ��
  inipay.SetField("buyertel", request.getParameter("buyertel") );			            // ������ ����ó(�޴��� ��ȣ �Ǵ� ������ȭ��ȣ)
  inipay.SetField("buyeremail",request.getParameter("buyeremail") );			        // ������ �̸��� �ּ�
  inipay.SetField("url", "http://www.your_domain.co.kr" ); 	                      // ���� ���񽺵Ǵ� ���� SITE URL�� �����Ұ�
  inipay.SetField("cardcode", request.getParameter("cardcode") ); 	          		// ī���ڵ� ����
  inipay.SetField("parentemail", request.getParameter("parentemail") ); 			    // ��ȣ�� �̸��� �ּ�(�ڵ��� , ��ȭ�����ÿ� 14�� �̸��� ���� �����ϸ�  �θ� �̸��Ϸ� ���� �����뺸 �ǹ�, �ٸ����� ���� ���ÿ� ���� ����)
	
  /*-----------------------------------------------------------------*
   * ������ ���� *                                                   *
   *-----------------------------------------------------------------*
   * �ǹ������ �ϴ� ������ ��쿡 ���Ǵ� �ʵ���̸�               *
   * �Ʒ��� ������ INIsecurestart.jsp ���������� ����Ʈ �ǵ���        *
   * �ʵ带 ����� �ֵ��� �Ͻʽÿ�.                                  *
   * ������ ������ü�� ��� �����ϼŵ� �����մϴ�.                   *
   *-----------------------------------------------------------------*/
  inipay.SetField("recvname",request.getParameter("recvname") );	// ������ ��
  inipay.SetField("recvtel",request.getParameter("recvtel") );		// ������ ����ó
  inipay.SetField("recvaddr",request.getParameter("recvaddr") );	// ������ �ּ�
  inipay.SetField("recvpostnum",request.getParameter("recvpostnum") );  // ������ �����ȣ
  inipay.SetField("recvmsg",request.getParameter("recvmsg") );		// ���� �޼���
	
  inipay.SetField("joincard",request.getParameter("joincard") );        // ����ī���ڵ�
  inipay.SetField("joinexpire",request.getParameter("joinexpire") );    // ����ī����ȿ�Ⱓ
  inipay.SetField("id_customer",request.getParameter("id_customer") );  // �Ϲ����� ��� ������� ����, user_id


  /****************
   * 4. ���� ��û *
   ****************/ 
  inipay.startAction();
	
  //Get PG Added Entity Sample
  if(inipay.GetResult("ResultCode").equals("00") )
  {
  }
	
  /*****************
   * 5. ����  ��� *
   *****************/
  /*****************************************************************************************************************
   *  1 ��� ���� ���ܿ� ����Ǵ� ���� ��� ������
   * 	�ŷ���ȣ : inipay.GetResult("tid")
   * 	����ڵ� : inipay.GetResult("ResultCode") ("00"�̸� ���� ����)
   * 	������� : inipay.GetResult("ResultMsg") (���Ұ���� ���� ����)
   * 	���ҹ�� : inipay.GetResult("PayMethod") (�Ŵ��� ����)
   * 	�����ֹ���ȣ : inipay.GetResult("MOID")
   *	�����Ϸ�ݾ� : inipay.GetResult("TotPrice")
   * 	�̴Ͻý� ���γ�¥ : inipay.GetResult("ApplDate") (YYYYMMDD)
   * 	�̴Ͻý� ���νð� : inipay.GetResult("ApplTime") (HHMMSS)  
   *
   *
   * ���� �Ǵ� �ݾ� =>����ǰ���ݰ�  ��������ݾװ� ���Ͽ� �ݾ��� �������� �ʴٸ�
   * ���� �ݾ��� �������� �ǽɵ����� �������� ó���� �����ʵ��� ó�� �ٶ��ϴ�. (�ش� �ŷ� ��� ó��)
   *
   *  2. �Ϻ� ���� ���ܿ��� �������� ���� ����,
   *     OCB Point/VBank �� ������ ���Ҽ��ܿ� ��� ����.
   * 	���ι�ȣ : inipay.GetResult("ApplNum") 
   *
   *
   *  3. �ſ�ī�� ���� ��� ������ (Card, VCard ����)
   * 	�ҺαⰣ : inipay.GetResult("CARD_Quota")
   * 	�������Һ� ���� : inipay.GetResult("CARD_Interest") ("1"�̸� �������Һ�), 
   *                    �Ǵ� inipay.GetResult("EventCode") (������/���� ������� ����, ���� ���� ������ �޴��� ����)
   * 	�ſ�ī��� �ڵ� : inipay.GetResult("CARD_Code") (�Ŵ��� ����)
   * 	ī��߱޻� �ڵ� : inipay.GetResult("CARD_BankCode") (�Ŵ��� ����)
   * 	�������� ���࿩�� : inipay.GetResult("CARD_AuthType") ("00"�̸� ����)
   *  ���� �̺�Ʈ ���� ���� : inipay.GetResult("EventCode")
   *
   *
   *      ** �޷����� �� ��ȭ�ڵ��  ȯ�� ���� **
   *	�ش� ��ȭ�ڵ� : inipay.GetResult("OrgCurrency")
   *	ȯ�� : inipay.GetResult("ExchangeRate")
   *
   *      �Ʒ��� "�ſ�ī�� �� OK CASH BAG ���հ���" �Ǵ�"�ſ�ī�� ���ҽÿ� OK CASH BAG����"�ÿ� �߰��Ǵ� ������
   * 	OK Cashbag ���� ���ι�ȣ : inipay.GetResult("OCB_SaveApplNum")
   * 	OK Cashbag ��� ���ι�ȣ : inipay.GetResult("OCB_PayApplNum")
   * 	OK Cashbag �����Ͻ� : inipay.GetResult("OCB_ApplDate") (YYYYMMDDHHMMSS)
   * 	OCB ī���ȣ : inipay.GetResult("OCB_Num")
   * 	OK Cashbag ���հ���� �ſ�ī�� ���ұݾ� : inipay.GetResult("CARD_ApplPrice")
   * 	OK Cashbag ���հ���� ����Ʈ ���ұݾ� : inipay.GetResult("OCB_PayPrice")
   *
   * 4. �ǽð� ������ü ���� ��� ������
   *
   * 	�����ڵ� : inipay.GetResult("ACCT_BankCode")
   *	���ݿ����� �������ڵ� : inipay.GetResult("CSHR_ResultCode")
   *	���ݿ����� ���౸���ڵ� : inipay.GetResult("CSHR_Type")
   *
   * 5. OK CASH BAG ���������� �̿�ÿ���  ���� ��� ������
   * 	OK Cashbag ���� ���ι�ȣ : inipay.GetResult("OCB_SaveApplNum")
   * 	OK Cashbag ��� ���ι�ȣ : inipay.GetResult("OCB_PayApplNum")
   * 	OK Cashbag �����Ͻ� : inipay.GetResult("OCB_ApplDate") (YYYYMMDDHHMMSS)
   * 	OCB ī���ȣ : inipay.GetResult("OCB_Num")
   *
   * 6. ������ �Ա� ���� ��� ������
   * 	������� ä���� ���� �ֹι�ȣ : inipay.GetResult("VACT_RegNum")
   * 	������� ��ȣ : inipay.GetResult("VACT_Num")
   * 	�Ա��� ���� �ڵ� : inipay.GetResult("VACT_BankCode")
   * 	�Աݿ����� : inipay.GetResult("VACT_Date") (YYYYMMDD)
   * 	�۱��� �� : inipay.GetResult("VACT_InputName")
   * 	������ �� : inipay.GetResult("VACT_Name")
   *
   * 7. �ڵ���, ��ȭ ���� ��� ������( "���� ���� �ڼ��� ����"���� �ʿ� , ���������� �ʿ���� ������)
   * 	��ȭ���� ����� �ڵ� : inipay.GetResult("HPP_GWCode")
   *
   * 8. �ڵ��� ���� ��� ������
   * 	�޴��� ��ȣ : inipay.GetResult("HPP_Num") (�ڵ��� ������ ���� �޴�����ȣ)
   *
   * 9. ��ȭ ���� ��� ������
   * 	��ȭ��ȣ : inipay.GetResult("ARSB_Num") (��ȭ������  ���� ��ȭ��ȣ)
   *
   * 10. ��ȭ ��ǰ�� ���� ��� ������
   * 	���� ���� ID : inipay.GetResult("CULT_UserID")
   *
   * 11. ���ݿ����� �߱� ����ڵ� (���������ü�ÿ��� ����)
   *    inipay.GetResult("CSHR_ResultCode")
   *
   * 12.ƾĳ�� �ܾ� ������
   *    inipay.GetResult("TEEN_Remains")
   *  ƾĳ�� ID : inipay.GetResult("TEEN_UserID")
   *
   * 13.����Ʈ���� ��ǰ��
   *	��� ī�� ���� : inipay.GetResult("GAMG_Cnt")
   *
   * 14.������ȭ ��ǰ��
   *	����� ID : inipay.GetResult("BCSH_UserID")
   *
   ****************************************************************************************************************/


  /*******************************************************************
   * 7. DB���� ���� �� �������                                      *
   *                                                                 *
   * ���� ����� DB � �����ϰų� ��Ÿ �۾��� �����ϴٰ� �����ϴ�  *
   * ���, �Ʒ��� �ڵ带 �����Ͽ� �̹� ���ҵ� �ŷ��� ����ϴ� �ڵ带 *
   * �ۼ��մϴ�.                                                     *
   *******************************************************************/
  /*
  boolean cancelFlag = false;
  // cancelFlag�� "ture"�� �����ϴ� condition �Ǵ��� ����������
  // �����Ͽ� �ֽʽÿ�.

  if(cancelFlag)
  {
    String tmp_TID = inipay.GetResult("tid");
    inipay.SetField("type", "cancel");         // ����
    inipay.SetField("tid", tmp_TID);              // ����
    inipay.SetField("cancelmsg", "DB FAIL");   // ��һ���
    inipay.startAction();
  }
  */


%>
<!-------------------------------------------------------------------------------------------------------
 *
 *
 *
 *	�Ʒ� ������ ���� ����� ���� ��� ������ �����Դϴ�.
 *
 *
 *
 -------------------------------------------------------------------------------------------------------->
 
<html>
<head>
<title>INIpay50 ���������� ����</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="css/group.css" type="text/css">
<style>
body, tr, td {font-size:10pt; font-family:����,verdana; color:#433F37; line-height:19px;}
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
	
	function show_receipt(tid) // ������ ���
	{
		if(<%=inipay.GetResult("ResultCode")%> == "00")
		{
			var receiptUrl = "https://iniweb.inicis.com/DefaultWebApp/mall/cr/cm/mCmReceipt_head.jsp?noTid=<%=inipay.GetResult("tid")%>&noMethod=1";
			window.open(receiptUrl,"receipt","width=430,height=700");
		}
		else
		{
			alert("�ش��ϴ� ���������� �����ϴ�");
		}
	}
		
	function errhelp() // �� �������� ���
	{
		var errhelpUrl = "http://www.inicis.com/ErrCode/Error.jsp?result_err_code=<%=inipay.GetResult("ResultErrorCode")%>&mid=<%=inipay.GetResult("MID")%>&tid=<%=inipay.GetResult("tid")%>&goodname=<%=inipay.GetResult("goodname")%>&price=<%=inipay.GetResult("price")%>&paymethod=<%=inipay.GetResult("PayMethod")%>&buyername=<%=inipay.GetResult("buyerName")%>&buyertel=<%=inipay.GetResult("buyertel")%>&buyeremail=<%=inipay.GetResult("buyeremail")%>&codegw=<%=inipay.GetResult("HPP_GWCode")%>";
		window.open(errhelpUrl,"errhelp","width=520,height=150, scrollbars=yes,resizable=yes");
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
<% 

/*-------------------------------------------------------------------------------------------------------
 * ���� ����� ���� ��� �̹����� ���� �ȴ�								*
 * 	 ��. ���� ���� �ÿ� "img/spool_top.gif" �̹��� ���						*
 *       ��. ���� ����� ���� ��� �̹����� ����							*
 *       	1. �ſ�ī�� 	- 	"img/card.gif"							*
 *		2. ISP		-	"img/card.gif"							*
 *		3. �������	-	"img/bank.gif"							*
 *		4. �������Ա�	-	"img/bank.gif"							*
 *		5. �ڵ���	- 	"img/hpp.gif"							*
 *		6. ��ȭ���� (ars��ȭ ����)	-	"img/phone.gif"					*
 *		7. ��ȭ���� (�޴���ȭ����)	-	"img/phone.gif"					*
 *		8. OK CASH BAG POINT		-	"img/okcash.gif"				*
 *		9. ��ȭ��ǰ��		-	"img/ticket.gif"					*
 *              10. K-merce ��ǰ�� 	- 	"img/kmerce.gif"                                        *
 *		11. ƾĳ�� ����		- 	"img/teen_top.gif"                                      *
 *              12. ����Ʈ����    -       "img/dgcl_top.gif"                                       *
 -------------------------------------------------------------------------------------------------------*/
String background_img = "";
if(inipay.GetResult("ResultCode").equals("01")) {
				background_img = "img/spool_top.gif";
}
else{
    Hashtable data_bgrImg = new Hashtable();
		background_img = "img/card.gif";    //default image

    try{
      data_bgrImg.put("Card","img/card.gif");  //�ſ�ī��
      data_bgrImg.put("VCard","img/card.gif"); //ISP
      data_bgrImg.put("HPP","img/hpp.gif");  //�޴���
      data_bgrImg.put("Ars1588Bill","img/phone.gif");  //1588
      data_bgrImg.put("PhoneBill","img/phone.gif");// ����
      data_bgrImg.put("OCBPoint","img/okcash.gif");// OKCASHBAG
      data_bgrImg.put("DirectBank","img/bank.gif");// ���������ü
      data_bgrImg.put("VBank","img/bank.gif");  // ������ �Ա� ����
      data_bgrImg.put("Culture","img/ticket.gif");// ��ȭ��ǰ�� ����
      data_bgrImg.put("TEEN","img/teen_top.gif");// ƾĳ�� ����
      data_bgrImg.put("DGCL","img/dgcl_top.gif");	// ����Ʈ����
      data_bgrImg.put("BCSH","img/ticket_top.gif");	// ������ȭ ��ǰ��
      
      Object tmp = data_bgrImg.get(inipay.GetResult("PayMethod"));
      background_img = ( tmp != null)? (String)tmp:background_img;
    }catch(Exception ex){
         // default image
    }
}
					
%>
    <td height="85" background=<%=background_img%> style="padding:0 0 0 64">
    				
<!-------------------------------------------------------------------------------------------------------
 *
 *  �Ʒ� �κ��� ��� ���������� �������� ����޼��� ��� �κ��Դϴ�.
 *
 *  1. inipay.GetResult("ResultCode")  (�� �� �� ��)
 *  2. inipay.GetResult("ResultMsg")   (��� �޼���)
 *  3. inipay.GetResult("PayMethod")   (�� �� �� ��)
 *  4. inipay.GetResult("TID")         (�� �� �� ȣ)
 *  5. inipay.GetResult("MOID")        (�� �� �� ȣ)
 -------------------------------------------------------------------------------------------------------->
 
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="3%" valign="top"><img src="img/title_01.gif" width="8" height="27" vspace="5"></td>
          <td width="97%" height="40" class="pl_03"><font color="#FFFFFF"><b>�������</b></font></td>
        </tr>
      </table></td>
  </tr>
  <tr> 
    <td align="center" bgcolor="6095BC">
      <table width="620" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td bgcolor="#FFFFFF" style="padding:0 0 0 56">
		  <table width="510" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="7"><img src="img/life.gif" width="7" height="30"></td>
                <td background="img/center.gif"><img src="img/icon03.gif" width="12" height="10">
                
                <!-------------------------------------------------------------------------------------------------------
                 * 1. inipay.GetResult("ResultCode") 										*	
                 *       ��. �� �� �� ��: "00" �� ��� ���� ����[�������Ա��� ��� - ������ �������Ա� ��û�� �Ϸ�]	*
                 *       ��. �� �� �� ��: "00"���� ���� ��� ���� ����  						*
                 --------------------------------------------------------------------------------------------------------> 
                  <b><% if(inipay.GetResult("ResultCode").equals("00") && inipay.GetResult("PayMethod").equals("VBank")) { 
                            out.write("������ �������Ա� ��û�� �Ϸ�Ǿ����ϴ�.");
                        }
                  	    else if(inipay.GetResult("ResultCode").equals("00") ) { 
                  	        out.write("������ ������û�� �����Ǿ����ϴ�.");
                  	    }
                        else{ 
                            out.write("������ ������û�� ���еǾ����ϴ�.");
                        } %> </b></td>
                <td width="8"><img src="img/right.gif" width="8" height="30"></td>
              </tr>
            </table>
            <br>
            <table width="510" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="407"  style="padding:0 0 0 9"><img src="img/icon.gif" width="10" height="11"> 
                  <strong><font color="433F37">��������</font></strong></td>
                <td width="103">&nbsp;</td>                
              </tr>
              <tr> 
                <td colspan="2"  style="padding:0 0 0 23">
		  <table width="470" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                    
                <!-------------------------------------------------------------------------------------------------------
                 * 2. inipay.GetResult("PayMethod")
                 *       ��. ���� ����� ���� ��
                 *       	1. �ſ�ī�� 	- 	Card
                 *       	2. ISP		-	VCard
                 *       	3. �������	-	DirectBank
                 *       	4. �������Ա�	-	VBank
                 *       	5. �ڵ���	- 	HPP
                 *       	6. ��ȭ���� (ars��ȭ ����)	-	Ars1588Bill
                 *       	7. ��ȭ���� (�޴���ȭ����)	-	PhoneBill
                 *       	8. OK CASH BAG POINT		-	OCBPoint
                 *       	9. ��ȭ��ǰ��			-	Culture
                 *       	10. ƾĳ�� ���� 		- 	TEEN
                 *       	11. ����Ʈ���� 		-	DGCL
                 *       	12. ������ȭ ��ǰ�� 		-	BCSH
                 *-------------------------------------------------------------------------------------------------------->
                      <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                      <td width="109" height="25">�� �� �� ��</td>
                      <td width="343"><%=inipay.GetResult("PayMethod")%></td>
                    </tr>
                    <tr> 
                      <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    </tr>
                    <tr> 
                      <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                      <td width="109" height="26">�� �� �� ��</td>
                      <td width="343"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><%=inipay.GetResult("ResultCode")%></td>
                            <td width='142' align='right'>
                          
                <!-------------------------------------------------------------------------------------------------------
                 * 3. inipay.GetResult("ResultCode") ���� ���� "������ ����" �Ǵ� "���� ���� �ڼ��� ����" ��ư ���		*
                 *       ��. ���� �ڵ��� ���� "00"�� ��쿡�� "������ ����" ��ư ���					*
                 *       ��. ���� �ڵ��� ���� "00" ���� ���� ��쿡�� "���� ���� �ڼ��� ����" ��ư ���			*
                 -------------------------------------------------------------------------------------------------------->
		<!-- ���а�� �� ���� ��ư ��� -->
                            	<%
                            		if(inipay.GetResult("ResultCode") == "00"){
                            	%>
                				            <a href='javascript:show_receipt();'><img src='img/button_02.gif' width='94' height='24' border='0'></a>
                				      <%
                           			}
                          			else{
                          		%>
                            		    <a href='javascript:errhelp();'><img src='img/button_01.gif' width='142' height='24' border='0'></a>
                            	<%
                            		}
                            	%>                    </td>
                          </tr>
                        </table></td>
                    </tr>
                
                <!-------------------------------------------------------------------------------------------------------
                 * 4. inipay.GetResult("ResultMsg") 										*
                 *    - ��� ������ ���� �ش� ���нÿ��� "[�����ڵ�] ���� �޼���" ���·� ���� �ش�.                     *
                 *		��> [9121]����Ȯ�ο���									*
                 -------------------------------------------------------------------------------------------------------->
                    <tr> 
                      <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    </tr>
                    <tr> 
                      <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                      <td width="109" height="25">�� �� �� ��</td>
                      <td width="343"><%=inipay.GetResult("ResultMsg")%></td>
                    </tr>
                    <tr> 
                      <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    </tr>
                    
                <!-------------------------------------------------------------------------------------------------------
                 * 5. inipay.GetResult("tid")											*
                 *    - �̴Ͻý��� �ο��� �ŷ� ��ȣ -��� �ŷ��� ������ �� �ִ� Ű�� �Ǵ� ��			        *
                 -------------------------------------------------------------------------------------------------------->
                    <tr> 
                      <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                      <td width="109" height="25">�� �� �� ȣ</td>
                      <td width="343"><%=inipay.GetResult("tid")%></td>
                    </tr>
                    <tr> 
                      <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    </tr>
                    
                <!-------------------------------------------------------------------------------------------------------
                 * 6. inipay.GetResult("MOID")											*
                 *    - �������� �Ҵ��� �ֹ���ȣ 									*
                 -------------------------------------------------------------------------------------------------------->
                    <tr> 
                      <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                      <td width="109" height="25">�� �� �� ȣ</td>
                      <td width="343"><%=inipay.GetResult("MOID")%></td>
                    </tr>
                    <tr> 
                      <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    </tr>
                    
                <!-------------------------------------------------------------------------------------------------------
                 * 7. inipay.GetResult("TotPrice")										*
                 *    - �����Ϸ� �ݾ�                  									*
	 			 *																					*
	 			 * ���� �Ǵ� �ݾ� =>����ǰ���ݰ�  ��������ݾװ� ���Ͽ� �ݾ��� �������� �ʴٸ�  *
	 			 * ���� �ݾ��� �������� �ǽɵ����� �������� ó���� �����ʵ��� ó�� �ٶ��ϴ�. (�ش� �ŷ� ��� ó��) *
	 			 *																									*
                 -------------------------------------------------------------------------------------------------------->
                     
                    <tr> 
                      <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                      <td width="109" height="25">�����Ϸ�ݾ�</td>
                      <td width="343"><%=inipay.GetResult("TotPrice")%> ��</td>
                    </tr>
                    <tr> 
                      <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    </tr>


<%
/*-------------------------------------------------------------------------------------------------------
	 *													*
	 *  �Ʒ� �κ��� ���� ���ܺ� ��� �޼��� ��� �κ��Դϴ�.    						*	
	 *													*
	 *  1.  �ſ�ī�� (OK CASH BAG POINT ���� ���� ���� )				*
	 -------------------------------------------------------------------------------------------------------*/

	if( "Card".equals(inipay.GetResult("PayMethod")) || "VCard".equals(inipay.GetResult("PayMethod")) ) {
		
%>
						<tr> 
                    		  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                    		  <td width="109" height="25">�ſ�ī���ȣ</td>
                    		  <td width="343"><%=inipay.GetResult("CARD_Num")%>
           </td>
                    		</tr>
                    		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    		</tr>
				<tr> 
                                  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                                  <td width="109" height="25">�� �� �� ¥</td>
                                  <td width="343"><%=(inipay.GetResult("ApplDate"))%>
                </td>
                                </tr>
                                <tr> 
                                  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                                </tr>
                                <tr> 
                                  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                                  <td width="109" height="25">�� �� �� ��</td>
                                  <td width="343"><%=(inipay.GetResult("ApplTime"))%>
               </td>
                                </tr>                	    
                    		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    		</tr>
                    		<tr> 
                    		  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                    		  <td width="109" height="25">�� �� �� ȣ</td>
                    		  <td width="343"><%=(inipay.GetResult("ApplNum"))%>
              </td>
                    		</tr>
                    		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    		</tr>
                    		<tr> 
                    		  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                    		  <td width="109" height="25">�� �� �� ��</td>
                    		  <td width="343"><%=(inipay.GetResult("CARD_Quota"))%>����&nbsp;<b>
                    		  <font color=red><% 
                      			if( "1".equals( inipay.GetResult("CARD_Interest") ) )
                      			{
                      				out.println("������");
                      			}else if ( "1".equals(inipay.GetResult("EventCode")) ) {
                      			  out.println("������ (�̴Ͻý�&ī���δ� �Ϲ� ������ �Һ� �̺�Ʈ)");
                      			}else if ( "12".equals(inipay.GetResult("EventCode")) ) {
                      			  out.println("ī���δ� �Ϲ� ������ + ���� �Ϲ� ���� �̺�Ʈ");
                      			}else if ( "14".equals(inipay.GetResult("EventCode")) ){
                      			  out.println("ī���δ� �Ϲ� ������ + ī���ȣ�� ���� �̺�Ʈ");
                      			}else if ( "24".equals(inipay.GetResult("EventCode")) ){
                      			  out.println("ī���δ� �Ϲ� ������ + ī�� Prefix�� ���� �̺�Ʈ");
                      			}else if ( "A1".equals(inipay.GetResult("EventCode")) ){
                      			  out.println("�����δ� �Ϲ� ������ �Һ� �̺�Ʈ");
                      			}else if ( "A2".equals(inipay.GetResult("EventCode")) ){
                      			  out.println("���� �Ϲ� ���� �̺�Ʈ");
                      			}else if ( "A3".equals(inipay.GetResult("EventCode")) ){
                      			  out.println("���� ������ + ���� �Ϲ� ���� �̺�Ʈ");
                      			}else if ( "A4".equals(inipay.GetResult("EventCode")) ){
                      			  out.println("���� ������ + ī���ȣ�� ���� �̺�Ʈ");
                      			}else if ( "A5".equals(inipay.GetResult("EventCode")) ){
                      			  out.println("ī���ȣ�� ���� �̺�Ʈ");
                      			}else if ( "B4".equals(inipay.GetResult("EventCode")) ){
                      			  out.println("���� ������ + ī�� Prefix�� ���� �̺�Ʈ");
                      			}else if ( "B5".equals(inipay.GetResult("EventCode")) ){
                      			  out.println("ī�� Prefix�� ���� �̺�Ʈ");
                      			}else if ( "C0".equals(inipay.GetResult("EventCode")) ){
                      			  out.println("���&ī���δ� Ư�� ������ �Һ� �̺�Ʈ");
                      			}else if ( "C1".equals(inipay.GetResult("EventCode")) ){
                      			  out.println("�����δ� Ư�� ������ �Һ� �̺�Ʈ");
                      			}else{
                      				out.println("�Ϲ�");
                      			}
                      		      %></font></b></td>
                    		</tr>
                    		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    		</tr>
                    		<tr> 
                    		  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                    		  <td width="109" height="25">ī �� �� ��</td>
                    		  <td width="343"><%=(inipay.GetResult("CARD_Code"))%>
              </td>
                    		</tr>
                    		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    		</tr>
                    		<tr> 
                    		  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                    		  <td width="109" height="25">ī��߱޻�</td>
                    		  <td width="343"><%=(inipay.GetResult("CARD_BankCode"))%>
              </td>
                    		</tr>
                    		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    		</tr>
                    		<tr> 
                    		  <td height="1" colspan="3">&nbsp;</td>
                    		</tr>
                    		<tr> 
                		  <td style="padding:0 0 0 9" colspan="3"><img src="img/icon.gif" width="10" height="11"> 
        	          	  <strong><font color="433F37">�޷����� ����</font></strong></td>
                		</tr>
                		<tr> 
                    		  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                    		  <td width="109" height="25">�� ȭ �� ��</td>
                    		  <td width="343"><%=(inipay.GetResult("OrgCurrency"))%>
             </td>
                    		</tr>
                    		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    		</tr>
                    		<tr> 
                    		  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                    		  <td width="109" height="25">ȯ    ��</td>
                    		  <td width="343"><%=(inipay.GetResult("ExchangeRate"))%>
              </td>
                    		</tr>
                    		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    		</tr>                    		
                    		<tr> 
                    		  <td height="1" colspan="3">&nbsp;</td>
                    		</tr>
                    		<tr> 
                		  <td style="padding:0 0 0 9" colspan="3"><img src="img/icon.gif" width="10" height="11"> 
        	          	  <strong><font color="433F37">OK CASHBAG ���� �� ��볻��</font></strong></td>
                		</tr>
                		<tr> 
                    		  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                    		  <td width="109" height="25">ī �� �� ȣ</td>
                    		  <td width="343"><%=(inipay.GetResult("OCB_Num"))%>
              </td>
                    		</tr>
                    		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    		</tr>
                    		<tr> 
                    		  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                    		  <td width="109" height="25">���� ���ι�ȣ</td>
                    		  <td width="343"><%=(inipay.GetResult("OCB_SaveApplNum"))%>
               </td>
                    		</tr>
                    		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    		</tr>
                    		<tr> 
                    		  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                    		  <td width="109" height="25">��� ���ι�ȣ</td>
                    		  <td width="343"><%=(inipay.GetResult("OCB_PayApplNum"))%>
               </td>
                    		</tr>
                    		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    		</tr>
                    		<tr> 
                    		  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                    		  <td width="109" height="25">�� �� �� ��</td>
                    		  <td width="343"><%=(inipay.GetResult("OCB_ApplDate"))%>
               </td>
                    		</tr>
                		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    		</tr>
                    		<tr> 
                    		  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                    		  <td width="109" height="25">����Ʈ���ұݾ�</td>
                    		  <td width="343"><%=(inipay.GetResult("OCB_PayPrice"))%>
             </td>
                    		</tr>
                    		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    		</tr>
<%
                    
          }
        
  /*-------------------------------------------------------------------------------------------------------
	 *
	 *  �Ʒ� �κ��� ���� ���ܺ� ��� �޼��� ��� �κ��Դϴ�.
	 *
	 *  2.  ������°��� ��� ���
	 -------------------------------------------------------------------------------------------------------*/
	 
          else if("DirectBank".equals(inipay.GetResult("PayMethod")) ){
%>

          			<tr> 
                                  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                                  <td width="109" height="25">�� �� �� ¥</td>
                                  <td width="343"><%=(inipay.GetResult("ApplDate"))%>
               </td>
                                </tr>
                                <tr> 
                                  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                                </tr>
                                <tr> 
                                  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                                  <td width="109" height="25">�� �� �� ��</td>
                                  <td width="343"><%=(inipay.GetResult("ApplTime"))%>
               </td>
                                </tr>
                                <tr> 
                                  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                                </tr>
                                <tr> 
                                  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                                  <td width="109" height="25">�� �� �� ��</td>
                                  <td width="343"><%=(inipay.GetResult("ACCT_BankCode"))%>
                </td>
                                </tr>
                                <tr> 
                                  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                                </tr>
                                <tr> 
                                  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                                  <td width="109" height="25">���ݿ�����<br>�߱ް���ڵ�</td>
                                  <td width="343"><%=(inipay.GetResult("CSHR_ResultCode"))%>
                </td>
                                </tr>
                                <tr> 
                                  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                                </tr>
				<tr>
                                  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                                  <td width="109" height="25">���ݿ�����<br>�߱ޱ����ڵ�</td>
                                  <td width="343"><%=(inipay.GetResult("CSHR_Type"))%>
                                    <font color=red><b>(0 - �ҵ������, 1 - ����������)</b></font></td>
                                </tr>
                                <tr>
                                  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                                </tr>
<%
          }
          
  /*-------------------------------------------------------------------------------------------------------
	 *													*
	 *  �Ʒ� �κ��� ���� ���ܺ� ��� �޼��� ��� �κ��Դϴ�.    						*	
	 *													*
	 *  3.  �������Ա� �Ա� ���� ��� ��� (���� ������ �ƴ� �Ա� ���� ���� ����)				*
	 -------------------------------------------------------------------------------------------------------*/
	 
          else if("VBank".equals(inipay.GetResult("PayMethod")) ){
%>

          			<tr> 
                    		  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                    		  <td width="109" height="25">�Աݰ��¹�ȣ</td>
                    		  <td width="343"><%=(inipay.GetResult("VACT_Num"))%>
            </td>
                    		</tr>
                    		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    		</tr>
                    		<tr> 
                    		  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                    		  <td width="109" height="25">�Ա� �����ڵ�</td>
                    		  <td width="343"><%=(inipay.GetResult("VACT_BankCode"))%>
            </td>
                    		</tr>
                    		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    		</tr>
                    		<tr> 
                    		  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                    		  <td width="109" height="25">������ ��</td>
                    		  <td width="343"><%=(inipay.GetResult("VACT_Name"))%>
            </td>
                    		</tr>
                    		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    		</tr>
                    		<tr> 
                    		  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                    		  <td width="109" height="25">�۱��� ��</td>
                    		  <td width="343"><%=(inipay.GetResult("VACT_InputName"))%>
             </td>
                    		</tr>
                       <!-- modify in 2007.11.23
                    		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    		</tr>
                    		<tr> 
                    		  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                    		  <td width="109" height="25">�۱��� �ֹι�ȣ</td>
                    		  <td width="343"><%=(inipay.GetResult("VACT_RegNum"))%>
              </td>
                    		</tr>
                    		-->
                    		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    		</tr>
                    		<tr> 
                    		  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                    		  <td width="109" height="25">��ǰ �ֹ���ȣ</td>
                    		  <td width="343"><%=(inipay.GetResult("MOID"))%>
              </td>
                    		</tr>
                    		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    		</tr>
                    		<tr> 
                    		  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                    		  <td width="109" height="25">�۱� ����</td>
                    		  <td width="343"><%=(inipay.GetResult("VACT_Date"))%>
               </td>
                    		</tr>
                    		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    		</tr>
                            <tr> 
                    		  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                    		  <td width="109" height="25">�۱� �ð�</td>
                    		  <td width="343"><%=(inipay.GetResult("VACT_Time"))%>
               </td>
                    		</tr>
                    		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    		</tr>
<%
          }
          
  /*-------------------------------------------------------------------------------------------------------
	 *													*
	 *  �Ʒ� �κ��� ���� ���ܺ� ��� �޼��� ��� �κ��Դϴ�.    						*	
	 *													*
	 *  4.  �ڵ��� ���� 											*
	 -------------------------------------------------------------------------------------------------------*/
	 
          else if("HPP".equals(inipay.GetResult("PayMethod"))) {
%>

          			<tr> 
                    		  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                    		  <td width="109" height="25">�޴�����ȣ</td>
                    		  <td width="343"><%=(inipay.GetResult("HPP_Num"))%>
                </td>
                    		</tr>
                    		<tr> 
                                  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                                </tr>
                    		<tr> 
                                  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                                  <td width="109" height="25">�� �� �� ¥</td>
                                  <td width="343"><%=(inipay.GetResult("ApplDate"))%>
                 </td>
                                </tr>
                                <tr> 
                                  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                                </tr>
                                <tr> 
                                  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                                  <td width="109" height="25">�� �� �� ��</td>
                                  <td width="343"><%=(inipay.GetResult("ApplTime"))%>
                 </td>
                                </tr>
				<tr> 
                    		  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    		</tr>
<%
          }
          
  /*-------------------------------------------------------------------------------------------------------
	 *
	 *  �Ʒ� �κ��� ���� ���ܺ� ��� �޼��� ��� �κ��Դϴ�.
	 *
	 *  5.  �Ŵ� ��ȭ ����(Ars1588Bill)
	 -------------------------------------------------------------------------------------------------------*/
	 
         else if( "Ars1588Bill".equals(inipay.GetResult("PayMethod")) ) {
%>
                		<tr> 
                    		  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                    		  <td width="109" height="25">�� ȭ �� ȣ</td>
                    		  <td width="343"><%=(inipay.GetResult("ARSB_Num"))%>
               </td>
                    		</tr>
                    		<tr> 
                                  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                                </tr>
                		<tr> 
                                  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                                  <td width="109" height="25">�� �� �� ¥</td>
                                  <td width="343"><%=(inipay.GetResult("ApplDate"))%>
                </td>
                                </tr>
                                <tr> 
                                  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                                </tr>
                                <tr> 
                                  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                                  <td width="109" height="25">�� �� �� ��</td>
                                  <td width="343"><%=(inipay.GetResult("ApplTime"))%>
               </td>
                                </tr>
                		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    		</tr>
<%
         }
  /*-------------------------------------------------------------------------------------------------------
	 *
	 *  �Ʒ� �κ��� ���� ���ܺ� ��� �޼��� ��� �κ��Դϴ�.
	 *
	 *  6.  �޴� ��ȭ ����(PhoneBill)
	 -------------------------------------------------------------------------------------------------------*/
	 
         else if("PhoneBill".equals(inipay.GetResult("PayMethod"))) {
%>
                		<tr> 
                    		  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                    		  <td width="109" height="25">�� ȭ �� ȣ</td>
                    		  <td width="343"><%=(inipay.GetResult("PHNB_Num"))%>
               </td>
                    		</tr>
                    		<tr> 
                                  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                                </tr>
                		<tr> 
                                  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                                  <td width="109" height="25">�� �� �� ¥</td>
                                  <td width="343"><%=(inipay.GetResult("ApplDate"))%>
                </td>
                                </tr>
                                <tr> 
                                  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                                </tr>
                                <tr> 
                                  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                                  <td width="109" height="25">�� �� �� ��</td>
                                  <td width="343"><%=(inipay.GetResult("ApplTime"))%>
               </td>
                                </tr>
                		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    		</tr>
<%
         }
         
  /*-------------------------------------------------------------------------------------------------------
	 *													*
	 *  �Ʒ� �κ��� ���� ���ܺ� ��� �޼��� ��� �κ��Դϴ�.    						*	
	 *													*
	 *  7.  OK CASH BAG POINT ���� �� ���� 									*
	 -------------------------------------------------------------------------------------------------------*/
	 
         else if("OCBPoint".equals(inipay.GetResult("PayMethod")) ){
%>
                		<tr> 
                    		  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                    		  <td width="109" height="25">ī �� �� ȣ</td>
                    		  <td width="343"><%=(inipay.GetResult("OCB_Num"))%>
               </td>
                    		</tr>
                    		<tr> 
                                  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                                </tr>
                		<tr> 
                                  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                                  <td width="109" height="25">�� �� �� ¥</td>
                                  <td width="343"><%=(inipay.GetResult("ApplDate"))%>
             </td>
                                </tr>
                                <tr> 
                                  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                                </tr>
                                <tr> 
                                  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                                  <td width="109" height="25">�� �� �� ��</td>
                                  <td width="343"><%=(inipay.GetResult("ApplTime"))%>
            </td>
                                </tr>
                                <tr> 
                    		  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    		</tr>
                    		<tr> 
                    		  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                    		  <td width="109" height="25">���� ���ι�ȣ</td>
                    		  <td width="343"><%=(inipay.GetResult("OCB_SaveApplNum"))%>
            </td>
                    		</tr>
                    		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    		</tr>
                    		<tr> 
                    		  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                    		  <td width="109" height="25">��� ���ι�ȣ</td>
                    		  <td width="343"><%=(inipay.GetResult("OCB_PayApplNum"))%>
            </td>
                    		</tr>
                		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    		</tr>
                    		<tr> 
                    		  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                    		  <td width="109" height="25">����Ʈ���ұݾ�</td>
                    		  <td width="343"><%=(inipay.GetResult("OCB_PayPrice"))%>
             </td>
                    		</tr>
                    		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    		</tr>
<%
         }
         
  /*-------------------------------------------------------------------------------------------------------
	 *													*
	 *  �Ʒ� �κ��� ���� ���ܺ� ��� �޼��� ��� �κ��Դϴ�.    						*	
	 *													*
	 *  8.  ��ȭ ��ǰ��						                			*
	 -------------------------------------------------------------------------------------------------------*/
	 
         else if( "Culture".equals(inipay.GetResult("PayMethod")) ){
%>
                		<tr> 
                                  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                                  <td width="109" height="25">���ķ��� ID</td>
                                  <td width="343"><%=(inipay.GetResult("CULT_UserID"))%>
                </td>
                                </tr>
                                <tr> 
                                  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                                </tr>
<%
         }
         
  /*-------------------------------------------------------------------------------------------------------
	 *													*
	 *  �Ʒ� �κ��� ���� ���ܺ� ��� �޼��� ��� �κ��Դϴ�.    						*	
	 *													*
	 *  9.  ƾĳ�� ����						                			*
	 -------------------------------------------------------------------------------------------------------*/
	 
         else if("TEEN".equals(inipay.GetResult("PayMethod")) ){
%>
                		<tr> 
                                  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                                  <td width="109" height="25">ƾĳ���ܾ�</td>
                                  <td width="343"><%=(inipay.GetResult("TEEN_Remains"))%>
                </td>
                                </tr>
                                <tr> 
                                  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                                </tr>
				<tr>
                                  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                                  <td width="109" height="25">ƾĳ�þ��̵�</td>
                                  <td width="343"><%=(inipay.GetResult("TEEN_UserID"))%>
                 </td>
                                </tr>
                                <tr>
                                  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                                </tr>
<%
         }
          
  /*-------------------------------------------------------------------------------------------------------
	 *													*
	 *  �Ʒ� �κ��� ���� ���ܺ� ��� �޼��� ��� �κ��Դϴ�.    						*	
	 *													*
	 *  10.  ����Ʈ���� ����						                			*
	 -------------------------------------------------------------------------------------------------------*/
          else if( "DGCL".equals(inipay.GetResult("PayMethod")) ){
%>
                		<tr> 
                                  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                                  <td width="109" height="25">����� ī�� ��</td>
                                  <td width="343"><%=(inipay.GetResult("GAMG_Cnt"))%> ��</td>
                                </tr>

<%                             
         /* �Ʒ��κ��� ����� ����Ʈ���� ��ȣ�� �ܾ��� �����ݴϴ�.(���� ���нÿ��� �ܾ״�� ������������ �����ݴϴ�.) */
         /* �ִ� 6����� ����� �����ϸ�, ������ ���� ī�常 ��µ˴ϴ�. */
                                for(int i=1 ; i <= Integer.parseInt(inipay.GetResult("GAMG_Cnt")) ; i++)
                                {
%>
                                        <tr> 
                                	  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                                	</tr>
					<tr>
                                	  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                                	  <td width="109" height="25">����� ī���ȣ</td>
                                	  <td width="343"><b><%=(inipay.GetResult("GAMG_Num"+i) )%>
                                	  </b></td>
                                	</tr>
                                	
<%
                                	if(inipay.GetResult("ResultCode").equals("00") )
                                	{
%>
                                			<tr>
                                	        	  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                                			</tr>
                                			<tr> 
                                	        	  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                                	  		  <td width="109" height="25">ī�� �ܾ�</td>
                                	        	  <td width="343"><b><%=(inipay.GetResult("GAMG_Remains"+i))%> ��</b></td>
                                 	        	</tr>
<%
                                 	}else{
%>

                                			<tr>
                                	        	  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                                			</tr>
                                			<tr> 
                                	        	  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                                	  		  <td width="109" height="25">�����޼���</td>
                                	        	  <td width="343"><b><%=(inipay.GetResult("GAMG_ErrMsg" +i))%>
                                	 </b></td>
                                 	        	</tr>
<%
                                 	}
                                }
         }
         
          
  /*-------------------------------------------------------------------------------------------------------
	 *
	 *  �Ʒ� �κ��� ���� ���ܺ� ��� �޼��� ��� �κ��Դϴ�.
	 *
	 *  11.  ������ȭ ��ǰ�� ���� (BCSH)
	 -------------------------------------------------------------------------------------------------------*/
         else if( "BCSH".equals(inipay.GetResult("PayMethod")) ){
%>
                		<tr> 
                                  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                                  <td width="109" height="25"> ID</td>
                                  <td width="343"><%=(inipay.GetResult("BCSH_UserID"))%>
                </td>
                                </tr>
                                <tr> 
                                  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                                </tr>
<%
         }
%>
                    		<tr>
                                  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                                </tr>
                  </table></td>
              </tr>
            </table>
            <br>
            
<!-------------------------------------------------------------------------------------------------------
 *
 *  ���� ������(inipay.GetResult("ResultCode").equals("00"�� ��� ) "�̿�ȳ�"  �����ֱ� �κ��Դϴ�.
 *  ���� ���ܺ��� �̿������ ���� ���ܿ� ���� ���� ������ ���� �ݴϴ�.
 *  switch , case�� ���·� ���� ���ܺ��� ��� �ϰ� �ֽ��ϴ�.
 *  �Ʒ� ������ ��� �մϴ�.
 *
 *  1.	�ſ�ī��
 *  2.  ISP ����
 *  3.  �ڵ���
 *  4.  �Ŵ� ��ȭ ���� (ARS1588Bill)
 *  5.  �޴� ��ȭ ���� (PhoneBill)
 *  6.	OK CASH BAG POINT
 *  7.  ���������ü
 *  8.  ������ �Ա� ����
 *  9.  ��ȭ��ǰ�� ����
 *  10. ƾĳ�� ����
 *  11. ����Ʈ���� ����
 *  12. ������ȭ ��ǰ�� ����
 -------------------------------------------------------------------------------------------------------->
 
<%
    if(inipay.GetResult("ResultCode").equals("00") ){
            		
       /*--------------------------------------------------------------------------------------------------------
	 			*													*
	 			* ���� ������ �̿�ȳ� �����ֱ� 			    						*	
				*													*
	 			*  1.  �ſ�ī�� 						                			*
	 			--------------------------------------------------------------------------------------------------------*/
    		if(inipay.GetResult("PayMethod").equals("Card")){	
%>
					<table width="510" border="0" cellspacing="0" cellpadding="0">
         					<tr> 
         					    <td height="25"  style="padding:0 0 0 9"><img src="img/icon.gif" width="10" height="11"> 
         					      <strong><font color="433F37">�̿�ȳ�</font></strong></td>
         					  </tr>
         					  <tr> 
         					    <td  style="padding:0 0 0 23"> 
         					      <table width="470" border="0" cellspacing="0" cellpadding="0">
         					        <tr>          					          
         					          <td height="25">(1) �ſ�ī�� û������ <b>\"�̴Ͻý�(inicis.com)\"</b>���� ǥ��˴ϴ�.<br>
         					          (2) LGī�� �� BCī���� ��� <b>\"�̴Ͻý�(�̿� ������)\"</b>���� ǥ��ǰ�, �Ｚī���� ��� <b>\"�̴Ͻý�(�̿���� URL)\"</b>�� ǥ��˴ϴ�.</td>
         					        </tr>
         					        <tr> 
         					          <td height="1" colspan="2" align="center"  background="img/line.gif"></td>
         					        </tr>
         					        
         					      </table></td>
         					  </tr>
         				      </table>
<%
       }//if(Card)
				
       /*--------------------------------------------------------------------------------------------------------
	 			*													*
	 			* ���� ������ �̿�ȳ� �����ֱ� 			    						*	
				*													*
	 			*  2.  ISP 						                				*
	 			--------------------------------------------------------------------------------------------------------*/
    		else if("VCard".equals(inipay.GetResult("PayMethod"))){  // ISP
%>
					<table width="510" border="0" cellspacing="0" cellpadding="0">
         					<tr> 
         					    <td height="25"  style="padding:0 0 0 9"><img src="img/icon.gif" width="10" height="11"> 
         					      <strong><font color="433F37">�̿�ȳ�</font></strong></td>
         					  </tr>
         					  <tr> 
         					    <td  style="padding:0 0 0 23"> 
         					      <table width="470" border="0" cellspacing="0" cellpadding="0">
         					        <tr>          					          
         					          <td height="25">(1) �ſ�ī�� û������ <b>\"�̴Ͻý�(inicis.com)\"</b>���� ǥ��˴ϴ�.<br>
         					          (2) LGī�� �� BCī���� ��� <b>\"�̴Ͻý�(�̿� ������)\"</b>���� ǥ��ǰ�, �Ｚī���� ��� <b>\"�̴Ͻý�(�̿���� URL)\"</b>�� ǥ��˴ϴ�.</td>
         					        </tr>
         					        <tr> 
         					          <td height="1" colspan="2" align="center"  background="img/line.gif"></td>
         					        </tr>
         					        
         					      </table></td>
         					  </tr>
         				      </table>
<%
       }//if(VCard)
					
       /*--------------------------------------------------------------------------------------------------------
	 			*													*
	 			* ���� ������ �̿�ȳ� �����ֱ� 			    						*	
				*													*
	 			*  3. �ڵ��� 						                				*
	 			--------------------------------------------------------------------------------------------------------*/
    		else if("HPP".equals(inipay.GetResult("PayMethod"))){
%>
					<table width="510" border="0" cellspacing="0" cellpadding="0">
         					<tr> 
         					    <td height="25"  style="padding:0 0 0 9"><img src="img/icon.gif" width="10" height="11"> 
         					      <strong><font color="433F37">�̿�ȳ�</font></strong></td>
         					  </tr>
         					  <tr> 
         					    <td  style="padding:0 0 0 23"> 
         					      <table width="470" border="0" cellspacing="0" cellpadding="0">
         					        <tr>          					          
         					          <td height="25">(1) �ڵ��� û������ <b>\"�Ҿװ���\"</b> �Ǵ� <b>\"�ܺ������̿��\"</b>�� û���˴ϴ�.<br>
         					          (2) ������ �� �ѵ��ݾ��� Ȯ���Ͻð��� �� ��� �� �̵���Ż��� �����͸� �̿����ֽʽÿ�.
         					          </td>
         					        </tr>
         					        <tr> 
         					          <td height="1" colspan="2" align="center"  background="img/line.gif"></td>
         					        </tr>
         					        
         					      </table></td>
         					  </tr>
         				      </table>
<%
        }//if(HPP)
       /*--------------------------------------------------------------------------------------------------------
	 			*													*
	 			* ���� ������ �̿�ȳ� �����ֱ� 			    						*	
				*													*
	 			*  4. ��ȭ ���� (ARS1588Bill)				                				*
	 			--------------------------------------------------------------------------------------------------------*/
    		else if("Ars1588Bill".equals(inipay.GetResult("PayMethod"))){
%>
					<table width="510" border="0" cellspacing="0" cellpadding="0">
         					<tr> 
         					    <td height="25"  style="padding:0 0 0 9"><img src="img/icon.gif" width="10" height="11"> 
         					      <strong><font color="433F37">�̿�ȳ�</font></strong></td>
         					  </tr>
         					  <tr> 
         					    <td  style="padding:0 0 0 23"> 
         					      <table width="470" border="0" cellspacing="0" cellpadding="0">
         					        <tr>          					          
         					          <td height="25">(1) ��ȭ û������ <b>\"������ �̿��\"</b>�� û���˴ϴ�.<br>
                                                          (2) �� �ѵ��ݾ��� ��� ������ �������� ��� ��ϵ� ��ȭ��ȣ ������ �ƴ� �ֹε�Ϲ�ȣ�� �������� å���Ǿ� �ֽ��ϴ�.<br>
                                                          (3) ��ȭ ������Ҵ� ������� �����մϴ�.
         					          </td>
         					        </tr>
         					        <tr> 
         					          <td height="1" colspan="2" align="center"  background="img/line.gif"></td>
         					        </tr>
         					        
         					      </table></td>
         					  </tr>
         				      </table>
<%
       }//if (Ars1588Bill)
					
       /*--------------------------------------------------------------------------------------------------------
	 			*													*
	 			* ���� ������ �̿�ȳ� �����ֱ� 			    						*	
				*													*
	 			*  5. ���� ���� (PhoneBill)				                				*
	 			--------------------------------------------------------------------------------------------------------*/
    		else if("PhoneBill".equals(inipay.GetResult("PayMethod"))){
%>
					<table width="510" border="0" cellspacing="0" cellpadding="0">
         					<tr> 
         					    <td height="25"  style="padding:0 0 0 9"><img src="img/icon.gif" width="10" height="11"> 
         					      <strong><font color="433F37">�̿�ȳ�</font></strong></td>
         					  </tr>
         					  <tr> 
         					    <td  style="padding:0 0 0 23"> 
         					      <table width="470" border="0" cellspacing="0" cellpadding="0">
         					        <tr>          					          
         					          <td height="25">(1) ��ȭ û������ <b>\"���ͳ� ������ (����)�����̿��\"</b>�� û���˴ϴ�.<br>
                                                          (2) �� �ѵ��ݾ��� ��� ������ �������� ��� ��ϵ� ��ȭ��ȣ ������ �ƴ� �ֹε�Ϲ�ȣ�� �������� å���Ǿ� �ֽ��ϴ�.<br>
                                                          (3) ��ȭ ������Ҵ� ������� �����մϴ�.
         					          </td>
         					        </tr>
         					        <tr> 
         					          <td height="1" colspan="2" align="center"  background="img/line.gif"></td>
         					        </tr>
         					        
         					      </table></td>
         					  </tr>
         				      </table>
<%
       }//if(PhoneBill)
				
       /*--------------------------------------------------------------------------------------------------------
	 			*													*
	 			* ���� ������ �̿�ȳ� �����ֱ� 			    						*	
				*													*
	 			*  6. OK CASH BAG POINT					                				*
	 			--------------------------------------------------------------------------------------------------------*/
    		else if("OCBPoint".equals(inipay.GetResult("PayMethod"))){
%>
					<table width="510" border="0" cellspacing="0" cellpadding="0">
         					<tr> 
         					    <td height="25"  style="padding:0 0 0 9"><img src="img/icon.gif" width="10" height="11"> 
         					      <strong><font color="433F37">�̿�ȳ�</font></strong></td>
         					  </tr>
         					  <tr> 
         					    <td  style="padding:0 0 0 23"> 
         					      <table width="470" border="0" cellspacing="0" cellpadding="0">
         					        <tr>          					          
         					          <td height="25">(1) OK CASH BAG ����Ʈ ������Ҵ� ������� �����մϴ�.
         					          </td>
         					        </tr>
         					        <tr> 
         					          <td height="1" colspan="2" align="center"  background="img/line.gif"></td>
         					        </tr>
         					        
         					      </table></td>
         					  </tr>
         				      </table>
<%
       }//if (OCBPoint)
					
       /*--------------------------------------------------------------------------------------------------------
	 			*													*
	 			* ���� ������ �̿�ȳ� �����ֱ� 			    						*	
				*													*
	 			*  7. ���������ü					                				*
	 			--------------------------------------------------------------------------------------------------------*/
    		else if("DirectBank".equals(inipay.GetResult("PayMethod"))){
%>
					<table width="510" border="0" cellspacing="0" cellpadding="0">
         					<tr> 
         					    <td height="25"  style="padding:0 0 0 9"><img src="img/icon.gif" width="10" height="11"> 
         					      <strong><font color="433F37">�̿�ȳ�</font></strong></td>
         					  </tr>
         					  <tr> 
         					    <td  style="padding:0 0 0 23"> 
         					      <table width="470" border="0" cellspacing="0" cellpadding="0">
         					        <tr>          					          
         					          <td height="25">(1) ������ ���忡�� �̿��Ͻ� �������� ǥ��˴ϴ�.<br>
         					                          (2) ������ ���� ����ȸ�� www.inicis.com�� ���� ��� <b>\"��볻�� �� û����� ��ȸ\"</b>������ Ȯ�ΰ����մϴ�.
         					          </td>
         					        </tr>
         					        <tr> 
         					          <td height="1" colspan="2" align="center"  background="img/line.gif"></td>
         					        </tr>
         					        
         					      </table></td>
         					  </tr>
         				      </table>
<%
      } //if(DirectBank)
					
       /*--------------------------------------------------------------------------------------------------------
	 			*													*
	 			* ���� ������ �̿�ȳ� �����ֱ� 			    						*	
				*													*
	 			*  8. ������ �Ա� ����					                			*
	 			--------------------------------------------------------------------------------------------------------*/		
    		else if("VBank".equals(inipay.GetResult("PayMethod"))){

%>
					<table width="510" border="0" cellspacing="0" cellpadding="0">
         					<tr> 
         					    <td height="25"  style="padding:0 0 0 9"><img src="img/icon.gif" width="10" height="11"> 
         					      <strong><font color="433F37">�̿�ȳ�</font></strong></td>
         					  </tr>
         					  <tr> 
         					    <td  style="padding:0 0 0 23"> 
         					      <table width="470" border="0" cellspacing="0" cellpadding="0">
         					        <tr>
                                      <td>
         					          (1) ��� ����� �Աݿ����� �Ϸ�� ���ϻ� ���� �ԱݿϷᰡ �̷���� ���� �ƴմϴ�.<br>
         					          (2) ��� �Աݰ��·� �ش� ��ǰ�ݾ��� �������Ա�(â���Ա�)�Ͻðų�, ���ͳ� ��ŷ ���� ���� �¶��� �۱��� �Ͻñ� �ٶ��ϴ�.<br>
                                      (3) �ݵ�� �Աݱ��� ���� �Ա��Ͻñ� �ٶ��, ����Աݽ� �ݵ�� �ֹ��Ͻ� �ݾ׸� �Ա��Ͻñ� �ٶ��ϴ�.
                                                          </td>
         					        </tr>
         					        <tr> 
         					          <td height="1" colspan="2" align="center"  background="img/line.gif"></td>
         					        </tr>
         					        
         					      </table></td>
         					  </tr>
         				      </table>
<%
        }//if(VBank)
					
       /*--------------------------------------------------------------------------------------------------------
	 			*													*
	 			* ���� ������ �̿�ȳ� �����ֱ� 			    						*	
				*													*
	 			*  9. ��ȭ��ǰ�� ����					                				*
	 			--------------------------------------------------------------------------------------------------------*/
    		else if("Culture".equals(inipay.GetResult("PayMethod"))){

%>
					<table width="510" border="0" cellspacing="0" cellpadding="0">
         					<tr> 
         					    <td height="25"  style="padding:0 0 0 9"><img src="img/icon.gif" width="10" height="11"> 
         					      <strong><font color="433F37">�̿�ȳ�</font></strong></td>
         					  </tr>
         					  <tr> 
         					    <td  style="padding:0 0 0 23"> 
         					      <table width="470" border="0" cellspacing="0" cellpadding="0">
         					        <tr>          					          
         					          <td height="25">(1) ��ȭ��ǰ���� �¶��ο��� �̿��Ͻ� ��� �������ο����� ����Ͻ� �� �����ϴ�.<br>
         					                          (2) ����ĳ�� �ܾ��� �����ִ� ���, ������ ����ĳ�� �ܾ��� �ٽ� ����Ͻ÷��� ���ķ��� ID�� ����Ͻñ� �ٶ��ϴ�.
         					          </td>
         					        </tr>
         					        <tr> 
         					          <td height="1" colspan="2" align="center"  background="img/line.gif"></td>
         					        </tr>
         					        
         					      </table></td>
         					  </tr>
         				      </table>
<%
       }
					
       /*--------------------------------------------------------------------------------------------------------
	 			*													*
	 			* ���� ������ �̿�ȳ� �����ֱ� 			    						*	
				*													*
	 			*  10. ƾĳ�� ����					                				*
	 			--------------------------------------------------------------------------------------------------------*/
    		else if("TEEN".equals(inipay.GetResult("PayMethod"))){
%>
					<table width="510" border="0" cellspacing="0" cellpadding="0">
         					<tr> 
         					    <td height="25"  style="padding:0 0 0 9"><img src="img/icon.gif" width="10" height="11"> 
         					      <strong><font color="433F37">�̿�ȳ�</font></strong></td>
         					  </tr>
         					  <tr> 
         					    <td  style="padding:0 0 0 23"> 
         					      <table width="470" border="0" cellspacing="0" cellpadding="0">
         					        <tr>          					          
         					          <td>(1)ƾĳ�ô� ���ͳ� ����Ʈ �Ǵ� PC�濡�� �����Ӱ� ����� �� �ִ� ���� ���������Դϴ�.<br>
							      (2)ƾĳ�� ī���ȣ ���� : ƾĳ�� ī�� �޸鿡 ���� 12�ڸ� ��ȣ�� �Է��Ͽ� �����ϴ� ����Դϴ�.<br>
							      (3)ƾĳ�� ���̵� ���� : ƾĳ�� ����Ʈ (www.teencash.co.kr)�� ȸ������ �� ƾĳ�� ����Ʈ�� �����Ͽ� ������ ƾĳ�� ī�带 ����Ͽ� �̿��ϴ� ����Դϴ�.
         					          </td>
         					        </tr>
         					        <tr> 
         					          <td height="1" colspan="2" align="center"  background="img/line.gif"></td>
         					        </tr>
         					        
         					      </table></td>
         					  </tr>
         				      </table>
<%
        }// if(TEEN)
					
	     /*--------------------------------------------------------------------------------------------------------
	 			*													*
	 			* ���� ������ �̿�ȳ� �����ֱ� 			    						*	
				*													*
	 			*  11. ����Ʈ���� ����				                				*
	 			--------------------------------------------------------------------------------------------------------*/
    		else if("DGCL".equals(inipay.GetResult("PayMethod"))){
%>
					<table width="510" border="0" cellspacing="0" cellpadding="0">
         					<tr> 
         					    <td height="25"  style="padding:0 0 0 9"><img src="img/icon.gif" width="10" height="11"> 
         					      <strong><font color="433F37">�̿�ȳ�</font></strong></td>
         					  </tr>
         					  <tr> 
         					    <td  style="padding:0 0 0 23"> 
         					      <table width="470" border="0" cellspacing="0" cellpadding="0">
         					        <tr>          					          
         					          <td>(1)����Ʈ������ ��ǰ�ǿ� �μ�Ǿ��ִ� ��ũ��ġ ��ȣ�� �����ϴ� ����Դϴ�.<br>
         					              (2)����Ʈ���� ������ ��ȭ��ǰ��(www.cultureland.co.kr)���� ���� �ϽǼ� �ֽ��ϴ�.<br>
         					              (3)����Ʈ������ �ִ� 6����� ����� �����մϴ�.
         					          </td>
         					        </tr>
         					        <tr> 
         					          <td height="1" colspan="2" align="center"  background="img/line.gif"></td>
         					        </tr>
         					        
         					      </table></td>
         					  </tr>
         				      </table>
<%
        }//if(DGCL)
       /*--------------------------------------------------------------------------------------------------------
	 			*
	 			* ���� ������ �̿�ȳ� �����ֱ�
				*
	 			*  12. ������ȭ��ǰ�� ����
	 			--------------------------------------------------------------------------------------------------------*/
    		else if("BCSH".equals(inipay.GetResult("PayMethod"))){

%>
					<table width="510" border="0" cellspacing="0" cellpadding="0">
         					<tr> 
         					    <td height="25"  style="padding:0 0 0 9"><img src="img/icon.gif" width="10" height="11"> 
         					      <strong><font color="433F37">�̿�ȳ�</font></strong></td>
         					  </tr>
         					  <tr> 
         					    <td  style="padding:0 0 0 23"> 
         					      <table width="470" border="0" cellspacing="0" cellpadding="0">
         					        <tr>          					          
         					          <td height="25">(1) ������ȭ��ǰ���� �¶��ο��� �̿��Ͻ� ��� �������ο����� ����Ͻ� �� �����ϴ�.<br>
         					                          (2) �ܾ��� �������� ���, �϶����� ID�� ����Ͽ� �� �� �ٸ� ������ �̿��Ͻʽÿ�.
         					          </td>
         					        </tr>
         					        <tr> 
         					          <td height="1" colspan="2" align="center"  background="img/line.gif"></td>
         					        </tr>
         					        
         					      </table></td>
         					  </tr>
         				      </table>
<%
       }//if (BCSH)
			}//if(ResultCode.equals("00"))
%>
            
            <!-- �̿�ȳ� �� -->
            
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

