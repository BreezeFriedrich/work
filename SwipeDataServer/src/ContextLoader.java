import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLException;

/**
 * Created by admin on 2017/5/16.
 */
public class ContextLoader {

    private static ContextLoader contextLoader=null;
    private ContextLoader() {};
    public static synchronized ContextLoader getInstance() {
        if (null == contextLoader) {
            contextLoader = new ContextLoader();
        }
        return contextLoader;
    }

    private static ApplicationContext context = null;
    static {
        context=new ClassPathXmlApplicationContext("spring-mybatis.xml");
    }

    public Object getBean(String beanName) throws SQLException {
        return context.getBean(beanName);
    }
}
