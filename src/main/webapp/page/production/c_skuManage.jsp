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
            <p>SEARCH RESULT FOR:</p>
            <div>
              <select name="" id="condition">
                <option value="3">keyword</option>
                <option value="5" selected="selected">sku</option>
                <option value="4">asin</option>
              </select>
              <textarea rows="1" cols="20"></textarea>
              <i class="iconfont icon-Icon12"></i>
            </div>
          </div>
          <div class="typeList">
            <span style="margin-right: 20px;">My Sku</span>
          </div>
          <div class="tagList">
            <b>Tag:</b>
            <span>Important</span>
            <span>Abnormal</span>
            <span>New</span>
            <span>T1</span>
            <span>T2</span>
            <span>T3</span>
            <span>T4</span>
            <span>T5</span>
          </div>
          <div class="categoryList">
            <span class="addCategory">+Category...</span>
          </div>
        </div>
        <div class="borderBottom"></div>
        <div class="pull-right usTime">
          东海岸：<span id="eastTime">2018-12-19 22:00:58</span>&nbsp;&nbsp;&nbsp;
          西海岸：<span id="westTime">2018-12-19 19:00:58</span>&nbsp;&nbsp;&nbsp;
          更新时间：<span id="inserttime"></span>
        </div>
        <h1>SKU监控</h1>
        <table class="table" id="table">
          <thead>
            <tr>
              <td style="width: 2%;"></td>
              <td style="width: 8%;">图片</td>
              <td style="width: 23%;text-align: left;">名称</td>
              <td style="width: 9%;">ASIN</td>
              <td style="width: 9%;">SKU</td>
              <td style="width: 9%;">价格</td>
              <td style="width: 9%;">Review</td>
              <td style="width: 9%;">New Review</td>
              <td style="width: 9%;">buy box</td>
              <td style="width: 9%;">是否是prime</td>
              <td style="width: 6%;">tag</td>
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
  </body>
  <jsp:include page="../production/common.jsp/commonJs.jsp"/>
</html>