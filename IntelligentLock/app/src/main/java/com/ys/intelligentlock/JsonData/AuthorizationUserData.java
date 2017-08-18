package com.ys.intelligentlock.JsonData;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/1/12.
 */

public class AuthorizationUserData {
    public int result;
    public List<UserInfo>  userList;

    public class UserInfo implements Serializable{
        public String name;
        public String cardNumb;
        public String dnCode;
        public String startTime;
        public String endTime;
        public String serviceNumb;
    }
}
