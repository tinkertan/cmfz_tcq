<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--jqgrid的核心css--%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jqgrid/css/ui.jqgrid.css">
<%--jqgrid的主题css--%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jqgrid/css/css/cupertino/jquery-ui-1.8.16.custom.css">
<style>
    #userTable tr td{
        text-align:center;
    }
</style>

<script type="text/javascript">

    $("#userTable").jqGrid({
        styleUI:"Bootstrap",
        url:"${pageContext.request.contextPath}/user/queryAll",
        datatype:"json",
        colNames:["编号","手机号","法名","省","市","性别","个性签名","头像","状态","注册时间"],
        colModel:[
            {name:"id",editable:true,resizable:true},
            {name:"phone"},
            {name:"dharmaname",editable:true},
            {name:"province"},
            {name:"city"},
            {name:"gender"},
            {name:"personalSign"},
            {name:"profile"},
            {name:"status",editable:true,edittype:'select',editoptions:{value:'正常:正常;冻结:冻结'}},
            {name:"registTime"}],
        pager:"#pageDiv",
        rowNum:3,
        rowList:[3,5,7],
        viewrecords:true,
        autowidth:true,
        height:"100%",
        multiselect:true,
        rownumbers:true,
    }).jqGrid("navGrid","#pageDiv",{"add":false,"del":false,"edit":false},{},{}).navButtonAdd('#pageDiv',{
        caption:"",
        buttonicon:"glyphicon glyphicon-edit",
        onClickButton: function(){
            updateUser();
        },
        position:"last"
    })  ;
    function updateUser() {
        var id = jQuery("#userTable").jqGrid('getGridParam', 'selarrrow');
        var s = id.toString();
        $.ajax({
            url:"${pageContext.request.contextPath}/user/edit",
            data:{"ids":s},
            type:"get",
            success:function () {
                $("#userTable").trigger("reloadGrid");
            }
        });
    }
    function exportButton() {
        location.href="${pageContext.request.contextPath}/user/exportFile"
    }

    function importButton() {
        var formData = new FormData(document.getElementById("importForm"));
        $.ajax({
            url:"${pageContext.request.contextPath}/user/importUser",
            type:"post",
            data:formData,
            processData:false,
            contentType : false,
            success:function () {
                $("#userTable").trigger("reloadGrid");
            }
        });
    }

</script>

    <h2><font color="#FFAD33">用户管理</font></h2>
<div align="center">
    <form class="form-inline" id="importForm">
        <div class="form-group">
            <a class="btn btn-default" href="#" onclick="exportButton()" style="background: #45da2a">一键导出</a>
        </div>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <div class="form-group">
            <input type="file" name="file" style="background: #33c5dc"/>
        </div>
        <input type="button" class="btn btn-default" onclick="importButton()" value="一键导入" style="background: #b5239c"/>
    </form>
</div>


<hr/>
<div id="articleDiv">
    <table id="userTable"></table>
    <div id="pageDiv"></div>
</div>


