<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<script src="https://cdn.jsdelivr.net/npm/echarts@5.3.3/dist/echarts.js"></script>
<head>
    <meta charset="UTF-8">
    <title>欢迎回来</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/static/layui/css/layui.css">
    <style>
        body{
            margin: 0px;padding: 0px;}
        #myChart{
        }
        #main{
            float: left;
        }
        #main2{
            float: right;
        }
    </style>
</head>
<body>
<br>
<br>
<fieldset class="layui-elem-field layui-field-title">
    <legend style="font-size: 26px">可视化界面</legend>
</fieldset>
<br>
<div id="myChart">
    <div id="main" style="width: 600px;height:400px;"></div>
    <div id="main2" style="width: 600px;height:400px;"></div>
</div>
</body>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));
    var myChart2 = echarts.init(document.getElementById('main2'));

    option2 = {
        xAxis: {
            data: ['18', '19', '20', '21', '22']
        },
        yAxis: {},
        series: [
            {
                data: [2, 5, 7, 16, 12],
                type: 'line',
                label: {
                    show: true,
                    position: 'bottom',
                    textStyle: {
                        fontSize: 20
                    }
                }
            }
        ]
    };

    // 指定图表的配置项和数据
    option = {
        legend: {
            orient: 'vertical',
            x: 'left',
            data: ['已出租房', '未出租房', '已预约房']
        },
        series: [
            {
                type: 'pie',
                radius: ['50%', '70%'],
                avoidLabelOverlap: false,
                label: {
                    show: false,
                    position: 'center',
                    emphasis: {
                        show: true
                    }
                },
                labelLine: {
                    show: false
                },
                emphasis: {
                    label: {
                        show: true,
                        fontSize: '30',
                        fontWeight: 'bold'
                    }
                },
                data: [
                    { value: 3, name: '已出租房' },
                    { value: 10, name: '未出租房' },
                    { value: 3, name: '已预约房' },
                ]
            }
        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
    myChart2.setOption(option2);
</script>
</html>