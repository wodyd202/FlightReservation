package com.ljy.flightreservation.airplane.application;

import com.ljy.flightreservation.airplane.application.model.ReservationSearch;
import com.ljy.flightreservation.airplane.application.model.ReservationModel;

import java.util.List;

public interface ReservationRepository {
    List<ReservationModel> findAll(ReservationSearch search);
}
