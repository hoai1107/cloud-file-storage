package org.example.fileservice.service;

import org.example.fileservice.dto.request.UploadFileDTO;
import org.example.fileservice.dto.response.FileObjectDTO;
import org.example.fileservice.dto.response.PresignedUrlDTO;
import org.example.fileservice.exception.BadRequestException;
import org.example.fileservice.mapper.FileMapper;
import org.example.fileservice.mapper.UserMapper;
import org.example.fileservice.model.S3File;
import org.example.fileservice.model.User;
import org.example.fileservice.repository.S3FileRepository;
import org.example.fileservice.repository.UserRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.S3Object;

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

    public List<FileObjectDTO> getAllFiles() {
        return s3Service.getAllFiles().stream()
                .map(content -> new FileObjectDTO(content.key(), content.size(), content.lastModified()))
                .toList();
    }

    public PresignedUrlDTO getUploadFileUrl(UploadFileDTO uploadFileDTO, UUID userId) {
        S3File s3File = FileMapper.INSTANCE.uploadFileDTOToS3File(uploadFileDTO);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException("User not found"));

        s3File.setUser(user);
        s3File = fileRepository.save(s3File);

        String presignedURL = s3Service.generatePutPresignedURL(s3File);
        return new PresignedUrlDTO(presignedURL);
    }

    public void getFile() {
        // TODO: Implement
    }
}
