package org.example.fileservice.repository;

import org.example.fileservice.model.S3File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface S3FileRepository extends JpaRepository<S3File, UUID> {
    Optional<S3File> findByS3Key(String s3Key);
}
