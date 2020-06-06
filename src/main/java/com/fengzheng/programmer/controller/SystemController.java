package com.fengzheng.programmer.controller;

import com.fengzheng.programmer.entity.Student;
import com.fengzheng.programmer.entity.User;
import com.fengzheng.programmer.service.StudentService;
import com.fengzheng.programmer.service.UserService;
import com.fengzheng.programmer.util.CpachaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 风筝丶
 * @create 2020/05/28 22:25
 * 系统主页控制器
 */
@Controller
@RequestMapping("/system")
public class SystemController {
    @Autowired
    private UserService userService;
    @Autowired
    private StudentService studentService;
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(Model model){

        return "system/index";
    }
    /**
     * 登录页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(Model model){
        return "system/login";
    }

    /**
     * 注销登录
     * @param request
     * @return
     */
    @RequestMapping(value = "/login_out",method=RequestMethod.GET)
    public String loginOut(HttpServletRequest request){
        request.getSession().setAttribute("user", null);
        return "redirect:login";
    }
    /**
     * 登录表单提交
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> login(
            @RequestParam(value = "username",required = true) String username,
            @RequestParam(value = "password",required = true) String password,
            @RequestParam(value = "vcode",required = true) String vcode,
            @RequestParam(value = "type",required = true) int type,
            HttpServletRequest request,
            Model model){
        Map<String,String> map=new HashMap<String, String>();
        if(StringUtils.isEmpty(username)){
            map.put("type","error");
            map.put("msg","用户名不能为空");
            return map;
        }if(StringUtils.isEmpty(password)){
            map.put("type","error");
            map.put("msg","密码不能为空");
            return map;
        }if (StringUtils.isEmpty(vcode)){
            map.put("type","error");
            map.put("msg","验证码不能为空");
            return map;
        }
        String loginCpacha = (String) request.getSession().getAttribute("loginCpacha");
        if (StringUtils.isEmpty(loginCpacha)){
            map.put("type","error");
            map.put("msg","长时间未操作,会话已失效,请刷新后重试!");
            return map;
            //验证码不一致
        }if (!vcode.toUpperCase().equals(loginCpacha.toUpperCase())){
            map.put("type","error");
            map.put("msg","验证码错误!");
            return map;
        }
        //验证码一致清空缓存
        request.getSession().setAttribute("loginCpacha",null);
        //从数据库中去查找用户
        //1为管理员
        if (type==1) {
            User user = userService.findByUserName(username);
            if (user == null) {
                map.put("type", "error");
                map.put("msg", "用户不存在");
                return map;
            }
            if (!password.equals(user.getPassword())) {
                map.put("type", "error");
                map.put("msg", "密码错误");
                return map;
            }
            request.getSession().setAttribute("user", user);
        }
        //学生
        if (type==2){
            Student student = studentService.findByUserName(username);
            if (student == null) {
                map.put("type", "error");
                map.put("msg", "学生不存在");
                return map;
            }
            if (!password.equals(student.getPassword())) {
                map.put("type", "error");
                map.put("msg", "密码错误");
                return map;
            }
            request.getSession().setAttribute("student", student);
        }
        request.getSession().setAttribute("userType",type);
        map.put("type","success");
        map.put("msg","登录成功");
        return map;
    }

    /**
     * 验证码
     * @param request
     * @param vcodeLen
     * @param width
     * @param height
     * @param response
     */
    @RequestMapping(value = "/get_cpacha",method = RequestMethod.GET)
    public void getCpacha(HttpServletRequest request,
                          @RequestParam(value = "vcodeLen",defaultValue = "4",required = false) Integer vcodeLen,
                          @RequestParam(value = "width",defaultValue = "98",required = false) Integer width,
                          @RequestParam(value = "height",defaultValue = "33",required = false) Integer height,
                          HttpServletResponse response){
        CpachaUtil cpachaUtil=new CpachaUtil(vcodeLen,width,height);
        String generatorVCode = cpachaUtil.generatorVCode();
        request.getSession().setAttribute("loginCpacha",generatorVCode);
        BufferedImage generatorRotateVCodeImage = cpachaUtil.generatorRotateVCodeImage(generatorVCode, true);
        try {
            ImageIO.write(generatorRotateVCodeImage,"gif",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
