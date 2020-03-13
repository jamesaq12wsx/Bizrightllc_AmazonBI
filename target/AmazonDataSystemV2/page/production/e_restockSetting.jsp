<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Restock Setting</title>
    <jsp:include page="../production/common.jsp/commonCss.jsp"/> 
    <style>
        .table>thead>tr>td {
            background: #fff;
        }
    </style>
</head>

<body class="setting">
    <!-- 头部 -->
    <ul class="nav"></ul>
    <!-- 内容 -->
    <div class="main" style="min-height: 600px;">
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
        <h1>Restock Setting</h1>
        <table class="table" id="listTable">
            <thead>
                <tr>
                    <th>SKU</th>
                    <th>INV Buffer (day)</th>
                    <th>Restock Frequency (day)</th>
                    <th>Lead Time (day)</th>
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
                    <span class="comfireBtn">Yes,delete it!</span>
                    <span class="deleteBtn">Cancel</span>
                </p>
            </div>
        </div>



        <jsp:include page="../production/common.jsp/commonJs.jsp"/>
<!--         <script src="jsp.js/a_OwnBrand.js"></script> -->
</body>

</html>