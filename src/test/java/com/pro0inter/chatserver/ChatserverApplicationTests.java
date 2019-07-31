package com.pro0inter.chatserver;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChatserverApplicationTests {

	@Test
	public void contextLoads() {
		//System.out.println(new File(getClass().getClassLoader().getResource("").getFile(),"new_fld").getAbsolutePath());
	}

}
