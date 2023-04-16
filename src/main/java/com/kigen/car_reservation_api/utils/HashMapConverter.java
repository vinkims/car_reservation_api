package com.kigen.car_reservation_api.utils;

import java.io.IOException;
import java.util.Map;

import javax.persistence.AttributeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HashMapConverter implements AttributeConverter<Map<String, Object>, String> {

    Logger logger = LoggerFactory.getLogger(HashMapConverter.class);

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, Object> contentInfo) {

        String contentJson = null;
        try {
            contentJson = mapper.writeValueAsString(contentInfo);
        } catch (final JsonProcessingException e) {
            logger.error("JSON write error", e);
        }

        return contentJson;
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String contentJSON) {

        Map<String, Object> contentData = null;
        try {
            contentData = mapper.readValue(contentJSON, new TypeReference<Map<String, Object>>() {   
            });
        } catch (final IOException e) {
            logger.error("JSON read error", e);
        }

        return contentData;
    }
    
}
