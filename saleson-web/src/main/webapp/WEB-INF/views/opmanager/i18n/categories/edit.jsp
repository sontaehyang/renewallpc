<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>
<c:if test="${categories.categoryLevel != '1'}">
	<input type="hidden" name="categoryGroupId" value="${categories.categoryGroupId}" />
</c:if>
			
				
	<h5 style="margin-top: 0; border-left: 4px solid #000; padding-left: 8px;"> ${categories.categoryLevel}${op:message('M01292')} <!-- 차 카테고리 수정 --> </h5>
	<table class="board_write_table bt2">
		<caption> ${op:message('M00545')} <!-- 카테고리 만들기 --></caption>
		<colgroup>
			<col style="width: 150px;" />
			<col style="width: auto;" />
		</colgroup>
		<tbody>
			<!-- 
			<tr>
				<td class="label">${op:message('M00546')} -- 카테고리 코드 -- / URL</td>
				<td>
					<div>
						${categories.categoryCode } / ${categories.categoryUrl} 
				 	</div>
				</td>
			</tr>
			-->
			<tr>
				<td class="label">URL</td>
				<td>
					<div style="font-size: 13px;">
						<c:choose>
							<c:when test='${op:property("saleson.view.type") eq "api"}'>
								<a href="${op:property("saleson.url.frontend")}/category/?code=${item.itemUserCode}" target="_blank"><img src="/content/opmanager/images/icon/icon_preview_pc.gif" alt="" /></a>
								/category/?code=<%-- ${categories.categoryUrl} --%>
							</c:when>
							<c:otherwise>
								<a href="/m/categories/index/${categories.categoryUrl}" target="_blank"><img src="/content/opmanager/images/icon/icon_preview_mobile.gif" alt="" /></a>
								<a href="/categories/index/${categories.categoryUrl}" target="_blank"><img src="/content/opmanager/images/icon/icon_preview_pc.gif" alt="" /></a>
								/categories/index/<%-- ${categories.categoryUrl} --%>
							</c:otherwise>
						</c:choose>


						<input type="text" id="categoryUrl" name="categoryUrl" style="width: 150px; font-size: 12px; color: #000" value="${categories.categoryUrl}" />
				 	</div>
				</td>
			</tr>
			<%-- <tr>
				<td class="label">${op:message('M00547')} <!-- 카테고리 위치 --> </td>
				<td>
					<div>
						${categories.categoryName }
				 	</div>
				</td>
			</tr> --%>
		 	 <tr>
				<td class="label">${op:message('M00548')} <!-- 현재분류명 --> </td>
				<td>
					<div>
						<input name="categoryName" type="text" class="required full" title="${op:message('M00548')}" value="${categories.categoryName }">
						<input name="categoryId" type="hidden" value="${categories.categoryId}" />
						<input name="categoryCode" type="hidden" value="${categories.categoryCode}" />
						<input name="categoryClass1" type="hidden" value="${categories.categoryClass1}" />
						<input name="categoryClass2" type="hidden" value="${categories.categoryClass2}" />
						<input name="categoryClass3" type="hidden" value="${categories.categoryClass3}" />
						<input name="categoryClass4" type="hidden" value="${categories.categoryClass4}" />
						<input name="categoryLevel" type="hidden" value="${categories.categoryLevel}" />
						<input name="currentCategoryUrl" type="hidden" value="${categories.categoryUrl}" />
						<input name="categoryUrlCheck" type="hidden" value="1" />
				 	</div>
				</td>
			</tr>
			
			
			<c:if test="${categories.categoryLevel == '1'}">
			<tr>
				<td class="label">카테고리 그룹</td>
				<td>
					<div>
						<p>
							<select name="categoryGroupId" class="category">
								<option value="0">= 카테고리 그룹 =</option> <!-- 팀/그룹 -->
								<c:forEach items="${categoryTeamGroupList}" var="categoriesTeam">
									<c:if test="${categoriesTeam.categoryTeamFlag == 'Y'}">
										<optgroup label="${categoriesTeam.name}">
										<c:forEach items="${categoriesTeam.categoriesGroupList}" var="categoriesGroup">
											<c:if test="${categoriesGroup.categoryGroupFlag == 'Y'}">
												<option value="${categoriesGroup.categoryGroupId}" label="${categoriesGroup.groupName}" ${op:selected(categories.categoryGroupId, categoriesGroup.categoryGroupId) }>${categoriesGroup.groupName}</option>
											</c:if>
										</c:forEach>
										</optgroup>
									</c:if>
								</c:forEach>
								
							</select>
						</p>
				 	</div>
				</td>
			</tr>
			</c:if>
			
			<tr>
				<td class="label">${op:message('M00191')}</td> <!-- 공개유무 --></td>
				<td>
					<div>
						<label><input type="radio" name="categoryFlag" value="Y" id="out_all" ${op:checked('Y',categories.categoryFlag) } /> ${op:message('M00096')} <!-- 공개 --> </label>
						<label><input type="radio" name="categoryFlag" value="N" id="out" ${op:checked('N',categories.categoryFlag) } /> ${op:message('M00097')} <!-- 비공개 --> </label>
				 	</div>
				</td>
			</tr>
			<tr style="display: none;">
				<td class="label">${op:message('M01604')} <!-- 접속권한 --></td>
				<td>
					<div>
						<form:radiobutton path="categories.accessType" value="1" checked="checked" label="${op:message('M00497')}" /> <!-- 제한없음 -->
						<form:radiobutton path="categories.accessType" value="2" label="${op:message('M01605')}" />  <!-- 회원만 -->
					</div>
				</td>
			</tr>
		</tbody>
	</table>

	<br/>
	<h5 style="margin-top: 0; border-left: 4px solid #000; padding-left: 8px;">
		검색 필터
		<a href="javascript:openFilterPopup('filterArea');" class="btn btn-gradient btn-sm">${op:message('M00377')} <!-- 조회 --></a> &nbsp;
	</h5>
	<table class="board_write_table bt2">
		<caption>검색 필터</caption>
		<colgroup>
			<col style="width: 150px;" />
			<col style="width: auto;" />
			<col style="width: 100px;" />
		</colgroup>
		<tbody id="filterArea">
			<c:forEach var="filterGroup" items="${filterGroups}">
				<tr class="filter-element">
					<td class="label">
						${filterGroup.label}
					</td>
					<td>
						<div>
							<c:set var="codeString" value=""/>
							<c:forEach var="filterCode" items="${filterGroup.codeList}" varStatus="i">
								${filterCode.label}<c:if test="${not i.last}">,</c:if>
							</c:forEach>
							${codeString}
						</div>
					</td>
					<td>
						<div>
							<a href="javascript:;" class="btn btn-gradient btn-sm remove-filter">삭제</a> &nbsp;
						</div>
						<input type="hidden" name="filterGroupIds" value="${filterGroup.id}"/>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<br/>
	<h5 style="margin-top: 0; border-left: 4px solid #000; padding-left: 8px;">
		리뷰 필터
		<a href="javascript:openFilterPopup('reviewFilterArea')" class="btn btn-gradient btn-sm">${op:message('M00377')} <!-- 조회 --></a> &nbsp;
	</h5>
	<table class="board_write_table bt2">
		<caption>리뷰 필터</caption>
		<colgroup>
			<col style="width: 150px;" />
			<col style="width: auto;" />
			<col style="width: 100px;" />
		</colgroup>
		<tbody id="reviewFilterArea">
		<c:forEach var="filterGroup" items="${reviewFilterGroups}">
			<tr class="filter-element">
				<td class="label">
						${filterGroup.label}
				</td>
				<td>
					<div>
						<c:set var="codeString" value=""/>
						<c:forEach var="filterCode" items="${filterGroup.codeList}" varStatus="i">
							${filterCode.label}<c:if test="${not i.last}">,</c:if>
						</c:forEach>
							${codeString}
					</div>
				</td>
				<td>
					<div>
						<a href="javascript:;" class="btn btn-gradient btn-sm remove-filter">삭제</a> &nbsp;
					</div>
					<input type="hidden" name="reviewFilterGroupIds" value="${filterGroup.id}"/>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>`

	<div class="buttons">
		<button type="submit" class="btn btn-active">수정</button> 
	</div>
	
	<c:if test="${categories.categoryLevel != '4' && categories.categoryType == '1' }">
		<h5 style="margin-top: 20px;border-left: 4px solid #000; padding-left: 8px;"> ${op:message('M01293')} <!-- 하위 -->  ${categories.categoryLevel+1 }${op:message('M01294')} <!-- 차 -->  ${op:message('M00545')} <!-- 카테고리 만들기 --> </h5>
		<table class="board_write_table bt2">
			<caption> ${op:message('M00545')} <!-- 카테고리 만들기 --></caption>
			<colgroup>
				<col style="width: 150px;" />
				<col style="width: auto;" />
			</colgroup>
			<tbody>
				<tr>
					<td class="label">${op:message('M00005')} <!-- 이름 --> </td>
					<td>
						<div>
							 <input name="categoryLowName" type="text" class="full" style="width: 50%;" title="${op:message('M00005')}" />
					 	</div>
					</td>
				</tr>
				<tr>
					<td class="label">URL</td>
					<td>
						<div>
							 <input name="categoryLowUrl" type="text" class="full" style="width: 50%;" title="URL" />
							 <input name="categoryLowUrlCheck" type="hidden" value="0" />
							 <a href="javascript:;" id="categroyLowUrlSearch" class="btn btn-gradient btn-sm">${op:message('M00377')} <!-- 조회 --></a> &nbsp;
					 	</div>
					</td>
				</tr>
				
				<tr>
					<td class="label">게시구분</td>
					<td>
						<div>
							<label><input type="radio" name="categoryLowFlag" value="Y" checked="checked" /> 게시</label>
							<label><input type="radio" name="categoryLowFlag" value="N" /> 게시안함</label>
					 	</div>
					</td>
				</tr>
			</tbody>
		</table>	
		
		<div class="buttons">
			<a href="javascript:;" id="categroyLowAdd" class="btn btn-dark-gray">${categories.categoryLevel + 1}차 카테고리 등록</a>
		</div>
	</c:if>	