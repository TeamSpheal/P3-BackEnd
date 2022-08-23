package com.revature.services;


import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
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

import com.revature.exceptions.RecordNotFoundException;

@SpringBootTest(classes = LocalImageService.class)
public class LocalImageServiceTest {

	@Autowired
	LocalImageService localImageServ;

	
	@Test
	void cannotUploadImage() throws FileNotFoundException, IOException {
		final MockMultipartFile multipartFile = new MockMultipartFile("images", "images.jpg", "image/jpeg", "random".getBytes());
				
		assertThrows(IOException.class, () -> {
			localImageServ.uploadMultipartFile(multipartFile);
	});
		
	}
	
	
	@Test
	void uploadImage() throws FileNotFoundException, IOException {
		final MockMultipartFile multipartFile = new MockMultipartFile("images", "image.jpg", "image/jpeg", "random".getBytes());
		Path root = Paths.get("src/main/resources/uploads").resolve(multipartFile.getOriginalFilename());
		Assertions.assertNotNull(localImageServ.uploadMultipartFile(multipartFile));
		Files.deleteIfExists(root);
	}
	
}
