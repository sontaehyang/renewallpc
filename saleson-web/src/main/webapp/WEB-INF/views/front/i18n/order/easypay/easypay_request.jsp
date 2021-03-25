<%@ page contentType="text/html; charset=euc-kr" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.security.Security"%>
<%--
    /*****************************************************************************
     * Easypay �⺻ ���������� include�Ѵ�
     ****************************************************************************/
--%>
<%@ page import="com.kicc.*" %>
<%!
    /**
     * �Ķ���� üũ �޼ҵ�
     */
    public String getNullToSpace(String param) {
        return (param == null) ? "" : param.trim();
    }
%>
<%
    /* -------------------------------------------------------------------------- */
    /* ::: ó������ ����                                                          */
    /* -------------------------------------------------------------------------- */
    final String TRAN_CD_NOR_PAYMENT    = "00101000";   // ����(�Ϲ�, ����ũ��)
    final String TRAN_CD_NOR_MGR        = "00201000";   // ����(�Ϲ�, ����ũ��)

    /* -------------------------------------------------------------------------- */
    /* ::: �������� ����                                                          */
    /* -------------------------------------------------------------------------- */
    final String GW_URL                 = "testgw.easypay.co.kr";  // Gateway URL ( test )
    //final String GW_URL               = "gw.easypay.co.kr";      // Gateway URL ( real )
    final String GW_PORT                = "80";                    // ��Ʈ��ȣ(����Ұ�)

    /* -------------------------------------------------------------------------- */
    /* ::: ���� ������ �¾� (��ü�� �°� ����)                                    */
    /* -------------------------------------------------------------------------- */
    /* �� ���� ��                                                                 */
    /* cert_file ���� ����                                                        */
    /* - pg_cert.pem ������ �ִ� ���丮��  ���� ��� ����                       */
    /* log_dir ���� ����                                                          */
    /* - log ���丮 ����                                                        */
    /* log_level ���� ����                                                        */
    /* - log ���� ���� (1 to 99(�������� ��))                                   */
    /* -------------------------------------------------------------------------- */
    final String CERT_FILE              = "�������������ּ�/easypay80_webpay_jsp/web/cert";
    final String LOG_DIR                = "�������������ּ�/easypay80_webpay_jsp/web/log";
    final int LOG_LEVEL                 = 1;

    /* -------------------------------------------------------------------------- */
    /* ::: ���ο�û ���� ����                                                     */
    /* -------------------------------------------------------------------------- */
    //[���]    
    String tr_cd            = getNullToSpace(request.getParameter("EP_tr_cd"));           // [�ʼ�]��û����
    String trace_no         = getNullToSpace(request.getParameter("EP_trace_no"));        // [�ʼ�]����������ȣ
    String order_no         = getNullToSpace(request.getParameter("EP_order_no"));        // [�ʼ�]�ֹ���ȣ
    String mall_id          = getNullToSpace(request.getParameter("EP_mall_id"));         // [�ʼ�]�����̵�    
    //[����]
    String encrypt_data     = getNullToSpace(request.getParameter("EP_encrypt_data"));    // [�ʼ�]��ȣȭ ����Ÿ      
    String sessionkey       = getNullToSpace(request.getParameter("EP_sessionkey"));      // [�ʼ�]��ȣȭŰ    

    /* -------------------------------------------------------------------------- */
    /* ::: ������� ���� ����                                                     */
    /* -------------------------------------------------------------------------- */
    String mgr_txtype       = getNullToSpace(request.getParameter("mgr_txtype"));         // [�ʼ�]�ŷ�����
    String mgr_subtype      = getNullToSpace(request.getParameter("mgr_subtype"));        // [����]���漼�α���
    String org_cno          = getNullToSpace(request.getParameter("org_cno"));            // [�ʼ�]���ŷ�������ȣ
    String mgr_amt          = getNullToSpace(request.getParameter("mgr_amt"));            // [����]�κ����/ȯ�ҿ�û �ݾ�
    String mgr_rem_amt      = getNullToSpace(request.getParameter("mgr_rem_amt"));        // [����]�κ���� �ܾ�    
    String mgr_bank_cd      = getNullToSpace(request.getParameter("mgr_bank_cd"));        // [����]ȯ�Ұ��� �����ڵ�
    String mgr_account      = getNullToSpace(request.getParameter("mgr_account"));        // [����]ȯ�Ұ��� ��ȣ
    String mgr_depositor    = getNullToSpace(request.getParameter("mgr_depositor"));      // [����]ȯ�Ұ��� �����ָ�
    
    /* -------------------------------------------------------------------------- */
    /* ::: ����                                                                   */
    /* -------------------------------------------------------------------------- */
    String mgr_data    = "";     // ��������
    String mall_data   = "";     // ��û����

    /* -------------------------------------------------------------------------- */
    /* ::: ���� ���                                                              */
    /* -------------------------------------------------------------------------- */
    String bDBProc              = "";     //������DBó����������
    String res_cd               = "";     //�����ڵ�  
    String res_msg              = "";     //����޽���
    String r_cno                = "";     //PG�ŷ���ȣ
    String r_amount             = "";     //�� �����ݾ�
    String r_order_no           = "";     //�ֹ���ȣ
    String r_auth_no            = "";     //���ι�ȣ
    String r_tran_date          = "";     //�����Ͻ�
    String r_escrow_yn          = "";     //����ũ�� �������
    String r_complex_yn         = "";     //���հ��� ����
    String r_stat_cd            = "";     //�����ڵ�
    String r_stat_msg           = "";     //���¸޽���
    String r_pay_type           = "";     //��������
    String r_mall_id            = "";     //������ Mall ID
    String r_card_no            = "";     //ī���ȣ
    String r_issuer_cd          = "";     //�߱޻��ڵ�
    String r_issuer_nm          = "";     //�߱޻��
    String r_acquirer_cd        = "";     //���Ի��ڵ�
    String r_acquirer_nm        = "";     //���Ի��
    String r_install_period     = "";     //�Һΰ���
    String r_noint              = "";     //�����ڿ���
    String r_part_cancel_yn     = "";     //�κ���� ���ɿ���
    String r_card_gubun         = "";     //�ſ�ī�� ����
    String r_card_biz_gubun     = "";     //�ſ�ī�� ����
    String r_cpon_flag          = "";     //�����������
    String r_bank_cd            = "";     //�����ڵ�
    String r_bank_nm            = "";     //�����
    String r_account_no         = "";     //���¹�ȣ
    String r_deposit_nm         = "";     //�Ա��ڸ�
    String r_expire_date        = "";     //���»�븸����
    String r_cash_res_cd        = "";     //���ݿ����� ����ڵ�
    String r_cash_res_msg       = "";     //���ݿ����� ����޼���
    String r_cash_auth_no       = "";     //���ݿ����� ���ι�ȣ
    String r_cash_tran_date     = "";     //���ݿ����� �����Ͻ�
    String r_cash_issue_type    = "";     //���ݿ���������뵵
    String r_cash_auth_type     = "";     //��������
    String r_cash_auth_value    = "";     //������ȣ
    String r_auth_id            = "";     //PhoneID
    String r_billid             = "";     //������ȣ
    String r_mobile_no          = "";     //�޴�����ȣ
    String r_mob_ansim_yn       = "";     //�Ƚɰ��� �������
    String r_ars_no             = "";     //��ȭ��ȣ
    String r_cp_cd              = "";     //����Ʈ��/������
    String r_cpon_auth_no       = "";     //�������ι�ȣ
    String r_cpon_tran_date     = "";     //���������Ͻ�
    String r_cpon_no            = "";     //������ȣ
    String r_remain_cpon        = "";     //�����ܾ�
    String r_used_cpon          = "";     //���� ���ݾ�
    String r_rem_amt            = "";     //�ܾ�
    String r_bk_pay_yn          = "";     //��ٱ��� ��������
    String r_canc_acq_date      = "";     //��������Ͻ�
    String r_canc_date          = "";     //����Ͻ�
    String r_refund_date        = "";     //ȯ�ҿ����Ͻ�

    /* -------------------------------------------------------------------------- */
    /* ::: EasyPayClient �ν��Ͻ� ���� [����Ұ� !!].                             */
    /* -------------------------------------------------------------------------- */
    EasyPayClient easyPayClient = new EasyPayClient();
    easyPayClient.easypay_setenv_init( GW_URL, GW_PORT, CERT_FILE, LOG_DIR, LOG_LEVEL );
    easyPayClient.easypay_reqdata_init();

    /* -------------------------------------------------------------------------- */
    /* ::: ���ο�û(�÷����� ��ȣȭ ���� ����)                                    */
    /* -------------------------------------------------------------------------- */
    if( TRAN_CD_NOR_PAYMENT.equals(tr_cd) ){

        // ���ο�û ���� ����
        easyPayClient.easypay_set_trace_no(trace_no);
        easyPayClient.easypay_encdata_set(encrypt_data, sessionkey);

    /* -------------------------------------------------------------------------- */
    /* ::: ������� ��û                                                          */
    /* -------------------------------------------------------------------------- */
    }else if( TRAN_CD_NOR_MGR.equals( tr_cd ) ) {

        int easypay_mgr_data_item;
        easypay_mgr_data_item = easyPayClient.easypay_item( "mgr_data" );

        easyPayClient.easypay_deli_us( easypay_mgr_data_item, "mgr_txtype"    , mgr_txtype    );          // [�ʼ�]�ŷ�����           
        easyPayClient.easypay_deli_us( easypay_mgr_data_item, "mgr_subtype"   , mgr_subtype   );          // [����]���漼�α���       
        easyPayClient.easypay_deli_us( easypay_mgr_data_item, "org_cno"       , org_cno       );          // [�ʼ�]���ŷ�������ȣ     
        easyPayClient.easypay_deli_us( easypay_mgr_data_item, "mgr_amt"       , mgr_amt       );          // [����]�ݾ�               
        easyPayClient.easypay_deli_us( easypay_mgr_data_item, "mgr_rem_amt"   , mgr_rem_amt   );          // [����]�κ���� �ܾ�      
        easyPayClient.easypay_deli_us( easypay_mgr_data_item, "mgr_bank_cd"   , mgr_bank_cd   );          // [����]ȯ�Ұ��� �����ڵ�  
        easyPayClient.easypay_deli_us( easypay_mgr_data_item, "mgr_account"   , mgr_account   );          // [����]ȯ�Ұ��� ��ȣ      
        easyPayClient.easypay_deli_us( easypay_mgr_data_item, "mgr_depositor" , mgr_depositor );          // [����]ȯ�Ұ��� �����ָ�  
        easyPayClient.easypay_deli_us( easypay_mgr_data_item, "req_ip"        , saleson.common.utils.CommonUtils.getClientIp() );// [�ʼ�]��û�� IP

    }

    /* -------------------------------------------------------------------------- */
    /* ::: ����                                                                   */
    /* -------------------------------------------------------------------------- */
    if ( tr_cd.length() > 0 ) {
        easyPayClient.easypay_run( mall_id, tr_cd, order_no );

        res_cd = easyPayClient.res_cd;
        res_msg = easyPayClient.res_msg;
    }
    else {
        res_cd  = "M114";
        res_msg = "���� ����|tr_cd���� �������� �ʾҽ��ϴ�.";
    }
    /* -------------------------------------------------------------------------- */
    /* ::: ��� ó��                                                              */
    /* -------------------------------------------------------------------------- */

    r_cno              = easyPayClient.easypay_get_res( "cno"             );     //PG�ŷ���ȣ
    r_amount           = easyPayClient.easypay_get_res( "amount"          );     //�� �����ݾ�
    r_order_no         = easyPayClient.easypay_get_res( "order_no"        );     //�ֹ���ȣ
    r_auth_no          = easyPayClient.easypay_get_res( "auth_no"         );     //���ι�ȣ
    r_tran_date        = easyPayClient.easypay_get_res( "tran_date"       );     //�����Ͻ�
    r_escrow_yn        = easyPayClient.easypay_get_res( "escrow_yn"       );     //����ũ�� �������
    r_complex_yn       = easyPayClient.easypay_get_res( "complex_yn"      );     //���հ��� ����
    r_stat_cd          = easyPayClient.easypay_get_res( "stat_cd"         );     //�����ڵ�
    r_stat_msg         = easyPayClient.easypay_get_res( "stat_msg"        );     //���¸޽���
    r_pay_type         = easyPayClient.easypay_get_res( "pay_type"        );     //��������
    r_mall_id          = easyPayClient.easypay_get_res( "mall_id"         );     //������ Mall ID
    r_card_no          = easyPayClient.easypay_get_res( "card_no"         );     //ī���ȣ
    r_issuer_cd        = easyPayClient.easypay_get_res( "issuer_cd"       );     //�߱޻��ڵ�
    r_issuer_nm        = easyPayClient.easypay_get_res( "issuer_nm"       );     //�߱޻��
    r_acquirer_cd      = easyPayClient.easypay_get_res( "acquirer_cd"     );     //���Ի��ڵ�
    r_acquirer_nm      = easyPayClient.easypay_get_res( "acquirer_nm"     );     //���Ի��
    r_install_period   = easyPayClient.easypay_get_res( "install_period"  );     //�Һΰ���
    r_noint            = easyPayClient.easypay_get_res( "noint"           );     //�����ڿ���
    r_part_cancel_yn   = easyPayClient.easypay_get_res( "part_cancel_yn"  );     //�κ���� ���ɿ���
    r_card_gubun       = easyPayClient.easypay_get_res( "card_gubun"      );     //�ſ�ī�� ����
    r_card_biz_gubun   = easyPayClient.easypay_get_res( "card_biz_gubun"  );     //�ſ�ī�� ����
    r_cpon_flag        = easyPayClient.easypay_get_res( "cpon_flag"       );     //�����������
    r_bank_cd          = easyPayClient.easypay_get_res( "bank_cd"         );     //�����ڵ�
    r_bank_nm          = easyPayClient.easypay_get_res( "bank_nm"         );     //�����
    r_account_no       = easyPayClient.easypay_get_res( "account_no"      );     //���¹�ȣ
    r_deposit_nm       = easyPayClient.easypay_get_res( "deposit_nm"      );     //�Ա��ڸ�
    r_expire_date      = easyPayClient.easypay_get_res( "expire_date"     );     //���»�븸����
    r_cash_res_cd      = easyPayClient.easypay_get_res( "cash_res_cd"     );     //���ݿ����� ����ڵ�
    r_cash_res_msg     = easyPayClient.easypay_get_res( "cash_res_msg"    );     //���ݿ����� ����޼���
    r_cash_auth_no     = easyPayClient.easypay_get_res( "cash_auth_no"    );     //���ݿ����� ���ι�ȣ
    r_cash_tran_date   = easyPayClient.easypay_get_res( "cash_tran_date"  );     //���ݿ����� �����Ͻ�
    r_cash_issue_type  = easyPayClient.easypay_get_res( "cash_issue_type" );     //���ݿ���������뵵
    r_cash_auth_type   = easyPayClient.easypay_get_res( "cash_auth_type"  );     //��������
    r_cash_auth_value  = easyPayClient.easypay_get_res( "cash_auth_value" );     //������ȣ
    r_auth_id          = easyPayClient.easypay_get_res( "auth_id"         );     //PhoneID
    r_billid           = easyPayClient.easypay_get_res( "billid"          );     //������ȣ
    r_mobile_no        = easyPayClient.easypay_get_res( "mobile_no"       );     //�޴�����ȣ
    r_mob_ansim_yn     = easyPayClient.easypay_get_res( "mob_ansim_yn"    );     //�Ƚɰ��� �������
    r_ars_no           = easyPayClient.easypay_get_res( "ars_no"          );     //��ȭ��ȣ
    r_cp_cd            = easyPayClient.easypay_get_res( "cp_cd"           );     //����Ʈ��/������
    r_cpon_auth_no     = easyPayClient.easypay_get_res( "cpon_auth_no"    );     //�������ι�ȣ
    r_cpon_tran_date   = easyPayClient.easypay_get_res( "cpon_tran_date"  );     //���������Ͻ�
    r_cpon_no          = easyPayClient.easypay_get_res( "cpon_no"         );     //������ȣ
    r_remain_cpon      = easyPayClient.easypay_get_res( "remain_cpon"     );     //�����ܾ�
    r_used_cpon        = easyPayClient.easypay_get_res( "used_cpon"       );     //���� ���ݾ�
    r_rem_amt          = easyPayClient.easypay_get_res( "rem_amt"         );     //�ܾ�
    r_bk_pay_yn        = easyPayClient.easypay_get_res( "bk_pay_yn"       );     //��ٱ��� ��������
    r_canc_acq_date    = easyPayClient.easypay_get_res( "canc_acq_date"   );     //��������Ͻ�
    r_canc_date        = easyPayClient.easypay_get_res( "canc_date"       );     //����Ͻ�
    r_refund_date      = easyPayClient.easypay_get_res( "refund_date"     );     //ȯ�ҿ����Ͻ�

    /* -------------------------------------------------------------------------- */
    /* ::: ������ DB ó��                                                         */
    /* -------------------------------------------------------------------------- */
    /* �����ڵ�(res_cd)�� "0000" �̸� ������� �Դϴ�.                            */
    /* r_amount�� �ֹ�DB�� �ݾװ� �ٸ� �� �ݵ�� ��� ��û�� �Ͻñ� �ٶ��ϴ�.     */
    /* DB ó�� ���� �� ��� ó���� ���ֽñ� �ٶ��ϴ�.                             */
    /* -------------------------------------------------------------------------- */
    if ( res_cd.equals("0000") ) {
        bDBProc = "true";     // DBó�� ���� �� "true", ���� �� "false"
        if ( bDBProc.equals("false") ) {
            // ���ο�û�� ���� �� �Ʒ� ����
            if( TRAN_CD_NOR_PAYMENT.equals(tr_cd) ) {
                int easypay_mgr_data_item;

                easyPayClient.easypay_reqdata_init();

                tr_cd = TRAN_CD_NOR_MGR;
                easypay_mgr_data_item = easyPayClient.easypay_item( "mgr_data" );
                if ( !r_escrow_yn.equals( "Y" ) ) {
                    easyPayClient.easypay_deli_us( easypay_mgr_data_item, "mgr_txtype", "40"   );
                }
                else {
                    easyPayClient.easypay_deli_us( easypay_mgr_data_item, "mgr_txtype",  "61"   );
                    easyPayClient.easypay_deli_us( easypay_mgr_data_item, "mgr_subtype", "ES02" );
                }
                easyPayClient.easypay_deli_us( easypay_mgr_data_item, "org_cno",  r_cno     );
                easyPayClient.easypay_deli_us( easypay_mgr_data_item, "order_no", order_no  );
                easyPayClient.easypay_deli_us( easypay_mgr_data_item, "req_ip",   saleson.common.utils.CommonUtils.getClientIp());
                easyPayClient.easypay_deli_us( easypay_mgr_data_item, "req_id",   "MALL_R_TRANS" );
                easyPayClient.easypay_deli_us( easypay_mgr_data_item, "mgr_msg",  "DB ó�� ���з� �����"  );

                easyPayClient.easypay_run( mall_id, tr_cd, order_no );

                res_cd = easyPayClient.res_cd;
                res_msg = easyPayClient.res_msg;
                r_cno = easyPayClient.easypay_get_res( "cno"              );    // PG�ŷ���ȣ
                r_canc_date = easyPayClient.easypay_get_res( "canc_date"  );    //����Ͻ�
            }
        }
    }
%>
<html>
<meta name="robots" content="noindex, nofollow">
<script type="text/javascript">
    function f_submit(){
        document.frm.submit();
    }
</script>

<body onload="f_submit();">
<form name="frm" method="post" action="./result.jsp">
    <input type="hidden" id="res_cd"           name="res_cd"          value="<%=res_cd%>">              <!-- ����ڵ� //-->
    <input type="hidden" id="res_msg"          name="res_msg"         value="<%=res_msg%>">             <!-- ����޽��� //-->
    <input type="hidden" id="cno"              name="cno"             value="<%=r_cno%>">               <!-- PG�ŷ���ȣ //-->
    <input type="hidden" id="amount"           name="amount"          value="<%=r_amount%>">            <!-- �� �����ݾ� //-->
    <input type="hidden" id="order_no"         name="order_no"        value="<%=r_order_no%>">          <!-- �ֹ���ȣ //-->
    <input type="hidden" id="auth_no"          name="auth_no"         value="<%=r_auth_no%>">           <!-- ���ι�ȣ //-->
    <input type="hidden" id="tran_date"        name="tran_date"       value="<%=r_tran_date%>">         <!-- �����Ͻ� //-->
    <input type="hidden" id="escrow_yn"        name="escrow_yn"       value="<%=r_escrow_yn%>">         <!-- ����ũ�� ������� //-->
    <input type="hidden" id="complex_yn"       name="complex_yn"      value="<%=r_complex_yn%>">        <!-- ���հ��� ���� //-->
    <input type="hidden" id="stat_cd"          name="stat_cd"         value="<%=r_stat_cd%>">           <!-- �����ڵ� //-->
    <input type="hidden" id="stat_msg"         name="stat_msg"        value="<%=r_stat_msg%>">          <!-- ���¸޽��� //-->
    <input type="hidden" id="pay_type"         name="pay_type"        value="<%=r_pay_type%>">          <!-- �������� //-->
    <input type="hidden" id="mall_id"          name="mall_id"         value="<%=r_mall_id%>">           <!-- ������ Mall ID //-->
    <input type="hidden" id="card_no"          name="card_no"         value="<%=r_card_no%>">           <!-- ī���ȣ //-->
    <input type="hidden" id="issuer_cd"        name="issuer_cd"       value="<%=r_issuer_cd%>">         <!-- �߱޻��ڵ� //-->
    <input type="hidden" id="issuer_nm"        name="issuer_nm"       value="<%=r_issuer_nm%>">         <!-- �߱޻�� //-->
    <input type="hidden" id="acquirer_cd"      name="acquirer_cd"     value="<%=r_acquirer_cd%>">       <!-- ���Ի��ڵ� //-->
    <input type="hidden" id="acquirer_nm"      name="acquirer_nm"     value="<%=r_acquirer_nm%>">       <!-- ���Ի�� //-->
    <input type="hidden" id="install_period"   name="install_period"  value="<%=r_install_period%>">    <!-- �Һΰ��� //-->
    <input type="hidden" id="noint"            name="noint"           value="<%=r_noint%>">             <!-- �����ڿ��� //-->
    <input type="hidden" id="part_cancel_yn"   name="part_cancel_yn"  value="<%=r_part_cancel_yn%>">    <!-- �κ���� ���ɿ��� //-->
    <input type="hidden" id="card_gubun"       name="card_gubun"      value="<%=r_card_gubun%>">        <!-- �ſ�ī�� ���� //-->
    <input type="hidden" id="card_biz_gubun"   name="card_biz_gubun"  value="<%=r_card_biz_gubun%>">    <!-- �ſ�ī�� ���� //-->
    <input type="hidden" id="cpon_flag"        name="cpon_flag"       value="<%=r_cpon_flag%>">         <!-- ����������� //-->
    <input type="hidden" id="bank_cd"          name="bank_cd"         value="<%=r_bank_cd%>">           <!-- �����ڵ� //-->
    <input type="hidden" id="bank_nm"          name="bank_nm"         value="<%=r_bank_nm%>">           <!-- ����� //-->
    <input type="hidden" id="account_no"       name="account_no"      value="<%=r_account_no%>">        <!-- ���¹�ȣ //-->
    <input type="hidden" id="deposit_nm"       name="deposit_nm"      value="<%=r_deposit_nm%>">        <!-- �Ա��ڸ� //-->
    <input type="hidden" id="expire_date"      name="expire_date"     value="<%=r_expire_date%>">       <!-- ���»�븸���� //-->
    <input type="hidden" id="cash_res_cd"      name="cash_res_cd"     value="<%=r_cash_res_cd%>">       <!-- ���ݿ����� ����ڵ� //-->
    <input type="hidden" id="cash_res_msg"     name="cash_res_msg"    value="<%=r_cash_res_msg%>">      <!-- ���ݿ����� ����޼��� //-->
    <input type="hidden" id="cash_auth_no"     name="cash_auth_no"    value="<%=r_cash_auth_no%>">      <!-- ���ݿ����� ���ι�ȣ //-->
    <input type="hidden" id="cash_tran_date"   name="cash_tran_date"  value="<%=r_cash_tran_date%>">    <!-- ���ݿ����� �����Ͻ� //-->
    <input type="hidden" id="cash_issue_type"  name="cash_issue_type" value="<%=r_cash_issue_type%>">   <!-- ���ݿ���������뵵 //-->
    <input type="hidden" id="cash_auth_type"   name="cash_auth_type"  value="<%=r_cash_auth_type%>">    <!-- �������� //-->
    <input type="hidden" id="cash_auth_value"  name="cash_auth_value" value="<%=r_cash_auth_value%>">   <!-- ������ȣ //-->
    <input type="hidden" id="auth_id"          name="auth_id"         value="<%=r_auth_id%>">           <!-- PhoneID //-->
    <input type="hidden" id="billid"           name="billid"          value="<%=r_billid%>">            <!-- ������ȣ //-->
    <input type="hidden" id="mobile_no"        name="mobile_no"       value="<%=r_mobile_no%>">         <!-- �޴�����ȣ //-->
    <input type="hidden" id="mob_ansim_yn"     name="mob_ansim_yn"    value="<%=r_mob_ansim_yn%>">      <!-- �Ƚɰ��� ������� //-->
    <input type="hidden" id="ars_no"           name="ars_no"          value="<%=r_ars_no%>">            <!-- ��ȭ��ȣ //-->
    <input type="hidden" id="cp_cd"            name="cp_cd"           value="<%=r_cp_cd%>">             <!-- ����Ʈ��/������ //-->
    <input type="hidden" id="cpon_auth_no"     name="cpon_auth_no"    value="<%=r_cpon_auth_no%>">      <!-- �������ι�ȣ //-->
    <input type="hidden" id="cpon_tran_date"   name="cpon_tran_date"  value="<%=r_cpon_tran_date%>">    <!-- ���������Ͻ� //-->
    <input type="hidden" id="cpon_no"          name="cpon_no"         value="<%=r_cpon_no%>">           <!-- ������ȣ //-->
    <input type="hidden" id="remain_cpon"      name="remain_cpon"     value="<%=r_remain_cpon%>">       <!-- �����ܾ� //-->
    <input type="hidden" id="used_cpon"        name="used_cpon"       value="<%=r_used_cpon%>">         <!-- ���� ���ݾ� //-->
    <input type="hidden" id="rem_amt"          name="rem_amt"         value="<%=r_rem_amt%>">           <!-- �ܾ� //-->
    <input type="hidden" id="bk_pay_yn"        name="bk_pay_yn"       value="<%=r_bk_pay_yn%>">         <!-- ��ٱ��� �������� //-->
    <input type="hidden" id="canc_acq_date"    name="canc_acq_date"   value="<%=r_canc_acq_date%>">     <!-- ��������Ͻ� //-->
    <input type="hidden" id="canc_date"        name="canc_date"       value="<%=r_canc_date%>">         <!-- ����Ͻ� //-->
    <input type="hidden" id="refund_date"      name="refund_date"     value="<%=r_refund_date%>">       <!-- ȯ�ҿ����Ͻ� //-->

</form>
</body>
</html>
