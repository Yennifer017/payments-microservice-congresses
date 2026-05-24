package ayd2.ps2026.demo.auth.jwt;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomUserDetailsTest {

    private static final Integer USER_ID = 1;
    private static final String USERNAME = "blue-dragon";

    @Test
    void customUserDetails_success() {

        // Arrange
        List<GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_ADMIN"),
                new SimpleGrantedAuthority("ROLE_USER")
        );

        CustomUserDetails customUserDetails =
                new CustomUserDetails(
                        USER_ID,
                        USERNAME,
                        authorities
                );

        // Act & Assert
        assertAll(
                () -> assertEquals(
                        USER_ID,
                        customUserDetails.getId()
                ),

                () -> assertEquals(
                        USERNAME,
                        customUserDetails.getUsername()
                ),

                () -> assertEquals(
                        authorities,
                        customUserDetails.getAuthorities()
                ),

                () -> assertEquals(
                        "",
                        customUserDetails.getPassword()
                )
        );
    }
}