package com.xcs.server.repository;

import com.xcs.server.domain.FileDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileDetailRepository extends JpaRepository<FileDetail,Integer> {
    Page<FileDetail> findByNameContaining(String name, Pageable pageable);
}
