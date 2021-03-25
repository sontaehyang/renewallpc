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

<style>
tr.team_tr td {
	border-top: 2px solid #999;
}
.board_list_table {
	border-bottom: 2px solid #999;
}
</style>

<div class="location">
	<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
</div>

<div class="item_list">

	<h3><span>그룹 배너 관리</span></h3>
	<p class="text" style="margin-bottom: 5px;">
		* 메인페이지 좌측 카테고리에 마우스 오버 시 노출되는 상품을 관리합니다.<br />
		* 카테고리 그룹별로 상품 정보를 등록하실 수 있습니다.
	</p>
	<div class="board_list">
		<table class="board_list_table group">
			<colgroup>
				<col style="width: 200px;">
				<col style="width: 200px;">
				<col />
				<col style="width: 120px;">
			</colgroup>
			<thead>
				<tr>
					<th>팀명</th>
					<th>그룹명</th>
					<th>이미지</th>
					<th>관리</th>
				</tr>
			</thead>
			<tbody>

				<c:forEach items="${categoriesTeamGroupList}" var="team" varStatus="i">
					<c:if test="${team.code != 'sale_outlets'}">
						<c:set var="rowspan" value="${fn:length(team.categoriesGroupList)}" />
						<tr class="team_tr">
							<td rowspan="${rowspan}">${team.name}</td>
							<c:forEach items="${team.categoriesGroupList}" var="group" varStatus="j">
								<c:if test="${j.index > 0}"></tr><tr></c:if>
									<td class="tex_l" style="border-left: 1px solid #ececec;">${group.groupName}</td>
									<td>
										<c:set var="count" value="0" />
										<c:if test="${not empty group.groupBanners}">
											<c:forEach items="${group.groupBanners}" var="banner" varStatus="i">
												<img src="${banner.imageSrc}" title="${banner.title}" width="${banner.thumbnailWidth / 3}" height="${banner.thumbnailHeight / 3}" />
												<c:if test="${ i.first == false }">
													&nbsp;&nbsp;&nbsp;
												</c:if>

												<c:set var="count">${count + 1}</c:set>
											</c:forEach>
										</c:if>

										<c:if test="${not empty group.itemList}">
											<c:if test="${count > 0}">
												<br /><br />
											</c:if>
											${ group.itemList } 상품 출력<br/>(배너가 등록되어 있을경우 배너 2개를 우선적으로 출력후 노출됨)
											<c:set var="count">${count + 1}</c:set>
										</c:if>

										<c:if test="${count == 0}">
											상품 이미지 자동 출력
										</c:if>
									</td>
									<td>
										<a href="/opmanager/group-banner/form/${group.categoryGroupId}" class="btn btn-gradient btn-xs" title="수정">${op:message('M00087')}</a>  <!-- 수정 -->
									</td>
							</c:forEach>
						</tr>
					</c:if>
				</c:forEach>
			</tbody>
		</table>
	</div> <!-- // board_list -->

</div> <!-- // item_list01 -->