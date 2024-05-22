package az.auth.dto;


import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Data
public class UserDto implements UserDetails {


    private Long id;

    private List<UserAuthorityDto> authorities;

    public String getPassword() {
        return "";
    }

    private String username;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    private boolean enabled;
}
