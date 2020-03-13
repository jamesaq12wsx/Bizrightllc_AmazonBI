$.extend($.fn.datagrid.methods, {
      editCell: function(jq,param){
        return jq.each(function(){
          var opts = $(this).datagrid('options');
          var fields = $(this).datagrid('getColumnFields',true).concat($(this).datagrid('getColumnFields'));
          for(var i=0; i<fields.length; i++){
            var col = $(this).datagrid('getColumnOption', fields[i]);
            col.editor1 = col.editor;
            if (fields[i] != param.field){
              col.editor = null;
            }else{
            	if(param.field=="SubCategory"){
            		console.log(JSON.stringify(col.editor));
            		col.editor={"type":"combobox","options":{"valueField":"Category","textField":"Category","method":"POST","queryParams":{"parentName":RootCategory},"url":"/AmazonDataSystemV2/sku/getParentCategoryInfo.htm"}};
            	}
            }
          }
          $(this).datagrid('beginEdit', param.index);
          for(var i=0; i<fields.length; i++){
            var col = $(this).datagrid('getColumnOption', fields[i]);
            col.editor = col.editor1;
          }
        });
      }
    });
    
    var editIndex = undefined;
    var _field = undefined;
    function endEditing(){
      if (editIndex == undefined){return true}
      if ($('#dg').datagrid('validateRow', editIndex)){
    	  if(_field=="productManager_id"){
    		  var ed = $('#dg').datagrid('getEditor', {index:editIndex,field:"productManager_id"});
        	  if(ed!=null){
        	    	var username = $(ed.target).combobox('getText');
        			$('#dg').datagrid('getRows')[editIndex]['productManager'] = username;
    	  }
    	}
        $('#dg').datagrid('endEdit', editIndex);
        RootCategory=$('#dg').datagrid('getRows')[editIndex]['RootCategory'];
        addEditRows();
        editIndex = undefined;
        return true;
      } else {
        return false;
      }
    }
function addEditRows(){
	let data=JSON.parse(_data);
	let updated=$('#dg').datagrid('getChanges', "updated");
	updated.forEach(function(value,index,array){
		if(value.asin!=""&& value.asin!=null && value.asin!=undefined){
            var pageNum=$('#dg').datagrid('getPager',value).data("pagination").options.pageNumber;
            var pageSize=$('#dg').datagrid('getPager',value).data("pagination").options.pageSize;
			updateRow(data[(pageNum-1)*pageSize+$('#dg').datagrid('getRowIndex',value)],value);
		}else{
			alert("asin不能为空！")
		}
	})
	let inserted=$('#dg').datagrid('getChanges', "inserted");
	inserted.forEach(function(value,index,array){
		if(value.asin!=""&& value.asin!=null && value.asin!=undefined){
		    // 默认值设置
            if( value.ProductLine==null || value.ProductLine==undefined) {
                value.ProductLine="";
            }
			insertRow(value);
		}else{
			alert("asin不能为空！")
		}
	})
}

function updateRow(oldRow,newRow){
	$.ajax({
		url:path+'/sku/updateProduct_skuInfo.htm',
		type:"POST",
		data:{
			oldRow:JSON.stringify(oldRow),
			newRow:JSON.stringify(newRow),
		},
		success:function(value){
			var data = JSON.parse(value);
			if (data.status == '1') {
				console.log("修改成功!")
			}
		}
	})
}
function insertRow(addRow){
	$.ajax({
		url:path+'/sku/insertProduct_skuInfo.htm',
		type:"POST",
		data:addRow,
		success:function(value){
			var data = JSON.parse(value);
			if (data.status == '1') {
				//showData();
			}
		}
	})
}
var RootCategory="";
function onClickCell(index, field){
	  RootCategory=$('#dg').datagrid('getRows')[index]['RootCategory'];
      if (endEditing()){
    	_field=field;
        $('#dg').datagrid('selectRow', index).datagrid('editCell', {index:index,field:field});
        editIndex = index;
      }
}
function append(){
            if (endEditing()){
                $('#dg').datagrid('appendRow',{status:'P'});
                /*editIndex = $('#dg').datagrid('getRows').length-1;
                $('#dg').datagrid('selectRow', editIndex)
                        .datagrid('beginEdit', editIndex);*/
            }
        }



function endEditing1(){
      if (editIndex == undefined){return true}
      if ($('#dg1').datagrid('validateRow', editIndex)){
        $('#dg1').datagrid('endEdit', editIndex);
        editIndex = undefined;
        return true;
      } else {
        return false;
      }
    }
function onClickCell1(index, field){
      if (endEditing1()){
        $('#dg1').datagrid('selectRow', index)
            .datagrid('editCell', {index:index,field:field});
        editIndex = index;
      }
    }
function append1(){
            if (endEditing()){
                $('#dg1').datagrid('appendRow',{status:'P'});
                /*editIndex = $('#dg').datagrid('getRows').length-1;
                $('#dg').datagrid('selectRow', editIndex)
                        .datagrid('beginEdit', editIndex);*/
            }
}

$(function() {// 初始化内容
      $(".easyui-fluid:eq(1)").css("display","none");
      $(".easyAdd:eq(1)").css("display","none");
});

$(".productBtn").click(function(){
  $(".easyui-fluid:eq(0)").show();
  $(".easyui-fluid:eq(1)").hide();
  $(".easyAdd:eq(0)").show().siblings("span.easyAdd").hide();
  $(this).addClass("on").siblings().removeClass("on");
})
$(".productMBtn").click(function(){
  $(".easyui-fluid:eq(1)").show();
  $(".easyui-fluid:eq(0)").hide();
  $(".easyAdd:eq(1)").show().siblings("span.easyAdd").hide();
  $(this).addClass("on").siblings().removeClass("on");
})

$('.datagrid-view2 td:eq(5)').click(function(){
  console.log(1);
  location.href='a_ProductCompetitior.html';
})
var _data;
function getSkuInfo_new(v){
	$("#dg").datagrid({
		url: path+"/sku/getSkuInfo_new.htm",
		method:"POST",
		queryParams:{
			top:v._top,
			keyword:v.input_value,
			brand:v.brand,
			SubCategory:v.category,
			tag:v.tag,
		},
		onLoadSuccess: function (data) {
			_data=JSON.stringify(data.originalRows);
            console.log(data);
            let h=$("body").height();
			let h2=$(window).height()
			if(h>h2){
			  $(".nav,.navSKU,.compareBox").height(h);
			}else{
			  $(".nav,.navSKU,.compareBox").height(h2);
			}
        },
        loadFilter:pagerFilter
	})
}
function showData(){
	editIndex = undefined;
    _field = undefined;
	getSkuInfo_new(getParameter())
}

function formatdate(val,row){
	 var date = new Date(val);
     return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate()+" "+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
}


function pagerFilter(data){
    if (typeof data.length == 'number' && typeof data.splice == 'function'){    // 判断数据是否是数组
        data = {
            total: data.length,
            rows: data
        }
    }
    var dg = $(this);
    var opts = dg.datagrid('options');
    var pager = dg.datagrid('getPager');
    pager.pagination({
        onSelectPage:function(pageNum, pageSize){
            opts.pageNumber = pageNum;
            opts.pageSize = pageSize;
            pager.pagination('refresh',{
                pageNumber:pageNum,
                pageSize:pageSize
            });
            dg.datagrid('loadData',data);
        },
    onRefresh:showData
    }
    );
    if (!data.originalRows){
        data.originalRows = (data.rows);
    }
    var start = (opts.pageNumber-1)*parseInt(opts.pageSize);
    var end = start + parseInt(opts.pageSize);
    data.rows = (data.originalRows.slice(start, end));
    return data;
}
function formatCompetitor(val,row){
	return '<a href="'+path+'/a_ProductCompetitior.htm?asin='+row.asin+'&sku='+row.ourCode+'" class="editCompetitons">edit</a>'
}
$(document).ready(function(){
	loadTag();
	getBrand();
	showData()
})
