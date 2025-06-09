package com.br.bibliotech.controllers;

import com.br.bibliotech.controllers.docs.FileControllerDocs;
import com.br.bibliotech.data.dto.UploadFileResponseDTO;
import com.br.bibliotech.service.FileStorageService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/file/v1")
public class FileController implements FileControllerDocs {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService service;

    @PostMapping("/uploadFile")
    @Override
    public UploadFileResponseDTO uploadFile(@RequestParam("file") MultipartFile file) {
        var filename = service.storeFile(file);

        //http://localhost:8080/api/file/v1/donwloadFile/filename.docx
        var fileDonwloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/file/v1/donwloadFile/")
                .path(filename)
                .toUriString();

        return new UploadFileResponseDTO(
                filename,
                fileDonwloadUri,
                file.getContentType(),
                file.getSize());
    }

    @Override
    public List<UploadFileResponseDTO> uploadMultipleFiles(MultipartFile[] files) {
        return List.of();
    }

    @Override
    public ResponseEntity<ResponseEntity> donwloadFile(String fileName, HttpServletRequest request) {
        return null;
    }
}