$(".tab>li").click(function(){
      var index=$(this).index();
      $(this).addClass("on").siblings("li").removeClass("on");
      $(".tabCt:eq("+index+")").show().siblings(".tabCt").hide();
      if($(".tabCt:eq("+index+")").height()+103>$(".navSKU").height()){
          $(".nav,.navSKU,.compareBox").height($(".navSKU").height());
          $(".compareBox .tabCt").height(($(".navSKU").height()-200));
          $(".compareBox .tabCt").css({"width":"190px","overflow-y":"auto"});
      }
});
/*------------------------------------------------------------------------------------------------------------------------------*/
var asin=sessionStorage.asin;//B00NN2RYHY
var sku=sessionStorage.sku_fx;
var page=1;
function getCommentDetails(obj){
	showLoading();
	if(obj!=123){
		$('.commentBox').html('');
	}else{
		page++;
	}
	var star=""
	if($("#checkbox5").prop('checked')){
		star=star+'5.0,'
	}
	if($("#checkbox4").prop('checked')){
		star=star+'4.0,'
	}
	if($("#checkbox3").prop('checked')){
		star=star+'3.0,'
	}
	if($("#checkbox2").prop('checked')){
		star=star+'2.0,'
	}
	if($("#checkbox1").prop('checked')){
		star=star+'1.0'
	}
	if(star==""){
		alert("至少选择一个！");
		$("#checkbox"+obj.name).prop('checked',true);
		return false;
	}
	if($("#checkbox_sku").prop('checked')){
		asin=""
	}else{
		asin=sessionStorage.asin;
	}
	
	var dataStr = $('#reportrange span').text();
	var d = dataStr.split('至');
	var startTime = d[0].trim();
	var endTime = d[1].trim();
	$.ajax({
		url:path+'/sku/Amazon_SKUAnalysis_ReviewAnalysis_CommentDetails.htm',
		data:{

			asin:asin,
			startTime:startTime,
			endTime:endTime,
			page:page,
			star:star,
			realBuy:$("#check2").find('option:selected').val(),
			lately:$("#check3").find('option:selected').val(),
			
		},
		type:'post',
		success:function(value){//alert(value)
			closeLoading();
			var data=JSON.parse(value);
			if(data.status=='1'){
				
				if(data.data.length>0){
					
					
					if(data.data.length<10){
						$("#jzgd").hide();
					}else{
						$("#jzgd").show();
					}
					if(obj!=123){
						$('.commentBox').html('');
					}else{
						page++;
					}
					$.each(data.data,function(index){
						$('.commentBox').append('<div class="commentList"> '+
								'<div class="commentList-a">'+
								'<i class="a-icon a-icon-user"></i>'+
								'<span class="userName">'+this.customerName+'</span>'+
								'<i class="a-icon a-icon-help"></i>'+
								'<span class="userHelp">有帮助：'+this.helpefulNum+'</span>'+
								'</div>'+
								'<div class="commentList-b">'+
								checkStar(this.star)+
								'<span class="userDsc">'+this.reviewTitle+'</span>'+
								'</div> <div class="commentList-c">'+
								'<span class="commentTime">'+this.inserttime+'</span>'+
								checkVerifiedPurchase(this.verifiedPurchase)
								+
								'</div> <div class="commentList-d">'+
								'<span>'+
								this.reviewContent
								+
								'</span>'+
								'</div> <div class="commentList-e">'+
								'<img src="'+this.reviewImageUrl+'" alt=""> </div> </div>')
					})
				}else{
					$('.commentBox').html("")
					$('.commentBox').append('<div class="commentList" style="text-align:center;">暂无评论！</div>');
					$("#jzgd").hide();
				}
			}else{
				
			}
		}
	
	})

}
function checkVerifiedPurchase(val){
	if(val==1){
		return '<i class="a-icon a-icon-label a-label-VerifiedPurchase"></i>'
	}else{
		return ''
	}
}
function checkStar(val){
	var x=parseFloat(val);
	if(Math.round(x*2)%2>0){
		return ' <i class="a-icon a-icon-star a-star-'+parseInt(Math.round(x*2)/2)+'-'+5+'"></i>'
	}else{
		return ' <i class="a-icon a-icon-star a-star-'+parseInt(Math.round(x*2)/2)+'"></i>'
	}
	
}
function downLoad2(){
	var star=""
		if($("#checkbox5").prop('checked')){
			star=star+'5.0,'
		}
		if($("#checkbox4").prop('checked')){
			star=star+'4.0,'
		}
		if($("#checkbox3").prop('checked')){
			star=star+'3.0,'
		}
		if($("#checkbox2").prop('checked')){
			star=star+'2.0,'
		}
		if($("#checkbox1").prop('checked')){
			star=star+'1.0'
		}
		
		if($("#checkbox_sku").prop('checked')){
			asin=""
		}else{
			asin=sessionStorage.asin;
		}
		var dataStr = $('#reportrange span').text();
		var d = dataStr.split('至');
		var startTime ="&startTime="+ d[0].trim();
		var endTime = "&endTime="+d[1].trim();
		var realBuy="&realBuy="+$("#check2").find('option:selected').val();
		var lately="&lately="+$("#check3").find('option:selected').val();
		let asi="&asin="+asin;
	window.location.href=path+"/sku/Amazon_SKUAnalysis_ReviewAnalysis_CommentDetails_downLoad.htm?star="+star+asi+startTime+endTime+realBuy+lately+"&page=-1";
}
$('#reportrange').change(function(){
	getCommentDetails()
})
$("#checkbox_sku").change(function(){
	getCommentDetails();
})
function showData(){
	if (asin==null) {
		if(getURLParameter('asin')!=null){
			sessionStorage.asin=getURLParameter('asin');
			asin=sessionStorage.asin;
			sessionStorage.sku_fx=getURLParameter('sku');
			sku=sessionStorage.sku_fx;
		}
	}
	$("head").append('<title>'+sku+'</title>')
	Amazon_SKUAnalysis_SkuSearch_Details(asin)
	SKU_Jingpin(asin)
	Amazon_MonitoringOverview_SKU_SimilarProducts(asin)
	$("#checkbox_sku").prop('checked',true)
	//$("#checkbox_sku").parent().hide();
	getCommentDetails()	
}
function showJINPINData(a,obj){
	if(hasClass(obj, "xz")){
		$(obj).removeClass("xz");
		return ;
	}
	if($(".xz").size()>0){
 		//alert("至多选择两个！");
 		return ;
 	}else{
 		$(obj).addClass("xz");
 	}
	 asin=a;
	 Amazon_SKUAnalysis_SkuSearch_Details(asin)
	 $("#checkbox_sku").prop('checked',false)
	 $("#checkbox_sku").parent().hide();
	 getCommentDetails()	
}
function showJINPINData1(a){
	 asin=a;
	 Amazon_SKUAnalysis_SkuSearch_Details(asin)
	 $("#checkbox_sku").prop('checked',false)
	 $("#checkbox_sku").parent().hide();
	 getCommentDetails()	
}
{
	showData()
}