package com.fengzheng.programmer.service;

import com.fengzheng.programmer.entity.Clazz;
import com.fengzheng.programmer.entity.Grade;

import java.util.List;
import java.util.Map;

/**
 * @author 风筝丶
 * @create 2020/05/31 16:12
 */
public interface ClazzService {
    int add(Clazz clazz);
    int delete(String ids);
    int edit(Clazz clazz);
    List<Clazz> findList(Map<String, Object> queryMap);
    List<Clazz> findAll();
    int  getTotal(Map<String, Object> queryMap);
}
