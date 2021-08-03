package com.ljy.flightreservation.flightInfo.application;

import com.ljy.flightreservation.flightInfo.application.model.FlightInfoModel;
import com.ljy.flightreservation.flightInfo.application.model.FlightInfoSearch;
import com.ljy.flightreservation.flightInfo.domain.agg.FlightInfo;

import java.util.List;

public interface FlightInfoRepository {
    void save(FlightInfo flightInfo);
    List<FlightInfoModel> findAll(FlightInfoSearch search);
}
