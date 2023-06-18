package org.dim4es.springserver.dto;

import org.dim4es.springserver.model.User;

public class AuthUserDto {

    private Long id;
    private String email;
    private String username;
    private String token;

    public AuthUserDto() {
    }

    public AuthUserDto(User user, String token) {
        id = user.getId();
        email = user.getEmail();
        username = user.getUsername();
        this.token = token;
    }

    public AuthUserDto(Long id, String email, String username, String token) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
