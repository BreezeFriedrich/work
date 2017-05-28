package com.yishu.util;

import java.sql.*;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

/**
 * Created by WindSpring on 2017/5/13.
 */
public class DBUtil {

    static int lport = 3306;//本地端口
    static String rhost = "127.0.0.1";//远程MySQL服务器
    static int rport = 3306;//远程MySQL服务端口

    public static void go() {
        String user = "qixu";//SSH连接用户名
        String password = "123456";//SSH连接密码
        String host = "112.25.233.122";//SSH服务器
        int port = 222;//SSH访问端口
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            System.out.println(session.getServerVersion());//这里打印SSH服务器版本信息
            int assinged_port = session.setPortForwardingL(lport, rhost, rport);
            System.out.println("localhost:" + assinged_port + " -> " + rhost + ":" + rport);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sql() {
        Connection conn = null;
        ResultSet rs = null;
        Statement st = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/swipe", "root", "2015@qxidc");
            st = conn.createStatement();
            String sql = "SELECT * FROM DeviceStatus";
            rs = st.executeQuery(sql);
            while (rs.next())
                System.out.println(rs.getString(2));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();st.close();conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public static void communicate(){
        go();
        sql();
    }
}
