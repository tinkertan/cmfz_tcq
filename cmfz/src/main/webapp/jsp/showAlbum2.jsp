<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--jqgrid的核心css--%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jqgrid/css/ui.jqgrid.css">
<%--jqgrid的主题css--%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jqgrid/css/css/cupertino/jquery-ui-1.8.16.custom.css">

<script type="text/javascript">
    $(function(){
        $("#albumTable").jqGrid({
            styleUI:"Bootstrap",
            url:"${pageContext.request.contextPath}/album/queryAll",
            datatype:"json",
            colNames:["编号","名称","图片","章节数","得分","作者","播音员","简介","出版时间"],
            colModel:[
                {name:"id"},
                {name:"title",editable:true},
                {name:"cover",editable:true,edittype:"file",formatter:function(cellvalue, options, rowObject){
                        return "<img style='width:50px;height:50px' src='${pageContext.request.contextPath}/uploadCover/"+cellvalue+"'/>";
                    }},
                {name:"count"},
                {name:"score"},
                {name:"author",editable:true},
                {name:"broadcast",editable:true},
                {name:"brief",editable:true},
                {name:"publishTime",editable:true,edittype:"date"}],
            pager:"#pageDiv",
            rowNum:3,
            rowList:[3,5,7],
            viewrecords:true,
            autowidth:true,
            editurl:"${pageContext.request.contextPath}/album/edit",
            height:"100%",
            multiselect:true,
            rownumbers:true,
            subGrid : true,
            subGridRowExpanded : function(subgrid_id, row_id) {
                // we pass two parameters
                // subgrid_id is a id of the div tag created whitin a table data
                // the id of this elemenet is a combination of the "sg_" + id of the row
                // the row_id is the id of the row
                // If we wan to pass additinal parameters to the url we can use
                // a method getRowData(row_id) - which returns associative array in type name-value
                // here we can easy construct the flowing
                var subgrid_table_id, pager_id;
                subgrid_table_id = subgrid_id + "_t";
                pager_id = "p_" + subgrid_table_id;
                $("#" + subgrid_id).html(
                    "<table id='" + subgrid_table_id
                    + "' class='scroll'></table><div id='"
                    + pager_id + "' class='scroll'></div>");
                jQuery("#" + subgrid_table_id).jqGrid(
                    {
                        styleUI:"Bootstrap",
                        url : "${pageContext.request.contextPath}/chapter/queryAll?albumId=" + row_id,
                        datatype : "json",
                        autowidth:true,
                        height:"100%",
                        multiselect:true,
                        colNames : [ '编号', '专辑编号', '音频名称', '音频大小','路径','上传时间','操作' ],
                        colModel : [
                            {name : "id",width : 80,key : true},
                            {name : "albumId",  width : 130},
                            {name : "title",width : 70,align : "right",editable:true},
                            {name : "size",width : 70,align : "right",editable:true},
                            {name : "downPath",width : 70,align : "right",edittype:"file",editable:true},
                            {name : "uploadTime",width : 70,align : "right",edittype:"date",editable:true},
                            {name : "downPath",width : 70,align : "right",formatter:function(cellvalue, options, rowObject){
                                    return "<a class=btn-primary' role='button' style='width:50px;height:50px' href='${pageContext.request.contextPath}/chapter/download?fileName="+cellvalue+"'>下载</>";}}
                        ],
                        rowNum : 20,
                        pager : pager_id,
                        sortname : 'num',
                        sortorder : "asc",
                        height : '100%',
                        editurl:"${pageContext.request.contextPath}/chapter/edit?albumId="+row_id,
                    });
                jQuery("#" + subgrid_table_id).jqGrid('navGrid',
                    "#" + pager_id, {
                        edit : false,
                        add : true,
                        del : true
                    },{},{
                    //这是添加部分
                    closeAfterAdd:true,
                    afterSubmit:function(response){
                        $.ajaxFileUpload({
                            url:"${pageContext.request.contextPath}/chapter/upload",
                            fileElementId:"downPath",
                            data:{"id":response.responseText},
                            type:"post",
                            success:function(){
                                $("#albumTable").trigger("reloadGrid");
                            }
                        })
                        return "[true]";
                    }
                    });
            },
        }).jqGrid("navGrid","#pageDiv",{},{
            //修改的部分
            closeAfterEdit:true,
            afterSubmit:function(response){
                $.ajaxFileUpload({
                    url:"${pageContext.request.contextPath}/album/upload",
                    fileElementId:"cover",
                    data:{"id":response.responseText},
                    type:"post",
                    success:function(){
                        $("#albumTable").trigger("reloadGrid");
                    }
                })
                return "[true]";
            }
            },{
            //添加的部分
            closeAfterAdd:true,
            afterSubmit:function(response){
                $.ajaxFileUpload({
                    url:"${pageContext.request.contextPath}/album/upload",
                    fileElementId:"cover",
                    data:{"id":response.responseText},
                    type:"post",
                    success:function(){
                        $("#albumTable").trigger("reloadGrid");
                    }
                })
                return "[true]";
            }
        });
    });

</script>
<h2><font color="#dc143c">专辑管理</font></h2>
<hr/>

<table id="albumTable"></table>
<div id="pageDiv"></div>
