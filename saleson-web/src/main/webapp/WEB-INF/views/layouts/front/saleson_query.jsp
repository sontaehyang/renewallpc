<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>

<form id="searchForm" class="searchForm" action="/products/result" method="get">
	<input type="hidden" name="where" value="ITEM_NAME">
	<fieldset>
		<input type="text" name="query" placeholder="" title="검색어를 입력하세요." class="search_box" autoComplete="off"  maxlength="50"/>
		<button type="submit" class="btn_search" title="검색">검색</button>
	</fieldset>

	<!-- 최근검색어, 검색어 자동완성 2017-05-12_seungil.lee -->
	<div id="op-query" class="sch_list_box">
		<div class="sch_list_tabs">
			<a href="#" data-tab="latest_search" class="on">최근 검색어</a>
			<a href="#" data-tab="recommend_search">추천 검색어</a>
		</div><!--// sch_list_tabs -->
		<div id="latest_search" class="searchArea">
			<ul></ul>
			<div class="btm">
				<button type="button" class="remove">전체 삭제</button>
				<button type="button" class="close">닫기</button>
			</div>
		</div>
		<div id="recommend_search" class="searchArea">
			<ul></ul>
			<div class="btm">
				<button type="button" class="close">닫기</button>
			</div>
		</div>
	</div>
	<!-- // 최근검색어, 검색어 자동완성 -->
</form>

<page:javascript>
<script type="text/javascript">
	var limit = 8;

	// cookie function 반드시 스크립트 루트에 위치 2017-05-15_seungil.lee
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

	// set placeholder, query onfocus 공백화, blurEvent()의 최하단에 초기화 기능 2017-05-16_seungil.lee
	var search = null;
	var $query = $('#searchForm input[name="query"]');
	function setPlaceholder() {
		$.ajaxSetup({
			"async" : "false"
		});

		$.post("/search", "", function(response){
			if (response.data != undefined && response.data.search != null) {
				$query.attr('placeholder', '#' + response.data.search.searchContents);
				search = response.data.search;
			}
		});

		// query에 onfocus일때 placeholder 공백화
		$query.on("focus", function(){
			$(this).attr('placeholder', '');
		});
	}

	$(function(){
		setPlaceholder();

		// 자동완성 스크립트
		$query.on('keyup', function(e){
			if (e.keyCode == 13) {
				if ($('#searchForm .searchArea:visible ul li.on').size()>0) {
					$query.val($('#searchForm .searchArea:visible ul li.on').text());
				}
			}
			autoComplete(e.keyCode);
		});

		// 커서 들어왔을 경우 검색어 자동완성 실행
		$query.on('focusin', function(){
			$("#op-query").show();
			autoComplete();
		});

		// 검색어 클릭 이벤트
		liEvent();

		// 검색창 닫기 이벤트
		closeEvent();

		// query blur event
		blurEvent();

		// 최근검색어 스크립트
		cookieInit();

		// 최근검색어 이벤트
		cookieDelEvent();

		// validator
		formValidator();
	});

	// 검색어 입력 여부에 따른 영역 숨김처리 2017-05-12_seungil.lee
	var nextIdx = 0;
	function toggleQuery() {
		if ($.trim($query.val()) != "") {
			$('#recommend_search').show();

			$("#latest_search").hide();
			$("#recommend_search").show();

			// 영역 반전시 on 클래스 조정 및 nextIdx 초기화
			$('#op-query a[data-tab="latest_search"]').removeClass('on');
			$('#op-query a[data-tab="recommend_search"]').addClass('on');
			nextIdx = 0;
		}
		else {
			$('#latest_search').show();

			$("#latest_search").show();
			$("#recommend_search").hide();

			// 영역 반전시 on 클래스 조정 및 nextIdx 초기화
			$('#op-query a[data-tab="recommend_search"]').removeClass('on');
			$('#op-query a[data-tab="latest_search"]').addClass('on');
			nextIdx = 0;
		}
	}

	// 자동완성 json파일 검색 후 불러오기 2017-05-12_seungil.lee
	function autoComplete(keyCode) {
		// 위아래 방향키일 경우 자동완성 실행하지 않고 event 넘기기
		if (keyCode == 38 || keyCode == 40) {
			arrowEvent(keyCode);
			return false;
		}

		var query = $.trim($query.val());
		toggleQuery();

		var param = {
			"query": query,
			"limit": limit
		};

		Common.loading.display = false;

		// 미리 저장 된 json파일 불러오기 (DB access 없음) - /keyword/keyword-init 으로 즉각 반영 가능
		$.post("/keyword/auto-complete", param, function(response){
			var autocomplete = "";

			if (response.length > 0) {
				$("#recommend_search ul").html("");
				$.each(response, function(i, data){
					var keyword = data.keyword;

					// 검색어와 정확히 일치하는 부분만 highlight 시켜주기
					keyword = keyword.replace(query, "<highlight>"+query+"</highlight>");
					autocomplete += "<li><span>"+ keyword +"</span></li>";
				});
			}
			else {
				autocomplete += getNoResultHtml();
			}

			$("#recommend_search ul").html(autocomplete);
		});

		Common.loading.display = true;
	}

	function arrowEvent(keyCode) {
		if (keyCode == 38 || keyCode == 40) {
			if ($('#searchForm .searchArea:visible ul li.on').size()>0) {
				// 40(↓), 38(↑) nextIdx 조정
				if (keyCode == 40) {
					nextIdx = $('#searchForm .searchArea:visible ul').find('li.on').index()+1;
				}
				else {
					nextIdx = $('#searchForm .searchArea:visible ul').find('li.on').index()-1;
				}

				// li가 마지막이고 다음 선택이면 첫번째, 처음이고 이전선택이면 마지막 li로 설정
				if ($('#searchForm .searchArea:visible ul li').size()==nextIdx) {
					nextIdx = 0;
				}
				else if (nextIdx < 0) {
					nextIdx = $('#searchForm .searchArea:visible ul li').size()-1;
				}
				// addClass 전에 on 클래스 모드 제거
				$('#searchForm ul li.on').removeClass('on');
				$('#searchForm .searchArea:visible ul li').eq(nextIdx).addClass('on');
				$query.val($('#searchForm .searchArea:visible ul li.on').text());
			}
			else {
				// on 클래스가 없을 경우 초기 지정
				if (keyCode == 40) {
					$('#searchForm .searchArea:visible ul li').eq(0).addClass('on');
					$query.val($('#searchForm .searchArea:visible ul li.on').text());
				}
				else if (keyCode == 38) {
					$('#searchForm .searchArea:visible ul li').eq($('#searchForm .searchArea:visible ul li').size()-1).addClass('on');
					$query.val($('#searchForm .searchArea:visible ul li.on').text());
				}
			}
		}
	}

	// 검색어 click event 2017-05-12_seungil.lee
	function liEvent() {
		$('#searchForm ul li span').unbind('click');
		$('#searchForm ul').on('click', 'li span', function(){
			// 결과없음 예외처리
			if ($(this).hasClass("noResult")) {
				return false;
			}

			$query.val($(this).text());

			// 최근검색어 쿠키에 추가 2017-05-15_seungil.lee
			if ($query.val() != "") {
				var list = new cookieList("latest");
				list.add($query.val());
			}

			$('#searchForm').submit();
		});

		$('#searchForm ul li').unbind('hover');
		$('#searchForm ul li').hover(function(){
			$('#searchForm ul li.on').removeClass("on");
		});
	}

	// 검색창 닫기 이벤트
	function closeEvent() {
		$('#searchForm .searchArea').on('click', 'button.close', function(){
			$('#op-query').hide();
		});
	}

	// 검색어 input에서 focus blur event 2017-05-12_seungil.lee
	function blurEvent() {
		$query.on('blur', function(e){
			$(document).mouseup(function (e){
				var searchLayer = $('.header_search');
				if (searchLayer.has(e.target).length === 0 && $(this).blur()) {
					$("#op-query").hide();

					// 숨김시 li.on 에서 on class 제거
					$('#searchForm ul li.on').removeClass("on");
				}
			});

			// query blur시에 공백화된 placeholder를 초기화
			if (search != null && typeof search.searchContents != "undefined" && search.searchContents != "") {
				$(this).attr('placeholder', '#' + search.searchContents);
			}
		});
	}

	// searchForm submit시 검색어와 추천 검색어 확인 후 link로 연결
	function formValidator() {
		$("#searchForm").validator(function(){
			if ($('#searchForm .searchArea:visible ul li.on').size()>0) {
				$query.val($('#searchForm .searchArea:visible ul li.on').text());
			}

			if ($query.val() == ""
				&& search != null && typeof search.searchContents != "undefined" && search.searchContents != "") {

				// 팝업 혹은 링크 연결
				if (search.searchLinkTargetFlag == "Y") {
					Common.popup(search.searchLink, search.searchContents, 1400, 800, "auto", 0, 0);
				}
				else {
					location.href = search.searchLink;
				}

				return false;
			}

			// 최근검색어 쿠키에 추가 2017-05-15_seungil.lee
			if ($query.val() != "") {
				var list = new cookieList("latest");
				list.add($query.val());
			}
		});
	}

	// 최근검색어 스크립트
	function cookieInit() {
		var list = new cookieList("latest");
// 	 	list.clear();
		var temp = list.items()+"";
		var key = temp.split(",");
		var keywordHtml = "";
		for (var i=0; i<key.length; i++) {
			if (key[i] != "") {
				keywordHtml += "<li><span>"+key[i]+"</span><button class='del' title='해당 검색어 삭제'></button></li>";
			}
		}

		if (keywordHtml == "") {
			keywordHtml += getNoResultHtml();
		}

		$("#latest_search ul").html(keywordHtml);
	}

	// 최근 검색어 삭제버튼 클릭 이벤트 2017-05-15_seungil.lee
	function cookieDelEvent() {
		var list = new cookieList("latest");

		// 삭제
		$("#latest_search ul li").on("click", "button.del", function(e) {
			e.stopPropagation();

			// 해당 검색어 삭제
			list.del($(this).parent().text());
			$(this).parent().remove();

			if ($("#latest_search ul li").length == 0) {
				$("#latest_search ul").html(getNoResultHtml());
			}
		});

		// 전체 삭제
		$("#latest_search").on("click", "button.remove", function() {
			list.remove();
			$("#latest_search ul").html(getNoResultHtml());
		});
	}

	function getNoResultHtml() {
		return "<li class=\"noResult\">검색된 데이터가 없습니다.</li>";
	}
</script>
</page:javascript>