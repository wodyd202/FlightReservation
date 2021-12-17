package com.ljy.flightreservation.services.flight.domain.value;

import com.ljy.flightreservation.services.airplane.domain.value.infra.SitListConverter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
abstract public class SpecialSit {
    // 스페셜 좌석 리스트
    @Convert(converter = SitListConverter.class)
    protected List<String> sitList;

    // 스페셜 좌석 추가 요금
    protected int sitSurcharge;

    abstract protected int getMinimumSurcharge();
    abstract protected String getName();

    protected SpecialSit(List<String> sitList, int sitSurcharge) {
        setSitSurcharge(sitSurcharge);
        setSitList(sitList);
    }

    private static DecimalFormat decFormat = new DecimalFormat("###,###");
    private void setSitSurcharge(int sitSurcharge) {
        if(sitSurcharge < getMinimumSurcharge()){
            throw new IllegalArgumentException(getName() + " 좌석 추가요금은 " + decFormat.format(getMinimumSurcharge()) + "원 이상 입력해주세요.");
        }
        this.sitSurcharge = sitSurcharge;
    }

    private void setSitList(List<String> sitList) {
        if(sitList.isEmpty()){
            throw new IllegalArgumentException(getName() + " 좌석 코드를 최소 하나 이상 입력해주세요.");
        }
        for (String sitInfo : sitList) {
            if(sitInfo.charAt(0) < 'A' || sitInfo.charAt(0) > 'Z'){
                throw new IllegalArgumentException("좌석 코드 앞자리는 [A-Z] 사이로 입력해주세요.");
            }
            if(sitInfo.charAt(1) < '0' || sitInfo.charAt(1) > '9'){
                throw new IllegalArgumentException("좌석 코드 뒷자리는 [0-9] 사이로 입력해주세요.");
            }
        }
        Set<String> sitSets = sitList.stream().collect(Collectors.toSet());
        if(sitList.size() != sitSets.size()){
            throw new IllegalArgumentException(getName() + " 좌석은 중복 코드를 허용하지 않습니다.");
        }
        this.sitList = sitList;
    }
}
