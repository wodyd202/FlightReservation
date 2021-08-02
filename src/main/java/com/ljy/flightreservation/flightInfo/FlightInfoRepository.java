package com.ljy.flightreservation.flightInfo;

import com.ljy.flightreservation.flightInfo.domain.agg.FlightInfo;

public interface FlightInfoRepository {
    void save(FlightInfo flightInfo);
}
