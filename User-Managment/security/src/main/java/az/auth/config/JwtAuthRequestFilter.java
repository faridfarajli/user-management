package az.auth.config;


import az.auth.JwtService;
import az.auth.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class JwtAuthRequestFilter extends OncePerRequestFilter {

    public static final String ACCESS_TOKEN_COOKIE = "jwt_accessToken";
    public static final String AUTHORITIES_CLAIM = "authorities";
    private final ObjectMapper objectMapper;
    private final JwtService jwtService;
    public static final String BEARER = "Bearer";


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Optional<org.springframework.security.core.Authentication> authenticationOptional = authenticate(getCookie(request));

    }

    private Optional<Cookie> getCookie(HttpServletRequest request) {
        Cookie[] cookies = Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]);
        return Arrays.stream(cookies).filter(cookie -> cookie.getName().equals(ACCESS_TOKEN_COOKIE))
                .findFirst();
    }

    private Optional<org.springframework.security.core.Authentication> authenticate(Optional<Cookie> cookie) {
        if (cookie.isEmpty()) {
            return Optional.empty();
        }

        final Claims claims = jwtService.parseToken(cookie.get().getValue());
        final Collection<? extends GrantedAuthority> userAuthorities = getUserAuthorities(claims);

        UserDetails userDetails = objectMapper.convertValue(claims.get("principal"), UserDto.class);
        final UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, "", userAuthorities);
        return Optional.of(authenticationToken);
    }

    private Collection<? extends GrantedAuthority> getUserAuthorities(Claims claims) {
        List<?> roles = claims.get(AUTHORITIES_CLAIM,List.class);
        return roles
                .stream()
                .map(a->new SimpleGrantedAuthority(a.toString()))
                .collect(Collectors.toList());
    }

         private Optional<String> getBearer(String header){
          if (header==null || !header.startsWith(BEARER)){
             return Optional.empty();
          }
          final String jwt = header.substring(BEARER.length())
                  .trim();
          return Optional.ofNullable(jwt);
         }
}
