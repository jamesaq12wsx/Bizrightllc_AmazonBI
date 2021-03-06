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
                <option value="5">sku</option>
                <option value="4">asin</option>
              </select>
              <textarea rows="1" cols="20"></textarea>
              <i class="iconfont icon-Icon12"></i>
            </div>
          </div>
          <strong>Date:</strong>
          <div class="dayCalendar" style="margin-bottom: 10px;">
            <input type="text" class="dayDate" value="2018-12-23" id="dataStr" readonly="">
            <b class="caret"></b>
          </div>
          <div class="typeList">
            <span style="margin-right: 20px;">My Sku</span>
            <span>Ipower</span>
            <span>Simple Deluxe</span>
            <span>Other</span>
          </div>
          <div class="tagList">
            <b>Tag:</b>
            
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
        <h1>价格总览</h1>
        <table class="table" style="margin-bottom: 0" id="t">
          <thead>
            <tr>
              <td style="width: 100px;"></td>
              <td style="width: 60px;"></td>
              <td style="width: 100px;text-align: left;">asin</td>
              <td style="width: 100px;text-align: left;">名称</td>
              <td style="width: 100px;text-align: left;">brand</td>
              <td style="width: 300px;">seller</td>
              <td style="width: 100px;">价格</td>
              <td style="width: 100px;">排名</td>
              <td style="width: 100px;">操作</td>
            </tr>
          </thead>
        </table>
        <div id="tableList">
            
        </div>
        <div id="page" class="page_div"></div> 
    </div>
    <!-- ************内容 -->
  </body>
  <jsp:include page="../production/common.jsp/commonJs.jsp"/>
</html>