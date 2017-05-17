import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * Created by admin on 2017/5/17.
 */
public class HttpServerTest {

    public static void testmain(String[] args) {
        try {
            //允许最大连接数
            int backLog = 10;
            InetSocketAddress inetSock = new InetSocketAddress(8086);
            HttpServer httpServer = HttpServer.create(inetSock, backLog);
            //直接返回Hello.....
            httpServer.createContext("/", new HandlerTestA());
            //显示已经处理的请求数，采用线程池
            httpServer.createContext("/test",new HandlerTestB());
            httpServer.setExecutor(null);
            httpServer.start();
            System.out.println("HttpServer Test Start!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

//直接处理请求
class HandlerTestA implements HttpHandler {

    public void handle(HttpExchange httpExchange) throws IOException {
        // TODO Auto-generated method stub
        //针对请求的处理部分
        //返回请求响应时，遵循HTTP协议
        String responseString = "<font color='#ff0000'>Hello! This a HttpServer!</font>";
        //设置响应头
        httpExchange.sendResponseHeaders(200, responseString.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(responseString.getBytes());
        os.close();
    }

}
//线程池还不会用，简略的使用了下，意思有点差距，后面在分析
class HandlerTestB implements HttpHandler{
    private static int requestNum = 0;
    ThreadPoolExecutor threadPoolExecutor;
    HandlerTestB(){
        //两个常在线程，最大3个
        threadPoolExecutor = new  ThreadPoolExecutor(2,3, 30,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(2),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }
    public void handle(HttpExchange he) throws IOException {
        // TODO Auto-generated method stub
        if((getQueueSize(threadPoolExecutor.getQueue()))<2){
            RequestTasks rqt = new RequestTasks(he);
            threadPoolExecutor.execute(rqt);
        }
        else System.out.println("Please Wait!");
    }
    private synchronized int getQueueSize(Queue queue)
    {
        return queue.size();
    }

}

//处理请求的任务
class RequestTasks implements Runnable{

    static int processedNum = 0;
    HttpExchange httpExchange;
    RequestTasks(HttpExchange he){
        httpExchange = he;
        processedNum++;
    }
    public void run() {
        // TODO Auto-generated method stub
        System.out.println("ProcessedNum:" +processedNum);
        String responseString = "ProcessedNum:" + processedNum + "\n";
        try{
            httpExchange.sendResponseHeaders(200, responseString.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(responseString.getBytes());
            os.close();
            //去掉注释，看看只能响应两个，有些问题
            //while(true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}