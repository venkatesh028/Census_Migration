package com.ideas2it.censusMigration.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface OneTimeMappingService {
    List<Object> readXLSXFile(MultipartFile file, String sourceEhrName, String serviceLine)
            throws FileNotFoundException, IOException;
}
