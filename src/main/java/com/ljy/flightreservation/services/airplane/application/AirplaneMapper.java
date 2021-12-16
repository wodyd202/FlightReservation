package com.ljy.flightreservation.services.airplane.application;

import com.ljy.flightreservation.services.airplane.application.model.RegisterAirplane;
import com.ljy.flightreservation.services.airplane.domain.Airplane;
import com.ljy.flightreservation.services.airplane.domain.value.*;
import org.springframework.stereotype.Component;


@Component
public class AirplaneMapper {

    public Airplane mapFrom(RegisterAirplane registerAirplane) {
        return Airplane.builder()
                .code(AirplaneCode.of(registerAirplane.getCode()))
                .sitInfo(SitInfo.builder()
                        .ignoreSitInfo(IgnoreSitInfo.builder()
                                .sitList(registerAirplane.getSitInfo().getIgnoreSitInfo().getSitList())
                                .build())
                        .businessSitInfo(BusinessSitInfo.builder()
                                .sitList(registerAirplane.getSitInfo().getBusinessSitInfo().getSitList())
                                .sitSurcharge(registerAirplane.getSitInfo().getBusinessSitInfo().getSitSurcharge())
                                .build())
                        .specialSitInfo(SpecialSitInfo.builder()
                                .sitList(registerAirplane.getSitInfo().getSpecialSitInfo().getSitList())
                                .sitSurcharge(registerAirplane.getSitInfo().getSpecialSitInfo().getSitSurcharge())
                                .build())
                        .sitCodeInfo(SitCodeInfo.builder()
                                .lastColSitNumber(registerAirplane.getSitInfo().getSitCodeInfo().getLastColSitNumber())
                                .lastRowSitCode(registerAirplane.getSitInfo().getSitCodeInfo().getLastRowSitCode())
                                .build())
                        .build()).build();
    }
}
