<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>


			<div class="location">
				<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
			</div>

			<h3><span>${op:message('M00197')}</span></h3>
			
			<div class="board_write">
				<form:form modelAttribute="searchParam" cssClass="opmanager-search-form clear" method="post">
					<fieldset>
						<legend class="hidden">${op:message('M00048')}</legend>
						
						<form:hidden path="sort" />
						<form:hidden path="orderBy" />
						
						<table class="board_write_table ">
							<caption>${op:message('M00197')}</caption>
							<colgroup>
								<col style="width:150px;" />
								<col style="width:auto;" />
							</colgroup>
							<tr>
								<td class="label">${op:message('M00197')}</td>
								<td>
									<div>
										<form:hidden path="where" value="BAN_WORD" title="${op:message('M00198')}" />
										<form:input path="query" title="${op:message('M00197')}" cssClass="full" />						 
								    </div>						    
								</td>
							</tr>					 
						</table>	
						
						<!-- 버튼시작 -->
						<div class="btn_all">
							<div class="btn_left">
								<button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='/opmanager/ban-word/list';"><span>${op:message('M00047')}</span></button>
							</div>
							<div class="btn_right">
								<button type="submit" class="btn btn-dark-gray btn-sm"><span>${op:message('M00048')}</span></button>
							</div>
						</div>
						<!-- 버튼 끝-->
						
					</fieldset>
				</form:form>
				
				<form id="banWord" action="${op:url('/opmanager/ban-word/create')}" method="post">
					<table class="board_write_table mt20">
						<caption>${op:message('M00197')}</caption>
						<colgroup>
							<col style="width:150px;" />
							<col style="width:auto;" />
						</colgroup>
						<tr>
							<td class="label">${op:message('M00197')}</td>
							<td>
								<div>
									<input type="text" name="banWord" class="required seven" title="${op:message('M00197')}" />
									&nbsp;
									<button type="submit" class="btn btn-dark-gray btn-sm"><span>${op:message('M00088')}</span></button>
							    </div>
							    
							</td>
						</tr>					 
					</table>
				</form>
										
			</div> <!-- // board_write -->
			
			
			
				
				<!-- 리스트 테이블 시작-->
				<div class="board_list">		
					<form id="listForm">		
						<table class="board_list_table">
							<caption>${op:message('M00199')}</caption>
							<colgroup>							
								<col style="width:10%;">
								<col style="width:auto;">
								<col style="width:12%;">
								<col style="width:12%;">
								<col style="width:12%;">
							</colgroup>
							<thead>
								<tr>
									<th>${op:message('M00200')}</th>
									<th>${op:message('M00197')}</th>
									<th>${op:message('M00201')}</th>
									<th>${op:message('M00202')}</th>
									<th>${op:message('M00074')}</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${list}" var="banWord" varStatus="i">

								<tr>
									<td>${pagination.itemNumber - i.count}</td>
									<td>
										<div>
											${banWord.banWord}
										</div>
									</td>
									<td>${banWord.userName}</td>
									<td>${op:date(fn:substring(banWord.creationDate, 0, 10))}</td>
									<td>
										<a href="javascript:deleteBanWord(${banWord.banWordId})" title="${op:message('M00203')} " class="table_btn">${op:message('M00074')}</a>
									</td>
								</tr>
								
								</c:forEach>
								
							</tbody>
						</table>		
					</form>		
					<c:if test="${empty list}">
					<div class="no_content">
						${op:message('M00204')}
					</div>
					</c:if>
				</div><!--//board_list E-->
				
				<page:pagination-manager /> 
			


				<div class="board_guide ml10">
					<p class="tip">Tip</p>
					<p class="tip">- ${op:message('M01477')}</p> <!-- 운영자를 등록한 단어는 상품 후기(리뷰)시 체크되는데 사용됩니다. -->
					<p class="tip">- ${op:message('M01478')}</p> <!-- 유해한 불량단어를 등록할 수 있습니다. -->
				</div>
				
				
<script type="text/javascript">
$(function() {
	$('#banWord').validator(function() {
		Common.confirm("${op:message('M00159')}", function(form) {
			$('#banWord').submit();
		});
		return false;
	});
});
			


function deleteBanWord(banWordId) {
	Common.confirm("${op:message('M00196')} ", function() {
		$.post(url("/opmanager/ban-word/delete/" + banWordId), {}, function(response) {
			Common.responseHandler(response, function() {
				alert("${op:message('M00205')}");
				location.reload();
			});
		});
	});
	
}

</script>			
			
			