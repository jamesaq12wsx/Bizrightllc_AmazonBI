<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


	
	<!-- jQuery必须要的 -->
    <script src="<%=path %>/page/production/vendors/jquery/dist/jquery.min.js"></script>
    
    
    <%-- <script src="<%=path %>/page/production/DataTables/jQuery-3.3.1/jquery-3.3.1.min.js"></script> --%>
    <!-- Bootstrap -->
    <script src="<%=path %>/page/production/vendors/bootstrap/dist/js/bootstrap.min.js"></script>
    
   	<script src="<%=path %>/page/production/vendors/datatables.net/js/jquery.dataTables.js"></script>
    <!-- bootstrap-daterangepicker-日期插件 必须要的 -->
    <script src="<%=path %>/page/production/vendors/moment/min/moment.min.js"></script>
    <script src="<%=path %>/page/production/vendors/bootstrap-daterangepicker/daterangepicker.js"></script>
    <script src="<%=path %>/page/production/vendors/bootstrap-datetimepicker/bootstrap-datetimepicker.js"></script>
    <script src="<%=path %>/page/production/vendors/bootstrap-datetimepicker/bootstrap-datetimepicker.zh-CN.js"></script>
    <script src="<%=path %>/page/production/js/date.js"></script>
    <%--  bootstrap input spinner  --%>
    <script src="<%=path %>/page/production/vendors/bootstrap-touchspin/src/jquery.bootstrap-touchspin.js"></script>
    <!-- echarttu  -->
    <script src="<%=path %>/page/production/js/Echarts/echarts.min.js"></script>
    <script src="<%=path %>/page/production/js/Echarts/shine.js"></script>
    <script src="<%=path %>/page/production/js/custom.min.js"></script>
    <script src="<%=path %>/page/production/js/paging.js"></script>
    <script type="text/javascript">
        var path="<%=path %>";
        var accountType=${accountType};
    </script>
    <script src="<%=path %>/page/production/js/public.js?m=<%=new Date().getTime() %>"></script>
	<script type="text/javascript">
    <%--var path="<%=path %>";--%>
    <%--var accountType=${accountType};--%>
	injectCustomJs();
	injectCustomJs_custom();
	function getHtmlDocName() {
	    var str = window.location.href;
	    str = str.substring(str.lastIndexOf("/") + 1);
	    str = str.substring(0, str.lastIndexOf("."));
	    return str;
	}
	function injectCustomJs_custom(){return ;
		/* if(getHtmlDocName()=="c_skuManage"){
			return ;
		} */
		let temp = document.createElement('script');
		temp.setAttribute('type', 'text/javascript');
		temp.setAttribute('charset', 'UTF-8');
		temp.src = path+"/page/production/js/custom.min.js";
		temp.onload = function()
		{
			this.parentNode.removeChild(this);
		};
		document.body.appendChild(temp);
	}
	function injectCustomJs(){
        if(getHtmlDocName()=="a_product"){
			return ;
		}
		let temp = document.createElement('script');
		temp.setAttribute('type', 'text/javascript');
		temp.setAttribute('charset', 'UTF-8');
		temp.src = path+"/page/production/jsp.js/"+getHtmlDocName()+".js";
		temp.onload = function()
		{
			this.parentNode.removeChild(this);
		};
		document.body.appendChild(temp);
	}
	</script>

