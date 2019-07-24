<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta charset="utf-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/boot/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/jqgrid/extend/css/trirand/ui.jqgrid-bootstrap.css">
    <script src="${pageContext.request.contextPath}/boot/js/jquery-3.3.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/boot/js/bootstrap.3.3.7.min.js"></script>
    <script src="${pageContext.request.contextPath}/jqgrid/js/i18n/grid.locale-cn.js"></script>
    <script src="${pageContext.request.contextPath}/jqgrid/extend/js/trirand/jquery.jqGrid.min.js"></script>
    <script src="${pageContext.request.contextPath}/boot/js/ajaxfileupload.js"></script>
    <script src="${pageContext.request.contextPath}/myjs/showTableAndPage.js"></script>

    <!-- 引入 ECharts 文件 -->
    <script type="text/javascript" src="http://cdn-hangzhou.goeasy.io/goeasy.js"></script>
    <script src="${pageContext.request.contextPath}/echars/echarts.js"></script>
    <script src="https://www.echartsjs.com/gallery/vendors/echarts/map/js/china.js?_v_=1553896255267"></script>
    <title>持明法洲后台管理系统</title>
</head>
<body>
<div class="container">
    <%--标题导航栏--%>
    <nav class="navbar navbar-inverse" >
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" href="#">
                    持明法洲后台管理系统 v1.0
                </a>
            </div>
            <div class="collapse navbar-collapse">
                <ul class="nav navbar-nav navbar-right">
                    <c:if test="${admin!=null}">
                        <li>
                            <a href="#">
                                欢迎：${sessionScope.admin.username}
                                <span class="glyphicon glyphicon-user"></span>
                            </a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/admin/logout">
                                退出登录
                                <span class="glyphicon glyphicon-log-out"></span>
                            </a>
                        </li>
                    </c:if>
                    <c:if test="${admin==null}">
                        <li>
                            <a href="${pageContext.request.contextPath}/jsp/login.jsp">
                                请登录
                                <span class="glyphicon glyphicon-log-in"></span>
                            </a>
                        </li>
                    </c:if>
                </ul>
            </div>
        </div>
    </nav>
    <%--页面主体--%>
    <div class="row" >
        <div class="col-sm-3">
            <%--左侧页面--%>
            <jsp:include page="left.jsp"></jsp:include>
        </div>
        <div class="col-sm-9" id="conterDiv">
            <%--中间主--%>
            <jsp:include page="center.jsp"></jsp:include>
        </div>
    </div>
    <%--页尾--%>
        <hr/>
    <div class="navbar-fixed-bottom">
        <h6 align="center">@百知教育 baizhi@zparkhr.com.cn</h6>
    </div>
</div>
</body>
</html>