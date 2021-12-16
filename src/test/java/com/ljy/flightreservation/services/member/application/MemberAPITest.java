package com.ljy.flightreservation.services.member.application;

import com.ljy.flightreservation.services.member.domain.Member;
import com.ljy.flightreservation.services.member.domain.MemberRepository;
import com.ljy.flightreservation.services.member.domain.RegisterMemberValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.mock;

@SpringBootTest
public class MemberAPITest {
    @Autowired MemberRepository memberRepository;

    public void newMember(Member.MemberBuilder builder){
        Member member = builder.build();
        member.register(mock(RegisterMemberValidator.class));
        memberRepository.save(member);
    }
}
