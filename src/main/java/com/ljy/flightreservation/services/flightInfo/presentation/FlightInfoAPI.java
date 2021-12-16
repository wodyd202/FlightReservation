package com.ljy.flightreservation.services.flightInfo.presentation;

import com.ljy.flightreservation.services.flightInfo.application.FlightInfoService;
import com.ljy.flightreservation.services.flightInfo.application.model.FlightInfoModel;
import com.ljy.flightreservation.services.flightInfo.application.model.FlightInfoSearch;
import com.ljy.flightreservation.services.flightInfo.application.model.RegistrationFlightInfo;
import com.ljy.flightreservation.core.http.CommandException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/flight-info")
public class FlightInfoAPI {
    @Autowired private FlightInfoService flightInfoService;

    @GetMapping
    public ResponseEntity findAll(FlightInfoSearch search){
        List<FlightInfoModel> list = flightInfoService.findAll(search);
        return ResponseEntity.ok(list);
    }

    @PutMapping
    public ResponseEntity registration(@Valid @RequestBody RegistrationFlightInfo command,
                                       Errors errors){
        verifyNotHasError(errors);
        FlightInfoModel flightInfoModel = flightInfoService.save(command);
        return ResponseEntity.ok(flightInfoModel);
    }

    private void verifyNotHasError(Errors errors){
        if(errors.hasErrors()){
            throw new CommandException(errors);
        }
    }
}
