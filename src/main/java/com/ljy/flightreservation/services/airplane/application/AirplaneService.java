package com.ljy.flightreservation.services.airplane.application;

import com.ljy.flightreservation.services.airplane.application.model.RegisterAirplane;
import com.ljy.flightreservation.services.airplane.domain.Airplane;
import com.ljy.flightreservation.services.airplane.domain.AirplaneRepository;
import com.ljy.flightreservation.services.airplane.domain.RegisterAirplaneValidator;
import com.ljy.flightreservation.services.airplane.domain.exception.AirplaneNotFoundException;
import com.ljy.flightreservation.services.airplane.domain.model.AirplaneModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 항공기 서비스
 */
@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class AirplaneService {
    private final AirplaneRepository airplaneRepository;

    // util
    private final AirplaneMapper airplaneMapper;
    private final RegisterAirplaneValidator registerAirplaneValidator;

    /**
     * @param registerAirplane
     * # 항공기 등록
     */
    public AirplaneModel register(RegisterAirplane registerAirplane) {
        Airplane airplane = airplaneMapper.mapFrom(registerAirplane);
        airplane.register(registerAirplaneValidator);

        airplaneRepository.save(airplane);

        AirplaneModel airplaneModel = airplane.toModel();
        log.info("save airplane into database : {}", airplaneModel);
        return airplaneModel;
    }

    public AirplaneModel getAirplaneModel(String airplaneCode) {
        return airplaneRepository.findById(airplaneCode).orElseThrow(AirplaneNotFoundException::new);
    }
}
