package org.dim4es.springserver.models;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class Role extends AbstractEntity {

    @Column(name = "role_name")
    private String roleName;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person roleOwner;

    public Role() {
    }

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
