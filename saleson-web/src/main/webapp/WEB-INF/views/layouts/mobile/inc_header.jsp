<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>

<div class="header">
	<h1><a href="/m"><img src="/content/mobile/images/main/renewallpc_logo.png" alt="renewallpc"></a></h1>
	<div class="side_menu_btn"><button type="button"><img src="/content/mobile/images/common/header_category_btn.png" alt="카테고리"></button></div>
	<div class="search"><a href="javascript:void(0);"></a></div>
	<div class="cart">
		<a href="/m/cart"></a><span class="num op-header-cart-quantity" id="header_cart_quantity">0</span>
	</div>
</div>

<!--  검색 팝업창 : s  -->
<div class="header_search">
	<form id="searchForm" action="/m/products/result" name="searchParam" onsubmit="latelyAdd()">
		<input type="hidden" name="where" value="ITEM_NAME" />
		<fieldset>
			<div class="form_title">
				<h3>검색</h3>
			</div>
			<button type="button" data-dismiss="modal" class="modal_close">
				<span class="screen_out">팝업 닫기</span>
			</button>
			<div class="search_wrap">
				<div class="title">
					<h3>어떤 상품을 찾고 계세요?</h3>
				</div>
				<div class="input_box">
					<input type="text" id="headerSearch" name="query" title="검색어를 입력해주세요" class="form-control box" placeholder="검색어를 입력해주세요" autoComplete="off" maxlength="50"/>
					<button type="submit" class="search_btn">
						<img src="/content/mobile/images/common/ico_search_orange.png" alt="검색 버튼">
					</button>
				</div>
				<label for="headerSearch" class="screen_out">검색하기</label>
			</div>

			<!--검색 모달창 > 인기,최근 검색어 탭-->
			<div id="op-query" class="search_box">
				<div class="tab_con">
					<ul>
						<li data-tab="latest_search" class="on"><a href="#">#최근검색어</a></li>
						<li data-tab="recommend_search"><a href="#">#추천검색어</a></li>
					</ul>
				</div>
				<div id="latest_search" class="tab_contents_con on">
					<ul class="history_list"></ul>
				</div>
				<div id="recommend_search" class="tab_contents_con">
					<ul class="history_list"></ul>
				</div>
			</div>
		</fieldset>
	</form>
	<!--  검색 팝업창 : e  -->
</div>

<page:javascript>
<script type="text/javascript">
	var searchWord = null;
	var $query = $('#searchForm input[name="query"]');
	var limit = 8;
	var cookieList = function(cookieName) {
		var cookie = $.cookie(cookieName);
		var items = cookie ? cookie.split(/,/) : new Array();

		// 출력 추천검색어 개수
		if (items.length > limit) {
			items.splice(0, items.length-limit);
		}

		return {
			"add" : function(val) {
				if (items.indexOf(val) >= 0) {
					items.splice(items.indexOf(val),1);
				}
				items.push(val);
				$.cookie(cookieName, items, { path: "/" });
			},
			"del" : function(val) {
				for (var i=0; i<items.length; i++) {
					if (items[i] == val) {
						items.splice(i,1);
					}
				}
				$.cookie(cookieName, items, { path: "/" });
			},
			"clear" : function() {
				items = "";
				$.cookie(cookieName, "");
			},
			"remove": function() {
				$.removeCookie(cookieName, { path: "/" });
			},
			"items" : function() {
				if (items.length > 0) {
					return items.reverse();
				}
				else {
					return items;
				}
			}
		}
	};

	$(function(){
		// 인기검색어 설정
		setPlaceholder();

		// 추천검색어 스크립트
		$query.on('keyup', function(e){
			if (e.keyCode == 13) {
				if ($('#searchForm .tab_contents_con:visible ul li.on').size()>0) {
					$query.val($('#searchForm .tab_contents_con:visible ul li.on').text());
				}
			}
			autoComplete(e.keyCode);
		});

		// 추천검색어 스크립트
		autoComplete();

		// 검색어 클릭 이벤트
		liEvent();

		// query blur event
		blurEvent();

		// 최근검색어 스크립트
		cookieInit();

		// 최근검색어 이벤트
		cookieDelEvent();

		// validator
		formValidator();
	});

	// 인기검색어 설정
	function setPlaceholder() {
		$.ajaxSetup({
			"async" : "false"
		});

		$.post("/search", "", function(response){
			if (response.data != undefined && response.data.search != null) {
				$query.attr('placeholder', '#' + response.data.search.searchContents);
				searchWord = response.data.search;
			}
		});

		// query에 onfocus일때 placeholder 공백화
		$query.on("focus", function(){
			$(this).attr('placeholder', '');
		});
	}

	// 추천검색어 스크립트
	function autoComplete() {
		Common.loading.display = false;

		var query = $.trim($query.val());
		toggleQuery();

		var param = {
			"query": query,
			"limit": limit
		};

		// 미리 저장 된 json파일 불러오기 (DB access 없음) - /keyword/keyword-init 으로 즉각 반영 가능
		$.post("/keyword/auto-complete", param, function(response){
			var autocomplete = "";

			if (response.length > 0) {
				$("#recommend_search ul").html("");
				$.each(response, function(i, data){
					var keyword = data.keyword;

					// 검색어와 정확히 일치하는 부분만 highlight 시켜주기
					keyword = keyword.replace(query, "<highlight>"+query+"</highlight>");
					autocomplete += "<li class='popular_search'>" +
							"<span class='popular_ranking_num'>" + (i + 1) + "</span>" +
							"<span class='popular_ranking_tit'>"+ keyword +"</span>" +
						"</li>";
				});
			}

			$("#recommend_search ul").html(autocomplete);
		});

		Common.loading.display = true;
	}

	// 검색어 클릭 이벤트
	function liEvent() {
		$('#op-query ul li span.popular_ranking_tit').unbind('click');
		$('#op-query ul').on('click', 'li span.popular_ranking_tit', function(){
			$query.val($(this).text());

			// 최근검색어 쿠키에 추가
			if ($query.val() != "") {
				var list = new cookieList("latest");
				list.add($query.val());
			}

			$('#searchForm').submit();
		});
	}

	// 검색어 input에서 focus blur event
	function blurEvent() {
		$query.on('blur', function(e){
			// query blur시에 공백화된 placeholder를 초기화
			if (searchWord != null && typeof searchWord.searchContents != "undefined" && searchWord.searchContents != "") {
				$(this).attr('placeholder', '#' + searchWord.searchContents);
			}
		});
	}

	// 최근검색어 스크립트
	function cookieInit() {
		var list = new cookieList("latest");
		var temp = list.items()+"";
		var key = temp.split(",");
		var keywordHtml = "";
		for (var i=0; i<key.length; i++) {
			if (key[i] != "") {
				keywordHtml += "<li class='popular_search'><span class='popular_ranking_tit'>"+key[i]+"</span><button type='button' class='close_btn del'></button></li>";
			}
			/*else if (key[i] == "" && key.length == 1){
				keywordHtml += "<li style='text-align:center;padding:0;'>최근 검색어가 없습니다.</li>";
			}*/
		}

		$("#latest_search ul").html(keywordHtml);
	}

	// 최근 검색어 삭제버튼 클릭 이벤트
	function cookieDelEvent() {
		var list = new cookieList("latest");
		$("#latest_search ul li").on("click", ".del",function(){
			list.del($(this).parent().text());
			$(this).parent().remove();
			//cookieDelInit();
		});
	}

	// 검색타입 전환 이벤트
	function toggleQuery() {
		if ($.trim($query.val()) != "") {
			$('#op-query li[data-tab="recommend_search"]').trigger('click');
		}
		else {
			$('#op-query li[data-tab="latest_search"]').trigger('click');
		}
	}

	// searchForm submit시 검색어와 추천 검색어 확인 후 link로 연결
	function formValidator() {
		$("#searchForm").validator(function() {
			if ($('#searchForm .tab_contents_con:visible ul li.on').size() > 0) {
				$query.val($('#searchForm .tab_contents_con:visible ul li.on').text());
			}

			if ($query.val() == ""
					&& searchWord != null && typeof searchWord.searchContents != "undefined" && searchWord.searchContents != "") {

				// 팝업 혹은 링크 연결
				if (searchWord.searchMobileLinkTargetFlag == "Y") {
					window.open(searchWord.searchMobileLink);
				} else {
					location.href = searchWord.searchMobileLink;
				}

				return false;
			}

			// 최근검색어 쿠키에 추가
			if ($query.val() != "") {
				var list = new cookieList("latest");
				list.add($query.val());
			}
		});
	}

	function latelyAdd() {
		var list = new cookieList("latest");
		list.add($query.val());
	}
</script>
</page:javascript>
