package org.example.fileservice.controller;

import org.example.fileservice.dto.response.FileObjectDTO;
import org.example.fileservice.dto.response.PresignedUrlDTO;
import org.example.fileservice.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("files")
public class FileController {

    private final S3Service s3Service;

    @Autowired
    public FileController(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @GetMapping
    public List<FileObjectDTO> getAllFiles() {
        return s3Service.getAllFiles();
    }

    @GetMapping("/put-presigned-url")
    public PresignedUrlDTO getPutPresignedURL(@RequestParam String filename) {
        return s3Service.generatePutPresignedURL(filename);
    }
}
