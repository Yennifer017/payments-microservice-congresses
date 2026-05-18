package ayd2.ps2026.demo.auth.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    private Integer id;
    private String username;
    private List<GrantedAuthority> authorities;

    public CustomUserDetails(
            Integer id,
            String username,
            List<GrantedAuthority> authorities
    ) {
        this.id = id;
        this.username = username;
        this.authorities = authorities;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return username;
    }
}
