package com.revature.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class AWSServiceImpl implements AWSService {
    Regions clientRegion = Regions.US_EAST_1;

    @Value("${aws.bucket.name}")
    String bucketName;

    public String uploadImageToAWS (MultipartFile multipartFile) {

        String fileObjKeyName = multipartFile.getOriginalFilename();

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
            return s3Client.getUrl(bucketName, fileObjKeyName).toString();
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process 
            // it, so it returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        } catch (IOException e) {
            // Can't get InputStream from multipartFile
            e.printStackTrace();
        }
        
        return null;
    }
}
