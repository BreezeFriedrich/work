package com.ys.intelligentlock.JsonData;

import java.util.List;

/**
 * Created by admin on 2017/1/12.
 */

public class PasswordData {
    public int result;

    public List<PasswordInfo> passwordList;

    public class PasswordInfo{
        public String password;
        public String startTime;
        public String endTime;
        public String serviceNumb;
    }
}
