package com.yishu.shiro.web;

import com.yishu.shiro.model.Resource;
import com.yishu.shiro.model.Role;
import com.yishu.shiro.model.User;
import com.yishu.shiro.service.IRoleService;
import com.yishu.shiro.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/4/26.
 */
@Controller
@RequestMapping(value = "/admin/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(Model model){
        model.addAttribute("list",userService.list());
        return "user/list";
    }

    /**
     * 跳转到添加用户的页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public String add(Model model){
        logger.debug("跳转到添加用户的页面");
        model.addAttribute("user",new User());
        model.addAttribute("roles",roleService.list());
        return "user/add";
    }

    /**
     *
     * @param user
     * @param request
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String add(User user, HttpServletRequest request){
        logger.debug("添加用户 post 方法");
        logger.debug(user.toString());
        List<Integer> roleIdList = new ArrayList<>();
        String[] roldIds = request.getParameterValues("roleId");
        //添加用户操作时,如果没有选中角色，则默认添加普通用户user角色.
        if(null==roldIds){
            roldIds=new String[1];
            roldIds[0]="5";//普通用户"user"角色的id为5
        }
        for(String roleId:roldIds){
            roleIdList.add(Integer.parseInt(roleId));
        }
        userService.add(user,roleIdList);

        return "redirect:list";
    }

    @ResponseBody
    @RequestMapping(value = "/updateStatus",method = RequestMethod.POST)
    public Map<String,Object> updateStatus(User user){
        User u = userService.update(user);
        Map<String,Object> result = new HashMap<>();
        if(u != null){
            result.put("success",true);
        }else {
            result.put("success",false);
            result.put("errorInfo","update status failure");
        }
        return result;
    }

    /**
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/update/{id}",method = RequestMethod.GET)
    public String update(@PathVariable("id") Integer id, Model model){

        User user = userService.load(id);
        model.addAttribute("user",user);

        model.addAttribute("roles",roleService.list());

        /**
         *
         */
        List<Role> hasRoles = userService.listUserRole(id);
        /**
         *
         */
        List<Integer> rids = new ArrayList<>();
        for(Role r:hasRoles) {
            rids.add(r.getId());
        }

        model.addAttribute("hasRole", rids);
        return "user/update";
    }

    /**
     * 更新用户的信息（包括更新用户绑定的角色）
     * @param user
     * @return
     */
    @RequestMapping(value = "/update/{id}",method = RequestMethod.POST)
    public String update(User user, HttpServletRequest request){

        logger.debug("user => " + user);
        String[] roleIds = request.getParameterValues("roleId");
        List<Integer> roleIdList = new ArrayList<>();
        for(String roleId:roleIds){
            roleIdList.add(Integer.valueOf(roleId));
        }
        userService.update(user,roleIdList);
        return "redirect:/admin/user/list";
    }

    /**
     *  根据用户 id 跳转到用户权限的列表页面
     * @return
     */
    @RequestMapping(value = "/resources/{id}",method = RequestMethod.GET)
    public String listResources(@PathVariable("id") Integer userId,Model model){
        List<Resource> resourceList = userService.listAllResource(userId);
        User user = userService.load(userId);
        model.addAttribute("resources",resourceList);
        model.addAttribute("user",user);
        return "user/resources";
    }

    /**
     * 批量删除用户
     *
     * 2、删除用户绑定的角色数据
     * @param userIds
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public Map<String,Object> delete(
            @RequestParam("userIds[]") List<Integer> userIds){
        Map<String,Object> result = new HashMap<>();
        try{
            userService.deleteUserAndRole(userIds);
            result.put("success",true);
        }catch (RuntimeException e){
            e.printStackTrace();
            result.put("success",false);
            result.put("errorInfo",e.getMessage());
        }
        return result;
    }
}
