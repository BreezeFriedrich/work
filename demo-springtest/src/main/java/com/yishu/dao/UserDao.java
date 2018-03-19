package com.yishu.dao;

import com.yishu.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by admin on 2017/4/26.
 */
//@Repository("userDao")
public interface UserDao {
    Integer add(User user);
    Integer update(User user);
    Integer deleteById(Integer id);
    Integer deleteByUserName(String username);
    Integer batchDelete(@Param("ids") List<Integer> ids);
    User load(Integer id);
    User loadByUserName(String username);
    List<User> listUser();
}
