package com.xcs.server.controllor;

import com.xcs.server.domain.FileDetail;
import com.xcs.server.service.FileDetailService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

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

        String path = uploadPath +
                fileDetail.getFamily() + "/" +
                fileDetail.getName() + "." + fileDetail.getExtension();
        File f = new File(path);
        FileUtils.writeByteArrayToFile(f, file.getBytes());
        return path;
    }

    @RequestMapping(value = "/files/all")
    public List<FileDetail> findAll() {
        return fileDetailService.findAll();
    }

    @RequestMapping(value = "/files/{id}/content")
    public String getFileContent(@PathVariable Integer id) throws IOException {
        FileDetail fileDetail = fileDetailService.findById(id);
        String path = uploadPath +
                fileDetail.getFamily() + "/" +
                fileDetail.getName() + "." + fileDetail.getExtension();
        File f = new File(path);
        return FileUtils.readFileToString(f,"utf-8");
    }
}
