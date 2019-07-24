<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<script type="text/javascript">
    function loadShowCarousel() {
        $("#conterDiv").empty();
        $("#conterDiv").load("${pageContext.request.contextPath}/jsp/showCarousel.jsp");
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
</div>