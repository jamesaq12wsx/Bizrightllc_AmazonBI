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
          <h1>评论概览</h1>
          <div class="tablistTarget  active">
          <div class="reviewInfo">
            <div id="main_avastar"></div>
            <div id="main_rwNum"></div>
            <div id="verifiedPurchase_num"></div>
          </div>
          <div class="startLevel">
          <div class="startBg startBg5">
              <span>5星：</span>
              <div class="startCover">
                <span id="five_zhanbi_span"></span>
              </div>
              <span class="occupation" id="five_zhanbi"></span>
              <span id="main_fiveStarNum"></span>
            </div>
        <div class="startBg startBg4">
              <span>4星：</span>
              <div class="startCover">
                <span id="four_zhanbi_span"></span>
              </div>
              <span class="occupation" id="four_zhanbi"></span>
              <span id="main_fourStarNum"></span>
            </div>
        <div class="startBg startBg3">
              <span>3星：</span>
              <div class="startCover">
                <span id="three_zhanbi_span"></span>
              </div>
              <span class="occupation"  id="three_zhanbi"></span>
              <span id="main_threeStarNum"></span>
            </div>            
            <div class="startBg startBg2">
              <span>2星：</span>
              <div class="startCover">
                <span id="two_zhanbi_span"></span>
              </div>
              <span class="occupation" id="two_zhanbi"></span>
              <span id="main_twoStarNum"></span>
            </div>
            <div class="startBg startBg1">
              <span>1星：</span>
              <div class="startCover">
                <span id="one_zhanbi_span"></span>
              </div>
              <span class="occupation" id="one_zhanbi"></span>
              <span id="main_oneStarNum"></span>
            </div>
          </div><br>
          <div class="calendar">
            <div id="reportrange" class="mydate">
              <span>2018-05-31 至 2018-06-06</span> <b class="caret"></b>
            </div>
          </div>
          <div class="borderBottom"></div>
          <h5>评论趋势</h5>
          <div class="selectionArea">
            <div>
              <input type="radio" class="magic-checkbox" id="checkbox1" name="city" checked="">
              <label for="checkbox1">总评论</label>
            </div>
            <div>
              <input type="radio" class="magic-checkbox" id="checkbox2" name="city" >
              <label for="checkbox2">新增评论</label>
            </div>
          </div>
          <div id="echart1" style="height: 240px;"></div>
          <div class="borderBottom"></div>
          <h5>得分及成因分析</h5>
          <div id="echart2" style="height: 240px;"></div>
          <div id="echart3" style="height: 240px;margin-top:60px;"></div>
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