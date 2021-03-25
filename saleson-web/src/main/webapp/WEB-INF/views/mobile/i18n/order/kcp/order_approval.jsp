<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import="kr.co.kcp.net.connection.*"      %>
<%@ page import="kr.co.kcp.net.connection.util.*" %>
<%@ page import="kr.co.kcp.net.connection.dto.*"  %>
<%@ page import="com.onlinepowers.framework.util.PropertiesUtils" %>

<%
    response.setHeader("Cache-Control","no-store");
    response.setHeader("Pragma","no-cache");
    response.setDateHeader("Expires",0);

    ConnectionKCP suc = new ConnectionKCP();  // KCP ���� ������ �����ϱ� ���� Object                     ( ����� �⺻�� �Ǵ� ��ü ) - �ʼ�
    OpenHash      oh  = new OpenHash();       // KCP �� ��Ž� ������ �������� Ȯ�� �ϱ� ���� Hash Object ( ��ü�� KCP ���� ��Ž� ������ �������� Ȯ�� �ϱ� ���� �ʿ�. �̼����� ��� ������ ���̺귯������ ��ü������ hash ó�� )
    HttpJsonXml   hjx = new HttpJsonXml();    // ���䰪 get value �������� �����ü� �ִ� Object           ( Java �Ǵ� JSP ������ �����͸� �Ľ��Ҷ� �ʿ� - XML �Ǵ� JSON )
    ParamData     pd  = new ParamData();      // �Ķ��Ÿ ���� �����Ҽ� �ִ� bean Object                  ( String, HashMap ������ ��ü ���� )
    
    String siteCode      = request.getParameter( "site_cd"       );
    String orderID       = request.getParameter( "ordr_idxx"     );
    String paymentAmount = request.getParameter( "good_mny"      );
    String paymentMethod = request.getParameter( "pay_method"    );
    String productName   = request.getParameter( "good_name"     );
    String response_type = request.getParameter( "response_type" );
    String Ret_URL       = request.getParameter( "Ret_URL"       );
    String escrow        = request.getParameter( "escw_used"     ) == null? "":request.getParameter( "escw_used" );
    
    pd.setGood_mny( paymentAmount );
    pd.setGood_name( productName );
    pd.setOrdr_idxx( orderID );
    pd.setPay_method( paymentMethod );
    pd.setRet_URL( Ret_URL );
    pd.setSite_cd( siteCode );
    pd.setEscw_used( escrow );

    if ("JSON".equals(response_type) || "XML".equals(response_type) )
    {
        pd.setResponse_type( response_type );
    }
    
    boolean g_conf_server = false;

    if ("true".equals(PropertiesUtils.getProperty("pg.kcp.g.conf.server"))) {
    	g_conf_server = true;
    }

    out.println(suc.kcpPaymentSmartPhone( request, g_conf_server, pd, PropertiesUtils.getProperty("pg.kcp.g.conf.log.dir")));
%>