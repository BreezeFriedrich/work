import com.sun.deploy.net.HttpRequest;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import model.DeviceStatus;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import service.ModuleService;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/5/17.
 */
public class HttpConServer {
    public static void main(String[] args) throws IOException {

        InetSocketAddress address=new InetSocketAddress(2017);
        HttpServer httpServer=HttpServer.create(address,100);
        httpServer.createContext("/", new HandlerTestA());
        //显示已经处理的请求数，采用线程池
        httpServer.createContext("/test",new HandlerTestB());
        httpServer.setExecutor(null);
        httpServer.start();
    }

    class MyHandler implements HttpHandler {

        @Autowired
        private ModuleService moduleService;
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            System.out.println("clientURI:"+httpExchange.getRequestURI());
            InputStreamReader is= new InputStreamReader(httpExchange.getRequestBody());
            OutputStream os = httpExchange.getResponseBody();
            String responseString="success";
            os.write(responseString.getBytes());
            os.close();
            BufferedReader bufferedReader=new BufferedReader(is);
            String line="";
            String result="";
            while((line=bufferedReader.readLine())!=null){
                result+=line+"\n";
            }
            String method=httpExchange.getRequestMethod();
            URI uri=httpExchange.getRequestURI();
            System.out.println("result结果:"+result);

            ObjectMapper objectMapper=new ObjectMapper();
            Map map=new HashMap();

            JsonNode node=objectMapper.readTree(result);
            int sign=node.get("sign").asInt();
            System.out.println("sign:"+String.valueOf(sign));

            switch (sign) {
                case 1:// listByStatus
                    int status=node.get("status").asInt();
                    List<DeviceStatus> list=moduleService.listByStatus(0);
                    Iterator it=list.iterator();
                    DeviceStatus deviceStatus=null;
                    while(it.hasNext()){
                        map.put("result",0);//0--获取数据成功
                        deviceStatus= (DeviceStatus) it.next();
                        map.put("data",deviceStatus);
                    }
                    os.write(objectMapper.writeValueAsBytes(map));
                    break;

                default:break;
            }
            os.close();
            os.close();

        }
    }
}