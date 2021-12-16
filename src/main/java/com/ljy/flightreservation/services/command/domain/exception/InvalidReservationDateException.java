package com.ljy.flightreservation.services.command.domain.exception;

public class InvalidReservationDateException extends IllegalArgumentException{
   public InvalidReservationDateException(String msg){
       super(msg);
   }
}
