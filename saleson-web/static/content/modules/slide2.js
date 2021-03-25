

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
			next: '.next_popup',
			prev: '.prev_popup',
			direction: "left"
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

 
	



 


	
});  //document