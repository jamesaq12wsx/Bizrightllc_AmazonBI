let taskNameEle;
let asinsEle;
let keywordsEle;
let errorAlertEle;
let errorMsgEle;
let taskResultModalHeaderEle;

let createTaskBtn;

let taskResultModel;

let tasks = [];
let selectTaskResult = null;

const taskStatusMap = {
    "O": 'ORIGINAL_INS',
    "U": 'UPLOAD_FILE',
    "P": 'TASK_OPEN',
    "W": 'TASK_WAITING',
    "S": 'TASK_SUCCESS',
    "F": 'TASK_FAILD',
};

/**
 * JS文档加载完成调用
 */
$(document).ready(function () {
    //sessionStorage.moduleType = '/f_Report_Promotions.htm';

    taskNameEle = $('#taskNameInput');
    asinsEle = $('#asinsInput');
    keywordsEle = $('#keywordsInput');
    taskResultModalHeaderEle = $('#taskResultModalHeader');

    createTaskBtn = $('#createTaskButton');
    createTaskBtn.click(createTask);

    errorAlertEle = $('#formAlert');
    errorMsgEle = $('#errorMsg');

    taskResultModel = $('#taskResultModal');


    fetchTaskList();

});

function _init() {

}

/**
 * Fetch task list
 */
function fetchTaskList(successCb, errorCb, completeCb) {

    showLoading();

    $.ajax({
        url: path + '/report/getProductKeywordTask.htm',
        type: 'POST',
        success: successCb ? successCb : function (response) {
            closeLoading();

            if (successCb) {
                successCb();
            } else {
                let taskResponse = JSON.parse(response);
                // console.log('task', response);
                if (taskResponse.status == '1') {
                    tasks = taskResponse.data;
                    reDraw();
                } else {
                    tasks = [];
                }
            }

        },
        error: errorCb ? errorCb : function () {
            closeLoading();

            if (errorCb) {
                errorCb();
            } else {
                console.log("Fetch Promotion setting error");
                promotionSettings = [];
            }

        },
        complete: completeCb ? completeCb : function () {
            closeLoading();
        }
    });
}

function fetchTaskResult(taskId, successCb, errorCb) {

    if (!taskId || taskId === '') {
        alert("Cannot fetch empty task  id");
        return;
    }

    showLoading();

    $.ajax({
        url: path + '/report/getProductKeywordTaskResult.htm',
        type: 'POST',
        data: {taskId: taskId},
        success: function (response) {
            closeLoading();

            if (successCb) {
                successCb(response);
            } else {
                let taskResponse = JSON.parse(response);
                // console.log('task', response);
                if (taskResponse.status == '1') {
                    selectTaskResult = taskResponse.data;

                } else {
                    selectTaskResult = null;
                }
            }

        },
        error: function () {
            closeLoading();

            if (errorCb) {
                errorCb();
            } else {
                console.log("Fetch task result error");
                promotionSettings = [];
            }

        },
        complete: function () {
            closeLoading();
        }
    });
}

/**
 * Create new task
 */
function createTask() {
    let requestBody = getRequestBody();

    if (!requestBody.taskName) {
        showErrorMessage("Task name cannot be empty");
        return;
    }

    if (!requestBody.asins) {
        showErrorMessage("Search asins cannot be empty");
        return;
    }

    if (!requestBody.keywords) {
        showErrorMessage("Keywords cannot be empty");
        return;
    }

    createTaskPost(requestBody);

}

function showErrorMessage(msg) {
    errorMsgEle.text(msg);
    errorAlertEle.alert();
}

function hideErrorMessage() {

}

function showRequestAlert() {

}

function reDraw() {

    $("#table").dataTable().fnDestroy();

    let table = $('#table').DataTable({
        pageLength: 20,
        data: tasks,
        // order: [[4, "desc"]],
        columns: [
            {
                data: "taskName"
            },
            {
                data: "taskStatus",
                render: function (data, type, row) {
                    return getStatusString(data);
                }
            },
            {
                data: "createdAt"
            },
            {
                "className": 'taskInfoBtn',
                "orderable": false,
                "data": null,
                "defaultContent": '<span class="infoIcon"><i class="iconfont icon-more"></i></i></span>',
            }
            // {
            //     "className": 'showResultBtn',
            //     "orderable": false,
            //     "data": null,
            //     "defaultContent": '<span class="downloadIcon"><i class="iconfont icon-download"></i></i></span>',
            // }
        ],
    });

    // TODO: Apply search

    $('#table tbody').on('click', 'td.taskInfoBtn', function () {
        let tr = $(this).closest('tr');
        let row = table.row(tr);
        let task = row.data();

        if (task.taskStatus !== 'S') {
            alert('Task haven\'t finished');
        } else {
            taskResultOnClickHandler(task);
        }

        // alert('You clicked info on row', row.data());
    });

    $('#table tbody').on('click', 'td.downloadTaskBtn', function () {
        let tr = $(this).closest('tr');
        let row = table.row(tr);

        alert('You clicked download on row', row.data());
    });
}

//This method redraw the table
function reDrawResultTable(taskResult) {

    taskResultModalHeaderEle.text(`Task: ${taskResult.taskName}`);

    $("#taskResultTable").dataTable().fnDestroy();

    let table = $('#taskResultTable').DataTable({
        pageLength: 20,
        data: taskResult.productKeywordResults ? taskResult.productKeywordResults : [],
        buttons: [
            'excelHtml5'
        ],
        columns: [
            {
                "className": 'asin-details-control',
                "orderable": false,
                "data": null,
                "defaultContent": '<span class="addIcon"><i class="iconfont icon-jia"></i></i></span>',
            },
            {
                data: "asin"
            },
            {
                data: 'asin',
                render: function (data, type, row, meta) {
                    // console.log('render total count');
                    // console.log(row);
                    let keywordTotalCount = Object.values(row.keywordCount).reduce((acc, cur) => acc + cur, 0);
                    return `<span class="totalKeywordCount">${keywordTotalCount}</span>`;
                }
            }
        ],
    });

    $('#taskResultTable tbody').on('click', 'td.asin-details-control', function () {

        // console.log("show keyword detail");

        let tr = $(this).closest('tr');
        let row = table.row(tr);

        if (row.child.isShown()) {
            // This row is already open - close it
            row.child.hide();
            tr.removeClass('shown');
        } else {
            // Open this row
            row.child(formatKeywordDetail(row.data())).show();
            tr.addClass('shown');
        }
    });
}

// format the detail of keyword result
// show every keyword appear count
function formatKeywordDetail(keywordResult) {
    // console.log("show keyword detail", keywordResult);

    var table = '<table style="width: 100%">';

    table += '<thead>\n' +
        '            <tr>\n' +
        '                <th>Keyword</th>\n' +
        '                <th>Count</th>\n' +
        '            </tr>\n' +
        '        </thead>';

    if (keywordResult.keywordCount) {
        table += '<tbody>';

        for (const [key, value] of Object.entries(keywordResult.keywordCount)) {
            table += `<tr>` +
                `<td>${key}</td>` +
                `<td>${value}</td>` +
                `</tr>`;
        }

        table += '</tbody>';
    }

    table += '</table>';

    return table;

}

function getRequestBody() {
    let taskName = taskNameEle.val().trim();
    let asins = asinsEle.val().trim();
    let keywords = keywordsEle.val().trim();

    return {
        taskName: taskName,
        asins: asins,
        keywords: keywords
    }
}

function createTaskPost(requestBody) {

    showLoading();

    console.log("Create task", requestBody);

    $.ajax({
        type: "POST",
        data: requestBody,
        url: path + "/report/generateProductReviewKeywordTask.htm",
        success: function (value) {

            closeLoading();

            let data = JSON.parse(value);
            if (data.status == '1') {
                fetchTaskList();
            } else {
                alert('添加失败');
                console.debug("Create failed", data);
            }
        },
        error: function () {
            closeLoading();
            alert('请求失败');
        }
    })
}

function getStatusString(status) {
    let result = taskStatusMap[status];

    if (result) {
        return result;
    } else {
        return status
    }
}

function openTaskResultModal(result) {
    console.log('Open result modal', result);
    reDrawResultTable(result);
    taskResultModel.modal('show');
}

function taskResultOnClickHandler(task) {
    showLoading();

    fetchTaskResult(task.taskId,
        function (response) {
            closeLoading();

            let taskResponse = JSON.parse(response);
            // console.log('task', response);
            if (taskResponse.status == '1') {
                openTaskResultModal(taskResponse.data);
            } else {
                alert('Get task result failed');
                selectTaskResult = null;
            }
        }, function () {
            closeLoading();

            alert('Get task result error');
            selectTaskResult = null;
        })
}

function closeTaskResultModal() {
    taskResultModel.modal('hide');
    selectTaskResult = null;
}

$("#downloadDeleteBtn").click(function () {
    // $("#downloadMessage").hide();
});

$('#downloadComfireBtn').click(function () {
    // window.open( path + "/report/toPromotionDownload.htm?taskId=" + taskId);
    // $("#downloadMessage").hide();
    // getList();
});






