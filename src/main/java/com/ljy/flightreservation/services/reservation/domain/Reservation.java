package com.ljy.flightreservation.services.reservation.domain;

import com.ljy.flightreservation.services.reservation.domain.model.ReservationModel;
import com.ljy.flightreservation.services.reservation.domain.value.*;
import com.ljy.flightreservation.services.reservation.domain.value.infra.TotalPriceConverter;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * 예약
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {
    // 예약 번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    // 운항 정보
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "seq", column = @Column(name = "flight_seq"))
    })
    private FlightInfo flightInfo;

    // 총 결제 금액
    @Convert(converter = TotalPriceConverter.class)
    @Column(nullable = false)
    private TotalPrice price;

    // 예약 상태
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationState state;

    // 예약 일자
    @Column(nullable = false)
    private LocalDate reservationDate;

    // 좌석 정보
    @Embedded
    private SitInfo sitInfo;

    // 예약자 정보
    @Embedded
    private Booker booker;

    @Builder
    public Reservation(FlightInfo flightInfo,
                       TotalPrice price,
                       SitInfo sitInfo,
                       Booker booker) {
        this.flightInfo = flightInfo;
        this.price = price;
        this.sitInfo = sitInfo;
        this.booker = booker;
    }

    public void reserve(RegisterReservationValidator registerReservationValidator) {
        registerReservationValidator.validation(flightInfo, sitInfo, booker, price);
        state = ReservationState.RESERVATE;
        reservationDate = LocalDate.now();
    }

    public ReservationModel toModel() {
        return ReservationModel.builder()
                .seq(seq)
                .flightInfo(flightInfo)
                .price(price)
                .state(state)
                .reservationDate(reservationDate)
                .sitCode(sitInfo)
                .booker(booker)
                .build();
    }
}
