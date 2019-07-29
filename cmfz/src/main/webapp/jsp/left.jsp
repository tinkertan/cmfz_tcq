<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<script type="text/javascript">
    function loadShowCarousel() {
        $("#conterDiv").empty();
        $.ajax({
            url:"${pageContext.request.contextPath}/jsp/showCarousel.jsp",
            type:"get",
            success:function (data) {
                if(data.indexOf("<!doctype html>")==-1){
                    $("#conterDiv").load("${pageContext.request.contextPath}/jsp/showCarousel.jsp");
                }else {
                    window.location.href="${pageContext.request.contextPath}/jsp/login.jsp";
                }
            }
        });
        /*$("#conterDiv").load("${pageContext.request.contextPath}/jsp/showCarousel.jsp",{},function (data) {
            alert(data);
            console.log(data);
            $("#conterDiv").load("${pageContext.request.contextPath}/jsp/showCarousel.jsp");
        });*/
    }
    function loadShowAlbum() {
        $("#conterDiv").empty();
        $("#conterDiv").load("${pageContext.request.contextPath}/jsp/showAlbum2.jsp");
    }

    function loadShowArticle() {
        $("#conterDiv").empty();
        $("#conterDiv").load("${pageContext.request.contextPath}/jsp/showArticle.jsp");
    }
    function loadShowUser() {
        $("#conterDiv").empty();
        $("#conterDiv").load("${pageContext.request.contextPath}/jsp/showUser.jsp");
    }
    function loadShowEchars() {
        $("#conterDiv").empty();
        $("#conterDiv").load("${pageContext.request.contextPath}/jsp/echars.jsp");
    }

</script>

<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">

    <div class="panel panel-default">
        <div class="panel-heading" role="tab" id="headingOne" style="background: #6CE8E8">
            <h4 class="panel-title" >
                <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne" >
                    轮播图
                </a>
            </h4>
        </div>
        <div id="collapseOne" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
            <div class="panel-body">
                <ul type="none" class="nav nav-pills nav-stacked">
                    <li><a href="javascript:loadShowCarousel()" class="button" >展示所有</a></li>
                </ul>
            </div>
        </div>
    </div>
    <shiro:authenticated>
    <div class="panel panel-default" >
        <div class="panel-heading" role="tab" id="headingTwo" style="background: #D87DD8">
            <h4 class="panel-title">
                <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo" >
                    专辑
                </a>
            </h4>
        </div>
        <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo" >
            <div class="panel-body">
                <ul type="none" class="nav nav-pills nav-stacked">
                    <li><a href="javascript:loadShowAlbum()" >专辑管理</a></li>
                </ul>
            </div>
        </div>
    </div>
    <div class="panel panel-default" >
        <div class="panel-heading" role="tab" id="headingThree" style="background: #52CC33">
            <h4 class="panel-title">
                <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                    文章
                </a>
            </h4>
        </div>
        <div id="collapseThree" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
            <div class="panel-body">
                <ul type="none" class="nav nav-pills nav-stacked">
                    <li><a href="javascript:loadShowArticle()"  >文章管理</a></li>
                </ul>
            </div>
        </div>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading" role="tab" id="headingFour" style="background: #FAFA5B">
            <h4 class="panel-title">
                <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseFour" aria-expanded="false" aria-controls="collapseFour">
                    用户
                </a>
            </h4>
        </div>
        <div id="collapseFour" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
            <div class="panel-body">
                <ul type="none" class="nav nav-pills nav-stacked">
                    <li><a href="javascript:loadShowUser()"  >展示所有用户</a></li>
                    <li><a href="javascript:loadShowEchars()"  >展示用户管理</a></li>
                </ul>
            </div>
        </div>
    </div>
    <shiro:hasRole name="vipadmin">
    <div class="panel panel-default">
        <div class="panel-heading" role="tab" id="headingFive" style="background: #FAFA5B">
            <h4 class="panel-title">
                <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseFive" aria-expanded="false" aria-controls="collapseFive">
                    管理员管理
                </a>
            </h4>
        </div>
        <div id="collapseFive" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFive">
            <div class="panel-body">
                <ul type="none" class="nav nav-pills nav-stacked">
                    <li><a href="javascript:void(0)"  >展示所有管理员</a></li>
                </ul>
            </div>
        </div>
    </div>
    </shiro:hasRole>
    </shiro:authenticated>
</div>