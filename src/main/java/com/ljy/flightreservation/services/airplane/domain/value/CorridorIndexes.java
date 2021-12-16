package com.ljy.flightreservation.services.airplane.domain.value;

import com.ljy.flightreservation.services.airplane.domain.infra.CorridorIndexConverter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import java.util.Collections;
import java.util.List;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CorridorIndexes {

    @Convert(converter = CorridorIndexConverter.class)
    @Column(nullable = false)
    private List<Integer> corridorIndexes;

    public CorridorIndexes(List<Integer> corridorIndexes){
        Collections.sort(corridorIndexes);
        this.corridorIndexes = corridorIndexes;
    }

    public List<Integer> get() {
        return corridorIndexes;
    }
}
