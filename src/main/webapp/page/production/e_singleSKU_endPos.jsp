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

    <title>单品SKU预测-endPos</title>
    <jsp:include page="../production/common.jsp/commonCss.jsp"/> 
    <style>

    </style>
</head>

<body class="delta">
 <!-- 头部 -->
    <ul class="nav"></ul>
    <!-- 内容 -->
    <div class="main">
         <div class="userManagement" style="float:none;text-align:right;margin-bottom:20px;">
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
<!--         <div> -->
<!--             <div class="searchBox"> -->
<!--                 <div style="width:315px;border:none;border-bottom: 1px solid #E1E1E1;"> -->
<!--                     <input type="text" style="height:30px;width:85%;"> -->
<!--                     <i class="iconfont icon-Icon12" style="cursor:pointer;"></i> -->
<!--                 </div> -->
<!--             </div> -->
<!--         </div> -->
        <div class="info">
            <img src="" alt="" class="product_img" id="skuImgUrl">
            <div class="describe">
                <p id="skuName"></p>
            </div>
            <div class="info_tables pull-right">
                <a href="##"
                    style="display:inline-block;width:110px;height:40px;border: 1px solid #ccc;text-align: center;line-height:40px;font-size:18px;" onclick="goEcharts()"><i
                        class='iconfont icon-Icon11'></i> chart</a>
            </div>

        </div>

        <div class="header_search" style="overflow: hidden;">
            <!-- <select class="form-control" style="display: inline-block; width:auto;">
                    <option>1</option>
                    <option>2</option>
                    <option>3</option>
                    <option>4</option>
                    <option>5</option>
                </select> -->
            <div class="right_Calculation pull-right">
                <div class="total"><span id="totalNum">Total:</span> </div>
                <div class="avg"><span id="AvgNum">Avg:</span> </div>
                <div class="max"><span id="MaxNum">Max:</span> </div>
                <div class="min"><span id="MinNum">Min:</span> </div>
            </div>
        </div>

        <div style="overflow-x: auto;">
            <table class="table endpostable" id="endpostable">
                <thead>
                    <tr>
                        <th rowspan="2">Week</th>
                        <th rowspan="2">So</th>
                        <th rowspan="2">SAmz单</th>
                        <th rowspan="2">Samz-Set-Total</th>
                        <th colspan="5" id="SAmz-set">SAmz-set</th>
                    </tr>
                    <tr id="skus">
                        
                    </tr>
                </thead>
                <tbody>
                    
                    
                </tbody>

            </table>
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