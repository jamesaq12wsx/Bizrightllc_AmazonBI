<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <jsp:include page="../production/common.jsp/commonCss.jsp"/> 
    <style>
        .table>thead>tr>td {
          background: #fff;
        }
    </style>
  </head>
  <body>
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
        <h1>Own Brand</h1>
        <span  class="easyAdd" ><i class="iconfont icon-jia"></i></span>
        <table class="table table-striped table-bordered">
          <thead>
            <tr>
              <td style="width: 80px;"></td>
              <td style="width: 90%;" class="tagName">Brand Name</td>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>
                <i class="iconfont icon-lajitong"></i>
              </td>
              <td class="tagName">
                <span>Important</span>
                <div>
                  <input type="text"><!--
               --><span class="save">save</span>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
    <!-- ************内容 -->
    <div class="cover deleteCategory">
      <div class="model">
          <i class="iconfont icon-gantanhao-xianxingyuankuang"></i>
          <h2>Are you sure?</h2>
          <p>You won't be able to revert this!</p>
          <p>
            <span class="comfireBtn" onclick="delOwnBrand()">Yes,delete it!</span>
            <span class="deleteBtn">Cancel</span>
          </p>
      </div>
    </div>
    </div>
  </body>
  <jsp:include page="../production/common.jsp/commonJs.jsp"/>
  
</html>