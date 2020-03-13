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
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />

    <title>产品线SKU预测</title>
    <jsp:include page="../production/common.jsp/commonCss.jsp"/>
    <!-- easyui核心UI文件 css -->
    <link rel="stylesheet" href="<%=path %>/page/production/jquery-easyui-1.7.0/themes/default/easyui.css">
    <!-- easyui图标 -->
    <link rel="stylesheet" href="<%=path %>/page/production/jquery-easyui-1.7.0/themes/icon.css">
    <!-- 自定义massage消息弹框样式 -->
    <link href="<%=path %>/page/production/css/alertmassage.css" rel="stylesheet">
    <style>
        /*固定浮动首列表头*/
        #left_div{
            width:30%;
            float: left;
        }
        #left_div1{
            width: 100%;
        }
        #left_div2{
            margin-top:-20px;
            width: 100%;
            height: 400px;
            overflow: hidden;
        }
        #left_table1{
            table-layout:fixed;
        }
        #left_table2{
            /**width和max-width一起写，手机浏览器打开也能固定长度**/
            table-layout:fixed;
        }
        #left_table1 th{
            background: #E9F8FF;
            text-align:center;
            height:60px;
            width:30%;
        }
        #left_table2 td{
            text-align:center;
            white-space:nowrap;
            height: 40px;
            width:30%;
            overflow: hidden;
            text-overflow: ellipsis;
        }

        #right_div{
            float: left;
            margin-top: -461px;
            margin-left: 30%;

        }
        #right_div1{
            width: 60%;
            overflow: hidden;
        }
        #right_divx{
            width: 100%;
        }
        #right_div2{
            margin-top:-20px;
            width: calc(60% + 17px);
            height:420px;
            overflow: auto;
        }
        #right_table1{
            width: 880px;
            table-layout:fixed;
            white-space:nowrap;
        }
        #right_table2{
            /**width和max-width一起写，手机浏览器打开也能固定长度**/
            width: 880px;
            max-width: 880px;
            white-space:nowrap;
            table-layout:fixed;
        }
        #right_table1 th{
            background: #E9F8FF;
            text-align:center;
            width: 200px;
            height: 60px;
        }
        #right_table2 td{
            width: 200px;
            text-align:center;
            height: 40px;
        }
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
            <p>SEARCH RESULT FOR ProductLine:</p>

            <div style="width:315px;border:none;border-bottom: 1px solid #E1E1E1;">
                <input style="height:30px;width:250px;" value="" id="ProductLine" name="ProductLine" class="easyui-combobox">
                <i class="iconfont icon-Icon12" style="cursor:pointer;"  id="searchBtn"></i>
            </div>
        </div>
    </div>
    <!-- 分割线 -->
    <div class="borderBottom"></div>


    <%--<div style="overflow-x: auto;">--%>
        <%--<table id="productlineskutable" class="table productlineskutable">--%>
            <%--<thead>--%>
            <%--</thead>--%>
            <%--<tbody>--%>
            <%--</tbody>--%>
        <%--</table>--%>
    <%--</div>--%>




    <!-- 切分部分 -->
    <div class="container-fluid">
        <div id="left_div">
            <div id="left_div1">
                <table id="left_table1" class="table table-bordered">
                </table>
            </div>
            <div id="left_div2">
                <table id="left_table2" class="table table-bordered">
                </table>
            </div>
        </div>
        <div id="right_div">
            <div id="right_div1">
                <div id="right_divx">
                    <table id="right_table1" class="table table-bordered">
                    </table>
                </div>
            </div>
            <div id="right_div2">
                <table id="right_table2" class="table table-bordered">
                </table>
            </div>
        </div>
    </div>



    <!-- 库存预警提示框 -->
    <!-- LA库存果果 -->
    <div id="alertLAExessInventory" class="messagealertdiv"  >
        <table id="alertLAExessInventoryTable" class="messagealerttable">
            <tr>
                <td>
                     LA库存过多!!!
                </td>
            </tr>
        </table>
    </div>
    <!-- LA库存过少 -->
    <div id="alertLALessInventory" class="messagealertdiv"  >
        <table id="alertLALessInventoryTable" class="messagealerttable">
            <tr>
                <td>
                     LA库存不足,请及时下单!!!
                </td>
            </tr>
        </table>
    </div>
    <!-- Prime库存过少 -->
    <div id="alertPrimeLessInventory" class="messagealertdiv"  >
        <table id="alertPrimeLessInventoryTable" class="messagealerttable">
            <tr>
                <td>
                     Prime库存不足风险!!!
                </td>
            </tr>
        </table>
    </div>
</div>


<jsp:include page="../production/common.jsp/commonJs.jsp"/>
</body>

<script src="<%=path %>/page/production/jquery-easyui-1.7.0/jquery.easyui.min.js"></script>

</html>