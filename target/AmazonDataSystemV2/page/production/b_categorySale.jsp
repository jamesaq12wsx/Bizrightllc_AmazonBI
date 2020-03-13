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
        <h1>类目品牌销售</h1>
        <div class="calendar">
          <span class="calendarTitle">category销量分析</span>
          <div id="reportrange" class="mydate">
            <span>2018-07-30 至 2018-08-05</span> <b class="caret"></b>
          </div>
        </div><br>
        <div id="echart1" style="height: 300px;"></div>
        <table class="table" style="margin-top: 10px;" id="hotRank">
          <thead>
            <tr>
              <td style="width: 60px;">排行</td>
              <td style="width: 80px;text-align: left;">类目名</td>
              <td style="width: 200px">销量</td>
              <td style="width: 250px;">占比</td>
            </tr>
          </thead>
          <tbody>
            
          </tbody>
        </table>
        <div id="page" class="page_div"><a id="prePage">&lt;上一页</a><a class="current">1</a><a>2</a><a>3</a><a>4</a><a>5</a>. . .<a>9</a><a id="nextPage">下一页&gt;</a></div>
        <div class="borderBottom"></div><!--分隔线-->
        <div class="calendar">
          <span class="calendarTitle">品牌销量分析</span>
          <div id="reportrange1" class="mydate">
            <span>2018-07-30 至 2018-08-05</span> <b class="caret"></b>
          </div>
        </div><br>
        <div id="echart3" style="height: 300px;width: 70%;display: inline-block;"></div>
        <div id="echart4" style="height: 300px;width: 29%;display: inline-block;"></div>
    </div>
  </body>
  <jsp:include page="../production/common.jsp/commonJs.jsp"/>
</html>