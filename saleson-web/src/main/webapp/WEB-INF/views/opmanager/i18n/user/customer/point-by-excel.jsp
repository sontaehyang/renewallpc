<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>

			<!-- 본문 -->
		<div class="popup_wrap">
			<h1 class="popup_title">${op:message('M00826')}</h1> <!-- 포인트 지급 -->
			<form:form modelAttribute="point" method="post" enctype="multipart/form-data">
				<div class="popup_contents">
					<h2>¶ ${op:message('M00826')} <a href="../../../../content/sample/point_sample.xlsx" class="table_btn" style="float: right;margin-bottom: 10px;" download><span>${op:message('M01280')}</span></a> <!-- 샘플 다운로드 --></h2> <!-- 포인트 지급 -->
						
					<table cellpadding="0" cellspacing="0" summary="" class="board_list_table">
						<caption>${op:message('M00246')} 지급</caption>
						<colgroup>
							<col style="width:35%;" />
							<col style="width:*" />
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">${op:message('M01070')}</th> <!-- 파일  -->
								<td class="tleft">
									<p class="default_file"><input type="file" class="upload" id="uploadInputBox" name="file" /></p>
								</td>
							</tr>
							<!-- <tr>
								<th scope="row">적립형태</th>
								<td class="tleft">
									<div>
										<input type="radio" name="pointType" id="point_plus" value="1" class="required" checked="checked" /> <label for="point_plus">지급</label>
										<input type="radio" name="pointType" id="point_minus" value="2" class="required" /> <label for="point_minus">차감</label>
									</div>
								</td>
							</tr> -->
							<tr>
								<th scope="row">${op:message('M01106')}</th> <!-- 지급 포인트 -->
								<td class="tleft">
									<div>
										<input type="text" name="point" class="required _number full" title="${op:message('M01106')}" style="width:200px;" /> P 
								 	</div>
								</td>
							</tr>
							<tr>
								<th scope="row">${op:message('M01107')}</th> <!-- 포인트 지급 내용 -->
								<td class="tleft">
									<div class="mt10">
										<textarea name="reason" cols="30" rows="10" title="${op:message('M01107')}" style="width:350px;"></textarea>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
					 
					<p class="popup_btns">
						<button type="submit" class="btn btn-active">${op:message('M00101')}</button> <!-- 저장 -->
						<a href="javascript:self.close();" class="btn btn-default">${op:message('M00037')}</a> <!-- 취소 -->
					</p>	 
				</div><!--//popup_contents E-->
			</form:form>
			<a href="javascript:self.close();" class="popup_close">창 닫기</a>
		</div>
		
	<script type="text/javascript">
		$(function(){
			$("#pointForm").validator(function(){
				var message = "ポイントを支給しますか？";	// 포인트를 지급하시겠습니까?
				Common.confirm(message, function(){
					$("#pointForm").submit();
				});
				return false;
			});
		});
	</script>