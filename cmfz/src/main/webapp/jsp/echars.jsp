<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>

<script type="text/javascript">
    $(function () {
        //goeasy
        var goEasy = new GoEasy({
            appkey: 'BC-8ea535922c6245859cd20df68361f338'
        });
        goEasy.subscribe({
            channel:'demo_channel',
            onMessage: function(message){
                var data = JSON.parse(message.content);
                showTable(data);
            }
        });
        //页面加载的ajax
        $.ajax({
            url:"${pageContext.request.contextPath}/user/stats",
            type:"post",
            dataType:"json",
            success:function (data) {
                showTable(data);
            }
        });

    })

    function showTable(data) {
        //这是人数月份统计
        var myChart = echarts.init(document.getElementById('main'));
        //这是地区人数（性别）统计
        var chart = echarts.init(document.getElementById('area'));
        //指定图表的配置项和数据
        var option = {
            title: {
                text: '注册用户情况统计'
            },
            tooltip: {},
            legend: {
                data:['人数']
            },
            xAxis: {
                data:data.month
            },
            yAxis: {},
            series: [{
                name: '人数',
                type: 'bar',
                data: data.personNum
            }]
        };
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);

        areaOption = {
            title : {
                text: '注册地区',
                subtext: '',
                left: 'center'
            },
            tooltip : {
                trigger: 'item'
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                data:['男','女']
            },
            visualMap: {
                min: 0,
                max: 2500,
                left: 'left',
                top: 'bottom',
                text:['高','低'],           // 文本，默认为数值文本
                calculable : true
            },
            toolbox: {
                show: true,
                orient : 'vertical',
                left: 'right',
                top: 'center',
                feature : {
                    mark : {show: true},
                    dataView : {show: true, readOnly: false},
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            series : [
                {
                    name: '男',
                    type: 'map',
                    mapType: 'china',
                    label: {
                        normal: {
                            show: false
                        },
                        emphasis: {
                            show: true
                        }
                    },
                    data:data.areaMan
                },
                {
                    name: '女',
                    type: 'map',
                    mapType: 'china',
                    label: {
                        normal: {
                            show: false
                        },
                        emphasis: {
                            show: true
                        }
                    },
                    data:data.areaWoman
                }
            ]
        };
        // 这是地图的图表
        chart.setOption(areaOption);
    }

</script>

<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
<div id="main" style="width: 600px;height:400px;"></div>
<div id="area" style="width: 600px;height:400px;"></div>

