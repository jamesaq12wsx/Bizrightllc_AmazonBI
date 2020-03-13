<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="../production/common.jsp/commonCss.jsp"/>
</head>
<body>
<!-- 头部 -->
<ul class="nav" style="display: inline-block;"></ul>
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
    <div class="borderBottom"></div>

    <table style="width: 100%;"  id="dg" class="easyui-datagrid"  data-options="
            iconCls: 'icon-edit',
            singleSelect: true,
            onClickCell: onClickCell,
            pagination:true,
				pageSize:20
          ">
        <thead>
        <tr>
            <th data-options="field:'CategoryId',editor:'text'">CategoryId</th>
            <th data-options="field:'CategoryName',editor:'text'">CategoryName</th>
            <th data-options="field:'ChildNum',editor:'text'">ChildNum</th>
            <th data-options="field:'ParentId',editor:'text'">ParentId</th>
            <th data-options="field:'SearchKey',editor:'text'">SearchKey</th>
            <th data-options="field:'CategoryLevel',editor:'text'">CategoryLevel</th>
            <th data-options="field:'IsMonitoring',width:100,
						editor:{
							type:'combobox',
							options:{
							    data:IsMonitoringData,
								valueField:'value',
								textField:'text',
								editable:false,
								panelHeight:70,
                                required: true
							}
						}">IsMonitoring</th>
            <th data-options="field:'InsertTime'" formatter="formatdate">InsertTime</th>
        </tr>
        </thead>
    </table>

</div>
</body>
<jsp:include page="../production/common.jsp/commonJs.jsp"/>
</html>