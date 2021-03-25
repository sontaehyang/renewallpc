<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>

<h3><span>${op:message('M00259')}</span></h3>

<div class="location">
	<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
</div>

	<form id="Group" method="post">
		
		<div class="board_write">
			<table class="board_write_table">
				<colgroup>
					<col style="width:150px;" />
					<col style="width: auto;" />
				</colgroup>
				<tbody>
					<tr>
						<th class="label">아이디</th>
						<td>
							<div>
								<input type="text" class="form-half required" id="deniedId" title="아이디" />
                                <button type="button" name="inputAdd" class="btn btn-dark-gray btn-sm">추가</button>
                                <button type="button" name="inputDel" class="btn btn-dark-gray btn-sm">제거</button>
							</div>
						</td>
					</tr>
				</tbody> 
			</table>				 
		</div>
		
		<div class="btn_center">
			<button type="button" onclick="editDeniedId();" class="btn btn-active">${op:message('M00101')}</button>
			<a href="javascript:history.back();" class="btn btn-default">${op:message('M00037')}</a>
		</div>
	</form>

<script type="text/javascript">
    var tr = createInput();

    function createInput(){
        var tr = document.createElement("tr");
        var th = document.createElement("th");
        th.setAttribute("class", "label");
        th.innerHTML = "아이디";
        var td = document.createElement("td");
        var div = document.createElement("div");
        var input = document.createElement("input");
        input.setAttribute("class", "required form-half");
        div.appendChild(input);
        td.appendChild(div);
        tr.appendChild(th);
        tr.appendChild(td);
        return tr;
    }

    $("button[name='inputAdd']").click(function(){
        var size = $("table tbody tr").length;
        if(size <= 4){
            $("table tbody").append($(tr).clone());
        } else if(size == 5){
            alert("한번에 최대 5개까지 등록이 가능합니다.");
        }
    });

    $("button[name='inputDel']").click(function(){
        var size = $("table tbody tr").length-1;
        if(size != 0){
            $("table tbody tr:eq("+size+")").remove();
        }
    });

    function inputAddIdData(){
        var size = $("table tbody tr").length;
        var id = "";
        for(var i = 0; i < size; i++){

        	var inputId = $("table tbody tr:eq("+i+") input").val().trim();

        	if (inputId == '') {
        		continue;
	        }

            id += inputId+",";
        }

        id = id.substr(0, id.length -1);

        return id;
    }

    function editDeniedId(){
        var id = inputAddIdData();
	    var key = "2";

	    var configData = {
		    deniedId : id,
		    deniedKey : key
	    };
	    if(id == "" || id == null){
		    alert("아이디를 입력해주세요.");
	    } else if (confirm("등록하시겠습니까?")) {
		    $.post('/opmanager/config/deny/editDeniedId', configData, function(response) {
			    alert("등록되었습니다.");
			    location.href = "/opmanager/config/deny/edit";
		    });
	    }

    }

</script>
