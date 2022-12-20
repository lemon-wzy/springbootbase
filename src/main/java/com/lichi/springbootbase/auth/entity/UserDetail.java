package com.lichi.springbootbase.auth.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @description: 用户信息
 * @author: lichi
 * @date: 2022/12/19 11:43
 * @version: 1.0
 * @since: 2022/12/19
 */
@Data
public class UserDetail implements Serializable, UserDetails {
    @Serial
    private static final long serialVersionUID = 1L;

    private UserInfo userInfo;
    private List<RoleInfo> roleInfoList;
    private Collection<? extends  GrantedAuthority> grantedAuthorities;
    private List<String> roles;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (!ObjectUtils.isEmpty(grantedAuthorities)) {
            return this.grantedAuthorities;
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        List<String> authorities = new ArrayList<>();
        if (!ObjectUtils.isEmpty(roleInfoList)) {
            roleInfoList.forEach(roleInfo -> {
                authorities.add(roleInfo.getRoleCode());
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+roleInfo.getRoleCode()));
            });
        }
        this.grantedAuthorities = grantedAuthorities;
        this.roles = authorities;
        return this.grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.userInfo.getPassword();
    }

    @Override
    public String getUsername() {
        return this.userInfo.getUsername();
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