package ru.breathoffreedom.mvc.models.user;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by boris_azanov on 29.11.16.
 */
public enum Role implements GrantedAuthority {
    ROLE_USER,
    ROLE_ADMIN,
    ROLE_SUPER_USER;

    public String getAuthority() {
        return name();
    }
}
