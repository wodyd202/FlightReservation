package com.ljy.flightreservation.services.airplane.domain.value;

import com.ljy.flightreservation.services.airplane.domain.model.BusinessSitInfoModel;
import com.ljy.flightreservation.services.airplane.domain.model.SpecialSitInfoModel;
import com.ljy.flightreservation.services.flightInfo.domain.value.SpecialSit;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Embeddable;
import java.util.List;

@Getter
@Embeddable
public class BusinessSitInfo extends SpecialSit {

    @Builder
    public BusinessSitInfo(List<String> sitList, int sitSurcharge) {
        super(sitList, sitSurcharge);
    }

    public BusinessSitInfo() {
        super(null, 0);
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
