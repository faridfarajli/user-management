package org.example;

import org.example.config.JwtTokenConfigProperties;
import org.example.dto.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.junit.platform.commons.util.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final ModelMapper modelMapper;
    private final JwtTokenConfigProperties properties;

    private Key key;


    @PostConstruct
    public void init(){
    byte[] keyBytes;
    if (StringUtils.isBlank(properties.getJwtProperties().getSecret())){
        throw new RuntimeException("Token config not found");
    }
    keyBytes = Decoders.BASE64.decode(properties.getJwtProperties().getSecret());
    key = Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims parseToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJwt(token)
                .getBody();
    }

    public String issueToken(Authentication authentication){
     final JwtBuilder jwtBuilder =Jwts.builder()
             .setIssuedAt(new Date())
             .setExpiration(Date.from(Instant.now().plus(Duration.ofSeconds(properties.getJwtProperties().getTokenValidityInSeconds()))))
             .setHeader(Map.of("type","JWT"))
             .signWith(key, SignatureAlgorithm.HS256)
             .addClaims(Map.of("authorities",authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList(),
                     "principal",modelMapper.map(authentication.getPrincipal(), UserDto.class)));
       return jwtBuilder.compact();
    }
}
