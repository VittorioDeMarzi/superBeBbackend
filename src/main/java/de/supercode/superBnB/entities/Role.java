package de.supercode.superBnB.entities;

public enum Role {
    GUEST,
    USER,
    ADMIN;

    public String getAuthority() {
        return this.name();
    }
}