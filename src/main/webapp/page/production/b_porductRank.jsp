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
        <h1>单品排行榜</h1>
        <div class="selectStyle" style="margin-top: 11px;">
          <span style="font-size: 16px;">channel：</span>
          <select name="" id="channel" style="margin-left:0" onchange="getHotRanking()">
            
          </select>
          <i></i>
          <b class="caret"></b>
        </div>
        
        <div class="selectStyle" style="margin: 11px 15px 0 15px;">
          <span style="font-size: 16px;">category：</span>
          <select name="category" id="category" style="margin-left:0" onchange="getHotRanking()">
            
          </select>
          <i></i>
          <b class="caret"></b>
        </div>
        
        
        <div class="selectStyle" style="margin-top: 11px;">
          <span style="font-size: 16px;">brand：</span>
          <select name="brand" id="brand" style="margin-left:0" onchange="getHotRanking()">
          
          </select>
          <i></i>
          <b class="caret"></b>
        </div>
        
        <div class="searchBox" style="display:inline-block;margin:10px 0 0 10px;">
            <div style="width:300px;">
              <select name="" id="condition"  >
                <option value="1">sku</option>
                <option value="2">asin</option>
                <option value="3">name</option>
              </select>
              <textarea rows="1" cols="20" style="width:200px;"></textarea>
              <i class="iconfont icon-Icon12"></i>
            </div>
          </div>
        <div class="calendar pull-right">
          <div id="reportrange" class="mydate">
            <span>2018-07-30 至 2018-08-05</span> <b class="caret"></b>
          </div>
        </div><br>
        <table class="table" style="margin-top: 10px;" id="hotRank">
          <thead>
            <tr>
              <td style="width: 60px;">排行</td>
              <td style="width: 80px;">商品图片</td>
              <td style="width: 350px;text-align: left;">商品名称</td>
              <td style="width: 200px">销量</td>
              <td style="width: 250px;">占比</td>
            </tr>
          </thead>
          <tbody>
            
          </tbody>
        </table>
         <div id="page" class="page_div"></div>
    </div>
    <!-- ************内容 -->
  </body>
  <jsp:include page="../production/common.jsp/commonJs.jsp"/>
</html>