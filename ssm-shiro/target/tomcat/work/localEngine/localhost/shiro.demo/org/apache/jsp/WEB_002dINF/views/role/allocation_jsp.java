package org.apache.jsp.WEB_002dINF.views.role;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;

public final class allocation_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

static private org.apache.jasper.runtime.ProtectedFunctionMapper _jspx_fnmap_0;

static {
  _jspx_fnmap_0= org.apache.jasper.runtime.ProtectedFunctionMapper.getMapForFunction("fn:length", org.apache.taglibs.standard.functions.Functions.class, "length", new Class[] {java.lang.Object.class});
}

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fshiro_005fhasPermission_0026_005fname;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fshiro_005fhasAnyRoles_0026_005fname;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fshiro_005fhasPermission_0026_005fname = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fshiro_005fhasAnyRoles_0026_005fname = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fc_005fchoose = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fc_005fotherwise = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fshiro_005fhasPermission_0026_005fname.release();
    _005fjspx_005ftagPool_005fshiro_005fhasAnyRoles_0026_005fname.release();
    _005fjspx_005ftagPool_005fc_005fchoose.release();
    _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
    _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.release();
    _005fjspx_005ftagPool_005fc_005fotherwise.release();
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
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

      out.write("\r\n");
      out.write("\r\n");
      out.write(" \r\n");
      out.write("\r\n");
      out.write("  \r\n");

String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;

      out.write("\r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html lang=\"zh-cn\">\r\n");
      out.write("\t<head>\r\n");
      out.write("\t\t<meta charset=\"utf-8\" />\r\n");
      out.write("\t\t");
      out.write("\r\n");
      out.write("\t\t<base href=\"");
      out.print(basePath);
      out.write("\"/>\r\n");
      out.write("\t\t<title>用户角色分配 - 权限管理</title>\r\n");
      out.write("    \t<meta content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no\" name=\"viewport\" />\r\n");
      out.write("\t\t<link   rel=\"icon\" href=\"https://open.itboy.net/favicon.ico\" type=\"image/x-icon\" />\r\n");
      out.write("\t\t<link   rel=\"shortcut icon\" href=\"http://img.wenyifan.net/images/favicon.ico\" />\r\n");
      out.write("\t\t<link href=\"");
      out.print(basePath);
      out.write("/js/common/bootstrap/3.3.5/css/bootstrap.min.css?");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${_v}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\" rel=\"stylesheet\"/>\r\n");
      out.write("\t\t<link href=\"");
      out.print(basePath);
      out.write("/css/common/base.css?");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${_v}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\" rel=\"stylesheet\"/>\r\n");
      out.write("\t\t<script  src=\"http://open.itboy.net/common/jquery/jquery1.8.3.min.js\"></script>\r\n");
      out.write("\t\t<script  src=\"");
      out.print(basePath);
      out.write("/js/common/layer/layer.js\"></script>\r\n");
      out.write("\t\t<script  src=\"");
      out.print(basePath);
      out.write("/js/common/bootstrap/3.3.5/js/bootstrap.min.js\"></script>\r\n");
      out.write("\t\t<script  src=\"");
      out.print(basePath);
      out.write("/js/shiro.demo.js\"></script>\r\n");
      out.write("\t\t<script >\r\n");
      out.write("\t\tso.init(function(){\r\n");
      out.write("\t\t\t\t//初始化全选。\r\n");
      out.write("\t\t\t\tso.checkBoxInit('#checkAll','[check=box]');\r\n");
      out.write("\t\t\t\t");
      if (_jspx_meth_shiro_005fhasPermission_005f0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t\t");
      //  shiro:hasPermission
      org.apache.shiro.web.tags.HasPermissionTag _jspx_th_shiro_005fhasPermission_005f1 = (org.apache.shiro.web.tags.HasPermissionTag) _005fjspx_005ftagPool_005fshiro_005fhasPermission_0026_005fname.get(org.apache.shiro.web.tags.HasPermissionTag.class);
      _jspx_th_shiro_005fhasPermission_005f1.setPageContext(_jspx_page_context);
      _jspx_th_shiro_005fhasPermission_005f1.setParent(null);
      // /WEB-INF/views/role/allocation.jsp(46,3) name = name type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_shiro_005fhasPermission_005f1.setName("/role/clearRoleByUserIds.shtml");
      int _jspx_eval_shiro_005fhasPermission_005f1 = _jspx_th_shiro_005fhasPermission_005f1.doStartTag();
      if (_jspx_eval_shiro_005fhasPermission_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n");
          out.write("\t\t\t");
          out.write("\r\n");
          out.write("\t\t\tfunction deleteById(ids){\r\n");
          out.write("\t\t\t\tvar index = layer.confirm(\"确定清除这\"+ ids.length +\"个用户的角色？\",function(){\r\n");
          out.write("\t\t\t\t\tvar load = layer.load();\r\n");
          out.write("\t\t\t\t\t$.post('");
          out.print(basePath);
          out.write("/role/clearRoleByUserIds.shtml',{userIds:ids.join(',')},function(result){\r\n");
          out.write("\t\t\t\t\t\tlayer.close(load);\r\n");
          out.write("\t\t\t\t\t\tif(result && result.status != 200){\r\n");
          out.write("\t\t\t\t\t\t\treturn layer.msg(result.message,so.default),!0;\r\n");
          out.write("\t\t\t\t\t\t}else{\r\n");
          out.write("\t\t\t\t\t\t\tlayer.msg(result.message);\r\n");
          out.write("\t\t\t\t\t\t\tsetTimeout(function(){\r\n");
          out.write("\t\t\t\t\t\t\t\t$('#formId').submit();\r\n");
          out.write("\t\t\t\t\t\t\t},1000);\r\n");
          out.write("\t\t\t\t\t\t}\r\n");
          out.write("\t\t\t\t\t},'json');\r\n");
          out.write("\t\t\t\t\tlayer.close(index);\r\n");
          out.write("\t\t\t\t});\r\n");
          out.write("\t\t\t}\r\n");
          out.write("\t\t\t");
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
      out.write("\t\t\t");
      //  shiro:hasPermission
      org.apache.shiro.web.tags.HasPermissionTag _jspx_th_shiro_005fhasPermission_005f2 = (org.apache.shiro.web.tags.HasPermissionTag) _005fjspx_005ftagPool_005fshiro_005fhasPermission_0026_005fname.get(org.apache.shiro.web.tags.HasPermissionTag.class);
      _jspx_th_shiro_005fhasPermission_005f2.setPageContext(_jspx_page_context);
      _jspx_th_shiro_005fhasPermission_005f2.setParent(null);
      // /WEB-INF/views/role/allocation.jsp(66,3) name = name type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_shiro_005fhasPermission_005f2.setName("/role/addRole2User.shtml");
      int _jspx_eval_shiro_005fhasPermission_005f2 = _jspx_th_shiro_005fhasPermission_005f2.doStartTag();
      if (_jspx_eval_shiro_005fhasPermission_005f2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n");
          out.write("\t\t\t");
          out.write("\r\n");
          out.write("\t\t\tfunction selectRole(){\r\n");
          out.write("\t\t\t\tvar checked = $(\"#boxRoleForm  :checked\");\r\n");
          out.write("\t\t\t\tvar ids=[],names=[];\r\n");
          out.write("\t\t\t\t$.each(checked,function(){\r\n");
          out.write("\t\t\t\t\tids.push(this.id);\r\n");
          out.write("\t\t\t\t\tnames.push($.trim($(this).attr('name')));\r\n");
          out.write("\t\t\t\t});\r\n");
          out.write("\t\t\t\tvar index = layer.confirm(\"确定操作？\",function(){\r\n");
          out.write("\t\t\t\t\t");
          out.write("\r\n");
          out.write("\t\t\t\t\tvar load = layer.load();\r\n");
          out.write("\t\t\t\t\t$.post('");
          out.print(basePath);
          out.write("/role/addRole2User.shtml',{ids:ids.join(','),userId:$('#selectUserId').val()},function(result){\r\n");
          out.write("\t\t\t\t\t\tlayer.close(load);\r\n");
          out.write("\t\t\t\t\t\tif(result && result.status != 200){\r\n");
          out.write("\t\t\t\t\t\t\treturn layer.msg(result.message,so.default),!1;\r\n");
          out.write("\t\t\t\t\t\t}\r\n");
          out.write("\t\t\t\t\t\tlayer.msg('添加成功。');\r\n");
          out.write("\t\t\t\t\t\tsetTimeout(function(){\r\n");
          out.write("\t\t\t\t\t\t\t$('#formId').submit();\r\n");
          out.write("\t\t\t\t\t\t},1000);\r\n");
          out.write("\t\t\t\t\t},'json');\r\n");
          out.write("\t\t\t\t});\r\n");
          out.write("\t\t\t}\r\n");
          out.write("\t\t\t/*\r\n");
          out.write("\t\t\t*根据角色ID选择权限，分配权限操作。\r\n");
          out.write("\t\t\t*/\r\n");
          out.write("\t\t\tfunction selectRoleById(id){\r\n");
          out.write("\t\t\t\tvar load = layer.load();\r\n");
          out.write("\t\t\t\t$.post(\"");
          out.print(basePath);
          out.write("/role/selectRoleByUserId.shtml\",{id:id},function(result){\r\n");
          out.write("\t\t\t\t\tlayer.close(load);\r\n");
          out.write("\t\t\t\t\tif(result && result.length){\r\n");
          out.write("\t\t\t\t\t\tvar html =[];\r\n");
          out.write("\t\t\t\t\t\t$.each(result,function(){\r\n");
          out.write("\t\t\t\t\t\t\thtml.push(\"<div class='checkbox'><label>\");\r\n");
          out.write("\t\t\t\t\t\t\thtml.push(\"<input type='checkbox' id='\");\r\n");
          out.write("\t\t\t\t\t\t\thtml.push(this.id);\r\n");
          out.write("\t\t\t\t\t\t\thtml.push(\"'\");\r\n");
          out.write("\t\t\t\t\t\t\tif(this.check){\r\n");
          out.write("\t\t\t\t\t\t\t\thtml.push(\" checked='checked'\");\r\n");
          out.write("\t\t\t\t\t\t\t}\r\n");
          out.write("\t\t\t\t\t\t\thtml.push(\"name='\");\r\n");
          out.write("\t\t\t\t\t\t\thtml.push(this.name);\r\n");
          out.write("\t\t\t\t\t\t\thtml.push(\"'/>\");\r\n");
          out.write("\t\t\t\t\t\t\thtml.push(this.name);\r\n");
          out.write("\t\t\t\t\t\t\thtml.push('</label></div>');\r\n");
          out.write("\t\t\t\t\t\t});\r\n");
          out.write("\t\t\t\t\t\treturn so.id('boxRoleForm').html(html.join('')) & $('#selectRole').modal(),$('#selectUserId').val(id),!1;\r\n");
          out.write("\t\t\t\t\t}else{\r\n");
          out.write("\t\t\t\t\t\treturn layer.msg(result.message,so.default);\r\n");
          out.write("\t\t\t\t\t}\r\n");
          out.write("\t\t\t\t},'json');\r\n");
          out.write("\t\t\t}\r\n");
          out.write("\t\t\t");
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
      out.write("\t\t</script>\r\n");
      out.write("\t</head>\r\n");
      out.write("\t<body data-target=\"#one\" data-spy=\"scroll\">\r\n");
      out.write("\t\t");
      out.write("\r\n");
      out.write("\t\t");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "../common/config/top.jsp", out, false);
      out.write("\r\n");
      out.write("\t\t<div class=\"container\" style=\"padding-bottom: 15px;min-height: 300px; margin-top: 40px;\">\r\n");
      out.write("\t\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t\t");
      out.write("\r\n");
      out.write("\t\t\t\t");
      //  shiro:hasAnyRoles
      org.apache.shiro.web.tags.HasAnyRolesTag _jspx_th_shiro_005fhasAnyRoles_005f0 = (org.apache.shiro.web.tags.HasAnyRolesTag) _005fjspx_005ftagPool_005fshiro_005fhasAnyRoles_0026_005fname.get(org.apache.shiro.web.tags.HasAnyRolesTag.class);
      _jspx_th_shiro_005fhasAnyRoles_005f0.setPageContext(_jspx_page_context);
      _jspx_th_shiro_005fhasAnyRoles_005f0.setParent(null);
      // /WEB-INF/views/role/allocation.jsp(128,4) name = name type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_shiro_005fhasAnyRoles_005f0.setName("888888,100003");
      int _jspx_eval_shiro_005fhasAnyRoles_005f0 = _jspx_th_shiro_005fhasAnyRoles_005f0.doStartTag();
      if (_jspx_eval_shiro_005fhasAnyRoles_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("  \r\n");
          out.write("\t\t\t\t\t<div id=\"one\" class=\"col-md-2\">\r\n");
          out.write("\t\t\t\t\t\t<ul data-spy=\"affix\" class=\"nav nav-list nav-tabs nav-stacked bs-docs-sidenav dropdown affix\" style=\"top: 100px; z-index: 100;\">\r\n");
          out.write("\t\t\t\t\t\t ");
          //  shiro:hasPermission
          org.apache.shiro.web.tags.HasPermissionTag _jspx_th_shiro_005fhasPermission_005f3 = (org.apache.shiro.web.tags.HasPermissionTag) _005fjspx_005ftagPool_005fshiro_005fhasPermission_0026_005fname.get(org.apache.shiro.web.tags.HasPermissionTag.class);
          _jspx_th_shiro_005fhasPermission_005f3.setPageContext(_jspx_page_context);
          _jspx_th_shiro_005fhasPermission_005f3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_shiro_005fhasAnyRoles_005f0);
          // /WEB-INF/views/role/allocation.jsp(131,7) name = name type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
          _jspx_th_shiro_005fhasPermission_005f3.setName("/role/index.shtml");
          int _jspx_eval_shiro_005fhasPermission_005f3 = _jspx_th_shiro_005fhasPermission_005f3.doStartTag();
          if (_jspx_eval_shiro_005fhasPermission_005f3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n");
              out.write("\t\t\t\t\t\t  <li class=\"\">\r\n");
              out.write("\t\t\t\t\t\t      <a href=\"");
              out.print(basePath);
              out.write("/role/index.shtml\">\r\n");
              out.write("\t\t\t\t\t\t    \t <i class=\"glyphicon glyphicon-chevron-right\"></i>角色列表\r\n");
              out.write("\t\t\t\t\t\t      </a>\r\n");
              out.write("\t\t\t\t\t\t  </li>\r\n");
              out.write("\t\t\t\t\t\t  ");
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
          out.write("\t\t\t\t\t\t ");
          //  shiro:hasPermission
          org.apache.shiro.web.tags.HasPermissionTag _jspx_th_shiro_005fhasPermission_005f4 = (org.apache.shiro.web.tags.HasPermissionTag) _005fjspx_005ftagPool_005fshiro_005fhasPermission_0026_005fname.get(org.apache.shiro.web.tags.HasPermissionTag.class);
          _jspx_th_shiro_005fhasPermission_005f4.setPageContext(_jspx_page_context);
          _jspx_th_shiro_005fhasPermission_005f4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_shiro_005fhasAnyRoles_005f0);
          // /WEB-INF/views/role/allocation.jsp(138,7) name = name type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
          _jspx_th_shiro_005fhasPermission_005f4.setName("/role/allocation.shtml");
          int _jspx_eval_shiro_005fhasPermission_005f4 = _jspx_th_shiro_005fhasPermission_005f4.doStartTag();
          if (_jspx_eval_shiro_005fhasPermission_005f4 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n");
              out.write("\t\t\t\t\t\t  <li class=\"active dropdown\">\r\n");
              out.write("\t\t\t\t\t\t      <a href=\"");
              out.print(basePath);
              out.write("/role/allocation.shtml\">\r\n");
              out.write("\t\t\t\t\t\t    \t <i class=\"glyphicon glyphicon-chevron-right\"></i>角色分配\r\n");
              out.write("\t\t\t\t\t\t      </a>\r\n");
              out.write("\t\t\t\t\t\t  </li>\r\n");
              out.write("\t\t\t\t\t\t  ");
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
          out.write("\t\t\t\t\t\t  ");
          //  shiro:hasPermission
          org.apache.shiro.web.tags.HasPermissionTag _jspx_th_shiro_005fhasPermission_005f5 = (org.apache.shiro.web.tags.HasPermissionTag) _005fjspx_005ftagPool_005fshiro_005fhasPermission_0026_005fname.get(org.apache.shiro.web.tags.HasPermissionTag.class);
          _jspx_th_shiro_005fhasPermission_005f5.setPageContext(_jspx_page_context);
          _jspx_th_shiro_005fhasPermission_005f5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_shiro_005fhasAnyRoles_005f0);
          // /WEB-INF/views/role/allocation.jsp(145,8) name = name type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
          _jspx_th_shiro_005fhasPermission_005f5.setName("/permission/index.shtml");
          int _jspx_eval_shiro_005fhasPermission_005f5 = _jspx_th_shiro_005fhasPermission_005f5.doStartTag();
          if (_jspx_eval_shiro_005fhasPermission_005f5 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n");
              out.write("\t\t\t\t\t\t  <li class=\" dropdown\">\r\n");
              out.write("\t\t\t\t\t\t      <a href=\"");
              out.print(basePath);
              out.write("/permission/index.shtml\">\r\n");
              out.write("\t\t\t\t\t\t    \t <i class=\"glyphicon glyphicon-chevron-right\"></i>权限列表\r\n");
              out.write("\t\t\t\t\t\t      </a>\r\n");
              out.write("\t\t\t\t\t\t  </li>\r\n");
              out.write("\t\t\t\t\t\t  ");
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
          out.write("\t\t\t\t\t\t  ");
          //  shiro:hasPermission
          org.apache.shiro.web.tags.HasPermissionTag _jspx_th_shiro_005fhasPermission_005f6 = (org.apache.shiro.web.tags.HasPermissionTag) _005fjspx_005ftagPool_005fshiro_005fhasPermission_0026_005fname.get(org.apache.shiro.web.tags.HasPermissionTag.class);
          _jspx_th_shiro_005fhasPermission_005f6.setPageContext(_jspx_page_context);
          _jspx_th_shiro_005fhasPermission_005f6.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_shiro_005fhasAnyRoles_005f0);
          // /WEB-INF/views/role/allocation.jsp(152,8) name = name type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
          _jspx_th_shiro_005fhasPermission_005f6.setName("/permission/allocation.shtml");
          int _jspx_eval_shiro_005fhasPermission_005f6 = _jspx_th_shiro_005fhasPermission_005f6.doStartTag();
          if (_jspx_eval_shiro_005fhasPermission_005f6 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n");
              out.write("\t\t\t\t\t\t  <li class=\"  dropdown\">\r\n");
              out.write("\t\t\t\t\t\t      <a href=\"");
              out.print(basePath);
              out.write("/permission/allocation.shtml\">\r\n");
              out.write("\t\t\t\t\t\t    \t <i class=\"glyphicon glyphicon-chevron-right\"></i>权限分配\r\n");
              out.write("\t\t\t\t\t\t      </a>\r\n");
              out.write("\t\t\t\t\t\t  </li>\r\n");
              out.write("\t\t\t\t\t\t  ");
              int evalDoAfterBody = _jspx_th_shiro_005fhasPermission_005f6.doAfterBody();
              if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                break;
            } while (true);
          }
          if (_jspx_th_shiro_005fhasPermission_005f6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _005fjspx_005ftagPool_005fshiro_005fhasPermission_0026_005fname.reuse(_jspx_th_shiro_005fhasPermission_005f6);
            return;
          }
          _005fjspx_005ftagPool_005fshiro_005fhasPermission_0026_005fname.reuse(_jspx_th_shiro_005fhasPermission_005f6);
          out.write("\r\n");
          out.write("\t\t\t\t\t\t</ul>\r\n");
          out.write("\t\t\t\t\t</div>\r\n");
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
      out.write("  \r\n");
      out.write("\t\t\t\t<div class=\"col-md-10\">\r\n");
      out.write("\t\t\t\t\t<h2>用户角色分配</h2>\r\n");
      out.write("\t\t\t\t\t<hr>\r\n");
      out.write("\t\t\t\t\t<form method=\"post\" action=\"\" id=\"formId\" class=\"form-inline\">\r\n");
      out.write("\t\t\t\t\t\t<div clss=\"well\">\r\n");
      out.write("\t\t\t\t\t      <div class=\"form-group\">\r\n");
      out.write("\t\t\t\t\t        <input type=\"text\" class=\"form-control\" style=\"width: 300px;\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${findContent}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\" \r\n");
      out.write("\t\t\t\t\t        \t\t\tname=\"findContent\" id=\"findContent\" placeholder=\"输入用户昵称 / 用户帐号\">\r\n");
      out.write("\t\t\t\t\t      </div>\r\n");
      out.write("\t\t\t\t\t     <span class=\"\"> ");
      out.write("\r\n");
      out.write("\t\t\t\t         \t<button type=\"submit\" class=\"btn btn-primary\">查询</button>\r\n");
      out.write("\t\t\t\t         \t");
      if (_jspx_meth_shiro_005fhasPermission_005f7(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t         </span>    \r\n");
      out.write("\t\t\t\t        </div>\r\n");
      out.write("\t\t\t\t\t<hr>\r\n");
      out.write("\t\t\t\t\t<table class=\"table table-bordered\">\r\n");
      out.write("\t\t\t\t\t\t<input type=\"hidden\" id=\"selectUserId\">\r\n");
      out.write("\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t<th width=\"5%\"><input type=\"checkbox\" id=\"checkAll\"/></th>\r\n");
      out.write("\t\t\t\t\t\t\t<th width=\"10%\">用户昵称</th>\r\n");
      out.write("\t\t\t\t\t\t\t<th width=\"10%\">Email/帐号</th>\r\n");
      out.write("\t\t\t\t\t\t\t<th width=\"10%\">状态</th>\r\n");
      out.write("\t\t\t\t\t\t\t<th width=\"55%\">拥有的角色</th>\r\n");
      out.write("\t\t\t\t\t\t\t<th width=\"10%\">操作</th>\r\n");
      out.write("\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t");
      if (_jspx_meth_c_005fchoose_005f0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t");
      if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t</form>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>");
      out.write("\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t");
      out.write("\r\n");
      out.write("\t\t\t<div class=\"modal fade bs-example-modal-sm\"  id=\"selectRole\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"selectRoleLabel\">\r\n");
      out.write("\t\t\t  <div class=\"modal-dialog modal-sm\" role=\"document\">\r\n");
      out.write("\t\t\t    <div class=\"modal-content\">\r\n");
      out.write("\t\t\t      <div class=\"modal-header\">\r\n");
      out.write("\t\t\t        <button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Close\"><span aria-hidden=\"true\">&times;</span></button>\r\n");
      out.write("\t\t\t        <h4 class=\"modal-title\" id=\"selectRoleLabel\">添加角色</h4>\r\n");
      out.write("\t\t\t      </div>\r\n");
      out.write("\t\t\t      <div class=\"modal-body\">\r\n");
      out.write("\t\t\t        <form id=\"boxRoleForm\">\r\n");
      out.write("\t\t\t          loading...\r\n");
      out.write("\t\t\t        </form>\r\n");
      out.write("\t\t\t      </div>\r\n");
      out.write("\t\t\t      <div class=\"modal-footer\">\r\n");
      out.write("\t\t\t        <button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">Close</button>\r\n");
      out.write("\t\t\t        <button type=\"button\" onclick=\"selectRole();\" class=\"btn btn-primary\">Save</button>\r\n");
      out.write("\t\t\t      </div>\r\n");
      out.write("\t\t\t    </div>\r\n");
      out.write("\t\t\t  </div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t");
      out.write("\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t</body>\r\n");
      out.write("</html>");
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

  private boolean _jspx_meth_shiro_005fhasPermission_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  shiro:hasPermission
    org.apache.shiro.web.tags.HasPermissionTag _jspx_th_shiro_005fhasPermission_005f0 = (org.apache.shiro.web.tags.HasPermissionTag) _005fjspx_005ftagPool_005fshiro_005fhasPermission_0026_005fname.get(org.apache.shiro.web.tags.HasPermissionTag.class);
    _jspx_th_shiro_005fhasPermission_005f0.setPageContext(_jspx_page_context);
    _jspx_th_shiro_005fhasPermission_005f0.setParent(null);
    // /WEB-INF/views/role/allocation.jsp(31,4) name = name type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_shiro_005fhasPermission_005f0.setName("/role/clearRoleByUserIds.shtml");
    int _jspx_eval_shiro_005fhasPermission_005f0 = _jspx_th_shiro_005fhasPermission_005f0.doStartTag();
    if (_jspx_eval_shiro_005fhasPermission_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("\t\t\t\t//全选\r\n");
        out.write("\t\t\t\tso.id('deleteAll').on('click',function(){\r\n");
        out.write("\t\t\t\t\tvar checkeds = $('[check=box]:checked');\r\n");
        out.write("\t\t\t\t\tif(!checkeds.length){\r\n");
        out.write("\t\t\t\t\t\treturn layer.msg('请选择要清除的用户。',so.default),!0;\r\n");
        out.write("\t\t\t\t\t}\r\n");
        out.write("\t\t\t\t\tvar array = [];\r\n");
        out.write("\t\t\t\t\tcheckeds.each(function(){\r\n");
        out.write("\t\t\t\t\t\tarray.push(this.value);\r\n");
        out.write("\t\t\t\t\t});\r\n");
        out.write("\t\t\t\t\treturn deleteById(array);\r\n");
        out.write("\t\t\t\t});\r\n");
        out.write("\t\t\t\t");
        int evalDoAfterBody = _jspx_th_shiro_005fhasPermission_005f0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_shiro_005fhasPermission_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fshiro_005fhasPermission_0026_005fname.reuse(_jspx_th_shiro_005fhasPermission_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fshiro_005fhasPermission_0026_005fname.reuse(_jspx_th_shiro_005fhasPermission_005f0);
    return false;
  }

  private boolean _jspx_meth_shiro_005fhasPermission_005f7(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  shiro:hasPermission
    org.apache.shiro.web.tags.HasPermissionTag _jspx_th_shiro_005fhasPermission_005f7 = (org.apache.shiro.web.tags.HasPermissionTag) _005fjspx_005ftagPool_005fshiro_005fhasPermission_0026_005fname.get(org.apache.shiro.web.tags.HasPermissionTag.class);
    _jspx_th_shiro_005fhasPermission_005f7.setPageContext(_jspx_page_context);
    _jspx_th_shiro_005fhasPermission_005f7.setParent(null);
    // /WEB-INF/views/role/allocation.jsp(173,14) name = name type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_shiro_005fhasPermission_005f7.setName("/role/clearRoleByUserIds.shtml");
    int _jspx_eval_shiro_005fhasPermission_005f7 = _jspx_th_shiro_005fhasPermission_005f7.doStartTag();
    if (_jspx_eval_shiro_005fhasPermission_005f7 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("\t\t\t\t         \t\t<button type=\"button\" id=\"deleteAll\" class=\"btn  btn-danger\">清空用户角色</button>\r\n");
        out.write("\t\t\t         \t\t");
        int evalDoAfterBody = _jspx_th_shiro_005fhasPermission_005f7.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_shiro_005fhasPermission_005f7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fshiro_005fhasPermission_0026_005fname.reuse(_jspx_th_shiro_005fhasPermission_005f7);
      return true;
    }
    _005fjspx_005ftagPool_005fshiro_005fhasPermission_0026_005fname.reuse(_jspx_th_shiro_005fhasPermission_005f7);
    return false;
  }

  private boolean _jspx_meth_c_005fchoose_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:choose
    org.apache.taglibs.standard.tag.common.core.ChooseTag _jspx_th_c_005fchoose_005f0 = (org.apache.taglibs.standard.tag.common.core.ChooseTag) _005fjspx_005ftagPool_005fc_005fchoose.get(org.apache.taglibs.standard.tag.common.core.ChooseTag.class);
    _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
    _jspx_th_c_005fchoose_005f0.setParent(null);
    int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
    if (_jspx_eval_c_005fchoose_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("\t\t\t\t\t\t\t");
        if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
          return true;
        out.write("\r\n");
        out.write("\t\t\t\t\t\t\t");
        if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
          return true;
        out.write("\r\n");
        out.write("\t\t\t\t\t\t");
        int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fchoose_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
    return false;
  }

  private boolean _jspx_meth_c_005fwhen_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:when
    org.apache.taglibs.standard.tag.rt.core.WhenTag _jspx_th_c_005fwhen_005f0 = (org.apache.taglibs.standard.tag.rt.core.WhenTag) _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.WhenTag.class);
    _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
    _jspx_th_c_005fwhen_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fchoose_005f0);
    // /WEB-INF/views/role/allocation.jsp(190,7) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fwhen_005f0.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${page != null && fn:length(page.list) gt 0}", java.lang.Boolean.class, (PageContext)_jspx_page_context, _jspx_fnmap_0, false)).booleanValue());
    int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
    if (_jspx_eval_c_005fwhen_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("\t\t\t\t\t\t\t\t");
        if (_jspx_meth_c_005fforEach_005f0(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
          return true;
        out.write("\r\n");
        out.write("\t\t\t\t\t\t\t");
        int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fwhen_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
    return false;
  }

  private boolean _jspx_meth_c_005fforEach_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:forEach
    org.apache.taglibs.standard.tag.rt.core.ForEachTag _jspx_th_c_005fforEach_005f0 = (org.apache.taglibs.standard.tag.rt.core.ForEachTag) _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(org.apache.taglibs.standard.tag.rt.core.ForEachTag.class);
    _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
    _jspx_th_c_005fforEach_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fwhen_005f0);
    // /WEB-INF/views/role/allocation.jsp(191,8) name = items type = javax.el.ValueExpression reqTime = true required = false fragment = false deferredValue = true expectedTypeName = java.lang.Object deferredMethod = false methodSignature = null
    _jspx_th_c_005fforEach_005f0.setItems(new org.apache.jasper.el.JspValueExpression("/WEB-INF/views/role/allocation.jsp(191,8) '${page.list}'",_el_expressionfactory.createValueExpression(_jspx_page_context.getELContext(),"${page.list}",java.lang.Object.class)).getValue(_jspx_page_context.getELContext()));
    // /WEB-INF/views/role/allocation.jsp(191,8) name = var type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fforEach_005f0.setVar("it");
    int[] _jspx_push_body_count_c_005fforEach_005f0 = new int[] { 0 };
    try {
      int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
      if (_jspx_eval_c_005fforEach_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n");
          out.write("\t\t\t\t\t\t\t\t\t<tr>\r\n");
          out.write("\t\t\t\t\t\t\t\t\t\t<td><input value=\"");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${it.id}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("\" check='box' type=\"checkbox\" /></td>\r\n");
          out.write("\t\t\t\t\t\t\t\t\t\t<td>");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${it.nickname}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("</td>\r\n");
          out.write("\t\t\t\t\t\t\t\t\t\t<td>");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${it.email}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("</td>\r\n");
          out.write("\t\t\t\t\t\t\t\t\t\t<td>");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${it.status==1?'有效':'禁止'}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("</td>\r\n");
          out.write("\t\t\t\t\t\t\t\t\t\t<td roleIds=\"");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${it.roleIds}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write('"');
          out.write('>');
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${it.roleNames}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("</td>\r\n");
          out.write("\t\t\t\t\t\t\t\t\t\t<td>\r\n");
          out.write("\t\t\t\t\t\t\t\t\t\t\t");
          if (_jspx_meth_shiro_005fhasPermission_005f8(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
            return true;
          out.write("\r\n");
          out.write("\t\t\t\t\t\t\t\t\t\t</td>\r\n");
          out.write("\t\t\t\t\t\t\t\t\t</tr>\r\n");
          out.write("\t\t\t\t\t\t\t\t");
          int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_c_005fforEach_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
    } catch (Throwable _jspx_exception) {
      while (_jspx_push_body_count_c_005fforEach_005f0[0]-- > 0)
        out = _jspx_page_context.popBody();
      _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
    } finally {
      _jspx_th_c_005fforEach_005f0.doFinally();
      _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
    }
    return false;
  }

  private boolean _jspx_meth_shiro_005fhasPermission_005f8(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  shiro:hasPermission
    org.apache.shiro.web.tags.HasPermissionTag _jspx_th_shiro_005fhasPermission_005f8 = (org.apache.shiro.web.tags.HasPermissionTag) _005fjspx_005ftagPool_005fshiro_005fhasPermission_0026_005fname.get(org.apache.shiro.web.tags.HasPermissionTag.class);
    _jspx_th_shiro_005fhasPermission_005f8.setPageContext(_jspx_page_context);
    _jspx_th_shiro_005fhasPermission_005f8.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fforEach_005f0);
    // /WEB-INF/views/role/allocation.jsp(199,11) name = name type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_shiro_005fhasPermission_005f8.setName("/role/addRole2User.shtml");
    int _jspx_eval_shiro_005fhasPermission_005f8 = _jspx_th_shiro_005fhasPermission_005f8.doStartTag();
    if (_jspx_eval_shiro_005fhasPermission_005f8 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t<i class=\"glyphicon glyphicon-share-alt\"></i><a href=\"javascript:selectRoleById(");
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${it.id}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
        out.write(");\">选择角色</a>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t");
        int evalDoAfterBody = _jspx_th_shiro_005fhasPermission_005f8.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_shiro_005fhasPermission_005f8.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fshiro_005fhasPermission_0026_005fname.reuse(_jspx_th_shiro_005fhasPermission_005f8);
      return true;
    }
    _005fjspx_005ftagPool_005fshiro_005fhasPermission_0026_005fname.reuse(_jspx_th_shiro_005fhasPermission_005f8);
    return false;
  }

  private boolean _jspx_meth_c_005fotherwise_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:otherwise
    org.apache.taglibs.standard.tag.common.core.OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (org.apache.taglibs.standard.tag.common.core.OtherwiseTag) _005fjspx_005ftagPool_005fc_005fotherwise.get(org.apache.taglibs.standard.tag.common.core.OtherwiseTag.class);
    _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
    _jspx_th_c_005fotherwise_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fchoose_005f0);
    int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
    if (_jspx_eval_c_005fotherwise_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("\t\t\t\t\t\t\t\t<tr>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t<td class=\"text-center danger\" colspan=\"6\">没有找到用户</td>\r\n");
        out.write("\t\t\t\t\t\t\t\t</tr>\r\n");
        out.write("\t\t\t\t\t\t\t");
        int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fotherwise_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
    return false;
  }

  private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f0 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f0.setParent(null);
    // /WEB-INF/views/role/allocation.jsp(214,5) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fif_005f0.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${page != null && fn:length(page.list) gt 0}", java.lang.Boolean.class, (PageContext)_jspx_page_context, _jspx_fnmap_0, false)).booleanValue());
    int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
    if (_jspx_eval_c_005fif_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("\t\t\t\t\t\t<div class=\"pagination pull-right\">\r\n");
        out.write("\t\t\t\t\t\t\t");
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${page.pageHtml}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
        out.write("\r\n");
        out.write("\t\t\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t\t");
        int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
    return false;
  }
}
