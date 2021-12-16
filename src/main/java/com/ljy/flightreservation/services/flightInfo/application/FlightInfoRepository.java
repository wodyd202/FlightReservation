package com.ljy.flightreservation.services.flightInfo.application;

import com.ljy.flightreservation.services.flightInfo.application.model.FlightInfoModel;
import com.ljy.flightreservation.services.flightInfo.application.model.FlightInfoSearch;
import com.ljy.flightreservation.services.flightInfo.domain.agg.FlightInfo;

import java.util.List;

public interface FlightInfoRepository {
    void save(FlightInfo flightInfo);
    List<FlightInfoModel> findAll(FlightInfoSearch search);
}
