package com.phion.tmall.interceptor;
 
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.phion.tmall.pojo.OrderItem;
import com.phion.tmall.pojo.User;
import com.phion.tmall.service.CategoryService;
import com.phion.tmall.service.OrderItemService;
 
public class OtherInterceptor implements HandlerInterceptor {
	
    @Autowired CategoryService categoryService;
    @Autowired OrderItemService orderItemService;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        return true;  
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, 
    		ModelAndView modelAndView) throws Exception {
    	
        HttpSession session = request.getSession();
        User user =(User) session.getAttribute("user");
        
        int  cartTotalItemNumber = 0;
        if(null!=user) {
            List<OrderItem> ois = orderItemService.listByUser(user);
            for (OrderItem oi : ois) {
                cartTotalItemNumber+=oi.getNumber();
            }
         
        }
        //购物车数量放到session中
        session.setAttribute("cartTotalItemNumber", cartTotalItemNumber);
    }
 
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}