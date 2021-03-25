nhn.husky.SE2M_MOVIE = jindo.$Class({

    name : "SE2M_MOVIE",

    $init : function(){},

    $ON_MSG_APP_READY : function(){
        this.oApp.exec("REGISTER_UI_EVENT", ["movie_attach", "click", "MOVIE_OPEN_WINDOW"]);
    },

    $LOCAL_BEFORE_FIRST : function(sMsg){
		if(!!this.oPopupMgr){ return; }
		// Popup Manager에서 사용할 param
		this.htPopupOption = {
			oApp : this.oApp,
			sName : this.name,
			bScroll : false,
			sProperties : "",
			sUrl : ""
		};
		this.oPopupMgr = nhn.husky.PopUpManager.getInstance(this.oApp);
	},
	
	/**
	 * 포토 웹탑 오픈
	 */
	$ON_MOVIE_OPEN_WINDOW : function(){			
		this.htPopupOption.sUrl = this.makePopupURL();
		this.htPopupOption.sProperties = "left=0,top=0,width=403,height=359,scrollbars=yes,location=no,status=0,resizable=no";

		this.oPopupWindow = this.oPopupMgr.openWindow(this.htPopupOption);
		
		// 처음 로딩하고 IE에서 커서가 전혀 없는 경우
		// 복수 업로드시에 순서가 바뀜	
		this.oApp.exec('FOCUS');
		return (!!this.oPopupWindow ? true : false);
	},
	
	/**
	 * 서비스별로 팝업에  parameter를 추가하여 URL을 생성하는 함수	 
	 * nhn.husky.SE2M_AttachQuickPhoto.prototype.makePopupURL로 덮어써서 사용하시면 됨.
	 */
	makePopupURL : function(){
		var sPopupUrl = "/smarteditor/upload-movie";
		
		return sPopupUrl;
	}
});