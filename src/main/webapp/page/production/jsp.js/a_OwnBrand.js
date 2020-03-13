﻿var brandname;
function _init(){
	$(".icon-lajitong").unbind()
	$(".icon-lajitong").click(function(){
		  $(".deleteCategory").show();
		  brandname=$(this).attr("name")
		})
		$(".easyAdd").unbind()
		$(".easyAdd").click(function(){
			$("tbody").append('<tr>\
		              <td></td>\
		              <td class="tagName">\
		                <span style="display:none;">Important</span>\
		                <div style="display:inline-block;">\
		                  <input type="text" ><!--\
		               --><span class="save" onclick="addOwnBrand(this)">save</span>\
		                </div>\
		              </td>\
		            </tr>')
		})
}

function getOwnBrand(){
	$.ajax({
		url:path+'/sku/getOwnBrand.htm',
		type:"POST",
		success:function(value){
			var data = JSON.parse(value);
			$("tbody").html("")
			if (data.status == '1') {
				if(data.data.length>0){
					$.each(data.data,function(index){
						$("tbody").append('<tr>\
              <td>\
                <i class="iconfont icon-lajitong" name="'+this.brand+'"></i>\
              </td>\
              <td class="tagName">\
                <span>'+this.brand+'</span>\
                <div>\
                  <input type="text"><!--\
               --><span class="save">save</span>\
                </div>\
              </td>\
            </tr>')
					})
				}else{
					$("tbody").html("")
					$("tbody").append('<tr><th colspan="100">暂无数据</th></tr>')
				}
			}
			_init()
		}
	})
}

function addOwnBrand(obj){
	$.ajax({
		url:path+'/sku/addOwnBrand.htm',
		type:"POST",
		data:{brand:$(obj).prev().val()},
		success:function(value){
			var data = JSON.parse(value);
			if (data.status == '1') {
				showData()
			}
		}
	})
}
function delOwnBrand(){
	$.ajax({
		url:path+'/sku/delOwnBrand.htm',
		type:"POST",
		data:{brand:brandname},
		success:function(value){
			var data = JSON.parse(value);
			if (data.status == '1') {
				$(".deleteCategory").hide()
				showData()
			}
		}
	})
}
$(".deleteBtn").click(function(){
	$(".deleteCategory").hide()
})
function showData(){
	getOwnBrand()
}

{
	showData()
}