package com.fengzheng.programmer.service.impl;

import com.fengzheng.programmer.dao.UserDao;
import com.fengzheng.programmer.entity.User;
import com.fengzheng.programmer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 风筝丶
 * @create 2020/05/29 22:25
 */
@Service
public class UserserviceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    public User findByUserName(String username) {
        return userDao.findByUserName(username);
    }

    public int add(User user) {
        return userDao.add(user);
    }

    public int delete(String ids) {
        return userDao.delete(ids);
    }


    public int edit(User user) {
        return userDao.edit(user);
    }

    public List<User> findList(Map<String, Object> queryMap) {
        return userDao.findList(queryMap);
    }

    public int getTotal(Map<String, Object> queryMap) {
        return userDao.getTotal(queryMap);
    }
}
