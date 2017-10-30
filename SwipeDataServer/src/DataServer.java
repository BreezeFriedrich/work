import com.alibaba.fastjson.JSON;
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
import shiro.model.*;
import shiro.service.IResourceService;
import shiro.service.IRoleService;
import shiro.service.IUserService;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;

/**
 * Created by admin on 2017/5/15.
 */
public class DataServer {
    /*读取jar包之外的配置文件
    static{
//        String jarPath=System.getProperty("java.class.path");
//        String propertiesPath = jarPath.substring(0,jarPath.indexOf("/SwipeDataServer/")+17)+ "conf/default.properties";

        String propertiesPath="SwipeDataServer/default.properties";
//        System.err.println("'default.properties' AbsolutePath: "+new File(propertiesPath).getAbsolutePath());
        InputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(propertiesPath));
//            in = this.getClass().getClassLoader().getResourceAsStream("resource.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Properties pro=new Properties();
        try {
            pro.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        port= Integer.parseInt(pro.getProperty("httpServer.port"));
    }
    */

    static {
        Properties prop = new Properties();
        try {
            String configFile = "default.properties";
            //以URL形式获取工程的资源文件 classpath 路径, 得到以file:/为开头的URL
            //例如返回: file:/D:/workspace/myproject01/WEB-INF/classes/
            URL classPath = Thread.currentThread().getContextClassLoader().getResource("");
            String proFilePath = classPath.toString();

            //移除开通的file:/六个字符
            proFilePath = proFilePath.substring(6);
            File file=new File(proFilePath+configFile);
            System.err.println("'default.properties' AbsolutePath: "+file.getAbsolutePath());

            //以文件流形式读取指定路径的配置文件 config.properties
            FileInputStream ins = new FileInputStream(file);

            //以properties对象形式读取文件流
            prop.load(ins);

            //...其它操作...

        } catch (Exception e) {
            e.printStackTrace();
        }

        port= Integer.parseInt(prop.getProperty("httpServer.port"));
        prop = null;
    }

/*
    static {

        Properties pro = new Properties();
        try (FileInputStream in = new FileInputStream("default.properties")){
            pro.load(in);
            in.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }catch (IOException e2) {
            e2.printStackTrace();
        }
        port= Integer.parseInt(pro.getProperty("httpServer.port"));

        String propertiesPath="default.properties";
        URL url = Main.class.getClassLoader().getResource(propertiesPath);
        InputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(url.getFile()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Properties pro=new Properties();
        try {
            pro.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        port= Integer.parseInt(pro.getProperty("httpServer.port"));
    }
*/

    private static  final Logger logger = LoggerFactory.getLogger(DataServer.class);

    private static final int port;

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
        Iterator it = list.iterator();
        DeviceStatus deviceStatus = null;
        while (it.hasNext()) {
            deviceStatus = (DeviceStatus) it.next();
        }
*/
        try {
            InetSocketAddress address = new InetSocketAddress(port);
            HttpServer httpServer = HttpServer.create(address, 40);//线程数量
            httpServer.createContext("/", new MyHandler());
            httpServer.setExecutor(Executors.newCachedThreadPool());
            httpServer.start();
            logger.info("HttpConnectionServer is listening on port " +httpServer.getAddress()+" , "+address.getPort());
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
    UserRole userRole=null;
    RoleResource roleResource=null;
    List<User> userList=null;
    List<Role> roleList=null;
    List<Resource> resourceList=null;
    List<String> stringList=null;
    List<Integer> ids=null;
    int id=0;
    int userId=0;
    int roleId=0;
    int resourceId=0;
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
//        logger.info("#DATA     ~ request-data:"+reqData);

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
        long timeTag1=new Date().getTime();

        switch (sign) {
            case 0://moduleStatus/listByStatus
                status=node.get("status").asInt();
                deviceStatusList=new ArrayList();
                tempDeviceStatusList=moduleService.listByStatus(status);
                it=tempDeviceStatusList.iterator();
                map.put("result",0);//0--获取数据成功
                while(it.hasNext()){
                    deviceStatus= (DeviceStatus) it.next();
                    deviceStatusList.add(deviceStatus);
                }
                map.put("data",deviceStatusList);
                responseBody.write(objectMapper.writeValueAsBytes(map));
                break;

            case 1://moduleStatus/listAllWithoutDuplicate
                deviceStatusList=new ArrayList();
                tempDeviceStatusList=moduleService.listAllWithoutDuplicate();
                it=tempDeviceStatusList.iterator();
//                map=new HashMap();
                map.put("result",0);//0--获取数据成功
                while(it.hasNext()){
                    deviceStatus= (DeviceStatus) it.next();
                    deviceStatusList.add(deviceStatus);
                }
                map.put("data",deviceStatusList);
                responseBody.write(objectMapper.writeValueAsBytes(map));
//                map=null;
                break;

            case 2://moduleStatus/listAll
                deviceStatusList=new ArrayList();
                tempDeviceStatusList=moduleService.listAll();
                it=tempDeviceStatusList.iterator();
                map.put("result",0);//0--获取数据成功
                while(it.hasNext()){
                    deviceStatus= (DeviceStatus) it.next();
                    deviceStatusList.add(deviceStatus);
                }
                map.put("data",deviceStatusList);
                responseBody.write(objectMapper.writeValueAsBytes(map));
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
                responseBody.write(objectMapper.writeValueAsBytes(map));
                break;

            case 4://moduleStatus/listByTimezone
                startTime=node.get("startTime").asText();
                endTime=node.get("endTime").asText();
                deviceStatusList=new ArrayList<>();
//                logger.info("tag - startTime.date="+startTime+"; endTime.date"+endTime);
                tempDeviceStatusList=moduleService.listByTimezone(startTime.toString(),endTime.toString());
                it=tempDeviceStatusList.iterator();
                map.put("result",0);
                while (it.hasNext()){
                    deviceStatus= (DeviceStatus) it.next();
                    deviceStatusList.add(deviceStatus);
                }
                map.put("data",deviceStatusList);
                responseBody.write(objectMapper.writeValueAsBytes(map));
                break;

            case 5://moduleStatus/listByParam
                status=node.get("status").asInt();
                endTime=node.get("endTime").asText();
                deviceid=node.get("deviceid").asText();
                deviceStatusList=new ArrayList();
                tempDeviceStatusList=moduleService.listByParam(endTime,status,deviceid);
                it=tempDeviceStatusList.iterator();
                map.put("result",0);//0--获取数据成功
                while(it.hasNext()){
                    deviceStatus= (DeviceStatus) it.next();
                    deviceStatusList.add(deviceStatus);
                }
                map.put("data",deviceStatusList);
                responseBody.write(objectMapper.writeValueAsBytes(map));
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
//                logger.info("endTime:"+endTime);
                deviceid=node.get("deviceid").asText();
                countNum=moduleService.countByParam(endTime,status,deviceid);
                map.put("result",0);//0--获取数据成功
                map.put("data",countNum);
                responseBody.write(objectMapper.writeValueAsBytes(map));
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
                affectedNum=moduleService.deleteByParam(endTime,status,deviceid);
                map.put("result",0);//0--获取数据成功
                map.put("data",affectedNum);
                responseBody.write(objectMapper.writeValueAsBytes(map));
                break;

            case 20://swipeRecord/listAll
                swipeRecordList=new ArrayList<>();
                tempSwipeRecordList=swipeRecordService.listAll();
                it=tempSwipeRecordList.iterator();
                map.put("result",0);
                while (it.hasNext()){
                    swipeRecord= (SwipeRecord) it.next();
                    swipeRecordList.add(swipeRecord);
                }
                map.put("data",swipeRecordList);
                responseBody.write(objectMapper.writeValueAsBytes(map));
                break;

            case 21://swipeRecord/listByTimezoneWhenFail
                swipeRecordList=new ArrayList<>();
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
                responseBody.write(objectMapper.writeValueAsBytes(map));
                break;

            case 22://swipeRecord/listAllWithStrategy
                swipeRecordList=new ArrayList<>();
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
                responseBody.write(JSON.toJSONBytes(map));
//                responseBody.write(objectMapper.writeValueAsBytes(map));
                break;

            case 23://swipeRecord/listByTimezone
                swipeRecordList=new ArrayList<>();
                startTime=node.get("startTime").asText();
                endTime=node.get("endTime").asText();
                tempSwipeRecordList=swipeRecordService.listByTimezone(startTime.toString(),endTime.toString());
                it=tempSwipeRecordList.iterator();
                map.put("result",0);
                while (it.hasNext()){
                    swipeRecord= (SwipeRecord) it.next();
                    swipeRecordList.add(swipeRecord);
                }
                map.put("data",swipeRecordList);
                responseBody.write(JSON.toJSONBytes(map));
//                responseBody.write(objectMapper.writeValueAsBytes(map));
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
//                logger.info("endTime:"+endTime);
                deviceid=node.get("deviceid").asText();
                countNum=swipeRecordService.countByParam(endTime,result,deviceid);
                map.put("result",0);//0--获取数据成功
                map.put("data",countNum);
                responseBody.write(objectMapper.writeValueAsBytes(map));
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
                affectedNum=swipeRecordService.deleteByParam(endTime,result,deviceid);
                map.put("result",0);//0--获取数据成功
                map.put("data",affectedNum);
                responseBody.write(objectMapper.writeValueAsBytes(map));
                break;

            case 12://swipeRecord/listByDate
                swipeRecordList=new ArrayList<>();
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
                tempSwipeRecordList=swipeRecordService.listByTimezone(date1.toString(),date2.toString());
                it=tempSwipeRecordList.iterator();
                map.put("result",0);
                while (it.hasNext()){
                    swipeRecord= (SwipeRecord) it.next();
                    swipeRecordList.add(swipeRecord);
                }
                map.put("data",swipeRecordList);
                responseBody.write(objectMapper.writeValueAsBytes(map));
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
                userId=node.path("userId").asInt();
                affactedNum=userService.delete(userId);
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
                userId=node.path("userId").asInt();
                user=userService.load(userId);
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
                roleId=node.path("roleId").asInt();
                userList=userService.listByRole(roleId);
                map.put("result",0);
                map.put("data",userList);
                responseBody.write(objectMapper.writeValueAsBytes(map));
                break;

            case 58://shiro/userDao/listUserRole
                userId=node.path("userId").asInt();
                roleList=userService.listUserRole(userId);
                map.put("result",0);
                map.put("data",roleList);
                responseBody.write(objectMapper.writeValueAsBytes(map));
                break;

            case 59://shiro/userDao/listAllResources
                userId=node.path("userId").asInt();
                resourceList=userService.listAllResources(userId);
                map.put("result",0);
                map.put("data",resourceList);
                responseBody.write(objectMapper.writeValueAsBytes(map));
                break;

            case 60://shiro/userDao/listRoleSnByUser
                userId=node.path("userId").asInt();
                stringList=userService.listRoleSnByUser(userId);
                map.put("result",0);
                map.put("data",stringList);
                responseBody.write(objectMapper.writeValueAsBytes(map));
                break;

            case 70://shiro/RoleDao/add
                role=objectMapper.treeToValue(node.path("role"),Role.class);
                affactedNum=roleService.add(role);
                map.put("result",0);
                map.put("data",affectedNum);
                responseBody.write(objectMapper.writeValueAsBytes(map));
                break;

            case 71://shiro/RoleDao/delete
                roleId=node.path("id").asInt();
                affactedNum=roleService.delete(roleId);
                map.put("result",0);
                map.put("data",affectedNum);
                responseBody.write(objectMapper.writeValueAsBytes(map));
                break;

            case 72://shiro/RoleDao/batchDelete
                ids=objectMapper.readValue(node.path("ids").traverse(), new TypeReference<List<Integer>>(){});
                affactedNum=roleService.batchDelete(ids);
                map.put("result",0);
                map.put("data",affectedNum);
                responseBody.write(objectMapper.writeValueAsBytes(map));
                break;

            case 73://shiro/RoleDao/update
                role=objectMapper.treeToValue(node.path("role"),Role.class);
                affactedNum=roleService.add(role);
                map.put("result",0);
                map.put("data",affectedNum);
                responseBody.write(objectMapper.writeValueAsBytes(map));
                break;

            case 74://shiro/RoleDao/listRole
                roleList=roleService.listRole();
                map.put("result",0);
                map.put("data",roleList);
                responseBody.write(objectMapper.writeValueAsBytes(map));
                break;

            case 75://shiro/RoleDao/load
                roleId=node.path("id").asInt();
                role=roleService.load(roleId);
                map.put("result",0);
                map.put("data",role);
                responseBody.write(objectMapper.writeValueAsBytes(map));
                break;

            case 76://shiro/RoleDao/loadUserRole
                userId=node.path("userId").asInt();
                roleId=node.path("roleId").asInt();
                userRole=roleService.loadUserRole(userId,roleId);
                map.put("result",0);
                map.put("data",userRole);
                responseBody.write(objectMapper.writeValueAsBytes(map));
                break;

            case 77://shiro/RoleDao/addUserRole
                userId=node.path("userId").asInt();
                roleId=node.path("roleId").asInt();
                affactedNum=roleService.addUserRole(userId,roleId);
                map.put("result",0);
                map.put("data",affectedNum);
                responseBody.write(objectMapper.writeValueAsBytes(map));
                break;

            case 78://shiro/RoleDao/addUserRoles
                userId=node.path("userId").asInt();
                ids=objectMapper.readValue(node.path("roleIds").traverse(), new TypeReference<List<Integer>>(){});
                affactedNum=roleService.addUserRoles(userId,ids);
                map.put("result",0);
                map.put("data",affectedNum);
                responseBody.write(objectMapper.writeValueAsBytes(map));
                break;

            case 79://shiro/RoleDao/deleteUserRole
                userId=node.path("userId").asInt();
                roleId=node.path("roleId").asInt();
                affactedNum=roleService.deleteUserRole(userId,roleId);
                map.put("result",0);
                map.put("data",affectedNum);
                responseBody.write(objectMapper.writeValueAsBytes(map));
                break;

            case 80://shiro/RoleDao/deleteUserRoles
                userId=node.path("userId").asInt();
                affactedNum=roleService.deleteUserRoles(userId);
                map.put("result",0);
                map.put("data",affectedNum);
                responseBody.write(objectMapper.writeValueAsBytes(map));
                break;

            case 81://shiro/RoleDao/batchDeleteRoleResource
                ids=objectMapper.readValue(node.path("roleIds").traverse(), new TypeReference<List<Integer>>(){});
                affactedNum=roleService.batchDeleteRoleResource(ids);
                map.put("result",0);
                map.put("data",affectedNum);
                responseBody.write(objectMapper.writeValueAsBytes(map));
                break;

            case 82://shiro/RoleDao/listRoleResource
                id=node.path("roleId").asInt();
                resourceList=roleService.listRoleResource(id);
                map.put("result",0);
                map.put("data",resourceList);
                responseBody.write(objectMapper.writeValueAsBytes(map));
                break;

            case 83://shiro/RoleDao/addRoleResource
                roleId=node.path("roleId").asInt();
                resourceId=node.path("resourceId").asInt();
                affactedNum=roleService.addRoleResource(roleId,resourceId);
                map.put("result",0);
                map.put("data",affectedNum);
                responseBody.write(objectMapper.writeValueAsBytes(map));
                break;

            case 84://shiro/RoleDao/deleteRoleResource
                roleId=node.path("roleId").asInt();
                resourceId=node.path("resourceId").asInt();
                affactedNum=roleService.deleteRoleResource(roleId,resourceId);
                map.put("result",0);
                map.put("data",affectedNum);
                responseBody.write(objectMapper.writeValueAsBytes(map));
                break;

            case 85://shiro/RoleDao/loadResourceRole
                roleId=node.path("roleId").asInt();
                resourceId=node.path("resourceId").asInt();
                roleResource=roleService.loadResourceRole(roleId,resourceId);
                map.put("result",0);
                map.put("data",roleResource);
                responseBody.write(objectMapper.writeValueAsBytes(map));
                break;

            case 86://shiro/RoleDao/deleteRoleAndUser
                ids=objectMapper.readValue(node.path("ids").traverse(), new TypeReference<List<Integer>>(){});
                affactedNum=roleService.deleteRoleAndUser(ids);
                map.put("result",0);
                map.put("data",affectedNum);
                responseBody.write(objectMapper.writeValueAsBytes(map));
                break;

            case 90://shiro/ResourceDao/add
                resource=objectMapper.treeToValue(node.path("resource"),Resource.class);
                affactedNum=resourceService.add(resource);
                map.put("result",0);
                map.put("data",affectedNum);
                responseBody.write(objectMapper.writeValueAsBytes(map));
                break;

            case 91://shiro/ResourceDao/delete
                resourceId=node.path("id").asInt();
                affactedNum=resourceService.delete(resourceId);
                map.put("result",0);
                map.put("data",affectedNum);
                responseBody.write(objectMapper.writeValueAsBytes(map));
                break;

            case 92://shiro/ResourceDao/update
                resource=objectMapper.treeToValue(node.path("resource"),Resource.class);
                affactedNum=resourceService.update(resource);
                map.put("result",0);
                map.put("data",affectedNum);
                responseBody.write(objectMapper.writeValueAsBytes(map));
                break;

            case 93://shiro/ResourceDao/listResource
                resourceList=resourceService.listResource();
                map.put("result",0);
                map.put("data",resourceList);
                responseBody.write(objectMapper.writeValueAsBytes(map));
                break;
            case 94://shiro/ResourceDao/load
                resourceId=node.path("id").asInt();
                resource=resourceService.load(resourceId);
                map.put("result",0);
                map.put("data",resource);
                responseBody.write(objectMapper.writeValueAsBytes(map));
                break;

            default:break;
            }
            bufferedReader.close();
            inputStreamReader.close();
            requestBody.close();
            responseBody.close();
            requestBody.close();
            long timeTag2=new Date().getTime();
//            logger.info("处理该请求用时: "+(timeTag2-timeTag1)+" ms");
    }

}