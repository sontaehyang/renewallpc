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

<!-- 내용 : s -->
<div class="con">

	<div>
		${featured.featuredContent}
	</div>

	<c:if test='${(featured.prodState == "2" || featured.prodState == "3") && fn:length(itemTypeList) > 1 }'>
		<div class="search_result sel">
			<select id="searchBox">
				<option value="all">전체</option>
				<c:forEach var="itemType" items="${itemTypeList}" varStatus="i">
					<option value="${i.index }">${itemType[ITEM_TYPE_NAME_KEY]}</option>
				</c:forEach>
			</select>
		</div>
	</c:if>

	<!-- search_result -->
	<div class="product_wrap">
		<c:forEach var="itemType" items="${itemTypeList}" varStatus="i">
			<div class="product_cate type-name" id="type-name-${i.index}">
				<c:if test='${featured.prodState == "2" or featured.prodState == "3"}'>
					<span>${itemType[ITEM_TYPE_NAME_KEY]}</span>
				</c:if>
			</div>

			<ul class="product_list typeA type-list" id="type-list-${i.index }" >
				<c:set var="items" value="${itemListMap[itemType[ITEM_TYPE_ID_KEY]]}" scope="request"/>
				<jsp:include page="../include/item-featured-list.jsp" />
			</ul>
		</c:forEach>

		<div id="reply-list" class="featured-reply">
			<jsp:include page="reply-list.jsp" />
		</div>

	</div>
</div>

<page:javascript>
<script type="text/javascript">

    $(document).ready(function() {

		$('#searchBox').on('change', function() {
			var value = $('#searchBox option:selected').val();

			if (value != 'all') {
				$('.type-name').hide();
				$('.type-list').hide();
				$('#type-name-'+value).show();
				$('#type-list-'+value).show();
			} else {
				$('.type-name').show();
				$('.type-list').show();
			}
		});

	    <c:if test="${featured.replyUsedFlag eq 'Y'}">
		paginationFeaturedReply(1);
	    initFeaturedReply();
	    </c:if>

	    try {
		    var jsonString = '${itemUserCodes}';

		    if (jsonString != '' && jsonString != 'null') {
			    EventLog.featured(JSON.parse(jsonString));
		    }
	    } catch (e) {}
	});

    function initFeaturedReply() {

	    Common.checkedMaxStringLength($('#replyContent'), null, 250);

	    $('#reply-list').on('click','#replySubmit', function() {

		    var replyContent = $('#replyContent').val().trim();

		    if (replyContent == "") {
			    alert("댓글을 입력해주세요.");
			    return false;
		    }

		    $.ajax({
			    url: '/m/featured/write-reply',
			    type: 'POST',
			    data: {
				    'page': 1,
				    'replyContent': replyContent,
				    'featuredUrl': '${featuredUrl}',
				    'featuredId': '${featured.featuredId}'
			    },
			    success: function(response) {
				    if (response.isSuccess) {

					    alert('등록 되었습니다.');
					    //console.log("response.data: ", response.data);

					    $('#replyContent').val('');

					    paginationFeaturedReply(1);

				    } else {
					    alert(response.errorMessage);
				    }
			    }

		    });

	    });
    }

	function paginationFeaturedReply(page) {

	    var param = {
	        'page' : page,
            'featuredUrl': '${featuredUrl}',
            'featuredId': '${featured.featuredId}'
		};

        $.get('/m/featured-reply-list', param, function(response){
			$('#reply-list').html(response);
		});

	}
</script>
</page:javascript>