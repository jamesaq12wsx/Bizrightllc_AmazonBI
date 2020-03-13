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
    <div class="main">
        <h1>Manage Category</h1>
        <div class="manageCategory">
          <div class="rootCategory">   
            <h2>RootCategory</h2> 
            <ul id="RootCategory">             
                        
            </ul>          
          </div>
          <div class="subCategory">
            <h2 style="margin-bottom: 30px;">SubCategory</h2>
            
          </div>
        </div>
    </div>
    <!-- ************内容 -->
    <div class="cover deleteCategory">
      <div class="model">
          <i class="iconfont icon-gantanhao-xianxingyuankuang"></i>
          <h2>Are you sure?</h2>
          <p>You won't be able to revert this!</p>
          <p>
            <span class="comfireBtn" id="deleteCategory">Yes,delete it!</span>
            <span class="deleteBtn" >Cancel</span>
          </p>
      </div>
    </div>
    <div class="cover addCategoryModel">
      <div class="model">
          <i class="iconfont icon-chanpinshanchu pull-right"></i>
          <h4>Add SubCategorys</h4>
          <div>
            <div class="categoryTitle">SubCategorys:</div>
            <textarea name="" id="" cols="30" rows="10"></textarea>
          </div>
          <p class="pull-right">
            <span class="deleteBtn">Cancel</span>
            <span class="comfireBtn" id="addcategory">Save</span>
          </p>
      </div>
    </div>
  </body>
  <jsp:include page="../production/common.jsp/commonJs.jsp"/>
</html>