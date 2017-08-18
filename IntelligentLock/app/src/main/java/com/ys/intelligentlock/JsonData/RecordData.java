package com.ys.intelligentlock.JsonData;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/2/9.
 */

public class RecordData implements Serializable{
    public int result;
    public List<RecordInfo> recordList;

    public class RecordInfo implements Serializable{
        public String gatewayCode;
        public String lockCode;
        public int openMode;
        public String operatememo;
        public String timetag;
        public CardInfo cardInfo;
        public PasswordInfo passwordInfo;

        public class CardInfo implements Serializable{
            public String name;
            public String cardNumb;
            public String dnCode;
            public String serviceNumb;
        }

        public class PasswordInfo implements Serializable{
            public String password;
            public String serviceNumb;
        }
    }
}
