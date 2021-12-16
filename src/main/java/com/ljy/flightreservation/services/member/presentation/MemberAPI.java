package com.ljy.flightreservation.services.member.presentation;

import com.ljy.flightreservation.core.http.CommandException;
import com.ljy.flightreservation.services.member.application.MemberService;
import com.ljy.flightreservation.services.member.application.exception.NoChangedMemberException;
import com.ljy.flightreservation.services.member.application.model.ChangeMember;
import com.ljy.flightreservation.services.member.application.model.RegisterMember;
import com.ljy.flightreservation.services.member.application.model.WithdrawalMember;
import com.ljy.flightreservation.services.member.domain.model.MemberModel;
import com.ljy.flightreservation.services.member.domain.value.MemberId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("api/member")
@AllArgsConstructor
public class MemberAPI {
    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<MemberModel> getMemberModel(Principal principal){
        MemberModel memberModel = memberService.getMember(principal.getName());
        return ResponseEntity.ok(memberModel);
    }

    /**
     * @param registerMember
     * @param errors
     * # 회원 등록
     */
    @PostMapping
    public ResponseEntity<MemberModel> registerMember(@Valid @RequestBody RegisterMember registerMember, Errors errors){
        if(errors.hasErrors()){
            throw new CommandException(errors);
        }
        MemberModel memberModel = memberService.register(registerMember);
        return ResponseEntity.status(HttpStatus.CREATED).body(memberModel);
    }

    /**
     * @param changeMember
     * @param errors
     * @param principal
     * # 회원 정보 변경
     */
    @PatchMapping
    public ResponseEntity<MemberModel> changeMember(@Valid @RequestBody ChangeMember changeMember, Errors errors, Principal principal){
        if(errors.hasErrors()){
            throw new CommandException(errors);
        }
        try {
            MemberModel memberModel = memberService.changeMember(changeMember, MemberId.of(principal.getName()));
            return ResponseEntity.status(HttpStatus.OK).body(memberModel);
        } catch (NoChangedMemberException e) {
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * @param withdrawalMember
     * @param errors
     * @param principal
     * # 회원 탈퇴
     */
    @DeleteMapping
    public ResponseEntity<MemberModel> withdrawal(@Valid @RequestBody WithdrawalMember withdrawalMember, Errors errors, Principal principal){
        if(errors.hasErrors()){
            throw new CommandException(errors);
        }
        MemberModel memberModel = memberService.withdrawal(withdrawalMember, MemberId.of(principal.getName()));
        return ResponseEntity.ok(memberModel);
    }
}
