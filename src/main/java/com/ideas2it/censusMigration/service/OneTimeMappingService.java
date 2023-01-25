package com.ideas2it.censusMigration.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.ideas2it.censusMigration.model.TargetRecord;

public interface OneTimeMappingService {
    List<TargetRecord> readXLSXFile(MultipartFile file, String sourceEhrName, String serviceLine)
            throws FileNotFoundException, IOException;
}
