<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<style type="text/css">
	div.secretFlag {padding: 1px 0px 10px 10px;}
</style>


<form:form modelAttribute="itemQna" method="post">
	<form:hidden path="itemId" />
	<input type="hidden" name="userName" value="${requestContext.user.userName}" />
	<input type="hidden" name="email" value="${requestContext.user.email}" />
	<input type="hidden" name="sellerId" value="${item.sellerId}" />

	<!-- 내용 : s -->
	<div class="con">
		<div class="pop_title">
			<h3>Q&amp;A 문의</h3>
			<a href="javascript:history.back();" class="history_back">뒤로가기</a>
		</div>
		<!-- //pop_title -->
		<div class="pop_con">
			<div class="write_top">
				<div class="inner">
					<div class="write_img">
						<img src="${item.imageSrc}" alt="${item.itemName}">
					</div>
					<div class="write_name">
						<p>${item.itemName}</p>
					</div>
				</div>
			</div>
			<!-- //write_top -->

			<div class="bd_table">
				<ul class="del_info">
					<li>
						<span class="del_tit t_gray">제목</span>
						<div class="input_area">
							<form:input path="subject" maxlength="50" class="transparent required _filter _emoji" title="${op:message('M00275')}" placeholder="제목을 입력해주세요" />
						</div>
					</li>
					<li>
						<span class="del_tit t_gray">문의구분</span>
						<div class="select_area">

							<form:select path="qnaGroup" class="transparent">

								<c:forEach items="${groups}" var="group">
									<form:option value="${group.id}" label="${group.label}" />
								</c:forEach>

							</form:select>

						</div>
					</li>
					<li>
						<span class="del_tit t_gray">내용</span>
						<div class="text_area">
							<form:textarea path="question" cols="30" rows="6" class="required _filter _emoji" title="문의내용" placeholder="내용을 입력해주세요" />
						</div>
					</li>
					<div class="secretFlag">
						<span><form:checkbox path="secretFlag" value="Y" label="비공개" /> 비공개<input type="hidden" name="!secretFlag" value="N" /></span>
					</div>
				</ul>
			</div>
			<!-- //bd_table -->


			<div class="btn_wrap">
				<button onclick="javascript:history.back();" class="btn_st1 reset">취소</button>
				<button type="submit" class="btn_st1 decision">등록</button>
			</div>
			<!-- //btn_wrap -->

		</div>
		<!-- //pop_con -->

	</div>
	<!-- 내용 : e -->

</form:form>


<page:javascript>
	<script type="text/javascript">
        $(function() {
            $('#itemQna').validator();
        });

        //내용 글자수체크 추가 [2017-04-27]minae.yun
        $('#question').on('keydown', function() {
            cal_pre();
        });

        //TEXTAREA 최대값 체크
        function cal_pre() {
            var tmpStr;

            tmpStr = $('#question').val();
            cal_byte(tmpStr, 500);
        }

        function cal_byte(aquery, maxlength) {

            var tmpStr;
            var temp=0;
            var onechar;
            var tcount;
            tcount = 0;

            tmpStr = new String(aquery);
            temp = tmpStr.length;

            for (k=0;k<temp;k++)	{
                onechar = tmpStr.charAt(k);
                if (escape(onechar) =='%0D') {

                } else if (escape(onechar).length > 4) {
                    tcount += 2;
                } else {
                    tcount++;
                }
            }


            if(tcount>maxlength) {
                reserve = tcount-maxlength;
                alert("내용이 너무 많습니다.");
                cutText();
                return;
            }

        }

        function cutText() {
            nets_check($('#question').val(), 500);
        }

        function nets_check(aquery, maxlength) {

            var tmpStr;
            var temp=0;
            var onechar;
            var tcount;
            tcount = 0;

            tmpStr = new String(aquery);
            temp = tmpStr.length;

            for (k=0;k<temp;k++)	{

                onechar = tmpStr.charAt(k);

                if (escape(onechar).length > 4) {
                    tcount += 2;
                } else {
                    // 엔터값이 들어왔을때 값(rn)이 두번실행되는데
                    if(escape(onechar)=='%0A') {

                    } else {
                        tcount++;
                    }
                }

                if(tcount>maxlength) {
                    tmpStr = tmpStr.substring(0,k);
                    break;
                }

            }

            $('#question').val(tmpStr);
            cal_byte(tmpStr);

        }
	</script>
</page:javascript>