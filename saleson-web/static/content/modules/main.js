 /* main_visual */
	(function($) {
	$.fn.visualwrap = function(options) {
		var opts = $.extend({
				btn_play:$('.play'),
				btn_stop:$('.stop'),
				btn_page:$('.btn_page'),
				visualList : $(this),
				delayTime : 3000,
				fadeTime : 1600,
				imgIndex : 2,
				images_current : 0
			},$.fn.visualwrap.defaults,options);
			opts.visualList.hide();
			function imgfade(cur,nex){
				$(opts.visualList[cur]).css('z-index',opts.imgIndex).show();
				if (nex>=opts.visualList.size())
				{
					$(opts.visualList[0]).css('z-index',opts.imgIndex-1).show().parent().addClass('current');
				}else{
					$(opts.visualList[nex]).css('z-index',opts.imgIndex-1).show().parent().addClass('current');
				}
			};
			imgfade(opts.images_current,(opts.images_current+1));
			$(opts.visualList).parent().removeClass('current');
			$(opts.visualList[opts.images_current]).parent().addClass('current');
			var interval = setInterval(fninterval,opts.delayTime);
			function fninterval(){
				$(opts.visualList[opts.images_current]).fadeOut(opts.fadeTime,'linear',imgfade(opts.images_current,(opts.images_current+1))).parent().removeClass('current');
				opts.images_current+=1;
				if (opts.visualList.size()<=opts.images_current)opts.images_current=0;
			}
			$(opts.btn_page).each(function(index) {
				$(this).bind('click', function() {
					clearInterval(interval);
					if (opts.images_current != index)
					{
					$(opts.visualList[index]).css('z-index',opts.imgIndex+10).show();
					$(opts.visualList[opts.images_current]).css('z-index',opts.imgIndex+20).show();
					console.log(index+' / '+opts.images_current);
					$(opts.visualList[opts.images_current]).fadeOut(opts.fadeTime,'linear').parent().removeClass('current');
					$(opts.visualList[index]).parent().addClass('current');
					opts.images_current = index;
					}
	
	//				opts.images_current = index-1;
	//				fninterval();
				});
			});
	
			opts.btn_stop.bind('click', function() {
				clearInterval(interval);
			});
			opts.btn_play.bind('click', function() {
				clearInterval(interval);
				interval = setInterval(fninterval,opts.delayTime);
			});
	};
	})($); 





/*main popup_zone*/
$(document).ready(function(){

		//main popupzone
		$(".popup_area ul").carouFredSel({
			align: "left",
			width: 265,
			height: 64,
			items:{
				visible : 1
				},
			scroll:{
				items:1,
				duration: 500
				},
			direction: "left",
			pagination	: ".banner_btn"
		});
		$(".stop_popup").click(function() {
			$(".popup_area ul").trigger("pause");
		});
		$(".play_popup").click(function() {
			$(".popup_area ul").trigger("play");
		});
		$('.popup_area a').each(function(index) {
			$(this).bind('focus', function() {
				$(".popup_zone ul").trigger("pause");
			});
		});
	
});   //main popupzone 


/*footer banner*/
$(function(){	
	$(".scroll_wrap").each(function(){	
		var li = $(this).find("li");
		var ul = $(this).find("ul");
		var right = $(this).find(".right");
		var left = $(this).find(".left");
		var scroll = $(this).find(".scroll");
		var pos = 0;
		var li_width = 160;
		var totalWidth = li.width() * li.length;
		ul.width(totalWidth);		
		right.click(function(){
			if (pos == totalWidth-400) {return false;}
			pos += li_width;
			scroll.animate({scrollLeft: pos},500);
		});
		left.click(function(){
			if (pos == 0) {return false;}
			pos -= li_width;
			scroll.animate({scrollLeft: pos},500);
		});
	});
});	

//footer banner






