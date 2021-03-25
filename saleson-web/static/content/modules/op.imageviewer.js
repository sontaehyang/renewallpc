/**
 * 상품 상세 페이지 이미지 슬라이더 
 * op.imageviewer.js
 * 
 * <pre>
 * 상품 상세이미지의 섬네일 목록을 클릭하거나
 * 섬네일 방향키를 이동하여 섬네일에 해당하는 상세 이미지를 
 * 확인한다..
 * </pre>

 * @author skc@onlinepowers.com
 * @date 2014.06.13
 */
(function ($) {


	var defaultOptions = {
		vertical: false,
		itemSize: 70,
		gapSize: 0,
		displayCount: 5,
		prefix: 'skc',
		activeClass: 'on'
			
	};

	var methods = {
		init: function (options) {

			return this.each(function () {

				var $this = $(this);
				var data = $this.data('options');

				if (!data) {
					options = $.extend(defaultOptions, options);
					$(this).data('options', options);
				}

				$.imageviewer.init($this, options);
			});
		}
		
	};


	$.fn.imageviewer = function (method) {

		if (methods[method]) {
			return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));

		} else if (typeof method === 'object' || !method) {
			return methods.init.apply(this, arguments);

		} else if (typeof method === 'function' || !method) {
			return methods.submitHandler.apply(this, arguments);

		} else {
			$.error(method + '는 존재하지 않는 Method 입니다.');

		}
	};



	// 스태틱 메서드로 지정 (Static Method)
	$.imageviewer = {
		selector: null,
		thumbnails: null,
		options: null,
		activeIndex: 1,
		
		init: function ($selector, options) {
			this.selector = $selector;
			this.options = options;
			
			var $thumbnails = this.thumbnails = $selector.find('li');
			var displayHeight = (options.displayCount * options.itemSize) - ((options.displayCount - 1) * options.gapSize);
			
			
			var imageCount = $thumbnails.size();
			if (imageCount > 0) {
				var position = options.vertical ? 'top' : 'left';
				var direction = options.vertical ? 'height' : 'width';
				
				// 초기화
				$selector.find('ul').css(direction, displayHeight + 'px').css('overflow', 'hidden');
				
				// 모션 타입 
				var motionType = 'type1';		// type1 : 표시되는 수보다 아이템 수가 작거나 같은 경우, 애니메이션 없이 포커스만 이동.
				
				if (imageCount == options.displayCount + 1) {
					motionType = 'type2';		// type2 : 표시되는 수보다 아이템 수가 1개만 많은 경우.
				
				} else if (imageCount >= options.displayCount + 2) {
					motionType = 'type3';		// type3 : 표시되는 수보다 아이템 수가 2개 이상 많은 경우.
				
				}
				
				// 아이템 위치 설정.
				$thumbnails.each(function(i) {
					var index = i + 1;
					if (motionType == 'type3' && index == imageCount) {
						index = 0;
					}

					var top = ((index - 1) * options.itemSize) - (options.gapSize * (index - 1));
					$(this).attr('id', options.prefix + index).css(position, top + 'px');
				});
				
				
				
				// 아이템 클릭.
				$selector.find('li > a').on('click', function(e) {
					e.preventDefault();
					
					var index = $(this).parent().attr('id').replace(options.prefix, '');
					
					$.imageviewer.view(index);
					
				});
				
				// 아이템 마우스 오버 이벤트
				$selector.find('li > a').not('.on').on('mouseenter', function(e) {
					e.preventDefault();
					$(this).parent().css('z-index', '2');
					
				}).on('mouseleave', function(e) {
					e.preventDefault();
					$(this).parent().css('z-index', '1');
					
				});
				
				
				// 이전 버튼 이벤트
				$selector.find('.prev').on('click', function(e) {
					e.preventDefault();
					if ($(this).parent().find('li').is(':animated')) {
						return;
					}
					
					if ($.imageviewer.activeIndex > 1) {
						$.imageviewer.view($.imageviewer.activeIndex - 1);
					} else {
						if (motionType == 'type1' && $.imageviewer.activeIndex == 1) {
							$.imageviewer.view(imageCount);
							return;
						}
						
						
						var hasZeroId = $('#' + options.prefix + '0').size() > 0 ? true : false;
						$thumbnails.each(function(i) {
							var currentId = Number($(this).attr('id').replace(options.prefix, ''));
							var newId = currentId + 1 == imageCount ? 0 : currentId + 1;
							
							if (motionType == 'type2') {
								if (hasZeroId) {
									newId = currentId + 1;
								} else {
									newId = currentId - 1;
								}
							}
							
							if (motionType == 'type3') {
								if (newId == 0) {
									$(this).hide();
								} else {
									$(this).show();
								}
							}
						
							var positionValue = (newId - 1) * options.itemSize - (options.gapSize * (newId - 1));
							$.imageviewer.move($(this), positionValue);		// 움직여라!
							
							$(this).attr('id', options.prefix + newId);
							
						});
						
						if ((motionType == 'type2' && hasZeroId) || motionType == 'type3') {
							$.imageviewer.view($.imageviewer.activeIndex);
						} else {
							$.imageviewer.view(options.displayCount);
						}
					}
					
				});
				
				
				// 다음 클릭 이벤트.
				$selector.find('.next').on('click', function(e) {
					e.preventDefault();
					if ($(this).parent().find('li').is(':animated')) {
						return;
					}
					
					// 애니메이션 기능 체크를 위한 조건 카운트.
					var animationConditionCount = imageCount < options.displayCount ? imageCount : options.displayCount;
					
					if ($.imageviewer.activeIndex < animationConditionCount) {
						$.imageviewer.view(Number($.imageviewer.activeIndex) + 1);
						
					} else {
						if ($.imageviewer.activeIndex == imageCount) {
							$.imageviewer.view(1);
							return;
						}
						
						// id = prefix + '0' 인 것이 있는가?
						var hasZeroId = $('#' + options.prefix + '0').size() > 0 ? true : false;
						
						$thumbnails.each(function(i) {
							var currentId = Number($(this).attr('id').replace(options.prefix, ''));
							var newId = currentId == 0 ? imageCount - 1 : currentId - 1;
							if (motionType == 'type2') {
								if (hasZeroId) {
									newId = currentId + 1;
								} else {
									newId = currentId - 1;
								}
							}
							
							if (motionType == 'type3') {
								if (newId == imageCount - 1) {
									$(this).hide();
								} else {
									$(this).show();
								}
							}
							
				
							var positionValue = (newId - 1) * options.itemSize - (options.gapSize * (newId - 1));
							$.imageviewer.move($(this), positionValue); 
							$(this).attr('id', options.prefix + newId);
							
						});
						
						if (motionType == 'type2' && hasZeroId) {
							$.imageviewer.view(1);
						} else {
							$.imageviewer.view($.imageviewer.activeIndex);
						}
					}
				});
				
				
				
				
			}
			
			// 포커스 / 이미지 보기
			this.view(1);
		},

		view: function(index) {
			var $thumbnails = this.thumbnails;
			
			this.activeIndex = index;
			$thumbnails.css('z-index', '1').find('a').removeClass(this.options.activeClass);
			
			var $activeItem = $('#' + this.options.prefix + index);
			$activeItem.css('z-index', '2').find('a').addClass(this.options.activeClass);
			
			
			// callback
			var itemIndex = $thumbnails.index($activeItem);
			
			if ($.isFunction(this.options.callback)) {
				this.options.callback({
					itemCount: $thumbnails.size(),
					index: itemIndex,
					selector: $('#' + this.options.prefix + index)
				});
			}
		},
		
		move: function($item, positionValue) {
			if (this.options.vertical) {
				$item.animate({'top': positionValue + 'px'}, 'fast');
				
			} else {
				$item.animate({'left': positionValue + 'px'}, 'fast');
				
			}
		}
		
			
	};

})(jQuery);

