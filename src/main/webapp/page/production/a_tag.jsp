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
  </head>
  <body>
    <!-- 头部 -->
    <ul class="nav"></ul>
    <!-- 内容 -->
    <div class="main" style="min-height: 600px;">
        <h1>Manage Tags</h1>
        <span  class="easyAdd" ><i class="iconfont icon-jia"></i></span>
        <table class="table" id="table">
          <thead>
            <tr>
              <td class="tagName">
                Tags Name
              </td>
              <td style="width: 33%">Usage Amount</td>
              <td style="width: 33%">Action</td>
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
    </div>
  </body>
  <jsp:include page="../production/common.jsp/commonJs.jsp"/>
</html>