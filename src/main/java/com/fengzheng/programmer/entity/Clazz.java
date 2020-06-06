package com.fengzheng.programmer.entity;

import org.springframework.stereotype.Component;

/**
 * @author 风筝丶
 * @create 2020/05/31 16:10
 */
@Component
public class Clazz {
    private Long id;
    private Long gradeId;
    private String clazzname;
    private String remark;

    public Long getGradeId() {
        return gradeId;
    }

    public void setGradeId(Long gradeId) {
        this.gradeId = gradeId;
    }

    public String getClazzname() {
        return clazzname;
    }

    public void setClazzname(String clazzname) {
        this.clazzname = clazzname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
