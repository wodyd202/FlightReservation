package com.ljy.flightreservation.airplane.domain.agg;

import com.ljy.flightreservation.airplane.domain.infra.CorridorIndexConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import java.util.Collections;
import java.util.List;

@Embeddable
public class CorridorIndexes {

    @Convert(converter = CorridorIndexConverter.class)
    @Column(nullable = false)
    private List<Integer> corridorIndexes;

    public CorridorIndexes(List<Integer> corridorIndexes){
        Collections.sort(corridorIndexes);
        this.corridorIndexes = corridorIndexes;
    }
}
