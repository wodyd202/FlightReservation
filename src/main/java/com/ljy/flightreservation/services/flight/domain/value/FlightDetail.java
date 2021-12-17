package com.ljy.flightreservation.services.flight.domain.value;

import com.ljy.flightreservation.services.flight.domain.model.FlightDetailModel;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.Embeddable;
import java.time.LocalDate;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FlightDetail {
    private LocalDate departureDate;
    private LocalDate arrivalDate;

    private int departureTime;
    private int estimatedArrivalTime;

    private String arrivalArea;

    @Builder
    public FlightDetail(LocalDate departureDate, LocalDate arrivalDate, int departureTime, int estimatedArrivalTime, String arrivalArea) {
        validation(departureDate, arrivalDate, departureTime, estimatedArrivalTime, arrivalArea);
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.departureTime = departureTime;
        this.estimatedArrivalTime = estimatedArrivalTime;
        this.arrivalArea = arrivalArea;
    }

    private void validation(LocalDate departureDate, LocalDate arrivalDate, int departureTime, int estimatedArrivalTime, String arrivalArea) {
        if(!StringUtils.hasText(arrivalArea)){
            throw new IllegalArgumentException("도착 지역을 입력해주세요.");
        }
        if(arrivalDate.isBefore(departureDate)){
            throw new IllegalArgumentException("출발일자는 도착일자 보다 이전이여야합니다.");
        }
        if(arrivalDate.equals(departureDate) && estimatedArrivalTime <= departureTime){
            throw new IllegalArgumentException("출발 시각은 도착 예정 시각보다 이전이여야합니다.");
        }
    }

    public FlightDetailModel toModel() {
        return FlightDetailModel.builder()
                .departureDate(departureDate)
                .arrivalDate(arrivalDate)
                .departureTime(departureTime)
                .estimatedArrivalTime(estimatedArrivalTime)
                .arrivalArea(arrivalArea)
                .build();
    }
}
