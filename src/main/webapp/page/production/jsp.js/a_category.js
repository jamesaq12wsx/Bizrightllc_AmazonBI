function getCategory_parent(){
	$.ajax({
		url:path+'/sku/getCategoryInfo_parent.htm',
		type:"POST",
		data:{parentName:0},
		success:function(value){
			var data = JSON.parse(value);
			if (data.status == '1') {
				$("#RootCategory").html("");
				data.data.forEach(function(value, index, array){
					$("#RootCategory").append(`<li class="${index==0?"on":""}">
	                <div class="rootCategoryList">
	                  <span>${value.Category}</span>
	                  <div>
	                    <i class="iconfont icon-xianshi_bi"></i>
	                    <i class="iconfont icon-shouye_shugang_shijiantixing"></i>
	                    <i class="iconfont icon-lajitong"></i>
	                  </div>
	                  <input type="text"  value="${value.Category}" onchange="updateparentCategory(this)" name="${value.Category}" parentname="${value.parent_Category}">
	                </div>
	              </li>`);
				})
				$("#RootCategory").append(`<li>
                <div class="addRootCategory">+ RootCategory...</div>
                </li>`);
				_init();
				$(".rootCategory li:eq(0)").click();
			}
		}
	})
}
var updateparentCategory=(obj)=>{
	if(!confirm("是否修改！"))return ;
	$.ajax({
		url:path+'/sku/updateParentCategory.htm',
		type:"POST",
		data:{
			newcategory:obj.value,
			category:obj.name,
			parentCategory:$(obj).attr('parentname')
		},
		success:function(value){
			let data=JSON.parse(value);
			if(data.data==1){
				$(obj).parent().find("span").text(obj.value);
			}
		}
	})
}
function getCategory_son(parentName){
	if(!$("#"+parentName.replace(/\s/g,"")).hasClass("subCategoryItem")){
    // category 空格匹配异常修改
    // if(!$("#"+parentName).hasClass("subCategoryItem")){
		$(".subCategory").append('<div class="subCategoryItem" id="'+parentName.replace(/\s/g,"")+'"></div>');
        // category 空格匹配异常修改
        // $(".subCategory").append('<div class="subCategoryItem" id="'+parentName+'"></div>');
		$.ajax({
			url:path+'/sku/getCategoryInfo_parent.htm',
			type:"POST",
			data:{parentName:parentName},
			success:function(value){
				var data = JSON.parse(value);
				if (data.status == '1') {
					data.data.forEach(function(value, index, array){
                        // category 空格匹配异常修改
                        // $("#"+parentName).append(`<div>
						$("#"+parentName.replace(/\s/g,"")).append(`<div>
		                <span>${value.Category}</span>
		                <div>
		                  <i class="iconfont icon-xianshi_bi"></i>
		                  <i class="iconfont icon-shouye_shugang_shijiantixing"></i>
		                  <i class="iconfont icon-lajitong"></i>
		                </div>
		                <input type="text" value="${value.Category}"onchange="updateparentCategory(this)" name="${value.Category}" parentname="${value.parent_Category}">
		              </div>`);
						if(index+1==data.data.length){
							$("#"+parentName.replace(/\s/g,"")).append(`<div class="addSubCategory"  name="${value.parent_Category}" >+ SubCategory...</div>`);
                            // category 空格匹配异常修改
                            // $("#"+parentName).append(`<div class="addSubCategory"  name="${value.parent_Category}" >+ SubCategory...</div>`);
							_init();
						};
					});
					if(data.data.length==0){
						$("#"+parentName.replace(/\s/g,"")).append(`<div class="addSubCategory"  name="${parentName}" >+ SubCategory...</div>`);
						// category 空格匹配异常修改
						// $("#"+parentName).append(`<div class="addSubCategory"  name="${parentName}" >+ SubCategory...</div>`);
						_init();
					}
				}
			}
		})
	}
	$("#"+parentName.replace(/\s/g,"")).show().siblings(".subCategoryItem").hide();
    // category 空格匹配异常修改
    // $("#"+parentName).show().siblings(".subCategoryItem").hide();
}
var deleteparentCategory=(p,s)=>{
	$.ajax({
		url:path+'/sku/deleteparentCategory.htm',
		type:"POST",
		data:{
			category:s,
			parentCategory:p
		},
		success:function(value){
			let data=JSON.parse(value);
			if(data.data==1){
				
				if(p==0){
					cg.parent().parent().parent().remove();
					$("#"+s.replace(/\s/g,"")).remove();
                    // category 空格匹配异常修改
                    // $("#"+s).remove();
				}else{
					cg.parent().parent().remove();
				}
				$(".deleteCategory").hide();
			}
		}
	})
};

var addCategory=(c)=>{
	$.ajax({
		url:path+'/sku/addCategory.htm',
		type:"POST",
		data:{
			category:c,
			parentCategory:parentCategory
		},
		success:function(value){
			let data=JSON.parse(value);
			if(data.data==1){
				if(parentCategory==0){
					$("#RootCategory li:last-child").before(`<li>
	                <div class="rootCategoryList">
	                  <span>${c}</span>
	                  <div>
	                    <i class="iconfont icon-xianshi_bi"></i>
	                    <i class="iconfont icon-shouye_shugang_shijiantixing"></i>
	                    <i class="iconfont icon-lajitong"></i>
	                  </div>
	                  <input type="text"  value="${c}" onchange="updateparentCategory(this)" name="${c}" parentname="0">
	                </div>
	              </li>`);
					$(".subCategory").append('<div class="subCategoryItem" id="'+c.replace(/\s/g,"")+'" style="display: none;"></div>');
                    // category 空格匹配异常修改
                    // $(".subCategory").append('<div class="subCategoryItem" id="'+c+'" style="display: none;"></div>');
					$("#"+c.replace(/\s/g,"")).append(`<div class="addSubCategory"  name="${c}" >+ SubCategory...</div>`);
                    // category 空格匹配异常修改
                    // $("#"+c).append(`<div class="addSubCategory"  name="${c}" >+ SubCategory...</div>`);
				}else{
                    // category 空格匹配异常修改
                    // $("#"+parentCategory).prepend(`<div>
					$("#"+parentCategory.replace(/\s/g,"")).prepend(`<div>
	                <span>${c}</span>
	                <div>
	                  <i class="iconfont icon-xianshi_bi"></i>
	                  <i class="iconfont icon-shouye_shugang_shijiantixing"></i>
	                  <i class="iconfont icon-lajitong"></i>
	                </div>
	                <input type="text" value="${c}"onchange="updateparentCategory(this)" name="${c}" parentname="${parentCategory}">
	              </div>`);
				}
				
			};
			$(".addCategoryModel").hide();
			_init();
		}
	})
};
$("#deleteCategory").click(function(){
	deleteparentCategory($("#deleteCategory").attr("p"),$("#deleteCategory").attr("s"))
});
var cg;
var parentCategory;
function _init(){
	$(".rootCategory li").unbind();
	$(".rootCategory li").click(function() {
	    var index = $(this).index();
	    $(this).addClass("on").siblings("li").removeClass("on");
	    $(".subCategoryItem:eq(" + index + ")").show().siblings(".subCategoryItem").hide();
	    getCategory_son($(this).find("span").text())
	  })


	$(".subCategoryItem>div:not('.addSubCategory'),.rootCategoryList").hover(function(){
	  $(this).children("span,input").hide();
	  $(this).addClass("editCategory");
	  $(this).children("div").show();
	  $(".icon-xianshi_bi").click(function(){
	    $(this).parent().parent().removeClass("editCategory").addClass("modifyCategory");
	    $(this).parent().parent().children("span,div").hide();
	    $(this).parent().parent().children("input").show();
	  })
	  $(".icon-lajitong").unbind();
	  $(".icon-lajitong").click(function(){
		  cg=$(this);
		  $("#deleteCategory").attr("p",$(this).parent().next().attr("parentname"));
		  $("#deleteCategory").attr("s",$(this).parent().next().attr("name"));
		  $(".deleteCategory").show();
	  })
	},function(){
	  $(this).children("div,input").hide();
	  $(this).removeClass("editCategory modifyCategory");
	  $(this).children("span").show();
	})
	$(".addSubCategory").unbind();
	$(".addSubCategory").click(function(){
	  parentCategory=$(this).attr("name")
	  $(".addCategoryModel").show();
	})
	$(".icon-chanpinshanchu").unbind();
	$(".icon-chanpinshanchu").click(function(){
	  $(".addCategoryModel").hide();
	})
	$(".addRootCategory").unbind();
	$(".addRootCategory").click(function(){
	  parentCategory=0;
	  $(".addCategoryModel").show();
	  $(".addCategoryModel h4").html("Add RootCategorys");
	  $(".addCategoryModel .categoryTitle").html("RootCategorys:");
	})
}
$(".deleteBtn").click(function(){
	$(".deleteCategory").hide();
});
$("#addcategory").click(function(){
	$(".addCategoryModel").find("textarea").val().split("\n").forEach(function(value, index, array){
		addCategory(value)
	})
});
$(".deleteBtn").click(function(){
	$(".addCategoryModel").hide()
});
{
	getCategory_parent();
}
