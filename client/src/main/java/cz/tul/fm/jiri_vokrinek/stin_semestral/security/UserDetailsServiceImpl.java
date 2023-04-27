package cz.tul.fm.jiri_vokrinek.stin_semestral.security;

import cz.tul.fm.jiri_vokrinek.stin_semestral.data.UserData;
import cz.tul.fm.jiri_vokrinek.stin_semestral.dto.UserLoginInfoDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserData userData;

    public UserDetailsServiceImpl(UserData userData) {
        this.userData = userData;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserLoginInfoDto dto = userData.findUserLoginInfo(username).orElseThrow();
        return new CCUserDetails(dto);
    }
}
