/**
 * 콘솔 로그 남기기.
 * log.debug("title", obj);
 */
var logger = window.console || { logger: function() {} };

// $selector.html() --> 부모 포함.가져오기 $selector.htmlWithParent();
jQuery(function ($) {
    $.fn.htmlWithParent = function () { return $("<div/>").append($(this).clone()).html(); };
    $.fn.parentHtml = function () { return $("<div/>").append($(this).clone()).html(); };
});


function url(url) {
    var contextPath = OP_CONTEXT_PATH == undefined ? "" : OP_CONTEXT_PATH;
    return contextPath + url;
}

String.prototype.replaceAll = function(stringToFind,stringToReplace){
    var temp = this;
    var index = temp.indexOf(stringToFind);
    while(index != -1){
        temp = temp.replace(stringToFind,stringToReplace);
        index = temp.indexOf(stringToFind);
    }
    return temp;
};


/**
 * 브라우저 이름과 버전 등을 반환하는 메서드가 있다.
 * @Deprecated Deprecated 예정 --> Common.browser.msie, Common.browser.version 으로 사용할 것.
 * @namespace
 */
var Browser = {
    ua: navigator.userAgent.toLowerCase(),
    f: function(s, h){ return ((h||'').indexOf(s) > -1); },
    v: navigator.vendor || '',
    /**
     * WebKit 여부
     * @returns {Boolean}
     */
    isWebkit: function() {
        return this.f('webkit', this.ua);
    },
    /**
     * Opera 여부
     * @returns {Boolean}
     */
    isOpera: function() {
        return (typeof window.opera != "undefined") || this.f("opera", this.ua);
    },
    /**
     * IE 여부
     * @returns {Boolean}
     */
    isIe: function() {
        return !this.isOpera() && this.f("msie", this.ua);
    },
    /**
     * Chrome 여부
     * @returns {Boolean}
     */
    isChrome: function() {
        return this.isWebkit() && this.f("chrome", this.ua);
    },
    /**
     * Safari 여부
     * @returns {Boolean}
     */
    isSafari: function() {
        return this.isWebkit() && !this.isChrome() && this.f("Apple", this.v);
    },
    /**
     * Firefox 여부
     * @returns {Boolean}
     */
    isFirefox: function() {
        return this.f("firefox", this.ua);
    },
    /**
     * Mozilla 여부
     * @returns {Boolean}
     */
    isMozilla: function() {
        return this.f("Gecko", this.ua) && !this.isSafari() && !this.isChrome() && !this.isFirefox();
    },
    /**
     * OmniWeb 여부
     * @returns {Boolean}
     */
    isOmniweb: function() {
        return this.f("omniweb", this.ua);
    },
    /**
     * Mobile 접근 여부
     * @returns {Boolean}
     */
    isMobile: function() {
        return (this.f("Mobile", this.ua) || this.f("Android", this.ua) || this.f("Nokia", this.ua)
            || this.f("webOS", this.ua) || this.f("Opera Mini", this.ua) || this.f("BlackBerry", this.ua) || this.f("PPC", this.ua)
            || this.f("Smartphone", this.ua) || this.f("IEMobile", this.ua)) && !this.f("iPad", this.ua);
    },
    /**
     * Mobile Safari 여부
     * @returns {Boolean}
     */
    isMobileSafari: function() {
        return (!this.f("IEMobile", this.ua) && this.f("Mobile", this.ua)) || (this.f("iPad", this.ua) && this.f("Safari", this.ua));
    },
    /**
     * Mobile Opera 여부
     * @returns {Boolean}
     */
    isMobileOpera: function() {
        return this.f("Opera Mini", this.ua);
    },
    /**
     * Mobile IE 여부
     * @returns {Boolean}
     */
    isMobileIe: function() {
        return this.f("PPC", this.ua) || this.f("Smartphone", this.ua) || this.f("IEMobile", this.ua);
    },
    /**
     * 브라우저 이름을 소문자로 반환하며 Internet Explorer는 ie로 반환한다.<br />
     * 지원 브라우저 : Internet Explorer, Chrome, Opera, FireFox, Safari
     * @returns {String}
     */
    getName: function() {
        var ua = this.ua;
        if (ua.indexOf('chrome') != -1) return 'chrome';
        if (ua.indexOf('opera') != -1) return 'opera';
        if (ua.indexOf('firefox') != -1) return 'firefox';
        if (ua.indexOf('safari') != -1) return 'safari';
        if (ua.indexOf('msie') != -1) return 'ie';
        if (ua.indexOf('netscape') != -1) return 'netscape';
        if (ua.indexOf('mozilla/5.0') != -1) return 'mozilla';
        if (ua.indexOf('\/') != -1) {
            if (ua.substr(0,ua.indexOf('\/')) != 'mozilla') {
                return navigator.userAgent.substr(0,ua.indexOf('\/'));
            } else return 'netscape';
        } else if (ua.indexOf(' ') != -1) return navigator.userAgent.substr(0,ua.indexOf(' '));
        else return navigator.userAgent;
    },
    /**
     * 브라우저 버전을 반환합니다.<br />
     * 지원 브라우저 : Internet Explorer, Chrome, Opera, FireFox, Safari
     * @returns {Float}
     */
    getVersion: function() {
        var version;
        try {
            if (this.isIe()) {
                ver = this.ua.match(/(?:msie) ([0-9.]+)/)[1];
                if (this.ua.match(/(?:trident)\/([0-9.]+)/)&&this.ua.match(/(?:trident)\/([0-9.]+)/)[1]==4){
                    nativeVersion = 8;
                }
            } else if (this.isSafari() || this.isMobileSafari()) {
                ver = parseFloat(this.ua.match(/version[ \/]([0-9.]+)/)[1]);
            } else if (this.isMobileOpera()) {
                ver = this.ua.match(/(?:opera\smini)\/([0-9.]+)/)[1];
            } else if (this.isFirefox() || this.isOmniweb()) {
                ver = this.ua.match(/(?:firefox|omniweb)\/([0-9.]+)/)[1];
            } else if (this.isMozilla()) {
                ver = this.ua.match(/rv:([0-9.]+)/)[1];
            } else if (this.isChrome()) {
                ver = this.ua.match(/chrome[ \/]([0-9.]+)/)[1];
            } else if (this.isOpera()) {
                ver = this.ua.match(/ersion[ \/]([0-9.]+)/)[1];
            }
            version = parseFloat(ver);
            if (isNaN(version)) version = -1;
        } catch(e) {
            version = -1;
        }

        return version;
    }

};

/**
 * 운영체제의 대한 정보를 반환하는 메서드가 있다.
 * @namespace
 */
var Os = {
    ua: navigator.userAgent.toLowerCase(),
    p: navigator.platform,
    f: function(s, h){ return ((h||'').indexOf(s) > -1); },
    /**
     * Windows 여부
     * @returns {Boolean}
     */
    isWin: function() {
        return this.f("Win", this.p);
    },
    /**
     * Mac 여부
     * @returns {Boolean}
     */
    isMac: function() {
        return this.f("Mac", this.p);
    },
    /**
     * Linux 여부
     * @returns {Boolean}
     */
    isLinux: function() {
        return this.f("Linux", this.p);
    },
    /**
     * Windows 2000 여부
     * @returns {Boolean}
     */
    isWin2000: function() {
        return this.isWin && (this.f("nt 5.0", this.ua) || this.f("2000", this.ua));
    },
    /**
     * Windows xp 여부
     * @returns {Boolean}
     */
    isWinXp: function() {
        return this.isWin && this.f("nt 5.1", this.ua);
    },
    /**
     * Vista 여부
     * @returns {Boolean}
     */
    isVista: function() {
        return this.isWin && this.f("nt 6.0", this.ua);
    },
    /**
     * Windows7 여부
     * @returns {Boolean}
     */
    isWin7: function() {
        return this.isWin && this.f("nt 6.1", this.ua);
    },
    /**
     * iPad 여부
     * @returns {Boolean}
     */
    isIPad: function() {
        return this.f("ipad", this.ua);
    },
    /**
     * iPhone 여부
     * @returns {Boolean}
     */
    isIPhone: function() {
        return this.f("iphone", this.ua) && !this.isIPad;
    },
    /**
     * Android 여부
     * @returns {Boolean}
     */
    isAndroid: function() {
        return this.f("android", this.ua);
    },
    /**
     * Nokia 여부
     * @returns {Boolean}
     */
    isNokia: function() {
        return this.f("nokia", this.ua);
    },
    /**
     * WebOS 여부
     * @returns {Boolean}
     */
    isWebOS: function() {
        return this.f("webos", this.ua);
    },
    /**
     * BlackBerry 여부
     * @returns {Boolean}
     */
    isBlackBerry: function() {
        return this.f("blackberry", this.ua);
    },
    /**
     * Mobile Windows 여부
     * @returns {Boolean}
     */
    isMobileWindow: function() {
        return this.f("ppc", this.ua) || this.f("smartphone", this.ua) || this.f("iemobile", this.ua);
    },
    /**
     * 운영체제 이름을 반환한다.
     * @returns {String}
     */
    getName: function() {
        var name;
        try {
            if (this.isWin2000()) {
                name = 'win2000';
            } else if (this.isWinXp()) {
                name = 'winxp';
            } else if (this.isVista()) {
                name = 'vista';
            } else if (this.isWin7()) {
                name = 'win7';
            } else if (this.isWin()) {
                name = 'win';
            } else if (this.isMac()) {
                name = 'mac';
            } else if (this.isLinux()) {
                name = 'linux';
            } else if (this.isIPad()) {
                name = 'ipad';
            } else if (this.isIPhone()) {
                name = 'iphone';
            } else if (this.isAndroid()) {
                name = 'android';
            } else if (this.isNokia()) {
                name = 'nokia';
            } else if (this.isWebOS()) {
                name = 'webos';
            } else if (this.isBlackBerry()) {
                name = 'blackberry';
            } else if (this.isMobileWindow()) {
                name = 'mobilewindow';
            } else {
                name = 'none';
            }
        } catch(e) {
            name = 'none';
        }
        return name;
    }
};


/**
 * Common
 */
var Common = {

    // Dynamic Load Script
    dynamic: {
        quSXI:new Array(),quS:new Array(),isLoad:function(a){for(var b=this.quS.length-1;b>=0;b--){if(this.quS[b]===a){return true}}return false},_runOnload:function(b,a,c){if(!a){return}b.onloadDone=false;if(typeof b.onreadystatechange==="undefined"&&!b.onloadDone){b.onloadDone=true;if(c){setTimeout(a,100)}else{b.onload=a}}b.onreadystatechange=function(){if(("loaded"===b.readyState||"complete"===b.readyState)&&!b.onloadDone){b.onloadDone=true;setTimeout(a,100)}}},script:function(c,a){if(!this.isLoad(c)){this.quS[this.quS.length]=c;var b=document.createElement("script");b.type="text/javascript";b.src=c;b.charset="utf-8";document.getElementsByTagName("head")[0].appendChild(b);this._runOnload(b,a)}else{if(a){setTimeout(a,100)}}},style:function(c,a){if(!this.isLoad(c)){this.quS[this.quS.length]=c;var b=document.createElement("link");b.rel="stylesheet";b.type="text/css";b.href=c;b.charset="utf-8";document.getElementsByTagName("head")[0].appendChild(b);this._runOnload(b,a)}else{if(a){setTimeout(a,100)}}}
    }
};


// 공통 스크립트 LOAD
Common.dynamic.script("/content/modules/op.link.js");


/**
 * 브라우저 Detect
 */
Common.browser = (function () {
    /**
     * 출처 : https://github.com/ded/bowser/tree/master/src
     */

    var t = true;

    function detect(ua) {

        function getFirstMatch(regex) {
            var match = ua.match(regex);
            return (match && match.length > 1 && match[1]) || '';
        }

        var iosdevice = getFirstMatch(/(ipod|iphone|ipad)/i).toLowerCase();
        var likeAndroid = /like android/i.test(ua);
        var android = !likeAndroid && /android/i.test(ua);
        var versionIdentifier = getFirstMatch(/version\/(\d+(\.\d+)?)/i);
        var tablet = /tablet/i.test(ua);
        var mobile = !tablet && /[^-]mobi/i.test(ua);
        var result;

        if (/opera|opr/i.test(ua)) {
            result = {
                name: 'Opera'
                , opera: t
                , version: versionIdentifier || getFirstMatch(/(?:opera|opr)[\s\/](\d+(\.\d+)?)/i)
            };
        }
        else if (/windows phone/i.test(ua)) {
            result = {
                name: 'Windows Phone'
                , windowsphone: t
                , msie: t
                , version: getFirstMatch(/iemobile\/(\d+(\.\d+)?)/i)
            };
        }
        else if (/msie|trident/i.test(ua)) {
            result = {
                name: 'Internet Explorer'
                , msie: t
                , version: getFirstMatch(/(?:msie |rv:)(\d+(\.\d+)?)/i)
            };
        }
        else if (/chrome|crios|crmo/i.test(ua)) {
            result = {
                name: 'Chrome'
                , chrome: t
                , version: getFirstMatch(/(?:chrome|crios|crmo)\/(\d+(\.\d+)?)/i)
            };
        }
        else if (iosdevice) {
            result = {
                name : iosdevice == 'iphone' ? 'iPhone' : iosdevice == 'ipad' ? 'iPad' : 'iPod'
            };
            // WTF: version is not part of user agent in web apps
            if (versionIdentifier) {
                result.version = versionIdentifier;
            }
        }
        else if (/sailfish/i.test(ua)) {
            result = {
                name: 'Sailfish'
                , sailfish: t
                , version: getFirstMatch(/sailfish\s?browser\/(\d+(\.\d+)?)/i)
            };
        }
        else if (/seamonkey\//i.test(ua)) {
            result = {
                name: 'SeaMonkey'
                , seamonkey: t
                , version: getFirstMatch(/seamonkey\/(\d+(\.\d+)?)/i)
            };
        }
        else if (/firefox|iceweasel/i.test(ua)) {
            result = {
                name: 'Firefox'
                , firefox: t
                , version: getFirstMatch(/(?:firefox|iceweasel)[ \/](\d+(\.\d+)?)/i)
            };
            if (/\((mobile|tablet);[^\)]*rv:[\d\.]+\)/i.test(ua)) {
                result.firefoxos = t
            }
        }
        else if (/silk/i.test(ua)) {
            result =	{
                name: 'Amazon Silk'
                , silk: t
                , version : getFirstMatch(/silk\/(\d+(\.\d+)?)/i)
            };
        }
        else if (android) {
            result = {
                name: 'Android'
                , version: versionIdentifier
            };
        }
        else if (/phantom/i.test(ua)) {
            result = {
                name: 'PhantomJS'
                , phantom: t
                , version: getFirstMatch(/phantomjs\/(\d+(\.\d+)?)/i)
            };
        }
        else if (/blackberry|\bbb\d+/i.test(ua) || /rim\stablet/i.test(ua)) {
            result = {
                name: 'BlackBerry'
                , blackberry: t
                , version: versionIdentifier || getFirstMatch(/blackberry[\d]+\/(\d+(\.\d+)?)/i)
            };
        }
        else if (/(web|hpw)os/i.test(ua)) {
            result = {
                name: 'WebOS'
                , webos: t
                , version: versionIdentifier || getFirstMatch(/w(?:eb)?osbrowser\/(\d+(\.\d+)?)/i)
            };
            /touchpad\//i.test(ua) && (result.touchpad = t)
        }
        else if (/bada/i.test(ua)) {
            result = {
                name: 'Bada'
                , bada: t
                , version: getFirstMatch(/dolfin\/(\d+(\.\d+)?)/i)
            };
        }
        else if (/tizen/i.test(ua)) {
            result = {
                name: 'Tizen'
                , tizen: t
                , version: getFirstMatch(/(?:tizen\s?)?browser\/(\d+(\.\d+)?)/i) || versionIdentifier
            };
        }
        else if (/safari/i.test(ua)) {
            result = {
                name: 'Safari'
                , safari: t
                , version: versionIdentifier
            };
        }
        else {
            result = {};
        }

        // set webkit or gecko flag for browsers based on these engines
        if (/(apple)?webkit/i.test(ua)) {
            result.name = result.name || "Webkit";
            result.webkit = t;
            if (!result.version && versionIdentifier) {
                result.version = versionIdentifier;
            }
        } else if (!result.opera && /gecko\//i.test(ua)) {
            result.name = result.name || "Gecko";
            result.gecko = t;
            result.version = result.version || getFirstMatch(/gecko\/(\d+(\.\d+)?)/i);
        }

        // set OS flags for platforms that have multiple browsers
        if (android || result.silk) {
            result.android = t;
        } else if (iosdevice) {
            result[iosdevice] = t;
            result.ios = t;
        }

        // OS version extraction
        var osVersion = '';
        if (iosdevice) {
            osVersion = getFirstMatch(/os (\d+([_\s]\d+)*) like mac os x/i);
            osVersion = osVersion.replace(/[_\s]/g, '.');
        } else if (android) {
            osVersion = getFirstMatch(/android[ \/-](\d+(\.\d+)*)/i);
        } else if (result.windowsphone) {
            osVersion = getFirstMatch(/windows phone (?:os)?\s?(\d+(\.\d+)*)/i);
        } else if (result.webos) {
            osVersion = getFirstMatch(/(?:web|hpw)os\/(\d+(\.\d+)*)/i);
        } else if (result.blackberry) {
            osVersion = getFirstMatch(/rim\stablet\sos\s(\d+(\.\d+)*)/i);
        } else if (result.bada) {
            osVersion = getFirstMatch(/bada\/(\d+(\.\d+)*)/i);
        } else if (result.tizen) {
            osVersion = getFirstMatch(/tizen[\/\s](\d+(\.\d+)*)/i);
        }
        if (osVersion) {
            result.osversion = osVersion;
        }

        // device type extraction
        var osMajorVersion = osVersion.split('.')[0];
        if (tablet || iosdevice == 'ipad' || (android && (osMajorVersion == 3 || (osMajorVersion == 4 && !mobile))) || result.silk) {
            result.tablet = t;
        } else if (mobile || iosdevice == 'iphone' || iosdevice == 'ipod' || android || result.blackberry || result.webos || result.bada) {
            result.mobile = t;
        }

        // Graded Browser Support
        // http://developer.yahoo.com/yui/articles/gbs
        if ((result.msie && result.version >= 10) ||
            (result.chrome && result.version >= 20) ||
            (result.firefox && result.version >= 20.0) ||
            (result.safari && result.version >= 6) ||
            (result.opera && result.version >= 10.0) ||
            (result.ios && result.osversion && result.osversion.split(".")[0] >= 6)
        ) {
            result.a = t;
        }
        else if ((result.msie && result.version < 10) ||
            (result.chrome && result.version < 20) ||
            (result.firefox && result.version < 20.0) ||
            (result.safari && result.version < 6) ||
            (result.opera && result.version < 10.0) ||
            (result.ios && result.osversion && result.osversion.split(".")[0] < 6)
        ) {
            result.c = t;
        } else {
            result.x = t;
        }

        return result;
    }

    var browser = detect(typeof navigator !== 'undefined' ? navigator.userAgent : '')


    /*
     * Set our detect method to the main bowser object so we can
     * reuse it to test other user agents.
     * This is needed to implement future tests.
     */
    browser._detect = detect;

    // 브라우저 정보
    //alert(JSON.stringify(browser, null, '    '));
    return browser;
}());





/**
 * 로그인 에러.
 */
Common.alertLoginError = function(errorCode) {

    if (errorCode == "") return;

    switch (errorCode) {
        case "1":
            alert('사용자 정보를 정확히 입력해 주십시오.');
            break;

        case "2":
            alert('사용자 정보를 정확히 입력해 주십시오.');
            break;

        case "3":
            alert('인증번호가 일치하지 않습니다.');
            break;

        default:
            alert('로그인에 실패하였습니다.');
            break;
    }
};


/**
 * 팝업 띄우는 메서드. <br />Chrome에서는 스크롤바 제어를 해당 페이지에서 스타일을 줘야 가능(overflow: hidden)<br />
 * @param {String} [url=""] URL
 * @param {String} popupName popup 이름
 * @param {int} [width="400"] 가로 사이즈
 * @param {int} [height="400"] 세로 사이즈
 * @param {int or String} [scrollbars="0"] 스크롤 여부 (예: yes, 1 아니오: n, no, 0)
 * @param {int} [xPosition="c"] 가로 위치 (가운데: c)
 * @param {int} [yPosition="c"] 세로 위치 (가운데: c)
 * @requires popupName
 * @description <pre class="code">
 popup('../../src/sample/popup.html', 'popup1')<br />
 => <input type="button" class="btn" value="실행" onclick="popup('../../src/sample/popup.html', 'popup1');return false;" /><br />
 popup('../../src/sample/popup.html', 'popup2', 200, 300, 'no')<br />
 => <input type="button" class="btn" value="실행" onclick="popup('../../src/sample/popup.html', 'popup2', 200, 300, 'no');return false;" /><br />
 popup('../../src/sample/popup.html', 'popup3', 200, 300, 'no', 100, 100)<br />
 => <input type="button" class="btn" value="실행" onclick="popup('../../src/sample/popup.html', 'popup3', 200, 300, 'no', 100, 100);return false;" />
 </pre>
 */
Common.popup = function(url, popupName, width, height, scrollbars, xPosition, yPosition) {

    if (isMobileLayer == true || isMobileLayer == 'true') {

        if ($('.op-app-popup-wrap').size() == 0) {
            $('body').wrapInner('<div class="op-app-popup-wrap"></div>');
        }

        $('.op-app-popup-wrap').hide();
        $('.op-app-popup-content').remove();

        $div = $('<div class="op-app-popup-content" style="height:'+ window.innerHeight +'px;width:100%"><iframe src="'+ url +'" name="'+ popupName +'" height="100%" width="100%" scrolling="yes" /></div>');
        $('body').append($div);

    } else {
        var xP = 0, yP = 0, xC = 0, yC = 0, scr = 0, zero = 0, one = 1;
        var target = url == null || url == '' ? '' : url;
        var w = width == null || width == '' ? 400 : width;
        var h = height == null || height == '' ? 400 : height;

        if (parseInt(navigator.appVersion) >= 4) {
            xC = (screen.width - w) / 2;
            yC = (screen.height - h) / 2;
        }

        xP = xPosition == null || xPosition == 'c' ? xC : xPosition;
        yP = yPosition == null || yPosition == 'c' ? yC : yPosition;
        scr = scrollbars == null || scrollbars == 'n' ? 0 : scrollbars;

        var parameters = 'location=' + zero +
            ',menubar=' + zero +
            ',height=' + h +
            ',width=' + w +
            ',toolbar=' + zero +
            ',scrollbars=' + scr +
            ',status=' + zero +
            ',resizable=' + one +
            ',left=' + xP +
            ',screenX=' + xP +
            ',top=' + yP +
            ',screenY=' + yP;

        popupName = popupName.replaceAll("-", "_");
        newWin = window.open(target, popupName, parameters);

        if (newWin) {
            newWin.focus();
        }

        //return newWin;
    }
};

/*
 * 금액에 3단위로 comma 처리를 위한
 * 스크립트를 구성함
 *
 * @File   : op.common.js
 * @Author : 이시정
 * @Date   : 2013-08-14
 * @param {int} [amount="1000"] n
 * @returns {String} (예: n = 1,000 )
 *
 */
Common.comma = function(n) {
    try {
        if (typeof n === 'string') {
            n = n.replace(/\,/g, '');
            if (n == '') {
                return "";
            }
        }

        n = Number(n);
    } catch(e) {
        return n;
    }

    var reg = /(^[+-]?\d+)(\d{3})/;   // 정규식
    n += '';                          // 숫자를 문자열로 변환

    while (reg.test(n))
        n = n.replace(reg, '$1' + ',' + '$2');

    if(n == 'NaN'){
        n = '';
    }

    return n;
};

Common.numberFormat = function(val) {
    return Common.comma(val);
};

/**
 * 지정된 Selector에 컴마(,)를 추가한다.
 */
Common.addNumberComma = function($selector) {
    $selector = $selector == undefined ? $('._number_comma') : $selector;
    $selector.each(function() {
        var $numberComma = $(this);
        $numberComma.val(Common.comma($numberComma.val()));
    });
};

/**
 * 지정된 Selector에 컴마(,)를 지운다.
 */
Common.removeNumberComma = function($selector) {
    $selector = $selector == undefined ? $('._number_comma') : $selector;
    $selector.each(function() {
        var $numberComma = $(this);
        $numberComma.val($numberComma.val().replace(/,/g, ""));
    });
};


Common.tryCatch = function(func) {
    try {
        if ($.isFunction(func)) {
            func();
        } else {
            alert("함수만 실행이 가능합니다.");
        }
    } catch (e) {
        alert('처리 중 오류가 발생하였습니다.\n-오류 : ' + e.message);
    }
};

Common.isDefined = function(obj) {
    if (typeof obj == 'undefined') {
        return false;
    } else {
        return true;
    }
};


/**
 * Undefined 인지 체
 */
Common.isUndefined = function(obj) {
    return !Common.isDefined(obj);
};


/**
 * Ajax 요청에 대한 응답을 처리한다.
 */
Common.responseHandler = function(response, success, error) {
    try {
        if (response.isSuccess) {
            Common.loading.hide();
            if (success != undefined) {
                success(response);
                return;
            }
        } else {
            Common.loading.hide();

            if (error != undefined) {
                error(response);
                return;
            } else {
                alert(response.errorMessage);
            }
            return;

        }
    } catch(e) {
        Common.exceptionHandler(e, "Ajax 응답 처리 시 오류가 발생하였습니다.\nCommon.responseHandler (op.common.js)");

    }
};

Common.exceptionHandler = function(exception, message) {
    var stack = message == undefined ? "" : message + '\n\n';

    stack += exception.toString() + "\n";

    for (var prop in exception)
    {
        stack += "-" + prop + " : " + exception[prop] + "\n";
    }

    alert(stack);
};


/**
 * 확인 메세지.
 */
Common.confirm = function(message, confirmFunction, cancelFunction) {
    if (confirm(message)) {
        if ($.isFunction(confirmFunction)) {
            confirmFunction();
        }
        return true;
    } else {
        if ($.isFunction(cancelFunction)) {
            cancelFunction();
        }
        return false;
    }
};


Common.handleFrameworkMessage = function(requestContextMessage, requestContextScript, flashMapMessage, flashMapScript) {
    Common.alertFrameworkMessage(requestContextMessage);
    Common.excuteFrameworkScript(requestContextScript);

    Common.alertFrameworkMessage(flashMapMessage);
    Common.excuteFrameworkScript(flashMapScript);
};

Common.alertFrameworkMessage = function(message) {
    Common.loading.hide();
    if (message != "") {
        message = message.replace(/<br\s*[\/]?>/gi, "\n");
        setTimeout(function() {alert(message);}, 100);

    }
};

Common.excuteFrameworkScript = function(script) {
    if (script != "") {
        if ($.isFunction(script)) {
            setTimeout(function() {script();}, 200);
        } else {
            setTimeout(function() {eval(script);}, 200);
        }
    }
};

/**
 * 목록에서 선택한 항목을 삭제 한다.
 */
Common.deleteCheckedList = function(url) {
    var $form = $('#listForm');

    if ($form.find('input[name=id]:checked').size() == 0) {
        alert('삭제할 항목을 선택해 주세요');
        return;
    }

    if (confirm('삭제 하시겠습니까?')) {
        $.post(url, $form.serialize(), function(response) {
            Common.responseHandler(response, function(){
                alert('처리되었습니다.');
                location.reload();
            });
        });
    } else {
        //
    }

};

Common.updateCheckedList = function(url) {
    var form = $('#listForm');

    if (form.find('input[type=id]:checked').size() == 0) {
        alert('수정할 항목을 선택해 주세요');
        return;
    }

    if (confirm('수정할 하시겠습니까?')) {
        $.post(url, $form.serialize(), function(response) {
            Common.responseHandler(response, function(){
                location.reload();
            });
        });
    } else {
        //
    }

};

/**
 * 목록에서 드레그하여 순서를 변경한다.
 */
Common.changeListOrdering = function(url) {
    var $form = $('#listForm');

    /*
    if ($form.find('input[name=id]:checked').size() == 0) {
        alert('삭제할 항목을 선택해 주세요');
        return;
    }*/

    if (confirm(Message.get("M01483"))) { // 순서를 변경하시겠습니까?
        $('#check_all').click();
        var param = $form.serialize();
        param = param + '&categoryId=' + $('#categoryId').val();


        $.post(url, param, function(response) {
            Common.responseHandler(response, function(){
                alert(Message.get("M01221"));	// 처리되었습니다.
                location.reload();
            });
        });
    } else {
        //
    }

};

/**
 * 목록 데이터를 처리한다. (삭제, 수정, 기타 등등)
 *
 * @param url 처리 URL
 * @param message 처리 전 알림 메세지.
 */
Common.updateListData = function(url, message, callback){
    var $form = $('#listForm');
    if ($form.find('input[name=id]:checked').size() == 0) {
        alert(Message.get("M00308"));	// 처리할 항목을 선택해 주세요.
        return;
    }

    $form.find('table input, table select, ul input, ul select').prop('disabled', true);
    $form.find('input[name=id]:checked').closest('tr').find('input, select').attr('disabled', false);
    $form.find('input[name=id]:checked').closest('li').find('input, select').attr('disabled', false);


    if (message != undefined) {
        if (!confirm(message)) {
            $form.find('table input, table select').prop('disabled', false);
            return;
        }
    }

    $.post(url, $form.serialize(), function(response) {
        Common.responseHandler(response, function(response){
            if (!Common.isUndefined(callback) && typeof callback === 'function') {
                callback(response);
            } else {
                location.reload();
            }
        });
    });

};

/**
 * 에디터 내용을 가져와 textarea에 설정.
 */
Common.getEditorContent = function(id, isRequired) {
    if (editors == undefined) {
        return;
    }

    editors.getById[id].exec("UPDATE_CONTENTS_FIELD", []); // 에디터의 내용이 textarea에 적용됩니다.

    var $targetContent = $('#' + id);
    var title = $targetContent.attr('title');
    var editorContent = $.trim($targetContent.val()).toLowerCase();

    if (editorContent == '<p>&nbsp;</p>') {
        $targetContent.val('');
        editorContent = '';
    }
    //if ($targetContent.hasClass('required')) {
    if (isRequired !== undefined && isRequired == true && editorContent == '') {
        $.validator.validatorAlert($.validator.messages['text'].format(title), $targetContent);
        editors.getById[id].exec("FOCUS");
        return false;
    }
};


function fn_ProcessSelectedList(controller, mode){
    var alt = mode=='update' ? '수정' : '삭제';
    if($('input.checkAll:checked').size()==0){
        alert(alt+'할 항목을 선택해 주세요');
        return;
    }
    $('table.opmanager-list-table input, table.opmanager-list-table select').attr('disabled', true);
    $('table.opmanager-list-table .checkAll:checked').closest('tr').find('input, select').attr('disabled', false);

    if (confirm(alt + '하시겠습니까?')) {
        $.post('/opmanager/'+controller+'/listUpdate/'+mode, $('.opmanager-list-form').serialize(), function(response) {
            responseHandler(response, function(){
                //alert('ok');
                location.reload();
            });
        });
    } else {
        $('table.opmanager-list-table input, table.opmanager-list-table select').attr('disabled', false);
    }

}


//로딩..

Common.dimmed = {
    show: function() {
        if ($('#dimmed').size() == 0) {
            $('body').append('<div id="dimmed"></div>');
        }
        $('#dimmed').show();
    },
    hide: function() {
        $('#dimmed').hide();
    }
};


var spinner = null;
Common.loading = {
    display: true,
    options:   {
        lines: 10, // The number of lines to draw
        length: 10, // The length of each line
        width: 7, // The line thickness
        radius: 12, // The radius of the inner circle
        color: '#ffffff', // #rgb or #rrggbb
        speed: 1.4, // Rounds per second
        trail: 54, // Afterglow percentage
        shadow: false // Whether to render a shadow
    },
    spinner: null
};

Common.loading.show = function () {

    if ($('#loading-dimmed').size() == 0) {
        $('body').append('<div id="loading-dimmed" style="display: none"></div>');
    }

    if ($('#loading').size() == 0) {
        $('body').append('<div id="loading" style="display: none"></div>');
    }

    if (Common.loading.display) {
        $('#loading-dimmed').show();
    }
    if (spinner == null) {
        if (Common.loading.display) {
            $('#loading').show();
            spinner = new Spinner(Common.loading.options).spin(document.getElementById('loading'));
        }
    }

};

Common.loading.hide = function () {
    if (spinner != null) {
        spinner.stop();
    }
    spinner = null;

    $('#loading-dimmed, #loading').hide();
    Common.loading.display = true;

};


//우클릭 방지
Common.diableContextMenu = function () {
    $(document).on('contextmenu', function(e) {
        return false;
    });
};

// 드래그 방지
Common.diableDragStart = function () {
    $(document).on('dragstart', function (e) {
        return false;
    });
};


// 즐겨찾기 
function addBookmark(title, url) {
    var link = url == 'undefined' ? document.href : url;

    // Internet Explorer
    if(document.all)
    {
        window.external.AddFavorite(link, title);
    }
    // Google Chrome
    else if(window.chrome){
        alert("Ctrl+D키를 누르시면 즐겨찾기에 추가하실 수 있습니다.");
    }
    // Firefox
    else if (window.sidebar) // firefox 
    {
        window.sidebar.addPanel(title, link, "");
    }
    // Opera
    else if(window.opera && window.print)
    { // opera 
        var elem = document.createElement('a');
        elem.setAttribute('href',link);
        elem.setAttribute('title',title);
        elem.setAttribute('rel','sidebar');
        elem.click();
    }
}



/**
 * 공통 기능 Plugin
 */
(function($) {
    $.extend($.fn, {
        check: function() {
            $(this).attr('checked', true);
        },
        uncheck: function() {
            $(this).attr('checked', false);
        }
    });
})(jQuery);



/*
 * Event Handler
 * 
 */
var EventHandler = {};
/**
 * 전체선택.
 */
EventHandler.checkAllEvent = function() {
    var $checkAll = $('#check_all');

    if ($checkAll.size() > 0) {
        $checkAll.on("click", function() {
            var isChecked = $(this).prop('checked');

            var $checkboxes = $(this).closest("table").find('input[name=id]:enabled');
            if (isChecked) {

                $checkboxes.prop('checked', true);
            } else {
                $checkboxes.prop('checked', false);
            }
        });
    }
};


EventHandler.changeNumberCommaEvent = function() {
    // 콤마 처리
    $('body').on("focus", '._number_comma', $("body"), function() {
        Common.removeNumberComma($(this));

    }).on("blur", '._number_comma', $("body"), function() {
        Common.addNumberComma($(this));
    });
};

/**
 * textarea maxlength=4000
 */
EventHandler.checkTextareaMaxlength = function() {

    $('textarea').on('keyup change', function() {
        var str = $(this).val();
        var maxlength = 4000;
        if (str.length > maxlength) {
            $(this).val(str.substr(0, maxlength));
            alert('4,000자 까지만 입력이 가능합니다.');
            $(this).focus();
            return false;
        }
    });

};

/**
 * 숫자만.
 */
EventHandler.checkOnlyNumber = function() {
    $(document).on('keyup ', '._number, ._number_comma', function(e) {
        var obj = $(this);

        if (!(e.which && (e.which > 47 && e.which < 58) || (e.which > 95 && e.which < 106) || e.which ==8 || e.which == 13 || e.which == 37 || e.which == 39 || e.which == 46 || e.which ==9|| e.which ==0 || (e.ctrlKey && e.which ==86) ) ) {
            e.preventDefault();
        }

        var pattern = /^[\-|0-9]+/g;
        var matchValue = obj.val().match(pattern);

        if (!pattern.test(obj.val())) {
            obj.val('');
        }

        if (obj.val() != matchValue) {
            obj.val(matchValue);
        }
    }).css('imeMode','disabled');
};

/**
 * @Deprecated 사용금지
 * @param obj
 * @param e
 */
function onlyNumber(obj, e){
    alert('oncick="onlyNumber(... 사용금지 class="_number"를 입력하세');
}


/**
 * 음수 입력 이벤트.
 */
EventHandler.checkOnlyNumberNegative = function() {
    $(document).on('keyup', '._number_negative', function(e) {
        var obj = $(this);
        if (!(e.which && (e.which > 47 && e.which < 58) || (e.which > 95 && e.which < 106) || e.which ==8 || (e.which == 109 || e.which == 189) || e.which == 13 || e.which == 37 || e.which == 39 || e.which == 46 || e.which ==9|| e.which ==0 || (e.ctrlKey && e.which ==86) ) ) {
            e.preventDefault();
        }
        // 좌우 방향키.
        if (e.which == 37 || e.which == 39) {
            return;
        }

        if (obj.val() == '-' || obj.val() == '0') {
            return;
        }

        var pattern = /^([-]?[1-9][0-9]*)/g;
        var matchValue = obj.val().match(pattern);

        if (!pattern.test(obj.val())) {
            obj.val('');
        }

        if (obj.val() != matchValue) {
            obj.val(matchValue);
        }

    }).css('imeMode','disabled');
};


/**
 * 소수점 2째 자리까지
 */
EventHandler.checkOnlyNumberFloat = function() {
    $('._number_float').on('keyup keydown', function(e) {
        onlyNumberFloat($(this),e);
    }).css('imeMode','disabled');
};
function onlyNumberFloat(obj, e) {
    var value = obj.val();
    if (e.which=='229' || e.which=='197' && $.browser.opera) {
        setInterval(function(){
            obj.trigger('keyup');
        }, 100);
    }

    // 현재 value값에 소수점(.) 이 있으면 . 입력불가

    if (e.which == 110 || e.which == 190) {
        if (value.indexOf(".") > -1) {
            e.preventDefault();
        }
    }

    if ( !e.which || !((e.which > 47 && e.which < 58) || (e.which > 95 && e.which < 106)  || (e.which == 190) || (e.which == 110) || (e.which == 8) || (e.which == 46))) {
        e.preventDefault();
    }

    var value = obj.val().match(/[^0-9.]/g);
    if (value!=null) {
        obj.val(obj.val().replace(/[^0-9.]/g,''));
    }
}

// 주소 관련
var Zipcode = {
    getSido : function(address) {
        try {
            if (address == "" || address == undefined) {
                return "";
            }

            var splitAddress = address.split(" ", 3);
            var pattern = ["시", "도"];
            var str = splitAddress[0];
            if (str == "" || str == undefined) {
                return "";
            }

            var endStr = str.substring(str.length - 1, str.length);
            for(i = 0; i < pattern.length; i++) {

                if (endStr == pattern[i]) {
                    return str;
                }
            }

            return "";
        } catch(e) {
            return "";
        }
    },
    getSigungu : function(address) {
        try {
            if (address == "" || address == undefined) {
                return "";
            }

            var splitAddress = address.split(" ", 3);
            var pattern = ["시", "군", "구"];
            var str = splitAddress[1];
            if (str == "" || str == undefined) {
                return "";
            }

            var endStr = str.substring(str.length - 1, str.length);
            for(i = 0; i < pattern.length; i++) {

                if (endStr == pattern[i]) {
                    return str;
                }
            }

            return "";
        } catch(e) {
            return "";
        }
    },
    getEupmyeondong : function(address) {
        try {
            if (address == "" || address == undefined) {
                return "";
            }

            var splitAddress = address.split(" ", 3);
            var pattern = ["읍", "면", "동"];
            var str = splitAddress[2];
            if (str == "" || str == undefined) {
                return "";
            }

            var endStr = str.substring(str.length - 1, str.length);
            for(i = 0; i < pattern.length; i++) {

                if (endStr == pattern[i]) {
                    return str;
                }
            }

            return "";
        } catch(e) {
            return "";
        }
    }

}

//Message init
var Message = {
    get: function(messageCode) {
        var message = '';
        $.ajaxSetup({'async': false});
        $.post('/common/message', {'messageCode' : messageCode}, function(resp){

            message = resp.data;
        }, 'json');
        return message;
    },

    isMessageCode : function(message) {
        if (message.substring(0, 1) == 'M' && message.length == 6) {
            return true;
        }
        return false;
    },


    // 일회성 메세지를 표시함.
    primary: function(message) {
        this.display('primary', message);
    },

    // 일회성 메세지를 표시함.
    info: function(message) {
        this.display('info', message);
    },

    // 일회성 메세지를 표시함.
    success: function(message) {
        this.display('success', message);
    },

    // 일회성 메세지를 표시함.
    warning: function(message) {
        this.display('warning', message);
    },

    // 일회성 메세지를 표시함.
    danger: function(message) {
        this.display('danger', message);
    },

    // 메세지 출력
    display: function(status, msg) {
        $('#fade_out_message').clearQueue().stop().remove();
        var message = this.isMessageCode(msg) ? this.get(msg) : msg;

        if ($('#fade_out_message').size() == 0) {
            $('body').append('<div id="fade_out_message"></div>');
        }
        //
        $('#fade_out_message').text(message);
        var marginLeft = Number($('#fade_out_message').width() / 2);
        $('#fade_out_message').attr('class', 'bg_' + status).css('margin-left', '-' + marginLeft + 'px').show().delay(700).fadeOut(1000);

    }

};


/**
 * 날짜 정보를 입력 받아서 유효성 체크
 * @param inDate YYYYMMDD
 **/
Common.validateDate = function (inDate){
    var END_OF_MONTH = [0,31,28,31,30,31,30,31,31,30,31,30,31];
    if (isNaN(inDate)) {
        return false;
    }

    if (Number(inDate)/10000000 <= 1) return false;

    var strDate = String(inDate);
    var nYear = Number(strDate.substring(0,4));
    var nMonth= Number(strDate.substring(4,6));
    var nDay	= Number(strDate.substring(6,8));

    //년 확인
    if (nYear <= 0)
        return false;

    //월 확인
    if (nMonth < 1 || nMonth > 12) return false;

    //윤달 확인
    if (nYear % 4 == 0)
        if (nYear % 100 != 0 || nYear % 400 == 0)
            END_OF_MONTH[2] = 29;

    //일 확인
    if (nDay < 1 || END_OF_MONTH[nMonth] < nDay)
        return false;

    return	true ;
};

EventHandler.calendarStartDateAndEndDateVaild =  function(){

    $('.term').on("change",function(){
        var ymd1 = "";
        var ymd2 = "";

        var $startDate = $('input[name$="StartDate"]').size() > 0 ? $('input[name$="StartDate"]') : $('input[name$="startDate"]');
        var $endDate = $('input[name$="EndDate"]').size() > 0 ? $('input[name$="EndDate"]') : $('input[name$="endtDate"]');

        if ($startDate.val() != undefined && $startDate.val() != '') {
            ymd1 = $startDate.val();

            if (!Common.validateDate(ymd1)) {
                alert('시작일 날짜를 YYYYMMDD 형식으로 정확히 입력해 주세요.');
                $startDate.val('').focus();

                return;
            }
        }

        if ($endDate.val() != undefined  && $endDate.val() != ''){
            ymd2 = $endDate.val();


            if (!Common.validateDate(ymd2)) {
                alert('종료일 날짜를 YYYYMMDD 형식으로 정확히 입력해 주세요.');
                $endDate.val('').focus();

                return false;
            }
        }


        if (ymd1 != '' && ymd2 != '') {
            var d1 = new Date(ymd1.substring(1,4),ymd1.substring(4,6)-1,ymd1.substring(6,8));
            var d2 = new Date(ymd2.substring(1,4),ymd2.substring(4,6)-1,ymd2.substring(6,8));

            var $dayNumber = (d2 - d1) /(1000*60*60*24);


            if ($dayNumber < 0){
                if ($(this).attr('name').indexOf('ndDate') > -1) {
                    alert('종료일을 시작일 이후 날짜로 지정해 주세요.');
                    $endDate.val('').focus();

                } else {
                    alert('시작일을 종료일 이전 날짜로 지정해 주세요.');
                    $startDate.val('').focus();
                }
                return false;

            }
        }

    });
};

EventHandler.calendarBeforDateVaild =  function(){
    var now = new Date();
    var year= now.getFullYear();
    var mon = (now.getMonth()+1)>9 ? ''+(now.getMonth()+1) : '0'+(now.getMonth()+1);
    var day = now.getDate()>9 ? ''+now.getDate() : '0'+now.getDate();

    now = year +  mon + day;

    $('._beforDate').on("change",function(){
        if ($(this).val() != '' && $(this).val() < now) {
            alert('오늘 이전의 날짜는 입력 할 수 없습니다.');
            $(this).val('');
        }
    });
};

EventHandler.calendarAfterDateVaild =  function(){
    var now = new Date();
    var year= now.getFullYear();
    var mon = (now.getMonth()+1)>9 ? ''+(now.getMonth()+1) : '0'+(now.getMonth()+1);
    var day = now.getDate()>9 ? ''+now.getDate() : '0'+now.getDate();

    now = year +  mon + day;

    $('._afterDate').on("change",function(){
        if ($(this).val() != '' && $(this).val() > now) {
            alert('오늘 이전의 날짜만 입력 할 수 있습니다.');
            $(this).val('');
        }
    });
};


EventHandler.checkRejectContentLength =  function(){
    $('#reject_content').on('keyup change', function() {
        var str = $(this).val();
        var maxlength = 140;
        if (str.length > maxlength) {
            $(this).val(str.substr(0, maxlength));
            alert('140자 까지만 입력이 가능합니다.');
            $(this).focus();
            return false;
        }
    });
};


/**
 * Load Event
 */
$(function() {

    // 이벤트 핸들러등록.
    EventHandler.checkAllEvent();
    EventHandler.changeNumberCommaEvent();
//	EventHandler.checkTextareaMaxlength();
    EventHandler.checkOnlyNumber();
    EventHandler.checkOnlyNumberNegative(); 	// 양수,음수 입력 이벤트.
    EventHandler.checkOnlyNumberFloat();
    EventHandler.checkRejectContentLength();

    // 이미지 Error
    $("img").error(function () {
        $(this).unbind("error").attr("src", "/content/images/common/no-image-gray.gif");
    });

    $('#query').css('imeMode','active');

    $('form').not('.without-loading').on("submit", function() {
        Common.loading.show();
    });

    $.ajaxSetup({
        beforeSend: function ( xhr ) {
            xhr.setRequestHeader(Common.getCsrfHeader(), Common.getCsrf());
            Common.loading.show();
        },
        complete: function () {
            Common.loading.hide();
	    },
        error: function() {
            Common.loading.hide();
        }
    });

    // auto csrf setting
    Common.setCsrfByForm();

});

Common.DateButtonEvent = {
    //1주일전, 15일전 등.. 오늘 날짜를 기준으로 날짜를 구함..
    set : function(element, character, sInput, eInput) {
        sInput = sInput == undefined ? 'input[name="startDate"]' : sInput;
        eInput = eInput == undefined ? 'input[name="endDate"]' : eInput;
        character = character == undefined ? '' : character;
        if ($(element).size() > 0 && $(sInput).size() > 0 && $(eInput).size() > 0) {
            var date = new Date();
            var eDate = date.getFullYear() + character + Common.addZero((date.getMonth() + 1), 2) + character + Common.addZero(date.getDate(), 2);
            $.each($(element), function(){
                $_hasClass = $(this).attr('class').split(' ');
                var step = undefined;
                var mode = 'today';
                $.each($_hasClass, function(i) {
                    var className = $.trim($_hasClass[i]);
                    if (className.indexOf('week-') == 0) {
                        step = className.replace('week-', '');
                        mode = 'week';
                    } else if (className.indexOf('day-') == 0) {
                        step = className.replace('day-', '');
                        mode = 'day';
                    } else if (className.indexOf('month-') == 0) {
                        step = className.replace('month-', '');
                        mode = 'month';
                    } else if (className.indexOf('year-') == 0) {
                        step = className.replace('year-', '');
                        mode = 'year';
                    } else if (className.indexOf('clear') == 0) {
                        mode = 'clear';
                    } else {
                        mode = 'today';
                    }
                });

                if (mode != undefined) {
                    $(this).bind('click', function() {
                        if (mode == 'today') {
                            $(sInput).val(eDate);
                            $(eInput).val(eDate);
                        } else if(mode == 'clear') {
                            $(sInput).val('');
                            $(eInput).val('');
                        } else if (step != undefined) {
                            $(sInput).val(Common.DateButtonEvent.getDiffDate(mode, step, character));
                            $(eInput).val(eDate);
                        }
                    });
                }

            });
        }
    },
    // 날짜를 계산해서 리턴..
    getDiffDate : function getDiffDate(mode, value, character) {
        var date = new Date();

        if (mode == 'week') {
            date.setDate(date.getDate() - (value * 7));
        } else if (mode == 'day') {
            date.setDate(date.getDate() - value);
        } else if (mode == 'month') {
            date.setMonth(date.getMonth() - value);
        } else if (mode == 'year') {
            date.setFullYear(date.getFullYear() - value);
        }

        return date.getFullYear() + character + Common.addZero((date.getMonth() + 1), 2) + character + Common.addZero(date.getDate(), 2);
    }
};

// 숫자에 0을 붙임..
Common.addZero = function(v, s) {
    var stringValue = "" + v;
    if (s > stringValue.length) {
        for (var i = 0; i < s - stringValue.length; i++) v = "0" + v;
    }

    return v;
};

//모바일 키패드 셋팅
Common.setMobileKeypad = (function(isStyleCopy){

    try {
        isStyleCopy = isStyleCopy == undefined ? false : isStyleCopy;
        function getCss(a) {
            var sheets = document.styleSheets, o = {};
            for (var i in sheets) {
                var rules = sheets[i].rules || sheets[i].cssRules;
                for (var r in rules) {
                    if (a.is(rules[r].selectorText)) {
                        o = $.extend(o, cssToJson(rules[r].style), cssToJson(a.attr('style')));
                    }
                }
            }

            return o;
        }

        function cssToJson(css) {
            var s = {};
            if (!css) return s;
            if (css instanceof CSSStyleDeclaration) {
                for (var i in css) {
                    if ((css[i]).toLowerCase) {
                        s[(css[i]).toLowerCase()] = (css[css[i]]);
                    }
                }
            } else if (typeof css == "string") {
                css = css.split("; ");
                for (var i in css) {
                    var l = css[i].split(": ");
                    s[l[0].toLowerCase()] = (l[1]);
                }
            }

            return s;
        }

        // 모바일 브라우저 인경우
        if (Common.browser.mobile == true) {
            // _number의 경우 전화 다이얼을 띄움
            $.each($('input[type=text]'), function() {
                var type = "";
                if ($(this).hasClass('_email') || $(this).hasClass('email')) {
                    type = "email";
                } else if ($(this).hasClass('_number') || $(this).hasClass('number')) {
                    type = "tel";
                } else {
                    type = "text";
                }

                if (isStyleCopy == true) {
                    var style = getCss($(this));
                    $(this).css(style);
                }

                $(this).attr('type', type);
            });
        }

    } catch(e) {
        //alert(e.message);
    }
});

// require 표시 
Common.displayRequireMark = function() {
    $('.required').closest('td').prev().append(' <span class="require">*</span>');
    //$('.required').closest('tr').find('td.label').append(' <span class="require">*</span>');
};

// 글자수 체크
Common.checkedMaxStringLength = function(textObj, showObj, limitLength) {

    var $input =  $(textObj);
    var $show = $(showObj);

    var update = function () {

        var before = limitLength;
        var now = limitLength - $input.val().length;

        if (now < 0) {

            var msg = limitLength+"자를 초과하였습니다";

            var str = $input.val();
            $input.focus();
            var inputVal = str.substr(0, limitLength);

            if ($show.length > 0) {

                if ($show.is('INPUT') || $show.is('TEXTAREA')) {
                    $show.val(msg);
                } else {
                    $show.text(msg);
                }
            } else {
                alert(msg);
            }
            now = 0;
            $input.val(inputVal);
        }

        if (before != now) {
            if ($show.length > 0) {
                $show.text(now);
            }
        }

    }

    $input.bind('input keyup paste', function(){
        setTimeout(update,0);
    });
    update();

};

Common.isEdited = function(keyCode){
    var isEdit = true;
    var codeList = new Array(9, 16, 17, 18, 19, 20, 21, 27, 33, 34, 35, 36, 37, 38, 39, 40, 91, 92, 93, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 144, 145);
    for (var i=0; i<codeList.length; i++) {
        if (keyCode == codeList[i]) {
            isEdit = false;
        }
    }
    return isEdit;
};

Common.getCsrf = function() {

	var metas = document.getElementsByTagName('meta');

	for (var i=0; i<metas.length; i++) {
		if (metas[i].getAttribute('name') == '_csrf') {
			return metas[i].getAttribute('content');
		}
	}

	return '';
}

Common.getCsrfHeader = function() {

	var metas = document.getElementsByTagName('meta');

	for (var i=0; i<metas.length; i++) {
		if (metas[i].getAttribute('name') == '_csrf_header') {
			return metas[i].getAttribute('content');
		}
	}

	return '';
}

Common.setCsrfByForm = function() {
    var forms = document.getElementsByTagName('form'),
        csrf = Common.getCsrf();

    for (var i=0; i<forms.length; i++) {
        var form = forms[i];
        var element = form.elements['_csrf'];
	    if (typeof element == 'undefined' ) {

            var newDiv = document.createElement('div');
		    newDiv.innerHTML = '<input type="hidden" name="_csrf" value="'+csrf+'">';
	        form.appendChild(newDiv);

        } else {
	        element.value = csrf;
        }
    }
}

Common.getCsrfNode = function() {
    var csrf = Common.getCsrf();
    return '<input type="hidden" name="_csrf" value="'+csrf+'">';
}

Common.getApplicationDevice = function() {

    var userAgent = navigator.userAgent || navigtor.vendor || window.opera;

    if (/SALESON_APPLICATION_ANDROID/i.test(userAgent)) {
        return 'ANDROID'
    }

    if (/SALESON_APPLICATION_IOS/.test(userAgent)) {
        return 'IOS';
    }

    return 'Not Application';
}

Common.isApplicationDevice = function() {

    var device = Common.getApplicationDevice();

    if (device == 'ANDROID') {
        return true;
    }

    if (device == 'IOS') {
        return true;
    }

    return false;
}