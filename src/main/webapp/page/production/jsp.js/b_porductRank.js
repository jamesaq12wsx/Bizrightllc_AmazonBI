﻿function showData(){
	getHotRanking()
}
var page=1;
function getHotRanking(){
	$("#hotRank tbody").html('<tr><td colspan="100">loading....</td></tr>');
	var dataStr = $('#reportrange span').text();
	var d = dataStr.split('至');
	var startTime = d[0].trim();
	var endTime = d[1].trim();
	$.ajax({
		type:'post',
		url:path + '/sku/Amazon_SalesAnalysis_HotRanking.htm',
		data : {
			startTime : startTime,
			endTime : endTime,
			page: page,
			BrandName:$("#brand").val(),
			category:$("#category").val(),
			channel:$("#channel").val(),
			skuasinname_top:$("#condition").val(),
			skuasinname :$("#condition").next().val().split("\n")[0]
		},
		success:function(value){//alert(value)
			var data = JSON.parse(value);
			if (data.status == '1') {
				$("#hotRank tbody").html("")
				if(data.data.length>0){
					$("#page").show();
					$("#page").paging({
					      pageNo:page,
					      totalPage: Math.ceil(data.total[0].num/10),
					      totalSize: data.total[0].num,
					      callback:function(value){
					    	  page=value;
					    	  getHotRanking();
					      }
					    }) 
					    $.each(data.data,function(index){
					    	$("#hotRank tbody").append('<tr><td style="text-align: center;">'+(((page-1)*10)+index+1)+'</td><td><img src="'+this.sku_imageUrl+'" alt=""></td> <td style="text-align: left;">'+this.title+'</td> <td>'+this.xiaoliang+'</td> <td> <div class="progress"> <div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: '+this.allxiaoliang_zhanbi+';"> </div> </div> <span>'+this.allxiaoliang_zhanbi+'</span> </td> </tr>')
					    })
				}else{
					$("#page").hide();
					$("#hotRank tbody").html("")
					$("#hotRank tbody").append('<tr><td colspan="100">暂无数据！</td></tr>');
				}
			}
		}
	})
}
function getSelect(){
	$.ajax({
		url:path + '/sku/getSelect_HotRanking.htm',
		type:"post",
		success:function(value){
			var data = JSON.parse(value);
			if (data.status == '1') {
				$("#brand").append('<option value="">全部</option>')
				$("#category").append('<option value="">全部</option>')
				$("#channel").append('<option value="">全部</option>')
				$.each(data.BrandName,function(index){
					$("#brand").append('<option value="'+this.BrandName+'">'+this.BrandName+'</option>')
				})
				$.each(data.category,function(index){
					$("#category").append('<option value="'+this.category+'">'+this.category+'</option>')
				})
				$.each(data.channel,function(index){
					$("#channel").append('<option value="'+this.channel+'">'+this.channel+'</option>')
				})
			}
		}
	})
}

function setSessionSkuId(skuId){
	alert("先加别的功能！")
	return ;
	var data={};
	data.skuId=skuId;
	sessionStorage.skuId=JSON.stringify(data);
	window.location.href=path+"/b_UserAnalysis.htm";
}

{
	getSelect();
	getHotRanking();
}
$('#reportrange').change(function(){
	getHotRanking();
})