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
	<style type="text/css">
		.selected {
			background-color: #e9f3fb;
		}
		
		.category_short {
			width: 400px;
		}
		
		.category_list {
			width: 400px;
		}
		
		.sortable-placeholder td {
			height: 40px;
			background: #d6eafd url("/content/styles/ui-lightness/images/ui-bg_diagonals-thick_20_666666_40x40.png") 50% 50% repeat;
			opacity: .5;
		}
	</style>
	<div class="location">
		<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
	</div>
	<c:set var="group" value="${categoryCondition.categoriesGroup}"/>
	<c:set var="category" value="${categoryCondition.categoriesGroup.categoriesForCondition[0]}"/>
	<c:set var="condition" value="${categoryCondition.categoriesGroup.categoriesForCondition[0].conditions[0]}"/>
	
	<div class="item_info_wrap">	
		<h3><span>검색조건 관리</span></h3>
		<form id="categoryConditionForm" method="post">
			<div class="board_write">						
				<table class="board_write_table" summary="검색조건">
					<colgroup>
						<col style="width: 200px;"/>
					</colgroup>
					<tbody>
						<tr>
							<td class="label">카테고리명</td>
							<td>
								<div>${group.name} > ${category.categoryName}</div>
							</td>
						</tr>
						<tr>
							<td class="label">카테고리코드</td>
							<td>
								<div>${category.categoryCode}</div>
							</td>
						</tr>
						<tr>
							<td class="label">조건명 <span class="require">*</span></td>
							<td>
								<div><input type="text" class="w50 required" name="conditionTitle" id="conditionTitle" value="${condition.conditionTitle}"/></div>
							</td>
						</tr>
						<tr>
							<td class="label">사용여부 <span class="require">*</span></td>
							<td>
								<div>
									<input type="radio" name="useYn" id="useY" value="Y" ${empty condition.useYn or condition.useYn == "Y" ? "checked" : ""}/> <label for="useY">사용</label>
									<input type="radio" name="useYn" id="useN" value="N" ${condition.useYn == "N" ? "checked" : ""}/> <label for="useN">미사용</label>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
				<div class="btn_center" style="margin-top: 15px;">
					<button type="submit" class="btn btn-active">저장</button>	
					<button type="button" class="btn btn-gray" onclick='location.href="/opmanager/condition/list";'>목록</button>	
				</div>
			</div>
		</form>
		
		<c:if test="${!condition.conditionEmpty}">
			<h3 style="margin-top: 20px;"><span>검색조건 상세</span></h3>
			<div class="category_set" style="margin-top: 10px;">
				<div class="category_short">
					<form id="searchForm" method="post">
						<div style="padding: 0 0 10px 10px;">
							<input type="radio" name="paramUseYn" id="paramAll" value="all" ${conditionParam.useYn == "all" ? "checked" : ""}/> <label for="paramAll">전체</label>
							<input type="radio" name="paramUseYn" id="paramUseY" value="Y" ${empty conditionParam.useYn or conditionParam.useYn == "Y" ? "checked" : ""}/> <label for="paramUseY">사용</label>
							<input type="radio" name="paramUseYn" id="paramUseN" value="N" ${conditionParam.useYn == "N" ? "checked" : ""}/> <label for="paramUseN">미사용</label>
						</div>
					</form>
					<div class="category_list h138 board_write">						
						<table class="board_write_table" summary="검색조건" style="text-align: center;">
							<colgroup>
								<col style=""/>
								<col style="width: 80px;"/>
								<col style="width: 60px;"/>
							</colgroup>
							<thead>
								<tr>
									<th class="label">상세명</th>
									<th class="label">사용여부</th>
									<th class="label">관리</th>
								</tr>
							</thead>
							<tbody class="sortable">
								<c:choose>
									<c:when test="${condition.detailsEmpty}">
										<tr>
											<td colspan="3"><div style="text-align: center; padding: 30px 0;">${cateogryCondition.details[0].detailTitle} 조회된 검색조건 상세 데이터가 없습니다.</div></td>
										</tr>
									</c:when>
									<c:otherwise>
										<c:forEach var="detail" items="${condition.details}">
											<tr class="condition-detail" data-detail-title="${detail.detailTitle}" data-detail-id="${detail.detailId}" data-use-yn="${detail.useYn}">
												<td>
													${detail.detailTitle}
												</td>
												<td>
													<div>${detail.useYn == "Y" ? "사용" : "미사용"}</div>
												</td>
												<td>
													<div><button type="button" class="btn btn-gradient btn-xs" onclick="javascript:;">삭제</button></div>
												</td>
											</tr>
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
					</div>
					<c:if test='${conditionParam.useYn == "Y" and !condition.detailsEmpty}'>
						<div class="btn_center" style="float: right;">
							<button type="button" class="btn btn-active" id="change-ordering">순서변경</button>	
						</div>
					</c:if>
				</div>
				<div class="board_write right_box">
					<h5 style="margin-top: 0; border-left: 4px solid #000; padding-left: 8px;" id="formLabel"> 검색조건 상세 등록 </h5>
					<form id="conditionDetailForm" action="/opmanager/condition/detail/form/${condition.conditionId}" method="post">
						<input type="hidden" name="detailId" id="detailId" value="0" />
						<input type="hidden" name="ordering" id="ordering" value='${condition.detailsEmpty ? "1" : fn:length(condition.details)+1}' />
						<table class="board_write_table" summary="검색조건">
							<colgroup>
								<col style="width: 150px;"/>
							</colgroup>
							<tr>
								<td class="label">
									조건상세명
								</td>
								<td>
									<div><input type="text" class="required w90" name="detailTitle" id="detailTitle" title="조건상세명"/></div>
								</td>
							</tr>
							<tr>
								<td class="label">
									사용여부
								</td>
								<td>
									<div>
										<input type="radio" name="useYn" id="detailUseY" value="Y" checked/> <label for="detailUseY">사용</label>
										<input type="radio" name="useYn" id="detailUseN" value="N" /> <label for="detailUseN">미사용</label>
									</div>
								</td>
							</tr>
						</table>
						<div class="btn_center" style="margin-top: 15px;">
							<button type="submit" class="btn btn-active">저장</button>	
							<button type="button" class="btn btn-gray" id="detailInit">초기화</button>	
						</div>
					</form>
				</div>
			</div>
		</c:if>
	</div>
	
	<script type="text/javascript">
		$(function(){
			// 검색조건 상세 목록 클릭시 우측 form으로 데이터 표시
			detailEvent();
			
			// 초기화버튼 이벤트
			detailInitEvent();
			
			// 검색조건 상세 사용, 미사용 이벤트
			paramUseYnEvent();
			
			$("#conditionDetailForm").validator();
			
			<c:if test='${conditionParam.useYn == "Y" and !condition.detailsEmpty}'>
				// 순서변경 스크립트				
				$(".sortable").sortable({
					placeholder : "sortable-placeholder"
				});
				
				// 순서 변경 클릭 이벤트
				changeOrderingEvent();
			</c:if>
		});
		
		function detailEvent() {
			$(".condition-detail").on("click", function(){
				$("#detailTitle").val($(this).data("detailTitle"));
				$("#detailId").val($(this).data("detailId"));
				$("#formLabel").text(" 검색조건 상세 수정 ");
				$(".condition-detail").removeClass("selected");
				$(this).addClass("selected");
				var useYn = $(this).data("useYn");
				if (useYn == "Y") {
					$("#detailUseY").prop("checked", true);
				}
				else {
					$("#detailUseN").prop("checked", true);
				}
			});
		}
		
		function detailInitEvent() {
			$("#detailInit").on("click", function(){
				$(".condition-detail").removeClass("selected");
				$("#formLabel").text(" 검색조건 상세 등록 ");
				$("#detailTitle").val("");
				$("#detailId").val("0");
				$("#detailUseY").prop("checked", true);
			});
		}
		
		function paramUseYnEvent() {
			$('input[name="paramUseYn"]').on('change', function(){
				$("#searchForm").submit();
			});
		}
		
		function changeOrderingEvent() {
			$("#change-ordering").on("click", function(){
				if (confirm("진행중인 내용은 저장되지 않습니다. 계속하시겠습니까?")) {
					var detailIds = "";
					var detailId = "";
					$(".condition-detail").each(function(index){
						detailId = $(this).data("detailId");
						detailIds += (index>0 ? "," : "") + detailId;
					});
					$.post("/opmanager/condition/detail/change-ordering", {"detailIds" : detailIds}, function(response){
						if (response.isSuccess) {
							alert("순서가 변경되었습니다.");
							location.reload();
						}
						else {
							alert("순서가 변경중 에러가 발생했습니다.");
							console.log(response.errorMessage);
						}
					});
				}
			});
		}
	</script>
