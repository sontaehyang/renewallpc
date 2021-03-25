<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

CSV

<div id="reportLink">업로드</div>

<form action="/opmanager/item/upload-csv" enctype="multipart/form-data" method="post">
	<input type="file" name="file[]" multiple="multiple" />
	
	<button type="submit">등록 </button>
</form>
<!-- 
<script type="text/javascript">

$(function() {
	// Check The Status Every 2 Seconds  
    var timer = setInterval(function() {  
          
        $.ajax({  
              url: '/opmanager/item/upload-csv-status',  
              success: function(data) {  
                  
                if(data === 'COMPLETE') {  
                    $('#reportLink').html("<a target='_target' href='report.html'>Download Report</a>");      
                    clearInterval(timer);  
                }  
              }  
        });  
          
    }, 2000); 
});

</script>	


 -->	