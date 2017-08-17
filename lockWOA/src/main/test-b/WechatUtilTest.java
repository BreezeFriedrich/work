import com.yishu.pojo.AccessToken;
import com.yishu.util.WechatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WechatUtilTest {
    private Logger logger= LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args){
        Logger logger= LoggerFactory.getLogger("WechatUtilTest-main");
        AccessToken accessToken= WechatUtil.getAccessToken();
        logger.info("access_token: {token: "+accessToken.getToken()+",expiresIn: "+accessToken.getExpiresIn()+"}");
    }
}
