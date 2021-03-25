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
	
	<h5 style="margin-top: 0; border-left: 4px solid #000; padding-left: 8px;"> 1차 카테고리 등록</h5>
	
	<table class="board_write_table bt2">
		<caption> ${op:message('M00545')}</caption>
		<colgroup>
			<col style="width: 150px;" />
			<col style="width: auto;" />
		</colgroup>
		<tbody>
			<%-- 
			<tr>
				<td class="label">${op:message('M00547')}</td>
				<td>
					<div>
						${op:message('M00560')} <!-- 전체분류 --> 
				 	</div>
				</td>
			</tr>
			--%>
		 	 <tr>
				<td class="label">${op:message('M01139')} <!-- 카테고리명 --></td>
				<td>
					<div>
						<input name="categoryName" type="text" class="required full" title="${op:message('M00561')}" style="width: 50%;">
						<input name="categoryLevel" type="hidden" value="1" />
				 	</div>
				</td>
			</tr>
			 <tr>
				<td class="label">${op:message('M00270')} <!-- 카테고리 -->  URL</td>
				<td>
					<div>
						/categories/index/<input name="categoryUrl" type="text" class="required" title="${op:message('M00270')} URL" style="width: 150px;">
						<input name="categoryUrlCheck" type="hidden" value="0" />
						<a href="javascript:;" id="categroyUrlSearch" class="btn btn-gradient btn-sm">${op:message('M00377')}</a> <!-- 조회 --> 
				 	</div>
				</td>
			</tr>
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
												<option value="${categoriesGroup.categoryGroupId}" label="${categoriesGroup.groupName}" />
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
			<tr>
				<td class="label">${op:message('M00191')}</td> <!-- 공개유무 --></td>
				<td>
					<div>
						<label><input type="radio" name="categoryFlag" value="Y" id="out_all" checked="checked" /> ${op:message('M00096')} </label>
						<label><input type="radio" name="categoryFlag" value="N" id="out"  /> <label>${op:message('M00097')} </label>
				 	</div>
				</td>
			</tr>
			<tr style="display:none;">
				<td class="label">${op:message('M01604')} <!-- 접속권한 --></td>
				<td>
					<div>
						<input type="radio" name="accessType" value="1" id="accessType1" checked="checked" /> <label for="accessType1">${op:message('M00497')}</label>
						<input type="radio" name="accessType" value="2" id="accessType2" /> <label for="accessType2">${op:message('M01605')}</label>
					</div>
				</td>
			</tr>		 
		</tbody>
	</table>
	<div class="buttons">
		<button type="submit" class="btn btn-active">등록</button> 
	</div>							 
