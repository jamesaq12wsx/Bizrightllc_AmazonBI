<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>VC Promotion</title>
    <jsp:include page="../production/common.jsp/commonCss.jsp"/>

    <style>

        tr{
            height: 50px;
        }

        .row {
            margin-top: 10px;
        }

        .modal-body label {
            color: black;
        }

    </style>

</head>
<body>
<!-- 头部 -->
<ul class="nav" style="display: inline-block;"></ul>
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
    <div>
        <div class="searchBox">
            <h2>VC PROMOTION: </h2>

            <form class="form form-inline" role="form">

                <legend>Search Criteria</legend>

                <div class="row">
                    <div class="form-group col-xs-12 col-sm-6 col-lg-4">
                        <label for="promotionId" class="col-lg-2">Promotion Id</label>
                        <div class="col-lg-8">
                            <input type="text" class="form-control" id="promotionId" placeholder="Promotion Id">
                        </div>
                    </div>
                    <div class="form-group col-xs-12 col-sm-6 col-lg-4">
                        <label for="asin" class="col-lg-2">Asin</label>
                        <div class="col-lg-8">
                            <input type="text" class="form-control" id="asin" placeholder="Asin">
                        </div>
                    </div>
                    <div class="form-group col-lg-4">
                        <label for="status" class="col-lg-2">Status</label>
                        <select class="form-control" id="status">
                            <option value=""></option>
                            <option value="Approved">Approved</option>
                            <option value="Approved and running">Approved and running</option>
                            <option value="Canceled">Canceled</option>
                            <option value="Needs your attention">Needs your attention</option>
                            <option value="Pending approval">Pending approval</option>
                        </select>
                    </div>
                </div>

                <div class="row">
                    <h5>Start Date Range</h5>
                </div>

                <div class="row">
                    <div class="form-group col-xs-12 col-sm-6 col-lg-4">
                        <label for="startDateFrom" class="col-lg-2">From</label>
                        <div class="col-lg-10">
                            <input type="text" class="form-control mydate_single" id="startDateFrom"
                                   placeholder="Start Date From">
                        </div>
                    </div>
                    <div class="form-group col-xs-12 col-sm-6 col-lg-4">
                        <label for="startDateTo" class="col-lg-2">To</label>
                        <div class="col-lg-10">
                            <input type="text" class="form-control mydate_single" id="startDateTo"
                                   placeholder="Start Date To">
                        </div>
                    </div>
                </div>

                <div class="row">
                    <h5>End Date Range</h5>
                </div>

                <div class="row">
                    <div class="form-group col-xs-12 col-sm-6 col-lg-4">
                        <label for="endDateFrom" class="col-lg-2">From</label>
                        <div class="col-lg-10">
                            <input type="text" class="form-control mydate_single" id="endDateFrom"
                                   placeholder="End Date From">
                        </div>
                    </div>
                    <div class="form-group col-xs-12 col-sm-6 col-lg-4">
                        <label for="startDateTo" class="col-lg-2">To</label>
                        <div class="col-lg-10">
                            <input type="text" class="form-control mydate_single" id="endDateTo"
                                   placeholder="End Date To">
                        </div>
                    </div>
                </div>

                <div class="row">
                    <button type="button" id="searchBtn" class="btn btn-primary" onclick="searchVcPromotionOnClick()">Search</button>
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
    <h1>VC Promotion</h1>
    <table class="table" id="table" style="width: 100%">
        <thead>
        <tr>
            <th style="width: 5%;"></th>
            <th style="width: 9%;">Promotion Id</th>
            <th style="width: 9%">Status</th>
            <th style="width: 9%;">Type</th>
            <th style="width: 9%;">Vendor Code</th>
            <th style="width: 9%;">MarketPlace</th>
            <th style="width: 9%;">Created On</th>
            <th style="width: 9%;">Start Date</th>
            <th style="width: 9%;">End Date</th>
            <th style="width: 9%;">Insert At</th>
        </tr>
        </thead>
        <tbody>

        </tbody>
    </table>
</div>
<!-- ************内容 -->
<div class="cover tagModel">
    <div class="model">
        <div>Tag:</div>
        <div class="tagModelList">

        </div>
        <p class="pull-right">
            <span class="comfireBtn">确认</span>
            <span class="deleteBtn">退出</span>
        </p>
    </div>
</div>
<%--Promotion Setting Modal--%>
<div class="modal fade" id="promotionModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Promotion Setting</h5>
                <button type="button" id="deleteSetting" class="btn-outline-danger" onclick="deleteSettingClick()">
                    <span aria-hidden="true">Delete</span>
                </button>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form>
                    <input id="idInput" type="hidden">
                    <div class="form-group">
                        <label for="promotionIdInput" class="col-form-label">Promotion Id:</label>
                        <input type="text" class="form-control" id="promotionIdInput">
                    </div>
                    <div class="form-group">
                        <label for="asinInput" class="col-form-label">Asin:</label>
                        <input class="form-control" id="asinInput">
                    </div>
                    <div class="form-group">
                        <label for="priceInput" class="col-form-label">Price:</label>
                        <input type="number" class="form-control money-input" id="priceInput" step="0.01" min="0">
                    </div>
                    <div class="form-group">
                        <label for="fundingInput" class="col-form-label">Funding:</label>
                        <input type="number" class="form-control money-input" id="fundingInput" step="0.01" min="0">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button onclick="savePromotionSetting()" type="button" class="btn btn-primary">Save</button>
            </div>
        </div>
    </div>
</div>

</body>
<jsp:include page="../production/common.jsp/commonJs.jsp"/>
</html>