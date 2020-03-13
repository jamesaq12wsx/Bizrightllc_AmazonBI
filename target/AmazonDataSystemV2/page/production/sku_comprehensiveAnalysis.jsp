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
    <ul class="navSKU"></ul>
    <!-- 内容 -->
    <div class="SKUmain">
        <div class="productInfo">
          <img src="" alt="">
          <div class="productDetail">
            
          </div>
        </div>
        
        <div>
          <h1>综合分析</h1>
          <div class="calendar">
            <div id="reportrange" class="mydate">
              <span>2018-05-29 至 2018-06-04</span> <b class="caret"></b>
            </div>
          </div>
          <div class="selectStyle" style="margin-top: 11px;">
            <select name="" id="cityCode" onchange="getComprehensiveAnalysis()">
              <option value="60601">芝加哥</option>
              <option value="90001">洛杉矶</option>
              <option value="10001">纽约</option>
            </select>
            <i></i>
            <b class="caret"></b>
          </div>
          <table class="table" style="margin-top: 10px;" id="offerListTable">
            <thead>
              <tr>
                <td style="width: 40%;text-align: left;">offer名称</td>
                <td style="width: 30%;">评价</td>
                <td style="width: 15%;">价格</td>
                <td style="width: 15%;"></td>
              </tr>
            </thead>
            <tbody>
              
            </tbody>
          </table>
         <!--  <div value="1 0"></div> -->
          <!-- <div id="page" class="page_div"></div> -->
        </div>
    </div>
    <!-- ************内容 -->
    <div class="compareBox">
      <div class="compareSearch">
        <h2>Compare with:</h2>
        <div>
          <input type="text" placeholder="请输入asin" onchange="showJINPINData1(this.value)">
          <i class="iconfont icon-sousuo"></i>
        </div>
      </div>
      <ul class="tab">
        <li class="on">竞品</li>
        <li>同类产品</li>
      </ul>
      <div class="borderBottom" style="margin: 20px 0 0 0;"></div>
      <div class="tabCt">
        <ul id="ul1">
          
        </ul>
      </div>
      <div class="tabCt" style="display: none;">
        <ul id="ul2">
          
        </ul>
      </div>
    </div>
  </body>
  <jsp:include page="../production/common.jsp/commonJs.jsp"/>
</html>