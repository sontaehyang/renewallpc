var Board = {
	boardContext : '',
	boardCode : '',
	
	init : function(boardBaseUri) {
		this.boardContext = boardBaseUri;
	},
	
	list : function(link) {
		alert(link);
		alert(url(link));
		if (link == '') {
			location.href = this.boardContext;
		} else {
			location.href = url(link);
		}
	}
};
