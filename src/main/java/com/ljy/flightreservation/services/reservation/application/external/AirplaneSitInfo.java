package com.ljy.flightreservation.services.reservation.application.external;

import com.ljy.flightreservation.services.airplane.domain.value.BusinessSitInfo;
import com.ljy.flightreservation.services.airplane.domain.value.IgnoreSitInfo;
import com.ljy.flightreservation.services.airplane.domain.value.SpecialSitInfo;
import com.ljy.flightreservation.services.reservation.domain.value.FlightInfo;
import com.ljy.flightreservation.services.reservation.domain.value.TotalPrice;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AirplaneSitInfo {
    // 스페셜 좌석 정보
    private SpecialSitInfo specialSitInfo;

    // 비즈니스 좌석 정보
    private BusinessSitInfo businessSitInfo;

    // 무시할 좌석 정보
    private IgnoreSitInfo ignoreSitInfo;

    @Builder
    public AirplaneSitInfo(SpecialSitInfo specialSitInfo,
                           BusinessSitInfo businessSitInfo,
                           IgnoreSitInfo ignoreSitInfo) {
        this.specialSitInfo = specialSitInfo;
        this.businessSitInfo = businessSitInfo;
        this.ignoreSitInfo = ignoreSitInfo;
    }

    public TotalPrice calcaulteTotalPrice(FlightInfo flightInfo, String sitCode) {
        if(ignoreSitInfo.getSitList().contains(sitCode)){
            throw new IllegalArgumentException("[" +sitCode+ "] 좌석이 존재하지 않습니다. 좌석을 다시 입력해주세요.");
        }
        if(specialSitInfo.getSitList().contains(sitCode)){
            return TotalPrice.won(flightInfo.getBasePrice() + specialSitInfo.getSitSurcharge());
        }
        if(businessSitInfo.getSitList().contains(sitCode)){
            return TotalPrice.won(flightInfo.getBasePrice() + businessSitInfo.getSitSurcharge());
        }
        return TotalPrice.won(flightInfo.getBasePrice());
    }
}

