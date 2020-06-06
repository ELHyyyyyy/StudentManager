package com.fengzheng.programmer.controller;

import com.fengzheng.programmer.entity.Clazz;
import com.fengzheng.programmer.entity.Grade;
import com.fengzheng.programmer.entity.Student;
import com.fengzheng.programmer.page.Page;
import com.fengzheng.programmer.service.ClazzService;
import com.fengzheng.programmer.service.GradeService;
import com.fengzheng.programmer.service.StudentService;
import com.fengzheng.programmer.util.StringUtil;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 学生信息管理
 */
@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private ClazzService clazzService;
    @Autowired
    private StudentService studentService;
    /**
     * 学生管理列表
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "list",method = RequestMethod.GET)
    public ModelAndView list(ModelAndView modelAndView){
        modelAndView.setViewName("student/student_list");
        List<Clazz> clazzList = clazzService.findAll();
        modelAndView.addObject("clazzList",clazzList);
        modelAndView.addObject("clazzListJson", JSONArray.fromObject(clazzList));
        return modelAndView;
    }
    /**
     * 获取学生列表
     * @param stuname
     * @param page
     * @return
     */
    @RequestMapping(value = "/get_list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getList(
            @RequestParam(value = "stuname",required = false,defaultValue = "") String stuname,
            @RequestParam(value = "clazzId",required = false) Long clazzId,
            HttpServletRequest request,
            Page page
    ){
        Map<String,Object> map=new HashMap<String, Object>();
        Map<String,Object> queryMap=new HashMap<String, Object>();
        queryMap.put("stuname","%"+stuname+"%");
        Object userType = request.getSession().getAttribute("userType");
        if("2".equals(userType.toString())){
            Student loginedStudent = (Student)request.getSession().getAttribute("student");
            queryMap.put("stuname","%"+loginedStudent.getStuname()+"%");
        }

        if(clazzId!=null){
            queryMap.put("clazzId",clazzId);
        }
        queryMap.put("offset",page.getOffsize());
        queryMap.put("pageSize",page.getRows());
        map.put("rows",studentService.findList(queryMap));//查询到的列表
        map.put("total",studentService.getTotal(queryMap));//总的记录
        return map;
    }
    /**
     * 添加学生
     * @param student
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> add(Student student){
        Map<String,String> map=new HashMap<String, String>();
        if(StringUtils.isEmpty(student.getStuname())){
            map.put("type", "error");
            map.put("msg", "学生姓名不能为空！");
            return map;
        }
        if(StringUtils.isEmpty(student.getPassword())){
            map.put("type", "error");
            map.put("msg", "学生登录密码不能为空！");
            return map;
        }
        Student exitStudent = studentService.findByUserName(student.getStuname());
        if(exitStudent!=null){
            if(student.getId()!=exitStudent.getId()){
                map.put("type", "error");
                map.put("msg", "学生已存在");
                return map;
            }
        }
        if(student.getClazzId() == null){
            map.put("type", "error");
            map.put("msg", "请选择所属班级！");
            return map;
        }
        student.setSn(StringUtil.generateSn("S", ""));
        if(studentService.add(student) <= 0){
            map.put("type", "error");
            map.put("msg", "学生添加失败！");
            return map;
        }
        map.put("type", "success");
        map.put("msg", "学生添加成功！");
        return map;
    }
    /**
     * 修改学生列表
     * @param student
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Map<String,Object> edit(Student student){
        Map<String,Object> map=new HashMap<String, Object>();
        if(student==null){
            map.put("type","error");
            map.put("msg","数据绑定出错");
            return map;
        }
        if(student.getClazzId()==null){
            map.put("type","error");
            map.put("msg","请选择所属班级");
            return map;
        }
        if(StringUtils.isEmpty(student.getStuname())){
            map.put("type","error");
            map.put("msg","班级不能为空");
            return map;
        } if (studentService.edit(student)<0){
            map.put("type","error");
            map.put("msg","修改失败");
            return map;
        }
        map.put("type","success");
        map.put("msg","修改成功！");
        return map;
    }
    /**
     * 删除学生
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
            if (studentService.delete(org.apache.commons.lang.StringUtils.join(Arrays.asList(ids), ",")) <= 0) {
                map.put("type", "error");
                map.put("msg", "删除失败");
                return map;
            }
        }catch (Exception e){
            map.put("type", "error");
            map.put("msg", "该学生下存在其他信息  !无法删除");
            return map;
        }
        map.put("type","success");
        map.put("msg","删除成功");
        return map;
    }
}
