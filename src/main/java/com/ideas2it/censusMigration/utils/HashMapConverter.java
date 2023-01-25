package com.ideas2it.censusMigration.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HashMapConverter implements AttributeConverter<Map<String, Object>, String> {

    private static final Logger logger = LogManager.getLogger();
    ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public String convertToDatabaseColumn(Map<String, Object> patientInfo) {

        String patientInfoJson = null;
        try {
            patientInfoJson = objectMapper.writeValueAsString(patientInfo);
        } catch (final JsonProcessingException e) {
            logger.error("JSON writing error", e);
        }

        return patientInfoJson;
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String patientInfoJSON) {

        Map<String, Object> patientInfo = null;
        try {
            patientInfo = objectMapper.readValue(patientInfoJSON,
                    new TypeReference<HashMap<String, Object>>() {
                    });
        } catch (final IOException e) {
            logger.error("JSON reading error", e);
        }

        return patientInfo;
    }
}