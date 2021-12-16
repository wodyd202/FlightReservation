package com.ljy.flightreservation.services.airplane.application.model;

import lombok.*;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeIgnoreSitInfo {
    private List<String> sitList;
}
