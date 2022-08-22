package com.revature.services;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

/**
 * Used to upload an image to S3 and get back a URL to display
 * on the front end. Intended for deployment.
 */
@Profile(value="test")
@Service
public class AWSImageService implements ImageService {
    private final Logger logger = LoggerFactory.getLogger(AWSImageService.class);

    /**
     * The region the S3 Bucket is in.
     */
    Regions clientRegion = Regions.US_EAST_1;

    /**
     * Name of the bucket in S3.
     */
    @Value("${aws.bucket.name}")
    String bucketName;

    public String uploadMultipartFile (MultipartFile multipartFile) throws AmazonServiceException, SdkClientException, IOException {
        String fileObjKeyName = UUID.randomUUID().toString();

        try {
            //This code expects that you have AWS credentials set up per:
            // https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/setup-credentials.html
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(clientRegion)
                .build();

            // Upload a file as a new object with ContentType and title specified.
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("image/jpeg");
            metadata.addUserMetadata("title", multipartFile.getOriginalFilename());
            PutObjectRequest request = new PutObjectRequest(bucketName, fileObjKeyName, multipartFile.getInputStream(), metadata);
            s3Client.putObject(request);

            // Return the URL of the image
            return s3Client.getUrl(bucketName, fileObjKeyName).toString();
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process 
            // it, so it returned an error response.
            logger.error(e.getMessage());
            throw e;
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            logger.error(e.getMessage());
            throw e;
        } catch (IOException e) {
            // Can't get InputStream from multipartFile
            logger.error(e.getMessage());
            throw e;
        }
    }
}
