package com.yishu.filter;

import com.yishu.domain.User;
import com.yishu.service.IWXService;
import com.yishu.util.DataUtil;
import com.yishu.util.GetOpenid;
import com.yishu.util.StringUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class OpenidFilter implements Filter {
	IWXService wxservice;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		String stime = DataUtil.fromDate24H();
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String url = req.getScheme() + "://" + req.getServerName() + req.getRequestURI();
		HttpSession session = req.getSession();
		String u_openid = (String) session.getAttribute(IWXService.SESSION_USER);
		System.out.println("url==============" + url);

		if (u_openid == null) {
			String code = request.getParameter("code");
			if (StringUtil.bIsNotNull(code)) {
				GetOpenid go = new GetOpenid();
				// String openId=CommonUtil.getOpenId(code);
				String openId = go.getOpenid(code);
				if (openId != null && !openId.equals("")) {
					User user = wxservice.findUserByopenid(openId);
					if (user != null) {
						// 将登录时间录入
						// userService.updateUserLogintime(stime,user.getOpenid());
						session.setAttribute(IWXService.SESSION_USER, openId);
						chain.doFilter(request, response);
					} else {
						user = new User();
						user.setOpenid(openId);
						user.setCreatetime(stime);
						wxservice.addSubscribe(user);
						session.setAttribute(IWXService.SESSION_USER, openId);
						chain.doFilter(request, response);
					}
				} else {
					resp.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx6234fc4a502ef625&redirect_uri="
						+ url + "&response_type=code&scope=snsapi_base#wechat_redirect");
				}
			} else {
				// 无公众号测试 暂时屏蔽
				resp.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx6234fc4a502ef625&redirect_uri="
					+ url + "&response_type=code&scope=snsapi_base#wechat_redirect");
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		ServletContext context = filterConfig.getServletContext();
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
		wxservice = (IWXService) ctx.getBean("wxservice");
	}

}
