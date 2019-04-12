package com.xcs.server.web.repository;

import com.xcs.server.web.domain.FileDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileDetailRepository extends JpaRepository<FileDetail,Integer> {
    Page<FileDetail> findByNameContaining(String name, Pageable pageable);

    List<FileDetail> findByFamily(String familyName);
}
