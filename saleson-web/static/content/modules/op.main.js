var Main = {
		receiptPopup : function(receiptCount){
			if (receiptCount > 0) {
				Common.popup(url("/proposal-donation/receiptyet-infom"), 'receipt', 500, 401,1);
			}
		},
		receiptPopupCheck : function(userId){
			var mainThis = this;
			$('#block_popup').on('click',function(){
				if ($(this).is(':checked')) {
					mainThis.popupNotView(userId);
				}
			});
		},
		popupNotView : function(userId){
			var today = new Date(); 
			var todayNumber = today.getFullYear().toString() + this.attachZero((today.getMonth() + 1)).toString() + this.attachZero(today.getDate()).toString();
			$.cookie('receiptPopup_'+userId, todayNumber,  { expires: 7, path: "/" });  // set cookie
			self.close();
		}, 
		
		attachZero : function(data){
			if (data < 10){
				data = '0'+data;
			}
			
			return data;
		}
};

