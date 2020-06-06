package com.fengzheng.programmer.dao;

import com.fengzheng.programmer.entity.Grade;
import com.fengzheng.programmer.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 风筝丶
 * @create 2020/05/29 22:14
 */
@Repository
public interface GradeDao {
    int add(Grade grade);
    int delete(String ids);
    int edit(Grade grade);
    List<Grade> findList(Map<String, Object> queryMap);
    List<Grade> findAll();
    int  getTotal(Map<String, Object> queryMap);
}
