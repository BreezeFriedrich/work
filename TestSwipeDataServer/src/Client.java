import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by admin on 2017/5/17.
 */
public class Client {

    private static final Logger logger= LoggerFactory.getLogger(Client.class);

    public static void main(String args[]){
        ObjectMapper mapper=new ObjectMapper();
        String senddata=" {\"sign\":\""+1+"\",\"status\":\""+0+"\"}";
        Date time1=new Date();
        for(int i=0;i<1;i++) {
            String data = HttpTool.postData(senddata);
            logger.info("#DATA     ~ " + data);
        }
        Date time2=new Date();
        int diff= (int) (time2.getTime()-time1.getTime())/1;
        logger.info("DIFF -Time:"+diff);
    }
}
