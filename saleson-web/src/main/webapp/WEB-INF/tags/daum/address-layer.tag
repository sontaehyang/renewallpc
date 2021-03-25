<%@ tag pageEncoding="utf-8" %>
<script type="text/javascript">
	var daumApiUrl = 'http://dmaps.daum.net/map_js_init/postcode.v2.js';
	if (window.location.protocol == 'https:') {
		daumApiUrl = 'https://spi.maps.daum.net/imap/map_js_init/postcode.v2.js';
	}
	document.write('<script src="'+ daumApiUrl +'">' + '</' + 'script>');
		
</script>

<style type="text/css">
.daum_layer_popup {
	display: none;
	position: fixed;
	z-index: 100000;
	width: 98%;
	left: 1%;
	top: 10%;
	background: #fff
}
.daum_layer_popup .layer_title {
	padding: 12px 0;
	text-align: center;
	background: #f2f2f2;
	font-size: 1.6rem;
	line-height: 26px;
	font-weight: 300;
	color: #333;
	border-top: 1px solid #000;
	border-left: 1px solid #000;
	border-right: 1px solid #000;
}
.daum_layer_popup .layer_close {
	position: absolute;
	right: 20px;
	top: 19px;
	display: block;
	width: 16px;
	height: 16px;
	background: url(/content/mobile/images/common/item_order_close.gif) no-repeat center;
	background-size: 100% 100%;
}
</style>

<div id="layerDaumAddr" class="daum_layer_popup" style="display: none;"> 
	<h1 class="layer_title">우편번호 찾기</h1> 
	<div id="daumAddr"></div>
	<button type="button" onclick="javascript:closeDaumPostcode();" class="layer_close"></button>
</div>

<script type="text/javascript">

//우편번호 찾기 화면을 넣을 element
var element_layer = document.getElementById('daumAddr');
var perant_element_layer = document.getElementById('layerDaumAddr');

function openDaumAddress(tagNames, callBack) {
	
	// 부모창 스크롤 정지
	 $('html, body').css('overflowY', 'hidden'); 
	 $('html, body').css('-webkit-overflow-scrolling', 'touch');
	 Common.loading.show();
	
	
	 var defaultTagNames = {
		'newZipcode'			: 'newZipcode',
		'zipcode1' 				: 'zipcode1',
		'zipcode2' 				: 'zipcode2',
		'zipcode' 				: 'zipcode',
		'sido'					: 'sido',
		'sigungu'				: 'sigungu',
		'eupmyeondong'			: 'eupmyeondong',
		'jibunAddress'			: 'address',
		'jibunAddressDetail' 	: 'addressDetail',
		'roadAddress'			: 'roadAddress',
		'buildingCode'			: 'buildingCode'
	}
	
	if (tagNames != undefined) {
		$.each(defaultTagNames, function(key, value) {
			if (tagNames[key] != undefined) {
				defaultTagNames[key] = tagNames[key];
			}
		});
	}
	
	new daum.Postcode({
	    oncomplete: function(data) {	        	
	    	try {
	    		
	    		var post = data.postcode;
	    		if (post == '') {
	    			post = data.zonecode;
	    		}
	    		
		        $('input[name="'+ defaultTagNames.newZipcode +'"]').val(data.zonecode);
		        $('input[name="'+ defaultTagNames.zipcode +'"]').val(post);
		        $('input[name="'+ defaultTagNames.zipcode1 +'"]').val(data.postcode1);
		        $('input[name="'+ defaultTagNames.zipcode2 +'"]').val(data.postcode2);
		        
		        $('input[name="'+ defaultTagNames.sido +'"]').val(data.sido);
		        $('input[name="'+ defaultTagNames.sigungu +'"]').val(data.sigungu);
		        $('input[name="'+ defaultTagNames.eupmyeondong +'"]').val(data.bname);
				
		        var jibunAddress = data.jibunAddress;
		        if (jibunAddress == '') {
		        	jibunAddress = data.autoJibunAddress;
		        }
		        
		        var roadAddress = data.roadAddress;
		        if (roadAddress == '') {
		        	roadAddress = data.autoRoadAddress;
		        }
		        
		        var addr = jibunAddress;
		        if(data.userSelectedType == 'R'){
		        	addr = roadAddress;
		        }

                if(data.buildingName != ''){
                    addr += ' ('+data.buildingName+')';
                    roadAddress += ' ('+data.buildingName+')';
                }
		        
		        $('input[name="'+ defaultTagNames.jibunAddress +'"]').val(addr);
		        $('input[name="'+ defaultTagNames.jibunAddressDetail +'"]').val("");
		        $('input[name="'+ defaultTagNames.jibunAddressDetail +'"]').focus();
		        
		        
		        $('input[name="'+ defaultTagNames.roadAddress +'"]').val(roadAddress);
		        $('input[name="'+ defaultTagNames.buildingCode +'"]').val(data.buildingCode);
		        
		        if ($.isFunction(callBack)) {
		        	callBack(data);
		        }
		        
		        closeDaumPostcode();
		        
	    	} catch (e) {
				alert(e.message);
			}
	    	
	    },
	    width : '100%',
        height : '100%'
	}).embed(element_layer);
	
	
	// iframe을 넣은 element를 보이게 한다.
	perant_element_layer.style.display = 'block';
    element_layer.style.display = 'block';
    
    // iframe을 넣은 element의 위치를 화면의 가운데로 이동시킨다.
    initLayerPosition();
    
    Common.loading.hide();
    $('#loading-dimmed').show();
}

//브라우저의 크기 변경에 따라 레이어를 가운데로 이동시키고자 하실때에는
// resize이벤트나, orientationchange이벤트를 이용하여 값이 변경될때마다 아래 함수를 실행 시켜 주시거나,
// 직접 element_layer의 top,left값을 수정해 주시면 됩니다.
function initLayerPosition(){
    var width = perant_element_layer.offsetWidth; //우편번호서비스가 들어갈 element의 width
    var height = window.innerHeight*0.7; //우편번호서비스가 들어갈 element의 height
    var borderWidth = 1; //샘플에서 사용하는 border의 두께

    // 위에서 선언한 값들을 실제 element에 넣는다.
    element_layer.style.width = width + 'px';
    element_layer.style.height = height + 'px';
    element_layer.style.border = borderWidth + 'px solid';
    // 실행되는 순간의 화면 너비와 높이 값을 가져와서 중앙에 뜰 수 있도록 위치를 계산한다.
    element_layer.style.left = (((window.innerWidth || document.documentElement.clientWidth) - width)/2 - borderWidth) + 'px';
    element_layer.style.top = (((window.innerHeight || document.documentElement.clientHeight) - height)/2 - borderWidth) + 'px';
}

function closeDaumPostcode() {
    // iframe을 넣은 element를 안보이게 한다.
    perant_element_layer.style.display = 'none';
    element_layer.style.display = 'none';
    
 // 부모창 스크롤 시작
    $('html, body').css('overflowY', 'scroll');
    $('html, body').css('-webkit-overflow-scrolling', 'auto');
    Common.loading.hide();
    $('#loading-dimmed').hide();
}


</script>