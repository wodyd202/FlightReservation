package com.ljy.flightreservation.airplane.command.domain.agg;

import com.ljy.flightreservation.airplane.command.domain.infra.CorridorIndexConverter;
import com.ljy.flightreservation.airplane.command.domain.value.AirplaneCode;
import com.ljy.flightreservation.airplane.command.domain.value.AirplaneState;
import com.ljy.flightreservation.airplane.command.domain.value.Sits;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "airplanes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Airplane {

    @EmbeddedId
    private AirplaneCode code;

    @Convert(converter = CorridorIndexConverter.class)
    @Column(nullable = false)
    private List<Integer> corridorIndexes;

    @Embedded
    private Sits sits;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AirplaneState state;

    @Builder
    public Airplane(AirplaneCode code, List<Integer> corridorIndexes, Sits sits) {
        this.code = code;
        this.corridorIndexes = corridorIndexes;
        this.sits = sits;
        this.state = AirplaneState.FLIGHT;
    }

    public int getTotalSitCount() {
        return sits.getTotalSit();
    }
}
