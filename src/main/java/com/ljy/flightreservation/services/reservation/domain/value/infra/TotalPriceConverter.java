package com.ljy.flightreservation.services.reservation.domain.value.infra;

import com.ljy.flightreservation.services.reservation.domain.value.TotalPrice;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;

@Component
public class TotalPriceConverter implements AttributeConverter<TotalPrice, Long> {
    @Override
    public Long convertToDatabaseColumn(TotalPrice totalPrice) {
        return totalPrice.get();
    }

    @Override
    public TotalPrice convertToEntityAttribute(Long won) {
        return TotalPrice.won(won);
    }
}
