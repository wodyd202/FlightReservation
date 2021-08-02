package com.ljy.flightreservation.airplane.domain.infra;

import javax.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CorridorIndexConverter implements AttributeConverter<List<Integer>, String> {

    @Override
    public String convertToDatabaseColumn(List<Integer> attribute) {
        StringBuilder builder = new StringBuilder();
        attribute.forEach(c->{
            builder.append(c + ",");
        });
        return builder.toString();
    }

    @Override
    public List<Integer> convertToEntityAttribute(String dbData) {
        return Arrays.asList(dbData.split(",")).stream().map(Integer::parseInt).collect(Collectors.toList());
    }
}
