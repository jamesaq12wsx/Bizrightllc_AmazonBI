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
function getCommentstat(){
	showLoading();
	var dataStr = $('#reportrange span').text();
	var d = dataStr.split('至');
	var startTime = d[0].trim();
	var endTime = d[1].trim();
	$.ajax({
		url:path+'/sku/Amazon_SKUAnalysis_ReviewAnalysis_RealtimeAnalysis_Commentstat.htm',
		data:{
			asin:asin,
			startTime:startTime,
			endTime:endTime,
			page:page,
			downLoad:1
		},
		type:'post',
		success:function(value){//alert(value)
			closeLoading();
			var data=JSON.parse(value);
			if(data.status=='1'){
				if(data.data.length>0){
					$("#offerListTable tbody").html('')
					 $("#page").paging({
					      pageNo:page,
					      totalPage: Math.ceil(data.total[0].num/50),
					      totalSize: data.total[0].num,
					      callback:function(value){
					    	  page=value;
					    	  getCommentstat();
					      }
					    }) 
					$.each(data.data,function(index){
						$("#offerListTable tbody").append('<tr> <td>'+(this.sku_rwNum=='0'?'-':this.sku_rwNum)+'</td> <td>'+(this.xinzeng_pinglun=='0'?'-':this.xinzeng_pinglun)+'</td> <td>'+(this.day_sku_fiveStarNum=='0'?'-':this.day_sku_fiveStarNum)+'</td> <td>'+(this.day_sku_fourStarNum=='0'?'-':this.day_sku_fourStarNum)+'</td> <td>'+(this.day_sku_threeStarNum=='0'?'-':this.day_sku_threeStarNum)+'</td> <td>'+(this.day_sku_twoStarNum=='0'?'-':this.day_sku_twoStarNum)+'</td> <td>'+(this.day_sku_oneStarNum=='0'?'-':this.day_sku_oneStarNum)+'</td> '+'<td>'+this.main_avaStar+'</td><td>'+this.year_month_day+'</td> </tr>');//<td>'+(this.main_avaStar=='0'?'-':this.main_avaStar)+'</td> 
					})
				}
			}else{
				
			}
		}
	
	})

}
function downLoad(){
	var dataStr = $('#reportrange span').text();
	var d = dataStr.split('至');
	var startTime = d[0].trim();
	var endTime = d[1].trim();
	window.location.href=path+"/sku/Amazon_SKUAnalysis_ReviewAnalysis_RealtimeAnalysis_Commentstat_downLoad.htm?asin="+asin+"&startTime="+startTime+"&endTime="+endTime+"&downLoad=0&page=0";
}
$('#reportrange').change(function(){
	getCommentstat()
})
function showData(){
	$("head").append('<title>'+sku+'</title>')
	Amazon_SKUAnalysis_SkuSearch_Details(asin)
	SKU_Jingpin(asin)
	Amazon_MonitoringOverview_SKU_SimilarProducts(asin)
	getCommentstat()
}
function showJINPINData1(a){
	asin=a;
	Amazon_SKUAnalysis_SkuSearch_Details(asin);
	SKU_Jingpin(asin);
	Amazon_MonitoringOverview_SKU_SimilarProducts(asin);
	getCommentstat();
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
	 getCommentstat()
}
{
	showData()
}