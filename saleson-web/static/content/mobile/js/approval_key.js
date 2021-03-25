    var ajax_flag = true;
    var req01_AJAX;
    var PayUrl="";

    function initRequest()
    {
        if ( window.XMLHttpRequest )
        {
            return new XMLHttpRequest();
        } else if ( window.ActiveXObject )
        {
            return new ActiveXObject( "Microsoft.XMLHTTP" );
        }
    }

    function sendRequest( url )
    {
        req01_AJAX = null;
        req01_AJAX = initRequest();

        if ( req01_AJAX )
        {
            req01_AJAX.onreadystatechange = process_AJAX;
            req01_AJAX.open( "POST", url, true );
            req01_AJAX.send( null );
        }
    }

    function kcp_AJAX()
    {
        // 해당 소스는 jQuery를 사용 하지 않는 업체에서도 작동 할수 있도록 구현되었습니다.
        // 이 부분은 javascript로 구현시 jQuery를 이용하면 더욱 쉽게 구현 할수 있습니다. 
        // 또는
        // javascript를 통한 ajax 통신을 할 필요 없이 소스내에 order_approval.jsp를 연동해서 사용 하셔도 관계 없으며
        // 제공되는 jar 파일에 직접 전달된 데이터를 사용할수 있도록 method가 구현 되어 있습니다.
        if(ajax_flag)
        {
            var url    = "/m/order/kcp/order_approval";
            var form = document.order_info;
            var params = "?site_cd=" + form.site_cd.value
                       + "&ordr_idxx=" + form.ordr_idxx.value
                       + "&good_mny=" + form.good_mny.value
                       + "&pay_method=" + form.pay_method.value
                       + "&escw_used=" + form.escw_used.value
                       + "&good_name=" + form.good_name.value
                       + "&response_type=" + form.response_type.value
                       + "&Ret_URL=" + form.Ret_URL.value;
            sendRequest( url + params );
            
            ajax_flag = false;
        }
        else
        {
            alert("통신 중입니다. 잠시만 기다려 주세요.");
        }
            
    }

    function process_AJAX()
    {
        if ( req01_AJAX.readyState == 4 ) //READY_STATE_COMPLETE = 4
        {
            if ( req01_AJAX.status == 200 )
            {
                if ( req01_AJAX.responseText != null )
                {
                    // 해당 소스는 jQuery를 사용 하지 않는 업체에서도 작동 할수 있도록 구현되었습니다.
                    // 이 부분은 javascript로 구현시 jQuery를 이용하면 더욱 쉽게 구현 할수 있습니다. 
                    // 또는
                    // javascript를 통한 ajax 통신을 할 필요 없이 소스내에 order_approval.jsp를 연동해서 사용 하셔도 관계 없으며
                    // 제공되는 jar 파일에 직접 전달된 데이터를 사용할수 있도록 method가 구현 되어 있습니다.
                    try
                    {
                        var form = document.order_info;
                        
                        if( form.response_type.value == "JSON" )
                        {
                            StateChangeForJSON( req01_AJAX ); // JSON 방식일 경우
                        }
                        else if( form.response_type.value == "XML" )
                        {
                            StateChangeForXML( req01_AJAX ); // XML 방식일 경우
                        }
                        else if( form.response_type.value == "TEXT" || form.response_type.value == "" )
                        {
                            StateChangeForText( req01_AJAX ); // TEXT 방식일 경우
                        }
                    }
                    catch( e )
                    {
                        StateChangeForText( req01_AJAX ); // TEXT 방식일 경우
                    }
                }
            }
            else
            {
                ajax_flag=true;
                alert( req01_AJAX.responseJSON );
            }
        }
    }
    
    function StateChangeForText( req )
    {
        var txt = req01_AJAX.responseText.split( ":" );
        
        if( txt[0].replace(/^\s*/,'').replace(/\s*$/,'') == '0000' )
        {
            document.getElementById( "approval" ).value = txt[1].replace(/^\s*/,'').replace(/\s*$/,'');
            
            // 아래 alert는 삭제 해도됨
//            alert("성공적으로 거래가 등록 되었습니다.");
            PayUrl = decodeURIComponent( txt[2].replace(/^\s*/,'').replace(/\s*$/,'') );
            
            document.getElementById( "traceNo" ).value = txt[3].replace(/^\s*/,'').replace(/\s*$/,'');
            
            if( txt.length == 6 )
            document.getElementById( "PayUrl"  ).value = txt[5].replace(/^\s*/,'').replace(/\s*$/,'');
            
            call_pay_form();
        }
        else
        {
            var res_msg = txt[2].replace(/^\s*/,'').replace(/\s*$/,'');
            
            ajax_flag=true;
            alert("실패 되었습니다.[" + res_msg + "]");
        }
    }
    
    function StateChangeForXML( req )
    {
        var xmlDoc = ( new DOMParser() ).parseFromString( req.responseText.replace(/^\s*/,'').replace(/\s*$/,''), "text/xml" ).documentElement;
        
        var x = xmlDoc.getElementsByTagName( "data" );
        
        if( x[0].getElementsByTagName( "Code" )[0].childNodes[0].nodeValue == '0000' )
        {
            document.getElementById( "approval" ).value = x[0].getElementsByTagName( "approvalKey" )[0].childNodes[0].nodeValue;
            // 아래 alert는 삭제 해도됨
//            alert("성공적으로 거래가 등록 되었습니다.");
            PayUrl = x[0].getElementsByTagName( "PayUrl" )[0].childNodes[0].nodeValue;
            
            document.getElementById( "traceNo" ).value = x[0].getElementsByTagName( "traceNo"     )[0].childNodes[0].nodeValue;
            
            try{
                document.getElementById( "PayUrl"  ).value = x[0].getElementsByTagName( "request_URI" )[0].childNodes[0].nodeValue;
            }catch(e){}
            
            call_pay_form();
        }
        else
        {
            ajax_flag=true;
            
            alert("실패 되었습니다.[" + x[0].getElementsByTagName( "Message" )[0].childNodes[0].nodeValue + "]");
        }
    }
    
    
    function StateChangeForJSON( xmlHttpRequest )
    {
    	var json = eval('('+ xmlHttpRequest.responseText +')');

        if( json.Code == '0000' )
        {
            document.getElementById( "approval" ).value = json.approvalKey;
            $('input#approval').val(json.approvalKey);
            // 아래 alert는 삭제 해도됨
//            alert("성공적으로 거래가 등록 되었습니다.");
            PayUrl = json.PayUrl;
            
            document.getElementById( "PayUrl"  ).value = json.request_URI;
            document.getElementById( "traceNo" ).value = json.traceNo;
            
            call_pay_form();
        }
        else
        {
            ajax_flag=true;
            
            alert("실패 되었습니다.[" + json.Message + "]");
        }
    }