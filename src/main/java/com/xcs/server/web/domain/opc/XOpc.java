package com.xcs.server.web.domain.opc;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class XOpc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 64,nullable = false)
    private String host;
    @Column(length = 64,nullable = false)
    private String serverProgID;
    @Column(length = 16,nullable = false)
    private String serverClientHandle;
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "opc")
    @JsonIgnore
    private Set<XGroup> groups=new HashSet<XGroup>();

    public XOpc() {
    }

    public XOpc(String host, String serverProgID, String serverClientHandle) {
        this.host = host;
        this.serverProgID = serverProgID;
        this.serverClientHandle = serverClientHandle;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getServerProgID() {
        return serverProgID;
    }

    public void setServerProgID(String serverProgID) {
        this.serverProgID = serverProgID;
    }

    public String getServerClientHandle() {
        return serverClientHandle;
    }

    public void setServerClientHandle(String serverClientHandle) {
        this.serverClientHandle = serverClientHandle;
    }

    public Set<XGroup> getGroups() {
        return groups;
    }

    public void setGroups(Set<XGroup> groups) {
        this.groups = groups;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof XOpc)
            return this.getId().equals(((XOpc)obj).getId());
        return false;
    }
}
