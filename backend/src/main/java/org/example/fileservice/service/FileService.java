package org.example.fileservice.service;

import org.example.fileservice.dto.request.UploadFileDTO;
import org.example.fileservice.dto.response.S3FileDTO;
import org.example.fileservice.dto.response.PresignedUrlDTO;
import org.example.fileservice.exception.BadRequestException;
import org.example.fileservice.mapper.FileMapper;
import org.example.fileservice.model.S3File;
import org.example.fileservice.model.User;
import org.example.fileservice.repository.S3FileRepository;
import org.example.fileservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FileService {

    private final S3FileRepository fileRepository;
    private final UserRepository userRepository;
    private final S3Service s3Service;

    public FileService(S3FileRepository fileRepository, UserRepository userRepository, S3Service s3Service) {
        this.fileRepository = fileRepository;
        this.userRepository = userRepository;
        this.s3Service = s3Service;
    }

    public List<S3FileDTO> getAllFiles(UUID userId) {
        return s3Service.getAllFiles(userId).stream()
                .map(s3Object -> {
                    S3File s3File = fileRepository.findByS3Key(s3Object.key())
                            .orElseThrow(() -> new BadRequestException("File not found"));

                    return FileMapper.INSTANCE.from(s3File, s3Object);
                })
                .toList();
    }

    public PresignedUrlDTO getUploadFileUrl(UploadFileDTO uploadFileDTO, UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new BadRequestException("User not found"));
        S3File file = FileMapper.INSTANCE.uploadFileDTOToS3File(uploadFileDTO);

        file.setUser(user);
        file.setS3Key(userId + "/" + file.getId());
        file = fileRepository.save(file);

        String presignedURL = s3Service.generatePutPresignedURL(file);
        return new PresignedUrlDTO(presignedURL);
    }

    public void getFile() {
        // TODO: Implement
    }
}
