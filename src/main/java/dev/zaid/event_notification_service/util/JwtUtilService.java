package dev.zaid.event_notification_service.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtUtilService {
        private static final String SECRET = "mysecretkeymysecretkeymysecretkey";

        private SecretKey getSignKey() {
            return Keys.hmacShaKeyFor(SECRET.getBytes());
        }

        public String generateToken(String username) {
            return Jwts.builder()
                    .subject(username)
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                    .signWith(getSignKey())
                    .compact();
        }

        public String extractUsername(String token) {
            return Jwts.parser()
                    .verifyWith(getSignKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        }

        public boolean validateToken(String token, UserDetails userDetails) {
            String username = extractUsername(token);
            return username.equals(userDetails.getUsername());
        }
    }
