<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>模块状态</title>
    <link rel="stylesheet" type="text/css" href="../../resources/styles/component/table.css" />
    <script type="text/javascript" src="../../resources/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="../../resources/scripts/component/table.js"></script>
    <script type="text/javascript" src="../../resources/scripts/moduleStatus.js"></script>
</head>
<body>
    <div id="container" style="margin-top: 80px;margin-left: 150px;">
        <button onclick="testA()">你好啊，来测试A</button>
        <button onclick="testB()">你好啊，来测试B</button>
        <%--
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
        --%>

        <table id="moduleStatusTable">
            <caption>刷卡模块状态表</caption>
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
            <tbody id="tbody">
            </tbody>
        </table>

        <table id="moduleStatusTable2">
            <caption>刷卡模块状态表2</caption>
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
            <tbody id="tbody2">
            </tbody>
        </table>
    </div>

</body>
</html>
