﻿var c_Asin;
function _init(){
	$(".icon-lajitong").unbind();
	$(".icon-lajitong").click(function(){
		  $(".deleteCategory").show();
		  c_Asin=($(this).parent().next().text())
		})
$(".easyAdd").unbind();
		$(".easyAdd").click(function(){
			$(".addCategoryModel").show();
		})
$(".icon-chanpinshanchu").unbind();
		$(".icon-chanpinshanchu").click(function(){
		  $(".addCategoryModel").hide();
		})
		$(".deleteBtn").unbind();
		$(".deleteBtn").click(function(){
			$(".addCategoryModel").hide();
			$(".deleteCategory").hide();
		})
}

$("#add").click(function(){
	$("#jp").val().split("\n").forEach(function(value, index, array){
		$.ajax({
			url:path+'/sku/addSkuCompareInfo.htm',
			type:"POST",
			data:{
				asin:getURLParameter('asin'),
				compareOurAsin:value
			},
			success:function(value){
				var data = JSON.parse(value);
				if (data.status == '1') {
					$(".addCategoryModel").hide();
					getData();
				}
			}
		})
	})
})
$("#del").click(function(){
	$.ajax({
		url:path+'/sku/delSkuCompareInfo.htm',
		type:"POST",
		data:{
			asin:getURLParameter('asin'),
			compareOurAsin:c_Asin
		},
		success:function(value){
			var data = JSON.parse(value);
			if (data.status == '1') {
				$(".deleteCategory").hide();
				getData();
			}
		}
	})
})
function getData(){
	$.ajax({
		url:path+'/sku/Amazon_MonitoringOverview_ProductJingping.htm',
		type:"POST",
		data:{
			asin:getURLParameter('asin')
		},
		success:function(value){
			var data = JSON.parse(value);
			$("#table tbody").html("")
			if (data.status == '1') {
				if(data.data.length>0){
					$.each(data.data,function(index){
						$("#table tbody").append('<tr>\
					              <td>\
					                <i class="iconfont icon-lajitong"></i>\
					              </td>\
					              <td>'+this.compareOurAsin+'</td>\
					              <td class="tagName">\
					              '+this.sku_productTitle+'\
					              </td>\
					            </tr>')
					})
				}else{
					$("#table tbody").append('<tr><td colspan="100">暂无竞品！</td></tr>')
				}
				_init();
			}
		}
	})
}

{
	$(".currentSku").html('<b>Sku:</b>'+getURLParameter('sku'))
	getData()
}