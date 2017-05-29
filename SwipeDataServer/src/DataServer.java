import com.sun.net.httpserver.*;
import model.DeviceStatus;
import model.SwipeRecord;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ModuleService;
import service.SwipeRecordService;
import service.impl.ModuleServiceImpl;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
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

    private static final int port=2017;

    public static void main(String[] args) throws IOException, SQLException {

//        ApplicationContext context = new ClassPathXmlApplicationContext("spring-mybatis.xml");
//        List<DeviceStatus> list = ((ModuleServiceImpl) (context.getBean("moduleService"))).listByStatus(0);
/*        List<DeviceStatus> list = null;
        ModuleService moduleService=null;
        moduleService=(ModuleServiceImpl) (ContextLoader.getBean("moduleService"));
        list = moduleService.listByStatus(0);
        logger.info("#OBJ      ~ moduleService:"+moduleService);
        Iterator it = list.iterator();
        DeviceStatus deviceStatus = null;
        while (it.hasNext()) {
            deviceStatus = (DeviceStatus) it.next();
            logger.info("#DATA     ~ devicestatus[i]:" + deviceStatus);
            logger.info("#DETAIL   ~ {id:" + deviceStatus.getDeviceid() + ";ip:" + deviceStatus.getDeviceip() + ";timestamp:" + deviceStatus.getTimestamp()+"}");
        }*/
        try {
            InetSocketAddress address = new InetSocketAddress(port);
            HttpServer httpServer = HttpServer.create(address, 40);//线程数量
            httpServer.createContext("/", new MyHandler());
            httpServer.setExecutor(Executors.newCachedThreadPool());
            httpServer.start();
            logger.info("#CON      ~ Server is listening on port" +httpServer.getAddress()+"|||"+address.getPort());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
class MyHandler implements HttpHandler{

    private static  final Logger logger = LoggerFactory.getLogger(HttpHandler.class);
    private ModuleService moduleService=null;
    private SwipeRecordService swipeRecordService=null;

    MyHandler() throws SQLException {
        this.moduleService = (ModuleServiceImpl) ContextLoader.getBean("moduleService");
        this.swipeRecordService = (SwipeRecordService) ContextLoader.getBean("swipeRecordService");
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

//        logger.info(Thread.currentThread().getName());
//        logger.info("#THREAD   ~ "+String.valueOf(Thread.activeCount()));
//        logger.info("#OBJ      ~ moduleService:"+moduleService);

        StringBuffer readStr=null;
        readStr=new StringBuffer();//要放到handle里面
        String result="";

        String requestMethod = exchange.getRequestMethod();
        if (!requestMethod.equalsIgnoreCase("POST")) {
            return;
        }

        Headers responseHeaders = exchange.getResponseHeaders();
        responseHeaders.set("Content-Type", "text/json");
        exchange.sendResponseHeaders(200, 0);
        OutputStream responseBody = exchange.getResponseBody();
        Headers requestHeaders = exchange.getRequestHeaders();

        InputStream requestBody=exchange.getRequestBody();
        InputStreamReader inputStreamReader=new InputStreamReader(requestBody,"UTF-8");
        BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
        String line="";

        while((line=bufferedReader.readLine())!=null){
            readStr.append(line+"\n");
        }
        result=new String(readStr);
        String method=exchange.getRequestMethod();
        URI uri=exchange.getRequestURI();
        logger.info("#DATA     ~ request-data:"+result);

        ObjectMapper objectMapper=new ObjectMapper();
        Map map=null;
        JsonNode node=objectMapper.readTree(result);
        int sign=node.get("sign").asInt();
//        logger.info("sign:"+String.valueOf(sign));

        Iterator it=null;
        List<DeviceStatus> tempDeviceStatusList=null;
        List<DeviceStatus> deviceStatusList=null;
        DeviceStatus deviceStatus=null;

        List<SwipeRecord> tempSwipeRecordList=null;
        List<SwipeRecord> swipeRecordList=null;
        SwipeRecord swipeRecord=null;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=null;

        switch (sign) {
            case 1:// listByStatus
                int status=node.get("status").asInt();
                deviceStatusList=new ArrayList();
                map=new HashMap();
                tempDeviceStatusList=moduleService.listByStatus(status);
                it=tempDeviceStatusList.iterator();
//                logger.info("#TAG      ~ start >>>>");
                map.put("result",0);//0--获取数据成功
                while(it.hasNext()){
                    deviceStatus= (DeviceStatus) it.next();
                    deviceStatusList.add(deviceStatus);
                }
                map.put("data",deviceStatusList);
//                logger.info("#TAG      ~ end   <<<<");
                logger.info("sign:"+sign+"  #DATA:"+String.valueOf(map));
                responseBody.write(objectMapper.writeValueAsBytes(map));
                map=null;
                break;

            case 2://listAllWithoutDuplicate
                deviceStatusList=new ArrayList();
                map=new HashMap();
                tempDeviceStatusList=moduleService.listAllWithoutDuplicate();
                it=tempDeviceStatusList.iterator();
                map.put("result",0);//0--获取数据成功
                while(it.hasNext()){
                    deviceStatus= (DeviceStatus) it.next();
                    deviceStatusList.add(deviceStatus);
                }
                map.put("data",deviceStatusList);
                logger.info("sign:"+sign+"  #DATA:"+String.valueOf(map));
                responseBody.write(objectMapper.writeValueAsBytes(map));
                map=null;
                break;

            case 3://swipeRecord/listAll
                swipeRecordList=new ArrayList<>();
                map=new HashMap();
                tempSwipeRecordList=swipeRecordService.listAll();
                it=tempSwipeRecordList.iterator();
                map.put("result",0);
                while (it.hasNext()){
                    swipeRecord= (SwipeRecord) it.next();
                    swipeRecordList.add(swipeRecord);
                }
                map.put("data",swipeRecordList);
                logger.info("#DATA     ~ response-data:"+String.valueOf(map));
                responseBody.write(objectMapper.writeValueAsBytes(map));
                map=null;
                break;

            case 4://swipeRecord/listByTimezone
                swipeRecordList=new ArrayList<>();
                map=new HashMap();
                String startTime=node.get("startTime").asText();
                String endTime=node.get("endTime").asText();
//                logger.info("tag2 - startTime.date="+startTime+"; endTime.date"+endTime);
//                String startTime=node.get("startTime").asText();
//                String endTime=node.get("endTime").asText();
                tempSwipeRecordList=swipeRecordService.listByTimezone(startTime.toString(),endTime.toString());
                it=tempSwipeRecordList.iterator();
                map.put("result",0);
                while (it.hasNext()){
                    swipeRecord= (SwipeRecord) it.next();
                    swipeRecordList.add(swipeRecord);
                }
                map.put("data",swipeRecordList);
                logger.info("#DATA     ~response-data:"+String.valueOf(map));
                responseBody.write(objectMapper.writeValueAsBytes(map));
                map=null;
                break;

            default:break;
            }
            bufferedReader.close();
            inputStreamReader.close();
            requestBody.close();
            responseBody.close();
            requestBody.close();
    }

}