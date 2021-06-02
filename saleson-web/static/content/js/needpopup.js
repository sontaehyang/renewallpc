/*********************************************** 
  needPopup 
  - Version 1.0.0
  - Copyright 2015 Dzmitry Vasileuski
	- Licensed under MIT (http://opensource.org/licenses/MIT)
***********************************************/

// popup object
var needPopup = (function() {

	// namespace
	var popup = {};
	// 캐시 된 노드
	popup.html = document.documentElement,
	popup.body = document.body,
	popup.window = window,
	// 상태 변수
	popup.target = 0, 
	// 창 스크롤 상단
	popup.scrollTopVal = 0,
	// 창 스크롤 높이
	popup.scrollHeight = popup.body.scrollHeight > popup.html.scrollHeight ? popup.body.scrollHeight : popup.html.scrollHeight,
	// 팝업이 열릴 때 html 클래스
	popup.openHtmlClass = popup.scrollHeight > popup.window.innerHeight ? 'needpopup-opened needpopup-scrolled' : 'needpopup-opened';

	// 전역 메서드 및 속성
	return {

		/* 팝업 초기화
		***********************************************/
		init : function() {

			// 기본 구성 설정
			popup.options = needPopup.config.default;

			// 팝업 쇼를 data-popupshow 노드에 바인딩
			$(popup.body).on('click','[data-needpopup-show]', function(event) {
				event.preventDefault();
				needPopup.show($(this).data('needpopupShow'),$(this));
			})
			

			// 제거제에 팝업 숨기기 바인딩
			$(popup.body).on('click','.needpopup_wrapper .remove, .needpopup_remover', function(event) {
				event.preventDefault();
				needPopup.hide();
			})
			

			// 외부를 클릭하면 팝업 숨기기 바인딩
			$(popup.body).on('click','.needpopup_wrapper', function(event) {
				console.log($(event.target).is('.needpopup_wrapper'));
				if (!$(event.target).is('.needpopup_wrapper')) return;

				event.preventDefault();
				if (!popup.options.closeOnOutside) return;

				// 팝업 창 밖에서 클릭되었는지 확인
				if ($(event.target).closest('.needpopup').length || $(event.target).is('.needpopup, .remove, .needpopup_remover')) return;

				needPopup.hide();
			})

			// Esc에서 팝업 숨기기
			$(document).keydown(function(event){
				if (event.which == 27) {
					needPopup.hide();
				}
			})

			// 창 크기 조정시 수정
			popup.resizeTimeout = 0;
			popup.resizeAllowed = true;
			$(popup.window).on('resize',function() {
				// throttling
				clearTimeout(popup.resizeTimeout);
				if (popup.resizeAllowed) {
          popup.resizeAllowed = false;

					// centrify popup
					needPopup.centrify();
					// 창 스크롤 높이 재 계산
					popup.scrollHeight = popup.body.scrollHeight > popup.html.scrollHeight ? popup.body.scrollHeight : popup.html.scrollHeight,
					// 필요한 경우 열린 htmml 클래스 변경
			 		popup.openHtmlClass = popup.scrollHeight > popup.window.innerHeight ? 'needpopup-opened needpopup-scrolled' : 'needpopup-opened';
			 	}
        popup.resizeTimeout = setTimeout(function() {
          popup.resizeAllowed = true;
        }, 100);
			})

			// 팝업 래퍼 만들기
			popup.wrapper = document.createElement('div');
			popup.wrapper.className = 'needpopup_wrapper';
			popup.body.appendChild(popup.wrapper);
			popup.wrapper = $(popup.wrapper);
		},

		/* Show popup
		***********************************************/
		show : function(_target, _trigger) {

			// 팝업 트리거가있는 경우 저장
			if (!_trigger)
				popup.trigger = 0;
			else
				popup.trigger = _trigger;

			// 이미 열린 팝업 숨기기
			if (popup.target)
				needPopup.hide(true);
			else {
				// 창 스크롤 상단 다시 계산
				popup.scrollTopVal = popup.window.pageYOffset;

				// 블록 페이지 스크롤
				$(popup.body).css({'top': -popup.scrollTopVal});
				$(popup.html).addClass(popup.openHtmlClass);
			}
			
			// 타겟 팝업 설정
			popup.target = $(_target);

			// 정의 된 경우 옵션 재설정
			popup.options = needPopup.config['default'];
			if (!!popup.target.data('needpopupOptions'))
				$.extend( popup.options, needPopup.config[popup.target.data('needpopupOptions')] );


			// 캐시 팝업 너비
			popup.minWidth = popup.target.outerWidth();

			// 레이아웃 생성
			popup.wrapper.append(popup.target);
			if (popup.options.removerPlace == 'outside')
				popup.wrapper.after('<a href="#" class="needpopup_remover"></a>');
			else if (popup.options.removerPlace == 'inside')
				popup.target.append('<a href="#" class="needpopup_remover"></a>'); 

			// 쇼 콜백 전에
			popup.options.onBeforeShow.call(popup,popup.target);
			
			// display popup
			popup.target.show();
			// centrify popup
			needPopup.centrify();
			// 팝업에 열린 클래스 추가 (전환 시간 초과)
			setTimeout(function(){
				popup.target.addClass('opened');

				// 쇼 콜백
				popup.options.onShow.call(popup,popup.target);
			},10);
			
		},

		/* 팝업 숨기기
		***********************************************/
		hide : function(_partial) {

			// 팝업 숨기기
			popup.target.hide().removeClass('opened');
			// 제거제 삭제
			//$('.embed-container').empty();
			$('.needpopup_remover').remove();

			// 완전 은신
			if (!_partial) {

				// 페이지 스크롤 차단 해제
				$(popup.html).removeClass(popup.openHtmlClass).removeClass('needpopup-overflow');
				$(popup.body).css({'top': 0}).scrollTop(popup.scrollTopVal);
				$(popup.html).scrollTop(popup.scrollTopVal);
			}

			// 콜백 숨기기
			popup.options.onHide.call(popup,popup.target);

			// 설정되지 않은 속성
			popup.target = 0;
		},

		/* 팝업을 Centrify하고 반응 형 클래스 설정
		***********************************************/
		centrify: function() {
			if (popup.target) {
				// 수직 중심화
				if (popup.target.outerHeight() > popup.window.innerHeight)
					popup.target.addClass('stacked');
				else
					popup.target.removeClass('stacked').css({'margin-top':-popup.target.outerHeight()/2, 'top':'50%'});

				// 폭 오버플로 감지
				popup.minWidth = $(popup.html).hasClass('needpopup-overflow') ? popup.minWidth : popup.target.outerWidth();
				if (popup.minWidth + 30 >= popup.window.innerWidth) {
					$(popup.html).addClass('needpopup-overflow');
				} else {
					$(popup.html).removeClass('needpopup-overflow');
				}
			}
		},

		/* 모든 옵션 세트를 포함하는 구성 객체
		***********************************************/
		'config': {
			'default' : {
				// 래퍼에 배치하려면 '외부', 팝업에 배치하려면 '내부'
				'removerPlace': 'inside',
				// 팝업 외부 클릭시 닫기
				'closeOnOutside': true,
				// 쇼 콜백
				onShow: function() {},
				// 쇼 콜백 전에
				onBeforeShow: function() {},
				// 콜백 숨기기
				onHide: function() {}
			}
		}

	}

})();
