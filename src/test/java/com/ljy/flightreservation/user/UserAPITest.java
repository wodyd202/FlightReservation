package com.ljy.flightreservation.user;

import com.ljy.flightreservation.ApiTest;
import com.ljy.flightreservation.user.command.application.UserRepository;
import com.ljy.flightreservation.user.command.application.UserService;
import com.ljy.flightreservation.user.command.application.model.*;
import com.ljy.flightreservation.user.command.domain.value.UserId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserAPITest extends ApiTest {

    @Test
    @DisplayName("회원 가입")
    void register() throws Exception {
        RegisterUser userCommand = RegisterUser.builder()
                .id("test123")
                .password("originPw")
                .email("test@test.com")
                .build();

        mvc.perform(post("/api/v1/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userCommand)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원가입 후 비밀번호 변경")
    void changePassword() throws Exception {
        saveUser("test123", "originPw");

        ChangePassword userCommand = ChangePassword.builder()
                .changePassword("changePw")
                .originPassword("originPw")
                .build();

        mvc.perform(put("/api/v1/user/password")
                .header("X-AUTH-TOKEN", obtainJwtToken("test123", "originPw"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userCommand)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원가입 후 여권번호 변경")
    void changePassport() throws Exception {
        saveUser("test123", "originPw");

        ChangePassport userCommand = new ChangePassport("X10382738");

        mvc.perform(put("/api/v1/user/passport")
                .header("X-AUTH-TOKEN", obtainJwtToken("test123", "originPw"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userCommand)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원 탈퇴")
    void withdrawal() throws Exception {
        saveUser("test1234", "originPw");

        Withdrawal userCommand = new Withdrawal("originPw");

        mvc.perform(delete("/api/v1/user")
                .header("X-AUTH-TOKEN", obtainJwtToken("test1234", "originPw"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userCommand)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("입금")
    void deposit() throws Exception {
        saveUser("test123", "originPw");

        DepositMoney userCommand = new DepositMoney(30000L);

        mvc.perform(put("/api/v1/user/deposit")
                .header("X-AUTH-TOKEN", obtainJwtToken("test123", "originPw"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userCommand)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("정보 조회")
    void findById() throws Exception {
        saveUser("test123", "originPw");

        mvc.perform(get("/api/v1/user")
                    .header("X-AUTH-TOKEN", obtainJwtToken("test123", "originPw")))
                    .andExpect(status().isOk())
                    .andDo(print());
    }

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    private void saveUser(String username, String password) {
        if (userRepository.findByUserId(new UserId(username)).isPresent()) {
            return;
        }

        RegisterUser userCommand = RegisterUser.builder()
                .id(username)
                .password(password)
                .email("test@test.com")
                .build();

        userService.register(userCommand);
    }

}
