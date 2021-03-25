/*
 *	jQuery carouFredSel 6.0.4
 *	Demo's and documentation:
 *	caroufredsel.frebsite.nl
 *
 *	Copyright (c) 2012 Fred Heusschen
 *	www.frebsite.nl
 *
 *	Dual licensed under the MIT and GPL licenses.
 *	http://en.wikipedia.org/wiki/MIT_License
 *	http://en.wikipedia.org/wiki/GNU_General_Public_License
 */


(function ($) {
    if ($.fn.carouFredSel) {
        return
    }
    $.fn.caroufredsel = $.fn.carouFredSel = function (u, w) {
        if (this.length == 0) {
            debug(true, 'No element found for "' + this.selector + '".');
            return this
        }
        if (this.length > 1) {
            return this.each(function () {
                $(this).carouFredSel(u, w)
            })
        }
        var y = this,
            $tt0 = this[0],
            starting_position = false;
        if (y.data('_cfs_isCarousel')) {
            starting_position = y.triggerHandler('_cfs_triggerEvent', 'currentPosition');
            y.trigger('_cfs_triggerEvent', ['destroy', true])
        }
        y._cfs_init = function (o, a, b) {
            o = go_getObject($tt0, o);
            o.items = go_getItemsObject($tt0, o.items);
            o.scroll = go_getScrollObject($tt0, o.scroll);
            o.auto = go_getAutoObject($tt0, o.auto);
            o.prev = go_getPrevNextObject($tt0, o.prev);
            o.next = go_getPrevNextObject($tt0, o.next);
            o.pagination = go_getPaginationObject($tt0, o.pagination);
            o.swipe = go_getSwipeObject($tt0, o.swipe);
            o.mousewheel = go_getMousewheelObject($tt0, o.mousewheel);
            if (a) {
                opts_orig = $.extend(true, {}, $.fn.carouFredSel.defaults, o)
            }
            opts = $.extend(true, {}, $.fn.carouFredSel.defaults, o);
            opts.d = cf_getDimensions(opts);
            z.direction = (opts.direction == 'up' || opts.direction == 'left') ? 'next' : 'prev';
            var c = y.children(),
                avail_primary = ms_getParentSize($wrp, opts, 'width');
            if (is_true(opts.cookie)) {
                opts.cookie = 'caroufredsel_cookie_' + conf.serialNumber
            }
            opts.maxDimension = ms_getMaxDimension(opts, avail_primary);
            opts.items = in_complementItems(opts.items, opts, c, b);
            opts[opts.d['width']] = in_complementPrimarySize(opts[opts.d['width']], opts, c);
            opts[opts.d['height']] = in_complementSecondarySize(opts[opts.d['height']], opts, c);
            if (opts.responsive) {
                if (!is_percentage(opts[opts.d['width']])) {
                    opts[opts.d['width']] = '100%'
                }
            }
            if (is_percentage(opts[opts.d['width']])) {
                z.upDateOnWindowResize = true;
                z.primarySizePercentage = opts[opts.d['width']];
                opts[opts.d['width']] = ms_getPercentage(avail_primary, z.primarySizePercentage);
                if (!opts.items.visible) {
                    opts.items.visibleConf.variable = true
                }
            }
            if (opts.responsive) {
                opts.usePadding = false;
                opts.padding = [0, 0, 0, 0];
                opts.align = false;
                opts.items.visibleConf.variable = false
            } else {
                if (!opts.items.visible) {
                    opts = in_complementVisibleItems(opts, avail_primary)
                }
                if (!opts[opts.d['width']]) {
                    if (!opts.items.visibleConf.variable && is_number(opts.items[opts.d['width']]) && opts.items.filter == '*') {
                        opts[opts.d['width']] = opts.items.visible * opts.items[opts.d['width']];
                        opts.align = false
                    } else {
                        opts[opts.d['width']] = 'variable'
                    }
                }
                if (is_undefined(opts.align)) {
                    opts.align = (is_number(opts[opts.d['width']])) ? 'center' : false
                }
                if (opts.items.visibleConf.variable) {
                    opts.items.visible = gn_getVisibleItemsNext(c, opts, 0)
                }
            } if (opts.items.filter != '*' && !opts.items.visibleConf.variable) {
                opts.items.visibleConf.org = opts.items.visible;
                opts.items.visible = gn_getVisibleItemsNextFilter(c, opts, 0)
            }
            opts.items.visible = cf_getItemsAdjust(opts.items.visible, opts, opts.items.visibleConf.adjust, $tt0);
            opts.items.visibleConf.old = opts.items.visible;
            if (opts.responsive) {
                if (!opts.items.visibleConf.min) {
                    opts.items.visibleConf.min = opts.items.visible
                }
                if (!opts.items.visibleConf.max) {
                    opts.items.visibleConf.max = opts.items.visible
                }
                opts = in_getResponsiveValues(opts, c, avail_primary)
            } else {
                opts.padding = cf_getPadding(opts.padding);
                if (opts.align == 'top') {
                    opts.align = 'left'
                } else if (opts.align == 'bottom') {
                    opts.align = 'right'
                }
                switch (opts.align) {
                case 'center':
                case 'left':
                case 'right':
                    if (opts[opts.d['width']] != 'variable') {
                        opts = in_getAlignPadding(opts, c);
                        opts.usePadding = true
                    }
                    break;
                default:
                    opts.align = false;
                    opts.usePadding = (opts.padding[0] == 0 && opts.padding[1] == 0 && opts.padding[2] == 0 && opts.padding[3] == 0) ? false : true;
                    break
                }
            } if (!is_number(opts.scroll.duration)) {
                opts.scroll.duration = 500
            }
            if (is_undefined(opts.scroll.items)) {
                opts.scroll.items = (opts.responsive || opts.items.visibleConf.variable || opts.items.filter != '*') ? 'visible' : opts.items.visible
            }
            opts.auto = $.extend(true, {}, opts.scroll, opts.auto);
            opts.prev = $.extend(true, {}, opts.scroll, opts.prev);
            opts.next = $.extend(true, {}, opts.scroll, opts.next);
            opts.pagination = $.extend(true, {}, opts.scroll, opts.pagination);
            opts.auto = go_complementAutoObject($tt0, opts.auto);
            opts.prev = go_complementPrevNextObject($tt0, opts.prev);
            opts.next = go_complementPrevNextObject($tt0, opts.next);
            opts.pagination = go_complementPaginationObject($tt0, opts.pagination);
            opts.swipe = go_complementSwipeObject($tt0, opts.swipe);
            opts.mousewheel = go_complementMousewheelObject($tt0, opts.mousewheel);
            if (opts.synchronise) {
                opts.synchronise = cf_getSynchArr(opts.synchronise)
            }
            if (opts.auto.onPauseStart) {
                opts.auto.onTimeoutStart = opts.auto.onPauseStart;
                deprecated('auto.onPauseStart', 'auto.onTimeoutStart')
            }
            if (opts.auto.onPausePause) {
                opts.auto.onTimeoutPause = opts.auto.onPausePause;
                deprecated('auto.onPausePause', 'auto.onTimeoutPause')
            }
            if (opts.auto.onPauseEnd) {
                opts.auto.onTimeoutEnd = opts.auto.onPauseEnd;
                deprecated('auto.onPauseEnd', 'auto.onTimeoutEnd')
            }
            if (opts.auto.pauseDuration) {
                opts.auto.timeoutDuration = opts.auto.pauseDuration;
                deprecated('auto.pauseDuration', 'auto.timeoutDuration')
            }
        };
        y._cfs_build = function () {
            y.data('_cfs_isCarousel', true);
            var a = y.children(),
                orgCSS = in_mapCss(y, ['textAlign', 'float', 'position', 'top', 'right', 'bottom', 'left', 'zIndex', 'width', 'height', 'marginTop', 'marginRight', 'marginBottom', 'marginLeft']),
                newPosition = 'relative';
            switch (orgCSS.position) {
            case 'absolute':
            case 'fixed':
                newPosition = orgCSS.position;
                break
            }
            $wrp.css(orgCSS).css({
                'overflow': 'hidden',
                'position': newPosition
            });
            y.data('_cfs_origCss', orgCSS).css({
                'textAlign': 'left',
                'float': 'none',
                'position': 'absolute',
                'top': 0,
                'right': 'auto',
                'bottom': 'auto',
                'left': 0,
                'marginTop': 0,
                'marginRight': 0,
                'marginBottom': 0,
                'marginLeft': 0
            });
            sz_storeMargin(a, opts);
            sz_storeSizes(a, opts);
            if (opts.responsive) {
                sz_setResponsiveSizes(opts, a)
            }
        };
        y._cfs_bind_events = function () {
            y._cfs_unbind_events();
            y.bind(cf_e('stop', conf), function (e, a) {
                e.stopPropagation();
                if (!z.isStopped) {
                    if (opts.auto.button) {
                        opts.auto.button.addClass(cf_c('stopped', conf))
                    }
                }
                z.isStopped = true;
                if (opts.auto.play) {
                    opts.auto.play = false;
                    y.trigger(cf_e('pause', conf), a)
                }
                return true
            });
            y.bind(cf_e('finish', conf), function (e) {
                e.stopPropagation();
                if (z.isScrolling) {
                    sc_stopScroll(scrl)
                }
                return true
            });
            y.bind(cf_e('pause', conf), function (e, a, b) {
                e.stopPropagation();
                tmrs = sc_clearTimers(tmrs);
                if (a && z.isScrolling) {
                    scrl.isStopped = true;
                    var c = getTime() - scrl.startTime;
                    scrl.duration -= c;
                    if (scrl.pre) {
                        scrl.pre.duration -= c
                    }
                    if (scrl.post) {
                        scrl.post.duration -= c
                    }
                    sc_stopScroll(scrl, false)
                }
                if (!z.isPaused && !z.isScrolling) {
                    if (b) {
                        tmrs.timePassed += getTime() - tmrs.startTime
                    }
                }
                if (!z.isPaused) {
                    if (opts.auto.button) {
                        opts.auto.button.addClass(cf_c('paused', conf))
                    }
                }
                z.isPaused = true;
                if (opts.auto.onTimeoutPause) {
                    var d = opts.auto.timeoutDuration - tmrs.timePassed,
                        perc = 100 - Math.ceil(d * 100 / opts.auto.timeoutDuration);
                    opts.auto.onTimeoutPause.call($tt0, perc, d)
                }
                return true
            });
            y.bind(cf_e('play', conf), function (e, b, c, d) {
                e.stopPropagation();
                tmrs = sc_clearTimers(tmrs);
                var v = [b, c, d],
                    t = ['string', 'number', 'boolean'],
                    a = cf_sortParams(v, t);
                b = a[0];
                c = a[1];
                d = a[2];
                if (b != 'prev' && b != 'next') {
                    b = z.direction
                }
                if (!is_number(c)) {
                    c = 0
                }
                if (!is_boolean(d)) {
                    d = false
                }
                if (d) {
                    z.isStopped = false;
                    opts.auto.play = true
                }
                if (!opts.auto.play) {
                    e.stopImmediatePropagation();
                    return debug(conf, 'Carousel stopped: Not scrolling.')
                }
                if (z.isPaused) {
                    if (opts.auto.button) {
                        opts.auto.button.removeClass(cf_c('stopped', conf));
                        opts.auto.button.removeClass(cf_c('paused', conf))
                    }
                }
                z.isPaused = false;
                tmrs.startTime = getTime();
                var f = opts.auto.timeoutDuration + c;
                dur2 = f - tmrs.timePassed;
                perc = 100 - Math.ceil(dur2 * 100 / f);
                if (opts.auto.progress) {
                    tmrs.progress = setInterval(function () {
                        var a = getTime() - tmrs.startTime + tmrs.timePassed,
                            perc = Math.ceil(a * 100 / f);
                        opts.auto.progress.updater.call(opts.auto.progress.bar[0], perc)
                    }, opts.auto.progress.interval)
                }
                tmrs.auto = setTimeout(function () {
                    if (opts.auto.progress) {
                        opts.auto.progress.updater.call(opts.auto.progress.bar[0], 100)
                    }
                    if (opts.auto.onTimeoutEnd) {
                        opts.auto.onTimeoutEnd.call($tt0, perc, dur2)
                    }
                    if (z.isScrolling) {
                        y.trigger(cf_e('play', conf), b)
                    } else {
                        y.trigger(cf_e(b, conf), opts.auto)
                    }
                }, dur2);
                if (opts.auto.onTimeoutStart) {
                    opts.auto.onTimeoutStart.call($tt0, perc, dur2)
                }
                return true
            });
            y.bind(cf_e('resume', conf), function (e) {
                e.stopPropagation();
                if (scrl.isStopped) {
                    scrl.isStopped = false;
                    z.isPaused = false;
                    z.isScrolling = true;
                    scrl.startTime = getTime();
                    sc_startScroll(scrl)
                } else {
                    y.trigger(cf_e('play', conf))
                }
                return true
            });
            y.bind(cf_e('prev', conf) + ' ' + cf_e('next', conf), function (e, b, f, g, h) {
                e.stopPropagation();
                if (z.isStopped || y.is(':hidden')) {
                    e.stopImmediatePropagation();
                    return debug(conf, 'Carousel stopped or hidden: Not scrolling.')
                }
                var i = (is_number(opts.items.minimum)) ? opts.items.minimum : opts.items.visible + 1;
                if (i > itms.total) {
                    e.stopImmediatePropagation();
                    return debug(conf, 'Not enough items (' + itms.total + ' total, ' + i + ' needed): Not scrolling.')
                }
                var v = [b, f, g, h],
                    t = ['object', 'number/string', 'function', 'boolean'],
                    a = cf_sortParams(v, t);
                b = a[0];
                f = a[1];
                g = a[2];
                h = a[3];
                var k = e.type.slice(conf.events.prefix.length);
                if (!is_object(b)) {
                    b = {}
                }
                if (is_function(g)) {
                    b.onAfter = g
                }
                if (is_boolean(h)) {
                    b.queue = h
                }
                b = $.extend(true, {}, opts[k], b);
                if (b.conditions && !b.conditions.call($tt0, k)) {
                    e.stopImmediatePropagation();
                    return debug(conf, 'Callback "conditions" returned false.')
                }
                if (!is_number(f)) {
                    if (opts.items.filter != '*') {
                        f = 'visible'
                    } else {
                        var m = [f, b.items, opts[k].items];
                        for (var a = 0, l = m.length; a < l; a++) {
                            if (is_number(m[a]) || m[a] == 'page' || m[a] == 'visible') {
                                f = m[a];
                                break
                            }
                        }
                    }
                    switch (f) {
                    case 'page':
                        e.stopImmediatePropagation();
                        return y.triggerHandler(cf_e(k + 'Page', conf), [b, g]);
                        break;
                    case 'visible':
                        if (!opts.items.visibleConf.variable && opts.items.filter == '*') {
                            f = opts.items.visible
                        }
                        break
                    }
                }
                if (scrl.isStopped) {
                    y.trigger(cf_e('resume', conf));
                    y.trigger(cf_e('queue', conf), [k, [b, f, g]]);
                    e.stopImmediatePropagation();
                    return debug(conf, 'Carousel resumed scrolling.')
                }
                if (b.duration > 0) {
                    if (z.isScrolling) {
                        if (b.queue) {
                            y.trigger(cf_e('queue', conf), [k, [b, f, g]])
                        }
                        e.stopImmediatePropagation();
                        return debug(conf, 'Carousel currently scrolling.')
                    }
                }
                tmrs.timePassed = 0;
                y.trigger(cf_e('slide_' + k, conf), [b, f]);
                if (opts.synchronise) {
                    var s = opts.synchronise,
                        c = [b, f];
                    for (var j = 0, l = s.length; j < l; j++) {
                        var d = k;
                        if (!s[j][2]) {
                            d = (d == 'prev') ? 'next' : 'prev'
                        }
                        if (!s[j][1]) {
                            c[0] = s[j][0].triggerHandler('_cfs_triggerEvent', ['configuration', d])
                        }
                        c[1] = f + s[j][3];
                        s[j][0].trigger('_cfs_triggerEvent', ['slide_' + d, c])
                    }
                }
                return true
            });
            y.bind(cf_e('slide_prev', conf), function (e, b, c) {
                e.stopPropagation();
                var d = y.children();
                if (!opts.circular) {
                    if (itms.first == 0) {
                        if (opts.infinite) {
                            y.trigger(cf_e('next', conf), itms.total - 1)
                        }
                        return e.stopImmediatePropagation()
                    }
                }
                sz_resetMargin(d, opts);
                if (!is_number(c)) {
                    if (opts.items.visibleConf.variable) {
                        c = gn_getVisibleItemsPrev(d, opts, itms.total - 1)
                    } else if (opts.items.filter != '*') {
                        var f = (is_number(b.items)) ? b.items : gn_getVisibleOrg(y, opts);
                        c = gn_getScrollItemsPrevFilter(d, opts, itms.total - 1, f)
                    } else {
                        c = opts.items.visible
                    }
                    c = cf_getAdjust(c, opts, b.items, $tt0)
                }
                if (!opts.circular) {
                    if (itms.total - c < itms.first) {
                        c = itms.total - itms.first
                    }
                }
                opts.items.visibleConf.old = opts.items.visible;
                if (opts.items.visibleConf.variable) {
                    var g = cf_getItemsAdjust(gn_getVisibleItemsNext(d, opts, itms.total - c), opts, opts.items.visibleConf.adjust, $tt0);
                    if (opts.items.visible + c <= g && c < itms.total) {
                        c++;
                        g = cf_getItemsAdjust(gn_getVisibleItemsNext(d, opts, itms.total - c), opts, opts.items.visibleConf.adjust, $tt0)
                    }
                    opts.items.visible = g
                } else if (opts.items.filter != '*') {
                    var g = gn_getVisibleItemsNextFilter(d, opts, itms.total - c);
                    opts.items.visible = cf_getItemsAdjust(g, opts, opts.items.visibleConf.adjust, $tt0)
                }
                sz_resetMargin(d, opts, true);
                if (c == 0) {
                    e.stopImmediatePropagation();
                    return debug(conf, '0 items to scroll: Not scrolling.')
                }
                debug(conf, 'Scrolling ' + c + ' items backward.');
                itms.first += c;
                while (itms.first >= itms.total) {
                    itms.first -= itms.total
                }
                if (!opts.circular) {
                    if (itms.first == 0 && b.onEnd) {
                        b.onEnd.call($tt0, 'prev')
                    }
                    if (!opts.infinite) {
                        nv_enableNavi(opts, itms.first, conf)
                    }
                }
                y.children().slice(itms.total - c, itms.total).prependTo(y);
                if (itms.total < opts.items.visible + c) {
                    y.children().slice(0, (opts.items.visible + c) - itms.total).clone(true).appendTo(y)
                }
                var d = y.children(),
                    i_old = gi_getOldItemsPrev(d, opts, c),
                    i_new = gi_getNewItemsPrev(d, opts),
                    i_cur_l = d.eq(c - 1),
                    i_old_l = i_old.last(),
                    i_new_l = i_new.last();
                sz_resetMargin(d, opts);
                var h = 0,
                    pR = 0;
                if (opts.align) {
                    var p = cf_getAlignPadding(i_new, opts);
                    h = p[0];
                    pR = p[1]
                }
                var i = (h < 0) ? opts.padding[opts.d[3]] : 0;
                var j = false,
                    i_skp = $();
                if (opts.items.visible < c) {
                    i_skp = d.slice(opts.items.visibleConf.old, c);
                    if (b.fx == 'directscroll') {
                        var k = opts.items[opts.d['width']];
                        j = i_skp;
                        i_cur_l = i_new_l;
                        sc_hideHiddenItems(j);
                        opts.items[opts.d['width']] = 'variable'
                    }
                }
                var l = false,
                    i_siz = ms_getTotalSize(d.slice(0, c), opts, 'width'),
                    w_siz = cf_mapWrapperSizes(ms_getSizes(i_new, opts, true), opts, !opts.usePadding),
                    i_siz_vis = 0,
                    a_cfs = {}, a_wsz = {}, a_cur = {}, a_old = {}, a_new = {}, a_lef = {}, a_lef_vis = {}, a_dur = sc_getDuration(b, opts, c, i_siz);
                switch (b.fx) {
                case 'cover':
                case 'cover-fade':
                    i_siz_vis = ms_getTotalSize(d.slice(0, opts.items.visible), opts, 'width');
                    break
                }
                if (j) {
                    opts.items[opts.d['width']] = k
                }
                sz_resetMargin(d, opts, true);
                if (pR >= 0) {
                    sz_resetMargin(i_old_l, opts, opts.padding[opts.d[1]])
                }
                if (h >= 0) {
                    sz_resetMargin(i_cur_l, opts, opts.padding[opts.d[3]])
                }
                if (opts.align) {
                    opts.padding[opts.d[1]] = pR;
                    opts.padding[opts.d[3]] = h
                }
                a_lef[opts.d['left']] = -(i_siz - i);
                a_lef_vis[opts.d['left']] = -(i_siz_vis - i);
                a_wsz[opts.d['left']] = w_siz[opts.d['width']];
                var m = function () {}, _a_wrapper = function () {}, _s_paddingold = function () {}, _a_paddingold = function () {}, _s_paddingnew = function () {}, _a_paddingnew = function () {}, _s_paddingcur = function () {}, _a_paddingcur = function () {}, _onafter = function () {}, _moveitems = function () {}, _position = function () {};
                switch (b.fx) {
                case 'crossfade':
                case 'cover':
                case 'cover-fade':
                case 'uncover':
                case 'uncover-fade':
                    l = y.clone(true).appendTo($wrp);
                    break
                }
                switch (b.fx) {
                case 'crossfade':
                case 'uncover':
                case 'uncover-fade':
                    l.children().slice(0, c).remove();
                    l.children().slice(opts.items.visibleConf.old).remove();
                    break;
                case 'cover':
                case 'cover-fade':
                    l.children().slice(opts.items.visible).remove();
                    l.css(a_lef_vis);
                    break
                }
                y.css(a_lef);
                scrl = sc_setScroll(a_dur, b.easing);
                a_cfs[opts.d['left']] = (opts.usePadding) ? opts.padding[opts.d[3]] : 0;
                if (opts[opts.d['width']] == 'variable' || opts[opts.d['height']] == 'variable') {
                    m = function () {
                        $wrp.css(w_siz)
                    };
                    _a_wrapper = function () {
                        scrl.anims.push([$wrp, w_siz])
                    }
                }
                if (opts.usePadding) {
                    if (i_new_l.not(i_cur_l).length) {
                        a_cur[opts.d['marginRight']] = i_cur_l.data('_cfs_origCssMargin');
                        if (h < 0) {
                            i_cur_l.css(a_cur)
                        } else {
                            _s_paddingcur = function () {
                                i_cur_l.css(a_cur)
                            };
                            _a_paddingcur = function () {
                                scrl.anims.push([i_cur_l, a_cur])
                            }
                        }
                    }
                    switch (b.fx) {
                    case 'cover':
                    case 'cover-fade':
                        l.children().eq(c - 1).css(a_cur);
                        break
                    }
                    if (i_new_l.not(i_old_l).length) {
                        a_old[opts.d['marginRight']] = i_old_l.data('_cfs_origCssMargin');
                        _s_paddingold = function () {
                            i_old_l.css(a_old)
                        };
                        _a_paddingold = function () {
                            scrl.anims.push([i_old_l, a_old])
                        }
                    }
                    if (pR >= 0) {
                        a_new[opts.d['marginRight']] = i_new_l.data('_cfs_origCssMargin') + opts.padding[opts.d[1]];
                        _s_paddingnew = function () {
                            i_new_l.css(a_new)
                        };
                        _a_paddingnew = function () {
                            scrl.anims.push([i_new_l, a_new])
                        }
                    }
                }
                _position = function () {
                    y.css(a_cfs)
                };
                var n = opts.items.visible + c - itms.total;
                _moveitems = function () {
                    if (n > 0) {
                        y.children().slice(itms.total).remove();
                        i_old = $(y.children().slice(itms.total - (opts.items.visible - n)).get().concat(y.children().slice(0, n).get()))
                    }
                    sc_showHiddenItems(j);
                    if (opts.usePadding) {
                        var a = y.children().eq(opts.items.visible + c - 1);
                        a.css(opts.d['marginRight'], a.data('_cfs_origCssMargin'))
                    }
                };
                var o = sc_mapCallbackArguments(i_old, i_skp, i_new, c, 'prev', a_dur, w_siz);
                _onafter = function () {
                    sc_afterScroll(y, l, b);
                    z.isScrolling = false;
                    clbk.onAfter = sc_fireCallbacks($tt0, b, 'onAfter', o, clbk);
                    queu = sc_fireQueue(y, queu, conf);
                    if (!z.isPaused) {
                        y.trigger(cf_e('play', conf))
                    }
                };
                z.isScrolling = true;
                tmrs = sc_clearTimers(tmrs);
                clbk.onBefore = sc_fireCallbacks($tt0, b, 'onBefore', o, clbk);
                switch (b.fx) {
                case 'none':
                    y.css(a_cfs);
                    m();
                    _s_paddingold();
                    _s_paddingnew();
                    _s_paddingcur();
                    _position();
                    _moveitems();
                    _onafter();
                    break;
                case 'fade':
                    scrl.anims.push([y, {
                            'opacity': 0
                        },
                        function () {
                            m();
                            _s_paddingold();
                            _s_paddingnew();
                            _s_paddingcur();
                            _position();
                            _moveitems();
                            scrl = sc_setScroll(a_dur, b.easing);
                            scrl.anims.push([y, {
                                    'opacity': 1
                                },
                                _onafter
                            ]);
                            sc_startScroll(scrl)
                        }
                    ]);
                    break;
                case 'crossfade':
                    y.css({
                        'opacity': 0
                    });
                    scrl.anims.push([l, {
                        'opacity': 0
                    }]);
                    scrl.anims.push([y, {
                            'opacity': 1
                        },
                        _onafter
                    ]);
                    _a_wrapper();
                    _s_paddingold();
                    _s_paddingnew();
                    _s_paddingcur();
                    _position();
                    _moveitems();
                    break;
                case 'cover':
                    scrl.anims.push([l, a_cfs,
                        function () {
                            _s_paddingold();
                            _s_paddingnew();
                            _s_paddingcur();
                            _position();
                            _moveitems();
                            _onafter()
                        }
                    ]);
                    _a_wrapper();
                    break;
                case 'cover-fade':
                    scrl.anims.push([y, {
                        'opacity': 0
                    }]);
                    scrl.anims.push([l, a_cfs,
                        function () {
                            y.css({
                                'opacity': 1
                            });
                            _s_paddingold();
                            _s_paddingnew();
                            _s_paddingcur();
                            _position();
                            _moveitems();
                            _onafter()
                        }
                    ]);
                    _a_wrapper();
                    break;
                case 'uncover':
                    scrl.anims.push([l, a_wsz, _onafter]);
                    _a_wrapper();
                    _s_paddingold();
                    _s_paddingnew();
                    _s_paddingcur();
                    _position();
                    _moveitems();
                    break;
                case 'uncover-fade':
                    y.css({
                        'opacity': 0
                    });
                    scrl.anims.push([y, {
                        'opacity': 1
                    }]);
                    scrl.anims.push([l, a_wsz, _onafter]);
                    _a_wrapper();
                    _s_paddingold();
                    _s_paddingnew();
                    _s_paddingcur();
                    _position();
                    _moveitems();
                    break;
                default:
                    scrl.anims.push([y, a_cfs,
                        function () {
                            _moveitems();
                            _onafter()
                        }
                    ]);
                    _a_wrapper();
                    _a_paddingold();
                    _a_paddingnew();
                    _a_paddingcur();
                    break
                }
                sc_startScroll(scrl);
                cf_setCookie(opts.cookie, y, conf);
                y.trigger(cf_e('updatePageStatus', conf), [false, w_siz]);
                return true
            });
            y.bind(cf_e('slide_next', conf), function (e, c, d) {
                e.stopPropagation();
                var f = y.children();
                if (!opts.circular) {
                    if (itms.first == opts.items.visible) {
                        if (opts.infinite) {
                            y.trigger(cf_e('prev', conf), itms.total - 1)
                        }
                        return e.stopImmediatePropagation()
                    }
                }
                sz_resetMargin(f, opts);
                if (!is_number(d)) {
                    if (opts.items.filter != '*') {
                        var g = (is_number(c.items)) ? c.items : gn_getVisibleOrg(y, opts);
                        d = gn_getScrollItemsNextFilter(f, opts, 0, g)
                    } else {
                        d = opts.items.visible
                    }
                    d = cf_getAdjust(d, opts, c.items, $tt0)
                }
                var h = (itms.first == 0) ? itms.total : itms.first;
                if (!opts.circular) {
                    if (opts.items.visibleConf.variable) {
                        var i = gn_getVisibleItemsNext(f, opts, d),
                            g = gn_getVisibleItemsPrev(f, opts, h - 1)
                    } else {
                        var i = opts.items.visible,
                            g = opts.items.visible
                    } if (d + i > h) {
                        d = h - g
                    }
                }
                opts.items.visibleConf.old = opts.items.visible;
                if (opts.items.visibleConf.variable) {
                    var i = cf_getItemsAdjust(gn_getVisibleItemsNextTestCircular(f, opts, d, h), opts, opts.items.visibleConf.adjust, $tt0);
                    while (opts.items.visible - d >= i && d < itms.total) {
                        d++;
                        i = cf_getItemsAdjust(gn_getVisibleItemsNextTestCircular(f, opts, d, h), opts, opts.items.visibleConf.adjust, $tt0)
                    }
                    opts.items.visible = i
                } else if (opts.items.filter != '*') {
                    var i = gn_getVisibleItemsNextFilter(f, opts, d);
                    opts.items.visible = cf_getItemsAdjust(i, opts, opts.items.visibleConf.adjust, $tt0)
                }
                sz_resetMargin(f, opts, true);
                if (d == 0) {
                    e.stopImmediatePropagation();
                    return debug(conf, '0 items to scroll: Not scrolling.')
                }
                debug(conf, 'Scrolling ' + d + ' items forward.');
                itms.first -= d;
                while (itms.first < 0) {
                    itms.first += itms.total
                }
                if (!opts.circular) {
                    if (itms.first == opts.items.visible && c.onEnd) {
                        c.onEnd.call($tt0, 'next')
                    }
                    if (!opts.infinite) {
                        nv_enableNavi(opts, itms.first, conf)
                    }
                }
                if (itms.total < opts.items.visible + d) {
                    y.children().slice(0, (opts.items.visible + d) - itms.total).clone(true).appendTo(y)
                }
                var f = y.children(),
                    i_old = gi_getOldItemsNext(f, opts),
                    i_new = gi_getNewItemsNext(f, opts, d),
                    i_cur_l = f.eq(d - 1),
                    i_old_l = i_old.last(),
                    i_new_l = i_new.last();
                sz_resetMargin(f, opts);
                var j = 0,
                    pR = 0;
                if (opts.align) {
                    var p = cf_getAlignPadding(i_new, opts);
                    j = p[0];
                    pR = p[1]
                }
                var k = false,
                    i_skp = $();
                if (opts.items.visibleConf.old < d) {
                    i_skp = f.slice(opts.items.visibleConf.old, d);
                    if (c.fx == 'directscroll') {
                        var l = opts.items[opts.d['width']];
                        k = i_skp;
                        i_cur_l = i_old_l;
                        sc_hideHiddenItems(k);
                        opts.items[opts.d['width']] = 'variable'
                    }
                }
                var m = false,
                    i_siz = ms_getTotalSize(f.slice(0, d), opts, 'width'),
                    w_siz = cf_mapWrapperSizes(ms_getSizes(i_new, opts, true), opts, !opts.usePadding),
                    i_siz_vis = 0,
                    a_cfs = {}, a_cfs_vis = {}, a_cur = {}, a_old = {}, a_lef = {}, a_dur = sc_getDuration(c, opts, d, i_siz);
                switch (c.fx) {
                case 'uncover':
                case 'uncover-fade':
                    i_siz_vis = ms_getTotalSize(f.slice(0, opts.items.visibleConf.old), opts, 'width');
                    break
                }
                if (k) {
                    opts.items[opts.d['width']] = l
                }
                if (opts.align) {
                    if (opts.padding[opts.d[1]] < 0) {
                        opts.padding[opts.d[1]] = 0
                    }
                }
                sz_resetMargin(f, opts, true);
                sz_resetMargin(i_old_l, opts, opts.padding[opts.d[1]]);
                if (opts.align) {
                    opts.padding[opts.d[1]] = pR;
                    opts.padding[opts.d[3]] = j
                }
                a_lef[opts.d['left']] = (opts.usePadding) ? opts.padding[opts.d[3]] : 0;
                var n = function () {}, _a_wrapper = function () {}, _s_paddingold = function () {}, _a_paddingold = function () {}, _s_paddingcur = function () {}, _a_paddingcur = function () {}, _onafter = function () {}, _moveitems = function () {}, _position = function () {};
                switch (c.fx) {
                case 'crossfade':
                case 'cover':
                case 'cover-fade':
                case 'uncover':
                case 'uncover-fade':
                    m = y.clone(true).appendTo($wrp);
                    m.children().slice(opts.items.visibleConf.old).remove();
                    break
                }
                switch (c.fx) {
                case 'crossfade':
                case 'cover':
                case 'cover-fade':
                    y.css('zIndex', 1);
                    m.css('zIndex', 0);
                    break
                }
                scrl = sc_setScroll(a_dur, c.easing);
                a_cfs[opts.d['left']] = -i_siz;
                a_cfs_vis[opts.d['left']] = -i_siz_vis;
                if (j < 0) {
                    a_cfs[opts.d['left']] += j
                }
                if (opts[opts.d['width']] == 'variable' || opts[opts.d['height']] == 'variable') {
                    n = function () {
                        $wrp.css(w_siz)
                    };
                    _a_wrapper = function () {
                        scrl.anims.push([$wrp, w_siz])
                    }
                }
                if (opts.usePadding) {
                    var o = i_new_l.data('_cfs_origCssMargin');
                    if (pR >= 0) {
                        o += opts.padding[opts.d[1]]
                    }
                    i_new_l.css(opts.d['marginRight'], o);
                    if (i_cur_l.not(i_old_l).length) {
                        a_old[opts.d['marginRight']] = i_old_l.data('_cfs_origCssMargin')
                    }
                    _s_paddingold = function () {
                        i_old_l.css(a_old)
                    };
                    _a_paddingold = function () {
                        scrl.anims.push([i_old_l, a_old])
                    };
                    var q = i_cur_l.data('_cfs_origCssMargin');
                    if (j > 0) {
                        q += opts.padding[opts.d[3]]
                    }
                    a_cur[opts.d['marginRight']] = q;
                    _s_paddingcur = function () {
                        i_cur_l.css(a_cur)
                    };
                    _a_paddingcur = function () {
                        scrl.anims.push([i_cur_l, a_cur])
                    }
                }
                _position = function () {
                    y.css(a_lef)
                };
                var r = opts.items.visible + d - itms.total;
                _moveitems = function () {
                    if (r > 0) {
                        y.children().slice(itms.total).remove()
                    }
                    var a = y.children().slice(0, d).appendTo(y).last();
                    if (r > 0) {
                        i_new = gi_getCurrentItems(f, opts)
                    }
                    sc_showHiddenItems(k);
                    if (opts.usePadding) {
                        if (itms.total < opts.items.visible + d) {
                            var b = y.children().eq(opts.items.visible - 1);
                            b.css(opts.d['marginRight'], b.data('_cfs_origCssMargin') + opts.padding[opts.d[3]])
                        }
                        a.css(opts.d['marginRight'], a.data('_cfs_origCssMargin'))
                    }
                };
                var s = sc_mapCallbackArguments(i_old, i_skp, i_new, d, 'next', a_dur, w_siz);
                _onafter = function () {
                    y.css('zIndex', y.data('_cfs_origCss').zIndex);
                    sc_afterScroll(y, m, c);
                    z.isScrolling = false;
                    clbk.onAfter = sc_fireCallbacks($tt0, c, 'onAfter', s, clbk);
                    queu = sc_fireQueue(y, queu, conf);
                    if (!z.isPaused) {
                        y.trigger(cf_e('play', conf))
                    }
                };
                z.isScrolling = true;
                tmrs = sc_clearTimers(tmrs);
                clbk.onBefore = sc_fireCallbacks($tt0, c, 'onBefore', s, clbk);
                switch (c.fx) {
                case 'none':
                    y.css(a_cfs);
                    n();
                    _s_paddingold();
                    _s_paddingcur();
                    _position();
                    _moveitems();
                    _onafter();
                    break;
                case 'fade':
                    scrl.anims.push([y, {
                            'opacity': 0
                        },
                        function () {
                            n();
                            _s_paddingold();
                            _s_paddingcur();
                            _position();
                            _moveitems();
                            scrl = sc_setScroll(a_dur, c.easing);
                            scrl.anims.push([y, {
                                    'opacity': 1
                                },
                                _onafter
                            ]);
                            sc_startScroll(scrl)
                        }
                    ]);
                    break;
                case 'crossfade':
                    y.css({
                        'opacity': 0
                    });
                    scrl.anims.push([m, {
                        'opacity': 0
                    }]);
                    scrl.anims.push([y, {
                            'opacity': 1
                        },
                        _onafter
                    ]);
                    _a_wrapper();
                    _s_paddingold();
                    _s_paddingcur();
                    _position();
                    _moveitems();
                    break;
                case 'cover':
                    y.css(opts.d['left'], $wrp[opts.d['width']]());
                    scrl.anims.push([y, a_lef, _onafter]);
                    _a_wrapper();
                    _s_paddingold();
                    _s_paddingcur();
                    _moveitems();
                    break;
                case 'cover-fade':
                    y.css(opts.d['left'], $wrp[opts.d['width']]());
                    scrl.anims.push([m, {
                        'opacity': 0
                    }]);
                    scrl.anims.push([y, a_lef, _onafter]);
                    _a_wrapper();
                    _s_paddingold();
                    _s_paddingcur();
                    _moveitems();
                    break;
                case 'uncover':
                    scrl.anims.push([m, a_cfs_vis, _onafter]);
                    _a_wrapper();
                    _s_paddingold();
                    _s_paddingcur();
                    _position();
                    _moveitems();
                    break;
                case 'uncover-fade':
                    y.css({
                        'opacity': 0
                    });
                    scrl.anims.push([y, {
                        'opacity': 1
                    }]);
                    scrl.anims.push([m, a_cfs_vis, _onafter]);
                    _a_wrapper();
                    _s_paddingold();
                    _s_paddingcur();
                    _position();
                    _moveitems();
                    break;
                default:
                    scrl.anims.push([y, a_cfs,
                        function () {
                            _position();
                            _moveitems();
                            _onafter()
                        }
                    ]);
                    _a_wrapper();
                    _a_paddingold();
                    _a_paddingcur();
                    break
                }
                sc_startScroll(scrl);
                cf_setCookie(opts.cookie, y, conf);
                y.trigger(cf_e('updatePageStatus', conf), [false, w_siz]);
                return true
            });
            y.bind(cf_e('slideTo', conf), function (e, b, c, d, f, g, h) {
                e.stopPropagation();
                var v = [b, c, d, f, g, h],
                    t = ['string/number/object', 'number', 'boolean', 'object', 'string', 'function'],
                    a = cf_sortParams(v, t);
                f = a[3];
                g = a[4];
                h = a[5];
                b = gn_getItemIndex(a[0], a[1], a[2], itms, y);
                if (b == 0) {
                    return false
                }
                if (!is_object(f)) {
                    f = false
                }
                if (z.isScrolling) {
                    if (!is_object(f) || f.duration > 0) {
                        return false
                    }
                }
                if (g != 'prev' && g != 'next') {
                    if (opts.circular) {
                        g = (b <= itms.total / 2) ? 'next' : 'prev'
                    } else {
                        g = (itms.first == 0 || itms.first > b) ? 'next' : 'prev'
                    }
                }
                if (g == 'prev') {
                    b = itms.total - b
                }
                y.trigger(cf_e(g, conf), [f, b, h]);
                return true
            });
            y.bind(cf_e('prevPage', conf), function (e, a, b) {
                e.stopPropagation();
                var c = y.triggerHandler(cf_e('currentPage', conf));
                return y.triggerHandler(cf_e('slideToPage', conf), [c - 1, a, 'prev', b])
            });
            y.bind(cf_e('nextPage', conf), function (e, a, b) {
                e.stopPropagation();
                var c = y.triggerHandler(cf_e('currentPage', conf));
                return y.triggerHandler(cf_e('slideToPage', conf), [c + 1, a, 'next', b])
            });
            y.bind(cf_e('slideToPage', conf), function (e, a, b, c, d) {
                e.stopPropagation();
                if (!is_number(a)) {
                    a = y.triggerHandler(cf_e('currentPage', conf))
                }
                var f = opts.pagination.items || opts.items.visible,
                    max = Math.ceil(itms.total / f) - 1;
                if (a < 0) {
                    a = max
                }
                if (a > max) {
                    a = 0
                }
                return y.triggerHandler(cf_e('slideTo', conf), [a * f, 0, true, b, c, d])
            });
            y.bind(cf_e('jumpToStart', conf), function (e, s) {
                e.stopPropagation();
                if (s) {
                    s = gn_getItemIndex(s, 0, true, itms, y)
                } else {
                    s = 0
                }
                s += itms.first;
                if (s != 0) {
                    if (items.total > 0) {
                        while (s > itms.total) {
                            s -= itms.total
                        }
                    }
                    y.prepend(y.children().slice(s, itms.total))
                }
                return true
            });
            y.bind(cf_e('synchronise', conf), function (e, s) {
                e.stopPropagation();
                if (s) {
                    s = cf_getSynchArr(s)
                } else if (opts.synchronise) {
                    s = opts.synchronise
                } else {
                    return debug(conf, 'No carousel to synchronise.')
                }
                var n = y.triggerHandler(cf_e('currentPosition', conf)),
                    x = true;
                for (var j = 0, l = s.length; j < l; j++) {
                    if (!s[j][0].triggerHandler(cf_e('slideTo', conf), [n, s[j][3], true])) {
                        x = false
                    }
                }
                return x
            });
            y.bind(cf_e('queue', conf), function (e, a, b) {
                e.stopPropagation();
                if (is_function(a)) {
                    a.call($tt0, queu)
                } else if (is_array(a)) {
                    queu = a
                } else if (!is_undefined(a)) {
                    queu.push([a, b])
                }
                return queu
            });
            y.bind(cf_e('insertItem', conf), function (e, b, c, d, f) {
                e.stopPropagation();
                var v = [b, c, d, f],
                    t = ['string/object', 'string/number/object', 'boolean', 'number'],
                    a = cf_sortParams(v, t);
                b = a[0];
                c = a[1];
                d = a[2];
                f = a[3];
                if (is_object(b) && !is_jquery(b)) {
                    b = $(b)
                } else if (is_string(b)) {
                    b = $(b)
                }
                if (!is_jquery(b) || b.length == 0) {
                    return debug(conf, 'Not a valid object.')
                }
                if (is_undefined(c)) {
                    c = 'end'
                }
                sz_storeMargin(b, opts);
                sz_storeSizes(b, opts);
                var g = c,
                    before = 'before';
                if (c == 'end') {
                    if (d) {
                        if (itms.first == 0) {
                            c = itms.total - 1;
                            before = 'after'
                        } else {
                            c = itms.first;
                            itms.first += b.length
                        } if (c < 0) {
                            c = 0
                        }
                    } else {
                        c = itms.total - 1;
                        before = 'after'
                    }
                } else {
                    c = gn_getItemIndex(c, f, d, itms, y)
                } if (g != 'end' && !d) {
                    if (c < itms.first) {
                        itms.first += b.length
                    }
                }
                if (itms.first >= itms.total) {
                    itms.first -= itms.total
                }
                var h = y.children().eq(c);
                if (h.length) {
                    h[before](b)
                } else {
                    y.append(b)
                }
                itms.total = y.children().length;
                y.trigger(cf_e('updateSizes', conf));
                y.trigger(cf_e('linkAnchors', conf));
                return true
            });
            y.bind(cf_e('removeItem', conf), function (e, c, d, f) {
                e.stopPropagation();
                var v = [c, d, f],
                    t = ['string/number/object', 'boolean', 'number'],
                    a = cf_sortParams(v, t);
                c = a[0];
                d = a[1];
                f = a[2];
                var g = false;
                if (c instanceof $ && c.length > 1) {
                    h = $();
                    c.each(function (i, a) {
                        var b = y.trigger(cf_e('removeItem', conf), [$(this), d, f]);
                        if (b) h = h.add(b)
                    });
                    return h
                }
                if (is_undefined(c) || c == 'end') {
                    h = y.children().last()
                } else {
                    c = gn_getItemIndex(c, f, d, itms, y);
                    var h = y.children().eq(c);
                    if (h.length) {
                        if (c < itms.first) itms.first -= h.length
                    }
                } if (h && h.length) {
                    h.detach();
                    itms.total = y.children().length;
                    y.trigger(cf_e('updateSizes', conf))
                }
                return h
            });
            y.bind(cf_e('onBefore', conf) + ' ' + cf_e('onAfter', conf), function (e, a) {
                e.stopPropagation();
                var b = e.type.slice(conf.events.prefix.length);
                if (is_array(a)) {
                    clbk[b] = a
                }
                if (is_function(a)) {
                    clbk[b].push(a)
                }
                return clbk[b]
            });
            y.bind(cf_e('currentPosition', conf), function (e, a) {
                e.stopPropagation();
                if (itms.first == 0) {
                    var b = 0
                } else {
                    var b = itms.total - itms.first
                } if (is_function(a)) {
                    a.call($tt0, b)
                }
                return b
            });
            y.bind(cf_e('currentPage', conf), function (e, a) {
                e.stopPropagation();
                var b = opts.pagination.items || opts.items.visible,
                    max = Math.ceil(itms.total / b - 1),
                    nr;
                if (itms.first == 0) {
                    nr = 0
                } else if (itms.first < itms.total % b) {
                    nr = 0
                } else if (itms.first == b && !opts.circular) {
                    nr = max
                } else {
                    nr = Math.round((itms.total - itms.first) / b)
                } if (nr < 0) {
                    nr = 0
                }
                if (nr > max) {
                    nr = max
                }
                if (is_function(a)) {
                    a.call($tt0, nr)
                }
                return nr
            });
            y.bind(cf_e('currentVisible', conf), function (e, a) {
                e.stopPropagation();
                var b = gi_getCurrentItems(y.children(), opts);
                if (is_function(a)) {
                    a.call($tt0, b)
                }
                return b
            });
            y.bind(cf_e('slice', conf), function (e, f, l, b) {
                e.stopPropagation();
                if (itms.total == 0) {
                    return false
                }
                var v = [f, l, b],
                    t = ['number', 'number', 'function'],
                    a = cf_sortParams(v, t);
                f = (is_number(a[0])) ? a[0] : 0;
                l = (is_number(a[1])) ? a[1] : itms.total;
                b = a[2];
                f += itms.first;
                l += itms.first;
                if (items.total > 0) {
                    while (f > itms.total) {
                        f -= itms.total
                    }
                    while (l > itms.total) {
                        l -= itms.total
                    }
                    while (f < 0) {
                        f += itms.total
                    }
                    while (l < 0) {
                        l += itms.total
                    }
                }
                var c = y.children(),
                    $i;
                if (l > f) {
                    $i = c.slice(f, l)
                } else {
                    $i = $(c.slice(f, itms.total).get().concat(c.slice(0, l).get()))
                } if (is_function(b)) {
                    b.call($tt0, $i)
                }
                return $i
            });
            y.bind(cf_e('isPaused', conf) + ' ' + cf_e('isStopped', conf) + ' ' + cf_e('isScrolling', conf), function (e, a) {
                e.stopPropagation();
                var b = e.type.slice(conf.events.prefix.length),
                    value = z[b];
                if (is_function(a)) {
                    a.call($tt0, value)
                }
                return value
            });
            y.bind(cf_e('configuration', conf), function (e, a, b, c) {
                e.stopPropagation();
                var d = false;
                if (is_function(a)) {
                    a.call($tt0, opts)
                } else if (is_object(a)) {
                    opts_orig = $.extend(true, {}, opts_orig, a);
                    if (b !== false) d = true;
                    else opts = $.extend(true, {}, opts, a)
                } else if (!is_undefined(a)) {
                    if (is_function(b)) {
                        var f = eval('opts.' + a);
                        if (is_undefined(f)) {
                            f = ''
                        }
                        b.call($tt0, f)
                    } else if (!is_undefined(b)) {
                        if (typeof c !== 'boolean') c = true;
                        eval('opts_orig.' + a + ' = b');
                        if (c !== false) d = true;
                        else eval('opts.' + a + ' = b')
                    } else {
                        return eval('opts.' + a)
                    }
                }
                if (d) {
                    sz_resetMargin(y.children(), opts);
                    y._cfs_init(opts_orig);
                    y._cfs_bind_buttons();
                    var g = sz_setSizes(y, opts);
                    y.trigger(cf_e('updatePageStatus', conf), [true, g])
                }
                return opts
            });
            y.bind(cf_e('linkAnchors', conf), function (e, a, b) {
                e.stopPropagation();
                if (is_undefined(a)) {
                    a = $('body')
                } else if (is_string(a)) {
                    a = $(a)
                }
                if (!is_jquery(a) || a.length == 0) {
                    return debug(conf, 'Not a valid object.')
                }
                if (!is_string(b)) {
                    b = 'a.caroufredsel'
                }
                a.find(b).each(function () {
                    var h = this.hash || '';
                    if (h.length > 0 && y.children().index($(h)) != -1) {
                        $(this).unbind('click').click(function (e) {
                            e.preventDefault();
                            y.trigger(cf_e('slideTo', conf), h)
                        })
                    }
                });
                return true
            });
            y.bind(cf_e('updatePageStatus', conf), function (e, b, c) {
                e.stopPropagation();
                if (!opts.pagination.container) {
                    return
                }
                var d = opts.pagination.items || opts.items.visible,
                    pgs = Math.ceil(itms.total / d);
                if (b) {
                    if (opts.pagination.anchorBuilder) {
                        opts.pagination.container.children().remove();
                        opts.pagination.container.each(function () {
                            for (var a = 0; a < pgs; a++) {
                                var i = y.children().eq(gn_getItemIndex(a * d, 0, true, itms, y));
                                $(this).append(opts.pagination.anchorBuilder.call(i[0], a + 1))
                            }
                        })
                    }
                    opts.pagination.container.each(function () {
                        $(this).children().unbind(opts.pagination.event).each(function (a) {
                            $(this).bind(opts.pagination.event, function (e) {
                                e.preventDefault();
                                y.trigger(cf_e('slideTo', conf), [a * d, -opts.pagination.deviation, true, opts.pagination])
                            })
                        })
                    })
                }
                var f = y.triggerHandler(cf_e('currentPage', conf)) + opts.pagination.deviation;
                if (f >= pgs) {
                    f = 0
                }
                if (f < 0) {
                    f = pgs - 1
                }
                opts.pagination.container.each(function () {
                    $(this).children().removeClass(cf_c('now', conf)).eq(f).addClass(cf_c('now', conf))
                });
                return true
            });
            y.bind(cf_e('updateSizes', conf), function (e) {
                var a = opts.items.visible,
                    a_itm = y.children(),
                    avail_primary = ms_getParentSize($wrp, opts, 'width');
                itms.total = a_itm.length;
                opts.maxDimension = ms_getMaxDimension(opts, avail_primary);
                if (z.primarySizePercentage) {
                    opts[opts.d['width']] = ms_getPercentage(avail_primary, z.primarySizePercentage)
                }
                if (opts.responsive) {
                    opts.items.width = opts.items.sizesConf.width;
                    opts.items.height = opts.items.sizesConf.height;
                    opts = in_getResponsiveValues(opts, a_itm, avail_primary);
                    a = opts.items.visible;
                    sz_setResponsiveSizes(opts, a_itm)
                } else if (opts.items.visibleConf.variable) {
                    a = gn_getVisibleItemsNext(a_itm, opts, 0)
                } else if (opts.items.filter != '*') {
                    a = gn_getVisibleItemsNextFilter(a_itm, opts, 0)
                }
                if (!opts.circular && itms.first != 0 && a > itms.first) {
                    if (opts.items.visibleConf.variable) {
                        var b = gn_getVisibleItemsPrev(a_itm, opts, itms.first) - itms.first
                    } else if (opts.items.filter != '*') {
                        var b = gn_getVisibleItemsPrevFilter(a_itm, opts, itms.first) - itms.first
                    } else {
                        var b = opts.items.visible - itms.first
                    }
                    debug(conf, 'Preventing non-circular: sliding ' + b + ' items backward.');
                    y.trigger(cf_e('prev', conf), b)
                }
                opts.items.visible = cf_getItemsAdjust(a, opts, opts.items.visibleConf.adjust, $tt0);
                opts.items.visibleConf.old = opts.items.visible;
                opts = in_getAlignPadding(opts, a_itm);
                var c = sz_setSizes(y, opts);
                y.trigger(cf_e('updatePageStatus', conf), [true, c]);
                nv_showNavi(opts, itms.total, conf);
                nv_enableNavi(opts, itms.first, conf);
                return c
            });
            y.bind(cf_e('destroy', conf), function (e, a) {
                e.stopPropagation();
                tmrs = sc_clearTimers(tmrs);
                y.data('_cfs_isCarousel', false);
                y.trigger(cf_e('finish', conf));
                if (a) {
                    y.trigger(cf_e('jumpToStart', conf))
                }
                sz_resetMargin(y.children(), opts);
                if (opts.responsive) {
                    y.children().each(function () {
                        $(this).css($(this).data('_cfs_origCssSizes'))
                    })
                }
                y.css(y.data('_cfs_origCss'));
                y._cfs_unbind_events();
                y._cfs_unbind_buttons();
                $wrp.replaceWith(y);
                return true
            });
            y.bind(cf_e('debug', conf), function (e) {
                debug(conf, 'Carousel width: ' + opts.width);
                debug(conf, 'Carousel height: ' + opts.height);
                debug(conf, 'Item widths: ' + opts.items.width);
                debug(conf, 'Item heights: ' + opts.items.height);
                debug(conf, 'Number of items visible: ' + opts.items.visible);
                if (opts.auto.play) {
                    debug(conf, 'Number of items scrolled automatically: ' + opts.auto.items)
                }
                if (opts.prev.button) {
                    debug(conf, 'Number of items scrolled backward: ' + opts.prev.items)
                }
                if (opts.next.button) {
                    debug(conf, 'Number of items scrolled forward: ' + opts.next.items)
                }
                return conf.debug
            });
            y.bind('_cfs_triggerEvent', function (e, n, o) {
                e.stopPropagation();
                return y.triggerHandler(cf_e(n, conf), o)
            })
        };
        y._cfs_unbind_events = function () {
            y.unbind(cf_e('', conf));
            y.unbind(cf_e('', conf, false));
            y.unbind('_cfs_triggerEvent')
        };
        y._cfs_bind_buttons = function () {
            y._cfs_unbind_buttons();
            nv_showNavi(opts, itms.total, conf);
            nv_enableNavi(opts, itms.first, conf);
            if (opts.auto.pauseOnHover) {
                var b = bt_pauseOnHoverConfig(opts.auto.pauseOnHover);
                $wrp.bind(cf_e('mouseenter', conf, false), function () {
                    y.trigger(cf_e('pause', conf), b)
                }).bind(cf_e('mouseleave', conf, false), function () {
                    y.trigger(cf_e('resume', conf))
                })
            }
            if (opts.auto.button) {
                opts.auto.button.bind(cf_e(opts.auto.event, conf, false), function (e) {
                    e.preventDefault();
                    var a = false,
                        b = null;
                    if (z.isPaused) {
                        a = 'play'
                    } else if (opts.auto.pauseOnEvent) {
                        a = 'pause';
                        b = bt_pauseOnHoverConfig(opts.auto.pauseOnEvent)
                    }
                    if (a) {
                        y.trigger(cf_e(a, conf), b)
                    }
                })
            }
            if (opts.prev.button) {
                opts.prev.button.bind(cf_e(opts.prev.event, conf, false), function (e) {
                    e.preventDefault();
                    y.trigger(cf_e('prev', conf))
                });
                if (opts.prev.pauseOnHover) {
                    var b = bt_pauseOnHoverConfig(opts.prev.pauseOnHover);
                    opts.prev.button.bind(cf_e('mouseenter', conf, false), function () {
                        y.trigger(cf_e('pause', conf), b)
                    }).bind(cf_e('mouseleave', conf, false), function () {
                        y.trigger(cf_e('resume', conf))
                    })
                }
            }
            if (opts.next.button) {
                opts.next.button.bind(cf_e(opts.next.event, conf, false), function (e) {
                    e.preventDefault();
                    y.trigger(cf_e('next', conf))
                });
                if (opts.next.pauseOnHover) {
                    var b = bt_pauseOnHoverConfig(opts.next.pauseOnHover);
                    opts.next.button.bind(cf_e('mouseenter', conf, false), function () {
                        y.trigger(cf_e('pause', conf), b)
                    }).bind(cf_e('mouseleave', conf, false), function () {
                        y.trigger(cf_e('resume', conf))
                    })
                }
            }
            if (opts.pagination.container) {
                if (opts.pagination.pauseOnHover) {
                    var b = bt_pauseOnHoverConfig(opts.pagination.pauseOnHover);
                    opts.pagination.container.bind(cf_e('mouseenter', conf, false), function () {
                        y.trigger(cf_e('pause', conf), b)
                    }).bind(cf_e('mouseleave', conf, false), function () {
                        y.trigger(cf_e('resume', conf))
                    })
                }
            }
            if (opts.prev.key || opts.next.key) {
                $(document).bind(cf_e('keyup', conf, false, true, true), function (e) {
                    var k = e.keyCode;
                    if (k == opts.next.key) {
                        e.preventDefault();
                        y.trigger(cf_e('next', conf))
                    }
                    if (k == opts.prev.key) {
                        e.preventDefault();
                        y.trigger(cf_e('prev', conf))
                    }
                })
            }
            if (opts.pagination.keys) {
                $(document).bind(cf_e('keyup', conf, false, true, true), function (e) {
                    var k = e.keyCode;
                    if (k >= 49 && k < 58) {
                        k = (k - 49) * opts.items.visible;
                        if (k <= itms.total) {
                            e.preventDefault();
                            y.trigger(cf_e('slideTo', conf), [k, 0, true, opts.pagination])
                        }
                    }
                })
            }
            if (opts.prev.wipe || opts.next.wipe) {
                deprecated('the touchwipe-plugin', 'the touchSwipe-plugin');
                if ($.fn.touchwipe) {
                    var c = (opts.prev.wipe) ? function () {
                            y.trigger(cf_e('prev', conf))
                        } : null,
                        wN = (opts.next.wipe) ? function () {
                            y.trigger(cf_e('next', conf))
                        } : null;
                    if (wN || wN) {
                        if (!z.touchwipe) {
                            z.touchwipe = true;
                            var d = {
                                'min_move_x': 30,
                                'min_move_y': 30,
                                'preventDefaultEvents': true
                            };
                            switch (opts.direction) {
                            case 'up':
                            case 'down':
                                d.wipeUp = c;
                                d.wipeDown = wN;
                                break;
                            default:
                                d.wipeLeft = wN;
                                d.wipeRight = c
                            }
                            $wrp.touchwipe(d)
                        }
                    }
                }
            }
            if ($.fn.swipe) {
                var f = 'ontouchstart' in window;
                if ((f && opts.swipe.onTouch) || (!f && opts.swipe.onMouse)) {
                    var g = $.extend(true, {}, opts.prev, opts.swipe),
                        scN = $.extend(true, {}, opts.next, opts.swipe),
                        swP = function () {
                            y.trigger(cf_e('prev', conf), [g])
                        }, swN = function () {
                            y.trigger(cf_e('next', conf), [scN])
                        };
                    switch (opts.direction) {
                    case 'up':
                    case 'down':
                        opts.swipe.options.swipeUp = swN;
                        opts.swipe.options.swipeDown = swP;
                        break;
                    default:
                        opts.swipe.options.swipeLeft = swN;
                        opts.swipe.options.swipeRight = swP
                    }
                    if (z.swipe) {
                        y.swipe('destroy')
                    }
                    $wrp.swipe(opts.swipe.options);
                    $wrp.css('cursor', 'move');
                    z.swipe = true
                }
            }
            if ($.fn.mousewheel) {
                if (opts.prev.mousewheel) {
                    deprecated('The prev.mousewheel option', 'the mousewheel configuration object');
                    opts.prev.mousewheel = null;
                    opts.mousewheel = {
                        items: bt_mousesheelNumber(opts.prev.mousewheel)
                    }
                }
                if (opts.next.mousewheel) {
                    deprecated('The next.mousewheel option', 'the mousewheel configuration object');
                    opts.next.mousewheel = null;
                    opts.mousewheel = {
                        items: bt_mousesheelNumber(opts.next.mousewheel)
                    }
                }
                if (opts.mousewheel) {
                    var h = $.extend(true, {}, opts.prev, opts.mousewheel),
                        mcN = $.extend(true, {}, opts.next, opts.mousewheel);
                    if (z.mousewheel) {
                        $wrp.unbind(cf_e('mousewheel', conf, false))
                    }
                    $wrp.bind(cf_e('mousewheel', conf, false), function (e, a) {
                        e.preventDefault();
                        if (a > 0) {
                            y.trigger(cf_e('prev', conf), [h])
                        } else {
                            y.trigger(cf_e('next', conf), [mcN])
                        }
                    });
                    z.mousewheel = true
                }
            }
            if (opts.auto.play) {
                y.trigger(cf_e('play', conf), opts.auto.delay)
            }
            if (z.upDateOnWindowResize) {
                var i = $(window),
                    _windowWidth = 0,
                    _windowHeight = 0;
                i.bind(cf_e('resize', conf, false, true, true), function (e) {
                    var a = i.width(),
                        nh = i.height();
                    if (a != _windowWidth || nh != _windowHeight) {
                        y.trigger(cf_e('finish', conf));
                        if (opts.auto.pauseOnResize && !z.isPaused) {
                            y.trigger(cf_e('play', conf))
                        }
                        sz_resetMargin(y.children(), opts);
                        y.trigger(cf_e('updateSizes', conf));
                        _windowWidth = a;
                        _windowHeight = nh
                    }
                })
            }
        };
        y._cfs_unbind_buttons = function () {
            var a = cf_e('', conf),
                ns2 = cf_e('', conf, false);
            ns3 = cf_e('', conf, false, true, true);
            $(document).unbind(ns3);
            $(window).unbind(ns3);
            $wrp.unbind(ns2);
            if (opts.auto.button) {
                opts.auto.button.unbind(ns2)
            }
            if (opts.prev.button) {
                opts.prev.button.unbind(ns2)
            }
            if (opts.next.button) {
                opts.next.button.unbind(ns2)
            }
            if (opts.pagination.container) {
                opts.pagination.container.unbind(ns2);
                if (opts.pagination.anchorBuilder) {
                    opts.pagination.container.children().remove()
                }
            }
            if (z.swipe) {
                y.swipe('destroy');
                $wrp.css('cursor', 'default');
                z.swipe = false
            }
            if (z.mousewheel) {
                z.mousewheel = false
            }
            nv_showNavi(opts, 'hide', conf);
            nv_enableNavi(opts, 'removeClass', conf)
        };
        if (is_boolean(w)) {
            w = {
                'debug': w
            }
        }
        var z = {
            'direction': 'next',
            'isPaused': true,
            'isScrolling': false,
            'isStopped': false,
            'mousewheel': false,
            'swipe': false
        }, itms = {
                'total': y.children().length,
                'first': 0
            }, tmrs = {
                'auto': null,
                'progress': null,
                'startTime': getTime(),
                'timePassed': 0
            }, scrl = {
                'isStopped': false,
                'duration': 0,
                'startTime': 0,
                'easing': '',
                'anims': []
            }, clbk = {
                'onBefore': [],
                'onAfter': []
            }, queu = [],
            conf = $.extend(true, {}, $.fn.carouFredSel.configs, w),
            opts = {}, opts_orig = $.extend(true, {}, u),
            $wrp = y.wrap('<' + conf.wrapper.element + ' class="' + conf.wrapper.classname + '" />').parent();
        conf.selector = y.selector;
        conf.serialNumber = $.fn.carouFredSel.serialNumber++;
        y._cfs_init(opts_orig, true, starting_position);
        y._cfs_build();
        y._cfs_bind_events();
        y._cfs_bind_buttons();
        if (is_array(opts.items.start)) {
            var A = opts.items.start
        } else {
            var A = [];
            if (opts.items.start != 0) {
                A.push(opts.items.start)
            }
        } if (opts.cookie) {
            A.unshift(parseInt(cf_getCookie(opts.cookie), 10))
        }
        if (A.length > 0) {
            for (var a = 0, l = A.length; a < l; a++) {
                var s = A[a];
                if (s == 0) {
                    continue
                }
                if (s === true) {
                    s = window.location.hash;
                    if (s.length < 1) {
                        continue
                    }
                } else if (s === 'random') {
                    s = Math.floor(Math.random() * itms.total)
                }
                if (y.triggerHandler(cf_e('slideTo', conf), [s, 0, true, {
                    fx: 'none'
                }])) {
                    break
                }
            }
        }
        var B = sz_setSizes(y, opts),
            itm = gi_getCurrentItems(y.children(), opts);
        if (opts.onCreate) {
            opts.onCreate.call($tt0, {
                'width': B.width,
                'height': B.height,
                'items': itm
            })
        }
        y.trigger(cf_e('updatePageStatus', conf), [true, B]);
        y.trigger(cf_e('linkAnchors', conf));
        if (conf.debug) {
            y.trigger(cf_e('debug', conf))
        }
        return y
    };
    $.fn.carouFredSel.serialNumber = 1;
    $.fn.carouFredSel.defaults = {
        'synchronise': false,
        'infinite': true,
        'circular': true,
        'responsive': false,
        'direction': 'left',
        'items': {
            'start': 0
        },
        'scroll': {
            'easing': 'swing',
            'duration': 500,
            'pauseOnHover': false,
            'event': 'click',
            'queue': false
        }
    };
    $.fn.carouFredSel.configs = {
        'debug': false,
        'events': {
            'prefix': '',
            'namespace': 'cfs'
        },
        'wrapper': {
            'element': 'div',
            'classname': 'caroufredsel_wrapper'
        },
        'classnames': {}
    };
    $.fn.carouFredSel.pageAnchorBuilder = function (a) {
        return '<li class="now"><a href="#a'+a+'"></a></li>'
    };
    $.fn.carouFredSel.progressbarUpdater = function (a) {
        $(this).css('width', a + '%')
    };
    $.fn.carouFredSel.cookie = {
        get: function (n) {
            n += '=';
            var b = document.cookie.split(';');
            for (var a = 0, l = b.length; a < l; a++) {
                var c = b[a];
                while (c.charAt(0) == ' ') {
                    c = c.slice(1)
                }
                if (c.indexOf(n) == 0) {
                    return c.slice(n.length)
                }
            }
            return 0
        },
        set: function (n, v, d) {
            var e = "";
            if (d) {
                var a = new Date();
                a.setTime(a.getTime() + (d * 24 * 60 * 60 * 1000));
                e = "; expires=" + a.toGMTString()
            }
            document.cookie = n + '=' + v + e + '; path=/'
        },
        remove: function (n) {
            $.fn.carouFredSel.cookie.set(n, "", -1)
        }
    };

    function sc_setScroll(d, e) {
        return {
            anims: [],
            duration: d,
            orgDuration: d,
            easing: e,
            startTime: getTime()
        }
    }

    function sc_startScroll(s) {
        if (is_object(s.pre)) {
            sc_startScroll(s.pre)
        }
        for (var a = 0, l = s.anims.length; a < l; a++) {
            var b = s.anims[a];
            if (!b) {
                continue
            }
            if (b[3]) {
                b[0].stop()
            }
            b[0].animate(b[1], {
                complete: b[2],
                duration: s.duration,
                easing: s.easing
            })
        }
        if (is_object(s.post)) {
            sc_startScroll(s.post)
        }
    }

    function sc_stopScroll(s, c) {
        if (!is_boolean(c)) {
            c = true
        }
        if (is_object(s.pre)) {
            sc_stopScroll(s.pre, c)
        }
        for (var a = 0, l = s.anims.length; a < l; a++) {
            var b = s.anims[a];
            b[0].stop(true);
            if (c) {
                b[0].css(b[1]);
                if (is_function(b[2])) {
                    b[2]()
                }
            }
        }
        if (is_object(s.post)) {
            sc_stopScroll(s.post, c)
        }
    }

    function sc_afterScroll(a, b, o) {
        if (b) {
            b.remove()
        }
        switch (o.fx) {
        case 'fade':
        case 'crossfade':
        case 'cover-fade':
        case 'uncover-fade':
            a.css('filter', '');
            break
        }
    }

    function sc_fireCallbacks(d, o, b, a, c) {
        if (o[b]) {
            o[b].call(d, a)
        }
        if (c[b].length) {
            for (var i = 0, l = c[b].length; i < l; i++) {
                c[b][i].call(d, a)
            }
        }
        return []
    }

    function sc_fireQueue(a, q, c) {
        if (q.length) {
            a.trigger(cf_e(q[0][0], c), q[0][1]);
            q.shift()
        }
        return q
    }

    function sc_hideHiddenItems(b) {
        b.each(function () {
            var a = $(this);
            a.data('_cfs_isHidden', a.is(':hidden')).hide()
        })
    }

    function sc_showHiddenItems(b) {
        if (b) {
            b.each(function () {
                var a = $(this);
                if (!a.data('_cfs_isHidden')) {
                    a.show()
                }
            })
        }
    }

    function sc_clearTimers(t) {
        if (t.auto) {
            clearTimeout(t.auto)
        }
        if (t.progress) {
            clearInterval(t.progress)
        }
        return t
    }

    function sc_mapCallbackArguments(a, b, c, d, e, f, g) {
        return {
            'width': g.width,
            'height': g.height,
            'items': {
                'old': a,
                'skipped': b,
                'new': c,
                'visible': c
            },
            'scroll': {
                'items': d,
                'direction': e,
                'duration': f
            }
        }
    }

    function sc_getDuration(a, o, b, c) {
        var d = a.duration;
        if (a.fx == 'none') {
            return 0
        }
        if (d == 'auto') {
            d = o.scroll.duration / o.scroll.items * b
        } else if (d < 10) {
            d = c / d
        }
        if (d < 1) {
            return 0
        }
        if (a.fx == 'fade') {
            d = d / 2
        }
        return Math.round(d)
    }

    function nv_showNavi(o, t, c) {
        var a = (is_number(o.items.minimum)) ? o.items.minimum : o.items.visible + 1;
        if (t == 'show' || t == 'hide') {
            var f = t
        } else if (a > t) {
            debug(c, 'Not enough items (' + t + ' total, ' + a + ' needed): Hiding navigation.');
            var f = 'hide'
        } else {
            var f = 'show'
        }
        var s = (f == 'show') ? 'removeClass' : 'addClass',
            h = cf_c('hidden', c);
        if (o.auto.button) {
            o.auto.button[f]()[s](h)
        }
        if (o.prev.button) {
            o.prev.button[f]()[s](h)
        }
        if (o.next.button) {
            o.next.button[f]()[s](h)
        }
        if (o.pagination.container) {
            o.pagination.container[f]()[s](h)
        }
    }

    function nv_enableNavi(o, f, c) {
        if (o.circular || o.infinite) return;
        var a = (f == 'removeClass' || f == 'addClass') ? f : false,
            di = cf_c('disabled', c);
        if (o.auto.button && a) {
            o.auto.button[a](di)
        }
        if (o.prev.button) {
            var b = a || (f == 0) ? 'addClass' : 'removeClass';
            o.prev.button[b](di)
        }
        if (o.next.button) {
            var b = a || (f == o.items.visible) ? 'addClass' : 'removeClass';
            o.next.button[b](di)
        }
    }

    function go_getObject(a, b) {
        if (is_function(b)) {
            b = b.call(a)
        } else if (is_undefined(b)) {
            b = {}
        }
        return b
    }

    function go_getItemsObject(a, b) {
        b = go_getObject(a, b);
        if (is_number(b)) {
            b = {
                'visible': b
            }
        } else if (b == 'variable') {
            b = {
                'visible': b,
                'width': b,
                'height': b
            }
        } else if (!is_object(b)) {
            b = {}
        }
        return b
    }

    function go_getScrollObject(a, b) {
        b = go_getObject(a, b);
        if (is_number(b)) {
            if (b <= 50) {
                b = {
                    'items': b
                }
            } else {
                b = {
                    'duration': b
                }
            }
        } else if (is_string(b)) {
            b = {
                'easing': b
            }
        } else if (!is_object(b)) {
            b = {}
        }
        return b
    }

    function go_getNaviObject(a, b) {
        b = go_getObject(a, b);
        if (is_string(b)) {
            var c = cf_getKeyCode(b);
            if (c == -1) {
                b = $(b)
            } else {
                b = c
            }
        }
        return b
    }

    function go_getAutoObject(a, b) {
        b = go_getNaviObject(a, b);
        if (is_jquery(b)) {
            b = {
                'button': b
            }
        } else if (is_boolean(b)) {
            b = {
                'play': b
            }
        } else if (is_number(b)) {
            b = {
                'timeoutDuration': b
            }
        }
        if (b.progress) {
            if (is_string(b.progress) || is_jquery(b.progress)) {
                b.progress = {
                    'bar': b.progress
                }
            }
        }
        return b
    }

    function go_complementAutoObject(a, b) {
        if (is_function(b.button)) {
            b.button = b.button.call(a)
        }
        if (is_string(b.button)) {
            b.button = $(b.button)
        }
        if (!is_boolean(b.play)) {
            b.play = true
        }
        if (!is_number(b.delay)) {
            b.delay = 0
        }
        if (is_undefined(b.pauseOnEvent)) {
            b.pauseOnEvent = true
        }
        if (!is_boolean(b.pauseOnResize)) {
            b.pauseOnResize = true
        }
        if (!is_number(b.timeoutDuration)) {
            b.timeoutDuration = (b.duration < 10) ? 2500 : b.duration * 5
        }
        if (b.progress) {
            if (is_function(b.progress.bar)) {
                b.progress.bar = b.progress.bar.call(a)
            }
            if (is_string(b.progress.bar)) {
                b.progress.bar = $(b.progress.bar)
            }
            if (b.progress.bar) {
                if (!is_function(b.progress.updater)) {
                    b.progress.updater = $.fn.carouFredSel.progressbarUpdater
                }
                if (!is_number(b.progress.interval)) {
                    b.progress.interval = 50
                }
            } else {
                b.progress = false
            }
        }
        return b
    }

    function go_getPrevNextObject(a, b) {
        b = go_getNaviObject(a, b);
        if (is_jquery(b)) {
            b = {
                'button': b
            }
        } else if (is_number(b)) {
            b = {
                'key': b
            }
        }
        return b
    }

    function go_complementPrevNextObject(a, b) {
        if (is_function(b.button)) {
            b.button = b.button.call(a)
        }
        if (is_string(b.button)) {
            b.button = $(b.button)
        }
        if (is_string(b.key)) {
            b.key = cf_getKeyCode(b.key)
        }
        return b
    }

    function go_getPaginationObject(a, b) {
        b = go_getNaviObject(a, b);
        if (is_jquery(b)) {
            b = {
                'container': b
            }
        } else if (is_boolean(b)) {
            b = {
                'keys': b
            }
        }
        return b
    }

    function go_complementPaginationObject(a, b) {
        if (is_function(b.container)) {
            b.container = b.container.call(a)
        }
        if (is_string(b.container)) {
            b.container = $(b.container)
        }
        if (!is_number(b.items)) {
            b.items = false
        }
        if (!is_boolean(b.keys)) {
            b.keys = false
        }
        if (!is_function(b.anchorBuilder) && !is_false(b.anchorBuilder)) {
            b.anchorBuilder = $.fn.carouFredSel.pageAnchorBuilder
        }
        if (!is_number(b.deviation)) {
            b.deviation = 0
        }
        return b
    }

    function go_getSwipeObject(a, b) {
        if (is_function(b)) {
            b = b.call(a)
        }
        if (is_undefined(b)) {
            b = {
                'onTouch': false
            }
        }
        if (is_true(b)) {
            b = {
                'onTouch': b
            }
        } else if (is_number(b)) {
            b = {
                'items': b
            }
        }
        return b
    }

    function go_complementSwipeObject(a, b) {
        if (!is_boolean(b.onTouch)) {
            b.onTouch = true
        }
        if (!is_boolean(b.onMouse)) {
            b.onMouse = false
        }
        if (!is_object(b.options)) {
            b.options = {}
        }
        if (!is_boolean(b.options.triggerOnTouchEnd)) {
            b.options.triggerOnTouchEnd = false
        }
        return b
    }

    function go_getMousewheelObject(a, b) {
        if (is_function(b)) {
            b = b.call(a)
        }
        if (is_true(b)) {
            b = {}
        } else if (is_number(b)) {
            b = {
                'items': b
            }
        } else if (is_undefined(b)) {
            b = false
        }
        return b
    }

    function go_complementMousewheelObject(a, b) {
        return b
    }

    function gn_getItemIndex(a, b, c, d, e) {
        if (is_string(a)) {
            a = $(a, e)
        }
        if (is_object(a)) {
            a = $(a, e)
        }
        if (is_jquery(a)) {
            a = e.children().index(a);
            if (!is_boolean(c)) {
                c = false
            }
        } else {
            if (!is_boolean(c)) {
                c = true
            }
        } if (!is_number(a)) {
            a = 0
        }
        if (!is_number(b)) {
            b = 0
        }
        if (c) {
            a += d.first
        }
        a += b;
        if (d.total > 0) {
            while (a >= d.total) {
                a -= d.total
            }
            while (a < 0) {
                a += d.total
            }
        }
        return a
    }

    function gn_getVisibleItemsPrev(i, o, s) {
        var t = 0,
            x = 0;
        for (var a = s; a >= 0; a--) {
            var j = i.eq(a);
            t += (j.is(':visible')) ? j[o.d['outerWidth']](true) : 0;
            if (t > o.maxDimension) {
                return x
            }
            if (a == 0) {
                a = i.length
            }
            x++
        }
    }

    function gn_getVisibleItemsPrevFilter(i, o, s) {
        return gn_getItemsPrevFilter(i, o.items.filter, o.items.visibleConf.org, s)
    }

    function gn_getScrollItemsPrevFilter(i, o, s, m) {
        return gn_getItemsPrevFilter(i, o.items.filter, m, s)
    }

    function gn_getItemsPrevFilter(i, f, m, s) {
        var t = 0,
            x = 0;
        for (var a = s, l = i.length; a >= 0; a--) {
            x++;
            if (x == l) {
                return x
            }
            var j = i.eq(a);
            if (j.is(f)) {
                t++;
                if (t == m) {
                    return x
                }
            }
            if (a == 0) {
                a = l
            }
        }
    }

    function gn_getVisibleOrg(a, o) {
        return o.items.visibleConf.org || a.children().slice(0, o.items.visible).filter(o.items.filter).length
    }

    function gn_getVisibleItemsNext(i, o, s) {
        var t = 0,
            x = 0;
        for (var a = s, l = i.length - 1; a <= l; a++) {
            var j = i.eq(a);
            t += (j.is(':visible')) ? j[o.d['outerWidth']](true) : 0;
            if (t > o.maxDimension) {
                return x
            }
            x++;
            if (x == l + 1) {
                return x
            }
            if (a == l) {
                a = -1
            }
        }
    }

    function gn_getVisibleItemsNextTestCircular(i, o, s, l) {
        var v = gn_getVisibleItemsNext(i, o, s);
        if (!o.circular) {
            if (s + v > l) {
                v = l - s
            }
        }
        return v
    }

    function gn_getVisibleItemsNextFilter(i, o, s) {
        return gn_getItemsNextFilter(i, o.items.filter, o.items.visibleConf.org, s, o.circular)
    }

    function gn_getScrollItemsNextFilter(i, o, s, m) {
        return gn_getItemsNextFilter(i, o.items.filter, m + 1, s, o.circular) - 1
    }

    function gn_getItemsNextFilter(i, f, m, s, c) {
        var t = 0,
            x = 0;
        for (var a = s, l = i.length - 1; a <= l; a++) {
            x++;
            if (x >= l) {
                return x
            }
            var j = i.eq(a);
            if (j.is(f)) {
                t++;
                if (t == m) {
                    return x
                }
            }
            if (a == l) {
                a = -1
            }
        }
    }

    function gi_getCurrentItems(i, o) {
        return i.slice(0, o.items.visible)
    }

    function gi_getOldItemsPrev(i, o, n) {
        return i.slice(n, o.items.visibleConf.old + n)
    }

    function gi_getNewItemsPrev(i, o) {
        return i.slice(0, o.items.visible)
    }

    function gi_getOldItemsNext(i, o) {
        return i.slice(0, o.items.visibleConf.old)
    }

    function gi_getNewItemsNext(i, o, n) {
        return i.slice(n, o.items.visible + n)
    }

    function sz_storeMargin(i, o, d) {
        if (o.usePadding) {
            if (!is_string(d)) {
                d = '_cfs_origCssMargin'
            }
            i.each(function () {
                var j = $(this),
                    m = parseInt(j.css(o.d['marginRight']), 10);
                if (!is_number(m)) {
                    m = 0
                }
                j.data(d, m)
            })
        }
    }

    function sz_resetMargin(i, o, m) {
        if (o.usePadding) {
            var x = (is_boolean(m)) ? m : false;
            if (!is_number(m)) {
                m = 0
            }
            sz_storeMargin(i, o, '_cfs_tempCssMargin');
            i.each(function () {
                var j = $(this);
                j.css(o.d['marginRight'], ((x) ? j.data('_cfs_tempCssMargin') : m + j.data('_cfs_origCssMargin')))
            })
        }
    }

    function sz_storeSizes(i, o) {
        if (o.responsive) {
            i.each(function () {
                var j = $(this),
                    s = in_mapCss(j, ['width', 'height']);
                j.data('_cfs_origCssSizes', s)
            })
        }
    }

    function sz_setResponsiveSizes(o, b) {
        var c = o.items.visible,
            newS = o.items[o.d['width']],
            seco = o[o.d['height']],
            secp = is_percentage(seco);
        b.each(function () {
            var a = $(this),
                nw = newS - ms_getPaddingBorderMargin(a, o, 'Width');
            a[o.d['width']](nw);
            if (secp) {
                a[o.d['height']](ms_getPercentage(nw, seco))
            }
        })
    }

    function sz_setSizes(a, o) {
        var b = a.parent(),
            $i = a.children(),
            $v = gi_getCurrentItems($i, o),
            sz = cf_mapWrapperSizes(ms_getSizes($v, o, true), o, false);
        b.css(sz);
        if (o.usePadding) {
            var p = o.padding,
                r = p[o.d[1]];
            if (o.align && r < 0) {
                r = 0
            }
            var c = $v.last();
            c.css(o.d['marginRight'], c.data('_cfs_origCssMargin') + r);
            a.css(o.d['top'], p[o.d[0]]);
            a.css(o.d['left'], p[o.d[3]])
        }
        a.css(o.d['width'], sz[o.d['width']] + (ms_getTotalSize($i, o, 'width') * 2));
        a.css(o.d['height'], ms_getLargestSize($i, o, 'height'));
        return sz
    }

    function ms_getSizes(i, o, a) {
        return [ms_getTotalSize(i, o, 'width', a), ms_getLargestSize(i, o, 'height', a)]
    }

    function ms_getLargestSize(i, o, a, b) {
        if (!is_boolean(b)) {
            b = false
        }
        if (is_number(o[o.d[a]]) && b) {
            return o[o.d[a]]
        }
        if (is_number(o.items[o.d[a]])) {
            return o.items[o.d[a]]
        }
        a = (a.toLowerCase().indexOf('width') > -1) ? 'outerWidth' : 'outerHeight';
        return ms_getTrueLargestSize(i, o, a)
    }

    function ms_getTrueLargestSize(i, o, b) {
        var s = 0;
        for (var a = 0, l = i.length; a < l; a++) {
            var j = i.eq(a);
            var m = (j.is(':visible')) ? j[o.d[b]](true) : 0;
            if (s < m) {
                s = m
            }
        }
        return s
    }

    function ms_getTotalSize(i, o, b, c) {
        if (!is_boolean(c)) {
            c = false
        }
        if (is_number(o[o.d[b]]) && c) {
            return o[o.d[b]]
        }
        if (is_number(o.items[o.d[b]])) {
            return o.items[o.d[b]] * i.length
        }
        var d = (b.toLowerCase().indexOf('width') > -1) ? 'outerWidth' : 'outerHeight',
            s = 0;
        for (var a = 0, l = i.length; a < l; a++) {
            var j = i.eq(a);
            s += (j.is(':visible')) ? j[o.d[d]](true) : 0
        }
        return s
    }

    function ms_getParentSize(a, o, d) {
        var b = a.is(':visible');
        if (b) {
            a.hide()
        }
        var s = a.parent()[o.d[d]]();
        if (b) {
            a.show()
        }
        return s
    }

    function ms_getMaxDimension(o, a) {
        return (is_number(o[o.d['width']])) ? o[o.d['width']] : a
    }

    function ms_hasVariableSizes(i, o, b) {
        var s = false,
            v = false;
        for (var a = 0, l = i.length; a < l; a++) {
            var j = i.eq(a);
            var c = (j.is(':visible')) ? j[o.d[b]](true) : 0;
            if (s === false) {
                s = c
            } else if (s != c) {
                v = true
            }
            if (s == 0) {
                v = true
            }
        }
        return v
    }

    function ms_getPaddingBorderMargin(i, o, d) {
        return i[o.d['outer' + d]](true) - i[o.d[d.toLowerCase()]]()
    }

    function ms_getPercentage(s, o) {
        if (is_percentage(o)) {
            o = parseInt(o.slice(0, -1), 10);
            if (!is_number(o)) {
                return s
            }
            s *= o / 100
        }
        return s
    }

    function cf_e(n, c, a, b, d) {
        if (!is_boolean(a)) {
            a = true
        }
        if (!is_boolean(b)) {
            b = true
        }
        if (!is_boolean(d)) {
            d = false
        }
        if (a) {
            n = c.events.prefix + n
        }
        if (b) {
            n = n + '.' + c.events.namespace
        }
        if (b && d) {
            n += c.serialNumber
        }
        return n
    }

    function cf_c(n, c) {
        return (is_string(c.classnames[n])) ? c.classnames[n] : n
    }

    function cf_mapWrapperSizes(a, o, p) {
        if (!is_boolean(p)) {
            p = true
        }
        var b = (o.usePadding && p) ? o.padding : [0, 0, 0, 0];
        var c = {};
        c[o.d['width']] = a[0] + b[1] + b[3];
        c[o.d['height']] = a[1] + b[0] + b[2];
        return c
    }

    function cf_sortParams(c, d) {
        var e = [];
        for (var a = 0, l1 = c.length; a < l1; a++) {
            for (var b = 0, l2 = d.length; b < l2; b++) {
                if (d[b].indexOf(typeof c[a]) > -1 && is_undefined(e[b])) {
                    e[b] = c[a];
                    break
                }
            }
        }
        return e
    }

    function cf_getPadding(p) {
        if (is_undefined(p)) {
            return [0, 0, 0, 0]
        }
        if (is_number(p)) {
            return [p, p, p, p]
        }
        if (is_string(p)) {
            p = p.split('px').join('').split('em').join('').split(' ')
        }
        if (!is_array(p)) {
            return [0, 0, 0, 0]
        }
        for (var i = 0; i < 4; i++) {
            p[i] = parseInt(p[i], 10)
        }
        switch (p.length) {
        case 0:
            return [0, 0, 0, 0];
        case 1:
            return [p[0], p[0], p[0], p[0]];
        case 2:
            return [p[0], p[1], p[0], p[1]];
        case 3:
            return [p[0], p[1], p[2], p[1]];
        default:
            return [p[0], p[1], p[2], p[3]]
        }
    }

    function cf_getAlignPadding(a, o) {
        var x = (is_number(o[o.d['width']])) ? Math.ceil(o[o.d['width']] - ms_getTotalSize(a, o, 'width')) : 0;
        switch (o.align) {
        case 'left':
            return [0, x];
        case 'right':
            return [x, 0];
        case 'center':
        default:
            return [Math.ceil(x / 2), Math.floor(x / 2)]
        }
    }

    function cf_getDimensions(o) {
        var a = [
            ['width', 'innerWidth', 'outerWidth', 'height', 'innerHeight', 'outerHeight', 'left', 'top', 'marginRight', 0, 1, 2, 3],
            ['height', 'innerHeight', 'outerHeight', 'width', 'innerWidth', 'outerWidth', 'top', 'left', 'marginBottom', 3, 2, 1, 0]
        ];
        var b = a[0].length,
            dx = (o.direction == 'right' || o.direction == 'left') ? 0 : 1;
        var c = {};
        for (var d = 0; d < b; d++) {
            c[a[0][d]] = a[dx][d]
        }
        return c
    }

    function cf_getAdjust(x, o, a, b) {
        var v = x;
        if (is_function(a)) {
            v = a.call(b, v)
        } else if (is_string(a)) {
            var p = a.split('+'),
                m = a.split('-');
            if (m.length > p.length) {
                var c = true,
                    sta = m[0],
                    adj = m[1]
            } else {
                var c = false,
                    sta = p[0],
                    adj = p[1]
            }
            switch (sta) {
            case 'even':
                v = (x % 2 == 1) ? x - 1 : x;
                break;
            case 'odd':
                v = (x % 2 == 0) ? x - 1 : x;
                break;
            default:
                v = x;
                break
            }
            adj = parseInt(adj, 10);
            if (is_number(adj)) {
                if (c) {
                    adj = -adj
                }
                v += adj
            }
        }
        if (!is_number(v) || v < 1) {
            v = 1
        }
        return v
    }

    function cf_getItemsAdjust(x, o, a, b) {
        return cf_getItemAdjustMinMax(cf_getAdjust(x, o, a, b), o.items.visibleConf)
    }

    function cf_getItemAdjustMinMax(v, i) {
        if (is_number(i.min) && v < i.min) {
            v = i.min
        }
        if (is_number(i.max) && v > i.max) {
            v = i.max
        }
        if (v < 1) {
            v = 1
        }
        return v
    }

    function cf_getSynchArr(s) {
        if (!is_array(s)) {
            s = [
                [s]
            ]
        }
        if (!is_array(s[0])) {
            s = [s]
        }
        for (var j = 0, l = s.length; j < l; j++) {
            if (is_string(s[j][0])) {
                s[j][0] = $(s[j][0])
            }
            if (!is_boolean(s[j][1])) {
                s[j][1] = true
            }
            if (!is_boolean(s[j][2])) {
                s[j][2] = true
            }
            if (!is_number(s[j][3])) {
                s[j][3] = 0
            }
        }
        return s
    }

    function cf_getKeyCode(k) {
        if (k == 'right') {
            return 39
        }
        if (k == 'left') {
            return 37
        }
        if (k == 'up') {
            return 38
        }
        if (k == 'down') {
            return 40
        }
        return -1
    }

    function cf_setCookie(n, a, c) {
        if (n) {
            var v = a.triggerHandler(cf_e('currentPosition', c));
            $.fn.carouFredSel.cookie.set(n, v)
        }
    }

    function cf_getCookie(n) {
        var c = $.fn.carouFredSel.cookie.get(n);
        return (c == '') ? 0 : c
    }

    function in_mapCss(a, b) {
        var c = {}, prop;
        for (var p = 0, l = b.length; p < l; p++) {
            prop = b[p];
            c[prop] = a.css(prop)
        }
        return c
    }

    function in_complementItems(a, b, c, d) {
        if (!is_object(a.visibleConf)) {
            a.visibleConf = {}
        }
        if (!is_object(a.sizesConf)) {
            a.sizesConf = {}
        }
        if (a.start == 0 && is_number(d)) {
            a.start = d
        }
        if (is_object(a.visible)) {
            a.visibleConf.min = a.visible.min;
            a.visibleConf.max = a.visible.max;
            a.visible = false
        } else if (is_string(a.visible)) {
            if (a.visible == 'variable') {
                a.visibleConf.variable = true
            } else {
                a.visibleConf.adjust = a.visible
            }
            a.visible = false
        } else if (is_function(a.visible)) {
            a.visibleConf.adjust = a.visible;
            a.visible = false
        }
        if (!is_string(a.filter)) {
            a.filter = (c.filter(':hidden').length > 0) ? ':visible' : '*'
        }
        if (!a[b.d['width']]) {
            if (b.responsive) {
                debug(true, 'Set a ' + b.d['width'] + ' for the items!');
                a[b.d['width']] = ms_getTrueLargestSize(c, b, 'outerWidth')
            } else {
                a[b.d['width']] = (ms_hasVariableSizes(c, b, 'outerWidth')) ? 'variable' : c[b.d['outerWidth']](true)
            }
        }
        if (!a[b.d['height']]) {
            a[b.d['height']] = (ms_hasVariableSizes(c, b, 'outerHeight')) ? 'variable' : c[b.d['outerHeight']](true)
        }
        a.sizesConf.width = a.width;
        a.sizesConf.height = a.height;
        return a
    }

    function in_complementVisibleItems(a, b) {
        if (a.items[a.d['width']] == 'variable') {
            a.items.visibleConf.variable = true
        }
        if (!a.items.visibleConf.variable) {
            if (is_number(a[a.d['width']])) {
                a.items.visible = Math.floor(a[a.d['width']] / a.items[a.d['width']])
            } else {
                a.items.visible = Math.floor(b / a.items[a.d['width']]);
                a[a.d['width']] = a.items.visible * a.items[a.d['width']];
                if (!a.items.visibleConf.adjust) {
                    a.align = false
                }
            } if (a.items.visible == 'Infinity' || a.items.visible < 1) {
                debug(true, 'Not a valid number of visible items: Set to "variable".');
                a.items.visibleConf.variable = true
            }
        }
        return a
    }

    function in_complementPrimarySize(a, b, c) {
        if (a == 'auto') {
            a = ms_getTrueLargestSize(c, b, 'outerWidth')
        }
        return a
    }

    function in_complementSecondarySize(a, b, c) {
        if (a == 'auto') {
            a = ms_getTrueLargestSize(c, b, 'outerHeight')
        }
        if (!a) {
            a = b.items[b.d['height']]
        }
        return a
    }

    function in_getAlignPadding(o, a) {
        var p = cf_getAlignPadding(gi_getCurrentItems(a, o), o);
        o.padding[o.d[1]] = p[1];
        o.padding[o.d[3]] = p[0];
        return o
    }

    function in_getResponsiveValues(o, a, b) {
        var c = cf_getItemAdjustMinMax(Math.ceil(o[o.d['width']] / o.items[o.d['width']]), o.items.visibleConf);
        if (c > a.length) {
            c = a.length
        }
        var d = Math.floor(o[o.d['width']] / c);
        o.items.visible = c;
        o.items[o.d['width']] = d;
        o[o.d['width']] = c * d;
        return o
    }

    function bt_pauseOnHoverConfig(p) {
        if (is_string(p)) {
            var i = (p.indexOf('immediate') > -1) ? true : false,
                r = (p.indexOf('resume') > -1) ? true : false
        } else {
            var i = r = false
        }
        return [i, r]
    }

    function bt_mousesheelNumber(a) {
        return (is_number(a)) ? a : null
    }

    function is_null(a) {
        return (a === null)
    }

    function is_undefined(a) {
        return (is_null(a) || typeof a == 'undefined' || a === '' || a === 'undefined')
    }

    function is_array(a) {
        return (a instanceof Array)
    }

    function is_jquery(a) {
        return (a instanceof jQuery)
    }

    function is_object(a) {
        return ((a instanceof Object || typeof a == 'object') && !is_null(a) && !is_jquery(a) && !is_array(a))
    }

    function is_number(a) {
        return ((a instanceof Number || typeof a == 'number') && !isNaN(a))
    }

    function is_string(a) {
        return ((a instanceof String || typeof a == 'string') && !is_undefined(a) && !is_true(a) && !is_false(a))
    }

    function is_function(a) {
        return (a instanceof Function || typeof a == 'function')
    }

    function is_boolean(a) {
        return (a instanceof Boolean || typeof a == 'boolean' || is_true(a) || is_false(a))
    }

    function is_true(a) {
        return (a === true || a === 'true')
    }

    function is_false(a) {
        return (a === false || a === 'false')
    }

    function is_percentage(x) {
        return (is_string(x) && x.slice(-1) == '%')
    }

    function getTime() {
        return new Date().getTime()
    }

    function deprecated(o, n) {
        debug(true, o + ' is DEPRECATED, support for it will be removed. Use ' + n + ' instead.')
    }

    function debug(d, m) {
        if (is_object(d)) {
            var s = ' (' + d.selector + ')';
            d = d.debug
        } else {
            var s = ''
        } if (!d) {
            return false
        }
        if (is_string(m)) {
            m = 'carouFredSel' + s + ': ' + m
        } else {
            m = ['carouFredSel' + s + ':', m]
        } if (window.console && window.console.log) {
            window.console.log(m)
        }
        return false
    }
    $.extend($.easing, {
        'quadratic': function (t) {
            var a = t * t;
            return t * (-a * t + 4 * a - 6 * t + 4)
        },
        'cubic': function (t) {
            return t * (4 * t * t - 9 * t + 6)
        },
        'elastic': function (t) {
            var a = t * t;
            return t * (33 * a * a - 106 * a * t + 126 * a - 67 * t + 15)
        }
    })
})(jQuery);