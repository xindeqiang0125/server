package com.xcs.server.web.service;

import com.xcs.server.web.domain.FileDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface FileDetailService {
    /**
     * 更新file元数据
     * @param fileDetail
     * @return 旧的file元数据
     */
    FileDetail saveFileDetail(FileDetail fileDetail);
    List<FileDetail> findAll();

    FileDetail findById(Integer id);

    List<FileDetail> delete(List<Integer> ids);

    Page<FileDetail> search(String name, Pageable pageable);

    FileDetail upload(FileDetail fileDetail);

    Set<String> findAllFamilys();

    List<FileDetail> findByFamily(String family);
}
