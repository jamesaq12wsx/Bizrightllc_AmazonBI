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
          <h1>销量分析</h1>
          <div class="tablistTarget active">
          <div class="calendar">
            <div id="reportrange" class="mydate">
              <span>2018-10-01 至 2018-10-07</span> <b class="caret"></b>
            </div>
          </div>
          <span class="btn amazonBtn pull-right" id="btn_dc">导出</span>
          <h5 style="display:block;margin-top: 10px;">平台总销售分析</h5>
          <table class="table" id="commonProgress">
            <thead>
              <tr>
                <td style="width: 20%;text-align: left;padding-left: 30px;">平台</td>
                <td style="text-align:left;width: 60%">占比</td>
                <td style="width: 20%">销量</td>
              </tr>
            </thead>
            <tbody>
              
            </tbody>
          </table>
          <h5>销售量趋势</h5><br>
          <div id="echart42" style="height: 210px;"></div>
          <div class="borderBottom"></div>
          <h5>详细数据</h5>
          <table class="table">
            <thead id="zj_title">
            
            </thead>
            <tbody id="zj_tr_td">
              
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
          
        </ul>
      </div>
    </div>
  </body>
  <jsp:include page="../production/common.jsp/commonJs.jsp"/>
</html>