package org.example.fileservice.mapper;

import org.example.fileservice.dto.request.UploadFileDTO;
import org.example.fileservice.model.S3File;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FileMapper {
    FileMapper INSTANCE = Mappers.getMapper(FileMapper.class);

    S3File uploadFileDTOToS3File(UploadFileDTO uploadFileDTO);
}
