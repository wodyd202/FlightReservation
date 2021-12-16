package com.ljy.flightreservation.services.member.presentation;

import com.ljy.flightreservation.services.member.application.MemberIntegrationTest;
import com.ljy.flightreservation.services.member.application.model.*;
import com.ljy.flightreservation.services.member.domain.value.MemberId;
import com.ljy.flightreservation.services.member.domain.value.Password;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static com.ljy.flightreservation.services.member.MemberFixture.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MemberAPI_Test extends MemberIntegrationTest {

    @Test
    @DisplayName("회원 등록")
    void register() throws Exception{
        // given
        RegisterMember registerMember = aRegisterMember().id("notexistmember").build();

        // when
        mockMvc.perform(post("/api/member")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertJson(registerMember)))

        // then
        .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("회원 아이디가 유효하지 않으면 400 에러")
    void registerInvalidId() throws Exception {
        // given
        RegisterMember registerMember = aRegisterMember().id("invalid@@").build();

        // when
        mockMvc.perform(post("/api/member")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertJson(registerMember)))

        // then
        .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("회원 비밀번호가 유효하지 않으면 400 에러")
    void registerInvalidPassword() throws Exception {
        // given
        RegisterMember registerMember = aRegisterMember().password("").build();

        // when
        mockMvc.perform(post("/api/member")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertJson(registerMember)))

        // then
        .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("이메일이 유효하지 않으면 400 에러")
    void registerInvalidEmail() throws Exception {
        // given
        RegisterMember registerMember = aRegisterMember().memberInfo(ChangeMemberInfo.builder()
                        .email("invalid")
                        .passport("")
                .build()).build();

        // when
        mockMvc.perform(post("/api/member")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertJson(registerMember)))

        // then
        .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("여권번호가 유효하지 않으면 400 에러")
    void registerInvalidPassport_1() throws Exception {
        // given
        RegisterMember registerMember = aRegisterMember().memberInfo(ChangeMemberInfo.builder()
                .email("test@google.com")
                .passport("invalid")
                .build()).build();

        // when
        mockMvc.perform(post("/api/member")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertJson(registerMember)))

        // then
        .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("여권번호 형식은 올바르나 유효하지 않으면 400 에러")
    void registerInvalidPassport_2() throws Exception {
        // given
        RegisterMember registerMember = aRegisterMember().memberInfo(ChangeMemberInfo.builder()
                .email("test@google.com")
                .passport("a10382738")
                .build()).build();

        // when
        mockMvc.perform(post("/api/member")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertJson(registerMember)))

        // then
        .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("회원 등록 시 중복된 아이디가 존재하면 400 에러 발생")
    void alreadyExistMember() throws Exception {
        // given
        newMember(aMember().id(MemberId.of("apialreadyexist")));
        RegisterMember registerMember = aRegisterMember().id("apialreadyexist").build();

        // when
        mockMvc.perform(post("/api/member")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertJson(registerMember)))

        // then
        .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("회원 변경시 토큰을 입력해야함")
    void changeMember_403() throws Exception {
        // when
        mockMvc.perform(patch("/api/member"))

        // then
        .andExpect(status().isForbidden());
    }

    @BeforeEach
    void setUp(){
        newMember(aMember().id(MemberId.of("apichangemember")).password(Password.of("password", passwordEncoder)));
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
                .header("X-AUTH-TOKEN",obtainsAccessToken("apichangemember","password"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertJson(changeMember)))

        // then
        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("이메일 변경시 유효하지 않은 이메일을 입력하면 400 에러")
    void changeInvalidEmail() throws Exception {
        // given
        ChangeMember changeMember = ChangeMember.builder()
                .email("invalidEmail")
                .build();

        // when
        mockMvc.perform(patch("/api/member")
                        .header("X-AUTH-TOKEN",obtainsAccessToken("apichangemember","password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertJson(changeMember)))

        // then
        .andExpect(status().isBadRequest());
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
                        .header("X-AUTH-TOKEN",obtainsAccessToken("apichangemember","password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertJson(changeMember)))

        // then
        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("여권번호 변경시 여권번호가 형식이 유효하지 않으면 400 에러")
    void changeInvalidFormatPassport() throws Exception {
        // given
        ChangeMember changeMember = ChangeMember.builder()
                .passport("invalid")
                .build();

        // when
        mockMvc.perform(patch("/api/member")
                        .header("X-AUTH-TOKEN",obtainsAccessToken("apichangemember","password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertJson(changeMember)))

        // then
        .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("여권번호 변경시 여권번호가 유효하지 않으면 400 에러")
    void changeInvalidPassport() throws Exception {
        // given
        ChangeMember changeMember = ChangeMember.builder()
                .passport("a10382738")
                .build();

        // when
        mockMvc.perform(patch("/api/member")
                        .header("X-AUTH-TOKEN",obtainsAccessToken("apichangemember","password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertJson(changeMember)))

        // then
        .andExpect(status().isBadRequest());
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
                        .header("X-AUTH-TOKEN",obtainsAccessToken("apichangemember","password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertJson(changeMember)))

        // then
        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("비밀번호 변경시 기존 비밀번호와 바꿀 비밀번호를 모두 입력하지 않으면 400 에러")
    void changeInvalidPassword() throws Exception {
        // given
        ChangeMember changeMember = ChangeMember.builder()
                .changePassword(ChangePassword.builder()
                        .changePassword("changepassword")
                        .build())
                .build();

        // when
        mockMvc.perform(patch("/api/member")
                        .header("X-AUTH-TOKEN",obtainsAccessToken("apichangemember","password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertJson(changeMember)))

        // then
        .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("변경된 사항이 존재하지 않을 경우 204")
    void notChangedMember() throws Exception {
        // given
        ChangeMember changeMember = ChangeMember.builder()
                .changePassword(ChangePassword.builder()
                        .changePassword("password")
                        .originPassword("password")
                        .build())
                .build();

        // when
        mockMvc.perform(patch("/api/member")
                        .header("X-AUTH-TOKEN",obtainsAccessToken("apichangemember","password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertJson(changeMember)))

        // then
        .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("회원 탈퇴시 토큰을 입력하지 않을 경우 403 에러")
    void withdrawal_403() throws Exception {
        // when
        mockMvc.perform(delete("/api/member"))

        // then
        .andExpect(status().isForbidden());
    }
    
    @Test
    @DisplayName("회원 탈퇴")
    void withdrawal() throws Exception {
        // given
        newMember(aMember().id(MemberId.of("apiwithdrawal")).password(Password.of("password", passwordEncoder)));
        WithdrawalMember withdrawalMember = WithdrawalMember.builder().originPassword("password").build();

        // when
        mockMvc.perform(delete("/api/member")
                .header("X-AUTH-TOKEN",obtainsAccessToken("apiwithdrawal","password"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertJson(withdrawalMember)))

        // then
        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원 조회")
    void getMemberModel() throws Exception {
        // when
        mockMvc.perform(get("/api/member")
                .header("X-AUTH-TOKEN",obtainsAccessToken("apichangemember","password")))

        // then
        .andExpect(status().isOk());
    }
}
