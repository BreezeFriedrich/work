import org.codehaus.jackson.map.ObjectMapper;

/**
 * Created by admin on 2017/5/17.
 */
public class Client {

    public static void main(String args[]){
        ObjectMapper mapper=new ObjectMapper();
        String senddata=" {\"sign\":\""+1+"\",\"status\":\""+0+"\"}";
        String data=HttpTool.postData(senddata);
        System.out.println(data);
    }
}
