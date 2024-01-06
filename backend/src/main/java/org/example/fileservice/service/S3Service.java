package org.example.fileservice.service;

import org.example.fileservice.dto.response.FileObjectDTO;
import org.example.fileservice.dto.response.PresignedUrlDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class S3Service {
    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    @Value("${bucket.name}")
    private String bucketName;

    // aws s3 ls s3://file-bucket --recursive --human-readable --summarize --endpoint-url=http://localhost:4566
    // aws s3 ls --endpoint-url=http://localhost:4566

    public S3Service(S3Client s3Client, S3Presigner s3Presigner) {
        this.s3Client = s3Client;
        this.s3Presigner = s3Presigner;
    }

    public List<FileObjectDTO> getAllFiles() {
        ListObjectsV2Request request = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .build();

        ListObjectsV2Response response = s3Client.listObjectsV2(request);

        return response.contents().stream()
                .map(content -> new FileObjectDTO(content.key(), content.size(), content.lastModified()))
                .collect(Collectors.toList());
    }

    public PresignedUrlDTO generatePutPresignedURL(String filename) {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(filename)
                .build();

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(5))
                .putObjectRequest(request)
                .build();

        PresignedPutObjectRequest presignedRequest = s3Presigner.presignPutObject(presignRequest);

        return new PresignedUrlDTO(presignedRequest.url().toExternalForm());
    }
}
