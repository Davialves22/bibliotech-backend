package com.br.bibliotech.service;

import com.br.bibliotech.config.FileStorageConfig;
import com.br.bibliotech.controllers.FileController;
import com.br.bibliotech.exception.FileStorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    private static final Logger logger = LoggerFactory.getLogger(FileStorageService.class);

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageConfig fileStorageConfig) {
        Path path = Paths.get(fileStorageConfig.getUpload_dir()).toAbsolutePath()
                .toAbsolutePath().normalize();

        this.fileStorageLocation = path;
        try {
            logger.info("Criando Diretório");
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception e) {
            logger.error("Diretório Não Disponível ou Criado.");
            throw new FileStorageException("Diretório Não Disponível ou Criado.", e);
        }
    }

    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (fileName.contains("..")) {
                logger.error("O Arquivo Contém um Caracter Inválido: " + fileName);
                throw new FileStorageException("O Arquivo Contém um Caracter Inválido: " + fileName);
            }
            logger.info("Salvando Arquivo em Disco...");
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;

        } catch (Exception e) {
            logger.error("Não foi possível Salvar o Arquivo: " + fileName + ". Por favor, Tente Novamente.", e);
            throw new FileStorageException("Não foi possível Salvar o Arquivo: " + fileName + ". Por favor, Tente Novamente.", e);
        }
    }
}