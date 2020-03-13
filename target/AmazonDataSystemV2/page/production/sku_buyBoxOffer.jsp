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
          <h1>Box buy offer分析</h1>
          <div class="tablistTarget active">
          <div class="monthlyCalendar">
            <input type="text" class="monthDate" value="2012-05" id="datetimepicker">
            <i></i>
            <b class="caret"></b>
          </div>
          <div class="selectionArea" style="width: 85%;">
              <div>
                <input type="checkbox" class="magic-checkbox" id="checkbox1" name="1" checked="">
                <label for="checkbox1">洛杉矶</label>
              </div>
              <div>
                <input type="checkbox" class="magic-checkbox" id="checkbox2" name="2" checked="">
                <label for="checkbox2">纽约</label>
              </div>
              <div>
                <input type="checkbox" class="magic-checkbox" id="checkbox3" name="3" checked="">
                <label for="checkbox3">芝加哥</label>
              </div>
            </div>
          <div id="echart1" style="height: 240px;width:49%;display: inline-block;"></div>
          <div id="echart13" style="height: 240px;width:49%;display: inline-block;"></div>
          <div id="echart14" style="height: 240px;width:49%;display: inline-block;"></div>
          <div class="borderBottom"></div>
          <p class="echartTitle">
            <span style="color:#fd9827;" id="categorylm"></span> 类目排行趋势
          </p>
          <div id="echart12" style="height: 410px;"></div>
          <div class="borderBottom"></div>
          <h5>buy box offer变动列表</h5>
          <div class="selectionArea">
            <div>
              <input type="radio" class="magic-checkbox" id="cityCode_checkbox1" name="city" checked="">
              <label for="cityCode_checkbox1">洛杉矶</label>
            </div>
            <div>
              <input type="radio" class="magic-checkbox" id="cityCode_checkbox2" name="city" >
              <label for="cityCode_checkbox2">纽约</label>
            </div>
            <div>
              <input type="radio" class="magic-checkbox" id="cityCode_checkbox3" name="city">
              <label for="cityCode_checkbox3">芝加哥</label>
            </div>
          </div>
          <table class="table" id="buyBoxOfferChange">
            <thead>
              <tr>
                <td style="width: 10%;">变更时间</td>
                <td style="width: 30%;text-align: left;">offer名称</td>
                <td style="width: 30%;">
                  <div class="indent111">当日价格</div>
                </td>
                <td style="width: 30%;">
                  <div class="indent111">前日价格</div>
                </td>
              </tr>
            </thead>
            <tbody>
              
            </tbody>
          </table>
        </div>
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
          <li>
            <div class="img">
              <img src="https://images-na.ssl-images-amazon.com/images/I/81FnekGOfZL._SX425_.jpg" alt="">
            </div>
            <div class="productName">
              Acer Aspire E 15 E5-575G-57D4 15.6-Inches Full HD Notebook (7th Gen Intel Core i5-7200U, GeForce 940MX, 8GB DDR4 SDRAM, 256GB SSD, Windows 10 Home), Obsidian Black
            </div>
            <p>by iPower</p>
            <p>
              <span class="price">$25.99</span>
              <i class="a-icon a-icon-prime"></i>
            </p>
            <p class="review">
              Rating: <span>4.5</span>  
              Review: <span>180</span>
            </p>
          </li>
          
        </ul>
      </div>
    </div>
  </body>
  <jsp:include page="../production/common.jsp/commonJs.jsp"/>
</html>