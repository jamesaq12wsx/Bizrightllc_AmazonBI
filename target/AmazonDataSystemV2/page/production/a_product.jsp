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
    <link rel="stylesheet" href="<%=path %>/page/production/jquery-easyui-1.7.0/themes/default/easyui.css">
  </head>
  <body>
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
            <p>SEARCH RESULT FOR:</p>
            <div>
              <select name="" id="condition">
                <option value="3">keyword</option>
                <option value="5" selected="selected">sku</option>
                <option value="4">asin</option>
              </select>
              <textarea rows="1" cols="20"></textarea>
              <i class="iconfont icon-Icon12"></i>
            </div>
          </div>
          <div class="typeList">
            <span style="margin-right: 20px;">My Sku</span>
            <span>Ipower</span>
            <span>Simple Deluxe</span>
            <span>Other</span>
          </div>
          <div class="tagList">
            <b>Tag:</b>
            <span>Important</span>
            <span>Abnormal</span>
            <span>New</span>
            <span>T1</span>
            <span>T2</span>
            <span>T3</span>
            <span>T4</span>
            <span>T5</span>
          </div>
          <div class="categoryList">
            <span class="addCategory">+Category...</span>
          </div>
        </div>
        <div class="borderBottom"></div>
        <div>
          <!-- <div class="tableRight"> -->
            <div id="tb" style="height:auto">
              <span href="javascript:void(0)" class="easyAdd btn1" data-options="iconCls:'icon-add',plain:true" onclick="append()"><i class="iconfont icon-jia"></i></span>
              <span href="javascript:void(0)" class="easyAdd" data-options="iconCls:'icon-add',plain:true" onclick="append1()"><i class="iconfont icon-jia btn2"></i></span>
              <span class="productBtn on">Product</span><!--
           --><span class="productMBtn">Product M</span>
            </div>
            <table style="width: 100%;"  id="dg" class="easyui-datagrid"  data-options="
            iconCls: 'icon-edit',
            singleSelect: true,
            onClickCell: onClickCell,
            pagination:true,
				pageSize:20
          ">
              <thead>
                <tr>
                  <!-- <th>Sku</th>
                  <th>Sku</th> -->
                  
                  <th data-options="field:'ourCode',editor:'text'">SKU</th>
                  <th data-options="field:'groupLeadSku',editor:'text'">groupLeadSku</th>
                  <th data-options="field:'Competitor',halign: 'center' " formatter="formatCompetitor">Competitor</th>
                  <th data-options="field:'asin',editor:'text'">asin</th>
                  <th data-options="field:'name',editor:'text',width:550">name</th>
                  <th data-options="field:'ownbrand',width:100,
						editor:{
							type:'combobox',
							options:{
								valueField:'brand',
								textField:'brand',
								method:'POST',
								url:'<%=path %>/sku/getOwnBrand_product.htm'
							}
						}">brand</th>
                  <th data-options="field:'productManager_id',width:100,
                  		formatter:function(value,row){
							return row.productManager;
						},
						editor:{
							type:'combobox',
							options:{
								valueField:'userid',
								textField:'username',
								method:'GET',
								url:'<%=path %>/user/getAccountByuser.htm'
							}
						}">productManager</th>
                  <th data-options="field:'RootCategory',width:150,
						editor:{
							type:'combobox',
							options:{
								valueField:'Category',
								textField:'Category',
								method:'POST',
								queryParams:{parentName:0},
								url:'<%=path %>/sku/getParentCategoryInfo.htm'
							}
						}">RootCategory</th>
                  <th data-options="field:'SubCategory',width:150,
						editor:{
							type:'combobox',
							options:{
								valueField:'Category',
								textField:'Category',
								method:'POST',
								queryParams:{parentName:''},
								url:'<%=path %>/sku/getParentCategoryInfo.htm'
							}
						}">SubCategory</th>
                  <th data-options="field:'ProductLine',width:150,
						editor:{
							type:'combobox',
							options:{
								valueField:'ProductLine',
								textField:'ProductLine',
								method:'POST',
								url:'<%=path %>/sku/getProductLineInfo.htm'
							}
						}">ProductLine</th>
                  <th data-options="field:'insertTime'" formatter="formatdate">insertTime</th>
                </tr>
              </thead>
            </table>
        </div>
    </div>
    <!-- ************内容 -->
  </body>
  <jsp:include page="../production/common.jsp/commonJs.jsp"/>
  
  <script src="<%=path %>/page/production/jquery-easyui-1.7.0/jquery.easyui.min.js"></script>
  <script src="<%=path %>/page/production/jsp.js/a_product.js"></script>
</html>