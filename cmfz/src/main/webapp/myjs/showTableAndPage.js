/*
var pagedata ={
    "pageTotal":pageTotal,
    "pageNum":pageNum,
    "countNum":countNum
}*/

function showPage(pagedata) {
    $("#pageNav").empty();
    $("#pageNav").append($("<li></li>").html("<a href='#' aria-label='Previous' onclick='query(1)'>" +
        " <span aria-hidden='true'>&laquo;</span></a>"));
    var pageNum = pagedata.pageNum;
    var pageTotal = pagedata.pageTotal;
    if((pageTotal-pageNum)>2||(pageTotal-pageNum)==2){
        if(pageNum>1){
            var num1= pageNum-1;
            var num2 = pageNum+3;
            for(var i =num1;i<num2;i++){
                $("#pageNav").append($("<li></li>").html("<a href='#' onclick='query("+i+")'>"+i+"</a>"));
            }
        } else {
            if(pageTotal>3){
                for(var i = pageNum;i<5;i++){
                    $("#pageNav").append($("<li></li>").html("<a href='#' onclick='query("+i+")'>"+i+"</a>"));
                }
            }else{
                for(var i = pageNum;i<pageTotal+1;i++){
                    $("#pageNav").append($("<li></li>").html("<a href='#' onclick='query("+i+")'>"+i+"</a>"));
                }
            }
        }
    }else{
        if(pageTotal>3){
            var num = pageTotal-3;
            var num2 =pageTotal+1;
            for(var i = num;i<num2;i++){
                $("#pageNav").append($("<li></li>").html("<a href='#' onclick='query("+i+")'>"+i+"</a>"));
            }
        }else {
            var num = pageTotal+1;
            for(var i = 1;i<num;i++){
                $("#pageNav").append($("<li></li>").html("<a href='#' onclick='query("+i+")'>"+i+"</a>"));
            }
        }
    }
    $("#pageNav").append($("<li></li>").html("<a href='#' aria-label='Next' onclick='query("+pageTotal+")'>" +
        " <span aria-hidden='true'>&raquo;</span></a>"));
    $("#pageNav").append($("<li></li>").html("<a href='#' >共计 "+pagedata.countNum+" 条</a>"));
}
/*
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
* */