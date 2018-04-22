package com.xcs.server.service;

import com.xcs.server.domain.FileDetail;
import com.xcs.server.repository.FileDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FileDetailServiceImpl implements FileDetailService {
    @Autowired
    private FileDetailRepository fileDetailRepository;

    @Override
    public void saveFileDetail(FileDetail fileDetail) {
        fileDetailRepository.save(fileDetail);
    }

    @Override
    public List<FileDetail> findAll() {
        return fileDetailRepository.findAll();
    }

    @Override
    public FileDetail findById(Integer id) {
        return fileDetailRepository.findOne(id);
    }

}
