package com.fengzheng.programmer.entity;

import org.springframework.stereotype.Component;

/**
 * @author 风筝丶
 * @create 2020/05/31 16:10
 */
@Component
public class Grade {
    private Long id;
    private String gradename;
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGradename() {
        return gradename;
    }

    public void setGradename(String gradename) {
        this.gradename = gradename;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
