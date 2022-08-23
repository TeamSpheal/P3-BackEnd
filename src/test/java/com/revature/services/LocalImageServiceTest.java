package com.revature.services;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;


@SpringBootTest(classes = LocalImageService.class)
class LocalImageServiceTest {

	@Autowired
	LocalImageService localImageServ;

	@Test
	void uploadImage() throws FileNotFoundException, IOException {
		final MockMultipartFile multipartFile = new MockMultipartFile("images", "image.jpg", "image/jpeg", "random".getBytes());
		Path root = Paths.get("src/main/resources/uploads").resolve(multipartFile.getOriginalFilename());
		Assertions.assertNotNull(localImageServ.uploadMultipartFile(multipartFile));
		Files.deleteIfExists(root);
	}
	
}
