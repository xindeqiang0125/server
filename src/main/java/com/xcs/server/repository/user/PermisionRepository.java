package com.xcs.server.repository.user;

import com.xcs.server.domain.user.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermisionRepository extends JpaRepository<Permission,Integer> {
    List<Permission> findByPermissionFamily_Id(Integer permissionFamilyId);
}
