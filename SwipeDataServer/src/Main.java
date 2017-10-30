import java.io.*;
import java.net.URL;
import java.util.Properties;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-10-30 17:05 admin
 * @since JDK1.7
 */
public class Main {
    /*
    public static void main(String[] args){
        int port=0;

        String propertiesPath="default.properties";
        InputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(propertiesPath));
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
        System.out.println(port);
    }
    */

    public static void main(String[] args){
        int port=0;

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
        System.out.println(port);
    }
}
