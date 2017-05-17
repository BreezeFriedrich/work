//import java.sql.Connection;
//import java.sql.SQLException;
//
///**
// * Created by admin on 2017/5/15.
// */
////与DbPoolConection的dbcp部分合用
//public class ConThreadPool {
//
//    private  static DbPoolConnection connectionPool;
//
///*创建线程池+ThreadLocal<T>操作*/
//
//    //创建线程池
//    private static ThreadLocal<Connection> conWrapper = new ThreadLocal<Connection>();
//    //打开连接
//    private Connection getConnection() throws Exception{
//        Connection con = connectionPool.getConnection();
//        if(con != null || !con.isClosed()){
//            return con;
//        }
//        //从线程池中获取数据库连接
//        con = connectionPool.getConnection();
//        if(con == null){
//            throw new SQLException("无法获取数据库连接");
//        }
//        //对新创建的数据库连接放到线程池中
//        conWrapper.set(con);
//        return con;
//    }
//    //关闭连接
//    private void closeConnection() throws SQLException {
//        //从线程池中获取连接池
//        Connection con = conWrapper.get();
//        if(con != null){
//            con.close();
//        }
//        conWrapper.remove();
//    }
//}
