package com.fengzheng.programmer.service;

import com.fengzheng.programmer.entity.Grade;

import java.util.List;
import java.util.Map;

/**
 * @author 风筝丶
 * @create 2020/05/31 16:12
 */
public interface GradeService {
    int add(Grade grade);
    int delete(String ids);
    int edit(Grade grade);
    List<Grade> findList(Map<String, Object> queryMap);
    List<Grade> findAll();
    int  getTotal(Map<String, Object> queryMap);
}
