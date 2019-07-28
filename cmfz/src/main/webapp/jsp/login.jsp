<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<!doctype html>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="../boot/css/bootstrap.min.css">
    <link rel="stylesheet" href="../jqgrid/extend/css/trirand/ui.jqgrid-bootstrap.css">
    <script src="../boot/js/jquery-3.3.1.min.js"></script>
    <script src="../boot/js/bootstrap.3.3.7.min.js"></script>
    <script src="../jqgrid/js/i18n/grid.locale-cn.js"></script>
    <script src="../jqgrid/extend/js/trirand/jquery.jqGrid.min.js"></script>
    <script src="../boot/js/ajaxfileupload.js"></script>
    <title>持明法洲后台管理系统</title>
    <script type="text/javascript">
        function check() {
            var username = $("#username").val();
            var pwd = $("#password").val();
            if(username==null||username=="") {
                alert("账号不能为空！")
                return false
            };
            if(pwd==null||pwd==""){
                alert("密码不能为空！");
                return false;
            }
            return true;
        }
    </script>

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
        </div>
    </nav>
    <%--页面主体--%>
    <div class="row">
        <div class="col-sm-7">
            <%--左侧页面--%>
        <jsp:include page="carousel.jsp"></jsp:include>
        </div>
        <div class="col-sm-3">
        <%--中间主--%>
            <font color="red"></font>
        <form action="${pageContext.request.contextPath}/admin/login" method="post" onsubmit="return check()">
            <div class="form-group">
                <label for="username">账号</label>
                <input type="text" name="username" class="form-control" id="username">
            </div>
            <div class="form-group">
                <label for="password">密码</label>
                <input type="password" class="form-control" id="password" name="password">
            </div>
            <div class="form-group"  align="center">
                <input type="submit" value="登录">
            </div>
        </form>
    </div>
        <div class="col-sm-2"></div>
    </div>

    <%--页尾--%>
        <hr/>
    <div class="navbar-fixed-bottom">
        <h6 align="center">@百知教育 baizhi@zparkhr.com.cn</h6>
    </div>
</div>
</body>
</html>