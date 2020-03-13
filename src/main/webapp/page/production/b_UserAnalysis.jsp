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
        <h1>用户分析</h1>
        <div class="calendar">
            <span class="calendarTitle">地区销量分析</span>
            <div id="reportrange" class="mydate">
              <span>2018-07-30 至 2018-08-05</span> <b class="caret"></b>
            </div>
          </div>
          <div id="echart1" style="height: 300px;"></div>
          <div class="borderBottom"></div><!--分隔线-->
          <div class="calendar">
            <span class="calendarTitle">时段销量分析</span>
            <div id="reportrange1" class="mydate">
              <span>2018-07-30 至 2018-08-05</span> <b class="caret"></b>
            </div>
          </div><br>
          <div id="echart2" style="height: 300px;"></div>
          
          <div class="borderBottom"></div><!--分隔线-->
          <div class="calendar">
            <span class="calendarTitle">category销量分析</span>
            <div id="reportrange2" class="mydate">
              <span>2018-07-30 至 2018-08-05</span> <b class="caret"></b>
            </div>
          </div><br>
          <div id="echart3" style="height: 600px;"></div>
        </div>
      </div>
    </div>
    <!-- ************内容 -->
  </body>
  <jsp:include page="../production/common.jsp/commonJs.jsp"/>
</html>