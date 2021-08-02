package com.ljy.flightreservation.flightInfo.domain.infra;

import com.ljy.flightreservation.flightInfo.domain.value.BasePrice;

import javax.persistence.AttributeConverter;

public class BasePriceConverter implements AttributeConverter<BasePrice, Long> {

    @Override
    public Long convertToDatabaseColumn(BasePrice attribute) {
        return attribute.get();
    }

    @Override
    public BasePrice convertToEntityAttribute(Long dbData) {
        return BasePrice.won(dbData);
    }
}
