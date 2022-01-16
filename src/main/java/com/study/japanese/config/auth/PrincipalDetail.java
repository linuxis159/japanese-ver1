package com.study.japanese.config.auth;

import com.study.japanese.entity.User;
import com.study.japanese.role.UserRole;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
@Getter
public class PrincipalDetail implements UserDetails {
    private User user;

    public PrincipalDetail(User user){
        this.user = user;

    }
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getId();
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collectors = new ArrayList();
        collectors.add(() -> {
            return "ROLE_"+user.getRole().toString();
        });


        return null;
    }
}
