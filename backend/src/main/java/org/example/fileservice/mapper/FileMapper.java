package org.example.fileservice.mapper;

import org.example.fileservice.dto.request.UploadFileDTO;
import org.example.fileservice.dto.response.S3FileDTO;
import org.example.fileservice.model.S3File;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import software.amazon.awssdk.services.s3.model.S3Object;

@Mapper
public interface FileMapper {
    FileMapper INSTANCE = Mappers.getMapper(FileMapper.class);

    S3File uploadFileDTOToS3File(UploadFileDTO uploadFileDTO);

    @Mapping(source = "s3File.id", target = "id")
    @Mapping(source = "s3File.fileName", target = "name")
    @Mapping(source = "s3File.fileType", target = "type")
    @Mapping(target = "size", expression = "java(s3Object.size())")
    @Mapping(target = "lastModified", expression = "java(s3Object.lastModified())")
    S3FileDTO from(S3File s3File, S3Object s3Object);
}
