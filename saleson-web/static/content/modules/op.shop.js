var Shop = {
    'mobilePrefix' : '/m'
};


/**
 * 모바일 페이지 여부 (디바이스 체크 아님)
 */

Shop.isMobilePage = (function () {
    var mobileCheck = location.href.split("/");

    for (var index in mobileCheck) {
        if (mobileCheck[index] == "m") {
            return true;
        }
    }
    return false;
}());


Shop.url = function(url) {
    if (Shop.isMobilePage) {
        return Shop.mobilePrefix + url;
    }
    return url;
};


/**
 * 상품검색
 */
Shop.findItem = function(targetId) {
    Common.popup("/opmanager/item/find-item?targetId=" + targetId, 'find-item', 1200, 600, 1);
};



// 관련상품 추가
Shop.addRelationItem = function(id, item) {
    var key = id + '_item_' + item.itemId;
    var itemCount = $('#' + key).length;

    if (itemCount == 0) {
        if (typeof findItemCallback == 'function') {
            findItemCallback(id, item);
        } else {
            var ordering = $('input[name=' + id + 'ItemIds]').length + 1;
            var html = '';
            html += '<li id="' + key + '">';

            if (id == "group-banner") {
                html += '<input type="hidden" name="itemList" value="' + item.itemId + '" />';
            } else {
                html += '	<input type="hidden" name="' + id + 'ItemIds" value="' + item.itemId + '" />';
            }

            html += '	<p class="image"><img src="' + item.itemImage + '" class="item_image size-100 none" alt="상품이미지" /></p>';
            html += '	<p class="title">';
            html += '       [' + item.itemUserCode + ']<br />' + item.itemName;

            if (id == 'addition') {
                html += '   <br/>&#60;퀵배송 요금 ' + item.quickDeliveryExtraChargeFlag + '&#62;';
            } else if (id == 'display' && item.displayQuantity > 0) {
                html += '   <br/>&#60;노출 수량 ' + Common.numberFormat(item.displayQuantity) + '개&#62;';
            }

            html += '   </p>';
            html += '	<span class="ordering">' + ordering + '</span>';
            html += '	<a href="javascript:Shop.deleteRelationItem(\'' + key + '\')" class="delete_item_image"><img src="/content/opmanager/images/icon/icon_x.gif" alt="" /></a>';
            html += '</li>';

            $('#' + id).append(html);
        }
    }
};


// 관련상품 삭제
Shop.deleteRelationItem = function(key) {
    $('#' + key).remove();
};


// 관련상품 추가여부 확인
Shop.isAddedRelationItem = function(id, itemId) {
    var key = id + '_item_' + itemId;
    var itemCount = $('#' + key).length;
    if (itemCount == 0) {
        return false;
    }
    return true;
};


//관련상품 삭제
Shop.deleteRelationItemAll = function(targetId) {
    $('#' + targetId).find('li').remove();
};

Shop.isAvilableRelatedItemCount = function(targetId) {
    var count = $('#' + targetId).find('li').length;
    if (count > 20) {
        return false;
    }
    return true;
}

// 회원만 상세페이지 접속 가능.
Shop.loginToItemDetailPage = function(itemUserCode) {
    var message = Message.get("M00775") + '\n' + Message.get("M00602");	// 로그인 후 확인이 가능합니다.\n 로그인 페이지로 이동하시겠습니까?
    if (confirm(message)) {
        location.href="/users/login?target=/products/view/" + itemUserCode;
    }
};



var ShopEventHandler = {};


/**
 * 팀/그룹 > 1차 > 2차 > 3차 > 4차 셀렉트 박스 선택 이벤트
 */
var CURRENT_SELECT_CATEGORY_LEVEL;
ShopEventHandler.categorySelectboxChagneEvent = function() {

    if ($('#categoryGroupId').length > 0) {
        // 카테고리 그룹 선택
        $('#categoryGroupId').on('change', function(){
            var categoryGroupId = $(this).val();
            $('#categoryId').val("");
            Shop.getCategoriesByCategoryGroupId(categoryGroupId, "");
        });


        // 1차 카테고리 목록 조회 (by categoryGroupId)
        Shop.getCategoriesByCategoryGroupId = function(categoryGroupId, categoryClass1) {
            Common.loading.display = false;
            $.post('/categories/options-by-groupid', {'categoryGroupId' : categoryGroupId, 'categoryClass1': categoryClass1}, function(response) {
                var optionTitles = [];

                for (var i = 1; i <= 4; i++) {
                    optionTitles[i] = '<option value="">= ' + i + Message.get("M00075") + ' =</option>';	// {}차 카테고리
                }


                $('select.category:not(#categoryGroupId)').empty().each(function(i) {
                    $(this).append(optionTitles[i + 1]);
                });
                $('#categoryClass1').append(response);
            }, 'html');
        };

        // 1~3차 카테고리 선택
        $(document).on("change", '#categoryClass1, #categoryClass2, #categoryClass3', function() {
            var categoryClass = $(this).val();
            var categoryId = $(this).find('option:selected').attr('rel');

            $('#categoryId').val(categoryId);

            CURRENT_SELECT_CATEGORY_LEVEL = $(this).attr('id').replace('categoryClass', '');
            Shop.getCategorisByCategoryClass(CURRENT_SELECT_CATEGORY_LEVEL, categoryClass, "");
        });


        Shop.getCategorisByCategoryClass = function(categoryLevel, categoryClass, categoryClass1) {
            CURRENT_SELECT_CATEGORY_LEVEL = categoryLevel;
            $.post('/categories/options', {'categoryClass' : categoryClass, 'categoryClass1' : categoryClass1}, function(response) {
                var nextCategoryLevel = Number(CURRENT_SELECT_CATEGORY_LEVEL) + 1;

                for (var i = nextCategoryLevel; i <= 4; i++) {
                    var optionTitle = '<option value="">= ' + i + '' + Message.get("M00075") + ' =</option>';	// {}차 카테고리
                    $('#categoryClass' + i).empty().append(optionTitle);
                }
                $('#categoryClass' + nextCategoryLevel).append(response);
            }, 'html');
        };

        // 카테고리 검색 활성화
        Shop.activeCategoryClass = function(categoryGroupId, categoryClass1, categoryClass2, categoryClass3, categoryClass4) {
            $.ajaxSetup({'async': false});
            if (categoryGroupId != '') {
                Shop.getCategoriesByCategoryGroupId(categoryGroupId, categoryClass1);
            }

            if (categoryClass1 != '') {
                Shop.getCategorisByCategoryClass("1", categoryClass1, categoryClass2);
            }

            if (categoryClass2 != '') {
                Shop.getCategorisByCategoryClass("2", categoryClass2, categoryClass3);
            }

            if (categoryClass3 != '') {
                Shop.getCategorisByCategoryClass("3", categoryClass3, categoryClass4);
            }

            $.ajaxSetup({'async': true});
        };
    }
};



/**
 * 팀 > 그룹 > 1차
 */
var CURRENT_SELECT_CATEGORY_LEVEL;
ShopEventHandler.categorySelectboxChagneEvent2 = function() {

    if ($('#code').length > 0) {

        // 카테고리 그룹 선택

        $('#code').on('change', function(){
            var teamId = $(this).val();
            Shop.getCategoriesByCategoryTeamId(teamId, "");
        });

        $('#categoryGroupId').on('change', function(){
            var categoryGroupId = $(this).val();
            $('#categoryId').val("");
            Shop.getCategoriesByCategoryGroupId(categoryGroupId, "");
        });


        // 1차 카테고리 목록 조회 (by categoryGroupId)

        Shop.getCategoriesByCategoryTeamId = function(categoryTeamId, categoryGroupId) {
            Common.loading.display = false;
            $.post('/categories/options-by-groupid2', {'categoryTeamId' : categoryTeamId, 'categoryGroupId': categoryGroupId}, function(response) {
                var optionTitles = [];

                for (var i = 1; i <= 4; i++) {
                    optionTitles[i] = '<option value="">= ' + i + Message.get("M00075") + ' =</option>';	// {}차 카테고리
                }


                $('select.category:not(#categoryGroupId)').empty().each(function(i) {
                    $(this).append(optionTitles[i + 1]);
                });
                $('#categoryGroupId').html(response);
            }, 'html');
        };

        Shop.getCategoriesByCategoryGroupId = function(categoryGroupId, categoryClass1) {
            Common.loading.display = false;
            $.post('/categories/options-by-groupid', {'categoryGroupId' : categoryGroupId, 'categoryClass1': categoryClass1}, function(response) {
                var optionTitles = [];

                for (var i = 1; i <= 4; i++) {
                    optionTitles[i] = '<option value="">= ' + i + Message.get("M00075") + ' =</option>';	// {}차 카테고리
                }


                $('select.category:not(#categoryGroupId)').empty().each(function(i) {
                    $(this).append(optionTitles[i + 1]);
                });
                $('#categoryClass1').append(response);
            }, 'html');
        };


        // 1~3차 카테고리 선택
        $(document).on("change", '#categoryClass1, #categoryClass2, #categoryClass3', function() {
            var categoryClass = $(this).val();
            var categoryId = $(this).find('option:selected').attr('rel');

            $('#categoryId').val(categoryId);

            CURRENT_SELECT_CATEGORY_LEVEL = $(this).attr('id').replace('categoryClass', '');
            Shop.getCategorisByCategoryClass(CURRENT_SELECT_CATEGORY_LEVEL, categoryClass, "");
        });

        Shop.getCategorisByCategoryClass = function(categoryLevel, categoryClass, categoryClass1) {
            CURRENT_SELECT_CATEGORY_LEVEL = categoryLevel;
            $.post('/categories/options', {'categoryClass' : categoryClass, 'categoryClass1' : categoryClass1}, function(response) {
                var nextCategoryLevel = Number(CURRENT_SELECT_CATEGORY_LEVEL) + 1;

                for (var i = nextCategoryLevel; i <= 4; i++) {
                    var optionTitle = '<option value="">= ' + i + '' + Message.get("M00075") + ' =</option>';	// {}차 카테고리
                    $('#categoryClass' + i).empty().append(optionTitle);
                }
                $('#categoryClass' + nextCategoryLevel).append(response);
            }, 'html');
        };

        // 카테고리 검색 활성화
        Shop.activeCategoryClass2 = function(code, categoryGroupId, categoryClass1, categoryClass2, categoryClass3, categoryClass4) {
            $.ajaxSetup({'async': false});

            if (code != '') {
                Shop.getCategoriesByCategoryTeamId(code, categoryGroupId);
            }

            if (categoryGroupId != '') {
                Shop.getCategoriesByCategoryGroupId(categoryGroupId, categoryClass1);
            }

            if (categoryClass1 != '') {
                Shop.getCategorisByCategoryClass("1", categoryClass1, categoryClass2);
            }

            if (categoryClass2 != '') {
                Shop.getCategorisByCategoryClass("2", categoryClass2, categoryClass3);
            }

            if (categoryClass3 != '') {
                Shop.getCategorisByCategoryClass("3", categoryClass3, categoryClass4);
            }

            $.ajaxSetup({'async': true});
        };
    }
};

function seoCopy($event){
    var flag = $event.attr("id");
    var inputName = $event.prev().attr("name");
    var inputName2 = $event.prev().attr("name").replace("rankSeo","categoriesSeo").replace("reviewSeo","categoriesSeo");
    switch (flag)
    {
        case "rank_title":
        case "rank_headercontents":
        case "rank_themawordtitle":
            $("input[name='"+inputName+"']").val($("input[name='"+inputName2+"']").val().replace("通販", "売れ筋人気ランキング"));
            break;
        case "rank_keyword":
            $("input[name='"+inputName+"']").val($("input[name='"+inputName2+"']").val().replace("通販", "売れ筋,人気,ランキング"));
            break;

        case "rev_title":
        case "rev_headercontents":
        case "rev_themawordtitle":
            $("input[name='"+inputName+"']").val($("input[name='"+inputName2+"']").val().replace("通販", "口コミ・レビュー"));
            break;
        case "rev_keyword":
            $("input[name='"+inputName+"']").val($("input[name='"+inputName2+"']").val().replace("通販","口コミ,レビュー"));
            break;
    }

}

/**
 * 해당 차수에 child category의 옵션을 가져온다.
 * 파라미터가 없으면 : TEAM 목록
 * categoryTeamCode : GROUP 목록
 * categoryGroupCode : 1차 카테고리 목록
 * categoryUrl : 하위 카테고리 목록.
 *
 * @param param object = {'categoryTeamCode' : '', categoryGroupCode : '', categoryUrl : '', categoryCode: ''} // categoryCode ==> 활성화 코드.(selected="selected")
 * @param string options Html
 * @return
 */
Shop.getChildCategoryOptionsByCode = function(param) {
    var result = '';
    $.ajaxSetup({'async': false});

    $.post('/categories/options-by-category-code', param, function(response) {
        result = response;
    }, 'html');

    $.ajaxSetup({'async': true});

    return result;
};



// 상품 목록에서 장바구니 담기.
Shop.addToCart = function(itemId, orderMinQuantity, nonmemberOrderType, isLogin, target, itemType) {
    var quantity = Number(orderMinQuantity) > 1 ? Number(orderMinQuantity) : 1;

    /*if(!Shop.checkedBusinessItem(itemType, businessFlag)){
        return;
    }*/

    if (isLogin == 'false' && nonmemberOrderType == '2') {
        var message = Message.get("M00601") + '\n' + Message.get("M00602");	// 회원만 구매가 가능합니다. 로그인 페이지로 이동하시겠습니까?
        if (confirm(message)) {
            location.href = Shop.url('/users/login?target=' + target);
        }
        return false;
    }
    var param = {
        'arrayRequiredItems' : itemId + '||' + quantity + '||'
    };

    $.post(Shop.url('/cart/add-item-to-cart'), param, function(response) {
        Common.responseHandler(response, function() {

            Shop.getCartInfo();

            Shop.openCartWishlistLayer('cart');

        });
    }, 'json');
};

// 상단 탑 배너 조회
Shop.getTopBanner = function() {
    Common.loading.display = false;
    var $topBanner = $('#op-top-banner');

    if ($topBanner.length == 0) {
        return;
    }

    $.post('/common/top-banner', {}, function(response) {
        Common.responseHandler(response, function(response) {
            var images = response.data;

            if (images != null) {

                if (images.length > 0) {

                    var html = '';

                    html += '<div class="owl-carousel owl-theme">';

                    for (var i = 0; i < images.length; i++) {
                        var image = images[i];

                        if (image.displayImage != '') {
                            html += '<a href="' + (image.displayUrl == '' ? 'javascript:void(0);' : image.displayUrl) + '"><img src="' + image.displayImageSrc + '" alt="' + image.displayContent + '" /></a>';
                        }
                    }

                    html += '</div>';

                    $topBanner.html(html);

                    $topBanner.find(".owl-carousel").trigger('destroy.owl.carousel');
                    $topBanner.find(".owl-carousel").owlCarousel({
                        items : 1,
                        loop : true,
                        nav : true,
                        dots : false
                    });
                }
            }
        });
    });

};

//오늘 본 상품 조회
Shop.getTodayItems = function() {
    Common.loading.display = false;
    var $todayItems = $('.op-today-items');

    if ($todayItems.length == 0) {
        return;
    }

    $.post('/common/today-items', {}, function(response) {
        Common.responseHandler(response, function(response) {
            var items = response.data;

            if (items != null) {

                if (items.length > 0) {

                    var html = '';

                    html += '	<div class="quick_latest">최근 본 상품</div>';
                    html += '	<div class="recency-item">'
                    html += '		<div class="item_area">';
                    html += '			<ul>';
                    for (var i = 0; i < items.length; i++) {
                        var item = items[i];
                        html += '			<li style="position:relative;height:58px;overflow:hidden;"><a href="' + item.link + '"><img src="' + item.itemImage + '" alt="item1" /></a></li>';
                    }
                    html += '			</ul>';
                    html += '		</div>';

                    html += '		<div class="page">';
                    html += '			<a href="#" class="prev"><img src="/content/images/btn/btn_quick_prev.gif" alt="이전상품"></a>';
                    html += '			<p><strong>1</strong>/<span>0</span></p>';
                    html += '			<a href="#" class="next"><img src="/content/images/btn/btn_quick_next.gif" alt="다음상품"></a>';
                    html += '		</div>';
                    html += '	</div>';

                    $todayItems.html(html);

                    try {
                        Shop.quickMenuPagination();
                    } catch(e) {

                    }
                }
            }
        });
    });

};

//오늘 본 상품 조회
Shop.getMobileTodayItems = function() {
    Common.loading.display = false;
    var $todayItems = $('.op-today-items');

    if ($todayItems.length == 0) {
        return;
    }

    $.post('/common/today-items', {}, function(response) {
        Common.responseHandler(response, function(response) {
            var items = response.data;

            if (items != null) {

                if (items.length > 0) {

                    var html = '';

                    html += '	<div class="title"><h2>오늘 본 상품</h2></div>';
                    html += '		<div class="viewed_list">';
                    html += '			<ul>';
                    for (var i = 0; i < items.length; i++) {

                        if (i > 2) {
                            break;
                        }

                        var item = items[i];
                        var itemName = item.itemName;
                        if (itemName.length > 25) {
                            itemName = itemName.substring(0, 25) + '..';
                        }

                        html += '			<li>';
                        html += '				<div class="img"><a href="' + item.link + '"><img src="' + item.imageSrc + '" alt="item1" /></a></div>'
                        html += '				<div class="txt">';
                        html += '					<a href="' + item.link + '">';
                        html += '						<p class="name">'+ itemName +'</p>';
                        html += '						<p class="price">'+ Common.numberFormat(item.exceptUserDiscountPresentPrice) +'원</p>';
                        html += '					</a>';
                        html += '				</div>';
                        html += '			</li>';
                    }
                    html += '			</ul>';
                    html += '		</div>';

                    $todayItems.html(html);
                }
            }
        });
    });

};

<!-- 카카오픽셀 설치 [장바구니 추가 이벤트 전송, 관심상품 추가 이벤트 전송] -->
Shop.toCollectionScript = function(text, itemCode) {
    if (text == 'cart') {
        <!-- 카카오픽셀 설치 [장바구니 추가 이벤트 전송] -->
        kakaoPixel('1612698247174901358').addToCart({
            id: itemCode
        });
        //alert(itemCode);
        //alert('카카오픽셀 카트 스크립트');
    }

    if (text == 'wishlist') {
        <!-- 카카오픽셀 설치 [장바구니 추가 이벤트 전송] -->
        kakaoPixel('1612698247174901358').addToWishList({
            id: itemCode
        });
        //alert(itemCode);
        //alert('카카오픽셀 관심상품 스크립트');
    }
}


// 상품목록에서 관심상품 담기
Shop.addToWishList = function(itemId, orderMinQuantity,  isLogin, target, wishlistGroupId, itemType) {
    var quantity = Number(orderMinQuantity) > 1 ? Number(orderMinQuantity) : 1;

    /*if(!Shop.checkedBusinessItem(itemType, businessFlag)){
        return;
    }*/

    if (isLogin == 'false') {
        var message = "관심상품 등록은 회원만 가능합니다." + '\n' + Message.get("M00602");	// 관심상품 등록은 회원만 가능합니다. 로그인 페이지로 이동하시겠습니까?
        if (confirm(message)) {
            location.href = Shop.url('/users/login?target=' + target);
        }
        return false;
    }
    var param = {
        'arrayItemId' : itemId,
        'arrayQuantitys' : quantity,
        'arrayRequiredOptions' : '',
        'arraySelectionOptions' : '',
        'wishlistGroupId' : wishlistGroupId
    };

    $.post(Shop.url('/wishlist/add'), param, function(response) {
        Common.responseHandler(response, function() {

            Shop.getWishlistCount();

            Shop.openCartWishlistLayer('wishlist');

        });
    }, 'json');
};

Shop.checkedBusinessItem = function(itemType, businessFlag){

    if (itemType == "2" && businessFlag != "Y") {
        alert("사업자회원만 구매 가능합니다.");
        return false;
    } else {
        return true;
    }
}

//상품 목록에서 입하소식신청.
Shop.applyForArrival = function(itemId, isLogin, target) {

    // 1. 로그인 체크
    if (isLogin == 'false') {
        alert('ログインしてください。');
        location.href = Shop.url('/users/login?target=' + target);
        return;
    }

    // 2. 신청 처리
    var param = {
        'itemId': itemId,
        'itemOptionIds': ''
    };

    $.post(Shop.url('/resale/apply-for-arrival'), param, function(response) {
        Common.responseHandler(response, function() {
            var message = Message.get("M00598") + '\n' + Message.get("M00596");	// 입하 소식을 신청했습니다. 바로 확인하시겠습니까?
            if (confirm(message)) {
                location.href = Shop.url("/mypage/resale");
            }
        });
    });
};

//관심상품 수 조회
Shop.getWishlistCount = function() {
    if ($('#aside_wishlist_count').length == 0) {
        return;
    }


    $.post('/common/wishlist', {}, function(response) {
        Common.responseHandler(response, function(response) {
            var count = response.data;

            if (count > 0) {
                $('#aside_wishlist_count').text(Common.numberFormat(count));
            }
        });
    }, 'json');
};


//장바구니 정보 조회
Shop.getCartInfo = function() {
    Common.loading.display = false;
    $.post('/common/cart', {}, function(response) {
        Common.responseHandler(response, function(response) {
            var obj = response.data;

            if ($('#quick-menu').length > 0) {
                $('#aside_cart_quantity').text(Common.numberFormat(obj.cartQuantity));
                $('#header_cart_quantity').text(Common.numberFormat(obj.cartQuantity));
            } else {
                if (obj.cartQuantity > 0) {
                    $('#header_cart_quantity').text(Common.numberFormat(obj.cartQuantity)).show();
                    $('#footer_cart_quantity').text(Common.numberFormat(obj.cartQuantity)).show();
                }
            }
        });
    }, 'json');

};

// 상품 목록에서 상세페이지로 이동.
Shop.goItemDetails = function(itemUserCode, nonmemberOrderType, isLogin) {
    if (nonmemberOrderType == '3' && isLogin == 'false') {
        Shop.loginToItemDetailPage(itemUserCode);
    } else {
        location.href = Shop.url('/products/view/' + itemUserCode);
    }
};

Shop.closeOrderLayer = function(type) {
    $('#layer_'+type).hide();
    Common.dimmed.hide();
};

// 장바구니 / 위시리스트 확인창 열기
Shop.openCartWishlistLayer = function(type) {
    Common.dimmed.show();

    var layerCartHtml = $('#op-layer-' + type).htmlWithParent();
    $('#op-layer-' + type).remove();
    $('body').append(layerCartHtml);
    $('#op-layer-' + type).show();
};

//장바구니 / 위시리스트 확인창 닫기
Shop.closeCartWishlistLayer = function() {
    $('#op-layer-wishlist, #op-layer-cart').hide();
    Common.dimmed.hide();
    //모바일에서 위시리스트 백그라운드 닫기
    $('.layer_pop_bg').hide();
};

// [모바일] 팀, 그룹, 카테고리 페이지의 '카테고리에서 찾기'
Shop.findCategory = function() {
    // 카테고리 검색 (하위 카테고리 목록)
    var $category = $('.category_depth01 > li > a');
    if ($category.length > 0) {
        $category.on('click', function(e) {
            var hasChildCategories = $(this).next().length > 0 ? true : false;

            if (hasChildCategories) {
                e.preventDefault();

                var $parent = $(this).parent();
                var isActive = $parent.hasClass('on');

                $(this).closest('ul').find(' > li').removeClass('on');
                if (!isActive) {
                    $parent.addClass('on');
                }
            }

        });
    }
};



Shop.setHeightOfItemList = function(selector) {
    var $selector = $(selector);
    var h = $selector.height() - 2;
    $selector.css({'overflow': 'hidden', 'height': h + 'px'});
};

// breadcrumbs select.team
Shop.showAllCategoriesLayer = function() {

    if ($('#op-all-menus').css('display') == 'none') {
        $('#op-all-menu-button').addClass('on');
        $("#op-all-menus").addClass('show');
    } else {
        Shop.hideAllCategoriesLayer();
    }

    $("#op-all-menus").focus();

};

Shop.hideAllCategoriesLayer = function() {
    $('#op-all-menu-button').removeClass('on');
    $("#op-all-menus").removeClass('show');
    $("#op-all-menus").focus();
};

/**
 * 방문자 접속 통계를 저장.
 */
Shop.writeVisitorLog = function() {
    if ($.cookie('IS_VISITED') != "1") {
        $.post('/common/visitor-log', {}, function() {

        });
    }
};

Shop.quickMenuPagination = function() {
    // QUICK MENU
    var $quickMenu = $('#quick-menu');
    var $todayItems = $('.item_area ul');
    var todayItemCount = $todayItems.find('> li').length;
    var todayItemsIndex = 0;
    var itemHeight = 58;
    var itemsPerPage = 3;
    var totalPages = Math.ceil(todayItemCount / itemsPerPage);
    var currentPage = 1;

    $quickMenu.find('.item_area').css('height', itemHeight * (itemsPerPage < todayItemCount ? itemsPerPage : todayItemCount));

    $quickMenu.find('.page p span').text(totalPages);

    $('.page a.prev').on('click', function(e) {
        e.preventDefault();

        if (totalPages > 1) {
            if (currentPage == 1) {
                currentPage = totalPages;
            } else {
                currentPage--;
            }

            var topSize = (currentPage - 1) * itemHeight * itemsPerPage;
            $todayItems.css('margin-top', '-' + topSize + 'px');
            $quickMenu.find('.page p strong').text(currentPage);
        }



    });

    $('.page a.next').on('click', function(e) {
        e.preventDefault();

        if (totalPages > 1) {
            if (currentPage == totalPages) {
                currentPage = 1;
            } else {
                currentPage++;
            }

            var topSize = (currentPage - 1) * itemHeight * itemsPerPage;
            $todayItems.css('margin-top', '-' + topSize + 'px');
            $quickMenu.find('.page p strong').text(currentPage);
        }

    });
};

//장바구니 정보 조회
Shop.getCouponInfo = function() {
    Common.loading.display = false;
    $.post('/common/coupon', {}, function(response) {
        Common.responseHandler(response, function(response) {
            var obj = response.data;

            if ($('#aside_coupon_count').length > 0) {
                $('#aside_coupon_count').text(Common.numberFormat(obj.userCouponCount));
            }

            if ($('#aside_shipping_coupon_count').length > 0) {
                $('#aside_shipping_coupon_count').text(Common.numberFormat(obj.userShippingCount));
            }

        });
    }, 'json');

};



/**
 * 페이지 링크롤 POST로 전송 (일괄처리)
 *
 * 페이징 class에 .op-pagination 가 추가되어야 함.
 * submit되는 form의 selector 값을 파라미터로 설정함.
 * <form id="abd" ==> Shop.handlePagination('#abd');
 *
 * @author skc@onlinepowers.com
 * @date 2018-07-11
 */
Shop.handlePagination = function(formSelector) {
    var $pagination = $('.op-pagination');
    var $form = $(formSelector);
    if ($pagination.length == 0 || $form.length == 0) {
        return;
    }

    // 페이지 링크 이벤트 처리.
    $pagination.find('a').on('click', function(e) {
        var href = $(this).attr('href');

        var pattern = /page=[0-9]+/;
        var m = href.match(pattern);
        if (m != null) {
            e.preventDefault();

            var page = m[0].replace('page=', '');

            if ($form.find('input[name=page]').length == 0) {
                $form.append('<input type="hidden" name="page" value="' + page + '" />')
            } else {
                $form.find('input[name=page]').val(page);
            }

            $form.submit();
        }
    });
};

Shop.procSessionTimeout = function() {

	if (OP_USER_TIMEOUT == '' || OP_USER_TIMEOUT == '0') {
		return;
	}

	var timeout = Number(OP_USER_TIMEOUT) * 60 * 1000;

	setTimeout(function () {

		if (Shop.isMobilePage) {
			location.href = '/op_security_logout?target=/m';
		} else {
			location.href = '/op_security_logout?target=/';
		}

	}, timeout);
}

/**
 * 사은픔 검색
 * @param targetId
 */
Shop.findGiftItem = function(targetId) {
    Common.popup("/opmanager/gift-item/find-item?processType=progress&targetId=" + targetId, 'find-gift-item', 1000, 600, 1);
};

