<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>hawthornegc商品信息</title>
    <jsp:include page="../production/common.jsp/commonCss.jsp"/>
    <!-- easyui核心UI文件 css -->
    <link rel="stylesheet" href="<%=path %>/page/production/jquery-easyui-1.7.0/themes/default/easyui.css">
    <!-- easyui图标 -->
    <link rel="stylesheet" href="<%=path %>/page/production/jquery-easyui-1.7.0/themes/icon.css">
    <style type="text/css">
    </style>
</head>

<body>
<!-- 头部 -->
<ul class="nav"></ul>
<!-- 内容 -->
<div class="main" style="min-height: 600px;">
    <h1>Manage Haw Task</h1>
    <span class="easyAdd"><i class="iconfont icon-jia"></i></span>
    <table class="table" id="table">
        <thead>
        <tr>
            <td class="taskId">Task Id</td>
            <td style="width: 20%">Task Name</td>
            <td style="width: 15%">Upload File Name</td>
            <td style="width: 15%">Download File Name</td>
            <td style="width: 10%">Task Sts</td>
            <td style="width: 10%">Insert Time</td>
            <td style="width: 15%">Action</td>
            <td style="width: 0%" hidden="hidden">Upload File Path</td>
            <td style="width: 0%" hidden="hidden">Download File Path</td>
        </tr>
        </thead>
        <tbody>

        </tbody>
    </table>
    <!-- ************内容 -->
    <div class="cover deleteCategory">
        <div class="model">
            <i class="iconfont icon-gantanhao-xianxingyuankuang"></i>
            <h2>Are you sure?</h2>
            <p>You won't be able to revert this!</p>
            <p>
                <span id="deleteComfireBtn" class="comfireBtn">Yes,delete it!</span>
                <span id="deleteDeleteBtn" class="deleteBtn">Cancel</span>
            </p>
        </div>
    </div>
    <!-- 上传文件选择框  -->
    <div id="uploadFileMessage" class="cover hawMessageBox">
        <div class="model">
            <form id="myform" enctype="multipart/form-data">
                <p>选择文件:<input type="file" id="files" name="files">
                <input id="uploadfile" type="sumbit" value="提交">
            </form>
        </div>
    </div>
    <!-- 启动任务 -->
    <div id="openTaskMessage" class="cover hawMessageBox">
        <div class="model">
            <i class="iconfont icon-gantanhao-xianxingyuankuang"></i>
            <h2>Are you sure?</h2>
            <p>You want to start this task!</p>
            <p>
                <span id="openComfireBtn" class="comfireBtn">Yes,open it!</span>
                <span id="openDeleteBtn" class="deleteBtn">Cancel</span>
            </p>
        </div>
    </div>
    <!-- 下载结果文件 -->
    <div id="downloadMessage" class="cover hawMessageBox">
        <div class="model">
            <i class="iconfont icon-gantanhao-xianxingyuankuang"></i>
            <h2>Are you sure?</h2>
            <p>You want to download this result report!</p>
            <p>
                <span id="downloadComfireBtn" class="comfireBtn">Yes,Download it!</span>
                <span id="downloadDeleteBtn" class="deleteBtn">Cancel</span>
            </p>
        </div>
    </div>
</div>
</body>
<jsp:include page="../production/common.jsp/commonJs.jsp"/>
<script src="<%=path %>/page/production/jquery-easyui-1.7.0/jquery.easyui.min.js"></script>

</html>