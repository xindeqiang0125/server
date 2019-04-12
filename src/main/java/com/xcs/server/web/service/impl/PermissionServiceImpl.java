package com.xcs.server.web.service.impl;

import com.xcs.server.web.domain.user.Permission;
import com.xcs.server.web.domain.user.PermissionFamily;
import com.xcs.server.web.repository.user.PermisionFamilyRepository;
import com.xcs.server.web.repository.user.PermisionRepository;
import com.xcs.server.web.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermisionFamilyRepository permisionFamilyRepository;
    @Autowired
    private PermisionRepository permisionRepository;

    @Override
    public List<PermissionFamily> findAllPermissionFamily() {
        return permisionFamilyRepository.findAll();
    }

    @Override
    public void addPermissionFamily(PermissionFamily permissionFamily) {
        if (permissionFamily != null) {
            permisionFamilyRepository.save(permissionFamily);
        }
    }

    @Override
    public void deletePermissionFamily(Integer id) {
        if (id != null) {
            permisionFamilyRepository.delete(id);
        }
    }

    @Override
    public List<Permission> findAllPermission() {
        return permisionRepository.findAll();
    }

    @Override
    public void addPermission(Permission permission) {
        if (permission != null) {
            permisionRepository.save(permission);
        }
    }

    @Override
    public void deletePermission(Integer id) {
        if (id != null) {
            permisionRepository.delete(id);
        }
    }

    @Override
    public List<Permission> findPermissionsByFamily(Integer permissionFamilyId) {
        if (permissionFamilyId == null) {
            return permisionRepository.findAll();
        }else {
            return permisionRepository.findByPermissionFamily_Id(permissionFamilyId);
        }
    }
}
