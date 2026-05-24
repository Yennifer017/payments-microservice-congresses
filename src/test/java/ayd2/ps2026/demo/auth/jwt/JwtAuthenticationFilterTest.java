package ayd2.ps2026.demo.auth.jwt;

import ayd2.ps2026.demo.common.exceptions.jwt.InvalidTokenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    private static final String TOKEN = "jwt-token";
    private static final String USERNAME = "blue-dragon";
    private static final String USER_TYPE = "ADMIN";
    private static final Integer USER_ID = 1;

    private static final String PUBLIC_ENDPOINT_ALL = "/swagger-ui/**";
    private static final String PUBLIC_ENDPOINT_GET = "/actuator/health";
    private static final String PRIVATE_ENDPOINT = "/example/example";
    private static final String POST_METHOD = "POST";
    private static final String GET_METHOD = "GET";

    @Mock
    private JwtTokenInspector jwtTokenInspector;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void isPublicEndpoint_true() {

        // Arrange
        when(request.getRequestURI())
                .thenReturn(PUBLIC_ENDPOINT_GET);

        when(request.getMethod())
                .thenReturn(GET_METHOD);

        // Act
        boolean result =
                jwtAuthenticationFilter.isPublicEndpoint(request);

        // Assert
        assertTrue(result);
    }

    @Test
    void isPublicEndpoint_allMethods_true() {

        // Arrange
        when(request.getRequestURI())
                .thenReturn(PUBLIC_ENDPOINT_ALL);

        when(request.getMethod())
                .thenReturn(null);

        // Act
        boolean result =
                jwtAuthenticationFilter.isPublicEndpoint(request);

        // Assert
        assertTrue(result);
    }

    @Test
    void isPublicEndpoint_false() {

        // Arrange
        when(request.getRequestURI())
                .thenReturn(PRIVATE_ENDPOINT);

        when(request.getMethod())
                .thenReturn(GET_METHOD);

        // Act
        boolean result =
                jwtAuthenticationFilter.isPublicEndpoint(request);

        // Assert
        assertFalse(result);
    }

    @Test
    void extractTokenFromHeader_success() {

        // Arrange
        when(request.getHeader("Authorization"))
                .thenReturn("Bearer " + TOKEN);

        // Act
        Optional<String> result =
                jwtAuthenticationFilter.extractTokenFromHeader(request);

        // Assert
        assertAll(
                () -> assertTrue(result.isPresent()),
                () -> assertEquals(TOKEN, result.get())
        );
    }

    @Test
    void extractTokenFromHeader_empty() {

        // Arrange
        when(request.getHeader("Authorization"))
                .thenReturn(null);

        // Act
        Optional<String> result =
                jwtAuthenticationFilter.extractTokenFromHeader(request);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void validateToken_success() {

        // Arrange
        when(jwtTokenInspector.extractUsername(TOKEN))
                .thenReturn(USERNAME);

        when(jwtTokenInspector.extractUserType(TOKEN))
                .thenReturn(USER_TYPE);

        when(jwtTokenInspector.extractUserId(TOKEN))
                .thenReturn(USER_ID);

        when(jwtTokenInspector.isTokenValid(TOKEN))
                .thenReturn(true);

        // Act
        Optional<UserDetails> result =
                jwtAuthenticationFilter.validateToken(TOKEN);

        // Assert
        assertAll(
                () -> assertTrue(result.isPresent()),

                () -> assertEquals(
                        USERNAME,
                        result.get().getUsername()
                ),

                () -> assertEquals(
                        1,
                        result.get().getAuthorities().size()
                )
        );
    }

    @Test
    void validateToken_invalidToken_returnsEmpty() {

        // Arrange
        when(jwtTokenInspector.extractUsername(TOKEN))
                .thenReturn(USERNAME);

        when(jwtTokenInspector.extractUserType(TOKEN))
                .thenReturn(USER_TYPE);

        when(jwtTokenInspector.extractUserId(TOKEN))
                .thenReturn(USER_ID);

        when(jwtTokenInspector.isTokenValid(TOKEN))
                .thenReturn(false);

        // Act
        Optional<UserDetails> result =
                jwtAuthenticationFilter.validateToken(TOKEN);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void validateToken_existingAuthentication_returnsEmpty() {

        // Arrange
        Authentication authentication =
                mock(Authentication.class);

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        when(jwtTokenInspector.extractUsername(TOKEN))
                .thenReturn(USERNAME);

        when(jwtTokenInspector.extractUserType(TOKEN))
                .thenReturn(USER_TYPE);

        when(jwtTokenInspector.extractUserId(TOKEN))
                .thenReturn(USER_ID);

        // Act
        Optional<UserDetails> result =
                jwtAuthenticationFilter.validateToken(TOKEN);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void authenticateUser_success() {

        // Arrange
        UserDetails userDetails =
                new CustomUserDetails(
                        USER_ID,
                        USERNAME,
                        List.of(
                                new SimpleGrantedAuthority("ROLE_ADMIN")
                        )
                );

        when(request.getRemoteAddr())
                .thenReturn("127.0.0.1");

        // Act
        jwtAuthenticationFilter.authenticateUser(
                userDetails,
                TOKEN,
                request
        );

        // Assert
        Authentication authentication =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        assertAll(
                () -> assertNotNull(authentication),

                () -> assertEquals(
                        USERNAME,
                        authentication.getName()
                ),

                () -> assertEquals(
                        TOKEN,
                        authentication.getCredentials()
                )
        );
    }

    @Test
    void doFilterInternal_publicEndpoint_success()
            throws ServletException, IOException {

        // Arrange
        when(request.getRequestURI())
                .thenReturn(PUBLIC_ENDPOINT_GET);

        when(request.getMethod())
                .thenReturn(POST_METHOD);

        // Act
        jwtAuthenticationFilter.doFilterInternal(
                request,
                response,
                filterChain
        );

        // Assert
        verify(filterChain)
                .doFilter(request, response);
    }

    @Test
    void doFilterInternal_validToken_success()
            throws ServletException, IOException {

        // Arrange
        when(request.getRequestURI())
                .thenReturn("/private");

        when(request.getMethod())
                .thenReturn("GET");

        when(request.getHeader("Authorization"))
                .thenReturn("Bearer " + TOKEN);

        when(request.getRemoteAddr())
                .thenReturn("127.0.0.1");

        when(jwtTokenInspector.extractUsername(TOKEN))
                .thenReturn(USERNAME);

        when(jwtTokenInspector.extractUserType(TOKEN))
                .thenReturn(USER_TYPE);

        when(jwtTokenInspector.extractUserId(TOKEN))
                .thenReturn(USER_ID);

        when(jwtTokenInspector.isTokenValid(TOKEN))
                .thenReturn(true);

        // Act
        jwtAuthenticationFilter.doFilterInternal(
                request,
                response,
                filterChain
        );

        // Assert
        assertAll(
                () -> assertNotNull(
                        SecurityContextHolder.getContext()
                                .getAuthentication()
                ),

                () -> verify(filterChain)
                        .doFilter(request, response)
        );
    }

    @Test
    void doFilterInternal_invalidTokenException_returnsUnauthorized()
            throws ServletException, IOException {

        // Arrange
        PrintWriter writer = mock(PrintWriter.class);

        when(request.getRequestURI())
                .thenReturn("/private");

        when(request.getMethod())
                .thenReturn("GET");

        when(request.getHeader("Authorization"))
                .thenReturn("Bearer " + TOKEN);

        when(response.getWriter())
                .thenReturn(writer);

        when(jwtTokenInspector.extractUsername(TOKEN))
                .thenThrow(new InvalidTokenException());

        // Act
        jwtAuthenticationFilter.doFilterInternal(
                request,
                response,
                filterChain
        );

        // Assert
        assertAll(
                () -> verify(response)
                        .setStatus(HttpStatus.UNAUTHORIZED.value()),

                () -> verify(response)
                        .setContentType("application/json"),

                () -> verify(writer)
                        .write(anyString()),

                () -> verify(filterChain)
                        .doFilter(request, response)
        );
    }

    @Test
    void doFilterInternal_publicEndpoint_returnsImmediately()
            throws ServletException, IOException {

        // Arrange
        when(request.getRequestURI())
                .thenReturn(PUBLIC_ENDPOINT_GET);

        when(request.getMethod())
                .thenReturn(POST_METHOD);

        // Act
        jwtAuthenticationFilter.doFilterInternal(
                request,
                response,
                filterChain
        );

        // Assert
        assertAll(
                () -> verify(filterChain, times(1))
                        .doFilter(request, response),

                () -> verifyNoInteractions(jwtTokenInspector),

                () -> assertNull(
                        SecurityContextHolder.getContext()
                                .getAuthentication()
                )
        );
    }
}