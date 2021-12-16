package com.ljy.flightreservation.services.member.domain;


import com.ljy.flightreservation.services.member.domain.infra.MoneyConverter;
import com.ljy.flightreservation.services.member.domain.model.MemberModel;
import com.ljy.flightreservation.services.member.domain.value.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 회원
 */
@Entity
@Table(name = "members")
@DynamicUpdate
@Slf4j
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    // 회원 아이디
    @EmbeddedId
    private MemberId id;

    // 회원 비밀번호
    @Embedded
    @AttributeOverride(
            name = "password",
            column = @Column(nullable = false)
    )
    private Password password;

    // 회원 정보
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "email", column = @Column(nullable = false)),
            @AttributeOverride(name = "passport", column = @Column(nullable = false))
    })
    private MemberInfo memberInfo;

    // 소지한 금액
    @Convert(converter = MoneyConverter.class)
    @Column(nullable = false)
    private Money money;

    // 회원 상태
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 7)
    private MemberState state;

    // 회원 등록일
    @Column(nullable = false)
    private LocalDateTime createDateTime;

    @Builder
    public Member(MemberId id, Password password, MemberInfo memberInfo) {
        this.id = id;
        this.password = password;
        this.memberInfo = memberInfo;
        this.money = Money.won(0);
    }

    /**
     * @param registerUserValidator
     * # 회원 등록
     */
    public void register(RegisterMemberValidator registerUserValidator) {
        registerUserValidator.validation(id, memberInfo);
        this.state = MemberState.CREATED;
        this.createDateTime = LocalDateTime.now();
    }

    /**
     * @param inputOriginPassword
     * @param changePassword
     * @param passwordEncoder
     * # 비밀번호 변경
     */
    public boolean changePassword(String inputOriginPassword,
                               Password changePassword,
                               PasswordEncoder passwordEncoder) {
        verifyEqualsOriginPassword(inputOriginPassword, passwordEncoder);
        if(passwordEncoder.matches(inputOriginPassword, changePassword.get())){
            return false;
        }
        this.password = changePassword;
        return true;
    }

    /**
     * @param inputOriginPassword
     * @param passwordEncoder
     * # 회원 탈퇴
     */
    public void withdrawal(String inputOriginPassword, PasswordEncoder passwordEncoder) {
        verifyEqualsOriginPassword(inputOriginPassword, passwordEncoder);
        this.state = MemberState.DELETED;
    }

    private void verifyEqualsOriginPassword(String inputOriginPassword, PasswordEncoder passwordEncoder) {
        if (!passwordEncoder.matches(inputOriginPassword, password.get())) {
            throw new IllegalArgumentException("기존 비밀번호가 일치하지 않습니다.");
        }
    }

    /**
     * @param changePassportValidator
     * @param passport
     * # 여권번호 변경
     */
    public boolean changePassport(PassportValidator changePassportValidator,
                               Passport passport) {
        changePassportValidator.validation(passport);
        if(memberInfo.getPassport() != null && memberInfo.getPassport().equals(passport)){
            return false;
        }
        this.memberInfo.changePassport(passport);
        return true;
    }

    /**
     * @param email
     * # 이메일 변경
     */
    public boolean changeEmail(Email email) {
        if(memberInfo.getEmail().equals(email)){
            return false;
        }
        this.memberInfo.changeEmail(email);
        return true;
    }

    /**
     * @param won
     * # 입금
     */
    public void deposit(Money won) {
        this.money = this.money.plus(won);
    }

    /**
     * @param won
     * # 결제
     */
    public void pay(Money won) {
        this.money = this.money.minus(won);
    }

    public MemberModel toModel() {
        return MemberModel.builder()
                .id(id)
                .password(password)
                .createDateTime(createDateTime)
                .memberInfo(memberInfo)
                .money(money)
                .state(state)
                .build();
    }
}
