package com.xcs.server.service;

import com.xcs.server.domain.user.Permission;
import com.xcs.server.domain.user.User;
import com.xcs.server.domain.user.UserGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserService {
    List<UserGroup> findAllUserGroup();

    List<UserGroup> findUserGroupsByName(String name);

    void saveUserGroup(UserGroup userGroup);

    void deleteUserGroup(List<Integer> ids);

    Set<Permission> findPermissionsByUserGroup(Integer userGroupId);

    Page<User> findUsersByNameOrTel(String nameOrTel,Pageable pageable);

    void saveUser(User user);

    void deleteUser(List<Integer> ids);

    Map<String, Object> checkUser(User user);

    void updateUser(String tel, String name, String password);
}
