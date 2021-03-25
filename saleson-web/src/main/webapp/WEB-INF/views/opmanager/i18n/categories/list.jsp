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

	<h3><span>${op:message('M00556')} <!-- 카테고리관리 --> </span></h3>
			
	<div class="email_tb">				
		
		
		<p class="c_icon">
			<span class="mr5">${op:message('M00557')} <!-- 카테고리 검색 -->  : 
				<!-- <input type="text" id="plugins4_q" value="" class="input" style="margin:5px;  border-radius:4px; border:1px solid silver;" /> -->
				
				<select name="categoryGroupId" title="메인 구분" > 
					<option value="" >${op:message('M00039')} <!-- 전체 --> </option>
					<c:forEach items="${categoryTeamGroupList}" var="list">
						<optgroup label="${list.name}">
							<c:forEach items="${list.categoriesGroupList}" var="list2">
								<option value="${list2.categoryGroupId}" ${op:selected(searchParam.categoryGroupId, list2.categoryGroupId)} >-${list2.groupName}</option>	
							</c:forEach>
						</optgroup>
					</c:forEach>
				</select>	
			</span>
			<span style="display: inline-block !important;">
				<button type="button" class="btn btn-dark-gray btn-sm" id="categoriesCreate"><span>1${op:message('M00075')}${op:message('M00192')}</span></button> <!-- 차 카테고리 --> <!-- 추가 -->
			</span> 
		</p>	
		
		<%-- <p style="margin:5px;">
			<select name="categoryGroupId" title="메인 구분" > 
				<option value="" >${op:message('M00039')} <!-- 전체 --> </option>
				<c:forEach items="${categoryTeamGroupList}" var="list">
					<optgroup label="${list.name}">
						<c:forEach items="${list.categoriesGroupList}" var="list2">
							<option value="${list2.categoryGroupId}" ${op:selected(searchParam.categoryGroupId, list2.categoryGroupId)} >-${list2.groupName}</option>	
						</c:forEach>
					</optgroup>
				</c:forEach>
			</select>	
		</p> --%>
		<!--// category_set -->		
		<form id="categoryForm" method="post" action="/opmanager/categories/create">
			<input type="hidden" name="cateGroupId" value="${searchParam.categoryGroupId}" />
			<div class="category_set">
							
				<div class="category_short">						
					<div class="category_list h138" style="width:350px;padding-bottom: 50px">							
						<div class="bundle_two" id="jstree_wrapper">
						</div> <!-- // bundle_two -->							
					</div> <!-- // category_list -->						
				</div> <!-- // category_short -->			
				
				<div class="board_write right_box">
				</div> <!--// board_write E-->
				
			</div> <!-- // category_set -->
			
				
		
		
			<div id="seo" style="display: none; margin-top: 20px">
				<h3>
					<span>${op:message('M00995')}</span> <!-- SEO 설정 -->
					<span class="f12"></span>
				</h3>
				
				<div class="board_write">
						
					<table class="board_write_table">
						<colgroup>
							<col style="width: 160px;">
							<col style="">
						</colgroup>
						<tbody>
		
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
										<input name="categoriesSeo.title" id="categoriesSeo.title" type="text" class="form-block" title="${op:message('M00090')}" />
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
										<input name="categoriesSeo.keywords" id="categoriesSeo.keywords" type="text" class="form-block" title="${op:message('M00997')}" />
										
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
										<textarea name="categoriesSeo.description" id="categoriesSeo.description" cols="30" rows="2" class="form-block" title="${op:message('M00998')}"></textarea>
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
										<input type="text" name="categoriesSeo.headerContents1" id="categoriesSeo.headerContents1" class="form-block" title="${op:message('M00999')}" />
									</div>
								</td>
							</tr>
							
							
						</tbody>
					</table>
							
				</div>
			
				
				<div class="btn_center">
					<button type="submit" class="btn btn-active">저장</button> 
					<button type="button" class="btn btn-default op-delete-category">${op:message('M00074')}</button> <!-- 삭제 -->
				</div>
			</div>
		</form>
	</div>

<script type="text/javascript">
var node = "";
var categoryGroupId = '${searchParam.categoryGroupId}'; 
var patterns = /^[a-zA-Z0-9_-]+$/;

$(function(){
	$("body").on("click","#categroyUrlSearch",function(){
		
		if( $("input[name='categoryUrl']").val() == null || $("input[name='categoryUrl']").val() == '' ){
			alert(Message.get("M01331")); // 카테고리 URL 값을 입력해주세요 
			return false;
		}
		
		var value = $("input[name='categoryUrl']").val();
		
		if(!patterns.test(value)){
			alert(Message.get("M01332")); // 영문, 숫자, 하이픈, 언더바 만 입력가능합니다. 
			return false;
		}
		
		var param = {
			"categoryUrl" : $("input[name='categoryUrl']").val()
		};
		
		$.post("/opmanager/categories/code-check",param,function(resp){
			Common.responseHandler(resp, function(){
				alert(Message.get("M01333")); // url 코드를 사용 하실수 있습니다. 
				$("input[name='categoryUrlCheck']").val("1");
			},function(){
				alert(resp.errorMessage);
				$("input[name='categoryUrlCheck']").val("0");
			});
		});
		
	});
		
	$("body").on("click","#categroyLowUrlSearch",function(){
			
			if( $("input[name='categoryLowUrl']").val() == null || $("input[name='categoryLowUrl']").val() == '' ){
				alert(Message.get("M01331")); // 카테고리 URL 값을 입력해주세요
				return false;
			}
			
			var value = $("input[name='categoryLowUrl']").val();
			
			if(!patterns.test(value)){
				alert(Message.get("M01332")); // 영문, 숫자, 하이픈, 언더바 만 입력가능합니다.
				return false;
			}
			
			var param = {
				"categoryUrl" : $("input[name='categoryLowUrl']").val()
			};
			
			$.post("/opmanager/categories/code-check",param,function(resp){
				Common.responseHandler(resp, function(){
					alert(Message.get("M01333")); // url 코드를 사용 하실수 있습니다.
					$("input[name='categoryLowUrlCheck']").val("1");
				},function(){
					alert(resp.errorMessage);
				});
			});
			
		});
		
		
		$(".seo_copy").on("click",function(){
			seoCopy($(this));
		});
		
		$("#categoryForm").submit(function(){
			Common.loading.hide();
			
			if( $("input[name='categoryName']").val() == '' ){
				alert(Message.get("M01334")); // 카테고리 명을 입력하세요 
				$("input[name='categoryName']").focus();
				return false;
			}
			
			// 수정일 때 카테고리 URL 수정할 수 있도록 해주세요.
			if ($("input[name='categoryUrlCheck']").val() == '1') {
				var $categoryUrl = $("input[name='categoryUrl']");
				
				
				 if ($categoryUrl.val() != $("input[name='currentCategoryUrl']").val()) {
					 // 중복 조회.
					 var value = $categoryUrl.val();
						
					if(!patterns.test(value)){
						alert(Message.get("M01332")); // 영문, 숫자, 하이픈, 언더바 만 입력가능합니다.
						return false;
					}
					

					$.ajaxSetup({
						async: false
					});
					
					var isPossibleUrl = false;
					var param = {
							"categoryUrl" : value
						};
					
					$.post("/opmanager/categories/code-check",param,function(resp){
						Common.responseHandler(resp, function(){
							//alert(Message.get("M01333")); // url 코드를 사용 하실수 있습니다.
							//$("input[name='categoryLowUrlCheck']").val("1");
							isPossibleUrl = true;
						},function(){
							isPossibleUrl = false;
							alert(resp.errorMessage);
						});
					});
					
					if (!isPossibleUrl) {
						$categoryUrl.focus();
						return false;
					}
				 }
			}
			
			
			
			
			if($("input[name='categoryLevel']").val() == '1'){
				
				if($("input[name='categoryUrlCheck']").val() != '1'){
					$("input[name='categoryUrl']").focus();
					alert(Message.get("M01335")); // 카테고리 URL 조회 하십시요 
					return false;
				}
				
				
				var $categoryGroupId = $("#categoryForm").find("select[name='categoryGroupId']");
				if ($categoryGroupId.val() == '0') {
					alert("0" + Message.get("M00075") + "를 선택하십시오."); // 0차 카테고리
					$categoryGroupId.focus();
					return false;
				}
			}
		});

	// 중복 검사 후 url을 변경한 경우.	
	$("body").on("change", "input[name=categoryLowUrl]",function(){
		$('input[name=categoryLowUrlCheck]').val('0');
	});
	
	$("body").on("click","#categroyLowAdd",function(){

		if( $("input[name='categoryLowName']").val() == '' ){
			$("input[name='categoryLowName']").focus();
			alert(Message.get("M01336"));	// 하위 카테고리 명을 입력하세요 
			return false;
		}
		
		if($("input[name='categoryLowUrlCheck']").val() != '1'){
			$("input[name='categoryUrl']").focus();
			alert(Message.get("M01337"));	// 하위 카테고리 URL 조회 하십시요 
			return false;
		}
		$("#categoryForm").attr("action","/opmanager/categories/low-create/"+$("input[name='categoryId']").val());
		$("input[name='categoryName']").val($("input[name='categoryLowName']").val());
		$("input[name='categoryLevel']").val(parseInt($("input[name='categoryLevel']").val())+1);
		$("input[name='categoryUrl']").val($("input[name='categoryLowUrl']").val());
		
		$("#categoryForm").submit();
	});
	
	//categoriesCreateClan(node);
	
	var jstree = $('#jstree_wrapper').jstree({
			"core" : {
				"animation" : 0,
			    "check_callback" : true,
			    "themes" : { "stripes" : true },
			    'data' : {
			    	'url' : function (node) {
			    		return node.id === '#' ? '/opmanager/categories/tree-list?categoryGroupId='+categoryGroupId : '/opmanager/categories/tree-list/'+node.id;
					},
					'data' : function (node) {
			            return { 'id' : node.id };
					}
			    }
			},
			'check_callback' : true,
		  	"types" : {
		  		"#" : {
			      	"max_depth" : 4,
			    },
/*			  		
			    "#" : {
//			      	"max_depth" : 4,
			      	"valid_children" : ["default"]
			    },
			    "root" : {
//			    	"max_depth": 4, 
			    	"valid_children" : ["default"]
			    },
			    */
			    "default" : {
			      	"icon" : {
			    	  	"image" : "/content/images/common/x_icon.png"
			    	},
			      	"valid_children" : ["default"]
			    }
		  },
		  "plugins" : [
		     "dnd","state", "search", "types"
		  ]
		});
	
		var to = false;
		$('#plugins4_q').keyup(function () {
		    if(to) { clearTimeout(to); }
		    to = setTimeout(function () {
		      var v = $('#plugins4_q').val();
		      $('#jstree_wrapper').jstree(true).search(v);
		    }, 250);
		  });
		
		$("#jstree_wrapper").on("select_node.jstree",function(e, data){
			
			$("#categoryForm").attr("action","/opmanager/categories/edit");
			node = data.node;
			var categoryId = categoriesCodeResult(data.node.id.split("-")[0]);
			
			$.get("/opmanager/categories/edit/"+categoryId,"",function(resp){
				$(".right_box").html(resp);
			},'html');
			
			$.get("/opmanager/categories/edit-seo/"+categoryId,"",function(resp){
				seoValue(resp);
			},'json');
			
			$("#seo").show();
			//setHeight();
			
		});
		
		$("#categoriesCreate").on("click",function(){
			categoriesCreateClan(node);
		});
		
		
		$("#jstree_wrapper").on("move_node.jstree",function(e, data){
			var currentCategoryName = data.node.text;
			currentCategoryName = currentCategoryName.replace(/(<([^>]+)>)/gi, '');
			var parent = data.parent;
			var current = data.node.id;
			var previous = '';
			var position = data.position;
			
			// node 정보 (parsing)
			var parentNode = getNode(parent);
			var currentNode = getNode(current);
			
			
			// 1차 카테고리로 이동하는 경우 GroupId는 필수!
			if (parent == '#' && categoryGroupId == '') {
				alert(Message.get('M01596')); // '1차 카테고리로 이동하려면 0차 카테고리를 먼저 선택해 주세요.'
				location.reload();
				return;
			}
			
			// 하위 카테고리 포함해서 이동가능한 레벨인가?
			var movePossibleLevel = 4 - (currentNode.childMaxLevel - currentNode.level + 1);

			if (parentNode.level > movePossibleLevel) {
				alert(movePossibleLevel + '차 카테고리 이내로 이동이 가능합니다.');  // n차 카테고리 이내로 이동이 가능합니다.
				location.reload();
				return;
			}
			
			// 이동될 카테고리의 형재 노드 정보 가져오기
			if (position > 0) {
				var $parent = $('#jstree_wrapper');
				
				if (parent != '#') {
					$parent = $('#' + parent);
				}
					
				previous = $parent.find('> ul > li').eq(position-1).attr('id');
			}
			
			var previousNode = getNode(previous);
			
			
			
			var message = '『 ' + currentCategoryName + ' 』 '+Message.get("M01338") + '';
			if (parent != '#') {
				message = '『 ' + currentCategoryName + ' 』 '+Message.get("M01338")+' 『 ' + $("#"+data.parent+" > a ").text() + ' 』 '+Message.get("M01339") ;
			}

			message += '\n\n' + Message.get("M01340") + '\n\n'+'이동시 해당 카테고리 및 그 이하 필터 정보가 제거됩니다.';

			Common.confirm(message, function(){
				var param = {
					'categoryGroupId': categoryGroupId,
					'parentCode': parentNode.code,
					'parentLevel': parentNode.level,
					'parentGroupId': parentNode.groupId,
					'currentCode': currentNode.code,
					'currentLevel': currentNode.level,
					'previousCode': previousNode.code,
					'previousLevel': previousNode.level,
					'previousOrdering': previousNode.ordering
				};
				//alert(param.parentGroupId);
				$.post("/opmanager/categories/move", param, function(resp) {
					Common.responseHandler(resp, function(){
						location.reload();
						
					},function(){
						alert(resp.errorMessage);
						location.reload();
					});
				});
				
			}, function(){
				location.reload();
			});
		});
		
		
	// 삭제
	// class 지정이 잘못되어 수정 2017-05-08_seungil.lee
	$('.btn.op-delete-category').on('click', function() {
		deleteCategory();
	});

	$("body").on('click','#filterArea .remove-filter', function() {
		$(this).closest('.filter-element').remove();
	});

	$("body").on('click','#reviewFilterArea .remove-filter', function() {
		$(this).closest('.filter-element').remove();
	});
});

$("select[name='categoryGroupId']").on("change",function(){
	location.href="/opmanager/categories/list?categoryGroupId="+$(this).val();
});


function deleteCategory(){
	var categoryCode = $('input[name=categoryCode]').val();
	var categoryLevel = $('input[name=categoryLevel]').val();
	var categoryId = $("input[name='categoryId']").val();
	
	var param = {
		'categoryCode': categoryCode,	
		'categoryLevel': categoryLevel,	
		'categoryId': categoryId	
	};
	
	// 메시지가 주석과 다르게 출력되어 수정 2017-05-08_seungil.lee
// 	var message = Message.get("M01598") + "\n" + Message.get("M00196");	// 하위 카테고리까지 모두 삭제되며 복구하실 수 없습니다. 삭제하시겠습니까?
	var message = "하위 카테고리까지 모두 삭제되며 복구하실 수 없습니다.\n삭제하시겠습니까?";	// 하위 카테고리까지 모두 삭제되며 복구하실 수 없습니다. 삭제하시겠습니까?
	if (confirm(message)) {
		$.post("/opmanager/categories/delete", param, function(resp){
			Common.responseHandler(resp, function(){
				location.reload();
			});
		});
	}
}
	
function categoriesCreate(){
	$.get("/opmanager/categories/create","",function(resp){
		$(".right_box").html(resp);
		
		
		
	},'html');
}
	
function categoriesCreateClan(node){
	$("#categoryForm").attr("action","/opmanager/categories/create");
	$("#jstree_wrapper").jstree("deselect_node",node);
	seoValue("");
	
	$.get("/opmanager/categories/create","",function(resp){
		$(".right_box").html(resp);
		var $formCategoryGroupId = $(".right_box").find("select[name=categoryGroupId]");		
		if (categoryGroupId != '' && $formCategoryGroupId.val() == "0") {
			$formCategoryGroupId.val(categoryGroupId);
		}
		
		$("#seo").show();
		//setHeight();
	},'html');
	
}
	
function categoriesCodeResult(code){
	var codeSize  = 12 - code.length;
	
	for(var i=0; i < codeSize; i++){
		code += "0"; 
	}
	return code;
}
	
function seoValue(resp){
	
	$("input[name='categoriesSeo.title']").val(resp != "" ? resp.categoriesSeo.title : "");
	$("input[name='categoriesSeo.keywords']").val(resp != "" ? resp.categoriesSeo.keywords : "");
	$("textarea[name='categoriesSeo.description']").val(resp != "" ? resp.categoriesSeo.description : "");
	$("input[name='categoriesSeo.headerContents1']").val(resp != "" ? resp.categoriesSeo.headerContents1 : "");
	$("input[name='categoriesSeo.headerContents2']").val(resp != "" ? resp.categoriesSeo.headerContents2 : "");
	$("textarea[name='categoriesSeo.headerContents3']").val(resp != "" ? resp.categoriesSeo.headerContents3 : "");
	$("input[name='categoriesSeo.themawordTitle']").val(resp != "" ? resp.categoriesSeo.themawordTitle : "");
	$("textarea[name='categoriesSeo.themawordDescription']").val(resp != "" ? resp.categoriesSeo.themawordDescription : "");
	
	/*
	$("input[name='rankSeo.title']").val(resp != "" ? resp.rankSeo.title : "");
	$("input[name='rankSeo.keywords']").val(resp != "" ? resp.rankSeo.keywords : "");
	$("textarea[name='rankSeo.description']").val(resp != "" ? resp.rankSeo.description : "");
	$("input[name='rankSeo.headerContents1']").val(resp != "" ? resp.rankSeo.headerContents1 : "");
	$("input[name='rankSeo.themawordTitle']").val(resp != "" ? resp.rankSeo.themawordTitle : "");
	$("textarea[name='rankSeo.themawordDescription']").val(resp != "" ? resp.rankSeo.themawordDescription : "");
	
	$("input[name='reviewSeo.title']").val(resp != "" ? resp.reviewSeo.title : "");
	$("input[name='reviewSeo.keywords']").val(resp != "" ? resp.reviewSeo.keywords : "");
	$("textarea[name='reviewSeo.description']").val(resp != "" ? resp.reviewSeo.description : "");
	$("input[name='reviewSeo.headerContents1']").val(resp != "" ? resp.reviewSeo.headerContents1 : "");
	$("input[name='reviewSeo.themawordTitle']").val(resp != "" ? resp.reviewSeo.themawordTitle : "");
	$("textarea[name='reviewSeo.themawordDescription']").val(resp != "" ? resp.reviewSeo.themawordDescription : "");
	*/
}

	
// 트리 Node 정보 가져오기.
function getNode(dataNodeId) {
	if (dataNodeId == '' || dataNodeId == '#') {
		return {
			'code': '',
			'level': 0,
			'ordering': 0,
			'childMaxLevel' : 0,
			'groupId' : 0,
		};
	}
	
	return {
		'code': dataNodeId.split('-')[0],
		'level': Number(dataNodeId.split('-')[1]),
		'ordering': Number(dataNodeId.split('-')[2]),
		'childMaxLevel': Number(dataNodeId.split('-')[3]),
		'groupId': Number(dataNodeId.split('-')[4]),
	};
}

function openFilterPopup(areaId) {
	Common.popup('/opmanager/categories-filter/list?target=popup&areaId='+areaId, 'categories-filter', 720, 800, 1);
}

function appFilterGroup(areaId, group) {

	var html = '',
		template = $('#filterAreaTemplate').html(),
		limit = 5,
		$filterArea = $('#'+areaId);

	var $elements = $filterArea.find('.filter-element');

	if (limit <= $elements.length) {
		alert('필터 그룹은 '+limit+'개 만 적용이 가능합니다.');
		return false;
	}

	if (typeof group != 'undefined' && group != null) {

		var id = group.id,
			duplicateFlag = false,
			name = '';

		name = areaId == 'reviewFilterArea' ? 'reviewFilterGroupIds' : 'filterGroupIds';

		for (var i=0; i<$elements.length; i++) {
			var $element = $elements.eq(i);

			var elementId = $element.find('input[name='+name+']').val();

			if (elementId == id) {
				duplicateFlag = true;
				break;
			}
		}

		if (duplicateFlag) {
			return false;
		}

		html = template.replaceAll('{{id}}', id)
			.replaceAll('{{name}}', name)
			.replaceAll('{{label}}', group.label)
			.replaceAll('{{codeString}}', group.codeString);

		$filterArea.append(html);
	}
}

</script>

<script type="text/html" id="filterAreaTemplate">
	<tr class="filter-element">
		<td class="label">
			{{label}}
		</td>
		<td>
			<div>
				{{codeString}}
			</div>
		</td>
		<td>
			<div>
				<a href="javascript:;" class="btn btn-gradient btn-sm remove-filter">삭제</a> &nbsp;
			</div>
			<input type="hidden" name="{{name}}" value="{{id}}"/>
		</td>
	</tr>
</script>