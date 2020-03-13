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
          <div class="calendar">
            <span class="calendarTitle">Promotion Report Download Range:</span>
            <div id="reportrange" class="mydate">
              <span>2018-07-30 至 2018-08-05</span> <b class="caret"></b>
            </div>
            <button id="down">Download</button>
          </div>
          <div>
            <table class="table" id="table">
                <thead>
                <tr>
                    <td class="taskId">Task Id</td>
                    <td style="width: 20%">Task Name</td>
                    <td style="width: 15%">Download File Name</td>
                    <td style="width: 10%">Task Sts</td>
                    <td style="width: 10%">Insert Time</td>
                    <td style="width: 15%">Action</td>
                    <td style="width: 0%" hidden="hidden">Download File Path</td>
                </tr>
                </thead>
                <tbody>

                </tbody>
            </table>
          </div>
    </div>
        <!-- 下载结果文件 -->
        <div id="downloadMessage" class="cover hawMessageBox">
            <div class="model">
                <i class="iconfont icon-gantanhao-xianxingyuankuang"></i>
                <h2>Are you sure?</h2>
                <p>You want to download this result report!</p>
                <p>
                    <span id="downloadComfireBtn" class="comfireBtn">Yes,Download it!</span>
                    <span id="downloadDeleteBtn" class="deleteBtn">Cancel</span>
                </p>
            </div>
        </div>
    </div>
  </body>
  <jsp:include page="../production/common.jsp/commonJs.jsp"/>
</html>