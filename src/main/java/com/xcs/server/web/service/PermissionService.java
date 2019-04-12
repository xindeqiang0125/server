package com.xcs.server.web.service;

import com.xcs.server.web.domain.user.Permission;
import com.xcs.server.web.domain.user.PermissionFamily;

import java.util.List;

public interface PermissionService {
    List<PermissionFamily> findAllPermissionFamily();

    void addPermissionFamily(PermissionFamily permissionFamily);

    void deletePermissionFamily(Integer id);

    List<Permission> findAllPermission();

    void addPermission(Permission permission);

    void deletePermission(Integer id);

    List<Permission> findPermissionsByFamily(Integer permissionFamilyId);
}
