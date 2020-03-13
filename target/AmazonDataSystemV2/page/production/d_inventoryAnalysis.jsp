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

  <title>库存分析</title>
  <jsp:include page="../production/common.jsp/commonCss.jsp"/> 
  <style>
    .inventoryTable>tbody>tr.forecast>td:not(.time) {
      background: #fff;
    }
  </style>
</head>

<body>
  <!-- 头部 -->
  <ul class="nav"></ul>
  <!-- 内容 -->
  <div class="main">
    <div class="searchBox">
      <p>SEARCH RESULT FOR:</p>
      <div>
        <select name="" id="">
          <option value="">sku</option>
        </select>
        <textarea rows="1" cols="20" id="asin">HIWKLTCLAMPLIGHTMX2</textarea>
        <i class="iconfont icon-Icon12"></i>
      </div>
    </div>
    <div class="mainRight" style="min-height: 800px;">
      <div class="productInfo" style="border-bottom: 0px;width: 920px;margin: 0 auto;color: #0066c0;">
        <img src="" alt="">
        <div class="productDetail">
          <div style="border:0">
            Acer Aspire E 15 E5-575G-57D4 15.6-Inches Full HD Notebook (7th Gen Intel Core i5-7200U, GeForce 940MX, 8GB
            DDR4 SDRAM, 256GB SSD, Windows 10 Home), Obsidian Black
          </div>
        </div>
      </div>
      <!-- tab切换 -->
      <ul class="tablist" style="width: 100%;">
        <li class="active"><a>全部</a></li>
        <!-- <li><a>有建议日期</a></li> -->
        <li class="pull-right">
          <span>更新时间：<span>2017-12-13</span></span>
        </li>
      </ul>
      <div class="borderBottom3" style="margin-top: 42px;"></div>



      <div class="tablistTarget active">
        
        <div style="display: inline-block;float: right;">
          <p style="margin-top:20px;">Inv | <span class="sale">Sale</span> | <span class="in">In</span> | <span
              class="out">O/out</span></p>
        </div>
        <table class="table inventoryTable" style="margin-top: 10px;">
          <thead>
            <tr>
              <td style="width: 6%"></td>
              <td style="width: 22%">
                <div>Vender</div>
              </td>
              <td style="width: 10%"></td>
              <td style="width: 22%">
                <div>FBA</div>
              </td>
              <td style="width: 10%;"></td>
              <td style="width: 22%">
                <div>US</div>
              </td>
            </tr>
          </thead>
          <tbody>
           
            
          </tbody>
        </table>
      </div>
      <!--tablistTarget-->

    </div>
  </div>
  <!-- ************内容 -->




  <jsp:include page="../production/common.jsp/commonJs.jsp"/>
  <script src="<%=path %>/page/production/jsp.js/d_inventoryAnalysis.js"></script>
</body>

</html>