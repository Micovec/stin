package cz.tul.fm.jiri_vokrinek.stin_semestral.security;

import cz.tul.fm.jiri_vokrinek.stin_semestral.dto.UserLoginInfoDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CCUserDetails implements UserDetails {

    private final UserLoginInfoDto loginInfo;

    public CCUserDetails(UserLoginInfoDto loginInfo) {
        this.loginInfo = loginInfo;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections
                .unmodifiableList(AuthorityUtils.createAuthorityList("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return loginInfo.getPassword();
    }

    @Override
    public String getUsername() {
        return loginInfo.getEmail();
    }

    public String getSecret() {
        return loginInfo.getSecret();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}