package com.revature.services;

import org.springframework.web.multipart.MultipartFile;

public interface AWSService {
    public String uploadImageToAWS (MultipartFile multipartFile);
}
