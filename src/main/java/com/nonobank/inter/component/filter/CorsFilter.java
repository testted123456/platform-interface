package com.nonobank.inter.component.filter;

import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.session.web.http.SessionRepositoryFilter;

//@Component
public class CorsFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

//	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest)req;
		System.out.println(request.getMethod());
		System.out.println("@@@@"+request.getUserPrincipal());
		
		HttpSession session = request.getSession();
		
		String attributeName = request.getParameter("attributeName");  
        String attributeValue = request.getParameter("attributeValue"); 
        SessionRepositoryFilter srf;
        session.getAttributeNames();
        System.out.println("++" +attributeName);
        System.out.println("++" +attributeValue);
//        RedisOperationsSessionRepository s = (RedisOperationsSessionRepository)session;
		HttpServletResponse response = (HttpServletResponse) res;  
		Enumeration<String>  enumer = request.getSession().getAttributeNames();
		while(enumer.hasMoreElements()){
			System.out.println("+++" + enumer.nextElement());
		}
		if("POST".equals(request.getMethod())){
			response.setHeader("Access-Control-Allow-Origin", "localhost");  
		}
		
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");  
        response.setHeader("Access-Control-Max-Age", "3600");  
        response.setHeader("Access-Control-Allow-Headers", "content-type"); 
        System.out.println("*********************************过滤器被使用**************************");  
        chain.doFilter(req, res);  
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
