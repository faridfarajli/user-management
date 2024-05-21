package org.example.dto;


import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class UserAuthorityDto implements GrantedAuthority {

    private Long id;

    private String authority;
}

