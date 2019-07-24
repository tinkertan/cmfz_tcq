<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    $(function () {
        query(1);
    });
    function query(pageNum){
        var pageSize = $("#pageSizeInput").val();
        $.get("${pageContext.request.contextPath}/album/queryAll?pageNum="+pageNum+"&&pageSize="+pageSize,function (date) {
            showTable(date);
           showPage(date)

        },"json")
    }
    function showTable(date) {
        var index = 1;
        $("#albumTable").empty();
        for (c in date.list){
            var indexTd = $("<td></td>").text(index);
            var optTd = $("<td></td>").html("<input type='checkbox' class='checkboxVal' name='ids' value='"+date.list[c].id+"'>");
            var idTd = $("<td></td>").text(date.list[c].id);
            var titleTd = $("<td></td>").text(date.list[c].title);
            var coverTd = $("<td></td>").html("<img src='${pageContext.request.contextPath}/uploadImg/"+date.list[c].cover+"' width='50px'>");
            var countTd = $("<td></td>").text(date.list[c].count);
            var scoreTd = $("<td></td>").text(date.list[c].score);
            var authorTd = $("<td></td>").text(date.list[c].author);
            var broadcastTd = $("<td></td>").text(date.list[c].broadcast);
            var briefTd = $("<td></td>").text(date.list[c].brief);
            var publishTimeTd = $("<td></td>").text(date.list[c].publishTime);
            $("#albumTable").append($("<tr></tr>").append(indexTd).append(optTd).append(idTd).append(titleTd).append(coverTd)
                .append(countTd).append(scoreTd).append(authorTd).append(broadcastTd).append(briefTd).append(publishTimeTd));
            index++;
        }
    }

    function addCarousel() {
        $("#addModel").modal('show')
    }
    function submitForm() {
        $("#addModel").modal('hide');
        $("#formPageSize").val(pageSizeVal())
        var formContent = new FormData(document.getElementById("addForm"));
        $.ajax({
            type: "post",
            url:"${pageContext.request.contextPath}/album/opt",
            dataType:"json",
            processData:false,
            contentType : false,
            data:formContent,
            success:function (date) {
                showTable(date);
                showPage(date)
            }
        });
        document.getElementById("addForm").reset();
    }

    function closeModel() {
        $("#addModel").modal('hide');
        document.getElementById("addForm").reset();
    }


    function pageSizeVal() {
        var pageSize = $("#pageSizeInput").val();
        return pageSize;
    }

    function modityCarousel() {
        $("#addModel").modal('show');
        var albumId =$("#checkboxForm").serialize();
        $.post("${pageContext.request.contextPath}/album/queryById",albumId,function (date) {
            $("#titleInput").val(date.title);
            $("#countInput").val(date.count);
            $("#authorInput").val(date.author);
            $("#broadcastInput").val(date.broadcast);
            $("#briefInput").val(date.brief);
            $("#publishTimeInput").val(date.publishTime);
            $("#albumIdInput").val(date.id);
        },"json");
    }
    
    function removeCarousel() {
        var ids =$("#checkboxForm").serialize();
        var pageSize = pageSizeVal();
        var idss = ids+"&pageSize="+pageSize;
        if(confirm("是否确认删除！")){
            $.post("${pageContext.request.contextPath}/album/remove",idss,function (date) {
                showTable(date);
                showPage(date)
            },"json");
        }
    }

    function queryLikeCarousel() {
        
    }
    
    

</script>
<h2>专辑管理</h2>
<hr/>
<form id="checkboxForm">
    <table id="carouselTable" class="table table-bordered table-striped table-hover">
        <thead>
            <td></td>
            <td><input type="checkbox" disabled></td>
            <td>id</td>
            <td>标题</td>
            <td>封面</td>
            <td>章节数</td>
            <td>得分</td>
            <td>作者</td>
            <td>播音员</td>
            <td>简介</td>
            <td>发布时间</td>
        </thead>
        <tbody id="albumTable"></tbody>
    </table>
</form>


<div align="center">
    <nav aria-label="Page navigation">
        <ul class="nav navbar-nav">
            <li><a href="#" onclick="addCarousel()"><span class="glyphicon glyphicon-plus"></span></a><li>
            <li><a href="#" onclick="modityCarousel()"><span class="glyphicon glyphicon-edit"></span></a><li>
            <li><a href="#" onclick="removeCarousel()"><span class="glyphicon glyphicon-trash"></span></a><li>
            <li><a href="#" onclick="queryLikeCarousel()"><span class="glyphicon glyphicon-search"></span></a><li>
            <li><a href="#" onclick="query(1)"><span class="glyphicon glyphicon-refresh"></span></a><li>

        </ul>
        <ul class="nav navbar-nav" id="pageNav">
        </ul>
        <ul class="nav navbar-nav">
            <li><input type='number'id='pageSizeInput' value='2' width="1px" style="width: 40px;height: 33px"/></li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <li id="countNum"></li>
        </ul>
    </nav>
</div>


<%--模态框--%>
<div class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" id="addModel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">轮播图</h4>
            </div>
            <div class="modal-body" align="center">
                <form id="addForm" class="form-inline">
                    专辑名称: &nbsp;&nbsp;<input type="text" class="form-control" id="titleInput" name="title" /><br/><br/>
                    封面:&nbsp;&nbsp;<input type="file" class="form-control" id="coverInput" name="uploadFile" /><br/><br/>
                    章节数:&nbsp;&nbsp; <input type="number" class="form-control" id="countInput" name="count" /><br/><br/>
                    作者: &nbsp;&nbsp;<input type="text" class="form-control" id="authorInput" name="author" /><br/><br/>
                    播音员:&nbsp;&nbsp; <input type="text" class="form-control" id="broadcastInput" name="broadcast" /><br/><br/>
                    简介: &nbsp;&nbsp;<textarea class="form-control" id="briefInput" name="brief" /><br/><br/>
                    发布时间: &nbsp;&nbsp;<input type="date" class="form-control" id="publishTimeInput" name="publishTime" /><br/>
                    <input type="hidden" name="pageSize" id="formPageSize" />
                    <input type="hidden" name="id" id="albumIdInput" />
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" onclick="closeModel()">关闭</button>
                <button type="button" class="btn btn-primary" onclick="submitForm()">提交</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
