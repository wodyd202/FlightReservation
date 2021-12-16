package com.ljy.flightreservation.services.airplane.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SitCodeInfoModel {
    private String lastRowSitCode;
    private int lastColSitNumber;

    @Builder
    public SitCodeInfoModel(String lastRowSitCode, int lastColSitNumber) {
        this.lastRowSitCode = lastRowSitCode;
        this.lastColSitNumber = lastColSitNumber;
    }
}
