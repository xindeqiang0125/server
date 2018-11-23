package com.xcs.server.service;

import com.xcs.server.domain.FileDetail;
import com.xcs.server.repository.FileDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class FileDetailServiceImpl implements FileDetailService {
    @Autowired
    private FileDetailRepository fileDetailRepository;

    @Override
    public FileDetail saveFileDetail(FileDetail fileDetail) {
        FileDetail one = fileDetailRepository.findOne(fileDetail.getId());
        FileDetail old=new FileDetail();
        old.setId(one.getId());
        old.setName(one.getName());
        old.setFamily(one.getFamily());
        old.setExtension(one.getExtension());
        old.setDetail(one.getDetail());
        fileDetailRepository.save(fileDetail);
        return old;
    }

    @Override
    public List<FileDetail> findAll() {
        return fileDetailRepository.findAll(new Sort(Sort.Direction.ASC,"family"));
    }

    @Override
    public FileDetail findById(Integer id) {
        return fileDetailRepository.findOne(id);
    }

    @Override
    public List<FileDetail> delete(List<Integer> ids) {
        List<FileDetail> list = new ArrayList<>();
        for (Integer id : ids) {
            FileDetail one = fileDetailRepository.findOne(id);
            list.add(one);
            fileDetailRepository.delete(id);
        }
        return list;
    }

    @Override
    public Page<FileDetail> search(String name, Pageable pageable) {
        return fileDetailRepository.findByNameContaining(name,pageable);
    }
}
