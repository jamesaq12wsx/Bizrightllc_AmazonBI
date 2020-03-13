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
    <ul class="nav"></ul>
    <!-- ************头部 -->
    <!-- 内容 -->
    <c:if test="${accountType ==1 || accountType ==2 }">
    <div class="main">
      <h1>账号管理</h1>
        <span class="btn amazonBtn pull-right" style="margin-bottom: 10px;" onclick="addAccount()">添加</span>
        <table class="table" id="userAccount">
          <thead>
            <tr>
              <td style="width: 40%;text-align: left;">账号名</td>
              <td style="width: 40%;text-align: left;">密码片</td>
              <td style="width: 20%">操作</td>
            </tr>
          </thead>
          <tbody>
            
          </tbody>
        </table> 
    </div>
    
</c:if>
<c:if test="${accountType ==1 || accountType ==2 }">
	该账号权限不够！
</c:if>
  <!-- 弹出框 -->
  <div class="cover" id="addAccount">
    <div class="model">
      <div class="modelTitle">
        <span>添加密码</span>
        <span class="delete "><i class="iconfont icon-chanpinshanchu"></i></span>
      </div>
      <div class="modelContent">
        <p class="addAccount">
          <span>主账号名:${username }</span>
          <input type="text" placeholder="请输入子账号名" id="userName">
        </p>
        <p><input type="text" placeholder="请输入密码" id="password"><select name="select" id="contentSelect"><option value="0">普通用户</option><option value="2">管理员</option></select></p>
        <p><button class="addConfirm" onclick="addUserAccount()">确认添加</button></p>
      </div>
      
    </div>
  </div>

  <!-- 弹出框 -->
  <div class="cover" id="modifyPassword">
    <div class="model">
      <div class="modelTitle">
        <span>修改密码</span>
        <span class="delete "><i class="iconfont icon-chanpinshanchu"></i></span>
      </div>
      <div class="modelContent">
        <p class="addAccount">
          <span>主账号名:${username }</span>
          <input type="text" placeholder="请输入子账号名" disabled="disabled" id="ziAccount">
        </p>
        <p><input type="text" placeholder="请输入密码" id="ziPassword"></p>
        <p><button class="addConfirm" onclick="editUserAccount()">确认修改</button></p>
      </div>
      
    </div>
  </div>
  </body>
  <jsp:include page="../production/common.jsp/commonJs.jsp"/>
</html>