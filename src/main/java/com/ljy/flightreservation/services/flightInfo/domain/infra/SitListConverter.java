package com.ljy.flightreservation.services.flightInfo.domain.infra;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import java.util.List;
import java.util.Set;

@Component
@AllArgsConstructor
public class SitListConverter implements AttributeConverter<List<String>, String> {
    private ObjectMapper objectMapper;

    @Override
    public String convertToDatabaseColumn(List<String> strings) {
        try {
            return objectMapper.writeValueAsString(strings);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<String> convertToEntityAttribute(String s) {
        return objectMapper.convertValue(s, List.class);
    }
}
