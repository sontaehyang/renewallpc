<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
		<link rel="stylesheet" type="text/css" href="/content/css/base.css">
	</head>
	<body>
		<style>
			/* Front-end Dev Mino Reset */
			html, body, div, span, applet, object, iframe,
			h1, h2, h3, h4, h5, h6, p, blockquote, pre,
			a, abbr, acronym, address, big, cite, code,
			del, dfn, em, img, ins, kbd, q, s, samp,
			small, strike, strong, sub, sup, tt, var,
			b, u, i, center,
			dl, dt, dd, ol, ul, li,
			fieldset, form, label, legend,
			table, caption, tbody, tfoot, thead, tr, th, td,
			article, aside, canvas, details, embed,
			figure, figcaption, footer, header, hgroup,
			menu, nav, output, ruby, section, summary,
			time, mark, audio, video {
				margin: 0;
				padding: 0;
				border: 0;
				font-size: 12px;
				letter-spacing: -0.5px;
				vertical-align: middle;
				font-family:"Noto Sans KR","Montserrat", Open Sans, Malgun Gothic, sans-serif;
			}
			html {
				box-sizing: border-box;
			}
			*, *:before, *:after {
				box-sizing: inherit;
			}
			body {
				line-height: 1;
				text-align: left;
				word-break: break-word;
			}
			* {
				-webkit-tap-highlight-color: rgba(0, 0, 0, 0);
				-moz-tap-highlight-color: rgba(0, 0, 0, 0);
			}
			button {
				overflow: visible;
				margin: 0;
				padding: 0;
				border: none;
				background: none;
				cursor: pointer;
				font-family: 'Noto Sans KR';
				outline: none;
			}

			#container {position:relative; width:100%; min-width:320px; min-height:200px; }
			#container .title {position:relative; width:100%; }
			#container .title h2 {font-size:17px; color:#1c2957; line-height:42px; font-weight:800; text-align:center; border-bottom:1px solid #1c2957; }
			#container .title .his_back {position:absolute; top:16px; left:15px; width:7px; height:13px; background:url(/content/mobile/images/common/title_arr_ico.png) no-repeat left top; background-size:7px 13px; }
			#container .title .his_back a {display:inline-block; width:100%; height:100%; }

			.notice_none {margin:60px 0 30px; padding-top:50px; background:url(/content/mobile/images/common/page_none.gif) no-repeat center top; background-size:39px; text-align:center;}
			.notice_none p {font-weight:600; font-size:10px; color:#666;}
			.notice_none .tit {margin-bottom:9px; font-size:14px; color:#1a1a1a; line-height:15px;}
			.page_none_btn {margin-bottom:63px; text-align:center;}
			.page_none_btn button {width:123px; padding:12px 0;}

			.ir_pm {display:block; overflow:hidden; font-size:0px; line-height:0; text-indent:-9999px; }

			.btn_st1 {font-size:14px; font-weight:800; color:#fff; background:#b2b2b2; border-radius:3px; padding:11px 36px; display:inline-block; text-align:center; }
			.btn_st1.decision {background:#ff7101; }
			.btn_st1.reset {background:#b2b2b2; }
		</style>
		<div id="container">
			<div class="title">
				<h2>사용할 수 없는 페이지</h2>
				<span class="his_back"><a href="javascript:void(0);" class="ir_pm" onclick="history.back();">뒤로가기</a></span>
			</div>
			<div class="con Chrome">
				<div class="notice_none">
					<p class="tit">찾으시는 페이지가 존재하지 않거나,<br>현재 사용할 수 없는 페이지 입니다.</p>
					<p>서비스 이용에 불편을 드려서 죄송합니다.</p>
				</div>
				<!-- notice_none -->
				<div class="page_none_btn">
					<button type="button" onClick="history.back()" class="btn_st1 reset">이전화면</button>
					<button type="button" onClick="location.href='/m'"  class="btn_st1 decision">메인으로 가기</button>
				</div>
				<!-- btn_wrap -->
			</div>
		</div>
	</body>
</html>