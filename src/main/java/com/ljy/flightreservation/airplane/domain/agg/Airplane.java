package com.ljy.flightreservation.airplane.domain.agg;

import com.ljy.flightreservation.airplane.domain.value.AirplaneCode;
import com.ljy.flightreservation.airplane.domain.value.AirplaneState;
import com.ljy.flightreservation.airplane.domain.value.CorridorIndexes;
import com.ljy.flightreservation.airplane.domain.value.Sits;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "airplanes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Airplane {

    @EmbeddedId
    private AirplaneCode code;

    @Embedded
    private CorridorIndexes indexes;

    @Embedded
    private Sits sits;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AirplaneState state;

    @Builder
    public Airplane(AirplaneCode code, CorridorIndexes corridorIndexes, Sits sits) {
        this.code = code;
        this.indexes = corridorIndexes;
        this.sits = sits;
        this.state = AirplaneState.FLIGHT;
    }

    public int getTotalSitCount() {
        return sits.getTotalSit();
    }
}
