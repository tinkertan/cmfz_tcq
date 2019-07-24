<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    $(function () {
        query(1);
    });
    function query(pageNum){
        var pageSize = $("#pageSizeInput").val();
        $.get("${pageContext.request.contextPath}/carousel/queryAll?pageNum="+pageNum+"&&pageSize="+pageSize,function (date) {
            showTable(date);
           showPage(date)

        },"json")
    }
    function showTable(date) {
        var index = 1;
        $("#carsouselTable").empty();
        for (c in date.list){
            var indexTd = $("<td></td>").text(index);
            var optTd = $("<td></td>").html("<input type='checkbox' class='checkboxVal' name='ids' value='"+date.list[c].id+"'>");
            var titleTd = $("<td></td>").text(date.list[c].title);
            var imgTd = $("<td></td>").html("<img src='${pageContext.request.contextPath}/uploadImg/"+date.list[c].imgPath+"' width='50px'>");
            var briefTd = $("<td></td>").text(date.list[c].id);
            var statusTd = $("<td></td>").text(date.list[c].status);
            var createTimeTd = $("<td></td>").text(date.list[c].createTime);
            $("#carsouselTable").append($("<tr></tr>").append(indexTd).append(optTd).append(briefTd).append(titleTd)
                .append(imgTd).append(statusTd).append(createTimeTd));
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
            url:"${pageContext.request.contextPath}/carousel/optCarousel",
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
    function pageSizeVal() {
        var pageSize = $("#pageSizeInput").val();
        return pageSize;
    }

    function modityCarousel() {
        var ids =$("#checkboxForm").serialize();
        var pageSize = pageSizeVal();
        var idss = ids+"&pageSize="+pageSize;
        $.get("${pageContext.request.contextPath}/carousel/updateCarousel",idss,function (date) {
            showTable(date);
            showPage(date)
        },"json");
    }
    
    function removeCarousel() {
        var ids =$("#checkboxForm").serialize();
        var pageSize = pageSizeVal();
        var idss = ids+"&pageSize="+pageSize;
        if(confirm("是否确认删除！")){
            $.post("${pageContext.request.contextPath}/carousel/deleteCarousel",idss,function (date) {
                showTable(date);
                showPage(date)
            },"json");
        }
    }

    function queryLikeCarousel() {
        
    }
    
    

</script>
<h2><font color="#7fff00">轮询图管理</font> </h2>
<hr/>
<form id="checkboxForm">
    <table id="carouselTable" class="table table-bordered table-striped table-hover">
        <thead>
            <td></td>
            <td><input type="checkbox" disabled></td>
            <td>id</td>
            <td>轮播图名称</td>
            <td>轮播图图片</td>
            <td>状态</td>
            <td>创建时间</td>
        </thead>
        <tbody id="carsouselTable"></tbody>
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
<div class="modal fade" tabindex="-1" role="dialog" id="addModel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">轮播图</h4>
            </div>
            <div class="modal-body">
                <form id="addForm" class="form-inline">
                    轮播图名称:
                    <input type="text" class="form-control" id="title" name="title" /><br/><br/>
                    图片:
                    <input type="file" class="form-control" id="imgPath" name="imgFile" />
                    <input type="hidden" name="pageSize" id="formPageSize" />
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" onclick="closeModel()">关闭</button>
                <button type="button" class="btn btn-primary" onclick="submitForm()">提交</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
