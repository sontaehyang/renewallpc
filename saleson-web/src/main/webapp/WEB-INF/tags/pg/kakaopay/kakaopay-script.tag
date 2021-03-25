<%@ tag pageEncoding="utf-8" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>


<!-- OpenSource Library -->
<script src="https://pg.cnspay.co.kr:443/dlp/scripts/lib/easyXDM.min.js" type="text/javascript"></script>
<script src="https://pg.cnspay.co.kr:443/dlp/scripts/lib/json3.min.js" type="text/javascript"></script>


<!-- DLP창에 대한 KaKaoPay Library -->
<script src="${op:property('kakaopay.web.path')}/js/dlp/client/kakaopayDlpConf.js" charset="utf-8"></script>
<script src="${op:property('kakaopay.web.path')}/js/dlp/client/kakaopayDlp.min.js" charset="utf-8"></script>

<link href="https://pg.cnspay.co.kr:443/dlp/css/kakaopayDlp.css" rel="stylesheet" type="text/css" />


<script type="text/javascript">
	function kakaopay() {
		
		if ($('#kakaopay_layer').size() == 0) {
			$('body').append('<div id="kakaopay_layer"  style="display: none"></div>');
		}
		
		if(document.buy.resultCode.value == '00') {
            // TO-DO : 가맹점에서 해줘야할 부분(TXN_ID)과 KaKaoPay DLP 호출 API
            kakaopayDlp.setTxnId(document.buy.txnId.value);
            
            //kakaopayDlp.setChannelType('WPM', 'TMS'); // PC결제
            //kakaopayDlp.setChannelType('MPM', 'WEB'); // 모바일 웹(브라우저)결제
            //kakaopayDlp.addRequestParams({ MOBILE_NUM : '010-9109-9252'}); // 초기값 세팅
            
            kakaopayDlp.callDlp('kakaopay_layer', document.buy, submitFunc);
            
        }	
	}
    /**
    cnspay  를 통해 결제를 시작합니다.
    */
    function cnspay() {
        
        // TO-DO : 가맹점에서 해줘야할 부분(TXN_ID)과 KaKaoPay DLP 호출 API
        // 결과코드가 00(정상처리되었습니다.)
        if(document.payForm.resultCode.value == '00') {
            // TO-DO : 가맹점에서 해줘야할 부분(TXN_ID)과 KaKaoPay DLP 호출 API
            kakaopayDlp.setTxnId(document.payForm.txnId.value);
            
            kakaopayDlp.setChannelType('WPM', 'TMS'); // PC결제
            //kakaopayDlp.setChannelType('MPM', 'WEB'); // 모바일 웹(브라우저)결제
            //kakaopayDlp.addRequestParams({ MOBILE_NUM : '010-1234-5678'}); // 초기값 세팅
            
            kakaopayDlp.callDlp('kakaopay_layer', document.payForm, submitFunc);
            
        } else {
            alert('[RESULT_CODE] : ' + document.payForm.resultCode.value + '\n[RESULT_MSG] : ' + document.payForm.resultMsg.value);
        }
        
    }

/*   
    function getTxnId(){
    	  // 카카오페이는 인증요청시 UTF-8, 결제요청시 utf-8을 사용하며 해당 인코딩값이 맞지않을시 한글이 깨질 수 있습니다.
        
        // form에 iframe 주소 세팅
        document.payForm.target = "txnIdGetterFrame";
        document.payForm.action = "getTxnId.jsp";
        document.payForm.acceptCharset = "utf-8";
        if (payForm.canHaveHTML) { // detect IE
            document.charset = payForm.acceptCharset;
        }
        
        // post로 iframe 페이지 호출
        document.payForm.submit();
        
        // payForm의 타겟, action을 수정한다
        document.payForm.target = "";
        document.payForm.action = "kakaopayLiteResult.jsp";
        document.payForm.acceptCharset = "utf-8";
        if (payForm.canHaveHTML) { // detect IE
            document.charset = payForm.acceptCharset;
        }
        // getTxnId.jsp의 onload 이벤트를 통해 cnspay() 호출
        
    }
*/
    var submitFunc = function cnspaySubmit(data){
        
        if(data.RESULT_CODE === '00') {
            
            // 매뉴얼 참조하여 부인방지코드값 관리
            
            document.buy.submit();
            
        } else if(data.RESULT_CODE === 'KKP_SER_002') {
            // X버튼 눌렀을때의 이벤트 처리 코드 등록
            alert('[RESULT_CODE] : ' + data.RESULT_CODE + '\n[RESULT_MSG] : ' + data.RESULT_MSG);
        } else {
            alert('[RESULT_CODE] : ' + data.RESULT_CODE + '\n[RESULT_MSG] : ' + data.RESULT_MSG);
        }
        
    };
    
</script>