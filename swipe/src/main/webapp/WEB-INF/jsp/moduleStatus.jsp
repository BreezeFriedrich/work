<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>模块状态</title>
    <script type="text/javascript" src="../../resources/easyui/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="../../resources/styles/moduleStatus.css" />
    <script>
        function testDB() {
            alert('testDB()');
            $.ajax({
                type:"POST",
                url:"/moduleStatus/listByStatus.do",
                data:{"status":0},
                dataType:"json",
                async:false,
                success: function(data){
                    alert(data);
                    console.log(data);
                },
                error:function(XMLHttpRequest){
                    alert('ajax-error');
//                    alert(XMLHttpRequest.status);
//                    alert(XMLHttpRequest.readyState);
//                    alert(textStatus);
                }
            })
        }
    </script>
</head>
<body>
    <button onclick="testDB()">你好啊，来测试DB</button>
    <table id="moduleStatusTable">
        <caption>模块状态表</caption>
        <thead>
            <tr>
                <th>deviceid</th>
                <th>deviceip</th>
                <th>status</th>
                <th>timestamp</th>
                <th>info</th>
                <th>reserved</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>SHTG1420151201000072</td>
                <td>192.168.111.83</td>
                <td>0</td>
                <td>2015/12/10 13:35:29</td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td>SHTG1420151201000071</td>
                <td>192.168.111.82</td>
                <td>0</td>
                <td>2015/12/10 13:35:34</td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td>SHTG1420151201000070</td>
                <td>192.168.111.81</td>
                <td>0</td>
                <td>2015/12/10 13:35:39</td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td>SHTG1420151201000068</td>
                <td>192.168.111.79</td>
                <td>0</td>
                <td>2015/12/10 13:36:01</td>
                <td></td>
                <td></td>
            </tr>
        </tbody>
    </table>
</body>
</html>
