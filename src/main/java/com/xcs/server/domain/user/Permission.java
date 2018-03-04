package com.xcs.server.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 32)
    private String name;
    @Column(length = 64)
    private String url;
    @ManyToMany(mappedBy = "permissions")
    @JsonIgnore
    private Set<UserGroup> userGroups=new HashSet<>();
    @JsonSerialize(using = PermissionFamilyJsonSerializer.class)
    @ManyToOne()
    @JoinColumn(nullable = false)
    private PermissionFamily permissionFamily;

    public Permission() {
    }

    public Permission(Integer id) {
        this.id=id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<UserGroup> getUserGroups() {
        return userGroups;
    }

    public void setUserGroups(Set<UserGroup> userGroups) {
        this.userGroups = userGroups;
    }

    public PermissionFamily getPermissionFamily() {
        return permissionFamily;
    }

    public void setPermissionFamily(PermissionFamily permissionFamily) {
        this.permissionFamily = permissionFamily;
    }
}
