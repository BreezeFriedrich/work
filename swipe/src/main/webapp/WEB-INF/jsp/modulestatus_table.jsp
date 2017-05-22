<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2017/5/22
  Time: 13:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>modulestatus</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="../../resources/dataTables/css/jquery.dataTables.css">
    <script type="text/javascript" charset="utf8" src="../../resources/dataTables/js/jquery.js"></script>
    <script type="text/javascript" charset="utf8" src="../../resources/dataTables/js/jquery.dataTables.js"></script>
    <script type="text/javascript" src="../../resources/scripts/modulestatus_table.js"></script>
    <script type="text/javascript">
        <!--init Datatables-->
        $(document).ready( function () {
            $('#table_id_example').DataTable();
        } );
    </script>
</head>
<body>

    <table id="table_id_example" class="display">
        <thead>
        <tr>
            <th>Column 1</th>
            <th>Column 2</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>Row 1 Data 1</td>
            <td>Row 1 Data 2</td>
        </tr>
        <tr>
            <td>Row 2 Data 1</td>
            <td>Row 2 Data 2</td>
        </tr>
        </tbody>
    </table>

    <div id="container-table">
        <table id="table-swipeRecord"></table>
    </div>
</body>
</html>
