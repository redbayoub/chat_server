package com.pro0inter.chatserver;

import com.pro0inter.chatserver.FileUpload.StorageService;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@ComponentScan("com.pro0inter.chatserver")
@EnableJpaAuditing
public class ChatserverApplication implements CommandLineRunner {

    @Autowired
    StorageService storageService;
	public static void main(String[] args) {
		SpringApplication.run(ChatserverApplication.class, args);
	}

	//Tomcat large file upload connection reset
	//http://www.mkyong.com/spring/spring-file-upload-and-connection-reset-issue/


	@Override
	public void run(String... args) throws Exception {
	    storageService.init();
	}
}
