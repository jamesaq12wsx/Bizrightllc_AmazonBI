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

    <title>单品SKU预测</title>
    <jsp:include page="../production/common.jsp/commonCss.jsp"/> 
    <style>

    </style>
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
            <div class="searchBox">
                <div style="width:315px;border:none;border-bottom: 1px solid #E1E1E1;">
                    <input type="text" style="height:30px;width:85%;" value="" id="sku">
                    <i class="iconfont icon-Icon12" style="cursor:pointer;"  id="searchBtn"></i>
                </div>
            </div>
        </div>
        <div class="info">
            <img src="" alt="" class="product_img" id="sku_imageUrl">
            <div class="describe">
                <p id="sku_productTitle"></p>
                <p>ASIN: <a id="asin"></a></p>
            </div>
            <div class="info_tables pull-right">
                <div class="tables_title">
                    <p>INV Buffer<span>(day)</span></p>
                    <p>Restock Frequency<span>(day)</span></p>
                    <p>Lead Time<span>(day)</span></p>
                    <p><a href="<%=path %>/e_restockSetting.htm">ALL ></a></p>
                </div>
                <div class="tables" id="canshuSetDiv">
                    <p  id="inv"><span val="15">15</span><span val="30">30</span><span val="45">45</span><span val="60">60</span></p>
                    <p  id="res"><span  val="15">15</span><span val="30">30</span><span val="60">60</span></p>
                    <p  id="lead"><span val="30">30</span><span val="45">45</span><span  val="60">60</span><span val="90">90</span></p>
                </div>
            </div>

        </div>
        <div class="table_header" style="overflow:hidden;">
        
        
            <div class="item pull-left">
                <span class="title">其他渠道Delta</span>
                <span class='apr'>APR</span>
                <input type="number" class="number" value="0" onchange="update1(this)">
                <i class='iconfont icon-icon-test3'></i>
            </div>
            <div class="item pull-left">
                <span class="title">Amazon单品Delta</span>
                <span class='apr'>APR</span>
                <input type="number" class="number" value="0" onchange="update2(this)">
                <i class='iconfont icon-icon-test3'></i>
            </div>
            <div class="item pull-left">
                <span class="title">Amazon套装Delta</span>
                <span class='apr'></span>
                <input type="number" class="number" value="0" disabled style="">
                <i class='iconfont icon-icon-test3'></i>
            </div>
            
            
            <div class="replan">replan</div>
            <div class="endpos pull-right"><a href="##"
                    style='display:inline-block;width:100%;height:100%;' onclick="goEndPos()">End POS</a></div>
        </div>
        <div style="overflow-x: auto;">
            <table class="table singleskutable">
                <thead>
                    <tr>
                        <th rowspan="2">Date</th>
                        <th colspan="5">Irwindale</th>
                        <th colspan="3">FBA</th>
                        <th colspan="4">Vendor</th>
                        <th>HZ</th>
                    </tr>
                    <tr>
                        <th>INV</th>
                        <th>S<span>o</span></th>
                        <th>PO<span>amz</span></th>
                        <th>C<span>ombo</span><br>(PO<span>amz</span>)</th>
                        <th>IN　<i class='iconfont icon-icon-test1' style='color:#DCFFE1'></i></th>
                        <th>INV</th>
                        <th>S<span>fba</span></th>
                        <th>IN　<i class='iconfont icon-icon-test1' style='color:#FFCA9A'></i></th>
                        <th>INV</th>
                        <th>EPOS<br><span>amz</span></th>
                        <th>IN<span>cf</span></th>
                        <th>IN<span>wt</span></th>
                        <th>INZ</th>
                    </tr>
                </thead>
                <tbody>
                    
                    

                </tbody>

            </table>
        </div>
        <div class="amaTankuang taozhuang" style="display: none;">
            <div class="content">
                <div class="ama_header">
                    <div class="ama_title pull-left">相关套装Delta</div>
                    <div class="close"><i class="iconfont icon-chanpinshanchu"></i></div>
                </div>
                <div class="tablebox" style="height:240px;margin-bottom:10px;" id="taozhuangTable">
                	<table class="table">
                	    <thead>
                	        <tr>
                	            <th>SKU</th>
                	            <th>Delta</th>
                	        </tr>
                	    </thead>
                	    <tbody>
                	       
                	    </tbody>
                	</table>
                </div>
            </div>
        </div>
        <div class="amaTankuang irwindale" style="display: none;" id="rule_us_table">
            <div class="content">
                <div class="ama_header">
                    <div class="ama_title pull-left">Irwindale IN Plan</div>
                    <div class="close"><i class="iconfont icon-chanpinshanchu"></i></div>
                </div>
                <div class="tablebox" style="height:240px;margin-bottom:10px;">
                	<table class="table">
                	    <thead>
                	        <tr>
                	            <th>BI PO Ref#</th>
                	            <th>Po Date</th>
                	            <th>ETA</th>
                	            <th>Qty</th>
                	        </tr>
                	    </thead>
                	    <tbody>
                	        
                	    </tbody>
                	</table>
                </div>
                <div class="replan_btn pull-right">Replan</div>
            </div>
        </div>
        <div class="amaTankuang fba" style="display:none;" id="rule_fba_table">
            <div class="content">
                <div class="ama_header">
                    <div class="ama_title pull-left">FBA IN Plan</div>
                    <div class="close"><i class="iconfont icon-chanpinshanchu"></i></div>
                </div>
                <div class="tablebox" style="height:240px;margin-bottom:10px;">
                	<table class="table">
                	    <thead>
                	        <tr>
                	            <th>BI PO Ref#</th>
                 	           <th>FBA origin pickup date Qty</th>
                 	           <th>Qty</th>
                 	       </tr>
                 	   </thead>
                 	   <tbody>
                 
                 	   </tbody>
                	</table>
                </div>
                <div class="replan_btn pull-right">Replan</div>
            </div>
        </div>
    </div>
    <!-- ************内容 -->
    <div class="draggable" style="display: none;">
        <div class="item">
            <div class="item_title">其他渠道Delta <i class='iconfont icon-icon-test2'></i></div>
            <ul style='display:none;'>
                
            </ul>
        </div>
        <div class="item">
            <div class="item_title">Amazon单品Delta <i class='iconfont icon-icon-test2'></i></div>
            <ul style='display:none;'>
                
            </ul>
        </div>
        <div class="item">
            <div class="item_title">Amazon套装Delta <i class='iconfont icon-icon-test2'></i></div>
            <ul style='display:none;'>
                
            </ul>
        </div>
    </div>



	 <div class="popover fade right in" role="tooltip" id="popover" style="top: 407px; left: 866.281px; display: none;">
	   <div class="arrow" style="top: 83.3333%;"></div>
	   <h3 class="popover-title" style="display: none;"></h3>
	   <div class="popover-content">
	    <table class="table" border="0">
	    	<thead>
	      <tr>
	       <td align="left">SKU</td>
	       <td align="left">Qty</td>
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