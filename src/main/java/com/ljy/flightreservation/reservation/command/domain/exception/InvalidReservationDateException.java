package com.ljy.flightreservation.reservation.command.domain.exception;

public class InvalidReservationDateException extends IllegalArgumentException{
   public InvalidReservationDateException(String msg){
       super(msg);
   }
}
