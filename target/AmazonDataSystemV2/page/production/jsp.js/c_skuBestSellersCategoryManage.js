var IsMonitoringData = [{"value":"Y","text":"是"},{"value":"N","text":"否"}];//json格式

/**
 * 页面加载执行
 */
$(document).ready(function () {
    sessionStorage.moduleType = '/c_skuBestSellersCategoryManage.htm';

    showData();

});


var _data;
/**
 * 获取目录基本信息
 * @param v
 * @constructor
 */
function Amazon_MonitoringOverview_BestSellerCategory(v){
    $("#dg").datagrid({
        url: path+"/sku/Amazon_MonitoringOverview_BestSellerCategory.htm",
        method:"POST",
        onLoadSuccess: function (data) {
            _data=JSON.stringify(data.data);
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

/**
 * 展示数据
 */
function showData(){
    editIndex = undefined;
    _field = undefined;
    Amazon_MonitoringOverview_BestSellerCategory(getParameter())
}


/**
 * 分页函数
 * @param data
 * @returns {*}
 */
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
    if (!data.data){
        data.data = (data.rows);
    }
    var start = (opts.pageNumber-1)*parseInt(opts.pageSize);
    var end = start + parseInt(opts.pageSize);
    data.rows = (data.data.slice(start, end));
    return data;
}

/**
 * 监听列表字段点击时间
 * @param index
 * @param field
 */
function onClickCell(index, field){
    if (endEditing()){
        _field=field;
        $('#dg').datagrid('selectRow', index).datagrid('editCell', {index:index,field:field});
        editIndex = index;
    }
}

/**
 * 编辑结束
 * @returns {boolean}
 */
function endEditing(){
    if (editIndex == undefined){return true}
    if ($('#dg').datagrid('validateRow', editIndex)){
        $('#dg').datagrid('endEdit', editIndex);
        CategoryId=$('#dg').datagrid('getRows')[editIndex]['CategoryId'];
        updateRow();
        editIndex = undefined;
        return true;
    } else {
        return false;
    }
}

/**
 * 更新记录
 * @param oldRow
 * @param newRow
 */
function updateRow(oldRow,newRow){
    $.ajax({
        url:path+'/sku/Amazon_MonitoringOverview_UpdBestSellerCategory.htm',
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
