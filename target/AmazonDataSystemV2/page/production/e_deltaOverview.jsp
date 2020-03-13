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

    <title>Delta Setting</title>
    <jsp:include page="../production/common.jsp/commonCss.jsp"/> 
    <style>

    </style>
</head>

<body class="delta">
    <!-- 头部 -->
    <ul class="nav"></ul>
    <!-- 内容 -->
    <div class="main">
        <div class="clearfix">
            <h1 class="pull-left">Delta Tags</h1>
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
        </div>
        <div class="tabs" id="tab">
            <span class="easyAdd"><i class="iconfont icon-jia"></i></span>
            <a href="javascript:;" class="current">Sales other channel</a><a href="javascript:;">EPOS Amazon</a>
        </div>
        <div style="overflow-x: auto;">
            <table class="table deltaovertable" id="deltaovertable">
                <thead>
                    <tr>
                        <th></th>
                        <th>SKU</th>
                        <th>2019<br>Jan</th>
                        <th>2019<br>Feb</th>
                        <th>2019<br>Mar</th>
                        <th>2019<br>Apr</th>
                        <th>2019<br>May</th>
                        <th>2019<br>Jun</th>
                        <th>2019<br>Jul</th>
                        <th>2019<br>Aug</th>
                        <th>2019<br>Sep</th>
                        <th>2019<br>Oct</th>
                        <th>2019<br>Nov</th>
                        <th>2019<br>Dec</th>
                    </tr>
                    <tr></tr>
                </thead>
                <tbody>
                    
                    
                </tbody>

            </table>
        </div>
    </div>







   <jsp:include page="../production/common.jsp/commonJs.jsp"/>
</body>

</html>