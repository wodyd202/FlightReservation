package com.ljy.flightreservation.services.airplane;

import com.ljy.flightreservation.services.airplane.application.model.*;
import com.ljy.flightreservation.services.airplane.domain.Airplane;
import com.ljy.flightreservation.services.airplane.domain.value.*;

import java.util.Arrays;

public class AirplaneFixture {
    public static Airplane.AirplaneBuilder aAirplane(){
        return Airplane.builder()
                .code(AirplaneCode.of("code"))
                .sitInfo(SitInfo.builder()
                        .ignoreSitInfo(IgnoreSitInfo.builder()
                                .sitList(Arrays.asList("C3"))
                                .build())
                        .businessSitInfo(BusinessSitInfo.builder()
                                .sitList(Arrays.asList("A1","B1"))
                                .sitSurcharge(30000)
                                .build())
                        .specialSitInfo(SpecialSitInfo.builder()
                                .sitList(Arrays.asList("C1", "C2"))
                                .sitSurcharge(50000)
                                .build())
                        .sitCodeInfo(SitCodeInfo.builder()
                                .lastColSitNumber(7)
                                .lastRowSitCode("Z")
                                .build())
                        .build());
    }

    public static RegisterAirplane.RegisterAirplaneBuilder aRegisterAirplane() {
        return RegisterAirplane.builder()
                .code("code")
                .sitInfo(ChangeSitInfo.builder()
                        .businessSitInfo(ChangeBusinessSitInfo.builder()
                                .sitList(Arrays.asList("A1","A2"))
                                .sitSurcharge(30000)
                                .build())
                        .specialSitInfo(ChangeSpecialSitInfo.builder()
                                .sitList(Arrays.asList("B1","B2"))
                                .sitSurcharge(50000)
                                .build())
                        .ignoreSitInfo(ChangeIgnoreSitInfo.builder()
                                .sitList(Arrays.asList("C1","C2"))
                                .build())
                        .sitCodeInfo(ChangeSitCodeInfo.builder()
                                .lastColSitNumber(5)
                                .lastRowSitCode("Z")
                                .build())
                        .build());
    }
}
