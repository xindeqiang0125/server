package com.xcs.server.service;

import com.xcs.server.domain.FileDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

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
}
