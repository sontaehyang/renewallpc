<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
	<head>
		<meta charset="utf-8">
		<link rel="stylesheet" type="text/css" href="/content/css/base.css">
	</head>
	<body>
		<style>
			.err_wrap {position:fixed;width:1250px;left:50%;top:50%;transform:translate(-50%, -50%);}
			.err_wrap .err_info {font-size:16px;font-weight:500;text-align:center;line-height:24px;}
			.err_wrap .err_info:before {content:"";display:block;height:100px;margin-bottom:30px;background:url('/content/images/icon/ico_404.png') no-repeat center top;}
			.err_wrap .err_info > dt {margin-bottom:30px;font-size:22px;font-weight:700;color:#000;}

			.btn_wrap{padding: 40px 0 0 0; text-align: center;}
			.btn-default{color:#4d4d4d; background:#f7f7f7; border:1px solid #d1d1d1; }
			.btn-default:hover,
			.btn-default:focus{ background:#e2e2e2; }
			.btn-success{color:#fff; background: #ff7101; border-bottom:1px solid #ff7101; }
			.btn-success:hover,
			.btn-success:focus{background: #cc5a00; border-bottom:1px solid #cc5a00;}
			.btn-lg{width:140px; height:50px; line-height:48px; font-size:15px; font-weight:bold;}
			.btn {
				display: inline-block;
				margin-bottom: 0;
				font-weight: 400;
				text-align: center;
				vertical-align: middle;
				cursor: pointer;
				background-image: none;
				border: 1px solid transparent;
				white-space: nowrap;
				border-radius:2px;
			}
			.btn:focus{outline:none; }
			.btn img{vertical-align:middle; margin-top:-1px; margin-right:3px;}
		</style>
		<div class="err_wrap">
			<dl class="err_info">
				<dt>페이지를 찾을 수 없습니다.</dt>
				<dd>페이지가 존재하지 않거나, 사용할 수 없는 페이지입니다. <br>입력하신 주소가 정확한지 다시 한 번 확인해주세요.</dd>
			</dl><!--// err_info -->

			<div class="btn_wrap">
				<a href="javascript:history.back();" class="btn btn-lg btn-default">이전으로</a>
				<a href="/" class="btn btn-lg btn-success">메인으로</a>
			</div><!--// btn_wrap -->
		</div>
	</body>
</html>