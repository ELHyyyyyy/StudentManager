package com.fengzheng.programmer.controller;

import com.fengzheng.programmer.entity.Grade;
import com.fengzheng.programmer.entity.User;
import com.fengzheng.programmer.page.Page;
import com.fengzheng.programmer.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.*;

/**
 * 年级信息管理
 * @author 风筝丶
 * @create 2020/05/31 16:09
 */
@Controller
@RequestMapping("/grade")
public class GradeController {
    @Autowired
    private GradeService gradeService;
    /**
     * 年级管理列表
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "list",method = RequestMethod.GET)
    public ModelAndView list(ModelAndView modelAndView){
        modelAndView.setViewName("grade/grade_list");
        return modelAndView;
    }
    /**
     * 获取年级列表
     * @param gradename
     * @param page
     * @return
     */
    @RequestMapping(value = "/get_list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getList(
            @RequestParam(value = "gradename",required = false,defaultValue = "") String gradename,
            Page page
    ){
        Map<String,Object> map=new HashMap<String, Object>();
        Map<String,Object> queryMap=new HashMap<String, Object>();
        queryMap.put("gradename","%"+gradename+"%");
        queryMap.put("offset",page.getOffsize());
        queryMap.put("pageSize",page.getRows());
        map.put("rows",gradeService.findList(queryMap));//查询到的列表
        map.put("total",gradeService.getTotal(queryMap));//总的记录
        return map;
    }
    /**
     * 添加年级
     * @param grade
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> add(Grade grade){
        Map<String,String> map=new HashMap<String, String>();
       if(StringUtils.isEmpty(grade.getGradename())){
            map.put("type","error");
            map.put("msg","年级名不能为空");
            return map;
        }
        if (gradeService.add(grade)<=0){
            map.put("type","error");
            map.put("msg","添加失败");
            return map;
        }
        map.put("type","success");
        map.put("msg","添加成功");
        return map;
    }
    /**
     * 修改年级列表
     * @param grade
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Map<String,Object> edit(Grade grade){
        Map<String,Object> map=new HashMap<String, Object>();
        if(grade==null){
            map.put("type","error");
            map.put("msg","数据绑定出错");
            return map;
        }
        if(StringUtils.isEmpty(grade.getGradename())){
            map.put("type","error");
            map.put("msg","年级名不能为空");
            return map;
        } if (gradeService.edit(grade)<0){
            map.put("type","error");
            map.put("msg","修改失败");
            return map;
        }
        map.put("type","success");
        map.put("msg","修改成功！");
        return map;
    }
    /**
     * 删除年级
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> delete(
            @RequestParam("ids[]") Long[] ids) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (ids == null || ids.length == 0) {
            map.put("type", "error");
            map.put("msg", "请选择要删除的数据");
            return map;
        }
        try {
        if (gradeService.delete(org.apache.commons.lang.StringUtils.join(Arrays.asList(ids), ",")) <= 0) {
            map.put("type", "error");
            map.put("msg", "删除失败");
            return map;
        }
    }catch (Exception e){
            map.put("type", "error");
            map.put("msg", "该年级下存在班级信息!无法删除");
            return map;
        }
        map.put("type","success");
        map.put("msg","删除成功");
        return map;
    }
}
