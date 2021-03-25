var EventLog = {

};

EventLog.log = function (url, param) {
	try {
		$.ajax({
			type:"post",
			url: url,
			data: JSON.stringify(param),
			contentType : 'application/json;charset=utf-8',
			success : function (response) {}
		});
	} catch (e) {}
}

EventLog.item = function(itemUserCode) {

	var param = {
		id : itemUserCode
	}

	EventLog.log('/event-log/item', param);
}

EventLog.featured = function(items) {

	var param = {
		items : items
	}
	EventLog.log('/event-log/featured', param);
}

EventLog.order = function(orderCode, items) {

	var param = {
		id : orderCode,
		items : items
	}
	EventLog.log('/event-log/order', param);
}

EventLog.joinUser = function(userId) {

	var param = {
		id : userId
	}

	EventLog.log('/event-log/join-user', param);
}