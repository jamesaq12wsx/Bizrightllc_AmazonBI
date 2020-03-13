/**
 * JS文档加载完成调用
 */
$(document).ready(function () {
    sessionStorage.moduleType = '/f_Report_HawProductInfo.htm';

    getTask();
});

var taskId = "";
var taskSts = "";
var downloadFilePath="";
var downloadFileName="";

function _init() {
    // 任务添加或修改保存按钮
    $(".save").unbind();
    $(".save").click(function () {
        $(this).parent().parent().find("div").hide();
        $(this).parent().parent().find("span").show();
        ToTask($(this).attr("name"), $(this).prev().val())
    })
    // 简易添加按钮
    $(".easyAdd").unbind();
    $(".easyAdd").click(function () {
        $("tbody").append('<tr>\
                <td class="taskId" value=""><span></span></td>\
                <td class="taskName">\
                    <span style="display:none">New</span>\
                    <div style="display:block">\
                        <input type="text">\
                        <span class="save" name="">save</span>\
                    </div>\
                </td>\
                <td><span class="blue"></span></td>\
                <td><span class="blue"></span></td>\
                <td><span class="blue"></span></td>\
                <td><span class="blue"></span></td>\
                <td></td>\
                <td style="width: 0%" hidden="hidden"><span class="blue"></span></td>\
                <td style="width: 0%" hidden="hidden"><span class="blue"></span></td>\
            </tr>')
        _init()
    })
    // 删除按钮
    $(".icon-lajitong").unbind();
    $(".icon-lajitong").click(function () {
        taskId = ($(this).parent().prev().prev().prev().prev().prev().prev().find("span").text());
        taskSts = ($(this).parent().prev().prev().find("span").text());
        $(".deleteCategory").show();
    })
    // 上传文件图标点击
    $(".icon-upload").unbind();
    $(".icon-upload").click(function () {
        taskId = ($(this).parent().prev().prev().prev().prev().prev().prev().find("span").text());
        taskSts = ($(this).parent().prev().prev().find("span").text());
        $("#uploadFileMessage").show();
    })
    // 启动图标点击
    $(".icon-open").unbind();
    $(".icon-open").click(function () {
        taskId = ($(this).parent().prev().prev().prev().prev().prev().prev().find("span").text());
        taskSts = ($(this).parent().prev().prev().find("span").text());
        $("#openTaskMessage").show();
    })
    // 启动图标点击
    $(".icon-download").unbind();
    $(".icon-download").click(function () {
        taskId = ($(this).parent().prev().prev().prev().prev().prev().prev().find("span").text());
        taskSts = ($(this).parent().prev().prev().find("span").text());
        downloadFilePath = ($(this).parent().next().next().find("span").text());
        downloadFileName = ($(this).parent().prev().prev().prev().find("span").text());
        $("#downloadMessage").show();
    })
}


/**
 * 获取任务列表
 *
 */
function getTask() {
    $.ajax({
        type: "POST",
        url: path + "/report/QryHawTaskList.htm",
        success: function (value) {
            var data = JSON.parse(value);
            if (data.status == '1') {
                $("#table tbody").html("")
                $.each(data.data, function (index, value) {
                    $("#table tbody").append(`<tr>
		              <td class="taskId" value="${value.task_id}"><span>${value.task_id}</span></td>
		              <td  class="taskName" >
		                <span class="blue">${value.task_name}</span>
		                <div>
		                  <input type="text" value="${value.task_name}">
		                  <span class="save" name="${value.task_name}">save</span>
		                </div>
		              </td>
		              <td><span class="blue">${value.upload_file_name}</span></td>
		              <td><span class="blue">${value.download_file_name}</span></td>
		              <td><span class="blue">${value.task_sts}</span></td>
		              <td><span class="blue"><fmt:formatDate value="${value.insert_time}" pattern="yyyy-MM-dd HH:mm:ss"/></span></td>
		              <td>
		                <i class="iconfont icon-open"></i>
		                <i class="iconfont icon-shouye_shugang_shijiantixing"></i>
		                <i class="iconfont icon-end"></i>
		                <i class="iconfont icon-shouye_shugang_shijiantixing"></i>
		                <i class="iconfont icon-upload"></i>
		                <i class="iconfont icon-shouye_shugang_shijiantixing"></i>
		                <i class="iconfont icon-download"></i>
		                <i class="iconfont icon-shouye_shugang_shijiantixing"></i>
		                <i class="iconfont icon-xianshi_bi"></i>
		                <i class="iconfont icon-shouye_shugang_shijiantixing"></i>
		                <i class="iconfont icon-lajitong"></i>
		              </td>
		              <td style="width: 0%" hidden="hidden"><span class="blue">${value.upload_file_path}</span></td>
		              <td style="width: 0%" hidden="hidden"><span class="blue">${value.download_file_path}</span></td>
		            </tr>`)
                })
                _init();
            }
        }
    })
}

function ToTask(taskId, newtaskName) {
    if (taskId == "") {
        addTask(newtaskName)
    } else {
        editTask(taskId, newtaskName)
    }
}

function addTask(taskName) {
    $.ajax({
        type: "POST",
        data: {
            taskName: taskName
        },
        url: path + "/report/generateHawTask.htm",
        success: function (value) {
            var data = JSON.parse(value);
            if (data.status == '1') {
                getTask()
            }
        }
    })
}

function editTask(taskId, newTaskName) {
    $.ajax({
        type: "POST",
        data: {
            taskId: taskId,
            taskName: newTaskName
        },
        url: path + "/report/updateHawTask.htm",
        success: function (value) {
            var data = JSON.parse(value);
            if (data.status == '1') {
                getTask()
            }
        }
    })
}

$("#deleteDeleteBtn").click(function () {
    $(".deleteCategory").hide();
})
$("#deleteComfireBtn").click(function () {

    $.ajax({
        type: "POST",
        data: {
            taskId: taskId,
            taskSts: "D"
        },
        url: path + "/report/updateHawTask.htm",
        success: function (value) {
            var data = JSON.parse(value);
            if (data.status == '1') {
                $(".deleteCategory").hide();
                getTask();
            }
        }
    })
})


$("#downloadDeleteBtn").click(function () {
    $("#downloadMessage").hide();
})
$('#downloadComfireBtn').click(function () {
    window.open( path + "/report/toDownload.htm?taskId=" + taskId);
    $("#downloadMessage").hide();
    getTask();
});

$("#openDeleteBtn").click(function () {
    $("#openTaskMessage").hide();
})
$('#openComfireBtn').click(function () {
    $.ajax({
        type: "POST",
        data: {
            taskId: taskId
        },
        url: path + "/report/openHawTask.htm",
        success: function (value) {
            var data = JSON.parse(value);
            if (data.status == '1') {
                $("#openTaskMessage").hide();
                getTask();
            }
        }
    })
});

// 上传文件确认按钮
$('#uploadfile').on('click',uploadFile);
/**
 * 上传文件函数
 */
function uploadFile() {
    var files = document.getElementById("files").files[0];
    // formdata
    var fm = new FormData();
    fm.append('files', files);
    fm.append('taskId', taskId);
    fm.append('taskSts', taskSts);
    $.ajax(
        {
            url: path+'/report/uploadFile.htm',
            type: 'POST',
            data: fm,
            contentType: false, //禁止设置请求类型
            processData: false, //禁止jquery对DAta数据的处理,默认会处理
            //禁止的原因是,FormData已经帮我们做了处理
            success: function (value) {
                var data = JSON.parse(value);
                if (data.status == '1') {
                    $("#uploadFileMessage").hide();
                    getTask();
                }
            },
            error: function (value) {
                $("#uploadFileMessage").hide();
                alert("上传失败");
            }
        }
    );
}


