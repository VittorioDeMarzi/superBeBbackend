package de.supercode.superBnB.entities;

public enum Role {
    ROLE_GUEST,
    ROLE_USER,
    ROLE_ADMIN;

    public String getAuthority() {
        return this.name();
    }
}