package org.example.fileservice.controller;

import org.example.fileservice.dto.request.UploadFileDTO;
import org.example.fileservice.dto.response.FileObjectDTO;
import org.example.fileservice.dto.response.PresignedUrlDTO;
import org.example.fileservice.mapper.FileMapper;
import org.example.fileservice.model.S3File;
import org.example.fileservice.service.FileService;
import org.example.fileservice.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("files")
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping
    public List<FileObjectDTO> getAllFiles() {
        return fileService.getAllFiles();
    }

    @GetMapping("/put-presigned-url")
    public PresignedUrlDTO getPutPresignedURL(@RequestBody UploadFileDTO uploadFileDTO, @RequestAttribute("id")UUID userId) {
        return fileService.getUploadFileUrl(uploadFileDTO, userId);
    }
}
