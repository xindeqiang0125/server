package com.xcs.server.controllor;

import com.xcs.server.domain.FileDetail;
import com.xcs.server.service.FileDetailService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class FileController {
    @Value("${file.upload.path}")
    private String uploadPath;

    @Autowired
    private FileDetailService fileDetailService;

    @RequestMapping(value = "/upload")
    public String uploadFile(FileDetail fileDetail, MultipartFile file, HttpServletRequest request) throws IOException {
        if ("".equals(fileDetail.getName())||"".equals(fileDetail.getFamily())){
            return "上传失败！请输入名称和类别！";
        }
        String name = file.getOriginalFilename();
        String extension = name.substring(name.lastIndexOf('.') + 1);
        fileDetail.setExtension(extension);
        fileDetailService.saveFileDetail(fileDetail);

        String path = getPath(fileDetail);
        File f = new File(path);
        FileUtils.writeByteArrayToFile(f, file.getBytes());
        return path;
    }

    private String getPath(FileDetail fileDetail) {
        return uploadPath +
                fileDetail.getFamily() + "/" +
                fileDetail.getId() + "." + fileDetail.getExtension();
    }

    @RequestMapping(value = "/files/all")
    public List<FileDetail> findAll() {
        return fileDetailService.findAll();
    }

    @RequestMapping(value = "/files/content")
    public String getFileContent(Integer id) throws IOException {
        FileDetail fileDetail = fileDetailService.findById(id);
        String path = getPath(fileDetail);
        File f = new File(path);
        return FileUtils.readFileToString(f,"utf-8");
    }

    @RequestMapping(value = "/files/save")
    public ResponseMsg saveFileDetail(FileDetail fileDetail){
        try {
            FileDetail old = fileDetailService.saveFileDetail(fileDetail);
            if (!old.equals(fileDetail)) {
                FileUtils.moveFile(new File(getPath(old)),new File(getPath(fileDetail)));
            }
            return ResponseMsg.getSuccess("保存成功");
        } catch (Exception e) {
            return ResponseMsg.getFailed("保存失败");
        }
    }

    @RequestMapping(value = "/files/delete")
    public ResponseMsg delFile(@RequestBody List<Integer> ids){
        try {
            List<FileDetail> fileDetails = fileDetailService.delete(ids);
            deleteFiles(fileDetails);
            return ResponseMsg.getSuccess("删除成功");
        } catch (Exception e) {
            return ResponseMsg.getFailed("删除失败");
        }
    }

    private void deleteFiles(List<FileDetail> fileDetails) {
        for (FileDetail detail : fileDetails) {
            deleteFile(detail);
        }
    }

    private void deleteFile(FileDetail detail) {
        String path = getPath(detail);
        File f = new File(path);
        FileUtils.deleteQuietly(f);
    }

    @RequestMapping(value = "/files/search")
    public Map<String,Object> search(@RequestParam(defaultValue = "")String name,
                                     @RequestParam(defaultValue = "1") Integer page,
                                     @RequestParam(defaultValue = "10") Integer rows){
        Page<FileDetail> fileDetails = fileDetailService.search(name,new PageRequest(page-1, rows));
        Map<String,Object> res=new HashMap<>();
        res.put("total",fileDetails.getTotalElements());
        res.put("rows",fileDetails.getContent());
        return res;
    }
}
