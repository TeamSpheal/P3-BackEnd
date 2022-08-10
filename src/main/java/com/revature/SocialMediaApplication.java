package com.revature;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.revature.models")
public class SocialMediaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialMediaApplication.class, args);
		System.out.println(
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
		System.out.println("Social Media Application is now running! Press the Red Stop Button in Eclipse to shutdown or CTRL+C in other IDEs.");
	}
}