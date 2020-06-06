package com.fengzheng.programmer.service.impl;

import com.fengzheng.programmer.dao.GradeDao;
import com.fengzheng.programmer.entity.Grade;
import com.fengzheng.programmer.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 风筝丶
 * @create 2020/05/31 16:13
 */
@Service
public class GradeServiceImpl implements GradeService {
    @Autowired
    private GradeDao gradeDao;

    public int add(Grade grade) {
        return gradeDao.add(grade);
    }

    public int delete(String ids) {
        return gradeDao.delete(ids);
    }

    public int edit(Grade grade) {
        return gradeDao.edit(grade);
    }

    public List<Grade> findList(Map<String, Object> queryMap) {
        return gradeDao.findList(queryMap);
    }

    public List<Grade> findAll() {
        return gradeDao.findAll();
    }

    public int getTotal(Map<String, Object> queryMap) {
        return gradeDao.getTotal(queryMap);
    }
}
