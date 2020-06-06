package com.fengzheng.programmer.service;

import java.util.List;
import java.util.Map;

import com.fengzheng.programmer.entity.Student;
import org.springframework.stereotype.Service;


/**
 * ѧ��service
 * @author llq
 *
 */
@Service
public interface StudentService {
    Student findByUserName(String stuname);
    int add(Student student);
    int edit(Student student);
    int delete(String ids);
    List<Student> findList(Map<String, Object> queryMap);
    List<Student> findAll();
    int getTotal(Map<String, Object> queryMap);
}
