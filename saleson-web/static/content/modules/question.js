	var oldN = ""
	function showQnA(curN) {					
		if(curN != oldN )	{
			document.getElementById("questTit"+curN).className='tit-on';					
			document.getElementById("quest"+curN).className='view-on';					
			if(oldN != '') {
				document.getElementById("questTit"+oldN).className='tit-off';
				document.getElementById("quest"+oldN).className='view-off';
			}
			
			oldN = curN;
		}else if(curN == oldN && document.getElementById("questTit"+curN).className =='tit-on') {
			document.getElementById("questTit"+oldN).className='tit-off';
			document.getElementById("quest"+oldN).className='view-off';
		}else{
			document.getElementById("questTit"+curN).className='tit-on';					
			document.getElementById("quest"+curN).className='view-on';
			
			}
	}