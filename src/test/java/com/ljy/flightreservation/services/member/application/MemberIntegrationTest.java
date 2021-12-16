package com.ljy.flightreservation.services.member.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ljy.flightreservation.IntegrationTest;
import com.ljy.flightreservation.services.member.domain.Member;
import com.ljy.flightreservation.services.member.domain.MemberRepository;
import com.ljy.flightreservation.services.member.domain.RegisterMemberValidator;
import com.ljy.flightreservation.services.member.domain.exception.UserNotFoundException;
import com.ljy.flightreservation.services.member.domain.value.MemberId;
import org.springframework.beans.factory.annotation.Autowired;

import static com.ljy.flightreservation.services.member.application.MemberServiceHelper.getMember;
import static org.mockito.Mockito.mock;

public class MemberIntegrationTest extends IntegrationTest {
    @Autowired MemberRepository memberRepository;

    public void newMember(Member.MemberBuilder builder){
        Member member = builder.build();
        member.register(mock(RegisterMemberValidator.class));
        try{
            getMember(memberRepository, MemberId.of(member.toModel().getId()));
        }catch (UserNotFoundException e){
            memberRepository.save(member);
        }
    }
}
