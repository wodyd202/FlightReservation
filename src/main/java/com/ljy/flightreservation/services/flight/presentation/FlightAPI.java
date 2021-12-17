package com.ljy.flightreservation.services.flight.presentation;

import com.ljy.flightreservation.services.flight.application.FlightService;
import com.ljy.flightreservation.services.flight.application.model.FlightModels;
import com.ljy.flightreservation.services.flight.application.model.FlightSearchDTO;
import com.ljy.flightreservation.services.flight.domain.model.FlightModel;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 운항 정보 API
 */
@RequestMapping("api/flight")
@RestController
@AllArgsConstructor
public class FlightAPI {
    private final FlightService flightService;

    /**
     * @param flightSeq
     * # 운항 상세 정보 조회
     */
    @GetMapping("{flightSeq}")
    public ResponseEntity<FlightModel> getFlightModel(@PathVariable Long flightSeq){
        FlightModel flightModel = flightService.getFlightModel(flightSeq);
        return ResponseEntity.ok(flightModel);
    }

    /**
     * @param flightSearchDTO
     * # 운항 정보 리스트 조회
     */
    @GetMapping
    public ResponseEntity<FlightModels> getFlightModels(FlightSearchDTO flightSearchDTO){
        FlightModels flightModels = flightService.getFlightModels(flightSearchDTO);
        return ResponseEntity.ok(flightModels);
    }
}
