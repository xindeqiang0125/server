package com.xcs.server.service;

import com.xcs.server.domain.user.Permission;
import com.xcs.server.domain.user.User;
import com.xcs.server.domain.user.UserGroup;
import com.xcs.server.repository.user.UserGroupRepository;
import com.xcs.server.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserGroupRepository userGroupRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserGroup> findAllUserGroup() {
        return userGroupRepository.findAll();
    }

    @Override
    public List<UserGroup> findUserGroupsByName(String name) {
        return userGroupRepository.findByNameContaining(name);
    }

    @Override
    public void saveUserGroup(UserGroup userGroup) {
        userGroupRepository.save(userGroup);
    }

    @Override
    public void deleteUserGroup(List<Integer> ids) {
        for (Integer id:ids){
            userGroupRepository.delete(id);
        }
    }

    @Override
    public Set<Permission> findPermissionsByUserGroup(Integer userGroupId) {
        return userGroupRepository.findOne(userGroupId).getPermissions();
    }

    @Override
    public Page<User> findUsersByNameOrTel(String nameOrTel,Pageable pageable) {
        return userRepository.findByNameContainingOrTelContaining(nameOrTel,nameOrTel,pageable);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(List<Integer> ids) {
        for (Integer id:ids){
            userRepository.delete(id);
        }
    }

    @Override
    public Map<String, Object> checkUser(User user) {
        User user1 = userRepository.findFirstByTelAndPassword(user.getTel(), user.getPassword());
        Map<String,Object> res=new HashMap<>();
        res.put("id",user1.getId());
        res.put("tel",user1.getTel());
        res.put("name",user1.getName());
        res.put("password",user1.getPassword());
        Set<Permission> permissions = user1.getUserGroup().getPermissions();
        Set<String> permissionStrings=new HashSet<>();
        permissions.forEach(permission -> {
            permissionStrings.add(permission.getUrl());
        });
        res.put("permissionUris", permissionStrings);
        return res;
    }

    @Override
    public void updateUser(String tel, String name, String password) {
        userRepository.updateNameAndPassword(tel, name, password);
    }
}
