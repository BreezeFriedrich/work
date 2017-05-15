import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by admin on 2017/5/15.
 */
public class DataSource {

    public static Connection conn = null;

    public static final String url = "jdbc:mysql://localhost:3306/datainfo";
    public static final String name = "com.mysql.jdbc.Driver";
    public static final String user = "root";
    public static final String password = "2015@qxidc";


    public DataSource(){
        try {
            Class.forName(name);   //指定连接类型
            conn =(Connection) DriverManager.getConnection(url, user, password);//获取连接
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  Connection getConnecntion() {
        return conn;
    }
}
