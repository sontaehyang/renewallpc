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


	<div class="location">
		<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
	</div>

<div class="item_list">

	<h3><span>${op:message('M00584')} <!-- 팀별 그룹관리 --> </span></h3>

	<div class="board_list">
		<form:form modelAttribute="searchParam" id="searchForm" method="post">
			<div class="count_title">
				<h5>
					<form:select path="categoryType">
						<form:option value="">${op:message('M00039')} <!-- 전체 --> </form:option>
						<form:option value="team">대그룹</form:option>
					</form:select>
				</h5>
				<%-- <button class="table_btn" id="ordering" style="float: right;" title="순서 정렬">${op:message('M00587')} <!-- 순서 정렬 --> </button>  --%>
			</div>
		</form:form>

		<form id="listForm">
		<table class="board_list_table group" style="width: 100%">
			<colgroup>
				<col style="width: 100px;">
				<col style="">
				<col style="">
				<col style="width: 100px;">
				<col style="width: 100px;">
				<col style="width: 120px;">
			</colgroup>
			<thead>
				<tr>
					<th>${op:message('M00588')} <!-- 레벨 --> </th>
					<th>${op:message('M00394')} <!-- 타이틀 --> </th>
					<th>CODE</th>
					<th>${op:message('M00589')} <!-- 사용/사용안함 --> </th>
					<th>${op:message('M00190')} <!-- 순서 --> </th>
					<th>${op:message('M00590')} <!-- 관리 --> </th>

				</tr>
			</thead>

				<c:if test="${searchParam.categoryType == 'team'}">
					<tbody class="sortable">
					<c:forEach items="${categoriesTeamGroupList}" var="list" varStatus="i">
						<tr class="back_color">
							<td>
								대그룹
								<input type="hidden" name="id" value="${list.categoryTeamId}"/>
								<input type="hidden" name="ordering" />
							</td>
							<td class="tex_l">${list.name}</td>
							<td>${list.code}</td>
							<td>${list.categoryTeamFlag}</td>
							<td>${list.ordering}</td>
							<td>
								<a href="javascript:fn_categoriesTeamUpdate('${list.categoryTeamId}');" class="btn btn-gradient btn-xs" title="수정">${op:message('M00087')}</a> <!-- 수정 -->
								<%-- <a href="javascript:fn_categoriesTeamDelete('${list.categoryTeamId}');" class="table_btn" title="삭제">${op:message('M00074')}</a> <!-- 삭제 --> --%>
							</td>
						</tr>
					</c:forEach>
					</tbody>
				</c:if>
				<c:if test="${searchParam.categoryType == 'group'}">
					<c:forEach items="${categoriesTeamGroupList}" var="list" varStatus="i">
							<tr>
								<td>중그룹 <!-- 그룹 --> </td>
								<td class="tex_l">┗ <a href="/opmanager/categories/list?categoryGroupId=${list.categoryGroupId}">${list.name}</a></td>
								<td>${list.code}</td>
								<td>${list.categoryGroupFlag }</td>
								<td>${list.ordering}</td>
								<td>
									<a href="javascript:fn_categoriesGroupUpdate('${list.categoryGroupId}');" class="btn btn-gradient btn-xs" title="수정">${op:message('M00087')}</a>  <!-- 수정 -->
									<%-- <a href="javascript:fn_categoriesGroupDelete('${list.categoryGroupId}');" class="table_btn" title="삭제">${op:message('M00074')}</a> <!-- 삭제 --> --%>
								</td>
							</tr>
						</c:forEach>
				</c:if>
				<c:if test="${searchParam.categoryType == ''}">
					<c:forEach items="${categoriesTeamGroupList}" var="list" varStatus="i">
						<tr class="back_color">
							<td>대그룹</td>
							<td class="tex_l">${list.name }</td>
							<td >${list.code}</td>
							<td>${list.categoryTeamFlag}</td>
							<td>${list.ordering}</td>
							<td>
								<a href="javascript:fn_categoriesTeamUpdate('${list.categoryTeamId}');" class="btn btn-gradient btn-xs" title="수정">${op:message('M00087')}</a> <!-- 수정 -->
								<%-- <a href="javascript:fn_categoriesTeamDelete('${list.categoryTeamId}');" class="table_btn" title="삭제">${op:message('M00074')}</a> <!-- 삭제 --> --%>
							</td>
						</tr>
						<tbody id="${list.code}_groups" class="sortable">
						<c:forEach items="${list.categoriesGroupList}" var="list2" varStatus="j">
							<c:if test="${list2.groupName != null && list2.groupName != '' }">
								<tr>
									<td>중그룹
										<input type="hidden" name="id" value="${list2.categoryGroupId}"/>
										<input type="hidden" name="ordering" />
									</td>
									<td class="tex_l">┗ <a href="/opmanager/categories/list?categoryGroupId=${list2.categoryGroupId}">${list2.groupName}</a></td>
									<td>${list2.code}</td>
									<td>${list2.categoryGroupFlag }</td>
									<td>${list2.groupOrdering}</td>
									<td>
										<a href="javascript:fn_categoriesGroupUpdate('${list2.categoryGroupId}');" class="btn btn-gradient btn-xs" title="수정">${op:message('M00087')}</a>  <!-- 수정 -->
										<%-- <a href="javascript:fn_categoriesGroupDelete('${list2.categoryGroupId}');" class="table_btn" title="삭제">${op:message('M00074')}</a> <!-- 삭제 --> --%>
									</td>
								</tr>
							</c:if>
						</c:forEach>
						</tbody>
					</c:forEach>
				</c:if>

		</table>

		<c:if test="${empty categoriesTeamGroupList}">
			<div class="no_content">
				<p>${op:message('M00591')} <!-- 등록된 데이터가 없습니다. --> </p>
			</div>
		</c:if>

		<div class="btn_all">
			<div class="btn_left mb0">
				<button type="button" onclick="changeOrdering('${searchParam.categoryType}')" class="btn btn-dark-gray btn-sm" style="border: 1px solid rgb(37, 165, 220); background-color: rgb(37, 165, 220);">정렬순서변경</button> <!-- 정렬순서변경 -->
			</div>
			<div class="btn_right mb0">
				<button type="button" onclick="location.href='/opmanager/categories-team-group/team/create'" class="btn btn-active btn-sm"><span class="glyphicon glyphicon-plus"></span> 대그룹 등록</button> <!-- 팀 등록 -->
				<button type="button" onclick="location.href='/opmanager/categories-team-group/group/create'" class="btn btn-active btn-sm"><span class="glyphicon glyphicon-plus"></span> 중그룹 등록</button> <!-- 그룹 등록 -->
			</div>
		</div>


		<div class="tex_r mt20">

		</div>
	</div> <!-- // board_list -->

</div> <!-- // item_list01 -->


<style>
td {background: #fff;}
.sortable-placeholder td {
	height: 50px;
	background: #d6eafd url("/content/styles/ui-lightness/images/ui-bg_diagonals-thick_20_666666_40x40.png") 50% 50% repeat;
	opacity: 0.5;
}
.back_color td {background:#ebe3d0}
</style>

<script type="text/javascript">
$(function(){
	$('.sortable').sortable({
        placeholder: "sortable-placeholder"
    });
    $('.sortable').disableSelection();

	categoryChange();
	teamGroupOrdering();

});


// 드레그 후 순서 변경.
function changeOrdering(type) {
	var $form = $('#listForm');

	$('tbody').each(function() {
		$(this).find('input[name=ordering]').each(function(index) {
			$(this).val(index + 1);
		});
	});

	if (type == '') {
		type = 'group';
	}

	if (confirm("정렬 순서를 변경하시겠습니까?")) {		//  변경하시겠습니까?
		$.post("/opmanager/categories-team-group/change-ordering/" + type, $form.serialize(), function(response) {
			Common.responseHandler(response, function(response){
				location.reload();
			});
		});
	}
}
function categoryChange(){
	$("#categoryType").on("change",function(){
		$("#searchForm").submit();
	});
}

function fn_categoriesTeamUpdate(teamId){
	location.href="/opmanager/categories-team-group/team/edit/"+teamId;
}

function fn_categoriesTeamDelete(teamId){
	Common.confirm(Message.get("M00594"), function() {
		location.href="/opmanager/categories-team-group/team/delete/"+teamId;
	});
}

function fn_categoriesGroupUpdate(groupId){
	location.href="/opmanager/categories-team-group/group/edit/"+groupId;
}

function fn_categoriesGroupDelete(groupId){
	Common.confirm(Message.get("M00594"), function() {
		location.href="/opmanager/categories-team-group/group/delete/"+groupId;
	});
}

function teamGroupOrdering(){
	$(".team_ordering, .group_ordering").on("keydown",function(e){

		var obj = $(this);

		if (!(e.which && (e.which > 47 && e.which < 58) || (e.which > 95 && e.which < 106) || e.which ==8 || e.which == 13 || e.which == 37 || e.which == 39 || e.which == 46 || e.which ==9|| e.which ==0 || (e.ctrlKey && e.which ==86) ) ) {
		 	e.preventDefault();
	   	}



	   	/* var pattern = /^[0-9]+/g;
	   	var matchValue = obj.val().match(pattern);

	   	if (!pattern.test(obj.val())) {
			obj.val('');
		}

		if (obj.val() != matchValue) {
			  obj.val(matchValue);
		} */

	});

	var orgTeamOrdering;
	$(".team_ordering").on("focusin", function(){
		orgTeamOrdering = $(this).val()
	}).on("focusout", function() {
		if ($.isNumeric($(this).val()) == false) {
			$(this).val(orgTeamOrdering);
			return false;
		}

		var changeOrdering = $(this).val();
		var isSave = true;
		$.each($('.team_ordering').not(this), function() {
			if ($(this).val() == changeOrdering) {
				isSave = false;
			}
		});

		if (changeOrdering == orgTeamOrdering) {
			return false;
		}

		if (isSave == false) {
			$(this).val(orgTeamOrdering);
		} else {

			var param  = {
				"ordering" : $(this).val(),
				"categoryTeamId" : $(this).attr("rel")
			};

			$.post("/opmanager/categories-team-group/ordering-edit/team", param, function(response) {
				Common.responseHandler(response, function(){
					location.reload();
				});
			});

		}
	});

	var orgGroupOrdering;
	$('.group_ordering').on("focusin", function(){
		orgGroupOrdering = $(this).val();
	}).on("focusout", function(){

		if ($.isNumeric($(this).val()) == false) {
			$(this).val(orgGroupOrdering);
			return false;
		}

		var changeOrdering = $(this).val();
		var isSave = true;
		$.each($('.' + $(this).attr('id')).not(this), function(){
			if ($(this).val() == changeOrdering) {
				isSave = false;
			}
		});

		if (changeOrdering == orgGroupOrdering) {
			return false;
		}

		if (isSave == false) {
			$(this).val(orgGroupOrdering);
		} else {
			var param  = {
					"ordering" : $(this).val(),
					"categoryGroupId" : $(this).attr("rel")
			};

			$.post("/opmanager/categories-team-group/ordering-edit/group", param, function(response) {
				Common.responseHandler(response, function(){
					location.reload();
				});
			});
		}

	})

	/* $("#ordering").on("click",function(){
		location.reload();
	}); */
}

// 순서변경
</script>
