<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="../production/common.jsp/commonCss.jsp"/>

    <style>

        .modal {
            color: black;
        }

    </style>
</head>
<body>
<!-- 头部 -->
<ul class="nav"></ul>
<!-- 内容 -->
<div class="main">
    <div class="userManagement">
        <span>Hello! <b>${final_username}</b></span>
        <div>
            <span class="sj"></span>
            <span class="sj2"></span>
            <ul>
                <c:if test="${accountType ==1 || accountType ==2 }">
                    <li><a href="<%=path %>/accountManage.htm">users Management</a></li>
                </c:if>
                <li><a href="<%=path %>/user/loginOut.htm">Log Out</a></li>
            </ul>
        </div>
    </div>

    <h1>Product Keyword Task</h1>

    <div>
        <div class="searchBox">

            <form class="form form-inline" role="form">

                <div id="formAlert" class="alert alert-warning alert-dismissible fade show" role="alert">
                    <p id="errorMsg"></p>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>

                <div class="row">
                    <div class="form-group col-lg-12">
                        <label for="taskNameInput" class="col-lg-2">Task Name</label>
                        <div class="col-lg-10">
                            <input class="form-control" id="taskNameInput" placeholder="Task Name"/>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="form-group col-lg-12">
                        <label for="asinsInput" class="col-lg-1">Asins</label>
                        <div class="col-lg-5">
                            <textarea class="form-control" id="asinsInput" placeholder="Asins" rows="10"></textarea>
                        </div>
                        <label for="keywordsInput" class="col-lg-1">Keywords</label>
                        <div class="col-lg-5">
                            <textarea class="form-control" id="keywordsInput" placeholder="Keywords"
                                      rows="10"></textarea>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <button type="button" id="createTaskButton" class="btn btn-primary">
                        Create
                    </button>
                </div>

            </form>

        </div>
    </div>
    <div class="borderBottom"></div>
    <div class="pull-right usTime">
        东海岸：<span id="eastTime">2018-12-19 22:00:58</span>&nbsp;&nbsp;&nbsp;
        西海岸：<span id="westTime">2018-12-19 19:00:58</span>&nbsp;&nbsp;&nbsp;
        更新时间：<span id="inserttime"></span>
    </div>

    <table class="table" id="table" style="width: 100%">
        <thead>
        <tr>
            <th style="width: 9%;">Task Name</th>
            <th style="width: 9%;">Status</th>
            <th style="width: 9%;">Created At</th>
            <th style="width: 3%;">Show Result</th>
        </tr>
        </thead>
        <tbody>

        </tbody>
    </table>
    <%--  Task list  --%>
</div>

<%--Task Result Modal--%>
<div class="modal fade" id="taskResultModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="taskResultModalHeader">Task</h5>
            </div>
            <div class="modal-body">
                <table class="table" id="taskResultTable" style="width: 100%">
                    <thead>
                    <tr>
                        <th style="width: 5%;"></th>
                        <th style="width: 9%;">Asin</th>
                        <th style="width: 9%;">Total Find Keyword</th>
                    </tr>
                    </thead>
                    <tbody>

                    </tbody>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
<%--                <button onclick="savePromotionSetting()" type="button" class="btn btn-primary">Download</button>--%>
            </div>
        </div>
    </div>
</div>

<%--Download Confirm Modal--%>
<div class="modal fade" id="downloadFileModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Download Product Keyword File</h5>
                <%--                    <button type="button" id="deleteSetting" class="btn-outline-danger" onclick="deleteSettingClick()">--%>
                <%--                        <span aria-hidden="true">Delete</span>--%>
                <%--                    </button>--%>
                <%--                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">--%>
                <%--                        <span aria-hidden="true">&times;</span>--%>
                <%--                    </button>--%>
            </div>
            <div class="modal-body">
                <p>Are you sure download file?</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button onclick="savePromotionSetting()" type="button" class="btn btn-primary">Download</button>
            </div>
        </div>
    </div>
</div>

</body>
<jsp:include page="../production/common.jsp/commonJs.jsp"/>
</html>