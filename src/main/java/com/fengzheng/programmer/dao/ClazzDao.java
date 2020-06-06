package com.fengzheng.programmer.dao;

import com.fengzheng.programmer.entity.Clazz;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 风筝丶
 * @create 2020/06/01 16:21
 */
@Repository
public interface ClazzDao {
    int add(Clazz clazz);
    int delete(String ids);
    int edit(Clazz clazz);
    List<Clazz> findList(Map<String, Object> queryMap);
    List<Clazz> findAll();
    int  getTotal(Map<String, Object> queryMap);
}
