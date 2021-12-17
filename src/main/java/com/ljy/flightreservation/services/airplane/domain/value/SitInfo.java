package com.ljy.flightreservation.services.airplane.domain.value;

import com.ljy.flightreservation.services.airplane.domain.model.SitInfoModel;
import com.ljy.flightreservation.services.flight.domain.value.SpecialSit;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SitInfo {
    // 좌석 코드 정보
    @Embedded
    private SitCodeInfo sitCodeInfo;

    // 스페셜 좌석 정보
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "sitList", column = @Column(name = "special_sit_list")),
            @AttributeOverride(name = "sitSurcharge", column = @Column(name = "special_sit_surcharge")),
    })
    private SpecialSitInfo specialSitInfo;

    // 비즈니스 좌석 정보
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "sitList", column = @Column(name = "busiuness_sit_list")),
            @AttributeOverride(name = "sitSurcharge", column = @Column(name = "busiuness_sit_surcharge")),
    })
    private BusinessSitInfo businessSitInfo;

    // 무시할 좌석 정보
    @Embedded
    @AttributeOverride(name = "sitList", column = @Column(name = "ignore_sit_list"))
    private IgnoreSitInfo ignoreSitInfo;

    @Builder
    public SitInfo(SitCodeInfo sitCodeInfo, SpecialSitInfo specialSitInfo, BusinessSitInfo businessSitInfo, IgnoreSitInfo ignoreSitInfo) {
        verifyNotContainsIgnoreSitInfo(specialSitInfo, ignoreSitInfo);
        verifyNotContainsIgnoreSitInfo(businessSitInfo, ignoreSitInfo);
        verifyContainAllSitInfo(sitCodeInfo, specialSitInfo);
        verifyContainAllSitInfo(sitCodeInfo, businessSitInfo);
        verifyNotContainsSameSitCode(specialSitInfo, businessSitInfo);

        this.sitCodeInfo = sitCodeInfo;
        this.specialSitInfo = specialSitInfo;
        this.businessSitInfo = businessSitInfo;
        this.ignoreSitInfo = ignoreSitInfo;
    }

    // 제외시킨 좌석이 아닌지 확인
    private void verifyNotContainsIgnoreSitInfo(SpecialSit specialSit, IgnoreSitInfo ignoreSitInfo) {
        List<String> sitList = specialSit.getSitList();
        List<String> ignoreSitInfoSitList = ignoreSitInfo.getSitList();
        for (String sitInfo : sitList) {
            if(ignoreSitInfoSitList.contains(sitInfo)){
                throw new IllegalArgumentException("[" + sitInfo + "] 좌표가 제외시킬 좌표에 포함되어 있습니다.");
            }
        }
    }

    // 비즈니스 좌석과 스페셜 좌석이 입력한 좌석 범위내에 있는지 확인
    private void verifyContainAllSitInfo(SitCodeInfo sitCodeInfo, SpecialSit specialSit) {
        List<String> specialSitInfoSitList = specialSit.getSitList();
        for (String sitInfo : specialSitInfoSitList) {
            char first = sitInfo.charAt(0);
            char second = sitInfo.charAt(1);
            if(first < 'A' || first > sitCodeInfo.getLastRowSitCode().charAt(0)){
                throw new IllegalArgumentException("[" + sitInfo + "] 좌석이 범위내에 존재하지 않습니다.");
            }
            if(second < '0' || second > sitCodeInfo.getLastColSitNumber() + '0'){
                throw new IllegalArgumentException("[" + sitInfo + "] 좌석이 범위내에 존재하지 않습니다.");
            }
        }
    }

    // 겹치는 좌석이 존재하지 않는지 확인
    private void verifyNotContainsSameSitCode(SpecialSitInfo specialSitInfo, BusinessSitInfo businessSitInfo) {
        List<String> specialSitInfoSitList = specialSitInfo.getSitList();
        List<String> businessSitInfoSitList = businessSitInfo.getSitList();
        if(businessSitInfoSitList.size() >= specialSitInfoSitList.size()){
            for (String sitInfo : businessSitInfoSitList) {
                if(specialSitInfoSitList.contains(sitInfo)){
                    throw new IllegalArgumentException("좌석 코드 ["+ sitInfo + "] 비즈니스 좌석과 스페셜 좌석이 동일합니다.");
                }
            }
        }
        if(businessSitInfoSitList.size() < specialSitInfoSitList.size()){
            for (String sitInfo : specialSitInfoSitList) {
                if(businessSitInfoSitList.contains(sitInfo)){
                    throw new IllegalArgumentException("좌석 코드 ["+ sitInfo + "] 비즈니스 좌석과 스페셜 좌석이 동일합니다.");
                }
            }
        }
    }

    public SitInfoModel toModel() {
        return SitInfoModel.builder()
                .businessSitInfo(businessSitInfo.toModel())
                .specialSitInfo(specialSitInfo.toModel())
                .sitCodeInfo(sitCodeInfo.toModel())
                .build();
    }
}
