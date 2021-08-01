package com.ljy.flightreservation.airplane.command.domain.infra;

import javax.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.List;

public class SitConverter implements AttributeConverter<List<String>, String> {

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        StringBuilder builder = new StringBuilder();
        attribute.forEach(c->{
            builder.append(c + ",");
        });
        return builder.toString();
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        return Arrays.asList(dbData.split(","));
    }
}
