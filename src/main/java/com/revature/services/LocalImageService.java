package com.revature.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity.HeadersBuilder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * This image service uploads files from API Requests into an uploads
 * directory under resources. Intended for local testing.
 */
@Profile(value="dev")
@Service
public class LocalImageService implements ImageService {
    private final Path root = Paths.get("src/main/resources/uploads");

    public LocalImageService () throws IOException {
        // If uploads folder doesn't exist then create one.
        try {
            if (!Files.exists(root)) {
                Files.createDirectory(root);
            }
        } catch (IOException e) {
            throw new IOException("Could not initialize folder for upload!");
        }
    }
    
    /**
     * Save a multipart file (usually an image)
     */
    public String uploadMultipartFile (MultipartFile multipartFile) throws IOException {
        String fileName = multipartFile.getOriginalFilename();
        try {
            Files.copy(multipartFile.getInputStream(), this.root.resolve(fileName));
            Path file = root.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
              return resource.getURL().toString();
            } else {
              throw new IOException("Could not read the file!");
            }
          } catch (MalformedURLException e) {
            throw new IOException("Error: " + e.getMessage());
        } catch (IOException e) {
            throw new IOException("Could not store the file. Error: " + e.getMessage());
        }
    }

}
