package com.revature.services;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    public String uploadMultipartFile (MultipartFile multipartFile) throws IOException;
}
