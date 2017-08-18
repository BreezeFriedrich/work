package com.hysm.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;

public class MyStrustFilter extends StrutsPrepareAndExecuteFilter {

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		String servlet = request.getRequestURI();// 设置例外的URI地址
		if (request.getRequestURI().contains("ReportServer")) {
			arg2.doFilter(arg0, arg1);
		} else {
			try {
				super.doFilter(arg0, arg1, arg2);
			} catch (IllegalStateException e) {
			}
		}
	}

}