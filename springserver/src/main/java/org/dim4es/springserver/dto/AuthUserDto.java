package org.dim4es.springserver.dto;

import org.dim4es.springserver.models.User;

public class AuthUserDto {

    private String email;
    private String username;
    private String token;

    public AuthUserDto() {
    }

    public AuthUserDto(User user, String token) {
        this.email = user.getEmail();
        this.username = user.getNickname();
        this.token = token;
    }

    public AuthUserDto(String email, String username, String token) {
        this.email = email;
        this.username = username;
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
