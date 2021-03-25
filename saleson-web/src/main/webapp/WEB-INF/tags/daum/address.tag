<%@ tag pageEncoding="utf-8" %>
<script type="text/javascript">
	var daumApiUrl = 'http://dmaps.daum.net/map_js_init/postcode.v2.js';
	if (window.location.protocol == 'https:') {
		daumApiUrl = 'https://spi.maps.daum.net/imap/map_js_init/postcode.v2.js';
	}
	document.write('<script src="'+ daumApiUrl +'">' + '</' + 'script>');
</script>

<script type="text/javascript">
function openDaumAddress(tagNames, callBack) {
	
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
		'roadAddress'			: 'address',
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
		        
		        
		        
	    	} catch (e) {
				alert(e.message);
			}
	    	
	    }
	}).open();
}
</script>