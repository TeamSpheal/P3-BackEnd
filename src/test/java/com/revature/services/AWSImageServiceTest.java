package com.revature.services;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;

@SpringBootTest(classes = AWSImageService.class)
public class AWSImageServiceTest {

	@Autowired
	AWSImageService imageServ;
	
	/*
	
	@Test
	void uploadMultipartFile() throws AmazonServiceException, SdkClientException, IOException {
		final MockMultipartFile multipartFile = new MockMultipartFile("images", "image.jpg", "image/jpeg", "random".getBytes());
		Assertions.assertNotNull(imageServ.uploadMultipartFile(multipartFile));
	}
	
	*/
}
