//package com.austral.bookin.service.unit;
//
//import com.austral.bookin.entity.Token;
//import com.austral.bookin.entity.User;
//import com.austral.bookin.repository.TokenRepository;
//import com.austral.bookin.service.token.TokenService;
//import org.junit.Rule;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mockito;
//import org.powermock.api.mockito.PowerMockito;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@SpringBootTest
//public class TokenServiceTest {
//
//    @Autowired
//    private TokenService tokenService;
//
//    @MockBean
//    private TokenRepository tokenRepository;
//
//    @Test
//    public void contextLoads() {
//        assertNotNull(tokenService);
//    }
//
//    @Test
//    @PrepareForTest({ UUID.class })
//    @DisplayName("Create password reset token")
//    public void createPasswordResetToken() {
//        final PasswordEncoder encoder = new BCryptPasswordEncoder();
//
//        UUID uuid = UUID.randomUUID();
//        PowerMockito.mockStatic(UUID.class);
//        PowerMockito.when(UUID.randomUUID()).thenReturn(uuid);
//
//        User user = new User(1L, "Katia", "Cammisa", "katia@hotmail.com", encoder.encode("password123"), "F", new HashSet<>(), new byte[4], new ArrayList<>());
//        Token token = new Token(null, uuid.toString(), user, new Date(125, Calendar.NOVEMBER, 1));
//
//        Mockito.doReturn(token)
//                .when(tokenRepository)
//                .save(token);
//
//        final Token result = tokenService.createPasswordResetToken(user);
//
//        assertEquals(token, result);
//    }
//}
