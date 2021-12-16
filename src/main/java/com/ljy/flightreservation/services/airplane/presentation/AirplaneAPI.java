package com.ljy.flightreservation.services.airplane.presentation;

import com.ljy.flightreservation.services.airplane.application.AirplaneService;
import com.ljy.flightreservation.services.airplane.domain.model.AirplaneModel;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 항공기 API
 */
@RequestMapping("api/airplane")
@RestController
@AllArgsConstructor
public class AirplaneAPI {
    private final AirplaneService airplaneService;

    /**
     * @param airplaneCode
     * # 해당 코드의 항공기 조회
     */
    @GetMapping("{airplaneCode}")
    public ResponseEntity<AirplaneModel> getAirplaneMode(@PathVariable String airplaneCode){
        AirplaneModel airplaneModel = airplaneService.getAirplaneModel(airplaneCode);
        return ResponseEntity.ok(airplaneModel);
    }
}
