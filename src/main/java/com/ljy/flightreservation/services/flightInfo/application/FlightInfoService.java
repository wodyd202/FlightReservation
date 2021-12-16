package com.ljy.flightreservation.services.flightInfo.application;

import com.ljy.flightreservation.services.flightInfo.application.model.FlightInfoMapper;
import com.ljy.flightreservation.services.flightInfo.application.model.FlightInfoModel;
import com.ljy.flightreservation.services.flightInfo.application.model.FlightInfoSearch;
import com.ljy.flightreservation.services.flightInfo.application.model.RegistrationFlightInfo;
import com.ljy.flightreservation.services.flightInfo.domain.agg.FlightInfo;
import com.ljy.flightreservation.services.flightInfo.domain.agg.RegistrationFlightInfoValidator;
import com.ljy.flightreservation.services.flightInfo.domain.value.FlightInfoCode;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class FlightInfoService {
    private final ApplicationEventPublisher publisher;

    private final FlightInfoRepository flightInfoRepository;
    private final FlightInfoMapper flightInfoMapper;

    private final RegistrationFlightInfoValidator registrationFlightInfoValidator;

    public FlightInfoService(ApplicationEventPublisher publisher,
                             FlightInfoRepository flightRepository,
                             FlightInfoMapper mapper,
                             RegistrationFlightInfoValidator registrationFlightInfoValidator) {
        this.publisher = publisher;
        this.flightInfoRepository = flightRepository;
        this.flightInfoMapper = mapper;
        this.registrationFlightInfoValidator = registrationFlightInfoValidator;
    }

    public List<FlightInfoModel> findAll(FlightInfoSearch search) {
        return flightInfoRepository.findAll(search);
    }

    public FlightInfoModel save(RegistrationFlightInfo registrationFlightInfo) {
        FlightInfo flightInfo = flightInfoMapper.mapFrom(registrationFlightInfo);
        flightInfo.registration(registrationFlightInfoValidator, createFlightInfoCode());
        flightInfoRepository.save(flightInfo);
        return FlightInfoModel.builder()
                .code(flightInfo.getCode())
                .area(flightInfo.getArea())
                .times(flightInfo.getTimes())
                .price(flightInfo.getPrice())
                .state(flightInfo.getState())
                .airplaneCode(flightInfo.getAirplaneCode())
                .needPassport(flightInfo.getNeedPassport())
                .build();
    }

    private FlightInfoCode createFlightInfoCode() {
        return new FlightInfoCode(UUID.randomUUID().toString());
    }

}
