<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!--
描述：菜单栏
-->
<div class="container-fluid">
    <div class="col-md-4">
        <img src="${pageContext.request.contextPath}/img/logo2_1.jpg"/>
    </div>
    <div class="col-md-5">
        <img src="${pageContext.request.contextPath}/img/header.png"/>
    </div>
    <div class="col-md-3" style="padding-top: 20px">
        <ol class="list-inline">
            <c:if test="${empty user }">
                <li><a
                        href="${pageContext.request.contextPath }/user?method=loginUI">登录</a></li>
                <li><a
                        href="${pageContext.request.contextPath }/user?method=registUI">注册</a></li>
            </c:if>
            <c:if test="${not empty user }">
                ${user.name }:您好
                <li><a
                        href="${pageContext.request.contextPath }/user?method=logout">退出</a></li>
                <li><a
                        href="${pageContext.request.contextPath }/order?method=findAllByPage&currPage=1">我的订单</a></li>
            </c:if>
            <li><a href="${pageContext.request.contextPath }/jsp/cart.jsp">购物车</a></li>
        </ol>
    </div>
</div>
<!--
描述：导航条
-->
<div class="container-fluid">
    <nav class="navbar navbar-inverse">
        <div class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed"
                        data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
                        aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span> <span
                        class="icon-bar"></span> <span class="icon-bar"></span> <span
                        class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="${pageContext.request.contextPath}/index.jsp">首页</a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse"
                 id="bs-example-navbar-collapse-1">
                <ul id="menuId" class="nav navbar-nav">
                    <%-- <c:forEach items="${clist }" var="c">
                        <li><a href="#">${c.cname }</a></li>
                    </c:forEach> --%>
                </ul>
                <form class="navbar-form navbar-right" role="search">
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="Search">
                    </div>
                    <button type="submit" class="btn btn-default">Submit</button>
                </form>

            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container-fluid -->
    </nav>
</div>
<script>
    $(function () {
        //发送ajax请求
        $.get("${pageContext.request.contextPath}/category?method=findAll", function (data) {
            //获取menu的ul标签
            var $ul = $("#menuId");

            //遍历数组
            $(data).each(function () {
                $ul.append($("<li><a href='${pageContext.request.contextPath}/product?method=findByPage&cid=" + this.cid + "&currPage=1'>" + this.cname + "</a></li>"));
            });
        }, "json");
    });
</script>