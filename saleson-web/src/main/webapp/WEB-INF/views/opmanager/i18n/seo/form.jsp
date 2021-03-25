<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

		<div class="location">
			<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
		</div>
		

		<form:form modelAttribute="seo" method="post">		
			<form:hidden path="seoId" />	
			
			<div class="item_list">
			
				<h3><span>SEO 설정 정보 등록</span></h3>

				
				<div class="board_write">
						
					<table class="board_write_table">
						<colgroup>
							<col style="width: 150px;">
							<col style="*">
						</colgroup>
						<tbody>
							<tr>
								<td class="label">URL</td>
								<td>
									<div>
									
										<c:choose>
											<c:when test="${seo.seoId == 0}">
												<form:input path="seoUrl" class="form-block required" maxlength="100" title="URL" />
											</c:when>
											<c:otherwise>
												${seo.seoUrl}
												<input type="hidden" id="seoUrl" name="seoUrl" value="${seo.seoUrl}" />
											</c:otherwise>
										</c:choose>
									</div>
								</td>
							</tr>
                            <tr>
                                <td class="label">검색 엔진에 공개</td>
                                <td>
                                    <div>
                                        <p class="text-info text-sm">
                                            * 검색 엔진에 상품 페이지 공개 여부를 설정합니다.
                                        </p>
                                        <p>
                                            <form:radiobutton path="indexFlag" value="Y" label="노출" />
                                            <form:radiobutton path="indexFlag" value="N" label="노출 안함" />
                                        </p>
                                    </div>
                                </td>
                            </tr>
							<tr>
								<td class="label">${op:message('M00090')}</td> <!-- 브라우저 타이틀 -->
								<td>
									<div>
                                        <p class="text-info text-sm">
                                            * 제목은 브라우저의 상단과 검색 엔진에 페이지 제목으로 나타납니다. <br />
                                            * 제목은 최대 55-60자 까지 가능합니다.  <br />
                                            * 2-5 단어로 페이지를 설명할 수 있는 제목을 선택하세요.<br />
                                            * (예: 상품명, 페이지명 등)
                                            * (예: 상품페이지의 경우 상품명을 입력해 주시면 됩니다.)
                                        </p>
										<form:input path="title" class="form-block required" maxlength="255" title="${op:message('M00090')}" />
									</div>
								</td>
							</tr>
							<tr>
								<td class="label">${op:message('M00997')}</td> <!-- Meta 키워드 -->
								<td>
									<div>
                                        <p class="text-info text-sm">
                                            * 최대 10 키워드로 페이지를 홍보할 수 있습니다. <br />
                                            * 키워드는 웹사이트 콘텐츠를 설명하는 단어나 짧은 구문이며 검색 사이트에서 사용자가 해당 페이지를 검색할 때 사용할 만한 단어를 포함해야 합니다.<br />
                                            * 태그란에 키워드를 입력할 때는 쉼표로 키워드들을 구분합니다.
                                        </p>
										<form:input path="keywords" class="form-block required" maxlength="255" title="${op:message('M00997')}" />
									</div>
								</td>
							</tr>
							<tr>
								<td class="label">${op:message('M00998')}</td> <!-- Meta용 기술서 -->
								<td>
									<div>
                                        <p class="text-info text-sm">
                                            * 해당 문구는 검색 엔진 목록의 사이트 제목 아래에 표시됩니다. <br />
                                            * 페이지 설명은 최대 250자 까지 사용 가능합니다. (또는 약  15-20 단어) <br />
                                            * (예: 페이지 제목이 Google 지도인 경우, 사이트 설명은 "웹에서 지도와 운전 경로를 보고 지역 정보를 검색하세요!"가 될 수 있습니다.)
                                        </p>
										<form:textarea path="description" class="form-block required" rows="3" maxlength="255" title="${op:message('M00998')}" />
									</div>
								</td>
							</tr>
							
							<tr>
								<td class="label">${op:message('M00999')}</td> <!-- H1태그 -->
								<td>
									<div>
                                        <p class="text-info text-sm">
                                            ※ ${op:message('M01040')} <!-- 페이지 상단의 <H1> 태그에 배포됩니다. -->
                                        </p>
										<p>
											<form:input path="headerContents1" maxlength="255" class="form-block required" title="${op:message('M00999')}" />
										</p>
									</div>
								</td>
							</tr>


						</tbody>
					</table>
							
				</div> <!-- // board_write -->					
			</div> <!-- // item_list02 -->
			
			<div class="tex_c mt20">
				<button type="submit" class="btn btn-active">${seo.seoId == 0 ? op:message('M00088') : op:message('M00087')}</button>
				<a href="javascript:Link.list('/opmanager/seo/list')" class="btn btn-default">${op:message('M00480')}</a>	<!-- 목록 -->
			</div>
				
		</form:form>
		
		



<script type="text/javascript">

$(function() {
	// 폼체크
	$("#seo").validator();

});
</script>