package com.xcs.server.repository.opc;

import com.xcs.server.domain.opc.XOpc;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpcRepository extends JpaRepository<XOpc,Integer> {
}
