package org.example.fileservice.controller;

import org.example.fileservice.dto.request.UploadFileDTO;
import org.example.fileservice.dto.response.S3FileDTO;
import org.example.fileservice.dto.response.PresignedUrlDTO;
import org.example.fileservice.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public List<S3FileDTO> getAllFiles(@RequestAttribute("id") UUID userId) {
        return fileService.getAllFiles(userId);
    }

    @PostMapping("/upload")
    public PresignedUrlDTO getPutPresignedURL(@RequestBody UploadFileDTO uploadFileDTO, @RequestAttribute("id") UUID userId) {
        return fileService.getUploadFileUrl(uploadFileDTO, userId);
    }

    @GetMapping("/get")
    public PresignedUrlDTO getGetPresignedURL(@RequestParam("fileId") UUID fileId, @RequestAttribute("id") UUID userId) {
        return fileService.getDownloadFileUrl(fileId, userId);
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteFile(@RequestParam("fileId") UUID fileId, @RequestAttribute("id") UUID userId) {
        fileService.deleteFile(fileId, userId);
    }
}
