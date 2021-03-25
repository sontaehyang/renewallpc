<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>

<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
</head>

<body onload="" oncontextmenu="return false;" ondragstart="return false;" onselectstart="return false;">
<table>
    <div id="sample_wrap">
    	<form name="v3d_form" method="post" action="../pay">
			<c:forEach items="${key}" var="key">
				<input type="hidden" name="${key}" value="">
			</c:forEach>
    	</form>
	</div>
</table>	
<script type="text/javascript">

function jsf__chk_type() {
    if ( document.v3d_form.card_code.value == "CCBC"||document.v3d_form.card_code.value == "CCKM"||document.v3d_form.card_code.value == "CCSU"||
	    document.v3d_form.card_code.value == "CCJB"||document.v3d_form.card_code.value == "CCKJ"||document.v3d_form.card_code.value == "CCPH"||
	    document.v3d_form.card_code.value == "CCSM"||document.v3d_form.card_code.value == "CCPB"||document.v3d_form.card_code.value == "CCSB"||
	    document.v3d_form.card_code.value == "CCKD"||document.v3d_form.card_code.value == "CCCJ"||document.v3d_form.card_code.value == "CCCU" ) {
    	
	   document.v3d_form.card_pay_method.value = "ISP";
	} else if ( document.v3d_form.card_code.value == "CCLG"||document.v3d_form.card_code.value == "CCDI"||document.v3d_form.card_code.value == "CCSS"||
		document.v3d_form.card_code.value == "CCKE"||document.v3d_form.card_code.value == "CCLO"||document.v3d_form.card_code.value == "CCCT"||
	    document.v3d_form.card_code.value == "CCNH"||document.v3d_form.card_code.value == "CCHN" ) {
		
		document.v3d_form.card_pay_method.value = "V3D";
	}
	   
}     

function iFrameUsed( frm ) {
    authViewSelect( "I" ); //I=ifrmae, P=Popup
    
    return jsf__pay_v3d(frm);
}

function  jsf__pay_v3d( form ) {
//     if (jsf__chk_v3d_card( form ) == true) {
    	
        KCP_Pay_Execute( form );
//     }
    
    return false ;
}

function m_Completepayment( frm_mpi, closeEvent ){
    var frm = document.v3d_form;
    
    if( frm_mpi.res_cd.value == "0000" )
    {
        GetField(frm, frm_mpi);

		createJson();

        closeEvent();        
    }
    else
    {
        closeEvent();
        
        setTimeout( "alert( \"[" + frm_mpi.res_cd.value + "]" + frm_mpi.res_msg.value  + "\");parent.Common.loading.hide();", 1000 );
    }
}
</script>

<script type="text/javascript" src="${op:property('pg.kcp.g.conf.hub.js.url')}"></script>

<script type="text/javascript">

$(document).ready(function(){
		
	var json = JSON.parse('${json}');
	
	var frm = $('form[name="v3d_form"]');
	
	$.each(json, function(key, val){ 
		$('input[name="'+key+'"]').val(val); 
	});
	
	frm.submit(function(e){
		var frm = document.v3d_form;
	
		jsf__chk_type();
	
		iFrameUsed(frm);

		e.preventDefault();
	});
	
	frm.submit();
	
})

// KCP결제창을 통해 받은 응답데이터를 Json객체로 만들어 부모페이지에 전달
function createJson(){

	var jsonObj={};
	$.each($('div#sample_wrap').find('input'), function(){
		jsonObj[$(this).attr('name')] = $(this).val();		
	});
	
	jsonObj = JSON.stringify(jsonObj);

	parent.goSubmit(jsonObj);
}

</script>
</body>
</html>