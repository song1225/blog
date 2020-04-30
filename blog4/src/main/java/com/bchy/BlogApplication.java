package com.bchy;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@ServletComponentScan
public class BlogApplication {
	
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		//Max size of one file
		factory.setMaxFileSize("1000MB"); //KB,MB
		/// Max Size of All files
		factory.setMaxRequestSize("1000MB");
		return factory.createMultipartConfig();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args); 
	}
}
