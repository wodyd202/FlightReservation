package com.ljy.flightreservation.services.airplane.domain.value;

import com.ljy.flightreservation.services.airplane.domain.model.BusinessSitInfoModel;
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
public class BusinessSitInfo extends SpecialSit {

    @Builder
    public BusinessSitInfo(List<String> sitList, int sitSurcharge) {
        super(sitList, sitSurcharge);
    }

    @Override
    protected int getMinimumSurcharge() {
        return 30000;
    }

    @Override
    protected String getName() {
        return "비즈니스";
    }

    public BusinessSitInfoModel toModel() {
        return BusinessSitInfoModel.builder()
                .sitList(sitList)
                .sitSurcharge(sitSurcharge)
                .build();
    }
}
