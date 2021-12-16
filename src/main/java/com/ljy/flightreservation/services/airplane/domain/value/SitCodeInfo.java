package com.ljy.flightreservation.services.airplane.domain.value;

import com.ljy.flightreservation.services.airplane.domain.model.SitCodeInfoModel;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SitCodeInfo {
    // 마지막 가로 좌석 코드
    private String lastRowSitCode;

    // 마지막 세로 좌석 번호
    private int lastColSitNumber;

    @Builder
    public SitCodeInfo(String lastRowSitCode, int lastColSitNumber) {
        this.lastRowSitCode = lastRowSitCode;
        this.lastColSitNumber = lastColSitNumber;
    }

    public SitCodeInfoModel toModel() {
        return SitCodeInfoModel.builder()
                .lastColSitNumber(lastColSitNumber)
                .lastRowSitCode(lastRowSitCode)
                .build();
    }
}
