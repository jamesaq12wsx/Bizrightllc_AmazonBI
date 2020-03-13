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

    <title>周|月销量报表</title>
    <jsp:include page="../production/common.jsp/commonCss.jsp"/>
    <!-- easyui核心UI文件 css -->
    <link rel="stylesheet" href="<%=path %>/page/production/jquery-easyui-1.7.0/themes/default/easyui.css">
    <!-- easyui图标 -->
    <link rel="stylesheet" href="<%=path %>/page/production/jquery-easyui-1.7.0/themes/icon.css">
    <style type="text/css">
        /*固定浮动首列表头*/
        #left_div{
            width:150px;
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
        #left_table1 th{
            background: #E9F8FF;
            text-align:center;
        }
        #left_table2 td{
            text-align:center;
            white-space:nowrap;
            height: 30px;
        }

        #right_div{
            float: left;
            margin-top: -435px;
            margin-left: 150px;
        }
        #right_div1{
            width: 755px;
            overflow: hidden;
        }
        #right_divx{
            width: 900px;
        }
        #right_div2{
            margin-top:-20px;
            width: 770px;
            height:420px;
            overflow: auto;
        }
        #right_table1{
            width: 880px;
            table-layout:fixed;
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
            width: 100px;
        }
        #right_table2 td{
            width: 100px;
            text-align:center;
            height: 30px;
        }
    </style>
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
        <div>

            <!-- 统计条件选择 -->
            <table style="border-collapse:separate; border-spacing:10px;">
                <tr><th>SEARCH FOR:</th></tr>
                <tr>
                    <td style="text-align: right">
                        统计方式:
                    </td>
                    <td>
                        <label><input type="radio" name="counttype" value="amount" checked>金额</label>
                        <label><input type="radio" name="counttype" value="sales">销量</label>
                    </td>
                </tr>
                <tr>
                    <td style="text-align: right">
                        统计时间单位:
                    </td>
                    <td>
                        <select name="" id="counttime" onchange="changeSelect()">
                            <option value="weekly" selected="selected">按周</option>
                            <option value="monthly">按月</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td style="text-align: right">
                        统计周/月数:
                    </td>
                    <td>
                        <select name="" id="countnum">
                        </select>
                    </td>
                </tr>
                <tr>
                    <td style="text-align: right">
                        SKU可选列表:
                    </td>
                    <td>
                        <input style="height:30px;width:250px;" value="" id="ourCode" name="ourCode" class="easyui-combobox" >
                    </td>
                </tr>
            </table>

            <div style="width:315px;border:none;border-bottom: 1px solid #E1E1E1;">
                <i class="iconfont icon-Icon12" style="cursor:pointer;"  id="searchBtn"></i>
            </div>
        </div>
    </div>
    <!-- 分割线 -->
    <div class="borderBottom"></div>


    <!-- 该表切分四块,固定表头和列头  -->
    <%--<div style="overflow-x: auto;">--%>
        <%--<table id="WeeklyAndMonthlyOutboundReport" class="table WeeklyAndMonthlyOutboundReport">--%>
            <%--<thead>--%>

            <%--</thead>--%>
            <%--<!-- 表格数据也是动态的 -->--%>
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

</div>


<jsp:include page="../production/common.jsp/commonJs.jsp"/>
</body>

<script src="<%=path %>/page/production/jquery-easyui-1.7.0/jquery.easyui.min.js"></script>

</html>