package com.fengzheng.programmer.dao;

import java.util.List;
import java.util.Map;

import com.fengzheng.programmer.entity.Student;
import org.springframework.stereotype.Repository;



@Repository
public interface StudentDao {
	Student findByUserName(String stuname);
	 int add(Student student);
	int edit(Student student);
	int delete(String ids);
	 List<Student> findList(Map<String, Object> queryMap);
	List<Student> findAll();
	int getTotal(Map<String, Object> queryMap);
}
