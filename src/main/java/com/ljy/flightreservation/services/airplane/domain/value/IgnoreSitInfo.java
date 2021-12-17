package com.ljy.flightreservation.services.airplane.domain.value;

import com.ljy.flightreservation.services.flight.domain.infra.SitListConverter;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Convert;
import javax.persistence.Embeddable;
import java.util.List;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IgnoreSitInfo {
    @Convert(converter = SitListConverter.class)
    private List<String> sitList;

    @Builder
    public IgnoreSitInfo(List<String> sitList) {
        this.sitList = sitList;
    }
}
