package com.ljy.flightreservation.restdocs.member;

import com.ljy.flightreservation.services.member.application.model.*;
import com.ljy.flightreservation.services.member.domain.value.MemberId;
import com.ljy.flightreservation.services.member.domain.value.Password;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static com.ljy.flightreservation.services.member.MemberFixture.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class MemberAPI_Test extends MemberRestDocsTest {

    @Test
    @DisplayName("회원 등록")
    void register() throws Exception{
        // given
        RegisterMember registerMember = aRegisterMember().id("restdocsmember").build();

        // when
        mockMvc.perform(post("/api/member")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertJson(registerMember)))

        // then
        .andDo(document("register_member",
        requestFields(
                fieldWithPath("id").type(STRING).description("회원 아이디"),
                fieldWithPath("password").type(STRING).description("회원 비밀번호"),
                fieldWithPath("memberInfo").type(OBJECT).description("회원 정보"),
                fieldWithPath("memberInfo.email").type(STRING).description("회원 이메일"),
                fieldWithPath("memberInfo.passport").type(STRING).description("회원 여권 번호").optional()
        )));
    }

    @BeforeEach
    void setUp(){
        newMember(aMember().id(MemberId.of("changerestdocs")).password(Password.of("password", passwordEncoder)));
    }

    @Test
    @DisplayName("회원 이메일 변경")
    void changeEmail() throws Exception {
        // given
        ChangeMember changeMember = ChangeMember.builder()
                .email("changeemail@google.com")
                .build();

        // when
        mockMvc.perform(patch("/api/member")
                .header("X-AUTH-TOKEN",obtainsAccessToken("changerestdocs","password"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertJson(changeMember)))

        // then
        .andDo(document("change_member_email",
        requestHeaders(
            headerWithName("X-AUTH-TOKEN").description("jwt 토큰")
        ),
        requestFields(
                fieldWithPath("email").type(STRING).description("회원 이메일"),
                fieldWithPath("changePassword").ignored(),
                fieldWithPath("passport").ignored()
        )));
    }

    @Test
    @DisplayName("여권번호 변경")
    void changePassport() throws Exception {
        // given
        ChangeMember changeMember = ChangeMember.builder()
                .passport("A10382738")
                .build();

        // when
        mockMvc.perform(patch("/api/member")
                        .header("X-AUTH-TOKEN",obtainsAccessToken("changerestdocs","password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertJson(changeMember)))

        // then
        .andDo(document("change_member_passport",
        requestHeaders(
                headerWithName("X-AUTH-TOKEN").description("jwt 토큰")
        ),
        requestFields(
                fieldWithPath("passport").type(STRING).description("여권 번호"),
                fieldWithPath("changePassword").ignored(),
                fieldWithPath("email").ignored()
        )));
    }

    @Test
    @DisplayName("비밀번호 변경")
    void changePassword() throws Exception {
        // given
        ChangeMember changeMember = ChangeMember.builder()
                .changePassword(ChangePassword.builder()
                        .originPassword("password")
                        .changePassword("changepassword")
                        .build())
                .build();

        // when
        mockMvc.perform(patch("/api/member")
                        .header("X-AUTH-TOKEN",obtainsAccessToken("changerestdocs","password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertJson(changeMember)))

        // then
        .andDo(document("change_member_password",
        requestHeaders(
                headerWithName("X-AUTH-TOKEN").description("jwt 토큰")
        ),
        requestFields(
                fieldWithPath("changePassword").type(OBJECT).description("비밀번호 변경시 사용되는 JSON"),
                fieldWithPath("changePassword.originPassword").type(STRING).description("기존 비밀번호"),
                fieldWithPath("changePassword.changePassword").type(STRING).description("변경할 비밀번호"),
                fieldWithPath("passport").ignored(),
                fieldWithPath("email").ignored()
        )));
    }

    @Test
    @DisplayName("회원 탈퇴")
    void withdrawal() throws Exception {
        // given
        newMember(aMember().id(MemberId.of("withdrawalrest")).password(Password.of("password", passwordEncoder)));
        WithdrawalMember withdrawalMember = WithdrawalMember.builder().originPassword("password").build();

        // when
        mockMvc.perform(delete("/api/member")
                .header("X-AUTH-TOKEN",obtainsAccessToken("withdrawalrest","password"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertJson(withdrawalMember)))

        // then
        .andDo(document("withdrawal_member",
        requestHeaders(
                headerWithName("X-AUTH-TOKEN").description("jwt 토큰")
        ),
        requestFields(
                fieldWithPath("originPassword").type(STRING).description("기존 비밀번호")
        )));
    }

    @Test
    @DisplayName("회원 조회")
    void getMemberModel() throws Exception {
        // when
        mockMvc.perform(get("/api/member")
                .header("X-AUTH-TOKEN",obtainsAccessToken("changerestdocs","password")))

        // then
        .andDo(document("get_member",
        requestHeaders(
                headerWithName("X-AUTH-TOKEN").description("jwt 토큰")
        ),
        responseFields(
                fieldWithPath("id").type(STRING).description("회원 아이디"),
                fieldWithPath("memberInfo").type(OBJECT).description("회원 정보"),
                fieldWithPath("memberInfo.email").type(STRING).description("회원 이메일"),
                fieldWithPath("memberInfo.passport").type(STRING).description("회원 여권 번호").optional(),
                fieldWithPath("money").type(NUMBER).description("가진 금액"),
                fieldWithPath("state").type(STRING).description("회원 상태"),
                fieldWithPath("createDateTime").type(STRING).description("회원 등록일")
        )));
    }
}
