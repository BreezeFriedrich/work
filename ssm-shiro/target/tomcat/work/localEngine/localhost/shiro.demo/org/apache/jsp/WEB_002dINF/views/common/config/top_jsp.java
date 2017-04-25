package org.apache.jsp.WEB_002dINF.views.common.config;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class top_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fshiro_005fhasAnyRoles_0026_005fname;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fshiro_005fhasPermission_0026_005fname;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fshiro_005fuser;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fshiro_005fprincipal_0026_005fproperty_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fshiro_005fguest;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fshiro_005fhasAnyRoles_0026_005fname = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fshiro_005fhasPermission_0026_005fname = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fshiro_005fuser = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fshiro_005fprincipal_0026_005fproperty_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fshiro_005fguest = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fshiro_005fhasAnyRoles_0026_005fname.release();
    _005fjspx_005ftagPool_005fshiro_005fhasPermission_0026_005fname.release();
    _005fjspx_005ftagPool_005fshiro_005fuser.release();
    _005fjspx_005ftagPool_005fshiro_005fprincipal_0026_005fproperty_005fnobody.release();
    _005fjspx_005ftagPool_005fshiro_005fguest.release();
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=utf-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write('\r');
      out.write('\n');
      out.write("\r\n");
      out.write("  \r\n");
      out.write("\r\n");
 
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
 
      out.write(" \r\n");
      out.write("<script baseUrl=\"");
      out.print(basePath);
      out.write("\" src=\"");
      out.print(basePath);
      out.write("/js/user.login.js\"></script>\r\n");
      out.write("<div class=\"navbar navbar-inverse navbar-fixed-top animated fadeInDown\" style=\"z-index: 101;height: 41px;\">\r\n");
      out.write("\t  \r\n");
      out.write("      <div class=\"container\" style=\"padding-left: 0px; padding-right: 0px;\">\r\n");
      out.write("        <div class=\"navbar-header\">\r\n");
      out.write("          <button data-target=\".navbar-collapse\" data-toggle=\"collapse\" type=\"button\" class=\"navbar-toggle collapsed\">\r\n");
      out.write("            <span class=\"sr-only\">导航</span>\r\n");
      out.write("            <span class=\"icon-bar\"></span>\r\n");
      out.write("            <span class=\"icon-bar\"></span>\r\n");
      out.write("            <span class=\"icon-bar\"></span>\r\n");
      out.write("          </button>\r\n");
      out.write("\t     </div>\r\n");
      out.write("\t     <div role=\"navigation\" class=\"navbar-collapse collapse\">\r\n");
      out.write("\t     \t\t<a id=\"_logo\"  href=\"");
      out.print(basePath);
      out.write("\" style=\"color:#fff; font-size: 24px;\" class=\"navbar-brand hidden-sm\">SSM + Shiro Demo 演示</a>\r\n");
      out.write("\t          <ul class=\"nav navbar-nav\" id=\"topMenu\">\r\n");
      out.write("\t\t\t\t<li class=\"dropdown \">\r\n");
      out.write("\t\t\t\t\t<a aria-expanded=\"false\" aria-haspopup=\"true\" role=\"button\" data-toggle=\"dropdown\" class=\"dropdown-toggle\" href=\"");
      out.print(basePath);
      out.write("/user/index.shtml\">\r\n");
      out.write("\t\t\t\t\t\t个人中心<span class=\"caret\"></span>\r\n");
      out.write("\t\t\t\t\t</a>\r\n");
      out.write("\t\t\t\t\t<ul class=\"dropdown-menu\">\r\n");
      out.write("\t\t\t\t\t\t<li><a href=\"");
      out.print(basePath);
      out.write("/user/index.shtml\">个人资料</a></li>\r\n");
      out.write("\t\t\t\t\t\t<li><a href=\"");
      out.print(basePath);
      out.write("/user/updateSelf.shtml\" >资料修改</a></li>\r\n");
      out.write("\t\t\t\t\t\t<li><a href=\"");
      out.print(basePath);
      out.write("/user/updatePswd.shtml\" >密码修改</a></li>\r\n");
      out.write("\t\t\t\t\t\t<li><a href=\"");
      out.print(basePath);
      out.write("/role/mypermission.shtml\">我的权限</a></li>\r\n");
      out.write("\t\t\t\t\t</ul>\r\n");
      out.write("\t\t\t\t</li>\t  \r\n");
      out.write("\t\t\t\t");
      out.write("\r\n");
      out.write("\t\t\t\t");
      //  shiro:hasAnyRoles
      org.apache.shiro.web.tags.HasAnyRolesTag _jspx_th_shiro_005fhasAnyRoles_005f0 = (org.apache.shiro.web.tags.HasAnyRolesTag) _005fjspx_005ftagPool_005fshiro_005fhasAnyRoles_0026_005fname.get(org.apache.shiro.web.tags.HasAnyRolesTag.class);
      _jspx_th_shiro_005fhasAnyRoles_005f0.setPageContext(_jspx_page_context);
      _jspx_th_shiro_005fhasAnyRoles_005f0.setParent(null);
      // /WEB-INF/views/common/config/top.jsp(36,4) name = name type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_shiro_005fhasAnyRoles_005f0.setName("888888,100002");
      int _jspx_eval_shiro_005fhasAnyRoles_005f0 = _jspx_th_shiro_005fhasAnyRoles_005f0.doStartTag();
      if (_jspx_eval_shiro_005fhasAnyRoles_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("          \r\n");
          out.write("\t\t\t\t<li class=\"dropdown \">\r\n");
          out.write("\t\t\t\t\t<a aria-expanded=\"false\" aria-haspopup=\"true\"  role=\"button\" data-toggle=\"dropdown\" class=\"dropdown-toggle\" href=\"");
          out.print(basePath);
          out.write("/member/list.shtml\">\r\n");
          out.write("\t\t\t\t\t\t用户中心<span class=\"caret\"></span>\r\n");
          out.write("\t\t\t\t\t</a>\r\n");
          out.write("\t\t\t\t\t<ul class=\"dropdown-menu\">\r\n");
          out.write("\t\t\t\t\t\t");
          //  shiro:hasPermission
          org.apache.shiro.web.tags.HasPermissionTag _jspx_th_shiro_005fhasPermission_005f0 = (org.apache.shiro.web.tags.HasPermissionTag) _005fjspx_005ftagPool_005fshiro_005fhasPermission_0026_005fname.get(org.apache.shiro.web.tags.HasPermissionTag.class);
          _jspx_th_shiro_005fhasPermission_005f0.setPageContext(_jspx_page_context);
          _jspx_th_shiro_005fhasPermission_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_shiro_005fhasAnyRoles_005f0);
          // /WEB-INF/views/common/config/top.jsp(42,6) name = name type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
          _jspx_th_shiro_005fhasPermission_005f0.setName("/member/list.shtml");
          int _jspx_eval_shiro_005fhasPermission_005f0 = _jspx_th_shiro_005fhasPermission_005f0.doStartTag();
          if (_jspx_eval_shiro_005fhasPermission_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n");
              out.write("\t\t\t\t\t\t\t<li><a href=\"");
              out.print(basePath);
              out.write("/member/list.shtml\">用户列表</a></li>\r\n");
              out.write("\t\t\t\t\t\t");
              int evalDoAfterBody = _jspx_th_shiro_005fhasPermission_005f0.doAfterBody();
              if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                break;
            } while (true);
          }
          if (_jspx_th_shiro_005fhasPermission_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _005fjspx_005ftagPool_005fshiro_005fhasPermission_0026_005fname.reuse(_jspx_th_shiro_005fhasPermission_005f0);
            return;
          }
          _005fjspx_005ftagPool_005fshiro_005fhasPermission_0026_005fname.reuse(_jspx_th_shiro_005fhasPermission_005f0);
          out.write("\r\n");
          out.write("\t\t\t\t\t\t");
          //  shiro:hasPermission
          org.apache.shiro.web.tags.HasPermissionTag _jspx_th_shiro_005fhasPermission_005f1 = (org.apache.shiro.web.tags.HasPermissionTag) _005fjspx_005ftagPool_005fshiro_005fhasPermission_0026_005fname.get(org.apache.shiro.web.tags.HasPermissionTag.class);
          _jspx_th_shiro_005fhasPermission_005f1.setPageContext(_jspx_page_context);
          _jspx_th_shiro_005fhasPermission_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_shiro_005fhasAnyRoles_005f0);
          // /WEB-INF/views/common/config/top.jsp(45,6) name = name type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
          _jspx_th_shiro_005fhasPermission_005f1.setName("/member/online.shtml");
          int _jspx_eval_shiro_005fhasPermission_005f1 = _jspx_th_shiro_005fhasPermission_005f1.doStartTag();
          if (_jspx_eval_shiro_005fhasPermission_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n");
              out.write("\t\t\t\t\t\t\t<li><a href=\"");
              out.print(basePath);
              out.write("/member/online.shtml\">在线用户</a></li>\r\n");
              out.write("\t\t\t\t\t\t");
              int evalDoAfterBody = _jspx_th_shiro_005fhasPermission_005f1.doAfterBody();
              if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                break;
            } while (true);
          }
          if (_jspx_th_shiro_005fhasPermission_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _005fjspx_005ftagPool_005fshiro_005fhasPermission_0026_005fname.reuse(_jspx_th_shiro_005fhasPermission_005f1);
            return;
          }
          _005fjspx_005ftagPool_005fshiro_005fhasPermission_0026_005fname.reuse(_jspx_th_shiro_005fhasPermission_005f1);
          out.write("\r\n");
          out.write("\t\t\t\t\t</ul>\r\n");
          out.write("\t\t\t\t</li>\t\r\n");
          out.write("\t\t\t\t");
          int evalDoAfterBody = _jspx_th_shiro_005fhasAnyRoles_005f0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_shiro_005fhasAnyRoles_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _005fjspx_005ftagPool_005fshiro_005fhasAnyRoles_0026_005fname.reuse(_jspx_th_shiro_005fhasAnyRoles_005f0);
        return;
      }
      _005fjspx_005ftagPool_005fshiro_005fhasAnyRoles_0026_005fname.reuse(_jspx_th_shiro_005fhasAnyRoles_005f0);
      out.write("         \r\n");
      out.write("\t\t\t\t");
      out.write("\r\n");
      out.write("\t\t\t\t");
      //  shiro:hasAnyRoles
      org.apache.shiro.web.tags.HasAnyRolesTag _jspx_th_shiro_005fhasAnyRoles_005f1 = (org.apache.shiro.web.tags.HasAnyRolesTag) _005fjspx_005ftagPool_005fshiro_005fhasAnyRoles_0026_005fname.get(org.apache.shiro.web.tags.HasAnyRolesTag.class);
      _jspx_th_shiro_005fhasAnyRoles_005f1.setPageContext(_jspx_page_context);
      _jspx_th_shiro_005fhasAnyRoles_005f1.setParent(null);
      // /WEB-INF/views/common/config/top.jsp(52,4) name = name type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_shiro_005fhasAnyRoles_005f1.setName("888888,100003");
      int _jspx_eval_shiro_005fhasAnyRoles_005f1 = _jspx_th_shiro_005fhasAnyRoles_005f1.doStartTag();
      if (_jspx_eval_shiro_005fhasAnyRoles_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n");
          out.write("\t\t\t\t\t<li class=\"dropdown active\">\r\n");
          out.write("\t\t\t\t\t\t<a aria-expanded=\"false\" aria-haspopup=\"true\"  role=\"button\" data-toggle=\"dropdown\" class=\"dropdown-toggle\" href=\"/permission/index.shtml\">\r\n");
          out.write("\t\t\t\t\t\t\t权限管理<span class=\"caret\"></span>\r\n");
          out.write("\t\t\t\t\t\t</a>\r\n");
          out.write("\t\t\t\t\t\t<ul class=\"dropdown-menu\">\r\n");
          out.write("\t\t\t\t\t\t\t");
          //  shiro:hasPermission
          org.apache.shiro.web.tags.HasPermissionTag _jspx_th_shiro_005fhasPermission_005f2 = (org.apache.shiro.web.tags.HasPermissionTag) _005fjspx_005ftagPool_005fshiro_005fhasPermission_0026_005fname.get(org.apache.shiro.web.tags.HasPermissionTag.class);
          _jspx_th_shiro_005fhasPermission_005f2.setPageContext(_jspx_page_context);
          _jspx_th_shiro_005fhasPermission_005f2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_shiro_005fhasAnyRoles_005f1);
          // /WEB-INF/views/common/config/top.jsp(58,7) name = name type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
          _jspx_th_shiro_005fhasPermission_005f2.setName("/role/index.shtml");
          int _jspx_eval_shiro_005fhasPermission_005f2 = _jspx_th_shiro_005fhasPermission_005f2.doStartTag();
          if (_jspx_eval_shiro_005fhasPermission_005f2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n");
              out.write("\t\t\t\t\t\t\t\t<li><a href=\"");
              out.print(basePath);
              out.write("/role/index.shtml\">角色列表</a></li>\r\n");
              out.write("\t\t\t\t\t\t\t");
              int evalDoAfterBody = _jspx_th_shiro_005fhasPermission_005f2.doAfterBody();
              if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                break;
            } while (true);
          }
          if (_jspx_th_shiro_005fhasPermission_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _005fjspx_005ftagPool_005fshiro_005fhasPermission_0026_005fname.reuse(_jspx_th_shiro_005fhasPermission_005f2);
            return;
          }
          _005fjspx_005ftagPool_005fshiro_005fhasPermission_0026_005fname.reuse(_jspx_th_shiro_005fhasPermission_005f2);
          out.write("\r\n");
          out.write("\t\t\t\t\t\t\t");
          //  shiro:hasPermission
          org.apache.shiro.web.tags.HasPermissionTag _jspx_th_shiro_005fhasPermission_005f3 = (org.apache.shiro.web.tags.HasPermissionTag) _005fjspx_005ftagPool_005fshiro_005fhasPermission_0026_005fname.get(org.apache.shiro.web.tags.HasPermissionTag.class);
          _jspx_th_shiro_005fhasPermission_005f3.setPageContext(_jspx_page_context);
          _jspx_th_shiro_005fhasPermission_005f3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_shiro_005fhasAnyRoles_005f1);
          // /WEB-INF/views/common/config/top.jsp(61,7) name = name type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
          _jspx_th_shiro_005fhasPermission_005f3.setName("/role/allocation.shtml");
          int _jspx_eval_shiro_005fhasPermission_005f3 = _jspx_th_shiro_005fhasPermission_005f3.doStartTag();
          if (_jspx_eval_shiro_005fhasPermission_005f3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n");
              out.write("\t\t\t\t\t\t\t\t<li><a href=\"");
              out.print(basePath);
              out.write("/role/allocation.shtml\">角色分配</a></li>\r\n");
              out.write("\t\t\t\t\t\t\t");
              int evalDoAfterBody = _jspx_th_shiro_005fhasPermission_005f3.doAfterBody();
              if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                break;
            } while (true);
          }
          if (_jspx_th_shiro_005fhasPermission_005f3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _005fjspx_005ftagPool_005fshiro_005fhasPermission_0026_005fname.reuse(_jspx_th_shiro_005fhasPermission_005f3);
            return;
          }
          _005fjspx_005ftagPool_005fshiro_005fhasPermission_0026_005fname.reuse(_jspx_th_shiro_005fhasPermission_005f3);
          out.write("\r\n");
          out.write("\t\t\t\t\t\t\t");
          //  shiro:hasPermission
          org.apache.shiro.web.tags.HasPermissionTag _jspx_th_shiro_005fhasPermission_005f4 = (org.apache.shiro.web.tags.HasPermissionTag) _005fjspx_005ftagPool_005fshiro_005fhasPermission_0026_005fname.get(org.apache.shiro.web.tags.HasPermissionTag.class);
          _jspx_th_shiro_005fhasPermission_005f4.setPageContext(_jspx_page_context);
          _jspx_th_shiro_005fhasPermission_005f4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_shiro_005fhasAnyRoles_005f1);
          // /WEB-INF/views/common/config/top.jsp(64,7) name = name type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
          _jspx_th_shiro_005fhasPermission_005f4.setName("/permission/index.shtml");
          int _jspx_eval_shiro_005fhasPermission_005f4 = _jspx_th_shiro_005fhasPermission_005f4.doStartTag();
          if (_jspx_eval_shiro_005fhasPermission_005f4 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n");
              out.write("\t\t\t\t\t\t\t\t<li><a href=\"");
              out.print(basePath);
              out.write("/permission/index.shtml\">权限列表</a></li>\r\n");
              out.write("\t\t\t\t\t\t\t");
              int evalDoAfterBody = _jspx_th_shiro_005fhasPermission_005f4.doAfterBody();
              if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                break;
            } while (true);
          }
          if (_jspx_th_shiro_005fhasPermission_005f4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _005fjspx_005ftagPool_005fshiro_005fhasPermission_0026_005fname.reuse(_jspx_th_shiro_005fhasPermission_005f4);
            return;
          }
          _005fjspx_005ftagPool_005fshiro_005fhasPermission_0026_005fname.reuse(_jspx_th_shiro_005fhasPermission_005f4);
          out.write("\r\n");
          out.write("\t\t\t\t\t\t\t");
          //  shiro:hasPermission
          org.apache.shiro.web.tags.HasPermissionTag _jspx_th_shiro_005fhasPermission_005f5 = (org.apache.shiro.web.tags.HasPermissionTag) _005fjspx_005ftagPool_005fshiro_005fhasPermission_0026_005fname.get(org.apache.shiro.web.tags.HasPermissionTag.class);
          _jspx_th_shiro_005fhasPermission_005f5.setPageContext(_jspx_page_context);
          _jspx_th_shiro_005fhasPermission_005f5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_shiro_005fhasAnyRoles_005f1);
          // /WEB-INF/views/common/config/top.jsp(67,7) name = name type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
          _jspx_th_shiro_005fhasPermission_005f5.setName("/permission/allocation.shtml");
          int _jspx_eval_shiro_005fhasPermission_005f5 = _jspx_th_shiro_005fhasPermission_005f5.doStartTag();
          if (_jspx_eval_shiro_005fhasPermission_005f5 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n");
              out.write("\t\t\t\t\t\t\t\t<li><a href=\"");
              out.print(basePath);
              out.write("/permission/allocation.shtml\">权限分配</a></li>\r\n");
              out.write("\t\t\t\t\t\t\t");
              int evalDoAfterBody = _jspx_th_shiro_005fhasPermission_005f5.doAfterBody();
              if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                break;
            } while (true);
          }
          if (_jspx_th_shiro_005fhasPermission_005f5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _005fjspx_005ftagPool_005fshiro_005fhasPermission_0026_005fname.reuse(_jspx_th_shiro_005fhasPermission_005f5);
            return;
          }
          _005fjspx_005ftagPool_005fshiro_005fhasPermission_0026_005fname.reuse(_jspx_th_shiro_005fhasPermission_005f5);
          out.write("\r\n");
          out.write("\t\t\t\t\t\t</ul>\r\n");
          out.write("\t\t\t\t\t</li>\t\r\n");
          out.write("\t\t\t\t");
          int evalDoAfterBody = _jspx_th_shiro_005fhasAnyRoles_005f1.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_shiro_005fhasAnyRoles_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _005fjspx_005ftagPool_005fshiro_005fhasAnyRoles_0026_005fname.reuse(_jspx_th_shiro_005fhasAnyRoles_005f1);
        return;
      }
      _005fjspx_005ftagPool_005fshiro_005fhasAnyRoles_0026_005fname.reuse(_jspx_th_shiro_005fhasAnyRoles_005f1);
      out.write("    \r\n");
      out.write("\t\t\t\t<li>\r\n");
      out.write("\t\t\t\t\t<a class=\"dropdown-toggle\" target=\"_blank\" href=\"http://www.sojson.com/tag_shiro.html\" target=\"_blank\">\r\n");
      out.write("\t\t\t\t\t\tShiro相关博客<span class=\"collapsing\"></span>\r\n");
      out.write("\t\t\t\t\t</a>\r\n");
      out.write("\t\t\t\t</li>\t          \r\n");
      out.write("\t\t\t\t<li>\r\n");
      out.write("\t\t\t\t\t<a class=\"dropdown-toggle\" href=\"http://www.sojson.com/shiro\" target=\"_blank\">\r\n");
      out.write("\t\t\t\t\t\t本项目介绍<span class=\"collapsing\"></span>\r\n");
      out.write("\t\t\t\t\t</a>\r\n");
      out.write("\t\t\t\t</li>\t          \r\n");
      out.write("\t\t\t\t<li>\r\n");
      out.write("\t\t\t\t\t<a class=\"dropdown-toggle\" href=\"http://www.sojson.com/jc/shiro.html\" target=\"_blank\">\r\n");
      out.write("\t\t\t\t\t\tShiro Demo 其他版本<span class=\"collapsing\"></span>\r\n");
      out.write("\t\t\t\t\t</a>\r\n");
      out.write("\t\t\t\t</li>\t          \r\n");
      out.write("\t          </ul>\r\n");
      out.write("\t           <ul class=\"nav navbar-nav  pull-right\" >\r\n");
      out.write("\t\t\t\t<li class=\"dropdown \" style=\"color:#fff;\">\r\n");
      out.write("\t\t\t\t\t");
      out.write("\r\n");
      out.write("\t\t\t\t\t");
      //  shiro:user
      org.apache.shiro.web.tags.UserTag _jspx_th_shiro_005fuser_005f0 = (org.apache.shiro.web.tags.UserTag) _005fjspx_005ftagPool_005fshiro_005fuser.get(org.apache.shiro.web.tags.UserTag.class);
      _jspx_th_shiro_005fuser_005f0.setPageContext(_jspx_page_context);
      _jspx_th_shiro_005fuser_005f0.setParent(null);
      int _jspx_eval_shiro_005fuser_005f0 = _jspx_th_shiro_005fuser_005f0.doStartTag();
      if (_jspx_eval_shiro_005fuser_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("  \r\n");
          out.write("\t\t\t\t\t\t<a aria-expanded=\"false\" aria-haspopup=\"true\"  role=\"button\" data-toggle=\"dropdown\" onclick=\"location.href='");
          out.print(basePath);
          out.write("/user/index.shtml'\" href=\"");
          out.print(basePath);
          out.write("/user/index.shtml\" class=\"dropdown-toggle qqlogin\" > \r\n");
          out.write("\t\t\t\t\t\t");
          if (_jspx_meth_shiro_005fprincipal_005f0(_jspx_th_shiro_005fuser_005f0, _jspx_page_context))
            return;
          out.write("\r\n");
          out.write("\t\t\t\t\t\t<span class=\"caret\"></span></a>\r\n");
          out.write("\t\t\t\t\t\t<ul class=\"dropdown-menu\" userid=\"");
          if (_jspx_meth_shiro_005fprincipal_005f1(_jspx_th_shiro_005fuser_005f0, _jspx_page_context))
            return;
          out.write("\">\r\n");
          out.write("\t\t\t\t\t\t\t<li><a href=\"");
          out.print(basePath);
          out.write("/user/index.shtml\">个人资料</a></li>\r\n");
          out.write("\t\t\t\t\t\t\t<li><a href=\"");
          out.print(basePath);
          out.write("/role/mypermission.shtml\">我的权限</a></li>\r\n");
          out.write("\t\t\t\t\t\t\t<li><a href=\"javascript:void(0);\" onclick=\"logout();\">退出登录</a></li>\r\n");
          out.write("\t\t\t\t\t\t</ul>\r\n");
          out.write("\t\t\t\t\t");
          int evalDoAfterBody = _jspx_th_shiro_005fuser_005f0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_shiro_005fuser_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _005fjspx_005ftagPool_005fshiro_005fuser.reuse(_jspx_th_shiro_005fuser_005f0);
        return;
      }
      _005fjspx_005ftagPool_005fshiro_005fuser.reuse(_jspx_th_shiro_005fuser_005f0);
      out.write("   \r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t");
      out.write("\r\n");
      out.write("\t\t\t\t\t");
      if (_jspx_meth_shiro_005fguest_005f0(_jspx_page_context))
        return;
      out.write("  \r\n");
      out.write("\t\t\t\t</li>\t\r\n");
      out.write("\t          </ul>\r\n");
      out.write("\t          <style>#topMenu>li>a{padding:10px 13px}</style>\r\n");
      out.write("\t    </div>\r\n");
      out.write("  \t</div>\r\n");
      out.write("</div>\r\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }

  private boolean _jspx_meth_shiro_005fprincipal_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_shiro_005fuser_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  shiro:principal
    org.apache.shiro.web.tags.PrincipalTag _jspx_th_shiro_005fprincipal_005f0 = (org.apache.shiro.web.tags.PrincipalTag) _005fjspx_005ftagPool_005fshiro_005fprincipal_0026_005fproperty_005fnobody.get(org.apache.shiro.web.tags.PrincipalTag.class);
    _jspx_th_shiro_005fprincipal_005f0.setPageContext(_jspx_page_context);
    _jspx_th_shiro_005fprincipal_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_shiro_005fuser_005f0);
    // /WEB-INF/views/common/config/top.jsp(94,6) name = property type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_shiro_005fprincipal_005f0.setProperty("nickname");
    int _jspx_eval_shiro_005fprincipal_005f0 = _jspx_th_shiro_005fprincipal_005f0.doStartTag();
    if (_jspx_th_shiro_005fprincipal_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fshiro_005fprincipal_0026_005fproperty_005fnobody.reuse(_jspx_th_shiro_005fprincipal_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fshiro_005fprincipal_0026_005fproperty_005fnobody.reuse(_jspx_th_shiro_005fprincipal_005f0);
    return false;
  }

  private boolean _jspx_meth_shiro_005fprincipal_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_shiro_005fuser_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  shiro:principal
    org.apache.shiro.web.tags.PrincipalTag _jspx_th_shiro_005fprincipal_005f1 = (org.apache.shiro.web.tags.PrincipalTag) _005fjspx_005ftagPool_005fshiro_005fprincipal_0026_005fproperty_005fnobody.get(org.apache.shiro.web.tags.PrincipalTag.class);
    _jspx_th_shiro_005fprincipal_005f1.setPageContext(_jspx_page_context);
    _jspx_th_shiro_005fprincipal_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_shiro_005fuser_005f0);
    // /WEB-INF/views/common/config/top.jsp(96,40) name = property type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_shiro_005fprincipal_005f1.setProperty("id");
    int _jspx_eval_shiro_005fprincipal_005f1 = _jspx_th_shiro_005fprincipal_005f1.doStartTag();
    if (_jspx_th_shiro_005fprincipal_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fshiro_005fprincipal_0026_005fproperty_005fnobody.reuse(_jspx_th_shiro_005fprincipal_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fshiro_005fprincipal_0026_005fproperty_005fnobody.reuse(_jspx_th_shiro_005fprincipal_005f1);
    return false;
  }

  private boolean _jspx_meth_shiro_005fguest_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  shiro:guest
    org.apache.shiro.web.tags.GuestTag _jspx_th_shiro_005fguest_005f0 = (org.apache.shiro.web.tags.GuestTag) _005fjspx_005ftagPool_005fshiro_005fguest.get(org.apache.shiro.web.tags.GuestTag.class);
    _jspx_th_shiro_005fguest_005f0.setPageContext(_jspx_page_context);
    _jspx_th_shiro_005fguest_005f0.setParent(null);
    int _jspx_eval_shiro_005fguest_005f0 = _jspx_th_shiro_005fguest_005f0.doStartTag();
    if (_jspx_eval_shiro_005fguest_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("  \r\n");
        out.write("\t\t\t\t\t\t <a aria-expanded=\"false\" aria-haspopup=\"true\"  role=\"button\" data-toggle=\"dropdown\"  href=\"javascript:void(0);\" class=\"dropdown-toggle qqlogin\" >\r\n");
        out.write("\t\t\t\t\t\t<img src=\"//qzonestyle.gtimg.cn/qzone/vas/opensns/res/img/Connect_logo_1.png\">&nbsp;登录</a>\r\n");
        out.write("\t\t\t\t\t");
        int evalDoAfterBody = _jspx_th_shiro_005fguest_005f0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_shiro_005fguest_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fshiro_005fguest.reuse(_jspx_th_shiro_005fguest_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fshiro_005fguest.reuse(_jspx_th_shiro_005fguest_005f0);
    return false;
  }
}
