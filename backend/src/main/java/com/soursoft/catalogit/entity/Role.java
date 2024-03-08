package com.soursoft.catalogit.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue( strategy =  GenerationType.IDENTITY)
    @Column( name = "rid")
    private long roleId;

    @Column(name = "rname")
    private String roleName;

    @ManyToMany(mappedBy = "roles")
    private Set<User> roleUsers;

    public Role() {}

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
