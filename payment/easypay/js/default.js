function inputCheckSpecial(obj){
	var ret= "true";
	var strobj = obj.value;

	//re = /[~!@\#$%<>^&*\()\-=+_\']/gi;
	re = /[@\#$%<>&\()\-=+_\']/gi;

	if( re.test(strobj) ){
		obj.value=strobj.replace(re,"");
		obj.focus();
		ret += ",false";
	}else {
		ret += ",true";
	}


	if (ret.indexOf("false")!=-1){
		return false;
	}else{
		return true;
	}
}


function press(e) { 
    var code = e.charCode?e.charCode:e.keyCode; 
    if(code<48 || code>57) 
        return false; 
    else return true; 
} 

function down(e) { 
    if(e.ctrlKey && e.keyCode==86) 
        return false; 
} 

var temp = ""; 
function up(obj) { 
    if(obj.value.search(/[^0-9]/) != -1) 
        obj.value = temp; 
    else 
        temp = obj.value; 
} 




function FlashObject(path, width, height)
{
    var m_movie = path;
    var m_width = width;
    var m_height = height;

    this.wmode = "";
    this.id = "";
    this.quality = "high";
    this.menu = "false";
    this.allowScriptAccess = "sameDomain";
    this.FlashVars = "";

    this.Render = function()
    {
        var html;

        html = "<object classid='clsid:d27cdb6e-ae6d-11cf-96b8-444553540000' codebase='http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0' width='" + m_width + "' height='" + m_height + "'";
        if (this.id != "") html += " id='" + this.id + "'";
        html += ">";
        
        html += "<param name='allowScriptAccess' value='" + this.allowScriptAccess + "' />";
        html += "<param name='movie' value='" + m_movie + "' />";
        html += "<param name='menu' value='" + this.menu + "' />";
        html += "<param name='quality' value='" + this.quality + "' />";
        if (this.wmode != "") html += "<param name='wmode' value='" + this.wmode + "' />";
        if (this.FlashVars != "") html += "<param name='FlashVars' value='" + this.FlashVars + "' />";
        
        html += "<embed src='" + m_movie + "' quality='" + this.quality + "' pluginspage='http://www.macromedia.com/go/getflashplayer' type='application/x-shockwave-flash' width='" + m_width + "' height='" + m_height + "'";
        html += " allowScriptAccess='" + this.allowScriptAccess + "'";
        if (this.wmode != "") html += " wmode='" + this.wmode + "'";
        if (this.FlashVars != "") html += " FlashVars='" + this.FlashVars + "'";
        html += " /></object>";
        
        document.write(html);
    }
}

function f_menu_goto(url)
{
	window.parent.location.href=url;
}


var totcount;

function setConfig(num){
	totcount = num;
}

function clickshow(num) {
	var showcase;
	var totcount1 = totcount;

	for (i=1;i<totcount1+1;i++) {
		menu=eval("document.all.block"+i+".style");
		imgch=eval("document.bar"+i);

		if (num==i)	{
			if (menu.display=="block") {
				menu.display="none";
			} else {
				menu.display="block";
				showcase = "1";
			}
		}
	}

	if (showcase == "1"){
		for (i=1;i<totcount1+1;i++) {
			menu=eval("document.all.block"+i+".style");
			imgch=eval("document.bar"+i);
			if (num!=i){
				if (menu.display=="block") menu.display="none";
			}
		}
	}
}

/*
// 오른쪽 마우스 막음 start
var message=""; 
function clickIE() {
	if (document.all) {
		(message);
		return false;
	}
}
function clickNS(e) {
	if(document.layers||(document.getElementById&&!document.all)) {
		if (e.which==2||e.which==3) {
			(message);
			return false;
		}
	}
}
if (document.layers) {
	document.captureEvents(Event.MOUSEDOWN);
	document.onmousedown=clickNS;
} else {
	document.onmouseup=clickNS;
	document.oncontextmenu=clickIE;
}

document.oncontextmenu=new Function("return false")
//document.onselectstart=new Function("return false")
document.ondragstart=new Function("return false")
// 오른쪽 마우스 막음 end
*/


function f_reset(obj)
{
	obj.reset();
}


function Numberchk() { 
	var key = event.keyCode;
	 if(!(key==8||key==9||key==13||key==46||key==144||(key>=48&&key<=57)||key==110||key==190)){
	//if(event.keyCode<45||(event.keyCode>57&&event.keyCode<96)||event.keyCode>105){
	//if(event.keyCode<48||(event.keyCode>57&&event.keyCode<96)||event.keyCode>105){
		event.returnValue = false; 
	}
} 

function searchEnter() { 
	var key = event.keyCode;
	 if(key==13){
   		f_list_submit();
	}
} 

function submitEnter() { 
	var key = event.keyCode;
	 if(key==13){		 
   		f_submit();
	}
} 

function confirmEnter() { 
	var key = event.keyCode;
	 if(key==13){		 
		f_confirm();
	}
}

function vComma(obj) { 
    var str = "" + obj.value.replace(/,/gi,''); // 콤마 제거 
    var regx = new RegExp(/(-?\d+)(\d{3})/); 
    var bExists = str.indexOf(".",0); 
    var strArr  = str.split('.'); 
    while(regx.test(strArr[0])){ 
        strArr[0] = strArr[0].replace(regx,"$1,$2"); 
    } 
    if (bExists > -1) 
        obj.value = strArr[0] + "." + strArr[1]; 
    else 
        obj.value = strArr[0]; 
} 

function trim(str) { 
    return str.replace(/(^\s*)|(\s*$)/g, ""); 
} 

function getNumber(str) { 
    str = "" + str.replace(/,/gi,''); // 콤마 제거 
    str = str.replace(/(^\s*)|(\s*$)/g, ""); // trim 
    return (new Number(str)); 
} 

function getComma(str) { 
	var tmp = "";
    var regx = new RegExp(/(-?\d+)(\d{3})/); 
	str = ""+str;

    var bExists = str.indexOf(".",0); 

    var strArr  = str.split('.'); 
    while(regx.test(strArr[0])){ 
        strArr[0] = strArr[0].replace(regx,"$1,$2"); 
    } 
    if (bExists > -1) 
        tmp =  strArr[0] + "." + strArr[1]; 
    else 
        tmp =  strArr[0]; 

	return tmp;
} 


function isLength(varCk) {
	var varLen = 0;
	var agr = navigator.userAgent;

	for (i=0; i<varCk.length; i++) {
		ch = varCk.charAt(i);
		if ((ch == "\n") || ((ch >= "ㅏ") && (ch <= "히")) || ((ch >="ㄱ") && (ch <="ㅎ")))
			varLen += 2;
		else
			varLen += 1;
	}
	return (varLen);
}



// 입력 문자열 검사 (숫자/특수문자)
function isInteger(varCk, charSet) {
	var chk=true;
	for (i=0; i<=varCk.length-1; i++) {
		ch = varCk.substring(i,i+1);
		if (ch>="0" && ch<="9") {
			chk = true;
		} else {
			chk=false;
			for (j=0; j<=charSet.length-1; j++) {
				comp = charSet.substring(j,j+1);
				if (ch==comp) {
					chk = true;
					break;
				}
			}
			if (!chk) 	break;	// 숫자+특수문자외의 문자가 있는 경우만 error 종료 2002.04.08
		}
	}
	return chk;
}



function isDay(varCk1,varCk2,varCk3) {
	if ( (isLength(varCk1)==4) && (isLength(varCk2)==2) && (isLength(varCk3)==2) ) {
		if ( (isInteger(varCk1,"")) && (isInteger(varCk2,"")) && (isInteger(varCk3,"")) ) {
			if (varCk1>="1900" && varCk1<="2099" && varCk2>="01" && varCk2<="12") {
				if (varCk2=="01" && varCk3>="01" && varCk3<="31") return true;
				if (varCk2=="02" && varCk3>="01" && varCk3<="28") return true;
				if (varCk2=="03" && varCk3>="01" && varCk3<="31") return true;
				if (varCk2=="04" && varCk3>="01" && varCk3<="30") return true;
				if (varCk2=="05" && varCk3>="01" && varCk3<="31") return true;
				if (varCk2=="06" && varCk3>="01" && varCk3<="30") return true;
				if (varCk2=="07" && varCk3>="01" && varCk3<="31") return true;
				if (varCk2=="08" && varCk3>="01" && varCk3<="31") return true;
				if (varCk2=="09" && varCk3>="01" && varCk3<="30") return true;
				if (varCk2=="10" && varCk3>="01" && varCk3<="31") return true;
				if (varCk2=="11" && varCk3>="01" && varCk3<="30") return true;
				if (varCk2=="12" && varCk3>="01" && varCk3<="31") return true;
				return false;
			}
			return false;
		} else {
			return false;
		}
	} else {
		return false;
	}
}

function isTime(varCk1,varCk2,varCk3) {
	if ( (isLength(varCk1)==2) && (isLength(varCk2)==2) && (isLength(varCk3)==2) ) {
		if ( (isInteger(varCk1,"")) && (isInteger(varCk2,"")) && (isInteger(varCk3,"")) ) {
			if (varCk1>="00" && varCk1<="23" && varCk2>="00" && varCk2<="59" && varCk3>="00" && varCk3<="59") {
				return true;
			}
			return false;
		} else {
			return false;
		}
	} else {
		return false;
	}
}

/*
 * 분리자를 이용하여 날짜의 유효성 체크
 * 예) 2000.03.24 -> '.'을 이용하여 체크한다.
 *@param inputDate 체크할 날짜
 *@param point 년,월,일 분리자
 */
function dateCheck(inputDate, point){
	var dateElement = new Array(3);
	
	/*if(point != ""){
		dateElement = inputDate.split(point);
		if(inputDate.length != 10 || dateElement.length != 3){
			return false;
		}
	}else{*/
		dateElement[0] = inputDate.substring(0,4);
		dateElement[1] = inputDate.substring(4,6);
		dateElement[2] = inputDate.substring(6,8);
	//}
	//년도 검사
	if( !( 1800 <= dateElement[0] && dateElement[0] <= 4000 ) ) {
		return false;
	}

	//달 검사
	if( !( 0 < dateElement[1] &&  dateElement[1] < 13  ) ) {
		return false;
	}

	// 해당 년도 월의 마지막 날
	var tempDate = new Date(dateElement[0], dateElement[1], 0);
	var endDay = tempDate.getDate();

	//일 검사
	if( !( 0 < dateElement[2] && dateElement[2] <= endDay ) ) {
		 return false;
	}

	return true;
}


/*
 * 날짜 비교
 * 종료일이 시작일 보다 작을때 false를
 * 정상 기간일 경우 true를 리턴한다.
 * @param startDate 시작일
 * @param endDate 종료일
 * @param point 날짜 구분자
 */
 function dateCompare(startDate, endDate, point){
	//정상 날짜인지 체크한다.
	var startDateChk = dateCheck(startDate, point);
	if(!startDateChk){
		return false;
	}
	var endDateChk = dateCheck(endDate, point, "end");
	
	if(!endDateChk){
		return false;
	}

	//년 월일로 분리 한다.
	var start_Date = new Array(3);
	var end_Date = new Array(3);

	if(point != ""){
		start_Date = startDate.split(point);
		end_Date = endDate.split(point);
		if(start_Date.length != 3 && end_Date.length != 3){
			return false;
		}
	}else{
		start_Date[0] = startDate.substring(0,4);
		start_Date[1] = startDate.substring(4,6);
		start_Date[2] = startDate.substring(6,8);

		end_Date[0] = endDate.substring(0,4);
		end_Date[1] = endDate.substring(4,6);
		end_Date[2] = endDate.substring(6,8);
	}

	//Date 객체를 생성한다.
	var sDate = new Date(start_Date[0], start_Date[1], start_Date[2]);
	var eDate = new Date(end_Date[0], end_Date[1], end_Date[2]);

	if(sDate > eDate){
		return false;
	}

	return true;
}

function corpAddr(){
	var frm = document.frm;

	if( frm.addrChk.checked ){
		frm.zip.value = frm.ceoZip.value;
		frm.addr1.value = frm.ceoAddr1.value;
		frm.addr2.value = frm.ceoAddr2.value;
	}else{
		frm.zip.value = "";
		frm.addr1.value = "";
		frm.addr2.value = "";
	}
}


