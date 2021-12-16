package com.ljy.flightreservation.services.airplane.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class BusinessSitInfoModel {
    private List<String> sitList;
    private int sitSurcharge;

    @Builder
    public BusinessSitInfoModel(List<String> sitList, int sitSurcharge) {
        this.sitList = sitList;
        this.sitSurcharge = sitSurcharge;
    }
}
