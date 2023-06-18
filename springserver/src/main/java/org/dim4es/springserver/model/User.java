package org.dim4es.springserver.model;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "user")
public class User extends AbstractEntity {

    @Column(name = "email")
    private String email;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "last_location")
    private String lastLocation;

    @Column(name = "last_location_update")
    private Instant lastLocationUpdate;

    @ManyToOne
    @JoinColumn(name = "id_city")
    private City city;

    @ManyToOne
    @JoinColumn(name = "id_country")
    private Country country;

    @Enumerated
    @Column(nullable = false)
    private UserStatus status;

    @Column(name = "last_status_update")
    private Instant lastStatusUpdate;

    public User() {
    }

    public User(String email, String username, String password) {
        status = UserStatus.OFFLINE;
        this.email = email;
        this.username = username;
        this.password = password;
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

    public void setUsername(String nickname) {
        this.username = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastLocation() {
        return lastLocation;
    }

    public void setLastLocation(String lastLocation) {
        this.lastLocation = lastLocation;
    }

    public Instant getLastLocationUpdate() {
        return lastLocationUpdate;
    }

    public void setLastLocationUpdate(Instant lastLocationUpdate) {
        this.lastLocationUpdate = lastLocationUpdate;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public Instant getLastStatusUpdate() {
        return lastStatusUpdate;
    }

    public void setLastStatusUpdate(Instant lastStatusUpdate) {
        this.lastStatusUpdate = lastStatusUpdate;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
