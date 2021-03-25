/* layerpopup */

function layer_open(id){
		var all = $('#' + id);
		var temp = all.find('.pop_layer');
		var bg = temp.prev().hasClass('bg');	//dimmed 레이어를 감지하기 위한 boolean 변수

		if(bg){
			all.fadeIn();	//'bg' 클래스가 존재하면 레이어가 나타나고 배경은 dimmed 된다. 
		}
			temp.fadeIn();
		

		// 화면의 중앙에 레이어를 띄운다.
		if (temp.outerHeight() < $(window).height() ) temp.css({'position': 'fixed', 'top': '50%', 'margin-top': '-'+temp.outerHeight()/2+'px'});
		else temp.css({'position': 'absolute', 'top': '0px', 'margin-top': '0px'});
		
		if (temp.outerWidth() < $(document).width() ) temp.css('margin-left', '-'+temp.outerWidth()/2+'px');
		else temp.css('left', '0px');

		temp.find('a.cbtn, button.cbtn').click(function(e){
			if(bg){
				$('.layer').fadeOut(); //'bg' 클래스가 존재하면 레이어를 사라지게 한다. 
			}else{
				temp.fadeOut();
			}
			e.preventDefault();
		});

		$('.layer .bg').click(function(e){	//배경을 클릭하면 레이어를 사라지게 하는 이벤트 핸들러
			$('.layer').fadeOut();
			e.preventDefault();
		});

	}	

function closeLayer(m) {
	var all = $('#' + m);
	var temp = all.find('.pop_layer');
	var bg = temp.prev().hasClass('bg');	//dimmed 레이어를 감지하기 위한 boolean 변수
	
		$('.layer').fadeOut(); //'bg' 클래스가 존재하면 레이어를 사라지게 한다. 
		temp.fadeOut();
	
}
