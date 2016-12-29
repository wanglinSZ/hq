package com.hq.sina;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 
 * @author allen.wang
 *
 */

@EnableScheduling
@SpringBootApplication
@ComponentScan(basePackages = { "com.hq.sina" })
public class MainApplication {

	/**
	 * main function
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
		System.out.println("****************Sina HQ Start*************************");
	}
}
