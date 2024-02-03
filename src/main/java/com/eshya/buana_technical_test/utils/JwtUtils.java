package com.eshya.buana_technical_test.utils;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    public String generateJWTTokenOAuth2(Authentication authentication) {
        return generateToken(authentication.getName());
    }

    // generateToken from Credentials Name
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject("oauth2_token")
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + 864000000))
                .claim("username", username)
                .signWith(SignatureAlgorithm.HS512,Constant.TOKEN_SECRET).compact();
    }
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    public Date getIssuedAtFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }
    /**
     * retrieving any information from token we will need the secret key
     *
     *
     * @param token
     * @return Claims :Additional token information
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(Constant.TOKEN_SECRET).parseClaimsJws(token).getBody();
    }

    public String getUsernameFromToken(String token) {
        logger.info("my token is {}", token);
        Object userId = Jwts.parser().setSigningKey(Constant.TOKEN_SECRET).parseClaimsJws(token).getBody().get("username");
        return (userId == null) ? "" : String.valueOf(userId);
    }
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(Constant.TOKEN_SECRET).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
