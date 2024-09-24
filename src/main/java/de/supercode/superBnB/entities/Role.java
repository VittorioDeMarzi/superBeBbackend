package de.supercode.superBnB.entities;

public enum Role {
    ROLE_USER,
    ROLE_ADMIN;

    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}