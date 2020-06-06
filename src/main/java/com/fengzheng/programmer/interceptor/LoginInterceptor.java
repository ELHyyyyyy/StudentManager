package com.fengzheng.programmer.interceptor;


import net.sf.json.JSONObject;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 风筝丶
 * @create 2020/05/30 11:11
 * 登录过滤拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {
   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       Object user = request.getSession().getAttribute("user");
       if(user==null){
           if ("XMLHttpRequest".equals(request.getHeader("X-Request-With"))){
               //ajax请求
               Map<String,String> map=new HashMap<String, String>();
               map.put("type","error");
               map.put("msg","登录状态已失效,请重新登录");
               response.getWriter().write(JSONObject.fromObject(map).toString());
               return false;
           }
           response.sendRedirect(request.getContextPath()+"/system/login");
           return false;
       }
       return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }
}
