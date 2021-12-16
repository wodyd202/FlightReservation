package com.ljy.flightreservation.services.airplane.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SitInfoModel {
    private SitCodeInfoModel sitCodeInfo;
    private SpecialSitInfoModel specialSitInfo;
    private BusinessSitInfoModel businessSitInfo;

    @Builder
    public SitInfoModel(SitCodeInfoModel sitCodeInfo, SpecialSitInfoModel specialSitInfo, BusinessSitInfoModel businessSitInfo) {
        this.sitCodeInfo = sitCodeInfo;
        this.specialSitInfo = specialSitInfo;
        this.businessSitInfo = businessSitInfo;
    }
}
