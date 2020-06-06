package com.fengzheng.programmer.service.impl;


import com.fengzheng.programmer.dao.StudentDao;
import com.fengzheng.programmer.entity.Student;
import com.fengzheng.programmer.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentDao studentDao;
    public Student findByUserName(String stuname) {

        return studentDao.findByUserName(stuname);
    }

    public int add(Student student) {
        return studentDao.add(student);
    }

    public int edit(Student student) {
        return studentDao.edit(student);
    }

    public int delete(String ids) {
        return studentDao.delete(ids);
    }

    public List<Student> findList(Map<String, Object> queryMap) {
        return studentDao.findList(queryMap);
    }

    public List<Student> findAll() {
        return studentDao.findAll();
    }

    public int getTotal(Map<String, Object> queryMap) {
        return studentDao.getTotal(queryMap);
    }
}
