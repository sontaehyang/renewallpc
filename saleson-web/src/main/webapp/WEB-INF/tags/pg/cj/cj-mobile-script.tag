<%@ tag pageEncoding="utf-8" %>

<script type="text/javascript">
function cjMobile(url)
{
	try {
		
		$_submitForm = $('<form id="cjPayForm"/>');
		$_submitForm.attr({
			'action'	: url,
			'method'	: 'post',
			'target'	: '_self'
		});
		
		$_submitForm.append($('div.pgInputArea', $('form#buy')).find('input'));
		$('body').append($_submitForm);
		$('form#cjPayForm').submit();
	
	} catch(e) {alert(e.message)};
}
</script>