<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>单品SKU预测-echarts</title>
    <jsp:include page="../production/common.jsp/commonCss.jsp"/> 
</head>

<body class="delta">
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

        </div>
        <div class="info">
            <img src="./images/hp.jpg" alt="" class="product_img" id="skuImgUrl">
            <div class="describe" style="width:60%;">
                <p></p>
<!--                 <p class='pull-left'>SKU: <a>BO5TJHGT</a></p> -->
<!--                 <p class='pull-left' style="margin-left:100px;">ASIN: <a>BO5TJHGT</a></p> -->
            </div>
        </div>
        <div style="overflow-x: auto;">
            <div id="echarts" style="width:100%;height:300px;"></div>
        </div>

    </div>

    <!--   <div class="cover">
    <div class="loading">
      <img src="images/loading.gif" alt="">
      <p>加载中...</p>
    </div>
  </div> -->




   <jsp:include page="../production/common.jsp/commonJs.jsp"/>

            
</body>

</html>


