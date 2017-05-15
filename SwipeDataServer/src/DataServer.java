import com.alibaba.druid.pool.DruidAbstractDataSource;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;

import java.io.*;
import java.sql.*;
import java.util.Properties;

/**
 * Created by admin on 2017/5/15.
 */
public class DataServer {

//    public void test(){
//
//        Properties pro = new Properties();
//        try (FileInputStream in = new FileInputStream("druid.properties")){
//            pro.load(in);
//            in.close();
//        } catch (FileNotFoundException e1) {
//            e1.printStackTrace();
//        }catch (IOException e2) {
//            e2.printStackTrace();
//        }
//        Connection con;
//        String sql="SELECT * FROM DeviceStatus";
//        try {
//            DruidDataSource druidDataSource= (DruidDataSource) DruidDataSourceFactory.createDataSource(pro);
//            con=druidDataSource.getConnection();
//            Statement ps=con.createStatement();
//            ps.execute(sql);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//
//        }
//
//    }

    public void doSql(String sql) throws SQLException {

        DbPoolConnection dbp = DbPoolConnection.getInstance();
        DruidPooledConnection con = dbp.getConnection();
        PreparedStatement ps = null;
        ResultSet rs=null;

//        String sql = "SELECT * FROM DeviceStatus";

        ps = con.prepareStatement(sql);
        rs=ps.executeQuery();
        while (rs.next()){
            rs.getString("status");
        }
        rs.close();
        ps.close();
        con.close();
    }


}
