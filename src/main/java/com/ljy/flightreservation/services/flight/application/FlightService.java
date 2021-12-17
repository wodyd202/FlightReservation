package com.ljy.flightreservation.services.flight.application;

import com.ljy.flightreservation.services.airplane.domain.AirplaneRepository;
import com.ljy.flightreservation.services.flight.application.model.FlightModels;
import com.ljy.flightreservation.services.flight.application.model.FlightSearchDTO;
import com.ljy.flightreservation.services.flight.application.model.RegisterFlight;
import com.ljy.flightreservation.services.flight.domain.Flight;
import com.ljy.flightreservation.services.flight.domain.FlightRepository;
import com.ljy.flightreservation.services.flight.domain.RegisterFlightValidator;
import com.ljy.flightreservation.services.flight.domain.exception.FlightNotFoundException;
import com.ljy.flightreservation.services.flight.domain.model.FlightModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 운항 정보 서비스
 */
@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;

    // util
    private final RegisterFlightValidator registerFlightValidator;
    private final FlightMapper flightMapper;

    /**
     * @param registerFlight
     * # 운항 정보 등록
     */
    public FlightModel register(RegisterFlight registerFlight) {
        Flight flight = flightMapper.mapFrom(registerFlight);
        flight.register(registerFlightValidator);

        flightRepository.save(flight);
        FlightModel flightModel = flight.toModel();

        log.info("save flight into database : {}", flightModel);
        return flightModel;
    }

    /**
     * @param flightSeq
     * # 운항 상세 정보 조회
     */
    public FlightModel getFlightModel(Long flightSeq) {
        return flightRepository.findById(flightSeq).orElseThrow(FlightNotFoundException::new);
    }

    /**
     * @param flightSearchDTO
     * # 운항 정보 리스트 조회
     */
    public FlightModels getFlightModels(FlightSearchDTO flightSearchDTO) {
        return FlightModels.builder()
                .flights(flightRepository.findAll(flightSearchDTO))
                .totalElement(flightRepository.countAll(flightSearchDTO))
                .build();
    }

}
