﻿//文档加载完成
$(document).ready(function() {
	
	$(document).keydown(function(event){ 
		if(event.keyCode==13){ 
			$("#login").click(); 
		} 
	});
//	
	$('#login').click(function(){
		$(this).html('登录中...');
		$(this).attr('disabled','disabled');
		$("#myform").submit();
	});
	
	sessionStorage.clear();
});



