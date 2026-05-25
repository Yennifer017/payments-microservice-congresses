package ayd2.ps2026.demo.auth.jwt;

import ayd2.ps2026.demo.common.exceptions.jwt.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtTokenInspectorTest {

    private static final String SECRET =
            "mysecretkeymysecretkeymysecretkey12";

    private static final Integer USER_ID = 1;
    private static final String USERNAME = "blue-dragon";
    private static final String ROLE = "ADMIN";

    @Mock
    private JwtConfig jwtConfig;

    @InjectMocks
    private JwtTokenInspector jwtTokenInspector;

    private String validToken;
    private String expiredToken;

    @BeforeEach
    void setUp() {

        when(jwtConfig.getSecretBytes())
                .thenReturn(SECRET.getBytes());

        validToken = Jwts.builder()
                .setSubject(USERNAME)
                .claim(
                        JwtGeneratorService.CLAIM_USER_ID,
                        USER_ID
                )
                .claim(
                        JwtGeneratorService.CLAIM_NAME_USER_ROLE,
                        ROLE
                )
                .setExpiration(
                        new Date(
                                System.currentTimeMillis() + 100000
                        )
                )
                .signWith(
                        Keys.hmacShaKeyFor(SECRET.getBytes()),
                        SignatureAlgorithm.HS256
                )
                .compact();

        expiredToken = Jwts.builder()
                .setSubject(USERNAME)
                .setExpiration(
                        new Date(
                                System.currentTimeMillis() - 100000
                        )
                )
                .signWith(
                        Keys.hmacShaKeyFor(SECRET.getBytes()),
                        SignatureAlgorithm.HS256
                )
                .compact();
    }

    @Test
    void extractUserType_success() throws InvalidTokenException {

        // Act
        String result =
                jwtTokenInspector.extractUserType(validToken);

        // Assert
        assertEquals(ROLE, result);
    }

    @Test
    void extractUserType_noRole_throwsJwtNoUserTypeException() {

        // Arrange
        String token = Jwts.builder()
                .setSubject(USERNAME)
                .setExpiration(
                        new Date(
                                System.currentTimeMillis() + 100000
                        )
                )
                .signWith(
                        Keys.hmacShaKeyFor(SECRET.getBytes()),
                        SignatureAlgorithm.HS256
                )
                .compact();

        // Act & Assert
        assertThrows(
                JwtNoUserTypeException.class,
                () -> jwtTokenInspector.extractUserType(token)
        );
    }

    @Test
    void extractUserType_invalidType_throwsJwtClaimTypeMismatchException() {

        // Arrange
        String token = Jwts.builder()
                .setSubject(USERNAME)

                // Guardamos role como Integer
                // pero se intentará leer como String
                .claim(
                        JwtGeneratorService.CLAIM_NAME_USER_ROLE,
                        123
                )

                .setExpiration(
                        new Date(
                                System.currentTimeMillis() + 100000
                        )
                )
                .signWith(
                        Keys.hmacShaKeyFor(SECRET.getBytes()),
                        SignatureAlgorithm.HS256
                )
                .compact();

        // Act & Assert
        assertThrows(
                JwtClaimTypeMismatchException.class,
                () -> jwtTokenInspector.extractUserType(token)
        );
    }

    @Test
    void extractUsername_success() throws InvalidTokenException {

        // Act
        String result =
                jwtTokenInspector.extractUsername(validToken);

        // Assert
        assertEquals(USERNAME, result);
    }

    @Test
    void extractUsername_noUsername_throwsJwtNoUsernameException() {

        // Arrange
        String token = Jwts.builder()

                .setExpiration(
                        new Date(
                                System.currentTimeMillis() + 100000
                        )
                )
                .signWith(
                        Keys.hmacShaKeyFor(SECRET.getBytes()),
                        SignatureAlgorithm.HS256
                )
                .compact();

        // Act & Assert
        assertThrows(
                JwtNoUsernameException.class,
                () -> jwtTokenInspector.extractUsername(token)
        );
    }

    @Test
    void extractUserId_success() throws InvalidTokenException {

        // Act
        Integer result =
                jwtTokenInspector.extractUserId(validToken);

        // Assert
        assertEquals(USER_ID, result);
    }

    @Test
    void extractUserId_noUserId_throwsJwtNoUserIdException() {

        // Arrange
        String token = Jwts.builder()
                .setSubject(USERNAME)
                .setExpiration(
                        new Date(
                                System.currentTimeMillis() + 100000
                        )
                )
                .signWith(
                        Keys.hmacShaKeyFor(SECRET.getBytes()),
                        SignatureAlgorithm.HS256
                )
                .compact();

        // Act & Assert
        assertThrows(
                JwtNoUserIdException.class,
                () -> jwtTokenInspector.extractUserId(token)
        );
    }

    @Test
    void extractUserId_invalidType_throwsJwtClaimTypeMismatchException() {

        // Arrange
        String token = Jwts.builder()
                .setSubject(USERNAME)

                .claim(
                        JwtGeneratorService.CLAIM_USER_ID,
                        "invalid-id"
                )

                .setExpiration(
                        new Date(
                                System.currentTimeMillis() + 100000
                        )
                )
                .signWith(
                        Keys.hmacShaKeyFor(SECRET.getBytes()),
                        SignatureAlgorithm.HS256
                )
                .compact();

        assertThrows(
                JwtClaimTypeMismatchException.class,
                () -> jwtTokenInspector.extractUserId(token)
        );
    }

    @Test
    void isTokenExpired_false() throws InvalidTokenException {

        // Act
        Boolean result =
                jwtTokenInspector.isTokenExpired(validToken);

        // Assert
        assertFalse(result);
    }

    @Test
    void isTokenExpired_true() {

        // Act & Assert
        assertThrows(
                JwtExpiredException.class,
                () -> jwtTokenInspector.isTokenExpired(expiredToken)
        );
    }

    @Test
    void extractExpiration_noExpiration_throwsJwtNoExpirationException() {

        // Arrange
        String token = Jwts.builder()
                .setSubject(USERNAME)

                // No seteamos expiration

                .signWith(
                        Keys.hmacShaKeyFor(SECRET.getBytes()),
                        SignatureAlgorithm.HS256
                )
                .compact();

        // Act & Assert
        assertThrows(
                JwtNoExpirationException.class,
                () -> jwtTokenInspector.extractExpiration(token)
        );
    }

    @Test
    void isTokenExpired_noExpiration_throwsJwtNoExpirationException() {

        // Arrange
        String token = Jwts.builder()
                .setSubject(USERNAME)

                // No seteamos expiration

                .signWith(
                        Keys.hmacShaKeyFor(SECRET.getBytes()),
                        SignatureAlgorithm.HS256
                )
                .compact();

        // Act & Assert
        assertThrows(
                JwtNoExpirationException.class,
                () -> jwtTokenInspector.isTokenExpired(token)
        );
    }

    @Test
    void extractExpiration_success() throws InvalidTokenException {

        // Act
        Date result =
                jwtTokenInspector.extractExpiration(validToken);

        // Assert
        assertNotNull(result);
    }

    @Test
    void isTokenValid_success() throws InvalidTokenException {

        // Act
        Boolean result =
                jwtTokenInspector.isTokenValid(validToken);

        // Assert
        assertTrue(result);
    }

    @Test
    void isTokenValid_expired_throwsJwtExpiredException() {

        // Act & Assert
        assertThrows(
                JwtExpiredException.class,
                () -> jwtTokenInspector.isTokenValid(expiredToken)
        );
    }

    @Test
    void extractUsername_malformedToken_throwsJwtMalformedException() {

        // Arrange
        String malformedToken = "invalid.token";

        // Act & Assert
        assertThrows(
                JwtMalformedException.class,
                () -> jwtTokenInspector.extractUsername(malformedToken)
        );
    }

    @Test
    void extractUsername_invalidSignature_throwsJwtSignatureInvalidException() {

        // Arrange
        String otherSecret =
                "othersecretothersecretothersecret12";

        String token = Jwts.builder()
                .setSubject(USERNAME)
                .setExpiration(
                        new Date(
                                System.currentTimeMillis() + 100000
                        )
                )
                .signWith(
                        Keys.hmacShaKeyFor(otherSecret.getBytes()),
                        SignatureAlgorithm.HS256
                )
                .compact();

        // Act & Assert
        assertThrows(
                JwtSignatureInvalidException.class,
                () -> jwtTokenInspector.extractUsername(token)
        );
    }

    @Test
    void extractUsername_illegalArgument_throwsJwtIllegalArgumentException() {

        // Act & Assert
        assertThrows(
                JwtIllegalArgumentException.class,
                () -> jwtTokenInspector.extractUsername(null)
        );
    }
}