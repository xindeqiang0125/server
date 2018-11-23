package com.xcs.server.service;

import com.xcs.server.domain.FileDetail;

import java.util.List;

public interface FileDetailService {
    void saveFileDetail(FileDetail fileDetail);
    List<FileDetail> findAll();

    FileDetail findById(Integer id);

    void delete(Integer id);
}
