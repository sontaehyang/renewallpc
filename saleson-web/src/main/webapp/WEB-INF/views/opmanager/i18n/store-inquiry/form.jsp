<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>
<%@ taglib prefix="daum"	tagdir="/WEB-INF/tags/daum" %>
<style>
span.require {color: #e84700; margin-left: 5px;}

</style>
		
			<div class="location">
				<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
			</div>
		
			
			
			<form:form modelAttribute="storeInquiry" method="post" enctype="multipart/form-data">
				<form:hidden path="storeInquiryId" />			
				<div class="item_list">
					<h3><span>입점문의</span></h3> 
					<div class="board_write">
						<table class="board_write_table">
							<caption>입점문의</caption>
							<colgroup>
								<col style="width:170px;" />
								<col style="width:auto;" />
								<col style="width:170px;" />
								<col style="width:auto;" />
							</colgroup>
							<tbody>
								<tr>
									<td class="label">상태</td> 
									<td colspan="3"> 
										<div>
											<form:radiobutton path="status" value="0" label="접수완료"  />
											<form:radiobutton path="status" value="1" label="처리중"  />
											<form:radiobutton path="status" value="2" label="처리완료"  />	 
									    </div>
									</td>							
								</tr>
								<tr>
									<td class="label">업체명</td>	
									<td>
										<div>
											${storeInquiry.company}											
									    </div>
									</td>
									<td class="label">담당자명</td>	
									<td>
										<div>
											${storeInquiry.userName}											
									    </div>
									</td>		
								</tr>	
								<tr>
									<td class="label">담당자 연락처</td>	
									<td>
										<div>
											${storeInquiry.phoneNumber}											
									    </div>
									</td>
									<td class="label">담당자 이메일</td>	
									<td>
										<div>
											${storeInquiry.email}											
									    </div>
									</td>		
								</tr>
								<tr>
									<td class="label">홈페이지 URL</td>
									<td colspan="3"> 
										<div>
											<a target="blank" href="http://${storeInquiry.homepage}">${storeInquiry.homepage}</a>
									    </div>
									</td>							
								</tr>
								<tr>
									<td class="label">판매하고자 하는</br>제품</td>
									<td colspan="3"> 
										<div>
											${op:nl2br(storeInquiry.content)}
									    </div>
									</td>							
								</tr>
								<c:if test="${storeInquiry.fileName != null}">
								<tr>
									<td class="label">첨부파일</td>
									<td colspan="3"> 
										<div>
											<%-- <a href="javascript:download('${storeInquiryId}')">${storeInquiry.fileName}</a> --%>
											<a href="/upload/store-inquiry/${storeInquiry.storeInquiryId}.${extension}" download="${storeInquiry.fileName}">${storeInquiry.fileName}</a>
									    </div>
									</td>							
								</tr>
								</c:if>
							</tbody>
						</table>
					</div>
				</div>
				<div class="btn_center">
					<button type="submit" class="btn btn-active"><span>${op:message('M00101')}</span></button> <!-- 저장 -->		
					<a href="/opmanager/store-inquiry/list" class="btn btn-default"><span>${op:message('M00037')}</span></a> <!-- 취소 --> 
				</div>
			</form:form>
			

<!-- 다음 주소검색 -->
<daum:address />
<script type="text/javascript">

$(function() {
	
});
</script>
