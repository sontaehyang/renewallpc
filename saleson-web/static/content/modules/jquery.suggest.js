(
    function ($) {
        $.suggest = function (input, options) {
            var doHide = 0; //0:隠さない 1:隠す
            var searchNum = 500; //10,30,100,300,500

            for (var i = 1; i <= 3; i++) {
                document.getElementById("tmpValTa" + i).value = "";
            }

            var $input = $(input).attr("autocomplete", "off");
            var NEW_UL = document.createElement("ul");
            NEW_UL.id = "SUGGEST_UL";
            var SUGGEST_LIST = $(NEW_UL);


            //SUGGEST_LIST.attachEvent("onmouseover",document.getElementById("ConditionsSearchWord").focus());

            var intervalID = null;
            var timeout = false; // hold timeout ID for suggestion results to appear	
            var prevWord = ""; // last recorded word of $input.val()

            var cache = []; // cache MRU list
            var cacheSize = 0; // size of cache in chars (bytes?)

            var cookie_name = "search";

            var form = "." + jQuery(input).attr("class");

            if (options["cookie_name"]) {
                cookie_name = options["cookie_name"];
            }


            var resultDiv = document.createElement("div");
            resultDiv.id = "RESULT_DIV";
            var ResultDiv = $(resultDiv);
            ResultDiv.appendTo('body');


            var str = "";
            str += "<div id='RESULT_DIV2'></div>";
            str += "<div id='RESULT_DIV3'></div>";

            document.getElementById("RESULT_DIV").innerHTML = str;
            document.getElementById("RESULT_DIV2").appendChild(NEW_UL);

            document.getElementById("RESULT_DIV").style.display = "none";


            resetPosition();

            $(window).load(resetPosition).resize(resetPosition);

            var eventFunc = function () {
                if (document.getElementById("RESULT_DIV") != undefined) {
                    document.getElementById("RESULT_DIV").style.display = "none";
                }
            };

            if (window.addEventListener) {
                window.addEventListener("click", eventFunc, false);
            } else if (window.attachEvent) {
                window.attachEvent("onclick", eventFunc);
            } else {
                window.onclick = eventFunc;
            }


            $input.blur(
                function () {
                    if (doHide) {
                        setTimeout
                            (
                                function () {
                                    SUGGEST_LIST.hide();
                                    document.getElementById("RESULT_DIV").style.display = "none";
                                }, 200
                            );
                    }
                }
            );


            try {
                SUGGEST_LIST.bgiframe();
            } catch (e) {}


            $input.focus(function () {
                intervalID = setInterval(
                    function () {
                        if (prevWord != $input.val()) {
                            suggest();
                            prevWord = $input.val();
                        }
                    },
                    300
                );
            });



            $input.blur(
                function () {
                    clearInterval(intervalID);
                    intervalID = null;
                }
            );


            // [SKC] $.browser.mozilla ==> 
            if (Common.browser.gecko) {
                $input.keypress(processKey); // onkeypress repeats arrow keys in Mozilla/Opera
                $input.keyup(processKey); // for IME
            } else {
                $input.keydown(processKey); // onkeydown repeats arrow keys in IE/Safari
            }




            function resetPosition() {
                var offset = $input.offset();

                SUGGEST_LIST.css({
                    top: (offset.top + input.offsetHeight) + 'px',
                    left: offset.left + 'px'
                });
            }




            function processKey(e) {
                if ((/27$|38$|40$/.test(e.keyCode) && SUGGEST_LIST.is(':visible')) || (/^13$|^9$/.test(e.keyCode) && getCurrentResult())) {
                    if ("keyup" == e.type) return;

                    if (e.preventDefault) e.preventDefault();
                    if (e.stopPropagation) e.stopPropagation();

                    e.cancelBubble = true;
                    e.returnValue = false;

                    switch (e.keyCode) {
                    case 38: // up
                        prevResult();
                        break;

                    case 40: // down
                        nextResult();
                        break;

                    case 9: // tab
                    case 13: // return
                        $currentResult = getCurrentResult();
                        if ($currentResult.find(".by_any_chance_url").length == 0) {
                            selectCurrentResult();
                            prevWord = $input.val();
                        } else {
                            title = $currentResult.find(".by_any_chance_url").text();
                            item_id = $currentResult.find(".by_any_chance_url").attr("id");
                            by_any_chance_item_id = $currentResult.find("img").attr("id");
                        }
                        break;

                    case 27: //	escape
                        if (doHide) {
                            SUGGEST_LIST.hide();
                            document.getElementById("RESULT_DIV").style.display = "none";
                        }
                        break;
                    }
                } else {
                    if (13 == e.keyCode) {
                        if ($.browser.mozilla && "keyup" == e.type) {
                            return;
                        }

                        prevWord = $input.val();

                        if (doHide) {
                            SUGGEST_LIST.hide(); // TODO click時入れる
                            document.getElementById("RESULT_DIV").style.display = "none";
                        }
                    }
                }
            }




            function suggest() {

                for (var i = 1; i <= 3; i++) {
                    document.getElementById("tmpValTa" + i).value = "";
                }



                var q = $.trim($input.val());
                var c = $.cookie(cookie_name);

                if (q.length >= options.minchars) {
                    cached = checkCache(q);

                    if (cached) {
                        resetPosition();
                        displayItems(cached['items']);
                        byAnyChance(cached['by_any_chance_items']);
                    } else {
                        s_items = [];
                        b_items = [];

                        jQuery.suggestCallback = function (list) {
                            resetPosition();

                            if (doHide) {
                                SUGGEST_LIST.hide();
                                document.getElementById("RESULT_DIV").style.display = "none";
                            }

                            var items = parseList(list, q);
                            s_items = items;
                            displayItems(items);
                        }

                        Common.loading.display = false;

                        if (searchNum == 10) {
                            $.getScript(options.source + '?' + $.param({
                                q: q,
                                c: c,
                                callback: 'jQuery.suggestCallback'
                            }));
                        } else if (searchNum == 30) {
                            $.getScript(options.source + '?' + $.param({
                                q: q,
                                limit: 30,
                                c: c,
                                callback: 'jQuery.suggestCallback'
                            }));
                        } else if (searchNum == 100) {
                            $.getScript(options.source + '?' + $.param({
                                q: q,
                                limit: 100,
                                c: c,
                                callback: 'jQuery.suggestCallback'
                            }));
                        } else if (searchNum == 300) {
                            $.getScript(options.source + '?' + $.param({
                                q: q,
                                limit: 300,
                                c: c,
                                callback: 'jQuery.suggestCallback'
                            }));
                        } else if (searchNum == 500) {
                            $.getScript(options.source + '?' + $.param({
                                q: q,
                                limit: 500,
                                c: c,
                                callback: 'jQuery.suggestCallback'
                            }));
                        }

                        jQuery.byAnyChanceCallback = function (list) {
                            b_items = list;
                            byAnyChance(list);
                            addToCache(q, s_items, b_items, s_items.length + b_items.length);
                        }
                        
                        Common.loading.display = true;
                    }
                } else {
                    if (doHide) {
                        SUGGEST_LIST.hide();
                        document.getElementById("RESULT_DIV").style.display = "none";
                    }
                }
            }




            function checkCache(q) {
                for (var i = 0; i < cache.length; i++) {
                    if (cache[i]['q'] == q) {
                        cache.unshift(cache.splice(i, 1)[0]);
                        return cache[0];
                    }
                }

                return false;
            }




            function addToCache(q, items, by_any_chance_items, size) {
                while (cache.length && (cacheSize + size > options.maxCacheSize)) {
                    var cached = cache.pop();
                    cacheSize -= cached['size'];
                }

                cache.push({
                    q: q,
                    size: size,
                    items: items,
                    by_any_chance_items: by_any_chance_items
                });

                cacheSize += size;
            }




            ////////////////////////////////////////////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////
            function displayItems(items) {
                    document.getElementById("RESULT_DIV").style.display = "none";
                    for (var i = 1; i <= 3; i++) {
                        document.getElementById("tmpValTa" + i).value = "";
                    }
                    if (!items) {
                        return false;
                    }
                    if (!items.length) {
                        if (doHide) {
                            SUGGEST_LIST.hide();
                        }
                        return;
                    }
                    document.getElementById("tmpValTa1").value = items;
                    var z = 0;
                    for (var i = 0; i < items.length; i++) {
                        if (delSpace(items[i]) != "") {
                            z++;
                        }
                    }
                    if (z > 0) {
                        document.getElementById("tmpValTa2").value = "";
                        document.getElementById("tmpValTa3").value = "";
                        document.getElementById("RESULT_DIV3").innerHTML = "";



                        var changeV = "";
                        changeV = $input.val();
                        changeV = replaceAll(changeV, " ", "|");
                        changeV = replaceAll(changeV, "　", "|");
                        changeV = replaceAll(changeV, ",", "|");
                        var ex_cv = (changeV).split("|");



                        var count = 0;
                        for (var i = 0; i < ex_cv.length; i++) {
                            if (delSpace(ex_cv[i]) != "") {
                                count++;
                            }
                        }



                        if (count == 1) {
                            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// one word/s

                            var html = "";

                            var val_input = $input.val();

                            var VAL = new Array();
                            var g = 0;
                            var h = 0;

                            for (var i = 0; i < items.length; i++) {
                                changeV = items[i];
                                changeV = replaceAll(changeV, " ", "|");
                                changeV = replaceAll(changeV, "　", "|");
                                changeV = replaceAll(changeV, ",", "|");
                                var ex_v = (changeV).split("|");

                                for (var j = 0; j < ex_v.length; j++) {
                                    if (delSpace(ex_v[j]) == "") {
                                        continue;
                                    }

                                    if (ex_v[j].indexOf(val_input, 0) > -1) {
                                        VAL[g] = ex_v[j];
                                        g++;
                                    }
                                }
                            }

                            var VAL2 = array_unique(VAL);



                            document.getElementById("tmpValTa2").value = VAL2;



                            if (document.getElementById("tmpValTa2").value != "") {
                                var CCC = new Array();
                                for (var i = 0; i < 15; i++) {
                                    CCC[i] = VAL2[i];
                                }




                                if (CCC.length == 0) {
                                    return false;
                                }




                                for (var i = 0; i < CCC.length; i++) {
                                    if (CCC[i] == "") {
                                        continue;
                                    }
                                    if (CCC[i] == undefined) {
                                        continue;
                                    }

                                    if (i == 14) {
                                        html += '<li id=\'li_line_' + i + '\' class=\'suggest_li\'>';
                                    } else {
                                        html += '<li id=\'li_line_' + i + '\' class=\'suggest_li\' >';
                                    }

                                    html += '<a href=\'javascript:void(0);\'>' + CCC[i] + '</a>';
                                    html += '</li>';
                                }
                                /////////////////////////////////////////////////////////// answer/s
                                if (document.getElementById("RESULT_DIV3") != undefined) {
                                    document.getElementById("RESULT_DIV3").innerHTML = "";
                                }




                                /////////////////////////////////////////////////////////// answer/e
                                //------------------//表示関係/s
                                SUGGEST_LIST.html(html).show();

                                var bashoX = getLeft("ConditionsSearchWord");
                                var bashoY = getTop("ConditionsSearchWord");
                                var scrollTop = getScrollTop();




                                document.getElementById("RESULT_DIV").style.display = "block";
                                document.getElementById("RESULT_DIV").style.zIndex = '1000';




                                if (CCC.length == 0) {
                                    document.getElementById("RESULT_DIV").style.display = "none";
                                }




                                //------------------//表示関係/e
                                //--------------//
                                SUGGEST_LIST.children('li')
                                    .mouseover(
                                        function () {




                                            //document.getElementById("RESULT_DIV").style.display = "block";




                                            SUGGEST_LIST.children('li').removeClass(options.selectClass);
                                            $(this).addClass(options.selectClass);
                                        }
                                    )




                                /*
//SUGGEST_LIST.attachEvent("onmouseover",document.getElementById("ConditionsSearchWord").focus());




.mousedown
(
function()
{





document.getElementById("RESULT_DIV").style.display = "block";



}
)
*/




                                .click
                                    (
                                        function (e) {
                                            e.preventDefault();
                                            e.stopPropagation();
                                            selectCurrentResult();
                                        }
                                    );




                                //--------------//
                            } //if (document.getElementById("tmpValTa2").value!="")
                            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// one word/e
                        } else {
                            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// fukusuu word/s




                            var html = "";

                            /*
var Arr = new Array();
Arr[0] = $input.val();
*/



                            var CCC = new Array();
                            CCC[0] = $input.val();




                            if (CCC.length == 0) {
                                return false;
                            }




                            for (var i = 0; i < CCC.length; i++) {
                                if (CCC[i] == "") {
                                    continue;
                                }
                                if (CCC[i] == undefined) {
                                    continue;
                                }
                                html += '<li id=\'li_line_' + i + '\' class=\'suggest_li\'>';
                                html += '<a href=\'javascript:void(0);\'>' + CCC[i] + '</a>';
                                html += '</li>';
                            }




                            //------------------//表示関係/s




                            SUGGEST_LIST.html(html).show();




                            /*
document.getElementById("SUGGEST_UL").style.top = "0px";
document.getElementById("SUGGEST_UL").style.left = "0px";
document.getElementById("SUGGEST_UL").style.marginTop = "0px";
document.getElementById("SUGGEST_UL").style.marginRight = "0px";
document.getElementById("SUGGEST_UL").style.marginBottom = "0px";
//document.getElementById("SUGGEST_UL").style.marginLeft = "-40px";
*/
                            document.getElementById("SUGGEST_UL").style.listStyleType = "none";
                            var bashoX = getLeft("ConditionsSearchWord");
                            var bashoY = getTop("ConditionsSearchWord");
                            var scrollTop = getScrollTop();
                            /*
document.getElementById("SUGGEST_UL").style.marginLeft = "0px";
document.getElementById("RESULT_DIV").style.background = "#ffffff";
document.getElementById("RESULT_DIV").style.position = "absolute";
document.getElementById("RESULT_DIV").style.top = (bashoY+scrollTop+22)+"px";
document.getElementById("RESULT_DIV").style.left = bashoX+"px";
*/




                            document.getElementById("RESULT_DIV").style.display = "block";



                            if (CCC.length == 0) {
                                document.getElementById("RESULT_DIV").style.display = "none";
                            }




                            //------------------//表示関係/e

                            //--------------//
                            SUGGEST_LIST.children('li')
                                .mouseover(
                                    function () {
                                        SUGGEST_LIST.children('li').removeClass(options.selectClass);
                                        $(this).addClass(options.selectClass);
                                    }
                                )
                                .click(
                                    function (e) {
                                        e.preventDefault();
                                        e.stopPropagation();
                                        selectCurrentResult();
                                    }
                                );
                            //--------------//




                            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// fukusuu word/e
                        }
                    } //if (z>0)
                } //function displayItems(items)
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////




            ///////////////////////////////////////////////////////////////////////////////////// 追加ファンクション/s
            function getLeft(youso) {
                // [SKC]
                try {
                    var html = document.documentElement;
                    var rect = document.getElementById(youso).getBoundingClientRect();
                    var left = rect.left - html.clientLeft;
                    return left;
                } catch (e) {
                    return 0;
                }
            }



            function getTop(youso) {
                // [SKC]
                try {
                    var html = document.documentElement;
                    var rect = document.getElementById(youso).getBoundingClientRect();
                    var top = rect.top - html.clientTop;
                    return top;
                } catch (e) {
                    return 0;
                }
            }



            function getScrollTop() {
                var html = document.documentElement;
                var body = document.body;
                var scrollTop = (body.scrollTop || html.scrollTop);
                return scrollTop;
            }



            function replaceAll(expression, org, dest) {
                return expression.split(org).join(dest);
            }



            function array_unique(Arr) {
                var storeArr = new Array;

                var ret = new Array;

                i = 0;
                f = 0;
                while (Arr[i] != null) {
                    if (Arr[i] != "") {
                        if (storeArr[String(Arr[i])]) {} else {
                            storeArr[String(Arr[i])] = 1;
                            ret[f] = Arr[i];
                            f++;
                        }
                    }

                    i++;
                }

                return ret;
            }



            if (!Array.prototype.contains) {
                Array.prototype.contains = function (value) {
                    for (var i in this) {
                        if (this.hasOwnProperty(i) && this[i] === value) {
                            return true;
                        }
                    }

                    return false;
                }
            }



            function fromHiragana_toKatakana(value) {
                var ret = "";
                ret = value.replace(/[ぁ-ん]/g, function (s) {
                    return String.fromCharCode(s.charCodeAt(0) + 0x60);
                });
                return ret;
            }



            function fromKatakana_toHiragana(value) {
                var ret = "";
                ret = value.replace(/[ァ-ン]/g, function (s) {
                    return String.fromCharCode(s.charCodeAt(0) - 0x60);
                });
                return ret;
            }



            function delSpace(p_val) {
                var flg = 1;

                for (i = 0; i < p_val.length; i++) {
                    if ((p_val.substring(i, i + 1) != ' ') && (p_val.substring(i, i + 1) != '　')) {
                        p_val = p_val.substring(i, p_val.length + 1);
                        flg = 0;
                        break;
                    }
                }

                for (i = p_val.length - 1; i >= 0; i--) {
                    if ((p_val.substring(i, i + 1) != ' ') && (p_val.substring(i, i + 1) != '　')) {
                        p_val = p_val.substring(0, i + 1);
                        flg = 0;
                        break;
                    }
                }

                if (flg) {
                    p_val = '';
                }

                return (p_val);
            }



            function DeleteLineFeed(myLen) {
                var newLen = '';

                for (var i = 0; i < myLen.length; i++) {
                    text = escape(myLen.substring(i, i + 1));

                    if (text != "%0D" && text != "%0A") {
                        newLen += myLen.substring(i, i + 1);
                    }
                }

                return (newLen);
            }



            function searchFromShiin(shiin) {
                var ret = "";

                var AAA = new Array();

                var before = "ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ";

                var after = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

                var str = "";

                for (var i = 0; i < after.length; i++) {
                    AAA[i] = after.substr(i, 1);
                }

                var pos = before.indexOf(shiin, 0);

                if (pos > -1) {
                    str = AAA[pos];
                } else {
                    var pos2 = after.indexOf(shiin, 0);
                    if (pos2 > -1) {
                        str = AAA[pos2];
                    }
                }

                var ret2 = "";
                if (str != "") {
                    switch (str.toLowerCase()) {
                    case "k":
                        ret = "かきくけこカキクケコ";
                        break;
                    case "s":
                        ret = "さしすせそサシスセソ";
                        break;
                    case "t":
                        ret = "たちつてとタチツテト";
                        break;
                    case "n":
                        ret = "なにぬねのナニヌネノ";
                        break;
                    case "h":
                        ret = "はひふへほハヒフヘホ";
                        break;
                    case "m":
                        ret = "まみむめもマミムメモ";
                        break;
                    case "y":
                        ret = "やゆよヤユヨ";
                        break;
                    case "r":
                        ret = "らりるれろラリルレロ";
                        break;
                    case "w":
                        ret = "わワ";
                        break;
                    case "g":
                        ret = "がぎぐげごガギグゲゴ";
                        break;
                    case "z":
                        ret = "ざじずぜぞザジズゼゾ";
                        break;
                    case "d":
                        ret = "だぢづでどダヂヅデド";
                        break;
                    case "b":
                        ret = "ばびぶべぼバビブベボ";
                        break;
                    case "p":
                        ret = "ぱぴぷぺぽパピプペポ";
                        break;
                    }

                    for (var i = 0; i < ret.length; i++) {
                        ret2 += ret.substr(i, 1) + "|";
                    }
                }

                return ret2;
            }

            ///////////////////////////////////////////////////////////////////////////////////// 追加ファンクション/e




            function byAnyChance(items) {
                if (!items) {
                    return;
                }

                if (!items.length) {
                    return;
                }

                var html = ''
                var sp = '<tr><td class="by_any_chance_title">ひょっとして</td><td colspan="2"></td></tr>';

                for (var i = 0; i < items.length; i++) {
                    html += '<li>';

                    if (i == 0 && i == items.length - 1) {
                        html += '<table class="by_any_chance by_any_chance_double">' + sp;
                    } else if (i == 0) {
                        html += '<table class="by_any_chance by_any_chance_first">' + sp;
                    } else if (i == items.length - 1) {
                        html += '<table class="by_any_chance by_any_chance_last">';
                    } else {
                        html += '<table class="by_any_chance">';
                    }

                    html += '<tr><td rowspan="2" width="50px">';
                    html += '<img src="' + items[i]["img_url"] + '" id="' + items[i]["by_any_chance_item_id"] + '" width="50px">';
                    html += '</td><td colspan="2">';
                    html += '<span class="by_any_chance_url" id="' + items[i]["item_id"] + '" name="' + items[i]["url"] + '">' + items[i]["title"] + '</span>';
                    html += '</td></tr><tr><td class="by_any_chance_price">';
                    html += '￥' + (items[i]["price"] + "").replace(/([0-9]+?)(?=(?:[0-9]{3})+$)/g, '$1,');
                    html += '</td><td class="by_any_chance_send_detail">';
                    html += '⇒詳細へ';
                    html += '</td></tr></table></li>';
                }

                if (SUGGEST_LIST.is(":visible")) {
                    SUGGEST_LIST.prepend(html);
                } else {
                    SUGGEST_LIST.html(html).show();
                }

                jQuery("table.by_any_chance").attr("width", jQuery("ul.SUGGEST_OUTER_UL").width());

                SUGGEST_LIST.children('li')

                .mouseover
                    (
                        function () {
                            SUGGEST_LIST.children('li').removeClass(options.selectClass);

                            $(this).addClass(options.selectClass);
                        }
                    )

                .click
                    (
                        function (e) {
                            if ($(this).find(".by_any_chance_url").length !== 0) {
                                title = $(this).find(".by_any_chance_url").text();

                                item_id = $(this).find(".by_any_chance_url").attr("id");

                                by_any_chance_item_id = $(this).find("img").attr("id");

                                e.preventDefault();

                                e.stopPropagation();
                            }
                        }
                    );
            }




            function selectAndMove() {
                $currentResult = getCurrentResult();

                if ($currentResult) {
                    location.href = $currentResult.find(".by_any_chance_url").attr("name");
                }
            }




            function parseList(tokens, q) {
                var items = [];

                for (var i = 0; i < tokens.length; i++) {
                    var token = $.trim(tokens[i]);
                    if (token) {
                        items[items.length] = token;
                    }
                }

                return items;
            }




            function getCurrentResult() {
                if (!SUGGEST_LIST.is(':visible')) {
                    return false;
                }

                var $currentResult = SUGGEST_LIST.children('li.' + options.selectClass);

                if (!$currentResult.length) {
                    $currentResult = false;
                }

                return $currentResult;
            }




            function selectCurrentResult() {
                $currentResult = getCurrentResult();

                if ($currentResult) {
                    $input.val($currentResult.text());




                    SUGGEST_LIST.hide();
                    document.getElementById("RESULT_DIV").style.display = "none";




                    /*
				if (doHide)
				{
					SUGGEST_LIST.hide();
					document.getElementById("RESULT_DIV").style.display = "none";
				}
*/
                    jQuery(form).submit();

                    if (options.onSelect) {
                        options.onSelect.apply($input[0]);
                    }
                }

                //-----------------// ■検索フォームsubmit/s
                var form = document.getElementById("kbmjSearchParam");
                form.submit();
                //-----------------// ■検索フォームsubmit/e
            }




            function nextResult() {
                $currentResult = getCurrentResult();

                if ($currentResult) {
                    $currentResult.removeClass(options.selectClass).next().addClass(options.selectClass);
                } else {
                    SUGGEST_LIST.children('li:first-child').addClass(options.selectClass);
                }
            }




            function prevResult() {
                $currentResult = getCurrentResult();

                if ($currentResult) {
                    $currentResult.removeClass(options.selectClass).prev().addClass(options.selectClass);
                } else {
                    SUGGEST_LIST.children('li:last-child').addClass(options.selectClass);
                }
            }
        }




        $.fn.suggest = function (source, options) {
            if (!source) {
                return;
            }

            options = options || {};
            options.source = source;
            options.delay = options.delay || 100;

            options.resultsClass = options.resultsClass || 'SUGGEST_OUTER_UL';

            options.selectClass = options.selectClass || 'ac_over';
            options.matchClass = options.matchClass || 'ac_match';
            options.minchars = options.minchars || 1;
            options.delimiter = options.delimiter || '\n';
            options.onSelect = options.onSelect || false;
            options.maxCacheSize = options.maxCacheSize || 65536;

            this.each(function () {
                new $.suggest(this, options);
            });

            return this;
        };



    }
)(jQuery);