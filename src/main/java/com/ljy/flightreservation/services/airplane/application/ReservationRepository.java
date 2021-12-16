package com.ljy.flightreservation.services.airplane.application;

import com.ljy.flightreservation.services.airplane.application.model.ReservationSearch;
import com.ljy.flightreservation.services.airplane.application.model.ReservationModel;

import java.util.List;

public interface ReservationRepository {
    List<ReservationModel> findAll(ReservationSearch search);
}
