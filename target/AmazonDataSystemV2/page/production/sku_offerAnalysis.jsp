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
          <h1>Offer分析</h1>
          <div class="overView">
            <div class="overViewP">
              <div>
                <p>63</p>
                <span>offer</span>
              </div>
              <div>
                <span style="font-size: 16px;">Los Angeles</span><br>
                <span>31 <i class="a-icon a-icon-prime"></i></span>
              </div>
            </div>
            <div class="overViewP">
              <div>
                <p>53</p>
                <span>offer</span>
              </div>
              <div>
                <span style="font-size: 16px;">New Orleans</span><br>
                <span>26 <i class="a-icon a-icon-prime"></i></span>
              </div>
            </div>
          </div>
          <div class="calendar">
            <div id="reportrange" class="mydate">
              <span>2018-05-29 至 2018-06-04</span> <b class="caret"></b>
            </div>
          </div>
          <div class="selectionArea">
          <div>
              <input type="radio" class="magic-checkbox" id="checkbox3" name="city" checked="">
              <label for="checkbox3">芝加哥</label>
            </div>
            <div>
              <input type="radio" class="magic-checkbox" id="checkbox1" name="city" >
              <label for="checkbox1">洛杉矶</label>
            </div>
            <div>
              <input type="radio" class="magic-checkbox" id="checkbox2" name="city" >
              <label for="checkbox2">纽约</label>
            </div>
            
          </div>
          <br>
          <div id="echart2" style="height: 240px;width:49%;display: inline-block;"></div>
           <div id="echart22" style="height: 240px;width:49%;display: inline-block;"></div>
          <div class="borderBottom"></div>
          <h5>offer变动列表</h5>
          <div class="selectStyle" style="margin-top: -6px;">
            <select name="" onchange="getofferChange(this.value)">
              <option value="60601">芝加哥</option>
              <option value="90001">洛杉矶</option>
              <option value="10001">纽约</option>
            </select>
            <i></i>
            <b class="caret"></b>
          </div>
          <table class="table" id="offerChange" >
          <thead>
            <tr>
              <td style="width: 40%;">新增offer</td>
              <td style="width: 40%;">下线offer</td>
              <td style="width: 20%;">变更时间</td>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td style="vertical-align: top;">
                <div class="offerList">
                  <div>
                    <i class="a-icon a-icon-plus"></i>
                    <a>Wireless Mall Sales</a>
                    <i class="a-icon a-icon-prime"></i>
                    <span class="red pull-right">$25.99</span>
                  </div>
                  <div>   
                    <i class="a-icon a-icon-star a-star-3"></i>
                     <span>92% <span>(428)</span></span>
                  </div>
                </div>
                <div class="offerList">
                  <div>
                    <i class="a-icon a-icon-plus"></i>
                    <a>Wireless Mall Sales</a>
                    <i class="a-icon a-icon-prime"></i>
                    <span class="red pull-right">$25.99</span>
                  </div>
                  <div>   
                    <i class="a-icon a-icon-star a-star-2-5"></i>
                     <span>92% <span>(428)</span></span>
                  </div>
                </div>
              </td>
              <td style="vertical-align: top;">
                <div class="offerList">
                 <div>
                    <i class="a-icon a-icon-reduce"></i>
                    <a>Wireless Mall Sales</a>
                    <i class="a-icon a-icon-prime"></i>
                    <span class="red pull-right">$25.99</span>
                  </div>
                  <div>   
                    <i class="a-icon a-icon-star a-star-5"></i>
                    <span>92% <span>(428)</span></span>
                  </div>
                </div>
              </td>
              <td>2018-02-11</td>
            </tr>
          </tbody>
          </table>
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