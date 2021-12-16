package com.ljy.flightreservation.services.member.domain.infra;

import com.ljy.flightreservation.services.member.domain.value.Money;

import javax.persistence.AttributeConverter;

public class MoneyConverter implements AttributeConverter<Money, Long> {

    @Override
    public Long convertToDatabaseColumn(Money attribute) {
        return attribute.get();
    }

    @Override
    public Money convertToEntityAttribute(Long dbData) {
        return Money.won(dbData);
    }
}
