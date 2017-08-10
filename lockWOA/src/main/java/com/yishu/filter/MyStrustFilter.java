package com.yishu.filter;

import org.apache.struts2.dispatcher.filter.StrutsPrepareAndExecuteFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class MyStrustFilter extends StrutsPrepareAndExecuteFilter {

	@Override
	public void doFilter(ServletRequest servletReq1, ServletResponse servletReq2, FilterChain filtChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletReq1;
		String servlet = request.getRequestURI();// 设置例外的URI地址
		if (request.getRequestURI().contains("ReportServer")) {
			filtChain.doFilter(servletReq1, servletReq2);
		} else {
			try {
				super.doFilter(servletReq1, servletReq2, filtChain);
			} catch (IllegalStateException e) {
			}
		}
	}

}
