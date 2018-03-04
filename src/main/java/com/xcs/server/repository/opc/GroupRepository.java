package com.xcs.server.repository.opc;

import com.xcs.server.domain.opc.XGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<XGroup,Integer> {
    Page<XGroup> findByGroupNameContaining(String groupName,Pageable pageable);
    Page<XGroup> findByOpc_Id(Integer opcId, Pageable pageable);
    Page<XGroup> findByOpc_IdAndGroupNameContaining(Integer opcId,String groupName,Pageable pageable);
}
