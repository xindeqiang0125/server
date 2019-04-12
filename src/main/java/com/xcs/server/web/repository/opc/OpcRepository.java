package com.xcs.server.web.repository.opc;

import com.xcs.server.web.domain.opc.XOpc;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpcRepository extends JpaRepository<XOpc,Integer> {
}
