<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${main != 'main'}">
		</div>
	</div>
</c:if>
</div><!--// container E-->
<!-- 하단 -->
<div id="footer">
	<span class="copy">COPYRIGHT (C) ONLINEPOWERS ALL RIGHTS RESERVED.</span>
	<span class="info">T : 02-6737-9200</span>
	<span class="info">F : 02-6737-3330</span>
	<span class="info">E : help@onlinepowers.com</span>
</div>
	

<!-- 비동기 업로드 처리.
<style>
#async_report {display: none; position: fixed; left:0; bottom: -220px; height: 250px; width: 250px; border: 1px solid #000; background: #fff;}
#async_report .async_header {padding: 10px 20px; background: #4b4e4e; color: #fff;}


</style>
<div id="async_report">
	<div class="async_header">
		CSV 처리중...
	</div>
	<div class="message">
		처리 중입니다.
	</div>
</div>	
<script type="text/javascript">
$(function() {
	asysnCheckerInit();
});

function asysnCheckerInit() {
	// Check The Status Every 2 Seconds  
	var timer = setInterval(function() {  
		Common.loading.display = false;
        $.ajax({  
        	url: '/opmanager/item/upload-csv-status',  
        	success: function(response) {  
           		if (response.data.status === 'NOTHING') {
            		clearInterval(timer);  
            		
            		$('#async_report').hide();
            	
           		} else if (response.data.status === 'COMPLETE') {  
                    $('#reportLink').html("<a target='_target' href='report.html'>Download Report</a>");  

					$('#async_report .message').html(response.data.message);
                    Common.loading.display = true;
                    clearInterval(timer);  
                    
                    $('#async_report').show().css('bottom', '0px');
                    
                }  else {
                	
                	$('#async_report').show().css('bottom', '-220px');
                }
        	}  
        });  
          
    }, 2000); 
} 
</script>
-->