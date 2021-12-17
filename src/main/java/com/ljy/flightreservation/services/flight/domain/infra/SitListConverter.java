package com.ljy.flightreservation.services.flight.domain.infra;

import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import java.util.ArrayList;
import java.util.List;

@Component
public class SitListConverter implements AttributeConverter<List<String>, String> {

    @Override
    public String convertToDatabaseColumn(List<String> sitList) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String sitInfo : sitList) {
            stringBuilder.append(sitInfo);
            stringBuilder.append(",");
        }
        return stringBuilder.toString();
    }

    @Override
    public List<String> convertToEntityAttribute(String s) {
        List<String> sitList = new ArrayList<>();
        String[] split = s.split(",");
        for (String sitInfo : split) {
            sitList.add(sitInfo);
        }
        return sitList;
    }
}
