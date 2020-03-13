/**
 * JS文档加载完成调用
 */
$(document).ready(function () {
    sessionStorage.moduleType = '/f_Report_Promotions.htm';

    getList();

    $('#down').click(function(){
        var dataStr = $('#reportrange span').text();
        var d = dataStr.split('至');
        var startTime = d[0].replace("-","").replace("-","").trim();
        var endTime = d[1].replace("-","").replace("-","").trim();
        addTask(startTime,endTime);
    });

});

var taskId = "";
var downloadFilePath="";
var downloadFileName="";

function _init() {
    // 删除按钮
    // $(".icon-lajitong").unbind();
    // $(".icon-lajitong").click(function () {
    //     taskId = ($(this).parent().prev().prev().prev().prev().prev().prev().find("span").text());
    //     taskSts = ($(this).parent().prev().prev().find("span").text());
    //     $(".deleteCategory").show();
    // })
    // 下载图标点击
    $(".icon-download").unbind();
    $(".icon-download").click(function () {
        taskId = ($(this).parent().prev().prev().prev().prev().prev().find("span").text());
        downloadFilePath = ($(this).parent().next().find("span").text());
        downloadFileName = ($(this).parent().prev().prev().prev().find("span").text());
        $("#downloadMessage").show();
    })

}


function getList() {
    $.ajax({
        type: "POST",
        url: path + "/report/QryPromotionList.htm",
        success: function (value) {
            var data = JSON.parse(value);
            if (data.status == '1') {
                $("#table tbody").html("")
                $.each(data.data, function (index, value) {
                    $("#table tbody").append(`<tr>
		              <td class="taskId" value="${value.task_id}"><span>${value.task_id}</span></td>
		              <td class="taskName" ><span class="blue">${value.task_name}</span></td>
		              <td><span class="blue">${value.download_file_name}</span></td>
		              <td><span class="blue">${value.task_sts}</span></td>
		              <td><span class="blue">${value.insert_time}</span></td>
		              <td>
		                <i class="iconfont icon-download"></i>
		                <i class="iconfont icon-shouye_shugang_shijiantixing"></i>
		                <i class="iconfont icon-lajitong"></i>
		              </td>
		              <td style="width: 0%" hidden="hidden"><span class="blue">${value.download_file_path}</span></td>
		            </tr>`)
                })
                _init();
            }
        }
    })
}

function addTask(dateFrom,dateTo) {
    $.ajax({
        type: "POST",
        data: {
            dateFrom: dateFrom,
            dateTo: dateTo
        },
        url: path + "/report/generatePromotionTask.htm",
        success: function (value) {
            var data = JSON.parse(value);
            if (data.status == '1') {
                getList();
            } else {
                alert('添加失败');
            }
        },
        error : function() {
            alert('请求失败');
        }
    })
}

$("#downloadDeleteBtn").click(function () {
    $("#downloadMessage").hide();
})
$('#downloadComfireBtn').click(function () {
    window.open( path + "/report/toPromotionDownload.htm?taskId=" + taskId);
    $("#downloadMessage").hide();
    getList();
});






