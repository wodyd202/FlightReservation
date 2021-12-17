package com.ljy.flightreservation.services.reservation.application.external;

public interface AirplaneRepository {
    AirplaneSitInfo getAirplaneSitInfo(String airplaneCode);
}
