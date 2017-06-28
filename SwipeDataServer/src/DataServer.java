import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.*;
import model.DeviceStatus;
import model.SwipeRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import service.ModuleService;
import service.SwipeRecordService;
import service.impl.ModuleServiceImpl;
import shiro.dao.ResourceDao;
import shiro.dao.RoleDao;
import shiro.dao.UserDao;
import shiro.model.Resource;
import shiro.model.Role;
import shiro.model.User;
import shiro.service.IResourceService;
import shiro.service.IRoleService;
import shiro.service.IUserService;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLDecoder;
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

//        String encodingStr="生态";
//        String str1=new String(encodingStr.getBytes("utf-8"),"gbk");
//        String str2=new String(str1.getBytes("gbk"),"utf-8");
//        String str3=new String(encodingStr.getBytes("utf-8"),"utf-8");
//        String str4=new String(encodingStr.getBytes("ISO-8859-1"),"utf-8");
//        String str5=new String(encodingStr.getBytes("utf-8"),"ISO-8859-1");
//        System.out.println(str1);
//        System.out.println(str2);
//        System.out.println(str3);
//        System.out.println(str4);
//        System.out.println(str5);

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
        }
*/
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
//    @Autowired
//    private UserDao userDao;
//    @Autowired
//    private RoleDao roleDao;
//    @Autowired
//    private ResourceDao resourceDao;
    private IUserService userService=null;
    private IRoleService roleService=null;
    private IResourceService resourceService=null;

    User user=null;
    Role role=null;
    Resource resource=null;
    List<User> userList=null;
    List<Role> roleList=null;
    List<Resource> resourceList=null;
    List<String> stringList=null;
    List<Integer> ids=null;
    int id=0;
    String username=null;
    int affactedNum=0;

    MyHandler() throws SQLException {
        this.moduleService = (ModuleServiceImpl) ContextLoader.getBean("moduleService");
        this.swipeRecordService = (SwipeRecordService) ContextLoader.getBean("swipeRecordService");
        this.userService= (IUserService) ContextLoader.getBean("userService");
        this.roleService= (IRoleService) ContextLoader.getBean("roleService");
        this.resourceService= (IResourceService) ContextLoader.getBean("resourceService");
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

//        logger.info(Thread.currentThread().getName());
//        logger.info("#THREAD   ~ "+String.valueOf(Thread.activeCount()));
//        logger.info("#OBJ      ~ moduleService:"+moduleService);

        StringBuffer readStr=null;
        readStr=new StringBuffer();//要放到handle里面
        String reqData="";

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
        reqData=new String(readStr);
        String method=exchange.getRequestMethod();
        URI uri=exchange.getRequestURI();
        logger.info("#DATA     ~ request-data:"+reqData);

        ObjectMapper objectMapper=new ObjectMapper();
        Map map=new HashMap();
        JsonNode node=objectMapper.readTree(reqData);
        int sign=node.get("sign").asInt();
        logger.info("sign:"+String.valueOf(sign));


        Iterator it=null;
        int countNum=0;
        int affectedNum=0;
        List<DeviceStatus> tempDeviceStatusList=null;
        List<DeviceStatus> deviceStatusList=null;
        DeviceStatus deviceStatus=null;

        List<SwipeRecord> tempSwipeRecordList=null;
        List<SwipeRecord> swipeRecordList=null;
        SwipeRecord swipeRecord=null;
        SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf2=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar calendar=Calendar.getInstance();
        Date date=null;
        String startTime="";
        String endTime="";
        int status=0;
        String deviceid="";
        int result=0;
        HashMap paramMap;

        switch (sign) {
            case 0://moduleStatus/listByStatus
                status=node.get("status").asInt();
                deviceStatusList=new ArrayList();
//                map=new HashMap();
                tempDeviceStatusList=moduleService.listByStatus(status);
                it=tempDeviceStatusList.iterator();
                map.put("result",0);//0--获取数据成功
                while(it.hasNext()){
                    deviceStatus= (DeviceStatus) it.next();
                    deviceStatusList.add(deviceStatus);
                }
                map.put("data",deviceStatusList);
                logger.info("sign:"+sign+"  #DATA:"+String.valueOf(map));
                responseBody.write(objectMapper.writeValueAsBytes(map));
//                map=null;
                break;

            case 1://moduleStatus/listAllWithoutDuplicate
                deviceStatusList=new ArrayList();
//                map=new HashMap();
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
//                map=null;
                break;

            case 2://moduleStatus/listAll
                deviceStatusList=new ArrayList();
//                map=new HashMap();
                tempDeviceStatusList=moduleService.listAll();
                it=tempDeviceStatusList.iterator();
                map.put("result",0);//0--获取数据成功
                while(it.hasNext()){
                    deviceStatus= (DeviceStatus) it.next();
                    deviceStatusList.add(deviceStatus);
                }
                map.put("data",deviceStatusList);
                logger.info("sign:"+sign+"  #DATA:"+String.valueOf(map));
                responseBody.write(objectMapper.writeValueAsBytes(map));
//                map=null;
                break;

            case 3://moduleStatus/listAllWithStrategy
                deviceStatusList=new ArrayList<>();
                paramMap=objectMapper.readValue(reqData,HashMap.class);
                tempDeviceStatusList=moduleService.listAllWithStrategy(paramMap);
                it=tempDeviceStatusList.iterator();
                map.put("result",0);
                while (it.hasNext()){
                    deviceStatus= (DeviceStatus) it.next();
                    deviceStatusList.add(deviceStatus);
                }
                map.put("data",deviceStatusList);
//                logger.info("#DATA     ~ response-data:"+String.valueOf(map));
                responseBody.write(objectMapper.writeValueAsBytes(map));
//                map=null;
                break;

            case 4://moduleStatus/listByTimezone
                startTime=node.get("startTime").asText();
                endTime=node.get("endTime").asText();
                deviceStatusList=new ArrayList<>();
//                map=new HashMap();
                logger.info("tag - startTime.date="+startTime+"; endTime.date"+endTime);
                tempDeviceStatusList=moduleService.listByTimezone(startTime.toString(),endTime.toString());
                it=tempDeviceStatusList.iterator();
                map.put("result",0);
                while (it.hasNext()){
                    deviceStatus= (DeviceStatus) it.next();
                    deviceStatusList.add(deviceStatus);
                }
                map.put("data",deviceStatusList);
                logger.info("#DATA     ~ response-data:"+String.valueOf(map));
                responseBody.write(objectMapper.writeValueAsBytes(map));
//                map=null;
                break;

            case 5://moduleStatus/listByParam
                status=node.get("status").asInt();
                endTime=node.get("endTime").asText();
                deviceid=node.get("deviceid").asText();
                deviceStatusList=new ArrayList();
//                map=new HashMap();
                tempDeviceStatusList=moduleService.listByParam(endTime,status,deviceid);
                it=tempDeviceStatusList.iterator();
                map.put("result",0);//0--获取数据成功
                while(it.hasNext()){
                    deviceStatus= (DeviceStatus) it.next();
                    deviceStatusList.add(deviceStatus);
                }
                map.put("data",deviceStatusList);
                logger.info("sign:"+sign+"  #DATA:"+String.valueOf(map));
                responseBody.write(objectMapper.writeValueAsBytes(map));
//                map=null;
                break;

            case 6://moduleStatus/countByParam
                status=node.get("status").asInt();
                endTime=node.get("endTime").asText();
                try {
                    date=sdf1.parse(endTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                endTime=sdf2.format(date);
                logger.info("endTime:"+endTime);
                deviceid=node.get("deviceid").asText();
//                map=new HashMap();
                countNum=moduleService.countByParam(endTime,status,deviceid);
                map.put("result",0);//0--获取数据成功
                map.put("data",countNum);
                logger.info("sign:"+sign+"  #DATA:"+String.valueOf(countNum));
                responseBody.write(objectMapper.writeValueAsBytes(map));
//                map=null;
                break;

            case 7://moduleStatus/deleteByParam
                status=node.get("status").asInt();
                endTime=node.get("endTime").asText();
                try {
                    date=sdf1.parse(endTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                endTime=sdf2.format(date);
                deviceid=node.get("deviceid").asText();
//                map=new HashMap();
                affectedNum=moduleService.deleteByParam(endTime,status,deviceid);
                map.put("result",0);//0--获取数据成功
                map.put("data",affectedNum);
                logger.info("sign:"+sign+"  #DATA:"+String.valueOf(affectedNum));
                responseBody.write(objectMapper.writeValueAsBytes(map));
//                map=null;
                break;

            case 20://swipeRecord/listAll
                swipeRecordList=new ArrayList<>();
//                map=new HashMap();
                tempSwipeRecordList=swipeRecordService.listAll();
                it=tempSwipeRecordList.iterator();
                map.put("result",0);
                while (it.hasNext()){
                    swipeRecord= (SwipeRecord) it.next();
                    swipeRecordList.add(swipeRecord);
                }
                map.put("data",swipeRecordList);
//                logger.info("#DATA     ~ response-data:"+String.valueOf(map));
                responseBody.write(objectMapper.writeValueAsBytes(map));
//                map=null;
                break;

            case 21://swipeRecord/listByTimezoneWhenFail
                swipeRecordList=new ArrayList<>();
//                map=new HashMap();
                startTime=node.get("startTime").asText();
                endTime=node.get("endTime").asText();
                tempSwipeRecordList=swipeRecordService.listByTimezoneWhenFail(startTime.toString(),endTime.toString());
                it=tempSwipeRecordList.iterator();
                map.put("result",0);
                while (it.hasNext()){
                    swipeRecord= (SwipeRecord) it.next();
                    swipeRecordList.add(swipeRecord);
                }
                map.put("data",swipeRecordList);
//                logger.info("#DATA     ~ response-data:"+String.valueOf(map));
                responseBody.write(objectMapper.writeValueAsBytes(map));
//                map=null;
                break;

            case 22://swipeRecord/listAllWithStrategy
                swipeRecordList=new ArrayList<>();
//                map=new HashMap();
//                String orderColumn=node.get("orderColumn").asText();
//                String orderDir=node.get("orderDir").asText();
//                SwipeRecordStrategy strategy=objectMapper.readValue(node.get("strategy"),SwipeRecordStrategy.class);
//                tempSwipeRecordList=swipeRecordService.listAllWithStrategy(orderColumn,orderDir,strategy);
                paramMap=objectMapper.readValue(reqData,HashMap.class);
                tempSwipeRecordList=swipeRecordService.listAllWithStrategy(paramMap);
                it=tempSwipeRecordList.iterator();
                map.put("result",0);
                while (it.hasNext()){
                    swipeRecord= (SwipeRecord) it.next();
                    swipeRecordList.add(swipeRecord);
                }
                map.put("data",swipeRecordList);
//                logger.info("#DATA     ~ response-data:"+String.valueOf(map));
                responseBody.write(objectMapper.writeValueAsBytes(map));
//                map=null;
                break;

            case 23://swipeRecord/listByTimezone
                swipeRecordList=new ArrayList<>();
//                map=new HashMap();
                startTime=node.get("startTime").asText();
                endTime=node.get("endTime").asText();
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
//                logger.info("#DATA     ~ response-data:"+String.valueOf(map));
                responseBody.write(objectMapper.writeValueAsBytes(map));
//                map=null;
                break;

            case 24://swipeRecord/countByParam
                result=node.get("result").asInt();
                endTime=node.get("endTime").asText();
                try {
                    date=sdf1.parse(endTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                endTime=sdf2.format(date);
                logger.info("endTime:"+endTime);
                deviceid=node.get("deviceid").asText();
//                map=new HashMap();
                countNum=swipeRecordService.countByParam(endTime,result,deviceid);
                map.put("result",0);//0--获取数据成功
                map.put("data",countNum);
                logger.info("sign:"+sign+"  #DATA:"+String.valueOf(countNum));
                responseBody.write(objectMapper.writeValueAsBytes(map));
//                map=null;
                break;

            case 25://swipeRecord/deleteByParam
                result=node.get("result").asInt();
                endTime=node.get("endTime").asText();
                try {
                    date=sdf1.parse(endTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                endTime=sdf2.format(date);
                deviceid=node.get("deviceid").asText();
//                map=new HashMap();
                affectedNum=swipeRecordService.deleteByParam(endTime,result,deviceid);
                map.put("result",0);//0--获取数据成功
                map.put("data",affectedNum);
                logger.info("sign:"+sign+"  #DATA:"+String.valueOf(affectedNum));
                responseBody.write(objectMapper.writeValueAsBytes(map));
//                map=null;
                break;

            case 12://swipeRecord/listByDate
                swipeRecordList=new ArrayList<>();
//                map=new HashMap();
                String param_date=node.get("date").asText();
                Date theDay=null;
                try {
                    theDay=sdf1.parse(param_date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Date date1=null;
                Date date2=null;
                date1=new Date(theDay.getTime()/86400000*86400000);
                date2=new Date((theDay.getTime()/86400000+1)*86400000);
                logger.info("date1:"+date1);
                logger.info("date2:"+date2);
                tempSwipeRecordList=swipeRecordService.listByTimezone(date1.toString(),date2.toString());
                it=tempSwipeRecordList.iterator();
                map.put("result",0);
                while (it.hasNext()){
                    swipeRecord= (SwipeRecord) it.next();
                    swipeRecordList.add(swipeRecord);
                }
                map.put("data",swipeRecordList);
                logger.info("#DATA     ~ response-data:"+String.valueOf(map));
                responseBody.write(objectMapper.writeValueAsBytes(map));
//                map=null;
                break;

                //连接shiro/service/impl与shiro/dao
            case 50://shiro/userDao/add
                user=objectMapper.treeToValue(node.path("user"),User.class);
                affactedNum=userService.add(user);
                map.put("result",0);
                map.put("data",affectedNum);
                responseBody.write(objectMapper.writeValueAsBytes(map));
                break;

            case 51://shiro/userDao/update
                user=objectMapper.treeToValue(node.path("user"),User.class);
                affactedNum=userService.update(user);
                map.put("result",0);
                map.put("data",affectedNum);
                responseBody.write(objectMapper.writeValueAsBytes(map));
                break;

            case 52://shiro/userDao/delete
                id=node.path("userId").asInt();
                affactedNum=userService.delete(id);
                map.put("result",0);
                map.put("data",affectedNum);
                responseBody.write(objectMapper.writeValueAsBytes(map));
                break;

            case 53://shiro/userDao/batchDelete
                ids=objectMapper.readValue(node.path("userIds").traverse(), new TypeReference<List<Integer>>(){});
                affactedNum=userService.batchDelete(ids);
                map.put("result",0);
                map.put("data",affectedNum);
                responseBody.write(objectMapper.writeValueAsBytes(map));
                break;

            case 54://shiro/userDao/load
                id=node.path("userId").asInt();
                user=userService.load(id);
                map.put("result",0);
                map.put("data",user);
                responseBody.write(objectMapper.writeValueAsBytes(map));
                break;

            case 55://shiro/userDao/loadByUserName
                username=node.path("username").asText();
                user=userService.loadByUserName(username);
                map.put("result",0);
                map.put("data",user);
                responseBody.write(objectMapper.writeValueAsBytes(map));
                break;

            case 56://shiro/userDao/listUser
                userList=userService.listUser();
                map.put("result",0);
                map.put("data",userList);
                responseBody.write(objectMapper.writeValueAsBytes(map));
                break;

            case 57://shiro/userDao/listByRole
                id=node.path("roleId").asInt();
                userList=userService.listByRole(id);
                map.put("result",0);
                map.put("data",userList);
                responseBody.write(objectMapper.writeValueAsBytes(map));
                break;

            case 58://shiro/userDao/listUserRole
                id=node.path("userId").asInt();
                roleList=userService.listUserRole(id);
                map.put("result",0);
                map.put("data",roleList);
                responseBody.write(objectMapper.writeValueAsBytes(map));
                break;

            case 59://shiro/userDao/listAllResources
                id=node.path("userId").asInt();
                resourceList=userService.listAllResources(id);
                map.put("result",0);
                map.put("data",resourceList);
                responseBody.write(objectMapper.writeValueAsBytes(map));
                break;

            case 60://shiro/userDao/listRoleSnByUser
                id=node.path("userId").asInt();
                stringList=userService.listRoleSnByUser(id);
                map.put("result",0);
                map.put("data",stringList);
                responseBody.write(objectMapper.writeValueAsBytes(map));
                break;
//shiro/dao/userDao
//            Integer add(User user);
//            Integer update(User user);
//            Integer delete(Integer id);
//            Integer batchDelete(@Param("ids") List<Integer> ids);
//            User load(Integer id);
//            User loadByUserName(String username);
//            List<User> listUser();
//            List<User> listByRole(Integer rid);
//            List<Role> listUserRole(Integer uid);
//            List<Resource> listAllResources(Integer uid);
//            List<String> listRoleSnByUser(Integer uid);
            default:break;
            }
            bufferedReader.close();
            inputStreamReader.close();
            requestBody.close();
            responseBody.close();
            requestBody.close();
    }

}