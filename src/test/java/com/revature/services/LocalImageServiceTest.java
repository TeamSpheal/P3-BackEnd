package com.revature.services;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest(classes = LocalImageService.class)
public class LocalImageServiceTest {

	@Autowired
	LocalImageService localImageServ;

	/*
	 * Path root = Mockito.mock(Path.class);
		root = Paths.get("src/main/resources/img.jpg");
		
	 */
	@Test
	void uploadImage() throws FileNotFoundException, IOException {
		final MockMultipartFile multipartFile = new MockMultipartFile("images", "image.jpg", "image/jpeg", "random".getBytes());
		Path root = Paths.get("src/main/resources/uploads").resolve(multipartFile.getOriginalFilename());
		Assertions.assertNotNull(localImageServ.uploadMultipartFile(multipartFile));
		Files.deleteIfExists(root);
	}
}
