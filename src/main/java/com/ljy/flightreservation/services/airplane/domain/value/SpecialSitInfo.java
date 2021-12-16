package com.ljy.flightreservation.services.airplane.domain.value;

import com.ljy.flightreservation.services.airplane.domain.model.SpecialSitInfoModel;
import com.ljy.flightreservation.services.flightInfo.domain.value.SpecialSit;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Embeddable;
import java.util.List;

@Getter
@Embeddable
public class SpecialSitInfo extends SpecialSit {
    public SpecialSitInfo() {
        super(null, 0);
    }

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
