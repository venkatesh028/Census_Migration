package com.ideas2it.censusMigration.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ideas2it.censusMigration.service.impl.OneTimeMappingServiceImpl;

@RestController
@RequestMapping("/api/v1/mappings")
public class OneTimeMappingController {
    private final OneTimeMappingServiceImpl oneTimeMappingServiceImpl;

    public OneTimeMappingController(OneTimeMappingServiceImpl oneTimeMappingServiceImpl){
       this.oneTimeMappingServiceImpl = oneTimeMappingServiceImpl;
    }

    @PostMapping("/{sourceEhrName}/{serviceLine}")
    public List<Object> redXLSXFile(@RequestBody MultipartFile file,@PathVariable("sourceEhrName") String sourceEhrName,
                                     @PathVariable("serviceLine") String serviceLine) throws IOException {
        return oneTimeMappingServiceImpl.readXLSXFile(file,sourceEhrName, serviceLine);
    }

}
