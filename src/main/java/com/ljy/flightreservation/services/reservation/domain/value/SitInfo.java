package com.ljy.flightreservation.services.reservation.domain.value;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SitInfo {
    private String code;

    @Builder
    public SitInfo(String code) {
        this.code = code;
    }
}
