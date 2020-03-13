/**
 * 页面加载执行
 */
$(document).ready(function () {
    sessionStorage.moduleType = '/c_skuBestSellersTop.htm';

    showBestSellerCategory();

});

/**
 * loading Best Seller Category List
 */
    function showBestSellerCategory() {
    $("#bs-left-col-category").jstree({
        "core": {
            "themes": {
                "responsive": false
            },
            // so that create works
            "check_callback": true,
            'data': function (obj, callback) {
                var jsonstr = "[]";
                var jsonarray = eval('(' + jsonstr + ')');
                $.ajax({
                    type: "POST",
                    url: path + '/sku/Amazon_MonitoringOverview_BestSellerCategory.htm',
                    dataType: "json",
                    async: false,
                    success: function (result) {
                        var arrays = result.data;
                        for (var i = 0; i < arrays.length; i++) {
                            console.log(arrays[i])
                            var arr = {
                                "id": arrays[i].id,
                                "parent": arrays[i].parent == "Any Department" || arrays[i].parent == null || arrays[i].parent == "" ? "#" : arrays[i].parent,
                                "text": arrays[i].text
                            }
                            jsonarray.push(arr);
                        }
                    }
                });
                callback.call(this, jsonarray);
            }
        },
    });
}

/**
 * 树节点左键相应函数（监听）
 */
$('#bs-left-col-category').on("select_node.jstree", function (node, selected, event) {
    //当前点击的对象的id
    // alert(selected.node.id);
    // var day = new Date();
    // day.setTime(day.getTime());
    // var datstr = day.getFullYear()+"-" + (day.getMonth()+1) + "-" + day.getDate();
    var datstr =$("#CrawlDate").val();
    getBestSellerTop100Data(selected.node.id,datstr);
})


/**
 * 查询当前类目下的top数据
 */
function getBestSellerTop100Data(CategoryId, CrawlDate) {
    showLoading();
    if ($('#table').hasClass('dataTable')) {
        let dttable = $('#table').dataTable();
        dttable.fnClearTable(); //清空一下table
        dttable.fnDestroy(); //还原初始化了的datatable
    }

    var myjson = {};
    myjson.CategoryId = CategoryId;
    myjson.CrawlDate = CrawlDate;

    $.ajax({
        url: path + '/sku/Amazon_MonitoringOverview_BestSellerTop.htm',
        type: "POST",
        data: myjson,
        dataType: 'json',
        async: false,
        success: function (value) {
            closeLoading();
            var data = value;
            if (data.status == '1') {
                $("#table tbody").html('');
                if (data.data.length > 0) {
                    $("#CrawlDate").text(data.data[0].CrawlDate);
                    $.each(data.data, function (index, value) {
                        $("#table tbody").append(`<tr> 
	                <td>
	                  <span class="addIcon" data-value="0" name="${value.Asin}"><i class="iconfont icon-jia"></i></span>
	                </td>
	                <td style="display: none">${value.CrawlId}</td>
	                <td>
	                  <img src="${value.Image}" alt="">
	                </td> 
	                <td style="text-align: left;">${value.Name}</td> 
	                <td>${value.Asin}</td>
	                <td>${value.Range}</td>c
	                <td>${value.Price}</td>
	                <td>${value.Rating}</td>
	                <td>${value.Reviews}</td>
	                <td>${value.Seller}</td>
	                <td>${value.IsPrime == 1 ? "是" : "否"}</td> 
	                <td>${value.CrawlDate}</td> 
	              </tr>`);
                    });
                    if ($.fn.dataTable.isDataTable('#table')) {
                        $('#table').DataTable();
                    } else {
                        $('#table').on('page.dt', function () {
                        }).dataTable({
                            bLengthChange: false,
                            "destroy": true,
                            searching: false,
                            iDisplayLength: 50,
                            "drawCallback": function (settings) {
                                _init_Iconclick();
                            }
                        });
                    }
                } else {
                    $("#table tbody").html('');
                    $("#table tbody").html('<tr><td colspan="100">暂无排行数据！</td></tr>');
                }

            }
        }
    })
}

/**
 * 查询当前类目下的top数据对应的详细Listing数据
 */
function getBestSellerTop100DetailData(obj,Asin,CrawlId,CrawlDate) {
    var myjson={};
    myjson.Asin=Asin;
    myjson.CrawlId=CrawlId;
    myjson.CrawlDate=CrawlDate;
    $.ajax({
        url:path+'/sku/Amazon_MonitoringOverview_BestSellerTopDetail.htm',
        type:"POST",
        data:myjson,
        dataType:'json',
        success:function(value){
            var data = value;
            if (data.status == '1') {
                if(data.data.length>0){
                    $("."+Asin).remove();
                    $.each(data.data,function(index,value){
                        obj.parent().parent().after(`<tr class="compareTr ${value.PrimaryAsin}"> 
                        <td>
                        </td>
                        <td style="display: none">${value.CrawlId}</td>
                        <td>
                          <img src="${value.Image}" alt="">
                        </td> 
                        <td style="text-align: left;">${value.Property}</td> 
                        <td>${value.ListingAsin}</td>
                        <td></td>
                        <td>${value.Price}</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td> 
                        <td>${value.CrawlDate}</td> 
                      </tr>`);
                    })
                }else{
                    obj.parent().parent().after('<tr class="tip '+Asin+'"><td colspan="100">没有Listing</td></tr>');
                }
            }
        }
    })
}

/**
 * 初始化按钮功能
 * @private
 */
function _init_Iconclick(){
    /**
     * 展开按钮功能
     */
    $(".addIcon").unbind();
    $(".addIcon").click(function () {
        var flag = $(this).attr("data-value");
        var tr=$(this).closest("tr");
        var Asin=tr.find("td:eq(4)").text();
        var CrawlId=tr.find("td:eq(1)").text();
        var CrawlDate=tr.find("td:eq(11)").text();

        if (flag == 0) {
            if($(this).parent().parent().next().attr("class")!="compareTr"){
                getBestSellerTop100DetailData($(this),Asin,CrawlId,CrawlDate);
            }else{
                $("."+$(this).attr("name")).show();
            }
            $(this).html('<i class="iconfont icon-jian"></i>');
            $(this).attr("data-value", "1");
        } else {
            $("." + $(this).attr("name")).hide();
            $(this).html('<i class="iconfont icon-jia"></i>');
            $(this).attr("data-value", "0");
        }
    })
}

/**
 * 时间插件改变触发
 */
$("#CrawlDate").change(function(){
    if ($('#bs-left-col-category').jstree(true).get_selected(true)[0]!=null) {
        var treeNode = $('#bs-left-col-category').jstree(true).get_selected(true)[0]; //获取所有选中的节点对象
        var nodeId = treeNode.id;
        var datstr =$("#CrawlDate").val();
        getBestSellerTop100Data(nodeId,datstr);
    }
})
