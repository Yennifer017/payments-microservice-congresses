package ayd2.ps2026.demo.auth.jwt;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import ayd2.ps2026.demo.auth.users.models.AppUser;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

/**
 * Servicio encargado de la generación de tokens JWT para usuarios autenticados.
 * 
 * Este servicio construye tokens firmados con información básica del usuario
 * como el rol y el nombre, utilizando una clave secreta definida en la
 * configuración.
 * 
 * El token generado incluye fecha de emisión, expiración y está firmado con
 * HMAC-SHA256.
 * 
 * @author Yennifer de Leon
 * @version 1.0
 * @since 2025-06-01
 */
@Service
@RequiredArgsConstructor
public class JwtGeneratorService {

    /**
     * Nombres de las claims que seran incluidas en el toke JWT
     */
    public static final String CLAIM_USER_ID = "id";
    public static final String CLAIM_USER_USERNAME = "username";
    public static final String CLAIM_NAME_USER_ROLE = "role";
    public static final String CLAIM_NAME_USER_STATUS = "status";

}
