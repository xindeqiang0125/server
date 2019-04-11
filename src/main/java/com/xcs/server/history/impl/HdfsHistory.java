package com.xcs.server.history.impl;

import com.xcs.server.history.History;
import com.xcs.server.opc.memory.ValueMap;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class HdfsHistory implements History {

    @Value("${hdfs.uri}")
    private String hadoopUri;
    @Value("${hdfs.user}")
    private String hadoopUser;

    private FileSystem fileSystem;
    private String rootPath = "/xcs-history";
    private FSDataOutputStream doubleOut;
    private FSDataOutputStream integerOut;
    private FSDataOutputStream stringOut;
    private FSDataOutputStream booleanOut;

    @PostConstruct
    public void setUp(){
        try {
            fileSystem = FileSystem.get(URI.create(hadoopUri),new Configuration(),hadoopUser);
            if (!fileSystem.exists(new Path(rootPath))) {
                fileSystem.mkdirs(new Path(rootPath));
            }
            doubleOut = fileSystem.create(new Path(getHistoryFileName("double")));
            integerOut = fileSystem.create(new Path(getHistoryFileName("integer")));
            stringOut = fileSystem.create(new Path(getHistoryFileName("string")));
            booleanOut = fileSystem.create(new Path(getHistoryFileName("boolean")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getHistoryFileName(String type) {
        StringBuilder sb = new StringBuilder();
        sb.append(rootPath).append("/").append(type).append("-").append(System.currentTimeMillis());
        return sb.toString();
    }

    @Override
    public List getDataHistory(Integer itemId, LocalDateTime start, LocalDateTime end) {
        return null;
    }

    @Override
    public void saveHistoryDatas(ValueMap datas, LocalDateTime now) {
        datas.forEach((s, value) -> writeRecord(s,value,now));
    }

    private void writeRecord(String id, com.xcs.server.opc.data.Value value, LocalDateTime now) {
        String type = value.getType();
        StringBuilder sb = new StringBuilder();
        String record = sb.append(id).append(" ").append(value.toString()).append(" ")
                .append(now.toString()).append("\n").toString();
        try {
            if (com.xcs.server.opc.data.Value.Type.DOUBLE.equals(type)){
                doubleOut.write(record.getBytes("utf-8"));
                doubleOut.hsync();
            } else if (com.xcs.server.opc.data.Value.Type.INTEGER.equals(type)){
                integerOut.write(record.getBytes("utf-8"));
                integerOut.hsync();
            }else if (com.xcs.server.opc.data.Value.Type.STRING.equals(type)){
                stringOut.write(record.getBytes("utf-8"));
                stringOut.hsync();
            }else {
                booleanOut.write(record.getBytes("utf-8"));
                booleanOut.hsync();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
