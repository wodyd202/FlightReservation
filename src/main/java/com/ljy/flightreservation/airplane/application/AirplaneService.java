package com.ljy.flightreservation.airplane.application;

import com.ljy.flightreservation.airplane.application.model.AirplaneMapper;
import com.ljy.flightreservation.airplane.application.model.AirplaneModel;
import com.ljy.flightreservation.airplane.application.model.RegistrationAirplane;
import com.ljy.flightreservation.airplane.domain.agg.Airplane;
import com.ljy.flightreservation.airplane.domain.value.AirplaneCode;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AirplaneService {
    private final AirplaneRepository airplaneRepository;
    private final ReservationRepository reservationRepository;
    private final AirplaneMapper mapper;

    public AirplaneService(AirplaneRepository airplaneRepository, ReservationRepository reservationRepository, AirplaneMapper mapper) {
        this.airplaneRepository = airplaneRepository;
        this.reservationRepository = reservationRepository;
        this.mapper = mapper;
    }

    public AirplaneModel save(RegistrationAirplane airplaneCommand) {
        Airplane airplane = mapper.mapFrom(createAirplaneCode(), airplaneCommand);
        airplaneRepository.save(airplane);
        return AirplaneModel.builder()
                .airplaneCode(airplane.getCode())
                .corridorIndexes(airplane.getIndexes())
                .sits(airplane.getSits())
                .state(airplane.getState())
                .build();
    }

    private AirplaneCode createAirplaneCode() {
        return new AirplaneCode(UUID.randomUUID().toString());
    }
}
