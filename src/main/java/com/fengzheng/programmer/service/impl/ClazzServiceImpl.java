package com.fengzheng.programmer.service.impl;

import com.fengzheng.programmer.dao.ClazzDao;
import com.fengzheng.programmer.entity.Clazz;
import com.fengzheng.programmer.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 风筝丶
 * @create 2020/06/01 16:27
 */
@Service
public class ClazzServiceImpl implements ClazzService {
    @Autowired
    private ClazzDao clazzDao;
    public int add(Clazz clazz) {
        return clazzDao.add(clazz);
    }

    public int delete(String ids) {
        return clazzDao.delete(ids);
    }

    public int edit(Clazz clazz) {
        return clazzDao.edit(clazz);
    }

    public List<Clazz> findList(Map<String, Object> queryMap) {
        return clazzDao.findList(queryMap);
    }

    public List<Clazz> findAll() {
        return clazzDao.findAll();
    }

    public int getTotal(Map<String, Object> queryMap) {
        return clazzDao.getTotal(queryMap);
    }
}
