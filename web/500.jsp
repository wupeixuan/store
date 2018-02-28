<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>500</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css"/>
    <script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
</head>

<body>
<!-- 动态包含 -->
<jsp:include page="/jsp/head.jsp"></jsp:include>

<div class="container-fluid">
    <h1>亲,不好意思,我们正在努力抢修~~~</h1>
</div>

<!-- 动态包含 -->
<jsp:include page="/jsp/foot.jsp"></jsp:include>

</body>

</html>