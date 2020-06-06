package com.fengzheng.programmer.controller;

import com.fengzheng.programmer.entity.User;
import com.fengzheng.programmer.page.Page;
import com.fengzheng.programmer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 风筝丶
 * @create 2020/05/30 11:34
 * 用户（管理员）控制器
 */
@RequestMapping("/user")
@Controller
public class UserController {
    @Autowired
    public UserService userService;
    /**
     * 用户管理列表
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "list",method = RequestMethod.GET)
    public ModelAndView list(ModelAndView modelAndView){
        modelAndView.setViewName("user/user_list");
        return modelAndView;
    }

    /**
     * 获取用户列表
     * @param username
     * @param page
     * @return
     */
    @RequestMapping(value = "/get_list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getList(
            @RequestParam(value = "username",required = false,defaultValue = "") String username,
            Page page
    ){
        Map<String,Object> map=new HashMap<String, Object>();
        Map<String,Object> queryMap=new HashMap<String, Object>();
        queryMap.put("username","%"+username+"%");
        queryMap.put("offset",page.getOffsize());
        queryMap.put("pageSize",page.getRows());
        map.put("rows",userService.findList(queryMap));//查询到的列表
        map.put("total",userService.getTotal(queryMap));//总的记录
        return map;
    }
    /**
     * 添加用户
     * @param user
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> add(User user){
        Map<String,String> map=new HashMap<String, String>();
        if(user==null){
            map.put("type","error");
            map.put("msg","数据绑定出错");
            return map;
        }if(StringUtils.isEmpty(user.getUsername())){
            map.put("type","error");
            map.put("msg","用户名不能为空");
            return map;
        }if(StringUtils.isEmpty(user.getPassword())){
            map.put("type","error");
            map.put("msg","密码不能为空");
            return map;
        }
        User exitUser = userService.findByUserName(user.getUsername());
        if(exitUser!=null){
            map.put("type","error");
            map.put("msg","用户已存在");
            return map;
        }
        if (userService.add(user)<0){
            map.put("type","error");
            map.put("msg","添加失败");
            return map;
        }
        map.put("type","success");
        map.put("msg","添加成功");
        return map;
    }

    /**
     * 修改用户
     * @param user
     * @return
     */
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> edit(User user){
        Map<String,String> map=new HashMap<String, String>();
        if(user==null){
            map.put("type","error");
            map.put("msg","数据绑定出错");
            return map;
        }if(StringUtils.isEmpty(user.getUsername())){
            map.put("type","error");
            map.put("msg","用户名不能为空");
            return map;
        }
        if(StringUtils.isEmpty(user.getPassword())){
            map.put("type","error");
            map.put("msg","密码不能为空");
            return map;
        }
        User exitUser = userService.findByUserName(user.getUsername());
        if(exitUser!=null){
            if (user.getId()!=exitUser.getId()){
                map.put("type","error");
                map.put("msg","用户已存在");
                return map;
            }
        }
        if (userService.edit(user)<0){
            map.put("type","error");
            map.put("msg","修改失败");
            return map;
        }
        map.put("type","success");
        map.put("msg","修改成功");
        return map;
    }

    /**
     * 删除用户
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> delete(
            @RequestParam("ids[]") Long[] ids){
        Map<String,Object> map=new HashMap<String, Object>();
        if(ids==null){
            map.put("type","error");
            map.put("msg","请选择要删除的数据");
            return map;
        }
        String idsString ="";
        for (Long id : ids) {
            idsString+=id+",";
        }
        idsString=idsString.substring(0,idsString.length()-1);
        if (userService.delete(idsString)<=0){
            map.put("type","error");
            map.put("msg","删除失败");
            return map;
        }
        map.put("type","success");
        map.put("msg","删除成功");
        return map;
    }
}
