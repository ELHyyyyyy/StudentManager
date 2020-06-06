package com.fengzheng.programmer.controller;

import com.fengzheng.programmer.entity.Clazz;
import com.fengzheng.programmer.entity.Grade;
import com.fengzheng.programmer.page.Page;
import com.fengzheng.programmer.service.ClazzService;
import com.fengzheng.programmer.service.GradeService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 年级信息管理
 * @author 风筝丶
 * @create 2020/05/31 16:09
 */
@Controller
@RequestMapping("/clazz")
public class ClazzController {
    @Autowired
    private GradeService gradeService;
    @Autowired
    private ClazzService clazzService;
    /**
     * 班级管理列表
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "list",method = RequestMethod.GET)
    public ModelAndView list(ModelAndView modelAndView){
        modelAndView.setViewName("clazz/clazz_list");
        List<Grade> all = gradeService.findAll();
        modelAndView.addObject("gradeList",all);
        modelAndView.addObject("gradeListJson", JSONArray.fromObject(all));
        return modelAndView;
    }
    /**
     * 获取班级列表
     * @param clazzname
     * @param page
     * @return
     */
    @RequestMapping(value = "/get_list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getList(
            @RequestParam(value = "clazzname",required = false,defaultValue = "") String clazzname,
            @RequestParam(value = "gradeId",required = false) Long gradeId,
            Page page
    ){
        Map<String,Object> map=new HashMap<String, Object>();
        Map<String,Object> queryMap=new HashMap<String, Object>();
        queryMap.put("clazzname","%"+clazzname+"%");
        if(gradeId!=null){
            queryMap.put("gradeId",gradeId);
        }
        queryMap.put("offset",page.getOffsize());
        queryMap.put("pageSize",page.getRows());
        map.put("rows",clazzService.findList(queryMap));//查询到的列表
        map.put("total",clazzService.getTotal(queryMap));//总的记录
        return map;
    }
    /**
     * 添加年级
     * @param clazz
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> add(Clazz clazz){
        Map<String,String> map=new HashMap<String, String>();
        if(StringUtils.isEmpty(clazz.getClazzname())){
            map.put("type","error");
            map.put("msg","年级名不能为空");
            return map;
        }
        if(clazz.getGradeId()==null){
            map.put("type","error");
            map.put("msg","请选择所属年级");
            return map;
        }
        if (clazzService.add(clazz)<=0){
            map.put("type","error");
            map.put("msg","添加失败");
            return map;
        }
        map.put("type","success");
        map.put("msg","添加成功");
        return map;
    }
    /**
     * 修改班级列表
     * @param clazz
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Map<String,Object> edit(Clazz clazz){
        Map<String,Object> map=new HashMap<String, Object>();
        if(clazz==null){
            map.put("type","error");
            map.put("msg","数据绑定出错");
            return map;
        }
        if(clazz.getGradeId()==null){
            map.put("type","error");
            map.put("msg","请选择所属年级");
            return map;
        }
        if(StringUtils.isEmpty(clazz.getClazzname())){
            map.put("type","error");
            map.put("msg","年级名不能为空");
            return map;
        } if (clazzService.edit(clazz)<0){
            map.put("type","error");
            map.put("msg","修改失败");
            return map;
        }
        map.put("type","success");
        map.put("msg","修改成功！");
        return map;
    }
    /**
     * 删除班级
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
            if (clazzService.delete(org.apache.commons.lang.StringUtils.join(Arrays.asList(ids), ",")) <= 0) {
                map.put("type", "error");
                map.put("msg", "删除失败");
                return map;
            }
        }catch (Exception e){
            map.put("type", "error");
            map.put("msg", "该班级下存在学生信息!无法删除");
            return map;
        }
        map.put("type","success");
        map.put("msg","删除成功");
        return map;
    }
}
