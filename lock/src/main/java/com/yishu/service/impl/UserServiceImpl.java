package com.yishu.service.impl;

import com.yishu.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-12-21 17:43 admin
 * @since JDK1.7
 */
@Service("userService")
public class UserServiceImpl implements IUserService {
    @Override
    public boolean checkLogin(String username, String password) {

        return true;
    }
}
