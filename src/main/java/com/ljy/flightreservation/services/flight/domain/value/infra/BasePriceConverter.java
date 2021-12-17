package com.ljy.flightreservation.services.flight.domain.value.infra;

import com.ljy.flightreservation.services.flight.domain.value.BasePrice;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;

@Component
public class BasePriceConverter implements AttributeConverter<BasePrice, Long> {
    @Override
    public Long convertToDatabaseColumn(BasePrice basePrice) {
        return basePrice.get();
    }

    @Override
    public BasePrice convertToEntityAttribute(Long won) {
        return BasePrice.won(won);
    }
}
