var label_Name="";
function _init(){
$(".icon-lajitong").unbind();
$(".icon-lajitong").click(function(){
  $(".deleteCategory").show();
  label_Name=($(this).parent().prev().prev().find("input").val());
})
$(".icon-chanpinshanchu").unbind();
$(".icon-chanpinshanchu").click(function(){
  $(".addCategoryModel").hide();
})
$(".icon-xianshi_bi").unbind();
$(".icon-xianshi_bi").click(function(){
	$(this).parent().parent().find(".tagName>div").show();
	$(this).parent().parent().find(".tagName>span").hide();
})
$(".save").unbind();
$(".save").click(function(){
	$(this).parent().parent().find("div").hide();
	$(this).parent().parent().find("span").show();
	Tolabel($(this).attr("name"),$(this).prev().val())
})
$(".easyAdd").unbind();
$(".easyAdd").click(function(){
	$("tbody").append('<tr>\
              <td class="tagName">\
                <span style="display:none">New</span>\
                <div style="display:block">\
                  <input type="text"><!--\
               --><span class="save" name="">save</span>\
                </div>\
              </td>\
              <td><span class="blue"></span></td>\
              <td>\
                <i class="iconfont icon-xianshi_bi"></i>\
                <i class="iconfont icon-shouye_shugang_shijiantixing"></i>\
                <i class="iconfont icon-lajitong"></i>\
              </td>\
            </tr>')
            _init()
})
}


/*------------------------------------------------------*/
function getLable(){
	$.ajax({
		type:"POST",
		url:path+"/sku/new_getLable.htm",
		success:function(value){
			var data = JSON.parse(value);
			if (data.status == '1') {
				$("#table tbody").html("")
				$.each(data.data,function(index,value){
					$("#table tbody").append(`<tr>
		              <td class="tagName">
		                <span>${value.labelName}</span>
		                <div>
		                  <input type="text" value="${value.labelName}"><!--
		               --><span class="save" name="${value.labelName}">save</span>
		                </div>
		              </td>
		              <td><span class="blue">${value.num}</span></td>
		              <td>
		                <i class="iconfont icon-xianshi_bi"></i>
		                <i class="iconfont icon-shouye_shugang_shijiantixing"></i>
		                <i class="iconfont icon-lajitong"></i>
		              </td>
		            </tr>`)
				})
				_init();
			}
		}
	})
}
function Tolabel(n,o){
	if(n==""){
		addlable(n,o)
	}else{
		editlabel(n,o)
	}
}
function addlable(n,o){
	$.ajax({
		type:"POST",
		data:{
			NewlabeName:o,
			labelName:n
		},
		url:path+"/sku/new_addlable.htm",
		success:function(value){
			var data = JSON.parse(value);
			if (data.status == '1') {
				getLable()
			}
		}
	})
}
function editlabel(n,o){
	$.ajax({
		type:"POST",
		data:{
			NewlabeName:o,
			labelName:n
		},
		url:path+"/sku/new_editlabel.htm",
		success:function(value){
			var data = JSON.parse(value);
			if (data.status == '1') {
				getLable()
			}
		}
	})
}
$(".deleteBtn").click(function(){
	$(".deleteCategory").hide();
})
$(".comfireBtn").click(function(){
	$.ajax({
		type:"POST",
		data:{
			labelName:label_Name
		},
		url:path+"/sku/new_deletelabel.htm",
		success:function(value){
			var data = JSON.parse(value);
			if (data.status == '1') {
				$(".deleteCategory").hide();
				getLable()
			}
		}
	})
})
{
	getLable()
}