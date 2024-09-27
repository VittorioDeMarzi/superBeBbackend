package de.supercode.superBnB.entities.user;

public enum Role {
    GUEST,
    USER,
    ADMIN;

    public String getAuthority() {
        return this.name();
    }
}