<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>

	<h3><span>이메일 설정 리스트</span></h3>
				
	<div class="board_write">
		
		<table class="board_list_table">
			<colgroup>
				<col style="width:5%;">
				<col style="width:12%;">
				<col style="width:auto;">
				<col style="width:14%;">
				<col style="width:14%;">	
				<col style="width:18%;">			
				<col style="width:12%">
			</colgroup>
			<thead>
				<tr>
					<th scope="col">번호</th>
					<th scope="col">코드</th>
					<th scope="col">분류명</th>
					<th scope="col">회원 메일수신</th>
					<th scope="col">관리자 메일수신</th>
					<th scope="col">수정/삭제</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${mailConfigList}" var="list" varStatus="i">
					<tr>
						 <td>${pagination.itemNumber - i.count}</td>
						 <td>${list.templateId}</td>
						 <td>${list.title}</td>
						 <td>${list.buyerSendFlagText }</td>
						 <td>${list.adminSendFlagText }</td>
						 <td><a href="/opmanager/mail-config/edit/${list.mailConfigId}" class="btn_write" title="수정">수정</a> <a href="javascript:;" class="btn_write deleteMail" title="삭제" rel="${list.mailConfigId}">삭제</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>	
		<c:if test="${empty mailConfigList}">
			<div class="no_content">
				<p>등록된 데이터가 없습니다.</p>
			</div>
		</c:if>
	</div> <!-- // board_write -->
	
	<page:pagination-manager />
	
	<p class="btns"> 
		<a href="/opmanager/mail-config/create" class="btn gray04">등록</a>
	</p>
	
	
<script type="text/javascript">
	$(function(){
		$(".deleteMail").on("click",function(){
			var mailConfigId = $(this).attr("rel");
			
			Common.confirm("메일 설정을 삭제 하시겠습니까?", function() {
				location.href="/opmanager/mail-config/delete/"+mailConfigId;
			});
			
		});
	});
</script>

