<%@ tag pageEncoding="utf-8" %>

<script type="text/javascript">
    var NICEPAY_FORM_NAME = 'NICEPAY_FORM';

    //결제창 최초 요청시 실행됩니다.
    function nicepayStart(){
        $('#' + NICEPAY_FORM_NAME).remove();

        $_submitForm = $('<form id="'+ NICEPAY_FORM_NAME +'"/>');
        $_submitForm.css('display', 'none');

        $.each($('div.pgInputArea', $('form#buy')).find('input'), function(){
            $_submitForm.append($('<input />').attr({
                'type'		: 'hidden',
                'name'		: $(this).attr('name'),
                'value'		: $(this).val()
            }));
        });

        $_submitForm.attr({
            'method'			: 'post',
            'accept-charset' 	: 'euc-kr',
            'action'			: 'https://web.nicepay.co.kr/v3/smart/smartPayment.jsp'
        });

        $('body').append($_submitForm);

        $('#vExp').val(getTomorrow());
        $_submitForm.submit();
    }

    //가상계좌입금만료일 설정 (today +1)
    function getTomorrow(){
        var today = new Date();
        var yyyy = today.getFullYear().toString();
        var mm = (today.getMonth()+1).toString();
        var dd = (today.getDate()+1).toString();

        if (mm.length < 2) {mm = '0' + mm;}
        if (dd.length < 2) {dd = '0' + dd;}

        return (yyyy + mm + dd);
    }
</script>