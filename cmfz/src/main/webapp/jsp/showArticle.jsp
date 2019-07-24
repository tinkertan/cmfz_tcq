<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--jqgrid的核心css--%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jqgrid/css/ui.jqgrid.css">
<%--jqgrid的主题css--%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jqgrid/css/css/cupertino/jquery-ui-1.8.16.custom.css">
<script charset="utf-8" src="../kindeditor/kindeditor-all.js"></script>
<script charset="utf-8" src="../kindeditor/lang/zh-CN.js"></script>

<script type="text/javascript">
    $(function(){
        queryArticle();
        KindEditor.create('#editor11', {
            width : '700px',
            uploadJson:'${pageContext.request.contextPath}/article/upload',
            fileManagerJson:'${pageContext.request.contextPath}/article/showPic',
            allowFileManager:true,
            filePostName:'content',
            afterBlur:function(){
                this.sync();
            }
        });
    });
    function getGuru() {
        var str="'";
        $.ajax({
            url:"${pageContext.request.contextPath}/guru/queryAllNoPage",
            type:"post",
            async:false,
            success:function (date) {
                for(var i=0;i<date.length;i++){
                    var option = $("<option name='guruId' value='"+date[i].id+"'>"+date[i].name+"</option>")
                    $("#guruNameSelect").append(option);
                }
            }
        });
        return str;
    }

    function queryArticle() {
        $("#guruTable").empty();
        $("#guruH2").text("");
        $("#guruPageDiv").empty();
        $("#articleTable").jqGrid({
            styleUI:"Bootstrap",
            url:"${pageContext.request.contextPath}/article/queryAll",
            datatype:"json",
            colNames:["编号","上师姓名","上师id","文章标题","内容","发布时间"],
            colModel:[
                {name:"id"},
                {name:"guru.name"},
                {name:"guruId",editable:true,edittype:'select',editoptions:{value:getGuru()}},
                {name:"title",editable:true},
                {name:"id",formatter:function(cellvalue, options, rowObject){
                        return "<a class=btn-primary' role='button' style='width:50px;height:50px' href='#' onclick='queryArticleById(\""+cellvalue+"\")'>预览</a>";}},
                {name:"publishTime",editable:true,edittype:"date"}],
            pager:"#articlePageDiv",
            rowNum:3,
            rowList:[3,5,7],
            viewrecords:true,
            autowidth:true,
            editurl:"${pageContext.request.contextPath}/article/edit",
            height:"100%",
            multiselect:true,
            rownumbers:true,
        }).jqGrid("navGrid","#articlePageDiv",{"add":false,"del":true,"edit":false},{},{
            //添加的部分
            closeAfterAdd:true,
            afterSubmit:function(response){
                $.ajaxFileUpload({
                    url:"${pageContext.request.contextPath}/article/upload",
                    fileElementId:"content",
                    data:{"id":response.responseText},
                    type:"post",
                    success:function(){
                        $("#articleTable").trigger("reloadGrid");
                    }
                })
                return "[true]";
            }
        }).navButtonAdd('#articlePageDiv',{
            caption:"",
            buttonicon:"glyphicon glyphicon-plus",
            onClickButton: function(){
                $("#addModel").modal('show');
            },
            position:"last"
        });
    }

    function queryGuru() {
        $("#articleDiv").empty();
        $("#articleButton").empty();
        $("#articleH2").text("");
        $("#guruH2").text("上师管理");
        $("#articlePageDiv").empty();
        $("#guruTable").jqGrid({
            styleUI:"Bootstrap",
            url:"${pageContext.request.contextPath}/guru/queryAll",
            datatype:"json",
            colNames:["编号","上师姓名","头像","状态","性别"],
            colModel:[
                {name:"id"},
                {name:"name",editable:true},
                {name:"profile",editable:true,edittype:"file",formatter:function(cellvalue, options, rowObject){
                        return "<img style='width:50px;height:50px' src='${pageContext.request.contextPath}/uploadProfile/"+cellvalue+"'/>";
                    }},
                {name:"status",editable:true,edittype:'select',editoptions:{value:'正常:正常;冻结:冻结'}},
                {name:"sex",editable:true,edittype:'select',editoptions:{value:'女:女;男:男'}}],
            pager:"#guruPageDiv",
            rowNum:3,
            rowList:[3,5,7],
            viewrecords:true,
            autowidth:true,
            editurl:"${pageContext.request.contextPath}/guru/edit",
            height:"100%",
            multiselect:true,
            rownumbers:true,
        }).jqGrid("navGrid","#guruPageDiv",{"add":true,"del":false,"edit":false},{},{
            //添加的部分
            closeAfterAdd:true,
            afterSubmit:function(response){
                $.ajaxFileUpload({
                    url:"${pageContext.request.contextPath}/guru/upload",
                    fileElementId:"profile",
                    data:{"id":response.responseText},
                    type:"post",
                    success:function(){
                        $("#guruTable").trigger("reloadGrid");
                    }
                })
                return "[true]";
            }
        }).navButtonAdd('#guruPageDiv',{
            caption:"",
            buttonicon:"glyphicon glyphicon-edit",
            onClickButton: function(){
                var id = jQuery("#guruTable").jqGrid('getGridParam', 'selarrrow');
                var ids = id.toString();
                $.ajax({
                    url:"${pageContext.request.contextPath}/guru/edit",
                    data:{"ids":ids},
                    type:"post",
                    success:function () {
                        $("#guruTable").trigger("reloadGrid");
                    }
                });
            },
            position:"last"
        }) ;
    }

    //这是查询文章内容并展示的方法
    function queryArticleById(id) {
        $("#showContent").modal("show");
        $.ajax({
            url:"${pageContext.request.contextPath}/article/queryById",
            data:{"id":id},
            type:"post",
            success:function (data) {
                $("#contentSpan").html(data.content);
            }
        });
    }
    function submitAddForm() {
        $.ajax({
            url:"${pageContext.request.contextPath}/article/edit",
            data:$("#addForm").serialize(),
            type:"post",
            success:function () {
                $("#articleTable").trigger("reloadGrid");
            }
        });
        $("#addModel").modal('hide');
    }
</script>

    <h2 id="articleH2"><font color="#07C607">文章管理</font></h2>
    <div align="right" id="articleButton">
        <button class="btn-primary" onclick="queryGuru()">上师管理</button>
    </div>
    <hr/>
<div id="articleDiv">
    <table id="articleTable"></table>
    <div id="articlePageDiv"></div>
</div>

<div id="guruDiv">
    <h2 id="guruH2"><font color="#07C607">上师管理</font></h2>
    <hr/>
    <table id="guruTable"></table>
    <div id="guruPageDiv"></div>
</div>

<div class="modal fade" role="dialog" id="addModel" >
    <div class="modal-dialog" role="document">
        <div class="modal-content" style="width: 730px">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">添加文章</h4>
            </div>
            <form id="addForm" class="form-horizontal">
            <div class="modal-body">
                上师姓名：&nbsp; &nbsp;<select type="text" class="form-control" name="guruId" id="guruNameSelect"></select><br/>
                文章标题：&nbsp; &nbsp;<input type="text" class="form-control" name="title" ><br/>
                <textarea id="editor11" name="content" style="width:700px;height:300px;">
                    &lt;strong&gt;文章内容&lt;/strong&gt;
                </textarea><br/>
                上传时间：&nbsp; &nbsp;<input type="date" class="form-control" name="publishTime" ><br/>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="submitAddForm()">保存</button>
            </div>
            </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<div class="modal fade" role="dialog" id="showContent" style="width: 1024px">
    <div class="modal-dialog" role="document">
        <div class="modal-content" style="width: 1024px">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">文章预览</h4>
            </div>
                <div class="modal-body">
                    <div id="contentSpan" style="width: 1024px">
                        <br style="clear:both;" />
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
