package com.api.knowknowgram.common.security;


import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.api.knowknowgram.common.enums.ERole;
import com.api.knowknowgram.entity.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String nickname;

    private String email;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private String providerId;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id, String nickname, String email, String password, String providerId,
        Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.providerId = providerId;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(Users user) {        
        List<GrantedAuthority> authorities = 
        Collections.singletonList(new SimpleGrantedAuthority(ERole.fromCode(user.getRole()).name()));

        return new UserDetailsImpl(
            user.getId(), 
            user.getNickname(), 
            user.getEmail(),
            user.getPassword(), 
            user.getProviderId(), 
            authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getProviderId() {
        return providerId;
    }

    @Override
    public String getUsername() {
        return nickname;
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
    public boolean equals(Object o) {
        if (this == o)
        return true;
        if (o == null || getClass() != o.getClass())
        return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}