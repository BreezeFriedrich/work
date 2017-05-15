import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import javax.sql.DataSource;
//import com.alibaba.druid.*;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import org.apache.commons.dbcp.BasicDataSource;
/**
 * Created by admin on 2017/5/15.
 */
public class DbPoolConnection {

    /*dbcp
    private static BasicDataSource dataSource = new BasicDataSource();
    public ConnectionPool(){
        //为数据源填写必填属性
        dataSource.setUsername("root");
        dataSource.setPassword("2015@qxidc");
        dataSource.setUrl("jdbc:mysql://localhost:3306/swipe");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        //指定数据库连接池中初始化连接数的个数
        dataSource.setInitialSize(5);
        //指定最大的连接数:同一时刻同时向数据库申请的连接数
        //最大空闲数，放洪峰过后，连接池中的连接过多，
        dataSource.setMaxActive(15);
        //指定最小连接数:数据库空闲状态下所需要保留的最小连接数
        //防止当洪峰到来时，再次申请连接引起的性能开销；
        dataSource.setMinIdle(10);
        //最长等待时间:等待数据库连接的最长时间，单位为毫秒，超出将抛出异常
        dataSource.setMaxWait(1000*5);
    }

    public Connection getConnection() throws Exception{
        return dataSource.getConnection();
    }
    */

    //druid

    private static DbPoolConnection databasePool=null;
    private static DruidDataSource druidDataSource = null;
    static {
        Properties properties = loadPropertyFile("druid.properties");
        try {
            druidDataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private DbPoolConnection() {}
    public static synchronized DbPoolConnection getInstance() {
        if (null == databasePool) {
            databasePool = new DbPoolConnection();
        }
        return databasePool;
    }
    public DruidPooledConnection getConnection() throws SQLException {
        return druidDataSource.getConnection();
    }
    public static Properties loadPropertyFile(String fullFile) {

        Properties p = new Properties();
        if(fullFile == "" || fullFile.equals(""))
        {
            System.out.println("属性文件为空!~");
        }
        else
        {
            //加载属性文件
            InputStream inStream = DbPoolConnection.class.getClassLoader().getResourceAsStream(fullFile);
            try {
                p.load(inStream);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return p;
    }
}
