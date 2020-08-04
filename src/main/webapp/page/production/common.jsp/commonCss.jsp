<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

    
 	<link href="<%=path %>/page/production/vendors/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- 日历 -->
    <link href="<%=path %>/page/production/vendors/bootstrap-daterangepicker/daterangepicker.css" rel="stylesheet">
    <link href="<%=path %>/page/production/vendors/bootstrap-datetimepicker/bootstrap-datetimepicker.min.css" rel="stylesheet">
    <link href="<%=path %>/page/production/vendors/datatables.net-bs/css/dataTables.bootstrap.min.css" rel="stylesheet">
    <%--input spinner--%>
    <link href="<%=path %>/page/production/vendors/bootstrap-touchspin/src/jquery.bootstrap-touchspin.css" rel="stylesheet">
    <!-- 阿里矢量 -->
    <link rel="stylesheet" href="http:////at.alicdn.com/t/font_980160_vzsbrovbpzr.css">
    <link rel="stylesheet" href="<%=path %>/page/production/css/magic-check.css">
    <link rel="stylesheet" href="http:////at.alicdn.com/t/font_1326427_4vspfyzmggm.css">
    <!-- 公共样式 -->
    <link href="<%=path %>/page/production/css/public.css" rel="stylesheet">
    
    
 	

