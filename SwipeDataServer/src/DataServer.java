import com.sun.net.httpserver.*;
import model.DeviceStatus;
import org.apache.ibatis.session.SqlSessionFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.ModuleService;

import javax.net.ssl.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.security.KeyStore;
import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

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

//    public void doSql(String sql) throws SQLException {
//
//        DbPoolConnection dbp = DbPoolConnection.getInstance();
//        DruidPooledConnection con = dbp.getConnection();
//        PreparedStatement ps = null;
//        ResultSet rs=null;
//
////        String sql = "SELECT * FROM DeviceStatus";
//
//        ps = con.prepareStatement(sql);
//        rs=ps.executeQuery();
//        while (rs.next()){
//            rs.getString("status");
//        }
//        rs.close();
//        ps.close();
//        con.close();
//    }

    private static  final Logger logger = LoggerFactory.getLogger(DataServer.class);

    public static Logger getLogger() {
        return logger;
    }

    private static final int port=222;

    public static void getBeans(){

        ApplicationContext context=new ClassPathXmlApplicationContext("spring-mybatis.xml");
        SqlSessionFactory sqlSessionFactory= (SqlSessionFactory) context.getBean("SqlSessionFactory");
        try {
            System.out.println(sqlSessionFactory.openSession().getConnection().getClientInfo().elements().toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException{
//        getBeans();
        try {
            // setup the socket address
            InetSocketAddress address = new InetSocketAddress(port);

            // initialise the HTTPS server
            HttpsServer httpsServer = HttpsServer.create(address, 100000);
            SSLContext sslContext = SSLContext.getInstance("TLS");
//            sslContext.getSocketFactory().createSocket();

            /*
            // initialise the keystore
            char[] password = "password".toCharArray();
            KeyStore ks = KeyStore.getInstance("JKS");
            FileInputStream fis = new FileInputStream("testkey.jks");
            ks.load(fis, password);

            // setup the key manager factory
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, password);

            // setup the trust manager factory
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(ks);

            // setup the HTTPS context and parameters
            sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
            */

            httpsServer.setHttpsConfigurator(new HttpsConfigurator(sslContext) {
                public void configure(HttpsParameters params) {
                    try {
                        // initialise the SSL context
                        SSLContext c = SSLContext.getDefault();
                        SSLEngine engine = c.createSSLEngine();
                        params.setNeedClientAuth(false);
                        params.setCipherSuites(engine.getEnabledCipherSuites());
                        params.setProtocols(engine.getEnabledProtocols());

                        // get the default parameters
                        SSLParameters defaultSSLParameters = c.getDefaultSSLParameters();
                        params.setSSLParameters(defaultSSLParameters);

                    } catch (Exception ex) {
                        System.out.println("Failed to create HTTPS port");
                    }
                }
            });
//            httpsServer.createContext("/test", new MyHandler());
            httpsServer.createContext("/", new MyHandler());
            httpsServer.setExecutor(Executors.newCachedThreadPool());
            httpsServer.start();
            System.out.println("Server is listening on port" +address.getPort() );

        } catch (Exception exception) {
            System.out.println("Failed to create HTTPS server on port " + port + " of localhost");
            exception.printStackTrace();

        }
    }

}
class MyHandler implements HttpHandler{

    private static  final Logger logger = LoggerFactory.getLogger(DataServer.class);

    @Autowired
    private ModuleService moduleService;
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        System.out.println(Thread.currentThread().getName());

        String requestMethod = exchange.getRequestMethod();
        if (!requestMethod.equalsIgnoreCase("POST")) {
            return;
        }
        System.out.println("MyHandler");
        Headers responseHeaders = exchange.getResponseHeaders();
        responseHeaders.set("Content-Type", "text/json");
        exchange.sendResponseHeaders(200, 0);
        OutputStream responseBody = exchange.getResponseBody();
        Headers requestHeaders = exchange.getRequestHeaders();

        InputStream requestBody=exchange.getRequestBody();

        InputStreamReader inputStreamReader=new InputStreamReader(requestBody,"UTF-8");
        BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
        String line="";
        String result="";
        while((line=bufferedReader.readLine())!=null){
            result+=line+"\n";
        }
        String method=exchange.getRequestMethod();
        URI uri=exchange.getRequestURI();
        System.out.println("method:"+method);
        //System.out.println("uri  "+uri.getPath().length());
        System.out.println("result结果:"+result);

        ContextLoader contextLoader=ContextLoader.getInstance();
        ObjectMapper objectMapper=new ObjectMapper();
        Map map=new HashMap();

        JsonNode node=objectMapper.readTree(result);
        int sign=node.get("sign").asInt();
        System.out.println("sign:"+String.valueOf(sign));
        logger.info("sign:"+String.valueOf(sign));

        switch (sign) {
            case 1:// listByStatus
                int status=node.get("status").asInt();
                List<DeviceStatus> list=moduleService.listByStatus(status);
                Iterator it=list.iterator();
                DeviceStatus deviceStatus=null;
                while(it.hasNext()){
                    map.put("result",0);//0--获取数据成功
                    deviceStatus= (DeviceStatus) it.next();
                    map.put("data",deviceStatus);
                }
                responseBody.write(objectMapper.writeValueAsBytes(map));
//                responseBody.write(objectMapper.toString().getBytes());
                break;

            default:break;
            }
            responseBody.close();
            requestBody.close();
    }

//    public static String printHexString( byte[] b, int len)
//    {
//        String bstr="";
//        for (int i = 0; i < len; i++) {
//            String hex = Integer.toHexString(b[i] & 0xFF);
//            if (hex.length() == 1) {
//                hex = '0' + hex;
//            }
//            bstr=bstr+" "+hex.toUpperCase();
//        }
//        return bstr;
//    }

}