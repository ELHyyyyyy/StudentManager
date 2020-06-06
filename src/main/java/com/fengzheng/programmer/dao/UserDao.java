package com.fengzheng.programmer.dao;

import com.fengzheng.programmer.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 风筝丶
 * @create 2020/05/29 22:14
 */
@Repository
public interface UserDao {
    User findByUserName(String username);
    int add(User user);
    int delete(String ids);
    int edit(User user);
    List<User> findList(Map<String,Object> queryMap);
    int  getTotal(Map<String,Object> queryMap);
}
