package com.revature;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.revature.models")
public class SocialMediaApplication {
	private static final Logger logger = LoggerFactory.getLogger(SocialMediaApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(SocialMediaApplication.class, args);
		logger.info("\n" +
"-------------------------------------------------------------------------------------------------\n" +
"######## ########    ###    ##     ##    ######  ########  ##     ## ########    ###    ##       \n" +
"   ##    ##         ## ##   ###   ###   ##    ## ##     ## ##     ## ##         ## ##   ##       \n" +
"   ##    ##        ##   ##  #### ####   ##       ##     ## ##     ## ##        ##   ##  ##       \n" +
"   ##    ######   ##     ## ## ### ##    ######  ########  ######### ######   ##     ## ##       \n" +
"   ##    ##       ######### ##     ##         ## ##        ##     ## ##       ######### ##       \n" +
"   ##    ##       ##     ## ##     ##   ##    ## ##        ##     ## ##       ##     ## ##       \n" +
"   ##    ######## ##     ## ##     ##    ######  ##        ##     ## ######## ##     ## ######## \n" +
"-------------------------------------------------------------------------------------------------\n"
		);
		logger.info("Social Media Application is now running! Press the Red Stop Button in Eclipse to shutdown or CTRL+C in other IDEs.");
	}

}