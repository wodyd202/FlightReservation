package com.ljy.flightreservation.services.flight.domain.infra;

import com.ljy.flightreservation.services.flight.domain.value.AirplaneCode;

import javax.persistence.AttributeConverter;

public class AirplaneCodeConverter implements AttributeConverter<AirplaneCode, String> {

    @Override
    public String convertToDatabaseColumn(AirplaneCode attribute) {
        return attribute.get();
    }

    @Override
    public AirplaneCode convertToEntityAttribute(String dbData) {
        return new AirplaneCode(dbData);
    }
}
