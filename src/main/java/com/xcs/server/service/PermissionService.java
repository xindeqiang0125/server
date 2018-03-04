package com.xcs.server.service;

import com.xcs.server.domain.user.Permission;
import com.xcs.server.domain.user.PermissionFamily;

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
