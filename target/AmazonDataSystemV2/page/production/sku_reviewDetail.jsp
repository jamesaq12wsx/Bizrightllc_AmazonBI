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
    <style type="text/css">
.more {
	text-align: center;
	background: #f5f5f5;
	cursor: pointer;
	padding: 10px;
	margin-top:10px;
}

.more:hover {
	background: #eee;
}
</style>
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
          <h1>评论详情</h1>
          <div class="screenBox">
            <div class="selectStar" style="margin-right:10px;">
              <div>
                <input type="checkbox" class="magic-checkbox" id="checkbox_sku" name="1" checked="">
                <label for="checkbox_sku">所有sku</label>
              </div>
            </div>
            <!-- <span>筛选：</span> -->
            <div class="calendar">
              <div id="reportrange" class="mydate">
                <span>2018-05-31 至 2018-06-06</span> <b class="caret"></b>
              </div>
            </div>

            <div class="selectStyle" style="margin-top: 11px;">
              <select name="" id="check2" onchange="getCommentDetails()">
                <option value="">所有评论者</option>
                <option value="">已确认购买</option>
              </select>
              <i></i>
              <b class="caret"></b>
            </div>
            
            <div class="selectStar">
              <div>
                <input type="checkbox" class="magic-checkbox" id="checkbox1" name="1" checked="" onchange="getCommentDetails(this)">
                <label for="checkbox1">1星</label>
              </div>
              <div>
                <input type="checkbox" class="magic-checkbox" id="checkbox2" name="2" checked="" onchange="getCommentDetails(this)">
                <label for="checkbox2">2星</label>
              </div>
              <div>
                <input type="checkbox" class="magic-checkbox" id="checkbox3" name="3" checked="" onchange="getCommentDetails(this)">
                <label for="checkbox3">3星</label>
              </div>
              <div>
                <input type="checkbox" class="magic-checkbox" id="checkbox4" name="4" checked="" onchange="getCommentDetails(this)">
                <label for="checkbox4">4星</label>
              </div>
              <div>
                <input type="checkbox" class="magic-checkbox" id="checkbox5" name="5" checked="" onchange="getCommentDetails(this)">
                <label for="checkbox5">5星</label>
              </div>
            </div>
            
            <span style="margin-left: 50px;">排序:</span>
            


            <div class="selectStyle" style="margin-top: 11px;">
              <select name="" id="check3" onchange="getCommentDetails()">
                <option value="">最近</option>
                <option value="">有帮助</option>
              </select>
              <i></i>
              <b class="caret"></b>
            </div>
            
            <span class="pull-right btn amazonBtn" onclick="downLoad2()">导出</span>
          </div>
          <div class="commentBox">
            
          </div>
           <p class="more" id="jzgd" onclick="getCommentDetails(123)">加载更多</p>
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