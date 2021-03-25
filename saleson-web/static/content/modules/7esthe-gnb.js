// Ready
$(function(){
 
	// gnb
	initCategory();
 
});


function initCategory() {
	$('.lnb_team > ul > li > a').on('focusin mouseenter', function() {
		$(this).parent().addClass('on');
		try {
			var $img = $(this).find('> img');
			$img.attr('src', $img.attr('src').replace('_on.gif', '.gif').replace('.gif', '_on.gif'));
		}catch(e){}
	}).on('focusout mouseleave', function() {
		$(this).parent().removeClass('on');
		try {
			var $img = $(this).find('> img');
			$img.attr('src', $img.attr('src').replace('_on.gif', '.gif'));
		}catch(e){}
	}).on('click', function(e) {
		e.preventDefault();
	});

	// 
	$('.lnb_group, span.arrow').on('focusin mouseover', function() {
		$(this).parent().addClass('on');
		$(this).addClass('on');
		
		try {
			var $img = $(this).parent().find('> a > img');
			$img.attr('src', $img.attr('src').replace('_on.gif', '.gif').replace('.gif', '_on.gif'));
		}catch(e){}
	}).on('focusout mouseout', function() {
		$(this).parent().removeClass('on');
		$(this).removeClass('on');
		try {
			var $img = $(this).parent().find('> a > img');
			$img.attr('src', $img.attr('src').replace('_on.gif', '.gif'));
		}catch(e){}
	});


	// 
	$('.lnb_group > ul > li > a').on('focusin mouseenter', function() {
		$(this).parent().addClass('on');
		$(this).addClass('on');

		var $groupMenu = $(this).closest('.lnb_group_menu');
		$groupMenu.addClass('on');
		
		try {
			var $img = $groupMenu.find('> a > img');
			$img.attr('src', $img.attr('src').replace('_on.gif', '.gif').replace('.gif', '_on.gif'));
		}catch(e){}
	}).on('focusout mouseleave', function() {
		$(this).parent().removeClass('on');
		$(this).removeClass('on');
		
		var $groupMenu = $(this).closest('.lnb_group_menu');
		$groupMenu.removeClass('on');
		
		try {
			var $img = $groupMenu.find('> a > img');
			$img.attr('src', $img.attr('src').replace('_on.gif', '.gif'));
		}catch(e){}
	});

	$('.lnb_layer_category').on('focusin mouseenter', function() {
		$(this).parent().addClass('on');
		$(this).parent().find('> a').addClass('on');
		$(this).addClass('on');

		var $groupMenu = $(this).closest('.lnb_group_menu');
		$groupMenu.addClass('on');

		try {
			var $img = $groupMenu.find('> a > img');
			$img.attr('src', $img.attr('src').replace('_on.gif', '.gif').replace('.gif', '_on.gif'));
		}catch(e){}
	}).on('focusout mouseleave', function() {
		$(this).parent().removeClass('on');
		$(this).parent().find('> a').removeClass('on');
		$(this).removeClass('on');
		
		var $groupMenu = $(this).closest('.lnb_group_menu');
		$groupMenu.removeClass('on');
		
		try {
			var $img = $groupMenu.find('> a > img');
			$img.attr('src', $img.attr('src').replace('_on.gif', '.gif'));
		}catch(e){}
	});

	$('.gnb_close').on('click', function(e) {
		e.preventDefault();
		$('.lnb_group_menu').removeClass("on");

	});
}