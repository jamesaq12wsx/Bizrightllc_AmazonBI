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
    <link rel="stylesheet" href="<%=path %>/page/production/js/jstree/dist/themes/default/style.min.css" />
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
    <div class="borderBottom"></div>

    <!-- 整体top数据div -->
    <div id="bs" class="a-container">
        <!-- top导航栏,动态多级菜单项 -->
        <!-- 多级菜单中,多个ui,ul列表由a标签组成,相应打印链接由js动态连接完成 -->
        <div  id="bs-left-col" class="SidebarLeft">
            <div id="bs-left-col-head" >
                <h1 id="bestSellersCategoryHead">Best Sellers Category</h1>
            </div>
            <div id="bs-left-col-category" >
            </div>
        </div>
        <!--  根据top多级菜单选择,展示对应top列表 -->
        <div id="bs-right-col" class="SidebarRight">
            <div class="pull-right usTime">
                排行日期：<span><input type="text"  id="CrawlDate" class="dayDate" size="12" readonly="true" /></span>
            </div>
            <h1 id="bestSellersTableHead">Best Sellers</h1>
            <table class="table" id="table">
                <thead>
                <tr>
                    <td style="width: 2%;"></td>
                    <td style="display: none">Id</td>
                    <td style="width: 8%;">Image</td>
                    <td style="width: 23%;text-align: left;">Name</td>
                    <td style="width: 9%;">ASIN</td>
                    <td style="width: 9%;">Range</td>
                    <td style="width: 9%;">Price</td>
                    <td style="width: 9%;">Rating</td>
                    <td style="width: 9%;">Reviews</td>
                    <td style="width: 9%;">Seller</td>
                    <td style="width: 9%;">是否是prime</td>
                    <td style="width: 5%;">Date</td>
                </tr>
                </thead>
                <tbody>

                </tbody>
            </table>
        </div>
    </div>


</div>
</body>
<jsp:include page="../production/common.jsp/commonJs.jsp"/>
<script src="<%=path %>/page/production/js/jstree/dist/jstree.min.js"></script>
</html>