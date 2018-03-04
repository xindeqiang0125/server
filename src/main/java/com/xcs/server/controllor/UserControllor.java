package com.xcs.server.controllor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xcs.server.domain.user.Permission;
import com.xcs.server.domain.user.PermissionFamily;
import com.xcs.server.domain.user.User;
import com.xcs.server.domain.user.UserGroup;
import com.xcs.server.service.PermissionService;
import com.xcs.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class UserControllor {
    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;

    //<editor-fold desc="permission">

    /**
     * 查询所有权限分类
     * @return
     */
    @RequestMapping("/permission/findallpermissionfamily")
    public List<PermissionFamily> findAllPermissionFamily(){
        return permissionService.findAllPermissionFamily();
    }

    @RequestMapping("/permission/addpermissionfamily")
    public ResponseMsg addPermissionFamily(PermissionFamily permissionFamily){
        permissionService.addPermissionFamily(permissionFamily);
        return new ResponseMsg(ResponseMsg.SUCCESS,"保存成功！");
    }

    @RequestMapping("/permission/deletepermissionfamily")
    public ResponseMsg deletePermissionFamily(Integer id){
        permissionService.deletePermissionFamily(id);
        return new ResponseMsg(ResponseMsg.SUCCESS,"删除成功！");
    }

    /**
     * 通过权限分类查询权限，若permissionFamilyId为空，返回所有权限
     * @param permissionFamilyId
     * @return
     */
    @RequestMapping("/permission/findpermissionbyfamily")
    public List<Permission> findPermissionByFamily(Integer permissionFamilyId){
        return permissionService.findPermissionsByFamily(permissionFamilyId);
    }

    @RequestMapping("/permission/addpermission")
    public ResponseMsg addPermission(Permission permission){
        permissionService.addPermission(permission);
        return new ResponseMsg(ResponseMsg.SUCCESS,"保存成功！");
    }

    @RequestMapping("/permission/deletepermission")
    public ResponseMsg deletePermission(Integer id){
        permissionService.deletePermission(id);
        return new ResponseMsg(ResponseMsg.SUCCESS,"删除成功！");
    }
    //</editor-fold>

    //<editor-fold desc="usergroup">
    @RequestMapping("/user/findallusergroups")
    public List<UserGroup> findAllUserGroups(){
        return userService.findAllUserGroup();
    }

    @RequestMapping("/user/findusergroupsbyname")
    public List<UserGroup> findUserGroupsByName(String name){
        return userService.findUserGroupsByName(name);
    }

    @RequestMapping("/user/saveusergroup")
    public ResponseMsg saveUserGroup(UserGroup userGroup,String permissionIds){
        List<Integer> lists=new Gson().fromJson(permissionIds,new TypeToken<List<Integer>>(){}.getType());
        Set<Permission> permissions=new HashSet<>();
        lists.forEach(integer -> {
            permissions.add(new Permission(integer));
        });
        userGroup.setPermissions(permissions);
        try {
            userService.saveUserGroup(userGroup);
        }catch (Exception e){
            return new ResponseMsg(ResponseMsg.SUCCESS,"保存失败");
        }
        return new ResponseMsg(ResponseMsg.SUCCESS,"保存成功");
    }

    @RequestMapping("/user/deleteusergroup")
    public ResponseMsg deleteUserGroup(@RequestBody List<Integer> ids){
        try {
            userService.deleteUserGroup(ids);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseMsg(ResponseMsg.FAILED,e.getMessage());
        } finally {
            return new ResponseMsg(ResponseMsg.SUCCESS,"删除成功");
        }
    }

    @RequestMapping("/user/findpermissionsbyusergroup")
    public Set<Permission> findPermissionsByUserGroup(Integer userGroupId){
        Set<Permission> permissions = new HashSet<>();
        try {
            permissions = userService.findPermissionsByUserGroup(userGroupId);
        } catch (Exception e) { }
        return permissions;
    }
    //</editor-fold>


    //<editor-fold desc="user">
    @RequestMapping("/user/findusersbynameortel_page")
    public Map<String, Object> findUsersByNameOrTel(String nameOrTel, Integer page, Integer rows){
        Map<String,Object> res=new HashMap<>();
        try {
            Page<User> users = userService.findUsersByNameOrTel(nameOrTel, new PageRequest(page - 1, rows));
            res.put("total",users.getTotalElements());
            res.put("rows",users.getContent());
        } catch (Exception e) {
//            e.printStackTrace();
            res.put("total",0);
            res.put("rows",new ArrayList());
        }
        return res;
    }

    @RequestMapping("/user/saveuser")
    public ResponseMsg saveUser(User user){
        try {
            userService.saveUser(user);
        } catch (Exception e) {
            //e.printStackTrace();
            return ResponseMsg.getFailed("保存用户失败");
        }
        return ResponseMsg.getSuccess("保存用户成功");
    }

    @RequestMapping("/user/deleteuser")
    public ResponseMsg deleteUser(@RequestBody List<Integer> ids){
        try {
            userService.deleteUser(ids);
        } catch (Exception e) {
//            e.printStackTrace();
            return ResponseMsg.getFailed("删除用户失败");
        }
        return ResponseMsg.getSuccess("删除用户成功");
    }
    //</editor-fold>
}
