package com.ljy.flightreservation.services.airplane.domain.value;

import com.ljy.flightreservation.services.airplane.domain.model.SpecialSitInfoModel;
import com.ljy.flightreservation.services.flight.domain.value.SpecialSit;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.List;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SpecialSitInfo extends SpecialSit {

    @Builder
    public SpecialSitInfo(List<String> sitList, int sitSurcharge) {
        super(sitList, sitSurcharge);
    }

    @Override
    protected int getMinimumSurcharge() {
        return 50000;
    }

    @Override
    protected String getName() {
        return "스페셜";
    }

    public SpecialSitInfoModel toModel() {
        return SpecialSitInfoModel.builder()
                .sitList(sitList)
                .sitSurcharge(sitSurcharge)
                .build();
    }
}
