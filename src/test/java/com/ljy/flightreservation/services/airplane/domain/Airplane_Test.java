package com.ljy.flightreservation.services.airplane.domain;

import com.ljy.flightreservation.services.airplane.domain.model.AirplaneModel;
import com.ljy.flightreservation.services.airplane.domain.value.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * 항공기 테스트
 */
public class Airplane_Test {

    @Test
    @DisplayName("비즈니스 좌석이 제외시킬 좌석 항목에 포함되어 있으면 안됨")
    void ignoreContainBusinessSitInfo(){
        // when
        assertThrows(IllegalArgumentException.class, ()->{
            SitInfo.builder()
                    .ignoreSitInfo(IgnoreSitInfo.builder()
                            .sitList(Arrays.asList("A2"))
                            .build())
                    .businessSitInfo(BusinessSitInfo.builder()
                            .sitList(Arrays.asList("A2","D1"))
                            .sitSurcharge(30000)
                            .build())
                    .specialSitInfo(SpecialSitInfo.builder()
                            .sitList(Arrays.asList("A1", "C2"))
                            .sitSurcharge(50000)
                            .build())
                    .sitCodeInfo(SitCodeInfo.builder()
                            .lastColSitNumber(7)
                            .lastRowSitCode("D")
                            .build())
                    .build();
        });
    }

    @Test
    @DisplayName("비즈니스 좌석 및 스페셜 좌석은 비행기 좌석 내에 있어야함")
    void notContainSitCode(){
        // when
        assertThrows(IllegalArgumentException.class, ()->{
            SitInfo.builder()
                    .ignoreSitInfo(IgnoreSitInfo.builder()
                            .sitList(Arrays.asList("C3"))
                            .build())
                    .businessSitInfo(BusinessSitInfo.builder()
                            .sitList(Arrays.asList("A2","Z1"))
                            .sitSurcharge(30000)
                            .build())
                    .specialSitInfo(SpecialSitInfo.builder()
                            .sitList(Arrays.asList("A1", "C2"))
                            .sitSurcharge(50000)
                            .build())
                    .sitCodeInfo(SitCodeInfo.builder()
                            .lastColSitNumber(7)
                            .lastRowSitCode("D")
                            .build())
                    .build();
        });
    }

    @Test
    @DisplayName("좌석 정보 앞자리는 A-Z, 뒷자리는 0-9 까지만 허용함")
    void invalidSitCode(){
        // when
        assertThrows(IllegalArgumentException.class, ()->{
            SitInfo.builder()
                    .ignoreSitInfo(IgnoreSitInfo.builder()
                            .sitList(Arrays.asList("C3"))
                            .build())
                    .businessSitInfo(BusinessSitInfo.builder()
                            .sitList(Arrays.asList("z1","C1"))
                            .sitSurcharge(30000)
                            .build())
                    .specialSitInfo(SpecialSitInfo.builder()
                            .sitList(Arrays.asList("A1", "C2"))
                            .sitSurcharge(50000)
                            .build())
                    .sitCodeInfo(SitCodeInfo.builder()
                            .lastColSitNumber(7)
                            .lastRowSitCode("Z")
                            .build())
                    .build();
        });
    }

    @Test
    @DisplayName("비즈니스 좌석은 중복 코드를 허용하지 않음")
    void alreadyExistBusinessSit(){
        // when
        assertThrows(IllegalArgumentException.class, ()->{
            SitInfo.builder()
                    .ignoreSitInfo(IgnoreSitInfo.builder()
                            .sitList(Arrays.asList("C3"))
                            .build())
                    .businessSitInfo(BusinessSitInfo.builder()
                            .sitList(Arrays.asList("C1","C1"))
                            .sitSurcharge(30000)
                            .build())
                    .specialSitInfo(SpecialSitInfo.builder()
                            .sitList(Arrays.asList("A1", "C2"))
                            .sitSurcharge(50000)
                            .build())
                    .sitCodeInfo(SitCodeInfo.builder()
                            .lastColSitNumber(7)
                            .lastRowSitCode("Z")
                            .build())
                    .build();
        });
    }

    @Test
    @DisplayName("비즈니스 좌석 추가요금을 10,000 이상 입력해야함")
    void invalidBusinessSitSurcharge(){
        // when
        assertThrows(IllegalArgumentException.class, ()->{
            SitInfo.builder()
                    .ignoreSitInfo(IgnoreSitInfo.builder()
                            .sitList(Arrays.asList("C3"))
                            .build())
                    .businessSitInfo(BusinessSitInfo.builder()
                            .sitList(Arrays.asList("C1","B1"))
                            .sitSurcharge(9000)
                            .build())
                    .specialSitInfo(SpecialSitInfo.builder()
                            .sitList(Arrays.asList("A1", "C2"))
                            .sitSurcharge(50000)
                            .build())
                    .sitCodeInfo(SitCodeInfo.builder()
                            .lastColSitNumber(7)
                            .lastRowSitCode("Z")
                            .build())
                    .build();
        });
    }

    @Test
    @DisplayName("항공기 등록시 비즈니스 좌석과 스페셜 좌석이 겹치면 안됨")
    void sameSitInfo(){
        // when
        assertThrows(IllegalArgumentException.class, ()->{
            SitInfo.builder()
                    .ignoreSitInfo(IgnoreSitInfo.builder()
                            .sitList(Arrays.asList("C3"))
                            .build())
                    .businessSitInfo(BusinessSitInfo.builder()
                            .sitList(Arrays.asList("A1","B1"))
                            .sitSurcharge(30000)
                            .build())
                    .specialSitInfo(SpecialSitInfo.builder()
                            .sitList(Arrays.asList("A1", "C2"))
                            .sitSurcharge(50000)
                            .build())
                    .sitCodeInfo(SitCodeInfo.builder()
                            .lastColSitNumber(7)
                            .lastRowSitCode("Z")
                            .build())
                    .build();
        });
    }

    @Test
    @DisplayName("항공기 생성")
    void newAirplane(){
        // given
        Airplane airplane = Airplane.builder()
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
                        .build())
                .build();
        AirplaneModel airplaneModel = airplane.toModel();

        // then
        assertEquals(airplaneModel.getCode(), "code");
        assertEquals(airplaneModel.getSitInfo().getBusinessSitInfo().getSitList(), Arrays.asList("A1","B1"));
        assertEquals(airplaneModel.getSitInfo().getBusinessSitInfo().getSitSurcharge(), 30000);
        assertEquals(airplaneModel.getSitInfo().getSpecialSitInfo().getSitList(), Arrays.asList("C1","C2"));
        assertEquals(airplaneModel.getSitInfo().getSpecialSitInfo().getSitSurcharge(), 50000);
        assertEquals(airplaneModel.getSitInfo().getSitCodeInfo().getLastColSitNumber(), 7);
        assertEquals(airplaneModel.getSitInfo().getSitCodeInfo().getLastRowSitCode(), "Z");
    }
}
