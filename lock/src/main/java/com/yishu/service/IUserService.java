package com.yishu.service;

import com.yishu.pojo.User;

import java.util.Map;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-12-21 17:42 admin
 * @since JDK1.7
 */
public interface IUserService {
    public Map checkLogin(String username, String password);
    public User getUserWithSubordinate(String phoneNumber,int grade);
    public User getUserWithSubordinate(User user);

    public User getSubordinateHierarchy(User user,int minGrade);
    public User getSubordinateHierarchyTillLock(User user);

    public Map addSubordinate(String ownerPhoneNumber,String juniorPhoneNumber,String juniorName,String juniorLocation,int grade);
    public boolean cancleSubordinate(String ownerPhoneNumber,String juniorPhoneNumber,int grade);
}
