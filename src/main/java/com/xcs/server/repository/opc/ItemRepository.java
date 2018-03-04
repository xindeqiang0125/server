package com.xcs.server.repository.opc;

import com.xcs.server.domain.opc.XItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<XItem,Integer>{
    Page<XItem> findByItemNameContaining(String itemName, Pageable pageable);
    Page<XItem> findByGroup_Id(Integer groupId, Pageable pageable);
    Page<XItem> findByGroup_IdAndItemNameContaining(Integer groupId,String itemName, Pageable pageable);
}
