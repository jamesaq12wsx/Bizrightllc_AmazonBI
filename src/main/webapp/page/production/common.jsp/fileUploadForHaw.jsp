<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>上传</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="Expires" content="0">
</head>
<body>
<body>
<form action="${pageContext.request.contextPath}/uploadFile" enctype="multipart/form-data" method="POST">
    <input type="file" name="files">
    <input type="submit" value="提交">
</form>
</body>
</body>
</html>

