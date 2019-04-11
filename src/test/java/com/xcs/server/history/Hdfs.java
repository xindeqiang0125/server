package com.xcs.server.history;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;

public class Hdfs {
    FileSystem fileSystem = null;
    Configuration configuration = null;

    @Test
    public void test1() throws IOException {
        fileSystem.mkdirs(new Path("/xcs/aa/bb"));
    }

    @Test
    public void test2() throws Exception {
        FSDataOutputStream outputStream = fileSystem.create(new Path("/xcs-history/double-2019-04-11-19:23:27"));
        for (int i = 0; i < 1000000; i++) {
            outputStream.write("xdq 南京大学\n".getBytes("utf-8"));
        }

    }

    @Test
    public void test3() throws Exception {
//        FSDataOutputStream append = fileSystem.append(new Path("/xcs-history/double.txt"));
//        IOUtils.copyBytes(
//                new FileInputStream(new File("C:\\Users\\XINDQ\\Desktop\\redis-5.0.3.tar.gz")),
//                append,
//                2048
//        );
    }

    @Test
    public void test4() throws IOException {
        fileSystem.delete(new Path("/xcs-history/double.txt"),true);
    }

    @Test
    public void test5() throws IOException {
//        FsStatus status = fileSystem.getStatus(new Path("/xcs-history/double-1554990253032"));
        FSDataInputStream open = fileSystem.open(new Path("/xcs-history/double-1554990253032"));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        IOUtils.copyBytes(open, outputStream,1024);
        System.out.println(outputStream.size());
    }

    @Before
    public void setUp() throws IOException, InterruptedException {
        configuration = new Configuration();
        fileSystem = FileSystem.get(URI.create("hdfs://192.168.3.128:9000"), configuration, "root");

    }

    @After
    public void tearDowm() {
        configuration = null;
        fileSystem = null;
    }
}
