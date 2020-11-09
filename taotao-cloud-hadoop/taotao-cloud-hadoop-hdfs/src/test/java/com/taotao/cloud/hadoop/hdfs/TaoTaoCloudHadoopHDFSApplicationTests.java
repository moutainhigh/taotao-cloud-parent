package com.taotao.cloud.hadoop.hdfs;

import com.taotao.cloud.hadoop.hdfs.service.HdfsService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = TaoTaoCloudHadoopHDFSApplication.class)
@RunWith(SpringRunner.class)
public class TaoTaoCloudHadoopHDFSApplicationTests {

	@Test
	public void contextLoads() {

	}

	@Autowired
	private HdfsService hdfsService;

	@Test
	public void testAddUser() throws Exception {
		hdfsService.mkdir("/taotao");
	}

}
