package shiro.kit;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * Created by admin on 2017/4/26.
 */
public class ShiroKit {

    public static String md5(String originPassword,String salt){
        return new Md5Hash(originPassword,salt).toString();
    }

    public static void main(String[] args) {
        System.out.println(md5("123456","admin"));
    }
}
