<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page" %>

<style>
.attendance {}
.attendance .content-top {
    margin-bottom: 15px;
}
.attendance .content-top img,
.attendance .content-bottom img {
    width: 100%;


}
.attendance .title {
    font-size: 24px;
    text-align: center;
    margin-bottom: 15px;
}
.attendance .desc {
    font-size: 18px;
    line-height: 1.5;
    text-align: center;
    margin-bottom: 15px;
}
.attendance .desc span {
    font-size: 18px;
    font-weight: bold;
    color: #ff0000;

}
.attendance .calendar {
    margin-bottom: 15px;
}

/* Weekdays (Mon-Sun) */
.attendance .calendar .weekdays {
    overflow: hidden;
    margin: 0;
    padding: 10px 0;
    background-color:#ddd;
}

.attendance .calendar .weekdays li {
    float: left;
    width: 14.2%;
    color: #666;
    text-align: center;
    padding: 10px 0;
}
.attendance .calendar .weekdays li.sun,
.attendance .calendar .weekdays li.sat {
    width: 14.5%;
}

.attendance .calendar .weekdays li.sun {
    color: #b2395f;
}
.attendance .calendar .weekdays li.sat {
    color: #2a83b2;
}

/* Days (1-31) */
.attendance .calendar .days {
    overflow: hidden;

    margin: 0;
    border: 1px solid #ccc;
    border-left: 0;
    border-top: 0;
}

.attendance .calendar .days li {
    list-style-type: none;
    float: left;
    width: 14.2%;
    text-align: center;

    font-size:14px;
    color: #777;
    padding: 10px 0;
    border: 1px solid #ccc;
    border-right: 0;
    border-bottom: 0;
    color: #444;
}
.attendance .calendar .days li.weekday-7,
.attendance .calendar .days li.weekday-6 {
    width: 14.5%;
}
.attendance .calendar .days li.weekday-7 {
    color: #b2395f;
}
.attendance .calendar .days li.weekday-6 {
    color: #2a83b2;
}
.attendance .calendar .days li.disabled {
    background: #eee;
    color: #888;
}

.attendance .calendar .days li span {
    display: inline-block;
    width: 30px;
    height: 30px;
    padding: 5px 0 0 0;
    border-radius: 15px;
    font-size: 14px;
}

    /* Highlight the "current" day */
.attendance .calendar .days li span.active {

    background: #1abc9c;
    color: white !important
}

/* 출석체크 버튼 */
.attendance-button-area {
    text-align: center;
    padding: 20px 0;
}
.btn {
    display: inline-block;
    font-weight: 400;
    text-align: center;
    white-space: nowrap;
    vertical-align: middle;
    -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
    border: 1px solid transparent;
    padding: .375rem .75rem;
    font-size: 1rem;
    line-height: 1.5;
    border-radius: .25rem;
    transition: color .15s ease-in-out,background-color .15s ease-in-out,border-color .15s ease-in-out,box-shadow .15s ease-in-out;

    font: 400 11px system-ui;
}
.btn-info {
    color: #fff;
    background-color: #17a2b8;
    border-color: #17a2b8;
}
.btn-lg {
    padding: .5rem 1rem;
    font-size: 1.25rem;
    line-height: 1.5;
    border-radius: .3rem;
}
[type=reset], [type=submit], button, html [type=button] {
    -webkit-appearance: button;
}
.btn:not(:disabled):not(.disabled) {
    cursor: pointer;
}
</style>

<div class="inner">
    <div class="location_area">
        <div class="breadcrumbs">
            <a href="/" class="home"><span class="hide">home</span></a>
            <span>출석체크</span>
        </div>
    </div><!-- // location_area E -->

    <div id="contents">


        <div class="attendance">
            <div class="content-top">${attendance.contentTop}</div>

            <div>
                <h3 class="title">${attendance.year}년 ${attendance.month}월</h3>

                <c:if test="${requestContext.userLogin}">
                <p class="desc">
                    이번 달 출석일 : <span>${checkedCount}</span>일 <br /><br />

                    회원님께서는 현재 <span>${continuouslyCheckedCount}</span>일 연속 출석체크 하셨습니다.<br />
                    출석일은 매달 1일 리셋됩니다.
                </p>
                </c:if>

                <div class="attendance-button-area">
                    <button class="btn btn-info btn-lg op-btn-attendance">출석체크</button>
                </div>
            </div>

            <div class="calendar">
                <ul class="weekdays">
                    <li class="sun">일</li>
                    <li>월</li>
                    <li>화</li>
                    <li>수</li>
                    <li>목</li>
                    <li>금</li>
                    <li class="sat">토</li>
                </ul>

                <ul class="days">
                    <c:forEach items="${calendar}" var="date">
                        <li class="weekday-${date.dayOfWeek} box ${date.disabled ? 'disabled' : ''}">
                            <span class="${date.checked ? 'active' : ''}">${date.day}</span>
                        </li>
                    </c:forEach>
                </ul>
            </div>

            <div class="content-bottom">${attendance.contentBottom}</div>

        </div>
    </div>
</div>


<page:javascript>	
<script type="text/javascript">
$(function() {
    // 출석 이벤트
    initAttendanceEventHandler();
});


function initAttendanceEventHandler() {
    var isLogin = '${requestContext.userLogin}';

    $('.op-btn-attendance').on('click', function() {
        if ('false' == isLogin) {
            if (confirm('로그인 후 출석 이벤트 참여가 가능합니다.\n로그인 페이지로 이동하시겠습니까?')) {
                location.href = '/users/login?target=/attendance';
            };
            return;
        }

        $.post('/attendance/check', {}, function(response) {
            Common.responseHandler(response, function(response) {
                if (response.data.eventCodes.length == 0) {
                    location.reload();

                } else {
                    // 출석 이벤트 완료 (이벤트 페이지로 이동)
                    var eventCodes = response.data.eventCodes.join("-");
                    var eventDays = response.data.eventDays.join("-");
                    alert('eventCodes -> '+eventCodes +'\neventDays -> '+eventDays);
                }
            });
        }, 'json');
    });

}
</script>
</page:javascript>