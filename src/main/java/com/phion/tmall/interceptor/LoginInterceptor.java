package com.phion.tmall.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.phion.tmall.pojo.User;

public class LoginInterceptor implements HandlerInterceptor {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		logger.info("-----------prehandle-------------");
		HttpSession session = request.getSession();
		String contextPath = session.getServletContext().getContextPath();
		String[] requireAuthPages = new String[]{
				//需要权限的界面
				"buy",
				"cart",
				"alipay",
				"bought",
				"confirmPay",
				"orderConfirmed",
				"review",
				
				//需要权限的后台操作
				"forebuyone",
                "forebuy",
                "forecart",
                "forechangeOrderItem",
                "foredeleteOrderItem",
                "forecreateOrder",
                "forepayed",
                "forebought",
                "foreconfirmPay",
                "foreorderConfirmed",
                "foredeleteOrder",
                "forereview",
                "foredoreview",
                
		};
		String uri = request.getRequestURI();
		uri = StringUtils.remove(uri, contextPath+"/");
		String page = uri;
		
		if(isBegingWith(page, requireAuthPages)){
            User user = (User) session.getAttribute("user");
            if(user==null) {
                response.sendRedirect("login");
                return false;
            }
        }
        return true; 
	}

	private boolean isBegingWith(String page, String[] requireAuthPages) {
		boolean result = false;
        for (String requiredAuthPage : requireAuthPages) {
            if(StringUtils.startsWith(page, requiredAuthPage)) {
                result = true; 
                break;
            }
        }
        return result;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
