package kr.co.trands.util;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import kr.co.trands.controller.dto.JwtTokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JWTUtil {

    // private final static String TOKEN_KEY = "s";
    private final static String TOKEN_KEY = "w";

    @Autowired
    private DateUtil dateUtils;

    public String createJwtToken(JwtTokenDto param) throws Exception {

        // JWT HEADERs
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS512");

        // JWT PAYLOADS
        Map<String, Object> payLoads = new HashMap<>();

        ZonedDateTime curTimeZdt = dateUtils.createUtcDateTime();
        long curTimeZdtTs = curTimeZdt.toInstant().toEpochMilli();
        Date now = new Date();
        now.setTime(curTimeZdtTs + param.getExpireTime());

        payLoads.put("accountId", param.getAccountId());

        SecretKey key = Keys.hmacShaKeyFor(TOKEN_KEY.getBytes());

        String jwtToken = Jwts.builder()
                .header()
                .add(headers)
                .and()
                .claims(payLoads)
                .expiration(now)
                .encryptWith(key, Jwts.ENC.A256CBC_HS512)
                .compact();

        log.info("new token: {}", jwtToken);

        return jwtToken;
    }

    public JwtTokenDto parseJwtToken(String jwtToken) {

        JwtTokenDto jwtTokenDto = new JwtTokenDto();

        SecretKey key = Keys.hmacShaKeyFor(TOKEN_KEY.getBytes());

        Jwt<?, ?> jwtParser = Jwts.parser()
                .decryptWith(key)
                .build()
                .parse(jwtToken);

        Claims claims = (Claims)jwtParser.getPayload();

        jwtTokenDto.setAccountId(claims.get("accountId", String.class));
        jwtTokenDto.setExpireTime(claims.getExpiration().getTime());

        return jwtTokenDto;
    }

}
