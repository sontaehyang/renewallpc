
var Social = {
	facebook : function(title, currentPageLink, refCode, refId) {
		this.share("facebook", title, currentPageLink);
		this.shareCount(refCode, refId, "1");
	},
	twitter : function(title, currentPageLink, refCode, refId) {
		this.share("twitter", title, currentPageLink);
		this.shareCount(refCode, refId, "2");
	},
	
	share : function(target, title, currentPageLink) {

		currentPageLink = currentPageLink.replace('http://withkb.kbstar.com:443', 'https://withkb.kbstar.com');
		currentPageLink = currentPageLink.replace('http://withkb.kbstar.com:30443', 'https://withkb.kbstar.com');
		currentPageLink = currentPageLink.replace('http://withkb.kbstar.com:20443', 'https://withkb.kbstar.com');
		
		
		
		var snsShareUrl;
		if (target == 'twitter') {
			var ranNum = Math.floor(Math.random()*10);
			snsShareUrl = 'http://twitter.com/share?url='+encodeURIComponent(currentPageLink)+'&text='+encodeURIComponent(title)+"&nocache="+ranNum;
		} else {
			snsShareUrl ="http://www.facebook.com/share.php?u="+encodeURIComponent(currentPageLink);
		}

		var win = window.open(snsShareUrl ,'sharer', 'toolbar=0, status=0, width=626, height=436');
		if(win){
			win.focus();
		}
	},
	
	shareCount : function(refCode,refId,snsType){
		if(refCode == undefined || refId == undefined) {
			return false;
		}
		
		var params = {
				'refCode' : refCode,
				'refId'   : refId,
				'snsType' : snsType
		};
		
		$.post(url('/sns/share'),params,function(){});
	}
};

