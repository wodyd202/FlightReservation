package com.ljy.flightreservation.services.flight;

import com.ljy.flightreservation.IntegrationTest;
import com.ljy.flightreservation.services.flight.domain.Flight;
import com.ljy.flightreservation.services.flight.domain.FlightRepository;
import com.ljy.flightreservation.services.flight.domain.RegisterFlightValidator;
import com.ljy.flightreservation.services.flight.domain.model.FlightModel;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.mock;

public class FlightIntegrationTest extends IntegrationTest {
    @Autowired
    FlightRepository flightRepository;

    protected FlightModel newFlight(Flight.FlightBuilder flightBuilder){
        Flight flight = flightBuilder.build();
        flight.register(mock(RegisterFlightValidator.class));
        flightRepository.save(flight);
        return flight.toModel();
    }
}
