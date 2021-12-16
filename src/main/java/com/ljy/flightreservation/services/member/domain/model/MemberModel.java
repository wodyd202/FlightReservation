package com.ljy.flightreservation.services.member.domain.model;

import com.ljy.flightreservation.services.member.domain.value.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberModel {
    private String id;
    private String password;
    private MemberInfoModel memberInfo;
    private long money;
    private MemberState state;
    private LocalDateTime createDateTime;

    public boolean isDeleted() {
        return state.equals(MemberState.DELETED);
    }

    @Builder
    public MemberModel(MemberId id,
                       Password password,
                       MemberInfo memberInfo,
                       Money money,
                       MemberState state,
                       LocalDateTime createDateTime) {
        this.id = id.get();
        this.password = password.get();
        this.memberInfo = memberInfo.toModel();
        this.money = money.get();
        this.state = state;
        this.createDateTime = createDateTime;
    }
}
