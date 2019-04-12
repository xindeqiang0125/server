package com.xcs.server.history.impl;

import com.xcs.server.history.History;
import com.xcs.server.opc.memory.ValueMap;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class HdfsHistory implements History {

    private static final Logger logger = LoggerFactory.getLogger(HdfsHistory.class);

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

    @Override
    public void setUp() {
        try {
            fileSystem = FileSystem.get(URI.create(hadoopUri), new Configuration(), hadoopUser);
            if (!fileSystem.exists(new Path(rootPath))) {
                fileSystem.mkdirs(new Path(rootPath));
            }
            doubleOut = fileSystem.create(new Path(getHistoryFileName("double")));
            integerOut = fileSystem.create(new Path(getHistoryFileName("integer")));
            stringOut = fileSystem.create(new Path(getHistoryFileName("string")));
            booleanOut = fileSystem.create(new Path(getHistoryFileName("boolean")));
            logger.info("HDFS历史数据初始化完成");
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("HDFS历史数据初始化失败：" + e.getMessage());
        }
    }

    @PreDestroy
    public void tearDown() {
        try {
            IOUtils.closeStream(doubleOut);
            IOUtils.closeStream(integerOut);
            IOUtils.closeStream(stringOut);
            IOUtils.closeStream(booleanOut);
            if (fileSystem != null) fileSystem.close();
            logger.info("HDFS历史数据资源回收完成");
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("HDFS历史数据资源回收失败" + e.getMessage());
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
        datas.forEach((s, value) -> writeRecord(s, value, now));
    }

    private void writeRecord(String id, com.xcs.server.opc.Value value, LocalDateTime now) {
        String type = value.getType();
        StringBuilder sb = new StringBuilder();
        String record = sb.append(id).append(" ").append(value.toString()).append(" ")
                .append(now.toString()).append("\n").toString();
        try {
            if (com.xcs.server.opc.Value.Type.DOUBLE.equals(type)) {
                doubleOut.write(record.getBytes("utf-8"));
                doubleOut.hsync();
            } else if (com.xcs.server.opc.Value.Type.INTEGER.equals(type)) {
                integerOut.write(record.getBytes("utf-8"));
                integerOut.hsync();
            } else if (com.xcs.server.opc.Value.Type.STRING.equals(type)) {
                stringOut.write(record.getBytes("utf-8"));
                stringOut.hsync();
            } else {
                booleanOut.write(record.getBytes("utf-8"));
                booleanOut.hsync();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
