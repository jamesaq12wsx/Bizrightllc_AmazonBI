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
        <h1><a href="<%=path %>/a_product.htm">Product</a>>Competitior Edit</h1>
        <span  class="easyAdd" ><i class="iconfont icon-jia"></i></span>
        <span class="currentSku"><b>Sku：</b>GLBULBMDEHG1000K6X2</span>
        <table class="table table-striped table-bordered" id="table">
          <thead>
            <tr>
              <td></td>
              <td>asin</td>
              <td style="width: 80%;" class="tagName">Name</td>
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
            <span class="comfireBtn"  id="del">Yes,delete it!</span>
            <span class="deleteBtn">Cancel</span>
          </p>
      </div>
    </div>

    <div class="cover addCategoryModel">
      <div class="model">
          <i class="iconfont icon-chanpinshanchu pull-right"></i>
          <h4>Please enter ASIN, one per line</h4>
          <div>
            <textarea style="width: 540px;" name="" id="jp" cols="30" rows="10"></textarea>
          </div>
          <p class="pull-right">
            <span class="deleteBtn">Cancel</span>
            <span class="comfireBtn" id="add">Save</span>
          </p>
      </div>
    </div>
    </div>
  </body>
  <jsp:include page="../production/common.jsp/commonJs.jsp"/>
</html>